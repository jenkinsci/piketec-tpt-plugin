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
package com.piketec.jenkins.plugins.tpt;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.Proc;
import hudson.Util;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

public class TptPlugin extends Builder implements TptLogger {

  private static final SimpleDateFormat DDMMYYHHMMSS = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

  private transient File exe;

  private String[] exePaths;

  private final String arguments;

  private final String report;

  private final ArrayList<JenkinsConfiguration> executionConfiguration;

  private transient BuildListener listener;

  @DataBoundConstructor
  public TptPlugin(String exe, String arguments,
                   ArrayList<JenkinsConfiguration> executionConfiguration, String report) {
    if (exe != null && !exe.isEmpty()) {
      this.exePaths = exe.split("[,;]");
    } else {
      this.exePaths = new String[0];
    }
    this.arguments = arguments;
    this.executionConfiguration = new ArrayList<JenkinsConfiguration>();

    if (executionConfiguration != null) {
      this.executionConfiguration.addAll(executionConfiguration);
    }
    this.report = report;
    this.listener = null;
  }

  protected Object readResolve() {
    if (exe != null) {
      exePaths = new String[] { exe.getPath() };
    }
    return this;
  }

  /**
   * Tpt exe path.
   * 
   * @return The path to the tpt.exe.
   */
  public String getExePaths() {
    StringBuilder b = new StringBuilder();
    for (String f : exePaths) {
      if (b.length() > 0) {
        b.append(", ");
      }
      b.append(f.trim());
    }
    return b.toString();
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
   * Report dir (optional).
   * 
   * @return The directory, where to store the results, can be <code>null</code>.
   */
  public String getReport() {
    return report;
  }

  public List<JenkinsConfiguration> getExecutionConfiguration() {
    return Collections.unmodifiableList(this.executionConfiguration);
  }

  @Override
  public void info(String msg) {
    listener.getLogger().println("[Info " + DDMMYYHHMMSS.format(new Date()) + "]: " + msg);
  }

  @Override
  public void error(String msg) {
    listener.getLogger().println("[Error " + DDMMYYHHMMSS.format(new Date()) + "]: " + msg);
  }

  @Override
  public void interrupt(String msg) {
    listener.getLogger().println("[Interrupt " + DDMMYYHHMMSS.format(new Date()) + "]: " + msg);
  }

  @Override
  public PrintStream getLogger() {
    return listener.getLogger();
  }

  /** durchlaeuft alle konfigurierten Testfiles, die nicht "skipped" sind */
  @Override
  public boolean perform(AbstractBuild< ? , ? > build, Launcher launch, BuildListener listener) {
    this.listener = listener;
    boolean success = true;
    FilePath workspace = build.getWorkspace();
    File workspaceDir = getWorkspaceDir(workspace);
    EnvVars environment;
    try {
      environment = build.getEnvironment(launch.getListener());
    } catch (IOException e1) {
      environment = new EnvVars();
      error(e1.getLocalizedMessage());
    } catch (InterruptedException e1) {
      interrupt(e1.getLocalizedMessage());
      return false;
    }
    FilePath exeFile = null;
    for (String f : exePaths) {
      exeFile =
          new FilePath(build.getBuiltOn().getChannel(), Util.replaceMacro(f.trim(), environment));
      try {
        if (exeFile.exists()) {
          break;
        }
      } catch (IOException e) {
        // NOP, just try next file
      } catch (InterruptedException e) {
        error("Interrupted");
        return false;
      }
    }
    try {
      if (exeFile == null || !exeFile.exists()) {
        error("No TPT installation found");
        return false;
      }
    } catch (IOException e) {
      error("No TPT installation found");
      return false;
    } catch (InterruptedException e) {
      error("Interrupted");
      return false;
    }
    String arguments = Util.replaceMacro(this.arguments, environment);

    for (JenkinsConfiguration ec : executionConfiguration) {

      if (ec.isEnableTest()) {
        File dataDir =
            JenkinsConfiguration.getAbsolutePath(workspaceDir, ec.getTestdataDir(), environment);
        File reportDir =
            JenkinsConfiguration.getAbsolutePath(workspaceDir, ec.getReportDir(), environment);
        File tptFile =
            JenkinsConfiguration.getAbsolutePath(workspaceDir, ec.getTptFile(), environment);
        String configurationName = Util.replaceMacro(ec.getConfiguration(), environment);
        info("*** Running TPT-File \"" + tptFile + //
            "\" with configuration \"" + configurationName + "\" now. ***");

        if (createParentDir(dataDir) && createParentDir(reportDir)) {
          String cmd =
              buildCommand(exeFile, arguments, tptFile, dataDir, reportDir, configurationName);

          try {
            // run the test...
            launchTPT(launch, cmd);
            // jetzt veroeffentlichen der Ergebnisse
            info("*** Publishing results now ***");
            publishResults(workspace, ec);
          } catch (IOException e) {
            error(e.getMessage());
            success = false;
            // continue with next config in case of I/O error
          } catch (InterruptedException e) {
            interrupt(e.getMessage());
            return false;
          }
        } else {
          error("Failed to create parent directories for " + dataDir + " and/or " + reportDir);
          success = false;
        }
      }
    }

    return success;
  }

  /**
   * @param ec
   * @return
   */
  private String buildCommand(FilePath exeFile, String arguments, File tptFile, File dataDir,
                              File reportDir, String configurationName) {
    StringBuilder cmd = new StringBuilder();
    String exeString = exeFile.getRemote();
    if (!exeString.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(exeString);
    if (!exeString.endsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(' ');
    cmd.append(arguments);
    cmd.append(' ');

    String tptFileString = tptFile.toString();
    if (!tptFileString.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(tptFileString);
    if (!tptFileString.endsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(' ');

    if (!configurationName.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(configurationName);
    if (!configurationName.endsWith("\"")) {
      cmd.append('"');
    }

    cmd.append(" --dataDir ");
    String dataDirString = dataDir.toString();
    if (!dataDirString.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(dataDirString);
    if (!dataDirString.endsWith("\"")) {
      cmd.append('"');
    }

    cmd.append(" --reportDir ");
    String reportDirString = reportDir.toString();
    if (!reportDirString.startsWith("\"")) {
      cmd.append('"');
    }
    cmd.append(reportDirString);
    if (!reportDirString.endsWith("\"")) {
      cmd.append('"');
    }

    return cmd.toString();
  }

  private void launchTPT(Launcher launcher, String cmd) throws InterruptedException, IOException {
    info("Launching \"" + cmd + "\"");
    Launcher.ProcStarter starter = launcher.new ProcStarter();
    starter.cmdAsSingleString(cmd);
    starter.stdout(listener.getLogger());
    starter.stderr(listener.getLogger());
    Proc tpt = null;
    try {
      tpt = starter.start();
      // allow 6h maximum
      int exitcode = tpt.joinWithTimeout(6, TimeUnit.HOURS, listener);
      // int exitcode = tpt.joinWithTimeout(1, TimeUnit.MINUTES,
      // listener);
      if (exitcode != 0) {
        error("TPT process stops with exit code " + exitcode);
      }
    } catch (IOException e) {
      throw new IOException("TPT launch error: " + e.getMessage());
    } catch (InterruptedException e) {
      try {
        tpt.kill();
      } catch (Exception e1) {
        throw new IOException(
            "TPT launch error: Interrupt requested, but cannot kill the TPT process. Please kill it manually.");
      }
      throw e;
    }
  }

  private void publishResults(FilePath workspace, JenkinsConfiguration ec) throws IOException {
    FilePath reportPath =
        ((report == null) || report.trim().isEmpty()) ? workspace : new FilePath(workspace, report);

    try {

      if (!reportPath.isDirectory()) {
        reportPath.mkdirs();

        if (!reportPath.isDirectory()) {
          throw new IOException("Could not create report directory \"" + reportPath + "\"");
        }
      }
    } catch (InterruptedException ie) {
      throw new IOException("failed to get the directory: " + reportPath, ie);
    }
    Publish.publishJUnitResults(workspace, reportPath, ec, "testcase_information.xml", this);
  }

  @Override
  public DescriptorImpl getDescriptor() {
    return (DescriptorImpl)super.getDescriptor();
  }

  private File getWorkspaceDir(FilePath workspace) {
    File workspaceDir = null;

    if (workspace == null) {
      info("location of the workspace is unknown");
    } else {

      try {
        workspaceDir = new File(workspace.toURI());
      } catch (IOException ioe) {
        info("Failed to get the workspace directory - reason: " + ioe);
      } catch (InterruptedException ie) {
        info("Failed to get the workspace directory - reason: " + ie);
      }
    }

    return workspaceDir;
  }

  private boolean createParentDir(File directory) {
    File parentDir = directory.getParentFile();

    return ((parentDir == null) || parentDir.isDirectory() || parentDir.mkdirs());
  }

  @Extension
  public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

    public static String getDefaultArguments() {
      return "--run build";
    }

    public static String getDefaultReport() {
      return "";
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
