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
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public abstract class TestCasesExportSettings implements Serializable {

  static final long serialVersionUID = 1L;

}
