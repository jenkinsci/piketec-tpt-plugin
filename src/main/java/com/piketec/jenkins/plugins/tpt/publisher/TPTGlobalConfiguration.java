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

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

/**
 * Allows the user to set the security in order to allow javascript on Jenkins. The jelly from this
 * class will apear on the Jenkins Global Configuration. This class is the responsable for the
 * "trust slaves and users to modify the Jenkins workspace option".
 * 
 * @author FInfantino, PikeTec GmbH
 *
 */
public class TPTGlobalConfiguration implements Describable<TPTGlobalConfiguration> {

  /**
   * Checks if the security has been set. If it has been set more than once, it sets the security
   * back to its old settings.
   */
  public static void setSecurity() {
    if (TPTGlobalConfiguration.DescriptorImpl.trustSlavesAndUsers) {
      System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "");
    } else {
      if (TPTGlobalConfiguration.DescriptorImpl.staticOldSettings != null) {
        System.setProperty("hudson.model.DirectoryBrowserSupport.CSP",
            TPTGlobalConfiguration.DescriptorImpl.staticOldSettings);
      }
    }
  }

  /**
   * @return Has the user activated the "trust slaves and users" check box in the global Jenkins
   *         configurations
   */
  public static boolean isTrustSlavesAndUsers() {
    return DescriptorImpl.trustSlavesAndUsers;
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

  /**
   * Creates a new TPTGlobalConfiguration
   */
  @DataBoundConstructor
  public TPTGlobalConfiguration() {
  }

  /**
   * The descriptor for TPTGlobalConfiguration
   * 
   * @author FInfantino, PikeTec GmbH
   */
  @Extension
  public static class DescriptorImpl extends Descriptor<TPTGlobalConfiguration> {

    static boolean trustSlavesAndUsers;

    static String staticOldSettings;

    private String oldSettings;

    private boolean toSave;

    /**
     * Creates a new Descriptor
     */
    public DescriptorImpl() {
      load();
      setTrustSlavesAndUsers(toSave);
      setStaticOldSettings(oldSettings);
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

    /**
     * Set if the user hase activated the "trust slaves and users" check box in the global Jenkins
     * configurations
     * 
     * @param enableTrustSlavesAndUsers
     *          Should the option be enabled
     */
    public static void setTrustSlavesAndUsers(boolean enableTrustSlavesAndUsers) {
      trustSlavesAndUsers = enableTrustSlavesAndUsers;
    }

    /**
     * @return Has the user activated the "trust slaves and users" check box in the global Jenkins
     *         configurations
     */
    public static boolean isTrustSlavesAndUsers() {
      return trustSlavesAndUsers;
    }

    /**
     * @return Has the user activated the "trust slaves and users" check box in the global Jenkins
     *         configurations
     */
    public static boolean getTrustSlavesAndUsers() {
      return trustSlavesAndUsers;
    }

    static void setStaticOldSettings(String oldSettings) {
      staticOldSettings = oldSettings;
    }

    private void setStaticOldSettings() {
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

}
