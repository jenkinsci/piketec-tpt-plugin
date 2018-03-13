package com.piketec.jenkins.plugins.tpt.publisher;

import java.io.File;
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

public class InvisibleActionHostingImages implements Action, StaplerProxy {

  private AbstractBuild< ? , ? > build;

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

  public AbstractBuild< ? , ? > getBuild() {
    return build;
  }

  public void setBuild(AbstractBuild< ? , ? > build) {
    this.build = build;
  }

  public void doDynamic(StaplerRequest req, StaplerResponse rsp)
      throws IOException, ServletException {

    DirectoryBrowserSupport dbs = new DirectoryBrowserSupport(this,
        new FilePath(new File(build.getRootDir().getAbsolutePath() + "\\Piketec-TPT\\Images")),
        "TPT Report", "clipboard.png", false);

    if (req.getRestOfPath().equals("")) {
      throw HttpResponses.forwardToView(this, "index.jelly");
    }
    dbs.generateResponse(req, rsp, this);
  }

}
