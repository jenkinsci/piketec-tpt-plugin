package com.piketec.jenkins.plugins.tpt.api.callables;

import java.util.Collections;

import com.piketec.jenkins.plugins.tpt.TptLogger;
import com.piketec.tpt.api.TptApi;

import hudson.FilePath;
import hudson.model.TaskListener;

/**
 * This code is executed on a Jenkins Agent. It should close an open TPT Project.
 */
public class CleanUpCallable extends TptApiCallable<Boolean> {

  private static final long serialVersionUID = 1L;

  private FilePath tptFilePath;

  public CleanUpCallable(TaskListener listener, String hostName, int tptPort, String tptBindingName,
                         FilePath[] exePaths, long tptStartUpTime, FilePath tptFilePath) {
    super(listener, tptPort, tptBindingName, exePaths, Collections.emptyList(), tptStartUpTime);
    this.tptFilePath = tptFilePath;
  }

  @Override
  public Boolean call() throws InterruptedException {
    TptLogger logger = getLogger();
    TptApi api = getApiIfTptIsOpen();
    if (api == null) {
      logger.info("TPT is already closed.");
      return true;
    }
    return closeProject(logger, api, tptFilePath);
  }

  public FilePath getFilePath() {
    return tptFilePath;
  }
}
