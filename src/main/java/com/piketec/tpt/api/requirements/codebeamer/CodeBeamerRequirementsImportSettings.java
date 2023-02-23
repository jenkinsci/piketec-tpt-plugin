package com.piketec.tpt.api.requirements.codebeamer;

import java.util.List;

import com.piketec.tpt.api.Pair;
import com.piketec.tpt.api.Requirement;
import com.piketec.tpt.api.Requirement.RequirementType;
import com.piketec.tpt.api.requirements.RequirementsImportSettings;

/**
 * The settings for the requirements import from codeBeamer.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public class CodeBeamerRequirementsImportSettings extends RequirementsImportSettings {

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

  private List<Pair<String, Requirement.RequirementType>> typeAssignments = null;

  private boolean importLinksToTestCases = true;

  private List<String> attributeFields = null;

  /**
   * The contructor for the import settings of requirements from codeBeamer.
   */
  public CodeBeamerRequirementsImportSettings() {
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
   * @return The assignments of codeBeamer types (items in the codeBeamer field 'Types') to the
   *         requirement type in TPT. Items without an assigned TPT type are imported as
   *         {@link RequirementType#REQUIREMENT Requirement}.
   */
  public List<Pair<String, Requirement.RequirementType>> getTypeAssignments() {
    return typeAssignments;
  }

  /**
   * @param typeAssignments
   *          The assignments of codeBeamer types (items in the codeBeamer field 'Types') to the
   *          requirement type in TPT. Items without an assigned TPT type are imported as
   *          {@link RequirementType#REQUIREMENT Requirement}.
   */
  public void setTypeAssignments(List<Pair<String, Requirement.RequirementType>> typeAssignments) {
    this.typeAssignments = typeAssignments;
  }

  /**
   * @return Whether to import links to test cases, taking into account all downstream references
   *         that originate from test case trackers, reference the requirement in its
   *         subjects/verifies field and have already been imported to TPT. For performance reasons
   *         it is recommended <b>not</b> to import the links during requirements import, but during
   *         the test case import.
   */
  public boolean isImportLinksToTestCases() {
    return importLinksToTestCases;
  }

  /**
   * @param importLinksToTestCases
   *          Whether to import links to test cases, taking into account all downstream references
   *          that originate from test case trackers, reference the requirement in its
   *          subjects/verifies field and have already been imported to TPT. For performance reasons
   *          it is recommended <b>not</b> to import the links during requirements import, but
   *          during the test case import.
   */
  public void setImportLinksToTestCases(boolean importLinksToTestCases) {
    this.importLinksToTestCases = importLinksToTestCases;
  }

  /**
   * @return The List of codeBeamer field names to import their content as additional requirement
   *         attributes.
   */
  public List<String> getAttributeFields() {
    return attributeFields;
  }

  /**
   * @param attributeColumns
   *          The List of codeBeamer field names to import their content as additional requirement
   *          attributes.
   */
  public void setAttributeFields(List<String> attributeColumns) {
    this.attributeFields = attributeColumns;
  }

}
