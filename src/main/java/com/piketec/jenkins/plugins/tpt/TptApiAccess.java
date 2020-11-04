package com.piketec.jenkins.plugins.tpt;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.piketec.jenkins.plugins.tpt.api.callables.ExecuteTestsSlaveCallable;
import com.piketec.jenkins.plugins.tpt.api.callables.GetTestCasesCallable;
import com.piketec.jenkins.plugins.tpt.api.callables.RunOverviewReportCallable;

import hudson.FilePath;
import hudson.Launcher;
import hudson.remoting.VirtualChannel;

/**
 * Since the TPT API should be used only on the localshost and not via a remote connection, all API
 * calls are done within Callables. This class provides utility methods to start the Callables.
 */
public class TptApiAccess {

  private Launcher launcher;

  private TptLogger logger;

  private FilePath[] exePaths;

  private int tptPort;

  private String tptBindingName;

  private long startUpWaitTime;

  /**
   * Provide all information that is needed to establish an TPT API connection. This information is
   * needed for any Callable that is called in later methods, so to reduce parameters in these
   * methods, get them here already.
   * 
   * @param launcher
   *          The launcher
   * @param logger
   *          for dumping messages
   * @param exePaths
   *          paths to the tpt.exe files, usually set in the Jenkins UI
   * @param tptPort
   *          port that the TPT API should use to connect.
   * @param tptBindingName
   *          binding name that the TPT API should use to connect.
   * @param tptStartupWaitTime
   *          time that Jenkins waits for TPT to start up if it is not running already
   */
  public TptApiAccess(Launcher launcher, TptLogger logger, FilePath[] exePaths, int tptPort,
                      String tptBindingName, long tptStartupWaitTime) {
    this.launcher = launcher;
    this.logger = logger;
    this.exePaths = exePaths.clone();
    this.tptPort = tptPort;
    this.tptBindingName = tptBindingName;
    this.startUpWaitTime = tptStartupWaitTime;
  }

  /**
   * Get all test cases for the given test set. If the test set is <code>null</code> or empty, the
   * test sets of the execution configuration are evaluated.
   * 
   * @param tptFilePath
   *          tpt file from which to get the test cases
   * @param executionConfigName
   *          execution configuration
   * @param testSet
   *          test set from which to get the test cases
   * @return a list of test case names for the given settings
   * @throws InterruptedException
   *           If thread was interrupted
   */
  public Collection<String> getTestCases(FilePath tptFilePath, String executionConfigName,
                                         String testSet)
      throws InterruptedException {
    GetTestCasesCallable callable =
        new GetTestCasesCallable(launcher.getListener(), "localhost", tptPort, tptBindingName,
            exePaths, startUpWaitTime, tptFilePath, executionConfigName, testSet);
    Collection<String> testCases = null;
    try {
      VirtualChannel channel = launcher.getChannel();
      if (channel == null) {
        logger.error("Getting test cases did not work: Agent does not support virtual channels.");
        return testCases;
      }
      testCases = channel.call(callable);
    } catch (IOException e) {
      logger.error("Getting test cases did not work: " + e.getMessage());
    }
    return testCases;
  }

  /**
   * Creates an overview report for already available test data.
   * 
   * @param tptFilePath
   *          - tpt file for which the overview report should be built
   * @param executionConfigName
   *          - execution configuration that was executed
   * @param testSet
   *          - test set that was executed
   * @param reportPath
   *          - path to the folder where the test case reports can be found and the overview report
   *          should be put
   * @param testDataPath
   *          - path to the test data that is needed to build the overview report
   * @return true if the report generation was successful, false otherwise.
   * @throws InterruptedException
   *           If thread was interrupted
   */
  public Boolean runOverviewReport(FilePath tptFilePath, String executionConfigName, String testSet,
                                   FilePath reportPath, FilePath testDataPath)
      throws InterruptedException {
    RunOverviewReportCallable callable = new RunOverviewReportCallable(launcher.getListener(),
        "localhost", tptPort, tptBindingName, exePaths, startUpWaitTime, tptFilePath,
        executionConfigName, testSet, reportPath, testDataPath);
    Boolean worked = false;
    try {
      VirtualChannel channel = launcher.getChannel();
      if (channel == null) {
        logger.error(
            "Running overview report did not work: Agent does not support virtual channels.");
        return worked;
      }
      worked = channel.call(callable);
    } catch (IOException e) {
      logger.error("Running overview report did not work: " + e.getMessage());
    }
    return worked;
  }

  /**
   * Executes all test cases named in <code>testSetList</code> as a sub set of the given test set.
   * 
   * @param tptFilePath
   *          - tpt file that should be executed
   * @param executionConfigName
   *          - execution configuration that should be executed
   * @param testSetName
   *          - test set that should be executed
   * @param slaveReportPath
   *          - path to where the report shall be put
   * @param slaveDataPath
   *          - path to where the test data shall be put
   * @param testSetList
   *          The list of test cases to execute
   * @return true if the execution was successful, false otherwise.
   * @throws InterruptedException
   *           If thread was interrupted
   */
  public Boolean executeTestsSlave(FilePath tptFilePath, String executionConfigName,
                                   String testSetName, FilePath slaveReportPath,
                                   FilePath slaveDataPath, List<String> testSetList)
      throws InterruptedException {
    ExecuteTestsSlaveCallable callable = new ExecuteTestsSlaveCallable(launcher.getListener(),
        "localhost", tptPort, tptBindingName, exePaths, startUpWaitTime, tptFilePath,
        slaveReportPath, slaveDataPath, executionConfigName, testSetList, testSetName);
    Boolean worked = false;
    try {
      VirtualChannel channel = launcher.getChannel();
      if (channel == null) {
        logger.error(
            "Executing tests on agent did not work: Agent does not support virtual channels.");
        return worked;
      }
      worked = channel.call(callable);
    } catch (IOException e) {
      logger.error("Executing tests on agent did not work: " + e.getMessage());
    }
    return worked;
  }
}
