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
package com.piketec.jenkins.plugins.tpt;

import java.io.IOException;
import java.util.List;

import javax.annotation.CheckForNull;

import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

import hudson.AbortException;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.Util;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import jenkins.tasks.SimpleBuildStep;

/**
 * Plugin executes a single given TPT test case. Intended to be used in the job started by
 * {@link TptPluginMasterJobExecutor}.
 */
public class TptPluginSlave extends Builder implements SimpleBuildStep {

  private String exePaths;

  @CheckForNull
  private String arguments = null;

  private String tptBindingName;

  private String tptPort;

  private String tptStartUpWaitTime;

  // ----------- Data Binding --------------

  /**
   * 
   * Those arguments are processed and then passed to the {@link TptPluginWorkerJobExecutor}. This
   * class is used as a wrapper
   * 
   * @param exePaths
   *          the paths to the Tpt Executables
   */
  @DataBoundConstructor
  public TptPluginSlave(String exePaths) {
    this.exePaths = exePaths;
  }

  /**
   * @return The list of paths to possible TPT-installations.
   */
  public String getExePaths() {
    return Util.fixNull(exePaths);
  }

  /**
   * Common command line opts. Delimiter between the options is one or more spaces. Inside
   * doublequotes spaces have no special meaning.
   * 
   * @return 0 or more options for tpt.
   */
  public String getArguments() {
    return Util.fixNull(arguments);
  }

  @DataBoundSetter
  public void setArguments(String arguments) {
    this.arguments = Util.fixEmpty(arguments);
  }

  /**
   * @return the RMI binding name for TPT
   */
  public String getTptBindingName() {
    return tptBindingName == null ? DescriptorImpl.getDefaultTptBindingName() : tptBindingName;
  }

  /**
   * @param tptBindingName
   *          The RMI binding name for TPT
   */
  @DataBoundSetter
  public void setTptBindingName(String tptBindingName) {
    this.tptBindingName =
        DescriptorImpl.getDefaultTptBindingName().equals(tptBindingName) ? null : tptBindingName;
  }

  /**
   * @return The port of the RMI registry
   */
  public String getTptPort() {
    return tptPort == null ? DescriptorImpl.getDefaultTptPort() : tptPort;
  }

  /**
   * @param tptPort
   *          The port of the RMI registry
   */
  @DataBoundSetter
  public void setTptPort(String tptPort) {
    this.tptPort = DescriptorImpl.getDefaultTptPort().equals(tptPort) ? null : tptPort;
  }

  /**
   * @return The time waited before trying to get the API handle after starting TPT
   */
  public String getTptStartUpWaitTime() {
    return tptStartUpWaitTime == null ? DescriptorImpl.getDefaultTptStartUpWaitTime()
        : tptStartUpWaitTime;
  }

  /**
   * @param tptStartUpWaitTime
   *          The time to wait for TPT to start
   */
  @DataBoundSetter
  public void setTptStartUpWaitTime(String tptStartUpWaitTime) {
    this.tptStartUpWaitTime =
        DescriptorImpl.getDefaultTptStartUpWaitTime().equals(tptStartUpWaitTime) ? null
            : tptStartUpWaitTime;
  }

  // --------------------------------------------------------------

  /**
   * It collects the necesary data (tpt exe path, tpt Port, tpt bindingname and tpt
   * expandedTptStartupWaitTime) from the environment. Then collects the necesary data from the
   * workload (put by the TptPluginMasterJobExecutor). After collecting all the necesary data it
   * creates a new {@link TptPluginWorkerJobExecutor} and execute it. This method will be called
   * from Jenkins when a build for a worker job is scheduled. @see retryableJob. The logic is that
   * the retryableJob schedules builds for the worker job and those builds will be executed here.
   */
  @Override
  public void perform(Run< ? , ? > run, FilePath workspace, EnvVars env, Launcher launcher,
                      TaskListener listener)
      throws InterruptedException, IOException {
    TptLogger logger = new TptLogger(listener.getLogger());
    perform(run, workspace, launcher, listener, env, logger);
  }

