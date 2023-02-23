package com.piketec.tpt.api.constants.platforms;

import com.piketec.tpt.api.cplatform.CCodePlatformConfiguration;

public interface ExeBased extends BasicPlatformConfig, EclipseCdtDebuggingConfig {

  public static final String ROUNDSCALINGRESULTS = "roundscalingresults";

  public static final String SINGLETHREADED = "singlethreaded";

  public static final String CALL_TPT_VM_API_BIND_SIGNAL_FINALIZE =
      "callTptVmapiBindSignalFinalize";

  /**
   * Not supported for {@link CCodePlatformConfiguration}
   */
  public static final String COVERAGE_FILES = "coverageFiles";

  /**
   * Not supported for {@link CCodePlatformConfiguration}
   */
  public static final String CUMCOV = "cumcov";

  public static final String COVERAGETOOL = "coveragetool";
}
