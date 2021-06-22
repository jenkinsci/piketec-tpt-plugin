/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2021 PikeTec GmbH
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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.Pair;
import com.piketec.tpt.api.Type;

/**
 * This {@link Step} provides the possibility to define a channel with given signal data.
 */
public interface EmbeddedSignalStep extends Step {

  /**
   * @return the embedded signal.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDeclaration() throws RemoteException;

  /**
   * Sets the embedded signal.
   * 
   * @param name
   *          name of the channel to define
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDeclaration(String name) throws RemoteException;

  /**
   * @return a map with all sample points. Look at
   *         {@link EmbeddedSignalStep#setSamplePoints(List, Type)} for further information.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<Pair<Long, String>> getSamplePoints() throws RemoteException;

  /**
   * Sets a map with all sample points. The {@link Long} parameter represents the time in micro
   * seconds and the {@link String} the value of the signal at this time point. Values can be
   * primitives or arrays of primitives.
   * 
   * @param signalPoints
   *          list of all samples
   * @param type
   *          data type to use
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSamplePoints(List<Pair<Long, String>> signalPoints, Type type)
      throws RemoteException;

  /**
   * @return <code>true</code> if the linear interpolation mode is enabled, <code>false</code> if
   *         last value mode is enabled.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isLinearInterpolation() throws RemoteException;

  /**
   * Set to <code>true</code> if linear interpolation mode is enabled, <code>false</code> if last
   * value mode is enabled.
   * 
   * @param on
   *          linear interpolation on/off
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setLinearInterpolation(boolean on) throws RemoteException;

}
