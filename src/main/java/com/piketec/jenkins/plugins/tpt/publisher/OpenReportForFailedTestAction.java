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

/**
 * Action to open a TPT report of a failed test case
 * 
 * @author FInfantino, PikeTec GmbH
 */
public class OpenReportForFailedTestAction implements Action, StaplerProxy {

  private String name;

  private String reportFile;

  private AbstractBuild< ? , ? > build;

  private String id;

  private String executionConfiguration;

  private String date;

  private String jenkinsConfigId;

  /**
   * This class is made for generating the failed html on the TPTReportPage. We open the index.html
   * and we replace the string "overview.html" with the name of the failed test. We store that in a
   * new html file called "failedTest.html". This will be done when it is requested by the user.
   * This class is also similar to InvisibleActionHostingHtml.
   * 
   * @param build
   *          The current jenkins build
   * @param fileName
   *          The name of the tpt file
   * @param reportFile
   *          the path to the report file
   * @param id
   *          the test case id
   * @param exConfig
   *          the name of the execution configurationm
   * @param date
   *          the execution date
   * @param jenkinsConfigId
   *          The unique ID of the configuration to create unique paths
   */
  public OpenReportForFailedTestAction(AbstractBuild< ? , ? > build, String fileName,
                                       String reportFile, String id, String exConfig, String date,
                                       String jenkinsConfigId) {
    this.id = id;
    this.name = fileName;
    this.executionConfiguration = exConfig;
    this.build = build;
    this.reportFile = reportFile;
    this.date = date;
    this.jenkinsConfigId = jenkinsConfigId;
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

  /**
   * @return The name of the TPT file
   */
  public String getName() {
    return name;
  }

  /**
   * @return The path to the html file
   */
  private File pathToHtml() {
    return TPTReportUtils.getReportDir(TPTReportUtils.getPikeTecDir(build), jenkinsConfigId);
  }

  /**
   * This method is called when an object from this class is created.
   * 
   * When clicking on a failed test case, the overview report should be opened, with the navigation
   * on the left and the failed test case report on the right.
   * 
   * It maps the URL the user sees to the file path where the corresponding html document lies.
   * 
   * @param req
   *          The request
   * @param rsp
   *          The response
   * @throws IOException
   *           if the response could not be generated
   * @throws ServletException
   *           if the response could not be generated
   */
  public void doDynamic(StaplerRequest req, StaplerResponse rsp)
      throws IOException, ServletException {

    File indexFromFile = new File(pathToHtml(), "index.html");

    String indexFromFileAsString = FileUtils.readFileToString(indexFromFile);
    String failedHtmlAsString = indexFromFileAsString.replace("overview.html", reportFile);
    File failedHTML = new File(pathToHtml(), "failedTest.html");

    FileUtils.writeStringToFile(failedHTML, failedHtmlAsString);
    DirectoryBrowserSupport dbs = new DirectoryBrowserSupport(this, new FilePath(pathToHtml()),
        "TPT Report", "clipboard.png", false);

    if (req.getRestOfPath().equals("")) {
      throw HttpResponses.forwardToView(this, "index.jelly");
    }
    dbs.generateResponse(req, rsp, this);
  }

  /**
   * @return the name of the TPT execution configuration
   */
  public String getExecutionConfiguration() {
    return executionConfiguration;
  }

  /**
   * set the name of the TPT execution configuration
   * 
   * @param executionConfiguration
   *          the name of the TPT execution configuration
   * 
   */
  public void setExecutionConfiguration(String executionConfiguration) {
    this.executionConfiguration = executionConfiguration;
  }
}
