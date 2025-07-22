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
package com.piketec.tpt.api.constants.assessments;

/**
 * For further information, please refer to the User Guide, Import Measurements Assesslet.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface ImportMeasurements extends BasicAssessment {

  public static final String FILE_NAME = "file";

  public static final String MAPPING_NAME = "mapping";

  public static final String MAPPING_FROM_PLATFORM = "mapping-from-platform";

  public static final String IMPORT_MODE = "import-mode";

  public static final String SELECTED_IMPORTS = "selected-imports";

  public static final String EXPORT = "export";

  public static final String IGNORE_SUFFIX = "ignore-suffix";

  public static final String CREATE_PARAMETERS = "create-parameters";

  public static final String SPLITUP_GAPS = "split-up-gaps";

  public static final String DEJITTER = "dejitter";

  public static final String TIME_OFFSET_MODE = "time-offset-mode";

  public static final String CORRELATION_CHANNEL = "correlation-channel";

  public static final String TIME_OFFSET = "time-offset-value";

  public static final String GLUE = "glue";

  public static final String FILL_TO_FIT_VALUE = "fill-value";

  public static final String FILL_TO_FIT = "fill-to-fit";

  public static final String CROP_TO_FIT = "crop";

  public static final String CORRELATION_MEASSUREMENT = "correlation-meassurement";

  public static final String SPLITUP_GAPS_THRESHOLD = "gap-threshold";

}
