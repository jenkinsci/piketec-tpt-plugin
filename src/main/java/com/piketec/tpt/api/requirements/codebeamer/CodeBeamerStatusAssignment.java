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
