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
package com.piketec.tpt.api;

import java.rmi.RemoteException;

import com.piketec.tpt.api.constants.assessments.GlobalVariable;

/**
 * A row of the global assessment. It defines constraints for a variable that must hold for a whole
 * test execution, so for all executed test cases together.
 * 
 * @see ExecutionConfiguration#createGlobalAssessmentRow()
 * @see ExecutionConfiguration#getGlobalAssessmentRows()
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 *
 * @deprecated Will be removed in TPT-20. Use assesslet {@link GlobalVariable}.
 */
@Deprecated
public interface GlobalAssessmentRow extends TptRemote {

  /**
   * Returns if this row is enabled. Only enabled rows will be considered during execution.
   * 
   * @return <code>true</code> if this row is enabled, <code>false</code> otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isEnabled() throws RemoteException;

  /**
   * Enables or disables this row. Only enabled rows will be considered during execution.
   * 
   * @param enabled
   *          <code>true</code> to enable this row, <code>false</code> otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setEnabled(boolean enabled) throws RemoteException;

  /**
   * Average value (arithmetic mean over all testcases) must be between {@link #getAverageMin()} and
   * {@link #getAverageMax()}
   * 
   * @return the maximum value constraint
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getAverageMax() throws RemoteException;

  /**
   * Average value (arithmetic mean over all testcases) must be between {@link #getAverageMin()} and
   * {@link #getAverageMax()}
   * 
   * @param averageMax
   *          The maximum value constraint. <code>Null</code> will be reduced to an empty string.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAverageMax(String averageMax) throws RemoteException;

  /**
   * Average value (arithmetic mean over all testcases) must be between {@link #getAverageMin()} and
   * {@link #getAverageMax()}
   * 
   * @return the minimum value constraint
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getAverageMin() throws RemoteException;

  /**
   * Average value (arithmetic mean over all testcases) must be between {@link #getAverageMin()} and
   * {@link #getAverageMax()}
   * 
   * @param averageMin
   *          The minimum value constraint. <code>Null</code> will be reduced to an empty string.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAverageMin(String averageMin) throws RemoteException;

  /**
   * Total count of occurrences (in all testcases) must between {@link #getCountMin()} and
   * {@link #getCountMax()}
   * 
   * @return the maximum value constraint
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCountMax() throws RemoteException;

  /**
   * Total count of occurrences (in all testcases) must between {@link #getCountMin()} and
   * {@link #getCountMax()}
   * 
   * @param countMax
   *          The maximum value constraint. <code>Null</code> will be reduced to an empty string.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCountMax(String countMax) throws RemoteException;

  /**
   * Total count of occurrences (in all testcases) must between {@link #getCountMin()} and
   * {@link #getCountMax()}
   * 
   * @return the minimum value constraint
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCountMin() throws RemoteException;

  /**
   * Total count of occurrences (in all testcases) must between {@link #getCountMin()} and
   * {@link #getCountMax()}
   * 
   * @param countMin
   *          The minimum value constraint. <code>Null</code> will be reduced to an empty string.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCountMin(String countMin) throws RemoteException;

  /**
   * Each variable value (one value per testcase) must be between {@link #getValueMin()} and
   * {@link #getValueMax()}
   * 
   * @return the maximum value constraint
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getValueMax() throws RemoteException;

  /**
   * Each variable value (one value per testcase) must be between {@link #getValueMin()} and
   * {@link #getValueMax()}
   * 
   * @param valueMax
   *          The maximum value constraint. <code>Null</code> will be reduced to an empty string.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setValueMax(String valueMax) throws RemoteException;

  /**
   * Each variable value (one value per testcase) must be between {@link #getValueMin()} and
   * {@link #getValueMax()}
   * 
   * @return the minimum value constraint
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getValueMin() throws RemoteException;

  /**
   * Each variable value (one value per testcase) must be between {@link #getValueMin()} and
   * {@link #getValueMax()}
   * 
   * @param valueMin
   *          The minimum value constraint. <code>Null</code> will be reduced to an empty string.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setValueMin(String valueMin) throws RemoteException;

  /**
   * Sum of all variables (from all testcases) must be between {@link #getSumMin()} and
   * {@link #getSumMax()}
   * 
   * @return the maximum value constraint
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getSumMax() throws RemoteException;

  /**
   * Sum of all variables (from all testcases) must be between {@link #getSumMin()} and
   * {@link #getSumMax()}
   * 
   * @param sumMax
   *          The maximum value constraint. <code>Null</code> will be reduced to an empty string.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSumMax(String sumMax) throws RemoteException;

  /**
   * Sum of all variables (from all testcases) must be between {@link #getSumMin()} and
   * {@link #getSumMax()}
   * 
   * @return the minimum value constraint
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getSumMin() throws RemoteException;

  /**
   * Sum of all variables (from all testcases) must be between {@link #getSumMin()} and
   * {@link #getSumMax()}
   * 
   * @param sumMin
   *          The minimum value constraint. <code>Null</code> will be reduced to an empty string.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSumMin(String sumMin) throws RemoteException;

  /**
   * @return assessment-variable that will be evaluated for all executed test cases
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getVariable() throws RemoteException;

  /**
   * @param variable
   *          The assessment variable that shall be evaluated for all executed test cases.
   *          <code>Null</code> will be reduced to an empty string.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setVariable(String variable) throws RemoteException;

}
