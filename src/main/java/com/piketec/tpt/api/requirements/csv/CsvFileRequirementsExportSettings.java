package com.piketec.tpt.api.requirements.csv;

import com.piketec.tpt.api.requirements.RequirementsExportSettings;

/**
 * The settings for the requirements export to a CSV file.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public class CsvFileRequirementsExportSettings extends RequirementsExportSettings {

  static final long serialVersionUID = 1L;

  private char delimiter = ',';

  private char quoting = '"';

  private String encoding = "ISO-8859-1";

  /**
   * The constructor for the settings of the requirements export to a CSV file.
   * 
   * @param exportFilePath
   *          The path of the target CSV file for the export.
   */
  public CsvFileRequirementsExportSettings(String exportFilePath) {
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
