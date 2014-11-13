package edu.uoc.tdp.pac3;

import javax.swing.JOptionPane;

/** Classe que recull totes les excepcions que es puguin donar al sistema i que mostra el missatge
 * corresponent implementant el mètode i18N
 * 
 * @author msolervi@uoc.edu, acamprubic@uoc.edu
 *
 */

public class PeticionsException extends Exception{
	private static final long serialVersionUID = 1L;
	public static final String DRIVER_NOT_FOUND = TDSLanguageUtils.getMessage("DRIVER_NOT_FOUND");
	public static final String CONFPROP_FILE_NOT_FOUND = TDSLanguageUtils.getMessage("CONFPROP_FILE_NOT_FOUND");
	public static final String INCORRECT_PROPERTIES_FILE = TDSLanguageUtils.getMessage("INCORRECT_PROPERTIES_FILE");
    public static final String ERROR_CONNEXIO_BBDD = TDSLanguageUtils.getMessage("ERROR_CONNEXIO_BBDD");
    public static final String EXCEPCIO_INESPERADA = TDSLanguageUtils.getMessage("EXCEPCIO_INESPERADA");
  
    public static final String SQL_NO_DATA_FOUND = TDSLanguageUtils.getMessage("SQL_NO_DATA_FOUND");
    public static final String UNKNOW_SQL_ERROR = TDSLanguageUtils.getMessage("UNKNOW_SQL_ERROR");
   
    
    public static final String ERROR_CONN_CLIENT_SERVER = TDSLanguageUtils.getMessage("ERROR_CONN_CLIENT_SERVER");
    public static final String ERROR_NOT_BOUND = TDSLanguageUtils.getMessage("ERROR_NOT_BOUND");
    public static final String ERR_REMOTE_PROCESS = TDSLanguageUtils.getMessage("ERR_REMOTE_PROCESS");
    public static final String UNKNOW_ERROR = TDSLanguageUtils.getMessage("UNKNOW_ERROR");
    public static final String ErrorAturantServidor = TDSLanguageUtils.getMessage("ErrorAturantServidor");
    public static final String ErrorIniciantServidor = TDSLanguageUtils.getMessage("ErrorIniciantServidor");
	public static final String CAMPANYES_COINCIDENTS = TDSLanguageUtils.getMessage("CAMPANYES_COINCIDENTS");
	public static final String ERROR_INS_CAMPANYA = TDSLanguageUtils.getMessage("ERROR_INS_CAMPANYA");
	public static final String ERROR_PETICIO_JA_FETA = TDSLanguageUtils.getMessage("ERROR_PETICIO_FETA");
    public static final String ERROR_NULL_TXT = TDSLanguageUtils.getMessage("ERROR_NULL_TXT");
    public static final String ERROR_DATE_FORMAT = TDSLanguageUtils.getMessage("ERROR_DATE_FORMAT");
    public static final String ERROR_PERIOD = TDSLanguageUtils.getMessage("ERROR_PERIOD");
	
    public PeticionsException()
	{
		JOptionPane.showMessageDialog(null, EXCEPCIO_INESPERADA, "Error", 0);
	}
	
	public PeticionsException(String paramString)
	{
	  JOptionPane.showMessageDialog(null, paramString, "Error", 0);
	}
}
