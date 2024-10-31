package com.piketec.jenkins.plugins.tpt.api.callables;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.piketec.jenkins.plugins.tpt.TptLogger;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.ExecutionConfiguration;
import com.piketec.tpt.api.ExecutionConfigurationItem;
import com.piketec.tpt.api.ExecutionStatus;
import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.RemoteCollection;
import com.piketec.tpt.api.TestSet;
import com.piketec.tpt.api.TptApi;

import hudson.FilePath;
import hudson.model.TaskListener;

/**
 * This class creates the tpt overview report. The code is executed on the Jenkins Agent and uses
 * the TPT API. NOTE: This is not the report that is shown in Jenkins, but only the local TPT
 * Report. To create a report in Jenkins, use the Report Plugin.
 */
public class RunOverviewReportCallable extends TptApiCallable<Boolean> {

  private static final long serialVersionUID = 1L;

  private FilePath tptFilePath;

  private String executionConfigName;

  private String testSet;

  private FilePath reportPath;

  private FilePath testDataPath;

  public RunOverviewReportCallable(TaskListener listener, int tptPort, String tptBindingName,
                                   FilePath[] exePaths, List<String> arguments,
                                   long startUpWaitTime, FilePath tptFilePath,
                                   String executionConfigName, String testSet, FilePath reportPath,
                                   FilePath testdataPath) {
    super(listener, tptPort, tptBindingName, exePaths, arguments, startUpWaitTime);
    this.tptFilePath = tptFilePath;
    this.executionConfigName = executionConfigName;
    this.testSet = testSet;
    this.reportPath = reportPath;
    this.testDataPath = testdataPath;
  }

  @Override
  public Boolean call() throws InterruptedException {
    TptLogger logger = getLogger();
    TptApi api = getApi();
    if (api == null) {
      logger.error("Could not establish connection to the TPT API.");
      return false;
    }
    Project project = getOpenProject(logger, api, tptFilePath);
    if (project == null) {
      return false;
    }
    try {
      // Get the execution cofig that should be executed
      ExecutionConfiguration executionConfig =
          getExecutionConfigByName(project, executionConfigName);
      if (executionConfig == null) {
        logger.error("Could not find execution configuration " + executionConfigName);
        return false;
      }

      String oldTestDataFile = executionConfig.getDataDirPath();
      String oldReportDir = executionConfig.getReportDirPath();
      executionConfig.setDataDirPath(testDataPath.getRemote());
      executionConfig.setReportDirPath(reportPath.getRemote());

      // set explicit defined test set for all items
      // This is done, because the worker jobs don't execute the testsets that were set originally
      // in the file but the one that is defined in Jenkins.
      ArrayList<TestSet> oldTestSets = new ArrayList<>();
      if (StringUtils.isNotEmpty(testSet)) {
        RemoteCollection<TestSet> allTestSets = project.getTestSets();
        TestSet newTestSet = null;
        for (TestSet t : allTestSets.getItems()) {
          if (t.getName().equals(testSet)) {
            newTestSet = t;
          }
        }
        if (newTestSet == null) {
          logger.error("Test set \"" + testSet + "\" not found.");
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
          Thread.currentThread().interrupt();
          execStatus.cancel();
          return false;
        }
      }
      executionConfig.setDataDirPath(oldTestDataFile);
      executionConfig.setReportDirPath(oldReportDir);

      // reset test sets to old values to maintain the file as it was before
      if (StringUtils.isNotEmpty(testSet)) {
        for (ExecutionConfigurationItem item : executionConfig.getItems()) {
          TestSet oldTestSet = oldTestSets.remove(0);
          // This happens because of a bug in the TPT API.
          if (oldTestSet != null) {
            item.setTestSet(oldTestSet);
          }
        }
      }
      return true;
    } catch (RemoteException e) {
      logger.error("RemoteException: " + e.getMessage());
      return false;
    } catch (ApiException e) {
      logger.error("ApiException: " + e.getMessage());
      return false;
    }
  }

}
