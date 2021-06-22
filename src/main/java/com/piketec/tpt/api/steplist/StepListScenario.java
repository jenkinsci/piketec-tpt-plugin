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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.RemoteList;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.ScenarioGroup;
import com.piketec.tpt.api.Testlet;
import com.piketec.tpt.api.diagram.DiagramScenario;

/**
 * A <code>StepListScenario</code> contains a list of {@link Step steps}.
 *
 * <p>
 * In TPT, it represents both the variants as well as the test cases. Create a new
 * <code>StepListScenario</code> via {@link Testlet#createDiagVariant(String, ScenarioGroup)}
 * </p>
 * 
 * @see Testlet#createDiagVariant(String, ScenarioGroup)
 * @see Project#getTopLevelTestlet()
 */
public interface StepListScenario extends Scenario {

  /**
   * @return the list of {@link Step Steps} in their given order.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<Step> getSteps() throws RemoteException;

  /**
   * Creates a step of a given type at the given position.
   * 
   * @param index
   *          Indicates the position where the new step shall be inserted in the step list
   *          represented by this object.
   * @param type
   *          The type of the newly created Step as String, look in {@link Step} for possible
   *          Strings as a type.
   * @return the newly created Step.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the given <code>type</code> does not exist.
   */
  public Step createStep(int index, String type) throws ApiException, RemoteException;

  /**
   * Sets the do assessment variable from a step list scenario. When it is set to true, all the
   * compare steps will be considered during the execution, otherwise they will be ignored. If the
   * variable is set to false, it will also automatically deactivate the report always variable.
   * 
   * @param active
   *          true if the do assessment variable should be turned on.
   * @throws ApiException
   *           if the scenario is not a step list scenario
   * @throws RemoteException
   *           remote communication problem
   */
  void setDoAssessment(boolean active) throws ApiException, RemoteException;

  /**
   * Get the value from the do assessment variable for the given scenario.
   * 
   * @return true if the do assessment variable is on, otherwise false.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isDoAssessment() throws RemoteException;

  /**
   * Sets the report always variable from a step list scenario. When it is set to true, all the
   * compare step results will appear in the report, otherwise they will be ignored. If this
   * variable is set to true, it will also automatically activate the do assessment variable.
   * 
   * @param active
   *          true if the report always variable should be turned on.
   * 
   * @throws ApiException
   *           If the scenario is not a step list scenario
   * @throws RemoteException
   *           remote communication problem
   */
  void setReportAlways(boolean active) throws ApiException, RemoteException;

  /**
   * Get the value from the report always variable for this scenario.
   * 
   * @return true if the report always variable is on, otherwise false.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isReportAlways() throws RemoteException;

  /**
   * @return A comma separated list of the used namespaces for the step list scenario.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getUsing() throws RemoteException;

  /**
   * Set the used namespace(s) for the step list scenario. For more namespaces provide a comma
   * separated list.
   * 
   * @param using
   *          The new namespace(s) that shall be used.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void setUsing(String using) throws RemoteException;

  /**
   * Transforms this {@link StepListScenario} into a {@link DiagramScenario} without any transitions
   * added to the path. All contents will be deleted, but the meta data like ids and attributes will
   * be retained. Do not use this {@link StepListScenario} afterwards.
   * 
   * @return This {@link StepListScenario} as an empty {@link DiagramScenario}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public DiagramScenario transformToDiagramScenario() throws RemoteException;

  /**
   * Generates a specification containing the step list and the initial values. This specification
   * can be remimported via {@link #importTestSpecification(String)}. <br>
   * Please note that some complex steps cannot be imported and will be imported as documentation
   * steps (e.g. embedded signal step) or will be unrolled (e.g. table step).
   * 
   * 
   * @return generated test specification for this step list.
   * @throws RemoteException
   *           remote communication problem
   */
  public String exportTestSpecification() throws RemoteException;

  /**
   * Imports a formatted step list specification into this {@link StepListScenario}. This method
   * imports initial values and appends all steps to the existing definitions.<br>
   * Please note that some complex steps cannot be imported and will be imported as documentation
   * steps (e.g. embedded signal step) or will be unrolled (e.g. table step).
   * 
   * @param specification
   *          The sepcification as formatted plain text, e.g. <br>
   *          <br>
   *          <code>
   *          Initialize myVar to 17<br>
   *          Set myVar to myVar+1 // myComment<br>
   *          Ramp myParam to 1 with 2/s</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   * @see #exportTestSpecification()
   */
  public void importTestSpecification(String specification)
      throws RemoteException;
}
