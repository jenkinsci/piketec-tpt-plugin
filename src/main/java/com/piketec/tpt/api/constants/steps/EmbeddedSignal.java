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
package com.piketec.tpt.api.constants.steps;

import com.piketec.tpt.api.steplist.EmbeddedSignalStep;

/**
 * For further information, please refer to the User Guide, Embedded Signal step.
 * 
 * @deprecated Will be removed in TPT-18. Use {@link EmbeddedSignalStep} instead.
 */
@Deprecated
public interface EmbeddedSignal extends AbstractStep {

  @Deprecated
  public static final String CHANNEL = "channel";

  @Deprecated
  public static final String SIGNAL_POINTS = "signal-points";

  @Deprecated
  public static final String INTERPOLATION_MODE = "interpolation-mode";

  @Deprecated
  public static final String TIME = "time";

  @Deprecated
  public static final String VALUE = "value";
}
