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

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.FilePath;
import hudson.model.DirectoryBrowserSupport;
import hudson.model.InvisibleAction;

/**
 * An invisibale action to show HTML reports
 * 
 * @author FInfantino, PikeTec GmbH
 */
public abstract class InvisibleActionHostingHtml extends InvisibleAction {

  private String jenkinsConfigId;

  private transient TPTReportPage parentPage = null;

  /**
   * @return The name of the executed platform
   */
  public String getJenkinsConfigId() {
    return jenkinsConfigId;
  }

  /**
   * Set the unique id of the jenkins configuartion that test case ran on.
   * 
   * @param jenkinsConfigId
   *          The unique id
   */
  public void setJenkinsConfigId(String jenkinsConfigId) {
    this.jenkinsConfigId = jenkinsConfigId;
  }

  void setParentPage(TPTReportPage parentPage) {
    this.parentPage = parentPage;
  }

  /**
   * @return The path to the html file
   */
  protected File pathToHtml() {
    return TPTReportUtils.getReportDir(TPTReportUtils.getPikeTecDir(parentPage.getBuild()),
        jenkinsConfigId);
  }

  /**
   * This method is called when an InvisibleActionHostingHtml object is created. It displays the
   * "index.html"
   * 
   * @param req
   *          The request
   * @param rsp
   *          The response
   * @throws IOException
   *           If an IO error occures
   * @throws ServletException
   *           If the response could not be generated
   */
  // lgtm[jenkins/csrf]
  public void doDynamic(StaplerRequest req, StaplerResponse rsp)
      throws IOException, ServletException {
    File pathToHtml = pathToHtml();
    DirectoryBrowserSupport dbs = new DirectoryBrowserSupport(this, new FilePath(pathToHtml),
        "TPT Report", "clipboard.png", false);
    dbs.generateResponse(req, rsp, this);
  }

}
