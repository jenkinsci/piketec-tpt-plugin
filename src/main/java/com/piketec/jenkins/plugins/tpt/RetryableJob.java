package com.piketec.jenkins.plugins.tpt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.Run;
import hudson.plugins.parameterizedtrigger.BuildTriggerConfig;

class RetryableJob {

  private BuildTriggerConfig config;

  private int tries;

  private Thread runner;

  private TptLogger logger;

  private InterruptedException interruptedException = null;

  RetryableJob(int tries, TptLogger logger, BuildTriggerConfig config) {
    if (tries < 1) {
      tries = 1;
    }
    this.tries = tries;
    this.logger = logger;
    this.config = config;
  }

  void perform(final AbstractBuild< ? , ? > build, final Launcher launcher,
               final BuildListener listener) {
    runner = new Thread(new Runnable() {

      @Override
      public void run() {
        boolean success = false;
        while (tries > 0 && !success) {
          List<Future<Run>> futures = new ArrayList<>();
          try {
            futures.addAll(config.perform3(build, launcher, listener).values());
            for (Future<Run> future : futures) {
              Run run = future.get();
              // retry if cancled or failed
              Result result = run.getResult();
              if (result != null) {
                success = result.isBetterOrEqualTo(Result.UNSTABLE);
              } else {
                assert false : "Build should not be running since we used future.get()";
                success = true;
              }
              if (future.isCancelled()) {
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
            logger.info("Job execution failed. Scheduling job for retry.");
          }
        }
      }
    });
    runner.start();
  }

  void join() throws InterruptedException {
    runner.join();
    if (interruptedException != null) {
      throw interruptedException;
    }
  }

  void cancel() {
    runner.interrupt();
  }

}
