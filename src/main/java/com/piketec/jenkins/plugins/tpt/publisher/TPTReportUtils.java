package com.piketec.jenkins.plugins.tpt.publisher;

import java.io.File;

import hudson.model.AbstractBuild;

public class TPTReportUtils {

  /**
   * @param baseDir
   * @param id
   * @return the directory where the report is saved on the machine that runs Jenkins
   */
  public static File getReportDir(File baseDir, String id) {
    return new File(baseDir, id);
  }

  /**
   * @return a directory "Piketec-TPT" on the machine that runs Jenkins
   */
  public static File getPikeTecDir(AbstractBuild< ? , ? > build) {
    return new File(build.getRootDir().getAbsolutePath() + File.separator + "Piketec-TPT");
  }

  /**
   * @return a directory "Piketec-TPT/Images" on the machine that runs Jenkins
   */
  public static File getImageDir(AbstractBuild< ? , ? > build) {
    return new File(getPikeTecDir(build), "Images");
  }
}
