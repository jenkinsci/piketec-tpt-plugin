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

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * The status of a test case or assesslet.
 * 
 * @author Copyright (c) 2014-2024 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface Status extends IdentifiableRemote {

  /**
   * Returns the author of this status.
   * 
   * @return author of this status
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getAuthor() throws RemoteException;

  /**
   * Returns the comment of this status.
   * 
   * @return comment of this status
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getComment() throws RemoteException;

  /**
   * Returns the date of this status.
   * 
   * @return date of this status
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Date getDate() throws RemoteException;

  /**
   * Returns the type of this status.
   * 
   * @return type of this status
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getType() throws RemoteException;

  /**
   * Returns the revision number of this status.
   * 
   * @return revision number of this status
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  int getRevisionNumber() throws RemoteException;

  /**
   * All tags assigned to this status.
   * 
   * @return a list of tags.
   * @throws RemoteException
   *           remote communication problem
   */
  List<String> getTags() throws RemoteException;
}
