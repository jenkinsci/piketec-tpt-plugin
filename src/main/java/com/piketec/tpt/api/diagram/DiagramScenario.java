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

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.RemoteCollection;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.Testlet;

/**
 * Ein DiagramScenario ist eine Testcase-Spezifkation oder eine Variante von einem
 * {@link DiagramTestlet}.
 * 
 * @see Testlet#createVariant(String, com.piketec.tpt.api.ScenarioGroup)
 */
public interface DiagramScenario extends Scenario {

  /**
   * Die Liste aller fuer dieses Scenario ausgewaehlten Transitionen.
   *
   */
  public RemoteCollection<Transition> getPath() throws ApiException, RemoteException;

  /**
   * Fuegt eine Transition der Liste der fuer dieses Scenario ausgewaehlten Transitionen hinzu.
   * 
   * @param t
   *          Die in den Pfad aufzunehmende Transition.
   * @throws RemoteException
   *           Wenn die uebergeben Transition kein Remoteobjekt ist, dass von der angesprochenen
   *           TPT-Instance stammt.
   */
  public void addTransitionToPath(Transition t) throws ApiException, RemoteException;

  /**
   * Die zu einem Zustand ausgewaehlte Variante oder <code>null</code>.
   * 
   * @param state
   *          Der <code>State</code>, dessen ausgewaehlte Variante zurueckgeliefert werden soll.
   * @return <code>null</code> wenn bisher keine Variante zu dem uebergebenene <code>State</code>
   *         ausgewaehlt wurde, sonst das ausgewaehlte <code>Scenario</code>
   * @throws RemoteException
   *           Wenn der uebergeben State kein Remoteobjekt ist, dass von der angesprochenen
   *           TPT-Instance stammt.
   */
  public Scenario getSelectedVariant(State state) throws ApiException, RemoteException;

  /**
   * Setzt die Variante fuer einen Zustand. Wenn <code>varian==null</code> ist, wird die Definition
   * fuer den Zustand geloescht.
   *
   * @param state
   *          Der Zustand, dem die Variante zugewiesen werden soll
   * @param variant
   *          Die Variante oder null wenn keine Variante gewaehlt sein soll
   * @throws RemoteException
   *           Wenn <code>state</code> oder <code>variant</code> kein Remoteobjekt ist, dass vond er
   *           angesprochen TPT-Instanz stammt.
   */
  public void setSelectedVariant(State state, Scenario variant) throws ApiException,
      RemoteException;

  /**
   * Die fuer eine Transition gewaehlt Transitionsspezifikation oder <code>null</code>.
   * 
   * @param transition
   *          Die Transition, zu der die gewaehlte Transitionsspezifikation gesucht wird oder
   *          <code>null</code>, wenn bisher keine gesetzt wurde.
   */
  public TransitionSpec getSelectedTransitionSpec(Transition transition) throws ApiException,
      RemoteException;

  /**
   * Setzt die Transitionsspezifikation fuer die Transition.
   * 
   * @param transition
   *          Die Transition, deren Transitionsspezifikation gesetzt werden soll.
   * @param transitionSpec
   *          Die Transitionsspezifikation oder <code>null</code>
   * @throws RemoteException
   *           Wenn <code>transition</code> oder <code>transitionSpec</code> kein Remoteobjekt ist,
   *           dass vond er angesprochen TPT-Instanz stammt.
   */
  public void setSelectedTransitionSpec(Transition transition, TransitionSpec transitionSpec)
      throws ApiException, RemoteException;

}
