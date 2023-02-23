package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * This {@link FormalRequirementStep} yields an interval from the first time the expression becomes
 * true, until the end of the context. The time step where the expression became true is included.
 */
public interface FromStep extends FromOrUntilStep {

  /**
   * Sets the expression.
   * 
   * @param expr
   *          the expression from which we are measuring
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the expr is {@code null}
   */
  public void setExpression(String expr) throws RemoteException, ApiException;

  /**
   * Sets a time which extends the interval where the expression is true by the specified duration.
   * 
   * @param interval
   *          the extending interval
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the interval is {@code null}
   */
  public void setFollowingIntervalLength(String interval) throws RemoteException, ApiException;

  /**
   * The time which extends the interval where the expression is true by the specified duration.
   * 
   * @return the time which extends the interval where the expression is true by the specified
   *         duration
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getFollowingIntervalLength() throws RemoteException;

}
