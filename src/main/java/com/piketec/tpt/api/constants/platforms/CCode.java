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

import com.piketec.tpt.api.cplatform.CCodePlatformConfiguration;

/**
 * For further information, please refer to the User Guide, C Code Platform.
 * 
 * @deprecated Will be removed in TPT-18. Use dedicated API {@link CCodePlatformConfiguration}
 *             instead.
 */
@Deprecated
public interface CCode extends BasicPlatformConfig, EclipseCdtDebuggingConfig {

  // ---- KEYS FOR PROPERTIES ----
  public final String CALL_TPT_VM_API_BIND_SIGNAL_FINALIZE = "callTptVmapiBindSignalFinalize";

  public final String CALL_TPT_VM_API_INIT_TEST_OUTPUTS = "callTptVmapiInitTestOutputs";

  public final String TPT_GENERATED_DIR = "tptGeneratedDir";

  public final String INSTRUMENTATIONLEVEL = "instrumentationlevel";

  // only for loading old files, now this option is included in the option "functions.kind"
  public final String FUNCTIONS_INIT = "functions.init";

  public final String FUNCTIONS_KIND = "functions.kind";

  public final String FUNCTIONS_PERIODS = "functions.periods";

  public final String FUNCTIONS_NAMES = "functions.names";

  public final String FUNCTIONS_ENABLED = "functions.enabled";

  public final String FUNCTIONS_IMPORTED = "functions.imported";

  public final String SRCFILES_DO_ANALYZE = "srcfiles.doAnalyze";

  public final String SRCFILES_EXTRAOPTS = "srcfiles.extraopts";

  public final String SRCFILES_PATH = "srcfiles.path";

  public final String SRCFILES_ADDITIONAL_HEADERFILES = "srcfiles.additionalHeaders";

  public final String LIBFILES = "libfiles";

  public final String INCLUDES = "includes";

  public final String DEFINES_VALUES = "defines.values";

  public final String DEFINES_VARNAMES = "defines.varnames";

  public final String X64BIT = "64bit";

  public final String IGNOREINCLUDES = "ignoreincludes";

  public final String EXTRACOMPILEROPTS = "extracompileropts";

  public final String EXTRALINKEROPTS = "extralinkeropts";

  public final String CUSTOMCOMPILEROPTS = "customLinkerOptions";

  public final String CUSTOMLINKEROPTS = "customLinkerOptions";

  public final String COMPILER = "compiler";

  public final String SINGLETHREADED = "singlethreaded";

  public final String WORKINGDIR = "workingdir";

  public final String INITINTERFACEVARIABLES = "initinterfacevariables";

  public final String ROUNDSCALINGRESULTS = "roundscalingresults";

  public final String USE_EFFECTIVE_INTERFACE = "useEffectiveInterface";

  public final String ENABLERW = "enablerw";

  public static final String COVERAGETOOL = "coveragetool";

  public static final String A2LFILE = "a2lfile";
}
