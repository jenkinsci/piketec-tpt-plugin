/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2018 PikeTec GmbH
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

/**
 * This class is for the failed test. Objects from this class will be created , when by parsing the
 * "test_summary.xml" we get a failed test (inconclusive, error or failed).
 * 
 * @author FInfantino, PikeTec GmbH
 *
 */
public class TPTTestCase {

  private String result;

  private String id;

  private String executionDate;

  private String reportFile;

  private String fileName;

  private int buildHistoy;

  private String executionConfiguration;

  private String testCaseName;

  private String platform;

  public TPTTestCase() {
    setBuildHistoy(1);

  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getReportFile() {
    return reportFile;
  }

  public void setReportFile(String reportFile) {
    this.reportFile = reportFile;
  }

  public String getExecutionDate() {
    return executionDate;
  }

  public void setExecutionDate(String executionDate) {
    this.executionDate = executionDate;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public int getBuildHistoy() {
    return buildHistoy;
  }

  public void setBuildHistoy(int buildHistoy) {
    this.buildHistoy = buildHistoy;
  }

  public String getExecutionConfiguration() {
    return executionConfiguration;
  }

  public void setExecutionConfiguration(String executionConfiguration) {
    this.executionConfiguration = executionConfiguration;
  }

  public String getTestCaseName() {
    return testCaseName;
  }

  public void setTestCaseName(String testCaseName) {
    this.testCaseName = testCaseName;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

}
