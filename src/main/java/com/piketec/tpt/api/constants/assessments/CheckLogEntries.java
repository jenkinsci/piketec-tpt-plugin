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
 * For further information, please refer to the User Guide, Check Log Entries Assesslet.
 */
public interface CheckLogEntries extends BasicAssessment {

  public static final String PATTERN = "pattern";

  /**
   * Attribute key to set whether the "Assesslet is ..."
   * <ul>
   * <li><code>PASSED if pattern matches</code></li>
   * <li><code>FAILED if pattern matches</code></li>
   * <li><code>FAILED if pattern does not match</code></li>
   * </ul>
   */
  public static final String CHECK_TYPE = "check-type";

  /**
   * Attribute value <code>FAILED if pattern matches</code> for the attribute {@link #CHECK_TYPE}.
   */
  public static final String FAILED_IF_MATCH = "failed_if_match";

  /**
   * Attribute value <code>FAILED if pattern does not match</code> for the attribute
   * {@link #CHECK_TYPE}.
   */
  public static final String FAILED_IF_NOMATCH = "failed_if_nomatch";

  /**
   * Attribute value <code>PASSED if pattern matches</code> for the attribute {@link #CHECK_TYPE}.
   */
  public static final String SUCCESS_IF_MATCH = "success_if_match";

}
