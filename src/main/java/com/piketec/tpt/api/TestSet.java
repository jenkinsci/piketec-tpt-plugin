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
 * Ein <code>TestSet</code> ist eine Menge von Testfaelle.
 */
public interface TestSet extends NamedObject, IdentifiableRemote {

  /**
   * @return Die Menge der Testfaelle.
   */
  public RemoteCollection<Scenario> getTestCases() throws ApiException, RemoteException;

  /**
   * Fuegt ein Testfall der Menge hinzu. Ein Testfall ist ein Scenario, dass direkt unetr dem
   * Toplevel Testlet haengt.
   * 
   * @see ScenarioOrGroup#isTestcaseOrGroup()
   * 
   * @param tc
   *          Der hinzuzufuegende Testfall.
   * @throws ApiException
   *           Wenn das uebergebene <code>Scenario</code> kein Testfall ist.
   * @throws RemoteException
   *           Wenn das uebergeben <code>Scenario</code> nicht von der angesprochenen TPT-Instance
   *           stammt.
   */
  public void addTestCase(Scenario tc) throws ApiException, RemoteException;

}
