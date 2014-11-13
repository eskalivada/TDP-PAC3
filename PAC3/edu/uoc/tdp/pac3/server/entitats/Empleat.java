package edu.uoc.tdp.pac3.server.entitats;

import java.io.Serializable;
import java.sql.Date;
//import java.text.DateFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Empleat implements Serializable {
	private static final long serialVErsionUID = 2L;
	private int idEmpleat;
	private int idTaller;
	private String nomEmpleat;
	private String cognomEmpleat;
	private Date dataAlta;
	private final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	
	public Empleat(){ super();}
	
	public Empleat(int idEmp,int idTaller,String nom, String cognom, Date dataAlta){
		super();
		this.idEmpleat=idEmp;
		this.idTaller=idTaller;
		this.nomEmpleat=nom;
		this.cognomEmpleat=cognom;
		this.dataAlta=dataAlta;
	}
	
	public int getIdEmpleat(){return idEmpleat;}
	public int getIdTaller(){return idTaller;}
	public String getNomEmpleat(){return nomEmpleat;}
	public String getCognomEmpleat(){return cognomEmpleat;}

	public void setIdEmpleat(int idE){this.idEmpleat=idE;}
	public void setIdTaller(int idT){this.idTaller=idT;}
	public void setNom (String nom){this.nomEmpleat=nom;}
	public void setCognom (String cognoms){this.cognomEmpleat=cognoms;}
	public void setDataAlta (Date data){this.dataAlta=data;}
	
	public Date getDataAlta(){return dataAlta;}
	
	public String toString(){
		return ("["+idEmpleat+"|"+nomEmpleat+"|"+cognomEmpleat+"|"+sdf.format(dataAlta)+"]");
	}

}


