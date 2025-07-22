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

import java.util.List;

/**
 * collection that points directly to a corresponding (remote) list in TPT
 *
 * @param <E>
 *          element type
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface RemoteList<E> extends RemoteCollection<E>, List<E> {

  /**
   * Removes the element at position <code>index</code> from the list. Same as {@link #remove(int)}.
   * 
   * @param index
   *          The position of the item that should be deleted.
   * 
   * @throws IndexOutOfBoundsException
   *           If the <code>index &lt; 0</code> or <code>index &gt;= getItems().size()</code>
   */
  public void delete(int index);

  /**
   * Moves an element from the position <code>from</code> to <code>to</code>. All elements in this
   * interval will be shifted accordingly such that no gaps exist afterwards and no items will be
   * overwritten.
   * 
   * @param from
   *          The old position of the element.
   * @param to
   *          The new position for the element given by <code>from</code>
   * @return Returns a reference to the moved item.
   * 
   * @throws IndexOutOfBoundsException
   *           If <code>to &lt; 0 </code> or <code>from &lt; 0</code> or
   *           <code>to &gt;= getItems().size()</code> or <code>from &gt;= getItems().size()</code>
   */
  public E move(int from, int to) throws IndexOutOfBoundsException;

  /**
   * @return returns "this". The method exists for legacy reasons only
   * @deprecated This method is useless after redesign of the API with TPT 2025.09. The collection
   *             itself is iterable now. Will be removed in TPT 2026.06.
   */
  @Deprecated
  @Override
  public RemoteList<E> asIterable();

}
