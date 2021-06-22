/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2021 PikeTec GmbH
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
 * For further information, please refer to the User Guide or Assessment Manual, section TPT
 * Assessment Type 'signalcomparison'.
 */
public interface SignalComparison extends BasicAssessment {

  /**
   * Possible values for {@link SignalComparison#REFERENCE_SOURCE}.
   *
   */
  public interface ReferenceSource {

    public static final String TESTRUN_ONLY = "TESTRUN_ONLY";

    public static final String REFERENCE_DIR = "REFERENCE_DIR";

    public static final String FILE = "FILE";

    public static final String BACK2BACK = "BACK2BACK";
  }

  public static final String REFERENCE_SOURCE = "reference-source";

  public static final String REF_FILE = "reference-file";

  public static final String IGNORE_SUFFIX = "ignore-suffix";

  public static final String EXPORT_REF = "export-reference-signal";

  public static final String EXPORT_DIFF = "export-difference-signal";

  public static final String EXPORT_ABSOLUTE_DIFF = "export-absolute-difference-signal";

  public static final String USE_PATTERNS_TO_HIGHLIGHT = "use-patterns-to-highlight-signal";

  public static final String REF_SUFFIX = "reference-suffix";

  public static final String IGNORE_TIME_BOUNDRIES = "ignore-time-boundaries";

  public static final String REF_DATA_TIMESHIFT = "reference-data-timeshift";

  public static final String DIFF_TO_HOSE_SUFFIX = "diff-suffix";

  public static final String DIFF_TO_REF_SUFFIX = "abs-diff-suffix";

  public static final String MAPPING = "mappig";

  public static final String CHANNEL = "channel";

  public static final String REF_NAME = "reference-name";

  public static final String TIME_TOLERANCE = "time-tolerance";

  public static final String VALUE_TOLERANCE = "value-tolerance";

  public static final String RELATIVE_TOLERANCE = "relative-tolerance";

  public static final String DISABLED = "disabled";

  public static final String LSB_TOLERANCE = "lsb-tolerance";

  public static final String REFERENCE_SPECIFICATIONS = "reference-specifications";

  /***
   * @deprecated Use {@link #REFERENCE_SPECIFICATIONS}
   */
  @Deprecated
  public static final String REFERENCE_SEPCIFICATIONS = REFERENCE_SPECIFICATIONS;

  public static final String EXECUTION_CONFIG = "execution-config";

  public static final String EXECUTION_ITEM_IDX = "item-index";

  public static final String EXECUTION_CONFIG_ITEM = "execution-config-item";

  public static final String DISPLAY_ABSOLUTE_DIFFERENCE = "display-absolute-difference";

  public static final String IGNORE_UNDEFINED_TIME_PHASES = "ignore-undefined-time-phases";

  public static final String COMPARE_ON_CHANGE = "compare-on-change";

}
