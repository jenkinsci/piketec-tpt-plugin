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
package com.piketec.jenkins.plugins.tpt.publisher;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import com.piketec.jenkins.plugins.tpt.Utils;

import hudson.FilePath;
import hudson.model.Action;
import hudson.model.DirectoryBrowserSupport;
import hudson.model.Job;
import hudson.model.Result;
import hudson.model.Run;
import hudson.util.HttpResponses;

/**
 * Generates the trend graph on the main page.
 * 
 * @author FInfantino, PikeTec GmbH
 *
 */
public class TrendGraph implements Action, StaplerProxy {

  private static final String INDENT = "\t";

  private static final String LF = "\n";

  private transient Run< ? , ? > lastSuccessBuild;

  private final Job< ? , ? > project;

  private ArrayList<Integer> failedBuilds = new ArrayList<>();

  private int passed;

  private int inconclusive;

  private int error;

  private int failed;

  private ArrayList<ResultData> historyData = new ArrayList<>();

  /**
   * Creates a new TrendGraph
   * 
   * @param project
   *          The Jenkins project this Trendgraph belongs to.
   */
  public TrendGraph(final Job< ? , ? > project) {
    this.project = project;
    initBuildAndTestCaseResultCounts();
    setHistoryIterativ();
  }

  /**
   * Gets the last successful build and gets the data (passed, inconclusive, error and failed tests)
   * from there. We need this method although we have the setHistoryIterativ method because in the
   * setHistoryIterativ method the call actualBuild.getPreviousBuildsOverThreshold doesnt get the
   * data from actual build, thats why we have a method for the history and this one for the actual
   * build.
   */
  private void initBuildAndTestCaseResultCounts() {
    lastSuccessBuild = project.getLastSuccessfulBuild();
    if (lastSuccessBuild == null) {
      return;
    }
    Action tptAction = lastSuccessBuild.getAction(TPTReportPage.class);
    if (tptAction == null) {
      return;
    }
    this.passed = ((TPTReportPage)tptAction).getPassedCount();
    this.inconclusive = ((TPTReportPage)tptAction).getInconclusiveCount();
    this.error = ((TPTReportPage)tptAction).getErrorCount();
    this.failed = ((TPTReportPage)tptAction).getFailedCount();
  }

  /**
   * Regenerates the trend graph. It is called everytime the page is refreshed, this is useful
   * because when a new build is created we want the graph to update on runtime.
   */
  private void refreshTrendGraph() {
    historyData.clear();
    setHistoryIterativ();
    initBuildAndTestCaseResultCounts();
  }

  /**
   * Fills the history Data with the data from the last 20 builds.
   */
  private void setHistoryIterativ() {
    if (lastSuccessBuild == null) {
      return;
    }
    Result result = lastSuccessBuild.getResult();
    if (result == null) {
      return;
    }
    @SuppressWarnings("unchecked")
    List<Run> builds =
        (List<Run>)lastSuccessBuild.getPreviousBuildsOverThreshold(20, Result.UNSTABLE);
    if (result.isBetterOrEqualTo(Result.UNSTABLE)) {
      builds.add(0, lastSuccessBuild);
    }
    for (Run run : builds) {
      ResultData toAdd = new ResultData();
      TPTReportPage tptAction = run.getAction(TPTReportPage.class); // is always unique
      if (tptAction == null) {
        continue;
      }
      toAdd.buildNummer = run.getNumber();
      toAdd.error = tptAction.getErrorCount();
      toAdd.passed = tptAction.getPassedCount();
      toAdd.inconclusive = tptAction.getInconclusiveCount();
      toAdd.failed = tptAction.getFailedCount();
      toAdd.total = toAdd.error + toAdd.failed + toAdd.passed + toAdd.inconclusive;
      historyData.add(toAdd);
    }
  }

  @Override
  public String getIconFileName() {
    return "/plugin/piketec-tpt/tpt.ico";
  }

  @Override
  public String getDisplayName() {
    return "TPT Trend Results";
  }

  @Override
  public String getUrlName() {
    return "TPTtrendResults";
  }

  /**
   * @return The Jenkins project this Trendgraph belongs to
   */
  public Job< ? , ? > getProject() {
    return project;
  }

  /**
   * @return The number of failed builds of the jenkins project this trend graph belongs to
   */
  public ArrayList<Integer> getFailedBuilds() {
    return failedBuilds;
  }

  /**
   * Set the number of failed builds of the jenkins project this trend graph belongs to
   * 
   * @param failedBuilds
   *          The number of failed builds of the jenkins project this trend graph belongs to
   */
  public void setFailedBuilds(ArrayList<Integer> failedBuilds) {
    this.failedBuilds = failedBuilds;
  }

