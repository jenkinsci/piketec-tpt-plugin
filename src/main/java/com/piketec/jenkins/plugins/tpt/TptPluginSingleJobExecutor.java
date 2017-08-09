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
import java.util.List;
import java.util.concurrent.TimeUnit;

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

  private boolean onlyNullExitCode = true; // have all TPT processes terminated with exit code == 0?

  private TptLogger logger;

  private Launcher launcher;

  private AbstractBuild< ? , ? > build;

  private BuildListener listener;

  private FilePath[] exePaths;

  private String arguments;

  private String jUnitXmlPath;

  private LogLevel jUnitLogLevel;

  private List<JenkinsConfiguration> executionConfigs;

  TptPluginSingleJobExecutor(AbstractBuild< ? , ? > build, Launcher launcher,
                             BuildListener listener, FilePath[] exePaths, String arguments,
                             String jUnitXmlPath, LogLevel jUnitLogLevel,
                             List<JenkinsConfiguration> executionConfigs) {
    logger = new TptLogger(listener.getLogger());
    this.launcher = launcher;
    this.build = build;
    this.listener = listener;
    this.exePaths = exePaths;
    this.arguments = arguments;
    this.jUnitXmlPath = jUnitXmlPath;
    this.jUnitLogLevel = jUnitLogLevel;
    this.executionConfigs = executionConfigs;
  }

  boolean execute() {
    boolean success = true;
    FilePath workspace = build.getWorkspace();
    File workspaceDir = Utils.getWorkspaceDir(workspace, logger);
    // use first found (existing) TPT installation
    FilePath exeFile = null;
    for (FilePath f : exePaths) {
      try {
        if (f.exists()) {
          exeFile = f;
          break;
        }
      } catch (IOException e) {
        // NOP, just try next file
      } catch (InterruptedException e) {
        logger.error("Interrupted");
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
        File dataDir = Utils.getAbsolutePath(workspaceDir, ec.getTestdataDir());
        File reportDir = Utils.getAbsolutePath(workspaceDir, ec.getReportDir());
        File tptFile = Utils.getAbsolutePath(workspaceDir, ec.getTptFile());
        String configurationName = ec.getConfiguration();
        logger.info("*** Running TPT-File \"" + tptFile + //
            "\" with configuration \"" + configurationName + "\" now. ***");
        if (Utils.createParentDir(dataDir, workspace)
            && Utils.createParentDir(reportDir, workspace)) {
          String cmd =
              buildCommand(exeFile, arguments, tptFile, dataDir, reportDir, configurationName);
          try {
            // run the test...
            launchTPT(launcher, listener, cmd, ec.getTimeout());
            // transform TPT results into JUnit results
            logger.info("*** Publishing results now ***");
            Utils.publishResults(workspace, ec, jUnitXmlPath, jUnitLogLevel, logger);
          } catch (IOException e) {
            logger.error(e.getMessage());
            success = false;
            // continue with next config in case of I/O error
          } catch (InterruptedException e) {
            logger.interrupt(e.getMessage());
            return false;
          }
        } else {
          logger
              .error("Failed to create parent directories for " + dataDir + " and/or " + reportDir);
          success = false;
        }
      }
    }
    return success && onlyNullExitCode;
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
  private String buildCommand(FilePath exeFile, String arguments, File tptFile, File dataDir,
                              File reportDir, String configurationName) {
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

    String tptFileString = tptFile.toString();
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

    cmd.append(" --dataDir ");
    // surround path with ""
    String dataDirString = dataDir.toString();
    if (!dataDirString.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(dataDirString);
    if (!dataDirString.endsWith("\"")) {
      cmd.append('"');
    }

    cmd.append(" --reportDir ");
    // surround path with ""
    String reportDirString = reportDir.toString();
    if (!reportDirString.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(reportDirString);
    if (!reportDirString.endsWith("\"")) {
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
  private void launchTPT(Launcher launcher, BuildListener listener, String cmd, long timeout)
      throws InterruptedException, IOException {
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
        onlyNullExitCode = false;
      }
    } catch (IOException e) {
      throw new IOException("TPT launch error: " + e.getMessage());
    } catch (InterruptedException e) {
      try {
        tpt.kill();
      } catch (Exception e1) {
        throw new IOException(
            "TPT launch error: Interrupt requested, but cannot kill the TPT process. Please kill it manually.");
      }
      throw e;
    }
  }

}
