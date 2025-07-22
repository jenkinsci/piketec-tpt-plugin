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
package com.piketec.tpt.api;

/**
 * A list of Objects that supports fast access to elements based on an index.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 * 
 * @param <KEY>
 *          the type of keys of this indexed list
 * @param <E>
 *          the type of elements in this indexed list
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface RemoteIndexedList<KEY, E> extends RemoteList<E> {

  /**
   * Returns the value object for the given key or <code>null</code> if no such value exists.
   * 
   * @param key
   *          the key whose associated value is to be returned
   * @return the value object for the given key
   */
  public E get(KEY key);

  /**
   * Returns the key that belongs to the given value object.
   * 
   * @param obj
   *          the value object whose key is to be returned
   * @return the key that belongs to the given value object
   */
  public KEY getKey(E obj);

  /**
   * Removes the object with the given key from the list.
   * 
   * @param key
   *          the key whose associated value is to be removed
   * @return the removed object with the specified key
   */
  public E removeByKey(KEY key);

  /**
   * Returns <code>true</code> if this list contains an object with the given key.
   *
   * @param key
   *          the key whose presence in this list is to be tested
   * @return <code>true</code> if this list contains an object for the specified key
   */
  public boolean containsKey(KEY key);

  /**
   * Returns a set of the keys contained in this list.
   * 
   * @return set of keys
   */
  public RemoteCollection<KEY> keySet();

  /**
   * @return returns "this". The method exists for legacy reasons only
   * 
   * @deprecated This method is useless after redesign of the API with TPT 2025.09. The collection
   *             itself is iterable now. Will be removed in TPT 2026.06.
   */
  @Deprecated
  @Override
  public RemoteIndexedList<KEY, E> asIterable();

}
