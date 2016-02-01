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

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.Job;
import hudson.model.Run;
import hudson.plugins.parameterizedtrigger.AbstractBuildParameters;
import hudson.plugins.parameterizedtrigger.CurrentBuildParameters;
import hudson.plugins.parameterizedtrigger.PredefinedBuildParameters;
import hudson.plugins.parameterizedtrigger.ResultCondition;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import jenkins.model.Jenkins.MasterComputer;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ListMultimap;
import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.ExecutionConfiguration;
import com.piketec.tpt.api.ExecutionConfigurationItem;
import com.piketec.tpt.api.ExecutionStatus;
import com.piketec.tpt.api.OpenResult;
import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.TptApi;

public class TptPluginMasterJobExecutor {

  private TptLogger logger;

  private Launcher launcher;

  private AbstractBuild< ? , ? > build;

  private BuildListener listener;

  private FilePath[] exePaths;

  private String jUnitXmlPath;

  private List<JenkinsConfiguration> executionConfigs;

  private int tptPort;

  private String tptBindingName;

  private String slaveJobName;

  private String testcaseVarName;

  private String execCfgVarName;

  private String tptFileVarName;

  private String exePathsVarName;

  private String testDataDirVarName;

  private String reportDirVarName;

  private long tptStartupWaitTime;

  public TptPluginMasterJobExecutor(AbstractBuild< ? , ? > build, Launcher launcher,
                                    BuildListener listener, FilePath[] exePaths,
                                    String jUnitXmlPath,
                                    List<JenkinsConfiguration> executionConfigs, int tptPort,
                                    String tptBindingName, String slaveJobName,
                                    String testcaseVarName, String execCfgVarName,
                                    String tptFileVarName, String exePathsVarName,
                                    String testDataDirVarName, String reportDirVarName,
                                    long tptStartupWaitTime) {
    logger = new TptLogger(listener.getLogger());
    this.launcher = launcher;
    this.build = build;
    this.listener = listener;
    this.exePaths = exePaths;
    this.jUnitXmlPath = jUnitXmlPath;
    this.executionConfigs = executionConfigs;
    this.tptPort = tptPort;
    this.tptBindingName = tptBindingName;
    this.slaveJobName = slaveJobName;
    this.testcaseVarName = testcaseVarName;
    this.execCfgVarName = execCfgVarName;
    this.tptFileVarName = tptFileVarName;
    this.exePathsVarName = exePathsVarName;
    this.testDataDirVarName = testDataDirVarName;
    this.reportDirVarName = reportDirVarName;
    this.tptStartupWaitTime = tptStartupWaitTime;
  }

  public boolean execute() {
    if (!(Computer.currentComputer() instanceof MasterComputer)) {
      logger.error("TPT master has to run on master node");
      return false;
    }
    TptApi api = null;
    String executionId = Double.toString(Math.random());
    try {
      api =
          Utils.getTptApi(build, launcher, logger, exePaths, tptPort, tptBindingName,
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
      FilePath testDataPath = new FilePath(build.getWorkspace(), ec.getTestdataDir().getPath());
      FilePath reportPath = new FilePath(build.getWorkspace(), ec.getReportDir().getPath());
      try {
        logger.info("Create and/or clear test data directory " + testDataPath.getRemote());
        testDataPath.mkdirs();
        testDataPath.deleteContents();
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
        OpenResult openProject = api.openProject(ec.getTptFile());
        if (openProject.getProject() == null) {
          logger.error("Could not open project:\n" + Utils.toString(openProject.getLogs(), "\n"));
          return false;
        }
        executionConfig = getExecutionConfigByName(openProject.getProject(), ec.getConfiguration());
        if (executionConfig == null) {
          logger.error("Could not find config");
          return false;
        }
        testCases = getTestCaseNames(executionConfig, build, ec);
      } catch (RemoteException e) {
        logger.error(e.getMessage());
        return false;
      } catch (ApiException e) {
        logger.error(e.getMessage());
        return false;
      }
      CurrentBuildParameters currentBuildParameters = new CurrentBuildParameters();
      ArrayList<Future<Run>> futures = new ArrayList<Future<Run>>();
      // start a slave job for every test case and publish the result
      for (String testCase : testCases) {
        logger.info("Create job for \"" + testCase + "\"");
        PredefinedBuildParameters predefinedBuildParameters =
            new PredefinedBuildParameters(this.testcaseVarName
                + "="
                + testCase
                + "\n" //
                + this.execCfgVarName
                + "="
                + ec.getConfiguration()
                + "\n" //
                + this.tptFileVarName
                + "="
                + ec.getTptFile().getPath().replace("\\", "\\\\")
                + "\n" //
                + this.exePathsVarName
                + "="
                + exePathsAsSingleString().replace("\\", "\\\\")
                + "\n" //
                + this.testDataDirVarName + "="
                + ec.getTestdataDir().getPath().replace("\\", "\\\\")
                + "\n" //
                + this.reportDirVarName + "=" + ec.getReportDir().getPath().replace("\\", "\\\\")
                + "\n" //
                + Utils.TPT_EXECUTION_ID_VAR_NAME + "=" + executionId//
            );

        ArrayList<AbstractBuildParameters> configs = new ArrayList<AbstractBuildParameters>();
        configs.add(currentBuildParameters);
        configs.add(predefinedBuildParameters);

        hudson.plugins.parameterizedtrigger.BuildTriggerConfig cfg =
            new hudson.plugins.parameterizedtrigger.BuildTriggerConfig(slaveJobName,
                ResultCondition.ALWAYS, false, null, configs);
        try {
          ListMultimap<Job, Future<Run>> perform3 = cfg.perform3(build, launcher, listener);
          futures.addAll(perform3.values());
        } catch (InterruptedException e) {
          logger.interrupt(e.getMessage());
          for (Future future : futures) {
            future.cancel(true);
          }
          return false;
        } catch (IOException e) {
          logger.error(e.getMessage());
        }
      }
      logger.info("Waiting for completion of child jobs");
      for (Future<Run> future : futures) {
        try {
          future.get();
        } catch (InterruptedException e) {
          logger.interrupt(e.getMessage());
          logger.info("Stopping slave jobs.");
          for (Future<Run> future2 : futures) {
            future2.cancel(true);
          }
          return false;
        } catch (ExecutionException e) {
          // NOP, job has been cancled but we do not need the result
        }
      }
      // now combine the reports of the different test executions
      try {
        File oldTestDataFile = executionConfig.getDataDir();
        File oldReportDir = executionConfig.getReportDir();

        if (ec.getTestdataDir() != null && !ec.getTestdataDir().getPath().isEmpty()) {
          executionConfig.setDataDir(new File(testDataPath.getRemote()));
        }
        if (ec.getReportDir() != null && !ec.getReportDir().getPath().isEmpty()) {
          executionConfig.setReportDir(new File(reportPath.getRemote()));
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
        int foundTestData = Utils.publishResults(build.getWorkspace(), ec, jUnitXmlPath, logger);
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
