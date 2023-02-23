package com.piketec.tpt.api;

import java.rmi.RemoteException;

/**
 * For test case attributes, that support a list of options, this class represents a single option
 * in such list.
 * 
 * Note that this is only relevant for test case attriutes of type <code>ENUM_ONE</code> or
 * <code>ENUM_MANY</code>.
 *
 */
public interface TestCaseAttributeOption extends IdentifiableRemote {

  /**
   * change option name
   * 
   * @param name
   *          the new option name
   * @throws RemoteException
   *           remote communication problem
   */
  void setName(String name) throws RemoteException;

  /**
   * Get the name of this option
   * 
   * @return option name
   * @throws RemoteException
   *           remote communication problem
   */
  String getName() throws RemoteException;
}
