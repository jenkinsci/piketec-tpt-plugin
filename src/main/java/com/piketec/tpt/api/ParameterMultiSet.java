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
import java.util.List;

/**
 * A parameter multi set which contains several parameter sets that will be executed in an multi
 * execution run with a test execution per set.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 *
 */
public interface ParameterMultiSet extends IdentifiableRemote {

  /**
   * Sets the name of this parameter multi set
   * 
   * @param name
   *          the name
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           problem while setting the name
   */
  void setName(String name) throws RemoteException, ApiException;

  /**
   * Returns the name of the parameter multi set.
   * 
   * @return the name of the parameter multi set.
   * @throws RemoteException
   *           remote communication problem
   */
  String getName() throws RemoteException;

  /**
   * Sets the amount of sets in this parameter multi set which are represented by columns.
   * 
   * @param sets
   *          the amount of sets
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the set number is under 0
   */
  void setSets(int sets) throws RemoteException, ApiException;

  /**
   * Returns the amount of sets in this parameter multi set.
   * 
   * @return the amount of sets in this parameter multi set.
   * @throws RemoteException
   *           remote communication problem
   */
  int getSets() throws RemoteException;

  /**
   * Returns the rows of this parameter multi set. Each row contains a parameter and its values per
   * set.
   * 
   * @return ParameterMultiSetRows a list of rows of the multi set containing of a parameter and its
   *         values per set.
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteCollection<ParameterMultiSetRow> getMultiSetRows() throws RemoteException;

  /**
   * Returns the row of the parameter in this parameter multi set. The row contains the parameter
   * and its values per set.
   * 
   * @param parameter
   *          the name of the parameter
   * @return ParameterMultiSetRow a row of the multi set containing of a parameter and its values
   *         per set.
   * @throws RemoteException
   *           remote communication problem
   */
  ParameterMultiSetRow getMultiSetRow(String parameter) throws RemoteException;

  /**
   * Adds a parameter to the parameter multi set at the end of the list.
   * 
   * @param parameter
   *          the name of the parameter
   * @return The new ParameterMultiSetRow with the added parameter.
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           problem while adding the parameter
   */
  ParameterMultiSetRow addParameter(String parameter) throws RemoteException;

  /**
   * Adds a parameter to the parameter multi set at the specified index.
   * 
   * @param parameter
   *          the name of the parameter
   * @param index
   *          the index at which to add the parameter.
   * @return The new ParameterMultiSetRow with the added parameter.
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           problem while adding the parameter
   */
  ParameterMultiSetRow addParameter(String parameter, int index)
      throws RemoteException, ApiException;

  /**
   * Removes a parameter from the parameter multi set
   * 
   * @param parameter
   *          the name of the parameter which should be removed
   * @return {@code true} if the element was found and removed, else {@code false}
   * @throws RemoteException
   *           remote communication problem
   */
  boolean removeParameter(String parameter) throws RemoteException;

  /**
   * Sets the default set of this parameter multi set, which can be run on its own.
   * {@code index == null} removes the default set, if the default set is used in an
   * {@code ExecutionConfigItem} it will be removed there as well. Set counting starts from 0 (0 ==
   * Set1).
   * 
   * @param index
   *          the set index of the default set
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the index exceeds the boundaries
   */
  void setDefaultSet(Integer index) throws RemoteException, ApiException;

  /**
   * Returns the default set which can be run on its own. {@code null} if no default set is defined.
   * Set counting starts from 0 (0 == Set1).
   * 
   * @return Returns the default set which can be run on its own. {@code null} if no default set is
   *         defined. Set counting starts from 0 (0 == Set1).
   * @throws RemoteException
   *           remote communication problem
   */
  Integer getDefaultSet() throws RemoteException;

  /**
   * Returns a list of problems in the parameter multi set. The List is empty if there are no
   * problems.
   * 
   * @return Returns a list of problems in the parameter multi set. The List is empty if there are
   *         no problems.
   * @throws RemoteException
   *           remote communication problem
   */
  List<String> getMultiSetProblems() throws RemoteException;

  /**
   * Adds a new set at a specified index.
   * 
   * @param index
   *          the index on which a new set should be added. Set counting starts from 0 (0 == Set1).
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the index is outside the current set list length +1
   */
  void addSet(int index) throws RemoteException, ApiException;

  /**
   * Removes the set at the given index, if the set is the default set and it is used in any
   * {@code ExecutionConfigItem} it will be deleted there as well.
   * 
   * @param index
   *          the index on which the set should be removed. Set counting starts from 0 (0 == Set1).
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the index is outside the current set list length
   */
  void removeSet(int index) throws RemoteException, ApiException;

}
