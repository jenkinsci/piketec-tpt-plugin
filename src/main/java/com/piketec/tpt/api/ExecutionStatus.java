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
import java.util.Collection;

import com.piketec.tpt.api.TestCaseExecutionStatus.TestCaseStatus;

/**
 * Interface zum Zugriff auf den aktuellen Status der Testausfuehrung. Das Interface spiegelt die
 * Informationen wieder, die der Progress-Dialog dem User darstellt.
 */
public interface ExecutionStatus extends Remote {

  /**
   * @return ob die Testausfuehrung NOCH nicht gestartet wurde
   *
   */
  public boolean isPending() throws ApiException, RemoteException;

  /**
   * @return ob die Testausfuehrung noch laeuft.
   *
   */
  public boolean isRunning() throws ApiException, RemoteException;

  /**
   * Bricht eine laufende Testausfuehrung ab.
   */
  public void cancel() throws ApiException, RemoteException;

  /**
   * Die Liste der Status fuer die einzelnen an der Testausfuehrung beteiligten Testfaelle.
   *
   * @return Die Liste oder eine leere Liste wenn keine Testausfuehrung gestartet wurde.
   */
  public Collection<TestCaseExecutionStatus> getAllTestCases() throws ApiException, RemoteException;

  /**
   * Die Anzahl aller an der Testausfuehrung beteiligten Testfaelle je Plattform. Taucht ein
   * Testfall in zwei {@link ExecutionConfigurationItem} auf (z.B. weil ein Back2Back-Test gemacht
   * wird), so wird der Testfall doppelt gezaehlt.
   *
   * @return Anzahl der Testfaelle
   */
  public int getNumberOfAllTestCases() throws ApiException, RemoteException;

  /**
   * Die Anzahl aller beteiligten Testfaelle die noch nicht abgeschlossen (also im Zustand
   * {@link TestCaseStatus#Pending}) sind. Taucht ein Testfall in zwei
   * {@link ExecutionConfigurationItem} auf (z.B. weil ein Back2Back-Test gemacht wird), so wird der
   * Status pro {@link ExecutionConfigurationItem} einzeln betrachtet.
   *
   * @return Anzahl der noch nicht ausgefuehrten Testfaelle
   */
  public int getNumberOfPendingTestCases() throws ApiException, RemoteException;

  /**
   * Liefert den gerade ausgefuehrten Testfall zurueck, also den Testfall, dessen Status
   * {@link TestCaseStatus#Running} ist.
   * 
   * @return Den gerade ausgefuehrten Tesfall oder <code>null</code>
   */
  public Scenario getCurrentTestCase() throws ApiException, RemoteException;

  /**
   * Liefert zurueck, wie der bisherige Gesamtzustand der Testausfuehrung ist. Der Gesamtstatus ist
   * der Status aller Testfaelle mit der hoechsten Prioritaet. Die Reihenfolge der Prioritaeten ist
   * dabei folgende (hoch zu niedrig): ResultError, ResultFailed, ResultNoAsssessments,
   * ResultUnknown, ResultSuccess, Running, Pending
   * 
   * @return Den Status mit der hoechsten Prioritaet
   * 
   * @throws ApiException
   *           Wenn die Anzahl der Testfaelle 0 ist und es somit keinen Status gibt
   */
  public TestCaseStatus getTotalExecutionStatus() throws ApiException, RemoteException;

}
