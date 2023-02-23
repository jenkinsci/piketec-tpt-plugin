package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * This {@link FormalRequirementStep} defines an interval which happens when a condition is
 * evaluated based on the {@link WhenType}.
 */
public interface WhenStep extends FormalRequirementStep {

  /**
   * The possible when types.
   */
  enum WhenType {
    BECOMES_TRUE, HAS_BECOME_TRUE, WILL_BECOME_TRUE, HAS_BEEN_TRUE;
  }

  /**
   * @return the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getExpression() throws RemoteException;

  /**
   * Sets the expression.
   * 
   * @param expr
   *          the expression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the expr is {@code null}
   */
  public void setExpression(String expr) throws RemoteException, ApiException;

  /**
   * The type of evaluation of the expression.
   * 
   * @return The type of evaluation of the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public WhenType getWhenType() throws RemoteException;

  /**
   * Sets the type of evaluation of the expression.
   * 
   * @param whenType
   *          the type of the expression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the whenType is {@code null}
   */
  public void setWhenType(WhenType whenType) throws RemoteException, ApiException;

  /**
   * The time component which belongs to the type of the evaluation.
   * 
   * @return The time component which belongs to the type of the evaluation.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTime() throws RemoteException;

  /**
   * Sets the time component which belongs to the type of the evaluation
   * 
   * @param time
   *          the time
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the time is {@code null}
   */
  public void setTime(String time) throws RemoteException, ApiException;
}
