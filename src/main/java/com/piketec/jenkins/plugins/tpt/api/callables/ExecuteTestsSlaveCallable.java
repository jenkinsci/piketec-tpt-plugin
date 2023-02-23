package com.piketec.jenkins.plugins.tpt.api.callables;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
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
import com.piketec.tpt.api.ExecutionStatus;
import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.ScenarioGroup;
import com.piketec.tpt.api.ScenarioOrGroup;
import com.piketec.tpt.api.TestSet;
import com.piketec.tpt.api.TptApi;

import hudson.FilePath;
import hudson.model.TaskListener;

/**
 * The Callable executes tpt test cases on a Jenkins Agent via the TPT API.
 */
public class ExecuteTestsSlaveCallable extends TptApiCallable<Boolean> {

  private static final long serialVersionUID = 1L;

  private FilePath tptFilePath;

  private FilePath slaveReportPath;

  private FilePath slaveDataPath;

  private String execCfg;

  private List<String> testSetList;

  private String testSetName;

  /**
   * Create a new callable to execute a subset of tests of a given test set as part of a complete
   * test execution.
   * 
   * @param listener
   *          The task listener
   * @param tptPort
   *          The port for TPT RMI API calls
   * @param tptBindingName
   *          The binding name for TPT RMI API calls
   * @param exePaths
   *          Paths to look for TPT installations
   * @param arguments
   *          startup arguments fo TPT
   * @param startUpWaitTime
   *          Timeto wait for TPT start up
   * @param tptFilePath
   *          The TPT file which tests shall be executeds
   * @param slaveReportPath
   *          Path where the report shall be stored in the workspace
   * @param slaveDataPath
   *          Path where the test data shall be stored in the workspace
   * @param executionConfigName
   *          The execution configuration to execute
   * @param testSet
   *          List of test cases to executes
   * @param testSetName
   *          The test set to execute
   */
  public ExecuteTestsSlaveCallable(TaskListener listener, int tptPort, String tptBindingName,
                                   FilePath[] exePaths, List<String> arguments,
                                   long startUpWaitTime, FilePath tptFilePath,
                                   FilePath slaveReportPath, FilePath slaveDataPath,
                                   String executionConfigName, List<String> testSet,
                                   String testSetName) {
    super(listener, tptPort, tptBindingName, exePaths, arguments, startUpWaitTime);
    this.tptFilePath = tptFilePath;
    this.slaveReportPath = slaveReportPath;
    this.slaveDataPath = slaveDataPath;
    this.execCfg = executionConfigName;
    this.testSetList = testSet;
    this.testSetName = testSetName;
  }

  @Override
  public Boolean call() throws InterruptedException {
    TptLogger logger = getLogger();
    try {
      TptApi api = getApi();
      if (api == null) {
        logger.error("Could not establish connection to the TPT API.");
        return false;
      }
      TptVersion tptVersion = TptVersion.getVersion(api);
      Project project = getOpenProject(logger, api, tptFilePath);
      if (project == null) {
        return false;
      }
      // search execution configuration by name
      Collection<ExecutionConfiguration> execConfigs =
          project.getExecutionConfigurations().getItems();
      ExecutionConfiguration config = null;
      for (ExecutionConfiguration elem : execConfigs) {
        if (elem.getName().equals(execCfg)) {
          config = elem;
          break;
        }
      }
      if (config == null) {
        logger.error("Could not find execution configuration " + execCfg);
        return false;
      }
      // adjust config to execute only the given one test case
      String oldReportDir = config.getReportDirPath();
      String oldTestDataDir = config.getDataDirPath();

      Collection<Scenario> foundScenearios = new HashSet<>();
      find(project.getTopLevelTestlet().getTopLevelScenarioOrGroup().getItems(), testSetList,
          foundScenearios);
      if (foundScenearios.size() != testSetList.size()) {
        logger
            .error("Could only find " + foundScenearios.size() + " of " + testSetList.size() + ".");
        return false;
      }
      logger.info("Setting test data directory to " + slaveDataPath.getRemote());
      config.setDataDirPath(slaveDataPath.getRemote());
      logger.info("Setting report directory to " + slaveReportPath.getRemote());
      config.setReportDirPath(slaveReportPath.getRemote());
      // store information to undo changes
      List<TestSet> oldTestSets = new ArrayList<>();
      List<TestSet> newTestSets = new ArrayList<>();
      List<ExecutionConfigurationItem> deactivated = new ArrayList<>();
      int i = 0;
      if (StringUtils.isEmpty(testSetName)) { // Use test sets defined in file
        for (ExecutionConfigurationItem item : config.getItems()) {
          TestSet existingTestSet = item.getTestSet();
          oldTestSets.add(existingTestSet);
          if (item.isActive()) {
            Collection<Scenario> intersectionSet = intersectByHash(
                TptApiHelper.getTestCasesFromTestSet(tptVersion, existingTestSet), foundScenearios);
            if (intersectionSet.isEmpty()) {
              item.setActive(false);
              deactivated.add(item);
            } else {
              String tmpTestSetName = "JENKINS Exec " + i;
              i++;
              logger.info("Create test set \"" + tmpTestSetName + "\" for execution of \""
                  + remoteScenarioSetToString(intersectionSet) + "\"");
              TestSet testSet = project.createTestSet(tmpTestSetName);
              newTestSets.add(testSet);
              for (Scenario scen : intersectionSet) {
                TptApiHelper.addTestCase(tptVersion, testSet, scen);
              }
              setTestSetCondtionIfPossible(existingTestSet, testSet, tptVersion, logger);
              item.setTestSet(testSet);
            }
          }
        }
      } else { // explicitly defined test set in Jenkins
        String tmpTestSetName = "JENKINS Exec " + testSetName;
        logger.info("Create test set \"" + tmpTestSetName + "\" for execution of \""
            + remoteScenarioSetToString(foundScenearios) + "\" from File " + tptFilePath.getName());
        TestSet testSet = project.createTestSet(tmpTestSetName);
        newTestSets.add(testSet);
        for (Scenario scen : foundScenearios) {
          TptApiHelper.addTestCase(tptVersion, testSet, scen);
        }
        for (ExecutionConfigurationItem item : config.getItems()) {
          oldTestSets.add(item.getTestSet());
          if (item.isActive()) {
            item.setTestSet(testSet);
          }
        }
        boolean testSetFound = false;
        for (TestSet definedTestset : project.getTestSets().getItems()) {
          if (definedTestset.getName().equals(testSetName)) {
            testSetFound = true;
            setTestSetCondtionIfPossible(definedTestset, testSet, tptVersion, logger);
            break;
          }
        }
        if (!testSetFound) {
          logger.warn("Unable to find test set \"" + testSet
              + "\" on agent. Unable to update test set condition.");
        }
      }
      // execute test
      ExecutionStatus execStatus = api.run(config);
      try {
        while (execStatus.isRunning() || execStatus.isPending()) {
          Thread.sleep(1000);
        }
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        execStatus.cancel();
        throw e;
      } finally {
        // undo changes
        logger.info("Set test sets in execution config to old values.");
        for (ExecutionConfigurationItem item : config.getItems()) {
          TestSet oldTestSet = oldTestSets.remove(0);
          // This happens because of a bug in the TPT API.
          if (oldTestSet != null) {
            item.setTestSet(oldTestSet);
          }
        }
        logger.info("reset test data and report directory to \"" + oldTestDataDir + "\" and \""
            + oldReportDir + "\"");
        config.setDataDirPath(oldTestDataDir);
        config.setReportDirPath(oldReportDir);
        for (TestSet testSet : newTestSets) {
          logger.info("delete temporary test set \"" + testSet.getName() + "\"");
          project.getTestSets().delete(testSet);
        }
        logger.info("Reactivate temporary deactivated execution config items.");
        for (ExecutionConfigurationItem item : deactivated) {
          item.setActive(true);
        }
      }
    } catch (RemoteException e) {
      logger.error(e.getLocalizedMessage());
      e.printStackTrace(logger.getLogger());
      return false;
    } catch (ApiException e) {
      logger.error(e.getLocalizedMessage());
      e.printStackTrace(logger.getLogger());
      return false;
    }
    return true;
  }

