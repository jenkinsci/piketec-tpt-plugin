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
import com.piketec.tpt.api.IdentifiableRemote;

/**
 * Eine horizontale Linie, die ein Diagramm in zwei Bereiche fuer prallele Automaten unterteilt.
 */
public interface Line extends IdentifiableRemote {

  /**
   * Fragt die Hoehenposition der Linie im Diagramm ab
   * 
   * @return Die y-Position
   */
  public int getPosition() throws ApiException, RemoteException;

  /**
   * Setzt die vertikale Position der Linie im Diagramm.
   * 
   * @param y
   *          Die Hoehenposition
   */
  public void setPosition(int y) throws ApiException, RemoteException;

}
