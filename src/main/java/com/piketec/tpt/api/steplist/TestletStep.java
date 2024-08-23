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

/**
 * The Testlet step lets you link to another testlet. This way, you can:
 * <ul>
 * <li>Create a hierarchical automaton inside a step list.</li>
 * <li>Specify which variants to use in a subautomaton.</li>
 * </ul>
 */
public interface TestletStep extends Step {

  /**
   * @return the linked testlet.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTestlet() throws RemoteException;

  /**
   * Sets the linked testlet.
   * 
   * @param testlet
   *          the name/path of the testlet
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTestlet(String testlet) throws RemoteException;

  /**
   * @return specified linked variant for the test execution.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getVariant() throws RemoteException;

  /**
   * Sets specified linked variant for the test execution.
   * 
   * @param variant
   *          name/path of the variant
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setVariant(String variant) throws RemoteException;

  /**
   * @return if the test should wait until the testlet is finished.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isWaitUntilTestletTerminates() throws RemoteException;

  /**
   * <code>true</code> if the test should wait until the testlet is finished.
   * 
   * @param value
   *          true if testlet should wait until testlet is finished
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setWaitUntilTestletTerminates(boolean value) throws RemoteException;

}
