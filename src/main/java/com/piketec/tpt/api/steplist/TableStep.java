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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * The {@link TableStep} is a step which provides the possibility to manage and execute similar wait
 * blocks in a compressed way. Abstractly, a {@link TableStep} consists of some usual columns and a
 * single (unusual) wait column. The usual columns will be, dependent to their
 * {@link TableStepColumnType}, executed as long there is enough time left. The time limit is
 * defined by the wait column. Then the next row will be executed.<br>
 * <br>
 */
public interface TableStep extends Step {

  /**
   * A table step column can apply the (check-,set-,call-)-rules.
   */
  enum TableStepColumnType {
    /**
     * This type provides a comparison operation in each row.
     */
    CHECK,
    /**
     * This type provides an assignment operation in each row.
     */
    SET,
    /**
     * This type provides a function call in each row.
     */
    CALL;
  }

  /**
   * @param columnIndex
   *          column index
   * @return the {@link TableStepColumnType} of the columnPosth column.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public TableStepColumnType getColumnType(int columnIndex) throws RemoteException, ApiException;

  /**
   * Sets the {@link TableStepColumnType} of the column at position columnPos.
   * 
   * @param columnIndex
   *          column index
   * @param type
   *          type of this column
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setColumnType(int columnIndex, TableStepColumnType type)
      throws RemoteException, ApiException;

  /**
   * @param columnIndex
   *          column index
   * @return the declaration name of the columnPosth column.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public String getDeclarationName(int columnIndex) throws RemoteException, ApiException;

  /**
   * Sets the declaration of the columnPosth column by their name.
   * 
   * @param columnIndex
   *          column index
   * @param name
   *          name of the channel/parameter/measurement/assessment to set/check in this column
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setDeclarationName(int columnIndex, String name) throws RemoteException, ApiException;

  /**
   * @param rowIndex
   *          row index
   * @return the time to wait after the row is executed.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public String getWaitExpr(int rowIndex) throws RemoteException, ApiException;

  /**
   * Set the time to wait after the row is executed.
   * 
   * @param rowIndex
   *          row index
   * @param expr
   *          expression to use as wait condition in this row
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setWaitExpr(int rowIndex, String expr) throws RemoteException, ApiException;

  /**
   * @param rowIndex
   *          row index
   * @param columnIndex
   *          column index
   * @return the value of an cell in the table.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public String getValueAt(int rowIndex, int columnIndex) throws RemoteException, ApiException;

  /**
   * Sets the value of a cell in the table.
   * 
   * @param rowIndex
   *          row index
   * @param columnIndex
   *          column index
   * @param value
   *          the expression to use in the specified table cell
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setValueAt(int rowIndex, int columnIndex, String value)
      throws RemoteException, ApiException;

  /**
   * Adds a new column at index. Index values starts to count with zero.
   * 
   * @param index
   *          column index
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void addColumn(int index) throws RemoteException, ApiException;

  /**
   * Removes the column at index. Index values starts to count with zero.
   * 
   * @param index
   *          column index
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void removeColumn(int index) throws RemoteException, ApiException;

  /**
   * Adds a new row at index. Index values starts to count with zero.
   * 
   * @param index
   *          row index
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void addRow(int index) throws RemoteException, ApiException;

  /**
   * Removes the row at index. Index values starts to count with zero.
   * 
   * @param index
   *          row index
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void removeRow(int index) throws RemoteException, ApiException;

  /**
   * @return The number of columns, time column not included.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getColumnCount() throws RemoteException;

  /**
   * @return The number of rows, header row not included.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getRowCount() throws RemoteException;

}
