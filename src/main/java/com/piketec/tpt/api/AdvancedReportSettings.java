/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2020 PikeTec GmbH
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

public interface AdvancedReportSettings extends IdentifiableRemote {

  /**
   * Different settings whether assessment variables are shown in the report or not.
   */
  enum ShowAssessmentVariables {

    /**
     * No assessment variable is shown in the report.
     */
    NEVER,
    /**
     * Assessment variables are only shown in the report if they have a result (passed, failed,
     * execution_error).
     */
    PASSED_OR_FAILED_OR_EXECERROR_ONLY,
    /**
     * Assessment variables are always shown in the report, even if they are inconclusive.
     */
    ALWAYS;

    /**
     * @deprecated this attribute exists for backwards compatibility reasons only. It has the same
     *             semantics as {@link #PASSED_OR_FAILED_OR_EXECERROR_ONLY}. Please use
     *             {@link #PASSED_OR_FAILED_OR_EXECERROR_ONLY} instead.
     */
    @Deprecated
    public static final ShowAssessmentVariables PASSED_OR_FAILED_ONLY =
        PASSED_OR_FAILED_OR_EXECERROR_ONLY;

  }

  public static final ShowAssessmentVariables SHOW_ASSESSMENT_VARIABLES_DEFAULT =
      ShowAssessmentVariables.PASSED_OR_FAILED_OR_EXECERROR_ONLY;

  /**
   * Adds an additional attribute for the requirements result tables.
   * 
   * @param attr
   *          the name of requirements attribute (column) to add to the report
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void addAdditionalRequirementAttr(String attr) throws RemoteException, ApiException;

  /**
   * Gets all additional attributes for the requirements result tables.
   * 
   * @return get attribute additionalRequirementAttrs
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public RemoteList<String> getAdditionalRequirementAttrs() throws RemoteException, ApiException;

  /**
   * Adds an attribute of the testcase to the testcase summary table.
   * 
   * @param attr
   *          the name of the test case attribute to add to the report
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void addTestcasetAttr(String attr) throws RemoteException, ApiException;

  /**
   * Gets all attributes that are shown for each testcase in the testcase summary table.
   * 
   * @return get testcase attributes
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public RemoteList<String> getTestcaseAttrs() throws RemoteException, ApiException;

  /**
   * Get attribute assessmentVariableFilter. In case of a huge amount of assessment variables it
   * might be useful to limit the maximum number of assessment variables shown in the report. This
   * can be done by specifying a regular expression pattern in
   * {@link #setAssessmentVariableFilter(String)}. Only if the pattern matches an assessment
   * variable name, the variable is shown in the report. For easier distinction use
   * {@link #getShowAssessmentVariables()}
   * 
   * @return get attribute assessmentVariableFilter, which is a regular expression pattern
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public String getAssessmentVariableFilter() throws RemoteException, ApiException;

  /**
   * Get attribute OverviewImagePath. This image will be shown at the beginning of the report.
   * 
   * @return get attribute OverviewImagePath
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public String getOverviewImagePath() throws RemoteException, ApiException;

  /**
   * Get attribute OverviewText. This text is shown at the beginning of the report.
   * 
   * @return get attribute OverviewText
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public String getOverviewText() throws RemoteException, ApiException;

  /**
   * Get target path for the compressed report file.
   * 
   * @return get attribute compressionPath
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public String getCompressionPath() throws RemoteException, ApiException;

  /**
   * Get attribute showAssessmentVariables. An assessment variable is a variable used for evaluating
   * tests. It contains data structures that relate the values of a variable to time intervals. Use
   * this variable to specify whether and how to display assessment variables in the report.
   * 
   * @return get attribute {@link ShowAssessmentVariables}:
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public com.piketec.tpt.api.AdvancedReportSettings.ShowAssessmentVariables getShowAssessmentVariables()
      throws RemoteException, ApiException;

  /**
   * Get attribute buildOverviewAutomatically. Set to true to finalize the test run and generate the
   * report overview automatically when the last test has been executed.
   * 
   * @return get attribute buildOverviewAutomatically
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isOverviewBuildAutomatically() throws RemoteException, ApiException;

  /**
   * Check only {@link Requirement Requirements} linked to the executed test case or variant: The
   * overall result of a requirement is derived from all partial results accumulated during the test
   * execution. Optionally you can restrict them to consider only results achieved while running
   * linked test cases or variants by setting this boolean to <code>true</code>.
   * 
   * @return get attribute considerOnlyLinkedRequirements
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isOnlyLinkedRequirements() throws RemoteException, ApiException;

  /**
   * Determines if a requirements overview section is generated.
   * 
   * @return get attribute createRequirementsOverview
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isCreateRequirementsOverviewSection() throws RemoteException, ApiException;

  /**
   * Determines if the report directory is deleted after writing the compressed report file. If and
   * only if the compressed report file isn't somewhere within the report directory.
   * 
   * @return get attribute deleteReportDirAfterCompression
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isDeleteReportDirAfterCompression() throws RemoteException, ApiException;

  /**
   * If this flag is set and a requirement was not checked in any assesslet
   * (REQUIREMENTS.checked()), its result is set to the test case result.
   * 
   * @return get attribute deriveRequirementResultsFromTCIfNotChecked
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isDeriveRequirementResultsFromTCIfNotCheckedInAnyAssesslets()
      throws RemoteException, ApiException;

  /**
   * Determines if a requirements assesslet results table section is written in the requirements
   * overview section, which shows which {@link Requirement} has been checked by what
   * {@link Assessment Assesslets} and with what result.
   * 
   * @return get attribute generateRequirementsAssessletResultsTable
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isGenerateRequirementsAssessletResultsTable() throws RemoteException, ApiException;

  /**
   * Determines if a variable summary section is generated.
   * 
   * @return get attribute generateVariableSummary
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isGenerateVariableSummary() throws RemoteException, ApiException;

  /**
   * Determines if Signal-Viewer-Links are included.
   * 
   * @return get attribute includeSignalViewerApplet
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isIncludeSignalViewerApplet() throws RemoteException, ApiException;

  /**
   * Determines if a compressed file is generated. If set to true but no file is set, an
   * {@link Exception} is thrown. The path can be set with {@link #setCompressionPath(String)}.
   * 
   * @return get attribute packReport
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isCompressReport() throws RemoteException, ApiException;

  /**
   * @return Determines if custom filter settings ({@link #getCustomPackExcludeFilter()} and
   *         {@link #getCustomPackIncludeFilter()}) will be used to determine the content of the
   *         report archive.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           isCompressReport()==false
   */
  public boolean isPackCustomFilter() throws RemoteException, ApiException;

