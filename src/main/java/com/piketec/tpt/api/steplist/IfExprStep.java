/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2024 Synopsys Inc.
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
 * This {@link Step} provides the possibility to use an <code>if-block</code> in a
 * {@link StepListScenario}
 */
public interface IfExprStep extends Step {

  /**
   * @return expression for the IfExprStep.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getExpression() throws RemoteException;

  /**
   * Expression for the IfExprStep.
   * 
   * @param expr
   *          expression
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setExpression(String expr) throws RemoteException;

  /**
   * @return <code>true</code> if this step should run "always".
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isAlways() throws RemoteException;

  /**
   * Determines if this step should run "always".
   * 
   * @param value
   *          <code>true</code> to use always semantics
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAlways(boolean value) throws RemoteException;
}
