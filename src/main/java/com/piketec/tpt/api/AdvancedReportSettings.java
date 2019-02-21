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
     * Assessment variables are only shown in the report if they have a result (passed, failed).
     */
    PASSED_OR_FAILED_ONLY,
    /**
     * Assessment variables are always shown in the report, even if they are inconclusive.
     */
    ALWAYS
  }

  public static final ShowAssessmentVariables SHOW_ASSESSMENT_VARIABLES_DEFAULT =
      ShowAssessmentVariables.PASSED_OR_FAILED_ONLY;

  /**
   * Adds an additional attribute for the requirements result tables.
   * 
   * @param attr
   */
  public void addAdditionalRequirementAttr(String attr) throws RemoteException, ApiException;

  /**
   * Gets all additional attributes for the requirements result tables.
   * 
   * @return get attribute additionalRequirementAttrs
   */
  public RemoteList<String> getAdditionalRequirementAttrs() throws ApiException, RemoteException;

  /**
   * Get attribute assessmentVariableFilter. In case of a huge amount of assessment variables it
   * might be useful to limit the maximum number of assessment variables shown in the report. This
   * can be done by specifying a regular expression pattern in
   * {@link #setAssessmentVariableFilter(String)}. Only if the pattern matches an assessment
   * variable name, the variable is shown in the report. For easier distinction use
   * {@link #getShowAssessmentVariables()}
   * 
   * @return get attribute assessmentVariableFilter, which is a regular expression pattern
   */
  public String getAssessmentVariableFilter() throws RemoteException, ApiException;

  /**
   * Get attribute OverviewImagePath. This image will be shown at the beginning of the report.
   * 
   * @return get attribute OverviewImagePath
   */
  public String getOverviewImagePath() throws RemoteException, ApiException;

  /**
   * Get attribute OverviewText. This text is shown at the beginning of the report.
   * 
   * @return get attribute OverviewText
   */
  public String getOverviewText() throws RemoteException, ApiException;

  /**
   * Get target path for the compressed report file.
   * 
   * @return get attribute compressionPath
   */
  public String getCompressionPath() throws RemoteException, ApiException;

  /**
   * Get attribute showAssessmentVariables. An assessment variable is a variable used for evaluating
   * tests. It contains data structures that relate the values of a variable to time intervals. Use
   * this variable to specify whether and how to display assessment variables in the report.
   * 
   * @return get attribute {@link ShowAssessmentVariables}:
   */
  public com.piketec.tpt.api.AdvancedReportSettings.ShowAssessmentVariables getShowAssessmentVariables()
      throws RemoteException, ApiException;

  /**
   * Get attribute buildOverviewAutomatically. Set to true to finalize the test run and generate the
   * report overview automatically when the last test has been executed.
   * 
   * @return get attribute buildOverviewAutomatically
   */
  public boolean isOverviewBuildAutomatically() throws RemoteException, ApiException;

  /**
   * Check only {@link Requirement Requirements} linked to the executed test case or variant: The
   * overall result of a requirement is derived from all partial results accumulated during the test
   * execution. Optionally you can restrict them to consider only results achieved while running
   * linked test cases or variants by setting this boolean to <code>true</code>.
   * 
   * @return get attribute considerOnlyLinkedRequirements
   */
  public boolean isOnlyLinkedRequirements() throws RemoteException, ApiException;

  /**
   * Determines if a requirements overview section is generated.
   * 
   * @return get attribute createRequirementsOverview
   */
  public boolean isCreateRequirementsOverviewSection() throws RemoteException, ApiException;

  /**
   * Determines if the report directory is deleted after writing the compressed report file. If and
   * only if the compressed report file isn't somewhere within the report directory.
   * 
   * @return get attribute deleteReportDirAfterCompression
   */
  public boolean isDeleteReportDirAfterCompression() throws RemoteException, ApiException;

  /**
   * If this flag is set and a requirement was not checked in any assesslet
   * (REQUIREMENTS.checked()), its result is set to the test case result.
   * 
   * @return get attribute deriveRequirementResultsFromTCIfNotChecked
   */
  public boolean isDeriveRequirementResultsFromTCIfNotCheckedInAnyAssesslets()
      throws RemoteException, ApiException;

  /**
   * Determines if a requirements assesslet results table section is written in the requirements
   * overview section, which shows which {@link Requirement} has been checked by what
   * {@link Assessment Assesslets} and with what result.
   * 
   * @return get attribute generateRequirementsAssessletResultsTable
   */
  public boolean isGenerateRequirementsAssessletResultsTable() throws RemoteException, ApiException;

  /**
   * Determines if a variable summary section is generated.
   * 
   * @return get attribute generateVariableSummary
   */
  public boolean isGenerateVariableSummary() throws RemoteException, ApiException;

  /**
   * Determines if Signal-Viewer-Links are included.
   * 
   * @return get attribute includeSignalViewerApplet
   */
  public boolean isIncludeSignalViewerApplet() throws RemoteException, ApiException;

  /**
   * Determines if a compressed file is generated. If set to true but no file is set, an
   * {@link Exception} is thrown. The path can be set with {@link #setCompressionPath(String)}.
   * 
   * @return get attribute packReport
   */
  public boolean isCompressReport() throws RemoteException, ApiException;

  /**
   * Determines if a comment column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @return get attribute platformOverviewComment
   */
  public boolean isShowPlatformOverviewComment() throws RemoteException, ApiException;

  /**
   * Determines if a directory column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @return get attribute platformOverviewDirectory
   */
  public boolean isShowPlatformOverviewDirectory() throws RemoteException, ApiException;

  /**
   * When importing requirements, there might be requirements tagged as heading (for more detail see
   * requirements import configure import types). They can not be linked to any test cases and are
   * there for structural reasons. If set to <code>false</code>, they are not shown in the report
   * (which might make it a bit smaller).
   * 
   * @return get attribute showRequirementHeadings
   */
  public boolean isShowRequirementHeadings() throws RemoteException, ApiException;

  /**
   * When importing requirements, there might be requirements tagged as information (for more detail
   * see requirements Import Configure import types). They can not be linked to any test cases and
   * are there for structural reasons. If set to false, they are not shown in the report (which
   * might make it a bit smaller).
   * 
   * @return get attribute showInformation
   */
  public boolean isShowRequirementInformation() throws RemoteException, ApiException;

  /**
   * Determines if comments in the requirements results table are displayed.
   * 
   * @return get attribute showRequirementComments
   */
  public boolean isShowRequirementComments() throws RemoteException, ApiException;

  /**
   * Get attribute checkAndShowTestCaseStatusInformation. If this is set to true, an overview table
   * is added to the report, showing all test cases with their status (e.g. stable, in progress,
   * new, ...)
   * 
   * @return get attribute checkAndShowTestCaseStatusInformation
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
   */
  public void setAssessmentVariableFilter(String assessmentVariableFilter)
      throws RemoteException, ApiException;

  /**
   * Change attribute buildOverviewAutomatically. Set to <code>true</code> to finalize the test run
   * and generate the report overview automatically when the last test has been executed.
   * 
   * @param buildReportAutomatically
   *          the new attribute value
   */
  public void setOverviewBuildAutomatically(boolean buildReportAutomatically)
      throws RemoteException, ApiException;

  /**
   * Set if only linked {@link Requirement Requirements} are printed into the report.
   * 
   * @param considerOnlyLinkedRequirements
   *          the new attribute value
   */
  public void setOnlyLinkedRequirements(boolean considerOnlyLinkedRequirements)
      throws RemoteException, ApiException;

  /**
   * Set if an requirement overview section is created.
   * 
   * @param createRequirementsOverview
   *          the new attribute value
   */
  public void setCreateRequirementsOverviewSection(boolean createRequirementsOverview)
      throws RemoteException, ApiException;

  /**
   * Set if the report-directory is deleted after generating the compressed report file. If and only
   * if the compressed report file isn't somewhere within the report directory.
   * 
   * @param deleteReportDirAfterCompression
   *          the new attribute value
   */
  public void setDeleteReportDirAfterCompression(boolean deleteReportDirAfterCompression)
      throws RemoteException, ApiException;

  /**
   * If this flag is set and a requirement was not checked in any assesslet
   * (REQUIREMENTS.checked()), its result is set to the test case result.
   * 
   * @param deriveRequirementResultsFromTCIfNotChecked
   *          the new attribute value
   */
  public void setDeriveRequirementResultsFromTCIfNotCheckedInAnyAssesslets(boolean deriveRequirementResultsFromTCIfNotChecked)
      throws RemoteException, ApiException;

  /**
   * Change attribute OverviewImagePath. This image will be shown at the beginning of the report.
   * 
   * @param OverviewImagePath
   *          the new attribute value
   */
  public void setOverviewImagePath(String OverviewImagePath) throws RemoteException, ApiException;

  /**
   * Change attribute overviewText. This text is shown at the beginning of the report.
   * 
   * @param OverviewText
   *          the new attribute value
   */
  public void setOverviewText(String OverviewText) throws RemoteException, ApiException;

  /**
   * Changes if a requirements assesslet results table section is generated, which shows which
   * {@link Requirement Requirements} have been checked by what {@link Assessment Assesslets} and
   * with what result.
   * 
   * @param generateRequirementsAssessletResultsTable
   *          the new attribute value
   */
  public void setGenerateRequirementsAssessletResultsTable(boolean generateRequirementsAssessletResultsTable)
      throws RemoteException, ApiException;

  /**
   * Changes if a Variables Summary section is generated.
   * 
   * @param generateVariableSummary
   *          the new attribute value
   */
  public void setGenerateVariableSummary(boolean generateVariableSummary)
      throws RemoteException, ApiException;

  /**
   * Changes if Signal-Viewer-Links are included.
   * 
   * @param includeSignalViewerApplet
   *          the new attribute value
   */
  public void setIncludeSignalViewerApplet(boolean includeSignalViewerApplet)
      throws RemoteException, ApiException;

  /**
   * Changes if a compressed report file is generated.
   * 
   * @param packReport
   *          the new attribute value
   */
  public void setCompressReport(boolean packReport) throws RemoteException, ApiException;

  /**
   * Changes if a comment column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @param platformOverviewComment
   *          the new attribute value
   */
  public void setShowPlatformOverviewComment(boolean platformOverviewComment)
      throws RemoteException, ApiException;

  /**
   * Changes if a directory column is generated for the Test Case Summary table in the Platform
   * Overview section.
   * 
   * @param platformOverviewDirectory
   *          the new attribute value
   */
  public void setShowPlatformOverviewDirectory(boolean platformOverviewDirectory)
      throws RemoteException, ApiException;

  /**
   * Changes the target path of the compressed report file. If the given path has no file extension,
   * a <code>.zip</code> is added to the path.
   * 
   * @param compressionPath
   *          the new attribute value
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
   */
  public void setShowRequirementInformation(boolean showInformation)
      throws RemoteException, ApiException;

  /**
   * Changes if comments are shown in the requirements results table.
   * 
   * @param showRequirementComments
   *          the new attribute value
   */
  public void setShowRequirementComments(boolean showRequirementComments)
      throws RemoteException, ApiException;

  /**
   * If this is set to true, an overview table is added to the report, showing all test cases with
   * their status (e.g. stable, in progress, new, ...)
   * 
   * @param checkAndShowTestCaseStatusInformation
   *          the new attribute value
   */
  public void setCheckAndShowTestCaseStatusInformation(boolean checkAndShowTestCaseStatusInformation)
      throws RemoteException, ApiException;
}
