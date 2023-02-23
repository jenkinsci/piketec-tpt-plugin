package com.piketec.tpt.api.requirements.polarion;

import java.io.Serializable;
import java.util.List;

import com.piketec.tpt.api.requirements.TestCasesExportSettings;

/**
 * The settings for the export of test cases to Polarion.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public class PolarionTestCasesExportSettings extends TestCasesExportSettings {

  static final long serialVersionUID = 1L;

  private final String projectId;

  private String testSet = null;

  private boolean exportDescription = true;

  private String workItemType = null;

  private String idAttribute = null;

  private List<TCAttributeToWorkItemFieldAssignment> attributeAssignments = null;

  private String linkRole = null;

  private boolean removeUnexportedLinks = false;

  /**
   * The constructor for the settings of the export of test cases to Polarion.
   * 
   * @param projectId
   *          The ID of the project in Polarion.
   */
  public PolarionTestCasesExportSettings(String projectId) {
    this.projectId = projectId;

  }

  /**
   * @return The ID of the project in Polarion.
   */
  public String getProjectId() {
    return projectId;
  }

  /**
   * @return The TPT test set to export a subset of TPT test cases. If <code>null</code>, all test
   *         cases are exported.
   */
  public String getTestSet() {
    return testSet;
  }

  /**
   * @param testSet
   *          The TPT test set to export a subset of TPT test cases. If <code>null</code>, all test
   *          cases are exported.
   */
  public void setTestSet(String testSet) {
    this.testSet = testSet;
  }

  /**
   * @return Whether to export the TPT description to the description field in Polarion. Note that
   *         the description will be exported as plain text.
   */
  public boolean isExportDescription() {
    return exportDescription;
  }

  /**
   * @param exportDescription
   *          Whether to export the TPT description to the description field in Polarion. Note that
   *          the description will be exported as plain text.
   */
  public void setExportDescription(boolean exportDescription) {
    this.exportDescription = exportDescription;
  }

  /**
   * @return The ID of the Polarion work item type to use for the exported test cases.
   */
  public String getWorkItemType() {
    return workItemType;
  }

  /**
   * @param workItemType
   *          The ID of the Polarion work item type to use for the exported test cases.
   */
  public void setWorkItemType(String workItemType) {
    this.workItemType = workItemType;
  }

  /**
   * @return The TPT test case attribute which contains the Polarion IDs. It will be used to update
   *         test cases which already are known in Polarion. Furthermore the IDs of newly created
   *         work items will be applied to the respective TPT test cases.
   */
  public String getIdAttribute() {
    return idAttribute;
  }

  /**
   * @param idAttribute
   *          The TPT test case attribute which contains the Polarion IDs. It will be used to update
   *          test cases which already are known in Polarion. Furthermore the IDs of newly created
   *          work items will be applied to the respective TPT test cases.
   */
  public void setIdAttribute(String idAttribute) {
    this.idAttribute = idAttribute;
  }

  /**
   * @return The list of assignments of TPT test case attributes to Polarion Work Item fields.
   */
  public List<TCAttributeToWorkItemFieldAssignment> getAttributeAssignments() {
    return attributeAssignments;
  }

  /**
   * @param attributeAssignments
   *          The list of assignments of TPT test case attributes to Polarion Work Item fields.
   */
  public void setAttributeAssignments(List<TCAttributeToWorkItemFieldAssignment> attributeAssignments) {
    this.attributeAssignments = attributeAssignments;
  }

  /**
   * @return The ID of the Polarion role of the exported links. If <code>null</code>, no links are
   *         exported.
   */
  public String getLinkRole() {
    return linkRole;
  }

  /**
   * @param linkRole
   *          The ID of the Polarion role of the exported links. If set to <code>null</code>, no
   *          links are exported.
   */
  public void setLinkRole(String linkRole) {
    this.linkRole = linkRole;
  }

  /**
   * @return Whether to remove unexported links. If <code>true</code>, Polarion links of the same
   *         {@link #getLinkRole() role as specified} that are not exported are removed.
   * @see #setLinkRole(String)
   */
  public boolean isRemoveUnexportedLinks() {
    return removeUnexportedLinks;
  }

  /**
   * @param removeUnexportedLinks
   *          Whether to remove unexported links. If set to <code>true</code>, Polarion links of the
   *          same {@link #getLinkRole() role as specified} that are not exported are removed.
   * @see #setLinkRole(String)
   */
  public void setRemoveUnexportedLinks(boolean removeUnexportedLinks) {
    this.removeUnexportedLinks = removeUnexportedLinks;
  }

  // -----------------------------------------------------------------------------

  /**
   * The assignment of a TPT test case attribute to a Polarion Work Item field.
   *
   */
  public static class TCAttributeToWorkItemFieldAssignment implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * The type of the Polarion Work Item field for the export.
     */
    public static enum ExportFieldType {
      /**
       * 
       */
      FIELD,
      /**
      * 
      */
      CUSTOM_DEFINED,
      /**
      * 
      */
      CUSTOM_UNDEFINED
    }

    private final ExportFieldType fieldType;

    private final String fieldId;

    private String attribute;

    /**
     * The constructor for the assignment of a TPT test case attribute to a Polarion Work Item
     * field.
     * 
     * @param fieldType
     *          The type of the Polarion Work Item field.
     * @param fieldId
     *          The ID of the Polarion Work Item field.
     *
     */
    public TCAttributeToWorkItemFieldAssignment(ExportFieldType fieldType, String fieldId) {
      this.fieldType = fieldType;
      this.fieldId = fieldId;
    }

    /**
     * @return The type of the Polarion Work Item field for the export.
     */
    public ExportFieldType getFieldType() {
      return fieldType;
    }

    /**
     * @return The ID of the Polarion Work Item field.
     */

    public String getFieldId() {
      return fieldId;
    }

    /**
     * @return The name of an existing TPT test case attribute. If <code>null</code>, a new test
     *         case attribute is created during import.
     */
    public String getAttribute() {
      return attribute;
    }

    /**
     * @param attribute
     *          The name of an existing TPT test case attribute. If <code>null</code>, a new test
     *          case attribute is created during import.
     */
    public void setAttribute(String attribute) {
      this.attribute = attribute;
    }
  }
}
