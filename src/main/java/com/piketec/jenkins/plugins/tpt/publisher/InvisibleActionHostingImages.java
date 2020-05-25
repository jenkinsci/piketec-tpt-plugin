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

import java.io.IOException;

import javax.servlet.ServletException;

import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.DirectoryBrowserSupport;
import hudson.util.HttpResponses;

/**
 * An invisible action to display images
 * 
 * @author FInfantino, PikeTec GmbH
 */
public class InvisibleActionHostingImages implements Action, StaplerProxy {

  private AbstractBuild< ? , ? > build;

  /**
   * Similar to InvisibleActionHostingHtml but with the images, such as the pie chart. @see
   * InvisibleActionHostingHtml.
   * 
   * @param build
   *          The current jenkins build
   */
  public InvisibleActionHostingImages(AbstractBuild< ? , ? > build) {
    this.build = build;
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
    return "Images";
  }

  @Override
  public Object getTarget() {
    return this;
  }

  /**
   * @return The jenkins build this action belongs to
   */
  public AbstractBuild< ? , ? > getBuild() {
    return build;
  }

  /**
   * Set the Jenkins build for this action
   * 
   * @param build
   *          The current jenkins build
   */
  public void setBuild(AbstractBuild< ? , ? > build) {
    this.build = build;
  }

  /**
   * Implicitly called jelly to create a html response
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
    DirectoryBrowserSupport dbs = new DirectoryBrowserSupport(this,
        new FilePath(TPTReportUtils.getImageDir(build)), "TPT Report", "clipboard.png", false);
    if (req.getRestOfPath().equals("")) {
      throw HttpResponses.forwardToView(this, "index.jelly");
    }
    dbs.generateResponse(req, rsp, this);
  }

}
