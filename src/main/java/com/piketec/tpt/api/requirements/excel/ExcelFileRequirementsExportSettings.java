package com.piketec.tpt.api.requirements.excel;

import com.piketec.tpt.api.requirements.RequirementsExportSettings;

/**
 * The settings for the requirements export to an Excel file.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public class ExcelFileRequirementsExportSettings extends RequirementsExportSettings {

  static final long serialVersionUID = 1L;

  private String anchor = "A1";

  private String sheet = "TPT Export";

  /**
   * The contructor for the settings of the requirements export to an Excel file.
   * 
   * @param exportFilePath
   *          The path of the target Excel file for the export.
   */
  public ExcelFileRequirementsExportSettings(String exportFilePath) {
    super(exportFilePath);
  }

  /**
   * @return The anchor (i.e., the upper-left most cell) within the sheet for the export.
   */
  public String getAnchor() {
    return anchor;
  }

  /**
   * @param anchor
   *          The anchor (i.e., the upper-left most cell) within the sheet for the export.
   */
  public void setAnchor(String anchor) {
    this.anchor = anchor;
  }

  /**
   * @return The target sheet of the Excel file for the export.
   */
  public String getSheet() {
    return sheet;
  }

  /**
   * @param sheet
   *          The target sheet of the Excel file for the export.
   */
  public void setSheet(String sheet) {
    this.sheet = sheet;
  }

}
