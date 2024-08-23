package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.steplist.formalrequirements.GeneralWhileNode.YToleranceType;

/**
 * The signal compare node is used to compare two signals to find out where the signals' behavior
 * differs and where it is similar.
 *
 *
 */
public interface SignalCompareShallNode extends GeneralShallNode {

  /**
   * Returns the signal.
   * 
   * @return the signal
   * @throws RemoteException
   *           remote communication problem
   */
  public String getSignal() throws RemoteException;

  /**
   * Sets the signal which should be compared.
   * 
   * @param signal
   *          which should be compared
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if signal is {@code null}
   */
  public void setSignal(String signal) throws RemoteException, ApiException;

  /**
   * Returns the reference.
   * 
   * @return the reference
   * @throws RemoteException
   *           remote communication problem
   */
  public String getReference() throws RemoteException;

  /**
   * Sets the reference for the signal compare.
   * 
   * @param reference
   *          which should be compared
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if reference is {@code null}
   */
  public void setReference(String reference) throws RemoteException, ApiException;

  /**
   * Sets the time tolerance
   * 
   * @param timeTolerance
   *          the time tolerance
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if timeTolerance is {@code null}
   */
  public void setTimeTolerance(String timeTolerance) throws RemoteException, ApiException;

  /**
   * Returns the time tolerance.
   * 
   * @return time tolerance
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTimeTolerance() throws RemoteException;

  /**
   * Sets the value tolerance
   * 
   * @param valueTolerance
   *          the value tolerance
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if valueTolerance is {@code null}
   */
  public void setValueTolerance(String valueTolerance) throws RemoteException, ApiException;

  /**
   * Returns the value tolerance.
   * 
   * @return value tolerance
   * @throws RemoteException
   *           remote communication problem
   */
  public String getValueTolerance() throws RemoteException;

  /**
   * Sets the value tolerance type which can be Absolute or Relative
   * 
   * @param type
   *          the value tolerance type
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if type is {@code null}
   */
  public void setValueToleranceType(YToleranceType type) throws RemoteException, ApiException;

  /**
   * Returns the value tolerance type.
   * 
   * @return value tolerance type
   * @throws RemoteException
   *           remote communication problem
   */
  public YToleranceType getValueToleranceType() throws RemoteException;

}
