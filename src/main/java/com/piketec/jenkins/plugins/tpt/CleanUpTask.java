/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2018 PikeTec GmbH
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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.Project;

import hudson.model.AbstractBuild;

/**
 * Task to execute after executing a builder. Usually done in a finally block
 * 
 * @author jkuhnert, PikeTec GmbH
 *
 */
public class CleanUpTask {

  private static Map<Object, List<CleanUpTask>> registry = new HashMap<Object, List<CleanUpTask>>();

  private Project prj;

  /**
   * @param project
   *          Tpt Project
   * @param masterId
   *          Abstractbuild as unique Id
   */
  public CleanUpTask(Project project, AbstractBuild masterId) {
    prj = project;
    if (prj != null) {
      register(this, masterId);
    }
  }

  /**
   * Close the tpt project
   * 
   * @return true if it was possible to close the project.
   */
  public boolean clean() {
    try {
      return prj.closeProject();
    } catch (ApiException e) {
      // not open
      return true;
    } catch (RemoteException e) {
      return false;

    }
  }

  /**
   * Adds a CleanUpTask to the registry
   * 
   * @param task
   *          which is going to be added
   * @param masterId
   *          to identify to which registry the task is going to be added
   */
  private static synchronized void register(CleanUpTask task, AbstractBuild masterId) {
    List<CleanUpTask> list = registry.get(masterId);
    if (list == null) {
      list = new ArrayList<CleanUpTask>();
      registry.put(masterId, list);
    }
    list.add(task);
  }

  /**
   * removes a list of CleanUpTask from the registry
   * 
   * @param masterId
   *          to identify to which registry the task is going to be removed
   * @return true if it was possible to erase the tasks.
   */
  public static synchronized boolean cleanUp(Object masterId) {
    List<CleanUpTask> tasks = registry.remove(masterId);
    if (tasks == null) {
      // nothing to clean up
      return true;
    }
    boolean success = true;
    for (CleanUpTask task : tasks) {
      success &= task.clean();
    }
    return success;
  }
}
