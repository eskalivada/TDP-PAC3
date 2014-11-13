package edu.uoc.tdp.pac3.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import edu.uoc.tdp.pac3.PeticionsException;
import edu.uoc.tdp.pac3.PeticionsInterface;
import edu.uoc.tdp.pac3.server.PeticionsImpl;


/** Classe que implementa les operacions de la part servidor
 * 
 * @author msolervi@uoc.edu, acamprubic@uoc.edu
 *
 */

public class Servidor {
	
	private static final long serialVersionUID = 1L;
	private final String propertiesFile = "properties/configuration.properties";
	
	public static boolean ConnexioEstablerta = false;
	
	private int PORT;
	private String JNDI_NAME;
	private PeticionsInterface remot = null;
	private Registry registry = null;
	
	public Servidor() throws IOException, PeticionsException{
		try {
			Properties prop = new Properties();
			prop.load(ClassLoader.
			getSystemClassLoader().
			getResourceAsStream(propertiesFile));
		
			this.PORT = new Integer(prop.getProperty("rmi_port"));
			this.JNDI_NAME = prop.getProperty("jndi_name");
		}
		catch (NullPointerException e){
			throw new PeticionsException(PeticionsException.CONFPROP_FILE_NOT_FOUND);
		}
		catch (NumberFormatException ne){
			throw new PeticionsException(PeticionsException.INCORRECT_PROPERTIES_FILE);
		}
		
	}
	
	/**
	 * Launch the application.
	 * @throws PeticionsException 
	 */
	
	public void iniciar() throws PeticionsException {
		try {
			try{
				registry = LocateRegistry.createRegistry(PORT);
				remot = new PeticionsImpl();
				registry.rebind(JNDI_NAME, remot);
			}catch(RemoteException e){
				registry = LocateRegistry.getRegistry(PORT);
				registry.rebind(JNDI_NAME, remot);
				
			}
		} catch (RemoteException e) {
			throw new PeticionsException(PeticionsException.ErrorIniciantServidor);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void atura() throws PeticionsException {
		//conecta servidor
		try {
			registry.unbind(JNDI_NAME);
		} catch (RemoteException e) {
			throw new PeticionsException(PeticionsException.ErrorAturantServidor);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
