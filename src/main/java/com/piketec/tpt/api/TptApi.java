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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Einstiegsklasse fuer den Zugriff auf TPT.
 */
public interface TptApi extends Remote {

  /**
   * Schliesst die TPT-Instanz.
   *
   * @return <code>false</code> wenn die Applikation nicht geschlossen werden kann. (nutzer
   *         unterbricht das schliessen, test laeuft und wird nicht abggebrochen, andere
   *         blockierende Operationen werden durchgefuehrt)
   */
  public boolean closeTpt() throws ApiException, RemoteException;

  /**
   * 
   * oeffnet ein existierendes TPT-Projekt und liefert ein Handle zu diesem zurueck. Wenn das
   * Projekt bereits geoeffnet ist, wird nur ein Handle zurueckgeliefert.
   * 
   * @param f
   *          das TPT-File
   * @return das geoeffnete Projekt
   *
   * @throws ApiException
   *           Wenn das File keine Datei ist oder es kein TPT-Projekt enthaelt.
   */
  public OpenResult openProject(File f) throws ApiException, RemoteException;

  /**
   * Erstellt ein neues TPT-Projekt. Dieses Projekt ist noch nicht gespeichert. Die Datei wird nur
   * fuer spaeteren Gebrauch angegeben.
   * 
   * @see Project#saveProject()
   * @see Project#saveAsProject(File)
   * 
   * @param f
   *          <code>null</code> oder die Datei, unter dem das Projekt spaeter gespeichert werden
   *          soll
   *
   * @throws ApiException
   *           Wenn das angegeben File bereits geoeffnet ist.
   */
  public Project newProject(File f) throws ApiException, RemoteException;

  /**
   * @return Liefert alle derzeit in TPT geoeffneten Projekte zurueck
   */
  public Collection<Project> getOpenProjects() throws ApiException, RemoteException;

  /**
   * @return Die TPT-Version der TPT-Instanz
   */
  public String getTptVersion() throws ApiException, RemoteException;

  /**
   * Startet die Ausfuehrung entsprechend der Konfiguration. Die Testausfuehrung erfolgt asynchron.
   *
   * @throws ApiException
   *           wenn bereits eine Testausfuehrung laeuft. oder die Testausfuehrung nicht gestartet
   *           werden konnte
   * @throws RemoteException
   *           Wenn <code>config</code> nicht von der angesprochenen TPT-Instanz stammt
   */
  public ExecutionStatus run(ExecutionConfiguration config) throws ApiException, RemoteException;

  /**
   * Startet die Erzeugung eines neuen Overviewreport. Die Erzeugung erfolgt asynchron.
   *
   * @throws ApiException
   *           wenn bereits eine Testausfuehrung laeuft. oder die Testausfuehrung nicht gestartet
   *           werden konnte
   * @throws RemoteException
   *           Wenn <code>config</code> nicht von der angesprochenen TPT-Instanz stammt
   */
  public ExecutionStatus reGenerateOverviewReport(ExecutionConfiguration config)
      throws ApiException, RemoteException;

  /**
   * Wenn das uebergebene Objekt in TPT selektierbar ist, wird dieses in TPT angewaehlt.
   * 
   * @param obj
   */
  public void select(IdentifiableRemote obj) throws ApiException, RemoteException;
}
