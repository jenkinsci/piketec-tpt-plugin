package com.piketec.tpt.api.cplatform;

import java.rmi.RemoteException;

import com.piketec.tpt.api.IdentifiableRemote;

/** Represents a key value pair of the macro defines in the C\C++-platform */
public interface CCodeDefine extends IdentifiableRemote {

  /**
   * @return The name of this definition
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getName() throws RemoteException;

  /**
   * @return The value of this definition
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDefinition() throws RemoteException;

  /**
   * @param definition
   *          The new definition
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDefinition(String definition) throws RemoteException;

}
