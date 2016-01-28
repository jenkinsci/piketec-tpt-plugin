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

/**
 * Eine Transitionsspezifikation beschreibt wann eine Transition ausgeloest werden darf (
 * {@link #getCondition()}) und was passiert, wenn sei ausgeloest wird ({@link #getActions()}).
 */
public interface TransitionSpec extends TransitionSpecOrGroup {

  /**
   * Liefert die Vorbedingung, die erfuellt sein muss, damit die Transition ausgeloest werden darf.
   * 
   * @return Die formale Vorbedingung
   */
  public String getCondition() throws ApiException, RemoteException;

  /**
   * Setzt die formale Vorbedingung, die erfuellt sein muss, damit die Transition ausgeloest werden
   * darf. Zur Syntax siehe im TPT Handbuch "Transitions and transition specifications"
   * 
   * @param condition
   *          Die formale Vorbedingung als String
   */
  public void setCondition(String condition) throws ApiException, RemoteException;

  /**
   * Liefert die Aktionen, die beim Ausloesen der Transition stattfinden.
   * 
   * @return Die Aktionen.
   */
  public String getActions() throws ApiException, RemoteException;

  /**
   * Setzt die Aktionen, die beim ausloesend er Transition ausgefuehrt werden. Zur Syntax siehe im
   * TPT Handbuch "Transitions and transition specifications"
   * 
   * @param actions
   *          Die Aktionen als String
   */
  public void setActions(String actions) throws ApiException, RemoteException;
}
