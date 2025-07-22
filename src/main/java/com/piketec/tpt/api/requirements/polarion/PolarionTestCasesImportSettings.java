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
package com.piketec.tpt.api.requirements.polarion;

import java.util.List;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.requirements.TestCasesImportSettings;

/**
 * The settings for the import of test cases from Polarion.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class PolarionTestCasesImportSettings extends TestCasesImportSettings {

  static final long serialVersionUID = 1L;

  /**
   * The choice for the import. Either import via Document by using the path to the document
   * followed by its ID (e.g., Testing/Test Specification), or import via Lucene by using a query in
   * Lucene syntax.
   */
  public enum PolarionImportMethod {
    /**
     * Import via Document by using the path to the document followed by its ID (e.g., Testing/Test
     * Specification)
     */
    DOCUMENT,
    /**
     * Import via Lucene by using a query in Lucene syntax.
     */
    LUCENE;
  }

  private String projectId = null;

  private PolarionImportMethod importMethod = PolarionImportMethod.DOCUMENT;

  private String documentPath = null;

  private String luceneQuery = null;

  private List<PolarionWorkItemFieldToTCAttributeAssignment> propertyAssignments = null;

  private List<TargetTypeAssignment> typeAssignments = null;

  private boolean importDescription = true;

  private List<String> linkRoles = null;

  private List<String> backLinkRoles = null;

  /**
   * The constructor for the settings of the test cases import from Polarion.
   * 
   * @param projectId
   *          The ID of the project in Polarion.
   */
  public PolarionTestCasesImportSettings(String projectId) {
    super();
    super.setSyncProperty(SynchronizationProperty.ATTRIBUTE);
    this.projectId = projectId;
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
   * @return The ID of the project in Polarion.
   */
  public String getProjectId() {
    return projectId;
  }

  /**
   * @param projectId
   *          The ID of the project in Polarion.
   */
  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  /**
   * @return The choice for the import. Either import via {@link PolarionImportMethod#DOCUMENT
   *         Document} by using the path to the document followed by its ID (e.g., Testing/Test
   *         Specification), or import via {@link PolarionImportMethod#LUCENE Lucene} by using a
   *         query in Lucene syntax.
   */
  public PolarionImportMethod getImportMethod() {
    return this.importMethod;
  }

  /**
   * @param importMethod
   *          The choice for the import. Either import via {@link PolarionImportMethod#DOCUMENT
   *          Document} by using the path to the document followed by its ID (e.g., Testing/Test
   *          Specification), or import via {@link PolarionImportMethod#LUCENE Lucene} by using a
   *          query in Lucene syntax.
   */
  public void setImportMethod(PolarionImportMethod importMethod) {
    this.importMethod = importMethod;
  }

  /**
   * @return The path to the document followed by its ID (e.g., Testing/Test Specification). Only
   *         relevant if import method is {@link PolarionImportMethod#DOCUMENT Document}.
   * @see #setImportMethod(PolarionImportMethod)
   */
  public String getDocumentPath() {
    return this.documentPath;
  }

  /**
   * @param documentPath
   *          The path to the document followed by its ID (e.g., Testing/Test Specification). Only
   *          relevant if import method is {@link PolarionImportMethod#DOCUMENT Document}.
   * @see #setImportMethod(PolarionImportMethod)
   */
  public void setDocumentPath(String documentPath) {
    this.documentPath = documentPath;
  }

  /**
   * @return If <code>null</code>, all work items of the project are imported. Otherwise a query in
   *         Lucene syntax to import a subset of the project work items (e.g., type:testcase). Only
   *         relevant if import method is {@link PolarionImportMethod#LUCENE Lucene}.
   * @see #setImportMethod(PolarionImportMethod)
   */
  public String getLuceneQuery() {
    return this.luceneQuery;
  }

  /**
   * @param luceneQuery
   *          If set to <code>null</code>, all work items of the project are imported. Otherwise set
   *          to a query in Lucene syntax to import a subset of the project work items (e.g.,
   *          type:testcase). Only relevant if import method is {@link PolarionImportMethod#LUCENE
   *          Lucene}.
   * @see #setImportMethod(PolarionImportMethod)
   */
  public void setLuceneQuery(String luceneQuery) {
    this.luceneQuery = luceneQuery;
  }

  /**
   * @return Whether to import the Polarion description to the test case description in TPT. Note
   *         that the description will be imported as plain text.
   */
  public boolean isImportDescription() {
    return importDescription;
  }

  /**
   * @param importDescription
   *          Whether to import the Polarion description to the test case description in TPT. Note
   *          that the description will be imported as plain text.
   */
  public void setImportDescription(boolean importDescription) {
    this.importDescription = importDescription;
  }

  /**
   * @return The list of assignments of the name of an imported object type to the TPT type (test
   *         case vs. test case group).
   */
  public List<TargetTypeAssignment> getTypeAssignments() {
    return typeAssignments;
  }

  /**
   * @param typeAssignments
   *          The list of assignments of the name of an imported object type to the TPT type (test
   *          case vs. test case group).
   */
  public void setTypeAssignments(List<TargetTypeAssignment> typeAssignments) {
    this.typeAssignments = typeAssignments;
  }

  /**
   * @return The list of assignments of Polarion Work Item fields to a TPT test case attributes.
   */
  public List<PolarionWorkItemFieldToTCAttributeAssignment> getPropertyAssignments() {
    return this.propertyAssignments;
  }

  /**
   * @param propertyAssignments
   *          The list of assignments of Polarion Work Item fields to a TPT test case attributes.
   */
  public void setPropertyAssignments(List<PolarionWorkItemFieldToTCAttributeAssignment> propertyAssignments) {
    this.propertyAssignments = propertyAssignments;
  }

  /**
   * @return The list of Polarion Work Item field IDs that are links. If no link is chosen, no links
   *         will be imported and the respective links in TPT will remain untouched.
   * @see #setBackLinkRoles(List)
   */
  public List<String> getLinkRoles() {
    return linkRoles;
  }

  /**
   * @param linkRoles
   *          The list of Polarion Work Item field IDs that are links. If no link and no backlink is
   *          chosen, no links will be imported and the respective links in TPT will remain
   *          untouched.
   * @see #setBackLinkRoles(List)
   */
  public void setLinkRoles(List<String> linkRoles) {
    this.linkRoles = linkRoles;
  }

  /**
   * @return The list of Polarion Work Item field IDs that are backlinks. If no backlink and no link
   *         is chosen, no links will be imported and the respective links in TPT will remain
   *         untouched.
   * @see #setLinkRoles(List)
   */
  public List<String> getBackLinkRoles() {
    return backLinkRoles;
  }

  /**
   * @param backLinkRoles
   *          The list of Polarion Work Item field IDs that are backlinks. If no backlink and no
   *          link is chosen, no links will be imported and the respective links in TPT will remain
   *          untouched.
   * @see #setLinkRoles(List)
   */
  public void setBackLinkRoles(List<String> backLinkRoles) {
    this.backLinkRoles = backLinkRoles;
  }

}
