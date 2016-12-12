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
package com.piketec.jenkins.plugins.tpt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Testcase {

  private String name;

  private int id;

  private String result;

  private Date execDate;

  private String execDuration;

  private final List<String> errors;

  private final List<String> log;

  /** ordered set of variables (ordered by name) */
  private final Set<AssessmentVariable> variables;

  Testcase() {
    errors = new ArrayList<String>();
    log = new ArrayList<String>();
    variables = new HashSet<AssessmentVariable>();
    execDuration = "0";
    result = "";
    name = "";
    id = 0;
  }

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  Set<AssessmentVariable> getVariables() {
    return Collections.unmodifiableSet(variables);
  }

  void addAssessmentVariable(AssessmentVariable assessmentVar) {
    variables.add(assessmentVar);
  }

  int getID() {
    return id;
  }

  void setID(int id) {
    this.id = id;
  }

  List<String> getErrors() {
    return Collections.unmodifiableList(errors);
  }

  void addErrors(String error) {
    error = error.replaceAll("address=0x[0-9a-fA-F]{4}", "address=0x%%%%");
    error = error.replaceAll("(heap size: \\d+, required heap size: \\d+)", "");
    this.errors.add(error);
  }

  void setExecDate(Date execDate) {
    this.execDate = execDate;
  }

  Date getExecDate() {
    return execDate;
  }

  void setExecDuration(String time) {
    this.execDuration = time;
  }

  String getExecDuration() {
    return execDuration;
  }

  void setResult(String result) {
    this.result = result;
  }

  String getResult() {
    return result;
  }

  void addLogEntry(String entry) {
    this.log.add(entry);
    this.log.add("\n");
  }

  List<String> getLogEntries() {
    return log;
  }

  /**
   * @return the testcase name concatinated with the id
   */
  String getQualifiedName() {
    return name + "_" + id;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (String s : errors) {
      sb.append(s).append("\n");
    }
    return sb.toString().trim();
  }
}