  private void perform(Run< ? , ? > run, FilePath workspace, Launcher launcher,
                       TaskListener listener, EnvVars environment, TptLogger logger)
      throws InterruptedException, IOException {
    String[] expandedStringExePaths = expand(environment, exePaths).split("[,;]");
    FilePath[] expandedExePaths = new FilePath[expandedStringExePaths.length];
    for (int i = 0; i < expandedExePaths.length; i++) {
      expandedExePaths[i] =
          new FilePath(workspace, expand(environment, expandedStringExePaths[i].trim()));
    }
    String expandedArguments = environment.expand(getArguments());
    int expandedTptPort;
    if (tptPort != null && !tptPort.isEmpty()) {
      try {
        expandedTptPort = Integer.parseInt(expand(environment, tptPort));
      } catch (NumberFormatException e) {
        logger.error("The given port " + expand(environment, tptPort) + " is not an integer."
            + " Using default value.");
        expandedTptPort = Utils.DEFAULT_TPT_PORT;
      }
    } else {
      expandedTptPort = Utils.DEFAULT_TPT_PORT;
    }
    String expandedTptBindingName;
    if (tptBindingName != null && !tptBindingName.isEmpty()) {
      expandedTptBindingName = expand(environment, tptBindingName);
    } else {
      expandedTptBindingName = DescriptorImpl.getDefaultTptBindingName();
    }
    long expandedTptStartupWaitTime;
    if (tptStartUpWaitTime != null && !tptStartUpWaitTime.isEmpty()) {
      try {
        expandedTptStartupWaitTime =
            Integer.parseInt(expand(environment, tptStartUpWaitTime)) * 1000;
      } catch (NumberFormatException e) {
        logger.error("The given TPT startup waiting time " + expand(environment, tptStartUpWaitTime)
            + " is not an integer. Using default value.");
        expandedTptStartupWaitTime = Utils.DEFAULT_STARTUP_WAIT_TIME * 1000;
      }
    } else {
      expandedTptStartupWaitTime = Utils.DEFAULT_STARTUP_WAIT_TIME * 1000;
    }

    String jobName = run.getParent().getName();
    WorkLoad workloadToDo = WorkLoad.pollWorkload(jobName);
    if (workloadToDo == null) {
      logger.error("Nothing todo. No work package for \"" + jobName + "\" enqueued.");
      throw new AbortException();
    }

    JenkinsConfiguration unresolvedConfig = workloadToDo.getJenkinsConfig();
    List<String> testCasesFromWorkload = workloadToDo.getTestCases();
    Run distributingJobRun = workloadToDo.getDistributingJobRun();
    FilePath distributingJobWorkspace = workloadToDo.getDistributingJobWorkspace();
    FilePath distributingJobDataDir = workloadToDo.getDistributingJobDataDir();
    FilePath distributingJobReportDir = workloadToDo.getDistributingJobReportDir();

    // Replace $-Vars:
    JenkinsConfiguration resolvedConfig = unresolvedConfig.replaceAndNormalize(environment);

    logger.info("File Name :               " + resolvedConfig.getTptFile());
    logger.info("Execution Configuration : " + resolvedConfig.getConfiguration());
    logger.info("Test Data directory :     " + resolvedConfig.getTestdataDir());
    logger.info("Report directory :        " + resolvedConfig.getReportDir());
    logger.info("Test Cases :              " + testCasesFromWorkload);
    if (StringUtils.isNotEmpty(unresolvedConfig.getTestSet())) {
      logger.info("Test Set :                " + resolvedConfig.getTestSet());
    }
    for (FilePath f : expandedExePaths) {
      logger.info("Path to tpt.exe :         " + f.getRemote());
    }

    TptPluginWorkerJobExecutor executor = new TptPluginWorkerJobExecutor(launcher, workspace,
        listener, expandedExePaths, expandedArguments, expandedTptPort, expandedTptBindingName,
        resolvedConfig, testCasesFromWorkload, expandedTptStartupWaitTime, distributingJobRun,
        distributingJobWorkspace, distributingJobDataDir, distributingJobReportDir);

    boolean result = executor.execute();
    if (!result) {
      // reenqueue for new try if job is configured to try multiple times
      WorkLoad.putWorkLoad(jobName, workloadToDo);
      throw new AbortException();
    }
  }

  @Override
  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl)super.getDescriptor();
  }

  private String expand(@CheckForNull EnvVars env, String toExpand) {
    if (env == null) {
      return toExpand;
    } else {
      return env.expand(toExpand);
    }
  }

  // --------------------------- Descriptor Class -----------------------------------

  /**
   * The Descriptor of {@link TptPluginSlave}
   * 
   * @author jkuhnert, PikeTec GmbH
   */
  @Extension
  @Symbol("tptAgent")
  public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

    @Override
    public boolean isApplicable(Class< ? extends AbstractProject> jobType) {
      // all project types allowed
      return true;
    }

    @Override
    public String getDisplayName() {
      return "Execute TPT tests as a worker for a TPT distributing job";
    }

    /**
     * @return "${PIKECTEC_TPT_EXE}"
     */
    public static String getDefaultExePaths() {
      return "${" + Utils.TPT_EXE_VAR + "}";
    }

    /**
     * @return "TptApi"
     */
    public static String getDefaultTptBindingName() {
      return Utils.DEFAULT_TPT_BINDING_NAME;
    }

    /**
     * @return "1099"
     */
    public static String getDefaultTptPort() {
      return String.valueOf(Utils.DEFAULT_TPT_PORT);
    }

    /**
     * @return "60" (1 min)
     */
    public static String getDefaultTptStartUpWaitTime() {
      return String.valueOf(Utils.DEFAULT_STARTUP_WAIT_TIME);
    }

  }

}
