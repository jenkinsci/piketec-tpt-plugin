package com.piketec.jenkins.plugins.tpt.publisher;

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
