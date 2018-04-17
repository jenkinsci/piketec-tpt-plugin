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

import java.io.IOException;
import java.util.ArrayList;
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

public final class Publish {

  /**
   * Publish the Junits results, it creates an XML file and write the results on it.
   * 
   * @param jenkinsconfiguration,
   *          to get the tpt file name
   * @param testdatadir
   *          , get the data to write on the xml
   * @param jUnitOutput
   *          dir, to know where to publish the results
   * @param logger,
   *          to display the information
   * @param loglevel,
   *          to check if there where any logs
   * @return the number of testcases .
   */
  public static int publishJUnitResults(JenkinsConfiguration ec, FilePath testDataDir,
                                        FilePath jUnitOutputDir, TptLogger logger,
                                        LogLevel logLevel)
      throws IOException {
    XmlStreamWriter xmlPub = null;

    try {
      String classname = FilenameUtils.getBaseName(ec.getTptFile());
      FilePath jUnitXMLFile = new FilePath(jUnitOutputDir,
          classname + "." + ec.getConfigurationWithUnderscore() + ".xml");
      xmlPub = new XmlStreamWriter();
      xmlPub.initalize(jUnitXMLFile);
      xmlPub.writeTestsuite(classname);
      List<Testcase> testdata = getTestcases(testDataDir, logger);
      logger.info("Found " + testdata.size() + " test results.");
      for (Testcase tc : testdata) {
        if (tc.getLogEntries(LogLevel.ERROR).isEmpty() && "SUCCESS".equals(tc.getResult())) {
          xmlPub.writeTestcase(classname, tc.getQualifiedName(), tc.getExecDuration());
        } else {
          StringBuilder log = new StringBuilder();
          for (LogEntry entry : tc.getLogEntries(logLevel)) {
            if (log.length() > 0) {
              log.append('\n');
            }
            log.append('[').append(entry.level.name()).append("] ").append(entry.message);
          }
          xmlPub.writeTestcaseError(classname, tc.getQualifiedName(), tc.getExecDuration(),
              log.toString());
        }
      }
      return testdata.size();
    } catch (XMLStreamException e) {
      throw new IOException("XML stream error: " + e.getMessage());
    } catch (FactoryConfigurationError e) {
      throw new IOException("XML configuration error: " + e.getMessage());
    } catch (InterruptedException ie) {
      throw new IOException("traverse test data directory failed: " + ie.getMessage());
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
   * @throws InterruptedException
   * @throws IOException
   */
  public static List<Testcase> getTestcases(FilePath rootdir, TptLogger logger)
      throws IOException, InterruptedException {
    Collection<FilePath> files = new HashSet<FilePath>();
    find(rootdir, "testcase_information.xml", files);
    List<Testcase> testcases = new ArrayList<Testcase>(files.size());

    for (FilePath f : files) {

      try {
        Testcase tc = TestcaseParser.parseXml(f);
        testcases.add(tc);
      } catch (IOException e) {
        logger.error("File \"" + f + "\": " + e.getMessage() + "\n\r");
      }
    }

    return testcases;
  }

  /**
   * find all files in directory "root" with file name "pattern" and stores them in collection
   * "files"
   * 
   * @throws InterruptedException
   * @throws IOException
   */
  private static void find(FilePath rootdir, String pattern, Collection<FilePath> files)
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
