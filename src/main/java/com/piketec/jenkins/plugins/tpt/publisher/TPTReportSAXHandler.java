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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TPTReportSAXHandler extends DefaultHandler {

  private static final String TESTCASEINFORMATION = "TestcaseInformation";

  private static final String TESTCASE = "Testcase";

  private ArrayList<TPTTestCase> failedTests;

  private TPTFile tptFile;

  private String reportDir;

  // Key id , Value TasCase Name
  private Map<String, String> nameAndId;

  private String executionConfiguration;

  // Key Result , Value how many of these
  private Map<String, Integer> resultCount;

  /**
   * This class is an XML Parser for parsing the "test_summary.xml" and extract all the relevant
   * data from it.
   * 
   * @param tptFile
   * @param failedTests
   * @param reportDirOnRemote
   * @param executionConfiguration
   */
  public TPTReportSAXHandler(TPTFile tptFile, ArrayList<TPTTestCase> failedTests,
                             String reportDirOnRemote, String executionConfiguration) {
    this.reportDir = reportDirOnRemote;
    this.tptFile = tptFile;
    this.failedTests = failedTests;
    this.executionConfiguration = executionConfiguration;
    nameAndId = new HashMap<>();
    resultCount = new HashMap<>();
    // Adding the start values
    resultCount.put("PASSED", 0);
    resultCount.put("INCONCLUSIVE", 0);
    resultCount.put("FAILED", 0);
    resultCount.put("ERROR", 0);
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if (TESTCASE.equalsIgnoreCase(qName)) {
      String id = attributes.getValue("Id");
      String name = attributes.getValue("Name");
      nameAndId.put(id, name);
    }
    // setFailedTests
    if (TESTCASEINFORMATION.equalsIgnoreCase(qName)) {
      String result = attributes.getValue("Result").toUpperCase();
      int currentResultValue = resultCount.get(result) + 1;
      resultCount.put(result, currentResultValue);
      String id = attributes.getValue("Testcase");
      String executionDate = attributes.getValue("ExecutionDate");
      String reportFile = attributes.getValue("ReportFile");
      String fileName = tptFile.getFileName();
      if (!result.equals("PASSED")) {
        TPTTestCase t = new TPTTestCase();
        t.setId(id);
        t.setExecutionDate(executionDate);
        t.setResult(result);
        t.setFileName(fileName);
        reportFile = getLinkToFailedReport(reportFile, reportDir);
        t.setPlatform(getPlatformName(reportFile));
        t.setReportFile(reportFile);
        t.setExecutionConfiguration(this.executionConfiguration);
        t.setTestCaseName(nameAndId.get(id));
        failedTests.add(t);
      }

      // set the result
      int error = resultCount.get("ERROR");
      int failed = resultCount.get("FAILED");
      int inconclusive = resultCount.get("INCONCLUSIVE");
      int passed = resultCount.get("PASSED");
      int total = error + failed + inconclusive + passed;
      tptFile.setExecutionError(error);
      tptFile.setFailed(failed);
      tptFile.setInconclusive(inconclusive);
      tptFile.setPassed(passed);
      tptFile.setTotal(total);
    }
  }

  private String getPlatformName(String reportFile) {
    int index = reportFile.indexOf("\\");
    if (index < 0) {
      return reportFile;
    }
    return reportFile.substring(0, index);
  }

  public String getLinkToFailedReport(String reportFile, String reportDir) {
    if (reportFile.equals("")) {
      return "";
    }
    Path path = Paths.get(reportDir);
    return path.relativize(Paths.get(reportFile)).toFile().getPath();

  }

}
