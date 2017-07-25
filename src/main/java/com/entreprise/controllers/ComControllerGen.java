package com.entreprise.controllers;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bouncycastle.util.encoders.Base64;
import org.castor.core.util.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.entreprise.Repository.IArticleR;
import com.entreprise.Repository.IAvoirR;
import com.entreprise.Repository.IBonCommandeR;
import com.entreprise.Repository.IBonLivraisonR;
import com.entreprise.Repository.ICategorieR;
import com.entreprise.Repository.IClientR;
import com.entreprise.Repository.ICommandeFournisseurR;
import com.entreprise.Repository.IFactureR;
import com.entreprise.Repository.IFournisseurR;
import com.entreprise.Repository.IModePaiementR;
import com.entreprise.Repository.IUsersR;
import com.entreprise.entities.Article;
import com.entreprise.entities.ArticleListe;
import com.entreprise.entities.AutrePaiement;
import com.entreprise.entities.Avoir;
import com.entreprise.entities.BonLivraison;
import com.entreprise.entities.Categorie;
import com.entreprise.entities.Client;
import com.entreprise.entities.CommandeClient;
import com.entreprise.entities.CommandeFournisseur;
import com.entreprise.entities.CompteBancaire;
import com.entreprise.entities.Facture;
import com.entreprise.entities.Fournisseur;
import com.entreprise.entities.ModePaiement;
import com.entreprise.entities.NumeroDisque;
import com.entreprise.entities.PaiementCheque;
import com.entreprise.entities.Representant;
import com.entreprise.entities.StatArticle;
import com.entreprise.entities.User;
import com.entreprise.metier.IComMetier;


@Controller
@RestController
public class ComControllerGen {
	@Autowired
	private IComMetier metier ;
	@Autowired
	private IArticleR iArticleR ;
	@Autowired
	private IAvoirR iAvoirR ;
	@Autowired
	private IBonCommandeR iBonCommandeR ;
	@Autowired
	private IBonLivraisonR iBonLivraisonR ;
	@Autowired
	private ICategorieR iCategorieR ;
	@Autowired
	private IClientR iClientR ;
	@Autowired
	private ICommandeFournisseurR iCommandeFournisseurR ;
	@Autowired
	private IFactureR iFactureR ;
	@Autowired
	private IFournisseurR iFournisseurR ;
	@Autowired
	private IModePaiementR iModePaiementR ;
	@Autowired
	private IUsersR iUsersR ;
	
	
	@RequestMapping("/all")
	public List<Client> getClients() {
		return  iClientR.findAll() ;
	}
	
	@RequestMapping("/addClient")
	public Client addClient(Client c) {
		return metier.addClient(c);
	}
	
	@RequestMapping("/addArticle")
	public Article addArticle(Article a , Long idCat) {
		return metier.addArticle(a, idCat);
	}
	
	@RequestMapping("/test")
	private String test() {
		return "test" ;
	}
	
	
	@RequestMapping(value="/allrepresentantsClient")
	public List<Representant> getAllRepresentantsClient() {
		return  metier.getAllRepresentantsClient();
		
	}
	
	@RequestMapping(value="/allrepresentantsFourn")
	public List<Representant> getAllRepresentantsFourn() {
		return  metier.getAllRepresentantsFourn();
		
	}
	
	@RequestMapping(value="/addClientRep")
	public Client addClientRep(Client c , Representant r) {
		return  metier.addClientRepresentant(c, r);
		
	}
	
	@RequestMapping(value="/allModePaiements")
	public List<ModePaiement> allModePaiements(int position , int nbre) {
		return  metier.getAllModePaies(position , nbre);
		
	}
	
	@RequestMapping(value="/addModePautreCl")
	public void addModePCl(AutrePaiement m , Long idCl) {
		metier.addModePautreCl(m, idCl);
		
	}
	
	@RequestMapping(value="/addModePchequeCl")
	public void addModePchequeCl(PaiementCheque m , Long idCl) {
		metier.addModePchequeCl(m, idCl);
		
	}
	
	@RequestMapping(value="/addModePbanqueCl")
	public void addModePbanqueCl(CompteBancaire m , Long idCl) {
		metier.addModePbanqueCl(m, idCl);
		
	}
	
	@RequestMapping(value="/allCategories")
	public List<Categorie> getAllCategories() {
		return metier.getAllCategories();
		
	}
	
	@RequestMapping(value="/allArticles")
	public List<Article> getAllArticles() {
		return  metier.getAllArticles();
		
	}
	
	
	
	@RequestMapping(value="/allFactures")
	public List<Facture> getAllFactures(int position , int nbre) {
		return  metier.getAllFactures(position , nbre);
		
	}
	
	
	/*@RequestMapping(value="/addCommandeClient")
	public CommandeClient addCommandeCclient(CommandeClient b ,List<ArticleListe> l , Long idCl) {
		return  metier.addBCommande(b, l, idCl);
		
		
		
	}*/
	
	
	@RequestMapping(value="/addCommandeClientD")
	public CommandeClient addCommandeClientD(CommandeClient b , double prixTotalCommande , Long idCl) {
		return  metier.addCommandeClientSimpleD(b,prixTotalCommande , idCl);
		
	}
	
	
	@RequestMapping(value="/addCommandeClient")
	public CommandeClient addCommandeClient(CommandeClient b , double prixTotalCommande , Long idCl) {
		return  metier.addCommandeClientSimple(b,prixTotalCommande , idCl);
		
	}
	
	@RequestMapping(value="/addArticleToCommande")
	public ArticleListe addArticleToCommande(ArticleListe a , Long idArt) {
		return  metier.addArticleToBonCommande(a , idArt);
		
	}
	
	@RequestMapping(value="/getArticlesForCommandeClient")
	public List<ArticleListe> getArticlesForCommandeClient(Long idComCl) {
		return  metier.getArticlesForCommande(idComCl);
		
	}
	
	@RequestMapping(value="/getArticlesForLivraisonClient")
	public List<ArticleListe> getArticlesForLivraisonClient(Long idBl) {
		
		return  metier.getArticlesForLivraisonClient(idBl);
		
	}
	
	@RequestMapping(value="/allBonLivraisonClient")
	public List<BonLivraison> getAllBonCommandeClient(int position , int nbre) {
		return metier.getBonLivraisonClient(position,nbre);
	}
	
	
	@RequestMapping(value="/addBonLivraisonClient")
	public void addBonLivraisonClient(BonLivraison b , Long idComCl) {
		metier.addBonLivraisonClient(b, idComCl);
	}
	
