package com.piketec.jenkins.plugins.tpt.api.callables;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.HashSet;

import com.piketec.jenkins.plugins.tpt.TptLogger;
import com.piketec.jenkins.plugins.tpt.Utils;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.ExecutionConfiguration;
import com.piketec.tpt.api.ExecutionConfigurationItem;
import com.piketec.tpt.api.OpenResult;
import com.piketec.tpt.api.Project;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.TptApi;

import hudson.FilePath;
import hudson.model.TaskListener;
import hudson.remoting.Callable;

/**
 * This class can open a TPT API connection. It is a Callable, which means it will be executed on a Jenkins Agent.
 * This is necessary, because we want to use the TPT API only via localhost and not via another remote connection.
 * Every request that is made to the TPT API extends this class.
 * 
 * @param <S> is the return type of the call method, i.e. the type of whatever you want to get from the TPT API.
 * 						NOTE: This type must be Serializable
 */
public abstract class TptApiCallable<S> implements  Callable<S, UnknownHostException>, Serializable{
	
	private static final long serialVersionUID = 1L;

	private TaskListener listener;
	private String hostName;
	private int tptPort;
	private String tptBindingName;
	private FilePath[] exePaths;
	private long startUpWaitTime;

	public TptApiCallable(TaskListener listener, String hostName, int tptPort, String tptBindingName,
			FilePath[] exePaths, long startUpWaitTime) {
		this.listener = listener;
		this.hostName = hostName;
		this.tptPort = tptPort;
		this.tptBindingName = tptBindingName;
		this.exePaths = exePaths;
		this.startUpWaitTime = startUpWaitTime;
	}
	
	/**
	 * @return a logger that prints its log messages live on the Jenkins Agent
	 */
	public TptLogger getLogger() {
		return new TptLogger(listener.getLogger());
	}
	
	/**
	 * @return a TPT API connection for the settings given in the constructor
	 */
  public TptApi getApi() throws UnknownHostException  {
  	TptLogger logger = getLogger();
  	logger.info("Try to connect to " + hostName + ":" + tptPort);
  	logger.info("TPT Binding name: " + tptBindingName);
  	try {
  		return connectToTPT();
  	} catch (RemoteException | NotBoundException e) {
  	// NOP, start TPT and try again
  	}
  	// start TPT and try again
  	if (!startTpt(startUpWaitTime)) {
	  	logger.error("Could not start TPT");
	  	return null;
  	}
  	try {
  		return connectToTPT();
  	} catch (RemoteException | NotBoundException e) {
	  	logger.error(e.getMessage());
	  	return null;
  	}
  }

	private  TptApi connectToTPT() throws RemoteException, NotBoundException, AccessException {
		TptLogger logger = getLogger();
		Registry registry;
		registry = LocateRegistry.getRegistry(hostName, tptPort);
		TptApi remoteApi = (TptApi)registry.lookup(tptBindingName);
		try {
			logger.info("Connected to TPT \"" + remoteApi.getTptVersion() + "\"");
		} catch (ApiException e) {
			logger.error(e.getMessage());
			// should not happen
		}
		return remoteApi;
	}
  
	private boolean startTpt(long startupWaitTime) {
		TptLogger logger = getLogger();
		FilePath exeFile = null;
    for (FilePath f : exePaths) {
      try {
        if (f.exists()) {
          exeFile = f;
          break;
        }
      } catch (IOException| InterruptedException e) {
        // NOP, just try next file
      }
    }
    try {
      if (exeFile == null || !exeFile.exists()) {
        logger.error("TPT exe not found.");
        return false;
      }
    } catch (IOException | InterruptedException e1) {
      logger.error("Could not dertmine existence of TPT.");
      return false;
    }
		ProcessBuilder builder = new ProcessBuilder(
	      "cmd.exe", "/c",exeFile.getRemote()+" --apiPort "+ Integer.toString(tptPort) +  " --apiBindingName "+ tptBindingName);
    try {
    	logger.info("Waiting " + startupWaitTime / 1000 + "s for TPT to start.");
      builder.start();
    } catch (IOException e) {
      logger.error("Could not start TPT.");
      return false;
    }
    try {
			Thread.sleep(startupWaitTime);
		} catch (InterruptedException e) {
			logger.error("Interrupt: "+e.getMessage());
		}
    return true;
	}

	/**
	 * Open the given TPT Project via the TPT API
 	 */
	OpenResult getOpenProject(TptLogger logger, TptApi api, FilePath tptFilePath) {
		 //Open the TPT Project via the TPT-API
    OpenResult openProject = null;
    try {
    	openProject = api.openProject(new File(tptFilePath.getRemote()));
      if (openProject.getProject() == null) {
        logger.error("Could not open project:\n" + Utils.toString(openProject.getLogs(), "\n"));
        return null;
      }
      return openProject;
    } catch (RemoteException e) {
      logger.error("RemoteException: "+e.getMessage());
      return null;
    } catch (ApiException e) {
      logger.error("ApiException: "+e.getMessage());
      return null;
		} catch (IOException e) {
			logger.error("IOException "+e.getMessage());
      return null;
		}
	}
	
	/**
   * Looks in the Tpt project if there is such Execution Configuration
   * 
   * @param project
   *          , TptProject
   * @param exeConfigName
   *          , the name of the Execution Configuration
   * 
   * @return the ExecutionConfiguration if found, null otherwise
   */
  ExecutionConfiguration getExecutionConfigByName(Project project, String exeConfigName)
      throws RemoteException, ApiException {
    Collection<ExecutionConfiguration> execConfigs =
        project.getExecutionConfigurations().getItems();
    for (ExecutionConfiguration elem : execConfigs) {
      if (elem.getName().equals(exeConfigName)) {
        return elem;
      }
    }
    return null;
  }


  /**
   * Get the testcases from an Execution configuration.
   * 
   * @param execution
   *          configuration , we will iterate over its items in order to get the testcases
   * 
   * @return a list with the test cases
   */
  Collection<String> getTestCaseNames(ExecutionConfiguration config)
      throws RemoteException, ApiException {
    HashSet<String> result = new HashSet<String>();
    for (ExecutionConfigurationItem item : config.getItems()) {
      if (item.getTestSet() == null || item.getTestSet().getTestCases() == null
          || item.getTestSet().getTestCases().getItems() == null) {
        return null;
      }
      for (Scenario testcase : item.getTestSet().getTestCases().getItems()) {
        result.add(testcase.getName());
      }
    }
    return result;
  }
	
}
