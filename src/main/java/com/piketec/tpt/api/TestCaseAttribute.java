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
 * A <code>TestCaseAttribute</code> represents a row in the Test Case Details view. It consists of a
 * name and a type.
 * 
 */
public interface TestCaseAttribute extends IdentifiableRemote {

  /**
   * Type String for String Test Case Attribute
   */
  public static final String STRING_TYPE = "String";

  /**
   * Type String for URI Test Case Attribute
   */
  public static final String URI_TYPE = "URI";

  /**
   * Returns the type of this attribute; currently it is either a <code>String</code> or a
   * <code>URI</code>.
   * 
   * @return The type as a <code>String</code>.
   * @throws ApiException
   * @throws RemoteException
   */
  String getType() throws ApiException, RemoteException;

  /**
   * @return Returns the name of this <code>TestCaseAttribute</code>
   * @throws ApiException
   * @throws RemoteException
   */
  String getName() throws ApiException, RemoteException;

  /**
   * Set the name for this <code>TestCaseAttribute</code>
   * 
   * @param newName
   *          The new name.
   * @throws ApiException
   *           If <code>newName==null</code> or <code>newName</code> does already exist.
   * @throws RemoteException
   */
  void setName(String newName) throws ApiException, RemoteException;

  /**
   * Indicates whether the value of a <code>TestCaseAttribute</code> will be copied with the test
   * case it is assigned to. In the TPT GUI this setting correponds to the check box in the
   * <code>TestCaseAttribute</code> definition dialog.
   * 
   * @return <code>true</code>, if the <code>TestCaseAttribute</code> will be automatically copied
   * @throws ApiException
   * @throws RemoteException
   */
  boolean isCopyable() throws ApiException, RemoteException;

  /**
   * Set a <code>TestCaseAttribute</code> to be "copyable". The values of a "copyable"
   * <code>TestCaseAttribute</code> are automatically copied into the new test case, when the
   * corresponding test case is copied or duplicated.
   * 
   * @param on
   *          <code>true</code>, if TestCaseAttribute should be "copyable".
   * @throws ApiException
   * @throws RemoteException
   */
  void setCopyable(boolean on) throws ApiException, RemoteException;

}
