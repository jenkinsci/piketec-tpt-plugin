package com.piketec.tpt.api;

import java.rmi.RemoteException;

import com.piketec.tpt.api.util.IterableRemoteIndexedList;

/**
 * A list of Objects that supports fast access to elements based on an index.
 * 
 * @author Copyright (c) 2014-2021 Piketec GmbH - MIT License (MIT) - All rights reserved
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
