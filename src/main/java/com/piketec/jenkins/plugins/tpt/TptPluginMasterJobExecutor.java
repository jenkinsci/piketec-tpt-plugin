/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 PikeTec GmbH
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
import java.util.HashMap;
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
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.TestSet;
import com.piketec.tpt.api.TptApi;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Computer;
import hudson.model.Job;
import jenkins.model.Jenkins;
import jenkins.model.Jenkins.MasterComputer;

class TptPluginMasterJobExecutor {

  private TptLogger logger;

  private Launcher launcher;

  private AbstractBuild< ? , ? > build;

  private BuildListener listener;

  private FilePath[] exePaths;

  private String jUnitXmlPath;

  private LogLevel jUnitLogLevel;

  private List<JenkinsConfiguration> executionConfigs;

  private int tptPort;

  private String tptBindingName;

  private String slaveJobName;

  private long tptStartupWaitTime;

  private int slaveJobCount;

  private int slaveJobTries;

  private static HashMap<String, List<WorkLoad>> workload = new HashMap<>();

  public static void putWorkLoad(String jobName, WorkLoad workloadToAdd) {
    synchronized (workload) {
      List<WorkLoad> set = workload.get(jobName);
      if (set == null) {
        set = new ArrayList<>();
        if (!set.contains(workloadToAdd)) {
          workload.put(jobName, set);
        }
      }
      set.add(workloadToAdd);
    }
  }

  public static WorkLoad getAndRemoveWorkload(String jobName) {
    synchronized (workload) {
      List<WorkLoad> set = workload.get(jobName);
      if (set == null || set.isEmpty()) {
        return null;
      }
      return set.remove(0);
    }
  }

  TptPluginMasterJobExecutor(AbstractBuild< ? , ? > build, Launcher launcher,
                             BuildListener listener, FilePath[] exePaths, String jUnitXmlPath,
                             LogLevel jUnitLogLevel, List<JenkinsConfiguration> executionConfigs,
                             int tptPort, String tptBindingName, String slaveJobName,
                             long tptStartupWaitTime, int slaveJobCount, int slaveJobTries) {
    logger = new TptLogger(listener.getLogger());
    this.launcher = launcher;
    this.build = build;
    this.listener = listener;
    this.exePaths = exePaths;
    this.jUnitXmlPath = jUnitXmlPath;
    this.jUnitLogLevel = jUnitLogLevel;
    this.executionConfigs = executionConfigs;
    this.tptPort = tptPort;
    this.tptBindingName = tptBindingName;
    this.slaveJobName = slaveJobName;
    this.tptStartupWaitTime = tptStartupWaitTime;
    this.slaveJobCount = slaveJobCount;
    this.slaveJobTries = slaveJobTries;

  }

  boolean execute() {
    if (!(Computer.currentComputer() instanceof MasterComputer)) {
      logger.error("TPT master has to run on master node");
      return false;
    }
    TptApi api = null;
    String executionId = Double.toString(Math.random());
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
      if (!ec.isEnableTest()) {
        continue;
      }
      Collection<String> testCases = null;
      ExecutionConfiguration executionConfig;
      String testdataDir = Utils.getGeneratedTestDataDir(ec);
      FilePath testDataPath = new FilePath(build.getWorkspace(), testdataDir);
      String reportDir = Utils.getGeneratedReportDir(ec);
      FilePath reportPath = new FilePath(build.getWorkspace(), reportDir);
      try {
        logger.info("Create and/or clear test data directory " + testDataPath.getRemote());
        Utils.deleteFiles(testDataPath);
        logger.info("Create and/or clear report directory " + reportPath.getRemote());
        reportPath.mkdirs();
        reportPath.deleteContents();
        if (!StringUtils.isBlank(jUnitXmlPath)) {
          FilePath path = new FilePath(build.getWorkspace(), jUnitXmlPath);
          logger.info("Create and/or clear JUnit XML directory " + path.getRemote());
          path.mkdirs();
          path.deleteContents();
        }
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        return false;
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
        new CleanUpTask(openProject.getProject(), executionId);

        executionConfig = getExecutionConfigByName(openProject.getProject(), ec.getConfiguration());
        if (executionConfig == null) {
          logger.error("Could not find config");
          return false;
        }
        if (!ec.getTestSet().equals("")) {
          for (TestSet definedTestset : openProject.getProject().getTestSets().getItems()) {
            if (definedTestset.getName().equals(ec.getTestSet())) {
              testCases = new ArrayList<>();
              for (Scenario testcase : definedTestset.getTestCases().getItems()) {
                testCases.add(testcase.getName());
              }
              break;
            }
          }
        } else {
          testCases = getTestCaseNames(executionConfig, build, ec);
        }
      } catch (RemoteException e) {
        logger.error(e.getMessage());
        return false;
      } catch (ApiException e) {
        logger.error(e.getMessage());
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
      ArrayList<String> testSets = new ArrayList<>();
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
          testSets.add(Utils.escapeTestCaseNames(currentTestSet));
          currentTestSet.clear();
        }
      }
      if (!currentTestSet.isEmpty()) {
        testSets.add(Utils.escapeTestCaseNames(currentTestSet));
      }
      // start one job for every test set

      Job slaveJob = null;

      for (Job j : Jenkins.getInstance().getAllItems(Job.class)) {
        if (j.getName().equals(slaveJobName)) {
          slaveJob = j;
        }
      }
      if (slaveJob == null) {
        logger.error("Slave Job not found");
        return false;
      }

      for (String testCase : testSets) {
        logger.info("Create job for \"" + testCase + "\"");

        WorkLoad workloadToAdd = new WorkLoad(ec.getTptFile(), ec.getConfiguration(), testdataDir,
            reportDir, ec.getTestSet(), testCase, build.getWorkspace());
        putWorkLoad(slaveJobName, workloadToAdd);

        RetryableJob retryableJob = new RetryableJob(slaveJobTries, logger, slaveJob);
        retryableJob.perform(build, launcher, listener);
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
        int foundTestData =
            Utils.publishResults(build.getWorkspace(), ec, jUnitXmlPath, jUnitLogLevel, logger);
        if (foundTestData != testCases.size()) {
          logger
              .error("Found only " + foundTestData + " of " + testCases.size() + " test results.");
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
      } finally {
        logger.info("Close open TPT project on master and slaves.");
        if (!CleanUpTask.cleanUp(executionId)) {
          logger.error("Could not close all open TPT files. "
              + "There is no guarantee next run will be be done with correct file version.");
          return false;
        }
      }
    }
    return true;
  }

  private ExecutionConfiguration getExecutionConfigByName(Project project, String name)
      throws RemoteException, ApiException {
    Collection<ExecutionConfiguration> execConfigs =
        project.getExecutionConfigurations().getItems();
    for (ExecutionConfiguration elem : execConfigs) {
      if (elem.getName().equals(name)) {
        return elem;
      }
    }
    return null;
  }

  private Collection<String> getTestCaseNames(ExecutionConfiguration config,
                                              AbstractBuild< ? , ? > build, JenkinsConfiguration ec)
      throws RemoteException, ApiException {
    HashSet<String> result = new HashSet<String>();
    for (ExecutionConfigurationItem item : config.getItems()) {
      for (Scenario testcase : item.getTestSet().getTestCases().getItems()) {
        result.add(testcase.getName());
      }
    }
    return result;
  }

  private String exePathsAsSingleString() {
    StringBuilder sb = new StringBuilder();
    for (FilePath path : exePaths) {
      if (sb.length() > 0) {
        sb.append(',');
      }
      sb.append(path.getRemote());
    }
    return sb.toString();
  }

}
