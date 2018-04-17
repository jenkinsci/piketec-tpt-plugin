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

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.piketec.jenkins.plugins.tpt.TptLog.LogLevel;
import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.ExecutionConfiguration;
import com.piketec.tpt.api.ExecutionConfigurationItem;
import com.piketec.tpt.api.ExecutionStatus;
import com.piketec.tpt.api.OpenResult;
import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.RemoteCollection;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.TestSet;
import com.piketec.tpt.api.TptApi;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Job;
import jenkins.model.Jenkins;

class TptPluginMasterJobExecutor {

  private TptLogger logger;

  private Launcher launcher;

  private AbstractBuild< ? , ? > build;

  private BuildListener listener;

  private FilePath[] exePaths;

  private List<JenkinsConfiguration> executionConfigs;

  private int tptPort;

  private String tptBindingName;

  private String slaveJobName;

  private long tptStartupWaitTime;

  private int slaveJobCount;

  private int slaveJobTries;

  private String jUnitXmlPath;

  private LogLevel jUnitLogLevel;

  private boolean enableJunit;

  /**
   * @param build
   *          to get the workspace, for the cleanuptask and for triggering a build for a slave
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
   * @param slaveJobName
   *          the name of the slave job, used for putting the workload to the right slave
   * @param tptStartupWaitTime
   *          the time it should wait before start tpt
   * @param slaveJobCount
   *          the number of slaveJobs that will be executed
   * @param slaveJobTries
   *          used for the retryablejob
   * @param jUnitXmlPath
   *          the path where the jUnit XML is going to be created
   * @param jUnitLogLevel
   * @param enableJunit
   *          to know if is necessary to generate the jUnit XML
   */
  TptPluginMasterJobExecutor(AbstractBuild< ? , ? > build, Launcher launcher,
                             BuildListener listener, FilePath[] exePaths,
                             List<JenkinsConfiguration> executionConfigs, int tptPort,
                             String tptBindingName, String slaveJobName, long tptStartupWaitTime,
                             int slaveJobCount, int slaveJobTries, String jUnitXmlPath,
                             LogLevel jUnitLogLevel, boolean enableJunit) {
    this.logger = new TptLogger(listener.getLogger());
    this.launcher = launcher;
    this.build = build;
    this.listener = listener;
    this.exePaths = exePaths;
    this.executionConfigs = executionConfigs;
    this.tptPort = tptPort;
    this.tptBindingName = tptBindingName;
    this.slaveJobName = slaveJobName;
    this.tptStartupWaitTime = tptStartupWaitTime;
    this.slaveJobCount = slaveJobCount;
    this.slaveJobTries = slaveJobTries;
    this.jUnitLogLevel = jUnitLogLevel;
    this.jUnitXmlPath = jUnitXmlPath;
    this.enableJunit = enableJunit;
  }

  /**
   * It binds to the Tpt Api , check if the given Execution Configuration exists. Prepares the test-
   * and data-directories. Then it split all the test cases (the currently set test set or the
   * explicitly given test set) in smaller chunks of tests cases, then creates the workloads for the
   * slaves. After that by calling the retryable job , it schedules the builds for the slaves. Then
   * it joins all the threads from the slaves, collect the results and regenerate a new Overview
   * report with the data from the slaves. It publishes the Junit XML if configured and resets the
   * temporary settings to the original values.
   * 
   * @return true if the execution from slaves and master were successful.
   */
  boolean execute() {
    TptApi api = null;
    boolean success = true;
    try {
      try {
        api = Utils.getTptApi(build, launcher, logger, exePaths, tptPort, tptBindingName,
            tptStartupWaitTime);
      } catch (InterruptedException e) {
        logger.error(e.getMessage());
        return false;
      }
      if (api == null) {
        return false;
      }
      for (JenkinsConfiguration ec : executionConfigs) {
        success &= executeOneConfig(ec, api);
      }
    } catch (InterruptedException e) {
      logger.interrupt(e.getMessage());
      return false;
    } finally {
      logger.info("Close open TPT project on master and slaves.");
      if (!CleanUpTask.cleanUp(build)) {
        logger.error("Could not close all open TPT files. "
            + "There is no guarantee next run will be be done with correct file version.");
        return false;
      }
    }
    return success;
  }

