package com.entreprise.entities;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
public class AutrePaiement extends ModePaiement {

	public AutrePaiement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AutrePaiement(String nom) {
		super(nom);
		// TODO Auto-generated constructor stub
	}
	
	

}
