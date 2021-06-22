/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2021 PikeTec GmbH
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

import java.rmi.RemoteException;
import java.util.List;

/**
 * This object provides an interface to obtain the current state of execution as well as any log
 * messages for the assigned test case.
 */
public interface TestCaseExecutionStatus extends TptRemote {

  /**
   * This enumeration represents all possible execution and result states for a test case.
   * <p>
   * The possible options are:
   * </p>
   * <ul>
   * <li>Pending</li>
   * <li>ResultUnknown</li>
   * <li>ResultSuccess</li>
   * <li>ResultFailed</li>
   * <li>ResultError</li>
   * <li>Running</li>
   * <li>ResultNoAsssessments <b>Deprecated:</b> Only listed to remain compatible to old TPT
   * versions. Completely replaced by ResultUnkown.</li>
   * </ul>
   */
  public enum TestCaseStatus {
    Pending, ResultUnknown, ResultSuccess, ResultFailed, ResultError, Running,
    /**
     * @deprecated Only listed to remain compatible to old TPT versions. Completely replaced by
     *             ResultUnkown. Will be removed in TPT-18.
     */
    @Deprecated
    ResultNoAsssessments
  }

  /**
   * @return Returns the current state of execution as {@link TestCaseStatus}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TestCaseStatus getStatus() throws RemoteException;

  /**
   * @return Returns a list of log entries as <code>String</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> getStatusLog() throws RemoteException;

  /**
   * Manuall set (reclassify) the execution status to either {@link TestCaseStatus#ResultSuccess} or
   * {@link TestCaseStatus#ResultFailed} for the report. The actual test case result will not be
   * overwritten by this operation.
   * <p>
   * This function corresponds to the "reclassify" button in the TPT GUI.
   * </p>
   * 
   * @param success
   *          <ul>
   *          <li><code>true</code> if the test result should be reclassified to a success</li>
   *          <li><code>false</code> if the test result should be reclassified to a failure</li>
   *          </ul>
   * @param userName
   *          The name for the user that is responsible for the reclassification.
   * @param comment
   *          A description/comment, why this reclassification is OK.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void reclassify(boolean success, String userName, String comment) throws RemoteException;

  /**
   * @return The {@link ExecutionConfigurationItem} in which the assigned test case was, is or
   *         should be executed.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public ExecutionConfigurationItem getExecutionConfigurationItem() throws RemoteException;

  /**
   * @return The assigned test case.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Scenario getTestcase() throws RemoteException;
}
