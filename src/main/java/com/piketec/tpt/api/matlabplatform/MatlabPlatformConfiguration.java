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
package com.piketec.tpt.api.matlabplatform;

import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.Pair;
import com.piketec.tpt.api.PlatformConfiguration;
import com.piketec.tpt.api.Project.SynchronizationMethod;

/**
 * The TPT API representation of the MATLAB/Simulink platform in TPT
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface MatlabPlatformConfiguration extends PlatformConfiguration {

  /**
   * Set the matlab version name.
   * 
   * @param matlabVersionName
   *          The name of the MATLAB version to be used
   * @throws RemoteException
   *           remote communication problem
   */
  public void setMatlabVersionName(String matlabVersionName) throws RemoteException;

  /**
   * @return The MATLAB version name
   * @throws RemoteException
   *           remote communication problem
   */
  public String getMatlabVersionName() throws RemoteException;

  /**
   * Set the subsystem block path.
   * 
   * @param sutSubsystemPath
   *          the new subsystem block path
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSutSubsystemPath(String sutSubsystemPath) throws RemoteException;

  /**
   * @return the subsystem block path
   * @throws RemoteException
   *           remote communication problem
   */
  public String getSutSubsystemPath() throws RemoteException;

  /**
   * Set the path to the original model file.
   * 
   * @param originalModelFilePath
   *          the path to the new original model file
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOriginalModelFilePath(String originalModelFilePath) throws RemoteException;

  /**
   * @return the path to the original model file
   * @throws RemoteException
   *           remote communication problem
   */
  public String getOriginalModelFilePath() throws RemoteException;

  /**
   * @return the path to the test frame file. If deriving the test frame file path is enabled, this
   *         will return the automatically derived path.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTestframeFilePath() throws RemoteException;

  /**
   * @return The absolute path to the TPT I/O file (with suffix "_tpt_io.m"). This is automatically
   *         derived from the configured test frame file path.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getIOFilePath() throws RemoteException;

  /**
   * Set a custom test frame file path.
   * 
   * @param testframeFilePath
   *          the new test frame file path
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCustomTestframeFilePath(String testframeFilePath) throws RemoteException;

  /**
   * @return <code>true</code> if the test frame file path is derived from the original model
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isTestFrameFilePathDeriveFromOriginalModel() throws RemoteException;

  /**
   * Automatically derive the test frame file path from the original model.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTestframeFilePathDeriveFromOriginalModel() throws RemoteException;

  /**
   * Set the original model load script
   * 
   * @param originalModelLoadScript
   *          the new original model load script
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOriginalModelLoadScript(String originalModelLoadScript) throws RemoteException;

  /**
   * @return the original model load script
   * @throws RemoteException
   *           remote communication problem
   */
  public String getOriginalModelLoadScript() throws RemoteException;

  /**
   * Set the test frame model load script
   * 
   * @param testframeModelLoadScript
   *          the new test frame model load script
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTestframeModelLoadScript(String testframeModelLoadScript) throws RemoteException;

  /**
   * @return the test frame model load script
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTestframeModelLoadScript() throws RemoteException;

  /**
   * Set the test run script
   * 
   * @param testRunScript
   *          the new test run script
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTestRunScript(String testRunScript) throws RemoteException;

  /**
   * @return the test run script
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTestRunScript() throws RemoteException;

  /**
   * Set the code coverage custom script
   * 
   * @param codeCoverageCustomScript
   *          the new code coverage custom script
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCodeCoverageCustomScript(String codeCoverageCustomScript) throws RemoteException;

  /**
   * @return the code coverage custom script
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCodeCoverageCustomScript() throws RemoteException;

  /**
   * Set the finalize script
   * 
   * @param finalizeScript
   *          the new finalize script
   * @throws RemoteException
   *           remote communication problem
   */
  public void setFinalizeScript(String finalizeScript) throws RemoteException;

  /**
   * @return the finalize script
   * @throws RemoteException
   *           remote communication problem
   */
  public String getFinalizeScript() throws RemoteException;

  /**
   * Returns the environment variables for the MATLAB/Simulink platform configuration as specified
   * by the user. This list corresponds to the "Environments Variables" tab of the MATLAB/Simulink
   * platform configuration GUI.
   * 
   * @return User attributes as list.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<Pair<String, String>> getEnvironmentVariablesList() throws RemoteException;

  /**
   * Set a list of user-defined environment variables given by <code>pair.first</code> to the value
   * given by the <code>pair.second</code> parameter.
   * 
   * @param attributes
   *          user-defined attribute list
   * 
   * @throws ApiException
   *           if
   *           <code>attributes==null || pair.second == null || pair.first==null || pair.first.contains("<span>&#92;</span>n") </code>
   * @throws RemoteException
   *           remote communication problem
   */
  public void setEnvironmentVariablesList(List<Pair<String, String>> attributes)
      throws ApiException, RemoteException;

  /**
   * Import the interface from the configured original model and subsystem block path (default all)
   * 
   * @param syncMethod
   *          Should the objects be synchronized by name or by external name
   * @throws RemoteException
   *           remote communication problem
   */
  public void importIO(SynchronizationMethod syncMethod) throws RemoteException;

  /**
   * Import any offline loggings that are activated in the model but not registered in TPT.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void importOfflineLoggings() throws RemoteException;

  /**
   * Generate the test frame for the model specified in the MATLAB-configuration.
   * 
   * @param reuseExistingInterfaceData
   *          Specify if interface data from a previous interface import that still exists in the
   *          MATLAB workspace shall be used
   * @throws RemoteException
   *           remote communication problem
   */
  public void generateTestframe(boolean reuseExistingInterfaceData) throws RemoteException;

  /**
   * Generate the model for FUSION (named "&lt;testframe&gt;_fusion") from the test frame specified
   * in the MATLAB-configuration. This model model will then automatically be built into a custom
   * fusion node dll file using the Simulink Coder or Embedded Coder (unless the
   * <code>prepareForBuildOnly</code> flag is set).
   * 
   * @param prepareForBuildOnly
   *          Set to <code>true</code> if the model for FUSION shall only be generated and not be
   *          built automatically.
   * @throws RemoteException
   *           remote communication problem
   */
  public void buildModelForFusion(boolean prepareForBuildOnly) throws RemoteException;

  /**
   * Generate the TPT I/O file (see {@link #getIOFilePath()} for details). If this file already
   * exists it will be overwritten.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void generateIOFile() throws RemoteException;

  /**
   * Open the original model in MATLAB via running the configured original model load script in
   * MATLAB.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void openOriginalModel() throws RemoteException;

  /**
   * Open the test frame model in MATLAB via running the configured test frame model load script in
   * MATLAB.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void openTestframeModel() throws RemoteException;

  /**
   * Add a measurement to project declarations and configure the mapping-data for offline logging
   * with TPT. The settings are not automatically applied in MATLAB.
   * 
   * <p>
   * Note: This is <b>for internal use only</b> and may change in future versions of TPT.
   * </p>
   * 
   * @param tptName
   *          The name of the new Measurement
   * @param typeStr
   *          The type string of the new Measurement
   * @param defaultValueStr
   *          The default value of the new Measurement
   * @param sourceBlockPath
   *          The source block path of the simulink signal that shall be logged
   * @param portNumber
   *          The source port number of the simulink signal that shall be logged
   * @param slLogName
   *          The simulink logging name
   * @param tlLogdataStructName
   *          The TargetLink log data struct name (empty if simulink logging is used)
   * @param useTlddLogging
   *          Enable to use logging via TargetLink DD, otherwise block-logging is used
   * @throws RemoteException
   *           remote communication problem
   */
  public void registerOfflineLogging(String tptName, String typeStr, String defaultValueStr,
                                     String sourceBlockPath, int portNumber, String slLogName,
                                     String tlLogdataStructName, boolean useTlddLogging)
      throws RemoteException;

}
