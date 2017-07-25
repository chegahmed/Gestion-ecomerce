package com.entreprise.metier;

import java.sql.Connection;

import java.util.Date;
import java.util.List;

import com.entreprise.entities.Article;
import com.entreprise.entities.ArticleListe;
import com.entreprise.entities.AutrePaiement;
import com.entreprise.entities.Avoir;
import com.entreprise.entities.CommandeClient;
import com.entreprise.entities.BonLivraison;
import com.entreprise.entities.Categorie;
import com.entreprise.entities.Client;
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

public interface IComMetier {
	public Client addClient(Client c) ;
	public Client addClientRepresentant(Client c , Representant r) ;
	public void deleteClient(Long id) ;
	public void updateClient(Long id) ;
	public List<Client> getAllClients(int position , int nbre) ;
	public Client getClientById(Long id) ;
	public Client getClientByNom(String nom) ;
	
	public Article addArticle(Article a , Long idCat) ;
	public void deleteArticle(Long id) ;
	public void updateArticle(Long id) ;
	public List<Article> getAllArticles() ;
	public List<Article> getArticlesByCategories(Long idCat) ;
	public Article getArticleById(Long id) ;
	public Article getArticleByNom(String nom) ;
	
	public Avoir addAvoirClient(Avoir a ,List<ArticleListe> l , Long idCl) ;
	public Avoir addAvoirFourn(Avoir a ,List<ArticleListe> l , Long idFourn) ;
	public void deleteAvoir(Long id) ;
	public void updateAvoir(Long id) ;
	public List<Avoir> getAllAvoirs() ;
	public List<Avoir> getAvoirsByClient(Long idCl) ;
	
	public CommandeClient addBCommande(CommandeClient b ,List<ArticleListe> l , Long idCl) ;
	public void deleteBCommande(Long id) ;
	public void updateBCommande(Long id) ;
	public List<CommandeClient> getAllBonCommandes(int position , int nbre) ;
	public List<CommandeClient> getCommandesByClient(Long idCl) ;
	public List<CommandeClient> getCommandesByDate(String date) ;
	
	
	public BonLivraison addBLivrFourn(BonLivraison b ,List<ArticleListe> l , Long idFour) ;
	public BonLivraison addBLivrClient(BonLivraison b ,List<ArticleListe> l , Long idCl) ;
	public BonLivraison addBLivrCommandes(BonLivraison b ,List<CommandeClient> lc , Long idCl) ;
	public void deleteBLivr(Long id) ;
	public void updateBLivr(Long id) ;
	public List<BonLivraison> getAllBonLivraisons() ;
	public List<BonLivraison> getLivraisonByFourn(Long idFourn) ;
	public List<BonLivraison> getLivraisonByDate(String date) ;
	
	
	public Categorie addCat(Categorie c) ;
	public void deleteCat(Long id) ;
	public void updateCat(Long id) ;
	public List<Categorie> getAllCategories() ;
	
	public CommandeFournisseur addComFour(CommandeFournisseur c ,List<ArticleListe> l , Long idFour) ;
	public void deleteComFour(Long id) ;
	public void updateComFour(Long id) ;
	public List<CommandeFournisseur> getAllComFournisseurs(int position , int nbre) ;
	public List<CommandeFournisseur> getComFourByFourn(Long idFourn) ;
	
	
	public ModePaiement addModeP(ModePaiement m) ;
	public void addModePautreCl(AutrePaiement m , Long idCl) ;
	public void addModePchequeCl(PaiementCheque m , Long idCl) ;
	public void addModePbanqueCl(CompteBancaire m , Long idCl) ;
	public void deleteModeP(Long idMod , Long idCl) ;
	public void updateModeP(Long id) ;
	public List<ModePaiement> getAllModePaies(int position , int nbre) ;
	public List<ModePaiement> getModePaiesByClient(Long idCl) ;
	