  /**
   * @return Determines if test report files will be archived in the report archive.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           isCompressReport()==false || isPackCustomFilter==true
   */
  public boolean isPackTestReportData() throws RemoteException, ApiException;

  /**
   * @return Determines if test summary files will be archived in the report archive.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           isCompressReport()==false || isPackCustomFilter==true
   */
  public boolean isPackTestSummaryData() throws RemoteException, ApiException;

  /**
   * @return Determines if test record files will be archived in the report archive.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           isCompressReport()==false || isPackCustomFilter==true
   */
  public boolean isPackTestRecordData() throws RemoteException, ApiException;

  /**
   * @return Returns the custom include filter string. Please see UI documentation for detailed
   *         information.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           isCompressReport()==false || isPackCustomFilter==false
   */
  public String getCustomPackIncludeFilter() throws RemoteException, ApiException;

  /**
   * @return Returns the custom exclude filter string. Please see UI documentation for detailed
   *         information.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           isCompressReport()==false || isPackCustomFilter==false
   */
  public String getCustomPackExcludeFilter() throws RemoteException, ApiException;

  /**
   * Determines if a comment column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @return get attribute platformOverviewComment
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isShowPlatformOverviewComment() throws RemoteException, ApiException;

  /**
   * Determines if a directory column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @return get attribute platformOverviewDirectory
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isShowPlatformOverviewDirectory() throws RemoteException, ApiException;

  /**
   * When importing requirements, there might be requirements tagged as heading (for more detail see
   * requirements import configure import types). They can not be linked to any test cases and are
   * there for structural reasons. If set to <code>false</code>, they are not shown in the report
   * (which might make it a bit smaller).
   * 
   * @return get attribute showRequirementHeadings
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isShowRequirementHeadings() throws RemoteException, ApiException;

  /**
   * When importing requirements, there might be requirements tagged as information (for more detail
   * see requirements Import Configure import types). They can not be linked to any test cases and
   * are there for structural reasons. If set to false, they are not shown in the report (which
   * might make it a bit smaller).
   * 
   * @return get attribute showInformation
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isShowRequirementInformation() throws RemoteException, ApiException;

  /**
   * Determines if comments in the requirements results table are displayed.
   * 
   * @return get attribute showRequirementComments
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isShowRequirementComments() throws RemoteException, ApiException;

  /**
   * Get attribute checkAndShowTestCaseStatusInformation. If this is set to true, an overview table
   * is added to the report, showing all test cases with their status (e.g. stable, in progress,
   * new, ...)
   * 
   * @return get attribute checkAndShowTestCaseStatusInformation
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public boolean isCheckAndShowTestCaseStatusInformation() throws RemoteException, ApiException;

  /**
   * Change attribute assessmentVariableFilter. In case of a huge amount of assessment variables it
   * might be useful to limit the maximum number of assessment variables shown in the report. This
   * can be done by specifying a regular expression pattern in the Pattern TextField. Only if the
   * pattern matches an assessment variable name, the variable is shown in the report. For easier
   * distinction use <code>setShowAssessmentVariables()</code>
   * 
   * @param assessmentVariableFilter
   *          the new attribute value: a regular expression pattern
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setAssessmentVariableFilter(String assessmentVariableFilter)
      throws RemoteException, ApiException;

  /**
   * Change attribute buildOverviewAutomatically. Set to <code>true</code> to finalize the test run
   * and generate the report overview automatically when the last test has been executed.
   * 
   * @param buildReportAutomatically
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setOverviewBuildAutomatically(boolean buildReportAutomatically)
      throws RemoteException, ApiException;

  /**
   * Set if only linked {@link Requirement Requirements} are printed into the report.
   * 
   * @param considerOnlyLinkedRequirements
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOnlyLinkedRequirements(boolean considerOnlyLinkedRequirements)
      throws RemoteException;

  /**
   * Set if an requirement overview section is created.
   * 
   * @param createRequirementsOverview
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCreateRequirementsOverviewSection(boolean createRequirementsOverview)
      throws RemoteException;

  /**
   * Set if the report-directory is deleted after generating the compressed report file. If and only
   * if the compressed report file isn't somewhere within the report directory.
   * 
   * @param deleteReportDirAfterCompression
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDeleteReportDirAfterCompression(boolean deleteReportDirAfterCompression)
      throws RemoteException;

  /**
   * If this flag is set and a requirement was not checked in any assesslet
   * (REQUIREMENTS.checked()), its result is set to the test case result.
   * 
   * @param deriveRequirementResultsFromTCIfNotChecked
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDeriveRequirementResultsFromTCIfNotCheckedInAnyAssesslets(boolean deriveRequirementResultsFromTCIfNotChecked)
      throws RemoteException;

  /**
   * Change attribute OverviewImagePath. This image will be shown at the beginning of the report.
   * 
   * @param overviewImagePath
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setOverviewImagePath(String overviewImagePath) throws RemoteException, ApiException;

  /**
   * Change attribute overviewText. This text is shown at the beginning of the report.
   * 
   * @param overviewText
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setOverviewText(String overviewText) throws RemoteException, ApiException;

  /**
   * Changes if a requirements assesslet results table section is generated, which shows which
   * {@link Requirement Requirements} have been checked by what {@link Assessment Assesslets} and
   * with what result.
   * 
   * @param generateRequirementsAssessletResultsTable
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setGenerateRequirementsAssessletResultsTable(boolean generateRequirementsAssessletResultsTable)
      throws RemoteException;

  /**
   * Changes if a Variables Summary section is generated.
   * 
   * @param generateVariableSummary
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setGenerateVariableSummary(boolean generateVariableSummary)
      throws RemoteException, ApiException;

  /**
   * Changes if Signal-Viewer-Links are included.
   * 
   * @param includeSignalViewerApplet
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setIncludeSignalViewerApplet(boolean includeSignalViewerApplet)
      throws RemoteException;

  /**
   * Changes if a compressed report file is generated.
   * 
   * @param packReport
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setCompressReport(boolean packReport) throws RemoteException, ApiException;

  /**
   * Changes if custom include and exclude filters are applied for compressed report.
   * 
   * @param customFilter
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error (e.g. {@link #isCompressReport()} ==false)
   */
  public void setPackCustomFilter(boolean customFilter) throws RemoteException, ApiException;

