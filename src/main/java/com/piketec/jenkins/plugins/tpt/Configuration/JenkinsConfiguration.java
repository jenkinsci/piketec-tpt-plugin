/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2018 Synopsys Inc.
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
package com.piketec.jenkins.plugins.tpt.Configuration;

import java.io.File;

import javax.annotation.CheckForNull;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import hudson.EnvVars;
import hudson.Extension;
import hudson.Util;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.util.FormValidation;
import jenkins.model.Jenkins;

/**
 * Repeatable subelement for the TPT configuration. Mainly a pair of TPT file and execution
 * configuration enriched with some additional information.
 * 
 * @author jkuhnert, Synopsys Inc.
 */
public class JenkinsConfiguration implements Describable<JenkinsConfiguration> {

  private boolean enableTest = DescriptorImpl.getDefaultEnableTest();

  @CheckForNull
  private String testSet = null;

  private long timeout = DescriptorImpl.getDefaultTimeout();

  @CheckForNull
  private String tptFile = null;

  @CheckForNull
  private String configuration = null;

  @CheckForNull
  private String testdataDir = null;

  @CheckForNull
  private String reportDir = null;

  @CheckForNull
  private String id = null;

  /**
   * the execution configuration is used by tpt to determine which file and which arguments is used.
   * later on, the back 2 back test determine the reference files with this.
   * 
   * @param tptFile
   *          path to an executable tpt file
   * @param configuration
   *          arguments and configuration (configuration in double quotes)
   * @param id
   *          The unique ID of the configuration to create unique paths
   */
  @DataBoundConstructor
  public JenkinsConfiguration(String tptFile, String configuration, String id) {
    this.tptFile = Util.fixEmpty(tptFile);
    this.configuration = Util.fixEmpty(configuration);
    this.id = Util.fixEmpty(id);
  }

  protected Object readResolve() {
    if (timeout <= 0) {
      timeout = DescriptorImpl.getDefaultTimeout();
    }
    return this;
  }

  /**
   * @return if this {@link JenkinsConfiguration} should run at all
   */
  public boolean isEnableTest() {
    return enableTest;
  }

  /**
   * @param enableTest
   *          the enableTest to set
   */
  @DataBoundSetter
  public void setEnableTest(boolean enableTest) {
    this.enableTest = enableTest;
  }

  /**
   * @return how long the execution of this test run is allwoed to take at max
   */
  public long getTimeout() {
    return timeout;
  }

  /**
   * @param timeout
   *          the timeout to set
   */
  @DataBoundSetter
  public void setTimeout(long timeout) {
    this.timeout = timeout;
  }

  /**
   * @return The TPT file
   */
  public String getTptFile() {
    return Util.fixNull(tptFile);
  }

  /**
   * @return the name of test set that should be used, <code>null</code> or empty if the test set
   *         defined in the file should be used.
   */
  public String getTestSet() {
    return Util.fixNull(testSet);
  }

  /**
   * @param testSet
   *          the testSet to set
   */
  @DataBoundSetter
  public void setTestSet(String testSet) {
    this.testSet = Util.fixEmpty(testSet);
  }

  /**
   * @return the whole configuration string defined in the jenkins conf
   */
  public String getConfiguration() {
    return Util.fixNull(configuration);
  }

  /**
   * @return the directory where the TPT report shall be written to.
   */
  public String getReportDir() {
    return Util.fixEmpty(reportDir);
  }

  /**
   * @param reportDir
   *          the reportDir to set
   */
  @DataBoundSetter
  public void setReportDir(String reportDir) {
    this.reportDir = Util.fixNull(reportDir);
  }

  /**
   * @return The directory where the execution result data shall be written to
   */
  public String getTestdataDir() {
    return Util.fixEmpty(testdataDir);
  }

  /**
   * @param testdataDir
   *          the testdataDir to set
   */
  @DataBoundSetter
  public void setTestdataDir(String testdataDir) {
    this.testdataDir = Util.fixNull(testdataDir);
  }

  /**
   * @return the unique id of this configuration
   */
  public String getId() {
    return Util.fixNull(id);
  }

  /**
   * @param id
   *          set the unique id of this configuration.
   */
  public void setId(String id) {
    this.id = Util.fixEmpty(id);
  }

  /**
   * 
   * This method resolves all variables that are used for the definition of the test set and the
   * execution configuration, but not for the directories. These are resolved on the respective
   * agent machine with the respective environment variables.
   * 
   * @param environment
   *          The map of environment variables and their values
   * @return A {@link JenkinsConfiguration} where all "${}"-Variables are replaced by their value if
   *         available in <code>environment</code>.
   */
  public JenkinsConfiguration replaceAndNormalize(EnvVars environment) {
    JenkinsConfiguration normalizedCfg =
        new JenkinsConfiguration(Util.replaceMacro(getTptFile(), environment),
            Util.replaceMacro(getConfiguration(), environment),
            Util.replaceMacro(getId(), environment));
    normalizedCfg.setTestdataDir(Util.replaceMacro(getTestdataDir(), environment));
    normalizedCfg.setReportDir(Util.replaceMacro(getReportDir(), environment));
    normalizedCfg.setEnableTest(isEnableTest());
    normalizedCfg.setTimeout(getTimeout());
    normalizedCfg.setTestSet(Util.replaceMacro(testSet, environment));
    return normalizedCfg;
  }

  // here, jenkins descriptor things
  /**
   * @return the actual descriptor of this object
   * 
   */
  @Override
  public Descriptor<JenkinsConfiguration> getDescriptor() {
    Jenkins instance = Jenkins.getInstanceOrNull();
    if (instance == null) {
      return null;
    }
    @SuppressWarnings("unchecked")
    Descriptor<JenkinsConfiguration> descriptor = instance.getDescriptor(getClass());
    return descriptor;
  }

  /**
   * Inline class for jenkins. so it is an repeatable object in conf.jelly
   */
  @Extension
  @Symbol("tptConfig")
  public static final class DescriptorImpl extends Descriptor<JenkinsConfiguration> {

    /**
     * @param tptFile
     *          the TPT file
     * @return An error if the TPT file field is empty
     */
    // lgtm[jenkins/csrf]
    // lgtm[jenkins/no-permission-check]
    public FormValidation doCheckTptFile(@QueryParameter File tptFile) {
      if ((tptFile != null) && (tptFile.getName().trim().length() > 0)) {
        return FormValidation.ok();
      } else {
        return FormValidation.error("Set the path of the TPT file.");
      }
    }

    /**
     * @param configuration
     *          The name of the execution configuration
     * @return An error
     */
    // lgtm[jenkins/csrf]
    public static FormValidation doCheckConfiguration(@QueryParameter String configuration) {
      if ((configuration == null) || (configuration.trim().length() == 0)) {
        return FormValidation.error("Enter a configuration name.");
      } else {
        return FormValidation.ok();
      }
    }

    /**
     * @return <code>true</code>
     */
    public static boolean getDefaultEnableTest() {
      return true;
    }

    /**
     * @return 6
     */
    public static long getDefaultTimeout() {
      return 6;
    }

    @Override
    public String getDisplayName() {
      return "TPT file configuration";
    }
  }

}
