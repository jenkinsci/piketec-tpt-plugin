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
import java.rmi.RemoteException;

/**
 * Either a test case or a Diagram/StepList variant of a TestLet.
 */
public interface Scenario extends ScenarioOrGroup {

  /**
   * Get all requirements currently linked to this scenario. The content of the collection is only a
   * snapshot of the current state but removing items from this list will remove the link in TPT
   * anyway even if the link was created after receiving this collection.
   * 
   * @return The currently linked requirements.
   * @throws ApiException
   * @throws RemoteException
   */
  RemoteCollection<Requirement> getLinkedRequirements() throws ApiException, RemoteException;

  /**
   * Returns the test data directory of scenario for a given execution configuration item. Since
   * ${tpt.date} and ${tpt.time} placeholders are only valid during runtime you can specify the
   * value if needed. Otherwise they are not defined and ApiException will be thrown if used in the
   * test data path.
   * 
   * @param execConfigItem
   *          The execution configuration item to calculate the test data directory.
   * @param dateValueOrNull
   *          The wanted value for ${tpt.date} or <code>null</code>.
   * @param timeValueOrNull
   *          The wanted value for ${tpt.time} or <code>null</code>.
   * @return The test data directory as an absolute file
   * @throws ApiException
   *           If the execution configuration does not belong to the model of the scenario, the
   *           execution configuration has no platform configuration set or the test data path of
   *           the execution configuration has unresolvable placeholder variables.
   * @throws RemoteException
   */
  public File getTestDataDirectory(ExecutionConfigurationItem execConfigItem,
                                   String dateValueOrNull, String timeValueOrNull)
      throws RemoteException, ApiException;

}
