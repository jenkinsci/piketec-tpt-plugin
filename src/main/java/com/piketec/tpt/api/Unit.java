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
import java.util.Map;

public interface Unit extends IdentifiableRemote {

  /**
   * Returns the name of this unit.
   * 
   * @return name of this unit
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  String getName() throws ApiException, RemoteException;

  /**
   * Set the name of this unit.
   * 
   * @param name
   *          to be set.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if unit is not editable, the given name has illegal characters or a unit with the
   *           same name or symbol already exists
   */
  void setName(String name) throws ApiException, RemoteException;

  /**
   * Returns the units symbol.
   * 
   * @return symbol of this unit
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  String getSymbol() throws ApiException, RemoteException;

  /**
   * Set the symbol of this unit.
   * 
   * @param symbol
   *          to be set.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if unit is not editable, the given symbol has illegal characters or a unit with the
   *           same name or symbol already exists
   */
  void setSymbol(String symbol) throws ApiException, RemoteException;

  /**
   * Returns the offset of this unit
   * 
   * @return unit offset
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  double getOffset() throws ApiException, RemoteException;

  /**
   * Set the offset of this unit.
   * 
   * @param offset
   *          to be set.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if unit is not editable
   */
  void setOffset(double offset) throws ApiException, RemoteException;

  /**
   * Returns the nominator of this units factor.
   * 
   * @return nominator
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  int getNominator() throws ApiException, RemoteException;

  /**
   * Set the nominator of this units factor
   * 
   * @param nominator
   *          to be set
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if unit is not editable
   */
  void setNominator(int nominator) throws ApiException, RemoteException;

  /**
   * Returns the denominator of this units factor.
   * 
   * @return denominator
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  int getDenomintaor() throws ApiException, RemoteException;

  /**
   * Set the denominator of this units factor
   * 
   * @param denominator
   *          to be set
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if unit is not editable
   */
  void setDenominator(int denominator) throws ApiException, RemoteException;

  /**
   * Returns the power of ten of this units factor.
   * 
   * @return decimal exponent for nominator
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  int getDecimalExponent() throws ApiException, RemoteException;

  /**
   * Set the exponent to the base of ten of this units factor
   * 
   * @param exponent
   *          to be set
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if unit is not editable
   */
  void setDecimalExponent(int exponent) throws ApiException, RemoteException;

  /**
   * Returns the power of two of this units factor.
   * 
   * @return binary exponent for nominator
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  int getBinaryExponent() throws ApiException, RemoteException;

  /**
   * Set the exponent to the base of two of this units factor
   * 
   * @param exponent
   *          to be set
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if unit is not editable
   */
  void setBinaryExponent(int exponent) throws ApiException, RemoteException;

  /**
   * Returns the power of &#x03C0; of this units factor.
   *
   * @return PI exponent for nominator
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  int getPiExponent() throws ApiException, RemoteException;

  /**
   * Set the exponent to the base of &#x03C0; of this units factor
   * 
   * @param exponent
   *          to be set
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if unit is not editable
   */
  void setPiExponent(int exponent) throws ApiException, RemoteException;

  /**
   * Returns the units dependencies. To remove a dependecy to an other unit set its exponent to 0.
   * 
   * @see #setDependency(Unit, short)
   * 
   * @return map of all base unit dependencies
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  Map<Unit, Short> getDependencies() throws ApiException, RemoteException;

  /**
   * Set the dependency of this unit to the given <code>unit</code> by defining an exponent. To
   * remove a dependecy set the exponent to <code>0</code>.
   * 
   * @param unit
   *          The unit this unit shall get a dependency to.
   * @param exponent
   *          The exponent of the dependency
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if unit is not editable
   */
  void setDependency(Unit unit, short exponent) throws ApiException, RemoteException;

  /**
   * Returns <code>true</code> if this unit is editable, <code>false</code> otehrwise.
   * 
   * @return <code>true</code> iff this unit is editable
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  boolean isEditable() throws ApiException, RemoteException;

  /**
   * Returns <code>true</code> if this unit has no dependencies to other units, <code>false</code>
   * otherwise.
   *
   * @return <code>true</code> iff this unit has no dependencies to other units
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  boolean isBaseUnit() throws ApiException, RemoteException;

}
