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
package com.piketec.tpt.api.requirements;

import java.io.Serializable;
import java.util.List;

import com.piketec.tpt.api.Pair;
import com.piketec.tpt.api.requirements.csv.CsvFileRequirementsExportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileRequirementsExportSettings;

/**
 * The common settings for the requirements export to a file.<br>
 * For the requirements export to a CSV file use {@link CsvFileRequirementsExportSettings}.<br>
 * For the requirements export to an Excel file use {@link ExcelFileRequirementsExportSettings}.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public abstract class RequirementsExportSettings implements Serializable {

  static final long serialVersionUID = 1L;

  private final String exportFilePath;

  private boolean withHeaderLine = true;

  private String document = null;

  private List<Pair<String, String>> assignments = null;

  /**
   * The constructor for the common settings of the requirements export to a file.<br>
   * For the requirements export to a CSV file use {@link CsvFileRequirementsExportSettings}.<br>
   * For the requirements export to an Excel file use {@link ExcelFileRequirementsExportSettings}.
   * 
   * @param exportFilePath
   *          The path of the target file for the export.
   */
  public RequirementsExportSettings(String exportFilePath) {
    this.exportFilePath = exportFilePath;
  }

  /**
   * @return The path of the target file for the export.
   */
  public String getExportFilePath() {
    return exportFilePath;
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
   * @return The assignment of TPT requirement fields, attributes or other details to export
   *         attributes. If <code>null</code>, the default assignments will be used. If a list is
   *         given, the export will be restricted to its elements.
   */
  public List<Pair<String, String>> getAssignments() {
    return assignments;
  }

  /**
   * @param assignments
   *          The assignment of TPT requirement fields, attributes or other details to export
   *          attributes. If <code>null</code>, the default assignments will be used. If a list is
   *          given, the export will be restricted to its elements.
   */
  public void setAssignments(List<Pair<String, String>> assignments) {
    this.assignments = assignments;
  }

  /**
   * @return <code>null</code>, if only the requirements for this document are exported.<br>
   *         Returns <code>null</code>, if all requirements are exported.
   */
  public String getDocument() {
    return document;
  }

  /**
   * @param document
   *          If not set to <code>null</code>, only the requirements for this document are
   *          exported.<br>
   *          If set to <code>null</code>, all requirements are exported.
   */
  public void setDocument(String document) {
    this.document = document;
  }

}
