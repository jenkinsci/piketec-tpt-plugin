/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2025 Synopsys Inc.
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
package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.IdentifiableRemote;

/**
 * {@link FormalRequirementDefine define} is time- dependent assessment variable to which value
 * complex expressions can be assigned. These variables can be reused in the Expression fields of
 * all formal requirement steps of the current TPT project.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface FormalRequirementDefine extends IdentifiableRemote {

  /**
   * Returns the name of the {@link FormalRequirementDefine define}
   * 
   * @return the name of the {@link FormalRequirementDefine define}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getName() throws RemoteException;

  /**
   * Sets the value of the {@link FormalRequirementDefine define}
   * 
   * @param value
   *          the value of the {@link FormalRequirementDefine define}
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the value is {@code null}
   */
  public void setValue(String value) throws RemoteException, ApiException;

  /**
   * Returns the value of the {@link FormalRequirementDefine define}
   * 
   * @return the value of the {@link FormalRequirementDefine define}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getValue() throws RemoteException;

}
