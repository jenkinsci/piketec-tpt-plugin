package com.piketec.tpt.api.requirements.reqif;

import java.util.List;

import com.piketec.tpt.api.requirements.RequirementsImportSettings;

/**
 * The settings for the requirements import from a reqIF file.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
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
   * @return Optional list of column names to import them as additional requirements attributes. To
   *         enable the automatic review of possible changes of an requirement attribute add the
   *         column name also to the {@link #getAutoReviewColumns() list of autoreview attributes}.
   * @see #setAutoReviewColumns(List)
   */
  public List<String> getAttributeColumns() {
    return attributeColumns;
  }

  /**
   * @param attributeColumns
   *          Optional list of column names to import them as additional requirements attributes. To
   *          enable the automatic review of possible changes of an requirement attribute add the
   *          column name also to the {@link #getAutoReviewColumns() list of autoreview attributes}.
   * @see #setAutoReviewColumns(List)
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
