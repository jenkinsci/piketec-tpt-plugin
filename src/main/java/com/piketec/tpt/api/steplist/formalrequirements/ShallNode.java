package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * This {@link ConditionTreeNode} specifies the expected behavior of a signal. The step should be
 * indented after a restriction type step or event type step. The behavior of the Shall step differs
 * depending on the step type is it indented after.
 */
public interface ShallNode extends GeneralShallNode {

  /**
   * Sets the expression which will be checked.
   * 
   * @param expr
   *          the expression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the expr is {@code null}
   */
  public void setExpression(String expr) throws RemoteException, ApiException;

  /**
   * The expression which will be checked.
   * 
   * @return the expression which will be checked.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getExpression() throws RemoteException;

}
