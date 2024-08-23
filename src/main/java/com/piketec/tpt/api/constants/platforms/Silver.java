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

/**
 * For further information, please refer to the User Guide, Silver Platform.
 */
public interface Silver extends BasicPlatformConfig {

  /**
   * Key for the working folder of the Silver platform in TPT.
   */
  public static final String WORKING_DIRECTORY = "simbenchdir";

  /**
   * Key for the path to the Silver project file (*.sil)
   */
  public static final String SILVER_PROJECT_FILE = "silverProjectFile";

  /**
   * Key for boolean flag speedup. If <code>true</code> the parameter "-s" is given to Silver in
   * order to ignore real time and run simulation as fast as possible. Without this option, Silver
   * tries to run the co-simulation in real time. Setting this option overrides the run mode
   * specified in the Silver project file.
   */
  public static final String SILVER_SPEEDUP = "silverSpeedup";

  /**
   * Key for the path to the log file of Silver test execution. If specified, redirect all logs from
   * standard output to the given log file.
   */
  public static final String SILVER_LOG_FILE = "silverLogFile";

  /**
   * Key for additional command line parameters given to Silver at startup, e.g. configuration
   * parameters such as "-D &lt;sfuParam=value&gt;".
   */
  public static final String SILVER_ADDITIONAL_COMMANDS = "additionalCommands";

  /**
   * Key for the installation path of Silver. Specification not needed if environmental variable
   * "SILVER_HOME" exists.
   */
  public static final String SILVER_INSTALLATION_DIRECTORY = "silverInstallDir";

  /**
   * Key for boolean flag how to close Silver during interactive execution. If <code>true</code>
   * Silver will be closed automatically after test execution (via Silver command line parameter
   * '--autoClose')
   */
  public static final String SILVER_AUTOCLOSE_AT_INTERACTIVE_EXECUTION = "autoCloseInteractive";

  /**
   * Key for boolean flag whether to disable the GUI module in Silver during test execution. If
   * <code>true</code> the GUI module in Silver is disabled (via Silver command line parameter '-G')
   */
  public static final String SILVER_DISABLE_GUI_MODULE = "disableGuiModule";

  /**
   * Key for boolean flag how to start Silver. If <code>true</code> Silver will be started with UI
   * (using "silver.exe"), otherwise without UI (using "silversim.exe -c").
   */
  public static final String TPT_INTERACTIVE_EXECUTION = "interactiveExecution";

  /**
   * Key for boolean flag whether to use a 32-bit VM-DLL for execution. If <code>true</code> the
   * 32-bit VM-DLL will be used, otherwise the 64-bit VM-DLL
   */
  public static final String TPT_32BIT_EXECUTION = "32bitExecution";

  /**
   * Key for boolean flag whether to prohibit concurrent execution even if multiple cores are
   * enabled in the execution configuration.<br>
   * This is useful especially if the execution requires some external resources that do not allow
   * parallel execution of different tests.
   */
  public static final String TPT_SINGLE_THREADED_EXECUTION = "singleThreadedExecution";

  /**
   * Key for boolean flag whether scaling information is applied when exchanging values with
   * Silver.<br>
   * If <code>true</code> and a mapping with scaling information is given, the raw values are
   * exchanged, otherwise the physical values are exchanged.
   */
  public static final String TPT_APPLY_SCALING = "applyScaling";

  /**
   * Key for boolean flag whether the scaling result is rounded when exchanging values with Silver.
   * Only relevant if the flag {@link #TPT_APPLY_SCALING} is <code>true</code> and a mapping with
   * scaling information is given.
   */
  public static final String TPT_ROUND_SCALING_RESULTS = "roundScalingResult";

  /**
   * Key for the used coverage tool. As value use either 'CTC++' for using CTC++, 'GNU gcov' for
   * using the tool gcov of the MinGW compiler, or an empty string for disabling code coverage.
   */
  public static final String COVERAGETOOL = "coveragetool";

  /**
   * Key for the path to the GCov data directory of Silver if 'GNU gcov' coverage is used for test
   * execution. If specified, it denotes the directory of the GCov .gcno files from compiling. If
   * not specified, the folder 'gcov_obj' in the working folder of the Silver platform is taken as
   * default.
   */
  public static final String SILVER_GCOV_DATA_DIR = "silverGCovDir";

  /**
   * Key for boolean flag whether gcov coverage data is cumulated. If <code>true</code> the data
   * files are not deleted at platform startup during test execution.
   */
  public static final String SILVER_GCOV_CUMULATE = "silverGCovCumulate";

  /**
   * Old command line property used before TPT-20. Please use the other properties instead.
   */
  @Deprecated
  public static final String COMMAND = "command";
}
