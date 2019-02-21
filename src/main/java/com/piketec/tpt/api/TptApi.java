/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016-2019 PikeTec GmbH
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
import java.util.Collection;

/**
 * Main entry point for the access to TPT via the Remote API
 */
public interface TptApi extends TptRemote {

  /**
   * Close the TPT instance represented by this object.
   *
   * @return <code>false</code> if the application cannot be closed. Possible reasons are:
   *         <li>the user interrupts the close operation</li>
   *         <li>a test is currently running and is not been canceled</li>
   *         <li>other blocking operations prevent TPT from closing</li>
   */
  public boolean closeTpt() throws ApiException, RemoteException;

  /**
   * 
   * Try to open an already existing project and to return a handle for the project
   * ({@link OpenResult#project}). If the project is already open, only the handle will be returned.
   * The project is not re-opened. Changes will not been overwritten.
   * <p>
   * Any errors or warnings that occur during the open-operation are stored in the
   * {@link OpenResult#logs}.
   * </p>
   * 
   * @param f
   *          The path to the TPT-file as {@link File}
   * @return A {@link OpenResult} containing the handle to the project and the log messages occurred
   *         during the open-operation.
   *
   * @throws ApiException
   *           If <code>f</code> do not exists or if <code>f</code> is not a TPT-file.
   */
  public OpenResult openProject(File f) throws ApiException, RemoteException;

  /**
   * Create a new TPT {@link Project} assigned with the given {@link File}. The new
   * <code>Project</code> will not be saved during this operation. The given file is only relevant
   * for later save operations.
   * 
   * @see Project#saveProject()
   * @see Project#saveAsProject(File)
   * 
   * @param f
   *
   * @throws ApiException
   *           If the given File is already opened in TPT
   */
  public Project newProject(File f) throws ApiException, RemoteException;

  /**
   * @return Returns the set of all {@link Project Projects} that are currently open in this TPT
   *         instance.
   */
  public Collection<Project> getOpenProjects() throws ApiException, RemoteException;

  /**
   * @return Returns the version name of the TPT instance represented by this API object.
   */
  public String getTptVersion() throws ApiException, RemoteException;

  /**
   * @return Returns the version number of file format this TPT version will use to store *.tpt-,
   *         *.tptz- and *.tptprj-Files when calling save.
   */
  public int getFileFormatVersion() throws ApiException, RemoteException;

  /**
   * Starts a run for the given {@link ExecutionConfiguration}. The test run is started
   * asynchronously. The progress of the test run can be monitored via the returned
   * {@link ExecutionStatus} object.
   * 
   * @param config
   *          The {@link ExecutionConfiguration} to be executed.
   * @return The state of execution for all test cases defined in the
   *         <code>ExecutionConfiguration</code>
   *
   * @throws ApiException
   *           if there is already a running test execution or if the test execution could not be
   *           started
   * @throws RemoteException
   *           If <code>config</code> is not part of the TPT instance represented by this object.
   */
  public ExecutionStatus run(ExecutionConfiguration config) throws ApiException, RemoteException;

  /**
   * Start the generation of the overview report. The generation is done asynchronously.
   * <p>
   * This function does not execute or asses the test cases specified in <code>config</code>. It
   * uses the existing result data and XML-files to generate a new overview report.
   * </p>
   * 
   * @param config
   *          The {@link ExecutionConfiguration} for which the overview report should be generated.
   * @throws ApiException
   *           if there is already a running test execution or if the test execution could not be
   *           started
   * @throws RemoteException
   *           If <code>config</code> is not part of the TPT instance represented by this object.
   */
  public ExecutionStatus reGenerateOverviewReport(ExecutionConfiguration config)
      throws ApiException, RemoteException;

  /**
   * Selects the object given by <code>obj</code> within the TPT GUI - if it is selectable (like a
   * {@link Scenario} or an {@link Assessment})
   *
   * 
   * @param obj
   *          A object that implements the {@link IdentifiableRemote} interface
   */
  public void select(IdentifiableRemote obj) throws ApiException, RemoteException;

  /**
   * Depending on its configuration, TPT requires some time to load all plugins. Since the API is
   * one of the first plugins that are loaded, it can be accessed before TPT is actually fully
   * loaded.
   * <p>
   * This function returns <code>true</code> as soon as all plugins have been completely loaded and
   * TPT is ready for use.
   * </p>
   * 
   * @return Whether TPT has finished its startup
   * @throws RemoteException
   */
  public boolean isReady() throws RemoteException;
}
