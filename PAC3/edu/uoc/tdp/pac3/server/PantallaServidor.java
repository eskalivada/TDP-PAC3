package edu.uoc.tdp.pac3.server;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.uoc.tdp.pac3.PeticionsException;
import edu.uoc.tdp.pac3.TDSLanguageUtils;
import edu.uoc.tdp.pac3.server.Servidor;

/** Classe que implementa la interfície gràfica de la part servidor
 * 
 * @author msolervi@uoc.edu, acamprubic@uoc.edu
 *
 */

public class PantallaServidor extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel lblServerStatus = null;
	private JButton btnStartServer = null;
	private JButton btnStopServer = null;
	private Servidor servidor = null;
	

	public static void main(String[] paramArrayOfString) {
	    if (paramArrayOfString.length == 0) 
	      TDSLanguageUtils.setDefaultLanguage("i18n/messages");
	
	    if (paramArrayOfString.length == 1) {
	    	Locale localLocale = new Locale(paramArrayOfString[0]);
	    	TDSLanguageUtils.setLanguage("i18n/messages", localLocale);
	    }
	    
	    SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
	        PantallaServidor ps;
			try {
				ps = new PantallaServidor();
		        ps.setDefaultCloseOperation(3);
		        ps.setVisible(true);
			} catch (PeticionsException e) {
				e.printStackTrace();
			}
	    }
	    });
	}
	
	/**
	 * Mètode que inicialitza el botó d'inici del servidor	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnStartServer() {
		if (btnStartServer == null) {
			btnStartServer = new JButton();
			btnStartServer.setBounds(new java.awt.Rectangle(20,40,150,30));
			btnStartServer.setText(TDSLanguageUtils.getMessage("BotoIniciar"));
			btnStartServer.setEnabled(true);
			if (Servidor.ConnexioEstablerta) btnStartServer.setEnabled(false);
			btnStartServer.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
		          try {
		        	 servidor.iniciar();
		        	 btnStartServer.setEnabled(false);
		        	 btnStopServer.setEnabled(true);
		        	 lblServerStatus.setText(TDSLanguageUtils.getMessage("EstatServidorIniciat"));
		          } catch (Exception gre) {
		        	  //TODO: Cambiar el tipus d'exception desde la classe Servidor
		              gre.printStackTrace(System.err);
		          }
		      }
		     });
		}
		return btnStartServer;
	}

	/**
	 * Mètode que inicialitza el botó d'aturada del servidor	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnStopServer() {
		if (btnStopServer == null) {
			btnStopServer = new JButton();
			btnStopServer.setBounds(new java.awt.Rectangle(200,40,150,30));
			btnStopServer.setText(TDSLanguageUtils.getMessage("BotoAturar"));
			btnStopServer.setEnabled(false);
			if (Servidor.ConnexioEstablerta) btnStopServer.setEnabled(true);
			btnStopServer.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
		          try {
		        	 try{
		        	  servidor.atura();
		        	 }catch(PeticionsException exc){
		        		 throw new PeticionsException(PeticionsException.DRIVER_NOT_FOUND);
		        	 }
		        	 btnStartServer.setEnabled(true);
		        	 btnStopServer.setEnabled(false);
		        	 lblServerStatus.setText(TDSLanguageUtils.getMessage("EstatServidorAturat"));
		          } catch (Exception e) {
//		        	TODO: Cambiar el tipus d'exception desde la classe Servidor
		        	  e.printStackTrace();
	 	 			 
		            }
		          }
		        }
		      );
		}
		return btnStopServer;
	}

	/**
	 * Mètode que crea el constructor per defecte, sense paràmetres
	 * @throws PeticionsException 
	 */
	public PantallaServidor() throws PeticionsException {
		super();
		initialize();		
	}
	

	/**
	 * Mètode que inicialitza 'this'
	 * 
	 * @return void
	 * @throws PeticionsException 
	 */
	private void initialize() throws PeticionsException {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(382, 114);
		this.setLocation(new Point(320, 100));
		this.setResizable(false);
	    String str = TDSLanguageUtils.getMessage("TitolServidor");
	    this.setTitle(str);
	    this.setContentPane(getJContentPane());
		
		try {
			servidor = new Servidor();
		} catch (IOException e) {
			throw new PeticionsException(PeticionsException.ERROR_CONN_CLIENT_SERVER);// TODO Auto-generated catch block
			
		}
		
	}

	/**
	 * Mètode que inicialitza el contenidor de la pantalla de connexió al servidor
	 * 
	 * @return javax.swing.JPanel
	 */


	private JPanel getJContentPane() {
		if (this.jContentPane == null) {
			this.jContentPane = new JPanel();
			this.jContentPane.setLayout(null);
			lblServerStatus = new JLabel();
			lblServerStatus.setBounds(new java.awt.Rectangle(10,10,500,20));
			lblServerStatus.setText(TDSLanguageUtils.getMessage("EstatServidorPendent"));


			jContentPane.add(lblServerStatus, null);
			this.jContentPane.add(getBtnStartServer(), null);
			this.jContentPane.add(getBtnStopServer(), null);
		}
		return jContentPane;
	}
	  
}  

