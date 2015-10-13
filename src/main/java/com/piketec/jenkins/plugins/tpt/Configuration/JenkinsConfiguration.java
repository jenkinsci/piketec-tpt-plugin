/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 PikeTec GmbH
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

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.util.FormValidation;

import java.io.File;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

public class JenkinsConfiguration implements Describable<JenkinsConfiguration> {

  private final boolean enableTest;

  private final File tptFile;

  private final String configuration;

  private final File testdataDir;

  private final File reportDir;

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
   */
  @DataBoundConstructor
  public JenkinsConfiguration(File tptFile, String configuration, File testdataDir, File reportDir,
                              boolean enableTest) {
    this.tptFile = tptFile;
    this.configuration = configuration;
    this.testdataDir = testdataDir;
    this.reportDir = reportDir;
    this.enableTest = enableTest;
  }

  public boolean isEnableTest() {
    return enableTest;
  }

  public File getTptFile() {
    return tptFile;
  }

  /**
   * @return the tpt filename without ".tpt"
   */
  public String getTptFileName() {
    int end = tptFile.getName().lastIndexOf(".");
    return tptFile.getName().substring(0, end);
  }

  public String getReportName() {
    return getTptFileName() + "." + getConfigurationWithUnderscore() + ".xml";
  }

  /**
   * @return the configuration with replaced whitespace. <code> " " -&gt; "_"</code>
   */
  public String getConfigurationWithUnderscore() {
    return configuration.replace(" ", "_");
  }

  /**
   * 
   * @return the tpt filename with a dot and the configuration with underscores as spaces
   */
  public String getClassname() {
    return getTptFileName() + "." + getConfigurationWithUnderscore();
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
    @SuppressWarnings("unchecked")
    Descriptor<JenkinsConfiguration> descriptor = Hudson.getInstance().getDescriptor(getClass());

    return descriptor;
  }

  public File getReportDir() {
    return reportDir;
  }

  public File getTestdataDir() {
    return testdataDir;
  }

  /**
   * Builds a absolute path from the workspace directory and the given path.
   * <ul>
   * <li>If both are <code>null</code>, the current working directory will returned - hopefully it
   * is inside the workspace.</li>
   * <li>If the workspace is <code>null</code>, the path will returned.</li>
   * <li>If the path is <code>null</code>, the workspace will returned.</li>
   * <li>If the path is absolute, the path will returned.</li>
   * <li>If the path is relative, the path will append to the workspace and returned.</li>
   * </ul>
   * 
   * @param workspaceDir
   *          Current workspace for the build.
   * @param path
   *          Relative or absolute path.
   * @return A absolute path, but it can be a nonexisting file system object or not a directory.
   */
  public static File getAbsolutePath(File workspaceDir, File path) {
    File absPath = workspaceDir;

    if (path == null) {
      absPath = (workspaceDir == null) ? new File("") : workspaceDir;
    } else if (path.isAbsolute()) {
      absPath = path;
    } else {
      absPath = (workspaceDir == null) ? path : new File(workspaceDir, path.toString());
    }

    return absPath.isAbsolute() ? absPath : absPath.getAbsoluteFile();
  }

  /**
   * Inline class for jenkins. so it is an repeatable object in conf.jelly
   */
  @Extension
  public static final class DescriptorImpl extends Descriptor<JenkinsConfiguration> {

    public static FormValidation doCheckTptFile(@QueryParameter File tptFile) {

      if ((tptFile != null) && (tptFile.getName().trim().length() > 0)) {
        return FormValidation.ok();
      } else {
        return FormValidation.error("Set the path of the TPT file.");
      }
    }

    public static FormValidation doCheckConfiguration(@QueryParameter String configuration) {
      return ((configuration == null) || (configuration.trim().length() == 0)) ? FormValidation
          .error("Enter a configuration name.") : FormValidation.ok();
    }

    public static boolean getDefaultEnableTest() {
      return true;
    }

    @Override
    public String getDisplayName() {
      return "";
    }
  }
}
