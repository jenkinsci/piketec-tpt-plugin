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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.xml.sax.SAXException;

import com.piketec.jenkins.plugins.tpt.InvisibleActionTPTExecution;
import com.piketec.jenkins.plugins.tpt.Publish;
import com.piketec.jenkins.plugins.tpt.TptLogger;
import com.piketec.jenkins.plugins.tpt.Utils;

import hudson.AbortException;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import jenkins.tasks.SimpleBuildStep;

/**
 * The post build action to publish the TPT test results in Jenkins
 * 
 * @author FInfantino, PikeTec GmbH
 */
public class TPTReportPublisher extends Recorder implements SimpleBuildStep {

  @DataBoundConstructor
  public TPTReportPublisher() {
    // NOP
  }

  /**
   * Creates the directories on the build directory, loops over all JenkinsConfigurations and
   * extract from each one the data from the "test_summary.xml". Then it sets the failed tests and
   * finally it creates the TPTReportPage action . (Thats the one who is displaying the files,
   * piechart and failed tests)
   */
  @Override
  public void perform(Run< ? , ? > build, FilePath workspace, EnvVars env, Launcher launcher,
                      TaskListener listener)
      throws InterruptedException, IOException {

    TptLogger logger = new TptLogger(listener.getLogger());
    logger.info("Starting Post Build Action \"TPT Report\"");

    List<InvisibleActionTPTExecution> jenkinsConfigurationsToPublishForThisWorkspace =
        Utils.getInvisibleActionTPTExecutions(build);

    if (jenkinsConfigurationsToPublishForThisWorkspace.isEmpty()) {
      throw new AbortException("Nothing to publish");
    }

    ArrayList<FilePath> uniqueTestDataDir = new ArrayList<>();
    ArrayList<FilePath> uniqueReportDataDir = new ArrayList<>();
    ArrayList<TPTFile> tptFiles = new ArrayList<>();
    ArrayList<TPTTestCase> failedTests = new ArrayList<>();
    // global file in Build
    File piketectptDir = TPTReportUtils.getPikeTecDir(build);
    if (!piketectptDir.exists()) {
      if (!piketectptDir.mkdirs()) {
        throw new IOException(
            "Could not create directory \"" + piketectptDir.getAbsolutePath() + "\"");
      }
    }

    for (InvisibleActionTPTExecution cfg : jenkinsConfigurationsToPublishForThisWorkspace) {
      // make file in build and copy report dir
      File dirExConfig = TPTReportUtils.getReportDir(piketectptDir, cfg.getId());
      if (!dirExConfig.mkdirs()) {
        throw new IOException(
            "Could not create directory \"" + dirExConfig.getAbsolutePath() + "\"");
      }
      FilePath reportDir = new FilePath(workspace, cfg.getReportDir());
      FilePath testDataDir = new FilePath(workspace, cfg.getTestDataDir());
      String tptFileName = FilenameUtils.getBaseName(cfg.getTptFile());
      if (reportDir.exists()) {
        Utils.copyRecursive(reportDir, new FilePath(dirExConfig), logger);
      }
      FilePath reportXML = new FilePath(testDataDir, "test_summary.xml");
      if (reportXML.exists()) {
        TPTFile newTPTFile = new TPTFile(tptFileName, cfg.getConfiguration(), cfg.getId());
        // get the remote path, then cut the path and get just what is needed (the last part),
        // see getLinkToFailedReport() in TPTReportSAXHandler.
        // Because of the GenerateOverviewReport bug, we should check if there are
        // testcase_information.xml, if there are not any of them, pass a flag to the parser.
        boolean isFileCorrupt = checkForTestCaseInformation(testDataDir);
        parse(reportXML, newTPTFile, failedTests, reportDir.getRemote(), cfg.getConfiguration(),
            logger, isFileCorrupt);
        tptFiles.add(newTPTFile);
      } else {
        logger.error("There is no test_summary.xml for the file \"" + tptFileName
            + "\". It won't be published ");
        continue;
      }
      // Check if the Testdata dir and the Report are unique, otherwise throw an exception
      if (uniqueReportDataDir.contains(reportDir) || uniqueTestDataDir.contains(testDataDir)) {
        throw new IOException("The directory \"" + cfg.getReportDir() + "\" or the directoy \""
            + cfg.getTestDataDir() + "\" is already used, please choose another one");
      }
      uniqueReportDataDir.add(reportDir);
      uniqueTestDataDir.add(testDataDir);
    }

    // Failed Since. Look up test in previous build. If failed there. extract failed since
    // information and add 1
    Run< ? , ? > lastSuccBuild = build.getPreviousNotFailedBuild();
    // the tpt report of last successful build
    TPTReportPage lastTptFilesAction =
        (lastSuccBuild == null) ? null : lastSuccBuild.getAction(TPTReportPage.class);
    if (lastTptFilesAction != null) {
      HashMap<FailedTestKey, TPTTestCase> prevFailed = new HashMap<>();
      for (TPTTestCase tptTestCase : lastTptFilesAction.getFailedTests()) {
        prevFailed.put(new FailedTestKey(tptTestCase.getId(), tptTestCase.getFileName(),
            tptTestCase.getExecutionConfiguration(), tptTestCase.getPlatform()), tptTestCase);
      }
      for (TPTTestCase t : failedTests) {
        String id = t.getId();
        String fileName = t.getFileName();
        String exeConfig = t.getExecutionConfiguration();
        String platform = t.getPlatform();
        FailedTestKey ftk = new FailedTestKey(id, fileName, exeConfig, platform);
        if (prevFailed.containsKey(ftk)) {
          t.setFailedSince(prevFailed.get(ftk).getFailedSince() + 1);
        }
      }
    }
    TPTReportPage filesAction = new TPTReportPage(build, failedTests.toArray(TPTTestCase[]::new),
        tptFiles.toArray(TPTFile[]::new));
    filesAction.createGraph();
    build.addAction(filesAction);
    listener.getLogger().println("Finished Post Build Action");
    if (!failedTests.isEmpty()) {
      build.setResult(Result.UNSTABLE);
    }
  }

