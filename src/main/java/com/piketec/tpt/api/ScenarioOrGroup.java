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
package com.piketec.tpt.api;

import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.util.DeprecatedAndRemovedException;
import com.piketec.tpt.api.util.UUIDObject;

/**
 * This object either represents a {@link Scenario} or a {@link ScenarioGroup}. It represents a node
 * within the tree structure defined by scenarios and scenario groups.
 * <p>
 * In TPT, it represents both the tree of variants and variant groups as well as the test cases and
 * test case groups. Both can be assigned with a description as well as parameters.
 * </p>
 */
public interface ScenarioOrGroup extends NamedObject, IdentifiableRemote, UUIDObject {

  /**
   * @return Returns the textual description of this <code>ScenarioOrGroup</code> (displayed int the
   *         Description view of TPT).
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDescription() throws RemoteException;

  /**
   * Set the testual description for this <code>ScenarioOrGroup</code> to be displayed in the
   * Description view of TPT.
   * 
   * @param description
   *          The new description.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDescription(String description) throws RemoteException;

  /**
   * Get the parent scenario group or <code>null</code> if this object reside on the top level
   * (meaning directly below the a {@link Testlet}).
   * 
   * @return the parent group or <code>null</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public ScenarioGroup getGroup() throws RemoteException;

  /**
   * Returns <code>true</code> if this is a {@link ScenarioGroup}, <code>false</code> otherwise.
   * 
   * @return <code>true</code> if this is a {@link ScenarioGroup}, <code>false</code> otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isGroup() throws RemoteException;

  /**
   * @return Returns the {@link Testlet} this <code>ScenarioOrGroup</code> belongs to. In case this
   *         object is a test case (see {@link #isTestcaseOrGroup()}) the "main" testlet is
   *         returned.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Testlet getTestlet() throws RemoteException;

  /**
   * Returns <code>true</code> if this ScenarioOrGroup represents a test case or test case group. A
   * <code>Scenario</code> is a test case, if it is within or a child of the
   * {@link Testlet#getTopLevelScenarioOrGroup() Scenarios} of the top level testlet. Otherwise, it
   * is a variant in a one of the {@link Testlet#getStates() sub-testlets}.
   *
   * @return
   *         <ul>
   *         <li><code>true</code> if it is a test case or test case group</li>
   *         <li><code>false</code> if it is a variant or variant group</li>
   *         </ul>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isTestcaseOrGroup() throws RemoteException;

  /**
   * Set a given initial <code>value</code> for a given <code>declarationName</code> for this
   * <code>ScenarioOrGroup</code>. This corresponds to the initial values tab for test cases and
   * variants. Child nodes inherit the setting from their parent groups.
   * 
   * 
   * @param declarationName
   *          The name of the {@link Declaration}. Struct members and array accesses with constant
   *          index are also supported.
   * @param value
   *          The new value for the <code>declarationName</code> or <code>null</code> to remove a
   *          value specific to this <code>ScenarioOrGroup</code>.
   * @throws ApiException
   *           <ul>
   *           <li>if the name of the declaration does not exists or</li>
   *           <li>the declaration is a read only parameter.</li>
   *           </ul>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setInitialValue(String declarationName, String value)
      throws ApiException, RemoteException;

  /**
   * Returns the initial value for a given <code>declarationName</code> as defined for this
   * <code>ScenarioOrGroup</code>. Returns <code>null</code> if no value has been set for this
   * <code>ScenarioOrGroup</code>.
   * 
   * @param declarationName
   *          Name of the {@link Declaration}. Struct members and array accesses with constant index
   *          are also supported.
   * @return <code>null</code> or the currently set value.
   * @throws ApiException
   *           <ul>
   *           <li>if the name of the parameter does not exists or</li>
   *           <li>the declaration is a read only parameter.</li>
   *           </ul>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getInitialValue(String declarationName) throws ApiException, RemoteException;

  /**
   * Returns a list of variables for which this test case/variant directly defines an initial value.
   * 
   * @return list of variables for which this test case/variant directly defines an initial value.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> getInitialValues() throws RemoteException;

  /**
   * Returns the value for a given {@link TestCaseAttribute} that is defined for this Scenario.
   * <p>
   * {@link TestCaseAttribute TestCaseAttributes} are only available for test cases. The type of the
   * Scenario can be determined using {@link ScenarioOrGroup#isTestcaseOrGroup()}.
   * </p>
   * 
   * @see Project#createTestCaseAttribute(String,
   *      com.piketec.tpt.api.TestCaseAttribute.TestCaseAttributeType)
   *      createTestCaseAttribute(String, TestCaseAttributeType))
   * 
   * @param name
   *          The <code>name</code> of the {@link TestCaseAttribute}.
   * @return The current <code>value</code> of the {@link TestCaseAttribute}
   * @throws ApiException
   *           If the <code>name == null</code> or if no {@link TestCaseAttribute} with the given
   *           name has been defined.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTestCaseAttributeValue(String name) throws ApiException, RemoteException;

  /**
   * Set the <code>value</code> for the {@link TestCaseAttribute} of a given <code>name</code> for
   * this <code>ScenarioOrGroup</code>.
   * <p>
   * {@link TestCaseAttribute TestCaseAttributes} are only available for test cases. The type of the
   * Scenario can be determined using {@link ScenarioOrGroup#isTestcaseOrGroup()}.
   * </p>
   * 
   * @param name
   *          The <code>name</code> of the {@link TestCaseAttribute}
   * @param value
   *          The new <code>value</code> for the {@link TestCaseAttribute} with the given
   *          <code>name</code>
   * @throws ApiException
   *           If <code>name==null</code> or if there exists no {@link TestCaseAttribute} with the
   *           given name or if the given value does not match the
   *           {@link TestCaseAttribute#getAttributeType() type} of the attribute.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTestCaseAttributeValue(String name, String value)
      throws ApiException, RemoteException;

  /**
   * Set the status of the scenario or group.
   * 
   * @param author
   *          The author of the status. <code>Null</code> will be reduced to an empty string.
   * @param comment
   *          The comment of the status. <code>Null</code> will be reduced to an empty string.
   * @param status
   *          The status type of the status.
   * @throws ApiException
   *           If this is not a test case or test case group, <code>author</code> contains line
   *           breaks, or given <code>status</code> is invalid.
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see Project#createStatusType(String)
   */
  public void setStatus(String author, String comment, String status)
      throws ApiException, RemoteException;