  /**
   * @return The number of passed tests of the last build of the jenkins project this trend graph
   *         belongs to.
   */
  public int getPassed() {
    return passed;
  }

  /**
   * Set the number of passed tests of the last build of the jenkins project this trend graph
   * belongs to.
   * 
   * @param passed
   *          TThe number of passed tests of the last build of the jenkins project this trend graph
   *          belongs to.
   */
  public void setPassed(int passed) {
    this.passed = passed;
  }

  /**
   * @return The number of failed tests of the last build of the jenkins project this trend graph
   *         belongs to.
   */
  public int getFailed() {
    return failed;
  }

  /**
   * Set the number of failed tests of the last build of the jenkins project this trend graph
   * belongs to.
   * 
   * @param failed
   *          The number of failed tests of the last build of the jenkins project this trend graph
   *          belongs to.
   */
  public void setFailed(int failed) {
    this.failed = failed;
  }

  /**
   * @return The number of tests with execution error of the last build of the jenkins project this
   *         trend graph belongs to.
   */
  public int getError() {
    return error;
  }

  /**
   * Set the number of tests with execution error of the last build of the jenkins project this
   * trend graph belongs to.
   * 
   * @param error
   *          The number of tests with execution error of the last build of the jenkins project this
   *          trend graph belongs to.
   */
  public void setError(int error) {
    this.error = error;
  }

  /**
   * @return The number of inconclusive tests of the last build of the jenkins project this trend
   *         graph belongs to.
   */
  public int getInconclusive() {
    return inconclusive;
  }

  /**
   * Set the number of inconclusive tests of the last build of the jenkins project this trend graph
   * belongs to.
   * 
   * @param inconclusive
   *          Set the number of inconclusive tests of the last build of the jenkins project this
   *          trend graph belongs to.
   */
  public void setInconclusive(int inconclusive) {
    this.inconclusive = inconclusive;
  }

  /**
   * @return A List of Data containing the numer off passed, failed, inconcluive etc. TPT tests of
   *         previous builds.
   */
  public ArrayList<ResultData> getHistoryData() {
    lastSuccessBuild = project.getLastSuccessfulBuild();
    refreshTrendGraph();
    return this.historyData;
  }

  @Override
  public Object getTarget() {
    return this;
  }

  /**
   * This method is called everytime the page is refreshed. It regenerates the json file and
   * refreshes the graph.
   * 
   * 
   * @param req
   *          The request
   * @param rsp
   *          The response
   * @throws IOException
   *           if the response could not be generated
   * @throws ServletException
   *           if the response could not be generated
   * @throws InterruptedException
   *           if the job is cancelled
   */
  public void doDynamic(StaplerRequest req, StaplerResponse rsp)
      throws IOException, ServletException, InterruptedException {
    // For every refresh the actual build will be updated. If actual build equals null, nothing to
    // show
    lastSuccessBuild = project.getLastSuccessfulBuild();
    refreshTrendGraph();
    if (lastSuccessBuild == null) {
      return;
    }
    generateJson();
    File buildDir = lastSuccessBuild.getRootDir();
    DirectoryBrowserSupport dbs = new DirectoryBrowserSupport(this,
        new FilePath(new File(buildDir.getAbsolutePath() + File.separator + "TrendGraph")),
        "TPT Report", "/plugin/piketec-tpt/tpt.ico", false);

    if (req.getRestOfPath().equals("")) {
      throw HttpResponses.forwardToView(this, "index.jelly");
    }
    dbs.generateResponse(req, rsp, this);
  }

  /**
   * Generates the json file with the historyData.
   * 
   * @throws IOException
   *           if files could not be created
   * @throws InterruptedException
   *           if the job is cancelled
   */
  private void generateJson() throws IOException, InterruptedException {
    File oldIndexHTML =
        new File(Utils.getTptPluginRootDir(), "TrendGraph" + File.separator + "index.html");
    File utilsJs =
        new File(Utils.getTptPluginRootDir(), "TrendGraph" + File.separator + "utils.js");

    File buildDir = lastSuccessBuild.getRootDir();

    File trendGraph = new File(buildDir.getAbsolutePath() + File.separator + "TrendGraph");

    if (!trendGraph.isDirectory() && !trendGraph.mkdirs()) {
      throw new IOException("Could not create directory \"" + trendGraph.getAbsolutePath() + "\"");
    }
    if (utilsJs.exists()) {
      FileUtils.copyFileToDirectory(utilsJs, trendGraph);
      FileUtils.copyFileToDirectory(oldIndexHTML, trendGraph);
    }
    // replace the place holder "toReplace" by actual json script
    String jsonScript = getResultArray(historyData);
    String newIndexHTMLWithJson =
        FileUtils.readFileToString(oldIndexHTML, Charset.forName("UTF-8"));
    newIndexHTMLWithJson = newIndexHTMLWithJson.replace("toReplace", jsonScript);
    File newIndexHTML = new File(
        buildDir.getAbsolutePath() + File.separator + "TrendGraph" + File.separator + "index.html");
    FileUtils.writeStringToFile(newIndexHTML, newIndexHTMLWithJson, Charset.forName("UTF-8"));
  }

