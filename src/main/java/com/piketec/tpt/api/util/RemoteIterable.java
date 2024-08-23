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
package com.piketec.tpt.api.util;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Iterator;

/**
 * An interface to provide iterable functionallity for remote objects. Since all methods of a
 * {@link Remote} interface <i>must</i> throw a {@link RemoteException} we cannot implement
 * {@link Iterable} directly.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 * 
 * @param <T>
 *          the type of elements in this collection
 */
public interface RemoteIterable<T> extends Remote {

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
  RemoteIterator<T> remoteIterator() throws RemoteException;

  /**
   * Provides an {@link Iterable} view of this <code>RemoteIterable</code>. This normally uses
   * {@link #remoteIterator()} and wraps the return value into a real {@link Iterator} that throws
   * {@link RuntimeException RuntimeExceptions} instead of <code>RemoteExceptions</code> so that
   * this view can be used in for-each loops.
   * 
   * @see ApiIterator
   * 
   * @return an iterable view of this <code>RemoteIterable</code>
   * @throws RemoteException
   *           remote communication problem
   */
  Iterable<T> asIterable() throws RemoteException;

}
