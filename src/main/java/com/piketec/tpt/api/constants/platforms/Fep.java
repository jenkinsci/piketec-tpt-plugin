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
 * For further information, please refer to the User Guide, FEP Platform.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface Fep extends BasicPlatformConfig {

  /**
   * Working directory of the platform
   */
  public static final String WORKING_DIR = "working-dir";

  /**
   * Path of the FEP system description file
   */
  public static final String DESCRIPTION_FILE = "description-file";

  /**
   * Defines the time master in FEP. Allowed values are "TPT" and "Other filter"
   */
  public static final String TIME_MASTER = "time-master";

  /**
   * Name of the FEP element that is the time master in FEP if TPT is not the time master
   */
  public static final String TIME_MASTER_ELEM = "time-master-elem";

  /**
   * Defines the trigger mode of the FEP timing master. Allowed values are "AFAP", "System Time",
   * "System Time x 2", or "System Time x 4"
   */
  public static final String TIME_MASTER_FACTOR = "time-master-factor";

  /**
   * Defines how TPT should be triggered. Allowed values are "Time" (TPT is triggered by FEP
   * simulation time) and "Event" (TPT is triggered via an event channel)
   */
  public static final String TRIGGER = "trigger";

  /**
   * Name of the event channel if TPT is event triggered
   */
  public static final String TRIGGER_CHANNEL = "trigger-channel";

  /**
   * Compile profile for Conan build in FEP Element instance, host, or post deploy dependency
   */
  public static final String CONAN_PROFILE = "conan-profile";

  /**
   * Conan reference (key <code>conan_reference</code> in FEP Element instance, Conan data package,
   * FEP Component, or post deploy dependency)
   */
  public static final String CONAN_REFERENCE = "conan-reference";

  /**
   * Conan options (key <code>conan_options</code> in FEP Element instance, Conan data package, or
   * post deploy dependency)
   */
  public static final String CONAN_OPTIONS = "conan-options";

  /**
   * Conan generators to be invoked during deployment in FEP post deploy dependency or in FEP
   * Element instance of a host (key <code>conan_generators</code>)
   */
  public static final String CONAN_GENERATORS = "conan-generators";

  /**
   * Conan settings in FEP Element instance or host
   */
  public static final String CONAN_SETTINGS = "conan-settings";

  /**
   * Identifier for the properties of the FEP system description
   */
  public static final String SYS_DESCRIPTION = "sys-description";

  /**
   * Metamodel specification version used in the FEP system description (key
   * <code>fep_metamodel_specification_version</code>)
   */
  public static final String SYS_METAMODEL_SPECIFICATION_VERSION = "sys-spec-version";

  /**
   * List of Conan references as baselines (key <code>baselines</code> in the FEP system
   * description)
   */
  public static final String SYS_BASELINES = "sys-baselines";

  /**
   * Identifier for the properties of the FEP system specification (key <code>system</code> in the
   * FEP system description)
   */
  public static final String SYS_SYSTEM = "sys-system";

  /**
   * Identifier for the information necessary for the deployment and launch of the FEP Elements (key
   * <code>deploy_and_launch</code> in the FEP system description)
   */
  public static final String SYS_DEPLOY_AND_LAUNCH = "sys-deploy-and-launch";

  /**
   * Identifier for the properties of the runtime configuration information (key
   * <code>configure</code> in the FEP system description)
   */
  public static final String SYS_CONFIGURE = "sys-configure";

  /**
   * The name of the FEP system specification (key <code>name</code>)
   */
  public static final String SYSTEM_NAME = "system-name";

  /**
   * The description of the FEP system specification (key <code>description</code>)
   */
  public static final String SYSTEM_DESCRIPTION = "system-description";

  /**
   * Simple log level (key <code>fep_log_level</code> in FEP system specification or FEP Element
   * instance)
   */
  public static final String LOG_LEVEL = "log-level";

  /**
   * Complex log level variant defining logger name and severity per logging sink (key
   * <code>fep_log_level</code> in FEP system specification or FEP Element instance)
   */
  public static final String LOG_LEVEL_COMPLEX = "log-level-complex";

  /**
   * Log level for the logging sink 'rpc' in complex log level variant (key
   * <code>fep_log_level</code> in FEP system specification or FEP Element instance)
   */
  public static final String LOG_LEVEL_RPC = "log-level-rpc";

  /**
   * Log level for the logging sink 'console' in complex log level variant (key
   * <code>fep_log_level</code> in FEP system specification or FEP Element instance)
   */
  public static final String LOG_LEVEL_CONSOLE = "log-level-console";

  /**
   * Log level for the logging sink 'file' in complex log level variant (key
   * <code>fep_log_level</code> in FEP system specification or FEP Element instance)
   */
  public static final String LOG_LEVEL_FILE = "log-level-file";

  /**
   * Log level for the logging sink 'file_json' in complex log level variant (key
   * <code>fep_log_level</code> in FEP system specification or FEP Element instance)
   */
  public static final String LOG_LEVEL_FILE_JSON = "log-level-json";

  /**
   * Identifier for the properties of FEP Element instances (key <code>element_instances</code> in
   * FEP system specification or in a host of FEP deploy and launch configuration)
   */
  public static final String ELEMENT_INSTANCES = "element-instances";

  /**
   * Name of a FEP element, data package, host, post deploy dependency, or logging sink
   */
  public static final String NAME = "name";

  /**
   * Identifier for the properties of data packages (key <code>data_packages</code> in FEP system
   * specification or in a host of FEP deploy and launch configuration)
   */
  public static final String DATA_PACKAGES = "data-packages";

  /**
   * Source type of a data package (key <code>source_type</code>)
   */
  public static final String DATA_PACKAGE_SOURCE_TYPE = "data-package-source-type";

  /**
   * URL of a Git data package (key <code>url</code>)
   */
  public static final String DATA_PACKAGE_URL = "data-package-url";

  /**
   * The revision of a Git data package (key <code>revision</code>)
   */
  public static final String DATA_PACKAGE_REVISION = "data-package-revision";

  /**
   * The action which will be applied if the target folder already exists on the target path for a
   * Git data package (key <code>if_target_folder_exists</code>)
   */
  public static final String DATA_PACKAGE_IF_TARGET_FOLDER_EXISTS =
      "data-package-if-target-folder-exists";

  /**
   * The name of the environment variable in which the username for the Git repository is stored for
   * a Git data package (key <code>username_env_var</code>)
   */
  public static final String DATA_PACKAGE_USERNAME_ENV_VAR = "data-package-username-env-var";

  /**
   * The name of the environment variable in which the password for the Git repository is stored for
   * a Git data package (key <code>password_env_var</code>)
   */
  public static final String DATA_PACKAGE_PASSWORD_ENV_VAR = "data-package-password-env-var";

  /**
   * Subfolder of a System folder data package (key <code>subfolder</code>)
   */
  public static final String DATA_PACKAGE_SUBFOLDER = "data-package-subfolder";

  /**
   * Identifier for the properties of timeouts (key <code>timeouts</code> in FEP deploy and launch
   * configuration)
   */
  public static final String TIMEOUTS = "timeouts";

  /**
   * The timeout for launching in <code>timeouts</code> of FEP deploy and launch configuration (key
   * <code>launch_s</code>) or in FEP Element instance of a host (key <code>launch_timeout_s</code>)
   */
  public static final String TIMEOUT_LAUNCH = "timeout-launch";

  /**
   * The timeout for shutdown in <code>timeouts</code> of FEP deploy and launch configuration (key
   * <code>shutdown_s</code>) or in FEP Element instance of a host (key
   * <code>shutdown_timeout_s</code>)
   */
  public static final String TIMEOUT_SHUTDOWN = "timeout-shutdown";

  /**
   * The timeouts of state transitions during runtime (key <code>fep_state_transitions_s</code>) in
   * <code>timeouts</code> of FEP deploy and launch configuration
   */
  public static final String TIMEOUT_STATE_TRANSITIONS = "timeout-state-transitions";

  /**
   * Identifier for the properties of the hosts (key <code>hosts</code> in FEP deploy and launch
   * configuration)
   */
  public static final String HOSTS = "hosts";

  /**
   * Identifier for the list of files, which shall be collected by the FEP tooling for a host (key
   * <code>files_to_collect</code>)
   */
  public static final String HOST_FILES_TO_COLLECT = "host-files-to-collect";

  /**
   * Identifier for the path to executable or script that shall be executed to launch the FEP
   * Element instance of a host (key <code>launch_path</code>)
   */
  public static final String ELEMENT_LAUNCH_PATH = "element-launch-path";

  /**
   * Identifier for the FEP Element instance of a host (key <code>launch_arguments</code>)
   */
  public static final String ELEMENT_LAUNCH_ARGUMENTS = "element-launch-arguments";

  /**
   * Identifier for the order of the FEP Element instance of a host in which it will be initialized
   * (key <code>init_priority</code>)
   */
  public static final String ELEMENT_INIT_PRIORITY = "element-init-priority";

  /**
   * Identifier for the order of the FEP Element instance of a host in which it will be started (key
   * <code>start_priority</code>)
   */
  public static final String ELEMENT_START_PRIORITY = "element-start-priority";

  /**
   * Identifier whether a FEP Element instance of a host is deployed externally (key
   * <code>external_deploy</code>)
   */
  public static final String ELEMENT_EXTERNAL_DEPLOY = "element-external-deploy";

  /**
   * Identifier whether a FEP Element instance of a host is launched externally (key
   * <code>external_launch</code>)
   */
  public static final String ELEMENT_EXTERNAL_LAUNCH = "element-external-launch";

  /**
   * Identifier whether a FEP Agent is running on a host (key <code>fep_agent_running</code>)
   */
  public static final String HOST_FEP_AGENT_RUNNING = "host-fep-agent-running";

  /**
   * Identifier for the map of environmental variables (key <code>environment_variables</code> in
   * FEP deploy and launch configuration, host, FEP Element instance of a host, post deploy
   * configuration, or post deploy dependency)
   */
  public static final String ENVIRONMENT_VARIABLES = "environment_variables";

  /**
   * Identifier for the properties of FEP Components (key <code>components</code> in FEP deploy and
   * launch configuration)
   */
  public static final String COMPONENTS = "components";

  /**
   * Identifier for the list of IIDs of a FEP Component (key <code>iid</code>)
   */
  public static final String COMPONENT_IID = "component-iid";

  /**
   * Identifier for the path to the library containing the FEP Component (key <code>path</code>)
   */
  public static final String COMPONENT_PATH = "component-path";

  /**
   * Identifier for the type of the FEP Component to be loaded (key <code>type</code>)
   */
  public static final String COMPONENT_TYPE = "component-type";

  /**
   * Identifier for the properties of FEP Post Deploy Script (key <code>post_deploy</code> in FEP
   * deploy and launch configuration)
   */
  public static final String POST_DEPLOY = "post-deploy";

  /**
   * Identifier for the path to the executable that shall be executed as Post Deploy Script (key
   * <code>post_deploy_path</code>)
   */
  public static final String POST_DEPLOY_PATH = "post-deploy-path";

  /**
   * Identifier for the maximal amount of seconds needed by the Post Deploy Script to be executed
   * (key <code>timeout_s</code>)
   */
  public static final String POST_DEPLOY_TIMEOUT = "post-deploy-timeout";

  /**
   * Identifier for the properties of dependencies of the Post Deploy Script (key
   * <code>dependencies</code> )
   */
  public static final String POST_DEPLOY_DEPENDENCIES = "post-deploy-dependencies";

  /**
   * Identifier for the source type of a dependency of the Post Deploy Script (key
   * <code>source_type</code> )
   */
  public static final String DEPENDENCY_SOURCE_TYPE = "dependency-source-type";

  /**
   * Identifier for the global properties of the FEP runtime configuration (key <code>global</code>)
   */
  public static final String CONFIGURE_PROPERTIES_GLOBAL = "configure-properties-global";

  /**
   * Identifier for the host properties of the FEP runtime configuration (key <code>hosts</code>)
   */
  public static final String CONFIGURE_PROPERTIES_HOSTS = "configure-properties-hosts";

  /**
   * Identifier for the FEP element properties of the FEP runtime configuration (key
   * <code>element_instances</code>)
   */
  public static final String CONFIGURE_PROPERTIES_ELEMENTS = "configure-properties-elements";

  /**
   * Identifier for the properties of the FEP runtime configuration which should be set in state
   * unloaded (key <code>state_unloaded</code>)
   */
  public static final String PROPERTY_UNLOADED = "property-unloaded";

  /**
   * Identifier for the properties of the FEP runtime configuration which should be set in state
   * loaded (key <code>state_loaded</code>)
   */
  public static final String PROPERTY_LOADED = "property-loaded";

  /**
   * Identifier for unknown entries in the read Yaml file (the FEP system description)
   */
  public static final String UNKNOWN_ENTRIES = "unknown-entries";

  /**
   * Identifier for optional arguments of the call to FEP Access deploy
   */
  public static final String DEPLOY_ARGUMENTS = "deploy-args";

  /**
   * Identifier for optional arguments of the call to FEP Access launch
   */
  public static final String LAUNCH_ARGUMENTS = "launch-args";

  /**
   * Identifier for optional arguments of the call to FEP Access configure
   */
  public static final String CONFIGURE_ARGUMENTS = "configure-args";

  /**
   * Identifier for optional arguments of the call to FEP Access set_properties
   */
  public static final String SET_PROPERTIES_ARGUMENTS = "set-props-args";

  /**
   * Identifier for optional arguments of the call to FEP Access terminate
   */
  public static final String TERMINATE_ARGUMENTS = "terminate-args";

}
