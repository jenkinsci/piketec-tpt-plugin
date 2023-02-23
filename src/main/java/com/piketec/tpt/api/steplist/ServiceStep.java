package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;
import java.util.List;

/**
 * This {@link Step} is a step which provides the possibility to execute a platform specific service
 * during test execution.
 *
 */
public abstract interface ServiceStep extends Step {

  /**
   * Type String for xil-diag-clear-dtcs-in-ecu
   */
  public static final String SVC_XIL_DIAG_CLEAR_DTCS_IN_ECU = "xil-diag-clear-dtcs-in-ecu";

  /**
   * Type String for xil-diag-clear-dtcs-in-fcn-grp
   */
  public static final String SVC_XIL_DIAG_CLEAR_DTCS_IN_FCN_GRP = "xil-diag-clear-dtcs-in-fcn-grp";

  /**
   * Type String for xil-diag-execute-diag-job-on-ecu
   */
  public static final String SVC_XIL_DIAG_EXECUTE_DIAG_JOB_ON_ECU =
      "xil-diag-execute-diag-job-on-ecu";

  /**
   * Type String for xil-diag-read-dtcs-in-ecu
   */
  public static final String SVC_XIL_DIAG_READ_DTCS_IN_ECU = "xil-diag-read-dtcs-in-ecu";

  /**
   * Type String for xil-diag-read-dtcs-in-fcn-grp
   */
  public static final String SVC_XIL_DIAG_READ_DTCS_IN_FCN_GRP = "xil-diag-read-dtcs-in-fcn-grp";

  /**
   * Type String for xil-diag-read-value-from-address
   */
  public static final String SVC_XIL_DIAG_READ_VALUE_FROM_ADDRESS =
      "xil-diag-read-value-from-address";

  /**
   * Type String for xil-diag-read-value-from-ecu
   */
  public static final String SVC_XIL_DIAG_READ_VALUE_FROM_ECU = "xil-diag-read-value-from-ecu";

  /**
   * Type String for xil-diag-read-values-from-fcn-grp
   */
  public static final String SVC_XIL_DIAG_READ_VALUES_FROM_FCN_GRP =
      "xil-diag-read-values-from-fcn-grp";

  /**
   * Type String for xil-diag-read-variant-coding-from-ecu
   */
  public static final String SVC_XIL_DIAG_READ_VARIANT_CODING_FROM_ECU =
      "xil-diag-read-variant-coding-from-ecu";

  /**
   * Type String for xil-diag-send-hex-service-to-ecu
   */
  public static final String SVC_XIL_DIAG_SEND_HEX_SERVICE_TO_ECU =
      "xil-diag-send-hex-service-to-ecu";

  /**
   * Type String for xil-diag-send-hex-service-to-fcn-grp
   */
  public static final String SVC_XIL_DIAG_SEND_HEX_SERVICE_TO_FCN_GRP =
      "xil-diag-send-hex-service-to-fcn-grp";

  /**
   * Type String for xil-diag-write-value-to-address
   */
  public static final String SVC_XIL_DIAG_WRITE_VALUE_TO_ADDRESS =
      "xil-diag-write-value-to-address";

  /**
   * Type String for xil-diag-write-value-to-ecu
   */
  public static final String SVC_XIL_DIAG_WRITE_VALUE_TO_ECU = "xil-diag-write-value-to-ecu";

  /**
   * Type String for xil-download-parameter-file
   */
  public static final String SVC_XIL_DOWNLOAD_PARAMETER_FILE = "xil-download-parameter-file";

  /**
   * Type String for xil-pause-simulation
   */
  public static final String SVC_XIL_PAUSE_SIMULATION = "xil-pause-simulation";

  /**
   * Type String for xil-play-stimulus-file
   */
  public static final String SVC_XIL_PLAY_STIMULUS_FILE = "xil-play-stimulus-file";

  /**
   * Type String for xil-send-value
   */
  public static final String SVC_XIL_SEND_VALUE = "xil-send-value";

  /**
   * Type String for xil-reset-all-electrical-errors
   */
  public static final String SVC_XIL_RESET_ALL_ELECTRICAL_ERRORS =
      "xil-reset-all-electrical-errors";

  /**
   * Type String for xil-reset-electrical-error
   */
  public static final String SVC_XIL_RESET_ELECTRICAL_ERROR = "xil-reset-electrical-error";

  /**
   * Type String for xil-set-electrical-error
   */
  public static final String SVC_XIL_SET_ELECTRICAL_ERROR = "xil-set-electrical-error";

