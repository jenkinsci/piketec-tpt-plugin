package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * This {@link FormalRequirementStep} represents a the base step for the {@link FromStep} and
 * {@link UntilStep}.
 */
public interface FromOrUntilStep extends FormalRequirementStep {

  /**
   * The possible trigger types.
   */
  enum TriggerType {
    NONE, FOR_AT_LEAST, FOR_AT_MOST;
  }

  /**
   * @return the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getExpression() throws RemoteException;

  /**
   * The trigger type used for the expression.
   * 
   * @return the trigger type used for the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TriggerType getTriggerType() throws RemoteException;

  /**
   * Sets the trigger type used for the expression.
   * 
   * @param triggerType
   *          type of the trigger
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the triggerType is {@code null}
   */
  public void setTriggerType(TriggerType triggerType) throws RemoteException, ApiException;

  /**
   * Sets the trigger length for which the expression must be {@code true}.
   * 
   * @param triggerLength
   *          length for which the trigger must be present
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the triggerLength is {@code null}
   */
  public void setTriggerLength(String triggerLength) throws RemoteException, ApiException;

  /**
   * The length for which the trigger expression must be {@code true}.
   * 
   * @return the length for which the trigger expression must be {@code true}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTriggerLength() throws RemoteException;

}
