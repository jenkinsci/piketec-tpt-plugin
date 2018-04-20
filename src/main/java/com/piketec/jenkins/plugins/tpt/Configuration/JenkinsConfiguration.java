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
package com.piketec.jenkins.plugins.tpt.Configuration;

import java.io.File;

import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import hudson.EnvVars;
import hudson.Extension;
import hudson.Util;
import hudson.model.AbstractProject;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.util.FormValidation;
import jenkins.model.Jenkins;

/**
 * Repeatable subelement for the TPT configuration. Mainly a pair of TPT file and execution
 * configuration enriched with some additional information.
 * 
 * @author jkuhnert, PikeTec GmbH
 */
public class JenkinsConfiguration implements Describable<JenkinsConfiguration> {

  private final boolean enableTest;

  private final String testSet;

  private long timeout;

  private final String tptFile;

  private final String configuration;

  private final String testdataDir;

  private final String reportDir;

  /**
   * the execution configuration is used by tpt to determine which file and which arguments is used.
   * later on, the back 2 back test determine the reference files with this.
   * 
   * @param tptFile
   *          - path to an executable tpt file <tt>*.tpt</tt>
   * @param configuration
   *          - arguments and configuration (configuration in double quotes)
   * @param testdataDir
   *          Testdata directory, if empty, path from the configuration will used.
   * @param reportDir
   *          Report directory, if empty, path from the configuration will used.
   * @param enableTest
   *          - true if you want to skip this configuration
   * @param timeout
   *          how long the execution of this test run is allwoed to take at max
   * @param testSet
   *          the name of test set that should be used, <code>null</code> or empty if the test set
   *          defined in the file should be used.
   */
  @DataBoundConstructor
  public JenkinsConfiguration(String tptFile, String configuration, String testdataDir,
                              String reportDir, boolean enableTest, long timeout, String testSet) {
    this.tptFile = tptFile;
    this.configuration = configuration;
    this.testdataDir = testdataDir;
    this.reportDir = reportDir;
    this.enableTest = enableTest;
    this.timeout = timeout;
    this.testSet = testSet;
  }

  protected Object readResolve() {
    if (timeout <= 0) {
      timeout = 6;
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
   * @return how long the execution of this test run is allwoed to take at max
   */
  public long getTimeout() {
    return timeout;
  }

  /**
   * @return The TPT file
   */
  public String getTptFile() {
    return tptFile;
  }

  /**
   * @return the name of test set that should be used, <code>null</code> or empty if the test set
   *         defined in the file should be used.
   */
  public String getTestSet() {
    return testSet;
  }

  /**
   * @return the configuration with replaced whitespace. <code> " " -&gt; "_"</code>
   */
  public String getConfigurationWithUnderscore() {
    return configuration.replace(" ", "_");
  }

  /**
   * @return the whole configuration string defined in the jenkins conf
   */
  public String getConfiguration() {
    return configuration;
  }

  // here, jenkins descriptor things
  /**
   * @return the actual descriptor of this object
   * 
   */
  @Override
  public Descriptor<JenkinsConfiguration> getDescriptor() {
    Jenkins instance = Jenkins.getInstance();
    if (instance == null) {
      return null;
    }
    @SuppressWarnings("unchecked")
    Descriptor<JenkinsConfiguration> descriptor = instance.getDescriptor(getClass());
    return descriptor;
  }

  /**
   * @return the directory where the TPT report shall be written to.
   */
  public String getReportDir() {
    return reportDir;
  }

  /**
   * @return The directory where the execution result data shall be written to
   */
  public String getTestdataDir() {
    return testdataDir;
  }

  /**
   * @param environment
   *          The map of environment variables and their values
   * @return A {@link JenkinsConfiguration} where all "${}"-Variables are replaced by their value if
   *         available in <code>environment</code>.
   */
  public JenkinsConfiguration replaceAndNormalize(EnvVars environment) {
    return new JenkinsConfiguration(Util.replaceMacro(tptFile, environment),
        Util.replaceMacro(configuration, environment), Util.replaceMacro(testdataDir, environment),
        Util.replaceMacro(reportDir, environment), enableTest, timeout,
        Util.replaceMacro(testSet, environment));
  }

  /**
   * Inline class for jenkins. so it is an repeatable object in conf.jelly
   */
  @Extension
  public static final class DescriptorImpl extends Descriptor<JenkinsConfiguration> {

    /**
     * @param tptFile
     *          the TPT file
     * @return An error if the TPT file field is empty
     */
    public static FormValidation doCheckTptFile(@QueryParameter File tptFile) {

      if ((tptFile != null) && (tptFile.getName().trim().length() > 0)) {
        return FormValidation.ok();
      } else {
        return FormValidation.error("Set the path of the TPT file.");
      }
    }

    /**
     * @param configuration
     *          The name of the execution configuration
     * @param project
     *          The jenkins project
     * @return An error
     */
    public static FormValidation doCheckConfiguration(@QueryParameter String configuration,
                                                      @AncestorInPath AbstractProject project) {
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

    /**
     * @return 0
     */
    public static int getDefaultSlaveJobCount() {
      return 0;
    }

    /**
     * @return an empty String
     */
    public static String getDefaultTestSet() {
      return "";
    }

    @Override
    public String getDisplayName() {
      return "";
    }
  }

}
