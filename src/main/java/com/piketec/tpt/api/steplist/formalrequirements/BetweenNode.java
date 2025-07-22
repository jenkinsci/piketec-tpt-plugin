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
package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * This {@link ConditionTreeNode} defines intervals between a start expression and a stop
 * expression, the abort expression aborts the interval before the stop expression becomes true.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface BetweenNode extends ConditionTreeNode {

  /**
   * The start expression.
   * 
   * @return the start expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getStartExpression() throws RemoteException;

  /**
   * Sets the start expression.
   * 
   * @param expr
   *          start expression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the expr is {@code null}
   */
  public void setStartExpression(String expr) throws RemoteException, ApiException;

  /**
   * The abort expression.
   * 
   * @return the abort expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getAbortExpression() throws RemoteException;

  /**
   * Sets the abort expression.
   * 
   * @param expr
   *          abort expression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the expr is {@code null}
   */
  public void setAbortExpression(String expr) throws RemoteException, ApiException;

  /**
   * The stop expression.
   * 
   * @return the stop expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getStopExpression() throws RemoteException;

  /**
   * Sets the stop expression.
   * 
   * @param expr
   *          stop expression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the expr is {@code null}
   */
  public void setStopExpression(String expr) throws RemoteException, ApiException;

}
