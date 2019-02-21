/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016-2019 PikeTec GmbH
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
package com.piketec.tpt.api.properties;

import java.util.ArrayList;

/**
 * A list of properties
 * 
 */
public class PropertyList extends ArrayList<Property> implements Property {

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    toString(buffer, "");
    return buffer.toString();
  }

  @Override
  public void toString(StringBuffer buffer, String indentation) {
    buffer.append(indentation);
    buffer.append("PropertyList [\n");
    for (Property p : this) {
      if (p == null) {
        buffer.append("null");
      } else {
        p.toString(buffer, indentation + "  ");
      }
      buffer.append("\n");
    }
    buffer.append(indentation);
    buffer.append("]");
  }

}
