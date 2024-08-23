/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2024 Synopsys Inc.
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
package com.piketec.tpt.api;

import java.io.Serializable;

/**
 * This object represents the result of a method call. It contains the resulting object as well as
 * any log messages occurred during method execution.
 * 
 * @param <T>
 *          The resulting type of the method call.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class ResultAndLogs<T> implements Serializable {

  static final long serialVersionUID = 1L;

  /**
   * The resulting object.
   */
  private final T result;

  /**
   * A list of messages occurred during the method call.
   */
  private final Log log;

  /**
   * Constructor. Should not be called manually. Will be called from implementation of api methods.
   * 
   * @param result
   *          the result of the method call
   * @param log
   *          list of log messages
   */
  public ResultAndLogs(T result, Log log) {
    this.result = result;
    this.log = log;
  }

  /**
   * @return The resulting object of the method call
   */
  public T getResult() {
    return result;
  }

  /**
   * Returns a list of log entries that have been occurred during the execution of the method.
   * 
   * @return List of log messages. Empty if no error or warning messages have been occurred.
   */
  public Log getLog() {
    return log;
  }

}
