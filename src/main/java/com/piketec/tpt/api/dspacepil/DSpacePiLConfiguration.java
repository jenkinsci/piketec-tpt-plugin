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
package com.piketec.tpt.api.dspacepil;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.PlatformConfiguration;
import com.piketec.tpt.api.matlabplatform.MatlabPlatformConfiguration;

/**
 * The TPT API representation of the dSpace PiL platform in TPT
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface DSpacePiLConfiguration extends PlatformConfiguration {

  /**
   * Automatically applies all the settings from the referenced MATLAB Platform. See
   * {@link #setMatlabSyncPlatform(MatlabPlatformConfiguration)} to specify the reference MATLAB
   * Platform
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if settings are missing or the reference Platform was not set.
   */
  public void synchronizeFromReference() throws RemoteException, ApiException;

  /**
   * Gets the current Working directory, by default the working directory is set to .pilData
   * 
   * @return the current Working directory
   * @throws RemoteException
   *           remote communication problem
   */
  public String getWorkingDirectory() throws RemoteException;

  /**
   * Sets the Working directory
   * 
   * @param workingDirectory
   *          the Working directory to be used
   * @throws RemoteException
   *           remote communication problem
   */
  public void setWorkingDirectory(String workingDirectory) throws RemoteException;

  /**
   * Gets the currently selected Matlab/Simulink reference configuration
   * 
   * @return the currently selected Matlab/Simulink configuration
   * @throws RemoteException
   *           remote communication problem
   */
  public MatlabPlatformConfiguration getMatlabSyncPlatform() throws RemoteException;

  /**
   * Sets the MATLAB/Simulink reference configuration
   * 
   * @param configuration
   *          the MATLAB/Simulink configuration to be selected, must not be <code>null</code>
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the configuration is not part of the current project
   */
  public void setMatlabSyncPlatform(MatlabPlatformConfiguration configuration)
      throws RemoteException, ApiException;

  /**
   * Gets the TargetLink version
   * 
   * @return the current TargetLink version
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTargetLinkVersion() throws RemoteException;

  /**
   * Sets the TargetLink version
   * 
   * @param targetLinkVersion
   *          the TargetLink version to be used
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTargetLinkVersion(String targetLinkVersion) throws RemoteException;

  /**
   * Gets the TargetLink name of the target PiL board or simulator
   * 
   * @return the current board name
   * @throws RemoteException
   *           remote communication problem
   */
  public String getBoardName() throws RemoteException;

  /**
   * Sets the TargetLink name of the target PiL board or simulator
   * 
   * @param boardName
   *          the board name to be used
   * @throws RemoteException
   *           remote communication problem
   */
  public void setBoardName(String boardName) throws RemoteException;

  /**
   * Gets the directory of the TargetLink simulation handler, i.e. the binary 'TLLoader.exe' of the
   * dSPACE TargetLink installation
   * 
   * @return the current TargetLink simulation handler directory
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTargetLinkSimulationHandlerDirPath() throws RemoteException;

  /**
   * Sets the directory of the TargetLink simulation handler, i.e. the binary 'TLLoader.exe' of the
   * dSPACE TargetLink installation
   * 
   * @param targetLinkSimulationHandlerDir
   *          the TargetLink simulation handler directory to be used
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTargetLinkSimulationHandlerDirPath(String targetLinkSimulationHandlerDir)
      throws RemoteException;

  /**
   * Gets the XML configuration file path for the TargetLink simulation module which specifies the
   * simulation and communication server
   * 
   * @return the current board configuration file path
   * @throws RemoteException
   *           remote communication problem
   */
  public String getBoardConfigurationFilePath() throws RemoteException;

  /**
   * Sets the XML configuration file path for the TargetLink simulation module which specifies the
   * simulation and communication server
   * 
   * @param boardConfigurationFile
   *          the TargetLink board configuration file path to be used
   * @throws RemoteException
   *           remote communication problem
   */
  public void setBoardConfigurationFilePath(String boardConfigurationFile) throws RemoteException;

  /**
   * Gets the XML configuration file path for the TargetLink simulation module which specifies the
   * target simulator
   * 
   * @return the current configuration file path
   * @throws RemoteException
   *           remote communication problem
   */
  public String getUserConfigurationFilePath() throws RemoteException;

  /**
   * Sets the XML configuration file path for the TargetLink simulation module which specifies the
   * target simulator
   * 
   * @param userConfigurationFile
   *          the configuration file path to be used
   * @throws RemoteException
   *           remote communication problem
   */
  public void setUserConfigurationFilePath(String userConfigurationFile) throws RemoteException;

  /**
   * Gets the target application file path to be used as system under test (SUT)
   * 
   * @return the current target application file path
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTargetApplicationFilePath() throws RemoteException;

  /**
   * Sets the target application file path which will be used as system under test (SUT)
   * 
   * @param targetApplicationFile
   *          the target application file path to be used
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTargetApplicationFilePath(String targetApplicationFile) throws RemoteException;

}
