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

import com.piketec.tpt.api.AssessmentOrGroup;
import com.piketec.tpt.api.IdentifiableRemote;
import com.piketec.tpt.api.NamedObject;
import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.Testlet;
import com.piketec.tpt.api.util.UUIDObject;

/**
 * This interface is implemented by all element that can be connected by a transition with each
 * other.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface DiagramNode extends NamedObject, IdentifiableRemote, UUIDObject {

  /**
   * Get the current name of this {@link DiagramNode}.
   * 
   * @return the current name of this {@link DiagramNode}.
   * 
   * @throws RemoteException
   *           remote communication error
   */
  @Override
  public String getName() throws RemoteException;

  /**
   * Set a new name of this {@link AssessmentOrGroup}.
   * 
   * @param newName
   *          the new name
   * @throws RemoteException
   *           remote communication error
   */
  @Override
  public void setName(String newName) throws RemoteException;

  /**
   * @return Returns the UUID of the {@link DiagramNode}.
   * @throws RemoteException
   *           remote communication problem
   */
  @Override
  public String getUUIDString() throws RemoteException;

  /**
   * Get the parent testlet of this digram node. The top level testlet
   * {@link Project#getTopLevelTestlet() top level testlet} is the root of the testlet tree and has
   * no parent. All other testlets and diagram nodes belong to a parent testlet as long as they are
   * not removed from the model.
   * 
   * @return The parent testlet or <code>null</code> if the diagram node is the top level testlet or
   *         not part of a model.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Testlet getParentTestlet() throws RemoteException;

}
