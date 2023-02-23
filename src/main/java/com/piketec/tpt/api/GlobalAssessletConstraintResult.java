/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2022 PikeTec GmbH
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

import com.piketec.tpt.api.TestCaseExecutionStatus.TestCaseStatus;

/**
 * This object provides an interface to obtain the result of a global assesslet constraint.
 */
public interface GlobalAssessletConstraintResult extends TptRemote {

  /**
   * Returns the name of the checked variable of this global assesslet constraint.
   * 
   * @return The variable name of this constraint.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getVariableName() throws RemoteException;

  /**
   * Returns the definition of this global assesslet constraint.
   * 
   * @return The constraint definition.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getConstraintDefinition() throws RemoteException;

  /**
   * Returns the result of this global assesslet constraint as {@link TestCaseStatus}.
   * 
   * @return The result of this constraint.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TestCaseStatus getResult() throws RemoteException;

  /**
   * Returns the number of checked test cases for the variable of this global assesslet constraint.
   * 
   * @return The number of checked test cases.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getCheckedTestCasesCount() throws RemoteException;

}
