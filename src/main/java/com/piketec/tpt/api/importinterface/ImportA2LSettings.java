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
package com.piketec.tpt.api.importinterface;

import java.io.File;
import java.util.Collection;

import com.piketec.tpt.api.Parameter.ExchangeMode;
import com.piketec.tpt.api.Project;

/**
 * {@link ImportInterfaceSettings} to import from an A2L file (including dcm, hex and txt file
 * import).
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public class ImportA2LSettings extends ImportInterfaceSettings {

  private static final long serialVersionUID = 1L;

  private File a2lFile;

  private File interfaceFileOrNull = null;

  private boolean importParametersOnly = false;

  private boolean arrayDimensionsFromA2L = false;

  private Collection<String> functionsForMeasurementImportOrNull = null;

  private File hexFileOrNull = null;

  private String deviceNameOrNull = null;

  private File dcmFileOrNull = null;

  private String defaultRasterNameOrNull = null;

  private boolean splitArraySignalsIntoScalars = false;

  private boolean splitArrayStartIndexIsOne = false;

  private ExchangeMode parameterExchangeMode = ExchangeMode.EXCHANGE;

  /**
   * Creates a new instance to be passed to {@link Project#importIO(ImportInterfaceSettings)}.
   * 
   * @param a2lFile
   *          The A2L file to be imported. Can be <code>null</code> but has to be set before
   *          invoking the import.
   */
  public ImportA2LSettings(File a2lFile) {
    this.setA2lFile(a2lFile);
  }

  /**
   * Gets the A2L file to import.
   * 
   * @return the a2lFile
   */
  public File getA2lFile() {
    return a2lFile;
  }

  /**
   * Gets the absolute path to the A2L file to import.
   * 
   * @return the a2lFile path
   */
  public String getA2lFilePath() {
    if (a2lFile == null) {
      return null;
    }
    return a2lFile.getAbsolutePath();
  }

  /**
   * Sets the A2L file to import.
   * 
   * @param a2lFile
   *          the a2lFile to set
   */
  public void setA2lFile(File a2lFile) {
    this.a2lFile = a2lFile;
  }

  /**
   * Sets the A2L file to import.
   * 
   * @param a2lFile
   *          the path to the file to set
   */
  public void setA2lFileByPath(String a2lFile) {
    if (a2lFile == null) {
      this.a2lFile = null;
    } else {
      this.a2lFile = new File(a2lFile);
    }
  }

  /**
   * Gets the text file containing interface information to reduce the imported declarations or
   * <code>null</code>. If an interface file is given, only channels, parameters, and constants
   * listed there will be imported.
   * 
   * @return the interface file or <code>null</code>
   */
  public File getInterfaceFileOrNull() {
    return interfaceFileOrNull;
  }

  /**
   * Gets the absolute path to the text file containing interface information to reduce the imported
   * declarations or <code>null</code>. If an interface file is given, only channels, parameters,
   * and constants listed there will be imported.
   * 
   * @return the path to the interface file or <code>null</code>
   */
  public String getInterfaceFilePathOrNull() {
    if (interfaceFileOrNull == null) {
      return null;
    }
    return interfaceFileOrNull.getAbsolutePath();
  }

  /**
   * Sets the text file containing interface information to reduce the imported declarations or
   * <code>null</code>. If an interface file is given, only channels, parameters, and constants
   * listed there will be imported.
   * 
   * @param interfaceFileOrNull
   *          the interface file or <code>null</code> to set
   */
  public void setInterfaceFileOrNull(File interfaceFileOrNull) {
    this.interfaceFileOrNull = interfaceFileOrNull;
  }

  /**
   * Sets the text file containing interface information to reduce the imported declarations or
   * <code>null</code>. If an interface file is given, only channels, parameters, and constants
   * listed there will be imported.
   * 
   * @param interfaceFileOrNull
   *          the path to the interface file or <code>null</code> to set
   */
  public void setInterfaceFilePathOrNull(String interfaceFileOrNull) {
    if (interfaceFileOrNull == null) {
      this.interfaceFileOrNull = null;
    } else {
      this.interfaceFileOrNull = new File(interfaceFileOrNull);
    }

  }

  /**
   * Returns wheter or not the import is restricted to entries from the parameter file.
   * 
   * @return <code>true</code> if only entries from the parameter file shall be imported.
   */
  public boolean isImportParametersOnly() {
    return importParametersOnly;
  }

  /**
   * Sets whether or not to restrict the import to entries from the parameter file.
   * 
   * @param importParametersOnly
   *          <code>true</code> if only entries from the parameter file shall be imported.
   */
  public void setImportParametersOnly(boolean importParametersOnly) {
    this.importParametersOnly = importParametersOnly;
  }

  /**
   * Returns <code>true</code> if array dimensions are read from A2L instead of given HEX or DCM
   * file.
   * 
   * @return <code>true</code> if array dimensions are read from A2L instead of given HEX or DCM
   *         file
   */
  public boolean isArrayDimensionsFromA2L() {
    return arrayDimensionsFromA2L;
  }

  /**
   * Sets whether or not to read array dimensions from A2L instead of given HEX or DCM file.
   * 
   * @param arrayDimensionsFromA2L
   *          <code>true</code> if array dimensions shall be read from A2L file
   */
  public void setArrayDimensionsFromA2L(boolean arrayDimensionsFromA2L) {
    this.arrayDimensionsFromA2L = arrayDimensionsFromA2L;
  }

  /**
   * Gets a collection of function names. If the collection is <code>null</code> all measurements
   * will be imported. If not, only measurements that are matching the name of an argument of one of
   * the specified functions will be imported.
   * 
   * @return the collection of function names or <code>null</code>
   */
  public Collection<String> getFunctionsForMeasurementImportOrNull() {
    return functionsForMeasurementImportOrNull;
  }

  /**
   * Sets a collection of function names. If the collection is <code>null</code> all measurements
   * will be imported. If not, only measurements that are matching the name of an argument of one of
   * the specified functions will be imported.
   * 
   * @param functionsOrNull
   *          the collection of function names or <code>null</code>
   */
  public void setFunctionsForMeasurementImportOrNull(Collection<String> functionsOrNull) {
    this.functionsForMeasurementImportOrNull = functionsOrNull;
  }

  /**
   * Gets the Intel HEX file to import. You can either import a hex file or a dcm file. If both are
   * set only the hex file will be taken into account.
   * 
   * @return the hex file to import
   */
  public File getHexFileOrNull() {
    return hexFileOrNull;
  }

  /**
   * Gets the absolute path to the Intel HEX file to import. You can either import a hex file or a
   * dcm file. If both are set only the hex file will be taken into account.
   * 
   * @return the path to the hex file to import
   */
  public String getHexFilePathOrNull() {
    if (hexFileOrNull == null) {
      return null;
    }
    return hexFileOrNull.getAbsolutePath();
  }

  /**
   * Sets the Intel HEX file to import. You can either import a hex file or a dcm file. If both are
   * set only the hex file will be taken into account.
   * 
   * @param hexFileOrNull
   *          the Intel HEX file to import or <code>null</code>.
   */
  public void setHexFileOrNull(File hexFileOrNull) {
    this.hexFileOrNull = hexFileOrNull;
  }

  /**
   * Sets the Intel HEX file to import. You can either import a hex file or a dcm file. If both are
   * set only the hex file will be taken into account.
   * 
   * @param hexFileOrNull
   *          the path to the Intel HEX file to import or <code>null</code>.
   */
  public void setHexFileOrNullByPath(String hexFileOrNull) {
    if (hexFileOrNull == null) {
      this.hexFileOrNull = null;
    } else {
      this.hexFileOrNull = new File(hexFileOrNull);
    }

  }

  /**
   * Gets the device name for the Intel HEX file import. Can be <code>null</code> to import a
   * generic device.
   * 
   * @return the device name or <code>null</code>.
   */
  public String getDeviceNameOrNull() {
    return deviceNameOrNull;
  }

  /**
   * Sets the device name for the Intel HEX file import. Can be <code>null</code> to import a
   * generic device.
   * 
   * @param deviceNameOrNull
   *          the device name or <code>null</code>
   */
  public void setDeviceName(String deviceNameOrNull) {
    this.deviceNameOrNull = deviceNameOrNull;
  }

  /**
   * Gets the DCM file to import. You can either import a hex file or a dcm file. If both are set
   * only the hex file will be taken into account.
   * 
   * @return the DCM file or <code>null</code>
   */
  public File getDcmFileOrNull() {
    return dcmFileOrNull;
  }

  /**
   * Gets the absolute path to the DCM file to import. You can either import a hex file or a dcm
   * file. If both are set only the hex file will be taken into account.
   * 
   * @return the path to the DCM file or <code>null</code>
   */
  public String getDcmFilePathOrNull() {
    if (dcmFileOrNull == null) {
      return null;
    }
    return dcmFileOrNull.getAbsolutePath();
  }

  /**
   * Sets the DCM file to import. You can either import a hex file or a dcm file. If both are set
   * only the hex file will be taken into account.
   * 
   * @param dcmFileOrNull
   *          the DCM file or <code>null</code> to set
   */
  public void setDcmFileOrNull(File dcmFileOrNull) {
    this.dcmFileOrNull = dcmFileOrNull;
  }

  /**
   * Sets the DCM file to import. You can either import a hex file or a dcm file. If both are set
   * only the hex file will be taken into account.
   * 
   * @param dcmFileOrNull
   *          the path to the DCM file or <code>null</code> to set
   */
  public void setDcmFileOrNullByPath(String dcmFileOrNull) {
    if (dcmFileOrNull == null) {
      this.dcmFileOrNull = null;
    } else {
      this.dcmFileOrNull = new File(dcmFileOrNull);
    }
  }

  /**
   * Gets the raster name that will be entered into the measurement mapping flavor column
   * raster_name for channels or measurements. Can be <code>null</code> or empty to use no default
   * raster name.
   * 
   * @return the default raster name
   */
  public String getDefaultRasterNameOrNull() {
    return defaultRasterNameOrNull;
  }

  /**
   * Sets the raster name that will be entered into the measurement mapping flavor column
   * raster_name for channels or measurements. Can be <code>null</code> or empty to use no default
   * raster name.
   * 
   * @param defaultRasterNameOrNull
   *          the default raster name or <code>null</code>
   */
  public void setDefaultRasterNameOrNull(String defaultRasterNameOrNull) {
    this.defaultRasterNameOrNull = defaultRasterNameOrNull;
  }

  /**
   * Returns whether or not arrays will be split into multiple scalar signals.
   * 
   * @return <code>true</code> if array signals will be split into multiple scalar signals.
   */
  public boolean isSplitArraySignalsIntoScalars() {
    return splitArraySignalsIntoScalars;
  }

  /**
   * Sets whether or not array signals will be split into multiple scalar signals.
   * 
   * @param splitArraySignals
   *          <code>true</code> if array signals shall be split into multiple scalar signals.
   */
  public void setSplitArraySignalsIntoScalars(boolean splitArraySignals) {
    this.splitArraySignalsIntoScalars = splitArraySignals;
  }

  /**
   * Returns whether or not the suffix of the scalar signals resulting by splitting an array starts
   * with "_000" or "_001".
   * 
   * @return <code>true</code> if the suffix starts counting with "1", <code>false</code> if the
   *         suffix starts counting with "0"
   * @see ImportA2LSettings#setSplitArraySignalsIntoScalars(boolean)
   */
  public boolean isSplitArrayStartIndexIsOne() {
    return splitArrayStartIndexIsOne;
  }

  /**
   * Sets whether or not the suffix of the scalar signals resulting by splitting an array starts
   * with "_000" or "_001".
   * 
   * @param splitArrayStartIndexIsOne
   *          <code>true</code> if the suffix starts counting with "1", <code>false</code> if the
   *          suffix starts counting with "0"
   * @see ImportA2LSettings#setSplitArraySignalsIntoScalars(boolean)
   */
  public void setSplitArrayStartIndexIsOne(boolean splitArrayStartIndexIsOne) {
    this.splitArrayStartIndexIsOne = splitArrayStartIndexIsOne;
  }

  /**
   * Gets the exchange mode that will be set for imported parameters.
   * 
   * @return the parameter exchange mode
   */
  public ExchangeMode getParameterExchangeMode() {
    return parameterExchangeMode;
  }

  /**
   * Sets the exchange mode that will be set for imported parameters.
   * 
   * @param parameterExchangeMode
   *          the new parameter exchange mode
   */
  public void setParameterExchangeMode(ExchangeMode parameterExchangeMode) {
    this.parameterExchangeMode = parameterExchangeMode;
  }
}
