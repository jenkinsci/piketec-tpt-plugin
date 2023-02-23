package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * This {@link FormalRequirementStep} defines intervals between a start expression and a stop
 * expression, the abort expression aborts the interval before the stop expression becomes true.
 */
public interface BetweenStep extends FormalRequirementStep {

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