	@RequestMapping(value="/addBonLivraisonFournisseur")
	public void addBonLivraisonFournisseur(BonLivraison b , Long idCom) {
		metier.addBonLivraisonFournisseur(b, idCom);
	}
	
	@RequestMapping(value="/addFactureAccompteClient")
	public void addFfactureAccompteClient(Facture f , Long idBl) {
		metier.addFactureAccompteClient(f, idBl);
	}
	
	@RequestMapping(value="/addFactureAccompteClientM")
	public void addFfactureAccompteClientM(Facture f , Long[] data) {
		metier.addFactureAccompteClientM(f, data);
	}
	
	@RequestMapping(value="/addFactureAccompteFournisseur")
	public void addFfactureAccompteFournisseur(Facture f , Long idBl) {
		metier.addFactureAccompteF(f, idBl);;
	}
	
	@RequestMapping(value="/addFactureDirecteE")
	public void addFactureDirecteClientE(Facture f , String nomMode ) {
		metier.addFactureDirecteE(f,nomMode);
	}
	
	@RequestMapping(value="/addFactureDirecteC")
	public void addFactureDirecteClientC(Facture f , String nomMode , String numCheque , String nomBanque) {
		metier.addFactureDirecteC(f,nomMode ,numCheque , nomBanque);
	}
	
	@RequestMapping(value="/addFactureDirecteB")
	public void addFactureDirecteClientB(Facture f , String nomMode  ,String numCompte , String nomBanque) {
		metier.addFactureDirecteB(f,nomMode ,numCompte , nomBanque);
	}
	
	@RequestMapping(value="/getFactureClient")
	public List<Facture> getFactureClient() {
		return metier.getFactureClient() ;
	}
	
	@RequestMapping(value="/getFactureDirecte")
	public List<Facture> getFactureDirecte() {
		return metier.getFactureDirecte() ;
	}
	
	@RequestMapping(value="/getFactureAccompte")
	public List<Facture> getFactureAccompte() {
		return metier.getFactureAccompte() ;
	}
	
	@RequestMapping(value="/getArticlesFacture")
	public List<ArticleListe> getArticlesFacture(Long idFac) {
		return metier.getArticlesForFacture(idFac) ;
	}
	
	@RequestMapping(value="/getModePaiementClient")
	public List<ModePaiement> getModePaiementClient(Long idCl) {
		return metier.getModePaiementByClient(idCl) ;
	}
	
	@RequestMapping(value="/getModePaiementF")
	public List<ModePaiement> getModePaiementF(Long idF) {
		return metier.getModePaiesByF(idF) ;
	}
	
	
	@RequestMapping(value="/updatePrixCommande")
	public double prixCommande() {
		   return  metier.updatePrixCommande() ;
	}
	
	@RequestMapping(value="/addAvoirClient")
	public Avoir addAvoirClient(Avoir a , double prixTotalAvoir, Long idCl) {
		return  metier.addAvoirClient(a, idCl , prixTotalAvoir);
		
	}
	
	@RequestMapping(value="/addArticleToAvoir")
	public ArticleListe addArticleToAvoir(ArticleListe a , Long idArt) {
		return  metier.addArticleToAvoir(a , idArt);
		
	}
	
	@RequestMapping(value="/getArticleForAvoir")
	public List<ArticleListe> getArticlesForAvoir(Long idAv) {
		return  metier.getArticlesForAvoir(idAv);
		
	}
	
	@RequestMapping(value="/allAvoirsClient")
	public List<Avoir> getAllAvoirsClient() {
		return  metier.getAllAvoirs();
		
	}
	
	@RequestMapping(value="/addCommandeFourn")
	public CommandeFournisseur addCommandeF(CommandeFournisseur c , Long idFourn ,  double prixTotalCommande) {
		return  metier.addCommandeFournisseur(c, idFourn , prixTotalCommande) ;
		
	}
	
	@RequestMapping(value="/getLastComFourn")
	public CommandeFournisseur getLastComFourn() {
		return  metier.getLastCommandeFour() ;
		
	}
	
	@RequestMapping(value="/addFournisseur")
	public Fournisseur addFournisseur(Fournisseur f) {
		return  metier.addFournisseur(f) ;
		
	}
	
	@RequestMapping(value="/allFournisseur")
	public List<Fournisseur> allFournisseur() {
		return  metier.getAllFournisseurs();
		
	}
	
	@RequestMapping(value="/addBonLivFour")
	public void addBonLivFourn(BonLivraison b , Long idComFour) {
		  metier.addBonLivraisonFournisseur(b, idComFour);
		
	}
	
	@RequestMapping(value="/allBonLivFourn")
	public List<BonLivraison> allBonsFournisseur() {
		return  metier.getBonLivraisonFourn();
		
	}
	
	@RequestMapping(value="/addFournRep")
	public Fournisseur addFournisseurRepresentant(Fournisseur f, Representant r) {
		return  metier.addFournisseurRepresentant(f, r);
		
	}
	
	@RequestMapping(value="/allComFourn")
	public List<CommandeFournisseur> getAllComFourn(int position, int nbre) {
		return  metier.getAllComFournisseurs(position,nbre);
		
	}
	
	@RequestMapping(value="/getArticlesForCommandeFour")
	public List<ArticleListe> getArticlesForCommandeFour(Long idComF) {
		return  metier.getArticlesForCommandeFour(idComF) ;
		
	}
	
	@RequestMapping(value="/getArticlesForBonLivsFour")
	public List<ArticleListe> getArticlesForBonLivsFour(Long idBl) {
		return  metier.getArticlesForBonLivs(idBl) ;
		
	}
	
	@RequestMapping(value="/addArticleToCommandeFourn")
	public ArticleListe addArticleToCommandeFourn(ArticleListe a , Long idArt) {
		return  metier.addArticleToBonCommandeFourn(a, idArt);
		
	}
	
	@RequestMapping(value="/addAvoirFourn")
	public Avoir addAvoirFourn(Avoir a , Long idF , double prixTotalAvoir) {
		return  metier.addAvoirFournisseur(a, idF , prixTotalAvoir);
		
	}
	
	@RequestMapping(value="/addArticleToAvoirFourn")
	public ArticleListe addArticleToAvoirFourn(ArticleListe a , Long idArt) {
		return  metier.addArticleToAvoirFourn(a , idArt);
		
	}
	
	
	@RequestMapping(value="/allAvoirsFourn")
	public List<Avoir> getAllAvoirsFourn() {
		return  metier.getAllAvoirs();
		
	}
	
	@RequestMapping(value="/addRepToClient")
	public void addRepToClient(Representant r , Long idCl) {
		metier.addRepresentantToClient(r, idCl);
		
	}
	
