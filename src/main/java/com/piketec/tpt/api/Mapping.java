/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2024 Synopsys Inc.
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

  /**
   * Initial values flavor
   */
  public static final String INIT_VALUES_FLAVOR = "Init Values";

  /**
   * A2L flavor
   */
  public static final String A2L_FLAVOR = "A2L";

  /**
   * FEP flavor
   */
  public static final String FEP_FLAVOR = "FEP";

  /**
   * CTB Mapping flavor
   */
  public static final String CTB_FLAVOR = "CTB Mapping";

  /**
   * LabCar flavor
   */
  public static final String LABCAR_FLAVOR = "LabCar";

  /**
   * Logging flavor
   */
  public static final String LOGGING_FLAVOR = "Logging";

  /**
   * Measurement flavor
   */
  public static final String MEASUREMENT_FLAVOR = "Measurement";

  /**
   * Min/Max flavor
   */
  public static final String MINMAX_FLAVOR = "Min/Max";

  /**
   * Output Operation flavor
   */
  public static final String OUTPUT_OPERATION_FLAVOR = "Output Operation";

  /**
   * Rename flavor
   */
  public static final String RENAME_FLAVOR = "Rename";

  /**
   * Simulink Measurement flavor
   */
  public static final String SIMULINK_MEASUREMENT_FLAVOR = "Simulink Measurement";

  /**
   * Simulink Object flavor
   */
  public static final String SIMULINK_OBJECT_FLAVOR = "Simulink Object";

  /**
   * TargetLink Data Dictionary flavor
   */
  public static final String TARGETLINK_DATA_DICTIONARY_FLAVOR = "TargetLink Data Dictionary";

  /**
   * TargetLink Measurement flavor
   */
  public static final String TARGETLINK_MEASUREMENT_FLAVOR = "TargetLink Measurement";

  /**
   * Tolerance flavor
   */
  public static final String TOLERANCE_FLAVOR = "Tolerance";

  /**
   * Scaling flavor
   */
  public static final String SCALING_FLAVOR = "Scaling";

  /**
   * TASMO Input Specification flavor
   */
  public static final String TASMO_INPUT_SPECIFICATION_FLAVOR = "TASMO Input Specification";

  /**
   * SOME/IP flavor
   */
  public static final String SOMEIP_FLAVOR = "SOME/IP Configuration";

  /**
   * Returns the list of all Mapping Flavors contained in this mapping.
   * 
   * @return A {@link RemoteCollection} of Mapping Flavors.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteCollection<String> getFlavors() throws RemoteException;

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
   *           If the declaration or the column is null, if the declaration does not exist locally
   *           or if the mapping does not have any flavor with the given column.
   * @throws RemoteException
   *           remote communication problem
   */
  String getMappingFlavorColumnValue(Declaration decl, String column, boolean allowDefaultValue)
      throws ApiException, RemoteException;

  /**
   * Get the value of the given mapping flavor column of structured elements and its members.
   * 
   * @param decl
   *          The {@link Declaration} from which you want to get the flavor.
   * @param subElement
   *          The subelement in case of a structured {@link Declaration}. Can be left empty or
   *          <code>null</code> to get the value for the root element. The sub elements must be
   *          separated by '.'. Do NOT add the name of the declaration as first element.
   * @param column
   *          The name of the column of the flavor. Valid column names are all mapping specific
   *          column names from the declaration editor, such as <code>Min</code>, <code>Max</code>,
   *          <code>Hidden</code>, <code>External_Name</code> etc.
   * @param allowDefaultValue
   *          A flag which allows to get the default value from the flavor in case it is null.
   * @return The String representation of the value from the given mapping flavor column.
   * 
   * @throws ApiException
   *           If the declaration or the column is null, if the declaration does not exist locally
   *           or if the mapping does not have any flavor with the given column.
   * @throws RemoteException
   *           remote communication problem
   */
  String getMappingFlavorColumnValue(Declaration decl, String subElement, String column,
                                     boolean allowDefaultValue)
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
   *           If the declaration or the column is null, if the declaration does not exist locally,
   *           if the mapping does not have any flavor with the given column or if the value can not
   *           be parsed to the column type.
   * @throws RemoteException
   *           remote communication problem
   */
  void setMappingFlavorColumnValue(Declaration decl, String column, String value)
      throws ApiException, RemoteException;

  /**
   * Set the value of the given mapping flavor column.
   * 
   * @param decl
   *          The {@link Declaration} from which you want to set the flavor.
   * @param subElement
   *          The subelement in case of a structured {@link Declaration}. Can be left empty or
   *          <code>null</code> to set the value for the root element. The sub elements must be
   *          separated by '.'. Do NOT add the name of the declaration as first element.
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
   *           If the declaration or the column is null, if the declaration does not exist locally,
   *           if the mapping does not have any flavor with the given column or if the value can not
   *           be parsed to the column type.
   * @throws RemoteException
   *           remote communication problem
   */
  void setMappingFlavorColumnValue(Declaration decl, String subElement, String column, String value)
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
   *           If the declaration or the column is null, if the declaration does not exist locally
   *           or if the mapping does not have any flavor with the given column.
   * @throws RemoteException
   *           remote communication problem
   */
  String getMappingFlavorColumnDefaultValue(Declaration decl, String column)
      throws ApiException, RemoteException;

  /**
   * 
   * Get the default value of the given mapping flavor column for the given declaration.
   * 
   * @param decl
   *          The {@link Declaration} from which you want to get the flavor.
   * @param subElement
   *          The subelement in case of a structured {@link Declaration}. Can be left empty or
   *          <code>null</code> to set the value for the root element. The sub elements must be
   *          separated by '.'. Do NOT add the name of the declaration as first element.
   * @param column
   *          The name of the column of the flavor.
   * @return The String representation of the default value from the flavor
   * @throws ApiException
   *           If the declaration or the column is null, if the declaration does not exist locally
   *           or if the mapping does not have any flavor with the given column.
   * @throws RemoteException
   *           remote communication problem
   */
  String getMappingFlavorColumnDefaultValue(Declaration decl, String subElement, String column)
      throws ApiException, RemoteException;

  /**
   * Returns <code>true</code> if this {@link Mapping} was loaded from the parent project.
   * 
   * @return <code>true</code> if this mapping was loaded from parent project, <code>false</code>
   *         otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @see ParentProjectSettings
   */
  boolean isLoadedFromParent() throws RemoteException;

  /**
   * Returns <code>true</code> if the flavor with the provided name exists in this {@link Mapping}
   * and was loaded from the parent project.
   * 
   * @param flavorName
   *          The name of the flavor.
   * @return <code>true</code> if this flavor was loaded from parent project, <code>false</code>
   *         otherwise.
   * @throws ApiException
   *           If <code>flavorName == null</code> or the mapping does not contain the flavor or the
   *           name of the flavor is unknown to TPT.
   * @throws RemoteException
   *           remote communication problem
   * @see ParentProjectSettings
   */
  public boolean isFlavorLoadedFromParent(String flavorName) throws ApiException, RemoteException;

}
