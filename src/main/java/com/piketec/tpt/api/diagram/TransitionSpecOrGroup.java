/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2025 Synopsys Inc.
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
import com.piketec.tpt.api.IdentifiableRemote;
import com.piketec.tpt.api.NamedObject;
import com.piketec.tpt.api.util.UUIDObject;

/**
 * <code>TransitionSpecOrGroup</code> represents a tree structure. Nodes are
 * {@link TransitionSpecGroup}, leaves are {@link TransitionSpec}.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface TransitionSpecOrGroup extends IdentifiableRemote, NamedObject, UUIDObject {

  /**
   * Get the current name of this {@link TransitionSpecOrGroup}.
   * 
   * @return the current name of this {@link TransitionSpecOrGroup}.
   * 
   * @throws RemoteException
   *           remote communication error
   */
  @Override
  public String getName() throws RemoteException;

  /**
   * Set a new name of this {@link TransitionSpecOrGroup}.
   * 
   * @param newName
   *          the new name
   * @throws RemoteException
   *           remote communication error
   */
  @Override
  public void setName(String newName) throws RemoteException;

  /**
   * @return Returns the UUID of the {@link TransitionSpecOrGroup}.
   * @throws RemoteException
   *           remote communication problem
   */
  @Override
  public String getUUIDString() throws RemoteException;

  /**
   * Returns a {@link TransitionSpecGroup} if the object is a child object or <code>null</code> if
   * the object is directly contained by the {@link Transition}.
   *
   * @return The parent group or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TransitionSpecGroup getGroup() throws RemoteException;

  /**
   * Returns <code>true</code> if this is a {@link TransitionSpecGroup}, <code>false</code>
   * otherwise.
   * 
   * @return <code>true</code> if this is a {@link TransitionSpecGroup}, <code>false</code>
   *         otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isGroup() throws RemoteException;

  /**
   * Returns the {@link Transition} object which directly or indirectly contains this
   * <code>TransitionSpecOrGroup</code>.
   * 
   * @return The parent <code>Transition</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Transition getTransition() throws RemoteException;

  /**
   * Moves this {@link TransitionSpecOrGroup} to a new position in the transition spec tree.
   * 
   * @param newParent
   *          the new parent {@link TransitionSpecGroup} or <code>null</code> to move it to top
   *          level.
   * @param index
   *          the new position under the new parent.
   * 
   * @throws ApiException
   *           If the new parent is invalid.
   * @throws RemoteException
   *           remote communication problem
   */
  public void move(TransitionSpecGroup newParent, int index) throws ApiException, RemoteException;
}
