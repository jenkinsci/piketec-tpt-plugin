/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 PikeTec GmbH
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
package com.piketec.jenkins.plugins.tpt;

import java.io.File;
import java.io.IOException;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

/**
 * Plugin executes a single given TPT test case. Intended to be used in the job started by
 * {@link TptPluginMasterJobExecutor}.
 */
public class TptPluginSlave extends Builder {

  private String exePaths;

  private String tptFile;

  private String tptBindingName;

  private String tptPort;

  private String testDataDir;

  private String reportDir;

  private String tptStartUpWaitTime;

  // ----------- Data Binding --------------

  @DataBoundConstructor
  public TptPluginSlave(String exePaths, String tptBindingName, String tptPort, String tptFile,
                        String testDataDir, String reportDir, String tptStartUpWaitTime) {
    this.exePaths = exePaths;
    this.tptFile = tptFile;
    this.tptBindingName = tptBindingName;
    this.tptPort = tptPort;
    this.testDataDir = testDataDir;
    this.reportDir = reportDir;
    this.tptStartUpWaitTime = tptStartUpWaitTime;

  }

  public String getExePaths() {
    return exePaths;
  }

  public String getTptFile() {
    return tptFile;
  }

  public String getTptBindingName() {
    return tptBindingName;
  }

  public String getTptPort() {
    return tptPort;
  }

  public String getTestDataDir() {
    return testDataDir;
  }

  public String getReportDir() {
    return reportDir;
  }

  /**
   * @return The time waited before trying to get the API handle after starting TPT
   */
  public String getTptStartUpWaitTime() {
    return tptStartUpWaitTime;
  }

  // --------------------------------------------------------------

  @Override
  public boolean perform(AbstractBuild< ? , ? > build, Launcher launcher, BuildListener listener)
      throws InterruptedException, IOException {
    TptLogger logger = new TptLogger(listener.getLogger());
    EnvVars environment;
    try {
      environment = build.getEnvironment(launcher.getListener());
    } catch (IOException e) {
      environment = new EnvVars();
      logger.error(e.getLocalizedMessage());
    } catch (InterruptedException e) {
      logger.error(e.getLocalizedMessage());
      return false;
    }
    String[] expandedStringExePaths = environment.expand(exePaths).split("[,;]");
    FilePath[] expandedExePaths = new FilePath[expandedStringExePaths.length];
    for (int i = 0; i < expandedExePaths.length; i++) {
      expandedExePaths[i] =
          new FilePath(launcher.getChannel(), environment.expand(expandedStringExePaths[i].trim()));
    }
    int expandedTptPort;
    if (tptPort != null && !tptPort.isEmpty()) {
      try {
        expandedTptPort = Integer.parseInt(environment.expand(tptPort));
      } catch (NumberFormatException e) {
        logger.error("The given port " + environment.expand(tptPort) + " is not an integer."
            + " Using default value.");
        expandedTptPort = DescriptorImpl.getDefaultTptPort();
      }
    } else {
      expandedTptPort = DescriptorImpl.getDefaultTptPort();
    }
    String expandedTptBindingName;
    if (tptBindingName != null && !tptBindingName.isEmpty()) {
      expandedTptBindingName = environment.expand(tptBindingName);
    } else {
      expandedTptBindingName = DescriptorImpl.getDefaultTptBindingName();
    }
    long expandedTptStartupWaitTime;
    if (tptStartUpWaitTime != null && !tptStartUpWaitTime.isEmpty()) {
      try {
        expandedTptStartupWaitTime =
            Integer.parseInt(environment.expand(tptStartUpWaitTime)) * 1000;
      } catch (NumberFormatException e) {
        logger.error("The given TPT startup waiting time " + environment.expand(tptStartUpWaitTime)
            + " is not an integer. Using default value.");
        expandedTptStartupWaitTime = DescriptorImpl.getDefaultTptStartUpWaitTime() * 1000;
      }
    } else {
      expandedTptStartupWaitTime = DescriptorImpl.getDefaultTptStartUpWaitTime() * 1000;
    }
    String expandedTptFile = environment.expand(tptFile);
    if (expandedTptFile == null) {
      expandedTptFile = "";
    }
    String expandedExecConfig = environment.expand("${" + Utils.TPT_EXECUTION_CONFIG_VAR + "}");
    String expandedTestDataDir = environment.expand(testDataDir);
    String expandedReportDir = environment.expand(reportDir);
    String expandedTestcaseName = environment.expand("${" + Utils.TPT_TEST_CASE_NAME_VAR + "}");
    String expandedExecutionId = environment.expand("${" + Utils.TPT_EXECUTION_ID_VAR_NAME + "}");
    TptPluginSlaveExecutor executor = new TptPluginSlaveExecutor(launcher, build, listener,
        expandedExePaths, expandedTptPort, expandedTptBindingName, new File(expandedTptFile),
        expandedExecConfig, expandedTestDataDir, expandedReportDir, expandedTestcaseName,
        expandedTptStartupWaitTime, expandedExecutionId);
    return executor.execute();
  }

  @Override
  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl)super.getDescriptor();
  }

  // --------------------------- Descriptor Class -----------------------------------

  @Extension
  public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

    @Override
    public boolean isApplicable(Class< ? extends AbstractProject> jobType) {
      // all project types allowed
      return true;
    }

    @Override
    public String getDisplayName() {
      return "Execute TPT tests slave";
    }

    public static String getDefaultExePaths() {
      return "${" + Utils.TPT_EXE_VAR + "}";
    }

    public static String getDefaultTptFile() {
      return "${" + Utils.TPT_FILE_VAR + "}";
    }

    public static String getDefaultTptBindingName() {
      return Utils.DEFAULT_TPT_BINDING_NAME;
    }

    public static int getDefaultTptPort() {
      return Utils.DEFAULT_TPT_PORT;
    }

    public static String getDefaultTestDataDir() {
      return "${" + Utils.TPT_TEST_DATA_DIR_VAR_NAME + "}";
    }

    public static String getDefaultReportDir() {
      return "${" + Utils.TPT_REPORT_DIR_VAR_NAME + "}";
    }

    public static int getDefaultTptStartUpWaitTime() {
      return Utils.DEFAULT_STARTUP_WAIT_TIME;
    }

  }

}
