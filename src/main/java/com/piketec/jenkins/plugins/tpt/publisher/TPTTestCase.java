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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;

import org.apache.commons.io.FileUtils;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.FilePath;
import hudson.model.DirectoryBrowserSupport;

/**
 * This class is for the failed test. Objects from this class will be created when parsing the
 * "test_summary.xml". We get a list of failed tests (inconclusive, error or failed).
 * 
 * @author FInfantino, PikeTec GmbH
 *
 */
public class TPTTestCase extends InvisibleActionHostingHtml {

  private String result;

  private String id;

  private String executionDate;

  private String reportFile;

  private String fileName;

  private int failedSince;

  private String executionConfiguration;

  private String testCaseName;

  private String platform;

  /**
   * Creates a new TPTTestCase data container
   */
  public TPTTestCase() {
    setFailedSince(1);
  }

  /**
   * @return The result represented by as a String
   */
  public String getResult() {
    return result;
  }

  /**
   * Set the result represented as a String
   * 
   * @param result
   *          the result represented as a String
   */
  public void setResult(String result) {
    this.result = result;
  }

  /**
   * @return The test case id
   */
  public String getId() {
    return id;
  }

  /**
   * Set the test case id
   * 
   * @param id
   *          The test case id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * In the test_summary.xml file are the relative paths to the report listed for each test case.
   * 
   * @return The path to the report file
   */
  public String getReportFile() {
    return reportFile;
  }

  /**
   * Set the path to the report file In the test_summary.xml file are the relative paths to the
   * report listed for each test case.
   * 
   * @param reportFile
   *          The path to the report file
   */
  public void setReportFile(String reportFile) {
    this.reportFile = reportFile;
  }

  /**
   * @return The date of the TPT test execution
   */
  public String getExecutionDate() {
    return executionDate;
  }

  /**
   * Set the date of the TPT test execution
   * 
   * @param executionDate
   *          The date of the TPT test execution
   */
  public void setExecutionDate(String executionDate) {
    this.executionDate = executionDate;
  }

  /**
   * @return The name of the TPT file
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Set the name of the TPT file
   * 
   * @param fileName
   *          The name of the TPT file
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * @return The number of builds this test failed
   */
  public int getFailedSince() {
    return failedSince;
  }

  /**
   * Set the number of builds this test failed
   * 
   * @param failedSince
   *          The number of builds this test failed
   */
  public void setFailedSince(int failedSince) {
    this.failedSince = failedSince;
  }

  /**
   * @return The name of the executed execution configuration
   */
  public String getExecutionConfiguration() {
    return executionConfiguration;
  }

  /**
   * Set the name of the executed execution configuration
   * 
   * @param executionConfiguration
   *          The name of the executed execution configuration
   */
  public void setExecutionConfiguration(String executionConfiguration) {
    this.executionConfiguration = executionConfiguration;
  }

  /**
   * @return The name of the test case
   */
  public String getTestCaseName() {
    return testCaseName;
  }

  /**
   * Set the name of the test case
   * 
   * @param testCaseName
   *          The name of the test case
   */
  public void setTestCaseName(String testCaseName) {
    this.testCaseName = testCaseName;
  }

  /**
   * @return The name of the executed platform
   */
  public String getPlatform() {
    return platform;
  }

  /**
   * Set the name of the executed platform
   * 
   * @param platform
   *          The name of the executed platform
   */
  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public void doIndex(StaplerRequest req, StaplerResponse rsp)
      throws IOException, ServletException {
    File pathToHtml = pathToHtml();
    // TODO: This is approach is not working with parallel access
    // we simply write a failedTest.html where the page of the right frame is replaced
    File indexFromFile = new File(pathToHtml, "index.html");
    String indexFromFileAsString =
        FileUtils.readFileToString(indexFromFile, Charset.forName("UTF-8"));
    String failedHtmlAsString = indexFromFileAsString.replace("overview.html", reportFile);
    File failedHTML = new File(pathToHtml, "failedTest.html");
    FileUtils.writeStringToFile(failedHTML, failedHtmlAsString, Charset.forName("UTF-8"));
    DirectoryBrowserSupport dbs = new DirectoryBrowserSupport(this, new FilePath(pathToHtml),
        "TPT Report", "clipboard.png", false);
    dbs.setIndexFileName("failedTest.html");
    dbs.generateResponse(req, rsp, this);
  }

}
