/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 PikeTec GmbH
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

import java.io.PrintStream;

public class TptLogger {

  PrintStream logger;

  public TptLogger(PrintStream logger) {
    this.logger = logger;
  }

  /**
   * Report an info message.
   * 
   * @param msg
   *          Message to show.
   */
  public void info(String msg) {
    logger.println("[Info " + Utils.getCurrentDateString() + "]: " + msg);
  }

  /**
   * Report an error message
   * 
   * @param msg
   *          Message to show.
   */
  public void error(String msg) {
    logger.println("[Error " + Utils.getCurrentDateString() + "]: " + msg);
  }

  /**
   * Report an interrupt message
   * 
   * @param msg
   *          Message to show.
   */
  public void interrupt(String msg) {
    logger.println("[Interrupt " + Utils.getCurrentDateString() + "]: " + msg);
  }

  /**
   * get access to the underlying message stream
   * 
   * @return Underlying stream from the logger.
   */
  public PrintStream getLogger() {
    return logger;
  }
}
