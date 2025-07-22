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
package com.piketec.tpt.api.util;

import java.rmi.RemoteException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.piketec.tpt.api.AccessCollection;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.RemoteCollection;

/**
 * collection that points directly to a corresponding (remote) collection in TPT
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 *
 * @param <E>
 *          element type
 * @param <X>
 *          inner remote collection type
 */
public class RemoteCollectionImpl<E, X extends AccessCollection<E>> extends AbstractCollection<E>
    implements RemoteCollection<E> {

  private static final long serialVersionUID = 2L;

  final X delegate;

  /**
   * creates a collection as a wrapper for a remote collection which lives inside TPT. This
   * constructor is not intended to be used by API users.
   * 
   * @param delegate
   *          the remote collection which lives inside TPT
   */
  public RemoteCollectionImpl(X delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof RemoteCollectionImpl) {
      RemoteCollectionImpl oc = (RemoteCollectionImpl)other;
      return oc.delegate.equals(delegate);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Returns all items in a copy of this collection. Any change to the returned collection is
   * <i>local</i> and will not be sent to TPT.<br>
   * 
   * @return all items of this <code>RemoteCollection</code> at once.
   */
  @Override
  public Collection<E> getItems() {
    return new ArrayList<>(this);
  }

  @Override
  public Iterator<E> iterator() {
    try {
      return new RemoteIterator<>(delegate.remoteIterator());
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public Iterator<E> remoteIterator() {
    return iterator();
  }

  @Override
  public RemoteCollection<E> asIterable() {
    return this;
  }

  /**
   * Delete an element from the list. This function directly deletes the corresponding
   * <code>element</code> in TPT.
   * <p>
   * 
   * If multiple {@link RemoteCollectionImpl}s refer to the same collection in TPT, the TPT object
   * will be deleted from the collection as soon as this method is called with any of those and the
   * change is reflected in all of them.
   * </p>
   * 
   * @param element
   *          The element to remove.
   */
  @Override
  public void delete(E element) {
    try {
      delegate.delete(element);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  /**
   * @return number of elements in this collection
   */
  @Override
  public int size() {
    try {
      return delegate.size();
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  /**
   * @return true if this collection contains no elements
   */
  @Override
  public boolean isEmpty() {
    try {
      return delegate.isEmpty();
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  /**
   * Returns <code>true</code> if this collection contains the specified element.
   *
   * @param o
   *          element whose presence in this collection is to be tested
   * @return <code>true</code> if this collection contains the specified element
   */
  @Override
  public boolean contains(Object o) {
    try {
      return delegate.contains(o);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  /**
   * Returns <code>true</code> if this collection contains all the elements in the given collection.
   *
   * @param c
   *          collection of elements whose presence in this collection is to be tested
   * @return <code>true</code>e if this collection contains all the specified elements
   */
  @Override
  public boolean containsAll(Collection< ? > c) {
    try {
      return delegate.containsAll(c);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }

  }

  /**
   * Removes all of this collection's elements that are contained in the given collection.
   *
   * @param c
   *          collection containing elements to be removed from this collection
   * @return <code>true</code> if this collection was changed as a result of the call
   */
  @Override
  public boolean deleteAll(Collection< ? > c) {
    try {
      return delegate.deleteAll(c);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  /**
   * Removes all of this collection's elements that are not contained in the specified collection.
   *
   * @param c
   *          collection containing elements to be retained in this collection
   * @return <code>true</code> if this collection was changed as a result of the call
   */
  @Override
  public boolean retainAll(Collection< ? > c) {
    try {
      return delegate.retainAll(c);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  /**
   * Remove all elements from this collection.
   */
  @Override
  public void clear() {
    try {
      delegate.clear();
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

}
