package com.piketec.jenkins.plugins.tpt;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

  public void initalize(File file) throws XMLStreamException, FactoryConfigurationError,
      FileNotFoundException {
    os = new FileOutputStream(file);
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
