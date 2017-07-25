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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Facture implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idFac ;
	private String numFac ;
	private String dateFac ;
	private String heureFac ;
	private String dateRegleFac ;
	private double totalFac ;
	private double totaltvaFac ;
	private double totalttcFac ;
	private double resteEnc ;
	private double resteRem ;
	private String dateEnc ;
	private double montantEnc ;
	
	private String remarqueFac ;
	private String totalString ;
	private String fichierFac ;
	private String etatFac ;
	private String typeFac ;
	private String paiement ;
	private Date dateVraiFac;
	private String categorieFacture ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<ArticleListe> articles ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<BonLivraison> bonLivraisons ;
	@OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Collection<CommandeClient> commandes;
	@ManyToOne
	@JoinColumn(name="codeClient")
	private Client client ;
	
	@ManyToOne
	@JoinColumn(name="codeFournisseur")
	private Fournisseur fournisseur ;
	
	@ManyToOne
	@JoinColumn(name="codePaiement")
	private ModePaiement modePaiement ;
	
	public long getIdFac() {
		return idFac;
	}

	public void setIdFac(long idFac) {
		this.idFac = idFac;
	}

	public String getNumFac() {
		return numFac;
	}

	public void setNumFac(String numFac) {
		this.numFac = numFac;
	}

	public String getDateFac() {
		return dateFac;
	}

	public void setDateFac(String dateFac) {
		this.dateFac = dateFac;
	}
	
	

	public String getPaiement() {
		return paiement;
	}

	public void setPaiement(String paiement) {
		this.paiement = paiement;
	}

	public String getHeureFac() {
		return heureFac;
	}

	public void setHeureFac(String heureFac) {
		this.heureFac = heureFac;
	}

	public String getDateRegleFac() {
		return dateRegleFac;
	}

	public void setDateRegleFac(String dateRegleFac) {
		this.dateRegleFac = dateRegleFac;
	}

	public double getTotalFac() {
		return totalFac;
	}

	public void setTotalFac(double totalFac) {
		this.totalFac = totalFac;
	}

	public String getRemarqueFac() {
		return remarqueFac;
	}

	public void setRemarqueFac(String remarqueFac) {
		this.remarqueFac = remarqueFac;
	}

	public String getFichierFac() {
		return fichierFac;
	}

	public void setFichierFac(String fichierFac) {
		this.fichierFac = fichierFac;
	}

	public String getEtatFac() {
		return etatFac;
	}

	public void setEtatFac(String etatFac) {
		this.etatFac = etatFac;
	}
	

	public double getResteEnc() {
		return resteEnc;
	}

	public void setResteEnc(double resteEnc) {
		this.resteEnc = resteEnc;
	}

	public double getResteRem() {
		return resteRem;
	}

	public void setResteRem(double resteRem) {
		this.resteRem = resteRem;
	}

	public String getDateEnc() {
		return dateEnc;
	}

	public void setDateEnc(String dateEnc) {
		this.dateEnc = dateEnc;
	}

	public double getMontantEnc() {
		return montantEnc;
	}

	public void setMontantEnc(double montantEnc) {
		this.montantEnc = montantEnc;
	}

	public String getTypeFac() {
		return typeFac;
	}

	public void setTypeFac(String typeFac) {
		this.typeFac = typeFac;
	}

	public Date getDateVraiFac() {
		return dateVraiFac;
	}

	public void setDateVraiFac(Date dateVraiFac) {
		this.dateVraiFac = dateVraiFac;
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
	
	
	@JsonIgnore
	public Collection<BonLivraison> getBonLivraisons() {
		return bonLivraisons;
	}
	public void setBonLivraisons(Collection<BonLivraison> bonLivraisons) {
		this.bonLivraisons = bonLivraisons;
	}
	
	
	
	public String getTotalString() {
		return totalString;
	}

	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}

	@JsonIgnore
	public Collection<CommandeClient> getCommandes() {
		return commandes;
	}
	public void setCommandes(Collection<CommandeClient> commandes) {
		this.commandes = commandes;
	}
	
	public String getCategorieFacture() {
		return categorieFacture;
	}
	public void setCategorieFacture(String categorieFacture) {
		this.categorieFacture = categorieFacture;
	}
	
	
	public ModePaiement getModePaiement() {
		return modePaiement;
	}
	public void setModePaiement(ModePaiement modePaiement) {
		this.modePaiement = modePaiement;
	}
	
	public String getDateRegle() {
		return dateRegleFac;
	}
	public void setDateRegle(String dateRegle) {
		this.dateRegleFac = dateRegle;
	}
	
	public double getTotaltvaFac() {
		return totaltvaFac;
	}

	public void setTotaltvaFac(double totaltvaFac) {
		this.totaltvaFac = totaltvaFac;
	}

	public double getTotalttcFac() {
		return totalttcFac;
	}

	public void setTotalttcFac(double totalttcFac) {
		this.totalttcFac = totalttcFac;
	}

	public Facture(String num, String date, String heure, double total, String remarque, String fichier,
			Collection<ArticleListe> articles, Client client) {
		super();
		this.numFac = num;
		this.dateFac = date;
		this.heureFac = heure;
		this.totalFac = total;
		this.remarqueFac = remarque;
		this.fichierFac = fichier;
		this.articles = articles;
		this.client = client;
	}
	
	
	
	public Facture(String num, String date, String heure, double total, String remarque, Collection<ArticleListe> articles) {
		super();
		this.numFac = num;
		this.dateFac = date;
		this.heureFac = heure;
		this.totalFac = total;
		this.remarqueFac = remarque;
		this.articles = articles;
	}
	public Facture(String num, String date, String heure, double total, String remarque, String fichier,
			Collection<ArticleListe> articles) {
		super();
		this.numFac = num;
		this.dateFac = date;
		this.heureFac = heure;
		this.totalFac = total;
		this.remarqueFac = remarque;
		this.fichierFac = fichier;
		this.articles = articles;
	}
	
	
	
	public Facture(String num, String date, String heure, double total, String etat) {
		super();
		this.numFac = num;
		this.dateFac = date;
		this.heureFac = heure;
		this.totalFac = total;
		this.etatFac = etat;
	}
	public Facture() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	
	
	
	

}
