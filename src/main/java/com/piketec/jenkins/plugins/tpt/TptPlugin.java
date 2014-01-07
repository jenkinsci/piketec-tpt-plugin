package com.piketec.jenkins.plugins.tpt;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.Proc;
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

  private final File exe;

  private final String arguments;

  private final String report;

  private final ArrayList<JenkinsConfiguration> executionConfiguration;

  private transient BuildListener listener;

  @DataBoundConstructor
  public TptPlugin(File exe, String arguments,
                   ArrayList<JenkinsConfiguration> executionConfiguration, String report) {
    this.exe = exe;
    this.arguments = arguments;
    this.executionConfiguration = new ArrayList<JenkinsConfiguration>();

    if (executionConfiguration != null) {
      this.executionConfiguration.addAll(executionConfiguration);
    }
    this.report = report;
    this.listener = null;
  }

  /**
   * Tpt exe path.
   * 
   * @return The path to the tpt.exe.
   */
  public File getExe() {
    return exe;
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

    for (JenkinsConfiguration ec : executionConfiguration) {

      if (ec.isEnableTest()) {
        File dataDir = JenkinsConfiguration.getAbsolutePath(workspaceDir, ec.getTestdataDir());
        File reportDir = JenkinsConfiguration.getAbsolutePath(workspaceDir, ec.getReportDir());
        File tptFile = JenkinsConfiguration.getAbsolutePath(workspaceDir, ec.getTptFile());
        info("*** Running TPT-File \"" + ec.getTptFileName() + //
            "\" with configuration \"" + ec.getConfiguration() + "\" now. ***");

        if (createParentDir(dataDir) && createParentDir(reportDir)) {
          String cmd = buildCommand(tptFile, dataDir, reportDir, ec);

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
  private String buildCommand(File tptFile, File dataDir, File reportDir, JenkinsConfiguration ec) {
    StringBuilder cmd = new StringBuilder();
    cmd.append('"').append(exe).append("\" ").append(arguments).append(" \"").append(tptFile)
        .append("\" \"").append(ec.getConfiguration()).append("\" --dataDir \"").append(dataDir)
        .append("\" --reportDir \"").append(reportDir).append('"');

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
      // int exitcode = tpt.joinWithTimeout(1, TimeUnit.MINUTES, listener);
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
    File workspaceDir = getWorkspaceDir(workspace);
    File reportDir = new File(workspaceDir, report);

    if (!(reportDir.isDirectory() || reportDir.mkdirs())) {
      throw new IOException("Could not create report directory \"" + reportDir + "\"");
    }
    Publish.publishJUnitResults(workspaceDir, reportDir, ec, "testcase_information.xml", this);
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

    // file tests
    public static FormValidation doCheckExe(@QueryParameter File exe) {
      if (!exe.exists()) {
        return FormValidation.error("Set the path of the tpt.exe file."
            + " If the path contains spaces, the path must be enclosed by double quotation marks.");
      }
      return FormValidation.ok();
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
