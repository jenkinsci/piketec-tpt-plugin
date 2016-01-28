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
package com.piketec.tpt.api;

import java.rmi.RemoteException;

import com.piketec.tpt.api.properties.PropertyMap;

/**
 * Die Konfuguration fuer eine Ausfuehrungsplatform. Die spezifischen Eigenschaften der
 * verschiedenen Platform-typen werden ueber die generischen Properties (vgl. {@link PropertyMap})
 * abgebildet.
 */
public interface PlatformConfiguration extends NamedObject, PlatformOrExecutionItemEnabler {

  public String getType() throws ApiException, RemoteException;

  /**
   * @return Timeout in Mikrosekunden.
   *
   */
  public long getTimeOut() throws ApiException, RemoteException;

  /**
   * Setzt den Timeout
   * 
   * @param timeOut
   *          Timeout in Mikrosekunden.
   *
   */
  public void setTimeOut(long timeOut) throws ApiException, RemoteException;

  /**
   * @return Schrittweite in Mikrosekunden.
   *
   */
  public long getStepSize() throws ApiException, RemoteException;

  /**
   * Setzt die Schrittweite
   * 
   * @param stepSize
   *          Schrittweite in Mikrosekunden.
   *
   */
  public void setStepSize(long stepSize) throws ApiException, RemoteException;

  /**
   * @return Groesse des Ringspeichers fuer den Zugriff auf Signalwerte in der Vergangenheit in
   *         Anzahl an Testschritten.
   *
   */
  public int getHistorySize() throws ApiException, RemoteException;

  /**
   * Setzt die Groesse des Ringspeichers fuer den Zugriff auf Signalwerte in der Vergangenheit in
   * Anzahl an Testschritten.
   * 
   * @param historySize
   *          Die neue Groesse des Ringspeichers
   */
  public void setHistorySize(int historySize) throws ApiException, RemoteException;

  /**
   * Liefert eine ProptertyMap String -&gt; Property zur Darstellung der Properties. Eine Property
   * ist entweder erneut eine PropertyMap, wodurch eine Baumstruktur entsteht oder ein String-Wert.
   *
   * @return die Properties
   */
  public PropertyMap getProperties() throws ApiException, RemoteException;

  /**
   * Konfiguriert die PlatformConfig anhand der uebergebenen PropertyMap.
   *
   * @param properties
   */
  public void setProperties(PropertyMap properties) throws ApiException, RemoteException;

  /**
   * Fuehrt eine Funktion der Platformkonfiguration aus. Die Funktion wird ueber ihren Namen
   * identifiziert. Die PropertyMap ermoeglicht es parameter zu uebergeben. Ist der angegeben
   * Funktionsname nicht bekannt oder entspricht die PropertyMap nicht den Erwartungen wird eine
   * ApiException ausgeloest. Die verfuegbaren Funktionen sind von der konkreten Platform abhaengig.
   * 
   * @param functionName
   *          Der Name der aufzurufenden Funktion
   * @param parameterOrNull
   *          Die Parameter oder <code>null</code>
   * @throws ApiException
   *           Wenn die Parameter nicht den Erwartungen entsprechen oder die Funktion unbekannt ist.
   */
  public void invoke(String functionName, PropertyMap parameterOrNull) throws ApiException,
      RemoteException;

}
