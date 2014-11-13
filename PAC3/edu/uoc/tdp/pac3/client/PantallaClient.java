package edu.uoc.tdp.pac3.client;


import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.JFormattedTextField;

import edu.uoc.tdp.pac3.client.Client;
import edu.uoc.tdp.pac3.PeticionsException;
import edu.uoc.tdp.pac3.PeticionsInterface;
import edu.uoc.tdp.pac3.TDSLanguageUtils;
import edu.uoc.tdp.pac3.server.entitats.Campanya;
import edu.uoc.tdp.pac3.server.entitats.Empleat;
import edu.uoc.tdp.pac3.server.entitats.Peticio;
import edu.uoc.tdp.pac3.server.entitats.Taller;

/** Classe que implementa la interfície gràfica de la part client
 * 
 * @author msolervi@uoc.edu, acamprubic@uoc.edu
 *
 */

public class PantallaClient extends JFrame{
	
		private static final long serialVersionUID = 1L;

		private JPanel jContentPane = null;
		private JPanel jContentPaneAltaCampanya = null;
		private JPanel jContentPaneAltaPeticio = null;
		private JPanel jContentPaneConsultaPeticions = null;
		private JPanel jContentPaneLlistaConsulta=null;
		
				
		private JMenuBar jJMenuBar = null;
		
		private JMenu iniciMenu = null;
		private JMenu gestioCampanyesMenu = null;
		
		private JMenuItem exitMenuItem = null;
		private JMenuItem AltaCampanyaMenu = null;
		private JMenuItem AltaPeticioMenu = null;
		private JMenuItem ConsultaPeticioMenu = null;
		private JMenuItem connServerMenu = null;
		
		private JComboBox cmbEmpleatAltaPeticio = null;
		private JComboBox cmbTallerAltaPeticio = null;
		private JComboBox cmbCampanyaAltaPeticio = null;
		private JComboBox cmbCampanyaConsultaPeticions = null;
		
		private PeticionsInterface PeticionsInterface;
		
		private JButton jBtnGravaAltaPeticio;
		private JButton jBtnGravaAltaCampanya;
		
		
		private JFormattedTextField jTxtNomAltaCampanya= new JFormattedTextField(new String());
		private JFormattedTextField jTxtDataIniciAltaCampanya=new JFormattedTextField(new String());
		private JFormattedTextField jTxtDataFiAltaCampanya=new JFormattedTextField(new String());
		
		private JLabel jLblNomAltaCampanya = null;
		private JLabel jLblIniciAltaCampanya = null;
		private JLabel jLblFiAltaCampanya = null;
		private JLabel jLblEmpleatAltaPeticio = null;
		private JLabel jLblTallerAltaPeticio = null;
		private JLabel jLblCampanyaAltaPeticio = null;
		private JLabel jLblTallerConsultaPeticions = null;
		private JLabel jLblCampanyaConsultaPeticions = null;
		
		private JTable TaulaConsulta = null;
						
		private Client client = null;
				
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {		
			if (args.length == 0) 
			    TDSLanguageUtils.setDefaultLanguage("i18n/messages");
			
			if (args.length == 1) {
			    Locale localLocale = new Locale(args[0]);
			    TDSLanguageUtils.setLanguage("i18n/messages", localLocale);
			}
			SwingUtilities.invokeLater(new Runnable() {
			public void run() {
					PantallaClient application = new PantallaClient();
					application.setDefaultCloseOperation(3);
					application.setVisible(true);
				}
			});
		}
		
		/**
		 * Aquest mètode obté el contingut de jTxtNomAltaCampanya	
		 * 	
		 * @return javax.swing.JTextField	
		 * @throws ParseException 
		 */
		private JFormattedTextField getJTxtNomAltaCampanya()  {
			if (jTxtNomAltaCampanya == null) {
				jTxtNomAltaCampanya = new JFormattedTextField(new String());
				jTxtNomAltaCampanya.addKeyListener(new KeyAdapter(){
					public void keyTyped(KeyEvent e){
						char c = e.getKeyChar();
						if (Character.isDigit(c) == false) {
				            e.consume();
				        }
					}
				});
			}
			return jTxtNomAltaCampanya;
		}

