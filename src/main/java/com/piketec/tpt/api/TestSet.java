/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2020 PikeTec GmbH
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

import java.rmi.RemoteException;

/**
 * A <code>TestSet</code> represents a set of test cases ({@link Scenario Scenarios})
 */
public interface TestSet extends TestSetOrGroup {

  /**
   * @return The {@link RemoteCollection set} of all {@link Scenario test cases} that are assigned
   *         to this <code>TestSet</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public RemoteCollection<Scenario> getTestCases() throws ApiException, RemoteException;

  /**
   * Add a new test case to the test set.
   * <p>
   * A test case is a {@link Scenario} that is placed directly or in any sub-group of the top level
   * {@link ScenarioGroup} of the top level {@link Testlet} of a TPT project.
   * </p>
   * 
   * @see ScenarioOrGroup#isTestcaseOrGroup()
   * @see Project#getTopLevelTestlet()
   * @see Testlet#getTopLevelScenarioOrGroup()
   * 
   * @param tc
   *          The test case to be added to the <code>TestSet</code>
   * @throws ApiException
   *           If the given <code>Scenario</code> is not a test case.
   * @throws RemoteException
   *           If the given <code>Scenario</code> is not an object from the current TPT instance.
   */
  public void addTestCase(Scenario tc) throws ApiException, RemoteException;

  /**
   * Returns a list of {@link ScenarioGroup test case groups} that will automatically select newly
   * added {@link Scenario test cases} and enables auto include for newly added {@link ScenarioGroup
   * test case groups}. For disable groups, look at
   * {@link #enableAutoIncludeForTestCaseGroup(ScenarioGroup)}
   *
   * @return A list of {@link ScenarioGroup test case groups}.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public RemoteCollection<ScenarioGroup> getAutoIncludeTestCaseGroups()
      throws ApiException, RemoteException;

  /**
   * Enable the {@link ScenarioGroup test case group} to automatically select newly added
   * {@link Scenario test cases} and {@link ScenarioGroup test case groups}. To disable, use
   * {@link #getAutoIncludeTestCaseGroups()} and remove the desired objects.
   * 
   * @see Assessment#enableForTestCase(ScenarioOrGroup)
   * 
   * @param scenarioGroup
   *          for which auto include should be activated.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void enableAutoIncludeForTestCaseGroup(ScenarioGroup scenarioGroup)
      throws ApiException, RemoteException;
}
