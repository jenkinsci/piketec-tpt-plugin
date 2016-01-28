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
 */package com.piketec.tpt.api;

import java.rmi.RemoteException;

/**
 * {@link Scenario}s koennen in einer Baumstruktur angeordnet werden. Damit eine
 * {@link ScenarioGroup} sowohl {@link Scenario} als auch {@link ScenarioGroup} aufnehmen kann,
 * erben beide von dieser Oberklasse.
 */
public interface ScenarioOrGroup extends NamedObject, IdentifiableRemote {

  /**
   * @return Die textuelle Beschreibung dieser <code>ScenarioOrGroup</code>
   */
  public String getDescription() throws ApiException, RemoteException;

  /**
   * Setzt die textuelle Beschreibung dieser <code>ScenarioOrGroup</code>
   * 
   * @param description
   *          Die neue Bschreibung
   */
  public void setDescription(String description) throws ApiException, RemoteException;

  /**
   * Die ScenarioGroup in der dieses Objekt haengt oder <code>null</code> wenn dieses Objekt ein
   * Toplevel Objekt ist also direkt unter einem {@link Testlet} haengt.
   *
   */
  public ScenarioGroup getGroup() throws ApiException, RemoteException;

  /**
   * @return Leifert das Testlet, unter dem sich diese <code>ScenarioOrGroup</code> befindet.
   * @throws ApiException
   * @throws RemoteException
   */
  public Testlet getTestlet() throws ApiException, RemoteException;

  /**
   * Liefert <code>true</code> wenn die ScenarioOrGroup unter dem Toplevel Testlet haengt.
   *
   * @return <code>true</code> wenn es sich um einen Testfall oder eine Testfallgruppe handelt.
   *         <code>false</code> wenn es sich um eine Variante oder Variantengruppe handelt.
   */
  public boolean isTestcaseOrGroup() throws RemoteException;

}