  /**
   * Type String for xil-start-simulation
   */
  public static final String SVC_XIL_START_SIMULATION = "xil-start-simulation";

  /**
   * Type String for xil-stop-simulation
   */
  public static final String SVC_XIL_STOP_SIMULATION = "xil-stop-simulation";

  /**
   * Type String for can-end-xcp-communication
   */
  public static final String SVC_CAN_END_XCP_COMMUNICATION = "can-end-xcp-communication";

  /**
   * Type String for can-init-xcp-communication
   */
  public static final String SVC_CAN_INIT_XCP_COMMUNICATION = "can-init-xcp-communication";

  /**
   * Type String for can-send-message
   */
  public static final String SVC_CAN_SEND_CAN_MESSAGE = "can-send-message";

  /**
   * Type String for can-send-custom-message
   */
  public static final String SVC_CAN_SEND_CUSTOM_CAN_MESSAGE = "can-send-custom-message";

  /**
   * Type String for can-simulate-crc-fault
   */
  public static final String SVC_CAN_SIMULATE_CRC_FAULT = "can-simulate-crc-fault";

  /**
   * Type String for can-suppress-message
   */
  public static final String SVC_CAN_SUPPRESS_CAN_MESSAGE = "can-suppress-message";

  /**
   * Type String for canape-calibration-finished
   */
  public static final String SVC_CANAPE_CALIBRATION_FINISHED = "canape-calibration-finished";

  /**
   * Type String for canape-execute-diagnostic-job
   */
  public static final String SVC_CANAPE_DIAG_EXECUTE_JOB = "canape-execute-diagnostic-job";

  /**
   * Type String for canape-send-diagnostic-read-request
   */
  public static final String SVC_CANAPE_DIAG_SEND_READ_REQUEST =
      "canape-send-diagnostic-read-request";

  /**
   * Type String for canape-send-diagnostic-request-by-name
   */
  public static final String SVC_CANAPE_DIAG_SEND_REQUEST_BY_NAME =
      "canape-send-diagnostic-request-by-name";

  /**
   * Type String for canape-send-diagnostic-write-numeric-request
   */
  public static final String SVC_CANAPE_DIAG_SEND_WRITE_NUMERIC_REQUEST =
      "canape-send-diagnostic-write-numeric-request";

  /**
   * Type String for canape-send-diagnostic-write-string-request
   */
  public static final String SVC_CANAPE_DIAG_SEND_WRITE_STRING_REQUEST =
      "canape-send-diagnostic-write-string-request";

  /**
   * Type String for canape-set-ecu-tester-present
   */
  public static final String SVC_CANAPE_DIAG_SET_ECU_TESTER_PRESENT =
      "canape-set-ecu-tester-present";

  /**
   * Type String for canape-execute-script
   */
  public static final String SVC_CANAPE_EXECUTE_SCRIPT = "canape-execute-script";

  /**
   * Type String for canape-pause-measurement
   */
  public static final String SVC_CANAPE_PAUSE_MEASUREMENT = "canape-pause-measurement";

  /**
   * Type String for canape-set-measurement-file
   */
  public static final String SVC_CANAPE_SET_MEASUREMENT_FILE = "canape-set-measurement-file";

  /**
   * Type String for canape-start-measurement
   */
  public static final String SVC_CANAPE_START_MEASUREMENT = "canape-start-measurement";

  /**
   * Type String for canape-stop-measurement
   */
  public static final String SVC_CANAPE_STOP_MEASUREMENT = "canape-stop-measurement";

  /**
   * Type String for canoe-diag-clear-dtcs-in-ecu
   */
  public static final String SVC_CANOE_DIAG_CLEAR_DTCS_IN_ECU = "canoe-diag-clear-dtcs-in-ecu";

  /**
   * Type String for canoe-diag-clear-dtcs-in-fcn-grp
   */
  public static final String SVC_CANOE_DIAG_CLEAR_DTCS_IN_FCN_GRP =
      "canoe-diag-clear-dtcs-in-fcn-grp";

  /**
   * Type String for canoe-diag-execute-diag-job-on-ecu
   */
  public static final String SVC_CANOE_DIAG_EXECUTE_DIAG_JOB_ON_ECU =
      "canoe-diag-execute-diag-job-on-ecu";

  /**
   * Type String for canoe-diag-read-dtcs-in-ecu
   */
  public static final String SVC_CANOE_DIAG_READ_DTCS_IN_ECU = "canoe-diag-read-dtcs-in-ecu";

