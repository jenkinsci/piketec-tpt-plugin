package com.piketec.jenkins.plugins.tpt.api.callables;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jenkinsci.remoting.RoleChecker;

import com.piketec.jenkins.plugins.tpt.TptLogger;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.ExecutionConfiguration;
import com.piketec.tpt.api.ExecutionConfigurationItem;
import com.piketec.tpt.api.ExecutionStatus;
import com.piketec.tpt.api.OpenResult;
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

  public ExecuteTestsSlaveCallable(TaskListener listener, String hostName, int tptPort,
                                   String tptBindingName, FilePath[] exePaths, long startUpWaitTime,
                                   FilePath tptFilePath, FilePath slaveReportPath,
                                   FilePath slaveDataPath, String executionConfigName,
                                   List<String> testSet, String testSetName) {
    super(listener, hostName, tptPort, tptBindingName, exePaths, startUpWaitTime);
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
    TptApi api = getApi();
    if (api == null) {
      logger.error("Could not establish connection to the TPT API.");
      return false;
    }
    OpenResult openProject = getOpenProject(logger, api, tptFilePath);
    try {
      // search execution configuration by name
      Collection<ExecutionConfiguration> execConfigs =
          openProject.getProject().getExecutionConfigurations().getItems();
      ExecutionConfiguration config = null;
      for (ExecutionConfiguration elem : execConfigs) {
        if (elem.getName().equals(execCfg)) {
          config = elem;
          break;
        }
      }
      if (config == null) {
        logger.error("Could not find config");
        return false;
      }
      // adjust config to execute only the given one test case
      String oldReportDir = config.getReferenceDirPath();
      String oldTestDataDir = config.getDataDirPath();

      Collection<Scenario> foundScenearios = new HashSet<>();
      find(openProject.getProject().getTopLevelTestlet().getTopLevelScenarioOrGroup().getItems(),
          testSetList, foundScenearios);
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
      if (StringUtils.isEmpty(testSetName)) {
        for (ExecutionConfigurationItem item : config.getItems()) {
          oldTestSets.add(item.getTestSet());
          if (item.isActive()) {
            Collection<Scenario> intersectionSet =
                intersectByHash(item.getTestSet().getTestCases().getItems(), foundScenearios);
            if (intersectionSet.isEmpty()) {
              item.setActive(false);
              deactivated.add(item);
            } else {
              String tmpTestSetName = "JENKINS Exec " + i;
              i++;
              logger.info("Create test set \"" + tmpTestSetName + "\" for execution of \""
                  + remoteScenarioSetToString(intersectionSet) + "\"");
              TestSet testSet = openProject.getProject().createTestSet(tmpTestSetName);
              newTestSets.add(testSet);
              for (Scenario scen : intersectionSet) {
                testSet.addTestCase(scen);
              }
              item.setTestSet(testSet);
            }
          }
        }
      } else {
        String tmpTestSetName = "JENKINS Exec " + testSetName;
        logger.info("Create test set \"" + tmpTestSetName + "\" for execution of \""
            + remoteScenarioSetToString(foundScenearios) + "\" from File " + tptFilePath.getName());

        TestSet testSet = openProject.getProject().createTestSet(tmpTestSetName);
        newTestSets.add(testSet);
        for (Scenario scen : foundScenearios) {
          testSet.addTestCase(scen);
        }
        for (ExecutionConfigurationItem item : config.getItems()) {
          oldTestSets.add(item.getTestSet());
          if (item.isActive()) {
            item.setTestSet(testSet);
          }
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
          openProject.getProject().getTestSets().delete(testSet);
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

}
