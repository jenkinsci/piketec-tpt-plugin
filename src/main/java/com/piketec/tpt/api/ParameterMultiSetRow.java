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
package com.piketec.tpt.api;

import java.rmi.RemoteException;

/**
 * This object represents a row in a {@link ParameterMultiSet}, that means it represents a parameter
 * and the values that are set for the parameter in the different sets.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 *
 */
public interface ParameterMultiSetRow extends IdentifiableRemote {

  /**
   * Returns the name of the Parameter used in this {@link ParameterMultiSetRow}.
   * 
   * @return the name of the Parameter used in this {@link ParameterMultiSetRow}.
   * @throws RemoteException
   *           remote communication problem
   */
  String getParameter() throws RemoteException;

  /**
   * Sets the name of the parameter which should be used in this {@code ParameterMultiSetRow}.
   * 
   * @param parameter
   *          the name of the parameter used in this row
   * @throws RemoteException
   *           remote communication problem
   */
  void setParameter(String parameter) throws RemoteException;

  /**
   * Sets the value for the parameter in the specified set. Setting the value to {@code null} sets
   * it to "not set" in this parameter set.
   * 
   * @param set
   *          the set for which the value should be set. Set counting starts from 0 (0 == Set1).
   * @param value
   *          the value that should be set for the parameter of this row in this set.
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the value can not be set for the parameter of this row.
   * 
   */
  void setValue(int set, String value) throws RemoteException, ApiException;

  /**
   * Returns the value in the given set defined for the parameter of this row.
   * 
   * @param set
   *          the set for which the value should be returned.
   * @return the value of the parameter in the given set.
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the set exceeds the amount of sets in this multi set
   */
  String getValue(int set) throws RemoteException, ApiException;

}
