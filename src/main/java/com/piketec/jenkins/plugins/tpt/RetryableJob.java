/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2018 PikeTec GmbH
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.piketec.jenkins.plugins.tpt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.Cause;
import hudson.model.CauseAction;
import hudson.model.Job;
import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.StringParameterValue;
import hudson.model.TaskListener;
import hudson.model.Cause.UpstreamCause;
import jenkins.model.ParameterizedJobMixIn;

class RetryableJob {

  private Job workerJob;

  private int tries;

  private Thread runner;

  private TptLogger logger;

  private InterruptedException interruptedException = null;

  private Run lastRun = null;

  /**
   * @param tries
   *          , how many tries should be done pro build
   * @param logger
   *          for prinitng messages
   * @param workerJob
   *          the job that might be retried
   */
  RetryableJob(int tries, TptLogger logger, Job workerJob) {
    if (tries < 1) {
      tries = 1;
    }
    this.tries = tries;
    this.logger = logger;
    this.workerJob = workerJob;

  }

  /**
   * Schedules the builds triggered by the distributingJobRun by calling schedule()
   * 
   * @see schedule
   * 
   * @param build
   *          to get the environment and for scheduling the build. It will be the same build
   *          scheduled but with different testcases.
   * @param listener
   *          to get the environment and for scheduling the build.
   */
  void perform(final Run< ? , ? > build, final TaskListener listener) {
    runner = new Thread(new Runnable() {

      @Override
      public void run() {
        boolean success = false;
        while (tries > 0 && !success) {
          List<Future<Run>> futures = new ArrayList<>();
          try {
            if (build instanceof AbstractBuild< ? , ? >) {
              EnvVars env = build.getEnvironment(listener);
              env.overrideAll(((AbstractBuild< ? , ? >)build).getBuildVariables());
            }
            // To be able to enqueue the same build multiple times, they have to be made
            // artificially different. We do that by adding a UUID. Everything else did not work.
            ArrayList<Action> parameterActions = new ArrayList<>();
            ArrayList<ParameterValue> parameterValues = new ArrayList<>();
            parameterValues.add(new StringParameterValue(UUID.randomUUID().toString(),
                UUID.randomUUID().toString()));
            parameterActions.add(new ParametersAction(parameterValues));

            final Future<Run> scheduled = schedule(build, workerJob,
                ((ParameterizedJobMixIn.ParameterizedJob)workerJob).getQuietPeriod(),
                parameterActions);
            if (scheduled != null) {
              futures.add(scheduled);
            }

            for (Future<Run> future : futures) {
              Run run = future.get();
              lastRun = run;
              // retry if cancled or failed
              Result result = run.getResult();
              if (result != null) {
                success = result.isBetterOrEqualTo(Result.UNSTABLE);
              } else {
                assert false : "Build should not be running since we used future.get()";
                success = true;
              }
              if (future.isCancelled()) {
                logger.error("Execution of test worker job was canceled.");
                tries = 0;
              }
            }
          } catch (InterruptedException e) {
            interruptedException = e;
            tries = 0;
            for (Future<Run> future : futures) {
              future.cancel(true);
            }
          } catch (IOException e) {
            logger.error(e.getMessage());
            // retry
          } catch (ExecutionException e) {
            logger.error(e.getMessage());
            // retry
          }
          tries--;
          if (!success && tries > 0) {
            logger.info(
                "Job execution failed. Scheduling job for retry. It is possible that two test cases"
                    + " have the same name, if so please make the test cases names unique.");
          }
        }
      }
    });
    runner.start();
  }

  /**
   * joins the threads
   */
  void join() throws InterruptedException {
    runner.join();
    if (interruptedException != null) {
      throw interruptedException;
    }
  }

  /**
   * interrupt the thread
   */
  void cancel() {
    runner.interrupt();
  }

  /**
   * Get the result of the last execution. Returns <code>null</code> if no execution has finished
   * yet.
   * 
   * @return The result of the last execution
   */
  @CheckForNull
  Result getResult() {
    Run runToGetResultFrom = lastRun;
    return runToGetResultFrom == null ? null : runToGetResultFrom.getResult();
  }

  /**
   * Schedules a build throgh ParameterizedJobMixIn.ParameterizedJob
   * 
   * @param build
   *          to get the cause from the build
   * @param project
   *          check if the job is triggable
   * @param quietPeriod
   *          for the method scheduleBuild2
   * @param list
   *          for the method scheduleBuild2
   * @return
   */
  // from prametrized trigger plugin BuildTriggerConfig
  @SuppressWarnings("unchecked")
  protected Future<Run> schedule(@Nonnull Run< ? , ? > build, @Nonnull final Job project,
                                 int quietPeriod, @Nonnull List<Action> list) {
    Cause cause = new UpstreamCause(build);
    List<Action> queueActions = new ArrayList<>(list);
    queueActions.add(new CauseAction(cause));

    // Includes both traditional projects via AbstractProject and Workflow Job
    if (project instanceof ParameterizedJobMixIn.ParameterizedJob) {
      final ParameterizedJobMixIn< ? , ? > parameterizedJobMixIn = new ParameterizedJobMixIn() {

        @Override
        protected Job< ? , ? > asJob() {
          return project;
        }
      };
      return (Future<Run>)parameterizedJobMixIn.scheduleBuild2(quietPeriod,
          queueActions.toArray(new Action[queueActions.size()]));
    }
    return null;
  }

}
