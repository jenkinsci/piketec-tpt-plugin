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
 * Configuration to use a parent project when loading projects.
 *
 *
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface ParentProjectSettings extends IdentifiableRemote {

  /**
   * Sets the path to the file of the parent TPT project. If set, TPT loads items from the parent
   * project into this project when loading this project. Unset the parent project by passing
   * <code>null</code> to this method.<br>
   * <br>
   * Note that the changes will not take effect until the project is reloaded.
   * 
   * @param path
   *          parent TPT project file
   * @throws RemoteException
   *           remote communication problem
   */
  void setParentProjectPath(String path) throws RemoteException;

  /**
   * Returns the path to the file of the parent TPT project or <code>null</code> if none is set.
   * 
   * @return the parent project file path or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getParentProjectPath() throws RemoteException;

  /**
   * To load input {@link Channel channels} from the parent project, set this flag to
   * <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if input channels shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadInputs(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if input channels are loaded from the parent project,
   * <code>false</code> otherwise.
   * 
   * @return <code>true</code> if inputs are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadInputs() throws RemoteException;

  /**
   * To load output {@link Channel channels} from the parent project, set this flag to
   * <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if output channels shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadOutputs(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if output channels are loaded from the parent project,
   * <code>false</code> otherwise.
   * 
   * @return <code>true</code> if outputs are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadOutputs() throws RemoteException;

  /**
   * To load local {@link Channel channels} from the parent project, set this flag to
   * <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if local channels shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadLocals(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if local channels are loaded from the parent project,
   * <code>false</code> otherwise.
   * 
   * @return <code>true</code> if local channels are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadLocals() throws RemoteException;

  /**
   * To load {@link Measurement measurements} from the parent project, set this flag to
   * <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if measurements shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadMeasurements(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if measurements are loaded from the parent project,
   * <code>false</code> otherwise.
   * 
   * @return <code>true</code> if measurements are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadMeasurements() throws RemoteException;

  /**
   * To load {@link Parameter parameters} from the parent project, set this flag to
   * <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if parameters shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadParameters(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if parameters are loaded from the parent project, <code>false</code>
   * otherwise.
   * 
   * @return <code>true</code> if parameters are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadParameters() throws RemoteException;

  /**
   * To load {@link Constant constants} from the parent project, set this flag to <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if constants shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadConstants(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if constants are loaded from the parent project, <code>false</code>
   * otherwise.
   * 
   * @return <code>true</code> if constants are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadConstants() throws RemoteException;

  /**
   * To load system {@link Constant constants} from the parent project, set this flag to
   * <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if system constants shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadSystemConstants(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if system constants are loaded from the parent project,
   * <code>false</code> otherwise.
   * 
   * @return <code>true</code> if system constants are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadSystemConstants() throws RemoteException;

  /**
   * To load {@link AssessmentVariable assessment variables} from the parent project, set this flag
   * to <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if assessment variables shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadAssessmentVariables(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if assessment variables are loaded from the parent project,
   * <code>false</code> otherwise.
   * 
   * @return <code>true</code> if assessment variables are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadAssessmentVariables() throws RemoteException;

  /**
   * To load equivalence class sets from the parent project, set this flag to <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if equivalence class sets shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadEquivalenceClassSets(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if equivalence class sets are loaded from the parent project,
   * <code>false</code> otherwise.
   * 
   * @return <code>true</code> if equivalence class sets are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadEquivalenceClassSets() throws RemoteException;

  /**
   * To load functions from the parent project, set this flag to <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if functions shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadFunctions(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if functions are loaded from the parent project, <code>false</code>
   * otherwise.
   * 
   * @return <code>true</code> if functions are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadFunctions() throws RemoteException;

  /**
   * To load {@link Requirement requirements} from the parent project, set this flag to
   * <code>true</code>.
   * 
   * @param load
   *          <code>true</code>, if requirements shall be loaded from the parent project
   * @throws RemoteException
   *           remote communication problem
   * @see #setParentProjectPath(String)
   */
  void setLoadRequirements(boolean load) throws RemoteException;

  /**
   * Returns <code>true</code> if requirements are loaded from the parent project,
   * <code>false</code> otherwise.
   * 
   * @return <code>true</code> if requirements are loaded, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isLoadRequirements() throws RemoteException;

  /**
   * Sets the script to filter the parent requirements before the import to the child project. Leave
   * empty to import an unfiltered list of all parent requirements.
   * 
   * @param script
   *          the script to filter parent requirements. <code>null</code> will be reduced to an
   *          empty string.
   * @throws RemoteException
   *           remote communication problem
   */
  void setRequirementsFilterScript(String script) throws RemoteException;

  /**
   * Returns the script to filter the parent requirements before the import to the child project.
   * 
   * @return the script to filter parent requirements
   * @throws RemoteException
   *           remote communication problem
   */
  String getRequirementsFilterScript() throws RemoteException;

}
