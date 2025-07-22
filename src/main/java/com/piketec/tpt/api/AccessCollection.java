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
package com.piketec.tpt.api;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import com.piketec.tpt.api.util.AccessIterator;
import com.piketec.tpt.api.util.RemoteIterator;

/**
 * A collection of items where changes to the items are directly performed in TPT
 * 
 * @param <E>
 *          the type of elements in this collection
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface AccessCollection<E> extends TptRemote {

  /**
   * Returns all items of this <code>RemoteCollection</code>. Any change to the returned
   * <code>Collection</code> is local and will not be sent to TPT.<br>
   * If you just want to iterate over the items use {@link #asIterable()}.
   * 
   * @return all items of this <code>RemoteCollection</code> at once.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #asIterable()
   */
  public Collection<E> getItems() throws RemoteException;

  /**
   * Delete an element from the list. This function directly deletes the corresponding
   * <code>element</code> in TPT.
   * <p>
   * 
   * If multiple <code>RemoteCollections</code> refer to the same collection in TPT, the TPT object
   * will be deleted from the collection as soon as this method is called with any of those and the
   * change is reflected in all of them.
   * </p>
   * 
   * @param element
   *          The element to remove.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void delete(E element) throws RemoteException;

  /**
   * Returns the number of elements in this collection.
   *
   * @return the number of elements in this collection
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int size() throws RemoteException;

  /**
   * Returns <code>true</code> if this collection contains no elements.
   *
   * @return {@code true} if this collection contains no elements
   *
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isEmpty() throws RemoteException;

  /**
   * Returns <code>true</code>e if this collection contains the specified element.
   *
   * @param o
   *          element whose presence in this collection is to be tested
   * @return <code>true</code>e if this collection contains the specified element
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean contains(Object o) throws RemoteException;

  /**
   * Returns <code>true</code> if this collection contains all the elements in the given collection.
   *
   * @param c
   *          collection of elements whose presence in this collection is to be tested
   * @return <code>true</code>e if this collection contains all the specified elements
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean containsAll(Collection< ? > c) throws RemoteException;

  /**
   * Removes all of this collection's elements that are contained in the given collection.
   *
   * @param c
   *          collection containing elements to be removed from this collection
   * @return <code>true</code> if this collection was changed as a result of the call
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean deleteAll(Collection< ? > c) throws RemoteException;

  /**
   * Removes all of this collection's elements that are not contained in the specified collection.
   *
   * @param c
   *          collection containing elements to be retained in this collection
   * @return <code>true</code> if this collection was changed as a result of the call
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean retainAll(Collection< ? > c) throws RemoteException;

  /**
   * Remove all elements from this collection.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void clear() throws RemoteException;

  /**
   * Provides a remote iterator that has the same interface as {@link Iterator} but every method can
   * throw a {@link RemoteException} as required by Java RMI. This method is most likely not
   * relevant for API users but needed for {@link #asIterable()}.
   * 
   * @see #asIterable()
   * 
   * @return a remote iterator
   * @throws RemoteException
   *           remote communication problem
   */
  public AccessIterator<E> remoteIterator() throws RemoteException;

  /**
   * Provides an {@link Iterable} view of this collection. This normally uses
   * {@link #remoteIterator()} and wraps the return value into a real {@link Iterator} that throws
   * {@link RuntimeException RuntimeExceptions} instead of <code>RemoteExceptions</code> so that
   * this view can be used in for-each loops.
   * 
   * @see RemoteIterator
   * 
   * @return an iterable view of this collection
   * @throws RemoteException
   *           remote communication problem
   */
  public Iterable<E> asIterable() throws RemoteException;

}
