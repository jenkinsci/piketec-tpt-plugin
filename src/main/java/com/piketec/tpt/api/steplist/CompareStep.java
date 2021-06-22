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
 * This {@link Step} provides the possibility to evaluate a comparison during test execution.
 */
public interface CompareStep extends Step {

  /**
   * The possible compare types.
   */
  enum CompareType {
    Exists, Always, First, Last
  }

  /**
   * @return the left-hand side term for the comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getLeftHandSideExpression() throws RemoteException;

  /**
   * Determines the left-hand side term for the comparison.
   * 
   * @param expr
   *          expression for left hand side
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setLeftHandSideExpression(String expr) throws RemoteException;

  /**
   * @return the right-hand side term for the comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getRightHandSideExpression() throws RemoteException;

  /**
   * Determines the right-hand side term for the comparison.
   * 
   * @param expr
   *          expression for right hand side
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setRightHandSideExpression(String expr) throws RemoteException;

  /**
   * Sets the explicit tolerance for this comparison.
   * 
   * @param expr
   *          tolerance expression
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTolerance(String expr) throws RemoteException;

  /**
   * @return the explicit tolerance for this comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTolerance() throws RemoteException;

  /**
   * <code>true</code> iff the tolerance shall be considered as relative tolerance in percent [%].
   * In this case, the tolerance is computed as <code>rhs * tolerance / 100</code> which means the
   * right hand side expression "rhs" is chosen to specify the actual tolerance.
   * 
   * @param relativeTolerance
   *          <code>true</code> iff tolerance shall be considered as relative value in percent [%]
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setRelativeTolerance(boolean relativeTolerance) throws RemoteException;

  /**
   * <code>true</code> iff the tolerance shall be considered as relative tolerance in percent [%].
   * In this case, the tolerance is computed as <code>rhs * tolerance / 100</code> which means the
   * right hand side expression "rhs" is chosen to specify the actual tolerance.
   * 
   * @return <code>true</code> iff tolerance shall be considered as relative value in percent [%]
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isRelativeTolerance() throws RemoteException;

  /**
   * Sets the operator for the comparison.
   * 
   * @param operator
   *          operator for comparison
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOperator(Operator operator) throws RemoteException;

  /**
   * @return the operator for the comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Operator getOperator() throws RemoteException;

  /**
   * Determines when the comparison is evaluated.
   * 
   * @param cmptype
   *          type for comparison
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCompareType(CompareType cmptype) throws RemoteException;

  /**
   * @return when the comparison is evaluated.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public CompareType getCompareType() throws RemoteException;

  /**
   * Set to <code>true</code> if a tolerance from scaling is used instead of the explicit tolerance.
   * 
   * @param tolfromscaling
   *          <code>true</code> if the tolerance shall be derived from scaling coeffs
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setToleranceFromScaling(boolean tolfromscaling) throws RemoteException;

  /**
   * @return <code>true</code> if a tolerance from scaling is used instead of the explicit
   *         tolerance.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isToleranceFromScaling() throws RemoteException;

}
