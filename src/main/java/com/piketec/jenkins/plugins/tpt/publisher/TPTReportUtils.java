package com.piketec.jenkins.plugins.tpt.publisher;

import java.io.File;

import hudson.model.AbstractBuild;

public class TPTReportUtils {

  /**
   * Get the report directory
   * 
   * @param baseDir
   *          The base directory
   * @param id
   *          The unique ID of the configuration to create unique paths
   * @return the directory where the report is saved on the machine that runs Jenkins
   */
  public static File getReportDir(File baseDir, String id) {
    return new File(baseDir, id);
  }

  /**
   * 
   * @param build
   *          The jenkins build
   * @return a directory "Piketec-TPT" on the machine that runs Jenkins
   */
  public static File getPikeTecDir(AbstractBuild< ? , ? > build) {
    return new File(build.getRootDir().getAbsolutePath() + File.separator + "Piketec-TPT");
  }

  /**
   * Get the image diretory.
   * 
   * @param build
   *          The jenkins build
   * @return a directory "Piketec-TPT/Images" on the machine that runs Jenkins
   */
  public static File getImageDir(AbstractBuild< ? , ? > build) {
    return new File(getPikeTecDir(build), "Images");
  }
}
