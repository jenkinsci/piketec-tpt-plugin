/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2018 Synopsys Inc.
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
package com.piketec.jenkins.plugins.tpt.publisher;

import com.piketec.jenkins.plugins.tpt.TptResult;

/**
 * Metainformation of a executed TPT file.
 * 
 * @author FInfantino, Synopsys Inc.
 */
public class TPTFile extends InvisibleActionHostingHtml {

  private String fileName;

  private String configuration;

  private int passed;

  private int inconclusive;

  private int failed;

  private int executionError;

  private int total;

  /**
   * Data container for each TPT File in order to organize all the TPT Files.
   * 
   * @param fileName
   *          The name of the TPT file
   * @param configuration
   *          the name of the execution configuration
   * @param jenkinsConfigId
   *          The unique ID of the configuration to create unique paths
   */
  public TPTFile(String fileName, String configuration, String jenkinsConfigId) {
    this.fileName = fileName;
    this.configuration = configuration;
    setJenkinsConfigId(jenkinsConfigId);
  }

  /**
   * @return The name of the TPT file
   */
  public String getFileName() {
    return fileName;
  }

  void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * @return The name of the execution configuration
   */
  public String getConfiguration() {
    return configuration;
  }

  void setConfiguration(String configuration) {
    this.configuration = configuration;
  }

  /**
   * @return the number of passed tests
   */
  public int getPassed() {
    return passed;
  }

  void setPassed(int passed) {
    this.passed = passed;
  }

  /**
   * @return the number of inconclusive tests
   */
  public int getInconclusive() {
    return inconclusive;
  }

  void setInconclusive(int inconclusive) {
    this.inconclusive = inconclusive;
  }

  /**
   * @return the number of tests with execution errors
   */
  public int getExecutionError() {
    return executionError;
  }

  void setExecutionError(int executionError) {
    this.executionError = executionError;
  }

  /**
   * @return the number of failed tests
   */
  public int getFailed() {
    return failed;
  }

  void setFailed(int failed) {
    this.failed = failed;
  }

  /**
   * @return the total number of tests
   */
  public int getTotal() {
    return total;
  }

  void setTotal(int total) {
    this.total = total;
  }

  public void addResult(TptResult result) {
    total++;
    switch (result) {
      case EXECUTION_ERROR:
        this.executionError++;
        break;
      case FAILED:
        this.failed++;
        break;
      case INCONCLUSIVE:
        this.inconclusive++;
        break;
      case PASSED:
        this.passed++;
        break;
    }
  }

}
