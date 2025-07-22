/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2018 Synopsys Inc.
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
package com.piketec.jenkins.plugins.tpt.publisher;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import org.kohsuke.stapler.bind.JavaScriptMethod;

import com.google.common.io.Files;

import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.Result;
import hudson.model.Run;
import jenkins.model.RunAction2;
import jenkins.security.stapler.StaplerDispatchable;
import jenkins.tasks.SimpleBuildStep;

/**
 * A Page with a table of all executed TPT files and their configuration on top and a table with all
 * failed test cases.
 * 
 * @author FInfantino, Synopsys Inc.
 */
public class TPTReportPage implements RunAction2, SimpleBuildStep.LastBuildAction {

  private Run< ? , ? > build;

  private transient Run run;

  private TPTTestCase[] failedTests;

  private TPTFile[] tptFiles;

  private int passedCount = 0;

  private int inconclusiveCount = 0;

  private int errorCount = 0;

  private int failedCount = 0;

  private static final Color COLOR_GREEN = Color.GREEN.darker();

  private static final Color COLOR_YELLOW = new Color(0xDDCC09);

  private static final Color COLOR_RED = new Color(240, 0, 0);

  private static final Color COLOR_BROWN = new Color(177, 7, 7);

  /**
   * Creates a new TPTRportPage
   * 
   * @param build
   *          The Jnekins build
   * @param failedTests
   *          The list of failed test cases
   * @param tptFiles
   *          The List of TPT files
   */
  public TPTReportPage(Run< ? , ? > build, TPTTestCase[] failedTests, TPTFile[] tptFiles) {
    this.build = build;
    for (TPTFile f : tptFiles) {
      passedCount += f.getPassed();
      inconclusiveCount += f.getInconclusive();
      errorCount += f.getExecutionError();
      failedCount += f.getFailed();
    }
    this.setFailedTests(failedTests);
    this.setTptFiles(tptFiles);
  }

  protected Object readResolve() {
    // set parent page
    setFailedTests(failedTests);
    setTptFiles(tptFiles);
    return this;
  }

  @Override
  public String getIconFileName() {
    return "/plugin/piketec-tpt/tpt.ico";
  }

  @Override
  public String getDisplayName() {
    return "TPT Report";
  }

  @Override
  public String getUrlName() {
    return "TPT_Report";
  }

  /**
   * @return The Jenkins build
   */
  public Run< ? , ? > getBuild() {
    return build;
  }

  /**
   * Set the Jenkins build
   * 
   * @param build
   *          The Jenkins build
   */
  public void setBuild(AbstractBuild< ? , ? > build) {
    this.build = build;
  }

  @Override
  public void onAttached(Run< ? , ? > run) {
    this.run = run;
  }

  @Override
  public void onLoad(Run< ? , ? > run) {
    this.run = run;
  }

  /**
   * @return The Jenkins run
   */
  public Run getRun() {
    return run;
  }

  // @Override
  // public Object getTarget() {
  // return this;
  // }

  /**
   * @return The list of executed TPT files
   */
  @StaplerDispatchable
  public TPTFile[] getTptFiles() {
    return tptFiles;
  }

  /**
   * Set the list of executed TPT files
   * 
   * @param tptFiles
   *          The list of executed TPT files
   */
  public void setTptFiles(TPTFile[] tptFiles) {
    this.tptFiles = tptFiles;
    for (TPTFile tptFile : tptFiles) {
      tptFile.setParentPage(this);
    }
  }

  /**
   * @return The list of failed test cases
   */
  @StaplerDispatchable
  public TPTTestCase[] getFailedTests() {
    return failedTests;
  }

  /**
   * Set the list of failed test cases
   * 
   * @param failedTests
   *          The list of failed test cases
   */
  public void setFailedTests(TPTTestCase[] failedTests) {
    this.failedTests = failedTests;
    for (TPTTestCase testCase : failedTests) {
      testCase.setParentPage(this);
    }
  }

  /**
   * @return The number of passed test cases
   */
  public int getPassedCount() {
    return passedCount;
  }

