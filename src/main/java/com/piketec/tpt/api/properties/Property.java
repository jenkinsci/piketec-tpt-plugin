/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 PikeTec GmbH
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

/**
 * Stellt einen beliebigen Wert dar. Der Wert kann dabei eine beliebige Struktur sein.
 */
public interface Property {

  /**
   * Fuellt den uebergebenen <code>StringBuffer</code> mit einem moeglichst uebersichtlichen
   * String-Repraesenatation aller dieser Property und aller Unterelemente, wobei mit der
   * uebergebenen Einrueckung <code>indentation</code> begonnen wird und jedes weitere Element um
   * weitere Leerzeichen eingerueckt wird.
   * 
   * @param buffer
   * @param indentation
   *          Der aktuelle String zum einruecken.
   */
  void toString(StringBuffer buffer, String indentation);

}
