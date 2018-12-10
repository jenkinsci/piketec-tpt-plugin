/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 PikeTec GmbH
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

/**
 * <code>TransitionSpecOrGroup</code> represents a tree structure. Nodes are
 * {@link TransitionSpecGroup}, leaves are {@link TransitionSpec}.
 */
public interface TransitionSpecOrGroup extends IdentifiableRemote, NamedObject {

  /**
   * Returns a {@link TransitionSpecGroup} if the object is a child object or <code>null</code> if
   * the object is dircrectly contained by the {@link Transition}.
   *
   * @return The parent group or <code>null</code>.
   */
  public TransitionSpecGroup getGroup() throws ApiException, RemoteException;

  /**
   * Returns the {@link Transition} object which directly or indirectly contains this
   * <code>TransitionSpecOrGroup</code>.
   * 
   * @return The parent <code>Transition</code>
   */
  public Transition getTransition() throws ApiException, RemoteException;
}
