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
package com.piketec.tpt.api.pythonstate;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.ScenarioGroup;
import com.piketec.tpt.api.Testlet;

/**
 * A <code>PythonStateScenario</code> represents a scenario with a Python script.
 * 
 * <p>
 * In TPT, it represents both the variants as well as the test cases. Create a new
 * <code>PythonStateScenario</code> via
 * {@link Testlet#createPythonStateVariant(String, ScenarioGroup)}
 * </p>
 * 
 * @see Testlet#createPythonStateVariant(String, ScenarioGroup)
 * @see Project#getTopLevelTestlet()
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 * 
 */
public interface PythonStateScenario extends Scenario {

  /**
   * Gets the Python script associated with this scenario.
   * 
   * @return The Python script as a {@link String}.
   * @throws RemoteException
   *           remote communication problem
   */
  String getScript() throws RemoteException;

  /**
   * Sets the Python script for this scenario.
   * 
   * @param script
   *          The new Python script as a {@link String}.
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the script is invalid or cannot be set
   */
  void setScript(String script) throws RemoteException, ApiException;
}
