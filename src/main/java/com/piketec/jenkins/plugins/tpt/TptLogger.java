package com.piketec.jenkins.plugins.tpt;

import java.io.PrintStream;

public interface TptLogger {

  /**
   * Report an info message.
   * 
   * @param msg
   *          Message to show.
   */
  public void info(String msg);

  /**
   * Report an error message
   * 
   * @param msg
   *          Message to show.
   */
  public void error(String msg);

  /**
   * Report an interrupt message
   * 
   * @param msg
   *          Message to show.
   */
  public void interrupt(String msg);

  /**
   * get access to the underlying message stream
   * 
   * @return Underlying stream from the logger.
   */
  public PrintStream getLogger();
}