		/**
		 * Aquest mètode inicializat jTxtDataIniciAltaCampanya	
		 * 	
		 * @return javax.swing.JTextField	
		 * @throws ParseException 
		 */
		private JFormattedTextField getJTxtDataIniciAltaCampanya()  {
			if (jTxtDataIniciAltaCampanya == null) {
				jTxtDataIniciAltaCampanya =new JFormattedTextField (new String());
				jTxtDataIniciAltaCampanya.addKeyListener(new KeyAdapter(){
					public void keyTyped(KeyEvent e){
						char c = e.getKeyChar();
						if (Character.isDigit(c) == false) {
				            e.consume();
				        }
					}
				});
			}
			return jTxtDataIniciAltaCampanya;
		}
		
		/**
		 * Aquest mètode inicialitza jTxtDataFiAltaCampanya	
		 * 	
		 * @return javax.swing.JTextField	
		 * @throws ParseException 
		 */
		private JFormattedTextField getJTxtDataFiAltaCampanya()  {
			if (jTxtDataFiAltaCampanya == null) {
				jTxtDataFiAltaCampanya = new JFormattedTextField (new String());
				jTxtDataFiAltaCampanya.addKeyListener(new KeyAdapter(){
					public void keyTyped(KeyEvent e){
						char c = e.getKeyChar();
						if (Character.isDigit(c) == false) {
				            e.consume();
				        }
					}
				});
			}
			return jTxtDataFiAltaCampanya;
		}
		
