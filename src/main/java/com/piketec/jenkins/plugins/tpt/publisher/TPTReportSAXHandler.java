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

  private static final String EXECUTIONSUMMARY = "ExecutionSummary";

  private static final String TESTCASE = "Testcase";

  private ArrayList<TPTTestCase> failedTests;

  private TPTFile tptFile;

  private String reportDir;

  // Key id , Value TasCase Name
  private Map<String, String> nameAndId;

  private String executionConfiguration;

  // Key Result , Value how many of these
  private Map<String, Integer> resultCount;

  public TPTReportSAXHandler(TPTFile tptFile, ArrayList<TPTTestCase> failedTests, String reportDir,
                             String executionConfiguration) {
    this.reportDir = reportDir;
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
        reportFile = getLinkToFailedReport2(reportFile, reportDir);
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

    // if (EXECUTIONSUMMARY.equalsIgnoreCase(qName)) {
    // int error = resultCount.get("ERROR");
    // int failed = resultCount.get("FAILED");
    // int inconclusive = resultCount.get("INCONCLUSIVE");
    // int passed = resultCount.get("PASSED");
    // int total = error + failed + inconclusive + passed;
    //
    // tptFile.setExecutionError(error);
    // tptFile.setFailed(failed);
    // tptFile.setInconclusive(inconclusive);
    // tptFile.setPassed(passed);

    // tptFile.setTotal(total);
    // }

  }

  private String getPlatformName(String reportFile) {
    String platform = "";
    for (int i = 0; i < reportFile.length(); i++) {
      if (reportFile.charAt(i) == '\\') {
        return platform;
      }
      platform += reportFile.charAt(i);
    }
    return platform;
  }

  public String getLinkToFailedReport2(String reportFile, String reportDir) {
    if (reportFile.equals("")) {
      return "";
    }
    Path path = Paths.get(reportDir);
    return path.relativize(Paths.get(reportFile)).toFile().getPath();

  }

}
