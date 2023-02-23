package com.piketec.tpt.api;

import java.rmi.RemoteException;

/**
 * the util toolbox is just a library containing a set of auxillary functions for the TPT API. These
 * auxiallaries are not directly related to TPT projects and/or the TPT tool.
 */
public interface UtilToolbox extends TptRemote {

  /**
   * This function removes all markup code contained in <code>markup</code> to specify styles like
   * <code>*</code>, <code>**</code>, <code>++</code> from the text.
   * 
   * @param markup
   *          markup text to be converted to plain text
   * 
   * @return Returns the plain text of a given markup text.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String transformMarkupToPlainText(String markup) throws RemoteException;

}
