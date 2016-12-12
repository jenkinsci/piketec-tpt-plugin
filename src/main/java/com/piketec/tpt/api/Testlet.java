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
 * Repraesentiert Testlet in TPT.
 */
public interface Testlet<E extends Scenario> extends NamedObject, IdentifiableRemote {

  /**
   * Erzeugt eine neue Variant oder ein neuen Testcase (wenn das Testlet das Toplevel Testlet ist)
   * Das neue Scenario wird in die angegebene Gruppe oder direkt unter dieses Testlet gehaengt.
   *
   * @param groupOrNull
   *          Die Gruppe in der das neue Scenario eingefuegt werden soll oder NULL wenn das neue
   *          Scenario unter dem Testlet haengen soll.
   */
  public E createVariant(String name, ScenarioGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * Erzeugt eine neue ScenarioOrGroup und haengt diese unter die angegebene Gruppe oder direkt
   * unter dieses Testlet.
   *
   * @param groupOrNull
   *          Die Gruppe in der die neue Gruppe eingefuegt werden soll oder NULL wenn die neue
   *          Gruppe unter dem Testlet haengen soll.
   */
  public ScenarioGroup createVariantGroup(String name, ScenarioGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * Liefert den Inhalt der obersten {@link ScenarioGroup} des Testlets.
   * 
   * @return Die Liste der {@link ScenarioOrGroup}, die direkt in der Hauptgruppe enthalten sind.
   * @throws ApiException
   * @throws RemoteException
   */
  public RemoteList<ScenarioOrGroup> getTopLevelScenarioOrGroup()
      throws ApiException, RemoteException;

}
