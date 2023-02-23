package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

import com.piketec.tpt.api.TptRemote;
import com.piketec.tpt.api.Type;

/**
 * Argument for {@link ServiceStep}
 *
 */
public interface ServiceStepArgument extends TptRemote {

  /**
   * Delivers the {@link Type} of a service step argument.
   * 
   * @return {@link Type} of a service step argument.
   * @throws RemoteException
   *           remote communication problem
   */
  public Type getType() throws RemoteException;

  /**
   * Delivers the name of a service step argument.
   * 
   * @return name of a service step argument.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getName() throws RemoteException;

  /**
   * Delivers the value of a service step argument.
   * 
   * @return value of a service step argument.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getValue() throws RemoteException;

  /**
   * Sets a value of a service step argument.
   * 
   * @param value
   *          New value for service step argument.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setValue(String value) throws RemoteException;

  /**
   * {@link ServiceStepArgument} can be mandatory for using a specific {@link ServiceStep}. This
   * function determines whether a {@link ServiceStepArgument} is mandatory or not. In case a
   * mandatory argument is not provided via TPT API, a compile error will occur for the
   * corresponding step list.
   * 
   * @return <code>true</code> if {@link ServiceStepArgument} is mandatory.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isMandatory() throws RemoteException;
}
