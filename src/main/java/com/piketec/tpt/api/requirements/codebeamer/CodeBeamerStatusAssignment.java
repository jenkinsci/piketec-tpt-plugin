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

import java.io.Serializable;

/**
 * The assigment of a TPT status type to a codeBeamer status option.
 */
public class CodeBeamerStatusAssignment implements Serializable {

  static final long serialVersionUID = 1L;

  private String tptStatus;

  private int cbStatusId;

  private String cbStatusName;

  /**
   * The constructor for the assigment of a TPT status type to a codeBeamer status option.
   */
  public CodeBeamerStatusAssignment() {
  }

  /**
   * @return The TPT status type.
   */
  public String getTptStatus() {
    return tptStatus;
  }

  /**
   * @param tptStatus
   *          The TPT status type.
   */
  public void setTptStatus(String tptStatus) {
    this.tptStatus = tptStatus;
  }

  /**
   * @return The ID of the codeBeamer status.
   */
  public int getCbStatusId() {
    return cbStatusId;
  }

  /**
   * @param cbStatusId
   *          The ID of the codeBeamer status.
   */
  public void setCbStatusId(int cbStatusId) {
    this.cbStatusId = cbStatusId;
  }

  /**
   * @return The name of the codeBeamer status.
   */
  public String getCbStatusName() {
    return cbStatusName;
  }

  /**
   * @param cbStatusName
   *          The name of the codeBeamer status.
   */
  public void setCbStatusName(String cbStatusName) {
    this.cbStatusName = cbStatusName;
  }
}
