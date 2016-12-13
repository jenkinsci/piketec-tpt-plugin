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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.michelin.cio.hudson.plugins.copytoslave.CopyToMasterNotifier;
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
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Computer;
import hudson.slaves.SlaveComputer;

/**
 * Executes one test case via TPT API.
 * 
 * @author jkuhnert, PikeTec GmbH
 *
 */
class TptPluginSlaveExecutor {

  private TptLogger logger;

  private Launcher launcher;

  private AbstractBuild< ? , ? > build;

  private BuildListener listener;

  private FilePath[] exePaths;

  private int tptPort;

  private String tptBindingName;

  private File tptFile;

  private String execCfg;

  private String testDataDir;

  private String reportDir;

  private Set<String> testSetString;

  private long tptStartupWaitTime;

  private String executionId;

  TptPluginSlaveExecutor(Launcher launcher, AbstractBuild< ? , ? > build, BuildListener listener,
                         FilePath[] exePaths, int tptPort, String tptBindingName, File tptFile,
                         String execCfg, String testDataDir, String reportDir, String testSet,
                         long tptStartupWaitTime, String executionId) {
    this.logger = new TptLogger(listener.getLogger());
    this.launcher = launcher;
    this.build = build;
    this.listener = listener;
    this.exePaths = exePaths;
    this.tptPort = tptPort;
    this.tptBindingName = tptBindingName;
    this.tptFile = tptFile;
    this.execCfg = execCfg;
    this.testDataDir = testDataDir;
    this.reportDir = reportDir;
    this.testSetString = Utils.unescapeTestcaseNames(testSet);
    this.tptStartupWaitTime = tptStartupWaitTime;
    this.executionId = executionId;
  }

  public boolean execute() {
    logger = new TptLogger(listener.getLogger());
    try {
      // start tpt and recieve API
      TptApi api;
      try {
        api = Utils.getTptApi(build, launcher, logger, exePaths, tptPort, tptBindingName,
            tptStartupWaitTime);
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        return false;
      }
      if (api == null) {
        return false;
      }
      // open TPT File
      OpenResult openProject = api.openProject(tptFile);
      if (openProject.getProject() == null) {
        logger.error("Could not open project:\n" + Utils.toString(openProject.getLogs(), "\n"));
        return false;
      }
      new CleanUpTask(openProject.getProject(), executionId);
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
      File oldReportDir = config.getReportDir();
      File oldTestDataDir = config.getDataDir();

      Collection<Scenario> foundScenearios = new HashSet<>();
      find(openProject.getProject().getTopLevelTestlet().getTopLevelScenarioOrGroup().getItems(),
          testSetString, foundScenearios);
      if (foundScenearios.size() != testSetString.size()) {
        logger.error(
            "Could only find " + foundScenearios.size() + " of " + testSetString.size() + ".");
        return false;
      }

      FilePath path = null;
      try {
        path = new FilePath(build.getWorkspace(), testDataDir).absolutize();
        if (Computer.currentComputer() instanceof SlaveComputer) {
          logger.info("Creating and/or cleaning test data directory");
          path.mkdirs();
          path.deleteContents();
        }
      } catch (IOException e) {
        logger.error("Could not create test data dir");
        return false;
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        return false;
      }
      logger.info("Setting test data directory to " + path.getRemote());
      config.setDataDir(new File(path.getRemote()));

      path = null;
      try {
        path = new FilePath(build.getWorkspace(), reportDir).absolutize();
        if (Computer.currentComputer() instanceof SlaveComputer) {
          logger.info("Creating and/or cleaning report directory");
          path.mkdirs();
          path.deleteContents();
        }
      } catch (IOException e) {
        logger.error(e.getMessage());
        config.setDataDir(oldTestDataDir);
        return false;
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        config.setDataDir(oldTestDataDir);
        return false;
      }
      logger.info("Setting report directory to " + path.getRemote());
      config.setReportDir(new File(path.getRemote()));

      // store information to undo changes
      List<TestSet> oldTestSets = new ArrayList<>();
      List<TestSet> newTestSets = new ArrayList<>();
      List<ExecutionConfigurationItem> deactivated = new ArrayList<>();
      int i = 0;
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
                + Utils.toString(intersectionSet, ", ") + "\"");
            TestSet testSet = openProject.getProject().createTestSet(tmpTestSetName);
            newTestSets.add(testSet);
            for (Scenario scen : intersectionSet) {
              testSet.addTestCase(scen);
            }
            item.setTestSet(testSet);
          }
        }

      }

      // execute test
      ExecutionStatus execStatus = api.run(config);
      while (execStatus.isRunning() || execStatus.isPending()) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          logger.interrupt(e.getMessage());
          execStatus.cancel();
          break;
        }
      }
      // undo changes
      logger.info("Set test sets in execution config to old values.");
      for (ExecutionConfigurationItem item : config.getItems()) {
        item.setTestSet(oldTestSets.remove(0));
      }
      try {
        String includes = !StringUtils.isBlank(testDataDir) ? testDataDir
            : new File(tptFile.getParent(), oldTestDataDir.getPath()).getAbsolutePath();
        includes += "\\**\\*.*";
        if (!StringUtils.isBlank(reportDir) || StringUtils.isBlank(oldReportDir.getPath())) {
          includes += "," + (!StringUtils.isBlank(reportDir) ? reportDir
              : new File(tptFile.getParent(), oldReportDir.getPath()).getAbsolutePath());
          includes += "\\**\\*.*";
        }
        CopyToMasterNotifier copyToMaster =
            new CopyToMasterNotifier(includes, "", false, "", false);
        copyToMaster.perform(build, launcher, listener);
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        return false;
      } catch (IOException e) {
        logger.error("could not copy results to master: " + e.getMessage());
      }
      logger.info("reset test data and report directory to " + oldTestDataDir.getPath() + " and "
          + oldReportDir.getPath());
      config.setDataDir(oldTestDataDir);
      config.setReportDir(oldReportDir);
      for (TestSet testSet : newTestSets) {
        logger.info("delete temporary test set \"" + testSet.getName() + "\"");
        openProject.getProject().getTestSets().delete(testSet);
      }
      logger.info("Reactivate temporary deactivated execution config items.");
      for (ExecutionConfigurationItem item : deactivated) {
        item.setActive(true);
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

}