  /**
   * Set the number of passed test cases
   * 
   * @param passedCount
   *          The number of passed test cases
   */
  public void setPassedCount(int passedCount) {
    this.passedCount = passedCount;
  }

  /**
   * @return The number of inconclusive test cases
   */
  public int getInconclusiveCount() {
    return inconclusiveCount;
  }

  /**
   * Set the number of inconclusive test cases
   * 
   * @param inconclusiveCount
   *          The number of inconclusive test cases
   */
  public void setInconclusiveCount(int inconclusiveCount) {
    this.inconclusiveCount = inconclusiveCount;
  }

  /**
   * @return The number of test cases with execution error
   */
  public int getErrorCount() {
    return errorCount;
  }

  /**
   * Set the number of test cases with execution error
   * 
   * @param errorCount
   *          The number of test cases with execution error
   */
  public void setErrorCount(int errorCount) {
    this.errorCount = errorCount;
  }

  /**
   * @return he number of failed test cases
   */
  public int getFailedCount() {
    return failedCount;
  }

  /**
   * Set the number of failed test cases
   * 
   * @param failedCount
   *          The number of failed test cases
   */
  public void setFailedCount(int failedCount) {
    this.failedCount = failedCount;
  }

  /**
   * Used to calculate the actual build number of the 'failed since' build, failed since is always
   * &gt;=1
   * 
   * @param failedSince
   *          The count of builds in the past that should have been unstable.
   * @return the actual number from the 'failed since' build
   */
  public int getNumberFromHistory(int failedSince) {
    List< ? > previousBuilds =
        this.build.getPreviousBuildsOverThreshold(failedSince - 1, Result.UNSTABLE);
    if (previousBuilds.isEmpty()) {
      return this.build.getNumber();
    }
    return ((Run< ? , ? >)previousBuilds.get(previousBuilds.size() - 1)).getNumber();
  }

  @JavaScriptMethod
  public String getPieChart() {
    File pieChartFile = getPieChartFile();
    try {
      if (!pieChartFile.exists()) {
        createGraph();
      }
      return "data:image/png;base64,"
          + Base64.getMimeEncoder().encodeToString(Files.toByteArray(pieChartFile));
    } catch (IOException e) {
      return "";
    }
  }

  @Override
  public Collection< ? extends Action> getProjectActions() {
    List<TrendGraph> projectActions = new ArrayList<>();
    projectActions.add(new TrendGraph(build.getParent()));
    return projectActions;
  }

  /**
   * Creates the pie chart from the TPT Report
   * 
   * @throws IOException
   */
  void createGraph() throws IOException {
    List<com.piketec.jenkins.plugins.tpt.publisher.PieChart.Segment> list = new ArrayList<>();
    com.piketec.jenkins.plugins.tpt.publisher.PieChart.Segment passed =
        new PieChart.Segment("Passed", passedCount, COLOR_GREEN);
    com.piketec.jenkins.plugins.tpt.publisher.PieChart.Segment inconlusive =
        new PieChart.Segment("Inconclusive", inconclusiveCount, COLOR_YELLOW);
    com.piketec.jenkins.plugins.tpt.publisher.PieChart.Segment error =
        new PieChart.Segment("Error", errorCount, COLOR_BROWN);
    com.piketec.jenkins.plugins.tpt.publisher.PieChart.Segment failed =
        new PieChart.Segment("Failed", failedCount, COLOR_RED);
    list.add(passed);
    list.add(inconlusive);
    list.add(error);
    list.add(failed);
    PieChart pieChart = new PieChart(list, 0, true);
    File output = getPieChartFile();
    BufferedImage image = pieChart.render(150);
    if (!output.exists() && !output.mkdirs()) {
      throw new IOException("Could not create directory " + output.getAbsolutePath());
    }
    if (!ImageIO.write(image, "png", output)) {
      throw new IOException("Could not create pie chart");
    }

  }

  private File getPieChartFile() {
    return new File(build.getRootDir().getAbsolutePath(),
        "Piketec-TPT" + File.separator + "Images" + File.separator + "pieChart.png");
  }

}
