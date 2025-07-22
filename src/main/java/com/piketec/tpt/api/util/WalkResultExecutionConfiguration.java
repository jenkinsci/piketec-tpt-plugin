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
package com.piketec.tpt.api.util;

import java.util.List;

import com.piketec.tpt.api.ExecutionConfiguration;
import com.piketec.tpt.api.ExecutionConfigurationGroup;
import com.piketec.tpt.api.ExecutionConfigurationOwner;

/**
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class WalkResultExecutionConfiguration extends
    WalkResult<ExecutionConfigurationOwner, ExecutionConfigurationGroup, ExecutionConfiguration> {

  private static final long serialVersionUID = 1L;

  /**
   * @param root
   *          root group of execution configurations, to which the {@link #getGroups()} and
   *          {@link #getElements()} directly belong to
   * @param execConfigGroups
   *          all sub-groups of execution configurations contained in the current "root" group
   * @param execConfigs
   *          all execution configurations contained in the current "root" group
   */
  public WalkResultExecutionConfiguration(ExecutionConfigurationOwner root,
                                          List<ExecutionConfigurationGroup> execConfigGroups,
                                          List<ExecutionConfiguration> execConfigs) {
    super(root, execConfigGroups, execConfigs);
  }

  /**
   * the group of execution configurations, to which the {@link #getGroups()} and
   * {@link #getElements()} directly belong to
   */
  @Override
  public ExecutionConfigurationOwner getRoot() {
    return super.getRoot();
  }

  /**
   * all execution configurations contained in the current "root" group
   */
  @Override
  public List<ExecutionConfiguration> getElements() {
    return super.getElements();
  }

  /**
   * all sub-groups of execution configurations contained in the current "root" group
   */
  @Override
  public List<ExecutionConfigurationGroup> getGroups() {
    return super.getGroups();
  }
}
