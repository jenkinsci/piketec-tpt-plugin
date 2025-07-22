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
package com.piketec.tpt.api.requirements.reqif;

import java.io.Serializable;
import java.util.List;

import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.TestCaseAttribute;
import com.piketec.tpt.api.requirements.TestCasesExportSettings;

/**
 * The settings for the export of test cases to reqIF.<br>
 * <b>Please make sure to save the TPT file after exporting the test cases via
 * {@link Project#exportTestCases(TestCasesExportSettings)} to keep the UUIDs which were generated
 * for TPT objects during this export.</b>
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class ReqIfTestCasesExportSettings extends TestCasesExportSettings {

  static final long serialVersionUID = 1L;

  private final String exportFilePath;

  private List<ReqIFTypeForAttribute> attributeTypes = null;

  private boolean exportGeneratedSpec = false;

  /**
   * The constructor for the settings of the test cases export to reqIF.<br>
   * <b>Please make sure to save the TPT file after exporting the test cases via
   * {@link Project#exportTestCases(TestCasesExportSettings)} to keep the UUIDs which were generated
   * for TPT objects during this export.</b>
   * 
   * @param exportFilePath
   *          The path to the target file for the export.
   */
  public ReqIfTestCasesExportSettings(String exportFilePath) {
    this.exportFilePath = exportFilePath;
  }

  /**
   * @return The path to the target file for the export.
   */
  public String getExportFilePath() {
    return exportFilePath;
  }

  /**
   * @return The list of assignments of textual TPT test case attributes to the reqIF text data
   *         types.
   * 
   */
  public List<ReqIFTypeForAttribute> getAttributeTypes() {
    return attributeTypes;
  }

  /**
   * @param attributeTypes
   *          The list of assignments of textual TPT test case attributes to the reqIF text data
   *          types.
   */
  public void setAttributeTypes(List<ReqIFTypeForAttribute> attributeTypes) {
    this.attributeTypes = attributeTypes;
  }

  /**
   * @return Whether to export the "Generated Specification".
   */
  public boolean isExportGeneratedSpec() {
    return exportGeneratedSpec;
  }

  /**
   * @param exportGeneratedSpec
   *          Whether to export the "Generated Specification".
   */
  public void setExportGeneratedSpec(boolean exportGeneratedSpec) {
    this.exportGeneratedSpec = exportGeneratedSpec;
  }

  // -----------------------------------------------------------------------------

  /**
   * The assignment of a textual TPT test case attribute to the reqIF text data type.
   */
  public static class ReqIFTypeForAttribute implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * The choice of the reqIF text data type for textual attributes.
     */
    public enum ReqIFDefinitionType {
      /**
       * Use XHTML as reqIF text data type for the textual TPT test case attribute.
       */
      XHTML,
      /**
       * Use STRING as reqIF text data type for the textual TPT test case attribute.
       */
      STRING;
    }

    private final TestCaseAttribute attribute;

    private final ReqIFDefinitionType type;

    /**
     * The constructor for the assignment of a textual TPT test case attribute to the reqIF text
     * data type.
     * 
     * @param attribute
     *          The textual TPT test case attribute.
     * @param type
     *          The choice of the reqIF text data type for the attribute.
     */
    public ReqIFTypeForAttribute(TestCaseAttribute attribute, ReqIFDefinitionType type) {
      this.attribute = attribute;
      this.type = type;
    }

    /**
     * @return The TPT test case attribute.
     */
    public TestCaseAttribute getAttribute() {
      return attribute;
    }

    /**
     * @return The choice of the reqIF text data type the textual TPT test case attribute.
     */
    public ReqIFDefinitionType getType() {
      return type;
    }

  }

}
