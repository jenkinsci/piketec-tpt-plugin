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
package com.piketec.tpt.api.cplatform;

/**
 * Setting to connect a function in the c-code to TPT
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public enum FunctionConnectionType {

  /** Do nothing concerning this function */
  IGNORE,

  /**
   * Schedule the function with TPT. Only supported for functions that are implemented in the SUT
   * code.
   */
  SCHEDULE,

  /**
   * Create a TPT client function to be called from a steplist. Only supported for functions that
   * are implemented in the SUT code.
   */
  CLIENT_FUNCTION,

  /**
   * Stub the function to call a TPT server function. Only supported for functions that are not
   * implemented in the SUT-code.
   */
  SERVER_FUNCTION,

  /** Stub the function to connect the arguments and the return value to TPT channels */
  ARGUMENTS_AS_CHANNELS,
  /**
   * "Stub the function returning zeros. Only supported for functions that are not implemented in
   * the SUT-code.
   */
  EMPTYSTUB;

}
