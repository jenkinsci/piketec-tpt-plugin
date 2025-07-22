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
 * With this global assesslet it can be evaluated if options of the used code coverage tools have
 * reached a given threshold. The threshold value can be set individually for each option. For
 * further information, please refer to the User Guide, section Global Coverage Assesslet.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface GlobalCoverage extends BasicAssessment {

  /**
   * Property for the list of rows of the Global Coverage Assesslet. Each row defines a threshold
   * that must be reached by all coverage tools used in executed execution configuration. This list
   * is represented by the table in the GUI.
   */
  public static final String SPECIFICATIONS = "specifications";

  /**
   * Property for coverage tool option.
   */
  public static final String COVERAGE_OPTION = "coverage-option";

  /**
   * Property for coverage tool option threshold.
   */
  public static final String COVERAGE_THRESHOLD = "coverage-threshold";

  /**
   * Coverage option name. Can be set via {@link #COVERAGE_OPTION}.
   */
  public static final String STATEMENT_COV = "Statement";

  /**
   * Coverage option name. Can be set via {@link #COVERAGE_OPTION}.
   */
  public static final String FUNCTION_COV = "Function";

  /**
   * Coverage option name. Can be set via {@link #COVERAGE_OPTION}.
   */
  public static final String DECISION_COV = "Decision";

  /**
   * Coverage option name. Can be set via {@link #COVERAGE_OPTION}.
   */
  public static final String CONDITION_COV = "Condition";

  /**
   * Coverage option name. Can be set via {@link #COVERAGE_OPTION}.
   */
  public static final String MULTICONDITION_COV = "Multicondition";

  /**
   * Coverage option name. Can be set via {@link #COVERAGE_OPTION}.
   */
  public static final String EXECUTION_COV = "Execution";

  /**
   * Coverage option name. Can be set via {@link #COVERAGE_OPTION}.
   */
  public static final String MCDC_COV = "MC/DC";

  /**
   * Coverage option name. Can be set via {@link #LINE_COV}.
   */
  public static final String LINE_COV = "Line";

  /**
   * Coverage option name. Can be set via {@link #BRANCH_COV}.
   */
  public static final String BRANCH_COV = "Branch";

  /**
   * List of options already supported by TPT. If a option is not listed here, it means that it is
   * not known yet, but may still be supported by TPT.
   */
  public static final String[] COV_OPTIONS_LIST = { STATEMENT_COV, FUNCTION_COV, DECISION_COV,
      CONDITION_COV, MULTICONDITION_COV, MCDC_COV, EXECUTION_COV, LINE_COV, BRANCH_COV };

}
