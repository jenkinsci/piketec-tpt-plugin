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
package com.piketec.tpt.api;

import java.rmi.RemoteException;

/**
 * An object representing either an {@link Assessment} or a group of assessments
 * ({@link AssessmentGroup}). These objects can build up a tree where both, assessments and
 * assessment groups, could be leaf nodes.
 *
 * @author Copyright (c) 2014-2021 Piketec GmbH - MIT License (MIT) - All rights reserved
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
   */
  public AssessmentGroup getGroup() throws RemoteException;

  /**
   * @return The parent TPT {@link Project} for this <code>AssessmentOrGroup</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Project getProject() throws RemoteException;

  /**
   * @return the assessment or assessment group ID.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the ID of the assessment is not an integer
   * 
   * @deprecated Will be removed in TPT-18. Since TPT-16 assessment IDs are strings. Use
   *             {@link #getIdString()} instead.
   */
  @Deprecated
  public int getId() throws RemoteException, ApiException;

  /**
   * @return the assessment or assessment group ID.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getIdString() throws RemoteException;

  /**
   * Set the status of the assessment or group.
   * 
   * @param author
   *          The author of the status. <code>Null</code> will be reduced to an empty string.
   * @param comment
   *          The comment of the status. <code>Null</code> will be reduced to an empty string.
   * @param status
   *          The status type of the status.
   * @throws ApiException
   *           If <code>auhor</code> contains line break or the given <code>status</code> is
   *           invalid.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setStatus(String author, String comment, String status)
      throws ApiException, RemoteException;

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

  /**
   * Copies <code>this</code> into the given <code>targetGroup</code> that can be from a different
   * {@link Project} that is opened in the same TPT instance. If the <code>targetGroup</code>
   * already contains an element with the same name a new one will be generated.
   * 
   * @param targetGroup
   *          The group to copy <code>this</code> into. Can be from another <code>Project</code>.
   * @param targetIndex
   *          The index where the copy will be inserted. Use {@link Integer#MAX_VALUE} to append the
   *          copy at the end.
   * @return The copy of this and all log messages that occured during copying.
   * @throws ApiException
   *           If targetGroup is <code>null</code> or copying failed.
   * @throws RemoteException
   *           remote communication problem
   */
  public ResultAndLogs<AssessmentOrGroup> copy(AssessmentOwner targetGroup, int targetIndex)
      throws ApiException, RemoteException;
}
