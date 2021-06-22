/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2021 PikeTec GmbH
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.piketec.tpt.api.diagram;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * A {@link TransitionSpec transition specification} defines, when a transition is able to fire
 * ({@link #getCondition()}) and which actions/stimulations have to be performed if the transition
 * fires ({@link #getActions()}).
 */
public interface TransitionSpec extends TransitionSpecOrGroup {

  /**
   * Returns the precondition that has to be satisfied for the transition to fire.
   * 
   * @return The formal precondition as {@link String}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCondition() throws RemoteException;

  /**
   * Set the formal precondition to be satisfied for the transition to fire. For a syntax reference
   * refer to the TPT User Guide, Section "Set a Transition Action"
   * 
   * @param condition
   *          The formal condition as {@link String}. <code>Null</code> will be reduced to an empty
   *          string.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCondition(String condition) throws RemoteException;

  /**
   * Returns the actions to be executed when a transition fires.
   * 
   * @return The description of the actions as {@link String}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getActions() throws RemoteException;

  /**
   * Set the actions to be executed when a transition is executed.For a Syntax reference refer to
   * the TPT User Guide, Section "Set a Transition Action"
   * 
   * @param actions
   *          The actions as {@link String}. <code>Null</code> will be reduced to an empty string.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setActions(String actions) throws RemoteException;

  /**
   * Returns <code>null</code> if transition specification can be compiled without errors, the
   * compile error message otherwise.
   * 
   * @return The compile error message or <code>null</code>
   * @throws ApiException
   *           If transition specification is not part of a project anymore or compiler is inactive
   *           for the project.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCompileError() throws RemoteException, ApiException;
}
