package com.piketec.tpt.api.requirements.excel;

import com.piketec.tpt.api.requirements.tabular.TabularTestCasesImportSettings;

/**
 * The settings for the requirements import of test cases from an Excel file.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class ExcelFileTestCasesImportSettings extends TabularTestCasesImportSettings {

  static final long serialVersionUID = 1L;

  /**
   * The choice for determining the range of cells within the sheet for the import.
   *
   */
  public enum ExcelRangeDeterminationMethod {
    /**
     * Use all cells within the sheet for the import.
     */
    AUTO_MAX,
    /**
     * Use the cells between the top-left most cell with name "ID" and the bottom-right most cell
     * within the sheet for the import.
     */
    AUTO_ID_TOP_LEFT,
    /**
     * Use the cells within the {@link #getRange() given range} (which must not be
     * <code>null</code>).
     * 
     * @see #setRange(String)
     */
    MANUAL
  }

  private ExcelRangeDeterminationMethod rangeDeterminationMethod =
      ExcelRangeDeterminationMethod.AUTO_MAX;

  private String range = null;

  private String sheet = null;

  private boolean checkForHyperlinks = false;

  /**
   * The constructor for the settings of the requirements import of test cases from an Excel file.
   * 
   * @param importFilePath
   *          The path of the source Excel file for the import.
   */
  public ExcelFileTestCasesImportSettings(String importFilePath) {
    super(importFilePath);
  }

  /**
   * @return The choice for determining the range of cells within the sheet for the import.
   */
  public ExcelRangeDeterminationMethod getRangeDeterminationMethod() {
    return this.rangeDeterminationMethod;
  }

  /**
   * @param rangeDeterminationMethod
   *          The choice for determining the range of cells within the sheet for the import.
   */
  public void setRangeDeterminationMethod(ExcelRangeDeterminationMethod rangeDeterminationMethod) {
    this.rangeDeterminationMethod = rangeDeterminationMethod;
  }

  /**
   * @return The range between the upper-left most cell and the lower-right most cell within the
   *         sheet for the import. Only relevant if the {@link #getRangeDeterminationMethod() range
   *         determination} is {@link ExcelRangeDeterminationMethod#MANUAL}.
   */
  public String getRange() {
    return this.range;
  }

  /**
   * @param range
   *          The range between the upper-left most cell and the lower-right most cell within the
   *          sheet for the import. Only relevant if the {@link #getRangeDeterminationMethod() range
   *          determination} is {@link ExcelRangeDeterminationMethod#MANUAL}. Must not be
   *          <code>null</code> in this case and of the form e.g., "A1:X27".
   */
  public void setRange(String range) {
    this.range = range;
  }

  /**
   * @return The source sheet of the Excel file for the import. If <code>null</code>, the first of
   *         the Excel file is used.
   */
  public String getSheet() {
    return this.sheet;
  }

  /**
   * @param sheet
   *          The source sheet of the Excel file for the import. If <code>null</code>, the first of
   *          the Excel file is used.
   */
  public void setSheet(String sheet) {
    this.sheet = sheet;
  }

  /**
   * @return Whether the importer creates additional columns to import them as well if hyperlinks
   *         are found in Excel cells.
   */
  public boolean isCheckForHyperlinks() {
    return this.checkForHyperlinks;
  }

  /**
   * @param checkForHyperlinks
   *          If set to <code>true</code>, the importer creates additional columns to import them as
   *          well if hyperlinks are found in Excel cells.
   */
  public void setCheckForHyperlinks(boolean checkForHyperlinks) {
    this.checkForHyperlinks = checkForHyperlinks;
  }

}