  /**
   * Minor function from generateJson()
   * 
   * @param data
   *          The data to create the graph from
   * @return
   */
  private static String getResultArray(ArrayList<ResultData> data) {
    StringBuffer buf = new StringBuffer();
    int indent = 1;
    buf.append(StringUtils.repeat(INDENT, indent + 1) + " { \"data\" : [" + LF);
    for (int i = 0; i < data.size(); i++) {
      ResultData currentData = data.get(i);
      if (i == data.size() - 1) {
        buf.append(getResultStruct(currentData.total, currentData.failed, currentData.inconclusive,
            currentData.error, currentData.passed, currentData.buildNummer, indent + 1, false));
      } else {
        buf.append(getResultStruct(currentData.total, currentData.failed, currentData.inconclusive,
            currentData.error, currentData.passed, currentData.buildNummer, indent + 1, true));
      }
    }
    buf.append(StringUtils.repeat(INDENT, indent + 1) + "]" + LF);
    buf.append("}");
    return buf.toString();
  }

  /**
   * Minor function from generateJson().
   * 
   * @param total
   *          The total number of test cases
   * @param failed
   *          The number of failed test cases
   * @param inconclusive
   *          The number of inconclusive test cases
   * @param error
   *          The number of test cases with execution errors
   * @param passed
   *          the number of passed test cases
   * @param buildNummer
   *          the build number of the Jenkins build
   * @param indent
   *          indentation. Just for formating the json output
   * @param withComma
   *          should the last line end with a comma
   * @return The part of the json output containing the numbers of test cases foreach result and teh
   *         build number
   */
  private static String getResultStruct(int total, int failed, int inconclusive, int error,
                                        int passed, int buildNummer, int indent,
                                        boolean withComma) {
    StringBuffer buf = new StringBuffer();
    buf.append(StringUtils.repeat(INDENT, indent + 1) + "[" + LF);
    buf.append(getJSONIntEntry("buildNummer", buildNummer, indent + 1, true));
    buf.append(getJSONIntEntry("total", total, indent + 1, true));
    buf.append(getJSONIntEntry("failed", failed, indent + 1, true));
    buf.append(getJSONIntEntry("inconclusive", inconclusive, indent + 1, true));
    buf.append(getJSONIntEntry("error", error, indent + 1, true));
    buf.append(getJSONIntEntry("passed", passed, indent + 1));
    // append comma if needed
    if (withComma) {
      buf.append(StringUtils.repeat(INDENT, indent + 1) + "]," + LF);
    } else {
      buf.append(StringUtils.repeat(INDENT, indent + 1) + "]" + LF);
    }
    return buf.toString();
  }

  private static String getJSONIntEntry(String name, int value, int indent, boolean withComma) {
    if (withComma) {
      return StringUtils.repeat(INDENT, indent + 1) + "{\"" + name + "\" : " + value + "}," + LF;
    } else {
      return StringUtils.repeat(INDENT, indent + 1) + "{\"" + name + "\" : " + value + "}" + LF;
    }
  }

  private static String getJSONIntEntry(String name, int value, int indent) {
    return getJSONIntEntry(name, value, indent, false);
  }

  /**
   * Data container to collect numbers of test results of TPT test execuiton for build previous
   * builds.
   * 
   * @author FInfantino, PikeTec GmbH
   *
   */
  public static class ResultData {

    /**
     * The total number of TPT test cases
     */
    public int total;

    /**
     * The number of TPT test cases with execution error
     */
    public int error;

    /**
     * The number of failed TPT test cases
     */
    public int failed;

    /**
     * The number of passed TPT test cases
     * 
     */
    public int passed;

    /**
     * The number of inconclusive TPT test cases
     */
    public int inconclusive;

    /**
     * The number of the Jenkins build
     */
    public int buildNummer;
  }

}
