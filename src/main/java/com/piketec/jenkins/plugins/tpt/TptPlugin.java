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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.CheckForNull;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import com.piketec.jenkins.plugins.tpt.TptLog.LogLevel;
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
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import jenkins.tasks.SimpleBuildStep;

/**
 * This class is just a data container for the TPTPlugin configuration in Jenkins. <br>
 * If you use this Jenkins plugin you have two Options to run TPT-Test. The first option is just to
 * run TPT via command line and execute the tests. The second option is to execute the tests via
 * API. In this case for every testcase a single slave job will be started. This slvae job must have
 * a proper configured TptPluginSlave Build step. Master-slave execution was was introduced in the
 * year 2016.
 */
public class TptPlugin extends Builder implements SimpleBuildStep {

  public static final String RUN_BUILD = "--run build";

  @CheckForNull
  private transient String exe; // deprecated, replaced by exePaths

  @CheckForNull
  private String exePaths = null;

  @CheckForNull
  private String arguments = null;

  private boolean isTptMaster = DescriptorImpl.getDefaultIsTptMaster();

  @CheckForNull
  private String slaveJob = null;

  @CheckForNull
  private String slaveJobCount = null;

  @CheckForNull
  private String slaveJobTries = null;

  @CheckForNull
  private String tptBindingName = null;

  @CheckForNull
  private String tptPort = null;

  @CheckForNull
  private String tptStartUpWaitTime = null;

  // null = old version where the behaviour could not be turned off -> enable in read resolve
  private Boolean enableJunit = null;

  @CheckForNull
  private String jUnitreport; // JUnit Report!

  @CheckForNull
  private LogLevel jUnitLogLevel = null;

  private final ArrayList<JenkinsConfiguration> executionConfiguration;

  private transient TptLogger logger;

  // --------------------- DATA BINDING -----------------------------------------

  /**
   * All the parameter are processed and then they are passed to TptPluginSingleJobExecutor or to
   * TptPluginMasterJobExecutor
   * 
   * @param exePaths
   *          paths to tpt executables separated by a comma or a semicolon
   * @param executionConfiguration
   *          all the jenkins configurations given in the descriptor, used to get the
   *          Files,Execution Configuration, test Set, testDataDir, reportDir,etc
   */
  @DataBoundConstructor
  public TptPlugin(String exePaths, ArrayList<JenkinsConfiguration> executionConfiguration) {
    if (exePaths != null) {
      this.exePaths = Util.fixEmpty(exePaths);
    }
    this.executionConfiguration = new ArrayList<>();
    if (executionConfiguration != null) {
      this.executionConfiguration.addAll(executionConfiguration);
    }
  }

  /**
   * This method is used to persist the data format when upgrading the plugin.
   * 
   * @return this
   * 
   */
  protected Object readResolve() {
    if (tptBindingName == null) {
      tptBindingName = DescriptorImpl.getDefaultTptBindingName();
    }
    if (tptPort == null || tptPort.trim().isEmpty()) {
      tptPort = DescriptorImpl.getDefaultTptPort();
    }
    if (exe != null) {
      this.exePaths = exe;
    }
    if (enableJunit == null) {
      enableJunit = Boolean.TRUE;
    }
    if (arguments != null && arguments.contains(TptPlugin.RUN_BUILD)) {
      arguments = arguments.replaceAll("\\s*" + TptPlugin.RUN_BUILD + "\\s*", " ");
      arguments = arguments.trim();
    }
    return this;
  }

  /**
   * @return The list of paths to possible TPT-installations.
   */
  public String getExePaths() {
    return Util.fixNull(exePaths);
  }

  /**
   * Deprecated, use exePaths instead
   * 
   * @param exe
   *          The path to the tpt.exe
   * 
   */
  @Deprecated
  public void setExe(String exe) {
    if (exe != null) {
      this.exe = exe;
    }
  }

  /**
   * @return Should testcase execution be delegated to a sub job
   */
  public boolean getIsTptMaster() {
    return isTptMaster;
  }

  /**
   * Should testcase execution be delegated to a sub job or is this run as a single job.
   * 
   * @param isTptMaster
   *          <code>true</code> if the execution should be
   */
  @DataBoundSetter
  public void setIsTptMaster(boolean isTptMaster) {
    this.isTptMaster = isTptMaster;
  }

  /**
   * @return Should testcase execution be deligated to a slave job
   */
  public boolean isIsTptMaster() {
    return isTptMaster;
  }

  /**
   * @return The name of slave job if the plugin runs in master mode
   */
  public String getSlaveJob() {
    return Util.fixNull(slaveJob);
  }