	@RequestMapping(value="/addRepToFourn")
	public void addRepToFourn(Representant r , Long idF) {
		metier.addRepresentantToFourn(r, idF);
		
	}
	
	@RequestMapping("/addCategorie")
	public Categorie addCategorie(Categorie c) {
		return metier.addCat(c);
	}
	
	@RequestMapping("/getArticleByDesignation")
	public Article getArticleByDesignation(String designation) {
		return metier.getArticleByNom(designation);
	}
	
	// Les Commandes Client
	
	@RequestMapping("/RechercheCommandeByIdandNomClient")
	public List<CommandeClient> RechercheCommandeByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getCommandeByNom(motCle,position ,nbre);
	}
	
	@RequestMapping("/nbreCommandesClient")
	public Long nbreCommandesClient() {
		return metier.getNombreCommandes() ;
	}
	
	@RequestMapping(value="/getCommandesClient")
	public List<CommandeClient> getAllCommandesClient(int position , int nbre) {
		return  metier.getAllBonCommandes(position ,nbre);
		
	}
	
	@RequestMapping(value="/allCommandesClient")
	public List<CommandeClient> allCommandesClient() {
		return  metier.allCommandesClient();
		
	}
	
	@RequestMapping("/nbreCommandesClientMC")
	public Long nbreCommandesClientMC(String motCle) {
		return metier.getNombreCommandesMC(motCle) ;
	}
	
	
	
	// Les Commandes Client traitées
	
		@RequestMapping("/RechercheCommandeByIdandNomClientT")
		public List<CommandeClient> RechercheCommandeByIdandNomClientT(String motCle , int position , int nbre) {
			return metier.getCommandeByNomTraitees(motCle, position, nbre);
		}
		
		@RequestMapping("/nbreCommandesClientT")
		public Long nbreCommandesClientT() {
			return metier.getNombreCommandesTraitees();
		}
		
		@RequestMapping(value="/getCommandesClientT")
		public List<CommandeClient> getAllCommandesClientT(int position , int nbre) {
			return  metier.getAllBonCommandesTraitees(position ,nbre);
			
		}
		
		@RequestMapping(value="/allCommandesClientT")
		public List<CommandeClient> allCommandesClientT() {
			return  metier.allCommandesClientTraitees();
			
		}
		
		@RequestMapping("/nbreCommandesClientMCT")
		public Long nbreCommandesClientMCT(String motCle) {
			return metier.getNombreCommandesMCTraitees(motCle) ;
		}
		
		
		
		// Les Commandes Client non traitées
		
		@RequestMapping("/RechercheCommandeByIdandNomClientNT")
		public List<CommandeClient> RechercheCommandeByIdandNomClientNT(String motCle , int position , int nbre) {
			return metier.getCommandeByNomNTraitees(motCle, position, nbre);
		}
		
		@RequestMapping("/nbreCommandesClientNT")
		public Long nbreCommandesClientNT() {
			return metier.getNombreCommandesNTraitees();
		}
		
		@RequestMapping(value="/getCommandesClientNT")
		public List<CommandeClient> getAllCommandesClientNT(int position , int nbre) {
			return  metier.getAllBonCommandesNTraitees(position ,nbre);
			
		}
		
		@RequestMapping(value="/allCommandesClientNT")
		public List<CommandeClient> allCommandesClientNT() {
			return  metier.allCommandesClientNTraitees();
			
		}
		
		@RequestMapping("/nbreCommandesClientMCNT")
		public Long nbreCommandesClientMCNT(String motCle) {
			return metier.getNombreCommandesMCNTraitees(motCle) ;
		}
		
		
	
	// LISTE BONLIVRAISONS Client
	
	@RequestMapping("/RechercheBLByIdandNomClient")
	public List<BonLivraison> RechercheBLByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getBLClientByNom(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreBLClient")
	public Long nbreBLClient() {
		return metier.getNombreBLClient() ;
	}
	
	@RequestMapping(value="/getBLClient")
	public List<BonLivraison> getAllBLClient(int position , int nbre) {
		return  metier.getBonLivraisonClient(position, nbre);
		
	}
	
	@RequestMapping(value="/allBLClient")
	public List<BonLivraison> allBLClient() {
		return  metier.allBLClient();
		
	}
	
	@RequestMapping("/nbreBLClientMC")
	public Long nbreBLClientMC(String motCle) {
		return metier.getNombreBLMC(motCle) ;
	}
	
	
	
	
	// Liste bons Livraisons Traités
	
	@RequestMapping("/RechercheBLByIdandNomClientT")
	public List<BonLivraison> RechercheBLByIdandNomClientT(String motCle , int position , int nbre) {
		return metier.getBLClientByNomTraitees(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreBLClientT")
	public Long nbreBLClientT() {
		return metier.getNombreBLClientTraitees() ;
	}
	
	@RequestMapping(value="/getBLClientT")
	public List<BonLivraison> getAllBLClientT(int position , int nbre) {
		return  metier.getAllBLTClient(position, nbre);
		
	}
	
	@RequestMapping(value="/allBLClientT")
	public List<BonLivraison> allBLClientT() {
		return  metier.allBLClientTraitees();
		
	}
	
	@RequestMapping("/nbreBLClientMCT")
	public Long nbreBLClientMCT(String motCle) {
		return metier.getNombreBLMCTraitees(motCle) ;
	}
	
	
	
	
	// Liste bons Livraisons NON  Traités
	
		@RequestMapping("/RechercheBLByIdandNomClientNT")
		public List<BonLivraison> RechercheBLByIdandNomClientNT(String motCle , int position , int nbre) {
			return metier.getBLClientByNomNTraitees(motCle, position, nbre);
		}
		
		@RequestMapping("/nbreBLClientNT")
		public Long nbreBLClientNT() {
			return metier.getNombreBLClientNTraitees() ;
		}
		
		@RequestMapping(value="/getBLClientNT")
		public List<BonLivraison> getAllBLClientNT(int position , int nbre) {
			return  metier.getAllBLNTClient(position, nbre);
			
		}
		
		@RequestMapping(value="/allBLClientNT")
		public List<BonLivraison> allBLClientNT() {
			return  metier.allBLClientNTraitees();
			
		}
		
		@RequestMapping("/nbreBLClientMCNT")
		public Long nbreBLClientMCNT(String motCle) {
			return metier.getNombreBLMCNTraitees(motCle) ;
		}
	
	
	
	
	//Factures client
	
	@RequestMapping("/RechercheFacByIdandNomClient")
	public List<Facture> RechercheFacByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getFacClientByNom(motCle, position, nbre) ;
	}
	
	@RequestMapping("/nbreFacClient")
	public Long nbreFacClient() {
		return metier.getNombreFacClient() ;
	}
	
	@RequestMapping(value="/getFacClient")
	public List<Facture> getAllFacClient(int position , int nbre) {
		return  metier.getAllFactures(position, nbre) ;
		
	}
	
	@RequestMapping(value="/allFacClient")
	public List<Facture> allFacClient() {
		return  metier.allFacClient();
		
	}
	
	@RequestMapping("/nbreFacClientMC")
	public Long nbreFacClientMC(String motCle) {
		return metier.getNombreFacMC(motCle) ;
	}
	
	
	//Factures traitées client
	
	
	@RequestMapping("/RechercheFacByIdandNomClientT")
	public List<Facture> RechercheFacByIdandNomClientT(String motCle , int position , int nbre) {
		return metier.getFacClientByNomTraitees(motCle, position, nbre) ;
	}
	
	@RequestMapping("/nbreFacClientT")
	public Long nbreFacClientT() {
		return metier.getNombreFacClientTraitees() ;
	}
	
	@RequestMapping(value="/getFacClientT")
	public List<Facture> getAllFacClientT(int position , int nbre) {
		return  metier.getAllFacturesTraitees(position, nbre) ;
		
	}
	
	@RequestMapping(value="/allFacClientT")
	public List<Facture> allFacClientT() {
		return  metier.allFacClientTraitees();
		
	}
	
	@RequestMapping("/nbreFacClientMCT")
	public Long nbreFacClientMCT(String motCle) {
		return metier.getNombreFacMCTraitees(motCle) ;
	}
	
	
	
	
	//Factures non traitées client
	
	
		@RequestMapping("/RechercheFacByIdandNomClientNT")
		public List<Facture> RechercheFacByIdandNomClientNT(String motCle , int position , int nbre) {
			return metier.getFacClientByNomNTraitees(motCle, position, nbre) ;
		}
		
		@RequestMapping("/nbreFacClientNT")
		public Long nbreFacClientNT() {
			return metier.getNombreFacClientNTraitees() ;
		}
		
		@RequestMapping(value="/getFacClientNT")
		public List<Facture> getAllFacClientNT(int position , int nbre) {
			return  metier.getAllFacturesNTraitees(position, nbre) ;
			
		}
		
		@RequestMapping(value="/allFacClientNT")
		public List<Facture> allFacClientNT() {
			return  metier.allFacClientNTraitees();
			
		}
		
		@RequestMapping("/nbreFacClientMCNT")
		public Long nbreFacClientMCNT(String motCle) {
			return metier.getNombreFacMCNTraitees(motCle) ;
		}
		
	
	
	
	
	
	
	
	@RequestMapping("/RechercheAvoirByIdandNomClient")
	public List<Avoir> RechercheAvoirByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getAvoirByNom(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreAvoirClient")
	public Long nbreAvoirClient() {
		return metier.getNombreAvoir() ;
	}
	
	@RequestMapping(value="/getAvoirClient")
	public List<Avoir> getAllAvoirClient(int position , int nbre) {
		return  metier.getAllAvoirClient(position, nbre) ;
		
	}
	
	@RequestMapping(value="/allAvoirClient")
	public List<Avoir> allAvoirClient() {
		return  metier.allAvoirClient();
		
	}
	
	@RequestMapping("/nbreAvoirClientMC")
	public Long nbreAvoirClientMC(String motCle) {
		return metier.getNombreAvoirMC(motCle) ;
	}
	
	
	//LES CLIENTS
	
	@RequestMapping("/RechercheClientByIdandNomClient")
	public List<Client> RechercheClientByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getClientByNom(motCle,position ,nbre);
	}
	
	@RequestMapping("/nbreClient")
	public Long nbreClientClient() {
		return metier.getNombreClient() ;
	}
	
	@RequestMapping(value="/getClient")
	public List<Client> getAllClient(int position , int nbre) {
		return  metier.getAllClients(position, nbre) ;
		
	}
	
	@RequestMapping(value="/allClient")
	public List<Client> allClient() {
		return  metier.allClient();
		
	}
	
	@RequestMapping("/nbreClientMC")
	public Long nbreClientMC(String motCle) {
		return metier.getClientMC(motCle) ;
	}
	
	
	@RequestMapping("/RechercheMPByIdandNomClient")
	public List<ModePaiement> RechercheMPByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getMPByNom(motCle,position ,nbre);
	}
	
	@RequestMapping("/nbreMPClient")
	public Long nbreMPClient() {
		return metier.getNombreMP() ;
	}
	
	@RequestMapping(value="/getMPClient")
	public List<ModePaiement> getAllMPClient(int position , int nbre) {
		return  metier.getAllModePaies(position, nbre) ;
		
	}
	
	@RequestMapping(value="/allMPClient")
	public List<ModePaiement> allMPClient() {
		return  metier.allMPClient();
		
	}
	
	@RequestMapping("/nbreMPClientMC")
	public Long nbreMPClientMC(String motCle) {
		return metier.getNombreCategorieMC(motCle) ;
	}
	
	
	@RequestMapping("/RechercheCategorieByIdandNom")
	public List<Categorie> RechercheCategorieByIdandNom(String motCle , int position , int nbre) {
		return metier.getCategorieByNom(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreCategorie")
	public Long nbreCategorie() {
		return metier.getNombreCategorie() ;
	}
	
	@RequestMapping(value="/getCategorie")
	public List<Categorie> getAllCategorie(int position , int nbre) {
		return  metier.getCategories(position, nbre) ;
		
	}
	
	@RequestMapping(value="/allCategorie")
	public List<Categorie> allCategorie() {
		return  metier.allCategorie();
		
	}
	
	@RequestMapping("/nbreCategorieMC")
	public Long nbreCategorieMC(String motCle) {
		return metier.getNombreCategorieMC(motCle) ;
	}
	
	
	
	
	@RequestMapping("/RechercheArticleByIdandNom")
	public List<Article> RechercheArticleByIdandNom(String motCle , int position , int nbre) {
		return metier.getArticleByNom(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreArticle")
	public Long nbreArticle() {
		return metier.getNombreArticle() ;
	}
	
	@RequestMapping(value="/getArticle")
	public List<Article> getAllArticle(int position , int nbre) {
		return  metier.getArticles(position, nbre) ;
		
	}
	
	@RequestMapping(value="/allArticle")
	public List<Article> allArticle() {
		return  metier.allArticleClient();
		
	}
	
	@RequestMapping("/nbreArticleMC")
	public Long nbreArticleMC(String motCle) {
		return metier.getNombreArticleMC(motCle) ;
	}
	
	@RequestMapping("/reglementFacture")
	public Facture reglementFacture(Long idF , Long idM , double montantF , double totaldu) {
		return metier.reglementFacture(idF, idM, montantF , totaldu);
	}
	
	@RequestMapping("/deleteCommandeClient")
	public void deleteCommandeClient(Long idComCl) {
		 metier.deleteBCommande(idComCl);
	}
	
	@RequestMapping("/deleteCommandeF")
	public void deleteCommandeF(Long idFour) {
		 metier.deleteComFour(idFour);
	}
	
	@RequestMapping("/deleteBL")
	public void deleteBL(Long idBL) {
		 metier.deleteBLivr(idBL);
	}
	
	@RequestMapping("/deleteBLF")
	public void deleteBLF(Long idBL) {
		 metier.deleteBLF(idBL);
	}
	
	@RequestMapping("/deleteAvoir")
	public void deleteAvoirClient(Long idAv) {
		 metier.deleteAvoir(idAv);
	}
	
	@RequestMapping("/deleteAvoirF")
	public void deleteAvoirF(Long idAv) {
		 metier.deleteAvoirF(idAv);
	}
	
	@RequestMapping("/deleteFacture")
	public void deleteFacture(Long idF) {
		 metier.deleteFacture(idF);
	}
	
	@RequestMapping("/deleteFactureF")
	public void deleteFactureF(Long idF) {
		 metier.deleteFactureF(idF);
	}
	
	@RequestMapping("/deleteClient")
	public void deleteClient(Long idCl) {
		 metier.deleteClient(idCl);
	}
	
	@RequestMapping("/deleteMP")
	public void deleteMP(Long idMP , Long idCl) {
		 metier.deleteModeP(idMP, idCl);
	}
	
	@RequestMapping("/getLastRepresentantClient")
	public Representant getLastRepresentantClient(Long idCl) {
		 return metier.getLastRepresentantClient(idCl);
	}
	
	@RequestMapping("/getLastRepresentantF")
	public Representant getLastRepresentantF(Long idF) {
		 return metier.getLastRepresentantF(idF);
	}
	
	
	
	@RequestMapping("/allLivs")
	public List<BonLivraison> allBons() {
		return metier.getAllBonLivraisons() ;
	}
	

	@RequestMapping("/statArticleClient")
	public List<ArticleListe>  statArticleClient(Long idArt) {
		return metier.statArticleClient(idArt) ;
	}
	
	@RequestMapping("/longs")
	public List<Long> longs(Long idArt) {
		return metier.longs(idArt) ;
	}
	
	@RequestMapping("/statRechercheMois")
	public StatArticle statistiqueMois(Long idArt) {
		return metier.statRecherche(idArt);
	}
	
/*	@RequestMapping("/getLogedUser")
	public Map<String , Object> getLogedUser(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession() ;
		SecurityContext securityContext = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT") ;
		String username = securityContext.getAuthentication().getName() ;
		List<String> roles =new ArrayList<>() ;
		for(GrantedAuthority ga : securityContext.getAuthentication().getAuthorities()) {
			roles.add(ga.getAuthority()) ;
		}
		Map<String , Object> params = new HashMap<>() ;
		params.put("username", username) ;
		params.put("roles", roles) ;
		return params ;
	}
	*/
	
	
	@RequestMapping("/addUser")
	public User addUser(User u , String role) {
		return metier.addUser(u,role);
	}
	


	@RequestMapping("/deleteUser")
	public void deleteUser(Long idUser) {
		metier.deleteUser(idUser);
	}
	
	@RequestMapping("/allUsers")
	public List<User> allUsers() {
		return metier.getAllUsers();
	}
	
	@RequestMapping("/getArticleByCode")
	public Article getArticleByCode(Long code) {
		return metier.getArticleById(code) ;
	}
	
	/*@RequestMapping("/RechercheCommandeTByIdandNomClient")
	public List<CommandeClient> RechercheCommandeTByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getCommandeByNomTraitees(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreCommandesTClient")
	public Long nbreCommandesTClient() {
		return metier.getNombreCommandesTraitees() ;
	}
	
	@RequestMapping(value="/getCommandesTClient")
	public List<CommandeClient> getAllCommandesTClient(int position , int nbre) {
		return  metier.getAllBonCommandesTraitees(position ,nbre);
		
	}
	
	@RequestMapping(value="/allCommandesTClient")
	public List<CommandeClient> allCommandesTClient() {
		return  metier.allCommandesClientTraitees();
		
	}
	
	@RequestMapping("/nbreCommandesTClientMC")
	public Long nbreCommandesTClientMC(String motCle) {
		return metier.getNombreCommandesMCTraitees(motCle) ;
	}
	
	@RequestMapping("/RechercheCommandeNTByIdandNomClient")
	public List<CommandeClient> RechercheCommandeNTByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getCommandeByNomNTraitees(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreCommandesNTClient")
	public Long nbreCommandesNTClient() {
		return metier.getNombreCommandesNTraitees() ;
	}
	
	@RequestMapping(value="/getCommandesNTClient")
	public List<CommandeClient> getAllCommandesNTClient(int position , int nbre) {
		return  metier.getAllBonCommandesNTraitees(position ,nbre);
		
	}
	
	@RequestMapping(value="/allCommandesNTClient")
	public List<CommandeClient> allCommandesNTClient() {
		return  metier.allCommandesClientNTraitees();
		
	}
	
	@RequestMapping("/nbreCommandesNTClientMC")
	public Long nbreCommandesNTClientMC(String motCle) {
		return metier.getNombreCommandesMCNTraitees(motCle) ;
	}
	
	@RequestMapping("/RechercheBLTByIdandNomClient")
	public List<BonLivraison> RechercheBLTByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getBLClientByNomTraitees(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreBLTClient")
	public Long nbreBTLClient() {
		return metier.getNombreBLClientTraitees() ;
	}
	
	@RequestMapping(value="/getBLTClient")
	public List<BonLivraison> getAllBLTClient(int position , int nbre) {
		return  metier.getAllBLTClient(position, nbre) ;
		
	}
	
	@RequestMapping(value="/allBLTClient")
	public List<BonLivraison> allBLTClient() {
		return  metier.allBLClientTraitees();
		
	}
	
	@RequestMapping("/nbreBLTClientMC")
	public Long nbreBLTClientMC(String motCle) {
		return metier.getNombreBLMCTraitees(motCle) ;
	}
	
	
	@RequestMapping("/RechercheBLNTByIdandNomClient")
	public List<BonLivraison> RechercheBLNTByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getBLClientByNomNTraitees(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreBLNTClient")
	public Long nbreBLNTClient() {
		return metier.getNombreBLClientNTraitees() ;
	}
	
	@RequestMapping(value="/getBLTClient")
	public List<BonLivraison> getAllBLNTClient(int position , int nbre) {
		return  metier.getAllBLNTClient(position, nbre);
		
	}
	
	@RequestMapping(value="/allBLNTClient")
	public List<BonLivraison> allBLNTClient() {
		return  metier.allBLClientNTraitees();
		
	}
	
	@RequestMapping("/nbreBLNTClientMC")
	public Long nbreBLNTClientMC(String motCle) {
		return metier.getNombreBLMCNTraitees(motCle) ;
	}
	
	
	@RequestMapping("/RechercheFacTByIdandNomClient")
	public List<Facture> RechercheFacTByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getFacClientByNomTraitees(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreFacTClient")
	public Long nbreFacTClient() {
		return metier.getNombreFacClientTraitees() ;
	}
	
	@RequestMapping(value="/getFacTClient")
	public List<Facture> getAllFacTClient(int position , int nbre) {
		return  metier.getAllFacturesTraitees(position, nbre) ;
		
	}
	
	@RequestMapping(value="/allFacTClient")
	public List<Facture> allFacTClient() {
		return  metier.allFacClientTraitees();
		
	}
	
	@RequestMapping("/nbreFacTClientMC")
	public Long nbreFacTClientMC(String motCle) {
		return metier.getNombreFacMCTraitees(motCle) ;
	}
	
	
	@RequestMapping("/RechercheFacNTByIdandNomClient")
	public List<Facture> RechercheFacNTByIdandNomClient(String motCle , int position , int nbre) {
		return metier.getFacClientByNomNTraitees(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreFacNTClient")
	public Long nbreFacNTClient() {
		return metier.getNombreFacClientNTraitees() ;
	}
	
	@RequestMapping(value="/getFacTClient")
	public List<Facture> getAllFacNTClient(int position , int nbre) {
		return  metier.getAllFacturesNTraitees(position, nbre);
		
	}
	
	@RequestMapping(value="/allFacNTClient")
	public List<Facture> allFacNTClient() {
		return  metier.allFacClientNTraitees();
		
	}
	
	@RequestMapping("/nbreFacNTClientMC")
	public Long nbreFacNTClientMC(String motCle) {
		return metier.getNombreFacMCNTraitees(motCle) ;
	}
	
	*/
	
	
	// Fournisseurs
	
	
	@RequestMapping("/RechercheCommandeByIdandNomF")
	public List<CommandeFournisseur> RechercheCommandeByIdandNomF(String motCle , int position , int nbre) {
		return metier.getCommandeFByNom(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreCommandesF")
	public Long nbreCommandesF() {
		return metier.getNombreCommandesF() ;
	}
	
	@RequestMapping(value="/getCommandesF")
	public List<CommandeFournisseur> getAllCommandesF(int position , int nbre) {
		return  metier.getAllComFournisseurs(position, nbre);
		
	}
	
	@RequestMapping(value="/allCommandesF")
	public List<CommandeFournisseur> allCommandesF() {
		return  metier.allCommandesF();
		
	}
	
	@RequestMapping("/nbreCommandesFMC")
	public Long nbreCommandesFMC(String motCle) {
		return metier.getNombreCommandesFMC(motCle);
	}
	
	
	//Commandes fournisseurs traitées
	
	
	@RequestMapping("/RechercheCommandeByIdandNomFT")
	public List<CommandeFournisseur> RechercheCommandeByIdandNomFT(String motCle , int position , int nbre) {
		return metier.getCommandeFByNomT(motCle, position, nbre);
	}
	
	@RequestMapping("/nbreCommandesFT")
	public Long nbreCommandesFT() {
		return metier.getNombreCommandesFT() ;
	}
	
	@RequestMapping(value="/getCommandesFT")
	public List<CommandeFournisseur> getAllCommandesFT(int position , int nbre) {
		return  metier.getAllCommandeFT(position, nbre);
		
	}
	
	@RequestMapping(value="/allCommandesFT")
	public List<CommandeFournisseur> allCommandesFT() {
		return  metier.allCommandesFT();
		
	}
	
	@RequestMapping("/nbreCommandesFMCT")
	public Long nbreCommandesFMCT(String motCle) {
		return metier.getNombreCommandesFMCT(motCle);
	}
	
	
	//Commandes fournisseurs non traitées
	
	
		@RequestMapping("/RechercheCommandeByIdandNomFNT")
		public List<CommandeFournisseur> RechercheCommandeByIdandNomFNT(String motCle , int position , int nbre) {
			return metier.getCommandeFByNomNT(motCle, position, nbre);
		}
		
		@RequestMapping("/nbreCommandesFNT")
		public Long nbreCommandesFNT() {
			return metier.getNombreCommandesFNT() ;
		}
		
		@RequestMapping(value="/getCommandesFNT")
		public List<CommandeFournisseur> getAllCommandesFNT(int position , int nbre) {
			return  metier.getAllCommandeFNT(position, nbre);
			
		}
		
		@RequestMapping(value="/allCommandesFNT")
		public List<CommandeFournisseur> allCommandesFNT() {
			return  metier.allCommandesFNT();
			
		}
		
		@RequestMapping("/nbreCommandesFMCNT")
		public Long nbreCommandesFMCNT(String motCle) {
			return metier.getNombreCommandesFMCNT(motCle);
		}
		
		
		//BONLIVRAISONS FOURNISSEURS
		
		
		@RequestMapping("/RechercheBLByIdandNomF")
		public List<BonLivraison> RechercheBLByIdandNomF(String motCle , int position , int nbre) {
			return metier.getBLFByNom(motCle, position, nbre);
		}
		
		@RequestMapping("/nbreBLF")
		public Long nbreBLF() {
			return metier.getNombreBLF() ;
		}
		
		@RequestMapping(value="/getBLF")
		public List<BonLivraison> getAllBLF(int position , int nbre) {
			return  metier.getAllBLF(position, nbre);
			
		}
		
		@RequestMapping(value="/allBLF")
		public List<BonLivraison> allBLF() {
			return  metier.allBLF();
			
		}
		
		@RequestMapping("/nbreBLFMC")
		public Long nbreBLFMC(String motCle) {
			return metier.getNombreBLFMC(motCle) ;
		}
		
		
		//BONLIVRAISONS FOURNISSEURS TRAITES
		
		
				@RequestMapping("/RechercheBLByIdandNomFT")
				public List<BonLivraison> RechercheBLByIdandNomFT(String motCle , int position , int nbre) {
					return metier.getBLFByNomT(motCle, position, nbre);
				}
				
				@RequestMapping("/nbreBLFT")
				public Long nbreBLFT() {
					return metier.getNombreBLFT() ;
				}
				
				@RequestMapping(value="/getBLFT")
				public List<BonLivraison> getAllBLFT(int position , int nbre) {
					return  metier.getAllBLFT(position, nbre);
					
				}
				
				@RequestMapping(value="/allBLFT")
				public List<BonLivraison> allBLFT() {
					return  metier.allBLFT();
					
				}
				
				@RequestMapping("/nbreBLFMCT")
				public Long nbreBLFMCT(String motCle) {
					return metier.getNombreBLFMCT(motCle) ;
				}
				
				
				
				//BONLIVRAISONS FOURNISSEURS NON TRAITES
				
				
				@RequestMapping("/RechercheBLByIdandNomFNT")
				public List<BonLivraison> RechercheBLByIdandNomFNT(String motCle , int position , int nbre) {
					return metier.getBLFByNomNT(motCle, position, nbre);
				}
				
				@RequestMapping("/nbreBLFNT")
				public Long nbreBLFNT() {
					return metier.getNombreBLFNT() ;
				}
				
				@RequestMapping(value="/getBLFNT")
				public List<BonLivraison> getAllBLFNT(int position , int nbre) {
					return  metier.getAllBLFNT(position, nbre);
					
				}
				
				@RequestMapping(value="/allBLFNT")
				public List<BonLivraison> allBLFNT() {
					return  metier.allBLFNT();
					
				}
				
				@RequestMapping("/nbreBLFMCNT")
				public Long nbreBLFMCNT(String motCle) {
					return metier.getNombreBLFMCNT(motCle) ;
				}
				
				
				
				// Factures fournisseurs
				
				
				@RequestMapping("/RechercheFacByIdandNomF")
				public List<Facture> RechercheFacByIdandNomF(String motCle , int position , int nbre) {
					return metier.getFacturesFByNom(motCle, position, nbre) ;
				}
				
				@RequestMapping("/nbreFacF")
				public Long nbreFacClientF() {
					return metier.getNombreFacturesF();
				}
				
				@RequestMapping(value="/getFacF")
				public List<Facture> getAllFacF(int position , int nbre) {
					return  metier.getAllFacturesF(position, nbre) ;
					
				}
				
				@RequestMapping(value="/allFacF")
				public List<Facture> allFacF() {
					return  metier.allFacturesF() ;
					
				}
				
				@RequestMapping("/nbreFacMCF")
				public Long nbreFacFMC(String motCle) {
					return metier.getNombreFacturesFMC(motCle) ;
				}
				
				
	             // Factures fournisseurs traitées
				
				
				@RequestMapping("/RechercheFacByIdandNomFT")
				public List<Facture> RechercheFacByIdandNomFT(String motCle , int position , int nbre) {
					return metier.getFacturesFByNomT(motCle, position, nbre) ;
				}
				
				@RequestMapping("/nbreFacFT")
				public Long nbreFacClientFT() {
					return metier.getNombreFacturesFT();
				}
				
				@RequestMapping(value="/getFacFT")
				public List<Facture> getAllFacFT(int position , int nbre) {
					return  metier.getAllFacturesFT(position, nbre) ;
					
				}
				
				@RequestMapping(value="/allFacFT")
				public List<Facture> allFacFT() {
					return  metier.allFacturesFT() ;
					
				}
				
				@RequestMapping("/nbreFacMCFT")
				public Long nbreFacFMCT(String motCle) {
					return metier.getNombreFacturesFMCT(motCle) ;
				}
				
				

	             // Factures fournisseurs non traitées
				
				
				@RequestMapping("/RechercheFacByIdandNomFNT")
				public List<Facture> RechercheFacByIdandNomFNT(String motCle , int position , int nbre) {
					return metier.getFacturesFByNomNT(motCle, position, nbre) ;
				}
				
				@RequestMapping("/nbreFacFNT")
				public Long nbreFacClientFNT() {
					return metier.getNombreFacturesFNT();
				}
				
				@RequestMapping(value="/getFacFNT")
				public List<Facture> getAllFacFNT(int position , int nbre) {
					return  metier.getAllFacturesFNT(position, nbre) ;
					
				}
				
				@RequestMapping(value="/allFacFNT")
				public List<Facture> allFacFNT() {
					return  metier.allFacturesFNT() ;
					
				}
				
				@RequestMapping("/nbreFacMCFNT")
				public Long nbreFacFMCNT(String motCle) {
					return metier.getNombreFacturesFMCNT(motCle) ;
				}
				
				
				
				// Avoirs fournisseurs
				
				
				@RequestMapping("/RechercheAvoirByIdandNomF")
				public List<Avoir> RechercheAvoirByIdandNomF(String motCle , int position , int nbre) {
					return metier.getAvoirByNomF(motCle, position, nbre);
				}
				
				@RequestMapping("/nbreAvoirFournisseur")
				public Long nbreAvoirFournisseur() {
					return metier.getNombreAvoirF() ;
				}
				
				@RequestMapping(value="/getAvoirFournisseur")
				public List<Avoir> getAllAvoirFournisseur(int position , int nbre) {
					return  metier.getAvoirFournisseur(position, nbre) ;
					
				}
				
				@RequestMapping("/nbreAvoirClientMF")
				public Long nbreAvoirClientMF(String motCle) {
					return metier.getNombreAvoirMCF(motCle) ;
				}
				
				@RequestMapping(value="/allAvoirsF")
				public List<Avoir> allAvoirsF() {
					return  metier.allAvoirsF();
					
				}
				
				
				//Fournisseurs
				
				@RequestMapping("/RechercheClientByIdandNomF")
				public List<Fournisseur> RechercheClientByIdandNomF(String motCle , int position , int nbre) {
					return metier.getFournByNom(motCle, position, nbre) ;
				}
				
				@RequestMapping("/nbreF")
				public Long nbreClientF() {
					return metier.getNombreFourn() ;
				}
				
				@RequestMapping(value="/getF")
				public List<Fournisseur> getAllF(int position , int nbre) {
					return  metier.getAllFournisseurs(position, nbre) ;
					
				}
				
				@RequestMapping(value="/allFourn")
				public List<Fournisseur> allFourn() {
					return  metier.allFourn();
					
				}
				
				@RequestMapping("/nbreFMC")
				public Long nbreFMC(String motCle) {
					return metier.getFournMC(motCle) ;
				}
				
				@RequestMapping("/deleteF")
				public void deleteF(Long idF) {
					 metier.deleteFour(idF);
				}
				
				@RequestMapping("/updateCommandeCl")
				public void updateCommandeCl(Long id , String date) {
					 metier.updateCommande(id, date);
				}
				
				@RequestMapping("/updateCommandeF")
				public void updateCommandeF(Long id , String date) {
					 metier.updateCommandeF(id, date);
				}
				
				@RequestMapping("/updateBl")
				public void updateBl(Long id , String date) {
					 metier.updateBl(id, date);
				}
				

				@RequestMapping("/updateF")
				public void updateF(Long id , String date) {
					 metier.updateFacture(id, date);;
				}
				
				@RequestMapping("/updateAv")
				public void updateAv(Long id , String date) {
					 metier.updateAv(id, date);
				}
				
				@RequestMapping("/updateCl")
				public void updateCl(Client c) {
					 metier.updateCl(c);
				}
				
				@RequestMapping("/updateFournisseur")
				public void updateFournisseur(Fournisseur f) {
					 metier.updateFournisseur(f);
				}
				
				@RequestMapping("/updateArticle")
				public void updateArticle(Article a) {
					 metier.updateArticle(a);
				}
				
				@RequestMapping("/deleteArticle")
				public void deleteArticle(Long id) {
					 metier.deleteArticle(id);
				}
				
				@RequestMapping("/pdfCommande")
				public String  pdfCommande(Long idComCl) {
					 return metier.pdfCommande(idComCl);
				}
				
				@RequestMapping("/pdfLivraison")
				public String pdfLivraison(Long idBl) {
					 return metier.pdfLivraison(idBl);
				}
				
				@RequestMapping("/pdfFacture")
				public String  pdfFacture(Long idFac) {
					return  metier.pdfFacture(idFac);
				}
				
				@RequestMapping("/getCommandeById")
				public CommandeClient  getCommandeById(Long idComCl) {
					return  metier.getCommandeById(idComCl);
				}
				
				@RequestMapping("/getLivraisonById")
				public BonLivraison  getLivraisonById(Long idBl) {
					return  metier.getLivraisonById(idBl);
				}
				
				@RequestMapping("/getFactureById")
				public Facture  getFactureById(Long idFac) {
					return  metier.getFactureById(idFac);
				}
				
				@RequestMapping("/lesArticles")
				public List<ArticleListe>  lesArticles(Long idCl) {
					return  metier.lesArticles(idCl);
				}
				
				@RequestMapping("/getRepresentantsClient")
				public List<Representant>  representants(Long idCl) {
					return  metier.getRepresentantsByClient(idCl);
				}
				
				@RequestMapping("/getRepresentantsF")
				public List<Representant>  representantsF(Long idF) {
					return  metier.getRepresentantsByFourn(idF);
				}
				
				@RequestMapping("/articlesComCl")
				public List<ArticleListe>  articlesComCl() {
					return  metier.articlesComCl() ;
				}
				
				@RequestMapping("/getArticleListeByDesignation")
				public Article getArticleListeByDesignation(String designation) {
					return metier.getArticleListeByNom(designation);
				}
				
				@RequestMapping("/getClientById")
				public Client getclientbyid(Long idCl) {
					return  metier.getClientById(idCl) ;
				}
				
				@RequestMapping("/getFacturesTByClient")
				public List<Facture> getFacturesTByClient(Long idCl) {
					return  metier.getFacturesTClient(idCl);
				}
				
				@RequestMapping("/getFacturesNTByClient")
				public List<Facture> getFacturesNTByClient(Long idCl) {
					return  metier.getFacturesNTClient(idCl);
				}
				
				@RequestMapping("/getAvoirsClient")
				public List<Avoir> getAvoirsClient(Long idCl) {
					return  metier.getAvoirsClient(idCl);
				}
				
				@RequestMapping("/releveCompte")
				public void releveCompte(Long idCl , String date) {
					  metier.releveCompte(idCl , date);
				}
				
				@RequestMapping("/listeInt")
				public void listeInt(Long[] data) {
					  metier.listeInt(data);
				}
				
				
				@RequestMapping("/userOnline")
				    public UserDetails getLoggedUser() {
				        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				    }
				
				@RequestMapping("/auth")
			    public int monUser(Authentication authentication , String login , String pass) {
					String login2 = authentication.getName();
					String pass2 = authentication.getCredentials().toString();
					if((login2.equals(login)) &&  (pass2.equals(pass)) ) {
						return 1 ;
					}
					else {
						return 0 ;
					}
				
			    }
				
				
				@RequestMapping("/auth2")
			    public User monUser3(Authentication authentication ) {
					
					
					User u = new User() ;
					String name = authentication.getName();
					String password = authentication.getCredentials().toString();
					u.setUsername(name);
					u.setPassword(password);
			        return u;
			    }
				
				@RequestMapping("/auto")
			    public int auto(Authentication authentication) {
					
				
                       return 1 ;

			    }
				
				@RequestMapping("/deconn")
				public void logoutDo(HttpServletRequest request,HttpServletResponse response){
					HttpSession session= request.getSession(false);
					    SecurityContextHolder.clearContext();
					         session= request.getSession(false);
					        if(session != null) {
					            session.invalidate();
					        }
					        for(Cookie cookie : request.getCookies()) {
					            cookie.setMaxAge(0);
					        }

					  
					}
				
				
				
				
				
				
			
				
				/*@RequestMapping("/auth")
			    public User authenticate(Authentication authentication) {
					User u = new User() ;
					String name = authentication.getName();
					String password = authentication.getCredentials().toString();
					u.setUsername(name);
					u.setPassword(password);
			        return u;
			    }*/
				
				/* @RequestMapping(method = RequestMethod.GET)   
				 public User showResults(final HttpServletRequest request, Principal principal) {
                     User u = new User() ;
                    
					return null;

				 }
				*/
				
				
				@RequestMapping("/serialNumber")
			    public String serialNumber(String drive) {
					
					drive ="C" ;

                       return metier.getSerialNumber(drive) ;

			    }
				
				@RequestMapping("/serialNumber2")
			    public String serialNumber2() {

                       return metier.getSerialNumber2() ;

			    }
				
				@RequestMapping("/addNumDisque")
				public NumeroDisque addNumDisque(NumeroDisque n) {
					
					return metier.addNumDisque(n);
				}
				
				@RequestMapping("/verifSerie")
				public String verifSerie(String num) {
					return metier.verifSerie(num);
				}
				
				
				
				

}
