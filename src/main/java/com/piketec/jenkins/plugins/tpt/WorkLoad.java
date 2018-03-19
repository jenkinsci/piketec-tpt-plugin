package com.piketec.jenkins.plugins.tpt;

import hudson.FilePath;

public class WorkLoad {

  private String fileName;

  private String dataDir;

  private String reportDir;

  private String testSetName;

  private String exeConfig;

  private String testCases;

  private String exeId;

  private FilePath masterWorkspace;

  public WorkLoad(String fileName, String exeConfig, String dataDir, String reportDir,
                  String testSetName, String testCases, FilePath masterWorkspace) {

    this.fileName = fileName;
    this.exeConfig = exeConfig;
    this.dataDir = dataDir;
    this.reportDir = reportDir;
    this.testSetName = testSetName;
    this.testCases = testCases;
    this.exeId = Double.toString(Math.random());
    this.setMasterWorkspace(masterWorkspace);
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getDataDir() {
    return dataDir;
  }

  public void setDataDir(String dataDir) {
    this.dataDir = dataDir;
  }

  public String getReportDir() {
    return reportDir;
  }

  public void setReportDir(String reportDir) {
    this.reportDir = reportDir;
  }

  public String getTestSetName() {
    return testSetName;
  }

  public void setTestSetName(String testSetName) {
    this.testSetName = testSetName;
  }

  public String getExeConfig() {
    return exeConfig;
  }

  public void setExeConfig(String exeConfig) {
    this.exeConfig = exeConfig;
  }

  public String getTestCases() {
    return testCases;
  }

  public void setTestCases(String testCases) {
    this.testCases = testCases;
  }

  public FilePath getMasterWorkspace() {
    return masterWorkspace;
  }

  public void setMasterWorkspace(FilePath masterWorkspace) {
    this.masterWorkspace = masterWorkspace;
  }

}
