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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

/**
 * This {@link Step} provides the possibility to execute steps in a do-while-loop as long as the
 * comparison condition is satisfied.
 */
public interface WaitValueStep extends Step {

  /**
   * @return the declaration for the comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDeclaration() throws RemoteException;

  /**
   * Determines the declaration for the comparison.
   * 
   * @param declaration
   *          left-hand side variable name
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDeclaration(String declaration) throws RemoteException;

  /**
   * @return the comparative value.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDefinition() throws RemoteException;

  /**
   * Determines the comparative value.
   * 
   * @param value
   *          right-hand side expression
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDefinition(String value) throws RemoteException;

  /**
   * Sets the local tolerance for this comparison.
   * 
   * @param expr
   *          tolerance expression
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTolerance(String expr) throws RemoteException;

  /**
   * @return the local tolerance for this comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTolerance() throws RemoteException;

  /**
   * Sets the operator for the comparison.
   * 
   * @param op
   *          operator
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOperator(Operator op) throws RemoteException;

  /**
   * @return the operator for the comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Operator getOperator() throws RemoteException;

  /**
   * Set to <code>true</code> if a tolerance from scaling is used instead of the local tolerance.
   * 
   * @param tolfromscaling
   *          true if the tolerance value should be derived from scaling coeffs
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setToleranceFromScaling(boolean tolfromscaling) throws RemoteException;

  /**
   * @return <code>true</code> if a tolerance from scaling is used instead of the local tolerance.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isToleranceFromScaling() throws RemoteException;

  /**
   * @return <code>true</code> if the step is assessed in the report.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isAssess() throws RemoteException;

  /**
   * Sets if the step is assessed in the report.
   * 
   * @param assess
   *          <code>true</code> if this steps should be considered in assessment
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAssess(boolean assess) throws RemoteException;

  /**
   * @return <code>true</code> if this step is only satisfied if the next step is satisfied, too.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isJoinWithNext() throws RemoteException;

  /**
   * Sets if this step is only satisfied if the next step is satisfied, too.
   * 
   * @param value
   *          <code>true</code> if this step should be combined with the next step of the same type
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setJoinWithNext(boolean value) throws RemoteException;
}
