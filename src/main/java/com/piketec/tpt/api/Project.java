/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2018 PikeTec GmbH
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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.piketec.tpt.api.Requirement.RequirementType;

/**
 * This object represents a TPT project. It has been either newly created with
 * {@link TptApi#newProject(File)} or opened via {@link TptApi#openProject(File)}.
 * 
 *
 * @author Copyright (c) 2018 Piketec GmbH - MIT License (MIT)
 */
public interface Project extends IdentifiableRemote {

  /**
   * Closes the project represented by this object in the TPT GUI.
   * <p>
   * It discards all changes since the last time the project has been saved. Closed projects cannot
   * be saved anymore.
   * </p>
   * 
   * @return <code>false</code> if the Project could not be closed.
   * @throws ApiException
   *           The project is not open/unknown
   */
  boolean closeProject() throws ApiException, RemoteException;

  /**
   * Saves the project in its assigned file. The file has been specified in one of the following
   * functions:
   * <p>
   * {@link Project#saveAsProject(File)}
   * <p>
   * </p>
   * {@link TptApi#newProject(File)}
   * <p>
   * </p>
   * {@link TptApi#openProject(File)}
   * </p>
   * 
   * @return A list of messages that have occurred while saving the project.
   * @throws ApiException
   *           If the project is not opened or if the file cannot be written.
   */
  List<String> saveProject() throws ApiException, RemoteException;

  /**
   * 
   * Saves the project in the given file. The file can be changed by this method. This function
   * basically represents the "Save as..." menu item.
   * <p>
   * The file extension defines the save format for the file (*.tpt, *.tptz, *.tptprj)
   * </p>
   * 
   * @see Project#saveProject()
   * @see TptApi#newProject(File)
   * @see TptApi#openProject(File)
   *
   * @return A list of messages that have occurred during the save operation.
   * @throws ApiException
   *           If the project is not open or it the project cannot be written to the given file,
   */
  List<String> saveAsProject(File f) throws ApiException, RemoteException;

  /**
   * 
   * Returns the {@link File} that is associated with this TPT project. If no file has been
   * specified so far, it returns <code>null</code>.
   * 
   * @return A TPT file or <code>null</code>
   */
  File getFile() throws ApiException, RemoteException;

  /**
   * @return Returns the set of all {@link ExecutionConfiguration ExecutionConfigurations} defined
   *         in this project.
   */
  RemoteCollection<ExecutionConfiguration> getExecutionConfigurations()
      throws ApiException, RemoteException;

  /**
   * @return Returns the set of all {@link TestSet TestSets} defined in this project.
   */
  RemoteCollection<TestSet> getTestSets() throws ApiException, RemoteException;

  /**
   * @return Returns the set of all {@link PlatformConfiguration PlatformConfigurations} defined for
   *         this project.
   */
  RemoteCollection<PlatformConfiguration> getPlatformConfigurations()
      throws ApiException, RemoteException;

  /**
   * Returns the list all top level <code>Assessments</code> and <code>AssessmentGroups</code> of
   * this project. These are basically those elements that are placed on the highest hierarchy level
   * in the Assessment view.
   * 
   * @return A list of {@link AssessmentOrGroup} representing the top level assessments and
   *         assessment groups.
   * 
   */
  RemoteList<AssessmentOrGroup> getTopLevelAssessments() throws ApiException, RemoteException;

  /**
   * Adds a new requirement to this project.
   * 
   * @param id
   *          The unique ID of the requirement
   * @param module
   *          The module name or <code>null</code>.
   * @param text
   *          The describing requirement text.
   * @param type
   *          The type of the requirement.
   * @return The newly created requirement.
   * @throws ApiException
   *           If a requirement with the same id already exists.
   * @throws RemoteException
   */
  Requirement createRequirement(String id, String module, String text, RequirementType type)
      throws ApiException, RemoteException;

  /**
   * Get the list of all requirements of this project.
   * 
   * @return The list of requirements.
   * @throws ApiException
   * @throws RemoteException
   */
  RemoteList<Requirement> getRequirements() throws ApiException, RemoteException;

