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
package com.piketec.tpt.api.tasmo;

import java.io.File;
import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.ExecutionConfigurationItem;
import com.piketec.tpt.api.ScenarioGroup;
import com.piketec.tpt.api.TptRemote;

/**
 * This interface provides access to TASMO test data generation.
 *
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface TasmoTestDataGenerationController extends TptRemote {

  /**
   * Gets the list of all coverage goals.
   * 
   * @return A list of all coverage goals
   * 
   * @throws ApiException
   *           if the TASMO test data generation controller object has been disposed
   * @throws RemoteException
   *           remote communication problem
   */
  public List<TasmoCoverageGoal> getCoverageGoals() throws RemoteException, ApiException;

  /**
   * Gets the currently selected Coverage Goals.
   * 
   * @return the currently selected Coverage Goals
   * @throws RemoteException
   *           remote communication problem
   */
  public List<TasmoCoverageGoal> getSelectedCoverageGoals() throws RemoteException;

  /**
   * Sets the selected {@link TasmoCoverageGoal} that should be covered. The
   * {@link TasmoCoverageGoal} selection can only be changed if the generation has not yet started
   * or it has been stopped. If <code>selectedCoverageGoals</code> contains an unknown
   * {@link TasmoCoverageGoal} an {@link ApiException} is thrown, the method
   * {@link #setSelectedCoverageGoals(String)} on the other hand does not throw an Exception in this
   * case.
   * 
   * @param selectedCoverageGoals
   *          the coverage goals to be selected
   * @throws ApiException
   *           if coverageGoals is null or unknown, or if the TASMO test data generation is
   *           currently running
   * @throws RemoteException
   *           remote communication problem
   */
  public void setSelectedCoverageGoals(List<TasmoCoverageGoal> selectedCoverageGoals)
      throws RemoteException, ApiException;

  /**
   * Exports the current {@link TasmoCoverageGoal} selection to the given file.
   * 
   * @param file
   *          the path to the file where the selection should be stored.
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped, the TASMO test data generation
   *           controller object has been disposed or file is null.
   */
  public void exportCoverageGoalsSelectionFile(String file) throws RemoteException, ApiException;

  /**
   * same as {@link #setSelectedCoverageGoalsFromFile(String)}. See this method for details.
   * 
   * @param goalsSelectionFile
   *          see {@link #setSelectedCoverageGoalsFromFile(String)} for details.
   * @throws ApiException
   *           see {@link #setSelectedCoverageGoalsFromFile(String)} for details.
   * @throws RemoteException
   *           see {@link #setSelectedCoverageGoalsFromFile(String)} for details.
   */
  public void setSelectedCoverageGoals(String goalsSelectionFile)
      throws RemoteException, ApiException;

  /**
   * Sets the selected {@link TasmoCoverageGoal} that should be covered from a given selection File.
   * The selection File can be exported using {@link #exportCoverageGoalsSelectionFile(String)} or
   * using the UI. The {@link TasmoCoverageGoal} selection can only be changed if the generation has
   * not yet started or it has been stopped. Specified {@link TasmoCoverageGoal} in the
   * goalsSelectionFile that do not exist are ignored.
   * 
   * @param goalsSelectionFile
   *          the path to the file where the coverage goals selection is stored.
   * @throws ApiException
   *           if coverageGoals is null, if the TASMO test data generation is currently running or
   *           if the goalsSelectionFile could not be read
   * @throws RemoteException
   *           remote communication problem
   */
  public default void setSelectedCoverageGoalsFromFile(String goalsSelectionFile)
      throws RemoteException, ApiException {
    setSelectedCoverageGoals(goalsSelectionFile);
  }

  /**
   * Gets the current state of the TASMO test data generation.
   * 
   * @return The current state of the TASMO test data generation
   * 
   * @throws ApiException
   *           if the TASMO test data generation controller object has been disposed
   * @throws RemoteException
   *           remote communication problem
   */
  public TasmoTestdataGenerationStatus getCurrentStatus() throws RemoteException, ApiException;

  /**
   * Gets the error message in case the TASMO test data generation stopped with an error. In this
   * case {@link #getCurrentStatus} will return {@link TasmoTestdataGenerationStatus#ERROR}.
   * 
   * If there was no error this function will return <code>null</code>.
   * 
   * @return The error message or <code>null</code> if there has not been any error
   * 
   * @throws ApiException
   *           if the TASMO test data generation controller object has been disposed
   * @throws RemoteException
   *           remote communication problem
   */
  public String getErrorMessage() throws RemoteException, ApiException;

  /**
   * Sets the execution config items from which measured coverage data from existing test cases
   * should be imported.
   * 
   * @param executionConfigItems
   *          The execution config items from which measured coverage data should be imported.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @throws ApiException
   *           if the TASMO test data generation is currently running or the TASMO test data
   *           generation controller object has been disposed
   */
  public void setImportedCoverageData(ExecutionConfigurationItem[] executionConfigItems)
      throws RemoteException, ApiException;

  /**
   * Starts or continue the TASMO test data generation.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation is already running or the TASMO test data
   *           generation controller object has been disposed
   */
  public void start() throws RemoteException, ApiException;

  /**
   * Pauses the TASMO test data generation.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation is not running or the TASMO test data generation
   *           controller object has been disposed
   */
  public void pause() throws RemoteException, ApiException;

  /**
   * Stops the TASMO test data generation.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped or the TASMO test data
   *           generation controller object has been disposed
   */
  public void stop() throws RemoteException, ApiException;

  /**
   * Exports the generated test cases.
   * 
   * @param targetGroup
   *          The target scenario group to export the test cases to.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped or the TASMO test data
   *           generation controller object has been disposed
   */
  public void exportGeneratedTestcases(ScenarioGroup targetGroup)
      throws RemoteException, ApiException;

  /**
   * Exports the input specification overview as csv.
   * 
   * @param file
   *          The file to export the results to. The csv extension is added to the file if not
   *          present.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped or the TASMO test data
   *           generation controller object has been disposed
   */
  public void exportInputSpecificationReport(File file) throws RemoteException, ApiException;

  /**
   * Exports the input specification overview as csv.
   * 
   * @param file
   *          The path to the file where to export the results to. The csv extension is added to the
   *          path if not present.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped, the TASMO test data generation
   *           controller object has been disposed or file is null.
   */
  public void exportInputSpecificationReportByPath(String file)
      throws RemoteException, ApiException;

  /**
   * Exports the coverage results overview as csv.
   * 
   * @param file
   *          The file to export the results to. The csv extension is added to the file if not
   *          present.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped, the TASMO test data generation
   *           controller object has been disposed or file is null.
   */
  public void exportCoverageResultsReport(File file) throws RemoteException, ApiException;

  /**
   * Exports the coverage results overview as csv.
   * 
   * @param file
   *          The path to the file where to export the results to. The csv extension is added to the
   *          file if not present.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped, the TASMO test data generation
   *           controller object has been disposed or file is null.
   */
  public void exportCoverageResultsReportByPath(String file) throws RemoteException, ApiException;

  /**
   * Closes and disposes this object. This will close the TASMO UI as well. After this is called all
   * further calls to functions of this object will throw an {@link ApiException}.
   * 
   * Can only be done if the TASMO test data generation has not yet been started or has already
   * stopped.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped or the TASMO test data
   *           generation controller object has been disposed.
   */
  void close() throws RemoteException, ApiException;

}
