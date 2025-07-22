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
import java.util.List;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.IdentifiableRemote;
import com.piketec.tpt.api.Requirement;

/**
 * A step is one entry in a {@link List} of {@link ConditionTreeNode} in {@link Requirement} it can
 * be created via {@link Requirement#createFormalRequirementNode(int, String)}.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface ConditionTreeNode extends IdentifiableRemote {

  /**
   * @return the name of the step type.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getType() throws RemoteException;

  /**
   * TPT offers the possibility to assign some objects comments which are added to the report. This
   * method provides the possibility to define such a {@link String}.
   * 
   * @param documentation
   *          a {@link String} which will be added as a comment to the report.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the documentation is {@code null}
   */
  public void setDocumentation(String documentation) throws RemoteException, ApiException;

  /**
   * @return a {@link String} which will be added as a comment to the report.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDocumentation() throws RemoteException;

  /**
   * Sets if this step is enabled.
   * 
   * @param value
   *          <code>true</code> if this step is active/enabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setActive(boolean value) throws RemoteException;

  /**
   * Determines if this step is enabled.
   *
   * @return <code>true</code> if this step is active/enabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isActive() throws RemoteException;

  /**
   * @return the hierarchy level (indent) of the FormalRequiermentStep. The hierarchy level starts
   *         from 0 and counts upwards. The indentation concatenates the indented step with the
   *         parent step using boolean AND. Formal requirement steps at the same indentation level
   *         are concatenated using boolean OR.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getHierarchyLevel() throws RemoteException;

  /**
   * Sets the hierarchy level (indent) of the FormalRequiermentStep. The hierarchy level starts from
   * 0 and counts upwards. The indentation concatenates the indented step with the parent step using
   * boolean AND. Formal requirement steps at the same indentation level are concatenated using
   * boolean OR.
   * 
   * @param level
   *          the hierarchy level.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setHierarchyLevel(int level) throws RemoteException;

}
