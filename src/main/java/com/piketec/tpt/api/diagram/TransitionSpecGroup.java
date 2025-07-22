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

import com.piketec.tpt.api.AccessList;
import com.piketec.tpt.api.RemoteList;

/**
 * The object represents a group of {@link TransitionSpec TransitionSpecs} and
 * {@link TransitionSpecGroup TransitionSpecGroups} that forms a recursive tree.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface TransitionSpecGroup
    extends AccessList<TransitionSpecOrGroup>, TransitionSpecOrGroup {

  /**
   * Returns the list of {@link TransitionSpec transition specifications} or
   * {@link TransitionSpecGroup specification groups} defined in this group.
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
  public RemoteList<TransitionSpecOrGroup> getTransitionSpecsOrGroups() throws RemoteException;

}
