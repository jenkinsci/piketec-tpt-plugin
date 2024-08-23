package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

/**
 * This {@link ConditionTreeNode} is used to define variables.
 */
public interface SetSignalRuleNode extends ConditionTreeNode {

  /**
   * Returns the name of the signal
   * 
   * @return The name of the signal
   * @throws RemoteException
   *           remote communication problem
   */
  public String getName() throws RemoteException;

  /**
   * Sets the name of the signal.
   * 
   * @param name
   *          the name of the signal
   * @throws RemoteException
   *           remote communication problem
   */
  public void setName(String name) throws RemoteException;

  /**
   * Returns the definition of the signal.
   * 
   * @return the definition of the signal
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDefinition() throws RemoteException;

  /**
   * Sets the definition of the signal.
   * 
   * @param definition
   *          the definition of the signal.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDefinition(String definition) throws RemoteException;

}
