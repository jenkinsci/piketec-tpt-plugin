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

import com.piketec.jenkins.plugins.tpt.api.callables.CleanUpCallable;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;

/**
 * Executes one test case via TPT API.
 * 
 * @author jkuhnert, PikeTec GmbH
 */
class TptPluginSlaveExecutor {

  private TptLogger logger;

  private Launcher launcher;

  private AbstractBuild< ? , ? > build;

  private BuildListener listener;

  private FilePath[] exePaths;

  private int tptPort;

  private String tptBindingName;

  private FilePath tptFile;

  private String execCfg;

  private String testDataDir;

  private String reportDir;

  private List<String> testSetString;

  private long tptStartupWaitTime;

  private AbstractBuild<?,?> masterId;

  private String testSetName;

  private FilePath masterWorkspace;

  /**
   * @param launcher
   *          passed for executing a process
   * @param build
   *          current build, used to get the workspace and for binding to the TptApi
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
  TptPluginSlaveExecutor(Launcher launcher, AbstractBuild< ? , ? > build, BuildListener listener,
                         FilePath[] exePaths, int tptPort, String tptBindingName, FilePath tptFile,
                         String execCfg, String testDataDir, String reportDir, List<String> testSet,
                         long tptStartupWaitTime, AbstractBuild<?,?> masterId, String testSetName,
                         FilePath masterWorkspace) {
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
    this.testSetString = testSet;
    this.tptStartupWaitTime = tptStartupWaitTime;
    this.masterId = masterId;
    this.testSetName = testSetName;
    this.masterWorkspace = masterWorkspace;
  }

  /**
   * Executes a small chunks of tests. It binds to the Tpt Api , check if the given Execution
   * Configuration exists. Prepares the test- and data-directories. Creates a temporary testSet from
   * the chunk of test (if no testSet is given). Then through the tpt api executes the testCases and
   * then it copies the results to the master workspace.
   * 
   * @return true if the tpt execution has been successfully.
   */
  public boolean execute() {
    TptApiAccess tptApiAccess = new TptApiAccess(launcher, logger, exePaths,  tptPort, tptBindingName, tptStartupWaitTime);
    
    // Register cleanup task that is called in the end to close remote TPT Project
    CleanUpCallable cleanUpCallable = new CleanUpCallable(listener, "localhost", tptPort, tptBindingName, 
    		exePaths, tptStartupWaitTime, tptFile);
    new CleanUpTask(masterId, cleanUpCallable, launcher);
    
    FilePath slaveReportPath = new FilePath(build.getWorkspace(), reportDir);
		FilePath slaveDataPath = new FilePath(build.getWorkspace(), testDataDir);
		FilePath masterDataPath = new FilePath(masterWorkspace, testDataDir);
		FilePath masterReportPath = new FilePath(masterWorkspace, reportDir);
		FilePath workspace = build.getWorkspace();
		
		// Clean and setup the report and testdata directoires
		try {
		  if (!masterWorkspace.equals(workspace)) {
		    logger.info("Creating and/or cleaning test data directory "+slaveDataPath.getRemote());
		    Utils.deleteFiles(slaveDataPath);
		  }
		} catch (IOException e) {
		  logger.error("Could not create or clear test data dir "+slaveDataPath.getRemote());
		  return false;
		} catch (InterruptedException e) {
		  logger.interrupt(e.getMessage());
		  return false;
		}
		try {
		  if (!masterWorkspace.equals(workspace)) {
		    logger.info("Creating and/or cleaning report directory "+slaveReportPath.getRemote());
//		    slaveReportPath.mkdirs();
		    slaveReportPath.deleteContents();
		  }
		} catch (IOException e) {
		  logger.error(e.getMessage());
		  return false;
		} catch (InterruptedException e) {
		  logger.interrupt(e.getMessage());
		  return false;
		}

		// Execute Tests on slava:
		boolean executionResult = tptApiAccess.executeTestsSlave(tptFile, execCfg, testSetName, slaveReportPath, 
				slaveDataPath, testSetString);
		if(executionResult) {
			logger.info("Execution worked");
		}else {
			logger.error("Execution failed!");
		}
		
		// Copy tpt-testresults back to master, so the master can build the report
		try {
		  slaveDataPath.copyRecursiveTo(masterDataPath);
		  slaveReportPath.copyRecursiveTo(masterReportPath);
		  logger.info("Copied all data to master from File " + tptFile.getName() + " to "
		      + masterWorkspace.getRemote());
		} catch (InterruptedException e) {
		  logger.interrupt("could not copy results to master: "+e.getMessage());
		  return false;
		} catch (IOException e) {
		  logger.error("could not copy results to master: " + e.getMessage());
		}
		return executionResult;
  }
}
