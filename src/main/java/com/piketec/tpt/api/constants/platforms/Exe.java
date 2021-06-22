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
package com.piketec.tpt.api.constants.platforms;

/**
 * For further information, please refer to the User Guide, EXE Platform.
 */
public interface Exe extends BasicPlatformConfig {

  public static final String WORKING_DIR = "workingdir";

  public static final String EXECUTABLE = "executable";

  public static final String EXECARGUMENTS = "execarguments";

  public static final String CFILE = "cfile";

  public static final String COVERAGETOOL = "coveragetool";

  public static final String CUMCOV = "cumcov";

  public static final String COVERAGE_FILES = "coverageFiles";

  public static final String SINGLE_THREADED = "singlethreaded";

  public static final String ENABLE_READ_WRITE = "enablerw";

  public static final String CALL_TPT_VM_API_BIND_SIGNAL_FINALIZE =
      "callTptVmapiBindSignalFinalize";

  public static final String CALL_TPT_VM_API_INIT_TEST_OUTPUTS = "callTptVmapiInitTestOutputs";

  public final String ROUNDSCALINGRESULTS = "roundscalingresults";

  public static final String USE_EFFECTIVE_INTERFACE = "useEffectiveInterface";

}
