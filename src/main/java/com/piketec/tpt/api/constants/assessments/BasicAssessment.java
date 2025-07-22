/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2025 Synopsys Inc.
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
package com.piketec.tpt.api.constants.assessments;

/**
 * For further information, please refer to the User Guide, section Common Assesslet Settings.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface BasicAssessment {

  public static final String ENABLED = "enabled";

  public static final String COMMENT = "comment";

  public static final String ERROR_IF_NOT_EXECUTED = "error-if-not-executed";

  public static final String EXEC_CONDITION = "execution-condition";

  public static final String VERBOSEREPORT = "verbose-report";

  public static final String REPORT_SECTION = "report-section";

  public static final String ASSESSLET_INTERVAL_TYPE = "intervall-type";

  public static final String ALWAYS_TYPE = "always";

  public static final String ERROR_IF_NO_MATCH = "error-if-no-match";

  public static final String REGEX_TYPE = "regex";

  public static final String TESTLET_TYPE = "testlet";

  public static final String STEP_CONTEXT_TYPE = "step";

  public static final String REGEX_VALUE = "regex";

  public static final String TESTLET_PATHS = "testlets";

  public static final String STEP_PATHS = "steps";

  public static final String TESTLET = "testlet";

  public static final String VARIANT = "variant";

  public static final String STEP_INDEX = "step-index";

  /**
   * Time context is not supported by back-to-back-, check log entries-, import measurements-, step
   * list-, timeout- and all report assesslets.
   */
  public static final String INTERVAL = "interval";

  /**
   * Not supported by m-script-, variable definitions- and report paragraph-assesslet.
   */
  public static final String COLLAPSE_REPORT_SECTION = "collapse-report-section";

}
