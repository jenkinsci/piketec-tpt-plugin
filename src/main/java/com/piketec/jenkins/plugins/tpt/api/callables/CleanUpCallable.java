package com.piketec.jenkins.plugins.tpt.api.callables;

import java.rmi.RemoteException;
import java.rmi.UnknownHostException;

import org.jenkinsci.remoting.RoleChecker;

import com.piketec.jenkins.plugins.tpt.TptLogger;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.OpenResult;
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
    super(listener, hostName, tptPort, tptBindingName, exePaths, tptStartUpTime);
    this.tptFilePath = tptFilePath;
  }

  @Override
  public Boolean call() throws UnknownHostException {
    TptLogger logger = getLogger();
    TptApi api = getApi();
    if (api == null) {
      logger.error("Could not establish connection to the TPT API.");
      return false;
    }
    OpenResult openResult = getOpenProject(logger, api, tptFilePath);
    boolean success = false;
    try {
      success = openResult.getProject().closeProject();
    } catch (RemoteException | ApiException e) {
      logger.error("Closing Project " + tptFilePath.getName() + " did not work: " + e.getMessage());
      return false;
    }
    return success;
  }

  @Override
  public void checkRoles(RoleChecker arg0) throws SecurityException {
  }

  public FilePath getFilePath() {
    return tptFilePath;
  }
}
