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

import java.awt.Point;
import java.rmi.RemoteException;

import com.piketec.tpt.api.diagram.DiagramNode;
import com.piketec.tpt.api.diagram.DiagramScenario;
import com.piketec.tpt.api.diagram.Final;
import com.piketec.tpt.api.diagram.Junction;
import com.piketec.tpt.api.diagram.Line;
import com.piketec.tpt.api.diagram.Positioned;
import com.piketec.tpt.api.diagram.TextArea;
import com.piketec.tpt.api.diagram.Transition;
import com.piketec.tpt.api.steplist.StepListScenario;

/**
 * A testlet represents the behavior of a state in in the test model. It can either be a graphical
 * state in the diagram or a testlet step in the step list. It has to contain at least one
 * {@link Scenario variant}.
 * <p>
 * It could contain variants for both, {@link StepListScenario StepListScenarios} or
 * {@link DiagramScenario DiagramScenarios}.
 */
public interface Testlet extends DiagramNode, Positioned {

  /**
   * @return Returns <code>true</code>, if this testlet has defined content. This means it is either
   *         a testlet or a reference. Returns <code>false</code> if no content has be set if the
   *         content has been deleted (for example by the context menu in the TPT GUI).
   *
   */
  public boolean hasDefinedContent() throws ApiException, RemoteException;

  /**
   * Creates a new local definition for this state that has not been referenced so far.
   * Additionally, a {@link StepListScenario} is created for this state. Any previously existing
   * definition for this state will be replaced by the new one. Any existing {@link Scenario
   * Scenarios} will be deleted.
   */
  public void createNewContent() throws ApiException, RemoteException;

