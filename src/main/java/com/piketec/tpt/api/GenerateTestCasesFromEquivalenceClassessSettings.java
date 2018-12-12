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

import java.io.Serializable;

/**
 * Class to provide settings for generation of TestCases from EquivalenceClassess.
 * 
 * @author TNagel, PikeTec GmbH
 *
 */
public class GenerateTestCasesFromEquivalenceClassessSettings implements Serializable {

  /**
   * Default value for combination.
   */
  public static final Combination DEFAULT_COMBINATION = Combination.SINGLE;

  /**
   * Default value for includeLeftAndRightBoundaries.
   */
  public static final boolean DEFAULT_INCLUDE_LEFT_AND_RIGHT_BOUNDARIES = false;

  /**
   * Default value for mergeAllProbsIntoOneVariant.
   */
  public static final boolean DEFAULT_MERGE_ALL_PROBS_INTO_ONE_VARIANT = false;

  /**
   * Default value for sequencesWaitTime.
   */
  public static final String DEFAULT_SEQUENCES_WAIT_TIME = "1s";

  /**
   * Enumeration for Combination-States.
   * 
   * @author TNagel, PikeTec GmbH
   *
   */
  public static enum Combination {
    SINGLE, PAIRED
  }

  /**
   * look at GUI-Documentation.
   */
  private Combination combination = DEFAULT_COMBINATION;

  /**
   * look at GUI-Documentation.
   */
  private boolean includeLeftAndRightBoundaries = DEFAULT_INCLUDE_LEFT_AND_RIGHT_BOUNDARIES;

  /**
   * look at GUI-Documentation.
   */
  private boolean mergeAllProbsIntoOneVariant = DEFAULT_MERGE_ALL_PROBS_INTO_ONE_VARIANT;

  /**
   * look at GUI-Documentation.
   */
  private String sequencesWaitTime = DEFAULT_SEQUENCES_WAIT_TIME;

  /**
   * 
   * @return combinatoric value.
   */
  public Combination getCombinatorics() {
    return this.combination;
  }

  /**
   * 
   * @param combination
   *          value to be set.
   */
  public void setCombinatorics(Combination combination) {
    this.combination = combination;
  }

  /**
   * 
   * @return includeLeftAndRightBoundaries value.
   */
  public boolean isIncludeLeftAndRightBoundaries() {
    return this.includeLeftAndRightBoundaries;
  }

  /**
   * 
   * @param includeLeftAndRightBoundaries
   *          value to be set.
   */
  public void setIncludeLeftAndRightBoundaries(boolean includeLeftAndRightBoundaries) {
    this.includeLeftAndRightBoundaries = includeLeftAndRightBoundaries;
  }

  /**
   * 
   * @return mergeAllProbsIntoOneVariant value.
   */
  public boolean isMergeAllProbs() {
    return this.mergeAllProbsIntoOneVariant;
  }

  /**
   * 
   * @param mergeAllProbsIntoOneVariant
   *          value to be set.
   */
  public void setMergeAllProbs(boolean mergeAllProbsIntoOneVariant) {
    this.mergeAllProbsIntoOneVariant = mergeAllProbsIntoOneVariant;
  }

  /**
   * 
   * @return sequencesWaitTime to be set.
   */
  public String getSequencesWaitTime() {
    return this.sequencesWaitTime;
  }

  /**
   * 
   * @param sequencesWaitTime
   *          value to be set.
   */
  public void setSequencesWaitTime(String sequencesWaitTime) {
    this.sequencesWaitTime = sequencesWaitTime;
  }
}
