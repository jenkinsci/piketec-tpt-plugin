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
package com.piketec.tpt.api.requirements.codebeamer;

import java.util.List;

import com.piketec.tpt.api.requirements.TestCasesExportSettings;

/**
 * The settings for the export of test cases to codeBeamer.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class CodeBeamerTestCasesExportSettings extends TestCasesExportSettings {

  static final long serialVersionUID = 1L;

  /**
   * The choice for exporting text to codeBeamer Wiki text fields.
   */
  public static enum CodeBeamerExportToWikiTextMethod {

    /**
     * Export text to codeBeamer Wiki text fields as it is.
     */
    NONE,
    /**
     * Export text to codeBeamer Wiki text fields as preformatted text.
     */
    PREFORMATTED,
    /**
     * Export text to codeBeamer Wiki text fields by escaping special characters.
     */
    ESCAPE;
  }

  private String testset = null;

  private String attributeWithCodeBeamerIDs = "codeBeamer ID";

  private boolean exportHierarchy = true;

  private boolean exportLinksToRequirements = false;

  private boolean exportResults = false;

  private List<CodeBeamerFieldAssignment> attributeAssignments = null;

  private String tptIdField = null;

  private int projectId = -1;

  private int trackerId = -1;

  private int testRunTrackerId = -1;

  private boolean exportDocStepsAsTestSteps = false;

  private CodeBeamerExportToWikiTextMethod exportToWikiTextMethod =
      CodeBeamerExportToWikiTextMethod.NONE;

  private boolean deleteUnknownItems = false;

  /**
   * The constructor for the export settings of test cases to codeBeamer.
   */
  public CodeBeamerTestCasesExportSettings() {
  }

  /**
   * @return testset The test set to export a subset of TPT test cases. If <code>null</code>, all
   *         test cases are exported.
   */
  public String getTestset() {
    return testset;
  }

  /**
   * @param testset
   *          The test set to export a subset of TPT test cases. If <code>null</code>, all test
   *          cases are exported.
   */
  public void setTestset(String testset) {
    this.testset = testset;
  }

  /**
   * @return The TPT test case attribute which contains the codeBeamer IDs. It will be used to
   *         update test cases which already are known in codeBeamer. Furthermore the IDs of newly
   *         created work items will be applied to the respective TPT test cases.
   */
  public String getAttributeWithCodeBeamerIDs() {
    return attributeWithCodeBeamerIDs;
  }

  /**
   * @param attributeWithCodeBeamerIDs
   *          The TPT test case attribute which contains the codeBeamer IDs. It will be used to
   *          update test cases which already are known in codeBeamer. Furthermore the IDs of newly
   *          created work items will be applied to the respective TPT test cases.
   */
  public void setAttributeWithCodeBeamerIDs(String attributeWithCodeBeamerIDs) {
    this.attributeWithCodeBeamerIDs = attributeWithCodeBeamerIDs;
  }

  /**
   * @return <code>true</code>, if the hierarchy of test cases is exported. <code>false</code>, if
   *         all test cases are exported as a flat list.
   */
  public boolean isExportHierarchy() {
    return exportHierarchy;
  }

  /**
   * @param exportHierarchy
   *          If <code>true</code>, the hierarchy of test cases is exported. If <code>false</code>,
   *          all test cases are exported as a flat list.
   */
  public void setExportHierarchy(boolean exportHierarchy) {
    this.exportHierarchy = exportHierarchy;
  }

  /**
   * @return If <code>true</code>, the links from test cases to requirements are exported to
   *         codeBeamer. Existing links in codeBeamer are overwritten.<br>
   *         If <code>false</code>, the links from test case to requirements are not exported.
   *         Existing links in codeBeamer remain untouched.
   */
  public boolean isExportLinksToRequirements() {
    return exportLinksToRequirements;
  }

  /**
   * @param exportLinksToRequirements
   *          If <code>true</code>, the links from test cases to requirements are exported to
   *          codeBeamer. Existing links in codeBeamer are overwritten.<br>
   *          If <code>false</code>, the links from test case to requirements are not exported.
   *          Existing links in codeBeamer remain untouched.
   */
  public void setExportLinksToRequirements(boolean exportLinksToRequirements) {
    this.exportLinksToRequirements = exportLinksToRequirements;
  }

  /**
   * @return If <code>true</code>, last known results are exported.
   */
  public boolean isExportResults() {
    return exportResults;
  }

  /**
   * @param exportResults
   *          If set to <code>true</code>, last known results are exported. A test run tracker must
   *          be selected in this case.
   * @see #setTestRunTrackerId(int)
   */
  public void setExportResults(boolean exportResults) {
    this.exportResults = exportResults;
  }

  /**
   * @return the list of assignments of a codeBeamer field to a test case attribute.
   */
  public List<CodeBeamerFieldAssignment> getAttributeAssignments() {
    return attributeAssignments;
  }

  /**
   * @param attributeAssignments
   *          the list of assignments of a codeBeamer field to a test case attribute.
   */
  public void setAttributeAssignments(List<CodeBeamerFieldAssignment> attributeAssignments) {
    this.attributeAssignments = attributeAssignments;
  }

  /**
   * @return The name of a codeBeamer field (of type integer or text) to which the TPT test case IDs
   *         will be written. If <code>null</code>, TPT test case IDs are not exported.<br>
   *         Some TPT projects use prefixed test case IDs, which cannot be written to integer fields
   *         in codeBeamer. Use a text field in this case.
   */
  public String getTptIdField() {
    return tptIdField;
  }

  /**
   * @param tptIdField
   *          The name of a codeBeamer field (of type integer or text) to which the TPT test case
   *          IDs will be written. If <code>null</code>, TPT test case IDs are not exported.<br>
   *          Some TPT projects use prefixed test case IDs, which cannot be written to integer
   *          fields in codeBeamer. Use a text field in this case.
   * 
   */
  public void setTptIdField(String tptIdField) {
    this.tptIdField = tptIdField;
  }

  /**
   * @return The ID of the project in codeBeamer.
   */
  public int getProjectId() {
    return projectId;
  }

  /**
   * @param projectId
   *          The ID of the project in codeBeamer.
   */
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  /**
   * @return The ID of the tracker from the project in codeBeamer. The test cases are exported into
   *         this tracker.
   */
  public int getTrackerId() {
    return trackerId;
  }

  /**
   * @param trackerId
   *          The ID of the tracker from the project in codeBeamer. The test cases are exported into
   *          this tracker.
   */
  public void setTrackerId(int trackerId) {
    this.trackerId = trackerId;
  }

  /**
   * @return The test run tracker from codeBeamer to which the test results are exported (if
   *         enabled).
   * @see #setExportResults(boolean)
   */
  public int getTestRunTrackerId() {
    return testRunTrackerId;
  }

  /**
   * @param testRunTrackerId
   *          The test run tracker from codeBeamer to which the test results are exported (if
   *          enabled).
   * @see #setExportResults(boolean)
   */
  public void setTestRunTrackerId(int testRunTrackerId) {
    this.testRunTrackerId = testRunTrackerId;
  }

  /**
   * @return Whether the documentation steps are exported as test steps to codeBeamer. If
   *         <code>true</code>, existing test steps in codeBeamer are overwritten. If the test step
   *         table has more than the three standard columns (Critical, Action and Expected Result),
   *         data in the other columns can be lost. This is only applied to step lists and
   *         documentation steps that have the correct syntax. The syntax looks as follows:
   *         #&lt;index&gt; &lt;!&gt;(if critical) &lt;action&gt; : &lt;expected result&gt;.
   */
  public boolean isExportDocStepsAsTestSteps() {
    return exportDocStepsAsTestSteps;
  }

  /**
   * @param exportDocStepsAsTestSteps
   *          If set to <code>true</code>, the documentation steps are exported as test steps to
   *          codeBeamer (existing test steps in codeBeamer are overwritten). If the test step table
   *          has more than the three standard columns (Critical, Action and Expected Result), data
   *          in the other columns can be lost. This is only applied to step lists and documentation
   *          steps that have the correct syntax. The syntax looks as follows: #&lt;index&gt;
   *          &lt;!&gt;(if critical) &lt;action&gt; : &lt;expected result&gt;.
   */
  public void setExportDocStepsAsTestSteps(boolean exportDocStepsAsTestSteps) {
    this.exportDocStepsAsTestSteps = exportDocStepsAsTestSteps;
  }

  /**
   * @return the choice for exporting to codeBeamer Wiki text fields
   */
  public CodeBeamerExportToWikiTextMethod getExportToWikiTextMethod() {
    return exportToWikiTextMethod;
  }

  /**
   * @param exportToWikiTextMethod
   *          the choice for exporting to codeBeamer Wiki text fields
   */
  public void setExportToWikiTextMethod(CodeBeamerExportToWikiTextMethod exportToWikiTextMethod) {
    this.exportToWikiTextMethod = exportToWikiTextMethod;
  }

  /**
   * @return Whether unknown tracker items in codeBeamer, i.e. items that are not exported during
   *         this export, will be moved to trash.
   */
  public boolean isDeleteUnknownItems() {
    return deleteUnknownItems;
  }

  /**
   * @param deleteUnknownItems
   *          If set to <code>true</code>, unknown tracker items in codeBeamer, i.e. items that are
   *          not exported during this export, will be moved to trash.
   */
  public void setDeleteUnknownItems(boolean deleteUnknownItems) {
    this.deleteUnknownItems = deleteUnknownItems;
  }

}
