package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.IdentifiableRemote;

/**
 * {@link FormalRequirementDefine define} is time- dependent assessment variable to which value
 * complex expressions can be assigned. These variables can be reused in the Expression fields of
 * all formal requirement steps of the current TPT project.
 */
public interface FormalRequirementDefine extends IdentifiableRemote {

  /**
   * Returns the name of the {@link FormalRequirementDefine define}
   * 
   * @return the name of the {@link FormalRequirementDefine define}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getName() throws RemoteException;

  /**
   * Sets the value of the {@link FormalRequirementDefine define}
   * 
   * @param value
   *          the value of the {@link FormalRequirementDefine define}
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the value is {@code null}
   */
  public void setValue(String value) throws RemoteException, ApiException;

  /**
   * Returns the value of the {@link FormalRequirementDefine define}
   * 
   * @return the value of the {@link FormalRequirementDefine define}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getValue() throws RemoteException;

}