	public Facture addFacture(Facture f ,List<ArticleListe> l , Long idCl ) ;
	public Facture addFactureLivs(Facture f , List<BonLivraison> b , Long idCl) ;
	public Facture addFactureCommandes(Facture f , List<CommandeClient> lc , Long idCl) ;
	public void deleteFacture(Long id) ;
	public void updateFacture(Long id) ;
	public List<Facture> getAllFactures(int position , int nbre) ;
	public List<Facture> getFacturesByClient(Long idCl) ;
	
	public Fournisseur addFour(Fournisseur f) ;
	public void deleteFour(Long id) ;
	public void updateFour(Long id) ;
	public List<Fournisseur> getAllFournisseurs() ;
	public Fournisseur getFournById(Long id) ;
	public Fournisseur getFournByNom(String nom) ;
	
	
	public User addUser(User u , String role) ;
	public void deleteUser(Long idUser) ;
	public void updateUser(Long id) ;
	public List<User> getAllUsers() ;
	public User getUserById(Long id) ;
	public User getUserByNom(String nom) ;
	
	public Facture reglementFacture(Long idFacture , Long idMode , double montantFac , double totaldu);
	
	public List<BonLivraison> getBonLivraisonsParDate() ;
	public List<CommandeClient> getCommandeClientParDate() ;
	public List<CommandeFournisseur> getCommandeFourParDate();
	public List<Facture> getFacturesParDate() ;
	
	public List<Avoir> getAvoirParNom() ;
	public List<BonLivraison> getBonLivraisonsParNom() ;
	public List<CommandeClient> getCommandeClientParNom() ;
	public List<CommandeFournisseur> getCommandeFourParNom();
	public List<Facture> getFacturesParNom() ;
	
	public List<Avoir> getAvoirParNum(String num) ;
	public List<BonLivraison> getBonLivraisonsParNum(String num) ;
	public List<CommandeClient> getCommandeClientParNum(String num) ;
	public List<CommandeFournisseur> getCommandeFourParNum(String num);
	public List<Facture> getFacturesParNum(String num) ;
	
	public List<Avoir> getAvoirParEntreDate(Date d1 , Date d2) ;
	public List<BonLivraison> getBonLivraisonsEntreDate(Date d1 , Date d2) ;
	public List<CommandeClient> getCommandeClientEntreDate(Date d1 , Date d2) ;
	public List<CommandeFournisseur> getCommandeFourEntreDate(Date d1 , Date d2);
	public List<Facture> getFacturesEntreDate(Date d1 , Date d2) ;
	
	public List<Representant> getAllRepresentantsClient() ;
	public List<Representant> getAllRepresentantsFourn() ;
	public void addRepresentantToClient(Representant r , Long idCl) ;
	public void addRepresentantToFourn(Representant r , Long idFourn) ;
	public boolean deleteRepresentant(Long idRep) ;
	public List<Representant> getRepresentantsByClient(Long idCl) ;
	public List<Representant> getRepresentantsByFourn(Long idFourn) ;
	
	public CommandeClient getLastCommandeClient() ;
	public CommandeClient addCommandeClientSimple(CommandeClient c , double prixTotalCommande ,  Long idCl) ;
	public CommandeClient addCommandeClientSimpleD(CommandeClient c , double prixTotalCommande ,  Long idCl) ;

	public ArticleListe addArticleToBonCommande(ArticleListe a , Long idArt) ;
	public List<ArticleListe> getArticlesForCommande(Long idCommande) ;
	public List<BonLivraison> getBonLivraisonClient(int position , int nbre) ;
	public List<BonLivraison> getBonLivraisonFourn() ;
	
	public void addBonLivraisonClient( BonLivraison b , Long idComCl) ;
	public void addBonLivraisonFournisseur(BonLivraison b ,Long idCom) ;
	public List<ArticleListe> getArticlesForLivraisonClient(Long idBl) ;
	
	public void addFactureAccompteClientM(Facture f ,Long[] data) ;
	public void addFactureAccompteClient(Facture f ,Long idBl) ;
	public void addFactureDirecteE(Facture f , String nomMode) ;
	public void addFactureDirecteC(Facture f , String nomMode , String numCheque , String nomBanque) ;
	public void addFactureDirecteB(Facture f , String nomMode , String numCompte , String nomBanque) ;
	public List<ArticleListe> getArticlesForFacture(Long idFacture) ;
	
