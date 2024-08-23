/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2024 Synopsys Inc.
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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A Log is a list of messages where each messages has a {@link LogType}.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class Log implements Serializable {

  static final long serialVersionUID = 1L;

  protected List<LogEntry> entries = new LinkedList<>();

  protected int errorCount = 0;

  protected int warningCount = 0;

  protected int infoCount = 0;

  /**
   * Adds a new log entry with {@link LogType#ERROR}
   * 
   * @param message
   *          The message text
   */
  public void addError(String message) {
    entries.add(new LogEntry(LogType.ERROR, message));
    errorCount++;
  }

  /**
   * @return The amout of error messages contained in this log.
   */
  public int getErrorCount() {
    return errorCount;
  }

  /**
   * Adds a new log entry with {@link LogType#WARNING}
   * 
   * @param message
   *          The message text
   */
  public void addWarning(String message) {
    entries.add(new LogEntry(LogType.WARNING, message));
    warningCount++;
  }

  /**
   * @return The amout of warning messages contained in this log.
   */
  public int getWarningCount() {
    return warningCount;
  }

  /**
   * Adds a new log entry with {@link LogType#INFO}
   * 
   * @param message
   *          The message text
   */
  public void addInfo(String message) {
    entries.add(new LogEntry(LogType.INFO, message));
    infoCount++;
  }

  /**
   * @return The amout of info messages contained in this log.
   */
  public int getInfoCount() {
    return infoCount;
  }

  /**
   * @return all log entries of all {@link LogType LogTypes} of this log as an unmodifiable list.
   */
  public List<LogEntry> getEntries() {
    return Collections.unmodifiableList(entries);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (LogEntry item : entries) {
      if (sb.length() > 0) {
        sb.append('\n');
      }
      sb.append('[').append(item.type.name()).append("] ");
      sb.append(item.text);
    }
    return sb.toString();
  }

  /**
   * The log type or severity of a log message.
   * 
   * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
   */
  public static enum LogType {
    /**
     * Log messages, that represents an error
     */
    ERROR,
    /**
     * Log messages, that represents a warning
     */
    WARNING,
    /**
     * Log messages, that represents an information
     */
    INFO
  }

  /**
   * A log message and its {@link LogType}.
   * 
   * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
   */
  public static class LogEntry implements Serializable {

    static final long serialVersionUID = 1L;

    private final LogType type;

    private final String text;

    private LogEntry(LogType type, String text) {
      this.text = text;
      this.type = type;
    }

    /**
     * @return The message of this log entry
     */
    public String getText() {
      return text;
    }

    /**
     * @return the log type of this log entry
     */
    public LogType getType() {
      return type;
    }

    @Override
    public String toString() {
      return '[' + type.name() + "] " + text;
    }
  }

}
