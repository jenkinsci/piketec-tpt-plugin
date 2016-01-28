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
import com.piketec.tpt.api.RemoteCollection;
import com.piketec.tpt.api.Testlet;
import com.piketec.tpt.api.steplist.StepListTestlet;

/**
 * Ein Testlet mit einem Diagram als Inhalt.
 * 
 * @see StepListTestlet
 */
public interface DiagramTestlet extends Testlet<DiagramScenario> {

  /**
   * @return eine {@link RemoteCollection}, die alle Transitionen dieses Diagramms enthaelt.
   */
  public RemoteCollection<Transition> getTransitions() throws ApiException, RemoteException;

  /**
   * @return eine {@link RemoteCollection}, die alle Linien dieses Diagramms enthaelt.
   */
  public RemoteCollection<Line> getLines() throws ApiException, RemoteException;

  /**
   * @return eine {@link RemoteCollection}, die alle Textbereiche dieses Diagramms enthaelt.
   */
  public RemoteCollection<TextArea> getTextAreas() throws ApiException, RemoteException;

  /**
   * @return eine {@link RemoteCollection}, die alle Zustaende dieses Diagramms enthaelt.
   */
  public RemoteCollection<State> getStates() throws ApiException, RemoteException;

  /**
   * @return eine {@link RemoteCollection}, die alle Transitions-Knotenpunkte dieses Diagramms
   *         enthaelt.
   */
  public RemoteCollection<Junction> getJunctions() throws ApiException, RemoteException;

  /**
   * @return eine {@link RemoteCollection}, die alle Endpunkte dieses Diagramms enthaelt.
   */
  public RemoteCollection<Final> getFinals() throws ApiException, RemoteException;

  /**
   * Erzeugt eine neue Transition vom Knoten <code>from</code> zum Knoten <code>to</code>.
   * 
   * @param from
   *          Der Startpunkt der Transition
   * @param to
   *          Der Zielpunkt der Transition
   * @return Die neu erstellte Transition
   * @throws ApiException
   *           Wenn <code>from</code> oder <code>to</code> nicht im Diagram enthalten ist oder wenn
   *           die Transition eine Zyklus herstellen wuerde, der nicht durch einen {@link State}
   *           unterbrochen wird.
   * @throws RemoteException
   *           Wenn <code>from</code> oder <code>to</code> nicht von der angesprochenen TPT-Instanz
   *           stammen.
   */
  public Transition createTransition(DiagramNode from, DiagramNode to) throws ApiException,
      RemoteException;

  /**
   * Erzeugt eine neue horizontale Linie auf Hoehe <code>y</code>
   * 
   * @param y
   *          Die Hoehe, auf der die Linie erstellt werden soll.
   * @return Die neu erzeugte Linie.
   */
  public Line createLine(int y) throws ApiException, RemoteException;

  /**
   * Erzeugt einen neuen Textbereich im Diagram an der Stelle <code>pos</code> mit dem Inhalt
   * <code>text</code>
   * 
   * @param text
   *          Der enthaltene Text
   * @param pos
   *          Die Position im Diagramm
   * @return Der neuerstellte Textbereich
   */
  public TextArea createTextArea(String text, Point pos) throws ApiException, RemoteException;

  /**
   * Erzeugt einen neuen Zustand der als Inhalt ({@link State#getTestlet()}) eine
   * {@link StepListTestlet} hat.
   *
   * @param name
   *          Der Name des Zustands.
   * @param pos
   *          Die Position des Zustands im Diagramm
   * @return Den neuerstellten State
   */
  public State createStepListState(String name, Point pos) throws ApiException, RemoteException;

  /**
   * Erzeugt einen neuen Zustand der als Inhalt ({@link State#getTestlet()}) eine
   * {@link DiagramTestlet} hat.
   * 
   * @param name
   *          Der Name des Zustands.
   * @param pos
   *          Die Position des Zustands im Diagramm
   * @return Den neuerstellten State
   */
  public State createDiagramState(String name, Point pos) throws ApiException, RemoteException;

  /**
   * Erstellt eine neue Junction an der angegebenen Position.
   * 
   * @param pos
   *          Die Position im Diagramm
   * @return Die neu erstellte Junction
   */
  public Junction createJunction(Point pos) throws ApiException, RemoteException;

  /**
   * Erstellt eine neue Final an der angegebenen Position.
   * 
   * @param pos
   *          Die Position im Diagramm
   * @return Der neu erstellte Final
   */
  public Final createFinal(Point pos) throws ApiException, RemoteException;

}
