/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2018 PikeTec GmbH
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
 * @author Copyright (c) 2018 Piketec GmbH - MIT License (MIT)
 *
 */
public interface Back2BackSettings extends IdentifiableRemote {

  public enum VariableType {
    INPUT, OUTPUT, LOCAL, PARAMETER
  }

  /**
   * Creates and adds a {@link Back2BackRow signal comparison row} for the given channel name.
   */
  public Back2BackRow addRow(String channel) throws RemoteException, ApiException;

  /**
   * Get all added {@link Back2BackRow signal comparison rows}.
   * 
   * @return get attribute rows
   */
  public RemoteList<Back2BackRow> getRows() throws RemoteException, ApiException;

  /**
   * @return the suffix added to the reference {@link Channel signals}.
   */
  public String getReferenceSuffix() throws RemoteException, ApiException;

  /**
   * Changes the suffix added to the reference {@link Channel signals}.
   * 
   * @param referenceSuffix
   *          the new attribute value
   */
  public void setReferenceSuffix(String referenceSuffix) throws RemoteException, ApiException;

  /**
   * @return the suffix added to the difference to hose {@link Channel signal}.
   */
  public String getDifferenceToHoseSuffix() throws RemoteException, ApiException;

  /**
   * Changes the suffix added to the difference to hose {@link Channel signal}.
   * 
   * @param differenceToHoseSuffix
   *          the new attribute value
   */
  public void setDifferenceToHoseSuffix(String differenceToHoseSuffix)
      throws RemoteException, ApiException;

  /**
   * @return the suffix added to the difference to the reference-signal {@link Channel signal}.
   */
  public String getDifferenceToReferenceSuffix() throws RemoteException, ApiException;

  /**
   * Changes the suffix added to the difference to the reference-signal {@link Channel signal}.
   * 
   * @param differenceToReferenceSuffix
   *          the new attribute value
   */
  public void setDifferenceToReferenceSuffix(String differenceToReferenceSuffix)
      throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if the {@link Channel reference signals} are exported.
   */
  public boolean isExportReferenceSignals() throws RemoteException, ApiException;

  /**
   * change <code>true</code> if the {@link Channel reference signals} are exported.
   * 
   * @param exportReferenceSignals
   *          the new attribute value
   */
  public void setExportReferenceSignals(boolean exportReferenceSignals)
      throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if the difference to {@link Channel hose signals} are exported.
   */
  public boolean isExportDifferenceToHoseSignals() throws RemoteException, ApiException;

  /**
   * Change if the difference to {@link Channel hose signals} are exported.
   * 
   * @param exportDifferenceToHoseSignals
   *          the new attribute value
   */
  public void setExportDifferenceToHoseSignals(boolean exportDifferenceToHoseSignals)
      throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if the difference to the {@link Channel reference signals} are
   *         exported.
   */
  public boolean isExportDifferenceToReferenceSignals() throws RemoteException, ApiException;

  /**
   * Change if the difference to the {@link Channel reference signals} are exported.
   * 
   * @param exportDifferenceToReferenceSignals
   *          the new attribute value
   */
  public void setExportDifferenceToReferenceSignals(boolean exportDifferenceToReferenceSignals)
      throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if patterns are used to highlight signals.
   */
  public boolean isUsePatternsToHighlightSignals() throws RemoteException, ApiException;

  /**
   * Changes if patterns are used to highlight signals.
   * 
   * @param usePatternsToHighlightSignals
   *          the new attribute value
   */
  public void setUsePatternsToHighlightSignals(boolean usePatternsToHighlightSignals)
      throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if the differenceSignal calculated by TPT.hose will not be shown but
   *         used to display, where the Assesslet fails (red background in SignalViewer). Instead an
   *         absolute Signal will be calculated and displayed.
   */
  public boolean isDisplayDifferenceToReference() throws RemoteException, ApiException;

