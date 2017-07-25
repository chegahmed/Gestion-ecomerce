package com.entreprise.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ArticleListe implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idArtListe ;
	private String designationAl ;
	private double prixUnitAl ;
	private int quantiteAl ;
	private double prixtotalAl ;
	private String dateEnregAl ;
	private Date dateVraiAl ;
	
	private String typeAl ;
	private String typeArt ;
	private int nbreLivre ;
	private String statLivre ;
	@ManyToOne
	@JoinColumn(name="codeArticle")
	private Article article ;
	@ManyToOne
	@JoinColumn(name="codeClient")
	private Client client ;
	
	@ManyToOne
	private Facture facture ;
	
	
	//@ManyToOne
	@ManyToOne(cascade = CascadeType.ALL)
	private BonLivraison bonLivraison ;
	
	@ManyToOne
	private CommandeClient commandeClient ;
	
	@ManyToOne
	private CommandeFournisseur commandeFournisseur ;
	
	@ManyToOne
	private Avoir avoir ;
	
	@ManyToOne
	@JoinColumn(name="codeFourn")
	private Fournisseur fournisseur ;
	
	public String getDesignationAl() {
		return designationAl;
	}
	public void setDesignationAl(String designationAl) {
		this.designationAl = designationAl;
	}
	public double getPrixUnitAl() {
		return prixUnitAl;
	}
	public void setPrixUnitAl(double prixUnitAl) {
		this.prixUnitAl = prixUnitAl;
	}
	public int getQuantiteAl() {
		return quantiteAl;
	}
	public void setQuantiteAl(int quantiteAl) {
		this.quantiteAl = quantiteAl;
	}
	public double getPrixtotalAl() {
		return prixtotalAl;
	}
	public void setPrixtotalAl(double prixtotalAl) {
		this.prixtotalAl = prixtotalAl;
	}
	public String getDateEnregAl() {
		return dateEnregAl;
	}
	public void setDateEnregAl(String dateEnregAl) {
		this.dateEnregAl = dateEnregAl;
	}
	public Date getDateVraiAl() {
		return dateVraiAl;
	}
	public void setDateVraiAl(Date dateVraiAl) {
		this.dateVraiAl = dateVraiAl;
	}
	public String getTypeAl() {
		return typeAl;
	}
	public void setTypeAl(String typeAl) {
		this.typeAl = typeAl;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

	public String getStatLivre() {
		return statLivre;
	}
	public void setStatLivre(String statLivre) {
		this.statLivre = statLivre;
	}
	public long getIdArtListe() {
		return idArtListe;
	}
	public void setIdArtListe(long idArtListe) {
		this.idArtListe = idArtListe;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	
	

	
	
	public String getTypeArt() {
		return typeArt;
	}
	public void setTypeArt(String typeArt) {
		this.typeArt = typeArt;
	}
	public Avoir getAvoir() {
		return avoir;
	}
	public void setAvoir(Avoir avoir) {
		this.avoir = avoir;
	}
	public Facture getFacture() {
		return facture;
	}
	public void setFacture(Facture facture) {
		this.facture = facture;
	}
	public BonLivraison getBonLivraison() {
		return bonLivraison;
	}
	public void setBonLivraison(BonLivraison bonLivraison) {
		this.bonLivraison = bonLivraison;
	}
	
	
	public CommandeFournisseur getCommandeFournisseur() {
		return commandeFournisseur;
	}
	public void setCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
		this.commandeFournisseur = commandeFournisseur;
	}
	public CommandeClient getCommandeClient() {
		return commandeClient;
	}
	public void setCommandeClient(CommandeClient commandeClient) {
		this.commandeClient = commandeClient;
	}
	
	
	
	public int getNbreLivre() {
		return nbreLivre;
	}
	public void setNbreLivre(int nbreLivre) {
		this.nbreLivre = nbreLivre;
	}
	public ArticleListe(String designation, double prixUnit, int quantite, double prixtotal, String type,
			Client client) {
		super();
		this.designationAl = designation;
		this.prixUnitAl = prixUnit;
		this.quantiteAl = quantite;
		this.prixtotalAl = prixtotal;
		this.typeAl = type;
		this.client = client;
	}
	public ArticleListe(String designation, double prixUnit, int quantite, double prixtotal, String type) {
		super();
		this.designationAl = designation;
		this.prixUnitAl = prixUnit;
		this.quantiteAl = quantite;
		this.prixtotalAl = prixtotal;
		this.typeAl = type;
	}
	
	public ArticleListe(String designation, double prixUnit, int quantite, double prixtotal, Article article) {
		super();
		this.designationAl = designation;
		this.prixUnitAl = prixUnit;
		this.quantiteAl = quantite;
		this.prixtotalAl = prixtotal;
		this.article = article;
	}
	public ArticleListe(String designation, double prixUnit, int quantite, double prixtotal) {
		super();
		this.designationAl = designation;
		this.prixUnitAl = prixUnit;
		this.quantiteAl = quantite;
		this.prixtotalAl = prixtotal;
	}
	public ArticleListe() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ArticleListe(String designationAl, double prixUnitAl,String typeArt) {
		super();
		this.designationAl = designationAl;
		this.prixUnitAl = prixUnitAl;
		this.typeArt = typeArt;
	}
	
	
	
	
	

}
