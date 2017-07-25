package com.entreprise.entities;

import javax.persistence.DiscriminatorColumn;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class CompteBancaire extends ModePaiement {
	private String numCompte ;
	private String nomBanque ;
	
	
	
	public String getNumCompte() {
		return numCompte;
	}
	public void setNumCompte(String numCompte) {
		this.numCompte = numCompte;
	}
	public String getNomBanque() {
		return nomBanque;
	}
	public void setNomBanque(String nomBanque) {
		this.nomBanque = nomBanque;
	}
	public CompteBancaire() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CompteBancaire(String nom) {
		super(nom);
		// TODO Auto-generated constructor stub
	}
	public CompteBancaire(String nom, String numCompte, String nomBanque) {
		super(nom);
		this.numCompte = numCompte;
		this.nomBanque = nomBanque;
	}
	
	
	
	
	
	

}
