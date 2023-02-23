package com.piketec.tpt.api.util;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.RemoteCollection;
import com.piketec.tpt.api.RemoteIndexedList;

/**
 * A wrapper obejct to provide iterable functionallity for a {@link RemoteIndexedList}. Since all
 * methods of a {@link Remote} interface <i>must</i> throw a {@link RemoteException} we cannot
 * implement {@link Iterable} directly.<br>
 * This wrapper has the same interface as a <code>RemoteCollection</code> and additionally
 * implements the {@link Iterable} interface. Since the methods of the latter do not throw
 * <code>RemoteException</code> this Object <i>cannot</i> be exported as a {@link RemoteObject}.
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 * 
 * @param <KEY>
 *          the type of the keys of this indexed list
 * @param <T>
 *          the type of elements in this indexed list
 */
public class IterableRemoteIndexedList<KEY, T> extends IterableRemoteList<T>
    implements RemoteIndexedList<KEY, T> {

  private static final long serialVersionUID = 1L;

  private final RemoteIndexedList<KEY, T> delegate;

  /**
   * An iterable view on a <code>RemoteIndexedList</code>. Most likely not needed by API users.
   * 
   * @param delegate
   *          The remote indexed list for which an iterable view is needed.
   * 
   * @see RemoteIndexedList#asIterable()
   */
  public IterableRemoteIndexedList(RemoteIndexedList<KEY, T> delegate) {
    super(delegate);
    this.delegate = delegate;
  }

  @Override
  public T get(KEY key) throws ApiException, RemoteException {
    return delegate.get(key);
  }

  @Override
  public KEY getKey(T obj) throws RemoteException {
    return delegate.getKey(obj);
  }

  @Override
  public T removeByKey(KEY key) throws RemoteException {
    return delegate.removeByKey(key);
  }

  @Override
  public boolean containsKey(KEY key) throws RemoteException {
    return delegate.containsKey(key);
  }

  @Override
  public RemoteCollection<KEY> keySet() throws RemoteException {
    return delegate.keySet();
  }

  @Override
  public IterableRemoteIndexedList<KEY, T> asIterable() throws RemoteException {
    return this;
  }

}
