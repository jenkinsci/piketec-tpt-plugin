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

import java.io.File;
import java.rmi.RemoteException;
import java.util.Map;

import com.piketec.tpt.api.util.DeprecatedAndRemovedException;

/**
 * Configuration of the test execution for a particular platform, test set and parameter set. Part
 * of a {@link ExecutionConfiguration}
 *
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface ExecutionConfigurationItem extends PlatformOrExecutionItemEnabler {

  /**
   * Get the current back to back settings. If <code>createIfUnavailable</code> is <code>true</code>
   * the return value is never <code>null</code> and new back to back settings are created with
   * reference directory as reference if no settings are available yet.
   * 
   * @param createIfUnavailable
   *          if <code>true</code> the result is never <code>null</code> and new setting are created
   *          if none are set.
   * 
   * @return Returns the Back2BackSettings if already configured, creates and returns fresh settings
   *         if set to "No Reference Platform" and <code>createIfUnavailable</code> is
   *         <code>true</code>, <code>null</code> otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Back2BackSettings getBack2BackSettings(boolean createIfUnavailable) throws RemoteException;

  /**
   * Removes the back to back settings of this item (set reference to "No Reference Platform").
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void removeBack2BackSettings() throws RemoteException;

  /**
   * @return The {@link ExecutionConfiguration} this instance belongs to.
   * 
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the instance does not belong to an <code>ExecutionConfiguration</code> and
   *           therefore is not part of a TPT model.
   */
  public ExecutionConfiguration getExecutionConfiguration() throws ApiException, RemoteException;

  /**
   * @return The index of this instance in its {@link ExecutionConfiguration}.
   * 
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the instance does not belong to a <code>ExecutionConfiguration</code> and
   *           therefore is not part of a TPT model.
   */
  public int getIndex() throws ApiException, RemoteException;

  /**
   * Enable or disable the execution of this <code>ExecutionConfigItem</code> in the parent
   * {@link ExecutionConfiguration}.
   * 
   * @param active
   *          <code>true</code> to enable, <code>false</code> otherwise
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setActive(boolean active) throws RemoteException;

  /**
   * @return Returns whether this <code>ExecutionConfigItem</code> is enabled for execution or not.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isActive() throws RemoteException;

  /**
   * @return Returns the selected test set or <code>null</code> if it set to "Selected test cases".
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TestSet getTestSet() throws RemoteException;

  /**
   * @return Returns the currently selected {@link PlatformConfiguration} or <code>null</code> if
   *         none has been selected so far.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public PlatformConfiguration getPlatformConfiguration() throws RemoteException;

  /**
   * @return Returns the parameter file or <code>null</code> if none has been selected so far.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated No support for $-variables and relative paths - use {@link #getParameterFilePath()}
   *             instead. Removed in TPT-19. Throws {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public File getParameterFile() throws RemoteException;

  /**
   * @return The parameter file as <code>String</code> or <code>null</code> if none has yet been
   *         selected.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getParameterFilePath() throws RemoteException;

  /**
   * Returns the variables defined for <b>this</b> <code>ExecutionConfigurationItem</code>.
   * <p>
   * These variable could potentially overwrite variables defined in a parent scope. However, other
   * variables defined in a parent scope are not returned by this function.
   * </p>
   * 
   * @return A map containing the names of the "locally" defined variables and there "local" values.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Map<String, String> getVariables() throws RemoteException;

  /**
   * Set a {@link TestSet} for this <code>ExecutionConfigurationItem</code>.
   * 
   * @param ts
   *          the test set to be set.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTestSet(TestSet ts) throws RemoteException;

  /**
   * Set a particular {@link PlatformConfiguration} for this ExecutionConfigurationItem.
   * 
   * @param pc
   *          The platform configuration
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setPlatformConfiguration(PlatformConfiguration pc) throws RemoteException;

  /**
   * Set the parameter file as <code>File</code> or deletes the entry if <code>f==null</code>
   * 
   * @param f
   *          The file containing the parameter set.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated No support for $-variables and relative paths - use
   *             {@link #setParameterFilePath(String)} instead. Removed in TPT-19. Throws
   *             {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public void setParameterFile(File f) throws RemoteException;

  /**
   * Set the parameter file as <code>String</code> or delete the entry if <code>path==null</code>
   * 
   * @param path
   *          A string containing the path to the parameter file.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setParameterFilePath(String path) throws RemoteException;

  /**
   * Enable or disable whether assessments should be executed for this
   * <code>ExecutionConfigItem</code>.
   * <p>
   * This option enables the user to omit the assessments for a particular platform configuration
   * although the execution of assessments is enabled for the execution configuration.
   * </p>
   * <p>
   * However, in the opposite case, disabling the assessment in the Execution Configuration and
   * enabling it for the Platform will have no effect.
   * </p>
   * 
   * @param run
   *          A Boolean indicating whether the assessments shall be run or not.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setRunAssessments(boolean run) throws RemoteException;

  /**
   * @return Returns <code>true</code> if assessments should be executed for this
   *         <code>ExecutionConfigItem</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isRunAssessments() throws RemoteException;

  /**
   * Set the variables <code>name</code> to the given <code>value</code> or delete it, if
   * value==<code>null</code>
   * 
   * @param name
   *          The name of the variable
   * @param value
   *          Either the value for <code>name</code> or <code>null</code> to delete
   *          <code>name</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if <code>name==null</code>
   */
  public void setVariable(String name, String value) throws ApiException, RemoteException;

}
