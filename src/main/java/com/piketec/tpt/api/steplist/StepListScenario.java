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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.RemoteList;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.Testlet;

/**
 * A <code>StepListScenario</code> contains a list of {@link Step steps} and can be a variant of a
 * {@link Testlet}.
 * 
 * @see Testlet#createSLVariant(String, com.piketec.tpt.api.ScenarioGroup)
 */
public interface StepListScenario extends Scenario {

  /**
   * @return The list of {@link Step Steps} in thier given order.
   */
  public RemoteList<Step> getSteps() throws ApiException, RemoteException;

  /**
   * Create a step of a given type at the given position.
   * 
   * @param index
   *          Indicates the position where the new step shall be insertet in the Steplist
   *          represented by this object.
   * @param type
   *          The type of the newly created Step as String
   * @return The newly created Step.
   * @throws ApiException
   *           If the given <code>type</code> does not exist.
   */
  public Step createStep(int index, String type) throws ApiException, RemoteException;

}
