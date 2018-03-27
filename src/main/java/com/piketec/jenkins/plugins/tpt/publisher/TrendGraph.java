package com.piketec.jenkins.plugins.tpt.publisher;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.DirectoryBrowserSupport;
import hudson.model.Result;
import hudson.model.Run;
import hudson.util.HttpResponses;
import jenkins.model.RunAction2;

public class TrendGraph implements RunAction2, StaplerProxy {

  private static final String INDENT = "\t";

  private static final String LF = "\n";

  private AbstractProject< ? , ? > project;

  private AbstractBuild< ? , ? > actualBuild;

  private transient Run< ? , ? > run;

  private ArrayList<Integer> failedBuilds = new ArrayList<Integer>();

  private int passed;

  private int inconclusive;

  private int error;

  private int failed;

  private ArrayList<ResultData> historyData;

  boolean isFirstBuild;

  public TrendGraph(final AbstractProject< ? , ? > project) {

    historyData = new ArrayList<>();
    this.project = project;
    initBuildAndTestCaseResultCounts();
    setHistoryIterativ();

  }

  public boolean getStaticValue() {
    return TPTGlobalConfiguration.DescriptorImpl.trustSlavesAndUsers;

  }

  private void initBuildAndTestCaseResultCounts() {
    actualBuild = this.project.getLastSuccessfulBuild();
    if (actualBuild == null) {
      return;
    }

    Action tptAction = actualBuild.getAction(TPTReportPage.class);
    if (tptAction == null) {
      return;
    }
    this.passed = ((TPTReportPage)tptAction).getPassedCount();
    this.inconclusive = ((TPTReportPage)tptAction).getInconclusiveCount();
    this.error = ((TPTReportPage)tptAction).getErrorCount();
    this.failed = ((TPTReportPage)tptAction).getFailedCount();
  }

  private void refreshTrendGraph() {

    historyData.clear();
    setHistoryIterativ();
    initBuildAndTestCaseResultCounts();
  }

  private void setHistoryIterativ() {
    if (actualBuild == null) {
      return;
    }
    @SuppressWarnings("unchecked")
    List<Run> builds = (List<Run>)actualBuild.getPreviousBuildsOverThreshold(20, Result.UNSTABLE);
    if (actualBuild.getResult().isBetterOrEqualTo(Result.UNSTABLE)) {
      builds.add(0, actualBuild);
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

  public AbstractProject< ? , ? > getProject() {
    return project;
  }

  public void setProject(AbstractProject< ? , ? > project) {
    this.project = project;
  }

  @Override
  public void onAttached(Run< ? , ? > run) {
    this.run = run;
  }

  @Override
  public void onLoad(Run< ? , ? > run) {
    this.run = run;

  }

  public Run< ? , ? > getRun() {
    return run;
  }

  public void setRun(Run< ? , ? > run) {
    this.run = run;
  }

  public ArrayList<Integer> getFailedBuilds() {
    return failedBuilds;
  }

  public void setFailedBuilds(ArrayList<Integer> failedBuilds) {
    this.failedBuilds = failedBuilds;
  }

  public int getPassed() {
    return passed;
  }

  public void setPassed(int passed) {
    this.passed = passed;
  }

  public int getFailed() {
    return failed;
  }

  public void setFailed(int failed) {
    this.failed = failed;
  }

  public int getError() {
    return error;
  }

  public void setError(int error) {
    this.error = error;
  }

  public int getInconclusive() {
    return inconclusive;
  }

  public void setInconclusive(int inconclusive) {
    this.inconclusive = inconclusive;
  }

  public ArrayList<ResultData> getHistoryData() {
    return this.historyData;
  }

  @Override
  public Object getTarget() {
    return this;
  }

  public void doDynamic(StaplerRequest req, StaplerResponse rsp)
      throws IOException, ServletException, InterruptedException {
    // For every refresh the actual build will be updated. If actual build equals null, nothing to
    // show
    actualBuild = this.project.getLastSuccessfulBuild();
    refreshTrendGraph();
    if (actualBuild == null) {
      return;
    }
    setSecurity();

    generateJson();

    File buildDir = actualBuild.getRootDir();

    DirectoryBrowserSupport dbs = new DirectoryBrowserSupport(this,
        new FilePath(new File(buildDir.getAbsolutePath() + "\\TrendGraph")), "TPT Report",
        "/plugin/piketec-tpt/tpt.ico", false);

    if (req.getRestOfPath().equals("")) {
      throw HttpResponses.forwardToView(this, "index.jelly");
    }
    dbs.generateResponse(req, rsp, this);
  }

  private void setSecurity() {
    if (TPTGlobalConfiguration.DescriptorImpl.trustSlavesAndUsers) {
      System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "");
    } else {
      System.setProperty("hudson.model.DirectoryBrowserSupport.CSP",
          TPTGlobalConfiguration.DescriptorImpl.staticOldSettings);
    }
  }

  private void generateJson() throws IOException, InterruptedException {
    File oldIndexHTML = new File(Utils.getTptPluginRootDir(), "TrendGraph/index.html");
    File utilsJs = new File(Utils.getTptPluginRootDir(), "TrendGraph/utils.js");

    File buildDir = actualBuild.getRootDir();

    File trendGraph = new File(buildDir.getAbsolutePath() + "\\TrendGraph");

    if (!trendGraph.isDirectory() && !trendGraph.mkdirs()) {
      throw new IOException("Could not create directory \"" + trendGraph.getAbsolutePath() + "\"");
    }
    if (utilsJs.exists()) {
      FileUtils.copyFileToDirectory(utilsJs, trendGraph);
      FileUtils.copyFileToDirectory(oldIndexHTML, trendGraph);
    }
    // replace the place holder "toReplace" by actual json script
    String jsonScript = getResultArray(historyData);
    String newIndexHTMLWithJson = FileUtils.readFileToString(oldIndexHTML);
    newIndexHTMLWithJson = newIndexHTMLWithJson.replace("toReplace", jsonScript);
    PrintWriter pw = new PrintWriter(buildDir.getAbsolutePath() + "\\TrendGraph\\index.html");
    pw.close();
    File newIndexHTML = new File(buildDir.getAbsolutePath() + "\\TrendGraph\\index.html");
    FileUtils.writeStringToFile(newIndexHTML, newIndexHTMLWithJson);

  }

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

}