  /**
   * Changes if the compressed report includes test report files.
   * 
   * @param packReportData
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error (e.g. {@link #isCompressReport()} &amp;&amp;
   *           !{@link #isPackCustomFilter()} ==false)
   */
  public void setPackTestReportData(boolean packReportData) throws RemoteException, ApiException;

  /**
   * Changes if the compressed report includes summary (e.g. xml) files.
   * 
   * @param packSummaryData
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error (e.g. {@link #isCompressReport()} &amp;&amp;
   *           !{@link #isPackCustomFilter()} ==false)
   */
  public void setPackTestSummaryData(boolean packSummaryData) throws RemoteException, ApiException;

  /**
   * Changes if the compressed report includes test record (e.g. tptbin) files.
   * 
   * @param packRecordData
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error (e.g. {@link #isCompressReport()} &amp;&amp;
   *           !{@link #isPackCustomFilter()} ==false)
   */
  public void setPackTestRecordData(boolean packRecordData) throws RemoteException, ApiException;

  /**
   * Sets a custom include filter string. Please see UI documentation for detailed information.
   * 
   * @param includeFilter
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error (e.g. {@link #isCompressReport()} &amp;&amp;
   *           {@link #isPackCustomFilter()} ==false)
   */
  public void setCustomPackIncludeFilter(String includeFilter) throws RemoteException, ApiException;

