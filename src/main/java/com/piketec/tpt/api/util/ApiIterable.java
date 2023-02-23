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
import java.rmi.RemoteException;
import java.util.Iterator;

import com.piketec.tpt.api.ApiException;

/**
 * A wrapper for a {@link RemoteIterable} that will catch all {@link RemoteException
 * RemoteExceptions} and rethrows them as {@link ApiException ApiExceptions}.
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 *
 * @param <E>
 *          the type of elements returned by this iterable
 * 
 */
public class ApiIterable<E> implements Serializable, Iterable<E> {

  private static final long serialVersionUID = 1L;

  private final RemoteIterable<E> remoteIterable;

  /**
   * Creates a new <code>ApiIterable</code>.
   * 
   * @param delegate
   *          The <code>RemoteIterable</code> where the method calls will be delgated to.
   */
  public ApiIterable(RemoteIterable<E> delegate) {
    this.remoteIterable = delegate;
  }

  @Override
  public Iterator<E> iterator() {
    try {
      return new ApiIterator<>(remoteIterable.remoteIterator());
    } catch (RemoteException e) {
      throw new ApiException(e);
    }
  }

}
