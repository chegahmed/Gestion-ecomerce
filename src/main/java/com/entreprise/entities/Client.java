package com.entreprise.entities;

import java.io.Serializable;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Client implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idCl ;
	private String nomSoc ;
	private String famSoc ;
	private String formeJur ;
	private String tel1 ;
	private String tel2 ;
	private String fax ;
	private String gsm1 ;
	private String gsm2 ;
	private String email1 ;
	private String email2 ;
	private String adresse ;
	private String siteweb ;
	private double seuil ;
	private double totaldu ;
	private double totalCumul ;
	private String risque ;
	private String etat ;
	private String numPatente ;
	private String numRegistre ;
	private String numTva ;
	private String numIce ;
	private String releveCompte ;
	private String dateReleve ;
	private double totalReleve ;
	
	
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<Representant> representants ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<Facture> factures ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<Avoir> avoirs ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<CommandeClient> commandeClients ;
	
	
	
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<ModePaiement> modePaiements ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<BonLivraison> bonLivs ;

	public long getIdCl() {
		return idCl;
	}
	public void setIdCl(long idCl) {
		this.idCl = idCl;
	}
	public String getNomSoc() {
		return nomSoc;
	}
	public void setNomSoc(String nomSoc) {
		this.nomSoc = nomSoc;
	}
	public String getFamSoc() {
		return famSoc;
	}
	public void setFamSoc(String famSoc) {
		this.famSoc = famSoc;
	}
	public String getFormeJur() {
		return formeJur;
	}
	public void setFormeJur(String formeJur) {
		this.formeJur = formeJur;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	
	public double getTotalReleve() {
		return totalReleve;
	}
	public void setTotalReleve(double totalReleve) {
		this.totalReleve = totalReleve;
	}
	public String getGsm1() {
		return gsm1;
	}
	public void setGsm1(String gsm1) {
		this.gsm1 = gsm1;
	}
	public String getGsm2() {
		return gsm2;
	}
	public void setGsm2(String gsm2) {
		this.gsm2 = gsm2;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getSiteweb() {
		return siteweb;
	}
	public void setSiteweb(String siteweb) {
		this.siteweb = siteweb;
	}
	public double getSeuil() {
		return seuil;
	}
	public void setSeuil(double seuil) {
		this.seuil = seuil;
	}
	
	
	
	public String getDateReleve() {
		return dateReleve;
	}
	public void setDateReleve(String dateReleve) {
		this.dateReleve = dateReleve;
	}
	public double getTotaldu() {
		return totaldu;
	}
	public void setTotaldu(double totaldu) {
		this.totaldu = totaldu;
	}
	public String getRisque() {
		return risque;
	}
	public void setRisque(String risque) {
		this.risque = risque;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getNumPatente() {
		return numPatente;
	}
	public void setNumPatente(String numPatente) {
		this.numPatente = numPatente;
	}
	public String getNumRegistre() {
		return numRegistre;
	}
	public void setNumRegistre(String numRegistre) {
		this.numRegistre = numRegistre;
	}
	public String getNumTva() {
		return numTva;
	}
	public void setNumTva(String numTva) {
		this.numTva = numTva;
	}

	public double getTotalCumul() {
		return totalCumul;
	}
	public void setTotalCumul(double totalCumul) {
		this.totalCumul = totalCumul;
	}
	public String getNumIce() {
		return numIce;
	}
	public void setNumIce(String numIce) {
		this.numIce = numIce;
	}
	@JsonIgnore
	public Collection<Representant> getRepresentants() {
		return representants;
	}
	public void setRepresentants(Collection<Representant> representants) {
		this.representants = representants;
	}
	@JsonIgnore
	public Collection<Facture> getFactures() {
		return factures;
	}
	public void setFactures(Collection<Facture> factures) {
		this.factures = factures;
	}
	@JsonIgnore
	public Collection<Avoir> getAvoirs() {
		return avoirs;
	}
	public void setAvoirs(Collection<Avoir> avoirs) {
		this.avoirs = avoirs;
	}
	@JsonIgnore
	public Collection<CommandeClient> getCommandeClients() {
		return commandeClients;
	}
	public void setCommandeClients(Collection<CommandeClient> commandeClients) {
		this.commandeClients = commandeClients;
	}
	
	
	
	
	
	public String getReleveCompte() {
		return releveCompte;
	}
	public void setReleveCompte(String releveCompte) {
		this.releveCompte = releveCompte;
	}
	@JsonIgnore
	public Collection<ModePaiement> getModePaiements() {
		return modePaiements;
	}
	public void setModePaiements(Collection<ModePaiement> modePaiements) {
		this.modePaiements = modePaiements;
	}
	
	@JsonIgnore
	public Collection<BonLivraison> getBonLivs() {
		return bonLivs;
	}
	public void setBonLivs(Collection<BonLivraison> bonLivs) {
		this.bonLivs = bonLivs;
	}
	public Client(String nomSoc, String famSoc, String formeJur, String tel1, String tel2, String fax, String gsm1,
			String gsm2, String email1, String email2, String adresse, String siteweb, double seuil, double totaldu,
			String risque, String etat, String numPatente, String numRegistre, String numTva, String numIce) {
		super();
		this.nomSoc = nomSoc;
		this.famSoc = famSoc;
		this.formeJur = formeJur;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.fax = fax;
		this.gsm1 = gsm1;
		this.gsm2 = gsm2;
		this.email1 = email1;
		this.email2 = email2;
		this.adresse = adresse;
		this.siteweb = siteweb;
		this.seuil = seuil;
		this.totaldu = totaldu;
		this.risque = risque;
		this.etat = etat;
		this.numPatente = numPatente;
		this.numRegistre = numRegistre;
		this.numTva = numTva;
		this.numIce = numIce;
	}
	
	
	public Client(String nomSoc, String famSoc, String formeJur, String tel1, String tel2, String fax, String gsm1,
			String gsm2, String email1, String email2, String adresse, String siteweb, double seuil, String risque,
			String numPatente, String numRegistre, String numTva, String numIce) {
		super();
		this.nomSoc = nomSoc;
		this.famSoc = famSoc;
		this.formeJur = formeJur;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.fax = fax;
		this.gsm1 = gsm1;
		this.gsm2 = gsm2;
		this.email1 = email1;
		this.email2 = email2;
		this.adresse = adresse;
		this.siteweb = siteweb;
		this.seuil = seuil;
		this.risque = risque;
		this.numPatente = numPatente;
		this.numRegistre = numRegistre;
		this.numTva = numTva;
		this.numIce = numIce;
	}
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	

}
