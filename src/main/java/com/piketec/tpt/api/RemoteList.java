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

/**
 * Eine geordnete Sammlung von Items. aenderungen an der Liste finden direkt in TPT statt.
 */
public interface RemoteList<E> extends RemoteCollection<E> {

  /**
   * Liefert das Element an der Stelle <code>index</code> zurueck.
   * 
   * @param index
   *          Die Stelle in der Liste.
   * @return Das das Element an der gegebenen Stelle.
   * @throws IndexOutOfBoundsException
   *           Wenn der <code>index &lt; 0</code> oder <code>index &gt;= getItems().size()</code>
   */
  public E get(int index) throws ApiException, IndexOutOfBoundsException, RemoteException;

  /**
   * Entfernt das Element an der Stelle <code>index</code> aus der Liste.
   * 
   * @param index
   *          Die Stelle des zu loeschenen Items.
   * @throws IndexOutOfBoundsException
   *           Wenn der <code>index &lt; 0</code> oder <code>index &gt;= getItems().size()</code>
   */
  public void delete(int index) throws ApiException, IndexOutOfBoundsException, RemoteException;

  /**
   * Verschiebt ein Item von der Stelle <code>form</code> nach <code>to</code>. Items in diesem
   * Intervall ruecken entsprechend auf, so dass keine Luecken entstehen und kein Item
   * ueberschrieben wird.
   * 
   * @param from
   *          Die alte Position des zu verschiebenden Items.
   * @param to
   *          Die neue Position des zu verschiebenden items.
   * @return Das verschoben Item.
   * @throws IndexOutOfBoundsException
   *           Wenn <code>to</code> oder <code>from</code> <code>&lt; 0</code> oder
   *           <code>&gt;= getItems().size()</code>
   */
  public E move(int from, int to) throws ApiException, IndexOutOfBoundsException, RemoteException;

}
