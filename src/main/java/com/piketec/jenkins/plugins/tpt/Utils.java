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

import java.io.File;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;

import com.piketec.jenkins.plugins.tpt.TptLog.LogLevel;
import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.TptApi;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import jenkins.model.Jenkins;

/**
 * Collection of some utility methods and constants
 * 
 * @author jkuhnert, PikeTec GmbH
 */
public class Utils {

  static final String TPT_EXE_VAR = "PIKETEC_TPT_EXE";

  static final String TPT_FILE_VAR = "PIKETEC_TPT_FILE";

  static final String TPT_EXECUTION_CONFIG_VAR = "PIKETEC_TPT_EXEC_CFG";

  static final String TPT_TEST_CASE_NAME_VAR = "PIKETEC_TPT_TEST_CASE";

  static final String TPT_TEST_DATA_DIR_VAR_NAME = "PIKETEC_TPT_TESTDATA_DIR";

  static final String TPT_REPORT_DIR_VAR_NAME = "PIKETEC_TPT_REPORT_DIR";

  static final String TPT_EXECUTION_ID_VAR_NAME = "PIKETEC_TPT_EXECUTION_ID";

  static final String TPT_TEST_SET_NAME_VAR = "PIKETEC-TPT_TEST_SET_NAME";

  static final int DEFAULT_TPT_PORT = 1099;

  static final String DEFAULT_TPT_BINDING_NAME = "TptApi";

  static final int DEFAULT_STARTUP_WAIT_TIME = 60;

  private static final SimpleDateFormat DDMMYYHHMMSS = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

  /**
   * @return the current date. Used for the logs
   */
  static String getCurrentDateString() {
    Date date = new Date(); // acquire date before waiting for lock
    return formatDate(DDMMYYHHMMSS, date);
  }

  /**
   * @param format
   * @param date
   * @return formatted date
   */
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

  /**
   * Creates a File (workspace) with a FilePath.
   * 
   * @param workspace
   *          The workspace of the jenkisn job
   * @param logger
   *          To print some messages
   * @return The worksapce as a native Java file.
   * @throws InterruptedException
   *           If the current Job is cancelled
   */
  public static File getWorkspaceDir(FilePath workspace, TptLogger logger)
      throws InterruptedException {
    File workspaceDir = null;
    if (workspace == null) {
      logger.info("Location of the workspace is unknown.");
    } else {
      try {
        workspaceDir = new File(workspace.toURI());
      } catch (IOException ioe) {
        logger.info("Failed to get the workspace directory - reason: " + ioe);
      }
    }
    return workspaceDir;
  }

  /**
   * Creates a String with a path to create for the test data dir. This will be relative to the
   * build workspace
   * 
   * @param ec
   *          through the jenkins configuration we get the name and the configuration of the file.
   *          Used to create an unique path.
   * @return a String with the folders for the test data dir.
   */
  public static String getGeneratedTestDataDir(JenkinsConfiguration ec) {
    if (ec.getTestdataDir() == null || ec.getTestdataDir().trim().isEmpty()) {
      return "Piketec/" + FilenameUtils.getBaseName(ec.getTptFile()) + "/" + ec.getConfiguration()
          + "/testdata";
    } else {
      return ec.getTestdataDir();
    }
  }

  /**
   * Creates a String with a path to create for the report dir. This will be relative to the build
   * workspace
   * 
   * @param ec
   *          through the jenkins configuration we get the name and the configuration of the file.
   *          Used to create an unique path.
   * @return a String with the folders for the report dir.
   */
  public static String getGeneratedReportDir(JenkinsConfiguration ec) {
    if (ec.getReportDir() == null || ec.getReportDir().trim().isEmpty()) {
      return "Piketec/" + FilenameUtils.getBaseName(ec.getTptFile()) + "/" + ec.getConfiguration()
          + "/report";
    } else {
      return ec.getReportDir();
    }
  }

  /**
   * @return the rootdir from the "piketec-tpt" plugin, used for knowing where to get html files,
   *         json or some data stored there.
   * @throws IOException
   *           If no Jenkins isntance could be found
   */
  public static File getTptPluginRootDir() throws IOException {
    Jenkins jenkinsInstance = Jenkins.getInstance();
    if (jenkinsInstance == null) {
      throw new IOException("No Jenkins instance found.");
    }
    return new File(jenkinsInstance.getRootDir(), "plugins\\piketec-tpt");
  }

