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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.RemoteList;

/**
 * A wrapper obejct to provide iterable functionallity for a {@link RemoteList}. Since all methods
 * of a {@link Remote} interface <i>must</i> throw a {@link RemoteException} we cannot implement
 * {@link Iterable} directly.<br>
 * This wrapper has the same interface as a <code>RemoteCollection</code> and additionally
 * implements the {@link Iterable} interface. Since the methods of the latter do not throw
 * <code>RemoteException</code> this Object <i>cannot</i> be exported as a {@link RemoteObject}.
 * 
 * @author Copyright (c) 2014-2021 Piketec GmbH - MIT License (MIT) - All rights reserved
 * 
 * @param <T>
 *          the type of elements in this list
 */
public class IterableRemoteList<T> extends IterableRemoteCollection<T> implements RemoteList<T> {

  private static final long serialVersionUID = 1L;

  private final RemoteList<T> delegate;

  /**
   * An iterable view on a <code>RemoteList</code>. Most likely not needed by API users.
   * 
   * @param delegate
   *          The remote list for which an iterable view is needed.
   * 
   * @see RemoteList#asIterable()
   */
  public IterableRemoteList(RemoteList<T> delegate) {
    super(delegate);
    this.delegate = delegate;
  }

  @Override
  public T get(int index) throws ApiException, IndexOutOfBoundsException, RemoteException {
    return delegate.get(index);
  }

  @Override
  public void delete(int index) throws ApiException, IndexOutOfBoundsException, RemoteException {
    delegate.delete(index);
  }

  @Override
  public T move(int from, int to) throws ApiException, IndexOutOfBoundsException, RemoteException {
    return delegate.move(from, to);
  }

  @Override
  public IterableRemoteList<T> asIterable() throws RemoteException {
    return this;
  }

}
