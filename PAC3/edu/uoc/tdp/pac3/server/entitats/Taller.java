package edu.uoc.tdp.pac3.server.entitats;

import java.io.Serializable;

public class Taller implements Serializable{
	private static final long serialVersionUID = 1L;
	private int idTaller;
	private String nomTaller;
	private String direccioTaller;
	
	public Taller(){ super();}
	
	public Taller(int idTaller, String nom, String direccio){
		super();
		this.idTaller=idTaller;
		this.nomTaller=nom;
		this.direccioTaller=direccio;
	}
		
	public int getIdTaller(){return idTaller;}	
	public String getNomTaller(){return nomTaller;}
	public String getDireccioTaller(){return direccioTaller;}
	
	public void setIdTaller (int id) {this.idTaller=id;}
	public void setNomTaller (String nom) {this.nomTaller=nom;}
	public void setDireccioTaller (String direccio) {this.direccioTaller=direccio;}
	
	public String toString(){ return "["+"idTaller"+"|"+"nomTaller"+"|"
			+"direccioTaller"+"]";
	}

}
