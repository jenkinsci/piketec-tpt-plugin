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
package com.piketec.tpt.api.constants.platforms;

import com.piketec.tpt.api.cplatform.CCodePlatformConfiguration;

public interface ExeBased extends BasicPlatformConfig, EclipseCdtDebuggingConfig {

  public static final String ROUNDSCALINGRESULTS = "roundscalingresults";

  public static final String SINGLETHREADED = "singlethreaded";

  public static final String CALL_TPT_VM_API_BIND_SIGNAL_FINALIZE =
      "callTptVmapiBindSignalFinalize";

  /**
   * Not supported for {@link CCodePlatformConfiguration}
   */
  public static final String COVERAGE_FILES = "coverageFiles";

  /**
   * Not supported for {@link CCodePlatformConfiguration}
   */
  public static final String CUMCOV = "cumcov";

  public static final String COVERAGETOOL = "coveragetool";
}
