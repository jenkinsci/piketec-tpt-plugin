/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 PikeTec GmbH
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

import hudson.FilePath;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javanet.staxutils.IndentingXMLStreamWriter;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XmlStreamWriter {

  private XMLStreamWriter writer;

  private OutputStream os;

  private BufferedOutputStream bos;

  public void initalize(FilePath file) throws XMLStreamException, FactoryConfigurationError,
      IOException, InterruptedException {
    os = file.write();
    bos = new BufferedOutputStream(os);
    writer =
        new IndentingXMLStreamWriter(XMLOutputFactory.newInstance().createXMLStreamWriter(bos,
            "UTF-8"));
    writer.writeStartDocument("UTF-8", "1.0");
  }

  public void writeTestsuite(String name) throws XMLStreamException {
    writer.writeStartElement("testsuite");
    writer.writeAttribute("name", name);
    writer.flush();
  }

  public void writeTestcase(String classname, String testname, String timeMillis)
      throws XMLStreamException {
    writer.writeStartElement("testcase");
    writer.writeAttribute("classname", classname);
    writer.writeAttribute("name", testname);
    writer.writeAttribute("time", millis2secs(timeMillis));
    writer.writeEndElement();
    writer.flush();
  }

  public void writeTestcaseError(String classname, String testname, String timeMillis, String error)
      throws XMLStreamException {
    writer.writeStartElement("testcase");
    writer.writeAttribute("classname", classname);
    writer.writeAttribute("name", testname);
    writer.writeAttribute("time", millis2secs(timeMillis));
    writer.writeStartElement("error");
    writer.writeAttribute("message", error);
    writer.writeEndElement();
    writer.flush();
    writer.writeEndElement();
    writer.flush();
  }

  private String millis2secs(String t) {
    try {
      return Double.toString(Long.parseLong(t) / 1000.0);
    } catch (NumberFormatException e) {
      return t;
    }
  }

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
