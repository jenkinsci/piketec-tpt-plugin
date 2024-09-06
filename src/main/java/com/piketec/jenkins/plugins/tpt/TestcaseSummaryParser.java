package com.piketec.jenkins.plugins.tpt;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import hudson.FilePath;

/**
 * This class is similar to the TestcaseParser. The difference is that this is used when an
 * Execution Error in TPT occurs and no testcase_infomation.xml are available. In such case the
 * test_summary.xml will be parsed and all testcases will be marked as execution errors.
 * 
 * Additonally global assesslet results are only available here.
 * 
 * 
 * @author FInfantino, PikeTec GmbH
 *
 */
public class TestcaseSummaryParser extends DefaultHandler {

  private String executionConfigName;

  private List<Testcase> testCases = new ArrayList<>();

  private TptResult globalAssessmentResult = null;

  /**
   * Parse an XML file to retrieve a testcase info instance (non null)
   * 
   * @param xmlFile
   *          test_summary.xml
   * @return A testcase, filled with the content of the xml-file. Result is allways Execution Error.
   * 
   * @throws IOException
   *           if the xml file cannot be read or has a wrong format
   * @throws InterruptedException
   *           If the Job is cancelled
   */
  public static TestCasesParseResult parseXml(FilePath xmlFile)
      throws IOException, InterruptedException {
    try (InputStream inputStream = xmlFile.read()) {
      TestcaseSummaryParser parser = new TestcaseSummaryParser();
      SAXParserFactory.newInstance().newSAXParser().parse(inputStream, parser);
      Testcase virtualGlobalAssessmentTestCase = null;
      if (parser.globalAssessmentResult != null) {
        virtualGlobalAssessmentTestCase = new Testcase();
        virtualGlobalAssessmentTestCase.setPlatformName("global assessment");
        virtualGlobalAssessmentTestCase.setName("result");
        virtualGlobalAssessmentTestCase.setExecutionConfigName(parser.executionConfigName);
        virtualGlobalAssessmentTestCase.setResult(parser.globalAssessmentResult);
      }
      return new TestCasesParseResult(parser.testCases, virtualGlobalAssessmentTestCase);
    } catch (ParserConfigurationException e) {
      throw new IOException("XML parser config error: " + e.getMessage());
    } catch (SAXException e) {
      throw new IOException("SAX error: " + e.getMessage());
    } catch (IOException e) {
      throw new IOException("I/O error: " + e.getMessage());
    }
  }

  // -------------------------------------

  private TestcaseSummaryParser() {
  }

  @Override
  public void startElement(String s, String s1, String elementName, Attributes attributes)
      throws SAXException {
    if (elementName.equals("Header")) {
      executionConfigName = attributes.getValue("ExecutionConfig");
    } else if (elementName.equalsIgnoreCase("Testcase")) {
      Testcase ti = new Testcase();
      ti.setName(attributes.getValue("Name"));
      try {
        ti.setID(Integer.parseInt(attributes.getValue("Id")));
      } catch (NumberFormatException e) {
        throw new SAXException("Could not parse ScenarioId", e);
      }
      ti.setResult(TptResult.EXECUTION_ERROR);
      ti.setExecDate(new Date());
      testCases.add(ti);
    } else if (elementName.equals("GlobAssesslet")) {
      String resultString = attributes.getValue("Result");
      // some global assesslets simply have no result (e.g. Regquirements Coverage), thats not even
      // "inconclusive"
      if (resultString != null) {
        globalAssessmentResult =
            TptResult.worstCase(globalAssessmentResult, TptResult.fromString(resultString));
      }
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
  }

  @Override
  public void characters(char[] ac, int i, int j) throws SAXException {
  }

  // ---------------------

}
