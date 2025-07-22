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
package com.piketec.tpt.api.requirements.csv;

import com.piketec.tpt.api.requirements.tabular.TabularRequirementsImportSettings;

/**
 * The settings for the requirements import from a CSV file.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
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
