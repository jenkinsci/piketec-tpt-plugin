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

/**
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 *
 */
public interface Back2BackSettings extends IdentifiableRemote {

  /**
   * The type of the declaration available for back to back settings.
   */
  public enum VariableType {
    /**
     * Input channels
     */
    INPUT,
    /**
     * Output channels
     */
    OUTPUT,
    /**
     * Local channels
     */
    LOCAL,
    /**
     * Parameters
     */
    PARAMETER
  }

  /**
   * Creates and adds a {@link Back2BackRow signal comparison row} for the given variable name.
   * 
   * @param variable
   *          variable name of the variable to be compared
   * 
   * @return the newly created row instance for variable comparison
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Back2BackRow addRow(String variable) throws RemoteException;

  /**
   * Get all added {@link Back2BackRow signal comparison rows}.
   * 
   * @return get attribute rows
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<Back2BackRow> getRows() throws RemoteException;

  /**
   * @return the suffix added to the reference signals.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getReferenceSuffix() throws RemoteException;

  /**
   * Changes the suffix added to the reference {@link Channel signals}.
   * 
   * @param referenceSuffix
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setReferenceSuffix(String referenceSuffix) throws RemoteException;

  /**
   * @return the suffix added to the difference to hose {@link Channel signal}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDifferenceToHoseSuffix() throws RemoteException;

  /**
   * Changes the suffix added to the difference to hose {@link Channel signal}.
   * 
   * @param differenceToHoseSuffix
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDifferenceToHoseSuffix(String differenceToHoseSuffix) throws RemoteException;

  /**
   * @return the suffix added to the difference to the reference-signal {@link Channel signal}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDifferenceToReferenceSuffix() throws RemoteException;

  /**
   * Changes the suffix added to the difference to the reference-signal {@link Channel signal}.
   * 
   * @param differenceToReferenceSuffix
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDifferenceToReferenceSuffix(String differenceToReferenceSuffix)
      throws RemoteException;

  /**
   * @return <code>true</code> if the {@link Channel reference signals} are exported to the report.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isExportReferenceSignals() throws RemoteException;

  /**
   * change <code>true</code> if the {@link Channel reference signals} are exported to the report.
   * 
   * @param exportReferenceSignals
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setExportReferenceSignals(boolean exportReferenceSignals) throws RemoteException;

  /**
   * @return <code>true</code> if the difference to {@link Channel hose signals} are exported to the
   *         report.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isExportDifferenceToHoseSignals() throws RemoteException;

  /**
   * Change if the difference to {@link Channel hose signals} are exported to the report.
   * 
   * @param exportDifferenceToHoseSignals
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setExportDifferenceToHoseSignals(boolean exportDifferenceToHoseSignals)
      throws RemoteException;

  /**
   * @return <code>true</code> if the difference to the {@link Channel reference signals} are
   *         exported to the report.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isExportDifferenceToReferenceSignals() throws RemoteException;

  /**
   * Change if the difference to the {@link Channel reference signals} are exported to the report.
   * 
   * @param exportDifferenceToReferenceSignals
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setExportDifferenceToReferenceSignals(boolean exportDifferenceToReferenceSignals)
      throws RemoteException;

  /**
   * @return <code>true</code> if patterns are used to highlight signals.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isHighlightSelectiveSamplePoints() throws RemoteException;

  /**
   * Changes if patterns are used to highlight signals.
   * 
   * @param usePatternsToHighlightSignals
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setHighlightSelectiveSamplePoints(boolean usePatternsToHighlightSignals)
      throws RemoteException;

  /**
   * @return <code>true</code> if the differenceSignal calculated by TPT.hose will not be shown but
   *         used to display, where the Assesslet fails (red background in SignalViewer). Instead an
   *         absolute Signal will be calculated and displayed.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isDisplayDifferenceToReference() throws RemoteException;

  /**
   * If set to true, the differenceSignal calculated by TPT.hose will not be shown but used to
   * display, where the Assesslet fails (red background in SignalViewer). Instead an absolute Signal
   * will be calculated and displayed.
   * 
   * @param displayDifferenceToReference
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDisplayDifferenceToReference(boolean displayDifferenceToReference)
      throws RemoteException;

  /**
   * @return <code>true</code> if the time-boundaries are ignored.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isIgnoreTimeBoundaries() throws RemoteException;

  /**
   * Change if the time-boundaries are ignored.
   * 
   * @param ignoreTimeBoundaries
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setIgnoreTimeBoundaries(boolean ignoreTimeBoundaries) throws RemoteException;

  /**
   * @return {@link Mapping} that is used for the tolerance.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Mapping getMappingForTolerance() throws RemoteException;

  /**
   * Change {@link Mapping} that is used for the tolerance.
   * 
   * @param mappingForTolerance
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setMappingForTolerance(Mapping mappingForTolerance) throws RemoteException;

  /**
   * @return {@link String} that represents the reference-data-time-shift.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getReferenceDataTimeShift() throws RemoteException;

  /**
   * Change {@link String} that represents the reference-data-time-shift.
   * 
   * @param referenceDataTimeShift
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setReferenceDataTimeShift(String referenceDataTimeShift) throws RemoteException;

  /**
   * @return {@link String} that represents the comparison-trigger-expression.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getComparisonTriggerExpression() throws RemoteException;

  /**
   * Change the {@link String} that represents the comparison-trigger-expression.
   * 
   * @param sampleFilterExpression
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setComparisonTriggerExpression(String sampleFilterExpression) throws RemoteException;

  /**
   * Set the reference directory of the parent {@link ExecutionConfiguration} as reference. A call
   * to {@link #getReference()} will return the {@link ExecutionConfiguration} in this case.
   * 
   * @throws ApiException
   *           If the configuration is not part of the model anymore.
   * @throws RemoteException
   *           remote communication problem
   */
  void setReferenceToReferenceDirectory() throws RemoteException, ApiException;