  /**
   * @param slaveJob
   *          The name of slave job if the plugin runs in master mode
   */
  @DataBoundSetter
  public void setSlaveJob(String slaveJob) {
    this.slaveJob = Util.fixEmpty(slaveJob);
  }

  /**
   * @return The number of slave job the plugin will run in master mode. A value below 1 means every
   *         test case will be started in its own job.
   */
  public String getSlaveJobCount() {
    return slaveJobCount == null ? DescriptorImpl.DEFAULT_SLAVE_JOB_COUNT : slaveJobCount;
  }

  /**
   * @param slaveJobCount
   *          The number of slave job the plugin will run in master mode. A value below 1 means
   *          every test case will be started in its own job.
   */
  @DataBoundSetter
  public void setSlaveJobCount(String slaveJobCount) {
    this.slaveJobCount =
        DescriptorImpl.DEFAULT_SLAVE_JOB_COUNT.equals(slaveJobCount) ? null : slaveJobCount;
  }

  /**
   * @return If the execution of a slave job fails it is possible to reschedule the job for another
   *         try. This is the maximal number of tries.
   */
  public String getSlaveJobTries() {
    return slaveJobTries == null ? DescriptorImpl.DEFAULT_SLAVE_JOB_TRIES : slaveJobTries;
  }

