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
package com.piketec.tpt.api;

import java.io.Serializable;
import java.util.Objects;

/**
 * A simple pair.
 * 
 * @param <A>
 *          type parameter for first element in pair
 * @param <B>
 *          type parameter for second element in pair
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class Pair<A, B> implements Serializable {

  private static final long serialVersionUID = 8728449163569637101L;

  private A first;

  private B second;

  /**
   * Create a pair of <code>first</code> and <code>second</code>
   * 
   * @param first
   *          The first pair member
   * @param second
   *          The second pair member
   */
  public Pair(A first, B second) {
    this.first = first;
    this.second = second;
  }

  /**
   * @return The first pair member
   */
  public A getFirst() {
    return first;
  }

  /**
   * Set the first pair member
   * 
   * @param first
   *          The new first pair member
   */
  public void setFirst(A first) {
    this.first = first;
  }

  /**
   * @return The second pair member
   */
  public B getSecond() {
    return second;
  }

  /**
   * Set the second pair member
   * 
   * @param second
   *          The new second pair member
   */
  public void setSecond(B second) {
    this.second = second;
  }

  @Override
  public String toString() {
    return "(" + first + "," + second + ")";
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second);
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
    Pair other = (Pair)obj;
    return Objects.equals(first, other.first) && Objects.equals(second, other.second);
  }
}
