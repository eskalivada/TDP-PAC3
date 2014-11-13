package edu.uoc.tdp.pac3.server;

import java.io.FileNotFoundException;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import edu.uoc.tdp.pac3.PeticionsException;
import edu.uoc.tdp.pac3.server.entitats.*;

/** Classe que gestiona la comunicació amb la BDD
 * 
 * @author msolervi@uoc.edu, acamprubic@uoc.edu
 *
 */

public class GestorBDD {
	private final String fitxerPropietats= "properties/configuration.properties";
	private Connection connexio;
	
	public GestorBDD() throws PeticionsException {
		super();
		connectarBDD();
	}
	
	/** Mètode que estableix la connexió amb la Base de dades
	 * 
	 * @return retorna cert si ha tingut èxit en la connexió, fals si no ha estat així
	 * @throws PeticionsException
	 */
	public boolean connectarBDD() throws PeticionsException {
		boolean ret=false;
		try {
		Properties prop = new Properties();
		prop.load(ClassLoader.getSystemClassLoader().getResourceAsStream(fitxerPropietats));
		
		String url = prop.getProperty("url");
		String usuari= prop.getProperty("username");
		String clau= prop.getProperty("password");
		
		Class.forName(prop.getProperty("odbc_driver"));
		
		connexio=DriverManager.getConnection(url, usuari, clau);
		ret=true;
		}
		catch (ClassNotFoundException exc) {
			throw new PeticionsException(PeticionsException.DRIVER_NOT_FOUND);
		}
		catch (FileNotFoundException exc) {
		//	No s'ha trobat el fitxer de configuració
		throw new PeticionsException(PeticionsException.CONFPROP_FILE_NOT_FOUND);
		}
		catch (IOException exc) {
		//	El fitxer de configuració no és correcte
		throw new PeticionsException(PeticionsException.INCORRECT_PROPERTIES_FILE);
		}
		catch (SQLException exc) {
		//	Error de connexió a la BDD
		throw new PeticionsException(PeticionsException.ERROR_CONNEXIO_BBDD);
		}
		catch (Exception exc){
		//	Altres excepcions
		throw new PeticionsException(PeticionsException.EXCEPCIO_INESPERADA);
		}
		return ret;
	}
	
	public boolean altaCampanya(String nom, Date dataInici, Date dataFi) throws PeticionsException {
		String insert_str = "INSERT INTO campanya " +
	               " (id_campanya, fecha_desde, fecha_hasta, fecha_alta, nombre) " +
	               " VALUES " +
	               "( ?,?,?,?,? )";
		int idCampanya=getLastCampanyaId()+1;
		
		PreparedStatement vPreparedStatement = null;
		
		Calendar cal = Calendar.getInstance();
		Date dataAlta = new Date(cal.getTime().getTime());
		
		try{
			vPreparedStatement = this.connexio.prepareStatement( insert_str,
													 ResultSet.TYPE_SCROLL_INSENSITIVE,
													 ResultSet.CONCUR_UPDATABLE);
			vPreparedStatement.setInt(1, idCampanya);
			vPreparedStatement.setDate(2, dataInici);
			vPreparedStatement.setDate(3, dataFi);
			vPreparedStatement.setDate(4, dataAlta);
			vPreparedStatement.setString(5, nom);
			vPreparedStatement.execute();
			return true;
		}
		catch (SQLException exceptions_insert_peticio_recurs) {
			throw new PeticionsException(PeticionsException.ERROR_INS_CAMPANYA);
		}
	}
	
	public boolean altaPeticio (int idEmpleat, int idTaller, int idCampanya) throws PeticionsException {
		String insert_str = "INSERT INTO peticion " +
	               " (id_taller, id_campanya, id_empleado, fecha_peticion, puntuacion) " +
	               " VALUES " +
	               "( ?,?,?,?,? )";
		PreparedStatement vPreparedStatement = null;
		
		Calendar cal = Calendar.getInstance();
		Date dataAlta = new Date(cal.getTime().getTime());
		int puntuacio = getPuntuacio (idTaller);
		
		try{
			vPreparedStatement = this.connexio.prepareStatement( insert_str,
													 ResultSet.TYPE_SCROLL_INSENSITIVE,
													 ResultSet.CONCUR_UPDATABLE);
			vPreparedStatement.setInt(1, idTaller);
			vPreparedStatement.setInt(2, idCampanya);
			vPreparedStatement.setInt(3, idEmpleat);
			vPreparedStatement.setDate(4, dataAlta);
			vPreparedStatement.setInt(5, puntuacio);
			vPreparedStatement.execute();
			return true;
		}
		catch (SQLException exceptions_insert_peticio_recurs) {
			throw new PeticionsException(PeticionsException.ERROR_INS_CAMPANYA);
		}
	}
	
