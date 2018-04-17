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
