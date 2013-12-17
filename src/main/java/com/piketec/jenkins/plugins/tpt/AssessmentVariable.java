package com.piketec.jenkins.plugins.tpt;

public class AssessmentVariable {

  private final String name;

  private final String result;

  private final String type;

  private final String value;

  private final StringBuilder msg = new StringBuilder();

  public AssessmentVariable(String name, String result, String type, String value) {
    this.name = name;
    this.result = result;
    this.type = type;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getResult() {
    return result;
  }

  public void appendMessage(String n) {
    msg.append(n);
  }

  public String getMessage() {
    return msg.toString();
  }

  public String getType() {
    return type;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    StringBuilder b =
        new StringBuilder("<AssessmentVariable name=\"" + name + "\", result=\"" + result + "\"");
    if (type != null) {
      b.append(", type=\"" + type + "\"");
    }
    if (value != null) {
      b.append(", value=\"" + value + "\"");
    }
    if (msg.length() > 0) {
      b.append(">" + msg + "</AssessmentVariable>");
    } else {
      b.append("></AssessmentVariable>");
    }
    return b.toString();
  }

  @Override
  public int hashCode() {
    int prime = 31;
    int res = 1;
    res = prime * res + ((msg == null) ? 0 : msg.toString().hashCode());
    res = prime * res + ((name == null) ? 0 : name.hashCode());
    res = prime * res + ((result == null) ? 0 : result.hashCode());
    res = prime * res + ((type == null) ? 0 : type.hashCode());
    res = prime * res + ((value == null) ? 0 : value.hashCode());
    return res;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AssessmentVariable other = (AssessmentVariable)obj;
    if (msg == null) {
      if (other.msg != null) {
        return false;
      }
    } else if (!msg.toString().equals(other.msg.toString())) {
      return false;
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }

    if (result == null) {
      if (other.result != null) {
        return false;
      }
    } else if (!result.equals(other.result)) {
      return false;
    }

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }

    return true;
  }

}
