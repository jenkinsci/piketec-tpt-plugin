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
package com.piketec.tpt.api.cplatform;

/**
 * The TASMO instrumentation settings usable in the C\C++ platform in TPT
 * 
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 * 
 * @deprecated The instrumentation level is not available as a setting in the C\C++ platform
 *             anymore. To enable coverage measurement use <code>setCoverageToTPT</code> in
 *             <code>CCodePlatformConfiguration</code>. Will be removed in TPT-23.
 */
@Deprecated
public enum TasmoCCodeInstrumentationLevel {

  /**
   * "No instrumentation"
   */
  NO_INSTRUMENTATION,

  /**
   * "Instrument for coverage measurement only, test data generation with TASMO not available"
   */
  COVERAGE_MEASUREMENT,

  /**
   * "Full instrumentation, required for test data generation with TASMO"
   */
  FULL_INSTRUMENTATION;

}
