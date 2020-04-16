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
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import com.piketec.jenkins.plugins.tpt.TptLog.LogLevel;
import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

import hudson.FilePath;
import hudson.Launcher;
import hudson.Proc;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;

/**
 * Executes the TPT test cases via command line on a single node.
 */
class TptPluginSingleJobExecutor {

  private TptLogger logger;

  private Launcher launcher;

  private AbstractBuild< ? , ? > build;

  private BuildListener listener;

  private FilePath[] exePaths;

  private String arguments;

  private List<JenkinsConfiguration> executionConfigs;

  private String jUnitXmlPath;

  private LogLevel jUnitLogLevel;

  private boolean enableJunit;

  /**
   * @param build
   *          used to get the workspace
   * @param launcher
   *          used to execute a process
   * @param listener
   *          to join TPT with a given timeout
   * @param exePaths
   *          the paths to the Tpt Executables
   * @param arguments
   *          commandline arguments for running tpt from the commandline
   * @param executionConfigs
   *          all the jenkins configurations given in the descriptor, used to get the
   *          Files,Execution Configuration, test Set, testDataDir, reportDir,etc
   * @param jUnitXmlPath
   *          the path where the jUnit XML is going to be created
   * @param jUnitLogLevel
   * @param enableJunit
   *          to know if is necessary to generate the jUnit XML
   */
  TptPluginSingleJobExecutor(AbstractBuild< ? , ? > build, Launcher launcher,
                             BuildListener listener, FilePath[] exePaths, String arguments,
                             List<JenkinsConfiguration> executionConfigs, String jUnitXmlPath,
                             LogLevel jUnitLogLevel, boolean enableJunit) {
    logger = new TptLogger(listener.getLogger());
    this.launcher = launcher;
    this.build = build;
    this.listener = listener;
    this.exePaths = exePaths;
    this.arguments = arguments;
    this.executionConfigs = executionConfigs;
    this.jUnitXmlPath = jUnitXmlPath;
    this.jUnitLogLevel = jUnitLogLevel;
    this.enableJunit = enableJunit;
  }

  /**
   * It looks for the tpt installation . Then prepares the test- and data directories. After that it
   * creates a command ( @see buildCommand ) in order to execute Tpt from the commandline. Then it
   * runs that command through the launcher and publish the Junit XML if necessary.
   * 
   * @return true if the execution from the tpt file was successful.
   */
  boolean execute() {
    boolean success = true;
    FilePath workspace = build.getWorkspace();
    // use first found (existing) TPT installation
    FilePath exeFile = null;
    for (FilePath f : exePaths) {
      try {
    	logger.info("Try to find TPT installation: "+f.getRemote());
        if (f.exists()) {
          exeFile = f;
          break;
        }
      } catch (IOException e) {
        // NOP, just try next file
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        return false;
      }
    }
    if (exeFile == null) {
      logger.error("No TPT installation found");
      return false;
    }
    // execute the sub-configuration
    for (JenkinsConfiguration ec : executionConfigs) {
      if (ec.isEnableTest()) {
      	try {
					ec = ec.replaceAndNormalize(Utils.getEnvironment(build, launcher, logger));
				} catch (InterruptedException e2) {
					logger.error(e2.getMessage());
					return false;
				}
	    	// Absolute paths are recognized as such, relative paths are resolved depending on the workspace directory,
	    	// or the unique sub folder created in the workspace for the current job. 
        FilePath testDataPath = new FilePath(build.getWorkspace(), Utils.getGeneratedTestDataDir(ec));
        FilePath reportPath = new FilePath(build.getWorkspace(), Utils.getGeneratedReportDir(ec));
        FilePath tptFilePath = new FilePath(build.getWorkspace(),ec.getTptFile());
        String configurationName = ec.getConfiguration();
        String tesSet = ec.getTestSet();
        logger.info("*** Running TPT-File \"" + tptFilePath + //
            "\" with configuration \"" + configurationName + "\" now. ***");
        
        try {
					testDataPath.mkdirs();
					reportPath.mkdirs();
				} catch (IOException | InterruptedException e1) {
					logger.error("Failed to create parent directories for " + testDataPath.getRemote() + " and/or " +reportPath.getRemote());
					return false;
				}
        
        String cmd = buildCommand(exeFile, arguments, tptFilePath, testDataPath.getRemote(),
            reportPath.getRemote(), configurationName, tesSet);
        try {
          // run the test...
        	boolean successOnlyForOneConfig = launchTPT(launcher, listener, cmd, ec.getTimeout());
          success &= successOnlyForOneConfig;
          if (successOnlyForOneConfig) {
            TPTBuildStepEntries.addEntry(ec, build);
          }
          if (enableJunit) {
            // transform TPT results into JUnit results
            logger.info("*** Publishing results now ***");
            Utils.publishAsJUnitResults(workspace, ec, testDataPath, jUnitXmlPath, jUnitLogLevel,
                logger);
          }
        } catch (IOException e) {
          logger.error(e.getMessage());
          success = false;
          // continue with next config in case of I/O error
        } catch (InterruptedException e) {
          logger.interrupt(e.getMessage());
          return false;
        }
      }
    }
    return success;
  }

