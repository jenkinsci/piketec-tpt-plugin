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
 * For further information, please refer to the User Guide, section Global Equivalence classes
 * Assesslet.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface GlobalEquivalenceClasses extends BasicAssessment {

  /**
   * Property whether report as <code>Failed</code> if there are values outside any equivalence
   * class.
   */
  public static final String FAIL_IF_VALUES_OUTSIDE_ANY = "fail-if-values-outside-any";

  /**
   * Property whether unassigned equivalence classes that have been covered should be reported
   * (display equivalence classes in the test report that are not defined as mandatory and were
   * hit).
   */
  public static final String REPORT_UNASSIGNED_COVERED_ECS = "report-unassigned-covered";

  /**
   * Property whether unassigned equivalence classes that have not been covered should be reported
   * (display equivalence classes in the test report that are not defined as forbidden and were not
   * hit).
   */
  public static final String REPORT_UNASSIGNED_NON_COVERED_ECS = "report-unassigned-non-covered";

  /**
   * Property whether the equivalence class information should also be reported in the overview.
   */
  public static final String REPORT_IN_OVERVIEW = "report-overview";

  /**
   * Property whether the two-point boundary value coverage should be tested.
   */
  public static final String BOUNDARY_TWO_POINT = "boundary-two-point";

  /**
   * Property whether the three-point boundary value coverage should be tested.
   */
  public static final String BOUNDARY_THREE_POINT = "boundary-three-point";

  /**
   * Property for the list of rows of the Global Equivalence Classes Assesslet. Each row checks if
   * all executed test cases use values from any given equivalence class. This list is represented
   * by the table 'Equivalence class evaluations' in the GUI.
   */
  public static final String ROW_SPECIFICATIONS = "row-specifications";

  /**
   * Property whether this row is enabled. Only enabled rows will be considered during execution.
   */
  public static final String ROW_ENABLED = "row-enabled";

  /**
   * Property for the signal name that shall be evaluated for a row.
   */
  public static final String ROW_VARIABLE = "row-variable";

  /**
   * Property for the list of equivalence classes that shall be covered by the test cases for the
   * signal.
   */
  public static final String ROW_MANDATORY = "row-mandatory";

  /**
   * Property for the list of equivalence classes that shall be not covered by the test cases for
   * the signal.
   */
  public static final String ROW_FORBIDDEN = "row-forbidden";

}
