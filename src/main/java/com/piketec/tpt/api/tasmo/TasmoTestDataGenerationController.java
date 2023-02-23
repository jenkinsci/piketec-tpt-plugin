/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2022 PikeTec GmbH
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
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface TasmoTestDataGenerationController extends TptRemote {

  /**
   * Get the list of all coverage goals.
   * 
   * @return A list of all coverage goals
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<TasmoCoverageGoal> getCoverageGoals() throws RemoteException;

  /**
   * Get the current state of the TASMO test data generation.
   * 
   * @return The current state of the TASMO test data generation
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public TasmoTestdataGenerationStatus getCurrentStatus() throws RemoteException;

  /**
   * Get the error message in case the TASMO test data generation stopped with an error. In this
   * case {@link #getCurrentStatus} will return {@link TasmoTestdataGenerationStatus#ERROR}.
   * 
   * If there was no error this function will return <code>null</code>.
   * 
   * @return The error message or <code>null</code> if there has not been any error
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getErrorMessage() throws RemoteException;

  /**
   * Set the execution config items from which measured coverage data from existing test cases
   * should be imported.
   * 
   * @param executionConfigItems
   *          The execution config items from which measured coverage data should be imported.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @throws ApiException
   *           if the TASMO test data generation is currently running.
   */
  public void setImportedCoverageData(ExecutionConfigurationItem[] executionConfigItems)
      throws RemoteException;

  /**
   * Start or continue the TASMO test data generation.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation is already running.
   */
  public void start() throws RemoteException;

  /**
   * Pause the TASMO test data generation.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation is not running.
   */
  public void pause() throws RemoteException;

  /**
   * Stop the TASMO test data generation.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped.
   */
  public void stop() throws RemoteException;

  /**
   * Export the generated test cases.
   * 
   * @param targetGroup
   *          The target scenario group to export the test cases to.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped.
   */
  public void exportGeneratedTestcases(ScenarioGroup targetGroup) throws RemoteException;

  /**
   * Export the input specification overview as csv.
   * 
   * @param file
   *          The file to export the results to. The csv extension is added to the file if not
   *          present.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped.
   */
  public void exportInputSpecificationReport(File file) throws RemoteException;

  /**
   * Export the input specification overview as csv.
   * 
   * @param file
   *          The path to the file where to export the results to. The csv extension is added to the
   *          path if not present.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped.
   */
  public void exportInputSpecificationReportByPath(String file)
      throws RemoteException, ApiException;

  /**
   * Export the coverage results overview as csv.
   * 
   * @param file
   *          The file to export the results to. The csv extension is added to the file if not
   *          present.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped.
   */
  public void exportCoverageResultsReport(File file) throws RemoteException;

  /**
   * Export the coverage results overview as csv.
   * 
   * @param file
   *          The path to the file where to export the results to. The csv extension is added to the
   *          file if not present.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped.
   */
  public void exportCoverageResultsReportByPath(String file) throws RemoteException, ApiException;

  /**
   * Close and dispose this object. This will close the TASMO UI as well. After this is called all
   * further calls to functions of this object will throw an {@link ApiException}.
   * 
   * Can only be done if the TASMO test data generation has not yet been startet or has already
   * stopped.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the TASMO test data generation has not yet stopped.
   */
  void close() throws RemoteException;

}
