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
 * For further information, please refer to the User Guide, section Trigger Rule Assesslet.
 */
public interface TriggerRule extends BasicAssessment {

  public static final String TYPE = "trigger-type";

  public static final String SAMPLE_FILTER_EXPRESSION = "trigger-expression";

  public static final String ABORT_OR_IGNORE_CONDITION = "abort-ignore-expression";

  public static final String START_CONDITION = "start-condition";

  public static final String STOP_CONDITION = "stop-condition";

  public static final String ALWAYS_OR_ONCE = "always-or-once";

  public static final String THEN_LIST = "then-expressions";

  public static final String ELSE_LIST = "else-expressions";

  public static final String SHOW_STATE_INFO = "show-state-info";

  public static final String IGNORE_LAST = "ignore-last-seconds";

  public static final String IGNORE_FIRST = "ignore-first-seconds";

  public static final String ERROR_IF_NO_MATCH = "error-if-no-match";

  public static final String ERROR_IF_ALWAYS_MATCH = "error-if-always-match";

  public static final String MAX_LENGTH = "max-time-length";

}
