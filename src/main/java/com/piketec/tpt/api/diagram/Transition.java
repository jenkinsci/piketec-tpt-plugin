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

import java.awt.Point;
import java.rmi.RemoteException;

import com.piketec.tpt.api.IdentifiableRemote;
import com.piketec.tpt.api.NamedObject;
import com.piketec.tpt.api.RemoteList;

/**
 * This object represents the graphical transition between two {@link DiagramNode DiagramNodes}.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface Transition extends NamedObject, IdentifiableRemote {

  /**
   * Get the current name of this {@link Transition}.
   * 
   * @return the current name of this {@link Transition}.
   * 
   * @throws RemoteException
   *           remote communication error
   */
  @Override
  public String getName() throws RemoteException;

  /**
   * Set a new name of this {@link Transition}.
   * 
   * @param newName
   *          the new name
   * @throws RemoteException
   *           remote communication error
   */
  @Override
  public void setName(String newName) throws RemoteException;

  /**
   * @return The starting diagram node of the <code>Transition</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public DiagramNode getFrom() throws RemoteException;

  /**
   * @return The end diagram node of the <code>Transition</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public DiagramNode getTo() throws RemoteException;

  /**
   * Set starting diagram node for this <code>Transition</code>
   * 
   * @param n
   *          The new starting point
   * 
   * @throws RemoteException
   *           If <code>n</code> does not belong to the TPT instance represented by the API object.
   */
  public void setFrom(DiagramNode n) throws RemoteException;

  /**
   * Set end diagram node for this <code>Transition</code>
   * 
   * @param n
   *          The new end point
   * 
   * @throws RemoteException
   *           If <code>n</code> does not belong to the TPT instance represented by the API object.
   */
  public void setTo(DiagramNode n) throws RemoteException;

  /**
   * Returns a list of auxiliary points for this transition that are used to define a curvy shape
   * instead of a straight line for this transition.
   * 
   * @return List of auxiliary {@link Point points}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<Point> getAuxPositions() throws RemoteException;

  /**
   * Adds a new entry in the list of auxiliary points for this transition.
   *
   * @param p
   *          The new auxiliary point
   * @param index
   *          The position of the new auxiliary point in the list.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void addAuxPoint(Point p, int index) throws RemoteException;

  /**
   * Adds a new entry in the list of auxiliary points for this transition.
   *
   * @param x
   *          The x coordinate of the new auxiliary point
   * @param y
   *          The y coordinate of the new auxiliary point
   * @param index
   *          The position of the new auxiliary point in the list.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void addAuxPoint(int x, int y, int index) throws RemoteException;

  /**
   * Returns the list of {@link TransitionSpec transition specifications} or
   * {@link TransitionSpecGroup specification groups} directly assigned to this transition.
   * <p>
   * Since it can be either specifications or groups of specifications, it returns a list of
   * {@link TransitionSpecOrGroup} that might represent a tree of specifications.
   * </p>
   * 
   * @return The list of transition specifications or specification groups.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<TransitionSpecOrGroup> getTopLevelTransitionSpecOrGroup()
      throws RemoteException;

  /**
   * Create a new {@link TransitionSpec}. The new specification is either added to a given group,
   * or, if no group given, directly to the list of specifications for this transition.
   *
   * @param name
   *          The name for the new {@link TransitionSpec}
   * @param groupOrNull
   *          The {@link TransitionSpecGroup}, where the newly created specification shall be added
   *          or <code>null</code> if it shall be directly added to the list of specifications of
   *          this transition.
   * @return The newly created {@link TransitionSpec}.
   * 
   * @throws RemoteException
   *           If <code>groupOrNull</code> does not belong to the TPT instance represented by the
   *           API object.
   */
  public TransitionSpec createTransitionSpec(String name, TransitionSpecGroup groupOrNull)
      throws RemoteException;

  /**
   * Create a new {@link TransitionSpecGroup}. The new group is either added to a given group, or,
   * if no group given, directly to the list of specifications for this transition.
   *
   * @param name
   *          The name for the new {@link TransitionSpecGroup}
   * @param groupOrNull
   *          The {@link TransitionSpecGroup}, where the newly created specification shall be added
   *          or <code>null</code> if it shall be directly added to the list of specifications of
   *          this transition.
   * @return The newly created {@link TransitionSpecGroup}.
   * 
   * @throws RemoteException
   *           If <code>groupOrNull</code> does not belong to the TPT instance represented by the
   *           API object.
   */
  public TransitionSpecGroup createTransitionSpecGroup(String name, TransitionSpecGroup groupOrNull)
      throws RemoteException;

}
