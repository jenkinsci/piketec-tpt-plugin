package com.piketec.tpt.api.requirements.tabular;

import java.util.List;

import com.piketec.tpt.api.Pair;
import com.piketec.tpt.api.requirements.TestCasesExportSettings;
import com.piketec.tpt.api.requirements.csv.CsvFileTestCasesExportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileTestCasesExportSettings;

/**
 * The common settings for the requirements export of test cases to a CVS or an Excel file.<br>
 * For the test cases export to a CSV file use {@link CsvFileTestCasesExportSettings}.<br>
 * For the test cases export to an Excel file use {@link ExcelFileTestCasesExportSettings}.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public abstract class TabularTestCasesExportSettings extends TestCasesExportSettings {

  static final long serialVersionUID = 1L;

  private final String exportFilePath;

  private boolean withHeaderLine = true;

  private volatile boolean exportGroups = true;

  private String testSet = null;

  private List<Pair<String, String>> assignments = null;

  /**
   * The constructor for the common settings of the requirements export of test cases to a CVS or an
   * Excel file.<br>
   * For the test cases export to a CSV file use {@link CsvFileTestCasesExportSettings}.<br>
   * For the test cases export to an Excel file use {@link ExcelFileTestCasesExportSettings}.
   * 
   * @param exportFilePath
   *          The path of the target file for the export.
   */
  public TabularTestCasesExportSettings(String exportFilePath) {
    this.exportFilePath = exportFilePath;
  }

  /**
   * @param withHeaderLine
   *          Whether the first line is a header.
   */
  public void setWithHeaderline(boolean withHeaderLine) {
    this.withHeaderLine = withHeaderLine;
  }

  /**
   * @return Whether the first line is a header.
   */
  public boolean isWithHeaderLine() {
    return withHeaderLine;
  }

  /**
   * @param exportGroups
   *          Whether test case groups are exported.
   */
  public void setExportGroups(boolean exportGroups) {
    this.exportGroups = exportGroups;
  }

  /**
   * @return Whether test case groups are exported.
   */
  public boolean isExportGroups() {
    return exportGroups;
  }

  /**
   * @param testSet
   *          The name of the test set with the contained test cases for the export. If
   *          <code>null</code>, all test cases are exported.
   */
  public void setTestSet(String testSet) {
    this.testSet = testSet;
  }

  /**
   * @return The name of the test set with the contained test cases for the export. If
   *         <code>null</code>, all test cases are exported.
   */
  public String getTestSet() {
    return testSet;
  }

  /**
   * @param assignments
   *          The assignment of test cases fields and attributes to export attributes. If
   *          <code>null</code>, the default assignments will be used. If a list is given, the
   *          export will be restricted to its elements.
   */
  public void setAssignments(List<Pair<String, String>> assignments) {
    this.assignments = assignments;
  }

  /**
   * @return The assignment of test cases fields and attributes to export attributes. If
   *         <code>null</code>, the default assignments will be used. If a list is given, the export
   *         will be restricted to its elements.
   */
  public List<Pair<String, String>> getAssignments() {
    return assignments;
  }

  /**
   * @return The path of the target file for the export.
   */
  public String getExportFilePath() {
    return exportFilePath;
  }
}
