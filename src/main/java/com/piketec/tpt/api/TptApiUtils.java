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
package com.piketec.tpt.api;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class contains static utility methods that can be used when working with the TPT RMI API.
 * 
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public class TptApiUtils {

  /**
   * Connect to TPT api with the given binding name and port.
   * 
   * @param binding
   *          The binding name to be used for lookup. This should match the configuration in TPT via
   *          the Preference page "TPT API".
   * @param hostname
   *          The host name to connect to. Most of the time this will be "localhost".
   * @param port
   *          The port to connect to. This should match the configuration in TPT via the Preference
   *          page "TPT API".
   * @return The {link: TptApi} object which is the root object for all further operations with the
   *         TPT API
   * @throws RemoteException
   *           remote communication problem
   * @throws NotBoundException
   *           If There is no binding with the given binding name
   */
  public static TptApi connect(String binding, String hostname, int port)
      throws RemoteException, NotBoundException {
    Registry registry = LocateRegistry.getRegistry(hostname, port);
    Remote apiObj = registry.lookup(binding);
    if (apiObj instanceof TptApi) {
      return (TptApi)apiObj;
    } else {
      throw new ApiException("Remote reference with binding \"" + binding + "\" on port " + port
          + "is not an instance of \"" + TptApi.class.getName() + "\"");
    }
  }

  /**
   * Connect to TPT api with the given binding name and port. As hostname "localhost" is assumed.
   * 
   * @param binding
   *          The binding name to be used for lookup. This should match the configuration in TPT via
   *          the Preference page "TPT API".
   * @param port
   *          The port to connect to. This should match the configuration in TPT via the Preference
   *          page "TPT API".
   * @return The {link: TptApi} object which is the root object for all further operations with the
   *         TPT API
   * @throws RemoteException
   *           remote communication problem
   * @throws NotBoundException
   *           If There is no binding with the given binding name
   */
  public static TptApi connect(String binding, int port) throws RemoteException, NotBoundException {
    return connect(binding, "localhost", port);
  }
}
