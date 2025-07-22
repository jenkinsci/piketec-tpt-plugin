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
 * This {@link ConditionTreeNode} defines an interval which happens when a condition is evaluated
 * based on the {@link WhenType}.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 * 
 */
public interface WhenNode extends ConditionTreeNode {

  /**
   * The possible when types.
   */
  enum WhenType {
    BECOMES_TRUE, HAS_BECOME_TRUE, WILL_BECOME_TRUE, HAS_BEEN_TRUE;
  }

  /**
   * The possible when extension types.
   */
  enum WhenExtensionType {
    NONE, PRECEDING, FOLLOWING;
  }

  /**
   * @return the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getExpression() throws RemoteException;

  /**
   * Sets the expression.
   * 
   * @param expr
   *          the expression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the expr is {@code null}
   */
  public void setExpression(String expr) throws RemoteException, ApiException;

  /**
   * The type of evaluation of the expression.
   * 
   * @return The type of evaluation of the expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public WhenType getWhenType() throws RemoteException;

  /**
   * Sets the type of evaluation of the expression.
   * 
   * @param whenType
   *          the type of the expression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the whenType is {@code null}
   */
  public void setWhenType(WhenType whenType) throws RemoteException, ApiException;

  /**
   * The time component which belongs to the type of the evaluation.
   * 
   * @return The time component which belongs to the type of the evaluation.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTime() throws RemoteException;

  /**
   * Sets the time component which belongs to the type of the evaluation
   * 
   * @param time
   *          the time
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the time is {@code null}
   */
  public void setTime(String time) throws RemoteException, ApiException;

  /**
   * The time for which the interval gets extended.
   * 
   * @return The time for which the interval gets extended
   * @throws RemoteException
   *           remote communication problem
   */
  public String getIntervalLength() throws RemoteException;

  /**
   * Sets the time for which the interval shall be extended.
   * 
   * @param intervalLength
   *          the time for which the interval shall be extended
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the time is {@code null}
   */
  public void setIntervalLength(String intervalLength) throws RemoteException, ApiException;

  /**
   * Sets the {@code WhenExtensionType} with which the interval shall be extended.
   * {@code WhenExtensionType} are NONE, PROCEEDING, FOLLOWING.
   * 
   * @param extensionType
   *          the {@code WhenExtensionType}
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the {@code WhenExtensionType} is null
   */
  public void setExtensionType(WhenExtensionType extensionType)
      throws RemoteException, ApiException;

  /**
   * The {@code WhenExtensionType} with wich the intervall is extended.
   * 
   * @return the {@code WhenExtensionType} with wich the intervall is extended.
   * @throws RemoteException
   *           remote communication problem
   */
  public WhenExtensionType getExtensionType() throws RemoteException;
}
