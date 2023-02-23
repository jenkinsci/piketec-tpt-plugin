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
package com.piketec.tpt.api;

import java.rmi.RemoteException;

import com.piketec.tpt.api.properties.Property;
import com.piketec.tpt.api.properties.PropertyMap;
import com.piketec.tpt.api.tasmo.TasmoTestDataGenerationController;

/**
 * This object represents a configuration for a specific platform adapter.
 * <p>
 * The particular properties of the various platforms are mapped to a generic {@link PropertyMap}.
 * </p>
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface PlatformConfiguration extends NamedObject, PlatformOrExecutionItemEnabler {

  /**
   * Type String for ADTF Platform
   */
  public static final String ADTF_PLATFORM_TYPE = "adtf";

  /**
   * Type String for Arduino Platform
   */
  public static final String ARDUINO_FUSION_PLATFORM_TYPE = "arduino";

  /**
   * Type String for ASCET Platform
   */
  public static final String ASCET_PLATFORM_TYPE = "ascet";

  /**
   * Type String for ASCET@FUSION Platform
   */
  public static final String ASCET_AT_FUSION_PLATFORM_TYPE = "ascet2";

  /**
   * Type String for Assessment Platform
   */
  public static final String ASSESSMENT_PLATFORM_TYPE = "assessment";

  /**
   * Type String for AUTOSAR Platform
   */
  public static final String AUTOSAR_PLATFORM_TYPE = "autosar";

  /**
   * Type String for AUTOSAR FUSION Platform
   */
  public static final String AUTOSAR_FUSION_PLATFORM_TYPE = "autosarfusion";

  /**
   * Type String for BOSCH FUSION Platform
   */
  public static final String BOSCH_FUSION_PLATFORM_TYPE = "bosch-fusion";

  /**
   * Type String for C Platform
   */
  public static final String C_PLATFORM_TYPE = "ccode";

  /**
   * Type String for CANape Platform
   */
  public static final String CANAPE_FUSION_PLATFORM_TYPE = "canape";

  /**
   * Type String for CANoe Platform
   */
  public static final String CANOE_PLATFORM_TYPE = "canoe";

  /**
   * Type String for CANoe@FUSION Platform
   */
  public static final String CANOE_AT_FUSION_PLATFORM_TYPE = "canoe@fusion";

  /**
   * Type String for CarMaker Platform
   */
  public static final String CARMAKER_FMU_FUSION_PLATFORM_TYPE = "carmakerfmu";

  /**
   * Type String for Concurrent HiL Platform
   */
  public static final String CONCURRENT_HIL_PLATFORM_TYPE = "concurrent-hil";

  /**
   * Type String for CTB Platform
   */
  public static final String CTB_PLATFORM_TYPE = "daimler-ctb";

  /**
   * Type String for DiagRA D Platform
   */
  public static final String DIAGRAD_FUSION_PLATFORM_TYPE = "diagrad";

  /**
   * Type String for dSPACE HiL@FUSION Platform
   */
  public static final String DSPACE_HIL_AT_FUSION_PLATFORM_TYPE = "dspaceXil";

  /**
   * Type String for dSPACE HiL Platform
   */
  public static final String DSPACE_HIL_PLATFORM_TYPE = "dspace-hil";

  /**
   * Type String for dSPACE PiL Platform
   */
  public static final String DSPACE_PIL_PLATFORM_TYPE = "dspace-pil";

  /**
   * Type String for EXE Platform
   */
  public static final String EXE_PLATFORM_TYPE = "exe";

  /**
   * Type String for FEP Platform
   */
  public static final String FEP_PLATFORM_TYPE = "fep";

  /**
   * Type String for FMI Platform
   */
  public static final String FMI_FUSION_PLATFORM_TYPE = "fmi";

  /**
   * Type String for FUSION Platform
   */
  public static final String FUSION_PLATFORM_TYPE = "fusion";

  /**
   * Type String for INCA Platform
   */
  public static final String GDB_FUSION_PLATFORM_TYPE = "gdb";

  /**
   * Type String for INCA Platform
   */
  public static final String INCA_FUSION_PLATFORM_TYPE = "inca";

  /**
   * Type String for LABCAR Platform
   */
  public static final String LABCAR_PLATFORM_TYPE = "labcar";

  /**
   * Type String for Lauterbach Platform
   */
  public static final String LAUTERBACH_FUSION_PLATFORM_TYPE = "lauterbach";

  /**
   * Type String for MATLAB/Simulink Platform
   */
  public static final String MATLAB_SIMULINK_PLATFORM_TYPE = "matlab";

  /**
   * Type String for Peak CAN Platform
   */
  public static final String PEAK_CAN_FUSION_PLATFORM_TYPE = "peakcan";

  /**
   * Type String for PLS UDE Platform
   */
  public static final String PLS_UDE_FUSION_PLATFORM_TYPE = "plsude";

  /**
   * Type String for RADARCAN Platform
   */
  public static final String RADARCAN_PLATFORM_TYPE = "radarcan";

  /**
   * Type String for RealtimeMaker Platform
   */
  public static final String REALTIMEMAKER_PLATFORM_TYPE = "realtimemaker";

  /**
   * Type String for RT-LAB Platform
   */
  public static final String RTLAB_FUSION_PLATFORM_TYPE = "rtlab";

  /**
   * Type String for Silver Platform
   */
  public static final String SILVER_PLATFORM_TYPE = "silver";

  /**
   * Type String for Simulink Real-Time Platform
   */
  public static final String SIMULINK_REAL_TIME_PLATFORM_TYPE = "matlab-rt";

  /**
   * Type String for SLRT@FUSION Platform
   */
  public static final String SLRT_AT_FUSION_PLATFORM_TYPE = "slrtxil@fusion";

  /**
   * Type String for Stand-alone Platform
   */
  public static final String STAND_ALONE_PLATFORM_TYPE = "stand-alone";

  /**
   * Type String for Vector CAN Platform
   */
  public static final String VECTOR_CAN_FUSION_PLATFORM_TYPE = "vectorcan";

  /**
   * Type String for VeriStand@FUSION Platform
   */
  public static final String VERISTAND_AT_FUSION_PLATFORM_TYPE = "veristand@fusion";

  /**
   * Type String for VeriStand Platform
   */
  public static final String VERISTAND_PLATFORM_TYPE = "veristand";

  /**
   * Type String for VTD Platform
   */
  public static final String VTD_FUSION_PLATFORM_TYPE = "vtd";

  /**
   * Type String for VW / Audi Platform
   */
  public static final String VW_AUDI_PLATFORM_TYPE = "vw-audi";

  /**
   * Type String for ASAM XIL@FUSION Platform
   */
  public static final String XIL_AT_FUSION_PLATFORM_TYPE = "asamXil";

  /**
   * @return The type name of the platform
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see Project#createPlatformConfiguration(String, String)
   */
  public String getType() throws RemoteException;

  /**
   * @return Returns the platform timeout in microseconds.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public long getTimeOut() throws RemoteException;

  /**
   * Set the platform timeout.
   * 
   * @param timeOut
   *          Timeout in microseconds.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTimeOut(long timeOut) throws RemoteException;

  /**
   * @return Returns the step size of the platform in microseconds.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public long getStepSize() throws RemoteException;

  /**
   * Set the step size for the platform.
   * 
   * @param stepSize
   *          Step size in microseconds.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setStepSize(long stepSize) throws RemoteException;

  /**
   * @return Returns the size of the ring buffer (history) that is used to enable access to signal
   *         values of preceding steps of a test case.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getHistorySize() throws RemoteException;

  /**
   * Set the {@link Mapping} for this platform configuration.
   * 
   * @param mapping
   *          The new {@link Mapping}. May be null.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setMapping(Mapping mapping) throws RemoteException;

  /**
   * @return Returns the {@link Mapping} for this platform configuration or <code>null</code> if no
   *         mapping is configured.
   * @throws RemoteException
   *           remote communication problem
   */
  public Mapping getMapping() throws RemoteException;

  /**
   * Set the size of the ring buffer (history) that is used to enable access to signal values of
   * preceding steps of a test case.
   * 
   * @param historySize
   *          The new size of the history in steps.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setHistorySize(int historySize) throws RemoteException;

  /**
   * Returns the properties of the platform adapter as {@link PropertyMap}.
   * <p>
   * A PropertyMap maps the properties as follows: {@link String} -&gt; {@link Property}. A
   * <code>Property</code> is either a <code>ProperyMap</code> or a <code>String</code> value.
   * </p>
   * The structure of the PropertyMap depends on the type of the platform adapter.
   * 
   * @return A {@link com.piketec.tpt.api.properties.PropertyMap PropertyMap} with the settings for
   *         the platform adapter.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public PropertyMap getProperties() throws RemoteException;

  /**
   * Set the configuration for the platform adapter via {@link PropertyMap}.
   * <p>
   * Since an incomplete <code>PropertyMap</code> could lead to unpredictable behavior, it is
   * generally recommended to modify the <code>PropertyMap</code> returned by
   * {@link #getProperties()}.
   * </p>
   * 
   * @param properties
   *          A PropertyMap for the respective platform adapter.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setProperties(PropertyMap properties) throws RemoteException;

  /**
   * Run a function of this particular platform adapter (e.g., import-interface, ...). The available
   * functions depend on the actual platform represented by this object. Some platforms do not have
   * functions.
   * <p>
   * The function is identified by its name. The {@link PropertyMap} can be used to provide
   * additional parameters to the function.
   * </p>
   * If the function name is unknown or if the <code>PropertyMap</code> does not match the expected
   * structure, an {@link ApiException} is invoked. Often exception message contains a hint which
   * functions are available.
   * 
   * @param functionName
   *          Name of the function to be invoked
   * @param parameterOrNull
   *          A <code>PropertyMap</code> representing the function arguments or <code>null</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the function is not available or the PropertyMap is invalid for the invoked
   *           function.
   */
  public void invoke(String functionName, PropertyMap parameterOrNull)
      throws ApiException, RemoteException;

  /**
   * Initialize the TASMO test case generation with this Platform.
   * 
   * @param mapping
   *          The mapping containing the TASMO input specification to be used.
   * @return The interface that provides access to TASMO test data generation.
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If TASMO is not licensed, the plugin needed for this platform is not loaded or this
   *           platform is not supported by TASMO.
   */
  public TasmoTestDataGenerationController initTasmoTestDataGeneration(Mapping mapping)
      throws ApiException, RemoteException;

  /**
   * Copies <code>this</code> into the given <code>target</code> that can be a different
   * {@link Project} that is opened in the same TPT instance. If the <code>target</code> already
   * contains an element with the same name a new one will be generated.
   * 
   * @param target
   *          The project to copy <code>this</code> into. Can be another <code>Project</code> than
   *          the one <code>this</code> belongs to.
   * @param targetIndex
   *          The index where the copy will be inserted. Use {@link Integer#MAX_VALUE} to append the
   *          copy at the end.
   * @return The copy of this and all log messages that occured during copying.
   * @throws ApiException
   *           If target is <code>null</code> or copying failed.
   * @throws RemoteException
   *           remote communication problem
   */
  public ResultAndLogs<PlatformConfiguration> copy(Project target, int targetIndex)
      throws ApiException, RemoteException;

}
