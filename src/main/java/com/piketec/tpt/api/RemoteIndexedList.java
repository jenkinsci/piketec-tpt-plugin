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

import com.piketec.tpt.api.util.IterableRemoteIndexedList;

/**
 * A list of Objects that supports fast access to elements based on an index.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 * 
 * @param <KEY>
 *          the type of keys of this indexed list
 * @param <E>
 *          the type of elements in this indexed list
 */
public interface RemoteIndexedList<KEY, E> extends RemoteList<E> {

  /**
   * Returns the value object for the given key or <code>null</code> if no such value exists.
   * 
   * @param key
   *          the key whose associated value is to be returned
   * @return the value object for the given key
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public E get(KEY key) throws RemoteException;

  /**
   * Returns the key that belongs to the given value object.
   * 
   * @param obj
   *          the value object whose key is to be returned
   * @return the key that belongs to the given value object
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public KEY getKey(E obj) throws RemoteException;

  /**
   * Removes the object with the given key from the list.
   * 
   * @param key
   *          the key whose associated value is to be removed
   * @return the removed object with the specified key
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public E removeByKey(KEY key) throws RemoteException;

  /**
   * Returns <code>true</code> if this list contains an object with the given key.
   *
   * @param key
   *          the key whose presence in this list is to be tested
   * @return <code>true</code> if this list contains an object for the specified key
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean containsKey(KEY key) throws RemoteException;

  /**
   * Returns a set of the keys contained in this list.
   * 
   * @return set of keys
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<KEY> keySet() throws RemoteException;

  @Override
  IterableRemoteIndexedList<KEY, E> asIterable() throws RemoteException;

}
