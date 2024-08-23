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
 * For further information, please refer to the User Guide, section ADTF Platform.
 */
public interface Adtf extends BasicPlatformConfig {

  // properties:
  /**
   * Path to the ADTF project file
   */
  public static final String PROJECT_FILE = "adtf-projectfile";

  /**
   * List of ADTF DDL files separated by semicolons
   */
  public static final String DDL_FILE = "adtf-ddlfiles";

  /**
   * Path to the ADTF global configuration file
   */
  public static final String GLOBAL_XML_FILE = "adtf-globalxmlfile";

  /**
   * Working directory of the platform
   */
  public static final String WORKING_DIR = "adtf-workingdir";

  /**
   * Name of the default configuration of an ADTF project
   */
  public static final String DEFAULT_CONFIG = "adtf-default-config";

  /**
   * Name of the selected ADTF configuration
   */
  public static final String SELECTED_CONFIG_NAME = "adtf-selected-config-name";

  /**
   * Boolean flag whether the selected ADTF configuration is the default of the project
   */
  public static final String SELECCONFIG_ISDEFAULT = "adtf-selecconfig-isdefault";

  /**
   * Path to a script (optionally with arguments) to startup ADTF
   */
  public static final String STARTUP_SCRIPT = "adtf-startupscript";

  /**
   * Path to a script (optionally with arguments) to shutdown ADTF
   */
  public static final String SHUTDOWN_SCRIPT = "adtf-shutdownscript";

  /**
   * Path to the dynamic binding header file if TPT is a dynamic binding client or server
   */
  public static final String DYN_BIND_HEADER = "adtf-dynbindheader";

  /**
   * Dynamic binding role of TPT. Allowed values are "Server" (TPT is a dynamic binding server),
   * "Client" (TPT is a dynamic binding client), and "None" (TPT has no dynamic binding)
   */
  public static final String DYN_BIND_ROLE = "adtf-dynbindrole";

  /**
   * Boolean flag whether platform execution is skipped during test execution
   */
  public static final String SKIP_EXEC = "adtf-skipexec";

  /**
   * Boolean flag whether test execution should only initialize ADTF but not start ADTF simulation
   * automatically
   */
  public static final String INIT_ONLY = "adtf-initonly";

  /**
   * Path to the ADTF manifest file
   */
  public static final String MANIFEST = "adtf-manifest";

  /**
   * Path to a script (optionally with arguments) to start ADTF during extract project
   */
  public static final String ANA_SCRIPT = "adtf-anascript";

  /**
   * Defines the time master in ADTF. Allowed values are "TPT" and "Other filter"
   */
  public static final String TIME_MASTER = "adtf-timemaster";

  /**
   * Defines whether the ADTF version is at least 3.0 or newer
   */
  public static final String NEW_VERSION = "adtf-new-version";

  /**
   * Defines whether properties of other ADTF filters are initialized before test execution starts
   */
  public static final String INIT_AT_TEST_START = "adtf-init-at-start";
}
