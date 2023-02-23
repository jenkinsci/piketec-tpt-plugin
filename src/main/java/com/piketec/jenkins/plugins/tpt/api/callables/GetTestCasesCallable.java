package com.piketec.jenkins.plugins.tpt.api.callables;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jenkinsci.remoting.RoleChecker;

import com.piketec.jenkins.plugins.tpt.TptApiHelper;
import com.piketec.jenkins.plugins.tpt.TptLogger;
import com.piketec.jenkins.plugins.tpt.TptVersion;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.ExecutionConfiguration;
import com.piketec.tpt.api.ExecutionConfigurationItem;
import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.TestSet;
import com.piketec.tpt.api.TptApi;

import hudson.FilePath;
import hudson.model.TaskListener;

/**
 * This Callable returns a list of all testcase for the given parameters. The code is run on a
 * Jenkins Agent and uses the TPT API.
 */
public class GetTestCasesCallable extends TptApiCallable<GetTestCasesCallableResult> {

  private static final long serialVersionUID = 1L;

  private FilePath tptFilePath;

  private String executionConfigName;

  private String testSet;

  public GetTestCasesCallable(TaskListener listener, int tptPort, String tptBindingName,
                              FilePath[] exePaths, List<String> arguments, long startUpWaitTime,
                              FilePath tptFilePath, String executionConfigName, String testSet) {
    super(listener, tptPort, tptBindingName, exePaths, arguments, startUpWaitTime);
    this.tptFilePath = tptFilePath;
    this.executionConfigName = executionConfigName;
    this.testSet = testSet;
  }

  @Override
  public void checkRoles(RoleChecker arg0) throws SecurityException {
  }

  @Override
  public GetTestCasesCallableResult call() throws InterruptedException {
    TptLogger logger = getLogger();
    try {
      TptApi api = getApi();
      if (api == null) {
        logger.error("Could not establish connection to the TPT API.");
        return null;
      }
      TptVersion tptVersion = TptVersion.getVersion(api);
      Set<String> testCases = null;
      int totalTestCaseCount = 0;

      // Open the TPT Project via the TPT-API
      Project project = getOpenProject(logger, api, tptFilePath);
      if (project == null) {
        return null;
      }
      // Get the execution cofig that should be executed
      ExecutionConfiguration executionConfig =
          getExecutionConfigByName(project, executionConfigName);
      if (executionConfig == null) {
        logger.error("Unable to find execution configuration \"" + executionConfigName + "\"");
        return null;
      }
      boolean testCaseConditionPresent = false;
      // Get all testcases that should be executed
      if (StringUtils.isNotEmpty(testSet)) { // explicit configured test set via Jenkins
        boolean testSetFound = false;
        for (TestSet definedTestset : project.getTestSets().getItems()) {
          if (definedTestset.getName().equals(testSet)) {
            testSetFound = true;
            testCases = new HashSet<>();
            for (Scenario testcase : TptApiHelper.getTestCasesFromTestSet(tptVersion,
                definedTestset)) {
              testCases.add(testcase.getName());
            }
            for (ExecutionConfigurationItem item : executionConfig.getItems()) {
              if (item.isActive()) {
                totalTestCaseCount += testCases.size();
              }
            }
            testCaseConditionPresent =
                checkForTestCaseConditionIfPossible(definedTestset, tptVersion);
            break;
          }
        }
        if (!testSetFound) {
          logger.error("Unable to find test set \"" + testSet + "\"");
          return null;
        }
      } else { // use test sets configured in TPT file
        testCases = new HashSet<>();
        for (ExecutionConfigurationItem item : executionConfig.getItems()) {
          if (item.isActive()) {
            TestSet testSet = item.getTestSet();
            if (testSet == null) {
              logger.error(
                  "Unable to get items for dynamic test set of currently in TPT selected test cases."
                      + " Please configure a test set.");
              return null;
            }
            for (Scenario testcase : TptApiHelper.getTestCasesFromTestSet(tptVersion, testSet)) {
              testCases.add(testcase.getName());
              totalTestCaseCount++;
            }
            testCaseConditionPresent |= checkForTestCaseConditionIfPossible(testSet, tptVersion);
          }
        }
      }
      if (testCases == null || testCases.isEmpty()) {
        logger.error(
            "No test cases are found to execute. It is possible that \"selected Test Cases \" is"
                + " configured as test set. If so please change it to another existing test set");
        return null;
      }
      return new GetTestCasesCallableResult(testCases, totalTestCaseCount, testCaseConditionPresent,
          tptVersion);
    } catch (RemoteException e) {
      logger.error("RemoteException: " + e.getMessage());
      return null;
    } catch (ApiException e) {
      logger.error("ApiException: " + e.getMessage());
      return null;
    }
  }

  private boolean checkForTestCaseConditionIfPossible(TestSet testset, TptVersion tptVersion)
      throws RemoteException {
    if (tptVersion.supportsTestCaseConditionAccess()) {
      return testset.isConditionEnabled();
    } else {
      return false;
    }
  }

}
