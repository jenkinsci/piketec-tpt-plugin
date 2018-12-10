/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 PikeTec GmbH
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
 * This object either represents a {@link Scenario} or a {@link ScenarioGroup}. It represents a node
 * within the tree structure defined by scenarios and scenario groups.
 * <p>
 * In TPT, it represents both the tree of variants and variant groups as well as the test cases and
 * test case groups. Both can be assigned with a description as well as parameters.
 * 
 */
public interface ScenarioOrGroup extends NamedObject, IdentifiableRemote {

  /**
   * @return Returns the textual description of this <code>ScenarioOrGroup</code> (displayed int the
   *         Description view of TPT).
   */
  public String getDescription() throws ApiException, RemoteException;

  /**
   * Set the testual description for this <code>ScenarioOrGroup</code> to be displayed in the
   * Description view of TPT.
   * 
   * @param description
   *          The new description.
   */
  public void setDescription(String description) throws ApiException, RemoteException;

  /**
   * Get the parent scenario group or <code>null</code> if this object reside on the top level
   * (meaning directly below the a {@link Testlet}).
   *
   */
  public ScenarioGroup getGroup() throws ApiException, RemoteException;

  /**
   * @return Returns the {@link Testlet} this <code>ScenarioOrGroup</code> belongs to.
   * @throws ApiException
   * @throws RemoteException
   */
  public Testlet getTestlet() throws ApiException, RemoteException;

  /**
   * Returns <code>true</code> if this ScenarioOrGroup represents a test case or test case group. A
   * <code>Scenario</code> is a test case, if it is within or a child of the
   * {@link Testlet#getTopLevelScenarioOrGroup() Scenarios} of the top level testlet. Otherwise, it
   * is a variant in a one of the {@link Testlet#getStates() sub-testlets}.
   *
   * @return
   *         <li><code>true</code> if it is a test case or test case group</li>
   *         <li><code>false</code> if it is a variant or variant group</li>
   */
  public boolean isTestcaseOrGroup() throws RemoteException;

  /**
   * Set a given <code>value</code> for a given <code>parameterName</code> for this
   * <code>ScenarioOrGroup</code>. This corresponds to the parameter tab for test cases and
   * variants. Child nodes inherit the setting from there parent groups.
   * 
   * 
   * @param parameterName
   *          The name of the {@link Parameter}.
   * @param value
   *          The new value for the {@link Parameter} or <br>
   *          <code>null</code> to remove a value specific to this <code>ScenarioOrGroup</code>.
   * @throws ApiException
   *           <lu>
   *           <li>if the name of the parameter does not exists or</li>
   *           <li>the object identified by the name is not a Parameter or</li>
   *           <li>the parameter is READONLY.</li> </lu>
   */
  public void setParameterDefinition(String parameterName, String value)
      throws ApiException, RemoteException;

  /**
   * Returns the parameter value for a given <code>parameterName</code> as defined for this
   * <code>ScenarioOrGroup</code>. Returns <code>null</code> if no value has been set for this
   * <code>ScenarioOrGroup</code>.
   * 
   * @param parameterName
   *          Name of the {@link Parameter}
   * @return <code>null</code> or the currently set value.
   * @throws ApiException
   *           <lu>
   *           <li>if the name of the parameter does not exists or</li>
   *           <li>the object identified by the name is not a Parameter or</li>
   *           <li>the parameter is READONLY.</li> </lu>
   */
  public String getParameterDefinition(String parameterName) throws ApiException, RemoteException;

  /**
   * Returns the value for a given {@link TestCaseAttribute} that is defined for this Scenario.
   * <p>
   * {@link TestCaseAttribute TestCaseAttributes} are only avalable for test cases. The type of the
   * Scenario can be determined using {@link ScenarioOrGroup#isTestcaseOrGroup()}.
   * 
   * 
   * @see Project#createTestCaseAttribute(String, String)
   * 
   * @param name
   *          The <code>name</code> of the {@link TestCaseAttribute}.
   * @return The current <code>value</code> of the {@link TestCaseAttribute}
   * @throws ApiException
   *           If the <code>name == null</code> or if no {@link TestCaseAttribute} with the given
   *           name has been defined.
   * @throws RemoteException
   */
  public Object getTestCaseAttributeValue(String name) throws ApiException, RemoteException;

  /**
   * Set the <code>value</code> for the {@link TestCaseAttribute} of a given <code>name</code> for
   * this <code>ScenarioOrGroup</code>.
   * <p>
   * {@link TestCaseAttribute TestCaseAttributes} are only available for test cases. The type of the
   * Scenario can be determined using {@link ScenarioOrGroup#isTestcaseOrGroup()}.
   * 
   * @param name
   *          The <code>name</code> of the {@link TestCaseAttribute}
   * @param value
   *          The new <code>value</code> for the {@link TestCaseAttribute} with the given
   *          <code>name</code>
   * @throws ApiException
   *           If <code>name==null</code> or if there exists no {@link TestCaseAttribute} with the
   *           given name or if the given value does not match the
   *           {@link TestCaseAttribute#getType() type} of the attribute.
   * @throws RemoteException
   */
  public void setTestCaseAttributeValue(String name, Object value)
      throws ApiException, RemoteException;

}
