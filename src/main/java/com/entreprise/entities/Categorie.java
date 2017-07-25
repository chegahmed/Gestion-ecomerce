package com.entreprise.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Categorie implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idCat ;
	private String nomCat ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<Article> articles ;
	
	public long getIdCat() {
		return idCat;
	}
	public void setIdCat(long idCat) {
		this.idCat = idCat;
	}
	public String getNomCat() {
		return nomCat;
	}
	public void setNomCat(String nomCat) {
		this.nomCat = nomCat;
	}
	@JsonIgnore
	public Collection<Article> getArticles() {
		return articles;
	}
	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
	}
	public Categorie(String nom, Collection<Article> articles) {
		super();
		this.nomCat = nom;
		this.articles = articles;
	}
	public Categorie() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Categorie(String nom) {
		super();
		this.nomCat = nom;
	}

}
