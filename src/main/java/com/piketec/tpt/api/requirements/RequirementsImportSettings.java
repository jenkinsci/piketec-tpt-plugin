package com.piketec.tpt.api.requirements;

import java.io.Serializable;
import java.util.List;

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
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public abstract class RequirementsImportSettings implements Serializable {

  static final long serialVersionUID = 1L;

  private String document = "";

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
   * @return Optional list of column names in the source file for which the automatic review of
   *         possible changes of a requirement attribute should be enabled.
   */
  public List<String> getAutoReviewColumns() {
    return autoReviewColumns;
  }

  /**
   * @param autoReviewColumns
   *          Optional list of column names in the source file for which the automatic review of
   *          possible changes of a requirement attribute should be enabled.
   */
  public void setAutoReviewColumns(List<String> autoReviewColumns) {
    this.autoReviewColumns = autoReviewColumns;
  }

}
