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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.TptApi;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;

class Utils {

  static final String TPT_EXE_VAR = "PIKETEC_TPT_EXE";

  static final String TPT_FILE_VAR = "PIKETEC_TPT_FILE";

  static final String TPT_EXECUTION_CONFIG_VAR = "PIKETEC_TPT_EXEC_CFG";

  static final String TPT_TEST_CASE_NAME_VAR = "PIKETEC_TPT_TEST_CASE";

  static final String TPT_TEST_DATA_DIR_VAR_NAME = "PIKETEC_TPT_TESTDATA_DIR";

  static final String TPT_REPORT_DIR_VAR_NAME = "PIKETEC_TPT_REPORT_DIR";

  static final String TPT_EXECUTION_ID_VAR_NAME = "PIKETEC_TPT_EXECUTION_ID";

  static final int DEFAULT_TPT_PORT = 1099;

  static final String DEFAULT_TPT_BINDING_NAME = "TptApi";

  static final int DEFAULT_STARTUP_WAIT_TIME = 60;

  private static final SimpleDateFormat DDMMYYHHMMSS = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

  static String getCurrentDateString() {
    Date date = new Date(); // acquire date before waiting for lock
    return formatDate(DDMMYYHHMMSS, date);
  }

  static String formatDate(DateFormat format, Date date) {
    synchronized (format) {
      return format.format(date);
    }
  }

  static boolean createParentDir(File directory, FilePath workspace) {
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

  static File getWorkspaceDir(FilePath workspace, TptLogger logger) {
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

  private static boolean startTpt(AbstractBuild< ? , ? > build, Launcher launcher, TptLogger logger,
                                  FilePath[] exePaths, int port, String bindingName,
                                  long startupWaitTime)
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
                                 long startupWaitTime)
      throws InterruptedException {
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

  static int publishResults(FilePath workspace, JenkinsConfiguration ec, String jUnitXml,
                            TptLogger logger)
      throws IOException {
    FilePath reportPath = ((jUnitXml == null) || jUnitXml.trim().isEmpty()) ? workspace
        : new FilePath(workspace, jUnitXml);

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

  static <T> String toString(Collection<T> list, String delimeter) {
    StringBuilder sb = new StringBuilder();
    for (T obj : list) {
      if (sb.length() > 0 && delimeter != null) {
        sb.append(delimeter);
      }
      sb.append(obj);
    }
    return sb.toString();
  }

  static String escapeTestCaseNames(Collection<String> testCases) {
    StringBuilder sb = new StringBuilder();
    for (String testCase : testCases) {
      if (testCase == null || testCase.isEmpty()) {
        continue;
      }
      if (sb.length() > 0) {
        sb.append(';');
      }
      sb.append(testCase.replace("\\", "\\\\").replace(";", "\\;"));
    }
    return sb.toString();
  }

  static Set<String> unescapeTestcaseNames(String testCases) {
    Set<String> result = new HashSet<>();
    StringBuilder currentTestCaseName = new StringBuilder();
    boolean escapeCharRead = false;
    for (char c : testCases.toCharArray()) {
      switch (c) {
        case '\\':
          if (escapeCharRead) {
            currentTestCaseName.append(c);
            escapeCharRead = false;
          } else {
            escapeCharRead = true;
          }
          break;
        case ';':
          if (escapeCharRead) {
            currentTestCaseName.append(c);
            escapeCharRead = false;
          } else {
            if (currentTestCaseName.length() > 0) {
              result.add(currentTestCaseName.toString());
              currentTestCaseName.setLength(0);
            }
            escapeCharRead = false;
          }
          break;
        default:
          assert escapeCharRead == false;
          currentTestCaseName.append(c);
          escapeCharRead = false;
      }
    }
    if (currentTestCaseName.length() > 0) {
      result.add(currentTestCaseName.toString());
    }
    return result;
  }

  /**
   * TPT changes its workind directory during execution, fails to set it back correctly after
   * multicore execution and prevents the deletion of test data directory. This method only deletes
   * the files in a directory recursively if {@link FilePath#path.deleteContents()} fails.
   */
  static void deleteFiles(FilePath path) throws IOException, InterruptedException {
    if (path.exists()) {
      if (path.isDirectory()) {
        try {
          path.deleteContents();
        } catch (IOException e) {
          deleteFilesRecursive(path);
        }
      } else {
        path.delete();
      }
    } else {
      path.mkdirs();
    }
  }

  private static void deleteFilesRecursive(FilePath path) throws IOException, InterruptedException {
    for (FilePath subPath : path.list()) {
      if (subPath.isDirectory()) {
        deleteFilesRecursive(subPath);
      } else {
        subPath.delete();
      }
    }
  }

}
