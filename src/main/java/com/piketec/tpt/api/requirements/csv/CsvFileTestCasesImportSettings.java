package com.piketec.tpt.api.requirements.csv;

import com.piketec.tpt.api.requirements.tabular.TabularTestCasesImportSettings;

/**
 * The settings for the requirements import of test cases from a CSV file.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public class CsvFileTestCasesImportSettings extends TabularTestCasesImportSettings {

  static final long serialVersionUID = 1L;

  private char delimiter = ',';

  private char quoting = '"';

  private String encoding = "ISO-8859-1";

  /**
   * The constructor for the settings of the requirements import of test cases from a CSV file.
   * 
   * @param importFilePath
   *          The path of the source CSV file for the import.
   */
  public CsvFileTestCasesImportSettings(String importFilePath) {
    super(importFilePath);
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