	public List<Facture> getFactureClient() ;
	public List<Facture> getFactureDirecte() ;
	public List<Facture> getFactureAccompte() ;
	public List<ModePaiement> getModePaiementByClient(Long idCl) ;
	
	public double updatePrixCommande() ;
	
	public Avoir getLastAvoirClient() ;
	public Avoir addAvoirClient(Avoir a , Long idCl, double prixTotalAvoir) ;
	public ArticleListe addArticleToAvoir(ArticleListe a ,Long idArt) ;
	public List<ArticleListe> getArticlesForAvoir(Long idAvoir) ;
	
	
	public CommandeFournisseur addCommandeFournisseur(CommandeFournisseur c , Long idFourn ,  double prixTotalCommande) ;
	public ArticleListe addArticleToBonCommandeFourn(ArticleListe a ,Long idArt) ;
	public Fournisseur addFournisseur(Fournisseur f) ;
	public CommandeFournisseur getLastCommandeFour() ;
	
	
	public Avoir getLastAvoirFournisseur() ;
	public Avoir addAvoirFournisseur(Avoir a , Long idFourn , double prixTotalAvoir) ;
	public ArticleListe addArticleToAvoirFourn(ArticleListe a ,Long idArt) ;
	public List<ArticleListe> getArticlesForAvoirFourn(Long idAvoir) ;
	
	public Fournisseur addFournisseurRepresentant(Fournisseur f, Representant r) ;
	public List<ArticleListe> getArticlesForCommandeFour(Long idComF) ;
	public List<ArticleListe> getArticlesForBonLivs(Long idBl) ;
	
	public List<CommandeClient> getCommandeByNom(String motCle , int position , int nbre) ;
	public long getNombreCommandes() ;
	public List<CommandeClient> allCommandesClient() ;
	public long getNombreCommandesMC(String motCle) ;
	
	public List<BonLivraison> getBLClientByNom(String motCle , int position , int nbre) ;
	public long getNombreBLClient() ;
	public List<BonLivraison> allBLClient() ;
	public long getNombreBLMC(String motCle) ;
	
	
	public List<Facture> getFacClientByNom(String motCle , int position , int nbre) ;
	public long getNombreFacClient() ;
	public List<Facture> allFacClient() ;
	public long getNombreFacMC(String motCle) ;
	
	public List<CommandeFournisseur> getCommandeFByNom(String motCle , int position , int nbre) ;
	public long getNombreCommandesF() ;
	public List<CommandeFournisseur> allCommandesF() ;
	public long getNombreCommandesFMC(String motCle) ;
	
	
	public List<Avoir> getAllAvoirClient(int position , int nbre) ;
	public List<Avoir> getAvoirByNom(String motCle , int position , int nbre) ;
	public long getNombreAvoir() ;
	public List<Avoir> allAvoirClient() ;
	public long getNombreAvoirMC(String motCle) ;
	
	public List<Client> getClientByNom(String motCle , int position , int nbre) ;
	public long getNombreClient() ;
	public List<Client> allClient() ;
	public long getClientMC(String motCle) ;
	
	public List<ModePaiement> getMPByNom(String motCle , int position , int nbre) ;
	public long getNombreMP() ;
	public List<ModePaiement> allMPClient() ;
	public long getNombreMPMC(String motCle) ;
	
	
	
	public List<Article> getArticles(int position , int nbre) ;
	public List<Article> getArticleByNom(String motCle , int position , int nbre) ;
	public long getNombreArticle() ;
	public List<Article> allArticleClient() ;
	public long getNombreArticleMC(String motCle) ;
	
	
	public List<Categorie> getCategories(int position , int nbre) ;
	public List<Categorie> getCategorieByNom(String motCle , int position , int nbre) ;
	public long getNombreCategorie() ;
	public List<Categorie> allCategorie() ;
	public long getNombreCategorieMC(String motCle) ;
	
