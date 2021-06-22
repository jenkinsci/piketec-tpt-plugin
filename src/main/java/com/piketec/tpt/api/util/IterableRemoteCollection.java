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
package com.piketec.tpt.api.util;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.Collection;
import java.util.Iterator;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.RemoteCollection;

/**
 * A wrapper obejct to provide iterable functionallity for a {@link RemoteCollection}. Since all
 * methods of a {@link Remote} interface <i>must</i> throw a {@link RemoteException} we cannot
 * implement {@link Iterable} directly.<br>
 * This wrapper has the same interface as a <code>RemoteCollection</code> and additionally
 * implements the {@link Iterable} interface. Since the methods of the latter do not throw
 * <code>RemoteException</code> this Object <i>cannot</i> be exported as a {@link RemoteObject}.
 * 
 * @author Copyright (c) 2014-2021 Piketec GmbH - MIT License (MIT) - All rights reserved
 * 
 * @param <T>
 *          the type of elements in this collection
 */
public class IterableRemoteCollection<T> implements RemoteCollection<T>, Iterable<T>, Serializable {

  private static final long serialVersionUID = 1L;

  private final RemoteCollection<T> delegate;

  /**
   * An iterable view on a <code>RemoteCollection</code>. Most likely not needed by API users.
   * 
   * @param delegate
   *          The remote list for which an iterable view is needed.
   * 
   * @see RemoteCollection#asIterable()
   */
  public IterableRemoteCollection(RemoteCollection<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  public RemoteIterator<T> remoteIterator() throws RemoteException {
    return delegate.remoteIterator();
  }

  @Override
  public Collection<T> getItems() throws ApiException, RemoteException {
    return delegate.getItems();
  }

  @Override
  public void delete(T element) throws ApiException, RemoteException {
    delegate.delete(element);
  }

  @Override
  public Iterator<T> iterator() {
    try {
      return new ApiIterator<>(remoteIterator());
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public IterableRemoteCollection<T> asIterable() throws RemoteException {
    return this;
  }

  @Override
  public int size() throws RemoteException {
    return delegate.size();
  }

  @Override
  public boolean isEmpty() throws RemoteException {
    return delegate.isEmpty();
  }

  @Override
  public boolean contains(T o) throws RemoteException {
    return delegate.contains(o);
  }

  @Override
  public boolean containsAll(Collection< ? extends T> c) throws RemoteException {
    return delegate.containsAll(c);
  }

  @Override
  public boolean deleteAll(Collection< ? > c) throws RemoteException {
    return delegate.deleteAll(c);
  }

  @Override
  public boolean retainAll(Collection< ? > c) throws RemoteException {
    return delegate.retainAll(c);
  }

  @Override
  public void clear() throws RemoteException {
    delegate.clear();
  }

}
