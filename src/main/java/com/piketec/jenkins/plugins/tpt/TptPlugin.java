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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

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
import hudson.util.FormValidation;

/**
 * This class is just a data container for the TPTPlugin configuration in Jenkins. <br>
 * If you use this Jenkins plugin you have two Options to run TPT-Test. The first option is just to
 * run TPT via command line and execute the tests. The second option is to execute the tests via
 * API. In this case for every testcase a single slave job will be started. This slvae job must have
 * a proper configured TptPluginSlave Build step. Master-slave execution was was introduced in the
 * year 2016.
 */
public class TptPlugin extends Builder {

  private transient String exe; // deprecated, replaced by exePaths

  private String exePaths;

  private final String arguments;

  private boolean isTptMaster;

  private String slaveJob;

  private String slaveJobCount;

  private String slaveJobTries;

  private String tptBindingName;

  private String tptPort;

  private String tptStartUpWaitTime;

  private final ArrayList<JenkinsConfiguration> executionConfiguration;

  private transient TptLogger logger;

  // --------------------- DATA BINDING -----------------------------------------

  @DataBoundConstructor
  public TptPlugin(String exe, String exePaths, String arguments, boolean isTptMaster,
                   String slaveJob, String slaveJobCount, String slaveJobTries,
                   String tptBindingName, String tptPort,
                   ArrayList<JenkinsConfiguration> executionConfiguration,
                   String tptStartUpWaitTime) {
    this.exePaths = exe;
    if (exePaths != null) {
      this.exePaths = exePaths;
    }
    this.arguments = arguments;
    this.isTptMaster = isTptMaster;
    this.slaveJob = slaveJob;
    this.slaveJobCount = slaveJobCount;
    this.slaveJobTries = slaveJobTries;
    this.tptBindingName = tptBindingName;
    this.tptPort = tptPort;
    this.tptStartUpWaitTime = tptStartUpWaitTime;

    this.executionConfiguration = new ArrayList<JenkinsConfiguration>();

    if (executionConfiguration != null) {
      this.executionConfiguration.addAll(executionConfiguration);

    }

  }

  protected Object readResolve() {
    if (tptBindingName == null) {
      tptBindingName = DescriptorImpl.getDefaultTptBindingName();
    }
    if (tptPort == null || tptPort.trim().isEmpty()) {
      tptPort = Integer.toString(DescriptorImpl.getDefaultTptPort());
    }
    if (exe != null) {
      this.exePaths = exe;
    }
    if (slaveJobCount == null) {
      slaveJobCount = "0";
    }
    if (slaveJobTries == null) {
      slaveJobTries = "1";
    }
    return this;
  }

  /**
   * @return The list of paths to possible TPT-installations.
   */
  public String getExePaths() {
    return exePaths;
  }

  /**
   * @return Should testcase execution be deligated to a slave job
   */
  public boolean getIsTptMaster() {
    return isTptMaster;
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
    return slaveJob;
  }

  /**
   * @return The number of slave job the plugin will run in master mode. A value below 1 means every
   *         test case will be started in its own job.
   */
  public String getSlaveJobCount() {
    return slaveJobCount;
  }

  /**
   * @return If the execution of a slave job fails it is possible to reschedule the job for another
   *         try. This is the maximal number of tries.
   */
  public String getSlaveJobTries() {
    return slaveJobTries;
  }

  /**
   * @return the RMI binding name for TPT
   */
  public String getTptBindingName() {
    return tptBindingName;
  }

  /**
   * @return The port of the RMI registry
   */
  public String getTptPort() {
    return tptPort;
  }

  /**
   * Common command line opts. Delimiter between the options is one or more spaces. Inside
   * doublequotes spaces have no special meaning.
   * 
   * @return 0 or more options for tpt.
   */
  public String getArguments() {
    return arguments;
  }

  /**
   * @return The time waited before trying to get the API handle after starting TPT
   */
  public String getTptStartUpWaitTime() {
    return tptStartUpWaitTime;
  }

  /**
   * @return List of all (repeatable) sub-configurations
   */
  public List<JenkinsConfiguration> getExecutionConfiguration() {
    return Collections.unmodifiableList(this.executionConfiguration);
  }

  // --------------------------------------------------------------

