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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

import com.piketec.tpt.api.TptRemote;
import com.piketec.tpt.api.Type;

/**
 * Argument for {@link ServiceStep}
 *
 */
public interface ServiceStepArgument extends TptRemote {

  /**
   * Delivers the {@link Type} of a service step argument.
   * 
   * @return {@link Type} of a service step argument.
   * @throws RemoteException
   *           remote communication problem
   */
  public Type getType() throws RemoteException;

  /**
   * Delivers the name of a service step argument.
   * 
   * @return name of a service step argument.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getName() throws RemoteException;

  /**
   * Delivers the value of a service step argument.
   * 
   * @return value of a service step argument.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getValue() throws RemoteException;

  /**
   * Sets a value of a service step argument.
   * 
   * @param value
   *          New value for service step argument.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setValue(String value) throws RemoteException;

  /**
   * {@link ServiceStepArgument} can be mandatory for using a specific {@link ServiceStep}. This
   * function determines whether a {@link ServiceStepArgument} is mandatory or not. In case a
   * mandatory argument is not provided via TPT API, a compile error will occur for the
   * corresponding step list.
   * 
   * @return <code>true</code> if {@link ServiceStepArgument} is mandatory.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isMandatory() throws RemoteException;
}
