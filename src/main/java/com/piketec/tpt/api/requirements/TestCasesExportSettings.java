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
package com.piketec.tpt.api.requirements;

import java.io.Serializable;

import com.piketec.tpt.api.requirements.codebeamer.CodeBeamerTestCasesExportSettings;
import com.piketec.tpt.api.requirements.csv.CsvFileTestCasesExportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileTestCasesExportSettings;
import com.piketec.tpt.api.requirements.polarion.PolarionTestCasesExportSettings;
import com.piketec.tpt.api.requirements.reqif.ReqIfTestCasesExportSettings;

/**
 * The common class for the settings of the requirement export of test cases.<br>
 * For the test cases export to a CSV file use {@link CsvFileTestCasesExportSettings}.<br>
 * For the test cases export to an Excel file use {@link ExcelFileTestCasesExportSettings}.<br>
 * For the test cases export to Polarion use {@link PolarionTestCasesExportSettings}.<br>
 * For the test cases export to codeBeamer use {@link CodeBeamerTestCasesExportSettings}.<br>
 * For the test cases export to refIF use {@link ReqIfTestCasesExportSettings}.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public abstract class TestCasesExportSettings implements Serializable {

  static final long serialVersionUID = 1L;

}
