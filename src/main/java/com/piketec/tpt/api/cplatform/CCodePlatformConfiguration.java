/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2021 PikeTec GmbH
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

import com.piketec.tpt.api.PlatformConfiguration;
import com.piketec.tpt.api.Project.SynchronizationMethod;
import com.piketec.tpt.api.RemoteList;

/**
 * The TPT API representation of the C\C++ platform in TPT
 * 
 * 
 * @author Copyright (c) 2014-2021 Piketec GmbH - MIT License (MIT) - All rights reserved
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
   *          The name of the desired compiler
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
   * @param instrumenationLevel
   *          The TASMO instrumentation level
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTasmoInstrumentationLevel(TasmoCCodeInstrumentationLevel instrumenationLevel)
      throws RemoteException;

  /**
   * Get the TASMO instrumentation level
   * 
   * @return The TASMO instrumentation level
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TasmoCCodeInstrumentationLevel getTasmoInstrumentationLevel() throws RemoteException;

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
   * Import the interface based on the current tree settings (default all)
   * 
   * @param syncMethod
   *          Should the objects be synchronized by name or by external name
   * @return A list with warnings that eventually occured, empty list if none occured
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> importIO(SynchronizationMethod syncMethod) throws RemoteException;

  /**
   * Set the linker options used when linking the test frame
   * 
   * @param linkerOptions
   *          The new options for the linker
   * @throws RemoteException
   *           remote communication problem
   */
  public void setLinkerOptions(String linkerOptions) throws RemoteException;

  /**
   * Get the linker options used when linking the test frame
   * 
   * @return The current linker options
   * @throws RemoteException
   *           remote communication problem
   */
  public String getLinkerOptions() throws RemoteException;

  /**
   * Set the compiler options used when linking the test frame
   * 
   * @param compilerOptions
   *          The new arguments for the compiler
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCompilerOptions(String compilerOptions) throws RemoteException;

  /**
   * Get the compiler options used when linking the test frame
   * 
   * @return The current compiler options
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCompilerOptions() throws RemoteException;

}
