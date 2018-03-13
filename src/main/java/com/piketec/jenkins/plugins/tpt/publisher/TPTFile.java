package com.piketec.jenkins.plugins.tpt.publisher;

public class TPTFile {

  private String fileName;

  private String configuration;

  private int passed;

  private int inconclusive;

  private int failed;

  private int executionError;

  private int total;

  private String htmlPath;

  public TPTFile(String fileName, String configuration) {
    this.fileName = fileName;
    this.configuration = configuration;

  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public int getPassed() {
    return passed;
  }

  public void setPassed(int passed) {
    this.passed = passed;
  }

  public int getInconclusive() {
    return inconclusive;
  }

  public void setInconclusive(int inconclusive) {
    this.inconclusive = inconclusive;
  }

  public String getConfiguration() {
    return configuration;
  }

  public void setConfiguration(String configuration) {
    this.configuration = configuration;
  }

  public int getExecutionError() {
    return executionError;
  }

  public void setExecutionError(int executionError) {
    this.executionError = executionError;
  }

  public int getFailed() {
    return failed;
  }

  public void setFailed(int failed) {
    this.failed = failed;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String getHtmlPath() {
    return htmlPath;
  }

  public void setHtmlPath(String htmlPath) {
    this.htmlPath = htmlPath;
  }

}
