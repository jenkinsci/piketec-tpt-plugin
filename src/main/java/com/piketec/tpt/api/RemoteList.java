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

import com.piketec.tpt.api.util.IterableRemoteList;

/**
 * An ordered collection of (Remote) elements. Changes to this list will be done directly in TPT.
 * 
 * @param <E>
 *          the type of elements in this list
 */
public interface RemoteList<E> extends RemoteCollection<E> {

  /**
   * Returns the element at position <code>index</code>.
   * 
   * @param index
   *          The position of the element in the list.
   * @return The element that is at this position.
   * 
   * @throws IndexOutOfBoundsException
   *           If the <code>index &lt; 0</code> or <code>index &gt;= getItems().size()</code>
   * @throws RemoteException
   *           remote communication problem
   */
  public E get(int index) throws IndexOutOfBoundsException, RemoteException;

  /**
   * Removes the element at position <code>index</code> from the list.
   * 
   * @param index
   *          The position of the item that should be deleted.
   * 
   * @throws IndexOutOfBoundsException
   *           If the <code>index &lt; 0</code> or <code>index &gt;= getItems().size()</code>
   * @throws RemoteException
   *           remote communication problem
   */
  public void delete(int index) throws IndexOutOfBoundsException, RemoteException;

  /**
   * Moves an element from the position <code>from</code> to <code>to</code>. All elements in this
   * interval will be shifted accordingly such that no gaps exist afterwards and no items will be
   * overwritten.
   * 
   * @param from
   *          The old position of the element.
   * @param to
   *          The new position for the element given by <code>from</code>
   * @return Returns a reference to the moved item.
   * 
   * @throws IndexOutOfBoundsException
   *           If <code>to &lt; 0 </code> or <code>from &lt; 0</code> or
   *           <code>to &gt;= getItems().size()</code> or <code>from &gt;= getItems().size()</code>
   * @throws RemoteException
   *           remote communication problem
   */
  public E move(int from, int to) throws IndexOutOfBoundsException, RemoteException;

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this
   * list does not contain the element.More formally, returns the lowest index i such that
   * Objects.equals(o, get(i)), or -1 if there is no such index.
   * 
   * @param element
   *          element to search for
   * @return the index of the first occurrence of the specified element in this list, or -1 if this
   *         list does not contain the element
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ClassCastException
   *           if the type of the specified element is incompatible with this list(optional)
   * @throws NullPointerException
   *           if the specified element is null and this list does not permit null
   *           elements(optional)
   */
  public int indexOf(E element) throws RemoteException, ClassCastException, NullPointerException;

  @Override
  public IterableRemoteList<E> asIterable() throws RemoteException;

}
