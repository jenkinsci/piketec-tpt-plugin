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

public class InvisibleActionHostingHtml implements Action, StaplerProxy {

  private String name;

  private AbstractBuild< ? , ? > build;

  private String exeConfig;

  public InvisibleActionHostingHtml(AbstractBuild< ? , ? > build, String urlFileName,
                                    String exeConfig) {
    name = urlFileName;
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

  public String getName() {
    return name;
  }

  public String path() {
    return build.getRootDir().getAbsolutePath() + "\\Piketec-TPT\\" + name + "\\" + exeConfig;
  }

  public void doDynamic(StaplerRequest req, StaplerResponse rsp)
      throws IOException, ServletException {

    File f = new File(build.getRootDir().getAbsolutePath() + "\\Piketec-TPT\\" + name + "\\"
        + exeConfig + "\\index.html");
    FileUtils.touch(f);

    DirectoryBrowserSupport dbs = new DirectoryBrowserSupport(this,
        new FilePath(new File(
            build.getRootDir().getAbsolutePath() + "\\Piketec-TPT\\" + name + "\\" + exeConfig)),
        "TPT Report", "clipboard.png", false);

    if (req.getRestOfPath().equals("")) {
      throw HttpResponses.forwardToView(this, "index.jelly");
    }
    dbs.generateResponse(req, rsp, this);
  }

}
