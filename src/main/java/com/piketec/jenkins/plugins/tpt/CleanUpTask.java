package com.piketec.jenkins.plugins.tpt;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.Project;

public class CleanUpTask {

  private static Map<Object, List<CleanUpTask>> registry = new HashMap<Object, List<CleanUpTask>>();

  private Project prj;

  public CleanUpTask(Project prokject, Object masterId) {
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

  private static synchronized void register(CleanUpTask task, Object masterId) {
    List<CleanUpTask> list = registry.get(masterId);
    if (list == null) {
      list = new ArrayList<CleanUpTask>();
      registry.put(masterId, list);
    }
    list.add(task);
  }

  public static synchronized boolean cleanUp(Object masterId) {
    List<CleanUpTask> tasks = registry.remove(masterId);
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
