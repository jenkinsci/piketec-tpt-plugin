package com.piketec.jenkins.plugins.tpt;

import hudson.FilePath;

public class WorkLoad {

  private String fileName;

  private String dataDir;

  private String reportDir;

  private String testSetName;

  private String exeConfig;

  private String testCases;

  private Object masterId;

  private FilePath masterWorkspace;

  public WorkLoad(String fileName, String exeConfig, String dataDir, String reportDir,
                  String testSetName, String testCases, FilePath masterWorkspace, Object masterId) {

    this.fileName = fileName;
    this.exeConfig = exeConfig;
    this.dataDir = dataDir;
    this.reportDir = reportDir;
    this.testSetName = testSetName;
    this.testCases = testCases;
    this.masterId = masterId;
    this.masterWorkspace = masterWorkspace;
  }

  public String getFileName() {
    return fileName;
  }

  public String getDataDir() {
    return dataDir;
  }

  public String getReportDir() {
    return reportDir;
  }

  public String getTestSetName() {
    return testSetName;
  }

  public String getExeConfig() {
    return exeConfig;
  }

  public String getTestCases() {
    return testCases;
  }

  public FilePath getMasterWorkspace() {
    return masterWorkspace;
  }

  public Object getMasterId() {
    return masterId;
  }

}
