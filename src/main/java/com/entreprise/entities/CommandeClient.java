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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CommandeClient implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idComCl ;
	private String numComCl ;
	private double totalComCl ;
	private double totaltvaComCl ;
	private double totalttcComCl ;
	private String fichierComCl ;
	private String totalString ;
	private String etatComCl ;
	private String dateComCl ;
	private Date dateVraiComCl ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<ArticleListe> articles ;
	@ManyToOne
	@JoinColumn(name="codeClient")
	private Client client ;
	@OneToOne
	@JoinColumn(name="codeLivraison")
	private BonLivraison livraison ;
	
	@ManyToOne
	@JoinColumn(name="codeFacture")
	private Facture facture ;

	public String getDateComCl() {
		return dateComCl;
	}
	public void setDateComCl(String dateComCl) {
		this.dateComCl = dateComCl;
	}
	public long getIdComCl() {
		return idComCl;
	}
	public void setIdComCl(long idComCl) {
		this.idComCl = idComCl;
	}
	public String getNumComCl() {
		return numComCl;
	}
	public void setNumComCl(String numComCl) {
		this.numComCl = numComCl;
	}
	public double getTotalComCl() {
		return totalComCl;
	}
	public void setTotalComCl(double totalComCl) {
		this.totalComCl = totalComCl;
	}
	public String getFichierComCl() {
		return fichierComCl;
	}
	public void setFichierComCl(String fichierComCl) {
		this.fichierComCl = fichierComCl;
	}
	public String getEtatComCl() {
		return etatComCl;
	}
	public void setEtatComCl(String etatComCl) {
		this.etatComCl = etatComCl;
	}
	
	public String getTotalString() {
		return totalString;
	}
	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}
	public Date getDateVraiComCl() {
		return dateVraiComCl;
	}
	public void setDateVraiComCl(Date dateVraiComCl) {
		this.dateVraiComCl = dateVraiComCl;
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
	
	public BonLivraison getLivraison() {
		return livraison;
	}

	public void setLivraison(BonLivraison livraison) {
		this.livraison = livraison;
	}

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}
	
	

	public double getTotaltvaComCl() {
		return totaltvaComCl;
	}
	public void setTotaltvaComCl(double totaltvaComCl) {
		this.totaltvaComCl = totaltvaComCl;
	}
	public double getTotalttcComCl() {
		return totalttcComCl;
	}
	public void setTotalttcComCl(double totalttcComCl) {
		this.totalttcComCl = totalttcComCl;
	}
	public CommandeClient(String num, double total, Collection<ArticleListe> articles) {
		super();
		this.numComCl = num;
		this.totalComCl = total;
		this.articles = articles;
	}
	public CommandeClient(String num, double total, String fichier, Collection<ArticleListe> articles) {
		super();
		this.numComCl = num;
		this.totalComCl = total;
		this.fichierComCl = fichier;
		this.articles = articles;
	}
	public CommandeClient(String num, double total, String fichier, Collection<ArticleListe> articles, Client client) {
		super();
		this.numComCl = num;
		this.totalComCl = total;
		this.fichierComCl = fichier;
		this.articles = articles;
		this.client = client;
	}
	

	
	public CommandeClient(String num, double total, String etat) {
		super();
		this.numComCl = num;
		this.totalComCl = total;
		this.etatComCl = etat;
	}
	public CommandeClient(String num, double total) {
		super();
		this.numComCl = num;
		this.totalComCl = total;
	}
	
	

	public CommandeClient(String numComCl, String dateComCl) {
		super();
		this.numComCl = numComCl;
		this.dateComCl = dateComCl;
	}
	
	

	public CommandeClient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
