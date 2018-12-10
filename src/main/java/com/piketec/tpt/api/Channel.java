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
 * A channel carries the signal of a test stimulation.
 * 
 * @author Copyright (c) 2017 Piketec GmbH - MIT License (MIT)
 */
public interface Channel extends Declaration {

  /**
   * A channel can be an input, output, local or undefined.
   *
   */
  enum ChannelMode {
    /**
     * An input channel
     */
    IN,
    /**
     * An output channel
     */
    OUT,
    /**
     * A local channel
     * 
     */
    LOCAL,
    /**
     * A channel with undefined mode
     */
    NONE
  }

  /**
   * Get if the channel shall be recorded during test execution.
   * 
   * @return <code>true</code> if the channel should be recorded, <code>false</code> otherwise.
   * @throws ApiException
   * @throws RemoteException
   */
  boolean isRecord() throws ApiException, RemoteException;

  /**
   * Set if the channel should be recorded during test execution.
   * 
   * @param on
   *          <code>true</code> if the channel should be recorded, <code>false</code> otherwise.
   * @throws ApiException
   * @throws RemoteException
   */
  void setRecord(boolean on) throws ApiException, RemoteException;

  /**
   * Get if the channel is an input, output, local or undefined.
   * 
   * @return The mode of the channel.
   * @throws ApiException
   * @throws RemoteException
   */
  ChannelMode getMode() throws ApiException, RemoteException;

  /**
   * Set the mode of the channel to input, output, local or undefined.
   * 
   * @param mode
   *          The new mode of the channel.
   * @throws ApiException
   * @throws RemoteException
   */
  void setMode(ChannelMode mode) throws ApiException, RemoteException;

}
