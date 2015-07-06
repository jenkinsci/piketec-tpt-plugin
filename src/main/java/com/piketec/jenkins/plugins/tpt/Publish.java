/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 PikeTec GmbH
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

public final class Publish {

  //
  // private static final SimpleDateFormat DDMMYY_HHMMSS = new SimpleDateFormat("yyMMdd_HHmmss");
  //
  // public static void publishHTML(File testdir, File refdir, File reportdir, PrintStream logger)
  // throws IOException {
  // StringBuilder errors = new StringBuilder();
  // Map<Integer, Testcase> refdata = getIndexedTestcases(refdir, errors);
  // List<Testcase> testdata = getTestcases(testdir, errors);
  // int refdataSize = refdata.size();
  // int testdataSize = testdata.size();
  // Date now = new Date();
  // int errorcounter = 0;
  // HtmlWriter writer = new HtmlWriter();
  // writer.appendHtmlHeader(refdir.getParentFile().getName());
  // try {
  // if (!refdir.exists()) {
  // throw new IOException("Reference directory " + refdir + " does not exist");
  // }
  // if (!testdir.exists()) {
  // throw new IOException("Test data directory " + testdir + " does not exist");
  // }
  // if (refdata.isEmpty()) {
  // throw new IOException("No reference data found in " + refdir);
  // }
  // if (testdata.isEmpty()) {
  // throw new IOException("No test data found in " + testdir);
  // }
  // if (errors.length() > 0) {
  // throw new IOException("Error while reading testcase_information.xml files:\n" + errors);
  // }
  // for (Testcase tc : testdata) {
  // if (tc.getExecDate() != null) {
  // now = tc.getExecDate();
  // }
  // Testcase ref = refdata.get(tc.getID());
  // if (ref == null) {
  // writer.appendError(tc.getFilesystemPath(), tc.getFilesystemPath(),
  // "No matching REFERENCE data for test case with ID=" + tc.getID() + " found.");
  // errorcounter++;
  // } else if (!tc.equalTestcases(ref)) {
  // writer.appendTableLine(ref, tc);
  // errorcounter++;
  // } else {
  // // fine! not necessary to report something...
  // }
  // refdata.remove(tc.getID());
  // }
  // for (Testcase ref : refdata.values()) {
  // writer.appendError(ref.getFilesystemPath(), ref.getFilesystemPath(),
  // "No matching TEST data for test case with ID=" + ref.getID() + " found.");
  // errorcounter++;
  // }
  // writer.appendTableHeader(testdataSize, refdataSize, errorcounter);
  // } catch (IOException e) {
  // writer.appendTableHeader(0, 0, 0);
  // writer.appendError(refdir, testdir, e.getMessage());
  // errorcounter++;
  // }
  // writer.appendHtmlEnd();
  // if (errorcounter > 0) {
  // writer.toFile(new File(reportdir, testdir.getName() + "_" + DDMMYY_HHMMSS.format(now)
  // + "_Failure.html"));
  // logger.println("Failed with " + errorcounter + " errors.");
  // } else {
  // writer.toFile(new File(reportdir, testdir.getName() + "_" + DDMMYY_HHMMSS.format(now)
  // + "_Success.html"));
  // logger.println("Comparison successful without deviations.");
  // }
  // }
  //
  // //
  // -------------------------------------------------------------------------------------------------------------

  public static void publishJUnitResults(FilePath workspaceDir, FilePath reportFolder,
                                         JenkinsConfiguration ex, String pattern, TptLogger logger)
      throws IOException {
    XmlStreamWriter xmlPub = null;

    try {
      String classname = ex.getClassname();
      FilePath reportFile = new FilePath(reportFolder, ex.getReportName());
      File testDataText = ex.getTestdataDir();
      FilePath testDataDir =
          ((testDataText == null) || testDataText.toString().trim().isEmpty()) ? workspaceDir
              : new FilePath(workspaceDir, testDataText.toString());
      List<Testcase> testdata;
      xmlPub = new XmlStreamWriter();
      xmlPub.initalize(reportFile);
      xmlPub.writeTestsuite(classname);
      testdata = getTestcases(testDataDir, logger);

      if (!testdata.isEmpty()) {
        removeOlderResults(testdata);

        for (Testcase tc : testdata) {

          if (tc.getErrors().isEmpty() && "SUCCESS".equals(tc.getResult())) {
            xmlPub.writeTestcase(classname, tc.getQualifiedName(), tc.getExecDuration());
          } else {
            xmlPub.writeTestcaseError(classname, tc.getQualifiedName(), tc.getExecDuration(), tc
                .getLogEntries().toString());
          }
        }
      }
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

  private static void removeOlderResults(List<Testcase> testcases) {
    Date latestDate = testcases.get(0).getExecDate();
    Iterator<Testcase> testcaseIterator;

    for (Testcase testcase : testcases) {
      Date execDate = testcase.getExecDate();

      if (execDate.after(latestDate)) {
        latestDate = execDate;
      }
    }
    testcaseIterator = testcases.iterator();

    while (testcaseIterator.hasNext()) {

      if (testcaseIterator.next().getExecDate().before(latestDate)) {
        testcaseIterator.remove();
      }
    }
  }

  // -------------------------------------------------------------------------------------------------------------

  // /**
  // * ermittelt alle Testfaelle aus den "testcase_information.xml" files unterhalb des Ordners
  // * "rootdir" rekursiv als indizierte Map (index ist die ID des jeweiligen Testfalls). Wurden
  // Files
  // * gefunden, die nicht geladen werden koennen, wird dies als Fehler in "errors" eingetragen. Die
  // * Methode liefert selbst nie einen Fehler.
  // */
  // private static Map<Integer, Testcase> getIndexedTestcases(File rootdir, StringBuilder errors) {
  // Map<Integer, Testcase> res = new HashMap<Integer, Testcase>();
  // for (Testcase t : getTestcases(rootdir, errors)) {
  // res.put(t.getID(), t);
  // }
  // return res;
  // }

  /**
   * ermittelt alle Testfaelle aus den "testcase_information.xml" files unterhalb des Ordners
   * "rootdir" rekursiv. Wurden Files gefunden, die nicht geladen werden koennen, wird dies als
   * Fehler in "errors" eingetragen. Die Methode liefert selbst nie einen Fehler.
   * 
   * @throws InterruptedException
   * @throws IOException
   */
  private static List<Testcase> getTestcases(FilePath rootdir, TptLogger logger)
      throws IOException, InterruptedException {
    Collection<FilePath> files = new HashSet<FilePath>();
    List<Testcase> testcases;
    find(rootdir, "testcase_information.xml", files);
    testcases = new ArrayList<Testcase>(files.size());

    for (FilePath f : files) {

      try {
        Testcase tc = TestcaseParser.parseXml(f);
        testcases.add(tc);
      } catch (IOException e) {
        logger.error("[Error]: File \"" + f + "\": " + e.getMessage() + "\n\r");
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