  /**
   * Create a new {@link TestSet} with the given name.
   * 
   * @param name
   *          The name of the new test set. <code>Null</code> will be reduced to an empty string.
   * @return The freshly created and empty test set.
   */
  TestSet createTestSet(String name) throws ApiException, RemoteException;

  /**
   * Create a new type. The structure of the type is given by the <code>typeString</code> argument.
   * You can see this string in TPT in the type editor in the "Type String" field at the bottom or
   * in an tptaif export.
   * <p>
   * </p>
   * Patterns:
   * <ul>
   * <li>Array : "primitive_type[]"</li>
   * <li>Curve : "curve[primitive_type1,primitive_type2]"</li>
   * <li>Map : "map[primitive_type1,primitive_type2,primitive_type3]"</li>
   * <li>Matrix : "primitive_type[][]"</li>
   * <li>Struct : "struct[e1:primitive_type1, e2:primitive_type2, e3:primitive_type3, ...]"</li>
   * </ul>
   * 
   * @param nameOrNull
   *          The name of the declared type or <code>null</code> to create an anonymous type.
   * @param typeString
   *          The type definition in the same format as seen in tptaif or in the type editor.
   * @return the new created type object.
   * @throws ApiException
   *           If the given name is not a legal identifier name, a type with the given name already
   *           exists or the given type string could not be parsed.
   * @throws RemoteException
   */
  Type createType(String nameOrNull, String typeString) throws ApiException, RemoteException;

  /**
   * Get a collection of all known types. Anonymous and predefined types are not included. If you
   * call this method multiple times the returned lists will have different hashes and equals will
   * return false when comparing these objects.
   * 
   * @return The collection of all explicitly declared types.
   * @throws ApiException
   * @throws RemoteException
   */
  RemoteCollection<Type> getTypes() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>boolean</code>.
   * 
   * @return The predefined Type <code>boolean</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeBoolean() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>uint8</code>.
   * 
   * @return The predefined Type <code>uint8</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeUInt8() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>int8</code>.
   * 
   * @return The predefined Type <code>int8</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeInt8() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>uint16</code>.
   * 
   * @return The predefined Type <code>uint16</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeUInt16() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>int16</code>.
   * 
   * @return The predefined Type <code>int16</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeInt16() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>uint32</code>.
   * 
   * @return The predefined Type <code>uint32</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeUInt32() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>int32</code>.
   * 
   * @return The predefined Type <code>int32</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeInt32() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>int64</code>.
   * 
   * @return The predefined Type <code>int64</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeInt64() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>float</code>.
   * 
   * @return The predefined Type <code>float</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeFloat() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>double</code>.
   * 
   * @return The predefined Type <code>double</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeDouble() throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>string</code>.
   * 
   * @return The predefined Type <code>string</code>
   * @throws ApiException
   * @throws RemoteException
   */
  Type getTypeString() throws ApiException, RemoteException;

  /**
   * @return The collection of all declarations (assessment variables, channels, constants,
   *         parameters, measurements)
   * @throws ApiException
   * @throws RemoteException
   */
  RemoteCollection<Declaration> getDeclarations() throws ApiException, RemoteException;

  /**
   * Creates a new channel with the given name.
   * 
   * @param name
   *          The name of the channel
   * @return The new created channel
   * @throws ApiException
   *           If the given name is not a legal identifier name or a declaration with the given name
   *           already exists.
   * @throws RemoteException
   */
  Channel createChannel(String name) throws ApiException, RemoteException;

  /**
   * Creates a new parameter with the given name. The default exchange mode is "exchange".
   * 
   * @param name
   *          The name of the parameter
   * @return The new created parameter
   * @throws ApiException
   *           If the given name is not a legal identifier name or a declaration with the given name
   *           already exists.
   * @throws RemoteException
   */
  Parameter createParameter(String name) throws ApiException, RemoteException;

  /**
   * Creates a new constant with the given name.
   * 
   * @param name
   *          The name of the constant
   * @return The new created constant
   * @throws ApiException
   *           If the given name is not a legal identifier name or a declaration with the given name
   *           already exists.
   * @throws RemoteException
   */
  Constant createConstant(String name) throws ApiException, RemoteException;

