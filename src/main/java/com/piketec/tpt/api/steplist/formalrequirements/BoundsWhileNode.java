/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2025 Synopsys Inc.
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
package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * The bounds node is used to find out if and where a signal stays within or exceeds certain bounds.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface BoundsWhileNode extends GeneralWhileNode {

  /**
   * Sets the minimum
   * 
   * @param min
   *          the minimum
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if min is {@code null}
   */
  public void setMin(String min) throws RemoteException, ApiException;

  /**
   * Returnes the minimum
   * 
   * @return minimum
   * @throws RemoteException
   *           remote communication problem
   */
  public String getMin() throws RemoteException;

  /**
   * Sets the maximum
   * 
   * @param max
   *          the maximum
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if max is {@code null}
   */
  public void setMax(String max) throws RemoteException, ApiException;

  /**
   * Returnes the maximum
   * 
   * @return maximum
   * @throws RemoteException
   *           remote communication problem
   */
  public String getMax() throws RemoteException;

  /**
   * Sets the channel
   * 
   * @param channel
   *          the channel
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if channel is {@code null}
   */
  public void setChannel(String channel) throws RemoteException, ApiException;

  /**
   * Returnes the channel
   * 
   * @return channel
   * @throws RemoteException
   *           remote communication problem
   */
  public String getChannel() throws RemoteException;

}
