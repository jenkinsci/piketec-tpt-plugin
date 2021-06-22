/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2021 PikeTec GmbH
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
package com.piketec.tpt.api.cplatform;

import java.rmi.RemoteException;

import com.piketec.tpt.api.IdentifiableRemote;

/**
 * A function that can be used for scheduling in the C\C++ platform in TPT
 * 
 * 
 * @author Copyright (c) 2014-2021 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface FunctionSchedulingItem extends IdentifiableRemote {

  /**
   * @return Will this function be scheduled by the generated code
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isScheduled() throws RemoteException;

  /**
   * Set if this function will be scheduled by the generated code
   * 
   * @param isScheduled
   *          Will the function be scheduled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setScheduled(boolean isScheduled) throws RemoteException;

  /**
   * @return The name of the function
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getName() throws RemoteException;

  /**
   * Sets the name of the function, only supported if the function is not imported from the source
   * code
   * 
   * @param name
   *          The name of the function
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setName(String name) throws RemoteException;

  /**
   * Gets the period of the function
   * 
   * @return The period of the function
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getPeriod() throws RemoteException;

  /**
   * Set the period to schedule the function
   * 
   * @param period
   *          The desired period for this function
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setPeriod(String period) throws RemoteException;

  /**
   * @return The function kind of the associated function
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public CCodeFunctionKind getFunctionKind() throws RemoteException;

  /**
   * Set the desired function kind of this scheduling item
   * 
   * @param cCodeFunctionKind
   *          The desired function kind
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setFunctionKind(CCodeFunctionKind cCodeFunctionKind) throws RemoteException;

  /**
   * Was this scheduling item imported from the sources or manually added
   * 
   * @return Was this scheduling item imported from the sources
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isImportedFromCCodeSource() throws RemoteException;

}
