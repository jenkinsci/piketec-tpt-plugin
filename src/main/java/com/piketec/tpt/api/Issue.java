package com.piketec.tpt.api;

import java.rmi.RemoteException;

/**
 * TPT API representation of an issue.
 *
 */
public interface Issue extends IdentifiableRemote {

  /**
   * Returns the status of this issue.
   * 
   * @return status of this issue
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getStatus() throws RemoteException;

  /**
   * Returns the key of this issue.
   * 
   * @return key of this issue
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getKey() throws RemoteException;

  /**
   * Returns the URL of this issue.
   * 
   * @return URL of this issue
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getURL() throws RemoteException;

}
