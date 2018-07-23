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
 * An invisibale action to show HTML reports
 * 
 * @author FInfantino, PikeTec GmbH
 */
public class InvisibleActionHostingHtml implements Action, StaplerProxy {

  private String name;

  private AbstractBuild< ? , ? > build;

  private String exeConfig;

  /**
   * This class is for hosting the HTML Report in an action. After requesting the url in the
   * TPTReportPage it redirects to this class and here will be the HTML displayed through the
   * doDynamic method.(It uses the DirectoryBrowserSupport)
   * 
   * @param build
   *          to locate where the file is
   * @param urlFileName
   *          to know which file it is
   * @param exeConfig
   *          to know which file it is , the path is made by a filename and a execution config
   */
  public InvisibleActionHostingHtml(AbstractBuild< ? , ? > build, String urlFileName,
                                    String exeConfig) {
    this.name = urlFileName;
    this.build = build;
    this.exeConfig = exeConfig;

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
    return name + exeConfig;
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
  public String path() {
    return build.getRootDir().getAbsolutePath() + File.separator + "Piketec-TPT" + File.separator
        + name + File.separator + exeConfig;
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
  public void doDynamic(StaplerRequest req, StaplerResponse rsp)
      throws IOException, ServletException {

    File f = new File(path() + File.separator + "index.html");
    FileUtils.touch(f); // refresh the index if security changed
    // this displays the "index.html" in the given path
    DirectoryBrowserSupport dbs = new DirectoryBrowserSupport(this, new FilePath(new File(path())),
        "TPT Report", "clipboard.png", false);
    if (req.getRestOfPath().equals("")) {
      throw HttpResponses.forwardToView(this, "index.jelly");
    }
    dbs.generateResponse(req, rsp, this);
  }

}
