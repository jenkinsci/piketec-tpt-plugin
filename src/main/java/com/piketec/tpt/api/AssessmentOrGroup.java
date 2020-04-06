/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2020 PikeTec GmbH
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

/**
 * An object representing either an {@link Assessment} or a group of assessments
 * ({@link AssessmentGroup}). These objects can build up a tree where both, assessments and
 * assessment groups, could be leaf nodes.
 *
 * @author Copyright (c) 2014-2020 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface AssessmentOrGroup extends NamedObject, IdentifiableRemote {

  /**
   * Returns the parent group ({@link AssessmentGroup}) of this assessment or <code>null</code> if
   * this is the top level object (i.e. it resides directly below the {@link Project}).
   *
   * @return The parent {@link AssessmentGroup} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public AssessmentGroup getGroup() throws ApiException, RemoteException;

  /**
   * @return The parent TPT {@link Project} for this <code>AssessmentOrGroup</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public Project getProject() throws ApiException, RemoteException;

  /**
   * @return the assessment or assessment group ID.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public int getId() throws ApiException, RemoteException;

  /**
   * Moves this {@link AssessmentOrGroup} to a new position in the assessment tree.
   * 
   * @param newParent
   *          the new parent {@link AssessmentGroup} or <code>null</code> to move it to top level.
   * @param index
   *          the new position under the new parent.
   * @throws ApiException
   *           If the new parent is invalid.
   * @throws RemoteException
   *           remote communication problem
   */
  public void move(AssessmentGroup newParent, int index) throws ApiException, RemoteException;
}