  /**
   * Type String for canoe-diag-read-dtcs-in-fcn-grp
   */
  public static final String SVC_CANOE_DIAG_READ_DTCS_IN_FCN_GRP =
      "canoe-diag-read-dtcs-in-fcn-grp";

  /**
   * Type String for canoe-diag-read-value-from-address
   */
  public static final String SVC_CANOE_DIAG_READ_VALUE_FROM_ADDRESS =
      "canoe-diag-read-value-from-address";

  /**
   * Type String for canoe-diag-read-value-from-ecu
   */
  public static final String SVC_CANOE_DIAG_READ_VALUE_FROM_ECU = "canoe-diag-read-value-from-ecu";

  /**
   * Type String for canoe-diag-read-values-from-fcn-grp
   */
  public static final String SVC_CANOE_DIAG_READ_VALUES_FROM_FCN_GRP =
      "canoe-diag-read-values-from-fcn-grp";

  /**
   * Type String for canoe-diag-read-variant-coding-from-ecu
   */
  public static final String SVC_CANOE_DIAG_READ_VARIANT_CODING_FROM_ECU =
      "canoe-diag-read-variant-coding-from-ecu";

  /**
   * Type String for canoe-diag-send-hex-service-to-ecu
   */
  public static final String SVC_CANOE_DIAG_SEND_HEX_SERVICE_TO_ECU =
      "canoe-diag-send-hex-service-to-ecu";

  /**
   * Type String for canoe-diag-send-hex-service-to-fcn-grp
   */
  public static final String SVC_CANOE_DIAG_SEND_HEX_SERVICE_TO_FCN_GRP =
      "canoe-diag-send-hex-service-to-fcn-grp";

  /**
   * Type String for canoe-diag-write-value-to-address
   */
  public static final String SVC_CANOE_DIAG_WRITE_VALUE_TO_ADDRESS =
      "canoe-diag-write-value-to-address";

  /**
   * Type String for canoe-diag-write-value-to-ecu
   */
  public static final String SVC_CANOE_DIAG_WRITE_VALUE_TO_ECU = "canoe-diag-write-value-to-ecu";

  /**
   * Type String for canoe-download-parameter-file
   */
  public static final String SVC_CANOE_DOWNLOAD_PARAMETER_FILE = "canoe-download-parameter-file";

  /**
   * Type String for canoe-pause-simulation
   */
  public static final String SVC_CANOE_PAUSE_SIMULATION = "canoe-pause-simulation";

  /**
   * Type String for canoe-play-stimulus-file
   */
  public static final String SVC_CANOE_PLAY_STIMULUS_FILE = "canoe-play-stimulus-file";

  /**
   * Type String for canoe-send-value
   */
  public static final String SVC_CANOE_SEND_VALUE = "canoe-send-value";

  /**
   * Type String for canoe-reset-all-electrical-errors
   */
  public static final String SVC_CANOE_RESET_ALL_ELECTRICAL_ERRORS =
      "canoe-reset-all-electrical-errors";

  /**
   * Type String for canoe-reset-electrical-error
   */
  public static final String SVC_CANOE_RESET_ELECTRICAL_ERROR = "canoe-reset-electrical-error";

  /**
   * Type String for canoe-set-electrical-error
   */
  public static final String SVC_CANOE_SET_ELECTRICAL_ERROR = "canoe-set-electrical-error";

  /**
   * Type String for canoe-start-simulation
   */
  public static final String SVC_CANOE_START_SIMULATION = "canoe-start-simulation";

  /**
   * Type String for canoe-stop-simulation
   */
  public static final String SVC_CANOE_STOP_SIMULATION = "canoe-stop-simulation";

  /**
   * Type String for ctb-model-switch-value
   */
  public static final String SVC_CTB_MODEL_SWITCH = "ctb-model-switch-value";

  /**
   * Type String for ctb-read-value
   */
  public static final String SVC_CTB_READ_VALUE = "ctb-read-value";

  /**
   * Type String for ctb-write-error
   */
  public static final String SVC_CTB_WRITE_ERROR = "ctb-write-error";

  /**
   * Type String for ctb-write-value
   */
  public static final String SVC_CTB_WRITE_VALUE = "ctb-write-value";

  /**
   * Type String for dspace-diag-clear-dtcs-in-ecu
   */
  public static final String SVC_DSPACE_DIAG_CLEAR_DTCS_IN_ECU = "dspace-diag-clear-dtcs-in-ecu";

