package com.entreprise.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Representant {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id ;
	private String nomRep ;
	private String prenomRep ;
	private String villeRep ;
	private String telRep ;
	private String gsm1Rep ;
	private String gsm2Rep ;
	private String emailRep ;
	@ManyToOne
	@JoinColumn(name="codeClient")
	private Client client ;
	@ManyToOne
	@JoinColumn(name="codeFourn")
	private Fournisseur fournisseur ;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNomRep() {
		return nomRep;
	}
	public void setNomRep(String nomRep) {
		this.nomRep = nomRep;
	}
	public String getPrenomRep() {
		return prenomRep;
	}
	public void setPrenomRep(String prenomRep) {
		this.prenomRep = prenomRep;
	}
	public String getVilleRep() {
		return villeRep;
	}
	public void setVilleRep(String villeRep) {
		this.villeRep = villeRep;
	}
	public String getTelRep() {
		return telRep;
	}
	public void setTelRep(String telRep) {
		this.telRep = telRep;
	}
	public String getGsm1Rep() {
		return gsm1Rep;
	}
	public void setGsm1Rep(String gsm1Rep) {
		this.gsm1Rep = gsm1Rep;
	}
	public String getGsm2Rep() {
		return gsm2Rep;
	}
	public void setGsm2Rep(String gsm2Rep) {
		this.gsm2Rep = gsm2Rep;
	}
	public String getEmailRep() {
		return emailRep;
	}
	public void setEmailRep(String emailRep) {
		this.emailRep = emailRep;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	public Representant(String nomRep, String prenomRep, String villeRep, String telRep, String gsm1Rep, String gsm2Rep,
			String emailRep, Client client, Fournisseur fournisseur) {
		super();
		this.nomRep = nomRep;
		this.prenomRep = prenomRep;
		this.villeRep = villeRep;
		this.telRep = telRep;
		this.gsm1Rep = gsm1Rep;
		this.gsm2Rep = gsm2Rep;
		this.emailRep = emailRep;
		this.client = client;
		this.fournisseur = fournisseur;
	}
	public Representant(String nomRep, String prenomRep, String villeRep, String telRep, String gsm1Rep, String gsm2Rep,
			String emailRep, Client client) {
		super();
		this.nomRep = nomRep;
		this.prenomRep = prenomRep;
		this.villeRep = villeRep;
		this.telRep = telRep;
		this.gsm1Rep = gsm1Rep;
		this.gsm2Rep = gsm2Rep;
		this.emailRep = emailRep;
		this.client = client;
	}
	public Representant(String nomRep, String prenomRep, String villeRep, String telRep, String gsm1Rep, String gsm2Rep,
			String emailRep, Fournisseur fournisseur) {
		super();
		this.nomRep = nomRep;
		this.prenomRep = prenomRep;
		this.villeRep = villeRep;
		this.telRep = telRep;
		this.gsm1Rep = gsm1Rep;
		this.gsm2Rep = gsm2Rep;
		this.emailRep = emailRep;
		this.fournisseur = fournisseur;
	}
	
	public Representant(String nomRep, String villeRep) {
		super();
		this.nomRep = nomRep;
		this.villeRep = villeRep;
	}
	public Representant() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
