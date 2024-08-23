package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * This {@link ConditionTreeNode} applies the given {@link TimeRestrictionType} to the previously
 * defined intervals.
 */
public interface TimeRestrictionNode extends ConditionTreeNode {

  /**
   * The possible when types.
   */
  enum TimeRestrictionType {
    IGNORE_FIRST, IGNORE_LAST, USE_FIRST, USE_LAST;
  }

  /**
   * Returns the type of time restriction.
   * 
   * @return the time restriction type
   * @throws RemoteException
   *           remote communication problem
   */
  public TimeRestrictionType getTimeRestriction() throws RemoteException;

  /**
   * Sets the type of time restriction.
   * 
   * @param timeRestriction
   *          the type of time restriction
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if timeRestriction is {@code null}
   */
  public void setTimeRestriction(TimeRestrictionType timeRestriction)
      throws RemoteException, ApiException;

  /**
   * The time component which belongs to the type of the time restriction.
   * 
   * @return The time component which belongs to the type of the time restriction.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTime() throws RemoteException;

  /**
   * Sets the time component which belongs to the type of the time restriction.
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
