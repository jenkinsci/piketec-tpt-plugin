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
package com.piketec.tpt.api.requirements.polarion;

import java.io.Serializable;

/**
 * The assignment of a Polarion Work Item field to a TPT test case attribute.
 *
 */
public class PolarionWorkItemFieldToTCAttributeAssignment implements Serializable {

  static final long serialVersionUID = 1L;

  /**
   * The type of the Polarion Work Item field.
   */
  public static enum WorkItemFieldType {
    /**
     * A standard Polarion Work Item field such as Title, Description, Author, Priority.
     */
    FIELD,
    /**
     * A custom defined Polarion Work Item field.
     */
    CUSTOM_DEFINED,
    /**
     * A custom undefined Polarion Work Item field.
     */
    CUSTOM_UNDEFINED
  }

  private final PolarionWorkItemFieldToTCAttributeAssignment.WorkItemFieldType fieldType;

  private final String fieldId;

  private String attribute;

  /**
   * The constructor for the assignment of a Polarion Work Item field to a TPT test case attribute.
   * 
   * @param fieldType
   *          The type of the Polarion Work Item field.
   * @param fieldId
   *          The ID of the Polarion Work Item field.
   */
  public PolarionWorkItemFieldToTCAttributeAssignment(PolarionWorkItemFieldToTCAttributeAssignment.WorkItemFieldType fieldType,
                                                      String fieldId) {
    this.fieldType = fieldType;
    this.fieldId = fieldId;
  }

  /**
   * @return The type of the Polarion Work Item field.
   */
  public PolarionWorkItemFieldToTCAttributeAssignment.WorkItemFieldType getFieldType() {
    return fieldType;
  }

  /**
   * @return The ID of the Polarion Work Item field.
   */
  public String getFieldId() {
    return fieldId;
  }

  /**
   * @return The name of an existing TPT test case attribute. If <code>null</code>, a new test case
   *         attribute is created during import.
   */
  public String getAttribute() {
    return attribute;
  }

  /**
   * @param attribute
   *          The name of an existing TPT test case attribute. If <code>null</code>, a new test case
   *          attribute is created during import.
   */
  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }
}
