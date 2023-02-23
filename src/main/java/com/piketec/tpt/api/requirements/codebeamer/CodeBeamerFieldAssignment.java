package com.piketec.tpt.api.requirements.codebeamer;

import java.io.Serializable;

/**
 * Represents the assignment of a codeBeamer field to a test case attribute for the requirements
 * import or export of test cases.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public class CodeBeamerFieldAssignment implements Serializable {

  static final long serialVersionUID = 1L;

  private volatile String testCaseAttribute;

  private volatile String cbFieldName;

  /**
   * The constructor for the assignment of codeBeamer fields to test case attributes
   */
  public CodeBeamerFieldAssignment() {
  }

  /**
   * @return The test case attribute to store the codeBeamer IDs in TPT.
   */
  public String getTestCaseAttribute() {
    return testCaseAttribute;
  }

  /**
   * @param testCaseAttribute
   *          The test case attribute to store the codeBeamer IDs in TPT.
   */
  public void setTestCaseAttribute(String testCaseAttribute) {
    this.testCaseAttribute = testCaseAttribute;
  }

  /**
   * @return The name of the codeBeamer field.
   */
  public String getCbFieldName() {
    return cbFieldName;
  }

  /**
   * @param cbFieldName
   *          The name of the codeBeamer field.
   */
  public void setCbFieldName(String cbFieldName) {
    this.cbFieldName = cbFieldName;
  }
}
