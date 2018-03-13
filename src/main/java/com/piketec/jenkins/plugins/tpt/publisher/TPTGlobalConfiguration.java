package com.piketec.jenkins.plugins.tpt.publisher;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

public class TPTGlobalConfiguration implements Describable<TPTGlobalConfiguration> {

  @DataBoundConstructor
  public TPTGlobalConfiguration() {

  }

  @Extension
  public static class DescriptorImpl extends Descriptor<TPTGlobalConfiguration> {

    public static boolean trustSlavesAndUsers;

    public static String staticOldSettings;

    public String oldSettings;

    public boolean toSave;

    public DescriptorImpl() {
      load();
      trustSlavesAndUsers = toSave;
      staticOldSettings = oldSettings;
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
      json = json.getJSONObject("securityPermission");
      setTrustSlavesAndUsers(json.getBoolean("trustSlavesAndUsers"));
      toSave = trustSlavesAndUsers;
      if (!toSave) {
        setStaticOldSettings();
      }
      oldSettings = staticOldSettings;
      save();
      return true;
    }

    @Override
    public String getDisplayName() {
      return "";
    }

    public void setTrustSlavesAndUsers(boolean enableClou) {
      trustSlavesAndUsers = enableClou;
    }

    public void setStaticOldSettings() {
      String settings = System.getProperty("hudson.model.DirectoryBrowserSupport.CSP");
      // set the setting for the first time
      settings = (settings == null)
          ? "sandbox; default-src 'none'; img-src 'self'; style-src 'self';" : settings;
      if (staticOldSettings == null) {
        staticOldSettings = settings;
      }
      System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", staticOldSettings);

    }

  }

  @Override
  public Descriptor<TPTGlobalConfiguration> getDescriptor() {
    Jenkins instance = Jenkins.getInstance();
    if (instance == null) {
      return null;
    }
    @SuppressWarnings("unchecked")
    Descriptor<TPTGlobalConfiguration> descriptor = instance.getDescriptor(getClass());
    return descriptor;
  }

  public boolean isTrustSlavesAndUsers() {
    return DescriptorImpl.trustSlavesAndUsers;
  }

  public static boolean getA() {
    return DescriptorImpl.trustSlavesAndUsers;
  }

}
