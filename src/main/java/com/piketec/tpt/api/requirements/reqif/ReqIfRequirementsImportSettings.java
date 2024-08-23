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
package com.piketec.tpt.api.requirements.reqif;

import java.util.List;

import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.requirements.RequirementsImportSettings;

/**
 * The settings for the requirements import from a reqIF file.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class ReqIfRequirementsImportSettings extends RequirementsImportSettings {

  static final long serialVersionUID = 1L;

  private String importFilePath = null;

  private List<String> attributeColumns = null;

  private boolean allAdditionalAttributeColumns = true;

  /**
   * The contructor for the settings of the requirements import from a reqIF file.
   * 
   * @param importFilePath
   *          The path to the source reqIF file for the import.
   */
  public ReqIfRequirementsImportSettings(String importFilePath) {
    super();
    this.importFilePath = importFilePath;
  }

  /**
   * The contructor for the settings of the requirements import from a reqIF file.<br>
   * The path to the source reqIF file for the import must be set via
   * {@link #setImportFilePath(String)}.
   */
  public ReqIfRequirementsImportSettings() {
    super();
  }

  /**
   * @return The path to the source reqIF file.
   */
  public String getImportFilePath() {
    return importFilePath;
  }

  /**
   * @param importFilePath
   *          The path of the source reqIF file for the import.
   */
  public void setImportFilePath(String importFilePath) {
    this.importFilePath = importFilePath;
  }

  /**
   * @return Optional list of column names to import them as additional requirements attributes.
   */
  public List<String> getAttributeColumns() {
    return attributeColumns;
  }

  /**
   * @param attributeColumns
   *          Optional list of column names to import them as additional requirements attributes. To
   *          enable the automatic review of possible changes of an requirement attribute
   *          {@link Project#setRequirementAttributeAutoReview(String, boolean) change the auto
   *          review flag of existing requirement attributes}.
   * @see Project#setRequirementAttributeAutoReview(String, boolean)
   */
  public void setAttributeColumns(List<String> attributeColumns) {
    this.attributeColumns = attributeColumns;
  }

  /**
   * @return Whether additional non-standard attributes of specification types that are not
   *         contained in {@link #getAttributeColumns() specified attributes} should be imported as
   *         well.
   * @see #setAttributeColumns(List)
   */
  public boolean isAllAdditionalAttributeColumns() {
    return allAdditionalAttributeColumns;
  }

  /**
   * @param allAdditionalColumns
   *          Whether additional non-standard attributes of specification types that are not
   *          contained in {@link #getAttributeColumns() specified attributes} should be imported as
   *          well.
   * @see #setAttributeColumns(List)
   */
  public void setAllAdditionalAttributeColumns(boolean allAdditionalColumns) {
    this.allAdditionalAttributeColumns = allAdditionalColumns;
  }

}
