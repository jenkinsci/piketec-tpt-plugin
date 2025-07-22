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
package com.piketec.tpt.api.requirements.codebeamer;

import java.io.Serializable;

/**
 * Represents the assignment of a codeBeamer field to a test case attribute for the requirements
 * import or export of test cases.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
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
