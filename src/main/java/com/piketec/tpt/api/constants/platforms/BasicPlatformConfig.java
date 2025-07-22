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
package com.piketec.tpt.api.constants.platforms;

/**
 * For further information, please refer to the User Guide, section Platform Configuration.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface BasicPlatformConfig {

  public static final String STEPSIZE = "stepsize";

  public static final String TIMEOUT = "timeout";

  public static final String HISTORY = "history";

  public static final String READ_PARAMS_ONLY_FIRST = "readparamsonlyfirst";

  public static final String MAPPING_NAME = "mappingname";

  /**
   * Key for boolean flag whether to use for channels the effective interface of a test case. If
   * <code>true</code>, the number of channels exchanged with the SUT at test runtime is limited to
   * those channels that are effectively read and/or written.
   */
  public static final String USE_EFFECTIVE_INTERFACE = "useEffectiveInterface";

  /**
   * Key for boolean flag whether to use for parameters the effective interface of a test case. If
   * <code>true</code>, the number of parameters exchanged with the SUT at test runtime is limited
   * to those parameters that are effectively read and/or written (supported only by {@link Silver}
   * platform).
   */
  public static final String USE_EFFECTIVE_INTERFACE_PARAMETERS = "useEffectiveInterfaceParameters";

}
