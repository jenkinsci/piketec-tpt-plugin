/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2024 Synopsys Inc.
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
package com.piketec.tpt.api;

import java.rmi.RemoteException;

import com.piketec.tpt.api.util.UUIDObject;

/**
 * An object representing either an {@link ExecutionConfiguration} or a group of execution
 * configurations ({@link ExecutionConfigurationGroup}). These objects can build up a tree where
 * both, execution configurations and execution configuration groups, could be leaf nodes.
 *
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface ExecutionConfigurationOrGroup extends NamedObject, IdentifiableRemote, UUIDObject {

  /**
   * Get the parent execution configuration group or <code>null</code> if this object resides on the
   * top level (meaning directly below the {@link Project}).
   * 
   * @return the parent group or <code>null</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public ExecutionConfigurationGroup getGroup() throws RemoteException;

  /**
   * Returns <code>true</code> if this is a {@link ExecutionConfigurationGroup}, <code>false</code>
   * otherwise.
   * 
   * @return <code>true</code> if this is a {@link ExecutionConfigurationGroup}, <code>false</code>
   *         otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isGroup() throws RemoteException;

  /**
   * Moves this {@link ExecutionConfigurationOrGroup} to a new position in the execution
   * configuration tree.
   * 
   * @param newParent
   *          the new parent node or <code>null</code> to move it to top level.
   * @param index
   *          the new position under the new parent.
   * @throws ApiException
   *           If the new parent is invalid.
   * @throws RemoteException
   *           remote communication problem
   */
  public void move(ExecutionConfigurationGroup newParent, int index)
      throws ApiException, RemoteException;

  /**
   * Copies <code>this</code> into the given <code>targetGroup</code> that can be from a different
   * {@link Project} that is opened in the same TPT instance. If the <code>targetGroup</code>
   * already contains an element with the same name a new one will be generated.
   * 
   * @param targetGroupOrProject
   *          The group or project to copy <code>this</code> into. The target can be from another
   *          <code>Project</code>.
   * @param targetIndex
   *          The index where the copy will be inserted. Use {@link Integer#MAX_VALUE} to append the
   *          copy at the end.
   * @return The copy of this and all log messages that occurred during copying.
   * @throws ApiException
   *           If targetGroup is <code>null</code> or copying failed.
   * @throws RemoteException
   *           remote communication problem
   */
  public ResultAndLogs<ExecutionConfigurationOrGroup> copy(ExecutionConfigurationOwner targetGroupOrProject,
                                                           int targetIndex)
      throws ApiException, RemoteException;
}
