package com.piketec.tpt.api.issues.jira;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * The settings for the creation of an issue in Jira
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class JiraIssuesCreateSettings implements Serializable {

  static final long serialVersionUID = 1L;

  /**
   * the choice of a report file to be attached when creating an issue
   */
  public static enum ReportFile {
    /**
     * No report file will be attached
     */
    NONE,
    /**
     * Report archive will be attached, if available
     */
    ARCHIVE_REPORT,
    /**
     * Overview PDF report will be attached, if available
     */
    PDF_REPORT,
    /**
     * Report archive and vverview PDF report will be attached, if available
     */
    ARCHIVE_AND_PDF

  }

  private ReportFile reportFile = ReportFile.NONE;

  private String projectKey = "";

  private String issueTypeId = "";

  private String description = "";

  private String summary = "";

  private List<File> additionalFiles = null;

  /**
   * The contructor for the create issue settings in Jira.
   */
  public JiraIssuesCreateSettings() {
  }

  /**
   * @return the choice of a report file (i.e., a {@link ReportFile#NONE}, a
   *         {@link ReportFile#ARCHIVE_REPORT}, a {@link ReportFile#PDF_REPORT}, a
   *         {@link ReportFile#ARCHIVE_AND_PDF}) to be attached when creating an issue
   */
  public ReportFile getReportFile() {
    return reportFile;
  }

  /**
   * @param reportFile
   *          the choice of a report file (i.e., a {@link ReportFile#NONE}, a
   *          {@link ReportFile#ARCHIVE_REPORT}, a {@link ReportFile#PDF_REPORT}, a
   *          {@link ReportFile#ARCHIVE_AND_PDF}) to be attached when creating an issue
   */
  public void setReportFile(ReportFile reportFile) {
    this.reportFile = reportFile;
  }

  /**
   * @return The key of the project
   */
  public String getProjectKey() {
    return projectKey;
  }

  /**
   * @param projectKey
   *          The key of the project
   */
  public void setProjectKey(String projectKey) {
    this.projectKey = projectKey;
  }

  /**
   * @return The ID of the issue type
   */
  public String getIssueTypeId() {
    return issueTypeId;
  }

  /**
   * @param issueTypeId
   *          The ID of the issue type
   */
  public void setIssueTypeId(String issueTypeId) {
    this.issueTypeId = issueTypeId;
  }

  /**
   * @return the list of additional files that will be attached when creating an issue
   */
  public List<File> getAdditionalFiles() {
    return additionalFiles;
  }

  /**
   * @param additionalFiles
   *          the list of additional files that will be attached when creating an issue
   */
  public void setAdditionalFiles(List<File> additionalFiles) {
    this.additionalFiles = additionalFiles;
  }

  /**
   * @return The description of the issue
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description
   *          The description of the issue
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return The summary of the issue
   */
  public String getSummary() {
    return summary;
  }

  /**
   * @param summary
   *          The summary of the issue
   */
  public void setSummary(String summary) {
    this.summary = summary;
  }

}
