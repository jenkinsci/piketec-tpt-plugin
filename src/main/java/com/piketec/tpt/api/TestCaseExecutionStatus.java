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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Gibt die Moeglichkeit, den aktuellen Ausfuerhungszustand und die Log-Messages fuer einen TestCase
 * abzufragen.
 */
public interface TestCaseExecutionStatus extends Remote {

  /**
   * Der aktuelle Ausfuehrungszustand des Testfalls bzw. dessen Ergebnis.
   * 
   * @author Copyright (c) 2014 Piketec GmbH - All rights reserved.
   */
  public enum TestCaseStatus {
    Pending, ResultUnknown, ResultSuccess, ResultFailed, ResultError, Running, ResultNoAsssessments
  }

  /**
   * @return Den aktuellen Ausfuehrungszustand
   */
  public TestCaseStatus getStatus() throws ApiException, RemoteException;

  /**
   * @return Die Liste der Logeintraege der Ausfuehrung
   */
  public List<String> getStatusLog() throws ApiException, RemoteException;

  /**
   * Setzt das Ergebnis manuell auf {@link TestCaseStatus#ResultSuccess} oder
   * {@link TestCaseStatus#ResultFailed}. Das eigentliche Testergbnis wird dabei nicht
   * ueberschrieben.
   * 
   * @param success
   *          <code>true</code> wenn das Testergbnis als Erfolg reklassifiert werden soll
   *          <code>false</code> wenn es als Misserfolg reklassifiert werden soll.
   * @param userName
   *          Der Name desjenigen, der die Reklassifierung verantwortet
   * @param comment
   *          Erklaerung zur Reklassifizierung.
   */
  public void reclassify(boolean success, String userName, String comment)
      throws ApiException, RemoteException;

  /**
   * @return Das {@link ExecutionConfigurationItem}, zu dem die Testausfuehrung gehoert.
   */
  public ExecutionConfigurationItem getExecutionConfigurationItem() throws RemoteException;

  /**
   * @return Den ausgefuehrten Testfall.
   */
  public Scenario getTestcase() throws RemoteException;
}