	public Representant getLastRepresentantClient(Long idCl) ;
	public Representant getLastRepresentantF(Long idF) ;
	
	public List<Avoir> getAvoirFournisseur(int position , int nbre) ;
	public List<Avoir> getAvoirByNomF(String motCle , int position , int nbre) ;
	public long getNombreAvoirF() ;
	public long getNombreAvoirMCF(String motCle) ;
	
	public List<ArticleListe> statArticleClient(Long idArt) ;
	
	public List<Long> longs(Long idArt) ;
	
	public List<StatArticle> statMois() ;
	public StatArticle statRecherche(Long idArt) ;
	
	public List<CommandeClient> getAllBonCommandesTraitees(int position , int nbre) ;
	public List<CommandeClient> getCommandeByNomTraitees(String motCle , int position , int nbre) ;
	public long getNombreCommandesTraitees() ;
	public List<CommandeClient> allCommandesClientTraitees() ;
	public long getNombreCommandesMCTraitees(String motCle) ;
	
	public List<CommandeClient> getAllBonCommandesNTraitees(int position , int nbre) ;
	public List<CommandeClient> getCommandeByNomNTraitees(String motCle , int position , int nbre) ;
	public long getNombreCommandesNTraitees() ;
	public List<CommandeClient> allCommandesClientNTraitees() ;
	public long getNombreCommandesMCNTraitees(String motCle) ;
	
	public List<BonLivraison> getBLClientByNomTraitees(String motCle , int position , int nbre) ;
	public long getNombreBLClientTraitees() ;
	public List<BonLivraison> allBLClientTraitees() ;
	public long getNombreBLMCTraitees(String motCle) ;
	public List<BonLivraison> getAllBLTClient(int position , int nbre) ;
	
	public List<BonLivraison> getBLClientByNomNTraitees(String motCle , int position , int nbre) ;
	public long getNombreBLClientNTraitees() ;
	public List<BonLivraison> allBLClientNTraitees() ;
	public long getNombreBLMCNTraitees(String motCle) ;
	public List<BonLivraison> getAllBLNTClient(int position , int nbre) ;
	
	public List<Facture> getAllFacturesTraitees(int position , int nbre) ;
	public List<Facture> getFacClientByNomTraitees(String motCle , int position , int nbre) ;
	public long getNombreFacClientTraitees() ;
	public List<Facture> allFacClientTraitees() ;
	public long getNombreFacMCTraitees(String motCle) ;
	
	public List<Facture> getAllFacturesNTraitees(int position , int nbre) ;
	public List<Facture> getFacClientByNomNTraitees(String motCle , int position , int nbre) ;
	public long getNombreFacClientNTraitees() ;
	public List<Facture> allFacClientNTraitees() ;
	public long getNombreFacMCNTraitees(String motCle) ;
	
	
	
	public List<CommandeFournisseur> getAllCommandeF( int position , int nbre) ;
	public List<CommandeFournisseur> getAllCommandeFT( int position , int nbre) ;
	public List<CommandeFournisseur> getCommandeFByNomT(String motCle , int position , int nbre) ;
	public long getNombreCommandesFT() ;
	public List<CommandeFournisseur> allCommandesFT() ;
	public long getNombreCommandesFMCT(String motCle) ;
	
	public List<CommandeFournisseur> getAllCommandeFNT( int position , int nbre) ;
	public List<CommandeFournisseur> getCommandeFByNomNT(String motCle , int position , int nbre) ;
	public long getNombreCommandesFNT() ;
	public List<CommandeFournisseur> allCommandesFNT() ;
	public long getNombreCommandesFMCNT(String motCle) ;
	
	
	
	public List<BonLivraison> getAllBLF( int position , int nbre) ;
	public List<BonLivraison> getBLFByNom(String motCle , int position , int nbre) ;
	public long getNombreBLF() ;
	public List<BonLivraison> allBLF() ;
	public long getNombreBLFMC(String motCle) ;
	
