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
package com.piketec.tpt.api.requirements;

import java.io.Serializable;
import java.util.List;

import com.piketec.tpt.api.TestCaseAttribute;
import com.piketec.tpt.api.requirements.codebeamer.CodeBeamerTestCasesImportSettings;
import com.piketec.tpt.api.requirements.csv.CsvFileTestCasesImportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileTestCasesImportSettings;
import com.piketec.tpt.api.requirements.polarion.PolarionTestCasesImportSettings;

/**
 * The common settings of the requirement import of test cases.<br>
 * For the test cases import from a CSV file use {@link CsvFileTestCasesImportSettings}.<br>
 * For the test cases import from an Excel file use {@link ExcelFileTestCasesImportSettings}.<br>
 * For the test cases import from Polarion use {@link PolarionTestCasesImportSettings}.<br>
 * For the test cases import from codeBeamer use {@link CodeBeamerTestCasesImportSettings}.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public abstract class TestCasesImportSettings implements Serializable {

  static final long serialVersionUID = 1L;

  /**
   * String constant which represents the creation of a new test set as target of imported test
   * cases.
   */
  public static final String CREATE_NEW_TEST_SET = "-- Create new test set --";

  /**
   * The choice how the importer matches TPT test cases against the imported ones.
   */
  public static enum SynchronizationMethod {
    /**
     * All imported test cases will be created new in TPT. The importer will not look up existing
     * test cases for any matches.
     */
    ALL_NEW,
    /**
     * The importer updates existing test cases. Imported and existing test cases are matched via
     * its ID.
     */
    UPDATE
  }

  /**
   * The choice which property of TPT test cases the importer matches against the imported ID
   * values. Not relevant if the {@link #getSyncMethod() synchronization method} is
   * {@link SynchronizationMethod#ALL_NEW}.
   */
  public static enum SynchronizationProperty {
    /**
     * Imported ID values will be matched against the TPT-IDs of TPT test cases.
     */
    TPT_ID,
    /**
     * Imported ID values will be matched against the names of TPT test cases.
     */
    NAME,
    /**
     * Imported ID values will be matched against values of the specified ID attribute in TPT test
     * cases.
     * 
     * @see TestCasesImportSettings#getIdAttribute()
     */
    ATTRIBUTE
  }

  /**
   * The choice how the importer creates the TPT test case hierarchy.
   */
  public static enum TestCaseHierarchyCreationMethod {
    /**
     * The import will not change the test case hierarchy in TPT. New test cases will be imported as
     * a flat list.
     */
    FLAT,
    /**
     * A column with level information exist. The column may only contain non-negative numbers, none
     * less than the first or more than one greater than the previous one. With these numbers the
     * importer creates a test case hierarchy.
     */
    LEVEL_AND_TYPE,
    /**
     * A column with group path information exist. The importer uses these group paths to create the
     * hierarchy.
     */
    GROUP_PATH_ATTRIBUTE
  }

  /**
   * The choice whether the imported test case should be created in TPT as step list or as time
   * partition test case.
   */
  public enum TestCaseType {
    /**
     * The imported test case should be created in TPT as step list.
     */
    STEPLIST,
    /**
     * The imported test case should be created in TPT as time partition test case.
     */
    TIME_PARTITION
  }

  private SynchronizationMethod syncMethod = SynchronizationMethod.UPDATE;

  private SynchronizationProperty syncProperty = SynchronizationProperty.TPT_ID;

  private String idAttribute = null;

  private TestCaseHierarchyCreationMethod hierarchyCreationMethod =
      TestCaseHierarchyCreationMethod.FLAT;

  private String testSetName = null;

  private TestCaseType preferredTestCaseType = TestCaseType.TIME_PARTITION;

  private String statusTypeForChanged = "";

  @Deprecated
  private List<String> autoReviewAttributes = null;

  /**
   * The constructor for the common settings of the requirement import of test cases.<br>
   * For the test cases import from a CSV file use {@link CsvFileTestCasesImportSettings}.<br>
   * For the test cases import from an Excel file use {@link ExcelFileTestCasesImportSettings}.<br>
   * For the test cases import from Polarion use {@link PolarionTestCasesImportSettings}.<br>
   * For the test cases import from codeBeamer use {@link CodeBeamerTestCasesImportSettings}.
   */
  public TestCasesImportSettings() {
  }

  /**
   * @return The choice how the importer matches TPT test cases against the imported ones.
   */
  public SynchronizationMethod getSyncMethod() {
    return syncMethod;
  }

  /**
   * @param syncMethod
   *          The choice how the importer matches TPT test cases against the imported ones.
   */
  public void setSyncMethod(SynchronizationMethod syncMethod) {
    this.syncMethod = syncMethod;
  }

  /**
   * @return The choice which property of TPT test cases the importer matches against the imported
   *         ID values. Not relevant if the {@link #getSyncMethod() synchronization method} is
   *         {@link SynchronizationMethod#ALL_NEW}.
   */
  public SynchronizationProperty getSyncProperty() {
    return syncProperty;
  }

  /**
   * @param syncProperty
   *          The choice which property of TPT test cases the importer matches against the imported
   *          ID values. Not relevant if the {@link #getSyncMethod() synchronization method} is
   *          {@link SynchronizationMethod#ALL_NEW}.
   */
  public void setSyncProperty(SynchronizationProperty syncProperty) {
    this.syncProperty = syncProperty;
  }

  /**
   * @return The TPT test case attribute that contains the ID to match existing test cases against
   *         imported ones. Not relevant if the {@link #getSyncMethod() synchronization method} is
   *         {@link SynchronizationMethod#ALL_NEW} or the {@link #getSyncProperty() synchronization
   *         property} is {@link SynchronizationProperty#TPT_ID} or
   *         {@link SynchronizationProperty#NAME}. Must be neither <code>null</code> nor empty
   *         otherwise.
   * @see #setSyncMethod(SynchronizationMethod)
   */
  public String getIdAttribute() {
    return idAttribute;
  }

  /**
   * @param idAttribute
   *          The TPT test case attribute that contains the ID to match existing test cases against
   *          imported ones. Not relevant if the {@link #getSyncMethod() synchronization method} is
   *          {@link SynchronizationMethod#ALL_NEW} or the {@link #getSyncProperty() synchronization
   *          property} is {@link SynchronizationProperty#TPT_ID} or
   *          {@link SynchronizationProperty#NAME}. Must be neither <code>null</code> nor empty
   *          otherwise.
   * @see #setSyncMethod(SynchronizationMethod)
   */
  public void setIdAttribute(String idAttribute) {
    this.idAttribute = idAttribute;
  }

  /**
   * @return The choice how the importer creates the TPT test case hierarchy.
   */
  public TestCaseHierarchyCreationMethod getHierarchyCreationMethod() {
    return hierarchyCreationMethod;
  }

  /**
   * @param hierarchyCreationMethod
   *          The choice how the importer creates the TPT test case hierarchy.
   */
  public void setHierarchyCreationMethod(TestCaseHierarchyCreationMethod hierarchyCreationMethod) {
    this.hierarchyCreationMethod = hierarchyCreationMethod;
  }

  /**
   * @return Optional choice for the TPT test set as target of imported test cases.<br>
   *         If <code>null</code>, no test set is used and the target is the test cases tree.<br>
   *         If set to {@value #CREATE_NEW_TEST_SET} (use constant {@link #CREATE_NEW_TEST_SET}), a
   *         new test set is created.<br>
   *         Otherwise choose a name of an existing test set as target.
   */
  public String getTestSetName() {
    return testSetName;
  }

  /**
   * @param testSetName
   *          Optional choice for the TPT test set as target of imported test cases.<br>
   *          If <code>null</code>, no test set is used and the target is the test cases tree.<br>
   *          If set to {@value #CREATE_NEW_TEST_SET} (use constant {@link #CREATE_NEW_TEST_SET}), a
   *          new test set is created.<br>
   *          Otherwise choose a name of an existing test set as target.
   */
  public void setTestSetName(String testSetName) {
    this.testSetName = testSetName;
  }

  /**
   * @return The choice whether the imported test case should be created in TPT as step list or as
   *         time partition test case.
   */
  public TestCaseType getPreferredTestCaseType() {
    return preferredTestCaseType;
  }

  /**
   * @param preferredTestCaseType
   *          The choice whether the imported test case should be created in TPT as step list or as
   *          time partition test case.
   */
  public void setPreferredTestCaseType(TestCaseType preferredTestCaseType) {
    this.preferredTestCaseType = preferredTestCaseType;
  }

  /**
   * @return The name of the status type that is set for items that are changed during the import.
   *         Only status types that aren't stable are used.
   */
  public String getStatusTypeForChanged() {
    return statusTypeForChanged;
  }

  /**
   * @param statusType
   *          The name of the status type that is set for items that are changed during the import.
   *          Only status types that aren't stable are used.
   */
  public void setStatusTypeForChanged(String statusType) {
    this.statusTypeForChanged = statusType;
  }

  /**
   * @return Optional list of column names in the source file for which the automatic review of
   *         possible changes of a test case attribute should be enabled.
   *
   * @deprecated This list has no effect anymore. Use {@link TestCaseAttribute#isAutoReview()}
   *             instead.
   */
  @Deprecated
  public List<String> getAutoReviewAttributes() {
    return autoReviewAttributes;
  }

  /**
   * @param autoReviewAttributes
   *          Optional list of column names in the source file for which the automatic review of
   *          possible changes of a test case attribute should be enabled.
   *
   * @deprecated This list has no effect anymore. Use
   *             {@link TestCaseAttribute#setAutoReview(boolean)} instead.
   */
  @Deprecated
  public void setAutoReviewAttributes(List<String> autoReviewAttributes) {
    this.autoReviewAttributes = autoReviewAttributes;
  }

  // -----------------------------------------------------------------------------

  /**
   * The assignment of the name of an imported object type to the TPT type (test case vs. test case
   * group).
   */
  public static class TargetTypeAssignment implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * The choice for the TPT type (test case vs. test case group) of imported objects or whether
     * the object should be ignored.
     */
    public static enum TargetType {
      /**
       * The choice between test case and test case group as TPT type for the imported object is
       * done automatically by the object position in the hierarchy (objects without children are
       * imported as test cases, others as groups).
       */
      AUTO,
      /**
       * The imported object is a test case. Make sure that those objects do not have children.
       */
      TEST_CASE,
      /**
       * The imported object is a test case group.
       */
      GROUP,
      /**
       * The imported object will be excluded from the import.
       */
      IGNORE;
    }

    private String sourceType;

    private TargetType targetType;

    /**
     * The constructor for the assignment of the name of an imported object type to the TPT type
     * (test case vs. test case group).
     * 
     * @param sourceType
     *          The name of the imported object type.
     * @param targetType
     *          The choice for the TPT type (test case vs. test case group) of imported objects or
     *          whether the object should be ignored.
     */
    public TargetTypeAssignment(String sourceType, TargetType targetType) {
      this.setSourceType(sourceType);
      this.setTargetType(targetType);
    }

    /**
     * @return The name of the imported object type.
     */
    public String getSourceType() {
      return sourceType;
    }

    /**
     * @param sourceType
     *          The name of the imported object type.
     */
    public void setSourceType(String sourceType) {
      this.sourceType = sourceType;
    }

    /**
     * @return The choice for the TPT type (test case vs. test case group) of imported objects or
     *         whether the object should be ignored.
     */
    public TargetType getTargetType() {
      return targetType;
    }

    /**
     * @param targetType
     *          The choice for the TPT type (test case vs. test case group) of imported objects or
     *          whether the object should be ignored.
     */
    public void setTargetType(TargetType targetType) {
      this.targetType = targetType;
    }
  }

}