  private boolean executeOneConfig(JenkinsConfiguration ec, TptApi api)
      throws InterruptedException {
    if (!ec.isEnableTest()) {
      return true;
    }
    Collection<String> testCases = null;
    ExecutionConfiguration executionConfig;
    String testdataDir = Utils.getGeneratedTestDataDir(ec);
    FilePath testDataPath = new FilePath(build.getWorkspace(), testdataDir);
    String reportDir = Utils.getGeneratedReportDir(ec);
    FilePath reportPath = new FilePath(build.getWorkspace(), reportDir);
    try {
      logger.info("Create and/or clean test data directory \"" + testDataPath.getRemote() + "\"");
      testDataPath.mkdirs();
      Utils.deleteFiles(testDataPath);
      logger.info("Create and/or clean report directory \"" + reportPath.getRemote() + "\"");
      reportPath.mkdirs();
      reportPath.deleteContents();
      if (!StringUtils.isBlank(jUnitXmlPath)) {
        FilePath path = new FilePath(build.getWorkspace(), jUnitXmlPath);
        logger.info("Create and/or clear JUnit XML directory " + path.getRemote());
        path.mkdirs();
        path.deleteContents();
      }
    } catch (IOException e) {
      logger.error("Could not create or clear directories on master: " + e.getMessage());
      return false;
    }
    try {
      OpenResult openProject = api.openProject(new File(ec.getTptFile()));
      if (openProject.getProject() == null) {
        logger.error("Could not open project:\n" + Utils.toString(openProject.getLogs(), "\n"));
        return false;
      }
      new CleanUpTask(openProject.getProject(), build);

      executionConfig = getExecutionConfigByName(openProject.getProject(), ec.getConfiguration());
      if (executionConfig == null) {
        logger.error("Could not find config");
        return false;
      }
      if (!ec.getTestSet().equals("")) {
        boolean testSetFound = false;
        for (TestSet definedTestset : openProject.getProject().getTestSets().getItems()) {
          if (definedTestset.getName().equals(ec.getTestSet())) {
            testSetFound = true;
            testCases = new ArrayList<>();
            for (Scenario testcase : definedTestset.getTestCases().getItems()) {
              testCases.add(testcase.getName());
            }
            break;
          }
        }
        if (!testSetFound) {
          logger.error("Could not find test set \"" + ec.getTestSet() + "\"");
          return false;
        }
      } else {
        testCases = getTestCaseNames(executionConfig);
      }
    } catch (RemoteException e) {
      logger.error(e.getMessage());
      return false;
    } catch (ApiException e) {
      logger.error(e.getMessage());
      return false;
    }
    // check if found test cases to execute
    if (testCases == null || testCases.isEmpty()) {
      logger.error("No test cases are found to execute.");
      return false;
    }
    ArrayList<RetryableJob> retryableJobs = new ArrayList<>();
    // create test sets for slave jobs
    int slaveJobSize;
    int remainer;
    if (slaveJobCount > 1) {
      slaveJobSize = testCases.size() / slaveJobCount;
      remainer = testCases.size() % slaveJobCount;
    } else {
      slaveJobSize = testCases.size();
      remainer = 0;
    }
    ArrayList<List<String>> subTestSets = getSubTestSets(testCases, slaveJobSize, remainer);
    // start one job for every test set
    Job slaveJob = null;
    Jenkins jenkinsInstance = Jenkins.getInstance();
    if (jenkinsInstance == null) {
      logger.error("No jenkins instance found");
      return false;
    }
    for (Job j : jenkinsInstance.getAllItems(Job.class)) {
      if (j.getName().equals(slaveJobName)) {
        slaveJob = j;
      }
    }
    if (slaveJob == null) {
      logger.error("Slave Job \"" + slaveJobName + "\" not found");
      return false;

    }
    for (List<String> subTestSet : subTestSets) {
      logger.info("Create job for \"" + subTestSet + "\"");

      // creates the workloads for the slaves, with the smaller chunks of testsets
      WorkLoad workloadToAdd = new WorkLoad(ec.getTptFile(), ec.getConfiguration(), testdataDir,
          reportDir, ec.getTestSet(), subTestSet, build.getWorkspace(), build);
      // it adds the workloads to an static HashMap.
      WorkLoad.putWorkLoad(slaveJobName, workloadToAdd);
      // Creates a retryable job , there are the builds scheduled. So the logic is : We put a
      // workload in a static HashMap and then we trigger a build for a slave. In that way we are
      // distributing the builds on the slaves.
      RetryableJob retryableJob = new RetryableJob(slaveJobTries, logger, slaveJob);
      retryableJob.perform(build, listener);
      retryableJobs.add(retryableJob);
    }
    logger.info("Waiting for completion of child jobs.");
    for (RetryableJob retryableJob : retryableJobs) {
      try {
        retryableJob.join();
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        logger.info("Stopping slave jobs.");
        for (RetryableJob retryableJobToCancle : retryableJobs) {
          retryableJobToCancle.cancel();
        }
        return false;
      }
    }
    // now combine the reports of the different test executions
    try {
      File oldTestDataFile = executionConfig.getDataDir();
      File oldReportDir = executionConfig.getReportDir();
      executionConfig.setDataDir(new File(testDataPath.getRemote()));
      executionConfig.setReportDir(new File(reportPath.getRemote()));
      // set explicit defined test set for all items
      ArrayList<TestSet> oldTestSets = new ArrayList<>();
      if (!ec.getTestSet().equals("")) {
        OpenResult openProject = api.openProject(new File(ec.getTptFile()));
        RemoteCollection<TestSet> allTestSets = openProject.getProject().getTestSets();
        TestSet newTestSet = null;
        for (TestSet t : allTestSets.getItems()) {
          if (t.getName().equals(ec.getTestSet())) {
            newTestSet = t;
          }
        }
        if (newTestSet == null) {
          logger.error("Test set \"" + ec.getTestSet() + "\" not found.");
          return false;
        }
        for (ExecutionConfigurationItem item : executionConfig.getItems()) {
          oldTestSets.add(item.getTestSet());
          item.setTestSet(newTestSet);
        }
      }
      ExecutionStatus execStatus = api.reGenerateOverviewReport(executionConfig);
      while (execStatus.isRunning() || execStatus.isPending()) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          execStatus.cancel();
          return false;
        }
      }
      executionConfig.setDataDir(oldTestDataFile);
      executionConfig.setReportDir(oldReportDir);
      // reset test sets to old values
      if (!ec.getTestSet().equals("")) {
        for (ExecutionConfigurationItem item : executionConfig.getItems()) {
          item.setTestSet(oldTestSets.remove(0));
        }
      }
      int foundTestData = 0;
      if (enableJunit) {
        foundTestData = Utils.publishResults(build.getWorkspace(), ec, testDataPath, jUnitXmlPath,
            jUnitLogLevel, logger);
      } else {
        try {
          foundTestData = Publish.getTestcases(testDataPath, logger).size();
        } catch (InterruptedException e) {
          logger.interrupt("Interrupted while parsing the \"test_summary.xml\" of the testcases.");
          return false;
        }
      }
      if (foundTestData != testCases.size()) {
        logger.error("Found only " + foundTestData + " of " + testCases.size() + " test results.");
        return false;
      }
    } catch (RemoteException e) {
      logger.error(e.getMessage());
      return false;
    } catch (ApiException e) {
      logger.error(e.getMessage());
      return false;
    } catch (IOException e) {
      logger.error("Could not publish result: " + e.getMessage());
      return false;
    }
    return true;
  }

  private ArrayList<List<String>> getSubTestSets(Collection<String> testCases, int slaveJobSize,
                                                 int remainer) {
    ArrayList<List<String>> testSets = new ArrayList<>();
    ArrayList<String> currentTestSet = new ArrayList<>();
    Iterator<String> iterator = testCases.iterator();
    while (iterator.hasNext()) {
      currentTestSet.add(iterator.next());
      if (currentTestSet.size() == slaveJobSize) {
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

  /**
   * Looks in the Tpt project if there is such Execution Configuration
   * 
   * @param project
   *          , TptProject
   * @param exeConfigName
   *          , the name of the Execution Configuration
   * 
   * @return the ExecutionConfiguration if found, null otherwise
   */
  private ExecutionConfiguration getExecutionConfigByName(Project project, String exeConfigName)
      throws RemoteException, ApiException {
    Collection<ExecutionConfiguration> execConfigs =
        project.getExecutionConfigurations().getItems();
    for (ExecutionConfiguration elem : execConfigs) {
      if (elem.getName().equals(exeConfigName)) {
        return elem;
      }
    }
    return null;
  }

  /**
   * Get the testcases from an Execution configuration.
   * 
   * @param execution
   *          configuration , we will iterate over its items in order to get the testcases
   * 
   * @return a list with the test cases
   */
  private Collection<String> getTestCaseNames(ExecutionConfiguration config)
      throws RemoteException, ApiException {
    HashSet<String> result = new HashSet<String>();
    for (ExecutionConfigurationItem item : config.getItems()) {
      for (Scenario testcase : item.getTestSet().getTestCases().getItems()) {
        result.add(testcase.getName());
      }
    }
    return result;
  }

}
