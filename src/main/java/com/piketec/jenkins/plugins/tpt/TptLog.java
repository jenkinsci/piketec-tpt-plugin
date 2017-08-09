package com.piketec.jenkins.plugins.tpt;

import java.util.LinkedList;
import java.util.List;

class TptLog {

  static enum LogLevel {
    NONE, ERROR, WARNING, INFO, ALL
  }

  private List<LogEntry> logEntries = new LinkedList<>();

  public void log(LogLevel level, String message) {
    if (level == LogLevel.NONE) {
      return;
    }
    logEntries.add(new LogEntry(level, message));
  }

  List<LogEntry> getLog(LogLevel level) {
    List<LogEntry> result = new LinkedList<>();
    if (level == LogLevel.NONE) {
      return result;
    }
    for (LogEntry entry : logEntries) {
      if (entry.level.ordinal() <= level.ordinal()) {
        result.add(entry);
      }
    }
    return result;
  }

  public static class LogEntry {

    final LogLevel level;

    final String message;

    LogEntry(LogLevel level, String message) {
      this.level = level;
      this.message = message;
    }
  }

}
