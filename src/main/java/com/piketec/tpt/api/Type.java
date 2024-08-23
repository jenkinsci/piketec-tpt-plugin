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

/**
 * A type is the data type of a {@link Declaration}. Create and get types by using
 * {@link Project#createType(String, String)}, {@link Project#getTypes()}. For predefined types use
 * for example {@link Project#getTypeDouble()}.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface Type extends IdentifiableRemote {

  /**
   * Get the name of the type.
   * 
   * @return The name of the type.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getName() throws RemoteException;

  /**
   * Get the type definition as a string as seen in tptaif or in the type editor.
   * 
   * @return The type definition as a string.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see Project#createType(String, String) for examples of the type syntax
   */
  String getTypeString() throws RemoteException;

  /**
   * Determines if the type is "predefined".
   * <ul>
   * <li>Any primitive type without a declared name and without enum consts is predefined.
   * <li>String types without a declared name are predefined.
   * <li>All other types are not predefined.
   * </ul>
   * Note, that every predefined type is also anonymous.
   * 
   * @return <code>true</code> if the type is predefined, <code>false</code> otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @see #isAnonymous()
   */
  boolean isPredefined() throws RemoteException;

  /**
   * 
   * Determine if the type is anonymous, i.e., if the type is <b>not</b> a custom type with an
   * explicitly declared name. This is the inverse function of {@link #isDeclared()}.
   * 
   * @return <code>true</code> if the type is anonymous, <code>false</code> otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @see #isDeclared()
   */
  boolean isAnonymous() throws RemoteException;

  /**
   * Determine if the type is declared, i.e., if the type is a custom type explicitly declared in
   * the type editor of TPT. This is the inverse function of {@link #isAnonymous()}.
   * 
   * @return <code>true</code> if the type is declared with a custom name, <code>false</code>
   *         otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @see #isAnonymous()
   */
  boolean isDeclared() throws RemoteException;

  /**
   * Returns <code>true</code> if this {@link Type} was loaded from the parent project.
   * 
   * @return <code>true</code> if this {@link Type} is loaded from parent project,
   *         <code>false</code> otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @see ParentProjectSettings
   */
  boolean isLoadedFromParent() throws RemoteException;

}
