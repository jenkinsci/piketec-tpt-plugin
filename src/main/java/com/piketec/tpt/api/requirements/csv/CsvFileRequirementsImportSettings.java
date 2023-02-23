package com.piketec.tpt.api.requirements.csv;

import com.piketec.tpt.api.requirements.tabular.TabularRequirementsImportSettings;

/**
 * The settings for the requirements import from a CSV file.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public class CsvFileRequirementsImportSettings extends TabularRequirementsImportSettings {

  static final long serialVersionUID = 1L;

  private char delimiter = ',';

  private char quoting = '"';

  private String encoding = "ISO-8859-1";

  /**
   * The contructor for the settings of the requirements import from a CSV file.
   * 
   * @param importFilePath
   *          The path of the source CSV file for the import.
   */
  public CsvFileRequirementsImportSettings(String importFilePath) {
    super(importFilePath);
  }

  /**
   * The contructor for the settings of the requirements import from a CSV file.<br>
   * The path to the CSV file for the import must be set via {@link #setImportFilePath(String)}.
   */
  public CsvFileRequirementsImportSettings() {
    this(null);
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
