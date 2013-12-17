package com.piketec.jenkins.plugins.tpt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class Testcase {

  private String name;

  private int id;

  private String configuration;

  private String result;

  private Date execDate;

  private String execDuration;

  private final List<String> errors;

  private final List<String> log;

  /** ordered set of variables (ordered by name) */
  private final Set<AssessmentVariable> variables;

  public Testcase() {
    errors = new ArrayList<String>();
    log = new ArrayList<String>();
    variables = new HashSet<AssessmentVariable>();
    execDuration = "0";
    result = "";
    configuration = "";
    name = "";
    id = 0;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<AssessmentVariable> getVariables() {
    return Collections.unmodifiableSet(variables);
  }

  public void addAssessmentVariable(AssessmentVariable assessmentVar) {
    variables.add(assessmentVar);
  }

  public int getID() {
    return id;
  }

  public void setID(int id) {
    this.id = id;
  }

  public List<String> getErrors() {
    return Collections.unmodifiableList(errors);
  }

  public void addErrors(String error) {
    error = error.replaceAll("address=0x[0-9a-fA-F]{4}", "address=0x%%%%");
    error = error.replaceAll("(heap size: \\d+, required heap size: \\d+)", "");
    this.errors.add(error);
  }

  public void setConfiguration(String configuration) {
    this.configuration = configuration;
  }

  public void setExecDate(Date execDate) {
    this.execDate = execDate;
  }

  public Date getExecDate() {
    return execDate;
  }

  public void setExecDuration(String time) {
    this.execDuration = time;
  }

  public String getExecDuration() {
    return execDuration;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getResult() {
    return result;
  }

  public void addLogEntry(String entry) {
    this.log.add(entry);
    this.log.add("\n");
  }

  public List<String> getLogEntries() {
    return log;
  }

  /**
   * @return the testcase name concatinated with the id
   */
  public String getQualifiedName() {
    return name + "_" + id;
  }

  /**
   * @return an alphanumeric string from configuration\\name_id
   */
  public String getFilesystemPath() {
    StringBuilder sb = new StringBuilder();
    sb.append(getAlphanumericString(configuration)).append("\\");
    sb.append(getAlphanumericString(name));
    sb.append("_").append(id);
    return sb.toString();
  }

  private static String getAlphanumericString(String nonAlphaNum) {
    Pattern p = Pattern.compile("[^a-zA-Z0-9]");
    return p.matcher(nonAlphaNum).replaceAll("_");
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (String s : errors) {
      sb.append(s).append("\n");
    }
    return sb.toString().trim();
  }

  /**
   * Vergleicht zwei Testcase-Strukturen miteinander. Sie sind gleich, wenn sie dassselbe Result,
   * dieselben Error-Messages und die gleichen Assessment-Variablen haben.
   * 
   * @param other
   *          Testfall fuer den Vergleich.
   * @return <code>true</code>, Testfaelle werden gleich bewertet, <code>false</code> sonst.
   */
  public boolean equalTestcases(Testcase other) {
    if (this == other) {
      return true;
    }
    if (!compareResult(other)) {
      return false;
    }
    if (!compareErrors(other)) {
      return false;
    }
    if (!compareAssessmentVariables(other)) {
      return false;
    }
    return true;
  }

  public boolean compareErrors(Testcase other) {
    return errors.equals(other.errors);
  }

  public boolean compareAssessmentVariables(Testcase other) {
    return variables.equals(other.variables);
  }

  public String getAssessmentVariableDiff(Testcase reference) {
    StringBuilder b = new StringBuilder();
    Set<AssessmentVariable> refs =
        new TreeSet<AssessmentVariable>(new AssessmentVariableComparator());
    refs.addAll(reference.getVariables());

    for (AssessmentVariable v : variables) {
      boolean containedInRefs = refs.remove(v);
      if (!containedInRefs) {
        b.append("[ONLY IN TEST DATA]: ").append(v).append("\n");
      }
    }
    for (AssessmentVariable v : refs) {
      b.append("[ONLY IN REFERENCE DATA]: ").append(v).append("\n");
    }
    return b.toString();
  }

  public boolean compareResult(Testcase other) {
    if (result == null) {
      if (other.result != null) {
        return false;
      }
    } else if (!result.equals(other.result)) {
      return false;
    }
    return true;
  }

  private static final class AssessmentVariableComparator implements Comparator<AssessmentVariable> {

    @Override
    public int compare(AssessmentVariable assessmentVar1, AssessmentVariable assessmentVar2) {
      return assessmentVar1.getName().compareTo(assessmentVar2.getName());
    }
  }
}
