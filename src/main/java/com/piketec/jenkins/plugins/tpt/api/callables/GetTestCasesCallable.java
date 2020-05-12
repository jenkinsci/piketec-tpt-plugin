package com.piketec.jenkins.plugins.tpt.api.callables;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.jenkinsci.remoting.RoleChecker;

import com.piketec.jenkins.plugins.tpt.TptLogger;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.ExecutionConfiguration;
import com.piketec.tpt.api.OpenResult;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.TestSet;
import com.piketec.tpt.api.TptApi;

import hudson.FilePath;
import hudson.model.TaskListener;

/**
 * This Callable returns a list of all testcase for the given parameters. The code is run on a
 * Jenkins Agent and uses the TPT API.
 */
public class GetTestCasesCallable extends TptApiCallable<Collection<String>> {

  private static final long serialVersionUID = 1L;

  private FilePath tptFilePath;

  private String executionConfigName;

  private String testSet;

  public GetTestCasesCallable(TaskListener listener, String hostName, int tptPort,
                              String tptBindingName, FilePath[] exePaths, long startUpWaitTime,
                              FilePath tptFilePath, String executionConfigName, String testSet) {
    super(listener, hostName, tptPort, tptBindingName, exePaths, startUpWaitTime);
    this.tptFilePath = tptFilePath;
    this.executionConfigName = executionConfigName;
    this.testSet = testSet;
  }

  @Override
  public void checkRoles(RoleChecker arg0) throws SecurityException {
  }

  @Override
  public Collection<String> call() throws UnknownHostException {
    TptLogger logger = getLogger();
    TptApi api = getApi();
    if (api == null) {
      logger.error("Could not establish connection to the TPT API.");
      return null;
    }
    Collection<String> testCases = null;

    // Open the TPT Project via the TPT-API
    OpenResult openProject = getOpenProject(logger, api, tptFilePath);
    try {
      // Get the execution cofig that should be executed
      ExecutionConfiguration executionConfig =
          getExecutionConfigByName(openProject.getProject(), executionConfigName);
      if (executionConfig == null) {
        logger.error("Could not find config");
        return null;
      }

      // Get all testcases that should be executed
      if (StringUtils.isNotEmpty(testSet)) {
        boolean testSetFound = false;
        for (TestSet definedTestset : openProject.getProject().getTestSets().getItems()) {
          if (definedTestset.getName().equals(testSet)) {
            testSetFound = true;
            testCases = new ArrayList<>();
            for (Scenario testcase : definedTestset.getTestCases().getItems()) {
              testCases.add(testcase.getName());
            }
            break;
          }
        }
        if (!testSetFound) {
          logger.error("Could not find test set \"" + testSet + "\"");
          return null;
        }
      } else {
        testCases = getTestCaseNames(executionConfig);
      }
    } catch (RemoteException e) {
      logger.error("RemoteException: " + e.getMessage());
      return null;
    } catch (ApiException e) {
      logger.error("ApiException: " + e.getMessage());
      return null;
    } catch (IOException e) {
      logger.error("IOException " + e.getMessage());
      return null;
    }
    if (testCases == null || testCases.isEmpty()) {
      logger.error(
          "No test cases are found to execute. It is possible that \"selected Test Cases \" is configured as test set. If so please change it to another existing test set");
      return null;
    }
    return testCases;
  }

}
