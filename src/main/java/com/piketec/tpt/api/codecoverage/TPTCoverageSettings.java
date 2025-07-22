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
package com.piketec.tpt.api.codecoverage;

import java.rmi.RemoteException;

/**
 * Represents the settings for a coverage measurement with TPT Coverage.<br>
 * Use <code>CCodePlatformConfiguration.setCoverageToTPT()</code> to get TPT Coverage settings, they
 * will be used for instrumentation afterwards
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface TPTCoverageSettings extends CoverageSettings {

  /**
   * @return If statement coverage is measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isStatementCoverage() throws RemoteException;

  /**
   * En- or disable statement coverage measurement
   * 
   * @param statementCoverage
   *          Should statement coverage be measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setStatementCoverage(boolean statementCoverage) throws RemoteException;

  /**
   * @return If function coverage is measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isFunctionCoverage() throws RemoteException;

  /**
   * En- or disable function coverage measurement
   * 
   * @param functionCoverage
   *          Should function coverage be measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setFunctionCoverage(boolean functionCoverage) throws RemoteException;

  /**
   * @return If decision coverage is measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isDecisionCoverage() throws RemoteException;

  /**
   * En- or disable decision coverage measurement
   * 
   * @param decisionCoverage
   *          Should decision coverage be measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDecisionCoverage(boolean decisionCoverage) throws RemoteException;

  /**
   * 
   * @return If condition coverage is measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isConditionCoverage() throws RemoteException;

  /**
   * En- or disable condition coverage measurement
   * 
   * @param conditionCoverage
   *          Should condition coverage be measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setConditionCoverage(boolean conditionCoverage) throws RemoteException;

  /**
   * 
   * @return If MC/DC coverage is measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isMCDCCoverage() throws RemoteException;

  /**
   * En- or disable MC/DC coverage measurement
   * 
   * @param mcdcCoverage
   *          Should MC/DC coverage be measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setMCDCCoverage(boolean mcdcCoverage) throws RemoteException;

  /**
   * Set the amount of spaces used to replace tab stops in the code, when generating the coverage
   * report
   * 
   * @param spacesPerTab
   *          The amount of spaces used to replace tabstops in the code, when generating the
   *          coverage report
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSpacesPerTab(int spacesPerTab) throws RemoteException;

  /**
   * 
   * @return The amount of spaces used to replace tabstops in the code, when generating the coverage
   *         report
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getSpacesPerTab() throws RemoteException;

  /**
   * @param searchHint
   *          The search hint used in the generated coverage report
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSearchHint(String searchHint) throws RemoteException;

  /**
   * @return The search hint used in the generated coverage report
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getSearchHint() throws RemoteException;

}
