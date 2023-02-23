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

import com.piketec.tpt.api.diagram.DiagramScenario;
import com.piketec.tpt.api.steplist.StepListScenario;

/**
 * Either a test case or a Diagram/StepList variant of a TestLet. *
 * <p>
 * In TPT, it represents both the variants as well as the test cases. Create new
 * <code>Scenarios</code> via {@link Testlet#createDiagVariant(String, ScenarioGroup)} or
 * {@link Testlet#createSLVariant(String, ScenarioGroup)}.
 * </p>
 * 
 * @see Project#getTopLevelTestlet()
 */
public interface Scenario extends ScenarioOrGroup {

  /**
   * Get all requirements currently linked to this scenario. The content of the collection is only a
   * snapshot of the current state but removing items from this list will remove the link in TPT
   * anyway even if the link was created after receiving this collection.
   * 
   * @return The currently linked requirements.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteCollection<Requirement> getLinkedRequirements() throws RemoteException;

  /**
   * Returns the test data directory of scenario for a given execution configuration item. Since
   * ${tpt.date} and ${tpt.time} placeholders are only valid during runtime they cannot be used in
   * the test data path and an {@link ApiException} will be thrown.
   * 
   * @param execConfigItem
   *          The execution configuration item to calculate the test data directory.
   * @return The test data directory as an absolute file
   * @throws ApiException
   *           If the execution configuration does not belong to the model of the scenario, the
   *           execution configuration has no platform configuration set or the test data path of
   *           the execution configuration has unresolvable placeholder variables.
   * @throws RemoteException
   *           remote communication problem
   */
  public File getTestDataDirectory(ExecutionConfigurationItem execConfigItem)
      throws RemoteException, ApiException;

  /**
   * Returns <code>null</code> if scenario can be compiled without errors, the compile error message
   * otherwise.
   * 
   * @return The compile error message or <code>null</code>
   * @throws ApiException
   *           If scenario is not part of a project anymore or compiler is inactive for the project.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCompileError() throws RemoteException, ApiException;

  /**
   * Returns the current status of the scenario.
   * 
   * @return The current status or <code>null</code> if status is "new".
   * @throws ApiException
   *           If this {@link Scenario} is not a test case.
   * @throws RemoteException
   *           remote communication problem
   */
  public Status getCurrentStatus() throws ApiException, RemoteException;

  /**
   * Returns a list of all status history entries of the scenario, the newest comes first.
   * 
   * @return A list of all {@link Status Statuses}.
   * @throws ApiException
   *           If this {@link Scenario} is not a test case.
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<Status> getStatusHistory() throws ApiException, RemoteException;

  /**
   * Returns <code>true</code> if this scenario has been modified since the last status history
   * entry or if it has no revision. Otherwise, returns <code>false</code>.
   *
   * @return
   *         <ul>
   *         <li><code>true</code> if it is modified or new</li>
   *         <li><code>false</code> if it is up-to-date</li>
   *         </ul>
   * @throws ApiException
   *           If this {@link Scenario} is not a test case.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean checkForNewRevision() throws ApiException, RemoteException;

  /**
   * Returns <code>true</code> if this scenario is marked as "modified"(=outdated) or has no
   * revision. Otherwise, returns <code>false</code>.
   *
   * @return
   *         <ul>
   *         <li><code>true</code> if it is outdated or new</li>
   *         <li><code>false</code> if it is up-to-date</li>
   *         </ul>
   * @throws ApiException
   *           If this {@link Scenario} is not a test case.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isStatusOutdated() throws ApiException, RemoteException;

  /**
   * Generates a specification for this scenario including the initial values. This specification
   * can be reimported via {@link StepListScenario#importTestSpecification(String)}.<br>
   * Please note that the reimport is not a lossless process, especially when importing a
   * specification that was exported from a {@link DiagramScenario}. Some complex steps cannot be
   * imported and will be replaced with documentation steps (e.g. embedded signal step).
   * 
   * 
   * @return generated test specification
   * @throws RemoteException
   *           remote communication problem
   */
  public String exportTestSpecification() throws RemoteException;

}
