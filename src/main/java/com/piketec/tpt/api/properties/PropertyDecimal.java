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
package com.piketec.tpt.api.properties;

import java.io.Serializable;

/**
 * A decimal property value.
 */
public class PropertyDecimal implements Property, Serializable {

  static final long serialVersionUID = 1L;

  private final double value;

  /**
   * @param value
   *          the actual decimal value represented by this property.
   */
  public PropertyDecimal(double value) {
    this.value = value;
  }

  /**
   * @return the decimal value represented by this property.
   */
  public double getValue() {
    return value;
  }

  @Override
  public String toString() {
    return Double.toString(value);
  }

  @Override
  public void toString(StringBuffer buffer, String indentation) {
    buffer.append(indentation);
    buffer.append(toString());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(value);
    result = prime * result + (int)(temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PropertyDecimal other = (PropertyDecimal)obj;
    if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) {
      return false;
    }
    return true;
  }

}
