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

import java.rmi.RemoteException;

import com.piketec.tpt.api.util.DeprecatedAndRemovedException;

/**
 * A <code>TestSet</code> represents a set of {@link Scenario test cases}. Test cases have to be
 * selected explicitly but they can be restricted further dynamically by a {@link #getCondition()
 * test set condition} or by restricting}them to test cases linked to requirements of a
 * {@link #getRequirementSet() selected requirement set}.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 * 
 */
public interface TestSet extends TestSetOrGroup {

  /**
   * @return The {@link RemoteCollection set} of all {@link Scenario test cases} that are assigned
   *         to this <code>TestSet</code> and are not suppressed by its test set condition (using
   *         default values to evaluate the condition).
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Use {@link #getSelectedTestCasesOrGroups()}. Removed in TPT-19. Throws
   *             {@link DeprecatedAndRemovedException}
   */
  @Deprecated
  public RemoteCollection<Scenario> getTestCases() throws RemoteException;

  /**
   * Get the selected test cases and test case groups without taking the test set condition into
   * account. If a group is contained in the set all descendant scenarios in this group are
   * contained in the set as well.
   * 
   * Please note that you cannot remove items from this collection if test set {@link #isLocked()}
   * 
   * @return The raw selected test cases and test case groups.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #isLocked()
   * @see #getCondition()
   * @see #isRestrictedToLinkedTestCases()
   */
  public RemoteCollection<ScenarioOrGroup> getSelectedTestCasesOrGroups() throws RemoteException;

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
   * @see #isLocked()
   * 
   * @param tc
   *          The test case to be added to the <code>TestSet</code>
   * @throws ApiException
   *           <ul>
   *           <li>If the test set is locked
   *           <li>If the given <code>Scenario</code> is not a test case or not from the current TPT
   *           instance.
   *           </ul>
   * @throws RemoteException
   *           remote communication problem
   * @deprecated Removed in TPT-21. Throws {@link DeprecatedAndRemovedException}. Use
   *             {@link #addTestCaseOrGroup(ScenarioOrGroup tcg)} which support scenario groups.
   */
  @Deprecated
  public void addTestCase(Scenario tc) throws ApiException, RemoteException;

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
   * @see #isLocked()
   * 
   * @param tcg
   *          The test case or the test case group to be added to the <code>TestSet</code>
   * @throws ApiException
   *           <ul>
   *           <li>If the test set is locked
   *           <li>If the given <code>Scenario</code> is not a test case or not from the current TPT
   *           instance.
   *           </ul>
   * @throws RemoteException
   *           remote communication problem
   */
  public void addTestCaseOrGroup(ScenarioOrGroup tcg) throws ApiException, RemoteException;

  /**
   * A test set can be locked to avoid accidental changes of the set of selected
   * {@link ScenarioOrGroup test cases or test case groups}. No test cases can be added to or
   * removed from a locked test set.
   * 
   * @return <code>false</code> if the scenario selection can be changed <code>true</code> otherwise
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isLocked() throws RemoteException;

  /**
   * A test set can be locked to avoid accidental changes of the set of selected
   * {@link ScenarioOrGroup test cases or test case groups}. No test cases can be added to or
   * removed from a locked test set.
   * 
   * @param locked
   *          <code>false</code> to make the scenario selection editable <code>true</code> otherwise
   * @throws RemoteException
   *           remote communication problem
   */
  public void setLocked(boolean locked) throws RemoteException;

  /**
   * Get the test set condition. If the condition is {@link #isConditionEnabled() enabled} only test
   * cases that fulfill the condition are considered as part of the test set.
   * {@link #getSelectedTestCasesOrGroups() Selected} test cases that do not fulfill the condition
   * will be suppressed.
   * 
   * @return the test set condition
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #isConditionEnabled()
   */
  public String getCondition() throws RemoteException;

  /**
   * Get the test set condition. If the condition is {@link #isConditionEnabled() enabled} only test
   * cases that fulfill the condition are considered as part of the test set.
   * {@link #getSelectedTestCasesOrGroups() Selected} test cases that do not fulfill the condition
   * will be suppressed.
   * 
   * @param condition
   *          The condition to set. <code>null</code> will be corrected to an empty string.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #setConditionEnabled(boolean)
   */
  public void setCondition(String condition) throws RemoteException;

  /**
   * Returns whether the {@link #getCondition() test set condition} is enabled. The condition will
   * only be evaluated and will only suppress selected test cases iff it is enabled.
   * 
   * @return <code>true</code> if the test case condition will suppress selected test cases
   *         <code>false</code> otherwise.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #getCondition
   */
  public boolean isConditionEnabled() throws RemoteException;

  /**
   * Set if the {@link #getCondition() test set condition} is enabled. The condition will only be
   * evaluated and will only suppress selected test cases iff it is enabled.
   * 
   * @param enabled
   *          <code>true</code> if the test case condition shall suppress selected test cases
   *          <code>false</code> otherwise.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #setCondition(String)
   */
  public void setConditionEnabled(boolean enabled) throws RemoteException;

  /**
   * Get the requirement set. The requirement set can be used to restrict the selected test cases to
   * test cases that are linked to a requirement of the given requirement set.
   * 
   * @return The selected requirement set or <code>null</code> for the implicit requirement set
   *         containing all requirements.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #isRestrictedToLinkedTestCases()
   */
  public RequirementSet getRequirementSet() throws RemoteException;

  /**
   * Set the requirement set. The requirement set can be used to restrict the selected test cases to
   * test cases that are linked to a requirement of the given requirement set.
   * 
   * @param requirementSet
   *          The requirement set to set or <code>null</code> for the implicit requirement set
   *          containing all requirements.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #setRestrictedToLinkedTestCases(boolean)
   */
  public void setRequirementSet(RequirementSet requirementSet) throws RemoteException;

  /**
   * Returns <code>true</code> iff only test cases linked to a requirement that is part of the
   * selected requirement set are considered as part of the test set.
   * {@link #getSelectedTestCasesOrGroups() Selected} test cases that are not linked to such a
   * requirement are suppressed.
   * 
   * @return <code>true</code> if test cases are suppressed based on the selected requirement set
   *         <code>false</code> otherwise.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #getRequirementSet()
   */
  public boolean isRestrictedToLinkedTestCases() throws RemoteException;

  /**
   * Set iff only test cases linked to a requirement that is part of the selected requirement set
   * are considered as part of the test set. {@link #getSelectedTestCasesOrGroups() Selected} test
   * cases that are not linked to such a requirement will be suppressed.
   * 
   * @param restrict
   *          <code>true</code> if test cases shall be suppressed based on the selected requirement
   *          set <code>false</code> otherwise.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #setRequirementSet(RequirementSet)
   */
  public void setRestrictedToLinkedTestCases(boolean restrict) throws RemoteException;

}
