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

import java.io.File;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import com.piketec.tpt.api.autosarplatform.AutosarPlatformConfiguration;
import com.piketec.tpt.api.cplatform.CCodePlatformConfiguration;
import com.piketec.tpt.api.util.WalkResultAssessment;
import com.piketec.tpt.api.util.WalkResultExecutionConfiguration;
import com.piketec.tpt.api.util.WalkResultScenario;
import com.piketec.tpt.api.util.WalkResultTestSet;
import com.piketec.tpt.api.util.WalkResultTestlet;

/**
 * Main entry point for the access to TPT via the TPT API. This API can be accessed via API Script
 * editor or via Java RMI.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface TptApi extends TptRemote {

  /**
   * String for setting the default compiler in
   * {@link CCodePlatformConfiguration#setCompiler(String)} and
   * {@link AutosarPlatformConfiguration#setCompiler(String)}.
   * 
   * @deprecated use constants {@link CCodePlatformConfiguration#DEFAULT_COMPILER} or
   *             {@link AutosarPlatformConfiguration#DEFAULT_COMPILER} instead.
   */
  @Deprecated
  public static final String DEFAULT_COMPILER = "<DEFAULT>";

  /**
   * Close the TPT instance represented by this object.
   *
   * @return <code>false</code> if the application cannot be closed. Possible reasons are:
   *         <ul>
   *         <li>the user interrupts the close operation</li>
   *         <li>a test is currently running and is not been canceled</li>
   *         <li>other blocking operations prevent TPT from closing</li>
   *         </ul>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean closeTpt() throws RemoteException;

  /**
   * 
   * Try to open an already existing project and to return a handle for the project
   * ({@link OpenResult#getProject()}). If the project is already open, only the handle will be
   * returned. The project is not re-opened. Changes will not be overwritten.
   * <p>
   * Any errors or warnings that occur during the open-operation are stored in the
   * {@link OpenResult#getLogs()}.
   * </p>
   * 
   * @param f
   *          The path to the TPT {@link File}
   * @return A {@link OpenResult} containing the handle to the project and the log messages occurred
   *         during the open-operation.
   *
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If <code>f</code> do not exists or if <code>f</code> is not a TPT file.
   */
  public OpenResult openProject(File f) throws ApiException, RemoteException;

  /**
   * 
   * Try to open an already existing project and to return a handle for the project
   * ({@link OpenResult#getProject()}). If the project is already open, only the handle will be
   * returned. The project is not re-opened. Changes will not be overwritten.
   * <p>
   * Any errors or warnings that occur during the open-operation are stored in the
   * {@link OpenResult#getLogs()}.
   * </p>
   * 
   * @param pathToTptFile
   *          The path to the TPT file
   * @return A {@link OpenResult} containing the handle to the project and the log messages occurred
   *         during the open-operation.
   *
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If <code>f</code> do not exists or if <code>f</code> is not a TPT file.
   */
  public OpenResult openProjectByPath(String pathToTptFile) throws ApiException, RemoteException;

  /**
   * Create a new TPT {@link Project} assigned with the given {@link File}. The new
   * <code>Project</code> will not be saved during this operation. The given file is only relevant
   * for later save operations.
   * 
   * @see Project#saveProject()
   * @see Project#saveAsProject(File)
   * 
   * @param f
   *          the file to use for this project
   *
   * @return the newly created TPT project
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the given File is already opened in TPT
   */
  public Project newProject(File f) throws ApiException, RemoteException;

  /**
   * Create a new TPT {@link Project} assigned with the given {@link File}. The new
   * <code>Project</code> will not be saved during this operation. The given file is only relevant
   * for later save operations.
   * 
   * @see Project#saveProject()
   * @see Project#saveAsProject(File)
   * 
   * @param pathToTptFile
   *          the path to the file to use for this project
   *
   * @return the newly created TPT project
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the given File is already opened in TPT
   */
  public Project newProjectByPath(String pathToTptFile) throws ApiException, RemoteException;

  /**
   * @return Returns the list of all {@link Project Projects} that are currently open in this TPT
   *         instance.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<Project> getOpenProjects() throws RemoteException;

  /**
   * @return Returns the version name of the TPT instance represented by this API object.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTptVersion() throws RemoteException;

  /**
   * @return Returns the installation directory of the TPT instance represented by this API object.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public File getTptInstallationDir() throws RemoteException;

  /**
   * @return Returns the absolute path to the installation directory of the TPT instance represented
   *         by this API object.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTptInstallationDirPath() throws RemoteException;

  /**
   * @return Returns the version number of file format this TPT version will use to store *.tpt-,
   *         *.tptz- and *.tptprj-Files when calling save.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getFileFormatVersion() throws RemoteException;

  /**
   * Starts a run for the given {@link ExecutionConfiguration}. The test run is started
   * asynchronously. The progress of the test run can be monitored via the returned
   * {@link ExecutionStatus} object. Use ExecutionStatus.join() to ensure TPT is waiting for the
   * test execution.
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
   * Starts a run for the given {@link ExecutionConfiguration} and waits for it to terminate. If you
   * want to monitor the progress of the test run use the asynchronous method {@link #run} instead.
   * 
   * @param config
   *          The {@link ExecutionConfiguration} to be executed.
   * 
   * @throws ApiException
   *           if there is already a running test execution or if the test execution could not be
   *           started
   * @throws RemoteException
   *           If <code>config</code> is not part of the TPT instance represented by this object.
   */
  public void runAndWait(ExecutionConfiguration config) throws ApiException, RemoteException;

  /**
   * Start the generation of the overview report. The generation is done asynchronously.
   * <p>
   * This function does not execute or asses the test cases specified in <code>config</code>. It
   * uses the existing result data and XML files to generate a new overview report.
   * </p>
   * 
   * @param config
   *          The {@link ExecutionConfiguration} for which the overview report should be generated.
   * @return the current status of report generation
   * 
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
   * @param obj
   *          A object that implements the {@link IdentifiableRemote} interface
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           This method is not supported if the API is running in headless mode
   */
  public void select(IdentifiableRemote obj) throws RemoteException, ApiException;

  /**
   * Returns the selected TPT {@link Project} or <code>null</code> if no project is selected.
   * 
   * @return The selected TPT project or <code>null</code>
   * @throws RemoteException
   *           remote communication problem
   */
  public Project getSelectedProject() throws RemoteException;

  /**
   * Returns the single selected {@link Testlet} or <code>null</code> if no testlet is selected.
   * 
   * @return The selected testlet or <code>null</code>
   * @throws RemoteException
   *           remote communication problem
   */
  public Testlet getSelectedTestlet() throws RemoteException;

  /**
   * Returns the list of selected {@link Testlet}s or <code>null</code> if no testlet is selected.
   * 
   * @return The list of selected testlets or <code>null</code>
   * @throws RemoteException
   *           remote communication problem
   */
  public List<Testlet> getSelectedTestlets() throws RemoteException;

  /**
   * Returns the selected {@link Scenario scenarios}. If scenarios and/or {@link ScenarioGroup
   * scenario groups} are selected, groups are ignored if <code>traverseSelectedGroups</code> is
   * <code>false</code>. If <code>traverseSelectedGroups</code> is <code>true</code> all scenarios
   * contained recursively in the selected groups are returned as well.
   * 
   * @param traverseSelectedGroups
   *          <code>true</code> if scenarios contained in selected groups shall be returned
   *          <code>false</code> if selected groups shall be ignored.
   * @return Returns a list of the selected {@link Scenario scenarios}
   * @throws RemoteException
   *           remote communication problem
   */
  public List<Scenario> getSelectedScenarios(boolean traverseSelectedGroups) throws RemoteException;

  /**
   * Returns the explicitly selected {@link ScenarioGroup scenario groups}. If only {@link Scenario
   * scenarios} are selected the returned collection will be empty.
   * 
   * @return Returns a list of the explicitly selected {@link ScenarioGroup scenario groups}
   * @throws RemoteException
   *           remote communication problem
   */
  public List<ScenarioGroup> getSelectedScenarioGroups() throws RemoteException;

  /**
   * Returns the selected {@link Assessment assessments}. If assessments and/or
   * {@link AssessmentGroup assessment groups} are selected, groups are ignored if
   * <code>traverseSelectedGroups</code> is <code>false</code>. If
   * <code>traverseSelectedGroups</code> is <code>true</code> all assessments contained recursively
   * in the selected groups are returned as well.
   * 
   * @param traverseSelectedGroups
   *          <code>true</code> if assessments contained in selected groups shall be returned
   *          <code>false</code> if selected groups shall be ignored.
   * @return Returns a list of the selected {@link Assessment assessments}
   * @throws RemoteException
   *           remote communication problem
   */
  public List<Assessment> getSelectedAssessments(boolean traverseSelectedGroups)
      throws RemoteException;

  /**
   * Returns the explicitly selected {@link AssessmentGroup assessment groups}. If only
   * {@link Assessment assessments} are selected the returned collection will be empty.
   * 
   * @return Returns a list of the explicitly selected {@link AssessmentGroup assessment groups}
   * @throws RemoteException
   *           remote communication problem
   */
  public List<AssessmentGroup> getSelectedAssessmentGroups() throws RemoteException;

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
   *           remote communication problem
   */
  public boolean isReady() throws RemoteException;

  /**
   * Bring the TPT main window to the front.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void toFront() throws RemoteException;

  /**
   * @return Returns the "toolbox" which contains a set of auxiliary functions which are not
   *         directly related to a {@link Project} or the TPT-Application.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public UtilToolbox getToolbox() throws RemoteException;

  /**
   * Traverses the assessment tree and returns a triple consisting of the current root, a list of
   * assessment groups and a list of assessments under the current root.
   * 
   * @param root
   *          The node where the iterator should start, this can either be the project itself or an
   *          assessment group.
   * @return Iterable
   * @throws RemoteException
   *           remote communication problem
   */
  public Iterable<WalkResultAssessment> walkAssessments(AssessmentOwner root)
      throws RemoteException;

  /**
   * Traverses the test set tree and returns a triple consisting of the current root, a list of test
   * set groups and a list of test sets under the current root.
   * 
   * @param root
   *          The node where the iterator should start, this can either be the project itself or a
   *          test set group.
   * @return Iterable
   * @throws RemoteException
   *           remote communication problem
   */
  public Iterable<WalkResultTestSet> walkTestSets(TestSetOwner root) throws RemoteException;

  /**
   * Traverses the execution configuration tree and returns a triple consisting of the current root,
   * a list of execution configuration groups and a list of execution configurations under the
   * current root.
   * 
   * @param root
   *          The node where the iterator should start, this can either be the project itself or an
   *          execution configuration group.
   * @return Iterable
   * @throws RemoteException
   *           remote communication problem
   */
  public Iterable<WalkResultExecutionConfiguration> walkExecutionConfigurations(ExecutionConfigurationOwner root)
      throws RemoteException;

  /**
   * Traverses the scenario tree and returns a triple consisting of the current root, a list of
   * scenario groups and a list of scenarios under the current root.
   * 
   * @param root
   *          The scenario group where the iterator should start.
   * @return Iterable
   * @throws RemoteException
   *           remote communication problem
   */
  public Iterable<WalkResultScenario> walkScenarios(ScenarioGroup root) throws RemoteException;

  /**
   * Traverses the testlet tree and returns a triple consisting of the current root, a list of
   * testlets under the current root and as the third component an empty list.
   * 
   * @param root
   *          The testlet where the iterator should start. Note, you can use
   *          {@link Project#getTopLevelTestlet()} to start traversing from the top of the testlet
   *          tree.
   * @return Iterable
   * @throws RemoteException
   *           remote communication problem
   */
  public Iterable<WalkResultTestlet> walkTestlets(Testlet root) throws RemoteException;

  /**
   * Gets the {@link ExecutionStatus} of all test cases defined in the given
   * {@link ExecutionConfiguration}.
   * 
   * @param config
   *          The {@link ExecutionConfiguration} for which execution status should be verified.
   * @return The state of execution for all test cases defined in the
   *         <code>ExecutionConfiguration</code> or <code>null</code> if there is no active
   *         execution.
   *
   * @throws ApiException
   *           If there is no builder matching this configuration.
   * @throws RemoteException
   *           If <code>config</code> is not part of the TPT instance represented by this object.
   */
  public ExecutionStatus getExecutionStatus(ExecutionConfiguration config)
      throws ApiException, RemoteException;

  /**
   * Returns all global variables defined in the preferences.
   * 
   * @return the collection of all global variables defined in the preferences.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see Project#getVariables()
   */
  Collection<String> getVariables() throws RemoteException;

  /**
   * Creates a new variable in the global preferences.
   * 
   * @param variable
   *          the variable name
   * @param value
   *          the value of the variable
   * @param showInReport
   *          If variable should be shown in the report
   * @throws ApiException
   *           if a variable with the same name already exists, the variable name starts with % or
   *           is empty
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see Project#getVariables()
   */
  void createVariable(String variable, String value, boolean showInReport)
      throws ApiException, RemoteException;

  /**
   * Removes a variable from the global preferences. The call succeeds even if no variable with the
   * given name exists.
   * 
   * @param variable
   *          the name of the variable to be removed
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see Project#removeVariable(String)
   */
  void removeVariable(String variable) throws RemoteException;

  /**
   * Return the value of the variable with the given name.
   * 
   * @param variable
   *          the name of the variable
   * @return the value of the variable
   * @throws ApiException
   *           If no variable with the given name exists
   * @throws RemoteException
   *           remote communication problem
   * @see Project#getVariableValue(String)
   */
  String getVariableValue(String variable) throws ApiException, RemoteException;

  /**
   * Set the value of the variable with the given name.
   * 
   * @param variable
   *          the name of the variable
   * @param value
   *          the new value of the variable
   * @throws ApiException
   *           If no variable with the given name exists
   * @throws RemoteException
   *           remote communication problem
   * @see Project#setVariableValue(String, String)
   */
  void setVariableValue(String variable, String value) throws ApiException, RemoteException;

  /**
   * Returns whether the variable is shown in the report.
   * 
   * @param variable
   *          the name of the variable
   * @return <code>true</code> if the variable is shown in the report, <code>false</code> otherwise
   * @throws ApiException
   *           If no variable with the given name exists
   * @throws RemoteException
   *           remote communication problem
   * @see Project#isVariableShowInReport(String)
   */
  boolean isVariableShowInReport(String variable) throws ApiException, RemoteException;

  /**
   * Sets whether the variable is shown in the report.
   * 
   * @param variable
   *          the name of the variable
   * @param showInReport
   *          <code>true</code> if the variable should be shown in the report, <code>false</code>
   *          otherwise
   * @throws ApiException
   *           If no variable with the given name exists
   * @throws RemoteException
   *           remote communication problem
   * @see Project#setVariableShowInReport(String, boolean)
   */
  void setVariableShowInReport(String variable, boolean showInReport)
      throws ApiException, RemoteException;

}
