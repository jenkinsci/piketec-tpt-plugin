package com.piketec.jenkins.plugins.tpt;

import java.util.List;

class TestCasesParseResult {

  final List<Testcase> testCases;

  final Testcase virtualGlobalAssessletTestCase;

  TestCasesParseResult(List<Testcase> testCases, Testcase virtualGlobalAssessletTestCase) {
    this.testCases = testCases;
    this.virtualGlobalAssessletTestCase = virtualGlobalAssessletTestCase;

  }

}