  /**
   * Creates a new measurement with the given name.
   * 
   * @param name
   *          The name of the measurement
   * @return The new created measurement
   * @throws ApiException
   *           If the given name is not a legal identifier name or a declaration with the given name
   *           already exists.
   * @throws RemoteException
   */
  Measurement createMeasurement(String name) throws ApiException, RemoteException;

  /**
   * Creates a new assessment variable with the given name.
   * 
   * @param name
   *          The name of the assessment variable
   * @return The new created assessment variable
   * @throws ApiException
   *           If the given name is not a legal identifier name or a declaration with the given name
   *           already exists.
   * @throws RemoteException
   */
  AssessmentVariable createAssessmentVariable(String name) throws ApiException, RemoteException;

  /**
   * Create a new {@link ExecutionConfiguration} with the given name.
   * 
   * @param name
   *          The name of the new Execution Configuration. <code>Null</code> will be reduced to an
   *          empty string.
   * @return The object representing the new Execution Configuration
   * 
   */
  ExecutionConfiguration createExecutionConfiguration(String name)
      throws ApiException, RemoteException;

  /**
   * Create a new {@link PlatformConfiguration} with the given name and the given type.
   * 
   * @param name
   *          The name for the new PlatformConfiguration. <code>Null</code> will be reduced to an
   *          empty string.
   * @param type
   *          A <code>String</code> representing the platform type.
   * 
   * @return Returns an object representing the new <code>PlatformConfiguration</code>.
   * 
   * @throws ApiException
   *           if <code>type==null</code> or <code>type</code> is unknown.
   */

  PlatformConfiguration createPlatformConfiguration(String name, String type)
      throws ApiException, RemoteException;

