package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * This {@link FormalRequirementStep} yields an interval from the start of the context interval
 * until the first time the expression gets fulfilled.
 */
public interface UntilStep extends FromOrUntilStep {

  /**
   * Sets the expression.
   * 
   * @param expr
   *          the expression until which we are measuring
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the expr is {@code null}
   */
  public void setExpression(String expr) throws RemoteException, ApiException;

  /**
   * sets a time which precedes the interval where the expression is {@code true} by the specified
   * duration.
   * 
   * @param interval
   *          the preceding interval
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the interval is {@code null}
   */
  public void setPrecedingIntervalLength(String interval) throws RemoteException, ApiException;

  /**
   * The time which precedes the interval where the expression is true by the specified duration.
   * 
   * @return the time which precedes the interval where the expression is true by the specified
   *         duration
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getPrecedingIntervalLength() throws RemoteException;
}