  /**
   * Type String for dspace-diag-clear-dtcs-in-fcn-grp
   */
  public static final String SVC_DSPACE_DIAG_CLEAR_DTCS_IN_FCN_GRP =
      "dspace-diag-clear-dtcs-in-fcn-grp";

  /**
   * Type String for dspace-diag-execute-diag-job-on-ecu
   */
  public static final String SVC_DSPACE_DIAG_EXECUTE_DIAG_JOB_ON_ECU =
      "dspace-diag-execute-diag-job-on-ecu";

  /**
   * Type String for dspace-diag-read-dtcs-in-ecu
   */
  public static final String SVC_DSPACE_DIAG_READ_DTCS_IN_ECU = "dspace-diag-read-dtcs-in-ecu";

  /**
   * Type String for dspace-diag-read-dtcs-in-fcn-grp
   */
  public static final String SVC_DSPACE_DIAG_READ_DTCS_IN_FCN_GRP =
      "dspace-diag-read-dtcs-in-fcn-grp";

  /**
   * Type String for dspace-diag-read-value-from-address
   */
  public static final String SVC_DSPACE_DIAG_READ_VALUE_FROM_ADDRESS =
      "dspace-diag-read-value-from-address";

  /**
   * Type String for dspace-diag-read-value-from-ecu
   */
  public static final String SVC_DSPACE_DIAG_READ_VALUE_FROM_ECU =
      "dspace-diag-read-value-from-ecu";

  /**
   * Type String for dspace-diag-read-values-from-fcn-grp
   */
  public static final String SVC_DSPACE_DIAG_READ_VALUES_FROM_FCN_GRP =
      "dspace-diag-read-values-from-fcn-grp";

  /**
   * Type String for dspace-diag-read-variant-coding-from-ecu
   */
  public static final String SVC_DSPACE_DIAG_READ_VARIANT_CODING_FROM_ECU =
      "dspace-diag-read-variant-coding-from-ecu";

  /**
   * Type String for dspace-diag-send-hex-service-to-ecu
   */
  public static final String SVC_DSPACE_DIAG_SEND_HEX_SERVICE_TO_ECU =
      "dspace-diag-send-hex-service-to-ecu";

  /**
   * Type String for dspace-diag-send-hex-service-to-fcn-grp
   */
  public static final String SVC_DSPACE_DIAG_SEND_HEX_SERVICE_TO_FCN_GRP =
      "dspace-diag-send-hex-service-to-fcn-grp";

  /**
   * Type String for dspace-diag-write-value-to-address
   */
  public static final String SVC_DSPACE_DIAG_WRITE_VALUE_TO_ADDRESS =
      "dspace-diag-write-value-to-address";

  /**
   * Type String for dspace-diag-write-value-to-ecu
   */
  public static final String SVC_DSPACE_DIAG_WRITE_VALUE_TO_ECU = "dspace-diag-write-value-to-ecu";

  /**
   * Type String for dspace-download-parameter-file
   */
  public static final String SVC_DSPACE_DOWNLOAD_PARAMETER_FILE = "dspace-download-parameter-file";

  /**
   * Type String for dspace-pause-simulation
   */
  public static final String SVC_DSPACE_PAUSE_SIMULATION = "dspace-pause-simulation";

  /**
   * Type String for dspace-play-stimulus-file
   */
  public static final String SVC_DSPACE_PLAY_STIMULUS_FILE = "dspace-play-stimulus-file";

  /**
   * Type String for dspace-send-value
   */
  public static final String SVC_DSPACE_SEND_VALUE = "dspace-send-value";

  /**
   * Type String for dspace-reset-all-electrical-errors
   */
  public static final String SVC_DSPACE_RESET_ALL_ELECTRICAL_ERRORS =
      "dspace-reset-all-electrical-errors";

  /**
   * Type String for dspace-reset-electrical-error
   */
  public static final String SVC_DSPACE_RESET_ELECTRICAL_ERROR = "dspace-reset-electrical-error";

  /**
   * Type String for dspace-set-electrical-error
   */
  public static final String SVC_DSPACE_SET_ELECTRICAL_ERROR = "dspace-set-electrical-error";

  /**
   * Type String for dspace-start-simulation
   */
  public static final String SVC_DSPACE_START_SIMULATION = "dspace-start-simulation";

  /**
   * Type String for dspace-stop-simulation
   */
  public static final String SVC_DSPACE_STOP_SIMULATION = "dspace-stop-simulation";