  /**
   * 
   * Create a {@link AssessmentGroup} with the given name and within the given parent
   * <code>AssessmentGroup</code>.
   * <p>
   * If <code>groupOrNull==null</code>, the newly created <code>AssessmentGroup</code> automatically
   * becomes a top level item.
   * </p>
   * 
   * @param name
   *          The name for the new <code>AssessmentGroup</code>. <code>Null</code> will be reduced
   *          to an empty string.
   * @param groupOrNull
   *          Either the parent <code>AssessmentGroup</code> or <code>null</code>
   * @return An object representing the newly created <code>AssessmentGroup</code>
   * 
   */
  AssessmentGroup createAssessmentGroup(String name, AssessmentGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * 
   * Creates a new <code>Assessment</code> with the given name and the given type below the given
   * <code>AssessmentGroup</code>. If <code>groupOrNull==null</code>, the newly created
   * <code>Assessment</code> automatically becomes a top level assessment
   * 
   * @param name
   *          The name of the new <code>Assessment</code>. <code>Null</code> will be reduced to an
   *          empty string.
   * @param type
   *          A <code>String</code> representing the assessment type. Constants for available types
   *          can be found in {@link Assessment}.
   * @param groupOrNull
   *          The parent group for the new <code>Assessment</code> or <code>null</code>
   * @return The object representing the newly created <code>Assessment</code>
   * 
   * @throws ApiException
   *           <code>type==null</code>
   */
  Assessment createAssessment(String name, String type, AssessmentGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * This function returns the top level testlet of a TPT project for which all other testlets are
   * child nodes.
   * <p>
   * In the project tree, this testlet is represented by the project node. Its variants are test
   * cases.
   * </p>
   * 
   * @return The top level {@link Testlet} for this project.
   * 
   */
  Testlet getTopLevelTestlet() throws ApiException, RemoteException;

  /**
   * Returns the list of declared {@link TestCaseAttribute TestCaseAttributes} for this project.
   * 
   * @return A list of TestCaseAttributes.
   * @throws ApiException
   * @throws RemoteException
   */
  RemoteCollection<TestCaseAttribute> getTestCaseAttributes() throws ApiException, RemoteException;

  /**
   * Create a new {@link TestCaseAttribute}
   * 
   * @see TestCaseAttribute#getType()
   * 
   * @param name
   *          The name for the new attribute.
   * @param type
   *          The type of the attribute as String
   * @return An newly created {@link TestCaseAttribute}
   * @throws ApiException
   *           If there already exists a {@link TestCaseAttribute} with the given name or
   *           <code>name==null</code> or <code>type==null</code> or if the given <code>type</code>
   *           is unknown.
   * @throws RemoteException
   */
  TestCaseAttribute createTestCaseAttribute(String name, String type)
      throws ApiException, RemoteException;

  /**
   * Returns an entry point for an API extension of the given type. The Return value needs to be
   * cast accordingly. Returns <code>null</code>, if no extension was registered for
   * <code>key</code>.
   * 
   * @param key
   *          Name of the extension
   * @return <code>null</code>, if no extension could have been found with the given
   *         <code>key</code>. Otherwise, an object representing the entry point.
   * 
   * @throws RemoteException
   * @throws ApiException
   *           If an extension has been found but is not available for the current TPT instance
   *           (e.g. missing license option).
   */
  Remote getExtensionOrNull(String key) throws RemoteException, ApiException;

  /**
   * @return Returns a list of the {@link Mapping Mappings} that are defined for this project.
   */
  RemoteList<Mapping> getMappings() throws RemoteException, ApiException;

  /**
   * Create a new {@link Mapping} with the <code>given</code> name. If the <code>name</code> already
   * exists, a suffix (_00&lt;n&gt;) is added to the name of the new mapping.
   * 
   * @param name
   *          The intended <code>name</code> for the new mapping. <code>Null</code> will be reduced
   *          to an empty string.
   * @return An object representing the new {@link Mapping}
   */
  Mapping createMapping(String name) throws RemoteException, ApiException;

  /**
   * Imports existing test data as step lists to TPT. This corresponds to "Tools | Generate Test
   * Cases from Test Data". This function could either try to update an already existing set of
   * imported test data or create a new set.
   * 
   * 
   * @param scenarioGroup
   *          A variant group where the imported test data should be inserted.
   * @param dir
   *          The directory where the test data resides.
   * @param filePatternOrNull
   *          A regular expression to constrain the set of files that are imported to TPT. If the
   *          expression is <code>null</code> or empty, all available files will be imported.
   * @param includeSubdirs
   *          Enable/disable the traversal of sub-directories of <code>dir</code> for further data
   *          to import.
   * @param channelPatternOrNull
   *          Provide a regular expression to define a mapping between channels in TPT and signal
   *          names in the file. The placeholder ${CHANNEL} can be used to refer to a arbitrary
   *          channel name in TPT: the expression <code>"prefix_${CHANNEL}_postfix"</code> would
   *          match a TPT channel <code>"mysignal"</code> to the signal
   *          <code>"prefix_mysignal_postfix"</code> in the file.
   * @param linkSignals
   *          Enable or disable the linking of test data files. If set to <code>false</code>, test
   *          date will be imported as Embedded Signal Step, instead.
   * @param renameMappingNameOrNull
   *          The name of a existing mapping with a rename flavor, that should be used to map the
   *          channels in TPT to the signal in the file.
   * @param createAssesslets
   *          Enable/Disable the automatic creation of Signal Comparison Assesslets for the input
   *          channels in the test data.
   * @param timeTolOrNull
   *          Specify the time tolerance for the Signal Comparison Assesslets. <code>null</code>
   *          means none.
   * @param valueTolOrNull
   *          Specify the value tolerance for the Signal Comparison Assesslets. <code>null</code>
   *          means none.
   * @param createLocalReferenceChannels
   *          Create local signals for the reference channels of the Signal comparison from the
   *          channels of the file to have the reference data available for embedded signals, too.
   * @param addTerminationCondition
   *          Add a wait stept that terminates the variant when the test data has been fully
   *          replayed.
   * @param assignParameters
   *          Enable the assignment of parameter values to test cases, if those are present in the
   *          test data file and a respective mapping flavor is present.
   * @param updateExistingGeneratedScenarios
   *          If this argument is set to <code>true</code>, TPT tries to find an older import to
   *          update with the new data. If it finds an older import, TPT updates already existing
   *          test cases, adds missing test cases, and removes such test cases, that do not have
   *          corresponding test data anymore.
   *          <p>
   *          </p>
   *          A older updateable import exists, if the testlet for the provided
   *          <code>scenarioGroup</code> contains exactly one child group that matches the name
   *          scheme <code>"Import DD.MM.YY HH:MM:SS - RootDirName"</code> and there exists a test
   *          case group of the same name.
   * @return Returns a <code>String</code> containing the error and warning messages occurred during
   *         the import.
   * @throws ApiException
   *           <ul>
   *           <li>If <code>dir</code> cannot be read.</li>
   *           <li>If no mapping with the name <code>renameMappingNameOrNull</code> can be found or
   *           the Mapping does not have a Rename Flavor.
   *           <li>If <code>createAssesslets == true</code>, but are no TPT-Input-Channels for which
   *           a Signal Comparison Assesslet could be created.</li>
   *           <li>If an error occurs during the import.</li>
   *           </ul>
   * @throws RemoteException
   */
  String generateTestCasesFromTestData(ScenarioGroup scenarioGroup, File dir,
                                       String filePatternOrNull, boolean includeSubdirs,
                                       String channelPatternOrNull, boolean linkSignals,
                                       String renameMappingNameOrNull, boolean createAssesslets,
                                       String timeTolOrNull, String valueTolOrNull,
                                       boolean createLocalReferenceChannels,
                                       boolean addTerminationCondition, boolean assignParameters,
                                       boolean updateExistingGeneratedScenarios)
      throws ApiException, RemoteException;

  /**
   * Imports declarations from the given file like the option available in the declaration editor.
   * Supported are tptaif, xml and xlsx files.
   * 
   * @param f
   *          The interface file containing the declarations to import.
   * @param mappingOrNull
   *          The mapping where mapping information shall be imported or <code>null</code> if a new
   *          mapping should be created if needed.
   * @return A list of warnings that occurred during import.
   * @throws ApiException
   *           If an error occurs during import or the file format is not supported.
   * @throws RemoteException
   */
  List<String> importIO(File f, Mapping mappingOrNull) throws ApiException, RemoteException;

  /**
   * 
   * @return is this ProjectFile created with the current TPT-version? Will return
   *         <code>false</code> for a new unsaved project.
   */
  boolean createdWithCurrentVersion() throws ApiException, RemoteException;

  /**
   * 
   * @return Version number of loaded tpt-project-file. For a new project this will return -1.
   */
  int getCreatedWithFileFormatVersion() throws ApiException, RemoteException;

  /**
   * 
   * @return TPT version name of the loaded tpt-project-file. For a new project this will return
   *         <code>null</code>.
   */
  String getCreatedWithTptVersionName() throws ApiException, RemoteException;

  /**
   * 
   * @param f
   *          {@link File} to be imported.
   * @return {@link List} with warnings occurred during import.
   */
  List<String> importEquivalenceClasses(File f) throws ApiException, RemoteException;

  /**
   * 
   * @param f
   *          target-{@link File} for export.
   * @return {@link List} with warnings occurred during import.
   */
  List<String> exportEquivalenceClasses(File f) throws ApiException, RemoteException;

  /**
   * This method provides the functionality to generate {@link Scenario test cases} from equivalence
   * classes.
   * 
   * @param scenarioOrGroup
   *          where generated {@link Scenario test cases} should be added.
   * @param settings
   *          for generation of {@link Scenario test cases} from equivalence classes.
   * @param equivalenceClassMap
   *          selected equivalence classes.
   * @return A {@link List List<String>} with all warnings as a log.
   */
  List<String> generateTestCasesFromEquivalenceClasses(ScenarioOrGroup scenarioOrGroup,
                                                       GenerateTestCasesFromEquivalenceClassessSettings settings,
                                                       Map<Declaration, Collection<String>> equivalenceClassMap)
      throws ApiException, RemoteException;

  /**
   * Get the default report section.
   * 
   * @return The default report section
   * @throws ApiException
   * @throws RemoteException
   */
  Assessment getDefaultReportSection() throws ApiException, RemoteException;

  /**
   * Set the default report section. If the given argument is <code>null</code> the top level report
   * section is set as the default.
   * 
   * @param reportSection
   *          The report section to be set.
   * @throws ApiException
   *           if the report section is not part of the project or if it is not a report section
   *           assesslet.
   * @throws RemoteException
   */
  void setDefaultReportSection(Assessment reportSection) throws ApiException, RemoteException;
}