  /**
   * Set a reference file as reference.
   * 
   * @param path
   *          The path to the reference file.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void setReferenceToFile(String path) throws RemoteException;

  /**
   * Set an other execution configuration item as reference.
   * 
   * @param item
   *          the execution configuration item.
   * 
   * @throws ApiException
   *           If <code>item</code> is from another model or is the same as the execution
   *           configuration item this back to back settings belong to
   *           ({@link #getExecConfigItem()}).
   * @throws RemoteException
   *           remote communication problem
   */
  void setReferenceToExecutionConfigurationItem(ExecutionConfigurationItem item)
      throws RemoteException, ApiException;

  /**
   * There are three types of possible references and therefore three different types of return
   * values.
   * <ol>
   * <li><b>File:</b> A path to reference file is given. The path is returned as a
   * {@link String}.</li>
   * <li><b>Execution configuration item:</b> An other execution configuration item is used as
   * reference. This {@link ExecutionConfigurationItem} is returned.</li>
   * <li><b>Reference directory:</b> The reference directory of the execution configuration is set
   * as a reference. the {@link ExecutionConfiguration} is returned.</li>
   * </ol>
   * 
   * @see #setReferenceToReferenceDirectory()
   * @see #setReferenceToFile(String)
   * @see #setReferenceToExecutionConfigurationItem(ExecutionConfigurationItem)
   * 
   * @return The back to back reference of this setting.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Object getReference() throws RemoteException;

  /**
   * Changes if auto-update is enabled for {@link Type type}.
   * 
   * @param type
   * @param on
   */

  /**
   * turn on/off auto-update feature for the specified {@link VariableType}
   * 
   * @param type
   *          variable type to change the settings for
   * @param on
   *          turn auto-update on or off
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAutoUpdate(VariableType type, boolean on) throws RemoteException;

  /**
   * @param type
   *          type for which the current setting should be determined
   * 
   * @return <code>true</code> if auto-update is enabled for {@link VariableType type}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isAutoUpdate(VariableType type) throws RemoteException;

  /**
   * @return <code>true</code> if differences in a sample are ignored. Default value is
   *         <code>true</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isIgnoreUndefinedPhases() throws RemoteException;

  /**
   * Changes if differences in a sample are ignored. Default value is <code>true</code>.
   * 
   * @param ignoreUndefinedPhases
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setIgnoreUndefinedPhases(boolean ignoreUndefinedPhases) throws RemoteException;

  /**
   * @return {@link ExecutionConfigurationItem} related to this {@link Back2BackSettings}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public ExecutionConfigurationItem getExecConfigItem() throws RemoteException;

}
