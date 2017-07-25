package com.entreprise.entities;

import javax.persistence.Entity;

@Entity
public class PaiementCheque extends ModePaiement {
	
	private String numCheque;
	private String nomBanque;
	
	
	public String getNomBanque() {
		return nomBanque;
	}

	public void setNomBanque(String nomBanque) {
		this.nomBanque = nomBanque;
	}

	public String getNumCheque() {
		return numCheque;
	}

	public void setNumCheque(String numCheque) {
		this.numCheque = numCheque;
	}

	

	public PaiementCheque(String nom, String numCheque, String nomBanque) {
		super(nom);
		this.numCheque = numCheque;
		this.nomBanque = nomBanque;
	}

	public PaiementCheque() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaiementCheque(String nom) {
		super(nom);
		// TODO Auto-generated constructor stub
	}
	
	
	
	

	
	

}
