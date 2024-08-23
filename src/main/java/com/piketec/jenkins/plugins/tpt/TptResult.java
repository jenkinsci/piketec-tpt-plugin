package com.piketec.jenkins.plugins.tpt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum TptResult {

  FAILED, PASSED, INCONCLUSIVE, EXECUTION_ERROR;

  public static TptResult fromString(String resultName) {
    resultName = resultName.toUpperCase();
    switch (resultName) {
      case "EXECUTION_ERROR":
      case "ERROR":
        return TptResult.EXECUTION_ERROR;
      case "FAILED":
        return TptResult.FAILED;

      case "INCONCLUSIVE":
      case "DONT_KNOW":
        return TptResult.INCONCLUSIVE;
      case "PASSED":
      case "SUCCESS":
        return TptResult.PASSED;
      default:
        throw new IllegalArgumentException("Unexpected value: " + resultName);
    }
  }

  public static @Nonnull TptResult worstCase(@Nullable TptResult a, @Nullable TptResult b) {
    if (a == b) {
      return a == null ? TptResult.INCONCLUSIVE : a;
    }
    int ao = prio(a);
    int bo = prio(b);
    assert ao != bo;
    if (ao < bo) {
      return a; // a is worse
    } else {
      return b; // b is worse
    }
  }

  private static int prio(@Nullable TptResult r) {
    if (r == null) {
      return 3;
    }
    switch (r) {
      case EXECUTION_ERROR:
        return -1;
      case FAILED:
        return 0;
      case PASSED:
        return 1;
      case INCONCLUSIVE:
        return 2;
    }
    return 2;
  }

}
