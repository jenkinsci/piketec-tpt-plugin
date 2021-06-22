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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

/**
 * This {@link Step} provides the possibility to execute steps in a do-while-loop as long as the
 * condition is satisfied.
 */
public interface WaitExprStep extends Step {

  /**
   * @return the wait condition.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getExpression() throws RemoteException;

  /**
   * Sets the wait condition.
   * 
   * @param expr
   *          wait condition
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setExpression(String expr) throws RemoteException;

  /**
   * @return <code>true</code> if the step is assessed in the report.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isAssess() throws RemoteException;

  /**
   * Determines if the step is assessed in the report.
   * 
   * @param assess
   *          <code>true</code> if assessment should be turned on
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAssess(boolean assess) throws RemoteException;
}
