package com.piketec.tpt.api.constants.assessments;

/**
 * With this global assesslet it can be evaluated if options of the used code coverage tools have
 * reached a given threshold. The threshold value can be set individually for each option. For
 * further information, please refer to the User Guide, section Global Coverage Assesslet.
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