  /**
   * Creates a new step list variant ({@link StepListScenario}) or a new test case (if the current
   * <code>Testlet</code> is the top level <code>Testlet</code>). If the parameter
   * <code>groupOrNull==null</code>, the newly created step list variant is placed directly below
   * the testlet ({@link Testlet#getTopLevelScenarioOrGroup()}). If a specific {@link ScenarioGroup}
   * is given, the newly created variant is placed there.
   * 
   * @param name
   *          The name of the newly created variant. <code>Null</code> will be reduced to an empty
   *          string.
   * @param groupOrNull
   *          Either the group where the newly created {@link StepListScenario} should be added or
   *          <code>null</code> to add it in the top level group of this <code>Testlet</code>.
   * @return the newly created {@link StepListScenario}.
   * @throws ApiException
   *           If the content of the <code>Testlet</code> is empty.
   */
  public StepListScenario createSLVariant(String name, ScenarioGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * Creates a new diagram variant ({@link DiagramScenario}) or a new test case (if the current
   * <code>Testlet</code> is the toplevel <code>Testlet</code>). If the parameter
   * <code>groupOrNull==null</code>, the newly created StepList variant is placed directly below the
   * Testlet ({@link Testlet#getTopLevelScenarioOrGroup()}). If a specific {@link ScenarioGroup} is
   * given, the newly created variant is placed there.
   * 
   * @param name
   *          The name of the new variant. <code>Null</code> will be reduced to an empty string.
   * @param groupOrNull
   *          Either the group where the newly created {@link DiagramScenario} should be added or
   *          <code>null</code> to add it in the toplevel group of this <code>Testlet</code>.
   * @return the newly created {@link DiagramScenario}
   * @throws ApiException
   *           If the content of the <code>Testlet</code> is empty
   */

  public DiagramScenario createDiagVariant(String name, ScenarioGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * Create a new {@link ScenarioOrGroup} and add it to the given {@link ScenarioGroup} directly
   * below this <code>Testlet</code>, if <code>groupOrNull==null</code>.
   *
   * @param name
   *          The name of the newly created group. <code>Null</code> will be reduced to an empty
   *          string.
   * @param groupOrNull
   *          The group, in which the newly created {@link ScenarioGroup} should be added or
   *          <code>null</code> if it shall be added to the top level group of the
   *          <code>Testlet</code>.
   * @return the newly created {@link ScenarioGroup}
   * @throws ApiException
   *           If the content of the <code>Testlet</code> is empty
   */
  public ScenarioGroup createVariantGroup(String name, ScenarioGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * Returns the <u>CONTENTS</u> of the top level {@link ScenarioGroup} of this <code>Testlet</code>
   * as list of {@link ScenarioOrGroup}.
   * 
   * @return The list of {@link ScenarioOrGroup} that are contained in the top level group.
   * @throws ApiException
   * @throws RemoteException
   */
  public RemoteList<ScenarioOrGroup> getTopLevelScenarioOrGroup()
      throws ApiException, RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all {@link Transition Transitions} in
   *         this hierarchy layer of the diagram.
   */
  public RemoteCollection<Transition> getTransitions() throws ApiException, RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all {@link Line Lines} in this
   *         hierarchy layer of the diagram.
   */
  public RemoteCollection<Line> getLines() throws ApiException, RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all {@link TextArea text areas} in
   *         this hierarchy layer of the diagram.
   */
  public RemoteCollection<TextArea> getTextAreas() throws ApiException, RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all {@link Testlet Testlets} in this
   *         hierarchy layer of the diagram.
   */
  public RemoteCollection<Testlet> getStates() throws ApiException, RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all {@link Junction Junctions} in this
   *         hierarchy layer of the diagram.
   */
  public RemoteCollection<Junction> getJunctions() throws ApiException, RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all final junctions ({@link Final}) in
   *         this hierarchy layer of the diagram.
   */
  public RemoteCollection<Final> getFinals() throws ApiException, RemoteException;

  /**
   * Creates a new {@link Transition} from a node <code>from</code> to a node <code>to</code>. A
   * node can either be a {@link Transition}, a {@link Junction} or {@link ApiException}
   * {@link Final}.
   * 
   * @see DiagramNode
   * 
   * @param from
   *          A {@link DiagramNode} representing the starting point of the <code>Transition</code>
   * @param to
   *          A {@link DiagramNode} representing the end point of the <code>Transition</code>
   * 
   * @return The newly created Transition
   * 
   * @throws ApiException
   *           <li>If either <code>from</code> or <code>to</code> are not part of the diagram or
   *           </li>
   *           <li>if the Transition would create a cycle that does not contain at least one
   *           {@link Testlet}</li>
   * @throws RemoteException
   *           If <code>from</code> or <code>to</code> are not part ot this TPT instance.
   */
  public Transition createTransition(DiagramNode from, DiagramNode to)
      throws ApiException, RemoteException;

  /**
   * Create a new horizontal {@link Line} with the height (vertical position) <code>y</code>
   * 
   * @param y
   *          The vertical position for the newly created {@link Line}
   * @return the newly created {@link Line}
   */
  public Line createLine(int y) throws ApiException, RemoteException;

  /**
   * Create a new {@link TextArea} at the position <code>pos</code> whith the content
   * <code>text</code>.
   * 
   * @param text
   *          The text that should be displayed in the <code>TextArea</code>. <code>Null</code> will
   *          be reduced to an empty string.
   * @param pos
   *          The position in the diagram given as {@link Point}.
   * @return The newly created <code>TextArea</code>
   */
  public TextArea createTextArea(String text, Point pos) throws ApiException, RemoteException;

  /**
   * Create a new diagram state ({@link Testlet}) at the given position
   *
   * @param name
   *          The name of the newly created state <code>Testlet</code>. <code>Null</code> will be
   *          reduced to an empty string.
   * @param pos
   *          The position of the <code>Testlet</code> in the diagram given as {@link Point}
   * @return the newly created {@link Testlet}
   */
  public Testlet createTestlet(String name, Point pos) throws ApiException, RemoteException;

  /**
   * Create a new state ({@link Testlet}) which is not visible in the diagram and is intended for
   * the use in the step list.
   *
   * @param name
   *          The name of the newly created state <code>Testlet</code>. <code>Null</code> will be
   *          reduced to an empty string.
   * @return the newly created {@link Testlet}
   */
  public Testlet createTestlet(String name) throws ApiException, RemoteException;

  /**
   * Create a new {@link Junction} at the given position <code>pos</pos>
   * 
   * @param pos
   *          The position in the diagram given as {@link Point}.
   * @return The newly created {@link Junction}
   */
  public Junction createJunction(Point pos) throws ApiException, RemoteException;

  /**
   * Creat a new {@link Final} junction at the given position <code>pos</code>
   * 
   * @param pos
   *          The Position in the Diagram given as {@link Point}.
   * @return The newly created {@link Final}
   */
  public Final createFinal(Point pos) throws ApiException, RemoteException;

}
