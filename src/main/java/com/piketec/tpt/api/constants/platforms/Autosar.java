/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2020 PikeTec GmbH
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
 * For further information, please refer to the User Guide, AUTOSAR Platform.
 */
public interface Autosar extends BasicPlatformConfig, EclipseCdtDebuggingConfig {

  public static final String PROJECT_DIR = "projectdir";

  public static final String OUT_DIR = "outdir";

  public static final String AR_XMLS = "arxmls";

  public static final String RUNNABLES_NAME = "runnables.name";

  public static final String RUNNABLES_INITIAL = "runnables.initial";

  public static final String RUNNABLES_AUTOMATIC_SCHEDULING = "runnables.automaticScheduling";

  public static final String RUNNABLES_PERIODS = "runnables.periods";

  public static final String ADDITIONALS = "additionals";

  public static final String GEN_RTE_CONTRACT_PHASE_HEADERS = "genRteContractPhaseHeaders";

  public static final String CDS_IS_DEFINED_AS_STRUCT_TYPE_DEF = "cdsIsDefinedAsStructTypedef";

  public static final String CDS_INST_IS_CONST_POINTER = "cdsInstIsConstPointer";

  public static final String CDS_SUPPRESS_CAL_PRM = "cdsSuppressCalPrm";

  public static final String CDS_IGNORED = "cdsIgnored";

  public static final String SWC_NAME = "swcname";

  public static final String SYSTEM_CONSTANT_VALUE_SETS = "systemConstantValueSets";

  public static final String C_COMPILER_NAME = "cCompilerName";

  public static final String X64BIT = "64bit";

  public static final String EXTRACOMPILEROPTS = "extracompileropts";

  public static final String DO_FAULT_INJECTION = "doFaultInjection";

  public static final String FAULT_INJ_SWCS_TO = "faultInjSwcsTo";

  public static final String FAULT_INJ_PORTS_TO = "faultInjPortsTo";

  public static final String ADDITIONAL_INCLUDES = "additionalIncludes";

  public static final String WRITE_COUNTER_SIGNALS = "writeCounterSignals";

  public static final String REMOVE_DEFAULT_PORT_PREFIXES = "removeDefaultPortPrefixes";

}