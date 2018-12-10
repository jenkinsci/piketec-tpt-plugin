/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2017 PikeTec GmbH
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
 * A type is the data type of a {@link Declaration}.
 * 
 *
 * @author Copyright (c) 2017 Piketec GmbH - MIT License (MIT).
 */
public interface Type extends IdentifiableRemote {

  /**
   * Get the name of the type.
   * 
   * @return The name of the type.
   * @throws ApiException
   * @throws RemoteException
   */
  String getName() throws ApiException, RemoteException;

  /**
   * Set the name of the type.
   * 
   * @param name
   *          The new name of the type.
   * @throws ApiException
   *           If the name is not legal identifier, a type with the given name already exists or the
   *           type is anonymous or predefined.
   * @throws RemoteException
   */
  void setName(String name) throws ApiException, RemoteException;

  /**
   * Get the type definition as a string as seen in tptaif or in the type editor.
   * 
   * @return The type definition as a string.
   * @throws ApiException
   * @throws RemoteException
   */
  String getTypeString() throws ApiException, RemoteException;

  /**
   * Get if the type is predefined.
   * 
   * @return <code>true</code> if the type is predefined, <code>false</code> otherwise.
   * @throws ApiException
   * @throws RemoteException
   */
  boolean isPredefined() throws ApiException, RemoteException;

  /**
   * 
   * Get if the type is anonymous.
   * 
   * @return <code>true</code> if the type is anonymous, <code>false</code> otherwise.
   * @throws ApiException
   * @throws RemoteException
   */
  boolean isAnonymous() throws ApiException, RemoteException;

}
