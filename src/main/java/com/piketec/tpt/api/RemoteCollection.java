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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Eine Sammlung von Item. aenderungen an dieser finden direkt in TPT statt.
 */
public interface RemoteCollection<E> extends Remote {

  /**
   * @return Liefert die Items aus dieser <code>RemoteCollection</code> zurueck. aenderungen in der
   *         <code>Collection</code> bleiben lokal und werden nicht ueber die API an TPT
   *         uebermittelt.
   */
  public Collection<E> getItems() throws ApiException, RemoteException;

  /**
   * Loescht ein Element aus der Liste. Es wird in TPT das Element geloescht, das von uebergeben
   * Objekt <code>element</code> repraesentiert wird. Das heisst, wenn zwei unterschiedliche
   * RemoteObjekte das TPT-Objekt <code>foo</code> repraesentieren, wird <code>foo</code> aus der
   * Liste entfernt, egal mit welchem der beiden Objekte die Methode aufgerufen wird.
   * 
   * @param element
   *          Das zu entfernende Element.
   */
  public void delete(E element) throws ApiException, RemoteException;

}
