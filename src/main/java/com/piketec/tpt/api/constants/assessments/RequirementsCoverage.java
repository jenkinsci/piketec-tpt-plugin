/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2022 PikeTec GmbH
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
 * For further information, please refer to the User Guide, section Requirements Assesslet.
 */
public interface RequirementsCoverage extends BasicAssessment {

  /**
   * Property whether requirement checks will be ignored if the requirements is not linked to the
   * executed test case (or variant).
   */
  public static final String CHECK_ONLY_LINKED = "check-only-linked";

  /**
   * Property whether the results of requirements that were not checked in assesslets will be
   * derived from the result of the current test case.
   */
  public static final String DERIVE_RESULT_FROM_TEST_CASE_IF_NOT_CHECKED = "derive-from-test-case";

  /**
   * Property whether a requirements report will be generated
   */
  public static final String GENERATE_REQUIREMENTS_REPORT = "generate-requirements-report";

  /**
   * Property whether the requirements results table in the requirements report will show
   * requirements which are not covered
   */
  public static final String SHOW_REQUIREMENTS_NOT_COVERED = "show-not-covered";

  /**
   * Property whether the requirements results table in the requirements report will show headings
   */
  public static final String SHOW_HEADING_ROWS = "show-heading-rows";

  /**
   * Property whether the requirements results table in the requirements report will show
   * information objects
   */
  public static final String SHOW_INFORMATION_ROWS = "show-information-rows";

  /**
   * Property whether the requirements results table in the requirements report will have a column
   * for detailed results
   */
  public static final String SHOW_DETAILED_RESULTS = "show-detailed-results";

  /**
   * Property whether the requirements results table in the requirements report will have a column
   * with requirement comments
   */
  public static final String SHOW_COMMENTS = "show-comments";

  /**
   * Property whether the requirements results table in the requirements report will have a column
   * with document versions
   */
  public static final String SHOW_DOCUMENT_VERSIONS = "show-document-versions";

  /**
   * Property whether a table with assesslet verdicts of requirements will be present in the
   * requirements report
   */
  public static final String GENERATE_ASSESSLET_RESULTS_TABLE = "generate-assesslet-results-table";

  /**
   * Property whether to check that all requirements are linked to an executed test case, directly
   * or indirectly via variants
   */
  public static final String CHECK_ALL_REQUIREMENTS_LINKED_TO_TEST_CASES =
      "check-all-requirements-linked-to-test-cases";

  /**
   * Property whether to check that all requirements are evaluated with <code>PASSED</code> or
   * <code>FAILED</code>
   */
  public static final String CHECK_ALL_REQUIREMENTS_PASSED_OR_FAILED =
      "check-all-requirements-passed-or-failed";

  /**
   * Property whether to check that requirements are evaluated with <code>PASSED</code> or
   * <code>FAILED</code> in linked test cases
   */
  public static final String CHECK_REQUIREMENTS_PASSED_OR_FAILED_IN_LINKED_TEST_CASES =
      "check-requirements-passed-or-failed-in-linked-test-cases";

  /**
   * Property whether to check that requirements are evaluated with
   * <code>REQUIREMENTS.checked()</code> in linked script assesslets
   */
  public static final String CHECK_REQUIREMENTS_CHECKED_IN_LINKED_SCRIPT_ASSESSLET =
      "check-requirements-checked-in-linked-script-assesslet";

  /**
   * Property whether to check that requirements are not evaluated with
   * <code>REQUIREMENTS.checked()</code> in unlinked script assesslets
   */
  public static final String CHECK_NO_REQUIREMENT_CHECKED_IN_UNLINKED_SCRIPT_ASSESSLET =
      "check-no-requirement-checked-in-unlinked-script-assesslet";

  /**
   * Property whether the assessment should fail in case of failed integrity checks
   * 
   * @see #CHECK_ALL_REQUIREMENTS_LINKED_TO_TEST_CASES
   * @see #CHECK_ALL_REQUIREMENTS_PASSED_OR_FAILED
   * @see #CHECK_REQUIREMENTS_PASSED_OR_FAILED_IN_LINKED_TEST_CASES
   * @see #CHECK_REQUIREMENTS_CHECKED_IN_LINKED_SCRIPT_ASSESSLET
   * @see #CHECK_NO_REQUIREMENT_CHECKED_IN_UNLINKED_SCRIPT_ASSESSLET
   */
  public static final String FAIL_IF_INTEGRITY_CHECKS_FAIL = "fail-if-integritiy-checks-fail";

  /**
   * Property for the list of requirement attributes which will be shown in additional columns in
   * the requirements results table in the requirements report
   */
  public static final String ADDITIONAL_ATTRIBUTES_IN_REQUIREMENTS_REPORT_TABLE =
      "additional-attributes-in-requirements-report-table";

  /**
   * Property whether a requirements table will be generated in test case reports
   */
  public static final String GENERATE_TABLE_IN_TEST_CASE_REPORTS =
      "generate-table-in-test-case-reports";

  /**
   * Property for the list of requirement attributes which will be shown in additional columns in
   * the requirements table in the test case report
   */
  public static final String ADDITIONAL_ATTRIBUTES_IN_TEST_CASE_REPORT_TABLE =
      "additional-attributes-in-test-case-report-table";

}
