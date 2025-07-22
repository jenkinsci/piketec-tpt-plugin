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

import java.rmi.RemoteException;
import java.util.Set;

import com.piketec.tpt.api.properties.Property;
import com.piketec.tpt.api.properties.PropertyMap;
import com.piketec.tpt.api.util.DeprecatedAndRemovedException;

/**
 * This class represents an assessment. The particular properties of the different assessment types
 * are mapped to generic properties (see {@link PropertyMap}).
 *
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface Assessment extends AssessmentOrGroup {

  /**
   * Type String for Check Log Entries Assesslet
   */
  public static final String CHECK_LOG_ENTRIES_TYPE = "CheckLogEntriesAssessletType";

  /**
   * Type String for Condition Tree Assesslet
   */
  public static final String CONDITION_TREE_TYPE = "ConditionTreeAssessletType";

  /**
   * Type String for Equivalence Classes Assesslet
   */
  public static final String EQUIVALENCE_CLASSES_TYPE = "EquivalenceClassesAssessletType";

  /**
   * Type String for Equivalence Classes Assesslet
   */
  public static final String FORMAL_REQUIREMENTS_TYPE = "FormalRequirmentsAssessletType";

  /**
   * Type String for Import Measurements Assesslet
   */
  public static final String IMPORT_MEASUREMENTS_TYPE = "ImportMeasurementsAssessletType";

  /**
   * Type String for Matlab-Script Assesslet
   */
  public static final String MATLAB_SCRIPT_TYPE = "MscriptAssessletType";

  /**
   * Type String for Min/Max Comparison Assesslet
   */
  public static final String MIN_MAX_COMPARISON_TYPE = "MinMaxAssessletType";

  /**
   * Type String for Report Assesslet Summary Table
   */
  public static final String REPORT_ASSESSLET_SUMMARY_TABLE_TYPE =
      "AssessletSummaryReportAssessletType";

  /**
   * Type String for Report Image Assesslet
   */
  public static final String REPORT_IMAGE_TYPE = "ImageReportAssessletType";

  /**
   * Type String for Report Meta Information Assesslet
   */
  public static final String REPORT_META_INFORMATION_TYPE = "MetaInformationReportAssessletType";

  /**
   * Type String for Report Paragraph Assesslet
   */
  public static final String REPORT_PARAGRAPH_TYPE = "ParagraphReportAssessletType";

  /**
   * Type String for Report Section Assesslet
   */
  public static final String REPORT_SECTION_TYPE = "SectionReportAssessletType";

  /**
   * Type String for Report Signal Graphic Assesslet
   */
  public static final String REPORT_SIGNAL_GRAPHIC_TYPE = "SignalGraphicReportAssessletType";

  /**
   * Type String for Report Signal Table Assesslet
   */
  public static final String REPORT_SIGNAL_TABLE_TYPE = "SignalTableReportAssessletType";

  /**
   * Type String for Report Table of Contents Assesslet
   */
  public static final String REPORT_TABLE_OF_CONTENTS_TYPE = "TableOfContentsReportAssessletType";

  /**
   * Type String for Report Table of Figures Assesslet
   */
  public static final String REPORT_TABLE_OF_FIGURES_TYPE = "TableOfFiguresReportAssessletType";

  /**
   * Type String for Script Assesslet
   */
  public static final String SCRIPT_TYPE = "ScriptAssessletType";

  /**
   * Type String for Sequence Check Assesslet
   */
  public static final String SEQUENCE_CHECK_TYPE = "SequenceCheckAssessletType";

  /**
   * Type String for Signal Comparison Assesslet
   */
  public static final String SIGNAL_COMPARISON_TYPE = "SignalComparisonAssessletType";

  /**
   * Type String for Step List Assesslet
   */
  public static final String TEST_STEP_LIST_ASSESSMENTS_TYPE = "StepListAssessments";

  /**
   * Type String for Timeout Assesslet
   */
  public static final String TIMEOUT_TYPE = "timeout";

  /**
   * Type String for Trigger Rule Assesslet
   */
  public static final String TRIGGER_RULE_TYPE = "TriggerRuleType";

  /**
   * Type String for Variable Definitions Assesslet
   */
  public static final String VARIABLE_DEFINITIONS_TYPE = "VarDefsType";

  /**
   * Type String for Global Script Assesslet
   */
  public static final String GLOBAL_SCRIPT_TYPE = "GlobalType";

  /**
   * Type String for Global Coverage Assesslet
   */
  public static final String GLOBAL_COVERAGE_TYPE = "GlobalCoverageType";

  /**
   * Type String for Global Variable Assesslet
   */
  public static final String GLOBAL_VARIABLE_TYPE = "GlobalVariableType";

  /**
   * Type String for Global Equivalence Classes Assesslet
   */
  public static final String GLOBAL_EQUIVALENCE_CLASSES_TYPE = "GlobalEquivalenceClassesType";

  /**
   * Type String for Requirements Coverage Assesslet
   */
  public static final String REQUIREMENTS_COVERAGE_TYPE = "rmassesslet";

  /**
   * @return The name of the assessment type.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getType() throws RemoteException;

  /**
   * @return The value of the description field in the assessment header.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDescription() throws RemoteException;

  /**
   * @return A comma separated list of the used namespaces for the assessment.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if assessment does not support using
   */
  public String getUsing() throws ApiException, RemoteException;

  /**
   * Set the used namespace(s) for the assessment. For more namespaces provide a comma separated
   * list.
   * 
   * @param using
   *          The new namespace(s) that shall be used.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if assessment does not support using
   */
  public void setUsing(String using) throws ApiException, RemoteException;

  /**
   * Set the value for the description field of the assessment header. <code>Null</code> will be
   * reduced to an empty string.
   * 
   * @param description
   *          The new description of the assessment.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDescription(String description) throws RemoteException;

  /**
   * Returns a list of variants, for which the assessment is enabled. Returns an empty list, if the
   * assessment is enabled for all variants.
   *
   * @return A list of {@link Scenario variants}.
   * 
   * @throws RemoteException
   *           remote communication problem
   *
   * @deprecated Use {@link #getSelectedVariantsOrGroups()}. Removed in TPT-19. Throws
   *             {@link DeprecatedAndRemovedException}
   */
  @Deprecated
  public RemoteCollection<Scenario> getSelectedVariants() throws RemoteException;

  /**
   * Returns a list of variants and variant groups, for which the assessment is enabled. Returns an
   * empty list, if the assessment is enabled for all variants. If a group is contained in the set
   * all descendant scenarios in this group are contained in the set as well. Removing an element
   * will remove parent elements as well to retain this constraint. Please note that removing a
   * group will only disable the group, but leave all its subelements enabled.
   *
   * @return A set of {@link ScenarioOrGroup variants}.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if assessment does not support enabling for {@link ScenarioOrGroup variants}
   */
  public RemoteCollection<ScenarioOrGroup> getSelectedVariantsOrGroups() throws RemoteException;

  /**
   * Enables the assessment for a variant or variant group. To disable, use
   * {@link #getSelectedVariantsOrGroups()} and remove the desired objects.<br>
   * Please note that using this method to enable a group will also recursively enable all its
   * subelements. Removing a group however will only disable the group, but leave all its
   * subelements enabled.
   * 
   * @param sog
   *          The variant / variant group ({@link ScenarioOrGroup ScenarioOrGroup}) for which the
   *          assesslet should be enabled.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if assessment does not support enabling for {@link ScenarioOrGroup variants}
   */
  public void enableForVariant(ScenarioOrGroup sog) throws RemoteException;

  /**
   * Returns a list of test cases for which the assessment is enabled.
   * 
   * @return A list of {@link Scenario test cases}
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Use {@link #getSelectedTestCasesOrGroups()}. Removed in TPT-19. Throws
   *             {@link DeprecatedAndRemovedException}
   */
  @Deprecated
  public RemoteCollection<Scenario> getSelectedTestCases() throws RemoteException;

  /**
   * Returns a list of test cases and test case groups, for which the assessment is enabled. If a
   * group is contained in the set all descendant scenarios in this group are contained in the set
   * as well. Removing an element will remove parent elements as well to retain this constraint.
   * Please note that removing a group will only disable the group, but leave all its subelements
   * enabled.
   * 
   * @return A set of {@link ScenarioOrGroup test cases}.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if assessment does not support enabling for {@link ScenarioOrGroup test cases}
   * 
   */
  public RemoteCollection<ScenarioOrGroup> getSelectedTestCasesOrGroups() throws RemoteException;

  /**
   * Enables the assessment for a test case or test case group. To disable, use
   * {@link #getSelectedTestCasesOrGroups()} and remove the desired objects.<br>
   * Please note that using this method to enable a group will also recursively enable all its
   * subelements. Removing a group however will only disable the group, but leave all its
   * subelements enabled.
   * 
   * @param sog
   *          A test case or test case group ({ScenarioOrGroup ScenarioOrGroup}), the test case
   *          should be enabled for.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if assessment does not support enabling for {@link ScenarioOrGroup test cases}
   */
  public void enableForTestCase(ScenarioOrGroup sog) throws RemoteException;

  /**
   * Returns a list of platform configurations, execution configurations and execution configuration
   * items for which the assessment is enabled. Returns an empty list, if the
   * <code>Assessment</code> is enabled for all platforms and all execution configuration items.
   *
   * @return A list of platform configurations, execution configurations and execution configuration
   *         items ({PlatformOrExecutionItemEnabler PlatformOrExecutionItemEnabler})
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<PlatformOrExecutionItemEnabler> getEnabledPlatformConfigurationsOrExecutionConfigurationItems()
      throws RemoteException;

  /**
   * Enables the assessment for a particular {PlatformConfiguration PlatformConfiguration}.
   *
   * @param pc
   *          The {@link PlatformConfiguration}, for which the assessment should be enabled.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if assessment does not support enabling for {@link PlatformConfiguration
   *           PlatformConfigurations}
   */
  public void enableForPlatformConfiguration(PlatformConfiguration pc) throws RemoteException;

  /**
   * Enables the assessment for a particular a {ExecutionConfigurationItem
   * ExecutionConfigurationItem}.
   *
   * @param execItem
   *          The {@link ExecutionConfigurationItem}, the assessment should be enabled for.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if assessment does not support enabling for {@link ExecutionConfigurationItem
   *           ExecutionConfigurationItems}
   */
  public void enableForExecutionConfigurationItem(ExecutionConfigurationItem execItem)
      throws RemoteException;

  /**
   * Enables the assessment for a particular a {ExecutionConfiguration ExecutionConfiguration}.
   *
   * @param ec
   *          The <code>ExecutionConfiguration</code>, the assessment should be enabled for.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if assessment does not support enabling for {@link ExecutionConfiguration
   *           ExecutionConfigurations}
   */
  public void enableForExecutionConfiguration(ExecutionConfiguration ec) throws RemoteException;

  /**
   * Returns the properties of the assessment as {@link PropertyMap}.
   * <p>
   * A <code>PropertyMap</code> maps the properties as follows: {@link String} &rarr;
   * {@link Property}. A <code>Property</code> is either a <code>PropertyMap</code> or a
   * <code>String</code> value.
   * </p>
   * The structure of the PropertyMap depends on the type of the assessment.
   * 
   * @return A {properties.PropertyMap PropertyMap} with the settings for the assessments.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public PropertyMap getProperties() throws RemoteException;

  /**
   * <p>
   * Configures the assessment with the provided PropertyMap.
   * </p>
   * <p>
   * To remove a removable property, e.g. a report section, set it to <code>null</code>.
   * </p>
   * It is <b>strongly recommended</b> to modify the PropertyMap returned by
   * {Assessment#getProperties() getProperties()}
   *
   * @param properties
   *          A {properties.PropertyMap PropertyMap} containing the settings.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setProperties(PropertyMap properties) throws RemoteException;

  /**
   * Returns all active test cases with respect to the variant context.
   * 
   * @return set of active test cases
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if assessment cannot be activated for {@link Scenario test cases}
   */
  public Set<Scenario> getActiveTestCases() throws RemoteException;

  /**
   * Get all requirements currently linked to this assesslet. The content of the collection is only
   * a snapshot of the current state but removing items from this list will remove the link in TPT
   * anyway even if the link was created after receiving this collection.
   * 
   * @return The currently linked requirements.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteCollection<Requirement> getLinkedRequirements() throws RemoteException;

  /**
   * Returns <code>null</code> if assessment can be compiled without errors, the compile error
   * message otherwise.
   * 
   * @return The compile error message or <code>null</code>.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getCompileError() throws RemoteException;

  /**
   * Returns the current status of the assessment.
   * 
   * @return The current status or <code>null</code> if status is "new".
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Status getCurrentStatus() throws RemoteException;

  /**
   * Returns a list of all status history entries of the assessment, the newest comes first.
   * 
   * @return A list of all {@link Status Statuses}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<Status> getStatusHistory() throws RemoteException;

  /**
   * Returns <code>true</code> if this assessment has been modified since the last status history
   * entry or if it has no revision. Otherwise, returns <code>false</code>.
   *
   * @return always <code>false</code> since revision is deprecated.
   * @throws RemoteException
   *           remote communication problem
   * @deprecated Will be removed in Z-2027.03. feature is canceled without substitution.
   */
  @Deprecated
  public boolean checkForNewRevision() throws RemoteException;

  /**
   * Returns <code>true</code> if this assessment is marked as "modified"(=outdated) or has no
   * revision. Otherwise, returns <code>false</code>.
   *
   * @return always <code>false</code> since revision is deprecated.
   * @throws RemoteException
   *           remote communication problem
   * @deprecated Will be removed in Z-2027.03. Feature is canceled without substitution
   */
  @Deprecated
  public boolean isStatusOutdated() throws RemoteException;

  /**
   * Returns <code>true</code> if this assessment is global
   *
   * @return
   *         <ul>
   *         <li><code>true</code> if it is a global assessment</li>
   *         <li><code>false</code> if it is not a global assessment</li>
   *         </ul>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isGlobal() throws RemoteException;

  /**
   * Set the report section for the assessment.
   * 
   * @param reportSecAssessment
   *          <code>Assesslet</code> to be set as the report section or <code>null</code> if the
   *          <code>&lt;Top-Level&gt;</code> should be set.
   * @throws ApiException
   *           if reportSecAssessment is not a report section assesslet
   * @throws RemoteException
   *           remote communication problem
   */
  void setReportSection(Assessment reportSecAssessment) throws ApiException, RemoteException;

}
