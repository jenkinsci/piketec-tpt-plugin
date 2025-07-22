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

import com.piketec.tpt.api.AccessIndexedList;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.RemoteCollection;
import com.piketec.tpt.api.RemoteIndexedList;

/**
 * collection that points directly to a corresponding (remote) collection in TPT
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 * @param <KEY>
 *          key type for indexed access
 * @param <E>
 *          element type
 * @param <X>
 *          inner remote list type
 */
public class RemoteIndexedListImpl<KEY, E, X extends AccessIndexedList<KEY, E>>
    extends RemoteListImpl<E, X> implements RemoteIndexedList<KEY, E> {

  private static final long serialVersionUID = 2L;

  /**
   * creates a collection as a wrapper for a remote collection which lives inside TPT. This
   * constructor is not intended to be used by API users.
   * 
   * @param delegate
   *          the remote collection which lives inside TPT
   */
  public RemoteIndexedListImpl(X delegate) {
    super(delegate);
  }

  @Override
  public boolean containsKey(KEY key) {
    try {
      return delegate.containsKey(key);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public E get(KEY key) {
    try {
      return delegate.get(key);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public KEY getKey(E obj) {
    try {
      return delegate.getKey(obj);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public RemoteCollection<KEY> keySet() {
    try {
      return new RemoteCollectionImpl<>(delegate.keySet());
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public E removeByKey(KEY key) {
    try {
      return delegate.removeByKey(key);
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

  @Override
  public RemoteIndexedList<KEY, E> asIterable() {
    return this;
  }

}
