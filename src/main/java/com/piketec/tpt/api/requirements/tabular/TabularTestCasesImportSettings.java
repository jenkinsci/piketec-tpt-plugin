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
package com.piketec.tpt.api.requirements.tabular;

import java.util.List;

import com.piketec.tpt.api.Pair;
import com.piketec.tpt.api.TestCaseAttribute;
import com.piketec.tpt.api.requirements.TestCasesImportSettings;
import com.piketec.tpt.api.requirements.TestCasesImportSettings.TargetTypeAssignment.TargetType;
import com.piketec.tpt.api.requirements.csv.CsvFileTestCasesImportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileTestCasesImportSettings;

/**
 * The common settings for the requirements import of test cases from a CVS or an Excel file.<br>
 * For the test cases import from a CSV file use {@link CsvFileTestCasesImportSettings}.<br>
 * For the test cases import from an Excel file use {@link ExcelFileTestCasesImportSettings}.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public abstract class TabularTestCasesImportSettings extends TestCasesImportSettings {

  static final long serialVersionUID = 1L;

  private final String importFilePath;

  private boolean withHeaderLine = true;

  private String idColumn = null;

  private String nameColumn = null;

  private String typeColumn = null;

  private List<TargetTypeAssignment> typeAssignments = null;

  private String levelColumn = null;

  private String groupPathColumn = null;

  private List<String> descriptionColumns = null;

  private volatile String linksColumn = null;

  private List<Pair<String, String>> attributeAssignments = null;

  /**
   * The constructor for the common settings of the requirements import of test cases from a CSV or
   * an Excel file.<br>
   * For the test cases import from a CSV file use {@link CsvFileTestCasesImportSettings}.<br>
   * For the test cases import from an Excel file use {@link ExcelFileTestCasesImportSettings}.
   * 
   * @param importFilePath
   *          The path of the source file for the import.
   */
  public TabularTestCasesImportSettings(String importFilePath) {
    super();
    this.importFilePath = importFilePath;
  }

  /**
   * @return The path of the source file for the import.
   */
  public String getImportFilePath() {
    return importFilePath;
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
   * @return The name of the column in the source file that contains the ID. Not relevant if the
   *         {@link #getSyncMethod() synchronization method} is
   *         {@link com.piketec.tpt.api.requirements.TestCasesImportSettings.SynchronizationMethod#ALL_NEW}.
   * @see #setSyncMethod(SynchronizationMethod)
   */
  public String getIdColumn() {
    return idColumn;
  }

  /**
   * 
   * Sets the name of the column in the source file that contains the ID and the TPT test case
   * attribute that contains the ID to match existing test cases against imported ones.<br>
   * Not relevant if the {@link #getSyncMethod() synchronization method} is
   * {@link com.piketec.tpt.api.requirements.TestCasesImportSettings.SynchronizationMethod#ALL_NEW}.
   * 
   * @param idColumn
   *          The name of the column in the source file that contains the ID.
   * @param idAttribute
   *          The TPT test case attribute that contains the ID to match existing test cases against
   *          imported ones. If <code>null</code>, the importer uses the TPT test case ID to find a
   *          match.
   * 
   * @see #setSyncMethod(SynchronizationMethod)
   */
  public void setIdColumnAndAttribute(String idColumn, String idAttribute) {
    this.idColumn = idColumn;
    setIdAttribute(idAttribute);
  }

  /**
   * @return Optional name of the column in the source file that contains the test case names (the
   *         name which is shown in the project tree). If left empty the importer will set default
   *         names.
   */
  public String getNameColumn() {
    return nameColumn;
  }

  /**
   * @param nameColumn
   *          Optional name of the column in the source file that contains the test case names (the
   *          name which is shown in the project tree). If left empty the importer will set default
   *          names.
   */
  public void setNameColumn(String nameColumn) {
    this.nameColumn = nameColumn;
  }

  /**
   * @return Optional name of the column in the source file that contains the type information to
   *         distinguish imported objects between test cases and test case groups. If no type column
   *         is chosen, objects without children will be imported as test cases, others as groups.
   *         Only relevant if the test case hierarchy creation method is
   *         {@link com.piketec.tpt.api.requirements.TestCasesImportSettings.TestCaseHierarchyCreationMethod#LEVEL_AND_TYPE}.
   * @see #setHierarchyCreationMethod(TestCaseHierarchyCreationMethod)
   */
  public String getTypeColumn() {
    return typeColumn;
  }

  /**
   * @param typeColumn
   *          Optional name of the column in the source file that contains the type information to
   *          distinguish imported objects between test cases and test case groups. If no type
   *          column is chosen, objects without children will be imported as test cases, others as
   *          groups. Only relevant if the test case hierarchy creation method is
   *          {@link com.piketec.tpt.api.requirements.TestCasesImportSettings.TestCaseHierarchyCreationMethod#LEVEL_AND_TYPE}.
   * @see #setHierarchyCreationMethod(TestCaseHierarchyCreationMethod)
   */
  public void setTypeColumn(String typeColumn) {
    this.typeColumn = typeColumn;
  }

  /**
   * @return Optional list with the assignments of the cell values in the type column to the target
   *         types (test case vs. test case group). Only relevant if the test case hierarchy
   *         creation method is
   *         {@link com.piketec.tpt.api.requirements.TestCasesImportSettings.TestCaseHierarchyCreationMethod#LEVEL_AND_TYPE}.<br>
   *         Assignments for not imported cell values will be ignored. Get rid of them by assigning
   *         {@link TargetType#AUTO}.
   */
  public List<TargetTypeAssignment> getTypeAssignments() {
    return typeAssignments;
  }

  /**
   * @param typeAssignments
   *          Optional list with the assignments of the cell values in the type column to the target
   *          types (test case vs. test case group). Only relevant if the test case hierarchy
   *          creation method is
   *          {@link com.piketec.tpt.api.requirements.TestCasesImportSettings.TestCaseHierarchyCreationMethod#LEVEL_AND_TYPE}.
   *          Assignments for not imported cell values will be ignored. Get rid of them by assigning
   *          {@link TargetType#AUTO}.
   */
  public void setTypeAssignments(List<TargetTypeAssignment> typeAssignments) {
    this.typeAssignments = typeAssignments;
  }

  /**
   * @return Optional name of the column in the source file that contains level information. Only
   *         relevant if the test case hierarchy creation method is
   *         {@link com.piketec.tpt.api.requirements.TestCasesImportSettings.TestCaseHierarchyCreationMethod#LEVEL_AND_TYPE}.
   * @see #setHierarchyCreationMethod(TestCaseHierarchyCreationMethod)
   */
  public String getLevelColumn() {
    return levelColumn;
  }

  /**
   * @param levelColumn
   *          Optional name of the column in the source file that contains level information. Only
   *          relevant if the test case hierarchy creation method is
   *          {@link com.piketec.tpt.api.requirements.TestCasesImportSettings.TestCaseHierarchyCreationMethod#LEVEL_AND_TYPE}.
   * @see #setHierarchyCreationMethod(TestCaseHierarchyCreationMethod)
   */
  public void setLevelColumn(String levelColumn) {
    this.levelColumn = levelColumn;
  }

  /**
   * @return Optional name of the column in the source file that contains group paths to create the
   *         hierarchy. Only relevant if the test case hierarchy creation method is
   *         {@link com.piketec.tpt.api.requirements.TestCasesImportSettings.TestCaseHierarchyCreationMethod#GROUP_PATH_ATTRIBUTE}.
   * @see #setHierarchyCreationMethod(TestCaseHierarchyCreationMethod)
   */
  public String getGroupPathColumn() {
    return groupPathColumn;
  }

  /**
   * @param groupPathColumn
   *          Optional name of the column in the source file that contains group paths to create the
   *          hierarchy. Only relevant if the test case hierarchy creation method is
   *          {@link com.piketec.tpt.api.requirements.TestCasesImportSettings.TestCaseHierarchyCreationMethod#GROUP_PATH_ATTRIBUTE}.
   * @see #setHierarchyCreationMethod(TestCaseHierarchyCreationMethod)
   */
  public void setGroupPathColumn(String groupPathColumn) {
    this.groupPathColumn = groupPathColumn;
  }

  /**
   * @return Optional list of names of columns in the source file that contain the test case
   *         descriptions.
   */
  public List<String> getDescriptionColumns() {
    return descriptionColumns;
  }

  /**
   * @param descriptionColumns
   *          Optional list of names of columns in the source file that contain the test case
   *          descriptions.
   */
  public void setDescriptionColumns(List<String> descriptionColumns) {
    this.descriptionColumns = descriptionColumns;
  }

  /**
   * @return Optional name of the column in the source file that contains requirement IDs to update
   *         the links to test cases. If <code>null</code>, links are not imported.
   */
  public String getLinksColumn() {
    return linksColumn;
  }

  /**
   * @param linksColumn
   *          Optional name of the column in the source file that contains requirement IDs to update
   *          the links to test cases. If set to <code>null</code>, links are not imported.
   */
  public void setLinksColumn(String linksColumn) {
    this.linksColumn = linksColumn;
  }

  /**
   * @return Optional list of assigments of column names in the source file to test case attribute
   *         names.
   */
  public List<Pair<String, String>> getAttributeAssignments() {
    return attributeAssignments;
  }

  /**
   * @param attributeAssignments
   *          Optional list of assigments of column names in the source file to test case attribute
   *          names. To enable the automatic review of possible changes of a test case attribute
   *          {@link TestCaseAttribute#setAutoReview(boolean) change the auto review flag of
   *          existing test case attributes}.
   * @see TestCaseAttribute#setAutoReview(boolean)
   */
  public void setAttributeAssignments(List<Pair<String, String>> attributeAssignments) {
    this.attributeAssignments = attributeAssignments;
  }

}
