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
package com.piketec.tpt.api.cplatform;

import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.PlatformConfiguration;
import com.piketec.tpt.api.Project.SynchronizationMethod;
import com.piketec.tpt.api.RemoteIndexedList;
import com.piketec.tpt.api.RemoteList;
import com.piketec.tpt.api.TptApi;
import com.piketec.tpt.api.codecoverage.CTCCoverageSettings;
import com.piketec.tpt.api.codecoverage.CoverageSettings;
import com.piketec.tpt.api.codecoverage.GCovCoverageSettings;
import com.piketec.tpt.api.codecoverage.TPTCoverageSettings;

/**
 * The TPT API representation of the C\C++ platform in TPT
 * 
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface CCodePlatformConfiguration extends PlatformConfiguration {

  /**
   * Set the path to the project root folder.
   * 
   * @param path
   *          The new path to the project root folder.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setProjectRootFolderPath(String path) throws RemoteException;

  /**
   * Get the path to the project root folder.
   * 
   * @return The path to the project root folder
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getProjectRootFolderPath() throws RemoteException;

  /**
   * Set the path to the output folder.
   * 
   * @param path
   *          The new path to the output folder
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOutputFolderPath(String path) throws RemoteException;

  /**
   * Get the path to the output folder.
   * 
   * @return The path to the output folder
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getOutputFolderPath() throws RemoteException;

  /**
   * Get the sources currently contained in this platform
   * 
   * @return The list of the currently set source files
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<SourceFileItem> getSourceFileItems() throws RemoteException;

  /**
   * Add a new source file with default settings to this configuration
   * 
   * @param path
   *          The path of this source file relative to the TPT project
   * @return The new created source file
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public SourceFileItem createSourceFileItem(String path) throws RemoteException;

  /**
   * Set the additional wrapper code
   * 
   * @param wrapperCode
   *          The additional wrapper code
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setWrapperCode(String wrapperCode) throws RemoteException;

  /**
   * Get the additional wrapper code
   * 
   * @return The additional wrapper code, may be empty
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getWrapperCode() throws RemoteException;

  /**
   * @param wrapperIsCpp
   *          Set to true if the wrapper code is to be treated as C++. Set to false if the wrapper
   *          code is to be treated as C.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setWrappperIsCpp(boolean wrapperIsCpp) throws RemoteException;

  /**
   * 
   * @return True if the wrapper code is set to be treated as C++. False if the wrapper code is set
   *         to be treated as C.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean getWrappperIsCpp() throws RemoteException;

  /**
   * @param extraOptions
   *          Set the extra compiler options for parsing or compiling the wrapper code.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setWrapperExtraCompilerOptions(String extraOptions) throws RemoteException;

  /**
   * @return The extra compiler options for parsing or compiling the wrapper code.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getWrapperExtraCompilerOptions() throws RemoteException;

  /**
   * Set the name of the compiler to be used
   * 
   * @param name
   *          The name of the desired compiler. Use {@link TptApi#DEFAULT_COMPILER} to set the
   *          default compiler.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCompiler(String name) throws RemoteException;

  /**
   * @return The name of the currenty used compiler
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCompiler() throws RemoteException;

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
   * Set the TASMO instrumentation level
   * 
   * @param instrumentationLevel
   *          The TASMO instrumentation level
   * 
   * @deprecated Use {@link #setCoverageToTPT()} to enable TPT coverage which is necessary for Tasmo
   *             testcase generation.<br>
   *             From TPT-19 Tasmo coverage can only be used if code coverage is measured with TPT
   *             coverage. For compatibility setting this to FULL_INSTRUMENTATION or
   *             COVERAGE_MEASUREMENT will enable TPT-Coverage and thus enable coverage measurement
   *             or TASMO testcase generation. Setting this to NO_INSTRUMENTATION will disable Tasmo
   *             coverage if it was enabled before.<br>
   *             Will be removed in TPT-23.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public void setTasmoInstrumentationLevel(TasmoCCodeInstrumentationLevel instrumentationLevel)
      throws RemoteException;

  /**
   * Get the TASMO instrumentation level
   * 
   * @return The TASMO instrumentation level, FULL_INSTRUMENTATION if TPT-Coverage is enabled and
   *         NONE if TPT-Coverage is not enabled.
   * 
   * @deprecated Use {@link #getCoverageSettings()} and check if the result is an instance of
   *             <code>TPTCoverageSettings</code> to check if the platform will be instrumented.<br>
   *             From TPT-19 a C\C++ platform can either be instrumented or not. For compatibility
   *             this method returns FULL_INSTRUMENTATION if TPT-Coverage is selected as coverage
   *             tool and <code>null</code> if TPT-Coverage is not enabled.<br>
   *             Will be removed in TPT-23.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public TasmoCCodeInstrumentationLevel getTasmoInstrumentationLevel() throws RemoteException;

  /**
   * Get the current state of the "use effective interface" option
   * 
   * @return Is the "use effective interface" option enabled
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
   * Get the current state of the "initialize interface variables with pointer types in C" option
   * 
   * @return Is the "initialize interface variables with pointer types in C" option enabled
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isInitInterfaceVariablesWithPointerTypesInC() throws RemoteException;

  /**
   * Set the "initialize interface variables with pointer types in C" option
   * 
   * @param isInitInterfaceVariablesWithPointerTypesInC
   *          En- or disable the "initialize interface variables with pointer types in C" option
   * @throws RemoteException
   *           remote communication problem
   */
  public void setInitInterfaceVariablesWithPointerTypesInC(boolean isInitInterfaceVariablesWithPointerTypesInC)
      throws RemoteException;

  /**
   * Get the current state of the "enable read/write for output channels" option
   * 
   * @return Is the "enable read/write for output channels" option enabled
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isEnableReadWriteForOutputChannels() throws RemoteException;

  /**
   * Set the "enable read/write for output channels" option
   * 
   * @param isEnableReadWriteForOutputChannels
   *          En- or disable the "enable read/write for output channels" option
   * @throws RemoteException
   *           remote communication problem
   */
  public void setEnableReadWriteForOutputChannels(boolean isEnableReadWriteForOutputChannels)
      throws RemoteException;

  /**
   * Get the current state of the "read initial values for output channels from SUT" option
   * 
   * @return Is the "read initial values for output channels from SUT" option enabled
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isReadInitialValuesForOutputChanneslFromSUT() throws RemoteException;

  /**
   * Set the "read initial values for output channels from SUT" option
   * 
   * @param isReadInitialValuesForOutputChanneslFromSUT
   *          En- or disable the "read initial values for output channels from SUT" option
   * @throws RemoteException
   *           remote communication problem
   */
  public void setReadInitialValuesForOutputChanneslFromSUT(boolean isReadInitialValuesForOutputChanneslFromSUT)
      throws RemoteException;

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
   * Generate the code to connect your c-code to TPT.
   * 
   * @param compile
   *          Immediately compile the code after generation
   * @return A list with warnings that eventually occured, empty list if none occured
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> generateCode(boolean compile) throws RemoteException;

  /**
   * @return The list of the include folders
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<IncludeFolder> getIncludeFolders() throws RemoteException;

  /**
   * Add a new include folder
   * 
   * @param path
   *          The path of the new include folder
   * @return The new include folder
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public IncludeFolder addIncludeFolder(String path) throws RemoteException;

  /**
   * Get the list of scheduled functions
   * 
   * @return The functions to be scheduled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<FunctionSchedulingItem> getSchedulingItems() throws RemoteException;

  /**
   * The interface of the currently set c files is refreshed based on the sources. To access the
   * imported interface afterwards use {@link #getSourcesInterface()}.
   * 
   * @return A list with warnings that eventually occured, empty list if none occured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> analyzeSources() throws RemoteException;

  /**
   * @return The list of imported sources interfaces, without the wrapper and the unresolved
   *         references. To update the interface based on the current sources use
   *         {@link #analyzeSources()}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<SourceInterface> getSourcesInterface() throws RemoteException;

  /**
   * @return The imported settings for the given source file, <code>null</code> if no
   *         SourceInterface matching this SourceFileItem could be found.
   * @param sourceFile
   *          The relevant source file object
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public SourceInterface getImportedCCodeBySource(SourceFileItem sourceFile) throws RemoteException;

  /**
   * @return The settings to connect unresolved references
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public SourceInterface getUnresolvedReferences() throws RemoteException;

  /**
   * @return The settings to connect the objects in the wrapper code
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public SourceInterface getWrapperInterface() throws RemoteException;

  /**
   * Import the interface based on the current source interface settings (default all).
   * 
   * Note: The scheduling table will not be updated here. See {@link #syncSchedulingItems()} for
   * that purpose.
   * 
   * @param syncMethod
   *          Should the objects be synchronized by name or by external name
   * @return A list with warnings that eventually occured, empty list if none occured
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> importIO(SynchronizationMethod syncMethod) throws RemoteException;

  /**
   * Synchronize the scheduling table with the current source interface settings. Functions where
   * the {@link FunctionConnectionType} is set to {@link FunctionConnectionType#SCHEDULE} will be
   * added to the scheduling table. Functions where the {@link FunctionConnectionType} is set to
   * anything else will be removed from the table. Use {@link #getSchedulingItems()} to access and
   * edit the scheduling setting for the individual functions afterwards.
   * 
   * @see SourceInterface#setFunctionConnectionType(String, FunctionConnectionType)
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void syncSchedulingItems() throws RemoteException;

  /**
   * Set the linker options used when generating the test frame. When calling this function,
   * <code>useCustomLinkerOptions</code> is set to <code>true</code>.
   * 
   * @param linkerOptions
   *          The new options for the linker
   * @throws RemoteException
   *           remote communication problem
   */
  public void setLinkerOptions(String linkerOptions) throws RemoteException;

  /**
   * Get the linker options used when generating the test frame
   * 
   * @return The current linker options
   * @throws RemoteException
   *           remote communication problem
   */
  public String getLinkerOptions() throws RemoteException;

  /**
   * Set to use the custom linker options
   * 
   * @param shouldTheCustomLinkerOptionsBeUsed
   *          Use the custom linker options
   * @throws RemoteException
   *           remote communication problem
   */
  public void setUseCustomLinkerOptions(boolean shouldTheCustomLinkerOptionsBeUsed)
      throws RemoteException;

  /**
   * Get if the custom linker options are getting used
   * 
   * @return If custom linker options are getting used
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isUseCustomLinkerOptions() throws RemoteException;

  /**
   * Set the compiler options used when generating the test frame. When calling this function,
   * <code>useCustomCompilerOptions</code> is set to <code>true</code>.
   * 
   * @param compilerOptions
   *          The new arguments for the compiler
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCompilerOptions(String compilerOptions) throws RemoteException;

  /**
   * Get the compiler options used when generating the test frame
   * 
   * @return The current compiler options
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCompilerOptions() throws RemoteException;

  /**
   * Set to use the custom compiler options
   * 
   * @param shouldTheCustomCompilerOptionsBeUsed
   *          Use the custom compiler options
   * @throws RemoteException
   *           remote communication problem
   */
  public void setUseCustomCompilerOptions(boolean shouldTheCustomCompilerOptionsBeUsed)
      throws RemoteException;

  /**
   * Get if the custom compiler options are getting used
   * 
   * @return If custom compiler options are getting used
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isUseCustomCompilerOptions() throws RemoteException;

  /**
   * Get the macro definitions
   * 
   * @return An indexed list containing all macro definitions
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteIndexedList<String, CCodeDefine> getDefinitions() throws RemoteException;

  /**
   * Add a macro definition or overwrite an existing one
   * 
   * @param name
   *          The name of the new definition
   * @param definition
   *          The new value of that definition, <code>null</code> will be treated like a definition
   *          without a value
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   */
  public void setDefinition(String name, String definition) throws RemoteException;

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

  /**
   * Sets path of the A2L file.
   * 
   * @param a2lFilePath
   *          relative or absolute path to the A2L file. If null is passed, the currently set path
   *          will be removed.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setA2LFile(String a2lFilePath) throws RemoteException;

  /**
   * Adds the information of a JSON file generated by cmake to this configuration. Previous
   * preprocessor defines will be overwritten.
   * 
   * @param jsonFile
   *          The path to the JSON file to be imported
   * 
   * @return If warnings occured during the import, empty string if no warnings occured.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the file cannot be imported
   */
  public String importSourceFromCMakeJSonFile(String jsonFile) throws RemoteException, ApiException;

  /**
   * Refresh includes from the source files. Searches all source files for includes, adding the new
   * include paths and updating changed include paths.
   * 
   * @return List with paths to includes or null due to an unexpected error.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> refreshIncludePaths() throws RemoteException;
}
