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

import hudson.FilePath;
import hudson.model.AbstractBuild;

/**
 * Through this class is how the data from master to slave is passed. A way to tell the slave what
 * testcases should be executed.
 * 
 * @author FInfantino, PikeTec GmbH
 */
public class WorkLoad {

  private static HashMap<String, LinkedList<WorkLoad>> workloads = new HashMap<>();

  private String fileName;

  private String dataDir;

  private String reportDir;

  private String testSetName;

  private String exeConfig;

  private List<String> testCases;

  private AbstractBuild masterId;

  private FilePath masterWorkspace;

  /**
   * @param fileName
   *          tpt file name
   * @param exeConfig
   *          tpt execution configuration
   * @param dataDir
   *          the path to test data dir
   * @param reportDir
   *          the path to the report dir
   * @param testSetName
   *          the name of the test set if given
   * @param testCases
   *          the test cases that should be executed by the slave
   * @param masterWorkspace
   *          the workspace from master, used for knowing where to copy the results
   * @param masterId
   *          the current build, used in order to get an unique id
   */
  public WorkLoad(String fileName, String exeConfig, String dataDir, String reportDir,
                  String testSetName, List<String> testCases, FilePath masterWorkspace,
                  AbstractBuild masterId) {

    this.fileName = fileName;
    this.exeConfig = exeConfig;
    this.dataDir = dataDir;
    this.reportDir = reportDir;
    this.testSetName = testSetName;
    this.testCases = testCases;
    this.masterId = masterId;
    this.masterWorkspace = masterWorkspace;
  }

  /**
   * @return get the tpt file name
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * @return get the test data dir
   */
  public String getDataDir() {
    return dataDir;
  }

  /**
   * @return get the report dir
   */
  public String getReportDir() {
    return reportDir;
  }

  /**
   * @return the name of the given test Set
   */
  public String getTestSetName() {
    return testSetName;
  }

  /**
   * @return the name of the tpt execution configuration
   */
  public String getExeConfig() {
    return exeConfig;
  }

  /**
   * @return the test cases that should be executed
   */
  public List<String> getTestCases() {
    return testCases;
  }

  /**
   * @return the workspace from master
   */
  public FilePath getMasterWorkspace() {
    return masterWorkspace;
  }

  /**
   * @return the current build, used to get an unique Id
   */
  public AbstractBuild getMasterId() {
    return masterId;
  }

  /**
   * Adds a workload to the static HashMap. This method is used when the masterJob put the workload
   * here and then triggers the slave job.
   * 
   * @param jobName
   * @param workloadToAdd
   */
  public static synchronized void putWorkLoad(String jobName, WorkLoad workloadToAdd) {
    LinkedList<WorkLoad> queue = workloads.get(jobName);
    if (queue == null) {
      queue = new LinkedList<>();
      if (!queue.contains(workloadToAdd)) {
        workloads.put(jobName, queue);
      }
    }
    queue.offer(workloadToAdd);
  }

  /**
   * Pops the workload from the static HashMap. This method is used from the slave when a build has
   * been triggered and it needs to do some workload.
   * 
   * @param jobName
   * @return the workload that has been removed , null if there is nothing to remove.
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
   * @param masterId
   */
  public static synchronized void clean(String jobName, AbstractBuild masterId) {
    LinkedList<WorkLoad> queue = workloads.get(jobName);
    if (queue == null) {
      return;
    }
    Iterator<WorkLoad> iterator = queue.iterator();
    while (iterator.hasNext()) {
      WorkLoad next = iterator.next();
      if (Objects.equals(next.masterId, masterId)) {
        iterator.remove();
      }
    }
    if (queue.isEmpty()) {
      workloads.remove(jobName);
    }
  }

}
