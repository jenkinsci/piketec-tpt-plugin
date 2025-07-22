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
package com.piketec.tpt.api.autosarplatform;

import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.PlatformConfiguration;
import com.piketec.tpt.api.Project.SynchronizationMethod;
import com.piketec.tpt.api.RemoteCollection;
import com.piketec.tpt.api.RemoteList;
import com.piketec.tpt.api.codecoverage.CTCCoverageSettings;
import com.piketec.tpt.api.codecoverage.CoverageSettings;
import com.piketec.tpt.api.codecoverage.GCovCoverageSettings;
import com.piketec.tpt.api.codecoverage.TPTCoverageSettings;

/**
 * The TPT API representation of the AUTOSAR platform in TPT
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface AutosarPlatformConfiguration extends PlatformConfiguration {

  /**
   * String for setting the default compiler in
   * {@link AutosarPlatformConfiguration#setCompiler(String)}.
   */
  public static final String DEFAULT_COMPILER = "<DEFAULT>";

  /**
   * Sets the path to the project root folder.
   * 
   * @param path
   *          The new path to the project root folder
   *
   * @throws RemoteException
   *           remote communication problem
   */
  public void setProjectRootFolderPath(String path) throws RemoteException;

  /**
   * Gets the path to the project root folder.
   * 
   * @return The path to the project root folder
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getProjectRootFolderPath() throws RemoteException;

  /**
   * Sets the path to the output folder.
   * 
   * @param path
   *          The new path to the output folder
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOutputFolderPath(String path) throws RemoteException;

  /**
   * Gets the path to the output folder.
   * 
   * 
   * @return The path to the output folder
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getOutputFolderPath() throws RemoteException;

  /**
   * Sets the name of the compiler to be used
   * 
   * @param name
   *          The name of the desired compiler. Use {@link #DEFAULT_COMPILER} to set the default
   *          compiler.
   * @throws ApiException
   *           if name is empty or null
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCompiler(String name) throws ApiException, RemoteException;

  /**
   * @return The name of the currently used compiler
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCompiler() throws RemoteException;

  /**
   * Sets additional compiler options
   * 
   * @param compilerOptions
   *          The compiler options to be applied
   * @throws ApiException
   *           if compilerOptions is null
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCompilerOptions(String compilerOptions) throws ApiException, RemoteException;

  /**
   * @return The compiler Options or an empty String if none are set
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCompilerOptions() throws RemoteException;

  /**
   * Configure if the test frame should be compiled in 64 bit
   * 
   * @param is64Bit
   *          <code>true</code> if the code should be compiled in 64 bit, <code>false</code>
   *          otherwise
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void set64Bit(boolean is64Bit) throws RemoteException;

  /**
   * @return <code>true</code> if the test frame will be compiled in 64 bit, <code>false</code>
   *         otherwise
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean is64Bit() throws RemoteException;

  /**
   * Add an arxml file. This paths must be relative to the configured project root folder for this
   * platform (see {@link AutosarPlatformConfiguration#getProjectRootFolderPath()}.
   * 
   * @param path
   *          The new path to Add
   * 
   * @throws ApiException
   *           In case the added path is invalid or already contained.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void addArxmlFile(String path) throws ApiException, RemoteException;

  /**
   * Get the paths of the currently selected arxml files. All paths are relative to the configured
   * project root folder for this platform (see
   * {@link AutosarPlatformConfiguration#getProjectRootFolderPath()}.
   * 
   * @return A remote collection with the selected configured arxml files
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<String> getArxmlFiles() throws RemoteException;

  /**
   * Set the AUTOSAR SWC that shall be tested. This must be the short name from the arxml.
   * 
   * @param swc
   *          The short name of the AUTOSAR SWC
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSelectedSWC(String swc) throws RemoteException;

  /**
   * Get the AUTOSAR SWC that shall be tested. This is the short name from the arxml.
   * 
   * @return The short name of the AUTOSAR SWC
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getSelectedSWC() throws RemoteException;

  /**
   * Add a system constant value set that shall be used. This must be the short name from the arxml.
   * 
   * @param name
   *          The short name of the system constant value set.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void addSystemConstantValueSet(String name) throws RemoteException;

  /**
   * @return A remote collection with the configured system constant value sets. The entries are the
   *         short names from the arxml.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<String> getSystemConstantValueSets() throws RemoteException;

  /**
   * Add a source file. This paths must be relative to the configured project root folder for this
   * platform (see {@link AutosarPlatformConfiguration#getProjectRootFolderPath()}.
   * 
   * @param path
   *          The new path to Add
   * 
   * @throws ApiException
   *           In case the added path is invalid or already contained.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void addSourceFile(String path) throws ApiException, RemoteException;

  /**
   * Get the paths of the currently selected source files. All paths are relative to the configured
   * project root folder for this platform (see
   * #{@link AutosarPlatformConfiguration#getProjectRootFolderPath()}.
   * 
   * @return A remote collection with the selected configured source files
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<String> getSourceFiles() throws RemoteException;

  /**
   * Add a path to the list of additional include folders. This path may be absolute or relative to
   * the project root folder for this platform.
   * 
   * @param path
   *          The new path to Add
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void addAdditionalIcludeFolder(String path) throws RemoteException;

  /**
   * Get the currently configured additional included paths.
   * 
   * @return A remote list with the currently configured additional include paths
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<String> getAdditionalIcludeFoldersList() throws RemoteException;

  /**
   * Set the stub header file
   * 
   * @param stubHeaderFile
   *          the new stub header file
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setStubHeaderFile(String stubHeaderFile) throws RemoteException;

  /**
   * Get the configured stub header files (separated by comma)
   * 
   * @return the configured stub header files
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getStubHeaderFiles() throws RemoteException;

  /**
   * @param importParameterCurveMapTypes
   *          <code>true</code> to import application data type with category CURVE or MAP for
   *          parameters as curve/map type to TPT, <code>false</code> to import them with the
   *          underlying implementation data type instead.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setImportParameterCurveMapTypes(boolean importParameterCurveMapTypes)
      throws RemoteException;

  /**
   * @return <code>true</code> if importing application data type with category CURVE or MAP for
   *         parameters as curve/map type to TPT, <code>false</code> otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isImportParameterCurveMapTypes() throws RemoteException;

  /**
   * @param importFunctionReturnValues
   *          <code>true</code> if TPT is supposed to import channels to modify the return values of
   *          functions returning error codes
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setImportFunctionReturnValues(boolean importFunctionReturnValues)
      throws RemoteException;

  /**
   * @return <code>true</code> if TPT is supposed to import channels to modify the return values of
   *         functions returning error codes
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isImportFunctionReturnValues() throws RemoteException;

  /**
   * @param assumeAllPortElementsExplicitAccess
   *          <code>true</code> to enable the "assume explicit access for all port elements" option,
   *          <code>false</code> to disable it
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAssumeAllPortElementsExplicitAccess(boolean assumeAllPortElementsExplicitAccess)
      throws RemoteException;

  /**
   * @return <code>true</code> if the "assume explicit access for all port elements" option enabled,
   *         <code>false</code> otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isAssumeAllPortElementsExplicitAccess() throws RemoteException;

  /**
   * @return <code>true</code> if using repository files is enabled, <code>false</code> otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isUseRepository() throws RemoteException;

  /**
   * Configure if arxml and source files from the AUTOSAR repository files should be used.
   * 
   * @param useRepository
   *          <code>true</code> to enable using repository files, <code>false</code> to disable it
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setUseRepository(boolean useRepository) throws RemoteException;

  /**
   * Get the current state of the "use effective interface" option
   * 
   * @return Is the the "use effective interface" option enabled
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isUseEffectiveInterface() throws RemoteException;

  /**
   * Set the "use effective interface" option
   * 
   * @param useEffectiveInterface
   *          En- or disable the "use effective interface" option
   * @throws RemoteException
   *           remote communication problem
   */
  public void setUseEffectiveInterface(boolean useEffectiveInterface) throws RemoteException;

  /**
   * Get the current state of the "include I/O consistency check" option
   * 
   * @return Is the "include I/O consistency check" option enabled
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isIncludeIOConsistencyCheck() throws RemoteException;

  /**
   * Set the "include I/O consistency check" option
   *
   * @param useIncludeIOConsistencyCheck
   *          En- or disable the "include I/O consistency check" option
   * @throws RemoteException
   *           remote communication problem
   */
  public void setIncludeIOConsistencyCheck(boolean useIncludeIOConsistencyCheck)
      throws RemoteException;

  /**
   * Get the current state of the "round scaling results" option
   * 
   * @return Is the "round scaling results" option enabled
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isRoundScalingResults() throws RemoteException;

  /**
   * Set the "round scaling results" option
   * 
   * @param useRoundScalingInterface
   *          En- or disable the "round scaling results" option
   * @throws RemoteException
   *           remote communication problem
   */
  public void setRoundScalingResults(boolean useRoundScalingInterface) throws RemoteException;

  /**
   * Add an arxml file from the repository. This paths must be relative to the repository root
   * folder.
   * 
   * @param path
   *          The new path to Add
   * 
   * @throws ApiException
   *           In case the added path is invalid or already contained.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void addRepositoryArxmlFile(String path) throws ApiException, RemoteException;

  /**
   * Get the paths of the currently selected arxml files from the repository. All paths are relative
   * to the configured repository root folder.
   * 
   * @return A remote collection with the currently selected arxml files from the repository
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<String> getRepositoryArxmlFiles() throws RemoteException;

  /**
   * Add a source file from the repository. This paths must be relative to the repository root
   * folder.
   * 
   * @param path
   *          The new path to Add
   * 
   * @throws ApiException
   *           In case the added path is invalid or already contained.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void addRepositorySourceFile(String path) throws ApiException, RemoteException;

  /**
   * Get the paths of the currently selected source files from the repository. All paths are
   * relative to the configured repository root folder.
   * 
   * @return A remote collection with the currently selected source files from the repository
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<String> getRepositorySourceFiles() throws RemoteException;

  /**
   * Add a path to the list of include folders from the repository. This path may be absolute or
   * relative to the repository root folder.
   * 
   * @param path
   *          The new path to Add
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void addRepositoryIcludeFolder(String path) throws RemoteException;

  /**
   * Get the currently configured included paths from the repository.
   * 
   * @return A remote list with the currently configured include paths from the repository
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<String> getRepositoryIncludeFoldersList() throws RemoteException;

  /**
   * Refresh the list of runnables from the arxml with the current settings.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void refreshRunnables() throws RemoteException;

  /**
   * Get the runnables imported from the arxml files.
   * 
   * @return A remote list with the runnables
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<RunnableSchedulingItem> getRunnables() throws RemoteException;

  /**
   * @return The configured custom interface file path
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCustomInterfaceFilePath() throws RemoteException;

  /**
   * Set the custom interface file path.
   * 
   * @param path
   *          the new custom interface file path
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCustomInterfaceFilePath(String path) throws RemoteException;

  /**
   * Enable or disable using a system to use its data mapping for sender-receiver elements. If
   * enabled the system to be used can be set via {@link #setSystemForSignalMapping(String)}.
   * 
   * @param useSystemMapping
   *          <code>true</code> to enable using a system data mapping, <code>false</code> to disable
   *          it
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setUseSystemMapping(boolean useSystemMapping) throws RemoteException;

  /**
   * @return <code>true</code> if using a system data mapping is enabled, <code>false</code> if it
   *         is disabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isUseSystemMapping() throws RemoteException;

  /**
   * Set the name of the system from which the data mapping for sender-receiver elements shall be
   * used. This must be the short name from the arxml.
   * 
   * @param systemName
   *          the short name of the the AUTOSAR system whose data mapping shall be used for
   *          sender-receiver elements
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSystemForSignalMapping(String systemName) throws RemoteException;

  /**
   * Get the name of the system whose data mapping shall be used for sender-receiver elements.
   * 
   * @return the short name of the the AUTOSAR system whose data mapping shall be used for
   *         sender-receiver elements
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getSystemForSignalMapping() throws RemoteException;

  /**
   * Import the interface based on the current settings (default all)
   * 
   * @param syncMethod
   *          Should the objects be synchronized by name or by external name
   * @return A list with warnings that eventually occured, empty list if none occured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> importIO(SynchronizationMethod syncMethod) throws RemoteException;

  /**
   * Generate the test frame code and optionally compile it.
   * 
   * @param compile
   *          Immediately compile the code after generation
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void generateCode(boolean compile) throws RemoteException;

  /**
   * @return If the coverage data will be cumulated
   *
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isCumulateCoverage() throws RemoteException;

  /**
   * Set if the coverage data will be cumulated
   * 
   * @param cumulate
   *          If the coverage data will be cumulated
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCumulateCoverage(boolean cumulate) throws RemoteException;

  /**
   * Get the current coverage settings
   * 
   * @return The current coverage settings or <code>null</code> if coverage is disabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public CoverageSettings getCoverageSettings() throws RemoteException;

  /**
   * Enable CTC++ coverage measurement to be used for this platform
   * 
   * @return The CTC++ coverage settings that will be used
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public CTCCoverageSettings setCoverageToCTC() throws RemoteException;

  /**
   * Enable GNU gcov coverage measurement to be used for this platform
   * 
   * @return The GNU gcov coverage settings that will be used
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public GCovCoverageSettings setCoverageToGCov() throws RemoteException;

  /**
   * Enable TPT coverage measurement to be used for this platform, enables TASMO test case
   * generation
   * 
   * @return The TPT coverage settings that will be used
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TPTCoverageSettings setCoverageToTPT() throws RemoteException;

  /**
   * Disable any sort of code coverage measurement in this platform
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void disableCoverage() throws RemoteException;

}
