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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

/**
 * This {@link Step} provides the possibility to open a message box during the test execution.
 */
public interface MessageBoxStep extends Step {

  /**
   * A message box has a type (OK,YES/NO, NO BUTTON), depending of its role during the test
   * execution.
   */
  enum MessageBoxType {
    /**
     * The user must confirm the message to close it.
     */
    OK,
    /**
     * The user must click either "Yes" or "No" to close the message. A result channel is mandatory.
     */
    YES_NO,
    /**
     * The user must do nothing, the message closes when the termination condition becomes true. For
     * this option, a termination condition is mandatory.
     */
    NO_BUTTON
  }

  /**
   * @return the message of this message box.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getMessage() throws RemoteException;

  /**
   * Set the message of this message box.
   * 
   * @param message
   *          the message to use
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setMessage(String message) throws RemoteException;

  /**
   * @return the result channel.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getChannel() throws RemoteException;

  /**
   * Sets the result channel.
   * 
   * @param channel
   *          the channel to assign the result to
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setChannel(String channel) throws RemoteException;

  /**
   * @return the termination condition for this message box.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCondition() throws RemoteException;

  /**
   * Sets the termination condition for this message box.
   * 
   * @param condition
   *          termination condition
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCondition(String condition) throws RemoteException;

  /**
   * @return the {@link MessageBoxType type} of this message box.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public MessageBoxType getMessageBoxType() throws RemoteException;

  /**
   * Sets the {@link MessageBoxType type} of this message box.
   * 
   * @param type
   *          the message box type
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setMessageBoxType(MessageBoxType type) throws RemoteException;

}