  /**
   * @param slaveJobTries
   *          If the execution of a slave job fails it is possible to reschedule the job for another
   *          try. This is the maximal number of tries.
   */
  @DataBoundSetter
  public void setSlaveJobTries(String slaveJobTries) {
    this.slaveJobTries =
        DescriptorImpl.DEFAULT_SLAVE_JOB_TRIES.equals(slaveJobTries) ? null : slaveJobTries;
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
   * @return The time waited before trying to get the API handle after starting TPT
   */
  public String getTptStartUpWaitTime() {
    return tptStartUpWaitTime == null ? DescriptorImpl.getDefaultTptStartUpWaitTime()
        : tptStartUpWaitTime;
  }

  /**
   * @param tptStartUpWaitTime
   *          the time waited before trying to get the API handle after starting TPT
   */
  @DataBoundSetter
  public void setTptStartUpWaitTime(String tptStartUpWaitTime) {
    this.tptStartUpWaitTime =
        DescriptorImpl.getDefaultTptStartUpWaitTime().equals(tptStartUpWaitTime) ? null
            : tptStartUpWaitTime;
  }

  /**
   * @return List of all (repeatable) sub-configurations
   */
  public List<JenkinsConfiguration> getExecutionConfiguration() {
    return Collections.unmodifiableList(this.executionConfiguration);
  }

  /**
   * @return if the TPT test result should be transformed into a JUnit XML (legacy behaviour)
   */
  public boolean isEnableJunit() {
    return Boolean.TRUE.equals(enableJunit);
  }

  /**
   * @param enableJunit
   *          if the TPT test result should be transformed into a JUnit XML (legacy behaviour)
   */
  @DataBoundSetter
  public void setEnableJunit(boolean enableJunit) {
    this.enableJunit = enableJunit;
  }

  /**
   * Report dir (optional).
   * 
   * @return The directory, where to store the results, can be <code>null</code>.
   */
  public String getJUnitreport() {
    return Util.fixEmpty(jUnitreport);
  }

  /**
   * @param jUnitreport
   *          The directory, where to store the results, can be <code>null</code>.
   */
  @DataBoundSetter
  public void setjUnitreport(String jUnitreport) {
    this.jUnitreport = Util.fixNull(jUnitreport);
  }

  /**
   * The severity level of TPT log messages that will be written to failed JUnit tests.
   * 
   * @return The severity level of TPT log messages that will be written to failed JUnit tests.
   */
  public LogLevel getJUnitLogLevel() {
    return jUnitLogLevel == null ? DescriptorImpl.getDefaultJUnitLogLevel() : jUnitLogLevel;
  }

  /**
   * @param jUnitLogLevel
   *          The severity level of TPT log messages that will be written to failed JUnit tests.
   */
  @DataBoundSetter
  public void setjUnitLogLevel(LogLevel jUnitLogLevel) {
    this.jUnitLogLevel =
        DescriptorImpl.getDefaultJUnitLogLevel().equals(jUnitLogLevel) ? null : jUnitLogLevel;
  }

  // --------------------------------------------------------------

  @Override
  public void perform(Run< ? , ? > run, FilePath workspace, EnvVars env, Launcher launcher,
                      TaskListener listener)
      throws InterruptedException, IOException {
    logger = new TptLogger(listener.getLogger());
    boolean result = false;
    if (isTptMaster) {
      result = performAsMaster(run, workspace, launcher, listener, env, executionConfiguration);
    } else {
      result =
          performWithoutSlaves(run, workspace, launcher, listener, env, executionConfiguration);
    }
    if (!result) {
      throw new AbortException();
    }
  }

  /**
   * Get the required data to create a TptPluginSingleJobExecutor and excecutes it. It is called
   * when there are no distributed builds.
   * 
   * All the parameters are used to get the data for creating a new TptPluginSingleJobExecutor
   * 
   * @param run
   *          The current Jenkins build
   * @param launcher
   *          The launcher
   * @param listener
   *          The listener
   * @param environment
   *          The map of envrionment varibales and their value
   * @param configs
   *          The configs with unresolved $-variables
   * @return true if it was possible to execute the TptPluginSingleJobExecutor.
   * @throws InterruptedException
   *           If thread was interrupted
   */
  public boolean performWithoutSlaves(Run< ? , ? > run, FilePath workspace, Launcher launcher,
                                      TaskListener listener, EnvVars environment,
                                      ArrayList<JenkinsConfiguration> configs)
      throws InterruptedException, IOException {
    // split and expand list of ptahs to TPT installations
    String[] expandedStringExePaths = environment.expand(getExePaths()).split("[,;]");
    FilePath[] expandedExePaths = new FilePath[expandedStringExePaths.length];
    if (workspace == null) {
      logger.error("No workspace available");
      return false;
    }
    for (int i = 0; i < expandedStringExePaths.length; i++) {
      expandedExePaths[i] =
          new FilePath(workspace, environment.expand(expandedStringExePaths[i].trim()));
    }
    // expand arguments and report
    String expandedArguments = environment.expand(getArguments());
    String jUnitXmlPath = environment.expand(getJUnitreport());
    // start execution
    TptPluginSingleJobExecutor executor =
        new TptPluginSingleJobExecutor(run, workspace, launcher, listener, expandedExePaths,
            expandedArguments, configs, jUnitXmlPath, getJUnitLogLevel(), isEnableJunit());
    return executor.execute();

  }

  /**
   * Get the required data to create a TptPluginMasterJobExecutor and excecutes it. It is called
   * when there are distributed builds. @see the execute() method from TptPluginMasterJobExecutor.
   * 
   * All the parameters are used to get the data for creating a new TptPluginMasterJobExecutor
   * 
   * @param build
   *          The current Jenkins build
   * @param launcher
   *          The launcher
   * @param listener
   *          The listener
   * @param environment
   *          The map of envrionment varibales and their value
   * @param configs
   *          The configs with unresolved $-variables
   * @return true if the execution from slaves and master were successful.
   * @throws InterruptedException
   *           If thread was interrupted
   */
  public boolean performAsMaster(Run< ? , ? > build, FilePath workspace, Launcher launcher,
                                 TaskListener listener, EnvVars environment,
                                 ArrayList<JenkinsConfiguration> configs)
      throws InterruptedException {
    // split and expand list of paths to TPT installations
    String[] expandedStringExePaths = environment.expand(getExePaths()).split("[,;]");
    FilePath[] expandedExePaths = new FilePath[expandedStringExePaths.length];
    if (workspace == null) {
      logger.error("No workspace available");
      return false;
    }
    for (int i = 0; i < expandedStringExePaths.length; i++) {
      expandedExePaths[i] =
          new FilePath(workspace, environment.expand(expandedStringExePaths[i].trim()));
    }
    String expandedArguments = environment.expand(getArguments());
    String jUnitXmlPath = environment.expand(getJUnitreport());
    // expand and parse TPT RMI port
    int expandedTptPort;
    String tptPort = getTptPort();
    if (!tptPort.isEmpty()) {
      try {
        expandedTptPort = Integer.parseInt(environment.expand(tptPort));
      } catch (NumberFormatException e) {
        logger.error("The given port " + environment.expand(tptPort) + " is not an integer."
            + " Using default value.");
        expandedTptPort = Utils.DEFAULT_TPT_PORT;
      }
    } else {
      expandedTptPort = Utils.DEFAULT_TPT_PORT;
    }
    // expand TPT RMI binding name
    String expandedTptBindingName;
    String tptBindingName = getTptBindingName();
    if (!tptBindingName.isEmpty()) {
      expandedTptBindingName = environment.expand(tptBindingName);
    } else {
      expandedTptBindingName = DescriptorImpl.getDefaultTptBindingName();
    }
    long expandedTptStartupWaitTime;
    String tptStartUpWaitTime = getTptStartUpWaitTime();
    if (tptStartUpWaitTime.isEmpty()) {
      try {
        expandedTptStartupWaitTime =
            Integer.parseInt(environment.expand(tptStartUpWaitTime)) * 1000;
      } catch (NumberFormatException e) {
        logger
            .error("The given TPT startup waiting time \"" + environment.expand(tptStartUpWaitTime)
                + "\" is not an integer. Using default value.");
        expandedTptStartupWaitTime = Utils.DEFAULT_STARTUP_WAIT_TIME * 1000;
      }
    } else {
      expandedTptStartupWaitTime = Utils.DEFAULT_STARTUP_WAIT_TIME * 1000;
    }
    // expand slaveJobCount
    int parsedSlaveJobCount = 1;
    String slaveJobCount = getSlaveJobCount();
    if (!slaveJobCount.isEmpty()) {
      try {
        parsedSlaveJobCount = Integer.parseInt(environment.expand(slaveJobCount));
      } catch (NumberFormatException e) {
        logger.error("The given number of slave jobs to start \""
            + environment.expand(slaveJobCount) + "\" is not an integer. Using default value.");
      }
    }
    // expand slaveJobTries
    int parsedSlaveJobTries = 1;
    String slaveJobTries = getSlaveJobTries();
    if (!slaveJobTries.isEmpty()) {
      try {
        parsedSlaveJobTries = Integer.parseInt(environment.expand(slaveJobTries));
      } catch (NumberFormatException e) {
        logger.error("The given number of tries to execute a slave jobs \""
            + environment.expand(slaveJobCount) + "\" is not an integer. Using default value.");
      }
    }
    // expand other variables
    String expandedSlaveJobName = environment.expand(getSlaveJob());
    // start execution
    TptPluginMasterJobExecutor executor =
        new TptPluginMasterJobExecutor(build, workspace, launcher, listener, expandedExePaths,
            expandedArguments, configs, expandedTptPort, expandedTptBindingName,
            expandedSlaveJobName, expandedTptStartupWaitTime, parsedSlaveJobCount,
            parsedSlaveJobTries, jUnitXmlPath, getJUnitLogLevel(), isEnableJunit());
    try {
      return executor.execute();
    } finally {
      // clean the workload, if the process is interrupted it removes the workload that did not
      // execute.
      WorkLoad.clean(expandedSlaveJobName, build);
    }
  }

  @Override
  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl)super.getDescriptor();
  }

  // --------------------------- Descriptor Class -----------------------------------

  /**
   * The descriptor of TptPlugin
   * 
   * @author jkuhnert, PikeTec GmbH
   */
  @Extension
  @Symbol("tptExecute")
  public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

    public static final String DEFAULT_SLAVE_JOB_COUNT = "1";

    public static final String DEFAULT_SLAVE_JOB_TRIES = "1";

    /**
     * @return "TptApi"
     */
    public static String getDefaultTptBindingName() {
      return "TptApi";
    }

    /**
     * @return <code>false</code>
     */
    public static boolean getDefaultIsTptMaster() {
      return false;
    }

    /**
     * @return "1099"
     */
    public static String getDefaultTptPort() {
      return Integer.toString(Utils.DEFAULT_TPT_PORT);
    }

    /**
     * @return "60" (1 min)
     */
    public static String getDefaultTptStartUpWaitTime() {
      return Integer.toString(Utils.DEFAULT_STARTUP_WAIT_TIME);
    }

    /**
     * @return <code>false</code>
     */
    public static boolean getDefaultEnableJunit() {
      return false;
    }

    /**
     * @return <code>INFO</code>
     */
    public static LogLevel getDefaultJUnitLogLevel() {
      return LogLevel.INFO;
    }

    /**
     * Basic validation of the entered paths to tpt.exe. At least one must exist.
     * 
     * @param exePaths
     *          The tpt installation paths
     * @return The validation of the form
     */
    public static FormValidation doCheckExePaths(@QueryParameter String exePaths) {
      if ((exePaths == null) || (exePaths.trim().length() == 0)) {
        return FormValidation.error("Enter at least one path to a tpt.exe");
      } else {
        return FormValidation.ok();
      }
    }

    @Override
    public boolean isApplicable(Class< ? extends AbstractProject> jobType) {
      // all project types allowed
      return true;
    }

    @Override
    public String getDisplayName() {
      return "Execute TPT test cases";
    }

    /**
     * Makes the combobox list on the descriptor with all the possible options for the JunitLogLevel
     * 
     * @return items from the combobox list
     */
    public ListBoxModel doFillJUnitLogLevelItems() {
      ListBoxModel items = new ListBoxModel();
      for (LogLevel goal : LogLevel.values()) {
        items.add(goal.name());
      }
      return items;
    }
  }

}
