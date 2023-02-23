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
package com.piketec.tpt.api.importinterface;

import java.io.Serializable;

import com.piketec.tpt.api.Mapping;
import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.Project.SynchronizationMethod;

/**
 * Settings to use for {@link Project#importIO(ImportInterfaceSettings)}. Use a specific sub class
 * to choose an importer.
 *
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public abstract class ImportInterfaceSettings implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The mapping to read mapping information from and/or write new information to. Can be
   * <code>null</code> to create a new mapping.
   */
  private Mapping mappingOrNull;

  /**
   * The {@link SynchronizationMethod} for matching existing objects to imported objects.
   */
  private SynchronizationMethod synchronizationMethod = SynchronizationMethod.EXTERNAL_NAME;

  /**
   * Returns the mapping to read mapping information from and/or write new information to. If
   * <code>null</code> a new mapping will be crated.
   * 
   * @return the Mapping or null
   */
  public Mapping getMappingOrNull() {
    return mappingOrNull;
  }

  /**
   * Sets the mapping to read mapping information from and/or write new information to. Can be
   * <code>null</code> to create a new mapping.
   * 
   * @param mappingOrNull
   *          the Mapping to set
   */
  public void setMappingOrNull(Mapping mappingOrNull) {
    this.mappingOrNull = mappingOrNull;
  }

  /**
   * Gets the {@link SynchronizationMethod} for matching existing objects to imported objects.
   * 
   * @return the SynchronizationMethod
   */
  public SynchronizationMethod getSynchronizationMethod() {
    return synchronizationMethod;
  }

  /**
   * Sets the {@link SynchronizationMethod} for matching existing objects to imported objects.
   * 
   * @param syncMethod
   *          the SynchronizationMethod to set
   */
  public void setSynchronizationMethod(SynchronizationMethod syncMethod) {
    this.synchronizationMethod = syncMethod;
  }

}
