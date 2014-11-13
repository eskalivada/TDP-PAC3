package edu.uoc.tdp.pac3.server.entitats;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Peticio implements Serializable{
	private static final long serialVersionUID = 4L;
	private int idTaller;
	private int idCampanya;
	private int idEmpleat;
	private Date dataPeticio;
	private int puntuacio;
	private final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public Peticio(){super();}
	
	public Peticio(int idT, int idC, int idE, Date data, int puntuacio){
		super();
		this.idTaller=idT;
		this.idCampanya=idC;
		this.idEmpleat=idE;
		this.dataPeticio=data;
		this.puntuacio=puntuacio;
	}
	
	public int getIdTaller(){return idTaller;}
	public int getIdCampanya(){return idCampanya;}
	public int getIdEmpleat(){return idEmpleat;}
	public Date getDataPeticio(){return dataPeticio;}
	public int getPuntuacio(){return puntuacio;}
	
	public void setIdTaller(int id){ this.idTaller=id;}
	public void setIdCampanya(int id){this.idCampanya=id;}
	public void setDataPeticio(Date data){this.dataPeticio=data;}
	public void setPuntuacio(int puntuacio){this.puntuacio=puntuacio;}
	public void setIdEmpleat(int id){this.idEmpleat=id;}
	
	public String toString(){
		return ("["+idTaller+"|"+idCampanya+"|"+idEmpleat+"|"+sdf.format(dataPeticio)+"|"+puntuacio+"]");
	}

}
