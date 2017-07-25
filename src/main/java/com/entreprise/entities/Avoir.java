package com.entreprise.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Avoir implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idAv ;
	private String numAv ;
	private String dateAv ;
	private String heureAv ;
	private double totalAv ;
	private String fichierAv ;
	private String etatAv ;
	private String typeAv ;
	private Date dateVraiAv ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<ArticleListe> articles ;
	@ManyToOne
	@JoinColumn(name="codeClient")
	private Client client ;
	@ManyToOne
	@JoinColumn(name="codeFourn")
	private Fournisseur fournisseur ;
	
	
	
	public long getIdAv() {
		return idAv;
	}
	public void setIdAv(long idAv) {
		this.idAv = idAv;
	}
	public String getNumAv() {
		return numAv;
	}
	public void setNumAv(String numAv) {
		this.numAv = numAv;
	}
	public String getDateAv() {
		return dateAv;
	}
	public void setDateAv(String dateAv) {
		this.dateAv = dateAv;
	}
	public String getHeureAv() {
		return heureAv;
	}
	public void setHeureAv(String heureAv) {
		this.heureAv = heureAv;
	}
	public double getTotalAv() {
		return totalAv;
	}
	public void setTotalAv(double totalAv) {
		this.totalAv = totalAv;
	}
	public String getFichierAv() {
		return fichierAv;
	}
	public void setFichierAv(String fichierAv) {
		this.fichierAv = fichierAv;
	}
	public String getEtatAv() {
		return etatAv;
	}
	public void setEtatAv(String etatAv) {
		this.etatAv = etatAv;
	}
	public String getTypeAv() {
		return typeAv;
	}
	public void setTypeAv(String typeAv) {
		this.typeAv = typeAv;
	}
	public Date getDateVraiAv() {
		return dateVraiAv;
	}
	public void setDateVraiAv(Date dateVraiAv) {
		this.dateVraiAv = dateVraiAv;
	}
	@JsonIgnore
	public Collection<ArticleListe> getArticles() {
		return articles;
	}
	public void setArticles(Collection<ArticleListe> articles) {
		this.articles = articles;
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
	public Avoir(String num, String date, String heure, double total, String fichier, Collection<ArticleListe> articles,
			Client client) {
		super();
		this.numAv = num;
		this.dateAv = date;
		this.heureAv = heure;
		this.totalAv = total;
		this.fichierAv = fichier;
		this.articles = articles;
		this.client = client;
	}
	
	
	
	public Avoir(String num, String date, String heure, double total, Collection<ArticleListe> articles) {
		super();
		this.numAv = num;
		this.dateAv = date;
		this.heureAv = heure;
		this.totalAv = total;
		this.articles = articles;
	}
	

	public Avoir(String num, String date, String heure, double total) {
		super();
		this.numAv = num;
		this.dateAv = date;
		this.heureAv = heure;
		this.totalAv = total;
	}
	
	
	
	public Avoir(String num, String date, String heure, double total, String etat) {
		super();
		this.numAv = num;
		this.dateAv = date;
		this.heureAv = heure;
		this.totalAv = total;
		this.etatAv = etat;
	}
	
	
	
	public Avoir(String num, String date, String heure, double total, String fichier, String etat, String type) {
		super();
		this.numAv = num;
		this.dateAv = date;
		this.heureAv = heure;
		this.totalAv = total;
		this.fichierAv = fichier;
		this.etatAv = etat;
		this.typeAv = type;
	}
	public Avoir() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