  /**
   * If set to true, the differenceSignal calculated by TPT.hose will not be shown but used to
   * display, where the Assesslet fails (red background in SignalViewer). Instead an absolute Signal
   * will be calculated and displayed.
   * 
   * @param displayDifferenceToReference
   *          the new attribute value
   */
  public void setDisplayDifferenceToReference(boolean displayDifferenceToReference)
      throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if the suffixes are ignored.
   */
  public boolean isIgnoreSuffix() throws RemoteException, ApiException;

  /**
   * Change if the suffixes are ignored.
   * 
   * @param ignoreSuffix
   *          the new attribute value
   */
  public void setIgnoreSuffix(boolean ignoreSuffix) throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if the time-boundaries are ignored.
   */
  public boolean isIgnoreTimeBoundaries() throws RemoteException, ApiException;

  /**
   * Change if the time-boundaries are ignored.
   * 
   * @param ignoreTimeBoundaries
   *          the new attribute value
   */
  public void setIgnoreTimeBoundaries(boolean ignoreTimeBoundaries)
      throws RemoteException, ApiException;

  /**
   * @return {@link Mapping} that is used for the tolerance.
   */
  public Mapping getMappingForTolerance() throws RemoteException, ApiException;

  /**
   * Change {@link Mapping} that is used for the tolerance.
   * 
   * @param mappingForTolerance
   *          the new attribute value
   */
  public void setMappingForTolerance(Mapping mappingForTolerance)
      throws RemoteException, ApiException;

  /**
   * @return {@link String} that represents the reference-data-time-shift.
   */
  public String getReferenceDataTimeShift() throws RemoteException, ApiException;

  /**
   * Change {@link String} that represents the reference-data-time-shift.
   * 
   * @param referenceDataTimeShift
   *          the new attribute value
   */
  public void setReferenceDataTimeShift(String referenceDataTimeShift)
      throws RemoteException, ApiException;

  /**
   * @return {@link String} that represents the comparison-trigger-expression.
   */
  public String getComparisonTriggerExpression() throws RemoteException, ApiException;

  /**
   * Change the {@link String} that represents the comparison-trigger-expression.
   * 
   * @param sampleFilterExpression
   *          the new attribute value
   */
  public void setComparisonTriggerExpression(String sampleFilterExpression)
      throws RemoteException, ApiException;

  /**
   * Set the reference directory of the parent {@link ExecutionConfiguration} as reference. A call
   * to {@link #getReference()} will return the {@link ExecutionConfiguration} in this case.
   * 
   * @throws ApiException
   *           If the configuration is not part of the model anymore.
   */
  void setReferenceToReferenceDirectory() throws RemoteException, ApiException;

  /**
   * Set a reference file as reference.
   * 
   * @param path
   *          The path to the reference file.
   */
  void setReferenceToFile(String path) throws RemoteException, ApiException;

  /**
   * Set an other execution configuration item as reference.
   * 
   * @param item
   *          the execution configuration item.
   * @throws ApiException
   *           If <code>item</code> is from another model or is the same as the execution
   *           configuration item this back to back settings belong to
   *           ({@link #getExecConfigItem()}).
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
   * @return The back to back reference of this setting.
   */
  public Object getReference() throws RemoteException, ApiException;

  /**
   * Changes if auto-update is enabled for {@link Type type}.
   * 
   * @param type
   * @param on
   */

  public void setAutoUpdate(VariableType type, boolean on) throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if auto-update is enabled for {@link Type type}.
   */
  public boolean isAutoUpdate(VariableType type) throws RemoteException, ApiException;

  /**
   * @return <code>true</code> if differences in a sample are ignored. Default value is
   *         <code>true</code>.
   */
  public boolean isIgnoreUndefinedPhases() throws RemoteException, ApiException;

  /**
   * Changes if differences in a sample are ignored. Default value is <code>true</code>.
   * 
   * @param ignoreUndefinedPhases
   *          the new attribute value
   */
  public void setIgnoreUndefinedPhases(boolean ignoreUndefinedPhases)
      throws RemoteException, ApiException;

  /**
   * @return {@link ExecutionConfigurationItem} related to this {@link Back2BackSettings}.
   */
  public ExecutionConfigurationItem getExecConfigItem() throws RemoteException, ApiException;

}
