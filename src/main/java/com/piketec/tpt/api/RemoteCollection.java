/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 PikeTec GmbH
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
import java.util.Collection;

/**
 * A collection of items where changes to the items are directly performed in TPT
 *
 * @author Copyright (c) 2014 Piketec GmbH - All rights reserved.
 */
public interface RemoteCollection<E> extends TptRemote {

  /**
   * @return Returns all items from this <code>RemoteCollection</code>. Any change to the returned
   *         <code>Collection</code> are local and will not be sent to TPT.
   */
  public Collection<E> getItems() throws ApiException, RemoteException;

  /**
   * Delete an element from the list. This function directly deletes the corresponding
   * <code>element</code> in TPT.
   * <p>
   * 
   * If multiple {@link NamedObject NamedObjects} refer to the same TPT object, the TPT object will
   * be deleted from the collection as soon as this method is called with any of those.
   * </p>
   * 
   * @param element
   *          The element to remove.
   */
  public void delete(E element) throws ApiException, RemoteException;

}
