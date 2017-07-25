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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BonLivraison implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idBl ;
	private String numBl ;
	private String dateBl ;
	private String heureBl ;
	private double totalhtBl ;
	private double totaltvaBl ;
	private double totalttcBl ;
	private String fichierBl ;
	private String totalString ;
	private String etatBl ;
	private String typeBl ;
	private Date dateVraiBl ;
	@ManyToOne
	@JoinColumn(name="codeBonLivs")
	private Facture facture ;
   
	
	
	//@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<ArticleListe> articlesBl ;
	@ManyToOne
	@JoinColumn(name="codeFourn")
	private Fournisseur fournisseur ;
	
	@OneToOne
	@JoinColumn(name="codeCommandeClient")
	private CommandeClient commandeClient ;
	
	@OneToOne
	@JoinColumn(name="codeCommandeFourn")
	private CommandeFournisseur commandeFourn ;
	
	@ManyToOne
	@JoinColumn(name="codeClient")
	private Client client ;
	
	
	
	
	public long getIdBl() {
		return idBl;
	}
	public void setIdBl(long idBl) {
		this.idBl = idBl;
	}
	public String getNumBl() {
		return numBl;
	}
	public void setNumBl(String numBl) {
		this.numBl = numBl;
	}
	public String getDateBl() {
		return dateBl;
	}
	public void setDateBl(String dateBl) {
		this.dateBl = dateBl;
	}
	public String getHeureBl() {
		return heureBl;
	}
	public void setHeureBl(String heureBl) {
		this.heureBl = heureBl;
	}
	public double getTotalhtBl() {
		return totalhtBl;
	}
	public void setTotalhtBl(double totalhtBl) {
		this.totalhtBl = totalhtBl;
	}
	public double getTotaltvaBl() {
		return totaltvaBl;
	}
	public void setTotaltvaBl(double totaltvaBl) {
		this.totaltvaBl = totaltvaBl;
	}
	public double getTotalttcBl() {
		return totalttcBl;
	}
	public void setTotalttcBl(double totalttcBl) {
		this.totalttcBl = totalttcBl;
	}
	public String getFichierBl() {
		return fichierBl;
	}
	public void setFichierBl(String fichierBl) {
		this.fichierBl = fichierBl;
	}
	public String getEtatBl() {
		return etatBl;
	}
	public void setEtatBl(String etatBl) {
		this.etatBl = etatBl;
	}
	public String getTypeBl() {
		return typeBl;
	}
	public void setTypeBl(String typeBl) {
		this.typeBl = typeBl;
	}
	public Date getDateVraiBl() {
		return dateVraiBl;
	}
	public void setDateVraiBl(Date dateVraiBl) {
		this.dateVraiBl = dateVraiBl;
	}
	
	

	public String getTotalString() {
		return totalString;
	}
	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}
	
	
	@JsonIgnore
	public Collection<ArticleListe> getArticlesBl() {
		return articlesBl;
	}
	public void setArticlesBl(Collection<ArticleListe> articlesBl) {
		this.articlesBl = articlesBl;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	
	
	public Facture getFacture() {
		return facture;
	}
	public void setFacture(Facture facture) {
		this.facture = facture;
	}
	
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	

	public CommandeClient getCommandeClient() {
		return commandeClient;
	}
	public void setCommandeClient(CommandeClient commandeClient) {
		this.commandeClient = commandeClient;
	}
	public CommandeFournisseur getCommandeFourn() {
		return commandeFourn;
	}
	public void setCommandeFourn(CommandeFournisseur commandeFourn) {
		this.commandeFourn = commandeFourn;
	}
	public BonLivraison(String num, String date, String heure, double totalht, double totaltva, double totalttc,
			Collection<ArticleListe> articles) {
		super();
		this.numBl = num;
		this.dateBl = date;
		this.heureBl = heure;
		this.totalhtBl = totalht;
		this.totaltvaBl = totaltva;
		this.totalttcBl = totalttc;
		this.articlesBl = articles;
	}

	
	public BonLivraison(String num, String date, String heure, double totalht, double totaltva, double totalttc,
			String etat) {
		super();
		this.numBl = num;
		this.dateBl = date;
		this.heureBl = heure;
		this.totalhtBl = totalht;
		this.totaltvaBl = totaltva;
		this.totalttcBl = totalttc;
		this.etatBl = etat;
	}
	public BonLivraison(String num, String date, String heure, double totalht, double totaltva, double totalttc) {
		super();
		this.numBl = num;
		this.dateBl = date;
		this.heureBl = heure;
		this.totalhtBl = totalht;
		this.totaltvaBl = totaltva;
		this.totalttcBl = totalttc;
	}
	public BonLivraison() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
