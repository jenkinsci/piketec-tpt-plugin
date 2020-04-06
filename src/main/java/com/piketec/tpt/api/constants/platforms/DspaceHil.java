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
 * For further information, please refer to the User Guide, dSPACE HiL Platform.
 */
public interface DspaceHil extends BasicPlatformConfig {

  public static final String MATLABVERSIONNAME = "matlabVersionName";

  public static final String MATLABLOGFILE = "matlabLogfile";

  public static final String MATLABSTARTUPDIR = "matlabStartupDir";

  public static final String ONLINECONFIG = "onlineConfiguration";

  public static final String APITYPE = "apiType";

  public static final String TAG_VENDOR_NAME = "vendorName";

  public static final String TAG_PRODUCT_NAME = "productName";

  public static final String TAG_PRODUCT_VERSION = "productVersion";

  public static final String BOARDNAME = "boardName";

  public static final String SDFFILENAME = "sdfFileName";

  public static final String TAG_TASK_INFO = "taskInfo";

  public static final String TAG_TASK_INFOS = "taskInfos";

  public static final String TPTSFUNCTIONLOCATION = "tptSfunctionLocation";

  public static final String MODELFILENAME = "modelFileName";

  public static final String TPTIOFILENAME = "tptIoFileName";

  public static final String BOARDTYPE = "boardType";

  public static final String APPLICATIOINFO = "applicationInfo";

  public static final String TPTSFUNNAME = "tptSfunName";

  public static final String TPTCMDNAME = "tptCmdName";

  public static final String DOWNSAMPLING = "downSampling";

  public static final String STARTDELAY = "startDelay";

  public static final String NUMOFSAMPLES = "numOfSamples";

  public static final String USECURRENTAPPINFO = "useCurrentAppInfo";

  public static final String PREFIRSTACTIVE = "preFirstActive";

  public static final String PREFIRSTCMD = "preFirstCmd";

  public static final String PREALLACTIVE = "preAllActive";

  public static final String PREALLCMD = "preAllCmd";

  public static final String EVERYNTHACTIVE = "everyNthActive";

  public static final String EVERYNTHCMD = "everyNthCmd";

  public static final String EVERYNTHWHICH = "everyNthWhich";

  public static final String POSTALLACTIVE = "postAllActive";

  public static final String POSTALLCMD = "postAllCmd";

  public static final String POSTLASTACTIVE = "postLastActive";

  public static final String POSTLASTCMD = "postLastCmd";

  public static final String LOGREQUESTED = "logRequested";

  public static final String SHOWONLYCOMMANDS = "showOnlyCommands";

  public static final String SHOWSECTIONHEADERS = "showSectionHeaders";

  public static final String TASK_PATH = "taskpath";
}