  /**
   * Type String for fep-is-signal-muted
   */
  public static final String SVC_FEP_IS_SIGNAL_MUTED = "fep-is-signal-muted";

  /**
   * Type String for fep-mute-signal
   */
  public static final String SVC_FEP_MUTE_SIGNAL = "fep-mute-signal";

  /**
   * Type String for fep-start-all-elements
   */
  public static final String SVC_FEP_START_ALL_ELEMENTS = "fep-start-all-elements";

  /**
   * Type String for fep-stop-all-elements
   */
  public static final String SVC_FEP_STOP_ALL_ELEMENTS = "fep-stop-all-elements";

  /**
   * Type String for fep-unmute-signal
   */
  public static final String SVC_FEP_UNMUTE_SIGNAL = "fep-unmute-signal";

  /**
   * Type String for get-fep-property-bool
   */
  public static final String SVC_FEP_GET_FEP_PROPERTY_BOOL = "get-fep-property-bool";

  /**
   * Type String for get-fep-property-double
   */
  public static final String SVC_FEP_GET_FEP_PROPERTY_DOUBLE = "get-fep-property-double";

  /**
   * Type String for get-fep-property-int
   */
  public static final String SVC_FEP_GET_FEP_PROPERTY_INT = "get-fep-property-int";

  /**
   * Type String for get-fep-property-string
   */
  public static final String SVC_FEP_GET_FEP_PROPERTY_STRING = "get-fep-property-string";

  /**
   * Type String for set-fep-element-status
   */
  public static final String SVC_FEP_SET_FEP_ELEMENT_STATUS = "set-fep-element-status";

  /**
   * Type String for set-fep-property-bool
   */
  public static final String SVC_FEP_SET_FEP_PROPERTY_BOOL = "set-fep-property-bool";

  /**
   * Type String for set-fep-property-double
   */
  public static final String SVC_FEP_SET_FEP_PROPERTY_DOUBLE = "set-fep-property-double";

  /**
   * Type String for set-fep-property-int
   */
  public static final String SVC_FEP_SET_FEP_PROPERTY_INT = "set-fep-property-int";

  /**
   * Type String for set-fep-property-string
   */
  public static final String SVC_FEP_SET_FEP_PROPERTY_STRING = "set-fep-property-string";

  /**
   * Type String for diagrad-get-obd-all-current-data
   */
  public static final String SVC_DIAGRAD_GET_OBD_ALL_CURRENT_DATA =
      "diagrad-get-obd-all-current-data";

  /**
   * Type String for diagrad-login
   */
  public static final String SVC_DIAGRAD_LOGIN = "diagrad-login";

  /**
   * Type String for diagrad-logout
   */
  public static final String SVC_DIAGRAD_LOGOUT = "diagrad-logout";

  /**
   * Type String for diagrad-start-obd-communication
   */
  public static final String SVC_DIAGRAD_START_OBD_COMMUNICATION =
      "diagrad-start-obd-communication";

  /**
   * Type String for diagrad-start-recording
   */
  public static final String SVC_DIAGRAD_START_RECORDING = "diagrad-start-recording";

  /**
   * Type String for diagrad-stop-obd-communication
   */
  public static final String SVC_DIAGRAD_STOP_OBD_COMMUNICATION = "diagrad-stop-obd-communication";

  /**
   * Type String for diagrad-stop-recording
   */
  public static final String SVC_DIAGRAD_STOP_RECORDING = "diagrad-stop-recording";

  /**
   * Type String for sound-play-sound-file
   */
  public static final String SVC_SOUND_PLAY_AUDIO_FILE = "sound-play-sound-file";

  /**
   * Type String for sound-play-beep-sound
   */
  public static final String SVC_SOUND_PLAY_BEEP = "sound-play-beep-sound";

  /**
   * Type String for sound-speak
   */
  public static final String SVC_SOUND_SPEAK = "sound-speak";

  /**
   * Type String for vtd-execute-scp-command
   */
  public static final String SVC_VTD_EXECUTE_SCP_COMMAND = "vtd-execute-scp-command";

  /**
   * Type String for inca-copy-reference-page-to-etk-flash
   */
  public static final String SVC_INCA_COPY_REFERENCE_PAGE_TO_ETK_FLASH =
      "inca-copy-reference-page-to-etk-flash";

  /**
   * Type String for inca-copy-reference-page-to-working-page
   */
  public static final String SVC_INCA_COPY_REFERENCE_PAGE_TO_WORKING_PAGE =
      "inca-copy-reference-page-to-working-page";

