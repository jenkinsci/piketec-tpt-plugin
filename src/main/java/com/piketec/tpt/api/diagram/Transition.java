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
package com.piketec.tpt.api.diagram;

import java.awt.Point;
import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.IdentifiableRemote;
import com.piketec.tpt.api.NamedObject;
import com.piketec.tpt.api.RemoteList;

/**
 * Eine Transition ist ein uebergang von einem {@link DiagramNode} zu einem anderen.
 */
public interface Transition extends NamedObject, IdentifiableRemote {

  /**
   * @return Den Startpunkt der Transition.
   */
  public DiagramNode getFrom() throws ApiException, RemoteException;

  /**
   * @return Den Endpunkt der Transition.
   */
  public DiagramNode getTo() throws ApiException, RemoteException;

  /**
   * Setzt den Startpunkt der Transition.
   * 
   * @param n
   *          Der neue Startpunkt.
   * @throws RemoteException
   *           Wenn <code>n</code> nicht von der angesprochenen TPT-Instanz stammt.
   */
  public void setFrom(DiagramNode n) throws ApiException, RemoteException;

  /**
   * Setzt den Endpunkt der Transition.
   * 
   * @param n
   *          Der neue Endtpunkt.
   * @throws RemoteException
   *           Wenn <code>n</code> nicht von der angesprochenen TPT-Instanz stammt.
   */
  public void setTo(DiagramNode n) throws ApiException, RemoteException;

  /**
   * Um einer Transition im Diagram eine Kruvenverlauf zu geben anstatt einer gerade Linie kann eine
   * Reihe von Hilfspunkten angeben werden, die bestimmen, wie sich die Transition verformen soll.
   * 
   * @return Die Liste der Hilfspunkte
   */
  public RemoteList<Point> getAuxPositions() throws ApiException, RemoteException;

  /**
   * Fuegt einen neuen Hilfspunkt fuer die Transition ein.
   *
   * @param p
   *          Der neue Hilfspukt
   * @param index
   *          Die Position in der Liste der Hilfspunkte.
   * @throws ApiException
   * @throws RemoteException
   */
  public void addAuxPoint(Point p, int index) throws ApiException, RemoteException;

  /**
   * Liefert die Menger der Transitionsspezifikationen oder der Gruppen, die dierekt dieser
   * Transition untergeordnet sind.
   * 
   * @return Die Liste der TransitionsSpezifikationen und Gruppen.
   */
  public RemoteList< ? extends TransitionSpecOrGroup> getTopLevelTransitionSpecOrGroup()
      throws ApiException, RemoteException;

  /**
   * Erzeugt eine neue TransitionSpec. Die neue TransitionSpec wird in die angegebene Gruppe oder
   * direkt unter diese Transition gehaengt.
   *
   * @param name
   *          Der Name der Transitionsspezfikation
   * @param groupOrNull
   *          Die Gruppe in der das neue TransitionSpec eingefuegt werden soll oder
   *          <code>null</code> , wenn das neue TransitionSpec direkt unter der Transition haengen
   *          soll.
   * @return Die neuerzeugte Transitionsspezifikation
   * @throws RemoteException
   *           Wenn <code>groupOrNull</code> nicht von der angesprochenen TPT-Instanz stammt.
   */
  public TransitionSpec createTransitionSpec(String name, TransitionSpecGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * Erzeugt eine neue TransitionSpecGroup. Die neue TransitionSpecGroup wird in die angegebene
   * Gruppe oder direkt unter diese Transition gehaengt.
   *
   * @param name
   *          Der Name der Transitionsspezfikationen-Gruppe
   * @param groupOrNull
   *          Die Gruppe in der das neue TransitionSpecGroup eingefuegt werden soll oder NULL wenn
   *          das neue TransitionSpecGroup unter dem Transition haengen soll.
   * @return Die neuerzeugte Transitionsspezifikationen-Gruppe
   * @throws RemoteException
   *           Wenn <code>groupOrNull</code> nicht von der angesprochenen TPT-Instanz stammt.
   */

  public TransitionSpecGroup createTransitionSpecGroup(String name, TransitionSpecGroup groupOrNull)
      throws ApiException, RemoteException;

}
