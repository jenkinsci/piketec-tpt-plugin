package com.piketec.tpt.api.requirements.csv;

import com.piketec.tpt.api.requirements.tabular.TabularTestCasesExportSettings;

/**
 * The settings for the requirements export of test cases to a CSV file.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public class CsvFileTestCasesExportSettings extends TabularTestCasesExportSettings {

  static final long serialVersionUID = 1L;

  private char delimiter = ',';

  private char quoting = '"';

  private String encoding = "ISO-8859-1";

  /**
   * The constructor for the settings of the requirements export of test cases to a CVS file.
   * 
   * @param exportFilePath
   *          The path of the target CSV file for the export.
   */
  public CsvFileTestCasesExportSettings(String exportFilePath) {
    super(exportFilePath);
  }

  /**
   * @return The delimiter between cells.
   */
  public char getDelimiter() {
    return this.delimiter;
  }

  /**
   * @param delimiter
   *          The delimiter between cells.
   */
  public void setDelimiter(char delimiter) {
    this.delimiter = delimiter;
  }

  /**
   * @return The quoting around a cell.
   */
  public char getQuoting() {
    return this.quoting;
  }

  /**
   * @param quoting
   *          The quoting around a cell.
   */
  public void setQuoting(char quoting) {
    this.quoting = quoting;
  }

  /**
   * @return The encoding of the file.
   */
  public String getEncoding() {
    return this.encoding;
  }

  /**
   * @param encoding
   *          The encoding of the file.
   */
  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

}
