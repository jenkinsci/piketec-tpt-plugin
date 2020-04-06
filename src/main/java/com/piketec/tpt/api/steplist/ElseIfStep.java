/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2020 PikeTec GmbH
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

import com.piketec.tpt.api.ApiException;

/**
 * This {@link Step} provides the possibility to use an <code>else-if-block</code> in a
 * {@link StepListScenario}
 */
public interface ElseIfStep extends Step {

  /**
   * @return the declaration for the comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDeclaration() throws RemoteException;

  /**
   * Determines the declaration used for the comparison.
   * 
   * @param name
   *          name of the channel/parameter to compare with
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setDeclaration(String name) throws RemoteException, ApiException;

  /**
   * @return the definition side term for the comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDefinition() throws RemoteException;

  /**
   * Determines definition side term for the comparison.
   * 
   * @param def
   *          expression to compare with
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setDefinition(String def) throws RemoteException, ApiException;

  /**
   * @return the local tolerance for this comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTolerance() throws RemoteException;

  /**
   * Sets the local tolerance for this comparison.
   * 
   * @param tolerance
   *          expression to specify the tolerance
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setTolerance(String tolerance) throws RemoteException, ApiException;

  /**
   * @return the operator for the comparison.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Operator getOperator() throws RemoteException;

  /**
   * Sets the operator for the comparison.
   * 
   * @param op
   *          operator
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setOperator(Operator op) throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if a tolerance from scaling is used instead of the local tolerance.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isToleranceFromScaling() throws RemoteException;

  /**
   * Set to <code>true</code> if a tolerance from scaling is used instead of the local tolerance.
   * 
   * @param tolerance
   *          true to derive tolerance from scaling coeffs
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setToleranceFromScaling(boolean tolerance) throws RemoteException, ApiException;

}
