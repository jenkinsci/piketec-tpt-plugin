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
package com.piketec.tpt.api.requirements.excel;

import com.piketec.tpt.api.requirements.RequirementsExportSettings;

/**
 * The settings for the requirements export to an Excel file.
 * 
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
