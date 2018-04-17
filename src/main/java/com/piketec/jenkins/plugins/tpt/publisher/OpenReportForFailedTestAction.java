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

public class OpenReportForFailedTestAction implements Action, StaplerProxy {

  private String name;

  private String reportFile;

  private AbstractBuild< ? , ? > build;

  private String id;

  private String executionConfiguration;

  private String date;

  private String platform;

  /**
   * This class is made for generating the failed html on the TPTReportPage. We open the index.html
   * and we replace the string "overview.html" with the name of the failed test. We store that in a
   * new html file called "failedTest.html". This will be done when it is requested by the user.
   * This class is also similar to InvisibleActionHostingHtml.
   * 
   * @param build
   * @param fileName
   * @param reportFile
   * @param id
   * @param exConfig
   * @param platform
   * @param date
   */
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

  /**
   * This method is called when an object from this class is created.
   * 
   * @param req
   * @param rsp
   * @throws IOException
   * @throws ServletException
   */
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