  /**
   * Just creates the command line string to start the TPT execution.
   * 
   * @param exeFile
   *          Path to tpt.exe
   * @param arguments
   *          arguments for TPT
   * @param tptFile
   *          TPT file to load
   * @param dataDir
   *          directory where TPT will store the execution data
   * @param reportDir
   *          directory where TPT will create the report
   * @param configurationName
   *          the name of the execution configuration to execute
   * @return The concatenated string to start the test execution via command line.
   */
  private String buildCommand(FilePath exeFile, String arguments, FilePath tptFile, String dataDir,
                              String reportDir, String configurationName, String testSet) {
    StringBuilder cmd = new StringBuilder();
    String exeString = exeFile.getRemote();
    // surround path with ""
    if (!exeString.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(exeString);
    if (!exeString.endsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(' ');
    cmd.append(arguments);
    cmd.append(' ');
    String tptFileString = tptFile.getRemote();
    // surround path with ""
    if (!tptFileString.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(tptFileString);
    if (!tptFileString.endsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(' ');
    // surround name with ""
    if (!configurationName.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(configurationName);
    if (!configurationName.endsWith("\"")) {
      cmd.append('"');
    }
    if (StringUtils.isNotEmpty(testSet)) {
      cmd.append(" --testSet ");
      // surround path with ""
      if (!testSet.startsWith("\"")) {
        cmd.append('"');
      }
      cmd.append(testSet);
      if (!testSet.endsWith("\"")) {
        cmd.append('"');
      }
      logger.info("Running " + testSet);
    }
    cmd.append(" --dataDir ");
    // surround path with ""
    if (!dataDir.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(dataDir);
    if (!dataDir.endsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(" --reportDir ");
    // surround path with ""
    if (!reportDir.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(reportDir);
    if (!reportDir.endsWith("\"")) {
      cmd.append('"');
    }
    return cmd.toString();
  }

  /**
   * Starts TPT via command line and executes the given tests.
   * 
   * @param launcher
   *          to start the process
   * @param listener
   *          to join TPT with a given timeout
   * @param cmd
   *          The command to execute via command line
   * @param timeout
   *          The maximum allowed runtime for TPT.
   * @throws InterruptedException
   * @throws IOException
   */
  private boolean launchTPT(Launcher launcher, BuildListener listener, String cmd, long timeout)
      throws InterruptedException, IOException {
    boolean exitCodeWasNull = true;
    logger.info("Launching \"" + cmd + "\"");
    Launcher.ProcStarter starter = launcher.new ProcStarter();
    starter.cmdAsSingleString(cmd);
    starter.stdout(logger.getLogger());
    starter.stderr(logger.getLogger());
    Proc tpt = null;
    try {
      tpt = starter.start();
      if (timeout <= 0) {
        timeout = JenkinsConfiguration.DescriptorImpl.getDefaultTimeout();
      }
      logger.info("Waiting for TPT to complete. Timeout: " + timeout + "h");
      int exitcode = tpt.joinWithTimeout(timeout, TimeUnit.HOURS, listener);
      if (exitcode != 0) {
        logger.error("TPT process stops with exit code " + exitcode);
        exitCodeWasNull = false;
      }
    } catch (IOException e) {
      throw new IOException("TPT launch error: " + e.getMessage());
    } catch (InterruptedException e) {
      try {
        tpt.kill();
      } catch (IOException | InterruptedException e1) {
        throw new IOException(
            "TPT launch error: Interrupt requested, but cannot kill the TPT process. Please kill it manually.");
      }
      throw e;
    }
    return exitCodeWasNull;
  }

}