	/** Métode que retorna la petició amb idTaller i idCampanya
	 * 
	 * @param idTaller identificador del taller
	 * @param idCampanya idetificador de la Campanya
	 * @return retorna la petició amb idTaller i idCampanya
	 * @throws PeticionsException
	 */
	
/**	public DefaultTableModel setFiles(int id) throws PeticionsException{
		Statement st = null;
		ResultSet rs = null;
		DefaultTableModel Taula = new DefaultTableModel();
		
		try{
			st = connexio.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
			StringBuffer query_string = new StringBuffer();
			query_string.append("SELECT * FROM peticion WHERE id_campanya =" + id);
			rs = st.executeQuery(query_string.toString());
			Object datos[]=new Object[5];
			while (rs.next()){
				datos[0]= rs.getInt("id_taller");
				datos[1]=rs.getInt("id_campanya");
				datos[2]=rs.getInt("id_empleado");
				datos[3]=rs.getDate("fecha_peticion");
				datos[4]=rs.getInt("puntuacion");
				Taula.addRow(datos);
			}
			
		}catch(SQLException e){
				throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		
		return Taula;
	}**/
	
	public Peticio getPeticio(int idTaller, int idCampanya) throws PeticionsException {
		Statement st = null;
		ResultSet rs = null;
		Peticio peticio = null;
		
		try{
			st = connexio.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
			StringBuffer query_string = new StringBuffer();
			query_string.append("SELECT * FROM peticion WHERE id_taller =" + idTaller + "AND id_campanya =" + idCampanya);
			rs = st.executeQuery(query_string.toString());
			if (rs.next()){
				peticio = new Peticio(rs.getInt("id_taller"),
		        		rs.getInt("id_campanya"),
		        		rs.getInt("id_empleado"),
		        		rs.getDate("fecha_peticion"),
		        		rs.getInt("puntuacion"));
			}
			else throw new PeticionsException(PeticionsException.SQL_NO_DATA_FOUND);
		}
		catch(SQLException e){
				throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return peticio;
	}
	
		
	/**metode que retorna l'empleat amb idEmpleat
	 * 
	 * @param idEmpleat identificador de l'empleat
	 * @return retorna l'empleat amb idEmpleat
	 * @throws PeticionsException
	 */
	public Empleat getEmpleat(int idEmpleat) throws PeticionsException {
		Statement st = null;
		ResultSet rs = null;
		Empleat empleat = null;
		
		try{
			st = connexio.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
			StringBuffer query_string = new StringBuffer();
			query_string.append("SELECT * FROM empleado WHERE id_empleado =" + idEmpleat);
			rs = st.executeQuery(query_string.toString());
			if (rs.next()){
		        empleat = new Empleat(rs.getInt("id_empleado"),
		        		rs.getInt("id_taller"),
		        		rs.getString("nombre"),
		        		rs.getString("apellidos"),
		        		rs.getDate("fecha_alta"));
			}
			else throw new PeticionsException(PeticionsException.SQL_NO_DATA_FOUND);
		}
		catch(SQLException e){
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return empleat;
	}
	
	/**Mètode que retorna el taller amb idTaller
	 * 
	 * @param idTaller identificador del taller
	 * @return retorna el taller amb idTaller
	 * @throws PeticionsException
	 */
	public Taller getTaller(int idTaller) throws PeticionsException {
		Statement st = null;
		ResultSet rs = null;
		Taller taller = null;
		
		try {
			st = connexio.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
			StringBuffer query_string = new StringBuffer();
			query_string.append("SELECT * FROM taller WHERE id_taller =" + idTaller);
			rs = st.executeQuery(query_string.toString());
			if (rs.next()){
				taller = new Taller(rs.getInt("id_taller"),
		        		rs.getString("nombre"),
		        		rs.getString("direccion"));
			}
			else throw new PeticionsException(PeticionsException.SQL_NO_DATA_FOUND);
		}
		catch(SQLException e){
				throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return taller;
	}
	
	/**Mètode que retorna la campanya amb idCampanya
	 * 
	 * @param idCampanya Identificador de la campanya
	 * @return retorna la campanya amb idCampanya
	 * @throws PeticionsException
	 */
	public Campanya getCampanya(int idCampanya) throws PeticionsException {
		Statement st = null;
		ResultSet rs = null;
		Campanya campanya = null;
		
		try{
			st = connexio.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_UPDATABLE);
			StringBuffer query_string = new StringBuffer();
			query_string.append("SELECT * FROM campanya WHERE id_campanya =" + idCampanya);
			rs = st.executeQuery(query_string.toString());
			if (rs.next()){
		        campanya = new Campanya(rs.getInt("id_campanya"),
		        		rs.getDate("fecha_desde"),
		        		rs.getDate("fecha_hasta"),
		        		rs.getDate("fecha_alta"),
		        		rs.getString("nombre"));
		    }
			else throw new PeticionsException(PeticionsException.SQL_NO_DATA_FOUND);
		}	 
		catch(SQLException e){
				throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return campanya;
	}
	
	/**Métode que retorna tot el llistat de peticions
	 * 
	 * @return retorna tot el llistat de peticions
	 * @throws PeticionsException
	 */
	public List<Peticio> getLlistaPeticions() throws PeticionsException{
		Statement st = null;
		ResultSet rs = null;
		List<Peticio> llista = new ArrayList<Peticio>();
		
		try {
			st = connexio.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
				StringBuffer query_string = new StringBuffer();
				query_string.append("SELECT * FROM peticion");
				rs = st.executeQuery(query_string.toString());
			while (rs.next()) {
				Peticio peticio = new Peticio();
				peticio.setIdTaller(rs.getInt("id_taller"));
				peticio.setIdCampanya(rs.getInt("id_campanya"));
				peticio.setIdEmpleat(rs.getInt("id_empleado"));
				peticio.setDataPeticio(rs.getDate("fecha_peticion"));
				peticio.setPuntuacio(rs.getInt("puntuacion"));
				llista.add(peticio);
			}
			
		}catch (SQLException e) {
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return llista;
	}

	
	public List<Peticio> getPeticionsCampanya(int idCampanya) throws PeticionsException{
		Statement st = null;
		ResultSet rs = null;
		List<Peticio> llista = new ArrayList<Peticio>();
		
		try {
			st = connexio.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
				StringBuffer query_string = new StringBuffer();
				query_string.append("SELECT * FROM peticion WHERE id_campanya =" +idCampanya+ "ORDER BY puntuacion DESC");
				rs = st.executeQuery(query_string.toString());
			while (rs.next()) {
				Peticio peticio = new Peticio();
				peticio.setIdTaller(rs.getInt("id_taller"));
				peticio.setIdCampanya(rs.getInt("id_campanya"));
				peticio.setIdEmpleat(rs.getInt("id_empleado"));
				peticio.setDataPeticio(rs.getDate("fecha_peticion"));
				peticio.setPuntuacio(rs.getInt("puntuacion"));
				llista.add(peticio);
			}
			
		}catch (SQLException e) {
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return llista;
	}
	
	/**Métode que retorna tot el llistat de Tallers
	 * 
	 * @return retorna tot el llistat de Tallers
	 * @throws PeticionsException
	 */
	public List<Taller> getLlistaTallers() throws PeticionsException{
		Statement st = null;
		ResultSet rs = null;
		List<Taller> llista = new ArrayList<Taller>();
		
		try {
			st = connexio.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
				StringBuffer query_string = new StringBuffer();
				query_string.append("SELECT * FROM taller");
				rs = st.executeQuery(query_string.toString());
			while (rs.next()) {
				Taller taller = new Taller();
				taller.setIdTaller(rs.getInt("id_taller"));
				taller.setNomTaller(rs.getString("nombre"));
				taller.setDireccioTaller(rs.getString("direccion"));
				llista.add(taller);
			}
			
		}catch (SQLException e) {
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return llista;
	}
	
	/**Mètode que retorna el llistat amb tots els empleats
	 * 
	 * @return retorna el llistat amb tots els empleats
	 * @throws PeticionsException
	 */
	public List<Empleat> getLlistaEmpleats() throws PeticionsException{
		Statement st = null;
		ResultSet rs = null;
		List<Empleat> llista = new ArrayList<Empleat>();
		
		try {
			st = connexio.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
				StringBuffer query_string = new StringBuffer();
				query_string.append("SELECT * FROM empleado");
				rs = st.executeQuery(query_string.toString());
			while (rs.next()) {
				Empleat empleat = new Empleat();
				empleat.setIdEmpleat(rs.getInt("id_empleado"));
				empleat.setIdTaller(rs.getInt("id_taller"));
				empleat.setNom(rs.getString("nombre"));
				empleat.setCognom(rs.getString("apellidos"));
				empleat.setDataAlta(rs.getDate("fecha_alta"));
				llista.add(empleat);
			}
			
		}catch (SQLException e) {
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return llista;
	}
	
	/**Métode que retorna el llistat de totes les campanyes
	 * 
	 * @return retorna el llistat de totes les campanyes
	 * @throws PeticionsException
	 */
	public List<Campanya> getLlistaCampanyes() throws PeticionsException{
		Statement st = null;
		ResultSet rs = null;
		List<Campanya> llista = new ArrayList<Campanya>();
		
		try {
			st = connexio.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
				StringBuffer query_string = new StringBuffer();
				query_string.append("SELECT * FROM campanya");
				rs = st.executeQuery(query_string.toString());
			while (rs.next()) {
				Campanya campanya = new Campanya(rs.getInt("id_campanya"),
						rs.getDate("fecha_desde"),
						rs.getDate("fecha_hasta"),
						rs.getDate("fecha_alta"),
						rs.getString("nombre"));
				llista.add(campanya);
			}
			
		}catch (SQLException e) {
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return llista;
	}
	

	
	/** mètode que retorna el nombre de peticions que ha fet un taller
	 * 
	 * @param idTaller
	 * @return
	 * @throws PeticionsException
	 */
	public int getNCampanyesTaller (int idTaller) throws PeticionsException {
		int nCampanyes=0;
		Statement st = null;
		ResultSet rs = null;
			
		try {
			st = connexio.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
				StringBuffer query_string = new StringBuffer();
				query_string.append("SELECT COUNT(*) AS rowcount FROM peticion WHERE id_taller ="+ idTaller);
				rs = st.executeQuery(query_string.toString());
				rs.next();
				nCampanyes=rs.getInt("rowcount");
		}catch (SQLException e) {
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		
		return nCampanyes;
	}
	
	/**Funció que retorna el número de campanyes que se solapen amb les dates indicades
	 * 
	 * @param dataInici
	 * @param dataFi
	 * @return
	 * @throws PeticionsException
	 */
	public int getQuantitatCampanyesCoincidents (Date dataInici, Date dataFi) throws PeticionsException {
		int nCampanyes=1;
		Statement st = null;
		ResultSet rs = null;
		  
		try {
		st = connexio.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
				String query_string ="SELECT COUNT(*) AS rowcount"+
						" FROM campanya WHERE (fecha_desde BETWEEN '" + dataInici + "' AND '" + dataFi + "')"+
						" OR (fecha_hasta BETWEEN '" + dataInici + "' AND '" + dataFi + "')"+
						" OR ('" + dataInici + "' BETWEEN fecha_desde AND fecha_hasta)"+
						" OR ('" + dataFi + "' BETWEEN fecha_desde AND fecha_hasta)";
				rs = st.executeQuery(query_string);
				rs.next();
				nCampanyes=rs.getInt("rowcount");
		}catch (SQLException e) {
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		
		return nCampanyes;
	}
	
	public boolean existeixPeticio (int idTaller, int idCampanya) throws PeticionsException {
		boolean existeix = true;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = connexio.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			StringBuffer query_string = new StringBuffer();
			query_string.append("SELECT COUNT(*) AS rowcount FROM peticion" +
					" WHERE id_taller = " + idTaller +
					" AND id_campanya = " + idCampanya);
			rs = st.executeQuery(query_string.toString());
			rs.next();
			if(rs.getInt("rowcount")==0){existeix=false;}
		}
		catch (SQLException e) {
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		
		return existeix;
	}
	
	private int getPuntuacio (int idTaller) throws PeticionsException {
		int puntuacio = 0;
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connexio.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			StringBuffer query_string = new StringBuffer();
			query_string.append("SELECT COUNT(*) AS rowcount FROM peticion " +
					"WHERE id_taller = " + idTaller);
			rs = st.executeQuery(query_string.toString());
			rs.next();
			if(rs.getInt("rowcount")==0){puntuacio=1;}
			else if (rs.getInt("rowcount")==1){puntuacio=3;}
			else if (rs.getInt("rowcount")>=2){puntuacio=6;}
		}
		catch (SQLException e) {
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return puntuacio;
	}
	
	private int getLastCampanyaId () throws PeticionsException {
		int maxId = 0;
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connexio.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			StringBuffer query_string = new StringBuffer();
			query_string.append("SELECT MAX(id_campanya) AS max FROM campanya");
			rs = st.executeQuery(query_string.toString());
			rs.next();
			maxId=rs.getInt("max");
		}
		catch (SQLException e) {
			throw new PeticionsException(PeticionsException.UNKNOW_SQL_ERROR);
		}
		return maxId;
	}
	
}
