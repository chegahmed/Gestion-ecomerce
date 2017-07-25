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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Fournisseur implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idF ;
	private String nomSocF ;
	private String familleF ;
	private String formeJurF ;
	private String tel1F ;
	private String tel2F ;
	private String faxF ;
	private String gsm1F ;
	private String gsm2F ;
	private String email1F ;
	private String email2F ;
	private String sitewebF ;
	
	
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<Representant> representants ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<CommandeFournisseur> commandeFours ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<BonLivraison> bonLivs ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<Avoir> avoirs ;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<ModePaiement> modePaiements ;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<Facture> factures ;
	
	
	
	
	@JsonIgnore
	public Collection<ModePaiement> getModePaiements() {
		return modePaiements;
	}

	public void setModePaiements(Collection<ModePaiement> modePaiements) {
		this.modePaiements = modePaiements;
	}

	@JsonIgnore
	public Collection<Representant> getRepresentants() {
		return representants;
	}
	
	@JsonIgnore
	public Collection<Facture> getFactures() {
		return factures;
	}
	public void setFactures(Collection<Facture> factures) {
		this.factures = factures;
	}
	public void setRepresentants(Collection<Representant> representants) {
		this.representants = representants;
	}
	@JsonIgnore
	public Collection<CommandeFournisseur> getCommandeFours() {
		return commandeFours;
	}
	public void setCommandeFours(Collection<CommandeFournisseur> commandeFours) {
		this.commandeFours = commandeFours;
	}
	@JsonIgnore
	public Collection<BonLivraison> getBonLivs() {
		return bonLivs;
	}
	public void setBonLivs(Collection<BonLivraison> bonLivs) {
		this.bonLivs = bonLivs;
	}
    
	@JsonIgnore
	public Collection<Avoir> getAvoirs() {
		return avoirs;
	}
	public void setAvoirs(Collection<Avoir> avoirs) {
		this.avoirs = avoirs;
	}

	
	public long getIdF() {
		return idF;
	}
	public void setIdF(long idF) {
		this.idF = idF;
	}
	public String getNomSocF() {
		return nomSocF;
	}
	public void setNomSocF(String nomSocF) {
		this.nomSocF = nomSocF;
	}
	public String getFamilleF() {
		return familleF;
	}
	public void setFamilleF(String familleF) {
		this.familleF = familleF;
	}
	public String getFormeJurF() {
		return formeJurF;
	}
	public void setFormeJurF(String formeJurF) {
		this.formeJurF = formeJurF;
	}
	public String getTel1F() {
		return tel1F;
	}
	public void setTel1F(String tel1f) {
		tel1F = tel1f;
	}
	public String getTel2F() {
		return tel2F;
	}
	public void setTel2F(String tel2f) {
		tel2F = tel2f;
	}
	public String getFaxF() {
		return faxF;
	}
	public void setFaxF(String faxF) {
		this.faxF = faxF;
	}
	public String getGsm1F() {
		return gsm1F;
	}
	public void setGsm1F(String gsm1f) {
		gsm1F = gsm1f;
	}
	public String getGsm2F() {
		return gsm2F;
	}
	public void setGsm2F(String gsm2f) {
		gsm2F = gsm2f;
	}
	public String getEmail1F() {
		return email1F;
	}
	public void setEmail1F(String email1f) {
		email1F = email1f;
	}
	public String getEmail2F() {
		return email2F;
	}
	public void setEmail2F(String email2f) {
		email2F = email2f;
	}
	public String getSitewebF() {
		return sitewebF;
	}
	public void setSitewebF(String sitewebF) {
		this.sitewebF = sitewebF;
	}
	public Fournisseur(String nomSoc, String famille, String formeJur, String tel1, String tel2, String fax,
			String gsm1, String gsm2, String email1, String email2, String siteweb) {
		super();
		this.nomSocF = nomSoc;
		this.familleF = famille;
		this.formeJurF = formeJur;
		this.tel1F = tel1;
		this.tel2F = tel2;
		this.faxF = fax;
		this.gsm1F = gsm1;
		this.gsm2F = gsm2;
		this.email1F = email1;
		this.email2F = email2;
		this.sitewebF = siteweb;
	}
	public Fournisseur() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
