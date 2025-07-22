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
package com.piketec.tpt.api.requirements;

import java.io.Serializable;
import java.util.List;

import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.requirements.codebeamer.CodeBeamerRequirementsImportSettings;
import com.piketec.tpt.api.requirements.csv.CsvFileRequirementsImportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileRequirementsImportSettings;
import com.piketec.tpt.api.requirements.polarion.PolarionRequirementsImportSettings;
import com.piketec.tpt.api.requirements.reqif.ReqIfRequirementsImportSettings;

/**
 * The common settings for the requirements import.<br>
 * For the requirements import from a CSV file use {@link CsvFileRequirementsImportSettings}.<br>
 * For the requirements import from an Excel file use
 * {@link ExcelFileRequirementsImportSettings}.<br>
 * For the requirements import from Polarion use {@link PolarionRequirementsImportSettings}.<br>
 * For the requirements import from codeBeamer use {@link CodeBeamerRequirementsImportSettings}.<br>
 * For the requirements import from refIF use {@link ReqIfRequirementsImportSettings}.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public abstract class RequirementsImportSettings implements Serializable {

  static final long serialVersionUID = 1L;

  private String document = "";

  private String statusTypeForChanged = "";

  @Deprecated
  private List<String> autoReviewColumns = null;

  /**
   * The constructor for the common settings of the requirements import.<br>
   * For the requirements import from a CSV file use {@link CsvFileRequirementsImportSettings}.<br>
   * For the requirements import from an Excel file use
   * {@link ExcelFileRequirementsImportSettings}.<br>
   * For the requirements import from Polarion use {@link PolarionRequirementsImportSettings}.<br>
   * For the requirements import from codeBeamer use
   * {@link CodeBeamerRequirementsImportSettings}.<br>
   * For the requirements import from refIF use {@link ReqIfRequirementsImportSettings}.
   */
  public RequirementsImportSettings() {
  }

  /**
   * @return The name of the document. An empty string represents the default document.<br>
   *         The requirements are imported in the document with this name. If the target document
   *         exists, it will be updated.
   */
  public String getDocument() {
    return document;
  }

  /**
   * @param document
   *          The name of the document. An empty string represents the default document.<br>
   *          The requirements are imported in the document with this name. If the target document
   *          exists, it will be updated.
   */
  public void setDocument(String document) {
    this.document = document;
  }

  /**
   * @return The name of the status type that is set for items that are changed during the import.
   *         Only status types that aren't stable are used.
   */
  public String getSetStatusTypeForChanged() {
    return statusTypeForChanged;
  }

  /**
   * @param statusType
   *          The name of the status type that is set for items that are changed during the import.
   *          Only status types that aren't stable are used.
   */
  public void setStatusTypeForChanged(String statusType) {
    this.statusTypeForChanged = statusType;
  }

  /**
   * @return Optional list of column names in the source file for which the automatic review of
   *         possible changes of a requirement attribute should be enabled.
   *
   * @deprecated This list has no effect anymore. Use
   *             {@link Project#setRequirementAttributeAutoReview(String, boolean)} instead.
   */
  @Deprecated
  public List<String> getAutoReviewColumns() {
    return autoReviewColumns;
  }

  /**
   * @param autoReviewColumns
   *          Optional list of column names in the source file for which the automatic review of
   *          possible changes of a requirement attribute should be enabled.
   *
   * @deprecated This list has no effect anymore. Use
   *             {@link Project#setRequirementAttributeAutoReview(String, boolean)} instead.
   */
  @Deprecated
  public void setAutoReviewColumns(List<String> autoReviewColumns) {
    this.autoReviewColumns = autoReviewColumns;
  }

}
