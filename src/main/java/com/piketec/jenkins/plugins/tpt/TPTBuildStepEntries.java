package com.piketec.jenkins.plugins.tpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;

public class TPTBuildStepEntries {

  private static Map<AbstractBuild< ? , ? >, List<JenkinsConfiguration>> entries = new HashMap<>();

  public static synchronized void addEntry(List<JenkinsConfiguration> configs,
                                           AbstractBuild< ? , ? > build) {

    List<JenkinsConfiguration> currentEntriesForWorkspace = entries.get(build);
    if (currentEntriesForWorkspace == null) {
      currentEntriesForWorkspace = new ArrayList<>();
    }
    currentEntriesForWorkspace.addAll(configs);
    entries.put(build, currentEntriesForWorkspace);
  }

  public static void cleanInvalidEntries() {
    List<AbstractBuild< ? , ? >> invalidEntries = new ArrayList<>();
    for (AbstractBuild< ? , ? > build : entries.keySet()) {
      Result result = build.getResult();
      if (result == null || result.isWorseThan(Result.UNSTABLE)) {
        invalidEntries.add(build);
      }
    }
    for (AbstractBuild< ? , ? > invalidEntry : invalidEntries) {
      entries.remove(invalidEntry);
    }
  }

  public static List<JenkinsConfiguration> getEntries(AbstractBuild< ? , ? > build) {
    return entries.get(build);
  }

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