  /**
   * Executes this Build-Step. Expands variables and starts the standalone or master executor.
   * 
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  public boolean perform(AbstractBuild< ? , ? > build, Launcher launcher, BuildListener listener)
      throws InterruptedException, IOException {
    logger = new TptLogger(listener.getLogger());
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
    ArrayList<JenkinsConfiguration> normalizedConfigs = new ArrayList<JenkinsConfiguration>();
    for (JenkinsConfiguration ec : executionConfiguration) {
      normalizedConfigs.add(ec.replaceAndNormalize(environment));
    }
    if (isTptMaster) {
      return performAsMaster(build, launcher, listener, environment, normalizedConfigs);
    } else {
      return performWithoutSlaves(build, launcher, listener, environment, normalizedConfigs);
    }

  }

  public boolean performWithoutSlaves(AbstractBuild< ? , ? > build, Launcher launch,
                                      BuildListener listener, EnvVars environment,
                                      ArrayList<JenkinsConfiguration> normalizedConfigs) {
    // split and expand list of ptahs to TPT installations
    String[] expandedStringExePaths = environment.expand(exePaths).split("[,;]");
    FilePath[] expandedExePaths = new FilePath[expandedStringExePaths.length];
    for (int i = 0; i < expandedStringExePaths.length; i++) {
      expandedExePaths[i] =
          new FilePath(launch.getChannel(), environment.expand(expandedStringExePaths[i].trim()));
    }
    // expand arguments and report
    String expandedArguments = environment.expand(this.arguments);
    // start execution
    TptPluginSingleJobExecutor executor = new TptPluginSingleJobExecutor(build, launch, listener,
        expandedExePaths, expandedArguments, normalizedConfigs);
    return executor.execute();
  }

  public boolean performAsMaster(AbstractBuild< ? , ? > build, Launcher launcher,
                                 BuildListener listener, EnvVars environment,
                                 ArrayList<JenkinsConfiguration> normalizedConfigs) {
    // split and expand list of ptahs to TPT installations
    String[] expandedStringExePaths = environment.expand(exePaths).split("[,;]");
    FilePath[] expandedExePaths = new FilePath[expandedStringExePaths.length];
    for (int i = 0; i < expandedStringExePaths.length; i++) {
      expandedExePaths[i] =
          new FilePath(launcher.getChannel(), environment.expand(expandedStringExePaths[i].trim()));
    }
    // expand and parse TPT RMI port
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
    // expand TPT RMI binding name
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
        logger
            .error("The given TPT startup waiting time \"" + environment.expand(tptStartUpWaitTime)
                + "\" is not an integer. Using default value.");
        expandedTptStartupWaitTime = DescriptorImpl.getDefaultTptStartUpWaitTime() * 1000;
      }
    } else {
      expandedTptStartupWaitTime = DescriptorImpl.getDefaultTptStartUpWaitTime() * 1000;
    }
    // expand slaveJobCount
    int parsedSlaveJobCount = 0;
    if (slaveJobCount != null && !slaveJobCount.isEmpty()) {
      try {
        parsedSlaveJobCount = Integer.parseInt(environment.expand(slaveJobCount));
      } catch (NumberFormatException e) {
        logger.error("The given number of slave jobs to start \""
            + environment.expand(slaveJobCount) + "\" is not an integer. Using default value.");
      }
    }
    // expand slaveJobTries
    int parsedSlaveJobTries = 1;
    if (slaveJobTries != null && !slaveJobTries.isEmpty()) {
      try {
        parsedSlaveJobTries = Integer.parseInt(environment.expand(slaveJobTries));
      } catch (NumberFormatException e) {
        logger.error("The given number of tries to execute a slave jobs \""
            + environment.expand(slaveJobCount) + "\" is not an integer. Using default value.");
      }
    }
    // expand other variables
    String expandedSlaveJobName = environment.expand(slaveJob);
    // start execution
    TptPluginMasterJobExecutor executor = new TptPluginMasterJobExecutor(build, launcher, listener,
        expandedExePaths, normalizedConfigs, expandedTptPort, expandedTptBindingName,
        expandedSlaveJobName, expandedTptStartupWaitTime, parsedSlaveJobCount, parsedSlaveJobTries);
    return executor.execute();
  }

  @Override
  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl)super.getDescriptor();
  }

  // --------------------------- Descriptor Class -----------------------------------

  @Extension
  public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

    public static String getDefaultArguments() {
      return "--run build";
    }

    public static String getDefaultTptBindingName() {
      return "TptApi";
    }

    public static boolean getDefaultIsTptMaster() {
      return false;
    }

    public static int getDefaultTptPort() {
      return Utils.DEFAULT_TPT_PORT;
    }

    public static int getDefaultTptStartUpWaitTime() {
      return Utils.DEFAULT_STARTUP_WAIT_TIME;
    }

    public static FormValidation doCheckArguments(@QueryParameter String arguments) {
      FormValidation formValidator = null;

      if ((arguments == null) || (arguments.trim().length() == 0)) {
        formValidator = FormValidation.error("At least type \"--run build\".");
      } else {
        formValidator = FormValidation.ok();
      }

      return formValidator;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isApplicable(Class< ? extends AbstractProject> jobType) {
      // all project types allowed
      return true;
    }

    @Override
    public String getDisplayName() {
      return "Execute TPT test cases";
    }
  }
}
