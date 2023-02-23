package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * This {@link FormalRequirementStep} yields intervals for which the expression is true
 */
public interface WhileStep extends FormalRequirementStep {

  /**
   * The possible when types. Which describe how often a condition should become true.
   */
  enum WhileType {
    Always, For_At_Least, For_More_Than, For_At_Most;
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
   * @return The type of evaluation of the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public WhileType getWhileType() throws RemoteException;

  /**
   * Sets the type of evaluation of the expression.
   * 
   * @param whileType
   *          the type of the expression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the whileType is {@code null}
   */
  public void setWhileType(WhileType whileType) throws RemoteException, ApiException;

  /**
   * @return The time for which the expression has to be true.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTime() throws RemoteException;

  /**
   * Sets the time for which the expression has to be true.
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
