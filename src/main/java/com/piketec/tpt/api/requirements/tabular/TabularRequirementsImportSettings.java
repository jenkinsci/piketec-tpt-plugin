package com.piketec.tpt.api.requirements.tabular;

import java.util.List;

import com.piketec.tpt.api.Pair;
import com.piketec.tpt.api.Requirement;
import com.piketec.tpt.api.requirements.RequirementsImportSettings;
import com.piketec.tpt.api.requirements.csv.CsvFileRequirementsImportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileRequirementsImportSettings;

/**
 * The common settings for the requirements import from a CSV or an Excel file.<br>
 * For the requirements import from a CSV file use {@link CsvFileRequirementsImportSettings}.<br>
 * For the requirements import from an Excel file use {@link ExcelFileRequirementsImportSettings}.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public abstract class TabularRequirementsImportSettings extends RequirementsImportSettings {

  static final long serialVersionUID = 1L;

  private String importFilePath = null;

  private boolean shouldImportAttachments = false;

  private boolean withHeaderLine = true;

  private String idColumn = "ID";

  private List<String> textColumns = null;

  private String uriColumn = null;

  private String typeColumn = null;

  private List<Pair<String, Requirement.RequirementType>> typeAssignments = null;

  private String documentVersionColumn = null;

  private String manualDocumentVersion = null;

  private String levelColumn = null;

  private String linksColumn = null;

  private String testCaseIdAttribute = null;

  private List<String> attributeColumns = null;

  /**
   * The contructor for the common settings of the requirements import from a CSV or an Excel
   * file.<br>
   * For the requirements import from a CSV file use {@link CsvFileRequirementsImportSettings}.<br>
   * For the requirements import from an Excel file use {@link ExcelFileRequirementsImportSettings}.
   * 
   * @param importFilePath
   *          The path of the source file for the import.
   */
  public TabularRequirementsImportSettings(String importFilePath) {
    super();
    this.importFilePath = importFilePath;
  }

  /**
   * The contructor for the common settings of the requirements import from a CSV or an Excel
   * file.<br>
   * For the requirements import from a CSV file use {@link CsvFileRequirementsImportSettings}.<br>
   * For the requirements import from an Excel file use
   * {@link ExcelFileRequirementsImportSettings}.<br>
   * The path to the source file for the import must be set via {@link #setImportFilePath(String)}.
   */
  public TabularRequirementsImportSettings() {
    super();
  }

  /**
   * @return The path of the source file for the import.
   */
  public String getImportFilePath() {
    return importFilePath;
  }

  /**
   * @param importFilePath
   *          The path of the source file for the import.
   */
  public void setImportFilePath(String importFilePath) {
    this.importFilePath = importFilePath;
  }

  /**
   * @return Whether the import should attach files to the imported requirements by embedding their
   *         contents in the project.
   */
  public boolean isShouldImportAttachments() {
    return shouldImportAttachments;
  }

  /**
   * @param shouldImportAttachments
   *          Set to <code>true</code> if the import should attach files to the imported
   *          requirements by embedding their contents in the project.
   */
  public void setShouldImportAttachments(boolean shouldImportAttachments) {
    this.shouldImportAttachments = shouldImportAttachments;
  }

  /**
   * @return Whether the first line is a header.
   */
  public boolean isWithHeaderLine() {
    return withHeaderLine;
  }

  /**
   * @param withHeaderLine
   *          Whether the first line is a header.
   */
  public void setWithHeaderLine(boolean withHeaderLine) {
    this.withHeaderLine = withHeaderLine;
  }

  /**
   * @return The name of the column in the source file which contains the requirement ID.
   */
  public String getIdColumn() {
    return idColumn;
  }

  /**
   * @param idColumn
   *          The name of the column in the source file which contains the requirement ID. Must not
   *          be <code>null</code>.
   */
  public void setIdColumn(String idColumn) {
    this.idColumn = idColumn;
  }

  /**
   * @return The column names in the source file which contain requirement texts. TPT shows
   *         requirement texts in the requirements table and in report tables.
   */
  public List<String> getTextColumns() {
    return textColumns;
  }

  /**
   * @param textColumns
   *          The column names in the source file which contain requirement texts. TPT shows
   *          requirement texts in the requirements table and in report tables.
   */
  public void setTextColumns(List<String> textColumns) {
    this.textColumns = textColumns;
  }

  /**
   * @return Optional name of the column in the source file which contains the requirement URI. The
   *         imported URIs will be used to create hyperlinks, e.g., to the requirements origin.
   *         These hyperlinks can then be found in the requirements table and in report tables.
   */
  public String getUriColumn() {
    return uriColumn;
  }

  /**
   * @param uriColumn
   *          Optional name of the column in the source file which contains the requirement URI. The
   *          imported URIs will be used to create hyperlinks, e.g., to the requirements origin.
   *          These hyperlinks can then be found in the requirements table and in report tables.
   */
  public void setUriColumn(String uriColumn) {
    this.uriColumn = uriColumn;
  }

  /**
   * @return Optional name of the column in the source file which contains type information. If no
   *         type column is chosen, all objects will be imported as requirement.
   */
  public String getTypeColumn() {
    return typeColumn;
  }

  /**
   * @param typeColumn
   *          Optional name of the column in the source file which contains type information. If no
   *          type column is chosen, all objects will be imported as requirement.
   */
  public void setTypeColumn(String typeColumn) {
    this.typeColumn = typeColumn;
  }

  /**
   * @return Only relevant if a type column is given via {@link #setTypeColumn(String)}. The list
   *         are the assignments of the cell values in the type column to the requirement types.
   */
  public List<Pair<String, Requirement.RequirementType>> getTypeAssignments() {
    return typeAssignments;
  }

  /**
   * @param typeAssignments
   *          Only relevant if a type column is given via {@link #setTypeColumn(String)}. The list
   *          are the assignments of the cell values in the type column to the requirement types.
   */
  public void setTypeAssignments(List<Pair<String, Requirement.RequirementType>> typeAssignments) {
    this.typeAssignments = typeAssignments;
  }

  /**
   * @return The name of the column in the source file which contains the document version. Only
   *         relevant if no manual document version is given. If not <code>null</code>, the version
   *         within the cell in this column will be used for the document.
   * @see TabularRequirementsImportSettings#setManualDocumentVersion(String)
   */
  public String getDocumentVersionColumn() {
    return documentVersionColumn;
  }

  /**
   * @param documentVersionColumn
   *          The name of the column in the source file which contains the document version. Only
   *          relevant if no manual document version is given. If not <code>null</code>, the version
   *          within the cell in this column will be used for the document.
   * @see TabularRequirementsImportSettings#setManualDocumentVersion(String)
   */
  public void setDocumentVersionColumn(String documentVersionColumn) {
    this.documentVersionColumn = documentVersionColumn;
  }

  /**
   * @return The given version that is used for the document.
   */
  public String getManualDocumentVersion() {
    return manualDocumentVersion;
  }

  /**
   * @param manualDocumentVersion
   *          If not <code>null</code>, this version will be used for the document.
   */
  public void setManualDocumentVersion(String manualDocumentVersion) {
    this.manualDocumentVersion = manualDocumentVersion;
  }

  /**
   * @return Optional name of the column in the source file which contains the indentation level.
   *         The requirement texts in the requirements table can be indented for a more organized
   *         view. Nonnegative integer values in the chosen column are taken as an amount of
   *         indentation. Note that changes regarding indentation levels are automatically reviewed
   *         and do not cause requirements to be marked for review.
   */
  public String getLevelColumn() {
    return levelColumn;
  }

  /**
   * @param levelColumn
   *          Optional name of the column in the source file which contains the indentation level.
   *          The requirement texts in the requirements table can be indented for a more organized
   *          view. Nonnegative integer values in the chosen column are taken as an amount of
   *          indentation. Note that changes regarding indentation levels are automatically reviewed
   *          and do not cause requirements to be marked for review.
   */
  public void setLevelColumn(String levelColumn) {
    this.levelColumn = levelColumn;
  }

  /**
   * @return Optional name of the column in the source file which contains the test case IDs to
   *         update the links to requirements. If <code>null</code>, links are not imported.
   */
  public String getLinksColumn() {
    return linksColumn;
  }

  /**
   * @param linksColumn
   *          Optional name of the column in the source file which contains the test case IDs to
   *          update the links to requirements. If <code>null</code>, links are not imported.
   */
  public void setLinksColumn(String linksColumn) {
    this.linksColumn = linksColumn;
  }

  /**
   * @return The name of the TPT test case attribute that contains the IDs to find the linked test
   *         cases. If <code>null</code>, the ID of the test case is used instead. Only relevant if
   *         a name of a links column is given such that links are imported.
   * @see #setLinksColumn(String)
   */
  public String getTestCaseIdAttribute() {
    return testCaseIdAttribute;
  }

  /**
   * @param testCaseIdAttribute
   *          The name of the TPT test case attribute that contains the IDs to find the linked test
   *          cases. If <code>null</code>, the ID of the test case is used instead. Only relevant if
   *          a name of a links column is given such that links are imported.
   * @see #setLinksColumn(String)
   */
  public void setTestCaseIdAttribute(String testCaseIdAttribute) {
    this.testCaseIdAttribute = testCaseIdAttribute;
  }

  /**
   * @return Optional list of column names in the source file to import them as additional
   *         requirements attributes. To enable the automatic review of possible changes of an
   *         requirement attribute add the column name also to the {@link #getAutoReviewColumns()
   *         list of autoreview attributes}.
   * @see #setAutoReviewColumns(List)
   */
  public List<String> getAttributeColumns() {
    return attributeColumns;
  }

  /**
   * @param attributeColumns
   *          Optional list of column names in the source file to import them as additional
   *          requirements attributes. To enable the automatic review of possible changes of an
   *          requirement attribute add the column name also to the {@link #getAutoReviewColumns()
   *          list of autoreview attributes}.
   * @see #setAutoReviewColumns(List)
   */
  public void setAttributeColumns(List<String> attributeColumns) {
    this.attributeColumns = attributeColumns;
  }

}
