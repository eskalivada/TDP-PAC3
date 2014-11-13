package edu.uoc.tdp.pac3.client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import edu.uoc.tdp.pac3.PeticionsException;
import edu.uoc.tdp.pac3.PeticionsInterface;


/** Classe que implementa les operacions de la part client
 * 
 * @author msolervi@uoc.edu, acamprubic@uoc.edu
 *
 */

public class Client {
	private PeticionsInterface PeticionsInterface;
	private final String propertiesFile = "properties/configuration.properties";
	
	private String URL = null;
	private int PORT = 0;
	private String JNDI_NAME = null; 
	
	public Client() {
		//	  Recuperar la informació del fitxer de properties
		Properties prop = new Properties();
		try {
			prop.load(ClassLoader.
			getSystemClassLoader().
			getResourceAsStream(propertiesFile));
			
			this.URL = prop.getProperty("server_url");
			this.PORT = new Integer(prop.getProperty("rmi_port"));
			this.JNDI_NAME = prop.getProperty("jndi_name");
			
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
	}

	public boolean connecta() throws PeticionsException
    {
      boolean vReturn=false;
      try {
    	 Registry registry = LocateRegistry.getRegistry(URL,PORT);
    	 this.PeticionsInterface=(PeticionsInterface)registry.lookup(JNDI_NAME);
    	 vReturn = true;
      } catch (NotBoundException localNotBoundException) {
    	  throw new PeticionsException(PeticionsException.ERROR_NOT_BOUND);
      } catch (RemoteException localRemoteException) {
    	  throw new PeticionsException(PeticionsException.ERROR_CONN_CLIENT_SERVER);
      }
      return vReturn;
    }
   
	public void desconnecta(){
		PeticionsInterface = null;
	
	}

	public PeticionsInterface getPeticionsInterface() {
		return PeticionsInterface;
	}


}