  private boolean checkForTestCaseInformation(FilePath testDataDir)
      throws IOException, InterruptedException {
    Collection<FilePath> files = new HashSet<>();
    Publish.find(testDataDir, "testcase_information.xml", files);
    boolean containsTestcaseInformation = false;
    FilePath summaryXMl = new FilePath(testDataDir, "test_summary.xml");
    if (summaryXMl.exists()) {
      try (InputStream inputStream = summaryXMl.read()) {
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        containsTestcaseInformation = result.contains("TestcaseInformation");
      }
    }
    // File is only corrupt, when there arent any testcase_information and it does not contain any
    // testcaseinformation tag.
    return files.size() == 0 && !containsTestcaseInformation;
  }

  /**
   * Xml SAXparser
   * 
   * @param xmlFile
   * @param tptFile
   * @param failedTests
   * @param reportDirOnRemote
   * @param executionConfiguration
   * @param isFileCorrupt
   * @throws InterruptedException
   */
  private void parse(FilePath xmlFile, TPTFile tptFile, ArrayList<TPTTestCase> failedTests,
                     String reportDirOnRemote, String executionConfiguration, TptLogger logger,
                     boolean isFileCorrupt)
      throws InterruptedException {
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

    try {
      SAXParser saxParser = saxParserFactory.newSAXParser();
      TPTReportSAXHandler handler = new TPTReportSAXHandler(tptFile, failedTests, reportDirOnRemote,
          executionConfiguration, isFileCorrupt, logger);
      try (InputStream inputStream = xmlFile.read()) {
        saxParser.parse(inputStream, handler);
      }
    } catch (ParserConfigurationException | SAXException | IOException e) {
      logger.error(e.getMessage());
    }

  }

  @Override
  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl)super.getDescriptor();
  }

  /**
   * The descriptor for the publisher
   * 
   * @author FInfantino, PikeTec GmbH
   *
   */
  @Extension
  @Symbol("tptReport")
  public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

    /**
     * In order to load the persisted global configuration, you have to call load() in the
     * constructor.
     */
    public DescriptorImpl() {
      load();
    }

    @Override
    public boolean isApplicable(Class< ? extends AbstractProject> jobType) {
      // Indicates that this builder can be used with all kinds of project
      // types.
      return true;
    }

    @Override
    public String getDisplayName() {
      return "TPT Report";
    }
  }

  @Override
  public BuildStepMonitor getRequiredMonitorService() {
    return BuildStepMonitor.NONE;
  }

  /**
   * In order to increase the performance and make a HashMap with a "FailedTestKey" instead of
   * looping throug a list.
   * 
   * @author FInfantino, PikeTec GmbH
   *
   */
  private static class FailedTestKey {

    private final String id;

    private final String fileName;

    private final String exeConfig;

    private final String platform;

    public FailedTestKey(String id, String fileName, String exeConfig, String platform) {
      this.id = id;
      this.fileName = fileName;
      this.exeConfig = exeConfig;
      this.platform = platform;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((exeConfig == null) ? 0 : exeConfig.hashCode());
      result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((platform == null) ? 0 : platform.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      FailedTestKey other = (FailedTestKey)obj;
      if (exeConfig == null) {
        if (other.exeConfig != null) {
          return false;
        }
      } else if (!exeConfig.equals(other.exeConfig)) {
        return false;
      }
      if (fileName == null) {
        if (other.fileName != null) {
          return false;
        }
      } else if (!fileName.equals(other.fileName)) {
        return false;
      }
      if (id == null) {
        if (other.id != null) {
          return false;
        }
      } else if (!id.equals(other.id)) {
        return false;
      }
      if (platform == null) {
        if (other.platform != null) {
          return false;
        }
      } else if (!platform.equals(other.platform)) {
        return false;
      }
      return true;
    }

  }

}
