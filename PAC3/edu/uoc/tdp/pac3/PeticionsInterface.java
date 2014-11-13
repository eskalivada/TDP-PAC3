package edu.uoc.tdp.pac3;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

import edu.uoc.tdp.pac3.server.entitats.*;

/** Classe interfície on es defineixen tots els procediments que el client pot cridar i que
 * alhora el servidor ha d'implementar
 * @author msolervi@uoc.edu, acamprubic@uoc.edu
 *
 */

public interface PeticionsInterface extends java.rmi.Remote {

/** Dona d'alta una nova campanya
 * 
 * @param nom Nom de la campanya
 * @param dataAlta Data d'inici en format dd/MM/aaaa
 * @param datafi Data de fi en format dd/MM/aaaa
 * @return retorna true si s'ha pogut donar d'alta la campanya, false si no s'ha pogut
 * @throws PeticionsException
 * @throws RemoteException
 */

public boolean entradaCampanya (String nom, Date dataAlta, Date datafi) throws PeticionsException, RemoteException;

/** Dona d'alta una nova petició
 * 
 * @param idEmpleat Identificador de l'empleat que fa la petició
 * @param idTaller Identificador del taller que fa la petició
 * @param idCampanya Identificador de la Campanya a la qual volen entrar
 * @return retorna true si han pogut entrar a la campanya, false si ha donat algun error
 * @throws PeticionsException
 * @throws RemoteException
 */

public boolean entradaPeticions (int idEmpleat, int idTaller, int idCampanya) throws PeticionsException, RemoteException;

/** Metode que retorna el llistat de peticions corresponents a una determinada Campanya
 * 
 * @param idCampanya Identificador de la campanya
 * @return llistat de peticions corresponents a la campanya indicada
 * @throws PeticionsException
 * @throws RemoteException
 */

public List<Peticio> consultaPeticions (int idCampanya) throws PeticionsException, RemoteException;

/**Metode que retorna el llistat de totes les peticions
 * 
 * @return llistat de totes les peticions
 * @throws PeticionsException
 * @throws RemoteException
 */
public List<Peticio> getLlistaPeticions() throws PeticionsException, RemoteException;

/** Metode que retorna el llistat de tots els empleats
 * 
 * @return llistat de tots els empleats
 * @throws PeticionsException
 * @throws RemoteException
 */
public List<Empleat> getLlistaEmpleats() throws PeticionsException, RemoteException;

/** Metode que retorna el llistat de totes les campanyes
 * 
 * @return llistat de totes les campanyes
 * @throws PeticionsException
 * @throws RemoteException
 */
public List<Campanya> getLlistaCampanyes() throws PeticionsException, RemoteException;

/** Metode que retorna el llistat de tots els tallers
 * 
 * @return llistat de tots els tallers
 * @throws PeticionsException
 * @throws RemoteException
 */
public List<Taller> getLlistaTallers() throws PeticionsException, RemoteException;

public List<Peticio> getPeticionsCampanya(int id) throws PeticionsException, RemoteException;

}
