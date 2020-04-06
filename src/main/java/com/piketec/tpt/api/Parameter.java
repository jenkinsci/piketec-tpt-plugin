/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2020 PikeTec GmbH
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
 * A parameter can change during a test execution but normally holds the same value most time.
 * 
 * @author Copyright (c) 2014-2020 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface Parameter extends Declaration {

  /**
   * Defines how parameter values will be exchanged between TPT and the SuT.
   * 
   * @author Copyright (c) 2014-2020 Piketec GmbH - MIT License (MIT) - All rights reserved
   */
  enum ExchangeMode {
    /**
     * The value of the parameter will be neither written nor read.
     */
    LOCAL,
    /**
     * The value of the parameter will only be read but not written.
     */
    READONLY,
    /**
     * The value of the parameter will be written and read back.
     */
    EXCHANGE,
    /**
     * The value of the parameter will be written but not read.
     */
    WRITEONLY
  }

  /**
   * Get the exchange mode of the parameter.
   * 
   * @return The mode of the parameter.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   * 
   * @see ExchangeMode
   */
  ExchangeMode getMode() throws ApiException, RemoteException;

  /**
   * Sets the exchange mode of the parameter.
   * 
   * @param mode
   *          The new exchange mode.
   * @throws ApiException
   *           If the given mode is unknown.
   * @throws RemoteException
   *           remote communication problem
   */
  void setMode(ExchangeMode mode) throws ApiException, RemoteException;

}
