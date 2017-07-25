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
public class CommandeFournisseur implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idFour ;
	private String numFour ;
	private String dateFour ;
	private String heureFour ;
	private  double totalFour ;
	private Date dateVraiFour ;
	private String etatComFour ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<ArticleListe> articles ;
	@ManyToOne
	private Fournisseur fournisseur ;
	
	@OneToOne
	@JoinColumn(name="codeLivraison")
	private BonLivraison livraison ;
	
	
	
	
	
	public long getIdFour() {
		return idFour;
	}
	public void setIdFour(long idFour) {
		this.idFour = idFour;
	}
	public String getNumFour() {
		return numFour;
	}
	public void setNumFour(String numFour) {
		this.numFour = numFour;
	}
	public String getDateFour() {
		return dateFour;
	}
	public void setDateFour(String dateFour) {
		this.dateFour = dateFour;
	}
	public String getHeureFour() {
		return heureFour;
	}
	public void setHeureFour(String heureFour) {
		this.heureFour = heureFour;
	}
	public double getTotalFour() {
		return totalFour;
	}
	public void setTotalFour(double totalFour) {
		this.totalFour = totalFour;
	}
	public Date getDateVraiFour() {
		return dateVraiFour;
	}
	public void setDateVraiFour(Date dateVraiFour) {
		this.dateVraiFour = dateVraiFour;
	}
	public String getEtatComFour() {
		return etatComFour;
	}
	public void setEtatComFour(String etatComFour) {
		this.etatComFour = etatComFour;
	}
	@JsonIgnore
	public Collection<ArticleListe> getArticles() {
		return articles;
	}
	public void setArticles(Collection<ArticleListe> articles) {
		this.articles = articles;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	
	public BonLivraison getLivraison() {
		return livraison;
	}
	public void setLivraison(BonLivraison livraison) {
		this.livraison = livraison;
	}
	public CommandeFournisseur(String num, String date, String heure, double total, Collection<ArticleListe> articles,
			Fournisseur fournisseurs) {
		super();
		this.numFour = num;
		this.dateFour = date;
		this.heureFour = heure;
		this.totalFour = total;
		this.articles = articles;
		this.fournisseur = fournisseur;
	}
	
	
	
	public CommandeFournisseur(String num, String date, String heure, double total, Collection<ArticleListe> articles) {
		super();
		this.numFour = num;
		this.dateFour = date;
		this.heureFour = heure;
		this.totalFour = total;
		this.articles = articles;
	}
	
	
	public CommandeFournisseur(String num, String date, String heure, double total) {
		super();
		this.numFour = num;
		this.dateFour = date;
		this.heureFour = heure;
		this.totalFour = total;
	}
	public CommandeFournisseur() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