  /**
   * Type String for inca-copy-working-page-to-etk-flash
   */
  public static final String SVC_INCA_COPY_WORKING_PAGE_TO_ETK_FLASH =
      "inca-copy-working-page-to-etk-flash";

  /**
   * Type String for inca-download-work-page
   */
  public static final String SVC_INCA_DOWNLOAD_WORK_PAGE = "inca-download-work-page";

  /**
   * Type String for inca-execute-odx-flashing
   */
  public static final String SVC_INCA_EXECUTE_ODX_FLASHING = "inca-execute-odx-flashing";

  /**
   * Type String for inca-flash-hex-file
   */
  public static final String SVC_INCA_FLASH_HEX_FILE = "inca-flash-hex-file";

  /**
   * Type String for inca-pause-measurement
   */
  public static final String SVC_INCA_PAUSE_MEASUREMENT = "inca-pause-measurement";

  /**
   * Type String for inca-start-target-os
   */
  public static final String SVC_INCA_START_ETARGET_OS = "inca-start-target-os";

  /**
   * Type String for inca-start-measurement
   */
  public static final String SVC_INCA_START_MEASUREMENT = "inca-start-measurement";

  /**
   * Type String for inca-stop-target-os
   */
  public static final String SVC_INCA_STOP_ETARGET_OS = "inca-stop-target-os";

  /**
   * Type String for inca-stop-measurement
   */
  public static final String SVC_INCA_STOP_MEASUREMENT = "inca-stop-measurement";

  /**
   * Type String for inca-upload-work-base
   */
  public static final String SVC_INCA_UPLOAD_WORKBASE = "inca-upload-work-base";

  /**
   * Type String for inca-write-event-comment
   */
  public static final String SVC_INCA_WRITE_EVENT_COMMENT = "inca-write-event-comment";

  /**
   * Type String for labcar-download-parameter-file
   */
  public static final String SVC_LABCAR_DOWNLOAD_LABCAR_PARAMETER_FILE =
      "labcar-download-parameter-file";

  /**
   * Type String for labcar-download-stimulus-file
   */
  public static final String SVC_LABCAR_DOWNLOAD_LABCAR_STIMULUS_FILE =
      "labcar-download-stimulus-file";

  /**
   * Type String for labcar-run-stimulus-file
   */
  public static final String SVC_LABCAR_RUN_LABCAR_STIMULUS_FILE = "labcar-run-stimulus-file";

  /**
   * Type String for labcar-stop-stimulus-file
   */
  public static final String SVC_LABCAR_STOP_LABCAR_STIMULUS_FILE = "labcar-stop-stimulus-file";

  /**
   * Type String for lauterbach-execute-command
   */
  public static final String SVC_LAUTERBACH_EXECUTE_COMMAND = "lauterbach-execute-command";

  /**
   * Type String for plsude-reset-trace-data
   */
  public static final String SVC_PLSUDE_RESET_TRACE_DATA = "plsude-reset-trace-data";

  /**
   * Type String for slrt-diag-clear-dtcs-in-ecu
   */
  public static final String SVC_SLRT_DIAG_CLEAR_DTCS_IN_ECU = "slrt-diag-clear-dtcs-in-ecu";

  /**
   * Type String for slrt-diag-clear-dtcs-in-fcn-grp
   */
  public static final String SVC_SLRT_DIAG_CLEAR_DTCS_IN_FCN_GRP =
      "slrt-diag-clear-dtcs-in-fcn-grp";

  /**
   * Type String for slrt-diag-execute-diag-job-on-ecu
   */
  public static final String SVC_SLRT_DIAG_EXECUTE_DIAG_JOB_ON_ECU =
      "slrt-diag-execute-diag-job-on-ecu";

  /**
   * Type String for slrt-diag-read-dtcs-in-ecu
   */
  public static final String SVC_SLRT_DIAG_READ_DTCS_IN_ECU = "slrt-diag-read-dtcs-in-ecu";

  /**
   * Type String for slrt-diag-read-dtcs-in-fcn-grp
   */
  public static final String SVC_SLRT_DIAG_READ_DTCS_IN_FCN_GRP = "slrt-diag-read-dtcs-in-fcn-grp";

  /**
   * Type String for slrt-diag-read-value-from-address
   */
  public static final String SVC_SLRT_DIAG_READ_VALUE_FROM_ADDRESS =
      "slrt-diag-read-value-from-address";

  /**
   * Type String for slrt-diag-read-value-from-ecu
   */
  public static final String SVC_SLRT_DIAG_READ_VALUE_FROM_ECU = "slrt-diag-read-value-from-ecu";

