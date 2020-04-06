/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2020 PikeTec GmbH
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

import com.piketec.tpt.api.properties.PropertyMap;

/**
 * This class represents an assessment. The particular properties of the different assessment types
 * are mapped to generic properties (see {@link PropertyMap}).
 *
 * @author Copyright (c) 2014-2020 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface Assessment extends AssessmentOrGroup {

  /**
   * Type String for Check Log Entries Assesslet
   */
  public static final String CHECK_LOG_ENTRIES_TYPE = "CheckLogEntriesAssessletType";

  /**
   * Type String for Check Log Entries Assesslet
   */
  public static final String CONDITION_TREE_TYPE = "ConditionTreeAssessletType";

  /**
   * Type String for Equivalence Classes Assesslet
   */
  public static final String EQUIVALENCE_CLASSES_TYPE = "EquivalenceClassesAssessletType";

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
   * Type String for Report Linked Requirements Assesslet
   */
  public static final String REPORT_LINKED_REQUIREMENTS_TYPE = "rmassesslet";

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
   * Type String for Test Step List Assessments Assesslet
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
   * @return The name of the assessment type.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public String getType() throws ApiException, RemoteException;

  /**
   * @return The value of the description field in the assessment header.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public String getDescription() throws ApiException, RemoteException;

  /**
   * Set the value for the description field of the assessment header. <code>Null</code> will be
   * reduced to an empty string.
   * 
   * @param description
   *          The new description of the assessment.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setDescription(String description) throws ApiException, RemoteException;

  /**
   * Returns a list of variants, for which the assessment is enabled. Returns an empty list, if the
   * assssement is enabled for all variants.
   *
   * @return A list of variants and variant groups.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   * 
   * @deprecated Use {@link #getSelectedVariants()} and {@link #getAutoIncludeVariantGroups()}
   */
  @Deprecated
  public RemoteList<ScenarioOrGroup> getEnabledVariants() throws ApiException, RemoteException;

  /**
   * Returns a list of variants, for which the assessment is enabled. Returns an empty list, if the
   * assssement is enabled for all variants.
   *
   * @return A list of {@link Scenario variants}.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public RemoteCollection<Scenario> getSelectedVariants() throws ApiException, RemoteException;

  /**
   * Enable the assessment for a particular variant or variant group. If it the arguement is a
   * {@link ScenarioGroup} auto include will be enabled for this group and all children will be
   * enabled recursivly. To disable, use {@link #getSelectedVariants()} or
   * {@link #getAutoIncludeVariantGroups()} and remove the desired objects.
   * 
   * @param sog
   *          The variant / variant group ({@link com.piketec.tpt.api.ScenarioOrGroup
   *          ScenarioOrGroup}) for which the assesslet should be enabled.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void enableForVariant(ScenarioOrGroup sog) throws ApiException, RemoteException;

  /**
   * Returns a list of variant groups that will automatically select newly added {@link Scenario
   * variants} and enables auto include for newly added {@link ScenarioGroup variant groups}. Look
   * at {@link #enableAutoIncludeForVariantGroup(ScenarioGroup)} for disable variant groups.
   *
   * @return A list of {@link ScenarioGroup variant groups}.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public RemoteCollection<ScenarioGroup> getAutoIncludeVariantGroups()
      throws ApiException, RemoteException;

  /**
   * Enable the group to automatically select newly added {@link Scenario variants} and
   * {@link ScenarioGroup variant groups}. To disable, use {@link #getAutoIncludeVariantGroups()}
   * and remove the desired objects.
   * 
   * @see Assessment#enableForVariant(ScenarioOrGroup)
   * 
   * @param scenarioGroup
   *          for which auto include should be activated
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void enableAutoIncludeForVariantGroup(ScenarioGroup scenarioGroup)
      throws ApiException, RemoteException;

  /**
   * Returns a list of test cases for which the assessment is enabled.
   * 
   * @return A list of test cases or test case groups ({@link com.piketec.tpt.api.ScenarioOrGroup
   *         ScenarioOrGroup})
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   * 
   * @deprecated Use {@link #getSelectedTestCases()} and {@link #getAutoIncludeTestCaseGroups()}
   */
  @Deprecated
  public RemoteCollection<ScenarioOrGroup> getEnabledTestCases()
      throws ApiException, RemoteException;

  /**
   * Returns a list of test cases for which the assessment is enabled.
   * 
   * @return A list of {@link Scenario test cases}
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public RemoteCollection<Scenario> getSelectedTestCases() throws ApiException, RemoteException;

  /**
   * Returns a list of {@link ScenarioGroup test case groups} that will automatically select newly
   * added {@link Scenario test cases} and enables auto include for newly added {@link ScenarioGroup
   * test case groups}. For disable groups, look at
   * {@link #enableAutoIncludeForTestCaseGroup(ScenarioGroup)}
   *
   * @return A list of {@link ScenarioGroup test case groups}.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public RemoteCollection<ScenarioGroup> getAutoIncludeTestCaseGroups()
      throws ApiException, RemoteException;

  /**
   * Enable the assessment for a test case or test case group. To disable, use
   * {@link #getSelectedTestCases()} or {@link #getAutoIncludeTestCaseGroups()} and remove the
   * desired objects.
   * 
   * @param sog
   *          A test case or test case group ({@link com.piketec.tpt.api.ScenarioOrGroup
   *          ScenarioOrGroup}), the test case should be enabled for.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void enableForTestCase(ScenarioOrGroup sog) throws ApiException, RemoteException;

  /**
   * Enable the {@link ScenarioGroup test case group} to automatically select newly added
   * {@link Scenario test cases} and {@link ScenarioGroup test case groups}. To disable, use
   * {@link #getAutoIncludeTestCaseGroups()} and remove the desired objects.
   * 
   * @see Assessment#enableForTestCase(ScenarioOrGroup)
   * 
   * @param scenarioGroup
   *          for which auto include should be activated.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void enableAutoIncludeForTestCaseGroup(ScenarioGroup scenarioGroup)
      throws ApiException, RemoteException;

  /**
   * Returns a list of platform configurations or execution ttems for which the assessment is
   * enabled. Returns an empty list, if the <code>Assessment</code> is enabled for all platforms and
   * all execution items.
   *
   * @return A list of Platform Configurations or Execution Items
   *         ({@link com.piketec.tpt.api.PlatformOrExecutionItemEnabler
   *         PlatformOrExecutionItemEnabler})
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public RemoteList<PlatformOrExecutionItemEnabler> getEnabledPlatformConfigurationsOrExecutionConfigurationItems()
      throws ApiException, RemoteException;

  /**
   * Enables the assessment for a particular {@link com.piketec.tpt.api.PlatformConfiguration
   * PlatformConfiguration}.
   *
   * @param pc
   *          The Platform Configuration, for which the assessment should be enabled.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void enableForPlatformConfiguration(PlatformConfiguration pc)
      throws ApiException, RemoteException;

  /**
   * Enables the assessment for a particular a {@link com.piketec.tpt.api.ExecutionConfigurationItem
   * ExecutionConfigurationItem}.
   *
   * @param execItem
   *          The <code>ExecutionConfigurationItem</code>, the assessment should be enabled for.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void enableForExecutionConfigurationItem(ExecutionConfigurationItem execItem)
      throws ApiException, RemoteException;

  /**
   * Returns the properties of the assessment as {@link PropertyMap}.
   * <p>
   * A <code>PropertyMap</code> maps the properties as follows: {@link java.lang.String String}
   * -&gt; {@link com.piketec.tpt.api.properties.Property Property}. A <code>Property</code> is
   * either a <code>PropertyMap</code> or a <code>String</code> value.
   * </p>
   * The structure of the PropertyMap depends on the type of the assessment.
   * 
   * @return A {@link com.piketec.tpt.api.properties.PropertyMap PropertyMap} with the settings for
   *         the assessments.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public PropertyMap getProperties() throws ApiException, RemoteException;

  /**
   * <p>
   * Configures the assessment with the provided PropertyMap.
   * </p>
   * <p>
   * To remove a removable property, e.g. a report section, set it to <code>null</code>.
   * </p>
   * It is <b>strongly recommended</b> to modify the PropertyMap returned by
   * {@link com.piketec.tpt.api.Assessment#getProperties() getProperties()}
   *
   * @param properties
   *          A {@link com.piketec.tpt.api.properties.PropertyMap PropertyMap} containing the
   *          settings.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public void setProperties(PropertyMap properties) throws ApiException, RemoteException;

  /**
   * Returns all active test cases with respect to the variant context.
   * 
   * @return set of active test cases
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public Set<Scenario> getActiveTestCases() throws ApiException, RemoteException;

  /**
   * Get all requirements currently linked to this assesslet. The content of the collection is only
   * a snapshot of the current state but removing items from this list will remove the link in TPT
   * anyway even if the link was created after receiving this collection.
   * 
   * @return The currently linked requirements.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           API constraint error
   */
  public RemoteCollection<Requirement> getLinkedRequirements() throws ApiException, RemoteException;

}
