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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import com.piketec.tpt.api.AccessList;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.RemoteList;

/**
 * collection that points directly to a corresponding (remote) collection in TPT
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 *
 * @param <E>
 *          element type
 * @param <X>
 *          inner remote list type
 */
public class RemoteListImpl<E, X extends AccessList<E>> extends RemoteCollectionImpl<E, X>
    implements RemoteList<E> {

  private static final long serialVersionUID = 2L;

  /**
   * creates a collection as a wrapper for a remote collection which lives inside TPT. This
   * constructor is not intended to be used by API users.
   * 
   * @param delegate
   *          the remote collection which lives inside TPT
   */
  public RemoteListImpl(X delegate) {
    super(delegate);
  }

  @Override
  public E get(int index) {
    try {
      return delegate.get(index);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public int indexOf(Object o) {
    try {
      return delegate.indexOf(o);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public int lastIndexOf(Object o) {
    try {
      return delegate.lastIndexOf(o);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public E remove(int index) {
    try {
      E r = delegate.get(index);
      delegate.delete(index);
      return r;
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public void delete(int index) {
    try {
      delegate.delete(index);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public E move(int from, int to) throws IndexOutOfBoundsException {
    try {
      return delegate.move(from, to);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  // ----------- unsupported: -----------

  @Override
  public void add(int index, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(int index, Collection< ? extends E> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public E set(int index, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator<E> listIterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<E> getItems() {
    return new ArrayList<>(this);
  }

  @Override
  public RemoteList<E> asIterable() {
    return this;
  }

}
