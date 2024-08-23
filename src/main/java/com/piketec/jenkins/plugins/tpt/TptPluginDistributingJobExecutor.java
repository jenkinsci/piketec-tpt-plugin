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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.piketec.jenkins.plugins.tpt.TptLog.LogLevel;
import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;
import com.piketec.jenkins.plugins.tpt.api.callables.CleanUpCallable;
import com.piketec.jenkins.plugins.tpt.api.callables.GetTestCasesCallableResult;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Job;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import jenkins.model.Jenkins;

class TptPluginDistributingJobExecutor {

  private TptLogger logger;

  private Launcher launcher;

  private Run< ? , ? > build;

  private FilePath workspace;

  private TaskListener listener;

  private FilePath[] exePaths;

  private String arguments;

  private List<JenkinsConfiguration> executionConfigs;

  private int tptPort;

  private String tptBindingName;

  private String workerJobName;

  private long tptStartupWaitTime;

  private int workerJobCount;

  private int workerJobTries;

  private String jUnitXmlPath;

  private LogLevel jUnitLogLevel;

  private boolean enableJunit;

  /**
   * @param build
   *          to get the workspace, for the cleanuptask and for triggering a build for a worker job
   * @param launcher
   *          to execute a process
   * @param listener
   * @param exePaths
   *          the paths to the tpt executables
   * @param executionConfigs
   *          all the jenkins configurations given in the descriptor, used to get the
   *          Files,Execution Configuration, test Set, testDataDir, reportDir,etc
   * @param tptPort
   *          the port for binding to the TptApi
   * @param tptBindingName
   *          the binding name used to connect to the TptApi (for the registry)
   * @param workerJobName
   *          the name of the worker job, used for putting the workload to the right worker job
   * @param tptStartupWaitTime
   *          the time it should wait before start tpt
   * @param workerJobCount
   *          the number of workerJobs that will be executed
   * @param workerJobTries
   *          used for the retryablejob
   * @param jUnitXmlPath
   *          the path where the jUnit XML is going to be created
   * @param jUnitLogLevel
   * @param enableJunit
   *          to know if is necessary to generate the jUnit XML
   */
  TptPluginDistributingJobExecutor(Run< ? , ? > build, FilePath workspace, Launcher launcher,
                                   TaskListener listener, FilePath[] exePaths, String arguments,
                                   List<JenkinsConfiguration> executionConfigs, int tptPort,
                                   String tptBindingName, String workerJobName,
                                   long tptStartupWaitTime, int workertJobCount, int workerJobTries,
                                   String jUnitXmlPath, LogLevel jUnitLogLevel,
                                   boolean enableJunit) {
    this.logger = new TptLogger(listener.getLogger());
    this.launcher = launcher;
    this.build = build;
    this.workspace = workspace;
    this.listener = listener;
    this.exePaths = exePaths;
    this.arguments = arguments;
    this.executionConfigs = executionConfigs;
    this.tptPort = tptPort;
    this.tptBindingName = tptBindingName;
    this.workerJobName = workerJobName;
    this.tptStartupWaitTime = tptStartupWaitTime;
    this.workerJobCount = workertJobCount;
    this.workerJobTries = workerJobTries;
    this.jUnitLogLevel = jUnitLogLevel;
    this.jUnitXmlPath = jUnitXmlPath;
    this.enableJunit = enableJunit;
  }

  /**
   * It binds to the Tpt Api , check if the given Execution Configuration exists. Prepares the test-
   * and data-directories. Then it split all the test cases (the currently set test set or the
   * explicitly given test set) in smaller chunks of tests cases, then creates the workloads for the
   * worker jobs. After that by calling the retryable job , it schedules the builds of the worker
   * jobs. Then it joins all the threads from the worker jobs, collect the results and regenerate a
   * new Overview report with the data from the worker jobs. It publishes the Junit XML if
   * configured and resets the temporary settings to the original values.
   * 
   * @return true if the execution from worker jobs and distributing job were successful.
   * @throws InterruptedException
   */
  boolean execute() throws InterruptedException {
    TptApiAccess tptApiAccess = new TptApiAccess(launcher, logger, exePaths,
        Utils.parseCommandLine(arguments), tptPort, tptBindingName, tptStartupWaitTime);
    boolean success = true;
    // We delete the JUnit results before iterating over the jenkinsConfigs
    if (workspace == null) {
      logger.error("No workspace available");
      return false;
    }
    removeJUnitData(workspace);
    try {
      for (JenkinsConfiguration ec : executionConfigs) {
        success &= executeOneConfig(ec, tptApiAccess);
      }
    } finally {
      logger.info("Close open TPT project of distributing job and worker jobs.");
      if (!CleanUpTask.cleanUp(build, logger)) {
        logger.error("Could not close all open TPT files. "
            + "There is no guarantee next run will be be done with correct file version.");
        success = false;
      }
    }
    return success;
  }

