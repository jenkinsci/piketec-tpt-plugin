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

import java.io.File;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * Die Konfiguration einer Testausfuehrung fuer eine Platform. Bestandteil einer
 * {@link ExecutionConfiguration}
 */
public interface ExecutionConfigurationItem extends PlatformOrExecutionItemEnabler {

  /**
   * Setzt, ob fuer dieses <code>ExecutionConfigItem</code> ausgefuehrt werden soll.
   * 
   * @param active
   *          Der neue Wert
   */
  public void setActive(boolean active) throws ApiException, RemoteException;

  /**
   * @return ob fuer dieses <code>ExecutionConfigItem</code> ausgefuehrt werden soll.
   */
  public boolean isActive() throws ApiException, RemoteException;

  /**
   * @return Das ausgewaehlte TestSet oder <code>null</code> wenn noch keiner ausgewaehlt wurde.
   */
  public TestSet getTestSet() throws ApiException, RemoteException;

  /**
   * @return Die ausgewaehlte Platformkonfiguration oder <code>null</code> wenn noch keine
   *         ausgewaehlt wurde.
   */
  public PlatformConfiguration getPlatformConfiguration() throws ApiException, RemoteException;

  /**
   * @return Das Parameter-File oder <code>null</code> wenn noch keins ausgewaehlt wurde.
   */
  public File getParameterFile() throws ApiException, RemoteException;

  /**
   * Liefert die definierten Variablen.
   * 
   * @return Die Namen der Variablennamen mit den ihnen zugeordneten Werten
   */
  public Map<String, String> getVariables() throws ApiException, RemoteException;

  /**
   * Setzt den Testset dieses ExecutionConfigurationItems.
   * 
   * @param ts
   *          Das TestSet
   */
  public void setTestSet(TestSet ts) throws ApiException, RemoteException;

  /**
   * Setz die Platformkonfiguration dieses ExecutionConfigurationItems.
   * 
   * @param pc
   *          Die Platformkonfiguration
   */
  public void setPlatformConfiguration(PlatformConfiguration pc)
      throws ApiException, RemoteException;

  /**
   * Setzt das Parameter-File oder loescht den Eintrag, wenn <code>f==null</code>
   * 
   * @param f
   *          Das Paramersterset-File
   */
  public void setParameterFile(File f) throws ApiException, RemoteException;

  /**
   * Setzt, ob fuer dieses <code>ExecutionConfigItem</code> Assessments ausgefuehrt werden sollen.
   * 
   * @param run
   *          der neue Wert
   */
  public void setRunAssessments(boolean run) throws ApiException, RemoteException;

  /**
   * @return Ob fuer dieses <code>ExecutionConfigItem</code> Assessments ausgefuehrt werden sollen.
   */
  public boolean isRunAssessments() throws ApiException, RemoteException;

  /**
   * Setzt die entsprechende Variable oder loescht diese (wenn value==<code>null</code>)
   * 
   * @param name
   *          Der Name der Variable
   * @param value
   *          Der neue Wert, <code>null</code> zum loeschen.
   * 
   * @throws ApiException
   *           wenn <code>name==null</code>
   *
   */
  public void setVariable(String name, String value) throws ApiException, RemoteException;
}
