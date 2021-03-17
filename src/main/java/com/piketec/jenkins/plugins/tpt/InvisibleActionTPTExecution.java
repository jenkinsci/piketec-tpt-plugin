package com.piketec.jenkins.plugins.tpt;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

import hudson.model.InvisibleAction;

/**
 * Invisible action to store data about executed {@link JenkinsConfiguration} to make them available
 * later for report generation.
 *
 */
public class InvisibleActionTPTExecution extends InvisibleAction {

  private final String id;

  private final String tptFile;

  private final String configuration;

  private final String testDataDir;

  private final String reportDir;

  /**
   * @param cfg
   *          A {@link JenkinsConfiguration} with all variables expanded
   */
  public InvisibleActionTPTExecution(JenkinsConfiguration cfg) {
    id = cfg.getId();
    tptFile = cfg.getTptFile();
    configuration = cfg.getConfiguration();
    testDataDir = Utils.getGeneratedTestDataDir(cfg);
    reportDir = Utils.getGeneratedReportDir(cfg);
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the tptFile
   */
  public String getTptFile() {
    return tptFile;
  }

  /**
   * @return the configuration
   */
  public String getConfiguration() {
    return configuration;
  }

  /**
   * @return the testDataDir
   */
  public String getTestDataDir() {
    return testDataDir;
  }

  /**
   * @return the reportDir
   */
  public String getReportDir() {
    return reportDir;
  }

}
