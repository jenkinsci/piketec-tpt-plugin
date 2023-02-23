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
package com.piketec.tpt.api.constants.platforms;

/**
 * For further information, please refer to the User Guide, FEP Platform.
 */
public interface Fep extends BasicPlatformConfig {

  // Property-Keys:
  /**
   * Working directory of the platform
   */
  public static final String FEP_WORKING_DIR = "fep-workingdir";

  /**
   * Path of the FEP system file
   */
  public static final String FEP_SYS = "fep-system";

  /**
   * List of FEP DDL files separated by semicolons
   */
  public static final String FEP_DDL = "fep-ddl";

  /**
   * List of FEP mapping files separated by semicolons
   */
  public static final String FEP_MAP = "fep-map";

  /**
   * Path to a script (optionally with arguments) to startup FEP
   */
  public static final String FEP_STARTUP_SCRIPT = "fep-startupscript";

  /**
   * Path to a script (optionally with arguments) to shutdown FEP
   */
  public static final String FEP_SHUTDOWN_SCRIPT = "fep-shutdownscript";

  /**
   * Defines the time master in FEP. Allowed values are "TPT" and "Other filter"
   */
  public static final String FEP_TIME_MASTER = "fep-master";

  /**
   * Name of the FEP element that is the time master in FEP if TPT is not the time master
   */
  public static final String FEP_TIME_MASTER_ELEM = "fep-master-elem";

  /**
   * Defines the trigger mode of the FEP timing master. Allowed values are "AFAP", "System Time",
   * "System Time x 2", or "System Time x 4"
   */
  public static final String FEP_TIME_MASTER_FACTOR = "fep-master-factor";

  /**
   * Defines how TPT should be triggered. Allowed values are "Time" (TPT is triggered by FEP
   * simulation time) and "Event" (TPT is triggered via an event channel)
   */
  public static final String FEP_TRIGGER = "fep-trigger";

  /**
   * Name of the event channel if TPT is event triggered
   */
  public static final String FEP_TRIGGER_CHANNEL = "fep-trigger-chan";

  /**
   * Name of the FEP installation
   */
  public static final String FEP_INSTALL_NAME = "fep-installname";

  /**
   * Path of the FEP installation
   */
  public static final String FEP_INSTALL_PATH = "fep-installpath";

  /**
   * Flag whether the FEP installation is a CONAN build
   */
  public static final String FEP_CONAN_BUILD = "fep-conanbuild";

  /**
   * Compile profile for CONAN build
   */
  public static final String FEP_CONAN_PROFILE = "fep-conanprofile";

  /**
   * Name of FEP simulation system
   */
  public static final String FEP_SYS_NAME = "fep-sysname";

  /**
   * Description of FEP simulation system
   */
  public static final String FEP_SYS_DESC = "fep-sysdesc";

  /**
   * Version of FEP simulation system
   */
  public static final String FEP_SYS_VERS = "fep-sysvers";

  /**
   * Contact of FEP simulation system
   */
  public static final String FEP_SYS_CONT = "fep-syscont";

  /**
   * Identifier for the property of the FEP system configuration
   */
  public static final String FEP_SYS_CONFIG = "fep-sysconfig";

  /**
   * Name of FEP system configuration
   */
  public static final String FEP_SYS_CONFIG_NAME = "fep-sysconfig-name";

  /**
   * Name of FEP subsystem or element
   */
  public static final String FEP_SUBSYS_ELEM_NAME = "fep-subsys-elem-name";

  /**
   * Priority of FEP subsystem or element
   */
  public static final String FEP_SUBSYS_ELEM_PRIO = "fep-subsys-elem-prio";

  /**
   * Status of FEP element
   */
  public static final String FEP_ELEM_STATUS = "fep-elem-status";

  /**
   * Type of FEP element
   */
  public static final String FEP_ELEM_TYPE = "fep-elem-type";

  /**
   * Comma separated list of channel names that have to be modified by TPT for the FEP element
   */
  public static final String FEP_ELEM_MODIFIED = "fep-elem-modified";

}
