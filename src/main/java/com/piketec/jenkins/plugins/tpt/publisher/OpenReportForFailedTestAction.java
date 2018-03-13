package com.piketec.jenkins.plugins.tpt.publisher;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.io.FileUtils;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.DirectoryBrowserSupport;
import hudson.util.HttpResponses;

public class OpenReportForFailedTestAction implements Action, StaplerProxy {

  private String name;

  private String reportFile;

  private AbstractBuild< ? , ? > build;

  private String id;

  private String executionConfiguration;

  private String date;

  private String platform;

  public OpenReportForFailedTestAction(AbstractBuild< ? , ? > build, String fileName,
                                       String reportFile, String id, String exConfig,
                                       String platform, String date) {
    this.id = id;
    name = fileName;
    this.platform = platform;
    this.executionConfiguration = exConfig;
    this.build = build;
    this.reportFile = reportFile;
    this.date = date;

  }

  @Override
  public String getDisplayName() {
    return null;
  }

  @Override
  public String getIconFileName() {
    return null;
  }

  @Override
  public String getUrlName() {
    return name + executionConfiguration + id + date;
  }

  @Override
  public Object getTarget() {
    return this;
  }

  public String getName() {
    return name;
  }

  public void doDynamic(StaplerRequest req, StaplerResponse rsp)
      throws IOException, ServletException {

    File indexFromFile = new File(build.getRootDir().getAbsolutePath() + "\\Piketec-TPT\\" + name
        + "\\" + executionConfiguration + "\\index.html");
    String indexFromFileAsString = FileUtils.readFileToString(indexFromFile);
    String failedHtmlAsString = indexFromFileAsString.replace("overview.html", reportFile);
    File failedHTML = new File(build.getRootDir().getAbsolutePath() + "\\Piketec-TPT\\" + name
        + "\\" + executionConfiguration + "\\failedTest.html");

    FileUtils.writeStringToFile(failedHTML, failedHtmlAsString);
    DirectoryBrowserSupport dbs = new DirectoryBrowserSupport(this,
        new FilePath(new File(build.getRootDir().getAbsolutePath() + "\\Piketec-TPT\\" + name + "\\"
            + executionConfiguration)),
        "TPT Report", "clipboard.png", false);

    if (req.getRestOfPath().equals("")) {
      throw HttpResponses.forwardToView(this, "index.jelly");
    }
    dbs.generateResponse(req, rsp, this);
  }

  public String getExecutionConfiguration() {
    return executionConfiguration;
  }

  public void setExecutionConfiguration(String executionConfiguration) {
    this.executionConfiguration = executionConfiguration;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

}
