package com.entreprise.entities;

import java.io.Serializable;
import java.util.Collection;

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
public class Article implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idArt ;
	private String designation ;
	private String etat ;
	private int stockmin ;
	private int stockmax ;
	private int stockreel ;
	private double prixachat ;
	private double prixvente ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<ArticleListe> articles ;
	@ManyToOne
	@JoinColumn(name="codeCategorie")
	private Categorie categorie ;
	@OneToOne
	@JoinColumn(name="codeStat")
	private StatArticle statistique  ;
	
	public long getIdArt() {
		return idArt;
	}
	public void setIdArt(long idArt) {
		this.idArt = idArt;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public int getStockmin() {
		return stockmin;
	}
	public void setStockmin(int stockmin) {
		this.stockmin = stockmin;
	}
	public int getStockmax() {
		return stockmax;
	}
	public void setStockmax(int stockmax) {
		this.stockmax = stockmax;
	}
	public int getStockreel() {
		return stockreel;
	}
	public void setStockreel(int stockreel) {
		this.stockreel = stockreel;
	}
	public double getPrixachat() {
		return prixachat;
	}
	
	
	public StatArticle getStatistique() {
		return statistique;
	}
	public void setStatistique(StatArticle statistique) {
		this.statistique = statistique;
	}
	public void setPrixachat(double prixachat) {
		this.prixachat = prixachat;
	}
	public double getPrixvente() {
		return prixvente;
	}
	public void setPrixvente(double prixvente) {
		this.prixvente = prixvente;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	@JsonIgnore
	public Collection<ArticleListe> getArticles() {
		return articles;
	}
	public void setArticles(Collection<ArticleListe> articles) {
		this.articles = articles;
	}
	public Article(String designation, String etat, int stockmin, int stockmax, int stockreel, double prixachat,
			double prixvente, Categorie categorie) {
		super();
		this.designation = designation;
		this.etat = etat;
		this.stockmin = stockmin;
		this.stockmax = stockmax;
		this.stockreel = stockreel;
		this.prixachat = prixachat;
		this.prixvente = prixvente;
		this.categorie = categorie;
	}
	
	
	
	public Article(String designation, String etat, int stockmin, int stockmax, int stockreel, double prixachat,
			double prixvente) {
		super();
		this.designation = designation;
		this.etat = etat;
		this.stockmin = stockmin;
		this.stockmax = stockmax;
		this.stockreel = stockreel;
		this.prixachat = prixachat;
		this.prixvente = prixvente;
	}
	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