  /**
   * Sets a custom exclude filter string. Please see UI documentation for detailed information.
   * 
   * @param excludeFilter
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error (e.g. {@link #isCompressReport()} &amp;&amp;
   *           {@link #isPackCustomFilter()} ==false)
   */
  public void setCustomPackExcludeFilter(String excludeFilter) throws RemoteException, ApiException;

  /**
   * Changes if a comment column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @param platformOverviewComment
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setShowPlatformOverviewComment(boolean platformOverviewComment)
      throws RemoteException, ApiException;

  /**
   * Changes if a directory column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @param platformOverviewDirectory
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setShowPlatformOverviewDirectory(boolean platformOverviewDirectory)
      throws RemoteException;

  /**
   * Changes the target path of the compressed report file. If the given path has no file extension,
   * a <code>.zip</code> is added to the path.
   * 
   * @param compressionPath
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setCompressionPath(String compressionPath) throws RemoteException, ApiException;

  /**
   * Change attribute showAssessmentVariables. An assessment variable is a variable used for
   * evaluating tests. It contains data structures that relate the values of a variable to time
   * intervals. Use this variable to specify whether and how to display assessment variables in the
   * report.
   * 
   * @param showAssessmentVariables
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setShowAssessmentVariables(ShowAssessmentVariables showAssessmentVariables)
      throws RemoteException, ApiException;

  /**
   * When importing requirements, there might be requirements tagged as heading (for more detail see
   * requirements import configure import types). They can not be linked to any test cases and are
   * there for structural reasons. If set to false, they are not shown in the report (which might
   * make it a bit smaller).
   * 
   * @param showHeadings
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setShowRequirementHeadings(boolean showHeadings) throws RemoteException, ApiException;

  /**
   * When importing requirements, there might be requirements tagged as information (for more detail
   * see requirements import configure import types). They can not be linked to any test cases and
   * are there for structural reasons. If set to false, they are not shown in the report (which
   * might make it a bit smaller).
   * 
   * @param showInformation
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setShowRequirementInformation(boolean showInformation)
      throws RemoteException, ApiException;

  /**
   * Changes if comments are shown in the requirements results table.
   * 
   * @param showRequirementComments
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setShowRequirementComments(boolean showRequirementComments)
      throws RemoteException, ApiException;

  /**
   * If this is set to true, an overview table is added to the report, showing all test cases with
   * their status (e.g. stable, in progress, new, ...)
   * 
   * @param checkAndShowTestCaseStatusInformation
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCheckAndShowTestCaseStatusInformation(boolean checkAndShowTestCaseStatusInformation)
      throws RemoteException;

}