  /**
   * Type String for slrt-diag-read-values-from-fcn-grp
   */
  public static final String SVC_SLRT_DIAG_READ_VALUES_FROM_FCN_GRP =
      "slrt-diag-read-values-from-fcn-grp";

  /**
   * Type String for slrt-diag-read-variant-coding-from-ecu
   */
  public static final String SVC_SLRT_DIAG_READ_VARIANT_CODING_FROM_ECU =
      "slrt-diag-read-variant-coding-from-ecu";

  /**
   * Type String for slrt-diag-send-hex-service-to-ecu
   */
  public static final String SVC_SLRT_DIAG_SEND_HEX_SERVICE_TO_ECU =
      "slrt-diag-send-hex-service-to-ecu";

  /**
   * Type String for slrt-diag-send-hex-service-to-fcn-grp
   */
  public static final String SVC_SLRT_DIAG_SEND_HEX_SERVICE_TO_FCN_GRP =
      "slrt-diag-send-hex-service-to-fcn-grp";

  /**
   * Type String for slrt-diag-write-value-to-address
   */
  public static final String SVC_SLRT_DIAG_WRITE_VALUE_TO_ADDRESS =
      "slrt-diag-write-value-to-address";

  /**
   * Type String for slrt-diag-write-value-to-ecu
   */
  public static final String SVC_SLRT_DIAG_WRITE_VALUE_TO_ECU = "slrt-diag-write-value-to-ecu";

  /**
   * Type String for slrt-download-parameter-file
   */
  public static final String SVC_SLRT_DOWNLOAD_PARAMETER_FILE = "slrt-download-parameter-file";

  /**
   * Type String for slrt-pause-simulation
   */
  public static final String SVC_SLRT_PAUSE_SIMULATION = "slrt-pause-simulation";

  /**
   * Type String for slrt-play-stimulus-file
   */
  public static final String SVC_SLRT_PLAY_STIMULUS_FILE = "slrt-play-stimulus-file";

  /**
   * Type String for slrt-send-value
   */
  public static final String SVC_SLRT_SEND_VALUE = "slrt-send-value";

  /**
   * Type String for slrt-reset-all-electrical-errors
   */
  public static final String SVC_SLRT_RESET_ALL_ELECTRICAL_ERRORS =
      "slrt-reset-all-electrical-errors";

  /**
   * Type String for slrt-reset-electrical-error
   */
  public static final String SVC_SLRT_RESET_ELECTRICAL_ERROR = "slrt-reset-electrical-error";

  /**
   * Type String for slrt-set-electrical-error
   */
  public static final String SVC_SLRT_SET_ELECTRICAL_ERROR = "slrt-set-electrical-error";

  /**
   * Type String for slrt-start-simulation
   */
  public static final String SVC_SLRT_START_SIMULATION = "slrt-start-simulation";

  /**
   * Type String for slrt-stop-simulation
   */
  public static final String SVC_SLRT_STOP_SIMULATION = "slrt-stop-simulation";

  /**
   * Type String for veristand-diag-clear-dtcs-in-ecu
   */
  public static final String SVC_VERISTAND_DIAG_CLEAR_DTCS_IN_ECU =
      "veristand-diag-clear-dtcs-in-ecu";

  /**
   * Type String for veristand-diag-clear-dtcs-in-fcn-grp
   */
  public static final String SVC_VERISTAND_DIAG_CLEAR_DTCS_IN_FCN_GRP =
      "veristand-diag-clear-dtcs-in-fcn-grp";

  /**
   * Type String for veristand-diag-execute-diag-job-on-ecu
   */
  public static final String SVC_VERISTAND_DIAG_EXECUTE_DIAG_JOB_ON_ECU =
      "veristand-diag-execute-diag-job-on-ecu";

  /**
   * Type String for veristand-diag-read-dtcs-in-ecu
   */
  public static final String SVC_VERISTAND_DIAG_READ_DTCS_IN_ECU =
      "veristand-diag-read-dtcs-in-ecu";

  /**
   * Type String for veristand-diag-read-dtcs-in-fcn-grp
   */
  public static final String SVC_VERISTAND_DIAG_READ_DTCS_IN_FCN_GRP =
      "veristand-diag-read-dtcs-in-fcn-grp";

  /**
   * Type String for veristand-diag-read-value-from-address
   */
  public static final String SVC_VERISTAND_DIAG_READ_VALUE_FROM_ADDRESS =
      "veristand-diag-read-value-from-address";

