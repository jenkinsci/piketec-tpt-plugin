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

/**
 * This object provides an interface to a mapping in TPT. The mapping consists of a number of
 * mapping flavors.
 */
public interface Mapping extends IdentifiableRemote, NamedObject {

  public static final String INIT_VALUES_FLAVOR = "Init Values";

  public static final String A2L_FLAVOR = "A2L";

  public static final String ADTF_FLAVOR = "Adtf";

  public static final String CATS_FLAVOR = "CATS";

  public static final String CTB_FLAVOR = "CTB Mapping";

  public static final String LABCAR_FLAVOR = "LabCar";

  public static final String LOGGING_FLAVOR = "Logging";

  public static final String MEASUREMENT_FLAVOR = "Measurement";

  public static final String MINMAX_FLAVOR = "Min/Max";

  public static final String OUTPUT_OPERATION_FLAVOR = "Output Operation";

  public static final String RENAME_FLAVOR = "Rename";

  public static final String SIMULINK_MEASUREMENT_FLAVOR = "Simulink Measurement";

  public static final String SIMULINK_OBJECT_FLAVOR = "Simulink Object";

  public static final String TARGETLINK_DATA_DICTIONARY_FLAVOR = "TargetLink Data Dictionary";

  public static final String TARGETLINK_MEASUREMENT_FLAVOR = "TargetLink Measurement";

  public static final String TOLERANCE_FLAVOR = "Tolerance";

  public static final String SCALING_FLAVOR = "Scaling";

  public static final String TASMO_INPUT_SPECIFICATION_FLAVOR = "TASMO Input Specification";

  public static final String SOMEIP_FLAVOR = "SOME/IP Configuration";

  /**
   * Returns the list of all Mapping Flavors contained in this mapping.
   * 
   * @return A {@link RemoteCollection} of Mapping Flavors.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  RemoteCollection<String> getFlavors() throws ApiException, RemoteException;

  /**
   * Add a new flavor to the mapping represented by this object.
   * 
   * @param flavorName
   *          The name of the flavor.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If <code>flavorName == null</code> or the mapping already contains the flavor or the
   *           name of the flavor is unknown to TPT.
   */
  void addFlavor(String flavorName) throws ApiException, RemoteException;

  /**
   * Get the value of the given mapping flavor column.
   * 
   * @param decl
   *          The {@link Declaration} from which you want to get the flavor.
   * @param column
   *          The name of the column of the flavor. Valid column names are all mapping specific
   *          column names from the declaration editor, such as <code>Min</code>, <code>Max</code>,
   *          <code>Hidden</code>, <code>External_Name</code> etc.
   * @param allowDefaultValue
   *          A flag which allows to get the default value from the flavor in case it is null.
   * @return The String representation of the value from the given mapping flavor column.
   * 
   * @throws ApiException
   *           If the declaration or the column is null, if the declaration does not locally exists
   *           or if the mapping does not have any flavor with the given column.
   * @throws RemoteException
   *           remote communication problem
   */
  String getMappingFlavorColumnValue(Declaration decl, String column, boolean allowDefaultValue)
      throws ApiException, RemoteException;

  /**
   * Set the value of the given mapping flavor column.
   * 
   * @param decl
   *          The {@link Declaration} from which you want to set the flavor.
   * @param column
   *          The name of the column of the flavor. Valid column names are all mapping specific
   *          column names from the declaration editor, such as <code>Min</code>, <code>Max</code>,
   *          <code>Hidden</code>, <code>External_Name</code> etc.
   * @param value
   *          The value to be set. Setting the value to the default value
   *          ({@link #getMappingFlavorColumnDefaultValue(Declaration, String)}) will let the value
   *          be null when the file is saved and loaded again.
   * 
   * @throws ApiException
   *           If the declaration or the column is null, if the declaration does not locally exists,
   *           if the mapping does not have any flavor with the given column or if the value can not
   *           be parsed to the column type.
   * @throws RemoteException
   *           remote communication problem
   */
  void setMappingFlavorColumnValue(Declaration decl, String column, String value)
      throws ApiException, RemoteException;

  /**
   * 
   * Get the default value of the given mapping flavor column for the given declaration.
   * 
   * @param decl
   *          The {@link Declaration} from which you want to get the flavor.
   * @param column
   *          The name of the column of the flavor.
   * @return The String representation of the default value from the flavor
   * @throws ApiException
   *           If the declaration or the column is null, if the declaration does not locally exists
   *           or if the mapping does not have any flavor with the given column.
   * @throws RemoteException
   *           remote communication problem
   */
  String getMappingFlavorColumnDefaultValue(Declaration decl, String column)
      throws ApiException, RemoteException;

}
