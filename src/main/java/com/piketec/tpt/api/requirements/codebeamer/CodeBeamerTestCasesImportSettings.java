package com.piketec.tpt.api.requirements.codebeamer;

import java.util.List;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.requirements.TestCasesImportSettings;

/**
 * The settings for the import of test cases from codeBeamer.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public class CodeBeamerTestCasesImportSettings extends TestCasesImportSettings {

  static final long serialVersionUID = 1L;

  /**
   * The choice between using a tracker (i.e., a simple query) or an advanced cbQL query for the
   * import.
   */
  public static enum ImportQueryType {
    /**
     * use a simple query via a tracker
     */
    SIMPLE,
    /**
     * use an advanced cbQL query
     */
    ADVANCED
  }

  private ImportQueryType queryType = ImportQueryType.SIMPLE;

  private int projectId = -1;

  private int trackerId = -1;

  private int simpleBaselineId = -1;

  private int advancedBaselineId = -1;

  private String advancedQuery = "";

  private boolean unescapeWikiText = false;

  private boolean importLinksToRequirements = true;

  private List<CodeBeamerStatusAssignment> statusTypeAssignments = null;

  private List<CodeBeamerFieldAssignment> attributeAssignments = null;

  private boolean importTestStepsAsDocSteps = false;

  private boolean importItemUrlAsAttribute = false;

  private String urlAttribute = "codeBeamer URL";

  /**
   * The contructor for the import settings of test cases from codeBeamer.
   */
  public CodeBeamerTestCasesImportSettings() {
    super();
    setHierarchyCreationMethod(TestCaseHierarchyCreationMethod.LEVEL_AND_TYPE);
    super.setSyncProperty(SynchronizationProperty.ATTRIBUTE);
  }

  /**
   * Overwritten to throw an {@link ApiException}. Value must not be changed.
   */
  @Override
  public void setSyncProperty(SynchronizationProperty syncProperty) {
    throw new ApiException(
        "SynchronizationProperty of PolarionTestCasesImportSettings cannot be changed. "
            + "SynchronizationProperty.ATTRIBUTE must be set.");
  }

  /**
   * @return The choice between using a tracker (i.e., a {@link ImportQueryType#SIMPLE simple
   *         query}) or an {@link ImportQueryType#ADVANCED advanced cbQL query} for the import.
   */
  public ImportQueryType getQueryType() {
    return queryType;
  }

  /**
   * @param queryType
   *          The choice between using a tracker (i.e., a {@link ImportQueryType#SIMPLE simple
   *          query}) or an {@link ImportQueryType#ADVANCED advanced cbQL query} for the import.
   */
  public void setQueryType(ImportQueryType queryType) {
    this.queryType = queryType;
  }

  /**
   * @return The ID of the project if using a tracker (i.e., a {@link ImportQueryType#SIMPLE simple
   *         query}) for the import. Not relevant if using an {@link ImportQueryType#ADVANCED
   *         advanced cbQL query}.
   */
  public int getProjectId() {
    return projectId;
  }

  /**
   * @param projectId
   *          The ID of the project if using a tracker (i.e., a {@link ImportQueryType#SIMPLE simple
   *          query}) for the import. Not relevant if using an {@link ImportQueryType#ADVANCED
   *          advanced cbQL query}.
   */
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  /**
   * @return The ID of the tracker if using a {@link ImportQueryType#SIMPLE simple query} for the
   *         import. Not relevant if using an {@link ImportQueryType#ADVANCED advanced cbQL query}.
   */
  public int getTrackerId() {
    return trackerId;
  }

  /**
   * @param trackerId
   *          The ID of the tracker if using a {@link ImportQueryType#SIMPLE simple query} for the
   *          import. Not relevant if using an {@link ImportQueryType#ADVANCED advanced cbQL query}.
   */
  public void setTrackerId(int trackerId) {
    this.trackerId = trackerId;
  }

  /**
   * @return The ID of the baseline if using a {@link ImportQueryType#SIMPLE simple query} for the
   *         import. Not relevant if using an {@link ImportQueryType#ADVANCED advanced cbQL
   *         query}.<br>
   *         The HEAD baseline is represented by <code>-1</code>.
   */
  public int getSimpleBaselineId() {
    return simpleBaselineId;
  }

  /**
   * @param simpleBaselineId
   *          The ID of the baseline if using a {@link ImportQueryType#SIMPLE simple query} for the
   *          import. Not relevant if using an {@link ImportQueryType#ADVANCED advanced cbQL
   *          query}.<br>
   *          The HEAD baseline is represented by <code>-1</code>.
   */
  public void setSimpleBaselineId(int simpleBaselineId) {
    this.simpleBaselineId = simpleBaselineId;
  }

  /**
   * @return The ID of the baseline if using an {@link ImportQueryType#ADVANCED advanced cbQL query}
   *         for the import. Not relevant if using a {@link ImportQueryType#SIMPLE simple
   *         query}.<br>
   *         The HEAD baseline is represented by <code>-1</code>.
   */
  public int getAdvancedBaselineId() {
    return advancedBaselineId;
  }

  /**
   * @param advancedBaselineId
   *          The ID of the baseline if using an {@link ImportQueryType#ADVANCED advanced cbQL
   *          query}) for the import. Not relevant if using a {@link ImportQueryType#SIMPLE simple
   *          query}.<br>
   *          The HEAD baseline is represented by <code>-1</code>.
   */
  public void setAdvancedBaselineId(int advancedBaselineId) {
    this.advancedBaselineId = advancedBaselineId;
  }

  /**
   * @return The query using an {@link ImportQueryType#ADVANCED advanced cbQL query}) for the
   *         import. Not relevant if using a {@link ImportQueryType#SIMPLE simple query}.
   */
  public String getAdvancedQuery() {
    return advancedQuery;
  }

  /**
   * @param advancedQuery
   *          The query using an {@link ImportQueryType#ADVANCED advanced cbQL query}) for the
   *          import. Not relevant if using a {@link ImportQueryType#SIMPLE simple query}.
   */
  public void setAdvancedQuery(String advancedQuery) {
    this.advancedQuery = advancedQuery;
  }

  /**
   * @return Whether imported text from codeBeamer wiki text fields will be unescaped.
   */
  public boolean isUnescapeWikiText() {
    return unescapeWikiText;
  }

  /**
   * @param unescapeWikiText
   *          Whether imported text from codeBeamer wiki text fields will be unescaped.
   */
  public void setUnescapeWikiText(boolean unescapeWikiText) {
    this.unescapeWikiText = unescapeWikiText;
  }

  /**
   * @return Whether to import links to requirements, taking into account all referenced items in
   *         the subjects/verifies field that originate from requirement trackers and have already
   *         been imported to TPT.
   */
  public boolean isImportLinksToRequirements() {
    return importLinksToRequirements;
  }

  /**
   * @param importLinksToRequirements
   *          Whether to import links to requirements, taking into account all referenced items in
   *          the subjects/verifies field that originate from requirement trackers and have already
   *          been imported to TPT.
   */
  public void setImportLinksToRequirements(boolean importLinksToRequirements) {
    this.importLinksToRequirements = importLinksToRequirements;
  }

  /**
   * @return The assigments of TPT status types to codeBeamer status options.
   */
  public List<CodeBeamerStatusAssignment> getStatusTypeAssignments() {
    return statusTypeAssignments;
  }

  /**
   * @param statusTypeAssignments
   *          The assigments of TPT status types to codeBeamer status options.
   */
  public void setStatusTypeAssignments(List<CodeBeamerStatusAssignment> statusTypeAssignments) {
    this.statusTypeAssignments = statusTypeAssignments;
  }

  /**
   * @return The list of codeBeamer fields to be imported as test case attributes.
   */
  public List<CodeBeamerFieldAssignment> getAttributeAssignments() {
    return attributeAssignments;
  }

  /**
   * @param attributeAssignments
   *          The list of codeBeamer fields to be imported as test case attributes.
   */
  public void setAttributeAssignments(List<CodeBeamerFieldAssignment> attributeAssignments) {
    this.attributeAssignments = attributeAssignments;
  }

  /**
   * @return Whether to import test steps as documentation steps into step lists. Existing
   *         documentation steps are matched via the index ('#&lt;index&gt;' in the begin of the
   *         documenation step) and are overriden. If no matching documentation step is found a new
   *         one will be added to the end of the step list.
   */
  public boolean isImportTestStepsAsDocSteps() {
    return importTestStepsAsDocSteps;
  }

  /**
   * @param importTestStepsAsDocSteps
   *          Whether to import test steps as documentation steps into step lists. Existing
   *          documentation steps are matched via the index ('#&lt;index&gt;' in the begin of the
   *          documenation step) and are overriden. If no matching documentation step is found a new
   *          one will be added to the end of the step list.
   */
  public void setImportTestStepsAsDocSteps(boolean importTestStepsAsDocSteps) {
    this.importTestStepsAsDocSteps = importTestStepsAsDocSteps;
  }

  /**
   * @return Whether the URL of each tracker item will be imported into a test case attribute.
   */
  public boolean isImportItemUrlAsAttribute() {
    return importItemUrlAsAttribute;
  }

  /**
   * @param importItemUrlAsAttribute
   *          Whether the URL of each tracker item will be imported into a test case attribute.
   */
  public void setImportItemUrlAsAttribute(boolean importItemUrlAsAttribute) {
    this.importItemUrlAsAttribute = importItemUrlAsAttribute;
  }

  /**
   * @return The name of the test case attribute to store the URL of each tracker item if
   *         {@link #isImportItemUrlAsAttribute()} is <code>true</code> (not relevant if it is
   *         <code>false</code>).
   */
  public String getUrlAttribute() {
    return urlAttribute;
  }

  /**
   * @param urlAttribute
   *          The name of the test case attribute to store the URL of each tracker item if
   *          {@link #isImportItemUrlAsAttribute()} is <code>true</code> (not relevant if it is
   *          <code>false</code>).
   */
  public void setUrlAttribute(String urlAttribute) {
    this.urlAttribute = urlAttribute;
  }

  // ------------------------------------------------------------

}