  @Override
  public void checkRoles(RoleChecker arg0) throws SecurityException {
  }

  /**
   * Finds all the test cases of a given test set
   * 
   * @param sogs
   * @param names
   * @param result
   * @throws RemoteException
   * @throws ApiException
   */

  private void find(Collection<ScenarioOrGroup> sogs, Collection<String> names,
                    Collection<Scenario> result)
      throws RemoteException, ApiException {
    for (ScenarioOrGroup sog : sogs) {
      if (sog instanceof Scenario) {
        if (names.contains(sog.getName())) {
          result.add((Scenario)sog);
        }
      } else {
        find(((ScenarioGroup)sog).getItems(), names, result);
      }
    }
  }

  /**
   * Matches the tests cases from a test set with all the test cases found.
   * 
   * @param scenColl1
   * @param scenCol2
   * @return the intersected test cases
   * @throws RemoteException
   * @throws ApiException
   */
  static Collection<Scenario> intersectByHash(Collection<Scenario> scenColl1,
                                              Collection<Scenario> scenCol2)
      throws RemoteException, ApiException {
    Set<String> scenCol1Names = new HashSet<>();
    ArrayList<Scenario> result = new ArrayList<>();
    for (Scenario scen : scenColl1) {
      scenCol1Names.add(scen.getName());
    }
    for (Scenario scen : scenCol2) {
      if (scenCol1Names.contains(scen.getName())) {
        result.add(scen);
      }
    }
    return result;
  }

  /**
   * Convert the given test cases to a String
   * 
   * @param intersectionSet
   *          to be converted
   * @return the given test cases as a String , separated with a comma
   * @throws RemoteException
   * @throws ApiException
   */
  private String remoteScenarioSetToString(Collection<Scenario> intersectionSet)
      throws RemoteException, ApiException {
    StringBuilder sb = new StringBuilder();
    for (Scenario scen : intersectionSet) {
      if (sb.length() > 0) {
        sb.append(", ");
      }
      sb.append(scen.getName());
    }
    return sb.toString();
  }

  /**
   * Sets the test condition if TPT version suports it via API. Prints a warning if TPT version can
   * use test set conditions but the feature is not available via API.
   */
  private void setTestSetCondtionIfPossible(TestSet existingTestSet, TestSet newTestSet,
                                            TptVersion tptVersion, TptLogger logger)
      throws RemoteException {
    if (tptVersion.supportsTestCaseConditionAccess()) {
      // just set ist
      newTestSet.setConditionEnabled(existingTestSet.isConditionEnabled());
      newTestSet.setCondition(existingTestSet.getCondition());
      newTestSet.setRequirementSet(existingTestSet.getRequirementSet());
      newTestSet.setRestrictedToLinkedTestCases(existingTestSet.isRestrictedToLinkedTestCases());
    } else if (tptVersion.supportsTestCaseConditions()) {
      logger
          .warn("The TPT version supports test set conditions but does not support configuration of"
              + " test set conditions via API."
              + "\nIf test set conditions are configured they will be ignored for the test run.");
    }
  }

}
