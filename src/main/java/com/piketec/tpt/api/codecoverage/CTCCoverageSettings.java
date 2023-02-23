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
package com.piketec.tpt.api.codecoverage;

import java.rmi.RemoteException;

/**
 * Represents the settings for a coverage measurement with CTC++ for an AUTOSAR or C/C++
 * Platform<br>
 * Use <code>PlatformConfiguration.setCoverageToCTC()</code> to get CTC settings, they will be used
 * for instrumentation afterwards
 */
public interface CTCCoverageSettings extends CoverageSettings {

  /**
   * @return The current symbol file, <code>null</code> if no symbol file is set
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getSymbolFile() throws RemoteException;

  /**
   * @param symbolFile
   *          The new symbol file
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSymbolFile(String symbolFile) throws RemoteException;

  /**
   * @return If function coverage is currently measured
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
   * @return If statement coverage is currently measured
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
   * @throws RemoteException
   *           remote communication problem
   */
  public void setStatementCoverage(boolean statementCoverage) throws RemoteException;

  /**
   * @return If decision coverage is currently measured
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
   * @return If condition coverage is currently measured
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
   * @return If MC/DC coverage is currently measured
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
   * @return If multicondition coverage is currenty measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isMulticonditionCoverage() throws RemoteException;

  /**
   * En- or disable multicondition coverage
   * 
   * @param multiconditionCoverage
   *          Should multicondition coverage be measured
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setMulticonditionCoverage(boolean multiconditionCoverage) throws RemoteException;

  /**
   * @return If annotations for report generation are enabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isEnableAnnotationsForReportGeneration() throws RemoteException;

  /**
   * En- or disable annotations for report generation
   * 
   * @param enableAnnotationForReportGeneration
   *          Should annotations for report generation be enabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setEnableAnnotationsForReportGeneration(boolean enableAnnotationForReportGeneration)
      throws RemoteException;

  /**
   * @return If HTML sources are disabled in the CTC report
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isDisableHTMLSourcesInCTCReport() throws RemoteException;

  /**
   * En- or disable HTML sources in the CTC report
   * 
   * @param disableHTMLSourcesInCTCReport
   *          Should HTML sources in the CTC report be disabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDisabledHTMLSourcesInCTCReport(boolean disableHTMLSourcesInCTCReport)
      throws RemoteException;

  /**
   * @return the source file list
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getSourceFileList() throws RemoteException;

  /**
   * Set the source file list
   * 
   * @param sourceFileList
   *          The new source file list, old values will be overwritten
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSourceFileList(String sourceFileList) throws RemoteException;

}
