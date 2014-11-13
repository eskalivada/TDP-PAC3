package edu.uoc.tdp.pac3.server.entitats;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Campanya implements Serializable{
	private static final long serialVersionUID = 3L;
	private int idCampanya;
	private Date dataInici;
	private Date dataFi;
	private Date dataAlta;
	private String nomCampanya;
	private final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public Campanya(){super();}
	
	public Campanya(int id, Date dataini, Date datafi, Date datalta, String nom){
		super();
		this.idCampanya=id;
		this.dataInici=dataini;
		this.dataFi=datafi;
		this.dataAlta=datalta;
		this.nomCampanya=nom;
	}
	
	public int getIdCampanya(){return idCampanya;}
	
	public Date getDataInici(){return dataInici;}
	
	public Date getDataFin(){return dataFi;}
	
	public Date getDataAlta(){return dataAlta;}
	
	public String getNomCampanya(){return nomCampanya;}
	
	public void setIdCampanya(int id){this.idCampanya=id;}
	
	public void setDataInici(Date ini){this.dataInici=ini;}

	public void setDataFi(Date fi){this.dataFi=fi;}	
	
	public void setDataAlta(Date dalta){this.dataAlta=dalta;}
	
	public void setNomCampanya(String nom){this.nomCampanya=nom;}
	
	public String toString(){
		return ("["+idCampanya+"|"+nomCampanya+"|"+sdf.format(dataInici)+"|"+sdf.format(dataFi)+"|"+sdf.format(dataAlta)+"]");
	}
}
