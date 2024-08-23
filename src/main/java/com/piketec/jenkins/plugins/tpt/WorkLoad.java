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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import com.piketec.jenkins.plugins.tpt.Configuration.JenkinsConfiguration;

import hudson.FilePath;
import hudson.model.Run;

/**
 * Through this class is how the data from a distributing job to a worker job is passed. A way to
 * tell the worker job what testcases should be executed.
 * 
 * @author FInfantino, PikeTec GmbH
 */
public class WorkLoad {

  private static HashMap<String, LinkedList<WorkLoad>> workloads = new HashMap<>();

  private List<String> testCases;

  private Run< ? , ? > distributingJobRun;

  private FilePath distributingJobWorkspace;

  private FilePath distributingJobDataDir;

  private FilePath distributingJobReportDir;

  private JenkinsConfiguration jenkinsConfig;

  /**
   * @param unresolvedConfig
   *          JenkinsConfiguration that contains paths and tpt file names with unresolved $-vars
   * @param subTestSet
   *          the test cases that should be executed by the worker
   * @param distributingJobWorkspace
   *          the workspace from the distributing job, used for knowing where to copy the results
   * @param distributingJobRun
   *          the current build, used in order to get an unique id
   * @param distributingJobDataDir
   *          The test data directory of the distributing job
   * @param distributingJobReportDir
   *          The report directory the distributing job
   */
  public WorkLoad(JenkinsConfiguration unresolvedConfig, List<String> subTestSet,
                  FilePath distributingJobWorkspace, Run< ? , ? > distributingJobRun,
                  FilePath distributingJobDataDir, FilePath distributingJobReportDir) {
    this.jenkinsConfig = unresolvedConfig;
    this.testCases = subTestSet;
    this.distributingJobRun = distributingJobRun;
    this.distributingJobWorkspace = distributingJobWorkspace;
    this.distributingJobDataDir = distributingJobDataDir;
    this.distributingJobReportDir = distributingJobReportDir;
  }

  /**
   * @return the jenkins configuration that contains paths and tpt file names with unresolved $-vars
   */
  public JenkinsConfiguration getJenkinsConfig() {
    return jenkinsConfig;
  }

  /**
   * @return the test cases that should be executed
   */
  public List<String> getTestCases() {
    return testCases;
  }

  /**
   * @return the workspace from the distributing job
   */
  public FilePath getDistributingJobWorkspace() {
    return distributingJobWorkspace;
  }

  /**
   * @return the current build, used to get an unique Id
   */
  public Run< ? , ? > getDistributingJobRun() {
    return distributingJobRun;
  }

  /**
   * @return the path to the data directory of the distributing job
   */
  public FilePath getDistributingJobDataDir() {
    return this.distributingJobDataDir;
  }

  /**
   * @return the path to the report directory of the distributing job
   */
  public FilePath getDistributingJobReportDir() {
    return this.distributingJobReportDir;
  }

  /**
   * Adds a workload to the static HashMap. This method is used when the distributing job put the
   * workload here and then triggers the worker job.
   * 
   * @param jobName
   *          The name of the jenkins job serving as a worker.
   * @param workloadToAdd
   *          The work package to be executed by the worker job
   */
  public static synchronized void putWorkLoad(String jobName, WorkLoad workloadToAdd) {
    LinkedList<WorkLoad> queue = workloads.get(jobName);
    if (queue == null) {
      queue = new LinkedList<>();
      workloads.put(jobName, queue);
    }
    if (!queue.contains(workloadToAdd)) {
      queue.offer(workloadToAdd);
    }
  }

  /**
   * Pops the workload from the static HashMap. This method is used from the worker when a build has
   * been triggered and it needs to do some workload.
   * 
   * @param jobName
   *          The name of the jenkins job serving as a worker job.
   * @return the workload that has been removed, null if there is nothing to remove.
   */
  public static synchronized WorkLoad pollWorkload(String jobName) {
    Queue<WorkLoad> queue = workloads.get(jobName);
    if (queue == null || queue.isEmpty()) {
      return null;
    }
    return queue.poll();
  }

  /**
   * Cleans the workload if there is an Interrupted Exeption. We dont want to store the workload if
   * something goes wrong.
   * 
   * @param jobName
   *          The name of the jenkins job serving as a worker.
   * @param distributingJobRun
   *          the build of the distributing job
   */
  public static synchronized void clean(String jobName, Run< ? , ? > distributingJobRun) {
    LinkedList<WorkLoad> queue = workloads.get(jobName);
    if (queue == null) {
      return;
    }
    Iterator<WorkLoad> iterator = queue.iterator();
    while (iterator.hasNext()) {
      WorkLoad next = iterator.next();
      if (Objects.equals(next.distributingJobRun, distributingJobRun)) {
        iterator.remove();
      }
    }
    if (queue.isEmpty()) {
      workloads.remove(jobName);
    }
  }

}
