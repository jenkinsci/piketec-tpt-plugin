/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2022 PikeTec GmbH
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

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.RemoteCollection;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.ScenarioGroup;
import com.piketec.tpt.api.Testlet;
import com.piketec.tpt.api.steplist.StepListScenario;

/**
 * A <code>DiagramScenario</code> is a automaton. It specifies
 * <ul>
 * <li>a path through the automaton given by a set of (active) {@link Transition Transitions}</li>
 * <li>a selected Variant ({@link Scenario}) for each Testlet in the path</li>
 * <li>a selected {@link TransitionSpec} for each of the above {@link Transition Transitions}</li>
 * </ul>
 *
 * <p>
 * In TPT, it represents both the variants as well as the test cases. Create a new
 * <code>DiagramScenario</code> via {@link Testlet#createDiagVariant(String, ScenarioGroup)}
 * </p>
 * 
 * @see Testlet#createSLVariant(String, ScenarioGroup)
 * @see Project#getTopLevelTestlet()
 */
public interface DiagramScenario extends Scenario {

  /**
   * @return The list of all selected {@link Transition Transitions} for this variant.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<Transition> getPath() throws RemoteException;

  /**
   * Add a {@link Transition} to the list of selected <code>Transitions</code> for this
   * <code>DiagramScenario</code>. Conflicting <code>Transitions</code> are automatically removed
   * from the list.
   * 
   * @param t
   *          The {@link Transition} to add to the path in the scenario.
   * 
   * @throws RemoteException
   *           If the given <code>Transition</code> object is not a {@link RemoteObject} or it does
   *           not originate from the TPT instance represented by this API object.
   */
  public void addTransitionToPath(Transition t) throws RemoteException;

  /**
   * Get the Variant that is currently selected for a given state in this {@link Scenario Variant}
   * or <code>null</code>.
   * 
   * @param state
   *          The <code>State</code>, for which the currently selected variant shall be examined.
   * @return <code>null</code> if no Variant has been previously selected for this
   *         <code>State</code> in the current Scenario. The selected {@link Scenario Variant}
   *         otherwise.
   * 
   * @throws RemoteException
   *           If the given <code>State</code> object is not a {@link RemoteObject} or it does not
   *           originate from the TPT instance represented by this API object.
   */
  public Scenario getSelectedVariant(Testlet state) throws RemoteException;

  /**
   * Set a given {@link Scenario Variant} for a given {@link Testlet} in the current
   * <code>Scenario</code>. If <code>variant==null</code>, the currently selected varant will be
   * deleted.
   *
   * @param state
   *          Represents the <code>State</code>, for which a variant shall be set.
   * @param variant
   *          A {@link Scenario Variant} to be set or <code>null</code> to reset the variant for
   *          this state.
   * 
   * @throws RemoteException
   *           If the given <code>state</code> or <code>variant</code> objects are not a
   *           {@link RemoteObject} or they do not originate from the TPT instance represented by
   *           this API object.
   */
  public void setSelectedVariant(Testlet state, Scenario variant) throws RemoteException;

  /**
   * Get the currently selected {@link TransitionSpec transition specification} for the given
   * {@link Transition} or <code>null</code>.
   * 
   * @param transition
   *          The <code>Transition</code>, for which the {@link TransitionSpec} shall be examined.
   * @return The currently selected {@link TransitionSpec} or <code>null</code> if none has been
   *         selected so far.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TransitionSpec getSelectedTransitionSpec(Transition transition) throws RemoteException;

  /**
   * Select a {@link TransitionSpec transition specification} for the given {@link Transition} in
   * the current <code>Scenario</code>.
   * 
   * @param transition
   *          The Transition, for which the new transition specification shall be set.
   * @param transitionSpec
   *          The new transition specification or <code>null</code> to select none.
   * 
   * @throws RemoteException
   *           If the given <code>transition</code> or <code>transitionSpec</code> objects are not a
   *           {@link RemoteObject} or they do not originate from the TPT instance represented by
   *           this API object.
   */
  public void setSelectedTransitionSpec(Transition transition, TransitionSpec transitionSpec)
      throws RemoteException;

  /**
   * Transforms this {@link DiagramScenario} into an empty {@link StepListScenario}. All contents
   * will be deleted, but the meta data like ids and attributes will be retained. Do not use this
   * {@link DiagramScenario} afterwards.
   * 
   * @return This {@link DiagramScenario} as an empty {@link StepListScenario}.
   * @throws RemoteException
   *           remote communication problem
   */
  public StepListScenario transformToStepList() throws RemoteException;

}
