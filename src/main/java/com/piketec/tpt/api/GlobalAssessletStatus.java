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

import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.TestCaseExecutionStatus.TestCaseStatus;

/**
 * This object provides an interface to obtain the state of a global assesslet.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface GlobalAssessletStatus extends TptRemote {

  /**
   * Returns the name of the executed global assesslet.
   * 
   * @return The global assesslet name.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getName() throws RemoteException;

  /**
   * Returns the overall result of this global assesslet as {@link TestCaseStatus}.
   * 
   * @return The overall result.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TestCaseStatus getOverallResult() throws RemoteException;

  /**
   * Returns the result of the this global assesslet as {@link TestCaseStatus}. This method always
   * returns the same result as {@link #getOverallResult()} (exists for legacy reasons only).
   * 
   * @return The result of this global assesslet (same as {@link #getOverallResult()}).
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Use {@link #getOverallResult()} instead. Will be removed in TPT 2026.06.
   */
  @Deprecated
  public TestCaseStatus getScriptResult() throws RemoteException;

  /**
   * Returns the results for each checked constraint of this global assesslet as
   * {@link GlobalAssessletConstraintResult}.
   * 
   * @return The results for each checked constraint.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<GlobalAssessletConstraintResult> getConstraintResults() throws RemoteException;

}
