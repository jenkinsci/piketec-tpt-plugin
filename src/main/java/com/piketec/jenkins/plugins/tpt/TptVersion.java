/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2021 Synopsys Inc.
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

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.piketec.tpt.api.TptApi;

/**
 * TPT versions are named like "15" or "15u1" meaning release version 15, update release 1. For the
 * initial release "15u0" the update suffix "u0" is ommited. Milestone and release candidates are
 * named like 15M1b1234 or 15RC2b1234. This class encapsulates version and update number ignoring
 * the fact that milestone and release candidates exist. These are interpreted as final releses with
 * an update number of null.
 *
 */
public class TptVersion implements Serializable {

  private static final Pattern versionPattern = Pattern.compile("\\D*([0-9]+)(u([0-9]+))?.*");

  /**
   * Read and parses the TPT version behind an {@link TptApi} object.
   * 
   * @param api
   *          The <code>TptApi</code> object to which the version shall be determined.
   * @return The parsed version string of the <code>TptApi</code> object.
   * @throws RemoteException
   *           remote communication problem
   */
  public static TptVersion getVersion(TptApi api) throws RemoteException {
    String version = api.getTptVersion();
    Matcher matcher = versionPattern.matcher(version);
    if (matcher.matches()) {
      String mainVersion = matcher.group(1);
      String updateLevelString = matcher.group(3);
      int major = Integer.parseInt(mainVersion);
      int updateLevel = 0;
      if (updateLevelString != null && !updateLevelString.isEmpty()) {
        updateLevel = Integer.parseInt(updateLevelString);
      }
      return new TptVersion(major, updateLevel);
    } else {
      return new TptVersion(0, 0);
    }
  }

  /**
   * The main TPT version
   */
  public final int major;

  /**
   * The update level
   */
  public final int updateLevel;

  private TptVersion(int major, int updateLevel) {
    this.major = major;
    this.updateLevel = updateLevel;
  }

  /**
   * Checks if this version is greater or equals than the given version.
   * 
   * @param major
   *          The minimal required major version
   * @param updateLevel
   *          the minimal required update level
   * @return if this version is greater or equals than the given version
   */
  public boolean isAtLeast(int major, int updateLevel) {
    if (this.major > major) {
      return true;
    } else if (this.major == major) {
      return this.updateLevel >= updateLevel;
    } else {
      return false;
    }
  }

  /**
   * To read and set test set conditions via API is available since 16u1.
   * 
   * @return <code>true</code> if this TPT version supports test case condtion API access,
   *         <code>false</code> otherwise.
   */
  public boolean supportsTestCaseConditionAccess() {
    return isAtLeast(16, 1);
  }

  /**
   * Test set condtions can be configured in TPT file since TPT 9. Requirement sets can be used for
   * test set conditions since TPT 11. For availabality via API see
   * {@link #supportsTestCaseConditionAccess()}.
   * 
   * @return <code>true</code> if this TPT version supports test case condtion in the TPT file,
   *         <code>false</code> otherwise.
   */
  public boolean supportsTestCaseConditions() {
    return isAtLeast(11, 0);
  }

  @Override
  public String toString() {
    return "TPT " + major + "u" + updateLevel;
  }
}
