/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2018 Synopsys Inc.
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.tools.ant.types.Commandline;

import com.piketec.jenkins.plugins.tpt.TptLog.LogLevel;
import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import jenkins.model.Jenkins;

/**
 * Collection of some utility methods and constants
 * 
 * @author jkuhnert, Synopsys Inc.
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

  private static final Pattern illegalWindowsFileNameCharacters =
      Pattern.compile("[<>:\"/\\\\|?*]|\\R");

  private static final Set<String> reservedWindowsFileNames = new HashSet<>(Arrays.asList("CON",
      "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9",
      "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9", ".", ".."));

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

  /**
   * Creates a String with a path to create for the test data dir. This will be relative to the
   * build workspace
   * 
   * @param ec
   *          through the jenkins configuration we get the id of the configuration, which is used to
   *          create a unique path.
   * @return a String with the folders for the test data dir.
   */
  public static String getGeneratedTestDataDir(JenkinsConfiguration ec) {
    if (ec.getTestdataDir() == null || ec.getTestdataDir().trim().isEmpty()) {
      return "Piketec" + File.separator + ec.getId() + File.separator + "testdata";
    } else {
      return ec.getTestdataDir();
    }
  }

  /**
   * Creates a String with a path to create for the report dir. This will be relative to the build
   * workspace
   * 
   * @param ec
   *          through the jenkins configuration we get the id of the configuration, which is used to
   *          create a unique path.
   * @return a String with the folders for the report dir.
   */
  public static String getGeneratedReportDir(JenkinsConfiguration ec) {
    if (ec.getReportDir() == null || ec.getReportDir().trim().isEmpty()) {
      return "Piketec" + File.separator + ec.getId() + File.separator + "report";
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
    Jenkins jenkinsInstance = Jenkins.get();
    if (jenkinsInstance == null) {
      throw new IOException("No Jenkins instance found.");
    }
    return new File(jenkinsInstance.getRootDir(), "plugins" + File.separator + "piketec-tpt");
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
    if (!reportPath.isDirectory()) {
      reportPath.mkdirs();
      if (!reportPath.isDirectory()) {
        throw new IOException("Could not create report directory \"" + reportPath + "\"");
      }
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

  /**
   * Transforms the list into a String by calling toString() on every item, seperating them by the
   * given <code>delimeter</code>
   * 
   * @param <T>
   *          The types of items contained in the list
   * @param list
   *          The list to tranform to String
   * @param delimeter
   *          The sting seperating the items
   * @return The list represneted as a string
   */
  public static <T> String toString(Collection<T> list, String delimeter) {
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
   * the files in a directory recursively if {@link FilePath#deleteContents()} fails. Also TPT may
   * keep tptbin-Files open for some Time in a cache, so we will retry more often to delete the
   * files than the standard implementation.
   * 
   * @param path
   *          The paths to delete
   * @throws IOException
   *           If path could not be deleted
   * @throws InterruptedException
   *           If thread was interrupted
   */
  public static void deleteFiles(FilePath path) throws IOException, InterruptedException {
    if (path.exists()) {
      if (path.isDirectory()) {
        try {
          path.deleteContents();
        } catch (IOException e) {
          deleteFilesRecursive(path);
        }
      } else {
        path.delete();
        path.mkdirs();
      }
    } else {
      path.mkdirs();
    }
  }

  /**
   * Delete the files from a given Filepath
   * 
   * @param path
   *          The path to delete
   * @throws IOException
   *           If paths could not be deleted
   * @throws InterruptedException
   *           If thread was interrupted
   */
  private static void deleteFilesRecursive(FilePath path) throws IOException, InterruptedException {
    for (FilePath subPath : path.list()) {
      if (subPath.isDirectory()) {
        deleteFilesRecursive(subPath);
      } else {
        deleteWithAdditonalTries(subPath);
      }
    }
  }

  private static void deleteWithAdditonalTries(FilePath path)
      throws IOException, InterruptedException {
    final int tptbinCloseTimer = 11000; // Timer in TPT is 10s, let's try a bit longer
    final int waitTime = 500;
    for (int i = 0; i < (tptbinCloseTimer / waitTime) - 1; i++) {
      try {
        path.delete();
        return; // successfull deleted!
      } catch (IOException e) {
        Thread.sleep(waitTime);
        continue; // try again
      }
    }
    path.delete(); // last try
  }

  /**
   * Copies all files from a remote location to another remote location.
   * 
   * FilePath.copyRecursiveTo is not able to do that. See
   * https://issues.jenkins-ci.org/browse/JENKINS-2126
   * 
   * @param from
   *          Target path
   * @param to
   *          Source path
   * @param logger
   *          for dumping messages
   * @throws IOException
   *           If an IO error occurs
   * @throws InterruptedException
   *           If thread was interrupted
   */
  public static void copyRecursive(FilePath from, FilePath to, TptLogger logger)
      throws IOException, InterruptedException {

    if (from.equals(to)) {
      return;
    }
    if (!from.exists()) {
      logger.error(from.getRemote() + " does not exist!");
      return;
    }
    if (!from.isDirectory()) {
      logger.error(from.getRemote() + " is not a directory!");
      return;
    }
    for (FilePath f : from.list()) {
      if (f.isDirectory()) {
        copyRecursive(f, new FilePath(to, f.getName()), logger);
      } else {
        FilePath toFile = new FilePath(to, f.getName());
        f.copyTo(toFile);
      }
    }
  }

  /**
   * Checks if name does not contain a lien break, &lt;, &gt;, :, \, ", /, \, |, ?, * nor matches
   * any of the reserved names CON, PRN, AUX, NUL, COM1, COM2, COM3, COM4, COM5, COM6, COM7, COM8,
   * COM9, LPT1, LPT2, LPT3, LPT4, LPT5, LPT6, LPT7, LPT8, and LPT9.
   * 
   * @param name
   *          The name to check
   * @return <code>true</code> if the name can be used as a windows file name
   */
  public static boolean isValidWidnowsFileName(String name) {
    if (illegalWindowsFileNameCharacters.matcher(name).find()) {
      return false;
    }
    if (reservedWindowsFileNames.contains(name.trim())) {
      return false;
    }
    return true;
  }

  /**
   * Get the environment variables for a build. In pipeline all strings should be handled as
   * litereals and replacing is dones by Groovy so we do not accept {@link Run} here.
   * 
   * @param build
   *          The build.
   * @param launcher
   *          The launcher
   * @param logger
   *          for dumping messages
   * @return the environment variables from the agent machine the code is running on
   * @throws InterruptedException
   *           If thread was interrupted
   */
  public static EnvVars getEnvironment(AbstractBuild< ? , ? > build, Launcher launcher,
                                       TptLogger logger)
      throws InterruptedException {
    EnvVars environment;
    try {
      environment = build.getEnvironment(launcher.getListener());
    } catch (IOException e) {
      environment = new EnvVars();
      logger.error(e.getLocalizedMessage());
    }
    return environment;
  }

  /**
   * Get all InvisibleActionTPTExecution of a build.
   * 
   * @param build
   *          The build to get the InvisibleActionTPTExecution list from
   * @return The list of InvisibleActionTPTExecution
   */
  public static List<InvisibleActionTPTExecution> getInvisibleActionTPTExecutions(final Run< ? , ? > build) {
    List<InvisibleActionTPTExecution> jenkinsConfigurationsToPublishForThisWorkspace =
        build.getActions(InvisibleActionTPTExecution.class);
    return jenkinsConfigurationsToPublishForThisWorkspace;
  }

  /**
   * Check for duplicate ID of <b>already finished</b> JenkinsConfiguration. Adds an
   * InvisibleActionTPTExecution if ID is valid. Is synchronized to ensure that parallel parts of a
   * pipeline using the same ID leads to a failure. Method is not heavily used, so staic
   * synchronized is feasible.
   * 
   * @param resolvedConfig
   *          JenkinsConfiguration with ID
   * @param build
   *          Build that executes the JenkinsConfiguration and may already have executed other
   *          JenkinsConfiguration wich must have a different ID.
   * @param logger
   *          to write an error message to
   * @return <code>true</code> if the ID is unique and a valid windows file name
   */
  public static synchronized boolean checkIdAndAddInvisibleActionTPTExecution(JenkinsConfiguration resolvedConfig,
                                                                              Run< ? , ? > build,
                                                                              TptLogger logger) {
    if (checkId(resolvedConfig, build, logger)) {
      build.addAction(new InvisibleActionTPTExecution(resolvedConfig));
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check for duplicate ID of <b>already finished</b> JenkinsConfiguration.
   * 
   * @param ec
   *          JenkinsConfiguration with ID
   * @param build
   *          Build that executes the JenkinsConfiguration and may already have executed other
   *          JenkinsConfiguration wich must have a different ID.
   * @param logger
   *          to write an error message to
   * @return <code>true</code> if the ID is unique and a valid windows file name
   */
  public static boolean checkId(JenkinsConfiguration ec, Run< ? , ? > build, TptLogger logger) {
    String id = ec.getId();
    if (id == null || id.isEmpty()) {
      logger.error("ID must not be empty");
      return false;
    }
    if (!isValidWidnowsFileName(id)) {
      logger.error("ID \"" + id + "\" is not a valid windows file name");
      return false;
    }
    List<InvisibleActionTPTExecution> invisibleActionTPTExecutions =
        getInvisibleActionTPTExecutions(build);
    for (InvisibleActionTPTExecution invisibleActionTPTExecution : invisibleActionTPTExecutions) {
      if (id.equals(invisibleActionTPTExecution.getId())) {
        logger.error("ID \"" + id + "\" already in use.");
        return false;
      }
    }
    return true;
  }

  /**
   * Translates a command line String to seperate arguments using
   * {@link Commandline#translateCommandline(String)}.
   * 
   * @return a fixed-size list backed by the command line array
   */
  public static List<String> parseCommandLine(String toProcess) {
    String[] translatedCommandline = Commandline.translateCommandline(toProcess);
    return Arrays.asList(translatedCommandline);
  }
}
