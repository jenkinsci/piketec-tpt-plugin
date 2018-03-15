package com.piketec.jenkins.plugins.tpt.publisher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.xml.sax.SAXException;

import com.piketec.jenkins.plugins.tpt.TptPlugin;
import com.piketec.jenkins.plugins.tpt.Utils;
import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.Project;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Builder;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;

public class TPTReportPublisher extends Notifier {

  public TPTReportPage filesAction = null;

  @DataBoundConstructor
  public TPTReportPublisher() {
    // allow to display HTML files
    setSecurity();
  }

  private void setSecurity() {
    if (TPTGlobalConfiguration.DescriptorImpl.trustSlavesAndUsers) {
      System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "");
    } else {
      if (TPTGlobalConfiguration.DescriptorImpl.staticOldSettings != null) {
        System.setProperty("hudson.model.DirectoryBrowserSupport.CSP",
            TPTGlobalConfiguration.DescriptorImpl.staticOldSettings);
      }
    }

  }

  @Override
  public boolean perform(@SuppressWarnings("rawtypes") final AbstractBuild build,
                         final Launcher launcher, final BuildListener listener)
      throws IOException, InterruptedException {

    listener.getLogger().println("Starting Post Build Action \"TPT Report\"");

    ArrayList<FilePath> uniqueTestDataDir = new ArrayList<>();
    ArrayList<FilePath> uniqueReportDataDir = new ArrayList<>();

    ArrayList<TPTFile> tptFiles = new ArrayList<>();
    ArrayList<TPTTestCase> failedTests = new ArrayList<>();
    FilePath workspace = build.getWorkspace();
    Project< ? , ? > proj = (Project< ? , ? >)build.getProject();

    // global file in Build
    File piketectptDir = new File(build.getRootDir().getAbsolutePath() + "/Piketec-TPT");
    if (!piketectptDir.exists()) {
      if (!piketectptDir.mkdirs()) {
        throw new IOException(
            "Could not create directory \"" + piketectptDir.getAbsolutePath() + "\"");
      }
    }

    for (Builder builder : proj.getBuilders()) {
      if (builder instanceof TptPlugin) {
        TptPlugin tptPlugin = (TptPlugin)builder;
        for (JenkinsConfiguration cfg : tptPlugin.getExecutionConfiguration()) {

          // make file in build und copy report dir
          String tptFileName = FilenameUtils.getBaseName(cfg.getTptFile());
          File dir = new File(piketectptDir.getAbsolutePath() + "/" + tptFileName);
          if (!dir.isDirectory()) {
            if (!dir.mkdirs()) {
              throw new IOException("Could not create directory \"" + dir.getAbsolutePath() + "\"");
            }
          }

          File dirExConfig = new File(
              piketectptDir.getAbsolutePath() + "/" + tptFileName + "/" + cfg.getConfiguration());
          if (!dirExConfig.mkdirs()) {
            throw new IOException(
                "Could not create directory \"" + dirExConfig.getAbsolutePath() + "\"");
          }

          FilePath reportDir = new FilePath(workspace, Utils.getGeneratedReportDir(cfg));
          FilePath testDataDir = new FilePath(workspace, Utils.getGeneratedTestDataDir(cfg));

          if (reportDir.exists()) {
            reportDir.copyRecursiveTo(new FilePath(dirExConfig));
          }

          FilePath reportXML = new FilePath(testDataDir, "test_summary.xml");

          if (reportXML.exists()) {
            TPTFile newTPTFile = new TPTFile(tptFileName, cfg.getConfiguration());
            parse(reportXML, newTPTFile, failedTests, reportDir.getRemote(),
                cfg.getConfiguration());
            tptFiles.add(newTPTFile);
          }

          // Check if the Testdata dir and the Report are unique, otherwise throw an exception
          if (uniqueReportDataDir.contains(reportDir) || uniqueTestDataDir.contains(testDataDir)) {
            throw new IOException("The directory \"" + cfg.getReportDir() + "\" or the directoy \""
                + cfg.getTestdataDir() + " is already used, please choose another one");
          }
          uniqueReportDataDir.add(reportDir);
          uniqueTestDataDir.add(testDataDir);

        }
      }
    }

    // Failed Since. Look up test in previous build. If failed there. extract failed since
    // information and add 1
    AbstractBuild lastSuccBuild = (AbstractBuild)build.getPreviousNotFailedBuild();

    // the tpt report of last successful build
    TPTReportPage lastTptFilesAction =
        (lastSuccBuild == null) ? null : lastSuccBuild.getAction(TPTReportPage.class);

    if (lastTptFilesAction != null) {
      HashMap<FailedTestKey, TPTTestCase> prevFailed = new HashMap<FailedTestKey, TPTTestCase>();
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
          t.setBuildHistoy(prevFailed.get(ftk).getBuildHistoy() + 1);
        }
      }
    }

    filesAction = new TPTReportPage(build, failedTests, tptFiles);
    filesAction.createGraph();
    build.addAction(filesAction);

    listener.getLogger().println("Finished Post Build Action");
    if (!failedTests.isEmpty()) {
      build.setResult(Result.UNSTABLE);
    }
    return true;
  }

  public void parse(FilePath xmlFile, TPTFile tptFile, ArrayList<TPTTestCase> failedTests,
                    String reportDir, String executionConfiguration)
      throws InterruptedException {
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

    try {
      SAXParser saxParser = saxParserFactory.newSAXParser();
      TPTReportSAXHandler handler =
          new TPTReportSAXHandler(tptFile, failedTests, reportDir, executionConfiguration);
      InputStream inputStream = xmlFile.read();
      try {
        saxParser.parse(inputStream, handler);
      } finally {
        IOUtils.closeQuietly(inputStream);
      }
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public Action getProjectAction(AbstractProject< ? , ? > project) {
    return new TrendGraph(project);
  }

  @Override
  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl)super.getDescriptor();
  }

  @Extension
  public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

    /**
     * In order to load the persisted global configuration, you have to call load() in the
     * constructor.
     */
    public DescriptorImpl() {
      load();
    }

    @Override
    public boolean isApplicable(@SuppressWarnings("rawtypes") Class< ? extends AbstractProject> jobType) {
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
