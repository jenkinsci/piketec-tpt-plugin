/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2024 Synopsys Inc.
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
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
 * </p>
 */
public interface Testlet extends DiagramNode, Positioned {

  /**
   * @return Returns <code>true</code>, if this testlet has defined content. This means it is either
   *         a testlet or a reference. Returns <code>false</code> if no content has been set or if
   *         the content has been deleted (for example by the context menu in the TPT GUI).
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean hasDefinedContent() throws RemoteException;

  /**
   * @return Returns {@link Testlet} that owns content. If no reference exists or
   *         <code>TestletContent</code> is null, it returns <code>null</code>.
   * @throws RemoteException
   *           remote communication problem
   * @see #isReferencing()
   */
  public Testlet getReference() throws RemoteException;

  /**
   * @return <code>true</code> if content of {@link Testlet} has different owner, otherwise
   *         <code>false</code>.
   * @throws RemoteException
   *           remote communication problem
   * @see #getReference()
   */
  public Boolean isReferencing() throws RemoteException;

  /**
   * Creates a new local definition for this state that has not been referenced so far.
   * Additionally, a {@link StepListScenario} is created for this state. Any previously existing
   * definition for this state will be replaced by the new one. Any existing {@link Scenario
   * Scenarios} will be deleted.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void createNewContent() throws RemoteException;

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
   * 
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the content of the <code>Testlet</code> is empty.
   */
  public StepListScenario createSLVariant(String name, ScenarioGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * Creates a new diagram variant ({@link DiagramScenario}) or a new test case (if the current
   * <code>Testlet</code> is the top-level <code>Testlet</code>). If the parameter
   * <code>groupOrNull==null</code>, the newly created StepList variant is placed directly below the
   * Testlet ({@link Testlet#getTopLevelScenarioOrGroup()}). If a specific {@link ScenarioGroup} is
   * given, the newly created variant is placed there.
   * 
   * @param name
   *          The name of the new variant. <code>Null</code> will be reduced to an empty string.
   * @param groupOrNull
   *          Either the group where the newly created {@link DiagramScenario} should be added or
   *          <code>null</code> to add it in the top-level group of this <code>Testlet</code>.
   * @return the newly created {@link DiagramScenario}
   * 
   * @throws RemoteException
   *           remote communication problem
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
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the content of the <code>Testlet</code> is empty
   */
  public ScenarioGroup createVariantGroup(String name, ScenarioGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * Set if diagram elements (states, transitions, junctions etc.) can be moved in the TPT UI.
   * 
   * @param diagramElementsMovable
   *          <code>true</code> if diagram elements should be movable in the TPT UI
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the content of the <code>Testlet</code> is empty
   */
  public void setDiagramElementsMovable(boolean diagramElementsMovable)
      throws ApiException, RemoteException;

  /**
   * Returns if diagram elements (states, transitions, junctions etc.) can be moved in the TPT UI.
   * 
   * @return <code>true</code> if diagram elements can be moved in the TPT UI. <code>false</code>
   *         otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the content of the <code>Testlet</code> is empty
   */
  public boolean isDiagramElementsMovable() throws ApiException, RemoteException;

  /**
   * Returns the <u>CONTENTS</u> of the top level {@link ScenarioGroup} of this <code>Testlet</code>
   * as list of {@link ScenarioOrGroup}.
   * 
   * @return The list of {@link ScenarioOrGroup} that are contained in the top level group.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<ScenarioOrGroup> getTopLevelScenarioOrGroup() throws RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all {@link Transition Transitions} in
   *         this hierarchy layer of the diagram.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<Transition> getTransitions() throws RemoteException;

  /**
   * Delivers the first transition with the given name or <code>null</code> if no such transition
   * exists.
   * 
   * @param name
   *          The name of the <code>Transition</code>.
   * @return The {@link PlatformConfiguration} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Transition getTransitionByName(String name) throws RemoteException;

  /**
   * Delivers all transitions, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * @return List of all {@link Transition Transitions}, matching the given name pattern.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<Transition> getTransitionsByNamePattern(Pattern namepattern) throws RemoteException;

  /**
   * Delivers all transitions, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * @return List of all {@link Transition Transitions}, matching the given name pattern.
   * 
   * @throws PatternSyntaxException
   *           If the expression's syntax is invalid
   * @throws RemoteException
   *           remote communication problem
   */
  public List<Transition> getTransitionsByNamePattern(String namepattern)
      throws PatternSyntaxException, RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all {@link Line Lines} in this
   *         hierarchy layer of the diagram.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<Line> getLines() throws RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all {@link TextArea text areas} in
   *         this hierarchy layer of the diagram.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<TextArea> getTextAreas() throws RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all {@link Testlet Testlets} in this
   *         hierarchy layer of the diagram.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<Testlet> getStates() throws RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all {@link Junction Junctions} in this
   *         hierarchy layer of the diagram.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<Junction> getJunctions() throws RemoteException;

  /**
   * @return Returns a {@link RemoteCollection} that contains all final junctions ({@link Final}) in
   *         this hierarchy layer of the diagram.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<Final> getFinals() throws RemoteException;

  /**
   * Get the top level group that contains all {@link ScenarioOrGroup ScenarioOrGroups} that are
   * delivered by {@link #getTopLevelScenarioOrGroup()}. A call of
   * {@link ScenarioOrGroup#getGroup()} on this group will deliver <code>null</code>.
   * 
   * @return the top-level scenario group or <code>null</code> if the testlet has no content
   *         ({@link #hasDefinedContent()}).
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  ScenarioGroup getToplevelScenarioGroup() throws RemoteException;

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
   *           <ul>
   *           <li>If either <code>from</code> or <code>to</code> are not part of the diagram or
   *           </li>
   *           <li>if the Transition would create a cycle that does not contain at least one
   *           {@link Testlet}</li>
   *           </ul>
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
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Line createLine(int y) throws RemoteException;

  /**
   * Create a new {@link TextArea} at the position <code>pos</code> with the content
   * <code>text</code>.
   * 
   * @param text
   *          The text that should be displayed in the <code>TextArea</code>. <code>Null</code> will
   *          be reduced to an empty string.
   * @param pos
   *          The position in the diagram given as {@link Point}.
   * @return The newly created <code>TextArea</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TextArea createTextArea(String text, Point pos) throws RemoteException;

  /**
   * Create a new {@link TextArea} at the given position with the content <code>text</code>.
   * 
   * @param text
   *          The text that should be displayed in the <code>TextArea</code>. <code>Null</code> will
   *          be reduced to an empty string.
   * @param x
   *          The x position in the diagram.
   * @param y
   *          The y position in the diagram.
   * @return The newly created <code>TextArea</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TextArea createTextArea(String text, int x, int y) throws RemoteException;

  /**
   * Create a new diagram state ({@link Testlet}) at the given position
   *
   * @param name
   *          The name of the newly created state <code>Testlet</code>. <code>Null</code> will be
   *          reduced to an empty string.
   * @param pos
   *          The position of the <code>Testlet</code> in the diagram given as {@link Point}
   * @return the newly created {@link Testlet}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Testlet createTestlet(String name, Point pos) throws RemoteException;

  /**
   * Create a new diagram state ({@link Testlet}) at the given position
   *
   * @param name
   *          The name of the newly created state <code>Testlet</code>. <code>Null</code> will be
   *          reduced to an empty string.
   * @param x
   *          The x position of the <code>Testlet</code> in the diagram
   * @param y
   *          The y position of the <code>Testlet</code> in the diagram
   * @return the newly created {@link Testlet}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Testlet createTestlet(String name, int x, int y) throws RemoteException;

  /**
   * Create a new state ({@link Testlet}) which is not visible in the diagram and is intended for
   * the use in the step list.
   *
   * @param name
   *          The name of the newly created state <code>Testlet</code>. <code>Null</code> will be
   *          reduced to an empty string.
   * @return the newly created {@link Testlet}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Testlet createTestlet(String name) throws RemoteException;

  /**
   * Create a new {@link Junction} at the given position <code>pos</code>
   * 
   * @param pos
   *          The position in the diagram given as {@link Point}.
   * @return The newly created {@link Junction}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Junction createJunction(Point pos) throws RemoteException;

  /**
   * Create a new {@link Junction} at the by x and y given position
   * 
   * @param x
   *          The x position in the diagram.
   * @param y
   *          The y position in the diagram.
   * @return The newly created {@link Junction}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Junction createJunction(int x, int y) throws RemoteException;

  /**
   * Create a new {@link Final} junction at the given position <code>pos</code>
   * 
   * @param pos
   *          The Position in the Diagram given as {@link Point}.
   * @return The newly created {@link Final}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Final createFinal(Point pos) throws RemoteException;

  /**
   * Create a new {@link Final} junction at the by x and y given position
   * 
   * @param x
   *          The x Position in the Diagram given as {@link Point}.
   * @param y
   *          The y Position in the Diagram given as {@link Point}.
   * @return The newly created {@link Final}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Final createFinal(int x, int y) throws RemoteException;

  /**
   * Copies <code>this</code> into the given <code>target</code> that can be from a different
   * {@link Project} that is opened in the same TPT instance. If the <code>target</code> already
   * contains an element with the same name a new one will be generated.
   * 
   * @param target
   *          The testlet to copy <code>this</code> into. Can be from another <code>Project</code>.
   * @return The copy of this and all log messages that occured during copying.
   * @throws ApiException
   *           If target is <code>null</code> or copying failed.
   * @throws RemoteException
   *           remote communication problem
   */
  ResultAndLogs<Testlet> copy(Testlet target) throws RemoteException, ApiException;
}
