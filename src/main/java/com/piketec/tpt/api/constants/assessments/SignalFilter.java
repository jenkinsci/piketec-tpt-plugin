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
 * For further information, please refer to the User Guide or Assessment Manual, section TPT
 * Assessment Type 'SignalTableReport' and 'SignalGraphicReport'.
 */
public interface SignalFilter {

  public static final String SHOW_ONLY_DIFFER_FROM_DEFAULT = "show-only-differ-from-default";

  public static final String FILTER_PATTERN = "filter";

  public static final String IS_INCLUDE_FILTER = "is-include-filter";

  public static final String SHOW_INPUTS = "show-inputs";

  public static final String SHOW_OUTPUTS = "show-outputs";

  public static final String SHOW_LOCALS = "show-locals";

  public static final String SHOW_MEASUREMENTS = "show-meassurements";

  public static final String SHOW_PARAMETERS = "show-parameters";

  public static final String SHOW_ASSESSMENT_VARIABLES = "show-assessment-variables";

  public static final String SHOW_CHANGING_SIGNALS = "show-changing-signals";

  public static final String SHOW_CONSTANT_SIGNALS = "show-constant-signals";

  public static final String SHOW_VARS_WITH_INCONCLUSIVE_RESULT =
      "show-vars-with-inconclusive-result";

  public static final String DEBUG_SHOW_NON_EXPORT_VARS = "show-non-exported";

  public static final String SHOW_SCRIPT_VARAIABLES = "show-script-variables";

  public static final String SIGNALS = "signals";

}