		/**
		 * Aquest mètode inicialitza el botó Guardar anomenat jBtnGravaAltaCampanya
		 * 	
		 * @return javax.swing.JButton	
		 */
		private JButton getJBtnGravaAltaCampanya() {
			if (jBtnGravaAltaCampanya == null) {
				jBtnGravaAltaCampanya = new JButton(TDSLanguageUtils.getMessage("BTN_GUARDAR"));
				this.jBtnGravaAltaCampanya.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
			          try {

			        	  String nomCampanya = new String(PantallaClient.this.jTxtNomAltaCampanya.getText());
		        		  String iniciCampanya = new String(PantallaClient.this.jTxtDataIniciAltaCampanya.getText());
		        		  String fiCampanya = new String(PantallaClient.this.jTxtDataFiAltaCampanya.getText());
		        		  
			        	  if (!fiCampanya.trim().equals("")&&!nomCampanya.trim().equals("")&&!iniciCampanya.trim().equals("")){

			        		  if (!comprobarFormatDates(iniciCampanya)||!comprobarFormatDates(fiCampanya)){
			        			  throw new PeticionsException(PeticionsException.ERROR_DATE_FORMAT);
			        		  }
			        		  SimpleDateFormat dInici=new SimpleDateFormat("yyyy-MM-dd");
			        		  Date dataInici = null;

			        		  try{dataInici=dInici.parse(iniciCampanya);}
			        		  catch (ParseException ex){
			        			  throw new PeticionsException(PeticionsException.ERROR_DATE_FORMAT);
			        			  }
			        		  			        				  
			        		  SimpleDateFormat dfi=new SimpleDateFormat("yyyy-MM-dd");
			        		  Date dataFi = null;
			        		  
			        		  try{dataFi=dfi.parse(fiCampanya);}
			        		  catch (ParseException ex){
			        			  throw new PeticionsException(PeticionsException.ERROR_DATE_FORMAT);
			        			  }
			        		  if(dataFi.compareTo(dataInici)<0){
			        			  throw new PeticionsException(PeticionsException.ERROR_PERIOD);
			        		  }
			        		  java.sql.Date sqlDataAlta = new java.sql.Date(dataInici.getTime());
			        		  java.sql.Date sqlDatafi = new java.sql.Date(dataFi.getTime());
				        	  PeticionsInterface.entradaCampanya(nomCampanya,sqlDataAlta,sqlDatafi);
				        	  JOptionPane.showMessageDialog(null, (Object)TDSLanguageUtils.getMessage("NOVA_CAMPANYA"), null, JOptionPane.INFORMATION_MESSAGE, null);

			        	  }
			        	  else {
			        		  throw new PeticionsException(PeticionsException.ERROR_NULL_TXT);
			        	  }
			  	      } catch (PeticionsException e) {
			  				e.printStackTrace();
			  		  } catch (RemoteException re){
			  			  re.printStackTrace();

			  		  }
			        }
			      });
				
			}
			return jBtnGravaAltaCampanya;
		}

		/**
		 * This method initializes jBtnGravaAltaPeticio
		 * 	
		 * @return javax.swing.JButton	
		 */
		private JButton getJBtnGravaAltaPeticio() {
			if (jBtnGravaAltaPeticio == null) {
				jBtnGravaAltaPeticio = new JButton(TDSLanguageUtils.getMessage("BTN_GUARDAR"));
				this.jBtnGravaAltaPeticio.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
			          try {
			        	  //aquí no recupera correctament el nom
			        	  //String txt_Empleat = PantallaClient.this.cmbEmpleatAltaPeticio.getToolTipText();
			        			  
			        	  //if (!txt_Empleat.trim().equals("")){
			        		 ComboItem cmbEmpleatSelected =(ComboItem)PantallaClient.this.cmbEmpleatAltaPeticio.getSelectedItem();
			        		 ComboItem cmbTallerSelected =(ComboItem)PantallaClient.this.cmbTallerAltaPeticio.getSelectedItem();
			        		 ComboItem cmbCampanyaSelected =(ComboItem)PantallaClient.this.cmbCampanyaAltaPeticio.getSelectedItem();
				        	 PeticionsInterface.entradaPeticions(cmbEmpleatSelected.id,cmbTallerSelected.id,cmbCampanyaSelected.id);
				        	 JOptionPane.showMessageDialog(null, (Object)TDSLanguageUtils.getMessage("NOVA_PETICIO"), null, JOptionPane.INFORMATION_MESSAGE, null);

			  	      } catch (PeticionsException e) {
			  				e.printStackTrace();
			  		  } catch (RemoteException re){
			  			  re.printStackTrace();
			  		  }
			        }
			      });
				
			}
			return jBtnGravaAltaPeticio;
		}
		
		private int getPeticio() {
			     ComboItem cmbCampanyaSelected =(ComboItem)PantallaClient.this.cmbCampanyaAltaPeticio.getSelectedItem();
				   
			return cmbCampanyaSelected.id;
		}
		
		
		/**
		 * This is the default constructor
		 */
		public PantallaClient() {
			super();
			initialize();
		}

		/**
		 * This method initializes this
		 * 
		 * @return void
		 */
		private void initialize() {
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setJMenuBar(getJJMenuBar());
			this.setSize(677, 217);
			this.setContentPane(getJContentPane());
			this.setTitle(TDSLanguageUtils.getMessage("CLIENT_APPLICATION_TITLE"));
			
			client = new Client();
		}

		/**
		 * This method initializes jContentPane
		 * 
		 * @return javax.swing.JPanel
		 */
		private JPanel getJContentPane() {
			if (jContentPane == null) {
				
				jContentPane = new JPanel();
				jContentPane.setLayout(null);
				
			}
			return jContentPane;
		}

		private JPanel getJContentPaneAltaCampanya(){
			if (jContentPaneAltaCampanya == null) {
				Border border = BorderFactory.createTitledBorder(TDSLanguageUtils.getMessage("ALTA_CAMPANYA"));
				
				jLblNomAltaCampanya= new JLabel(TDSLanguageUtils.getMessage("NOM_CAMPANYA"), JLabel.RIGHT);
				jLblIniciAltaCampanya= new JLabel(TDSLanguageUtils.getMessage("INICI_CAMPANYA"), JLabel.RIGHT);
				jLblFiAltaCampanya= new JLabel(TDSLanguageUtils.getMessage("FI_CAMPANYA"), JLabel.RIGHT);
			
				
				jContentPaneAltaCampanya = new JPanel(new GridLayout(2, 1, 10, 5));
				
				JPanel elements = new JPanel();
				JPanel controls = new JPanel();
				
				jContentPaneAltaCampanya.setBorder(border);
				elements.setLayout(new GridLayout(3, 3, 10, 5));
	            
				elements.add(jLblNomAltaCampanya, null);
				elements.add(getJTxtNomAltaCampanya(),null);
				
				elements.add(jLblIniciAltaCampanya, null);
				elements.add(getJTxtDataIniciAltaCampanya(),null);
				
				elements.add(jLblFiAltaCampanya, null);
				elements.add(getJTxtDataFiAltaCampanya(),null);
				
				controls.add(getJBtnGravaAltaCampanya(), null);
				
				jContentPaneAltaCampanya.add(elements);
				jContentPaneAltaCampanya.add(controls);
				
			}
			return jContentPaneAltaCampanya;
		}

		private JPanel getJContentPaneAltaPeticio(){
			if (jContentPaneAltaPeticio == null) {
				Border border = BorderFactory.createTitledBorder(TDSLanguageUtils.getMessage("ALTA_PETICIO"));
				
				jLblEmpleatAltaPeticio = new JLabel(TDSLanguageUtils.getMessage("EMPLEAT_PETICIO"), JLabel.RIGHT);
				jLblTallerAltaPeticio = new JLabel(TDSLanguageUtils.getMessage("TALLER_PETICIO"), JLabel.RIGHT);
				jLblCampanyaAltaPeticio = new JLabel(TDSLanguageUtils.getMessage("CAMPANYA_PETICIO"),JLabel.RIGHT);
				
				jContentPaneAltaPeticio = new JPanel(new GridLayout(2, 1, 10, 5));
				JPanel elements = new JPanel();
				JPanel controls = new JPanel();
				
				jContentPaneAltaPeticio.setBorder(border);
				elements.setLayout(new GridLayout(3, 3, 10, 5));
	            
				elements.add(jLblEmpleatAltaPeticio, null);
				elements.add(getCmbEmpleatAltaPeticio(),null);
				
				elements.add(jLblTallerAltaPeticio, null);
				elements.add(getCmbTallerAltaPeticio(),null);
				
				elements.add(jLblCampanyaAltaPeticio, null);
				elements.add(getCmbCampanyaAltaPeticio(),null);

				controls.add(getJBtnGravaAltaPeticio(), null);
				
				jContentPaneAltaPeticio.add(elements);
				jContentPaneAltaPeticio.add(controls);
				
			}
			return jContentPaneAltaPeticio;
		}

		private JPanel getJContentPaneConsultaPeticions() throws RemoteException, PeticionsException{
			JPanel jContentPaneConsultaPetis=null;	
			JPanel jContentPane2=null;
			JPanel jContentPane3=null;
			
			if (jContentPaneConsultaPeticions == null) {
			
				Border border = BorderFactory.createTitledBorder(TDSLanguageUtils.getMessage("CONSULTA"));
								
				JLabel jLblCampanyaConsultaPeticions = new JLabel(TDSLanguageUtils.getMessage("CAMPANYA_CONSULTA"), JLabel.RIGHT);
								
				jContentPaneConsultaPetis = new JPanel(new FlowLayout());
				jContentPane2= new JPanel(new GridLayout(1,1,10,5));
				jContentPane3= new JPanel(new GridLayout(2,1,10,5));
								
				jContentPane3.setBorder(border);
				
				jContentPaneConsultaPetis.setLayout(new FlowLayout());
				jContentPane2.setLayout(new GridLayout(1,1,10,5));
				jContentPane3.setLayout(new GridLayout(2,1,10,5));
					            
				jContentPaneConsultaPetis.add(jLblCampanyaConsultaPeticions,null);
				jContentPaneConsultaPetis.add(getCmbCampanyaConsultaPeticio(),null);
				
				final Object header[] = {
						TDSLanguageUtils.getMessage("HEADER_TALLER"),
		    			TDSLanguageUtils.getMessage("HEADER_NOM_EMPLEAT"),
		    			TDSLanguageUtils.getMessage("HEADER_DATA"),
		    			TDSLanguageUtils.getMessage("HEADER_PUNTUACIO")};
				ComboItem cmbCampanyaSelected =(ComboItem)PantallaClient.this.cmbCampanyaConsultaPeticions.getSelectedItem();
				TaulaConsulta=new JTable(drawTableConsultaPeticions(cmbCampanyaSelected.id), header);
				JScrollPane scrollPeticions = new JScrollPane(TaulaConsulta);
				
				jContentPane2.add(scrollPeticions,null);
				
				jContentPane3.add(jContentPaneConsultaPetis,null);
				jContentPane3.add(jContentPane2,null);
								
			}
			return jContentPane3;
		}
		
		/**Funció que retorna una taula amb les dades de totes les peticions amb idCampanya=id;
		 * 		
		 * @param id Identificador de la campanya
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private Object[][] drawTableConsultaPeticions(int id){
			
			Object[][] PeticionsPerCampanya = null;
			try {
				
				//Creem un diccionari de tallers i empleats per tal de poder-ne recuperar dades
				Taller taller=null;
				Empleat empleat=null;
				
				Iterator<Taller> iter_tallers = PeticionsInterface.getLlistaTallers().iterator();
				Iterator<Empleat> iter_empleats = PeticionsInterface.getLlistaEmpleats().iterator();
				
				Dictionary<Integer,Taller> dict_tallers = new Hashtable();
				Dictionary<Integer,Empleat> dict_empleats = new Hashtable();
				
				while (iter_tallers.hasNext()){
					taller=(Taller)iter_tallers.next();
					dict_tallers.put(taller.getIdTaller(), taller);
				}
				
				while (iter_empleats.hasNext()){
					empleat=(Empleat)iter_empleats.next();
					dict_empleats.put(empleat.getIdEmpleat(), empleat);
				}
								
				List<Peticio> peticions = PeticionsInterface.getPeticionsCampanya(id);
				Iterator<Peticio> iterPeticio = PeticionsInterface.getPeticionsCampanya(id).iterator();
				PeticionsPerCampanya = new Object[peticions.size()][4];
				int k=0;
				while (iterPeticio.hasNext()){
					Peticio peticio = (Peticio)iterPeticio.next();
					
					String nomTaller = dict_tallers.get(peticio.getIdTaller()).getNomTaller();
					String nomEmpleat = dict_empleats.get(peticio.getIdEmpleat()).getNomEmpleat();
					String dataPeticio = peticio.getDataPeticio().toString();
					int puntuacio = peticio.getPuntuacio();
					
					PeticionsPerCampanya[k][0]=nomTaller;
					PeticionsPerCampanya[k][1]=nomEmpleat;
					PeticionsPerCampanya[k][2]=dataPeticio;
					PeticionsPerCampanya[k][3]=new String(new Integer(puntuacio).toString());
					k++;
		
				}
			
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (PeticionsException e) {
				e.printStackTrace();
			}
			return PeticionsPerCampanya;
		}
		
		
		
		/**
		 * This method initializes jJMenuBar	
		 * 	
		 * @return javax.swing.JMenuBar	
		 */
		private JMenuBar getJJMenuBar() {
			if (jJMenuBar == null) {
				jJMenuBar = new JMenuBar();
				jJMenuBar.add(getMenuInici());
				jJMenuBar.add(getMenuOpcions());
				this.gestioCampanyesMenu.setEnabled(false);
				
			}
			return jJMenuBar;
		}

		/**
		 * This method initializes jMenu	
		 * 	
		 * @return javax.swing.JMenu	
		 */
		private JMenu getMenuInici() {
			if (iniciMenu == null) {
				iniciMenu = new JMenu();
				iniciMenu.setText(TDSLanguageUtils.getMessage("CLIENT_MENU_INICI"));
				iniciMenu.add(getConnectaServerRMI());
				iniciMenu.add(getExitMenuItem());
			}
			return iniciMenu;
		}

		/**
		 * This method initializes jMenu	
		 * 	
		 * @return javax.swing.JMenu	
		 */
		private JMenu getMenuOpcions() {
			if (gestioCampanyesMenu == null) {
				gestioCampanyesMenu = new JMenu();
				gestioCampanyesMenu.setText(TDSLanguageUtils.getMessage("CLIENT_MENU_OPCIONS"));
				gestioCampanyesMenu.add(getMenuAltaCampanya());
				gestioCampanyesMenu.add(getMenuAltaPeticio());
				gestioCampanyesMenu.add(getMenuConsultaPeticions());
			}
			return gestioCampanyesMenu;
		}



		/**
		 * This method initializes jMenuItem	
		 * 	
		 * @return javax.swing.JMenuItem	
		 */
		private JMenuItem getExitMenuItem() {
			if (exitMenuItem == null) {
				exitMenuItem = new JMenuItem();
				exitMenuItem.setText(TDSLanguageUtils.getMessage("CLIENT_MENU_INICI_EXIT"));
				exitMenuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
			}
			return exitMenuItem;
		}

		/**
		 * This method initializes jMenuItem	
		 * 	
		 * @return javax.swing.JMenuItem	
		 */
		private JMenuItem getMenuAltaCampanya() {
			if (AltaCampanyaMenu == null) {
				AltaCampanyaMenu = new JMenuItem();
				AltaCampanyaMenu.setText(TDSLanguageUtils.getMessage("CLIENT_MENU_OPCIONS_ALTA_CAMPANYA"));
				AltaCampanyaMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
						Event.CTRL_MASK, true));
				this.AltaCampanyaMenu.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
			        	if (PantallaClient.this.jContentPane!=null)
			        		PantallaClient.this.remove(jContentPane); //jContentPane.setVisible(false);
			        	
			        	if (PantallaClient.this.jContentPaneAltaPeticio!= null)
			        		PantallaClient.this.remove(jContentPaneAltaPeticio); //jContentPaneAltaPeticio.setVisible(false);
			        	
			        	if (PantallaClient.this.jContentPaneConsultaPeticions!=null)
			        		PantallaClient.this.remove(jContentPaneConsultaPeticions);
			        	
			        	PantallaClient.this.setContentPane(PantallaClient.this.getJContentPaneAltaCampanya());
			        	
			        	PantallaClient.this.validate();
			        	PantallaClient.this.repaint();
			        	
			        }
			      });
			}
			return AltaCampanyaMenu;
		}
		
		private JComboBox getCmbEmpleatAltaPeticio()
		  {
		    if (this.cmbEmpleatAltaPeticio == null) {
		      this.cmbEmpleatAltaPeticio = new JComboBox();
		      Iterator llistaEmpleats = null;
		      
		      try {
		    	  llistaEmpleats = PeticionsInterface.getLlistaEmpleats().iterator();
				  while (llistaEmpleats.hasNext()){
					  Empleat recurs = (Empleat)llistaEmpleats.next();
					  this.cmbEmpleatAltaPeticio.addItem(new ComboItem(recurs.getIdEmpleat(),
							                                    recurs.getNomEmpleat()));
				  }
		      } catch (PeticionsException e) {
					e.printStackTrace();
			  } catch (RemoteException re){
				  re.printStackTrace();
			  }
			  
		    }

		    return this.cmbEmpleatAltaPeticio;
		  }	  
		
		private JComboBox getCmbCampanyaAltaPeticio()
		  {
		    if (this.cmbCampanyaAltaPeticio == null) {
		      this.cmbCampanyaAltaPeticio = new JComboBox();
		      Iterator<Campanya> llistaCampanyes = null;
		      
		      try {
		    	  llistaCampanyes= PeticionsInterface.getLlistaCampanyes().iterator();
				  while (llistaCampanyes.hasNext()){
					  Campanya recurs = (Campanya)llistaCampanyes.next();
					  this.cmbCampanyaAltaPeticio.addItem(new ComboItem(recurs.getIdCampanya(),
							                                    		    recurs.getNomCampanya()));
				  }
		      } catch (PeticionsException e) {
					e.printStackTrace();
			  } catch (RemoteException re){
				  re.printStackTrace();
			  }
			  
		    }

		    return this.cmbCampanyaAltaPeticio;
		  }	  
		
		/**
		 * this.jBtnGravaAltaPeticio.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
			          try {
			        	  //aquí no recupera correctament el nom
			        	  //String txt_Empleat = PantallaClient.this.cmbEmpleatAltaPeticio.getToolTipText();
			        			  
			        	  //if (!txt_Empleat.trim().equals("")){
			        		 ComboItem cmbEmpleatSelected =(ComboItem)PantallaClient.this.cmbEmpleatAltaPeticio.getSelectedItem();
			        		 ComboItem cmbTallerSelected =(ComboItem)PantallaClient.this.cmbTallerAltaPeticio.getSelectedItem();
			        		 ComboItem cmbCampanyaSelected =(ComboItem)PantallaClient.this.cmbCampanyaAltaPeticio.getSelectedItem();
				        	 PeticionsInterface.entradaPeticions(cmbEmpleatSelected.id,cmbTallerSelected.id,cmbCampanyaSelected.id);

			  	      } catch (PeticionsException e) {
			  				e.printStackTrace();
			  		  } catch (RemoteException re){
			  			  re.printStackTrace();
			  		  }
			        }
			      });
		 * @return
		 */
		
		private JComboBox getCmbCampanyaConsultaPeticio()
		  {
		    if (this.cmbCampanyaConsultaPeticions == null) {
		    	this.cmbCampanyaConsultaPeticions = new JComboBox();
		    	this.cmbCampanyaConsultaPeticions.addActionListener(new ActionListener(){
		    		public void actionPerformed(ActionEvent AE){
		    		getMenuConsultaPeticions();}});
		    		//}});
		    	
		    	
		      		      
		      Iterator<Campanya> llistaCampanyes = null;
		      
		      try {
		    	  llistaCampanyes= PeticionsInterface.getLlistaCampanyes().iterator();
				  while (llistaCampanyes.hasNext()){
					  Campanya recurs = (Campanya)llistaCampanyes.next();
					  this.cmbCampanyaConsultaPeticions.addItem(new ComboItem(recurs.getIdCampanya(),
							                                    		    recurs.getNomCampanya()));
				  }
		      } catch (PeticionsException e) {
					e.printStackTrace();
			  } catch (RemoteException re){
				  re.printStackTrace();
			  }
			  
		    }

		    return this.cmbCampanyaConsultaPeticions;
		  }	  
		
		private JComboBox getCmbTallerAltaPeticio()
		  {
		    if (this.cmbTallerAltaPeticio == null) {
		      this.cmbTallerAltaPeticio = new JComboBox();
		      Iterator llistaTallers = null;
		      
		      try {
		    	  llistaTallers = PeticionsInterface.getLlistaTallers().iterator();
				  while (llistaTallers.hasNext()){
					  Taller taller = (Taller)llistaTallers.next();
					  this.cmbTallerAltaPeticio.addItem(new ComboItem(taller.getIdTaller(),
							  							  	         taller.getNomTaller()));
				  }
		      } catch (PeticionsException e) {
					e.printStackTrace();
			  } catch (RemoteException re){
				  re.printStackTrace();
			  }
			  
		    }

		    return this.cmbTallerAltaPeticio;
		  }	 	
		
		/**
		 * This method initializes jMenuItem	
		 * 	
		 * @return javax.swing.JMenuItem	
		 */
		private JMenuItem getMenuAltaPeticio() {
			if (AltaPeticioMenu == null) {
				AltaPeticioMenu = new JMenuItem();
				AltaPeticioMenu.setText(TDSLanguageUtils.getMessage("CLIENT_MENU_OPCIONS_PETICIO"));
				AltaPeticioMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
						Event.CTRL_MASK, true));
				this.AltaPeticioMenu.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
			        	if (PantallaClient.this.jContentPane!=null)
			        		PantallaClient.this.remove(jContentPane); //jContentPane.setVisible(false);
			        	
			        	if (PantallaClient.this.jContentPaneAltaCampanya!=null)
			        		PantallaClient.this.remove(jContentPaneAltaCampanya); 
			        	
			        	if (PantallaClient.this.jContentPaneConsultaPeticions!=null)
			        		PantallaClient.this.remove(jContentPaneConsultaPeticions);
			        	
			        	PantallaClient.this.setContentPane(PantallaClient.this.getJContentPaneAltaPeticio());
			        	
			        	PantallaClient.this.validate();
			        	PantallaClient.this.repaint();
			        }
			      });
			}
			return AltaPeticioMenu;
		}

		/**
		 * This method initializes jMenuItem	
		 * 	
		 * @return javax.swing.JMenuItem	
		 */
		private JMenuItem getMenuConsultaPeticions() {
			if (ConsultaPeticioMenu == null) {
				ConsultaPeticioMenu = new JMenuItem();
				ConsultaPeticioMenu.setText(TDSLanguageUtils.getMessage("CLIENT_MENU_OPCIONS_CONSULTA"));
				ConsultaPeticioMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
						Event.CTRL_MASK, true));
				this.ConsultaPeticioMenu.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
			        	if (PantallaClient.this.jContentPane!=null)
			        		PantallaClient.this.remove(jContentPane); 
			        	
			        	if (PantallaClient.this.jContentPaneAltaCampanya!=null)
			        		PantallaClient.this.remove(jContentPaneAltaCampanya); 
			        	
			        	if (PantallaClient.this.jContentPaneAltaPeticio!=null)
			        		PantallaClient.this.remove(jContentPaneAltaPeticio);
			        	
			        	
			        	try {
							PantallaClient.this.setContentPane(getJContentPaneConsultaPeticions());
						} catch (RemoteException | PeticionsException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	
			        	PantallaClient.this.validate();
			        	PantallaClient.this.repaint();
			        }
			      });
				return ConsultaPeticioMenu;
			}
			else{
				try {
						PantallaClient.this.setContentPane(getJContentPaneConsultaPeticions());
				} catch (RemoteException | PeticionsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        	PantallaClient.this.validate();
		        	PantallaClient.this.repaint();
		        }
				return AltaCampanyaMenu;
			}

		/**
		 * This method initializes jMenuItem	
		 * 	
		 * @return javax.swing.JMenuItem	
		 */
		private JMenuItem getConnectaServerRMI() {
			if (connServerMenu == null) {
				connServerMenu = new JMenuItem();
				connServerMenu.setText(TDSLanguageUtils.getMessage("CLIENT_MENU_INICI_CONN_SERVER_RMI"));
				connServerMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK, true));
				this.connServerMenu.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
			          try {
			            if (client.connecta()){ 
				            setTitle(TDSLanguageUtils.getMessage("CLIENT_APPLICATION_TITLE") + " - " +
				            		 TDSLanguageUtils.getMessage("CLIENT_CONNECTAT"));
				            PeticionsInterface = client.getPeticionsInterface();
				            PantallaClient.this.gestioCampanyesMenu.setEnabled(true);
				            PantallaClient.this.connServerMenu.setEnabled(false);
				            
			            }
			          } catch (PeticionsException gre) {
			        	  setTitle(TDSLanguageUtils.getMessage("CLIENT_APPLICATION_TITLE"));
			        	  gre.printStackTrace(System.err);
			          }
			        }
			      });
			}
			return connServerMenu;
		}
		
		/** Mètode que comproba si donada una cadena que representa una data aquesta està
		 * expressada en el format yyyy-MM-dd
		 * 
		 * @param data
		 * @return
		 */
		private boolean comprobarFormatDates (String data) {
			boolean correcte = false;
			char[] Adata = data.toCharArray();
			if (Adata[4]=='-'&&Adata[7]=='-'&&data.length()==10){
				correcte=true;
			}
			return correcte;
		}

	} 

	class ComboItem
	{
	  int id;
	  String name;
	  ComboItem(int id, String name){
		this.id = id;
		this.name = name;
	  }
	  public String toString()
	  {
	    return this.name;
	  }


	  
}

	