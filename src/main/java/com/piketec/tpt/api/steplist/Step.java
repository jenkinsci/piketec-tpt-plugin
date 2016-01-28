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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.IdentifiableRemote;
import com.piketec.tpt.api.properties.PropertyMap;

/**
 * Ein Step ist ein Eintrag in einem {@link StepListScenario}.
 */
public interface Step extends IdentifiableRemote {

  /**
   * @return Den Namen des Step-Types
   */
  public String getType() throws ApiException, RemoteException;

  /**
   * Liefert eine ProptertyMap String -&gt; Property zur Darstellung der Properties. Eine Property
   * ist entweder erneut eine PropertyMap, wodurch eine Baumstruktur entsteht oder ein String-Wert.
   *
   * @return Die Properties des Steps
   */
  public PropertyMap getProperties() throws ApiException, RemoteException;

  /**
   * Konfiguriert den Step anhand der uebergebenen PropertyMap.
   *
   * @param properties
   */
  public void setProperties(PropertyMap properties) throws ApiException, RemoteException;

}
