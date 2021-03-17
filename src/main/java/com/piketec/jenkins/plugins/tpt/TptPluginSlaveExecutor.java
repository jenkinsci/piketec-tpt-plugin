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
import java.util.List;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;
import com.piketec.jenkins.plugins.tpt.api.callables.CleanUpCallable;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;

/**
 * Executes one test case via TPT API.
 * 
 * @author jkuhnert, PikeTec GmbH
 */
class TptPluginSlaveExecutor {

  private TptLogger logger;

  private Launcher launcher;

  private FilePath workspace;

  private TaskListener listener;

  private FilePath[] exePaths;

  private int tptPort;

  private String tptBindingName;

  private List<String> testSetList;

  private long tptStartupWaitTime;

  private Run< ? , ? > masterId;

  private FilePath masterWorkspace;

  private FilePath masterDataPath;

  private FilePath masterReportPath;

  private JenkinsConfiguration jenkinsConfig;

  /**
   * @param launcher
   *          passed for executing a process
   * @param listener
   *          for the logs
   * @param exePaths
   *          the paths to the Tpt Executables
   * @param tptPort
   *          the port for binding to the TptApi
   * @param tptBindingName
   *          the binding name used to connect to the TptApi (for the registry)
   * @param tptFile
   *          the tpt file that will be executed
   * @param execCfg
   *          the tpt execution configuration as String
   * @param testDataDir
   *          the path to the test data dir
   * @param testSet
   *          a chunk of test
   * @param tptStartupWaitTime
   *          the time it should wait before start tpt
   * @param masterId
   *          actual build, used for getting an unique id
   * @param testSetName
   *          the name of the test set if given
   * @param masterWorkspace
   *          the workspace from the master, to know where to copy the results
   */
  TptPluginSlaveExecutor(Launcher launcher, FilePath workspace, TaskListener listener,
                         FilePath[] exePaths, int tptPort, String tptBindingName,
                         JenkinsConfiguration jenkinsConfig, List<String> testSet,
                         long tptStartupWaitTime, Run< ? , ? > masterId, FilePath masterWorkspace,
                         FilePath masterDataPath, FilePath masterReportPath) {
    this.logger = new TptLogger(listener.getLogger());
    this.launcher = launcher;
    this.workspace = workspace;
    this.listener = listener;
    this.exePaths = exePaths;
    this.tptPort = tptPort;
    this.tptBindingName = tptBindingName;
    this.jenkinsConfig = jenkinsConfig;
    this.testSetList = testSet;
    this.tptStartupWaitTime = tptStartupWaitTime;
    this.masterId = masterId;
    this.masterWorkspace = masterWorkspace;
    this.masterDataPath = masterDataPath;
    this.masterReportPath = masterReportPath;
  }

  /**
   * Executes a small chunks of tests. It binds to the Tpt Api , check if the given Execution
   * Configuration exists. Prepares the test- and data-directories. Creates a temporary testSet from
   * the chunk of test (if no testSet is given). Then through the tpt api executes the testCases and
   * then it copies the results to the master workspace.
   * 
   * @return true if the tpt execution has been successfully.
   * @throws InterruptedException
   *           If thread was interrupted
   */
  public boolean execute() throws InterruptedException {
    TptApiAccess tptApiAccess =
        new TptApiAccess(launcher, logger, exePaths, tptPort, tptBindingName, tptStartupWaitTime);

    if (workspace == null) {
      logger.error("No workspace available");
      return false;
    }
    FilePath slaveReportPath = new FilePath(workspace, Utils.getGeneratedReportDir(jenkinsConfig));
    FilePath slaveDataPath = new FilePath(workspace, Utils.getGeneratedTestDataDir(jenkinsConfig));
    FilePath tptFilePath = new FilePath(workspace, jenkinsConfig.getTptFile());

    // Register cleanup task that is called in the end to close remote TPT Project
    CleanUpCallable cleanUpCallable = new CleanUpCallable(listener, "localhost", tptPort,
        tptBindingName, exePaths, tptStartupWaitTime, tptFilePath);
    new CleanUpTask(masterId, cleanUpCallable, launcher);

    // Clean and setup the report and testdata directoires
    try {
      if (!masterWorkspace.equals(workspace)) {
        logger.info("Creating and/or cleaning test data directory " + slaveDataPath.getRemote());
        Utils.deleteFiles(slaveDataPath);
      }
    } catch (IOException e) {
      logger.error("Could not create or clear test data dir " + slaveDataPath.getRemote());
      return false;
    }
    try {
      if (!masterWorkspace.equals(workspace)) {
        logger.info("Creating and/or cleaning report directory " + slaveReportPath.getRemote());
        slaveReportPath.mkdirs();
        slaveReportPath.deleteContents();
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
      return false;
    }

    // Execute Tests on slave:
    boolean executionResult =
        tptApiAccess.executeTestsSlave(tptFilePath, jenkinsConfig.getConfiguration(),
            jenkinsConfig.getTestSet(), slaveReportPath, slaveDataPath, testSetList);

    // Copy tpt-testresults back to master, so the master can build the report
    try {
      Utils.copyRecursive(slaveDataPath, masterDataPath, logger);
      Utils.copyRecursive(slaveReportPath, masterReportPath, logger);
      logger.info("Copied all data to master from File " + tptFilePath.getName() + " to "
          + masterWorkspace.getRemote());
    } catch (IOException e) {
      logger.error("could not copy results to master: " + e.getMessage());
    }
    return executionResult;
  }
}
