/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2022 PikeTec GmbH
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
 * TPT API representation of a requirement set. A Requirement set can be used to filter the test
 * cases in a test set and in the UI to filter the requirements view. Requirement sets are a dynamic
 * collection of {@link Requirement Requirements}. Which requirements are part of a requirement set
 * is defined by a condition that is evaluated when runnig an {@link ExecutionConfiguration}. When
 * it is used in the UI, default values may be assumed for variables used in the condition.
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface RequirementSet extends NamedObject, IdentifiableRemote {

  /**
   * Get the requirement set condition. The condition defines which requirements are part of this
   * requirement set.
   * 
   * @return the condition.
   * 
   * @throws RemoteException
   *           remote communication error
   */
  String getCondition() throws RemoteException;

  /**
   * Set the requirement set condition. The condition defines which requirements are part of this
   * requirement set.
   * 
   * @param condition
   *          The new requirement set condition.
   * @throws RemoteException
   *           remote communication error
   */
  void setCondition(String condition) throws RemoteException;
}
