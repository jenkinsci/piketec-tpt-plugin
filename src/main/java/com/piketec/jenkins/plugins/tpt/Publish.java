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
package com.piketec.jenkins.plugins.tpt;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.FilenameUtils;

import com.piketec.jenkins.plugins.tpt.TptLog.LogEntry;
import com.piketec.jenkins.plugins.tpt.TptLog.LogLevel;
import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

import hudson.FilePath;

/**
 * Class for helper methods to collect and tranform TPT test result.
 * 
 * @author jkuhnert, Synopsys Inc.
 */
public final class Publish {

  /**
   * Publish the Junits results, it creates an XML file and write the results on it.
   * 
   * @param jenkinsConfig
   *          The configuration to which the TPT test resuklt should be tranformed to JUnit
   * @param testDataDir
   *          The directory where TPT test data should be searched
   * @param jUnitOutputDir
   *          The directory where the transformed results should be written to.
   * @param logger
   *          to display the information
   * @param logLevel
   *          the threshold for the severity of the log messages
   * @return the number of testcases .
   * @throws IOException
   *           if an error occured while parsing TPT test data or writing the JUnit xml files
   * @throws InterruptedException
   *           If the job was interrupted
   */
  public static int publishJUnitResults(JenkinsConfiguration jenkinsConfig, FilePath testDataDir,
                                        FilePath jUnitOutputDir, TptLogger logger,
                                        LogLevel logLevel)
      throws IOException, InterruptedException {
    XmlStreamWriter xmlPub = null;

    try {
      String tptFileName = FilenameUtils.getBaseName(jenkinsConfig.getTptFile());
      FilePath jUnitXMLFile =
          new FilePath(jUnitOutputDir, jenkinsConfig.getId().replace(" ", "_") + ".xml");
      xmlPub = new XmlStreamWriter();
      xmlPub.initalize(jUnitXMLFile);
      xmlPub.writeTestsuite(tptFileName);
      logger.info("Collecting test cases");
      TestCasesParseResult testdata = getTestcases(testDataDir, logger);
      logger.info("Found " + testdata.testCases.size() + " test results.");
      for (Testcase tc : testdata.testCases) {
        if (tc.getLogEntries(LogLevel.ERROR).isEmpty() && TptResult.PASSED.equals(tc.getResult())) {
          xmlPub.writeTestcase(tptFileName, tc);
        } else {
          StringBuilder log = new StringBuilder();
          log.append(tc.getResult().name());
          for (LogEntry entry : tc.getLogEntries(logLevel)) {
            log.append('\n');
            log.append('[').append(entry.level.name()).append("] ").append(entry.message);
          }
          xmlPub.writeTestcaseError(tptFileName, tc, log.toString());
        }
      }
      if (testdata.virtualGlobalAssessletTestCase != null) {
        if (testdata.virtualGlobalAssessletTestCase.getResult() == TptResult.PASSED) {
          xmlPub.writeTestcase(tptFileName, testdata.virtualGlobalAssessletTestCase);
        } else {
          xmlPub.writeTestcaseError(tptFileName, testdata.virtualGlobalAssessletTestCase,
              testdata.virtualGlobalAssessletTestCase.getResult().name());
        }
      }
      return testdata.testCases.size();
    } catch (XMLStreamException e) {
      throw new IOException("XML stream error: " + e.getMessage());
    } catch (FactoryConfigurationError e) {
      throw new IOException("XML configuration error: " + e.getMessage());
    } finally {
      if (xmlPub != null) {
        xmlPub.close();
      }
    }
  }

  // -------------------------------------------------------------------------------------------------------------

  /**
   * Collects recursively all test cases by searching for "testcase_information.xml" files in
   * "rootdir". If a file could not be loaded, an error message will be printed.
   * 
   * @param testDataDir
   *          The directory where TPT test data should be searched
   * @param logger
   *          to display the information
   * @return The list of parsed TPT test cases
   * 
   * @throws IOException
   *           If an error occured while parsing TPT test data
   * @throws InterruptedException
   *           If the job was interrupted
   */
  public static TestCasesParseResult getTestcases(FilePath testDataDir, TptLogger logger)
      throws IOException, InterruptedException {
    Collection<FilePath> files = new HashSet<>();
    find(testDataDir, "testcase_information.xml", files);
    FilePath xmlFile = new FilePath(testDataDir, "test_summary.xml");
    if (!xmlFile.exists()) {
      logger.error("No \"test_summary.xml\" found.");
    }
    TestCasesParseResult testSummaryTestcases = TestcaseSummaryParser.parseXml(xmlFile);
    // Wenn es kein testcase_information.xml gibt bedeutet nicht, dass es keine Tests gibt. (Es
    // ist wegen den GenerateOverviewReport bug)
    // Es muss dann trotzdem eine test_summary.xml geben bei der testDataDir
    if (files.size() > 0) {
      // we use the testcase_information.xml files, if available. But we need to add the global
      // assesslet result from test summary xml.
      testSummaryTestcases.testCases.clear();
      for (FilePath f : files) {
        try {
          Testcase tc = TestcaseParser.parseXml(f);
          testSummaryTestcases.testCases.add(tc);
        } catch (IOException e) {
          logger.error("File \"" + f + "\": " + e.getMessage() + "\n\r");
        }
      }
    }
    return testSummaryTestcases;
  }

  /**
   * find all files in directory "root" with file name "pattern" and stores them in collection
   * "files"
   * 
   * @param rootdir
   *          The directory that should be searched
   * @param pattern
   *          The file name that has to be found.
   * @param files
   *          The collection to pupulate
   * 
   * @throws IOException
   *           If an error occured while parsing TPT test data
   * @throws InterruptedException
   *           If the job was interrupted
   */
  public static void find(FilePath rootdir, String pattern, Collection<FilePath> files)
      throws IOException, InterruptedException {
    if (rootdir.isDirectory()) {
      List<FilePath> children = rootdir.list();
      if (children != null) {
        for (FilePath child : children) {
          find(child, pattern, files);
        }
      }
    } else if (rootdir.exists() && (!rootdir.isDirectory())
        && rootdir.getName().equalsIgnoreCase(pattern)) {
      files.add(rootdir);
    }
  }
}
