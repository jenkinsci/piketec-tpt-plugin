package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

public interface GeneralShallNode extends ConditionTreeNode {

  /**
   * The possible duration types.
   */
  enum DurationType {
    ALWAYS, NEVER, AT_LEAST_ONCE, BECOMES_TRUE, BECOMES_FALSE, AT_THE_START, AT_THE_END,
    FOR_EXACTLY, FOR_AT_LEAST, FOR_AT_MOST, FOR_BETWEEN;
  }

  /**
   * The possible tolerance types.
   */
  enum ToleranceType {
    IMMEDIATELY, AFTER_EXACTLY, WITHIN;
  }

  /**
   * Sets the duration type for the expression.
   * 
   * @param durationType
   *          type of the duration
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the durationType is {@code null}
   */
  public void setDurationType(DurationType durationType) throws RemoteException, ApiException;

  /**
   * The duration type for the expression.
   * 
   * @return the duration type for the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public DurationType getDurationType() throws RemoteException;

  /**
   * Sets the duration time for the expression.
   * 
   * @param duration
   *          the duration time
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the duration is {@code null}
   */
  public void setDurationTime(String duration) throws RemoteException, ApiException;

  /**
   * @return the duration time for the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDurationTime() throws RemoteException;

  /**
   * Sets the max time for the expression.
   * 
   * @param maxTime
   *          the max time of the expression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the maxTime is {@code null}
   */
  public void setMaxTime(String maxTime) throws RemoteException, ApiException;

  /**
   * The max time of the expression.
   * 
   * @return the max time of the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getMaxTime() throws RemoteException;

  /**
   * Sets the time tolerance type for the expression.
   * 
   * @param toleranceType
   *          type of the time tolerance
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the toleranceType is {@code null}
   */
  public void setToleranceType(ToleranceType toleranceType) throws RemoteException, ApiException;

  /**
   * The time tolerance type for the expression.
   * 
   * @return the time tolerance type for the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public ToleranceType getToleranceType() throws RemoteException;

  /**
   * Sets the time tolerance for the expression.
   * 
   * @param tolerance
   *          the time tolerance
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the tolerance is {@code null}
   */
  public void setToleranceTime(String tolerance) throws RemoteException, ApiException;

  /**
   * The time tolerance for the expression.
   * 
   * @return the time tolerance for the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getToleranceTime() throws RemoteException;

}
