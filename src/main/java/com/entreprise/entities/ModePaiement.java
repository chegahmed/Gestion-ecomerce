package com.entreprise.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_PAI" , discriminatorType=DiscriminatorType.STRING)
public class ModePaiement implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idMode ;
	private String nomMode ;
	@ManyToOne
	@JoinColumn(name="codeClient")
	private Client client ;
	
	@ManyToOne
	@JoinColumn(name="codeFour")
	private Fournisseur fournisseur ;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<Facture> factures ;
	
	
	
	

	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	public long getIdMode() {
		return idMode;
	}
	public void setIdMode(long idMode) {
		this.idMode = idMode;
	}
	public String getNomMode() {
		return nomMode;
	}
	public void setNomMode(String nomMode) {
		this.nomMode = nomMode;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	@JsonIgnore
	public Collection<Facture> getFactures() {
		return factures;
	}
	public void setFactures(Collection<Facture> factures) {
		this.factures = factures;
	}
	public ModePaiement(String nom) {
		super();
		this.nomMode = nom;
	}
	public ModePaiement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
