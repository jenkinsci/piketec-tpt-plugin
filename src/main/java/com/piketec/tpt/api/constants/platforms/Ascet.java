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
 * For further information, please refer to the User Guide, section ASCET Platform.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface Ascet extends BasicPlatformConfig {

  public static final String ASCET_VERSION = "ascetversion";

  public static final String ASCET_DATABASE_PATH = "ascetdatabasepath";

  public static final String SUT_PROJECT_PATH = "sutprojectpath";

  public static final String SELECTED_SUT_MODULES = "selectedsutmodules";

  public static final String TEST_ENVIRONMENT_PATH = "testenvironmentpath";

  public static final String TEST_DRIVER_NAME = "testdrivername";

  public static final String TEST_FRAME_PROJECT_NAME = "testframeprojectname";

  public static final String TASK = "task";

  public static final String TASK_TYPE = "tasktype";

  public static final String STEP_SIZE = "stepsize";

  public static final String TARGET = "target";

  public static final String CODE_GENERATOR = "codegenerator";

  public static final String DEFAULT_NAMES_STATE = "defaultnamesstate";

  public static final String RECORD_INIT = "recordinit";

  public static final String DIM_CHECK = "dimcheck";

  public static final String LIMITING = "limiting";

  public static final String ROUND_SCALING = "roundscaling";

  public static final String INDEPENDENT_STEP_SIZE_STATE = "independentstepsizestate";

  public static final String ARRAY_CHANNELS_AS_NATIVES = "arraychannelsasnatives";

  public static final String CONCATENATE_CHANNEL_NAMES_ALWAYS = "concatenatechannelnamesalways";

  public static final String SUT_MODULE_NAMES = "sutmodulenames";

  public static final String TASKS = "tasks";

  public static final String TASK_TYPES = "tasktypes";

  public static final String STEP_SIZES = "stepsizes";

  public static final String TARGETS = "targets";

  public static final String CODE_GENERATORS = "codegenerators";

  public static final String KEEP_EXPERIMENT_OPEN = "keepexperimentopen";

  public static final String CLEAN_CGEN = "cleancgen";

  public static final String RESTART_EXPERIMENT = "restartexperiment";

  public static final String RESTART_EXPERIMENT_EVERY_X_TIMES = "restartexperimenteveryxtimes";

  public static final String TPT_PROCESS_POSITION_IN_TASK = "tptprocesspositionintask";

  public static final String SUT_MODULE_NAME = "sutmodulename";

  public static final String SYSTEM_CONSTANTS = "systemconstants";

  public static final String SYSTEM_CONSTANT_VALUES = "systemconstantValues";
}
