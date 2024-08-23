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
package com.piketec.tpt.api.constants.assessments;

/**
 * With this global assesslet, all test cases of a test execution can be evaluated, the test results
 * of all test cases can be summarized, and new calculations can be made from the available results
 * of the individual test cases. For further information, please refer to the User Guide, section
 * Global Variable Assesslet.
 */
public interface GlobalVariable extends BasicAssessment {

  /**
   * Property for the list of rows of the Global Variable Assesslet. Each row defines constraints
   * for a variable that must hold for a whole test execution, so for all executed test cases
   * together. This list is represented by the table in the GUI.
   */
  public static final String SPECIFICATIONS = "specifications";

  /**
   * Property whether this row is enabled. Only enabled rows will be considered during execution.
   */
  public static final String ROW_ENABLED = "row-enabled";

  /**
   * Property for the assessment variable that shall be evaluated for all executed test cases.
   */
  public static final String VARIABLE = "variable";

  /**
   * Property for the maximum average value constraint. The average value (arithmetic mean over all
   * testcases) must be between {@link #AVERAGE_MIN} and {@link #AVERAGE_MAX}
   */
  public static final String AVERAGE_MAX = "average-max";

  /**
   * Property for the minimum average value constraint. The average value (arithmetic mean over all
   * testcases) must be between {@link #AVERAGE_MIN} and {@link #AVERAGE_MAX}
   */
  public static final String AVERAGE_MIN = "average-min";

  /**
   * Property for the maximum count constraint. Total count of occurrences (in all testcases) must
   * between {@link #COUNT_MIN} and {@link #COUNT_MAX}
   */
  public static final String COUNT_MAX = "count-max";

  /**
   * Property for the minimum count constraint. Total count of occurrences (in all testcases) must
   * between {@link #COUNT_MIN} and {@link #COUNT_MAX}
   */
  public static final String COUNT_MIN = "count-min";

  /**
   * Property for the maximum value constraint. Each variable value (one value per testcase) must be
   * between {@link #VALUE_MIN} and {@link #VALUE_MAX}
   */
  public static final String VALUE_MAX = "val-max";

  /**
   * Property for the minimum value constraint. Each variable value (one value per testcase) must be
   * between {@link #VALUE_MIN} and {@link #VALUE_MAX}
   */
  public static final String VALUE_MIN = "val-min";

  /**
   * Property for the maximum sum constraint. The sum of all variables (from all testcases) must be
   * between {@link #SUM_MIN} and {@link #SUM_MAX}
   */
  public static final String SUM_MAX = "sum-max";

  /**
   * Property for the minimum sum constraint. The sum of all variables (from all testcases) must be
   * between {@link #SUM_MIN} and {@link #SUM_MAX}
   */
  public static final String SUM_MIN = "sum-min";

}
