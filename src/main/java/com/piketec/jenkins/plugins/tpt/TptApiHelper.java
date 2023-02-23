/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2023 PikeTec GmbH
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
package com.piketec.jenkins.plugins.tpt;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import com.piketec.tpt.api.RemoteCollection;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.ScenarioOrGroup;
import com.piketec.tpt.api.TestSet;

/**
 * Provides some Methods to generalize access to the TPT API.
 */
public class TptApiHelper {

  /**
   * Get the test cases that are part of the given test set. Test case groups are ignored but all
   * underlaying test cases will be returned. For TPT versions less than 16 the test cases may be
   * reduced by a test set condition evaluated with default values. This is the best we can do in
   * that case.
   * 
   * @param tptVersion
   *          The TPT version the test set was read from
   * @param testSet
   *          The test set to get the test cases from
   * @return All test cases that are part of the given test set
   * @throws RemoteException
   *           remote communication problem
   */
  public static Collection<Scenario> getTestCasesFromTestSet(TptVersion tptVersion, TestSet testSet)
      throws RemoteException {
    if (tptVersion.isAtLeast(16, 0)) {
      Collection<Scenario> result = new ArrayList<>();
      for (ScenarioOrGroup sog : testSet.getSelectedTestCasesOrGroups().asIterable()) {
        if (sog instanceof Scenario) {
          result.add((Scenario)sog);
        }
      }
      return result;
    } else {
      // older version, we have to use deprecated getTestCases()
      @SuppressWarnings("deprecation")
      RemoteCollection<Scenario> result = testSet.getTestCases();
      return result.getItems();
    }
  }

  /**
   * Adds a test case to the test set. Will use the correct method for the given TPT version. The
   * test set and the test case must be part of the same TPT model loaded in the same TPT instance.
   * 
   * @param tptVersion
   *          The TPT version the test set was read from
   * @param testSet
   *          The test set to add the test case to
   * @param testCase
   *          The test case to add to the test set
   * @throws RemoteException
   *           remote communication problem
   */
  @SuppressWarnings("deprecation")
  public static void addTestCase(TptVersion tptVersion, TestSet testSet, Scenario testCase)
      throws RemoteException {
    if (tptVersion.isAtLeast(19, 0)) {
      testSet.addTestCaseOrGroup(testCase);
    } else {
      // older version, we have to use deprecated addTestCase(Scenario)
      testSet.addTestCase(testCase);
    }
  }

}
