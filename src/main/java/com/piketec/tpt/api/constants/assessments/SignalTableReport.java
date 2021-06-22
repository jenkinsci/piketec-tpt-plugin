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
 * Assessment Type 'report_signaltable'.
 */
public interface SignalTableReport extends BasicAssessment, SignalFilter {

  public static final String TRACE_TABLE = "show-as-trace-table";

  public static final String STATE_INFO = "show-state-information";

  public static final String SUB_ELEMENTS = "show-sub-elements";

  public static final String INITIAL_VALUES = "show-initial-values";

  public static final String SHOW_CONSTANTS = "show-constants";

  public static final String SHOW_SYSCONSTANTS = "show-sysconstants";

  public static final String SORTING = "sorting";

  public static final String SORTING_CRITERION = "criterion";

  public static final String SORTING_ORDER = "order";

  /**
   * Criterion types for sorting
   */
  public interface SortingCriterion {

    public static final String NAME = "NAME";

    public static final String TYPE = "TYPE";

    public static final String FROM = "FROM";

    public static final String TO = "TO";

    public static final String COMMENT = "COMMENT";
  }

  /**
   * Order types for sorting
   */
  public interface SortingOrder {

    public static final String ASCENDING = "ASCENDING";

    public static final String DESCENDING = "DESCENDING";
  }

}
