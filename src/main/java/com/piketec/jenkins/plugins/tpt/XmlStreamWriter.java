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
package com.piketec.jenkins.plugins.tpt;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import hudson.FilePath;
import javanet.staxutils.IndentingXMLStreamWriter;

class XmlStreamWriter {

  private XMLStreamWriter writer;

  private OutputStream os;

  private BufferedOutputStream bos;

  /**
   * Used by Publish.publishJUnitResults, write the XML file.
   * 
   * @param file
   *          The file write to
   * @throws XMLStreamException
   *           If the XML cannot be created
   * @throws FactoryConfigurationError
   *           if the XML write coudl not be created
   * @throws IOException
   *           if the file cannot be written
   * @throws InterruptedException
   *           if the job is cancelled
   */
  public void initalize(FilePath file)
      throws XMLStreamException, FactoryConfigurationError, IOException, InterruptedException {
    os = file.write();
    bos = new BufferedOutputStream(os);
    writer = new IndentingXMLStreamWriter(
        XMLOutputFactory.newInstance().createXMLStreamWriter(bos, "UTF-8"));
    writer.writeStartDocument("UTF-8", "1.0");
  }

  /**
   * Used by Publish.publishJUnitResults , it writes a test case in the XML
   * 
   * @param name
   *          The name of the JUnit test suite
   * @throws XMLStreamException
   *           If the XML cannot be created
   */
  public void writeTestsuite(String name) throws XMLStreamException {
    writer.writeStartElement("testsuite");
    writer.writeAttribute("name", name);
    writer.flush();
  }

  /**
   * Used by Publish.publishJUnitResults , it writes a test case in the XML
   * 
   * @param tptFileName
   *          the name of the TPT file the test originates from
   * @param tc
   *          the test case to write
   * @throws XMLStreamException
   *           If the XML cannot be created
   */
  public void writeTestcase(String tptFileName, Testcase tc) throws XMLStreamException {
    writer.writeStartElement("testcase");
    writer.writeAttribute("classname", getClassName(tptFileName, tc));
    writer.writeAttribute("name", tc.getQualifiedName());
    writer.writeAttribute("time", millis2secs(tc.getExecDuration()));
    writer.writeEndElement();
    writer.flush();
  }

  /**
   * Used by Publish.publishJUnitResults , it writes an error in the XML
   * 
   * @param tptFileName
   *          the name of the TPT file the test originates from
   * @param tc
   *          the test case to write
   * @param error
   *          the error message
   * @throws XMLStreamException
   *           If the XML cannot be created
   */
  public void writeTestcaseError(String tptFileName, Testcase tc, String error)
      throws XMLStreamException {
    writer.writeStartElement("testcase");
    writer.writeAttribute("classname", getClassName(tptFileName, tc));
    writer.writeAttribute("name", tc.getQualifiedName());
    writer.writeAttribute("time", millis2secs(tc.getExecDuration()));
    writer.writeStartElement("error");
    writer.writeAttribute("message", error);
    writer.writeEndElement();
    writer.flush();
    writer.writeEndElement();
    writer.flush();
  }

  /**
   * Get the full name of a test cases. The pattern is
   * $executionconfigname$.$platformname$.$testcasename$_$testcase-id$. If execution configuration
   * name or platform name are unavailable for some reaseon they are skipped.
   * 
   * @param tc
   *          The test case to get the full for
   * @return The full name
   */
  private String getClassName(String filename, Testcase tc) {
    StringBuilder testname = new StringBuilder(filename);
    String executionConfigName = tc.getExecutionConfigName();
    if (executionConfigName != null && !executionConfigName.isEmpty()) {
      testname.append('.').append(tc.getExecutionConfigName());
    }
    String platformName = tc.getPlatformName();
    if (platformName != null && !platformName.isEmpty()) {
      testname.append('.').append(tc.getPlatformName());
    }
    return testname.toString();
  }

  /**
   * Convert milliseconds to seconds
   * 
   * @param t
   * @return a String with the converted seconds
   */
  private String millis2secs(String t) {
    try {
      return Double.toString(Long.parseLong(t) / 1000.0);
    } catch (NumberFormatException e) {
      return t;
    }
  }

  /**
   * Writes the end element and closes the file.
   */
  public void close() {
    if (writer != null) {
      try {
        writer.writeEndElement();
        writer.flush();
        writer.writeEndDocument();
        writer.flush();
        writer.close();
        bos.close();
        os.close();
      } catch (XMLStreamException xe) {
        xe.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        writer = null;
        bos = null;
        os = null;
      }
    }
  }
}