  private void removeJUnitData(FilePath workspace) throws InterruptedException {
    if (!StringUtils.isBlank(jUnitXmlPath)) {
      FilePath path = new FilePath(workspace, jUnitXmlPath);
      logger.info("Create and/or clear JUnit XML directory " + path.getRemote());
      try {
        path.mkdirs();
        path.deleteContents();
      } catch (IOException e) {
        logger.error("Could not create and/or clear JUnit XML directory " + path.getRemote());
      }
    }
  }

  /**
   * This method collects all testcases that are supposed to be executed via the TPT API, divides
   * them into different workloads and calls the worker jobs to execute these. Then it collects
   * their results.
   */
  private boolean executeOneConfig(JenkinsConfiguration unresolvedConfig, TptApiAccess tptApiAccess)
      throws InterruptedException {
    boolean success = true;
    if (!unresolvedConfig.isEnableTest()) {
      return true;
    }
    // Resolve $-vars in paths, test set and execution config names
    JenkinsConfiguration resolvedConfig = unresolvedConfig;
    if (build instanceof AbstractBuild) {
      resolvedConfig = unresolvedConfig.replaceAndNormalize(
          Utils.getEnvironment((AbstractBuild< ? , ? >)build, launcher, logger));
    }
    if (!(build instanceof AbstractBuild)) {
      // We cannot check all IDs beforehand for pipeline jobs so do it here
      if (!Utils.checkId(resolvedConfig, build, logger)) {
        return false;
      }
    }
    // Get necessery paths the user added in the job configuration:
    GetTestCasesCallableResult testCases = null;
    if (workspace == null) {
      logger.error("No workspace available");
      return false;
    }
    FilePath testDataPath = new FilePath(workspace, Utils.getGeneratedTestDataDir(resolvedConfig));
    FilePath reportPath = new FilePath(workspace, Utils.getGeneratedReportDir(resolvedConfig));
    FilePath tptFilePath = new FilePath(workspace, resolvedConfig.getTptFile());
    try {
      logger.info("Create and/or clean test data directory \"" + testDataPath.getRemote() + "\"");
      testDataPath.mkdirs();
      Utils.deleteFiles(testDataPath);
      logger.info("Create and/or clean report directory \"" + reportPath.getRemote() + "\"");
      reportPath.mkdirs();
      reportPath.deleteContents();
    } catch (IOException e) {
      logger.error("Could not create or clear directories: " + e.getMessage());
      return false;
    }
    // Register cleanup task that is called in the end to close remote TPT Project
    CleanUpCallable cleanUpCallable = new CleanUpCallable(listener, "localhost", tptPort,
        tptBindingName, exePaths, tptStartupWaitTime, tptFilePath);
    new CleanUpTask(build, cleanUpCallable, launcher);
    // Get the list of testcases via the TPT API
    testCases = tptApiAccess.getTestCases(tptFilePath, resolvedConfig.getConfiguration(),
        resolvedConfig.getTestSet());
    if (testCases == null) {
      logger.error("Unable to get test cases via TPT API.");
      return false;
    }
    // Divide testcases into Workloads for the worker jobs to execute
    ArrayList<RetryableJob> retryableJobs = new ArrayList<>();
    // create test sets for worker jobs
    int workerJobSize;
    int remainer;
    if (workerJobCount >= 1) {
      workerJobSize = testCases.testCases.size() / workerJobCount;
      remainer = testCases.testCases.size() % workerJobCount;
    } else {
      workerJobSize = 1;
      remainer = 0;
    }
    ArrayList<List<String>> subTestSets =
        getSubTestSets(testCases.testCases, workerJobSize, remainer);
    // start one job for every test set
    Job workerJob = null;
    Jenkins jenkinsInstance = Jenkins.getInstanceOrNull();
    if (jenkinsInstance == null) {
      logger.error("No jenkins instance found");
      return false;
    }
    for (Job j : jenkinsInstance.getAllItems(Job.class)) {
      if (j.getName().equals(workerJobName)) {
        workerJob = j;
      }
    }
    if (workerJob == null) {
      logger.error("Worker job \"" + workerJobName + "\" not found");
      return false;

    }
    for (List<String> subTestSet : subTestSets) {
      logger.info("Create job for \"" + subTestSet + "\"");

      // creates the workloads for the worker jobs, with the smaller chunks of testsets
      WorkLoad workloadToAdd =
          new WorkLoad(unresolvedConfig, subTestSet, workspace, build, testDataPath, reportPath);
      // it adds the workloads to an static HashMap.
      WorkLoad.putWorkLoad(workerJobName, workloadToAdd);
      // Creates a retryable job , there are the builds scheduled. So the logic is : We put a
      // workload in a static HashMap and then we trigger a build for a worker job. In that way we
      // are distributing the builds on the worker jobs.
      RetryableJob retryableJob = new RetryableJob(workerJobTries, logger, workerJob);
      retryableJob.perform(build, listener);
      retryableJobs.add(retryableJob);
    }
    logger.info("Waiting for completion of child jobs.");
    for (RetryableJob retryableJob : retryableJobs) {
      try {
        retryableJob.join();
        Result result = retryableJob.getResult();
        if (result != null && result.isWorseThan(Result.UNSTABLE)) {
          success = false;
          logger.error("Child job failed.");
        }
      } catch (InterruptedException e) {
        logger.info("Stopping worker jobs.");
        logger.interrupt(e.getMessage());
        for (RetryableJob retryableJobToCancle : retryableJobs) {
          retryableJobToCancle.cancel();
        }
        throw e;
      }
    }
    // Build Overview report:
    logger.info("Building overview report.");
    boolean buildingReportWorked = tptApiAccess.runOverviewReport(tptFilePath,
        resolvedConfig.getConfiguration(), resolvedConfig.getTestSet(), reportPath, testDataPath);
    if (!buildingReportWorked) {
      logger.error("Building overview report did not work!");
    }
    try {
      int foundTestData = 0;
      if (enableJunit) {
        logger.info("*** Publishing as JUnit results now ***");
        foundTestData = Utils.publishAsJUnitResults(workspace, resolvedConfig, testDataPath,
            jUnitXmlPath, jUnitLogLevel, logger);
        logger.info("*** Publishing finished ***");
      } else {
        foundTestData = Publish.getTestcases(testDataPath, logger).testCases.size();
      }
      if (foundTestData != testCases.testCaseCount) {
        // testCases.testCaseCount is some kind of maximal number of test cases that may have been
        // executed. Test set conditions are only able to reduce the number of executed test cases.
        // So, we found exaclty testCases.testCaseCount test results, we can assume that everything
        // is fine.
        if (foundTestData > testCases.testCaseCount) {
          logger.error("More test results found than test cases executed.");
          return false;
        } else if (testCases.tptVersion.supportsTestCaseConditions()
            && !testCases.tptVersion.supportsTestCaseConditionAccess()) {
          logger.warn("Unable to check if all test results are present."
              + " The used TPT version supports test set condtions but no API access to check if test"
              + " set condtions are configured. We found " + foundTestData
              + " test results and would expected to find " + testCases.testCaseCount
              + " without any test set conditions.");
        } else if (testCases.tptVersion.supportsTestCaseConditionAccess()
            && testCases.testCaseConditionsPresent) {
          logger.warn("Unable to check if all test results are present. Some test sets use"
              + " test set condtions. Test set condtions cannot be evaluated to calculate the number"
              + " of executed test cases. We found " + foundTestData
              + " test results and would expected to find " + testCases.testCaseCount
              + " without any test set conditions.");
        } else {
          logger.error(
              "Found only " + foundTestData + " of " + testCases.testCaseCount + " test results.");
          return false;
        }
      }
    } catch (RemoteException e) {
      logger.error(e.getMessage());
      return false;
    } catch (IOException e) {
      logger.error("Could not publish result: " + e.getMessage());
      return false;
    }
    return Utils.checkIdAndAddInvisibleActionTPTExecution(resolvedConfig, build, logger) & success;
  }

  private ArrayList<List<String>> getSubTestSets(Collection<String> testCases, int workerJobSize,
                                                 int remainer) {
    ArrayList<List<String>> testSets = new ArrayList<>();
    ArrayList<String> currentTestSet = new ArrayList<>();
    Iterator<String> iterator = testCases.iterator();
    while (iterator.hasNext()) {
      currentTestSet.add(iterator.next());
      if (currentTestSet.size() == workerJobSize) {
        if (remainer > 0) {
          assert iterator.hasNext();
          // distribute remainer evenly
          if (iterator.hasNext()) {
            currentTestSet.add(iterator.next());
          }
          remainer--;
        }
        testSets.add(currentTestSet);
        currentTestSet = new ArrayList<>();
      }
    }
    if (!currentTestSet.isEmpty()) {
      testSets.add(currentTestSet);
    }
    return testSets;
  }

}
