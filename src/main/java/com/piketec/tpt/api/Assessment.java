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
 */package com.piketec.tpt.api;

import java.rmi.RemoteException;

import com.piketec.tpt.api.properties.PropertyMap;

/**
 * Ein Assessment. Die spezifischen Eigenschaften der verschiedenen Assessment-typen werden ueber
 * die generischen Properties (vgl. {@link PropertyMap}) abgebildet.
 */
public interface Assessment extends AssessmentOrGroup {

  /**
   * @return Typname des Assessment.
   */
  public String getType() throws ApiException, RemoteException;

  /**
   * @return Beschreibung des Assessments.
   */
  public String getDescription() throws ApiException, RemoteException;

  /**
   * Setzt die Berschreibung des Assessments.
   * 
   * @param description
   *          Die neue Beschreibung.
   */
  public void setDescription(String description) throws ApiException, RemoteException;

  /**
   * Liefert die Liste der Varianten, fuer die dieses Assessment aktiviert ist. Ist die Liste leer,
   * ist das Assessment fuer alle Varianten aktiviert.
   *
   * @return die Liste der Varianten.
   */
  public RemoteList<ScenarioOrGroup> getEnabledVariants() throws ApiException, RemoteException;

  /**
   * Aktiviert das Assessment fuer eine Variant oder Varaintengruppe.
   * 
   * @param sog
   *          Die Varainte oder die Variantengruppe, fuer den dieses Assessment aktiviert werden
   *          soll
   *
   */
  public void enableForVariant(ScenarioOrGroup sog) throws ApiException, RemoteException;

  /**
   * Liefert die Liste der TestCases, fuer die dieses Assessment aktiviert ist. Ist die Liste leer,
   * ist das Assessment fuer alle TestCases aktiviert.
   *
   * @return die Liste der TestCases
   */
  public RemoteCollection<ScenarioOrGroup> getEnabledTestCases() throws ApiException,
      RemoteException;

  /**
   * Aktiviert das Assessment fuer einen TestCase oder TestCases-Gruppe.
   * 
   * @param sog
   *          Der Testfall oder die testfallgruppe, fuer den dieses Assessment aktiviert werden soll
   */
  public void enableForTestCase(ScenarioOrGroup sog) throws ApiException, RemoteException;

  /**
   * Liefert die Liste der PlatformConfigurations und Exe, fuer die dieses Assessment aktiviert ist.
   * Ist die Liste leer, ist das Assessment fuer jede PlatformConfiguration aktiviert.
   *
   * @return Die Liste der Platformkonfigurationen
   */
  public RemoteList<PlatformOrExecutionItemEnabler> getEnabledPlatformConfigurationsOrExecutionConfigurationItems()
      throws ApiException, RemoteException;

  /**
   * Aktiviert das Assessment fuer eine Platformkonfiguration.
   *
   * @param pc
   *          die Platformkonfiguration, fuer diese Assessment aktiviert werden soll
   */
  public void enableForPlatformConfiguration(PlatformConfiguration pc) throws ApiException,
      RemoteException;

  /**
   * Aktiviert das Assessment fuer einen Eintrag einer Ausfuehrungskonfiguration.
   *
   * @param execItem
   *          die Platformkonfiguration, fuer diese Assessment aktiviert werden soll
   */
  public void enableForExecutionConfigurationItem(ExecutionConfigurationItem execItem)
      throws ApiException, RemoteException;

  /**
   * Liefert eine ProptertyMap String -&gt; Property zur Darstellung der Properties. Eine Property
   * ist entweder erneut eine PropertyMap, wodurch eine Baumstruktur entsteht oder ein String-Wert.
   *
   * @return Die PropertyMap mit den Einstellungen
   */
  public PropertyMap getProperties() throws ApiException, RemoteException;

  /**
   * Konfiguriert das Assessment anhand der uebergebenen PropertyMap.
   *
   * @param properties
   *          Die PropertyMap mit den Einstellungen
   */
  public void setProperties(PropertyMap properties) throws ApiException, RemoteException;

}
