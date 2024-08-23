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
import java.util.List;

/**
 * This object represents the result of an attempt to open a TPT project. It contains the project as
 * well as any log messages occurred during the opening of the project.
 * <p>
 * Log messages can occur if the project file was created in a previous release of TPT or if it
 * contains information for license options currently not available.
 * </p>
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class OpenResult implements Serializable {

  static final long serialVersionUID = 1L;

  /**
   * A handle to the open TPT project.
   */
  private final Project project;

  /**
   * A list of messages occurred during the open-operation.
   */
  private final List<String> logs;

  /**
   * Constructor. Should not be called manually. Will be called from
   * {@link TptApi#openProject(java.io.File)}.
   * 
   * @param project
   *          the TPT project
   * @param logs
   *          list of log messages
   */
  public OpenResult(Project project, List<String> logs) {
    super();
    this.project = project;
    this.logs = logs;
  }

  /**
   * Returns a list of log entries that have occurred during the parsing of the TPT file.
   * 
   * @return List of log messages. Empty if no errors/warning have occurred.
   */
  public List<String> getLogs() {
    return logs;
  }

  /**
   * @return The freshly opened TPT project
   */
  public Project getProject() {
    return project;
  }

}