	public List<BonLivraison> getAllBLFT( int position , int nbre) ;
	public List<BonLivraison> getBLFByNomT(String motCle , int position , int nbre) ;
	public long getNombreBLFT() ;
	public List<BonLivraison> allBLFT() ;
	public long getNombreBLFMCT(String motCle) ;
	
	public List<BonLivraison> getAllBLFNT( int position , int nbre) ;
	public List<BonLivraison> getBLFByNomNT(String motCle , int position , int nbre) ;
	public long getNombreBLFNT() ;
	public List<BonLivraison> allBLFNT() ;
	public long getNombreBLFMCNT(String motCle) ;
	
	
	
	public List<Facture> getAllFacturesF( int position , int nbre) ;
	public List<Facture> getFacturesFByNom(String motCle , int position , int nbre) ;
	public long getNombreFacturesF() ;
	public List<Facture> allFacturesF() ;
	public long getNombreFacturesFMC(String motCle) ;
	
	public List<Facture> getAllFacturesFT( int position , int nbre) ;
	public List<Facture> getFacturesFByNomT(String motCle , int position , int nbre) ;
	public long getNombreFacturesFT() ;
	public List<Facture> allFacturesFT() ;
	public long getNombreFacturesFMCT(String motCle) ;
	
	public List<Facture> getAllFacturesFNT( int position , int nbre) ;
	public List<Facture> getFacturesFByNomNT(String motCle , int position , int nbre) ;
	public long getNombreFacturesFNT() ;
	public List<Facture> allFacturesFNT() ;
	public long getNombreFacturesFMCNT(String motCle) ;
	
	public List<Avoir> getAllAvoirsF( int position , int nbre) ;
	public List<Avoir> getAvoirsFByNom(String motCle , int position , int nbre) ;
	public long getNombreAvoirsF() ;
	public List<Avoir> allAvoirsF() ;
	public long getNombreAvoirsFMC(String motCle) ;
	
	public List<Fournisseur> getAllFournisseurs (int position , int nbre) ;
	public List<Fournisseur> getFournByNom(String motCle , int position , int nbre) ;
	public long getNombreFourn() ;
	public List<Fournisseur> allFourn() ;
	public long getFournMC(String motCle) ;
	
	
	public void updateCommande(Long id , String date) ;
	public void updateCommandeF(Long id , String date) ;
	public void updateBl(Long id , String date) ;
	public void updateFacture(Long id , String date) ;
	
	public void addFactureAccompteF(Facture f ,Long idBl) ;
	public void deleteFactureF(Long id) ;
	
	public void deleteBLF(Long id) ;
	
	public List<ModePaiement> getModePaiesByF(Long idF) ;
	
	public void addModePautreF(AutrePaiement m , Fournisseur f) ;
	public void deleteAvoirF(Long id) ;
	public void updateAv(Long id , String date) ;
	public void updateCl(Client c) ;
	public void updateFournisseur(Fournisseur f) ;
	public void updateArticle(Article a) ;
	
	public String pdfCommande(Long idComCl) ;
	public String pdfLivraison(Long idBl) ;
	public String pdfFacture(Long idFac) ;
	
	public Connection connection() ;
	
	public CommandeClient getCommandeById(Long id) ;
	
	public BonLivraison getLivraisonById(Long id) ;
	public Facture getFactureById(Long id) ;
	public ArticleListe getLastArticleListe(Long idArt ,Long idCl) ;
	
	public List<ArticleListe> lesArticles(Long idCl) ;
	
	public void addArticleListe(ArticleListe a) ;
	
	public List<ArticleListe> articlesComCl() ;
	public Article getArticleListeByNom(String nom) ;
	
	public List<Facture> getFacturesTClient(Long idCl) ;
	public List<Facture> getFacturesNTClient(Long idCl) ;
	public List<Avoir> getAvoirsClient(Long idCl) ;
	
	public void releveCompte(Long idCl , String date) ;
	
	public void listeInt(Long[] data) ;
	
	public String getSerialNumber(String drive) ;
	
	public String getSerialNumber2() ;
	
	public NumeroDisque addNumDisque(NumeroDisque n) ;
	
	public String verifSerie(String num) ;
	
}
