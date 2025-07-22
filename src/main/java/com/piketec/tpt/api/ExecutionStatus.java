/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2025 Synopsys Inc.
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
package com.piketec.tpt.api;

import java.io.File;
import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.TestCaseExecutionStatus.TestCaseStatus;

/**
 * This object provides an interface to the current state of the test execution which is the
 * information as provided by the "Build Progress" Dialog.
 *
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface ExecutionStatus extends TptRemote {

  /**
   * @return Indicates whether the test execution is interrupted by user interaction, paused or has
   *         not started yet.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isPending() throws RemoteException;

  /**
   * @return Indicates whether the test execution is currently running (and not queued, interrupted
   *         or finished).
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isRunning() throws RemoteException;

  /**
   * Cancels the current test execution.
   * <p>
   * The outcome of this operation depends on the platform. In the most cases, the execution of the
   * current test case is finished.
   * </p>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void cancel() throws RemoteException;

  /**
   * Returns a list containing the execution state ({@link TestCaseExecutionStatus}) for each test
   * case that is part of this test execution.
   *
   * @return A list containing the states of the executed test cases or an empty list, if no test
   *         execution have been started so far.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<TestCaseExecutionStatus> getAllTestCases() throws RemoteException;

  /**
   * Returns the number of all test cases of the current test execution. Test cases that are part of
   * the test set in more than one {@link ExecutionConfigurationItem} (e.g. in a Back2Back test
   * scenario), will be counted for each {@link ExecutionConfigurationItem} separately.
   *
   * @return Number of total test cases executed
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getNumberOfAllTestCases() throws RemoteException;

  /**
   * Returns the number of test cases that are part of the current test execution that are currently
   * not finished (with state {@link TestCaseStatus#Pending}).
   * <p>
   * Test cases that are part of the test set in more than one {@link ExecutionConfigurationItem}
   * (e.g. in a Back2Back test scenario), will be counted for each
   * {@link ExecutionConfigurationItem} separately.
   * </p>
   *
   * @return The number of currently not executed test cases.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getNumberOfPendingTestCases() throws RemoteException;

  /**
   * Return the test case that is currently running (with the state {@link TestCaseStatus#Running}).
   * 
   * @return The currently executed test case or <code>null</code> if no test case is running.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Scenario getCurrentTestCase() throws RemoteException;

  /**
   * Returns the current (cumulative) execution state of the overall test execution.
   * <p>
   * The cumulative execution state is derived from the following priority (from high to low):
   * ResultError, ResultFailed, ResultSuccess, ResultUnknown, Running, Pending
   * </p>
   * The cumulative state is derived from the highest priority of all test cases and the result of
   * the global assessment.
   * 
   * @return The test case execution state with the highest priority.
   * 
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If there are no test cases in the executed test set.
   */
  public TestCaseStatus getTotalExecutionStatus() throws ApiException, RemoteException;

  /**
   * Returns the global assessment state of the overall test execution as
   * {@link GlobalAssessmentStatus}.
   * 
   * @return The global assessment state.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public GlobalAssessmentStatus getGlobalAssessmentStatus() throws RemoteException;

  /**
   * @return Returns a list of log entries as <code>String</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> getExecutionLog() throws RemoteException;

  /**
   * Waits for the execution to finish, then returns. Returns immediately if execution is already
   * finished. Will return if execution is canceled or <b>paused</b> e.g. because the limit of test
   * cases allowed to fail is reached or some user interaction.
   * 
   * @param timeout
   *          The maximum time in seconds to wait for the execution to finish. A timeout of
   *          {@code 0} or less means to wait forever.
   * 
   * @throws ApiException
   *           If thread is interrupted
   * @throws RemoteException
   *           remote communication problem
   */
  public void join(int timeout) throws ApiException, RemoteException;

  /**
   * Waits for the execution to finish. Shorthand for {@link #join(int)} with a timeout 0 or less.
   * See {@link #join(int)} for details.
   * 
   * @throws ApiException
   *           If thread is interrupted
   * @throws RemoteException
   *           remote communication problem
   */
  public void join() throws ApiException, RemoteException;

  /**
   * Reclassify test results from a file with reclassification information of a former test
   * execution.<br>
   * Existing reclassifications of the current test execution are not overridden.
   * 
   * @param file
   *          the XML file with reclassification information
   * 
   * @return a list of warnings, e.g., if tests with reclassification information in the given file
   *         are not present in the current test execution.
   * 
   * @throws ApiException
   *           if the execution is running, the file does not exist or is not a valid XML file with
   *           reclassification information
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> reclassifyFromFile(File file) throws ApiException, RemoteException;

  /**
   * Reclassify test results from a file with reclassification information of a former test
   * execution.<br>
   * Existing reclassifications of the current test execution are not overridden.
   * 
   * @param file
   *          the XML file with reclassification information
   * 
   * @return a list of warnings, e.g., if tests with reclassification information in the given file
   *         are not present in the current test execution.
   * 
   * @throws ApiException
   *           if the execution is running, the file does not exist or is not a valid XML file with
   *           reclassification information
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> reclassifyFromFilePath(String file) throws ApiException, RemoteException;

}
