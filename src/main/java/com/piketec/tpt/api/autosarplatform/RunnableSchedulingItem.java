/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2025 Synopsys Inc.
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
package com.piketec.tpt.api.autosarplatform;

import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.IdentifiableRemote;

/**
 * An AUTOSAR runnable that can be scheduled in the AUTOSAR platform in TPT
 * 
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface RunnableSchedulingItem extends IdentifiableRemote {

  /**
   * @return The name of the runnable
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getName() throws RemoteException;

  /**
   * @return If true the runnable will be scheduled automatically in the generated test frame. If
   *         false a client function will be imported for this runnable so that it can be called
   *         explicitly.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isAutomaticScheduling() throws RemoteException;

  /**
   * Configure if this runnable shall be scheduled automatically or a client function for explicit
   * scheduling shall be imported.
   * 
   * @param automaticScheduling
   *          If true the runnable will be scheduled automatically in the generated test frame. If
   *          false a client function will be imported for this runnable so that it can be called
   *          explicitly.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAutomaticScheduling(boolean automaticScheduling) throws RemoteException;

  /**
   * Get the periods to be used for this runnable.
   * 
   * @return The periods for this runnable
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<String> getPeriods() throws RemoteException;

  /**
   * Set the periods to be used for this runnable.
   * 
   * @param period
   *          The periods for this runnable
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setPeriods(List<String> period) throws RemoteException;

  /**
   * @return True if this runnable is configured to be executed only on entry, false otherwise.
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isOnlyOnEntry() throws RemoteException;

  /**
   * Configure if a scheduled runnable shall be executed only on entry.
   * 
   * @param onlyOnEntry
   *          True if this runnable shall be executed only on entry, false otherwise
   * @throws RemoteException
   *           remote communication problem
   */
  public void setOnlyOnEntry(boolean onlyOnEntry) throws RemoteException;

}
