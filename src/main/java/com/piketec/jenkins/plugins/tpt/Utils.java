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

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.TptApi;

public class Utils {

  public static final String TPT_EXE_VAR = "PIKETEC_TPT_EXE";

  public static final String TPT_FILE_VAR = "PIKETEC_TPT_FILE";

  public static final String TPT_EXECUTION_CONFIG_VAR = "PIKETEC_TPT_EXEC_CFG";

  public static final String TPT_TEST_CASE_NAME_VAR = "PIKETEC_TPT_TEST_CASE";

  public static final String TPT_TEST_DATA_DIR_VAR_NAME = "PIKETEC_TPT_TESTDATA_DIR";

  public static final String TPT_REPORT_DIR_VAR_NAME = "PIKETEC_TPT_REPORT_DIR";

  public static final int DEFAULT_TPT_PORT = 1099;

  public static final String DEFAULT_TPT_BINDING_NAME = "TptApi";

  public static final int DEFAULT_STARTUP_WAIT_TIME = 60;

  private static final SimpleDateFormat DDMMYYHHMMSS = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

  public static String getCurrentDateString() {
    Date date = new Date(); // acquire date before waiting for lock
    return formatDate(DDMMYYHHMMSS, date);
  }

  public static String formatDate(DateFormat format, Date date) {
    synchronized (format) {
      return format.format(date);
    }
  }

  public static boolean createParentDir(File directory, FilePath workspace) {
    File parentDir = directory.getParentFile();

    if (parentDir == null) {
      return true;
    }

    try {
      new FilePath(workspace, parentDir.getPath()).mkdirs();
      return new FilePath(workspace, parentDir.getPath()).isDirectory();
    } catch (IOException e) {
      // NOP
    } catch (InterruptedException e) {
      // NOP
    }
    return false;
  }

  public static File getWorkspaceDir(FilePath workspace, TptLogger logger) {
    File workspaceDir = null;

    if (workspace == null) {
      logger.info("Location of the workspace is unknown.");
    } else {

      try {
        workspaceDir = new File(workspace.toURI());
      } catch (IOException ioe) {
        logger.info("Failed to get the workspace directory - reason: " + ioe);
      } catch (InterruptedException ie) {
        logger.info("Failed to get the workspace directory - reason: " + ie);
      }
    }

    return workspaceDir;
  }

  private static boolean startTpt(AbstractBuild< ? , ? > build, Launcher launcher,
                                  TptLogger logger, FilePath[] exePaths, int port,
                                  String bindingName, long startupWaitTime)
      throws InterruptedException {
    FilePath exeFile = null;
    for (FilePath f : exePaths) {
      try {
        if (f.exists()) {
          exeFile = f;
          break;
        }
      } catch (IOException e) {
        // NOP, just try next file
      }
    }
    try {
      if (exeFile == null || !exeFile.exists()) {
        logger.error("TPT exe not found.");
        return false;
      }
    } catch (IOException e1) {
      logger.error("Could not dertmine existence of TPT.");
      return false;
    }
    Launcher.ProcStarter starter = launcher.new ProcStarter();
    starter.cmds(exeFile.getRemote(), "--apiPort", Integer.toString(port), "--apiBindingName",
        bindingName);
    try {
      starter.start();
    } catch (IOException e) {
      logger.error("Could not start TPT.");
      return false;
    }
    logger.info("Waiting " + startupWaitTime / 1000 + "s for TPT to start.");
    Thread.sleep(startupWaitTime);
    return true;
  }

  public static TptApi getTptApi(AbstractBuild< ? , ? > build, Launcher launcher, TptLogger logger,
                                 FilePath[] exePaths, int tptPort, String tptBindingName,
                                 long startupWaitTime) throws InterruptedException {
    Registry registry;
    String hostName = null;
    try {
      hostName = launcher.getChannel().call(new GetHostNameCallable());
    } catch (IOException e1) {
      logger.error("Could not retrive host name: " + e1.getMessage());
      return null;
    }
    logger.info("Try to connect to " + hostName + ":" + tptPort);
    logger.info("TPT Binding name: " + tptBindingName);
    boolean tptSuccessfullyStarted = false;
    try {
      registry = LocateRegistry.getRegistry(hostName, tptPort);
      TptApi remoteApi = (TptApi)registry.lookup(tptBindingName);
      try {
        logger.info("Connected to TPT \"" + remoteApi.getTptVersion() + "\"");
      } catch (ApiException e) {
        logger.error(e.getMessage());
        // should not happen
      }
      return remoteApi;
    } catch (RemoteException e) {
      tptSuccessfullyStarted =
          startTpt(build, launcher, logger, exePaths, tptPort, tptBindingName, startupWaitTime);
    } catch (NotBoundException e) {
      tptSuccessfullyStarted =
          startTpt(build, launcher, logger, exePaths, tptPort, tptBindingName, startupWaitTime);
    }
    if (!tptSuccessfullyStarted) {
      logger.error("Could not start TPT");
      return null;
    }
    try {
      registry = LocateRegistry.getRegistry(hostName, tptPort);
      TptApi remoteApi = (TptApi)registry.lookup(tptBindingName);
      try {
        logger.info("Connected to TPT \"" + remoteApi.getTptVersion() + "\"");
      } catch (ApiException e) {
        logger.error(e.getMessage());
        // should not happen
      }
      return remoteApi;
    } catch (RemoteException e) {
      logger.error(e.getMessage());
      return null;
    } catch (NotBoundException e) {
      logger.error(e.getMessage());
      return null;
    }
  }

  public static int publishResults(FilePath workspace, JenkinsConfiguration ec, String jUnitXml,
                                   TptLogger logger) throws IOException {
    FilePath reportPath =
        ((jUnitXml == null) || jUnitXml.trim().isEmpty()) ? workspace : new FilePath(workspace,
            jUnitXml);

    try {

      if (!reportPath.isDirectory()) {
        reportPath.mkdirs();

        if (!reportPath.isDirectory()) {
          throw new IOException("Could not create report directory \"" + reportPath + "\"");
        }
      }
    } catch (InterruptedException ie) {
      throw new IOException("Failed to get the directory: " + reportPath, ie);
    }
    return Publish.publishJUnitResults(workspace, reportPath, ec, logger);
  }

  /**
   * Builds a absolute path from the workspace directory and the given path.
   * <ul>
   * <li>If both are <code>null</code>, the current working directory will returned - hopefully it
   * is inside the workspace.</li>
   * <li>If the workspace is <code>null</code>, the path will returned.</li>
   * <li>If the path is <code>null</code>, the workspace will returned.</li>
   * <li>If the path is absolute, the path will returned.</li>
   * <li>If the path is relative, the path will append to the workspace and returned.</li>
   * </ul>
   * 
   * @param workspaceDir
   *          Current workspace for the build.
   * @param path
   *          Relative or absolute path.
   * @return A absolute path, but it can be a nonexisting file system object or not a directory.
   */
  public static File getAbsolutePath(File workspaceDir, File path) {
    File absPath = workspaceDir;

    if (path == null) {
      absPath = (workspaceDir == null) ? new File("") : workspaceDir;
    } else {
      if (path.isAbsolute()) {
        absPath = path;
      } else {
        absPath = (workspaceDir == null) ? path : new File(workspaceDir, path.toString());
      }
    }
    return absPath.isAbsolute() ? absPath : absPath.getAbsoluteFile();
  }

}
