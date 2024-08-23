package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;
import java.util.List;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.IdentifiableRemote;
import com.piketec.tpt.api.Requirement;

/**
 * A step is one entry in a @{@link List} of @{@link ConditionTreeNode} in {@link Requirement} it
 * can be created via {@code addConditionTreeNode(String type)}.
 */
public interface ConditionTreeNode extends IdentifiableRemote {

  /**
   * @return the name of the step type.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getType() throws RemoteException;

  /**
   * TPT offers the possibility to assign some objects comments which are added to the report. This
   * method provides the possibility to define such a {@link String}.
   * 
   * @param documentation
   *          a {@link String} which will be added as a comment to the report.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the documentation is {@code null}
   */
  public void setDocumentation(String documentation) throws RemoteException, ApiException;

  /**
   * @return a {@link String} which will be added as a comment to the report.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDocumentation() throws RemoteException;

  /**
   * Sets if this step is enabled.
   * 
   * @param value
   *          <code>true</code> if this step is active/enabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setActive(boolean value) throws RemoteException;

  /**
   * Determines if this step is enabled.
   *
   * @return <code>true</code> if this step is active/enabled
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isActive() throws RemoteException;

  /**
   * @return the hierarchy level (indent) of the FormalRequiermentStep. The hierarchy level starts
   *         from 0 and counts upwards. The indentation concatenates the indented step with the
   *         parent step using boolean AND. Formal requirement steps at the same indentation level
   *         are concatenated using boolean OR.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public int getHierarchyLevel() throws RemoteException;

  /**
   * Sets the hierarchy level (indent) of the FormalRequiermentStep. The hierarchy level starts from
   * 0 and counts upwards. The indentation concatenates the indented step with the parent step using
   * boolean AND. Formal requirement steps at the same indentation level are concatenated using
   * boolean OR.
   * 
   * @param level
   *          the hierarchy level.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setHierarchyLevel(int level) throws RemoteException;

}
