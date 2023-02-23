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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

import com.piketec.tpt.api.IdentifiableRemote;
import com.piketec.tpt.api.properties.PropertyMap;
import com.piketec.tpt.api.util.DeprecatedAndRemovedException;

/**
 * A step is one entry in a {@link StepListScenario}.
 */
public interface Step extends IdentifiableRemote {

  /**
   * Type String for call-function-step
   */
  public static final String CALL_FUNCTION = "call-function";

  /**
   * Type String for channel-step
   */
  public static final String CHANNEL = "channel";

  /**
   * Type String for compare-step
   */
  public static final String COMPARE = "compare";

  /**
   * Type String for documentation-step
   */
  public static final String DOCUMENTATION = "documentation";

  /**
   * Type String for else-if-expression-step
   */
  public static final String ELSE_IF_EXPRESSION = "else-if-expression";

  /**
   * Type String for else-if-value-step
   */
  public static final String ELSE_IF_VALUE = "else-if-value";

  /**
   * Type String for else-step
   */
  public static final String ELSE = "else";

  /**
   * Type String for embedded-signal-step
   */
  public static final String EMBEDDED_SIGNAL = "embedded-signal";

  /**
   * Type String for end-step
   */
  public static final String END = "end";

  /**
   * Type String for if-expression-step
   */
  public static final String IF_EXPRESSION = "if-expression";

  /**
   * Type String for if-value-step
   */
  public static final String IF_VALUE = "if-value";

  /**
   * Type String for import-signal-step
   */
  public static final String IMPORT_SIGNAL = "import-signal";

  /**
   * Type String for message-box-step
   */
  public static final String MESSAGE_BOX = "message-box";

  /**
   * Type String for parallel-step
   */
  public static final String PARALLEL = "parallel";

  /**
   * Type String for parameter-step
   */
  public static final String PARAMETER = "parameter";

  /**
   * Type String for ramp-step
   */
  public static final String RAMP = "ramp-channel";

  /**
   * Type String for reset-all-parameters-step
   */
  public static final String RESET_ALL_PARAMETERS = "reset-all-parameters";

  /**
   * Type String for reset-parameter-step
   */
  public static final String RESET_PARAMETER = "reset-parameter";

  /**
   * Type String for reset-target-step
   */
  public static final String RESET_TARGET = "reset-target";

  /**
   * Type String for table-step
   */
  public static final String TABLE = "table";

  /**
   * Type String for testlet-step
   */
  public static final String TESTLET = "testlet";

  /**
   * Type String for wait-expression-step
   */
  public static final String WAIT_EXPRESSION = "wait-expression";

  /**
   * Type String for wait-step
   */
  public static final String WAIT = "wait";

  /**
   * Type String for wait-for-value-step
   */
  public static final String WAIT_FOR_VALUE = "wait-for-value";

  /**
   * Type String for while-expression-step
   */
  public static final String WHILE_EXPRESSION = "while-expression";

  /**
   * @return the name of the step type.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getType() throws RemoteException;

  /**
   * Returns a {@link PropertyMap} String -&gt; Property that represents all properties of the given
   * step type. A Property is either a Child-{@link PropertyMap}, which creates a tree-like
   * structure, or a {@link String} value.
   *
   * @return all properties for the step represented by this object.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Removed in TPT-19. Throws {@link DeprecatedAndRemovedException}
   */
  @Deprecated
  public PropertyMap getProperties() throws RemoteException;

  /**
   * Configures the step with the given <code>PropertyMap</code>.
   *
   * @param properties
   *          properties to apply this this step
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Removed in TPT-19. Throws {@link DeprecatedAndRemovedException}
   */
  @Deprecated
  public void setProperties(PropertyMap properties) throws RemoteException;

  /**
   * TPT offers the possibility to assign some objects comments which are added to the report. This
   * method provides the possibility to define such a {@link String}.
   * 
   * @param documentation
   *          a {@link String} which will be added as a comment to the report.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDocumentation(String documentation) throws RemoteException;

  /**
   * @return a {@link String} which will be added as a comment to the report.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDocumentation() throws RemoteException;

  /**
   * @return the {@link StepListScenario} which contains this step.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public StepListScenario getStepList() throws RemoteException;

  /**
   * Sets if this step is enabled.
   * 
   * @param value
   *          <code>true</code> if this step is active/enabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setActive(boolean value) throws RemoteException;

  /**
   * Determines if this step is enabled.
   *
   * @return <code>true</code> if this step is active/enabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isActive() throws RemoteException;

}