  /**
   * Type String for veristand-diag-read-value-from-ecu
   */
  public static final String SVC_VERISTAND_DIAG_READ_VALUE_FROM_ECU =
      "veristand-diag-read-value-from-ecu";

  /**
   * Type String for veristand-diag-read-values-from-fcn-grp
   */
  public static final String SVC_VERISTAND_DIAG_READ_VALUES_FROM_FCN_GRP =
      "veristand-diag-read-values-from-fcn-grp";

  /**
   * Type String for veristand-diag-read-variant-coding-from-ecu
   */
  public static final String SVC_VERISTAND_DIAG_READ_VARIANT_CODING_FROM_ECU =
      "veristand-diag-read-variant-coding-from-ecu";

  /**
   * Type String for veristand-diag-send-hex-service-to-ecu
   */
  public static final String SVC_VERISTAND_DIAG_SEND_HEX_SERVICE_TO_ECU =
      "veristand-diag-send-hex-service-to-ecu";

  /**
   * Type String for veristand-diag-send-hex-service-to-fcn-grp
   */
  public static final String SVC_VERISTAND_DIAG_SEND_HEX_SERVICE_TO_FCN_GRP =
      "veristand-diag-send-hex-service-to-fcn-grp";

  /**
   * Type String for veristand-diag-write-value-to-address
   */
  public static final String SVC_VERISTAND_DIAG_WRITE_VALUE_TO_ADDRESS =
      "veristand-diag-write-value-to-address";

  /**
   * Type String for veristand-diag-write-value-to-ecu
   */
  public static final String SVC_VERISTAND_DIAG_WRITE_VALUE_TO_ECU =
      "veristand-diag-write-value-to-ecu";

  /**
   * Type String for veristand-download-parameter-file
   */
  public static final String SVC_VERISTAND_DOWNLOAD_PARAMETER_FILE =
      "veristand-download-parameter-file";

  /**
   * Type String for veristand-pause-simulation
   */
  public static final String SVC_VERISTAND_PAUSE_SIMULATION = "veristand-pause-simulation";

  /**
   * Type String for veristand-play-stimulus-file
   */
  public static final String SVC_VERISTAND_PLAY_STIMULUS_FILE = "veristand-play-stimulus-file";

  /**
   * Type String for veristand-send-value
   */
  public static final String SVC_VERISTAND_SEND_VALUE = "veristand-send-value";

  /**
   * Type String for veristand-reset-all-electrical-errors
   */
  public static final String SVC_VERISTAND_RESET_ALL_ELECTRICAL_ERRORS =
      "veristand-reset-all-electrical-errors";

  /**
   * Type String for veristand-reset-electrical-error
   */
  public static final String SVC_VERISTAND_RESET_ELECTRICAL_ERROR =
      "veristand-reset-electrical-error";

  /**
   * Type String for veristand-set-electrical-error
   */
  public static final String SVC_VERISTAND_SET_ELECTRICAL_ERROR = "veristand-set-electrical-error";

  /**
   * Type String for veristand-start-simulation
   */
  public static final String SVC_VERISTAND_START_SIMULATION = "veristand-start-simulation";

  /**
   * Type String for veristand-stop-simulation
   */
  public static final String SVC_VERISTAND_STOP_SIMULATION = "veristand-stop-simulation";

  /**
   * Delivers a list of arguments for a {@link ServiceStep}.
   * 
   * @return List containing arguments.
   * @throws RemoteException
   *           remote communication problem
   */
  public List<ServiceStepArgument> getArguments() throws RemoteException;

  /**
   * @return <code>true</code> if a {@link ServiceStep} has arguments, regardless whether they are
   *         mandatory or not.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean hasArguments() throws RemoteException;

  /**
   * Delivers the number of available arguments for a {@link ServiceStep}.
   * 
   * @return Number of arguments.
   * @throws RemoteException
   *           remote communication problem
   */
  int getNumberOfArguments() throws RemoteException;

  /**
   * @return <code>true</code> if this step should run "always".
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isAlways() throws RemoteException;

  /**
   * Determines if this step should run "always".
   * 
   * @param value
   *          <code>true</code> to use always semantics
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAlways(boolean value) throws RemoteException;

  /**
   * Determines whether this step can be executed "once" and "always". If true, you can use the
   * {@code setAlways} function to set
   * 
   * @return {@code true} if once and always semantics are allowed for this step.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean canBeExecutedOnceAndAlways() throws RemoteException;

}
