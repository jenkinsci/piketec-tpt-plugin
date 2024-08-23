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
 * A <code>RemoteIterator</code> has the same interface as an {@link Iterator} but since all
 * {@link Remote} objetcs must throw a {@link RemoteException} every method does exactly that. A
 * <code>RemoteIterator</code> can be wrapped in a {@link ApiIterator} to get a conventional
 * <code>Iterator</code>.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 * 
 * @param <E>
 *          the type of elements returned by this iterator
 */
public interface RemoteIterator<E> extends Remote {

  /**
   * Returns <code>true</code> if the iteration has more elements, <code>false</code> otherwise.
   * 
   * @return <code>true</code> if the iteration has more elements, <code>false</code> otherwise.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see Iterator#hasNext()
   */
  public boolean hasNext() throws RemoteException;

  /**
   * Get the next element of the iteration.
   * 
   * @return the next element of the iteration.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see Iterator#next()
   */
  public E next() throws RemoteException;

  /**
   * Removes the last element returned by this iterator from the underlying collection (optional
   * operation).
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see Iterator#remove()
   */
  public void remove() throws RemoteException;

}
