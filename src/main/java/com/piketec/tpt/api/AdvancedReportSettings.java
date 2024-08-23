/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2024 Synopsys Inc.
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

import com.piketec.tpt.api.constants.assessments.RequirementsCoverage;
import com.piketec.tpt.api.util.DeprecatedAndRemovedException;

/**
 * General configuration of the report created after a test run.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
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

  }

  /**
   * Adds an attribute of the testcase to the testcase summary table.
   * 
   * @param attr
   *          the name of the test case attribute to add to the report
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void addTestcasetAttr(String attr) throws RemoteException;

  /**
   * Gets all attributes that are shown for each testcase in the testcase summary table.
   * 
   * @return get testcase attributes
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<String> getTestcaseAttrs() throws RemoteException;

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
   */
  public String getAssessmentVariableFilter() throws RemoteException;

  /**
   * Get attribute OverviewImagePath. This image will be shown at the beginning of the report.
   * 
   * @return get attribute OverviewImagePath
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getOverviewImagePath() throws RemoteException;

  /**
   * Get attribute OverviewText. This text is shown at the beginning of the report.
   * 
   * @return get attribute OverviewText
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getOverviewText() throws RemoteException;

  /**
   * Get target path for the compressed report file.
   * 
   * @return get attribute compressionPath
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCompressionPath() throws RemoteException;

  /**
   * Get attribute showAssessmentVariables. An assessment variable is a variable used for evaluating
   * tests. It contains data structures that relate the values of a variable to time intervals. Use
   * this variable to specify whether and how to display assessment variables in the report.
   * 
   * @return get attribute {@link ShowAssessmentVariables}:
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public com.piketec.tpt.api.AdvancedReportSettings.ShowAssessmentVariables getShowAssessmentVariables()
      throws RemoteException;

  /**
   * Get attribute buildOverviewAutomatically. Set to true to finalize the test run and generate the
   * report overview automatically when the last test has been executed.
   * 
   * @return get attribute buildOverviewAutomatically
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isOverviewBuildAutomatically() throws RemoteException;

  /**
   * Determines if the report directory is deleted after writing the compressed report file. If and
   * only if the compressed report file isn't somewhere within the report directory.
   * 
   * @return get attribute deleteReportDirAfterCompression
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isDeleteReportDirAfterCompression() throws RemoteException;

  /**
   * Determines if a variable summary section is generated.
   * 
   * @return get attribute generateVariableSummary
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isGenerateVariableSummary() throws RemoteException;

  /**
   * Determines if "Status Summary" report page is generated.
   * 
   * @return get attribute generateStatusSummary
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isGenerateStatusSummary() throws RemoteException;

  /**
   * Determines if Signal-Viewer-Links are included.
   * 
   * @return get attribute includeSignalViewerApplet
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isIncludeSignalViewerApplet() throws RemoteException;

  /**
   * Determines if a compressed file is generated. If set to true but no file is set, an
   * {@link Exception} is thrown. The path can be set with {@link #setCompressionPath(String)}.
   * 
   * @return get attribute packReport
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isCompressReport() throws RemoteException;

  /**
   * @return Determines if custom filter settings ({@link #getCustomPackExcludeFilter()} and
   *         {@link #getCustomPackIncludeFilter()}) will be used to determine the content of the
   *         report archive.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isPackCustomFilter() throws RemoteException;

  /**
   * @return Determines if test report files will be archived in the report archive.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isPackTestReportData() throws RemoteException;

  /**
   * @return Determines if test summary files will be archived in the report archive.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isPackTestSummaryData() throws RemoteException;

  /**
   * @return Determines if test record files will be archived in the report archive.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isPackTestRecordData() throws RemoteException;

  /**
   * @return Returns the custom include filter string. Please see UI documentation for detailed
   *         information.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCustomPackIncludeFilter() throws RemoteException;

  /**
   * @return Returns the custom exclude filter string. Please see UI documentation for detailed
   *         information.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCustomPackExcludeFilter() throws RemoteException;

  /**
   * Determines if a comment column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @return get attribute platformOverviewComment
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Renamed, use {@link #isShowTestCommentColumn()}. Will be removed in TPT-22.
   */
  @Deprecated
  public boolean isShowPlatformOverviewComment() throws RemoteException;

  /**
   * Determines if a comment column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @return get attribute platformOverviewComment
   * 
   * @throws RemoteException
   *           remote communication problem
   **/
  public boolean isShowTestCommentColumn() throws RemoteException;

  /**
   * Determines if a directory column is generated for the Test Case Summary table in the Platform
   * Overview section. The directory column will be generated only if PDF Report is selected.
   * 
   * @return get attribute platformOverviewDirectory
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Option is removed
   *             without replacement.
   */
  @Deprecated
  public boolean isShowPlatformOverviewDirectory() throws RemoteException;

  /**
   * Determines if a column with IDs of the linked requirements is generated for the Test Case
   * Summary table in the Platform Overview section.
   * 
   * @return get attribute platformOverviewLinkedRequirements
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Renamed, use {@link #isShowLinkedRequirementsColumn()}. Will be removed in TPT-22.
   */
  @Deprecated
  public boolean isShowPlatformOverviewLinkedRequirements() throws RemoteException;

  /**
   * Determines if a column with IDs of the linked requirements is generated for the Test Case
   * Summary table in the Platform Overview section.
   * 
   * @return get attribute platformOverviewLinkedRequirements
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isShowLinkedRequirementsColumn() throws RemoteException;

  /**
   * Get attribute checkAndShowTestCaseStatusInformation. If this is set to true, an overview table
   * is added to the report, showing all test cases with their status (e.g. stable, in progress,
   * new, ...)
   * 
   * @return get attribute checkAndShowTestCaseStatusInformation
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isCheckAndShowTestCaseStatusInformation() throws RemoteException;

  /**
   * Get attribute checkAndShowAssessmentStatusInformation. If this is set to true, an overview
   * table is added to the report, showing all assessments with their status (e.g. stable, in
   * progress, new, ...)
   * 
   * @return get attribute checkAndShowAssessmentStatusInformation
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isCheckAndShowAssessmentStatusInformation() throws RemoteException;

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
   */
  public void setAssessmentVariableFilter(String assessmentVariableFilter) throws RemoteException;

  /**
   * Change attribute buildOverviewAutomatically. Set to <code>true</code> to finalize the test run
   * and generate the report overview automatically when the last test has been executed.
   * 
   * @param buildReportAutomatically
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOverviewBuildAutomatically(boolean buildReportAutomatically)
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
   * Change attribute OverviewImagePath. This image will be shown at the beginning of the report.
   * 
   * @param overviewImagePath
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOverviewImagePath(String overviewImagePath) throws RemoteException;

  /**
   * Change attribute overviewText. This text is shown at the beginning of the report.
   * 
   * @param overviewText
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOverviewText(String overviewText) throws RemoteException;

  /**
   * Changes if a Variables Summary section is generated.
   * 
   * @param generateVariableSummary
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setGenerateVariableSummary(boolean generateVariableSummary) throws RemoteException;

  /**
   * Changes if "Status Summary" report page is generated.
   * 
   * @param generateStatusSummary
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setGenerateStatusSummary(boolean generateStatusSummary) throws RemoteException;

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
   */
  public void setCompressReport(boolean packReport) throws RemoteException;

  /**
   * Changes if custom include and exclude filters are applied for compressed report.
   * 
   * @param customFilter
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setPackCustomFilter(boolean customFilter) throws RemoteException;

  /**
   * Changes if the compressed report includes test report files.
   * 
   * @param packReportData
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setPackTestReportData(boolean packReportData) throws RemoteException;

  /**
   * Changes if the compressed report includes summary (e.g. xml) files.
   * 
   * @param packSummaryData
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setPackTestSummaryData(boolean packSummaryData) throws RemoteException;

  /**
   * Changes if the compressed report includes test record (e.g. tptbin) files.
   * 
   * @param packRecordData
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setPackTestRecordData(boolean packRecordData) throws RemoteException;

  /**
   * Sets a custom include filter string. Please see UI documentation for detailed information.
   * 
   * @param includeFilter
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCustomPackIncludeFilter(String includeFilter) throws RemoteException;

  /**
   * Sets a custom exclude filter string. Please see UI documentation for detailed information.
   * 
   * @param excludeFilter
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCustomPackExcludeFilter(String excludeFilter) throws RemoteException;

  /**
   * Changes if a comment column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @param platformOverviewComment
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Renamed, use {@link #setShowTestCommentColumn(boolean)}. Will be removed in TPT-22.
   */
  @Deprecated
  public void setShowPlatformOverviewComment(boolean platformOverviewComment)
      throws RemoteException;

  /**
   * Changes if a comment column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @param showTestCommentColumn
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setShowTestCommentColumn(boolean showTestCommentColumn) throws RemoteException;

  /**
   * Changes if a directory column is generated for the Test Case Summary table in the Platform
   * Overview section. The directory column will be generated only if PDF Report is selected.
   * 
   * @param platformOverviewDirectory
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Option is removed
   *             without replacement.
   */
  @Deprecated
  public void setShowPlatformOverviewDirectory(boolean platformOverviewDirectory)
      throws RemoteException;

  /**
   * Changes if a column with IDs of the linked requirements is generated for the Test Case Summary
   * table in the Platform Overview section.
   * 
   * @param platformOverviewLinkedRequirements
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   *
   * @deprecated Renamed, use {@link #setShowLinkedRequirementsColumn(boolean)}. Will be removed in
   *             TPT-22.
   */
  @Deprecated
  public void setShowPlatformOverviewLinkedRequirements(boolean platformOverviewLinkedRequirements)
      throws RemoteException;

  /**
   * Changes if a column with IDs of the linked requirements is generated for the Test Case Summary
   * table in the Platform Overview section.
   * 
   * @param showLinkedRequirementsColumn
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setShowLinkedRequirementsColumn(boolean showLinkedRequirementsColumn)
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
   */
  public void setCompressionPath(String compressionPath) throws RemoteException;

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
   */
  public void setShowAssessmentVariables(ShowAssessmentVariables showAssessmentVariables)
      throws RemoteException;

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

  /**
   * If this is set to true, an overview table is added to the report, showing all assessments with
   * their status (e.g. stable, in progress, new, ...)
   * 
   * @param checkAndShowAssessmentStatusInformation
   *          the new attribute value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCheckAndShowAssessmentStatusInformation(boolean checkAndShowAssessmentStatusInformation)
      throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#ADDITIONAL_ATTRIBUTES_IN_REQUIREMENTS_REPORT_TABLE}.
   * 
   * @param attr
   *          without meaning
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public void addAdditionalRequirementAttr(String attr) throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @return throws a RuntimeException
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#ADDITIONAL_ATTRIBUTES_IN_REQUIREMENTS_REPORT_TABLE}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public RemoteList<String> getAdditionalRequirementAttrs() throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @return throws a RuntimeException
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#CHECK_ONLY_LINKED}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public boolean isOnlyLinkedRequirements() throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @return throws a RuntimeException
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#GENERATE_REQUIREMENTS_REPORT}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public boolean isCreateRequirementsOverviewSection() throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @return throws a RuntimeException
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#DERIVE_RESULT_FROM_TEST_CASE_IF_NOT_CHECKED}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public boolean isDeriveRequirementResultsFromTCIfNotCheckedInAnyAssesslets()
      throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @return throws a RuntimeException
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#GENERATE_ASSESSLET_RESULTS_TABLE}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public boolean isGenerateRequirementsAssessletResultsTable() throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @return throws a RuntimeException
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#SHOW_HEADING_ROWS}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public boolean isShowRequirementHeadings() throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @return throws a RuntimeException
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#SHOW_INFORMATION_ROWS}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public boolean isShowRequirementInformation() throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @return throws a RuntimeException
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#SHOW_COMMENTS}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public boolean isShowRequirementComments() throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @return throws a RuntimeException
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#SHOW_DOCUMENT_VERSIONS}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public boolean isShowDocumentVersions() throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @param considerOnlyLinkedRequirements
   *          without meaning
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#CHECK_ONLY_LINKED}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public void setOnlyLinkedRequirements(boolean considerOnlyLinkedRequirements)
      throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @param createRequirementsOverview
   *          without meaning
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#GENERATE_REQUIREMENTS_REPORT}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public void setCreateRequirementsOverviewSection(boolean createRequirementsOverview)
      throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @param deriveRequirementResultsFromTCIfNotChecked
   *          without meaning
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#DERIVE_RESULT_FROM_TEST_CASE_IF_NOT_CHECKED}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public void setDeriveRequirementResultsFromTCIfNotCheckedInAnyAssesslets(boolean deriveRequirementResultsFromTCIfNotChecked)
      throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @param generateRequirementsAssessletResultsTable
   *          without meaning
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#GENERATE_ASSESSLET_RESULTS_TABLE}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public void setGenerateRequirementsAssessletResultsTable(boolean generateRequirementsAssessletResultsTable)
      throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @param showHeadings
   *          without meaning
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#SHOW_HEADING_ROWS}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public void setShowRequirementHeadings(boolean showHeadings) throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @param showInformation
   *          without meaning
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#SHOW_INFORMATION_ROWS}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public void setShowRequirementInformation(boolean showInformation) throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @param showRequirementComments
   *          without meaning
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#SHOW_COMMENTS}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public void setShowRequirementComments(boolean showRequirementComments) throws RemoteException;

  /**
   * Does nothing except throw a RuntimeException.
   * 
   * @param showDocumentVersions
   *          without meaning
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Replaced by
   *             {@link Assessment#REQUIREMENTS_COVERAGE_TYPE} with property
   *             {@link RequirementsCoverage#SHOW_DOCUMENT_VERSIONS}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  @Deprecated
  public void setShowDocumentVersions(boolean showDocumentVersions) throws RemoteException;

}