  /**
   * @return the scenario or scenario group ID.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the ID of the assessment is not an integer
   * 
   * @deprecated Removed in TPT-19. Throws {@link DeprecatedAndRemovedException}. Since TPT-16
   *             scenario IDs are strings. Use {@link #getIdString()} instead.
   */
  @Deprecated
  public int getId() throws RemoteException;

  /**
   * @return the scenario or scenario group ID.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getIdString() throws RemoteException;

  /**
   * Moves this {@link ScenarioOrGroup} to a new position in the scenario tree.
   * 
   * @param newParent
   *          the new parent {@link ScenarioGroup}.
   * @param index
   *          the new position under the new parent.
   * @throws ApiException
   *           If <code>newParent==null</code> or the new parent is for an other reason invalid.
   * @throws RemoteException
   *           remote communication problem
   */
  public void move(ScenarioGroup newParent, int index) throws ApiException, RemoteException;

  /**
   * Copies <code>this</code> into the given <code>targetGroup</code> that can be from a different
   * {@link Project} that is opened in the same TPT instance. If the <code>targetGroup</code>
   * already contains an element with the same name a new one will be generated.
   * 
   * @param targetGroup
   *          The group to copy <code>this</code> into. Can be from another <code>Project</code>.
   * @param targetIndex
   *          The index where the copy will be inserted. Use {@link Integer#MAX_VALUE} to append the
   *          copy at the end.
   * @return The copy of this and all log messages that occured during copying.
   * @throws ApiException
   *           If targetGroup is <code>null</code> or copying failed.
   * @throws RemoteException
   *           remote communication problem
   */
  public ResultAndLogs<ScenarioOrGroup> copy(ScenarioGroup targetGroup, int targetIndex)
      throws ApiException, RemoteException;
}
