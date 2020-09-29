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

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

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

  private String tptBindingName;

  private String tptPort;

  private String tptStartUpWaitTime;

  // ----------- Data Binding --------------

  /**
   * 
   * Those arguments are processed and then passed to the TptPluginSlaveExecutor. This class is used
   * as a wrapper
   * 
   * @param exePaths
   *          the paths to the Tpt Executables
   * @param tptBindingName
   *          the binding name used to connect to the TptApi (for the registry)
   * @param tptPort
   *          the port for binding to the TptApi
   * @param tptStartUpWaitTime
   *          the time it should wait before start tpt
   */
  @DataBoundConstructor
  public TptPluginSlave(String exePaths, String tptBindingName, String tptPort,
                        String tptStartUpWaitTime) {
    this.exePaths = exePaths;
    this.tptBindingName = tptBindingName;
    this.tptPort = tptPort;
    this.tptStartUpWaitTime = tptStartUpWaitTime;
  }

  /**
   * @return the given paths to the tpt executable (tpt.exe)
   */
  public String getExePaths() {
    return exePaths;
  }

  /**
   * @return the given bindingname, used to connect to the api.
   */
  public String getTptBindingName() {
    return tptBindingName;
  }

  /**
   * @return the tpt port , used to make the connection to the TptApi
   */
  public String getTptPort() {
    return tptPort;
  }

  /**
   * @return The time waited before trying to get the API handle after starting TPT
   */
  public String getTptStartUpWaitTime() {
    return tptStartUpWaitTime;
  }

  // --------------------------------------------------------------

  /**
   * It collects the necesary data (tpt exe path, tpt Port, tpt bindingname and tpt
   * expandedTptStartupWaitTime) from the environment. Then collects the necesary data from the
   * workload (put by the TptPluginMasterJobExecutor). After collecting all the necesary data it
   * creates a new TptPluginSlaveExecutor and execute it . This method will be called from Jenkins
   * when a build for a slave is scheduled. @see retryableJob. The logic is that the retryableJob
   * schedules builds for the slaves and those builds will be executed here.
   * 
   * @return true if the slave executed successfully its workload.
   */
  @Override
  public boolean perform(AbstractBuild< ? , ? > build, Launcher launcher, BuildListener listener)
      throws InterruptedException, IOException {

    TptLogger logger = new TptLogger(listener.getLogger());
    EnvVars environment = Utils.getEnvironment(build, launcher, logger);
    String[] expandedStringExePaths = environment.expand(exePaths).split("[,;]");
    FilePath[] expandedExePaths = new FilePath[expandedStringExePaths.length];
    for (int i = 0; i < expandedExePaths.length; i++) {
      expandedExePaths[i] =
          new FilePath(build.getWorkspace(), environment.expand(expandedStringExePaths[i].trim()));
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

    String jobName = build.getProject().getName();
    WorkLoad workloadToDo = WorkLoad.pollWorkload(jobName);
    if (workloadToDo == null) {
      logger.error("Nothing todo. No work package for \"" + jobName + "\" enqueued.");
      return false;
    }

    JenkinsConfiguration unresolvedConfig = workloadToDo.getJenkinsConfig();
    List<String> testCasesFromWorkload = workloadToDo.getTestCases();
    AbstractBuild masterId = workloadToDo.getMasterId();
    FilePath masterWorkspace = workloadToDo.getMasterWorkspace();
    FilePath masterDataDir = workloadToDo.getMasterDataDir();
    FilePath masterReportDir = workloadToDo.getMasterReportDir();

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

    TptPluginSlaveExecutor executor =
        new TptPluginSlaveExecutor(launcher, build, listener, expandedExePaths, expandedTptPort,
            expandedTptBindingName, resolvedConfig, testCasesFromWorkload,
            expandedTptStartupWaitTime, masterId, masterWorkspace, masterDataDir, masterReportDir);

    boolean result = executor.execute();
    if (!result) {
      // reenqueue for new try if job is configured to try multiple times
      WorkLoad.putWorkLoad(jobName, workloadToDo);
    }
    return result;
  }

  @Override
  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl)super.getDescriptor();
  }

  // --------------------------- Descriptor Class -----------------------------------

  /**
   * The Descriptor of TptPluginSlave
   * 
   * @author jkuhnert, PikeTec GmbH
   */
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
    public static int getDefaultTptPort() {
      return Utils.DEFAULT_TPT_PORT;
    }

    /**
     * @return "60" (1 min)
     */
    public static int getDefaultTptStartUpWaitTime() {
      return Utils.DEFAULT_STARTUP_WAIT_TIME;
    }

  }

}
