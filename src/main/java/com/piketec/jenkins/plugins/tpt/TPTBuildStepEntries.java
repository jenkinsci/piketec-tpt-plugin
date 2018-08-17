package com.piketec.jenkins.plugins.tpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;

/**
 * A cache to collect executed TPT build steps to make them available for a TPT Report publishing
 * step even if they are hidden in e.g. a conditional build step.
 * 
 * @author Felipe Infantino, PikeTec GmbH
 */
public class TPTBuildStepEntries {

  private static Map<AbstractBuild< ? , ? >, List<JenkinsConfiguration>> entries = new HashMap<>();

  /**
   * Adds an entry to the cache
   * 
   * @param config
   *          The executed TPT configuration
   * @param build
   *          The build that executed the configuration
   */
  public static synchronized void addEntry(JenkinsConfiguration config,
                                           AbstractBuild< ? , ? > build) {

    List<JenkinsConfiguration> currentEntriesForWorkspace = entries.get(build);
    if (currentEntriesForWorkspace == null) {
      currentEntriesForWorkspace = new ArrayList<>();
    }
    currentEntriesForWorkspace.add(config);
    entries.put(build, currentEntriesForWorkspace);
  }

  /**
   * Get all TPT configurations that were run by the given build.
   * 
   * @param build
   *          The Jenkins build that could have executed some TPT configurations
   * @return <code>null</code> if no TPT configurations were (successfully) executed by the build or
   *         a list of the successfully executed TPT configurations.
   */
  public static List<JenkinsConfiguration> getEntries(AbstractBuild< ? , ? > build) {
    return entries.get(build);
  }

  /**
   * This run listenere cleans the cache if a build is finished.
   * 
   * @author Felipe Infantino, PikeTec GmbH
   */
  @Extension
  public static class RunListenerImpl extends RunListener<Run> {

    @Override
    public void onCompleted(Run r, TaskListener listener) {
      if (r instanceof AbstractBuild< ? , ? > && entries.containsKey(r)) {
        entries.remove(r);
      }
    }
  }

}
