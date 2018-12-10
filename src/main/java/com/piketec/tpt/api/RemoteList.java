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
package com.piketec.tpt.api;

import java.rmi.RemoteException;

/**
 * A sorted set of (Remote) elements. Changes to this list will be done directly in TPT.
 */
public interface RemoteList<E> extends RemoteCollection<E> {

  /**
   * Returns the element at position <code>index</code>.
   * 
   * @param index
   *          The position of the element in the list.
   * @return The element that is at this position.
   * @throws IndexOutOfBoundsException
   *           If the <code>index &lt; 0</code> or <code>index &gt;= getItems().size()</code>
   */
  public E get(int index) throws ApiException, IndexOutOfBoundsException, RemoteException;

  /**
   * Removes the element at position <code>index</code> from the list.
   * 
   * @param index
   *          The position of the item that should be deleted.
   * @throws IndexOutOfBoundsException
   *           If the <code>index &lt; 0</code> or <code>index &gt;= getItems().size()</code>
   */
  public void delete(int index) throws ApiException, IndexOutOfBoundsException, RemoteException;

  /**
   * Moves an element from the position <code>from</code> to <code>to</code>. All elements in this
   * interval will be shifted accordingly such that no gaps exist afterwards and no items will be
   * overwritten.
   * 
   * @param from
   *          The old position of the element.
   * @param to
   *          The new position for the element given by <code>from</code>
   * @return Returns a referens to the moved item.
   * @throws IndexOutOfBoundsException
   *           If <code>to &lt; 0 </code> or <code>from &lt; 0</code> or
   *           <code>to &gt;= getItems().size()</code> or <code>from &gt;= getItems().size()</code>
   */
  public E move(int from, int to) throws ApiException, IndexOutOfBoundsException, RemoteException;

}
