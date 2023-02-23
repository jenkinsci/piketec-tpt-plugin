/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2022 PikeTec GmbH
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
package com.piketec.tpt.api.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An object of this class is returned when WalkIterator is used.
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 * @param <R>
 *          Root class, owner of group.
 * @param <G>
 *          Group class.
 * @param <E>
 *          Elements class.
 *
 */
public class WalkResult<R, G, E> implements Iterable<Object>, Serializable {

  private static final long serialVersionUID = 1L;

  private R root;

  private List<G> groups;

  private List<E> elements;

  /**
   * @param root
   *          Element where the search started.
   * @param groups
   *          List of groups under the root.
   * @param elements
   *          List of elements under the root.
   */
  public WalkResult(R root, List<G> groups, List<E> elements) {
    this.root = root;
    this.groups = groups;
    this.elements = elements;
  }

  /**
   * @return Returns root node.
   */
  public R getRoot() {
    return this.root;
  }

  /**
   * @return Returns the list of groups underlying the root.
   */
  public List<G> getGroups() {
    return this.groups;
  }

  /**
   * @return Returns the list of elements underlying the root.
   */
  public List<E> getElements() {
    return this.elements;
  }

  @Override
  public Iterator<Object> iterator() {
    List<Object> l = new ArrayList<>();
    l.add(root);
    l.add(groups);
    l.add(elements);
    return l.iterator();
  }
}
