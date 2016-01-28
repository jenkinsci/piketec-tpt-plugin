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

/**
 * Ein <code>StepListScenario</code> enthaelt eine Liste von {@link Step} und ist der Inhalt eines
 * {@link StepListTestlet}.
 */
public interface StepListScenario extends Scenario {

  /**
   * @return Die Liste der Steps in ihrer Reihenfolge.
   */
  public RemoteList<Step> getSteps() throws ApiException, RemoteException;

  /**
   * @param index
   *          An welche Stelle in der Liste de Steps soll der Step eingefuehgt werden.
   * @param type
   *          Die Art des Steps, der erzeugt werden soll.
   * @return Den neuerstellten Step.
   * @throws ApiException
   *           Wennd er angebene Typ <code>type</code> nicht gefunden wurde.
   */
  public Step createStep(int index, String type) throws ApiException, RemoteException;

}
