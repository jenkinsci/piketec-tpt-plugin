package com.piketec.jenkins.plugins.tpt.api.callables;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import com.piketec.jenkins.plugins.tpt.TptVersion;

/**
 * Data container for the result returned by {@link GetTestCasesCallable}. Contains the union of all
 * test cases contained in test sets used by a execution configuration and the total number of test
 * cases. The total number may be greater than the items in the set union since single test cases
 * can be used by multiple test sets.
 *
 */
public class GetTestCasesCallableResult implements Serializable {

  /**
   * The names of all test cases.
   */
  public final Set<String> testCases;

  /**
   * The total number of test cases including duplicates.
   */
  public final int testCaseCount;

  /**
   * <code>true</code> if any of the test sets had an test set condition AND tpt version supported
   * cecking for test set condtions via API, <code>false</code> otherwise.
   */
  public final boolean testCaseConditionsPresent;

  /**
   * The TPT version the test cases were collected with
   */
  public final TptVersion tptVersion;

  GetTestCasesCallableResult(Set<String> testCases, int testCaseCount,
                             boolean testCaseConditionsPresent, TptVersion tptVersion) {
    this.testCases = Collections.unmodifiableSet(testCases);
    this.testCaseCount = testCaseCount;
    this.testCaseConditionsPresent = testCaseConditionsPresent;
    this.tptVersion = tptVersion;
  }

}
