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

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * Alle Einstellungen fuer ein Ausfuehrungskonfuguration. Eine ExecutionConfiguration kann ueber
 * {@link Project#createExecutionConfiguration(String)} angelegt werden. Die Konfiguration besteht
 * aus mehreren Attributen sowie einer Liste der {@link ExecutionConfigurationItem} Die zur
 * Verfuegung stehenden Attribute entsprechen den Attributen, die ueber die GUI verstellt werden
 * koennen. Die Beschreibung kann aus dem allgemeinen Users Guide entnommen werden.
 */
@SuppressWarnings("unused")
public interface ExecutionConfiguration extends Remote, IdentifiableRemote, NamedObject,
    RemoteList<ExecutionConfigurationItem> {

  /**
   * Das Ausgabeformat des Reports.
   * 
   * @author Copyright (c) 2014 Piketec GmbH - All rights reserved.
   */
  public enum ReportFormat {
    Html, Pdf, AllInOnePdf, MHtml
  }

  /**
   * Die Struktur des Datenverzeichnisses
   * 
   * @author Copyright (c) 2014 Piketec GmbH - All rights reserved.
   */
  public enum DataDirStructure {
    HIERARCHICAL_WITH_INDEX, HIERARCHICAL_WITH_ID, FLAT_WITH_INDEX, FLAT_WITH_ID, FLAT_ONLY_ID
  }

  /**
   * Erzeugt eine neue {@link ExecutionConfigurationItem} und fuegt diese an das Ende der Liste der
   * Items an.
   * 
   * @return Das neuerzeugte ExecutionConfigurationItem
   * 
   */
  public ExecutionConfigurationItem createExecutionConfigurationItem() throws ApiException,
      RemoteException;

  /**
   * @return Das Datenverzeichnis
   */
  public File getDataDir() throws ApiException, RemoteException;

  /**
   * @return Das Verzeichnis fuer den Report
   */
  public File getReportDir() throws ApiException, RemoteException;

  /**
   * Setzt das Arbeitsverzeichnis.
   * 
   * @param f
   *          Das Datenverzecihnis
   */
  public void setDataDir(File f) throws ApiException, RemoteException;

  /**
   * Kann wie in der GUI leer <code>null</code> gesetzt werden. In diesem Fall wird das DataDir
   * verwendet.
   * 
   * @param f
   *          Das Datenverzeichnis oder <code>null</code>
   */
  public void setReportDir(File f) throws ApiException, RemoteException;

  /**
   * @return ob die Tests ausgefuehrt werden sollen
   */
  public boolean isRunExec() throws ApiException, RemoteException;

  /**
   * @return ob die Assessments ausgefuehrt werden sollen.
   */
  public boolean isRunAssess() throws ApiException, RemoteException;

  /**
   * @return ob ein Report generiert werden soll
   */
  public boolean isRunReport() throws ApiException, RemoteException;

  /**
   * @return ob die Ausfuehrung im Debug-Modus stattfinden soll
   */
  public boolean isRunDebug() throws ApiException, RemoteException;

  /**
   * @return ob das Dashboard benutz werden soll
   */
  public boolean isRunDashboard() throws ApiException, RemoteException;

  /**
   * @param enabled
   *          ob die Testst ausgefuehrt werden soll
   */
  public void setRunExec(boolean enabled) throws ApiException, RemoteException;

  /**
   * @param enabled
   *          ob die Assessments ausgefuehrt werden sollen.
   */
  public void setRunAssess(boolean enabled) throws ApiException, RemoteException;

  /**
   * @param enabled
   *          ob ein Report generiert werden soll
   */
  public void setRunReport(boolean enabled) throws ApiException, RemoteException;

  /**
   * @param enabled
   *          ob die Ausfuehrung im Debug-Modus stattfinden soll
   */
  public void setRunDebug(boolean enabled) throws ApiException, RemoteException;

  /**
   * @param enabled
   *          ob das Dashboard benutz werden soll
   */
  public void setRunDashboard(boolean enabled) throws ApiException, RemoteException;

  /**
   * Die zusaetzlichen Userattribute einer ExecutionConfiguration.
   * 
   * @return Die Zurodnung von Attributen und ihrem Wert
   *
   */
  public Map<String, String> getAttributes() throws ApiException, RemoteException;

  /**
   * Setzt das entsprechende Userattribut oder loescht das Attribut (wenn value==null)
   * 
   * @param key
   *          Der Name des Attributs
   * @param value
   *          Der neue Wert, <code>null</code> zum loeschen.
   * 
   * @throws ApiException
   *           wenn <code>name==null</code>
   *
   */
  public void setAttributes(String key, String value) throws ApiException, RemoteException;

  /**
   * @return das aktuell eingestellte Reportformat
   * 
   * @throws ApiException
   *           wenn das eingestellte Reportformat der API nicht bekannt ist.
   */
  public ReportFormat getReportFormat() throws ApiException, RemoteException;

  /**
   * Setzt das ReportFormat
   * 
   * @param rf
   *          Das neue Reportformat
   * @throws ApiException
   *           Wenn die API das angebene Format keinem Format in TPT zuordnen kann.
   */
  public void setReportFormat(ReportFormat rf) throws ApiException, RemoteException;

  /**
   * @return die aktuell eingestellte Datenverzeichnis-Struktur
   * @throws ApiException
   *           wenn die eingestellte Datenverzeichnis-Struktur der API nicht bekannt ist.
   */
  public DataDirStructure getDataDirStructure() throws ApiException, RemoteException;

  /**
   * Setzt die Struktur des Datenverzeichnisses.
   * 
   * @param dds
   *          Die neue Datenverzeichnis-Struktur.
   * @throws ApiException
   *           Wenn die API die angebene Struktur keiner Struktur in TPT zuordnen kann.
   */
  public void setDataDirStructure(DataDirStructure dds) throws ApiException, RemoteException;

  /**
   * @return <code>null</code> wenn kein Pack-Report eingestellt ist, die Zieldatei sonst.
   *
   */

  public File getReportPackFile() throws ApiException, RemoteException;

  /**
   * Setzt das ZIP-File in das der Report gepackt werden soll oder stellt das packen aus, wenn
   * <code>zipFile==null</code>.
   *
   * @param zipFile
   *          die Zieldatei fuer den gepackten Report oder <code>null</code>.
   *
   */
  public void setReportPackFile(File zipFile) throws ApiException, RemoteException;

  /**
   * @return ob der ungepackte Report nach dem packen geloescht werden soll.
   */
  public boolean isDeleteReportDirAfterPack() throws ApiException, RemoteException;

  /**
   * @param enable
   *          ob der ungepackte Report nach dem packen geloescht werden soll
   */
  public void setDeleteReportDirAfterPack(boolean enable) throws ApiException, RemoteException;

}
