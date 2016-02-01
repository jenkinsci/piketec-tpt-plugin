package com.piketec.jenkins.plugins.tpt;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.Project;

public class CleanUpTask {

  private static Map<String, List<CleanUpTask>> registry = new HashMap<String, List<CleanUpTask>>();

  private Project prj;

  public CleanUpTask(Project prokject, String masterId) {
    prj = prokject;
    if (prj != null) {
      register(this, masterId);
    }
  }

  public boolean clean() throws RemoteException {
    try {
      return prj.closeProject();
    } catch (ApiException e) {
      // not open
      return true;
    }
  }

  private static synchronized void register(CleanUpTask task, String masterId) {
    List<CleanUpTask> list = registry.get(masterId);
    if (list == null) {
      list = new ArrayList<CleanUpTask>();
      registry.put(masterId, list);
    }
    list.add(task);
  }

  public static synchronized boolean cleanUp(String executionId) {
    List<CleanUpTask> tasks = registry.remove(executionId);
    if (tasks == null) {
      // nothing to clean up
      return true;
    }
    boolean success = true;
    for (CleanUpTask task : tasks) {
      try {
        success &= task.clean();
      } catch (RemoteException e) {
        success = false;
      }
    }
    return success;
  }
}
