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

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * A collection of items where changes to the items are directly performed in TPT
 * 
 * @param <E>
 *          the type of elements in this collection
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface RemoteCollection<E> extends Collection<E>, Serializable {

  /**
   * Returns all items in a copy of this collection. Any change to the returned collection is
   * <i>local</i> and will not be sent to TPT.<br>
   * 
   * @return all items of this <code>RemoteCollection</code> at once.
   */
  public Collection<E> getItems();

  /**
   * Delete an element from the list. This function directly deletes the corresponding
   * <code>element</code> in TPT.
   * <p>
   * 
   * If multiple <code>RemoteCollections</code> refer to the same collection in TPT, the TPT object
   * will be deleted from the collection as soon as this method is called with any of those and the
   * change is reflected in all of them.
   * </p>
   * 
   * @param element
   *          The element to remove.
   */
  public void delete(E element);

  /**
   * Returns the number of elements in this collection.
   *
   * @return the number of elements in this collection
   */
  @Override
  public int size();

  /**
   * Returns <code>true</code> if this collection contains no elements.
   *
   * @return {@code true} if this collection contains no elements
   */
  @Override
  public boolean isEmpty();

  /**
   * Returns <code>true</code>e if this collection contains the specified element.
   *
   * @param o
   *          element whose presence in this collection is to be tested
   * @return <code>true</code>e if this collection contains the specified element
   */
  @Override
  public boolean contains(Object o);

  /**
   * Returns <code>true</code> if this collection contains all the elements in the given collection.
   *
   * @param c
   *          collection of elements whose presence in this collection is to be tested
   * @return <code>true</code>e if this collection contains all the specified elements
   */
  @Override
  public boolean containsAll(Collection< ? > c);

  /**
   * Removes all of this collection's elements that are contained in the given collection.
   *
   * @param c
   *          collection containing elements to be removed from this collection
   * @return <code>true</code> if this collection was changed as a result of the call
   */
  public boolean deleteAll(Collection< ? > c);

  /**
   * Removes all of this collection's elements that are not contained in the specified collection.
   *
   * @param c
   *          collection containing elements to be retained in this collection
   * @return <code>true</code> if this collection was changed as a result of the call
   */
  @Override
  public boolean retainAll(Collection< ? > c);

  /**
   * Remove all elements from this collection.
   */
  @Override
  public void clear();

  /**
   * @return returns "this". The method exists for legacy reasons only.
   * 
   * @deprecated This method is useless after redesign of the API with TPT 2025.09. The collection
   *             itself is iterable now. Will be removed in TPT 2026.06.
   */
  @Deprecated
  public RemoteCollection<E> asIterable();

  /**
   * @return same as {@link #iterator()}. The method exists for legacy reasons only.
   * 
   * @deprecated This method is useless after redesign of the API with TPT 2025.09. Use the common
   *             {@link #iterator()} method instead with the same effect. Will be removed in TPT
   *             2026.06.
   */
  @Deprecated
  public Iterator<E> remoteIterator();

}
