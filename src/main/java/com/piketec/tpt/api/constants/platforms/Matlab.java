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
 * For further information, please refer to the User Guide, MATLAB Platform.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface Matlab extends BasicPlatformConfig {

  public static final String EXECIOFILEOVERWRITE = "execIOFileOverwrite";

  public static final String EXECTESTFRAMEREGEN = "execTestFrameRegen";

  public static final String EXECKEEPMATLABOPEN = "execKeepMatlabOpen";

  public static final String FASTRESTART = "fastRestart";

  public static final String SLSIMULATIONMODE = "slSimulationMode";

  public static final String ENFORCEFIRSTTESTCASESINGLETHREADED =
      "enforceFirstTestCaseSingleThreaded";

  public static final String EXECMODELLOADSCRIPT = "execModelLoadScript";

  public static final String CODECOVERAGECUSTOMSCRIPT = "codeCoverageCustomScript";

  public static final String EXECFINALIZESCRIPT = "execFinalizeScript";

  public static final String EXECRESTARTMATLABEVERY = "execRestartMatlabEvery";

  public static final String EXECRUNTESTSCRIPT = "execRunTestScript";

  public static final String EXECTESTMODEL = "execTestmodel";

  public static final String GENERATETESTFRAME = "generateTestframe";

  public static final String TESTFRAMEPOSTPROCESSSCRIPT = "testFramePostProcessScript";

  public static final String MATLABLOGFILE = "matlabLogfile";

  public static final String MATLABSTARTUPDIR = "matlabStartupDir";

  public static final String MATLABSTARTUPSCRIPT = "matlabStartupScript";

  public static final String MATLABVERSIONNAME = "matlabVersionName";

  public static final String ORIGINALBLOCKPATH = "originalBlockPath";

  public static final String ORIGINALMODELFILE = "originalModelFile";

  public static final String ORIGINALMODELLOADSCRIPT = "originalModelLoadScript";

  public static final String PARAMETERIMPORTMODE = "parameterImportMode";

  public static final String PARAMETERIMPORTANDREADSCRIPTCALL = "parameterImportAndReadScriptCall";

  public static final String PARAMETERWRITESCRIPTCALL = "parameterWriteScriptCall";

  public static final String LASTPARAMETERFILE = "lastParameterFile";

  public static final String PARAMETERLOADFCN = "parameterLoadFcn";

  public static final String PARAMETERLOADMODE = "parameterLoadMode";

  public static final String PARAMETERWRITEFCN = "parameterWriteFcn";

  public static final String PARAMETERWRITEMODE = "parameterWriteMode";

  public static final String UPDATETARGETLINKCODEPARAMS = "updateTargetLinkCodeParams";

  public static final String TPT_MATLAB_USE_GENERATED_TESTFRAME_FILENAME =
      "tpt.matlab.testframefilename.usegenerated";

  public static final String TPT_MATLAB_DERIVE_FILENAME_FROM_ORIGINAL_MODEL =
      "tpt.matlab.geratetestframe.derivefrommodel";

  public static final String CHECKIO = "CHECKIO";

  public static final String USESUTTYPESTYPESNATIVELY = "useSutTypesTypesNatively";

  public static final String CONCATSIGNALNAMES = "concatSignalNames";

  public static final String PREFERPORTNAMES = "preferPortNames";

  public static final String IMPORTALLPARAMS = "IMPORTALLPARAMS";

  public static final String DUALSCALEDPARAMSUSEVALUE = "dualScaledParamsUseValue";

  public static final String IMPORTALLENUMS = "importAllEnums";

  public static final String SPLITDSCHANNELS = "splitDsChannels";

  public static final String STARTVECTORINDEXWT0 = "STARTVECTORINDEXWT0";

  public static final String CHECKALLOWSREDUCEDIO = "checkAllowsReducedIO";

  public static final String CHECKINTERNALSIGNALS = "checkInternalSignals";

  public static final String FIXINTERNALSIGNALSAUTOMATICALLY = "fixInternalSignalsAutomatically";

  public static final String INTERNALSIGNALSBREAKLINKS = "internalSignalsBreakLinks";

  public static final String LOGONLYUSEDINTERNALSIGNALS = "logOnlyUsedInternalSignals";

  public static final String DATASTOREHANDLINGMODE = "dataStoreHandlingMode";

  public static final String DATASTORECHANNELTYPE = "dataStoreChannelType";

  public static final String USESLFCNSTUBBING = "useSlFcnStubbing";

  public static final String TPT_MATLAB_DTC = "tpt.matlab.dtc";

  public static final String TPT_MATLAB_DTC_ROUNDINGMODE = "tpt.matlab.dtc.roundingmode";

  public static final String TPT_MATLAB_DTC_EXPLICITTYPES = "tpt.matlab.dtc.explicittypes";

  public static final String TPT_MATLAB_RATETRANS = "tpt.matlab.ratetrans";

  public static final String GENERATE_WITH_ADDPATH = "generate_with_addpath";

  public static final String PREPARE_TARGETLINK_SUBSYSTEM = "prepareTargetLinkSubsystem";

  public static final String VECTORTREAT = "nativeVectors";

  public static final String SFUNUSEDIRECTFEEDTHROUGH = "sFunUseDirectFeedthrough";

  public static final String MEASURE_SCALED_DATA = "measure_scaled_data";

  public static final String HANDLE_SIMULINK_OBJECTS = "handle_simulink_objects";

  public static final String HANDLE_SIGNALNAME_MISSMATCH = "handle_signalname_missmatch";

  public static final String CODECOVERAGE_ENABLED = "code_coverage_enabled";

  public static final String COVERAGE_EXCLUDED_BLOCKS = "coverage_excluded_blocks";

  public static final String CODECOVERAGE_ONLY_REPORTING = "code_coverage_only_reporting";

  public static final String CUMULATECOVERAGEDATA_ENABLED = "cumulate_coverage_data_enabled";

  public static final String CODECOVERAGETOOL = "code_coverage_tool";

  public static final String CUMULATIVE_COVERAGE_DATA_NAME = "cumulativeCoverageDataName";

  public static final String INTERFACEORDERSORTMANUAL = "interfaceOrderSortManual";

  public static final String INPORTORDER = "inportOrder";

  public static final String OUTPORTORDER = "outportOrder";

  public static final String INCHANNELSORDER = "inChannelsOrder";

  public static final String OUTCHANNELSORDER = "outChannelsOrder";

}
