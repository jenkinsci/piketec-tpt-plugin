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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.piketec.jenkins.plugins.tpt.TptLogger;
import com.piketec.jenkins.plugins.tpt.TptResult;

/**
 * Parser for paring the TPT test_summary.xml file.
 * 
 * @author FInfantino, PikeTec GmbH
 */
class TPTReportSAXHandler extends DefaultHandler {

  private static final String TESTCASEINFORMATION = "TestcaseInformation";

  private static final String TESTCASE = "Testcase";

  private static final String GLOBAL_ASSESSLET = "GlobAssesslet";

  private TPTTestCase failedGlobalAssesslet = null;

  private ArrayList<TPTTestCase> failedTests;

  private TPTFile tptFile;

  private String reportDir;

  // Key id , Value TasCase Name
  private Map<String, String> nameAndId;

  private String executionConfiguration;

  private boolean isFileCorrupt;

  private TptLogger logger;

  /**
   * This class is an XML Parser for parsing the "test_summary.xml" and extract all the relevant
   * data from it.
   * 
   * @param tptFile
   *          The data container that will be enriched with the parsing results
   * @param failedTests
   *          The list that will be filled with all test cases that are not passed
   * @param reportDirOnRemote
   *          The report directory. Needed to resolve paths to report files
   * @param executionConfiguration
   *          The name of the execution configuration
   * @param isFileCorrupt
   *          if the test_summary.xml is corrupt the test cases were failed very early and we have
   *          to create the failure manually.
   */
  public TPTReportSAXHandler(TPTFile tptFile, ArrayList<TPTTestCase> failedTests,
                             String reportDirOnRemote, String executionConfiguration,
                             boolean isFileCorrupt, TptLogger logger) {
    this.reportDir = reportDirOnRemote;
    this.tptFile = tptFile;
    this.failedTests = failedTests;
    this.executionConfiguration = executionConfiguration;
    this.isFileCorrupt = isFileCorrupt;
    this.logger = logger;
    nameAndId = new HashMap<>();
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if (TESTCASE.equalsIgnoreCase(qName)) {
      String id = attributes.getValue("Id");
      String name = attributes.getValue("Name");
      nameAndId.put(id, name);
      // If the file is corrupt, then we are goint to set the test case to Error and add it to the
      // failed tests and fill the other parameters with default values. This is for the publisher.
      // If the file is corrupt, than we do not have a testsummary tag, so we are filling the things
      // here.
      if (isFileCorrupt) {
        tptFile.addResult(TptResult.EXECUTION_ERROR);
        TPTTestCase t = new TPTTestCase();
        t.setId(id);
        t.setExecutionDate(new Date().toString());
        t.setResult("ERROR");
        t.setFileName(tptFile.getFileName());
        t.setPlatform("Corrupted Platform");
        t.setReportFile("Corrupted File");
        t.setExecutionConfiguration(this.executionConfiguration);
        t.setTestCaseName(name);
        t.setJenkinsConfigId(tptFile.getJenkinsConfigId());
        failedTests.add(t);
      }
    }
    // setFailedTests
    if (TESTCASEINFORMATION.equalsIgnoreCase(qName)) {
      String resultString = attributes.getValue("Result");
      TptResult result = TptResult.fromString(resultString);
      tptFile.addResult(result);
      String id = attributes.getValue("Testcase");
      String executionDate = attributes.getValue("ExecutionDate");
      String reportFile = attributes.getValue("ReportFile");
      String fileName = tptFile.getFileName();
      if (result != TptResult.PASSED) {
        TPTTestCase t = new TPTTestCase();
        t.setId(id);
        t.setExecutionDate(executionDate);
        t.setResult(resultString);
        t.setFileName(fileName);
        t.setPlatform(getPlatformName(reportFile, reportDir));
        t.setReportFile(getLinkToFailedReport(reportFile, reportDir));
        t.setExecutionConfiguration(this.executionConfiguration);
        t.setTestCaseName(nameAndId.get(id));
        t.setJenkinsConfigId(tptFile.getJenkinsConfigId());
        failedTests.add(t);
      }
    }
    // set global assesslet results
    if (GLOBAL_ASSESSLET.equalsIgnoreCase(qName)) {
      String resultString = attributes.getValue("Result");
      if (resultString != null) {
        TptResult result = TptResult.fromString(resultString);
        // some global assesslets simply have no result (e.g. Regquirements Coverage), thats not
        // even "inconclusive"
        if (result != TptResult.PASSED) {
          if (failedGlobalAssesslet == null) {
            failedGlobalAssesslet = new TPTTestCase();
            failedGlobalAssesslet.setFileName(tptFile.getFileName());
            failedGlobalAssesslet.setExecutionConfiguration(this.executionConfiguration);
            failedGlobalAssesslet.setTestCaseName("global assesslet");
            failedGlobalAssesslet.setJenkinsConfigId(tptFile.getJenkinsConfigId());
            failedGlobalAssesslet.setReportFile("globalassessment.html");
            failedGlobalAssesslet.setResult(result.name());
            failedTests.add(failedGlobalAssesslet);
          } else {
            TptResult oldResult = TptResult.fromString(failedGlobalAssesslet.getResult());
            failedGlobalAssesslet.setResult(TptResult.worstCase(oldResult, result).name());
          }
        }
      }
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if ("GlobAssesslets".equalsIgnoreCase(qName)) {
      if (failedGlobalAssesslet != null) {
        tptFile.addResult(TptResult.fromString(failedGlobalAssesslet.getResult()));
      }
    }
  }

  /**
   * This method extracts the platform name from the report path. It is assumed, that both input
   * Strings represent absolute paths.
   */
  private String getPlatformName(String reportFile, String reportDir) {
    String relativePath = getLinkToFailedReport(reportFile, reportDir);
    if (relativePath.isEmpty()) {
      logger.error("Could not extract the platform name!");
      return "";
    }
    // This is handled with String-methods, because these are Windows Paths or Linux Path and if
    // we'd use path.relativize while Jenkins is running on a machine with another OS than the paths
    // are created on, it wouldn't work.
    int pathSepIdx = relativePath.indexOf("\\");
    if (pathSepIdx < 0) {
      pathSepIdx = relativePath.indexOf('/');
    }
    if (pathSepIdx >= 0) {
      return relativePath.substring(0, pathSepIdx);
    } else {
      return relativePath;
    }
  }

  /**
   * This method returns the relative path from the report file depending on the report directory.
   * It is assumed, that both input Strings represent absolute paths.
   */
  private String getLinkToFailedReport(String reportFile, String reportDir) {
    if (!reportFile.toLowerCase().startsWith(reportDir.toLowerCase())) {
      logger.error("Can't extract relative path to test case report. At least one of the "
          + "following paths is not an absolute path: reportFile = " + reportFile
          + ", reportDirectory = " + reportDir);
      return "";
    }
    if (reportFile.equalsIgnoreCase(reportDir)) {
      logger.error("Can't extract relative path to test case report. They are equal, "
          + "even though they shouldn't be: reportFile = " + reportFile + ", reportDirectory = "
          + reportDir);
      return "";
    }
    // This is handled with String-methods, because these are Windows Paths or Linux Path and if
    // we'd use path.relativize while Jenkins is running on a machine with another OS than the paths
    // are created on, it wouldn't work.
    String substring = reportFile.substring(reportDir.length());
    if (substring.startsWith("\\") || substring.startsWith("/")) {
      substring = substring.substring(1);
    }
    return substring;
  }

}
