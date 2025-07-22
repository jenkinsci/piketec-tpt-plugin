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
package com.piketec.tpt.api.properties;

import java.util.ArrayList;

/**
 * A list of properties
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class PropertyList extends ArrayList<Property> implements Property {

  private static final long serialVersionUID = 2079986645319486649L;

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    toString(buffer, "");
    return buffer.toString();
  }

  /**
   * Shorthand for <code>add(new PropertyBool(value))</code>
   * 
   * @param value
   *          The boolean value that shall be added.
   */
  public void add(boolean value) {
    this.add(new PropertyBool(value));
  }

  /**
   * Shorthand for <code>add(new PropertyDecimal(value))</code>
   * 
   * @param value
   *          The double value that shall be added.
   */
  public void add(double value) {
    this.add(new PropertyDecimal(value));
  }

  /**
   * Shorthand for <code>add(new PropertyInt(value))</code>
   * 
   * @param value
   *          The integer property value that shall be added.
   */
  public void add(int value) {
    this.add(new PropertyInt(value));
  }

  /**
   * Shorthand for <code>add(new PropertyLong(value))</code>
   * 
   * @param value
   *          The long integer property value that shall be added.
   */
  public void add(long value) {
    this.add(new PropertyLong(value));
  }

  /**
   * Shorthand for <code>add(new PropertyString(value))</code>
   * 
   * @param value
   *          The {@link String} property value that shall be added.
   */
  public void add(String value) {
    this.add(new PropertyString(value));
  }

  /**
   * Shorthand for <code>add(new PropertyList())</code>
   * 
   * @return the new {@link PropertyList}
   */
  public PropertyList addList() {
    PropertyList list = new PropertyList();
    this.add(list);
    return list;
  }

  /**
   * Shorthand for <code>add(new PropertyMap())</code>
   * 
   * @return the new {@link PropertyMap}
   */
  public PropertyMap addMap() {
    PropertyMap map = new PropertyMap();
    this.add(map);
    return map;
  }

  /**
   * Shorthand for <code>add(new PropertyBool(value))</code>
   * 
   * @param index
   *          the position in the list where the element should be added.
   * 
   * @param value
   *          The boolean value that shall be added.
   */
  public void set(int index, boolean value) {
    this.set(index, new PropertyBool(value));
  }

  /**
   * Shorthand for <code>add(new PropertyDecimal(value))</code>
   * 
   * @param index
   *          the position in the list where the element should be added.
   * 
   * @param value
   *          The double value that shall be added.
   */
  public void set(int index, double value) {
    this.set(index, new PropertyDecimal(value));
  }

  /**
   * Shorthand for <code>add(new PropertyInt(value))</code>
   * 
   * @param index
   *          the position in the list where the element should be added.
   * 
   * @param value
   *          The integer property value that shall be added.
   */
  public void set(int index, int value) {
    this.set(index, new PropertyInt(value));
  }

  /**
   * Shorthand for <code>add(new PropertyLong(value))</code>
   * 
   * @param index
   *          the position in the list where the element should be added.
   * 
   * @param value
   *          The long integer property value that shall be added.
   */
  public void set(int index, long value) {
    this.set(index, new PropertyLong(value));
  }

  /**
   * Shorthand for <code>add(new PropertyString(value))</code>
   * 
   * @param index
   *          the position in the list where the element should be added.
   * 
   * @param value
   *          The {@link String} property value that shall be added.
   */
  public void set(int index, String value) {
    this.set(index, new PropertyString(value));
  }

  /**
   * Shorthand for <code>add(new PropertyList())</code>
   * 
   * @param index
   *          the position in the list where the new list should be added
   */
  public void setList(int index) {
    this.set(index, new PropertyList());
  }

  /**
   * Shorthand for <code>add(new PropertyMap())</code>
   * 
   * @param index
   *          the position in the list where the map should be added
   */
  public void setMap(int index) {
    this.set(index, new PropertyMap());
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
