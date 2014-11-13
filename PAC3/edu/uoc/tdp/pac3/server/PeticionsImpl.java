package edu.uoc.tdp.pac3.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

import edu.uoc.tdp.pac3.PeticionsException;
import edu.uoc.tdp.pac3.PeticionsInterface;
import edu.uoc.tdp.pac3.server.entitats.Campanya;
import edu.uoc.tdp.pac3.server.entitats.Empleat;
import edu.uoc.tdp.pac3.server.entitats.Peticio;
import edu.uoc.tdp.pac3.server.entitats.Taller;

/** Classe que implementa la Interfície PeticionsInterface.java
 * 
 * @author msolervi@uoc.edu, acamprubic@uoc.edu
 *
 */

public class PeticionsImpl extends java.rmi.server.UnicastRemoteObject implements PeticionsInterface, Serializable{
	
	private static final long serialVersionUID = 1L;
	private GestorBDD gestorBDD = null;
	
	public PeticionsImpl() throws RemoteException, PeticionsException {
		super();
		this.gestorBDD = new GestorBDD();
	}

	/** Dona d'alta una nova campanya
	 * 
	 * @param nom Nom de la campanya
	 * @param dataInici Data d'inici en format dd/MM/aaaa
	 * @param dataFi Data de fi en format dd/MM/aaaa
	 * @return retorna true si s'ha pogut donar d'alta la campanya, false si no s'ha pogut
	 * @throws PeticionsException
	 * @throws RemoteException
	 */

	public boolean entradaCampanya (String nom, Date dataInici, Date dataFi) throws PeticionsException, RemoteException{
		boolean ret=false;
		if (gestorBDD.getQuantitatCampanyesCoincidents(dataInici,dataFi)>0)
			throw new PeticionsException(PeticionsException.CAMPANYES_COINCIDENTS);
		ret=gestorBDD.altaCampanya(nom, dataInici, dataFi);  	
		return ret;
	}

	/** Dona d'alta una nova petició
	 * 
	 * @param idEmpleat Identificador de l'empleat que fa la petició
	 * @param idTaller Identificador del taller que fa la petició
	 * @param idCampanya Identificador de la Campanya a la qual volen entrar
	 * @return retorna true si han pogut entrar a la campanya, false si ha donat algun error
	 * @throws PeticionsException
	 * @throws RemoteException
	 */

	public boolean entradaPeticions (int idEmpleat, int idTaller, int idCampanya) throws PeticionsException, RemoteException{
		boolean ret=false;
		if (gestorBDD.existeixPeticio(idTaller, idCampanya)==true)
			throw new PeticionsException(PeticionsException.ERROR_PETICIO_JA_FETA);
		ret=gestorBDD.altaPeticio(idEmpleat, idTaller, idCampanya);
		return ret;
	}

	/** Metode que retorna el llistat de peticions corresponents a un determinat Taller i Campanya
	 * 
	 * @param idTaller Identificador del taller
	 * @param idCampanya Identificador de la campanya
	 * @return llistat de peticions corresponents al taller i campanya indicats
	 * @throws PeticionsException
	 * @throws RemoteException
	 */

	public List<Peticio> consultaPeticions (int idCampanya) throws PeticionsException, RemoteException{
		return gestorBDD.getPeticionsCampanya(idCampanya);
	}

	/**Metode que retorna el llistat de totes les peticions
	 * 
	 * @return llistat de totes les peticions
	 * @throws PeticionsException
	 * @throws RemoteException
	 */
	public List<Peticio> getLlistaPeticions() throws PeticionsException, RemoteException{
		return gestorBDD.getLlistaPeticions();
	}

	/** Metode que retorna el llistat de tots els empleats
	 * 
	 * @return llistat de tots els empleats
	 * @throws PeticionsException
	 * @throws RemoteException
	 */
	public List<Empleat> getLlistaEmpleats() throws PeticionsException, RemoteException{
		return gestorBDD.getLlistaEmpleats();
	}

	/** Metode que retorna el llistat de totes les campanyes
	 * 
	 * @return llistat de totes les campanyes
	 * @throws PeticionsException
	 * @throws RemoteException
	 */
	public List<Campanya> getLlistaCampanyes() throws PeticionsException, RemoteException{
		return gestorBDD.getLlistaCampanyes();
	}

	/** Metode que retorna el llistat de tots els tallers
	 * 
	 * @return llistat de tots els tallers
	 * @throws PeticionsException
	 * @throws RemoteException
	 */
	public List<Taller> getLlistaTallers() throws PeticionsException, RemoteException{
		return gestorBDD.getLlistaTallers();
	}
	
	public List<Peticio> getPeticionsCampanya(int id) throws PeticionsException, RemoteException{
		return gestorBDD.getPeticionsCampanya(id);
	}

}
