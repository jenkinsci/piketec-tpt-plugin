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

import com.piketec.tpt.api.util.DeprecatedAndRemovedException;

/**
 * A <code>Declaration</code> is either a signal or a parameter or a constant.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface Declaration extends NamedObject, IdentifiableRemote {

  /**
   * @return The group the declaration belongs to. Empty String if no Group is defined.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getGroup() throws RemoteException;

  /**
   * Set the group the declaration should belong to.
   * 
   * @param group
   *          The new group. <code>Null</code> or an string containing only whitespaces will be
   *          reduced to an empty string.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void setGroup(String group) throws RemoteException;

  /**
   * Returns the name of the unit of the declaration. For complex types units of subvariables are
   * united.
   * 
   * @return current unit as a string
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getUnit() throws RemoteException;

  /**
   * Set the unit of the declaration. For structs, maps and curves provide a comma separated list of
   * units in braces, which fits their structure.<br>
   * <br>
   * <b>Note:</b> At the moment single units without curly braces (e.g. "km") are accepted for
   * curves and maps. In this case the unit of the value element will be set. In the future this
   * might be rejected. This, this abbreviation is <i>not</i> recommended.
   * 
   * @param unit
   *          The new unit of the declaration. <code>Null</code> will be reduced to an empty string.
   * @throws ApiException
   *           If unit string format is invalid, structure does not match type of the declaration or
   *           unit does not exist.
   * @throws RemoteException
   *           remote communication problem
   */
  void setUnit(String unit) throws ApiException, RemoteException;

  /**
   * Get the default value of the declaration. The format is the same as seen in tptaif or the
   * declaration editor.
   * 
   * @return The default value string.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getDefaultValue() throws RemoteException;

  /**
   * Set the default value of the declaration. The format is the same as seen in tptaif or the
   * declaration editor.
   * 
   * @param defaultValue
   *          The new default value.
   * 
   * @throws ApiException
   *           If the given <code>defaultValue</code> string could not be parsed.
   * @throws RemoteException
   *           remote communication problem
   */
  void setDefaultValue(String defaultValue) throws ApiException, RemoteException;

  /**
   * Get the type of the declaration.
   * 
   * @return The type of the declaration.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getType() throws RemoteException;

  /**
   * Set the type of the declaration. Anonymous types are copied so the following code will return
   * false:
   * 
   * <pre>
   * Type anonymousType = project.createType(null, "float[]");
   * Channel channel = project.createChannel("myChannel");
   * channel.setType(anonymousType);
   * return channel.getType().equals(anonymousType); // false
   * </pre>
   * 
   * If the new type is a struct, map or curve and the declaration has a unit set, the unit will be
   * removed since units are not allowed for declarations of these types.
   * 
   * @param type
   *          The new type of the declaration. This will reset the default value.
   * @throws ApiException
   *           If the given type is unknown in the TPT project.
   * @throws RemoteException
   *           remote communication problem
   */
  void setType(Type type) throws ApiException, RemoteException;

  /**
   * Get the description of the declaration.
   * 
   * @return The description of the declaration.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getDescription() throws RemoteException;

  /**
   * Set the description of the declaration.
   * 
   * @param description
   *          The new description of the declaration. <code>Null</code> will be reduced to an empty
   *          string.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void setDescription(String description) throws RemoteException;

  /**
   * Returns the {@link Unit} set to this declaration. The method is only allowed on non-structured
   * declarations. Use {@link #getUnit()} for structured declarations.
   * 
   * @return the unit for this declaration or <code>null</code> if no unit is set.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If this declaration has a structured type.
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Units are
   *             represented as Strings only.
   */

  /**
   * Returns <code>true</code> if this declaration was loaded from the parent project.
   * 
   * @return <code>true</code> if this declaration was loaded from parent project,
   *         <code>false</code> otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @see ParentProjectSettings
   */
  boolean isLoadedFromParent() throws RemoteException;

  @Deprecated
  Unit getUnitObject() throws ApiException, RemoteException;

  /**
   * Set the unit of this declaration. The method is only allowed on unstructured declarations. Use
   * {@link #setUnit(String)} for structured declarations.
   * 
   * @param unit
   *          to be set or <code>null</code> to remove unit.
   * @throws ApiException
   *           If this declaration has a structured type.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Units are
   *             represented as Strings only.
   */
  @Deprecated
  void setUnit(Unit unit) throws ApiException, RemoteException;

}
