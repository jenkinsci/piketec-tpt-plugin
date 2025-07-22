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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 * A property consisting of multiple key-value-pairs.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class PropertyMap implements Property, Serializable {

  static final long serialVersionUID = 1L;

  HashMap<String, Property> map = new HashMap<>();

  /**
   * @return Returns a set of all keys
   */
  public Set<String> getKeys() {
    return map.keySet();
  }

  /**
   * Get the Values defined for a given key.
   * 
   * @param key
   *          The key
   * @return The value that is stored in the map for this key.
   */
  public Property getValue(String key) {
    return map.get(key);
  }

  /**
   * Assign a property value to a given key.Already existing properties will be overwritten.
   * 
   * @param key
   *          The property key
   * @param value
   *          The property value that shall be assigned to this key.
   */
  public void setValue(String key, Property value) {
    map.put(key, value);
  }

  /**
   * Shorthand for <code>setValue(key, new PropertyBool(value))</code>
   * 
   * @param key
   *          The property key
   * @param value
   *          The boolean value that shall be assigned to this key.
   */
  public void setValue(String key, boolean value) {
    map.put(key, new PropertyBool(value));
  }

  /**
   * Shorthand for <code>setValue(key, new PropertyDecimal(value))</code>
   * 
   * @param key
   *          The property key
   * @param value
   *          The double value that shall be assigned to this key.
   */
  public void setValue(String key, double value) {
    map.put(key, new PropertyDecimal(value));
  }

  /**
   * Shorthand for <code>setValue(key, new PropertyInt(value))</code>
   * 
   * @param key
   *          The property key
   * @param value
   *          The integer property value that shall be assigned to this key.
   */
  public void setValue(String key, int value) {
    map.put(key, new PropertyInt(value));
  }

  /**
   * Shorthand for <code>setValue(key, new PropertyLong(value))</code>
   * 
   * @param key
   *          The property key
   * @param value
   *          The long integer property value that shall be assigned to this key.
   */
  public void setValue(String key, long value) {
    map.put(key, new PropertyLong(value));
  }

  /**
   * Shorthand for <code>setValue(key, new PropertyString(value))</code>
   * 
   * @param key
   *          The property key
   * @param value
   *          The {@link String} property value that shall be assigned to this key.
   */
  public void setValue(String key, String value) {
    map.put(key, new PropertyString(value));
  }

  /**
   * Shorthand for <code>setValue(key, new PropertyList())</code>
   * 
   * @param key
   *          The property key
   * @return the new {@link PropertyList}
   */
  public PropertyList setListValue(String key) {
    PropertyList list = new PropertyList();
    map.put(key, list);
    return list;
  }

  /**
   * Shorthand for <code>setValue(key, new PropertyMap())</code>
   * 
   * @param key
   *          The property key
   * @return the new {@link PropertyMap}
   */
  public PropertyMap setMapValue(String key) {
    PropertyMap newMap = new PropertyMap();
    map.put(key, newMap);
    return newMap;
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    toString(buffer, "");
    return buffer.toString();
  }

  @Override
  public void toString(StringBuffer buffer, String indentation) {
    String indent1 = indentation + "  ";
    String indent2 = indentation + "    ";
    buffer.append(indentation);
    buffer.append("PropertyMap {\n");
    for (Entry<String, Property> entry : map.entrySet()) {
      if (entry.getKey() == null) {
        buffer.append(indent1);
        buffer.append("null");
      } else {
        buffer.append(indent1);
        buffer.append(entry.getKey());
      }
      buffer.append("=\n");
      if (entry.getValue() == null) {
        buffer.append(indent2);
        buffer.append("null");
      } else {
        entry.getValue().toString(buffer, indent2);
      }
      buffer.append('\n');
    }
    buffer.append(indentation);
    buffer.append('}');
  }

  @Override
  public int hashCode() {
    return Objects.hash(map);
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
    PropertyMap other = (PropertyMap)obj;
    if (!Objects.equals(map, other.map)) {
      return false;
    }
    return true;
  }

}
