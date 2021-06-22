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
import java.util.List;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.Pair;

/**
 * This {@link Step} provides the possibility to import several signals into several declarations.
 */
public interface ImportSignalStep extends Step {

  /**
   * Sets the assignment of declarations to signals in the data file (List of Pair&lt;Declaration
   * Name, Signal Name in File&gt;). The method will ensure that always at least one
   * declaration-assignment row exits. When <code>assignment</code> is an empty list one row with
   * empty fields will be added.
   * 
   * @param assignment
   *          all assignments to be imported
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDeclarationAssignment(List<Pair<String, String>> assignment)
      throws RemoteException;

  /**
   * @return a (List of Pair&lt;Declaration Name, Signal Name in File&gt;) with an assignment of
   *         imported declarations and their name in the data file.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<Pair<String, String>> getDeclarationAssignment() throws RemoteException;

  /**
   * @return the data file path.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getFile() throws RemoteException;

  /**
   * Sets the data file path.
   * 
   * @param file
   *          the file path to use for import
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if file is null or contains a line break
   */
  public void setFile(String file) throws RemoteException, ApiException;

  /**
   * @return the name of the Excel sheet to be used in case of an Excel import.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getExcelSheet() throws RemoteException;

  /**
   * Sets the Excel sheet name.
   * 
   * @param excelSheet
   *          the name of the Excel sheet to be used in case of an Excel import
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if excelSheet is null
   */
  public void setExcelSheet(String excelSheet) throws RemoteException, ApiException;

  /**
   * @return an (optional) time axis name.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTimeAxisName() throws RemoteException;

  /**
   * Sets an (optional) time axis name.
   * 
   * @param name
   *          time axis name (optional)
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if name is null or contains a line break
   */
  public void setTimeAxisName(String name) throws RemoteException, ApiException;

  /**
   * @return the time shift.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTimeShift() throws RemoteException;

  /**
   * Sets the time shift.
   * 
   * @param shift
   *          expression for time shift
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if shift is null or contains a line break
   */
  public void setTimeShift(String shift) throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if the linear interpolation mode is enabled, <code>false</code> if
   *         last value mode is enabled.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isLinearInterpolation() throws RemoteException;

  /**
   * Set to <code>true</code> if linear interpolation mode is enabled, <code>false</code> if last
   * value mode is enabled.
   * 
   * @param on
   *          turn on/off linear interpolation
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setLinearInterpolation(boolean on) throws RemoteException;

}
