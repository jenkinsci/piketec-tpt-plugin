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
import com.piketec.tpt.api.RemoteCollection;
import com.piketec.tpt.api.Testlet;
import com.piketec.tpt.api.diagram.DiagramTestlet;

/**
 * Ein <code>StepListTestlet</code> ist ein {@link Testlet}, dass eine Step-List enthaelt.
 * 
 * @see DiagramTestlet
 */
public interface StepListTestlet extends Testlet<StepListScenario>, RemoteCollection<Testlet> {

  /**
   * Erzeugt ein neues untergeordnetes {@link StepListTestlet}.
   * 
   * @param name
   *          Der Name des Testlets
   * @return Das neuerzeugte Testlet
   */
  public StepListTestlet createStepListTestlet(String name) throws ApiException, RemoteException;

  /**
   * Erzeugt ein neues untergeordnetes {@link DiagramTestlet}.
   * 
   * @param name
   *          Der Name des Testlets
   * @return Das neuerzeugte Testlet
   */
  public DiagramTestlet createDiagramState(String name) throws ApiException, RemoteException;

}
