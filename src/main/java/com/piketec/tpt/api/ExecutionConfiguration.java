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
package com.piketec.tpt.api;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * This object represents all settings for a particular execution configuration.
 * <p>
 * An <code>ExecutionConfiguration</code> can be only created via
 * {@link Project#createExecutionConfiguration(String)}. The configuration consists of a number of
 * attributes as well as a list of {@link ExecutionConfigurationItem}. The attributes represent the
 * controls from the execution configuration GUI.
 * </p>
 * For a detailed description of the attributes please refer to the User Guide.
 * 
 * 
 * @author Copyright (c) 2014 Piketec GmbH - All rights reserved.
 */
public interface ExecutionConfiguration
    extends IdentifiableRemote, NamedObject, RemoteList<ExecutionConfigurationItem> {

  /**
   * Enumeration representing the possible output formats of the report.
   * 
   * @author Copyright (c) 2014 Piketec GmbH - All rights reserved.
   */
  public enum ReportFormat {
    Html, Pdf, AllInOnePdf, MHtml
  }

  /**
   * Enumeration representing the different directory structure configurations.
   * 
   * @author Copyright (c) 2014 Piketec GmbH - All rights reserved.
   */
  public enum DataDirStructure {
    HIERARCHICAL_WITH_INDEX, HIERARCHICAL_WITH_ID, FLAT_WITH_INDEX, FLAT_WITH_ID, FLAT_ONLY_ID
  }

  /**
   * Creates a new {@link ExecutionConfigurationItem} and adds it to the tail of the
   * ExecutionConfiguration list. This list is represented by the table in the GUI.
   * 
   * 
   * @return A fresh ExecutionConfigurationItem
   * 
   */
  public ExecutionConfigurationItem createExecutionConfigurationItem()
      throws ApiException, RemoteException;

  /**
   * @return The data directory.
   * @deprecated No support for $-variables and relative paths - use {@link #getDataDirPath()}
   *             instead.
   */
  @Deprecated
  public File getDataDir() throws ApiException, RemoteException;

  /**
   * @return Returns the test data directory as {@link String}
   */
  public String getDataDirPath() throws ApiException, RemoteException;

  /**
   * @return Returns the advanced report settings.
   */
  public AdvancedReportSettings getAdvancedReportSettings() throws RemoteException, ApiException;

  /**
   * @return The report directory.
   * @deprecated No support for $-variables and relative paths - use {@link #getReportDirPath()}
   *             instead.
   */
  @Deprecated
  public File getReportDir() throws ApiException, RemoteException;

  /**
   * @return Returns the report directory.
   */
  public String getReportDirPath() throws ApiException, RemoteException;

  /**
   * Set the data directory.
   * 
   * @param f
   *          The Data directory as File.
   * @deprecated No support for $-variables and relative paths - use {@link #setDataDirPath(String)}
   *             instead.
   */
  @Deprecated
  public void setDataDir(File f) throws ApiException, RemoteException;

  /**
   * Set the data directory.
   * 
   * @param path
   *          A directory as String.
   */
  public void setDataDirPath(String path) throws ApiException, RemoteException;

  /**
   * Set the report directory.
   * <p>
   * Optionally, specify the report directory as {@link File}. Use <code>null</code> to set the data
   * directory.
   * </p>
   * 
   * @param f
   *          The report directory as <code>File</code> or <code>null</code>
   * @deprecated No support for $-variables and relative paths - use
   *             {@link #setReportDirPath(String)} instead.
   */
  @Deprecated
  public void setReportDir(File f) throws ApiException, RemoteException;

  /**
   * Set the report directory.
   * <p>
   * Optionally, specify the report directory as {@link String}. Use <code>null</code> to set the
   * data directory.
   * </p>
   * 
   * @param path
   *          The report directory as <code>String</code> or <code>null</code>
   * 
   */
  public void setReportDirPath(String path) throws ApiException, RemoteException;

  /**
   * @return Returns <code>true</code> if test should be executed. Represents the "Execute" check
   *         box.
   */
  public boolean isRunExec() throws ApiException, RemoteException;

  /**
   * @return Returns <code>true</code> if the assessments should be executed. Represents the
   *         "Assess" check box.
   */
  public boolean isRunAssess() throws ApiException, RemoteException;

  /**
   * 
   * @return Returns <code>true</code> if a report should be generated. Represents the "Report"
   *         check box.
   * 
   */
  public boolean isRunReport() throws ApiException, RemoteException;

  /**
   * @return Returns <code>true</code> if the execution should be done in debug mode. Represents the
   *         "Debug" check box.
   */
  public boolean isRunDebug() throws ApiException, RemoteException;

  /**
   * @return Returns <code>true</code> if the dashboard should be enabled during test execution.
   *         Represent the "Dashboard" check box.
   */
  public boolean isRunDashboard() throws ApiException, RemoteException;

  /**
   * @param enabled
   *          Set whether tests should be executed.
   */
  public void setRunExec(boolean enabled) throws ApiException, RemoteException;

  /**
   * @param enabled
   *          Set whether assessments should be executed.
   */
  public void setRunAssess(boolean enabled) throws ApiException, RemoteException;

  /**
   * 
   * @param enabled
   *          Set whether a report should be generated.
   *          <p>
   *          Note that a report can be only generated if assessments have been executed.
   *          </p>
   */
  public void setRunReport(boolean enabled) throws ApiException, RemoteException;

  /**
   * @param enabled
   *          Enable the debug mode for the execution.
   */
  public void setRunDebug(boolean enabled) throws ApiException, RemoteException;

  /**
   * @param enabled
   *          Enable the dashboard during execution.
   */
  public void setRunDashboard(boolean enabled) throws ApiException, RemoteException;

  /**
   * Returns the additional attributes for a Execution Configuration as specified by the user. This
   * map corresponds to the "Attributes" tab of the Execution Configuration GUI.
   * 
   * @return User attributes as map
   *
   */
  public Map<String, String> getAttributes() throws ApiException, RemoteException;

  /**
   * Set a user-defined attribute given by <code>key</code> to the value given by the
   * <code>value</code> parameter.
   * <p>
   * </p>
   * If <code>value==null</code>, the attribute <code>key</code> will be deleted.
   * 
   * @param key
   *          The name of the attribute to be set.
   * @param value
   *          The new value or <code>null</code> to delete the attribute.
   * 
   * @throws ApiException
   *           if <code>name==null</code>
   *
   */
  public void setAttributes(String key, String value) throws ApiException, RemoteException;

  /**
   * 
   * @return The currently selected report format
   * 
   * @throws ApiException
   *           If the currently selected report format is unknown.
   */
  public ReportFormat getReportFormat() throws ApiException, RemoteException;

  /**
   * Set the report format.
   * 
   * @param rf
   *          the new report format.
   * @throws ApiException
   *           If the given report format is not known to the API.
   */
  public void setReportFormat(ReportFormat rf) throws ApiException, RemoteException;

  /**
   * @return the currently selected directory structure for saving the test data.
   * @throws ApiException
   *           if the directory structure is unknown
   */
  public DataDirStructure getDataDirStructure() throws ApiException, RemoteException;

  /**
   * Set the directory structure for the data directory.
   * 
   * @param dds
   *          The new directory structure.
   * @throws ApiException
   *           if the given directory structure is unknown to the API.
   */
  public void setDataDirStructure(DataDirStructure dds) throws ApiException, RemoteException;

  /**
   * @return Returns <code>null</code> if "Pack report" is not enabled. Otherwise, the target file
   *         for the ZIP is returned.
   * @deprecated No support for $-variables and relative paths - use {@link #getReportPackPath()}
   *             instead.
   */

  @Deprecated
  public File getReportPackFile() throws ApiException, RemoteException;

  /**
   * Set the ZIP file where the packed report should be stored. Using <code>zipFile==null</code>
   * will disable the "Pack report" option.
   *
   * @param zipFile
   *          target file or <code>null</code>.
   * @deprecated No support for $-variables and relative paths - use
   *             {@link #setReportPackPath(String)} instead.
   */
  @Deprecated
  public void setReportPackFile(File zipFile) throws ApiException, RemoteException;

  /**
   * @return Returns <code>null</code> if "Pack report" is not enabled. Otherwise, the target file
   *         for the ZIP is returned.
   * @deprecated Use {@link AdvancedReportSettings#getCompressionPath()} instead.
   */
  @Deprecated
  public String getReportPackPath() throws ApiException, RemoteException;

  /**
   * Set the ZIP file where the packed report should be stored. Using <code>zipFile==null</code>
   * will disable the "Pack report" option.
   *
   * @param zipFile
   *          target file or <code>null</code>.
   * 
   * @deprecated Use {@link AdvancedReportSettings#setCompressionPath(String)} instead.
   */
  @Deprecated
  public void setReportPackPath(String zipFile) throws ApiException, RemoteException;

  /**
   * @return Returns <code>true</code> if the report directory should be deleted after it has been
   *         zipped by "Pack report".
   * @deprecated Use {@link AdvancedReportSettings#isDeleteReportDirAfterCompression()} instead.
   */
  @Deprecated
  public boolean isDeleteReportDirAfterPack() throws ApiException, RemoteException;

  /**
   * @param enable
   *          Enable or disable the automatic removal of the report directory if "Pack report" is
   *          enabled and after the report has been zipped.
   * @deprecated Use {@link AdvancedReportSettings#setDeleteReportDirAfterCompression(boolean)}
   *             instead.
   */
  @Deprecated
  public void setDeleteReportDirAfterPack(boolean enable) throws ApiException, RemoteException;

}
