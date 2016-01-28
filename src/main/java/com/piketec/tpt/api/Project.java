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

import java.io.File;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Ein Projekt ist ein geoeffnetes oder neues TPT-File.
 */
public interface Project extends IdentifiableRemote {

  /**
   * Schliesst dieses Projekt in TPT.
   *
   * @return false if the Project could not be closed
   * @throws ApiException
   *           The project is not open/unknown
   */
  public boolean closeProject() throws ApiException, RemoteException;

  /**
   * Speichert dieses Projekt unter der ihm zugeordneten Datei.
   * 
   * @see Project#saveAsProject(File)
   * @see TptApi#newProject(File)
   * @see TptApi#openProject(File)
   *
   * @return Die Liste der Log-Messages vom Speichern
   * @throws ApiException
   *           Wenn das Projekt nicht geoeffnet ist oder es nicht in die Datei geschrieben werden
   *           konnte.
   */
  public List<String> saveProject() throws ApiException, RemoteException;

  /**
   * 
   * Speichert das Projekt unter der angegeben Datei. Der Dateiname des Projekts wird durch diese
   * Methode neu gesetzt.
   * 
   * @see Project#saveProject()
   * @see TptApi#newProject(File)
   * @see TptApi#openProject(File)
   *
   * @return Die Liste der Log-Messages vom Speichern
   * @throws ApiException
   *           Wenn das Projekt nicht geoeffnet ist oder es nicht in die Datei geschrieben werden
   *           konnte.
   */
  public List<String> saveAsProject(File f) throws ApiException, RemoteException;

  /**
   * 
   * Liefert die Datei zurueck, unter der das Projekt gespeichert werden wird. Kann
   * <code>null</code> sein, wenn noch keine Datei angegeben ist.
   * 
   * @return Die TPT-Datei oder <code>null</code>
   */
  public File getFile() throws ApiException, RemoteException;

  /**
   * @return Die Menge aller <code>ExecutionConfigurations</code> von diesem Projekt.
   */
  public RemoteCollection<ExecutionConfiguration> getExecutionConfigurations() throws ApiException,
      RemoteException;

  /**
   * @return Die Menge aller <code>TestSets</code> von diesem Projekt.
   */
  public RemoteCollection<TestSet> getTestSets() throws ApiException, RemoteException;

  /**
   * @return Die Menge aller <code>PlatformConfigurations</code> von diesem Projekt.
   */
  public RemoteCollection<PlatformConfiguration> getPlatformConfigurations() throws ApiException,
      RemoteException;

  /**
   * @return Die Menge aller <code>Assessments</code> und <code>AssessmentGroups</code> von diesem
   *         Projekt, die direkt unter diesem Projekt haengen.
   */
  public RemoteList<AssessmentOrGroup> getTopLevelAssessments() throws ApiException,
      RemoteException;

  /**
   * Erstellt ein neues <code>TestSet</code> mit dem angegeben Namen.
   * 
   * @param name
   *          Der Name des neuen TestSets.
   * @return Das neuerstellte TestSet
   */
  public TestSet createTestSet(String name) throws ApiException, RemoteException;

  /**
   * Erstellt ein neues <code>ExecutionConfiguration</code> mit dem angegeben Namen.
   * 
   * @param name
   *          Der Name des neuen ExecutionConfiguration.
   * @return Das neuerstellte ExecutionConfiguration
   */
  public ExecutionConfiguration createExecutionConfiguration(String name) throws ApiException,
      RemoteException;

  /**
   * Erstellt ein neues <code>PlatformConfiguration</code> mit dem angegeben Namen.
   * 
   * @param name
   *          Der Name des neuen PlatformConfiguration.
   * @return Das neuerstellte PlatformConfiguration
   */
  public PlatformConfiguration createPlatformConfiguration(String name, String type)
      throws ApiException, RemoteException;

  /**
   * 
   * Erzeugt eine neue <code>AssessmentGroup</code> mit dem angegeben Namen unter der angegeben
   * <code>AssessmentGroup</code>. Ist <code>gorupOrNull==null</code> wird die neuerstellte Gruppe
   * eine ToplevelGruppe.
   * 
   * @param name
   *          Der Name der <code>AssessmentGroup</code>
   * @param groupOrNull
   *          Der Parent der neuen <code>AssessmentGroup</code> oder <code>null</code>
   * @return Die neuerstellte <code>AssessmentGroup</code>
   */
  public AssessmentGroup createAssessmentGroup(String name, AssessmentGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * 
   * Erzeugt eine neue <code>Assessment</code> mit dem angegeben Namen unter der angegeben
   * <code>AssessmentGroup</code>. Ist <code>gorupOrNull==null</code> wird das neuerstellte
   * <code>Assessment</code> ein Toplevle-Assessment.
   * 
   * @param name
   *          Der Name des <code>Assessment</code>
   * @param groupOrNull
   *          Der Parent des neuen <code>AssessmentAssessmentGroup</code> oder <code>null</code>
   * @return Das neuerstellte <code>Assessment</code>
   */
  public Assessment createAssessment(String name, String type, AssessmentGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * @return Das oberste Testlet, das keinem anderen Testlet unetrgordnet ist. <code>null</code>,
   *         wenn kein Content gesetzt ist.
   */
  public Testlet< ? extends Scenario> getTopLevelTestlet() throws ApiException, RemoteException;
}
