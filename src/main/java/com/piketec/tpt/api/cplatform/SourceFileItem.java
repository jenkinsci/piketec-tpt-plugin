/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2022 PikeTec GmbH
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
package com.piketec.tpt.api.cplatform;

import java.rmi.RemoteException;

import com.piketec.tpt.api.IdentifiableRemote;
import com.piketec.tpt.api.RemoteList;

/**
 * The setting of a single source file in the C\C++ platform in TPT
 * 
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface SourceFileItem extends IdentifiableRemote {

  /**
   * @return The path of this source file relative to the TPT project
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getPath() throws RemoteException;

  /**
   * @param path
   *          The path of this source file relative to the TPT project
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setPath(String path) throws RemoteException;

  /**
   * @return Will this source be analyzed when importing the interface
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isAnalyze() throws RemoteException;

  /**
   * @param analyze
   *          Set if this source will be analyzed when importing the interface
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAnalyze(boolean analyze) throws RemoteException;

  /**
   * @return The extra compiler options for parsing or compiling this file.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getExtraOptions() throws RemoteException;

  /**
   * @param extraOptions
   *          Set the extra compiler options for parsing or compiling this file.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setExtraOptions(String extraOptions) throws RemoteException;

  /**
   * Get the additional header files used when this file is compiled or parsed
   * 
   * @return The additional header files for this file
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<String> getAdditionalHeaderFiles() throws RemoteException;

  /**
   * Add a new addition header file that is used when this file is compiled or parsed
   * 
   * @param path
   *          The path to the header file
   * @throws RemoteException
   *           remote communication problem
   */
  public void addAdditionalHeaderFile(String path) throws RemoteException;

}