  /**
   * Checks of the existance of Tpt and tries to start tpt through the api. The process is run by
   * Launcher.ProcStarter.
   * 
   * @param build
   * @param launcher
   * @param logger
   * @param exePaths
   * @param port
   * @param bindingName
   * @param startupWaitTime
   * @return
   * @throws InterruptedException
   */
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

  /**
   * Conects to the Tpt Api through a registry. It looks for a host with the channel from the
   * launcher. Then it creates a registry with the host and the port, and finally it binds the
   * registry with the TptApi through the tpt binding name.
   * 
   * @param build
   *          The current Jenkins build
   * @param launcher
   *          The launcher
   * @param logger
   *          The logger to displax messages
   * @param exePaths
   *          The paths to TPT executables
   * @param tptPort
   *          the TPTRMI port
   * @param tptBindingName
   *          the TPT RMI binding name
   * @param startupWaitTime
   *          The wait time to start up TPT
   * @return Tpt Api that allows accessing tpt through the api.
   * @throws InterruptedException
   *           If the Job is cancelled
   */
  public static TptApi getTptApi(AbstractBuild< ? , ? > build, Launcher launcher, TptLogger logger,
                                 FilePath[] exePaths, int tptPort, String tptBindingName,
                                 long startupWaitTime)
      throws InterruptedException {
    String hostName = null;
    try {
      hostName = launcher.getChannel().call(new GetHostNameCallable());
    } catch (IOException e1) {
      logger.error("Could not retrive host name: " + e1.getMessage());
      return null;
    }
    logger.info("Try to connect to " + hostName + ":" + tptPort);
    logger.info("TPT Binding name: " + tptBindingName);
    try {
      return connectToTPT(logger, tptPort, tptBindingName, hostName);
    } catch (RemoteException | NotBoundException e) {
      // NOP, start TPT and try again
    }
    // start TPT and try again
    if (!startTpt(build, launcher, logger, exePaths, tptPort, tptBindingName, startupWaitTime)) {
      logger.error("Could not start TPT");
      return null;
    }
    try {
      return connectToTPT(logger, tptPort, tptBindingName, hostName);
    } catch (RemoteException | NotBoundException e) {
      logger.error(e.getMessage());
      return null;
    }
  }

  private static TptApi connectToTPT(TptLogger logger, int tptPort, String tptBindingName,
                                     String hostName)
      throws RemoteException, NotBoundException, AccessException {
    Registry registry;
    registry = LocateRegistry.getRegistry(hostName, tptPort);
    TptApi remoteApi = (TptApi)registry.lookup(tptBindingName);
    try {
      logger.info("Connected to TPT \"" + remoteApi.getTptVersion() + "\"");
    } catch (ApiException e) {
      logger.error(e.getMessage());
      // should not happen
    }
    return remoteApi;
  }

  /**
   * Publishes the Junit XML , it creates the folder for the XML and then it publishes the XML by
   * calling Publish.publishJUnitResults,
   * {@link Publish#publishJUnitResults(JenkinsConfiguration, FilePath, FilePath, TptLogger, LogLevel)
   * Publish.publishJUnitResults}. This method is used as a wrapper.
   * 
   * @param workspace
   *          The Jenkins workspace
   * @param jenkinsConfiguration
   *          The configuration to which the TPT test resuklt should be tranformed to JUnit
   * @param testDataDir
   *          The directory where TPT test data should be searched
   * @param jUnitXml
   *          The directory where the transformed results should be written to.
   * @param jUnitLogLevel
   *          the threshold for the severity of the log messages
   * @param logger
   *          To display messages
   * @return the number of test cases in the Junit XML
   * @throws IOException
   *           If an IO exception occured while parsing the TPT test results or while writing the
   *           JUnit xml files
   * @throws InterruptedException
   *           If the job is cancelled
   */
  static int publishAsJUnitResults(FilePath workspace, JenkinsConfiguration jenkinsConfiguration,
                                   FilePath testDataDir, String jUnitXml, LogLevel jUnitLogLevel,
                                   TptLogger logger)
      throws IOException, InterruptedException {
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
    return Publish.publishJUnitResults(jenkinsConfiguration, testDataDir, reportPath, logger,
        jUnitLogLevel);
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

  /**
   * Delete the files from a given Filepath
   * 
   * @param path
   * @throws IOException
   * @throws InterruptedException
   */
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
