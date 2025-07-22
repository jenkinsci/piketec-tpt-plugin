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
package com.piketec.tpt.api.util;

/**
 * This exception is thrown from methods that are deprecated and supposed to be removed, but still
 * exist as <i>empty</i>, non-functional stub methods. Such methods were <b>not</b> removed from the
 * API, since they are used by customers not only from Java processes but also from Jython scripts
 * (e.g., the API Script view or pre/post scripts of platforms and execution configs), where they
 * would lead to runtime errors that are difficult to understand (due to the lack of a compiler in
 * Jython). So, we throw this exception instead that will lead to better understandable messages.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class DeprecatedAndRemovedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new DeprecatedAndRemovedException with the specified details.
   * 
   * @param fullQualifiedName
   *          The method that has been removed
   * @param tptversion
   *          The TPT version it has been removed
   * 
   */
  public DeprecatedAndRemovedException(String fullQualifiedName, String tptversion) {
    super("The method " + fullQualifiedName + " has been removed since TPT-" + tptversion);
    assert (fullQualifiedName.contains("com.piketec.tpt.api")
        || fullQualifiedName.contains("tptplugins.vw.api"))
        && !fullQualifiedName.contains(
            ".apiimpl.") : "Please use the full qualified names of the interface and not the internal implementation.";
  }

}
