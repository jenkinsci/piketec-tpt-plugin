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
package com.piketec.tpt.api.cplatform;

import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.IdentifiableRemote;

/**
 * This class represents the imported sources interface of a single source file, the "unresolved
 * references" or the wrapper code
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface SourceInterface extends IdentifiableRemote {

  /**
   * @return The source file associated with this imported sources, <code>null</code> if this
   *         SourceInterface is based on wrapper code or on "unresolved references" and variables
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public SourceFileItem getSourceFileItem() throws RemoteException;

  /**
   * Check if a variable will be connected to TPT
   * 
   * @param variableName
   *          Name of the variable
   * @return If the variable will be connected to TPT
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isVariableConnected(String variableName) throws RemoteException;

  /**
   * Should the variable be connected to TPT
   * 
   * @param variableName
   *          Name of the variable
   * @param isConnected
   *          Will the variable be connected to TPT
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setVariableConnected(String variableName, boolean isConnected) throws RemoteException;

  /**
   * Determine the ConnectionType of the function
   * 
   * @param functionName
   *          The name of the function
   * @return The FunctionConnectionType of the function, <code>null</code> if the function does not
   *         exist within the class of this object
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public FunctionConnectionType getFunctionConnectionType(String functionName)
      throws RemoteException;

  /**
   * Set the way the function will be connected to TPT
   * 
   * @param functionName
   *          The name of the function
   * @param connectionType
   *          The desired FunctionConnectionType
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setFunctionConnectionType(String functionName, FunctionConnectionType connectionType)
      throws RemoteException;

  /**
   * Get the variables in this source file.
   * 
   * @return The names of the variables in this source file.
   *
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> getVariables() throws RemoteException;

  /**
   * Get the class instances contained in the associated file
   * 
   * @return The names of the class instances contained in the associated file.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> getClassInstances() throws RemoteException;

  /**
   * Get the functions contained in the associated source file
   * 
   * @return The names of the functions contained in the associated file.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> getFunctions() throws RemoteException;

  /**
   * Determines the number of function arguments
   * 
   * @param functionName
   *          The name of the function
   * @return The number of arguments in the function declaration
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getNumOfFunctionArguments(String functionName) throws RemoteException;

  /**
   * Determines the name of a function argument in the imported source code
   * 
   * @param functionName
   *          The name of the function
   * @param argumentIndex
   *          The index of the relevant argument
   * @return The name of the function argument
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getFunctionArgumentName(String functionName, int argumentIndex)
      throws RemoteException;

  /**
   * Determines the channel name of a function argument that will be imported to TPT
   * 
   * @param functionName
   *          The name of the function
   * @param argumentIndex
   *          The index of the relevant argument
   * @return The name of the channel that will be used for the function argument in TPT
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getScheduledFunctionArgumentChannelName(String functionName, int argumentIndex)
      throws RemoteException;

  /**
   * Sets the channel name of a function argument that will be imported to TPT
   * 
   * @param functionName
   *          The name of the function
   * @param argumentIndex
   *          The index of the relevant argument
   * @param channelName
   *          The name used to import the function argument to a channel in TPT
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setScheduledFunctionArgumentChannelName(String functionName, int argumentIndex,
                                                      String channelName)
      throws RemoteException;

  /**
   * Get the class instance with the given name
   * 
   * @param name
   *          The name of the class instance
   * @return The class instance with the given name <code>null</code> if no such class exists
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public ImportedClassInstanceSettings getClassInstance(String name) throws RemoteException;

  /**
   * Get a list of the internal variables of a function in this file
   * 
   * @param functionName
   *          The name of the function
   * 
   * @return A list of the internal variables of a function contained in this object
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> getInternalVariables(String functionName) throws RemoteException;

  /**
   * Will the internal variable be connected in the current settings
   * 
   * @param functionName
   *          The name of the function
   * @param internalVariableName
   *          The name of the internal variable
   * @param connect
   *          If the variable should be connected to TPT
   * @throws RemoteException
   *           remote communication problem
   * 
   */
  public void setInternalVariableConnected(String functionName, String internalVariableName,
                                           boolean connect)
      throws RemoteException;

  /**
   * Set if the internal variable will be connected to TPT
   * 
   * @param functionName
   *          The name of the function
   * @param internalVariableName
   *          The name of the internal variable
   * @return If the internal variable will be connected to TPT
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   */
  public boolean isInternalVariableConnected(String functionName, String internalVariableName)
      throws RemoteException;
}
