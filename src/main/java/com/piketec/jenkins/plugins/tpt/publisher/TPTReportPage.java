package com.piketec.jenkins.plugins.tpt.publisher;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.AbstractBuild;
import hudson.model.Result;
import hudson.model.Run;
import jenkins.model.RunAction2;

public class TPTReportPage implements RunAction2, StaplerProxy {

  private AbstractBuild< ? , ? > build;

  private transient Run run;

  private ArrayList<TPTTestCase> failedTests;

  private ArrayList<TPTFile> tptFiles;

  private int passedCount = 0;

  private int inconclusiveCount = 0;

  private int errorCount = 0;

  private int failedCount = 0;

  private static final Color COLOR_GREEN = Color.GREEN.darker();

  private static final Color COLOR_YELLOW = new Color(0xDDCC09);

  private static final Color COLOR_RED = new Color(240, 0, 0);

  private static final Color COLOR_BROWN = new Color(177, 7, 7);

  public TPTReportPage(AbstractBuild< ? , ? > build, ArrayList<TPTTestCase> failedTests,
                       ArrayList<TPTFile> tptFiles) {

    for (TPTFile f : tptFiles) {
      passedCount += f.getPassed();
      inconclusiveCount += f.getInconclusive();
      errorCount += f.getExecutionError();
      failedCount += f.getFailed();
    }
    this.build = build;

    this.setFailedTests(failedTests);
    this.setTptFiles(tptFiles);

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

  public AbstractBuild< ? , ? > getBuild() {
    return build;
  }

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

  public Run getRun() {
    return run;
  }

  @Override
  public Object getTarget() {
    return this;
  }

  public ArrayList<TPTFile> getTptFiles() {
    return tptFiles;
  }

  public void setTptFiles(ArrayList<TPTFile> tptFiles) {
    this.tptFiles = tptFiles;
  }

  public ArrayList<TPTTestCase> getFailedTests() {
    return failedTests;
  }

  public void setFailedTests(ArrayList<TPTTestCase> failedTests) {
    this.failedTests = failedTests;
  }

  public int getPassedCount() {
    return passedCount;
  }

  public void setPassedCount(int passedCount) {
    this.passedCount = passedCount;
  }

  public int getInconclusiveCount() {
    return inconclusiveCount;
  }

  public void setInconclusiveCount(int inconclusiveCount) {
    this.inconclusiveCount = inconclusiveCount;
  }

  public int getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(int errorCount) {
    this.errorCount = errorCount;
  }

  public int getFailedCount() {
    return failedCount;
  }

  public void setFailedCount(int failedCount) {
    this.failedCount = failedCount;
  }

  public boolean getStaticValue() {
    return TPTGlobalConfiguration.DescriptorImpl.trustSlavesAndUsers;
  }

  // used to calculate the actual number from the 'failed since' build, failed Since is always >=1
  public int getNumberFromHistory(int failedSince) {
    List< ? > previousBuilds =
        this.build.getPreviousBuildsOverThreshold(failedSince - 1, Result.UNSTABLE);
    if (previousBuilds.isEmpty()) {
      return this.build.getNumber();
    }
    return ((Run< ? , ? >)previousBuilds.get(previousBuilds.size() - 1)).getNumber();
  }

  // Host images, HTML report and failed report
  // see http://stapler.kohsuke.org/reference.html
  public Object getDynamic(String name, StaplerRequest req, StaplerResponse rsp) {

    if (name.equals("SecurityError")) {
      return new SecurityErrorAction();
    }

    if (name.equals("Images")) {
      return new InvisibleActionHostingImages(build);
    }
    for (TPTTestCase t : failedTests) {
      if (name.equals(t.getFileName() + t.getExecutionConfiguration() + t.getId() + t.getPlatform()
          + t.getExecutionDate())) {
        return new OpenReportForFailedTestAction(build, t.getFileName(), t.getReportFile(),
            t.getId(), t.getExecutionConfiguration(), t.getPlatform(), t.getExecutionDate());
      }
    }
    for (TPTFile t : this.tptFiles) {
      if (name.equals(t.getFileName() + t.getConfiguration())) {
        return new InvisibleActionHostingHtml(build, t.getFileName(), t.getConfiguration());
      }

    }

    return null;
  }

  public void createGraph() throws IOException {
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

    File output =
        new File(build.getRootDir().getAbsolutePath() + "\\Piketec-TPT\\Images\\pieChart.png");

    BufferedImage image = pieChart.render(150);

    if (!output.exists() && !output.mkdirs()) {
      throw new IOException("Could not create directory " + output.getAbsolutePath());
    }
    if (!ImageIO.write(image, "png", output)) {
      throw new IOException("Could not create pie chart");
    }

  }

}
