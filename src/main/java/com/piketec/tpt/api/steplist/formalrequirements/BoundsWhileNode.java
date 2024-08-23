package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * The bounds node is used to find out if and where a signal stays within or exceeds certain bounds.
 *
 */
public interface BoundsWhileNode extends GeneralWhileNode {

  /**
   * Sets the minimum
   * 
   * @param min
   *          the minimum
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if min is {@code null}
   */
  public void setMin(String min) throws RemoteException, ApiException;

  /**
   * Returnes the minimum
   * 
   * @return minimum
   * @throws RemoteException
   *           remote communication problem
   */
  public String getMin() throws RemoteException;

  /**
   * Sets the maximum
   * 
   * @param max
   *          the maximum
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if max is {@code null}
   */
  public void setMax(String max) throws RemoteException, ApiException;

  /**
   * Returnes the maximum
   * 
   * @return maximum
   * @throws RemoteException
   *           remote communication problem
   */
  public String getMax() throws RemoteException;

  /**
   * Sets the channel
   * 
   * @param channel
   *          the channel
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if channel is {@code null}
   */
  public void setChannel(String channel) throws RemoteException, ApiException;

  /**
   * Returnes the channel
   * 
   * @return channel
   * @throws RemoteException
   *           remote communication problem
   */
  public String getChannel() throws RemoteException;

}
