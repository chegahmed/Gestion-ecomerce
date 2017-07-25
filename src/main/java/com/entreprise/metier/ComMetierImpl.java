package com.entreprise.metier;

import java.sql.Connection;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.entreprise.dao.IComDao;
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
@Transactional
public class ComMetierImpl implements IComMetier {

	private IComDao dao ;

	public void setDao(IComDao dao) {
		this.dao = dao;
	}

	@Override
	public Client addClient(Client c) {
		return dao.addClient(c);
		
	}

	@Override
	public void deleteClient(Long id) {
		dao.deleteClient(id);
	}

	@Override
	public void updateClient(Long id) {
		dao.updateClient(id);
	}


	@Override
	public Client getClientById(Long id) {
		return dao.getClientById(id);
	}

	@Override
	public Client getClientByNom(String nom) {
		return dao.getClientByNom(nom);
	}

	@Override
	public Article addArticle(Article a, Long idCat) {
		return dao.addArticle(a, idCat);
	}

	@Override
	public void deleteArticle(Long id) {
		dao.deleteArticle(id);
	}

	@Override
	public void updateArticle(Long id) {
		dao.updateArticle(id);
	}

	@Override
	public List<Article> getAllArticles() {
		return dao.getAllArticles();
	}

	@Override
	public List<Article> getArticlesByCategories(Long idCat) {
		return dao.getArticlesByCategories(idCat);
	}

	@Override
	public Article getArticleById(Long id) {
		return dao.getArticleById(id);
	}

	@Override
	public Article getArticleByNom(String nom) {
		return dao.getArticleByNom(nom);
	}

	@Override
	public Avoir addAvoirClient(Avoir a, List<ArticleListe> l ,Long idCl) {
		return dao.addAvoirClient(a,l, idCl);
	}
	
	@Override
	public Avoir addAvoirFourn(Avoir a, List<ArticleListe> l, Long idFourn) {
		// TODO Auto-generated method stub
		return dao.addAvoirFourn(a,l, idFourn);
	}

	@Override
	public void deleteAvoir(Long id) {
	dao.deleteAvoir(id);
	}

	@Override
	public void updateAvoir(Long id) {
		dao.updateAvoir(id); 
	}

	@Override
	public List<Avoir> getAllAvoirs() {
		return dao.getAllAvoirs();
	}

	@Override
	public List<Avoir> getAvoirsByClient(Long idCl) {
		return dao.getAvoirsByClient(idCl);
	}

	@Override
	public CommandeClient addBCommande(CommandeClient b,List<ArticleListe> l , Long idCl) {
		return dao.addBCommande(b,l,idCl);
	}

	@Override
	public void deleteBCommande(Long id) {
		 dao.deleteBCommande(id);
	}

	@Override
	public void updateBCommande(Long id) {
		 dao.updateBCommande(id);
	}

	@Override
	public List<CommandeClient> getAllBonCommandes(int position , int nbre) {
		return dao.getAllBonCommandes(position , nbre);
	}

	@Override
	public List<CommandeClient> getCommandesByClient(Long idCl) {
		return dao.getCommandesByClient(idCl);
	}

	@Override
	public List<CommandeClient> getCommandesByDate(String date) {
		return dao.getCommandesByDate(date);
	}

	@Override
	public BonLivraison addBLivrFourn(BonLivraison b,List<ArticleListe> l , Long idFour) {
		 return dao.addBLivrFourn(b,l, idFour);
	}

	@Override
	public void deleteBLivr(Long id) {
		 dao.deleteBLivr(id);
	}

	@Override
	public void updateBLivr(Long id) {
		dao.updateBLivr(id);
	}

	@Override
	public List<BonLivraison> getAllBonLivraisons() {
		return dao.getAllBonLivraisons();
	}

	@Override
	public List<BonLivraison> getLivraisonByFourn(Long idFourn) {
		return dao.getLivraisonByFourn(idFourn);
	}

	@Override
	public List<BonLivraison> getLivraisonByDate(String date) {
		return dao.getLivraisonByDate(date);
	}

	@Override
	public Categorie addCat(Categorie c) {
		return dao.addCat(c);
	}

	@Override
	public void deleteCat(Long id) {
		dao.deleteCat(id);
	}

	@Override
	public void updateCat(Long id) {
		dao.updateCat(id);
	}

	@Override
	public List<Categorie> getAllCategories() {
		return dao.getAllCategories();
	}

	@Override
	public CommandeFournisseur addComFour(CommandeFournisseur c,List<ArticleListe> l , Long idFour) {
		return dao.addComFour(c,l, idFour);
	}

	@Override
	public void deleteComFour(Long id) {
		dao.deleteComFour(id);
	}

	@Override
	public void updateComFour(Long id) {
		dao.updateComFour(id);
	}

	@Override
	public List<CommandeFournisseur> getAllComFournisseurs(int position, int nbre) {
		return dao.getAllComFournisseurs(position,nbre);
	}

	@Override
	public List<CommandeFournisseur> getComFourByFourn(Long idFourn) {
		return dao.getComFourByFourn(idFourn);
	}

	@Override
	public ModePaiement addModeP(ModePaiement m) {
		return dao.addModeP(m);
	}



	@Override
	public void deleteModeP(Long idMod, Long idCl) {
		dao.deleteModeP(idMod, idCl);
	}

	@Override
	public void updateModeP(Long id) {
		dao.updateModeP(id);
	}

	@Override
	public List<ModePaiement> getAllModePaies(int position , int nbre) {
		return dao.getAllModePaies(position ,nbre);
	}

	@Override
	public List<ModePaiement> getModePaiesByClient(Long idCl) {
		return dao.getModePaiesByClient(idCl);
	}

	@Override
	public Facture addFacture(Facture f,List<ArticleListe> l , Long idCl) {
		 return dao.addFacture(f,l,idCl);
	}

	@Override
	public void deleteFacture(Long id) {
		 dao.deleteFacture(id);
	}

	@Override
	public void updateFacture(Long id) {
		dao.updateFacture(id);
	}

	@Override
	public List<Facture> getAllFactures(int position , int nbre) {
		return dao.getAllFactures(position , nbre);
	}

	@Override
	public List<Facture> getFacturesByClient(Long idCl) {
		return dao.getFacturesByClient(idCl);
	}

	@Override
	public Fournisseur addFour(Fournisseur f) {
		return dao.addFour(f);
	}

	@Override
	public void deleteFour(Long id) {
		 dao.deleteFour(id);
	}

	@Override
	public void updateFour(Long id) {
		dao.updateFour(id);
	}

	@Override
	public List<Fournisseur> getAllFournisseurs() {
		return dao.getAllFournisseurs();
	}

	@Override
	public Fournisseur getFournById(Long id) {
		return dao.getFournById(id);
	}

	@Override
	public Fournisseur getFournByNom(String nom) {
		return dao.getFournByNom(nom);
	}

	@Override
	public User addUser(User u , String role) {
		return dao.addUser(u,role);
	}

	@Override
	public void deleteUser(Long idUser) {
		dao.deleteUser(idUser);
	}

	@Override
	public void updateUser(Long id) {
		dao.updateUser(id);
	}

	@Override
	public List<User> getAllUsers() {
		return dao.getAllUsers();
	}

	@Override
	public User getUserById(Long id) {
		return dao.getUserById(id);
	}

	@Override
	public User getUserByNom(String nom) {
		return dao.getUserByNom(nom);
	}

	@Override
	public Facture addFactureLivs(Facture f, List<BonLivraison> b, Long idCl) {
		return dao.addFactureLivs(f,b, idCl);
	}

	@Override
	public BonLivraison addBLivrClient(BonLivraison b, List<ArticleListe> l, Long idCl) {
		// TODO Auto-generated method stub
		return dao.addBLivrClient(b,l, idCl);
	}

	@Override
	public Facture reglementFacture(Long idFacture , Long idMode , double montantFac , double totaldu) {
		return dao.reglementFacture(idFacture,idMode,montantFac,totaldu);
	}

	@Override
	public Facture addFactureCommandes(Facture f, List<CommandeClient> lc, Long idCl) {
		return dao.addFactureCommandes(f, lc, idCl);
	}

	@Override
	public BonLivraison addBLivrCommandes(BonLivraison b, List<CommandeClient> lc, Long idCl) {
		return dao.addBLivrCommandes(b, lc, idCl);
	}

	@Override
	public List<BonLivraison> getBonLivraisonsParDate() {
		// TODO Auto-generated method stub
		return dao.getBonLivraisonsParDate();
	}

	@Override
	public List<CommandeClient> getCommandeClientParDate() {
		// TODO Auto-generated method stub
		return dao.getCommandeClientParDate();
	}

	@Override
	public List<CommandeFournisseur> getCommandeFourParDate() {
		// TODO Auto-generated method stub
		return dao.getCommandeFourParDate();
	}

	@Override
	public List<Facture> getFacturesParDate() {
		// TODO Auto-generated method stub
		return dao.getFacturesParDate();
	}

	@Override
	public List<Avoir> getAvoirParNom() {
		// TODO Auto-generated method stub
		return dao.getAvoirParNom();
	}

	@Override
	public List<BonLivraison> getBonLivraisonsParNom() {
		// TODO Auto-generated method stub
		return dao.getBonLivraisonsParNom();
	}

	@Override
	public List<CommandeClient> getCommandeClientParNom() {
		// TODO Auto-generated method stub
		return dao.getCommandeClientParNom();
	}

	@Override
	public List<CommandeFournisseur> getCommandeFourParNom() {
		// TODO Auto-generated method stub
		return dao.getCommandeFourParNom();
	}

	@Override
	public List<Facture> getFacturesParNom() {
		// TODO Auto-generated method stub
		return dao.getFacturesParNom();
	}

	@Override
	public List<Avoir> getAvoirParNum(String num) {
		// TODO Auto-generated method stub
		return dao.getAvoirParNum(num);
	}

	@Override
	public List<BonLivraison> getBonLivraisonsParNum(String num) {
		// TODO Auto-generated method stub
		return dao.getBonLivraisonsParNum(num);
	}

	@Override
	public List<CommandeClient> getCommandeClientParNum(String num) {
		// TODO Auto-generated method stub
		return dao.getCommandeClientParNum(num);
	}

	@Override
	public List<CommandeFournisseur> getCommandeFourParNum(String num) {
		// TODO Auto-generated method stub
		return dao.getCommandeFourParNum(num);
	}

	@Override
	public List<Facture> getFacturesParNum(String num) {
		// TODO Auto-generated method stub
		return dao.getFacturesParNum(num);
	}

	@Override
	public List<Avoir> getAvoirParEntreDate(Date d1, Date d2) {
		// TODO Auto-generated method stub
		return dao.getAvoirParEntreDate(d1, d2);
	}

	@Override
	public List<BonLivraison> getBonLivraisonsEntreDate(Date d1, Date d2) {
		// TODO Auto-generated method stub
		return dao.getBonLivraisonsEntreDate(d1, d2);
	}

	@Override
	public List<CommandeClient> getCommandeClientEntreDate(Date d1, Date d2) {
		// TODO Auto-generated method stub
		return dao.getCommandeClientEntreDate(d1, d2);
	}

	@Override
	public List<CommandeFournisseur> getCommandeFourEntreDate(Date d1, Date d2) {
		// TODO Auto-generated method stub
		return dao.getCommandeFourEntreDate(d1, d2);
	}

	@Override
	public List<Facture> getFacturesEntreDate(Date d1, Date d2) {
		// TODO Auto-generated method stub
		return dao.getFacturesEntreDate(d1, d2);
	}

	@Override
	public List<Representant> getAllRepresentantsClient() {
		// TODO Auto-generated method stub
		return dao.getAllRepresentantsClient();
	}
	
	@Override
	public List<Representant> getAllRepresentantsFourn() {
		// TODO Auto-generated method stub
		return dao.getAllRepresentantsFourn();
	}

	@Override
	public void addRepresentantToClient(Representant r, Long idCl) {
		// TODO Auto-generated method stub
		dao.addRepresentantToClient(r, idCl);
	}

	@Override
	public void addRepresentantToFourn(Representant r, Long idFourn) {
		// TODO Auto-generated method stub
		dao.addRepresentantToFourn(r, idFourn);
	}

	@Override
	public boolean deleteRepresentant(Long idRep) {
		// TODO Auto-generated method stub
		return dao.deleteRepresentant(idRep);
	}

	@Override
	public List<Representant> getRepresentantsByClient(Long idCl) {
		// TODO Auto-generated method stub
		return dao.getRepresentantsByClient(idCl);
	}

	@Override
	public List<Representant> getRepresentantsByFourn(Long idFourn) {
		// TODO Auto-generated method stub
		return dao.getRepresentantsByFourn(idFourn);
	}

	@Override
	public Client addClientRepresentant(Client c, Representant r) {
		// TODO Auto-generated method stub
		return dao.addClientRepresentant(c, r);
	}

	@Override
	public void addModePautreCl(AutrePaiement m, Long idCl) {
		dao.addModePautreCl(m, idCl);
		
	}

	@Override
	public void addModePchequeCl(PaiementCheque m, Long idCl) {
		dao.addModePchequeCl(m, idCl);
		
	}

	@Override
	public void addModePbanqueCl(CompteBancaire m, Long idCl) {
		dao.addModePbanqueCl(m, idCl);
		
	}

	@Override
	public CommandeClient getLastCommandeClient() {
		// TODO Auto-generated method stub
		return dao.getLastCommandeClient();
	}

	@Override
	public CommandeClient addCommandeClientSimple(CommandeClient c, double prixTotalCommande , Long idCl) {
		return dao.addCommandeClientSimple(c,prixTotalCommande, idCl);
	}
	
	@Override
	public CommandeClient addCommandeClientSimpleD(CommandeClient c, double prixTotalCommande , Long idCl) {
		return dao.addCommandeClientSimple(c,prixTotalCommande, idCl);
	}

	@Override
	public ArticleListe addArticleToBonCommande(ArticleListe a , Long idArt) {
		// TODO Auto-generated method stub
		return dao.addArticleToBonCommande(a , idArt);
	}

	@Override
	public List<ArticleListe> getArticlesForCommande(Long idCommande) {
		return dao.getArticlesForCommande(idCommande);
	}

	@Override
	public List<BonLivraison> getBonLivraisonClient(int position , int nbre) {
		// TODO Auto-generated method stub
		return dao.getBonLivraisonClient(position,nbre);
	}

	@Override
	public List<BonLivraison> getBonLivraisonFourn() {
		// TODO Auto-generated method stub
		return dao.getBonLivraisonFourn();
	}

	@Override
	public void addBonLivraisonClient(BonLivraison b ,Long idComCl) {
		dao.addBonLivraisonClient(b,idComCl);
		
	}

	@Override
	public void addBonLivraisonFournisseur(BonLivraison b ,Long idCom) {
		dao.addBonLivraisonFournisseur(b,idCom);
	}

	@Override
	public List<ArticleListe> getArticlesForLivraisonClient(Long idBl) {
		return dao.getArticlesForLivraisonClient(idBl);
	}

	@Override
	public void addFactureAccompteClientM(Facture f, Long[] data) {
		dao.addFactureAccompteClientM(f, data);
	}
	
	@Override
	public void addFactureAccompteClient(Facture f, Long idBl) {
		dao.addFactureAccompteClient(f, idBl);
	}

	

	@Override
	public List<ArticleListe> getArticlesForFacture(Long idFacture) {
		return dao.getArticlesForFacture(idFacture);
	}

	@Override
	public List<Facture> getFactureClient() {
		return dao.getFactureClient();
	}

	@Override
	public List<Facture> getFactureDirecte() {
		// TODO Auto-generated method stub
		return dao.getFactureDirecte();
	}

	@Override
	public List<Facture> getFactureAccompte() { 
		// TODO Auto-generated method stub
		return dao.getFactureAccompte();
	}

	@Override
	public List<ModePaiement> getModePaiementByClient(Long idCl) {
		// TODO Auto-generated method stub
		return dao.getModePaiementByClient(idCl);
	}

	@Override
	public double updatePrixCommande() {
		return dao.updatePrixCommande();
	}

	@Override
	public Avoir getLastAvoirClient() {
		return dao.getLastAvoirClient();
	}

	@Override
	public Avoir addAvoirClient(Avoir a, Long idCl,double prixTotalAvoir) {
		return dao.addAvoirClient(a, idCl , prixTotalAvoir);
	}

	@Override
	public ArticleListe addArticleToAvoir(ArticleListe a, Long idArt) {
		return dao.addArticleToAvoir(a, idArt);
	}

	@Override
	public List<ArticleListe> getArticlesForAvoir(Long idAvoir) {
		return dao.getArticlesForAvoir(idAvoir);
	}

	@Override
	public CommandeFournisseur addCommandeFournisseur(CommandeFournisseur c, Long idFourn ,  double prixTotalCommande) {
		return dao.addCommandeFournisseur(c, idFourn , prixTotalCommande);
	}

	@Override
	public ArticleListe addArticleToBonCommandeFourn(ArticleListe a, Long idArt) {
		return dao.addArticleToBonCommandeFourn(a, idArt);
	}

	@Override
	public Fournisseur addFournisseur(Fournisseur f) {
		return dao.addFournisseur(f);
	}

	@Override
	public CommandeFournisseur getLastCommandeFour() {
		return dao.getLastCommandeFour();
	}

	@Override
	public Avoir getLastAvoirFournisseur() {
		return dao.getLastAvoirFournisseur();
	}

	@Override
	public Avoir addAvoirFournisseur(Avoir a, Long idFourn , double prixTotalAvoir) {
		return dao.addAvoirFournisseur(a, idFourn , prixTotalAvoir);
	}

	@Override
	public ArticleListe addArticleToAvoirFourn(ArticleListe a, Long idArt) {
		return dao.addArticleToAvoirFourn(a, idArt);
	}

	@Override
	public List<ArticleListe> getArticlesForAvoirFourn(Long idAvoir) {
		return dao.getArticlesForAvoirFourn(idAvoir);
	}

	@Override
	public Fournisseur addFournisseurRepresentant(Fournisseur f, Representant r) {
		return dao.addFournisseurRepresentant(f, r);
	}

	@Override
	public List<ArticleListe> getArticlesForCommandeFour(Long idComF) {
		// TODO Auto-generated method stub
		return dao.getArticlesForCommandeFour(idComF);
	}

	@Override
	public List<ArticleListe> getArticlesForBonLivs(Long idBl) {
		// TODO Auto-generated method stub
		return dao.getArticlesForBonLivs(idBl);
	}

	@Override
	public List<CommandeClient> getCommandeByNom(String motCle, int position , int nbre) {
		// TODO Auto-generated method stub
		return dao.getCommandeByNom(motCle , position , nbre);
	}

	@Override
	public long getNombreCommandes() {
		return dao.getNombreCommandes();
	}
	
	@Override
	public List<CommandeClient> allCommandesClient() {
		return dao.allCommandesClient();
	}

	@Override
	public long getNombreCommandesMC(String motCle) {
		return dao.getNombreCommandesMC(motCle);
	}

	@Override
	public List<BonLivraison> getBLClientByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getBLClientByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreBLClient() {
		// TODO Auto-generated method stub
		return dao.getNombreBLClient();
	}

	@Override
	public List<BonLivraison> allBLClient() {
		// TODO Auto-generated method stub
		return dao.allBLClient();
	}

	@Override
	public long getNombreBLMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreBLMC(motCle);
	}

	@Override
	public List<Facture> getFacClientByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getFacClientByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreFacClient() {
		// TODO Auto-generated method stub
		return dao.getNombreFacClient();
	}

	@Override
	public List<Facture> allFacClient() {
		// TODO Auto-generated method stub
		return dao.allFacClient();
	}

	@Override
	public long getNombreFacMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreFacMC(motCle);
	}

	@Override
	public List<CommandeFournisseur> getCommandeFByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getCommandeFByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreCommandesF() {
		// TODO Auto-generated method stub
		return dao.getNombreCommandesF();
	}

	@Override
	public List<CommandeFournisseur> allCommandesF() {
		// TODO Auto-generated method stub
		return dao.allCommandesF();
	}

	@Override
	public long getNombreCommandesFMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreCommandesFMC(motCle);
	}

	@Override
	public List<Avoir> getAllAvoirClient(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllAvoirClient(position, nbre);
	}

	@Override
	public List<Avoir> getAvoirByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAvoirByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreAvoir() {
		// TODO Auto-generated method stub
		return dao.getNombreAvoir();
	}

	@Override
	public List<Avoir> allAvoirClient() {
		// TODO Auto-generated method stub
		return dao.allAvoirClient();
	}

	@Override
	public long getNombreAvoirMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreAvoirMC(motCle);
	}

	@Override
	public List<Client> getAllClients(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllClients(position, nbre);
	}

	@Override
	public List<Client> getClientByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getClientByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreClient() {
		// TODO Auto-generated method stub
		return dao.getNombreClient();
	}

	@Override
	public List<Client> allClient() {
		// TODO Auto-generated method stub
		return dao.allClient();
	}

	@Override
	public long getClientMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getClientMC(motCle);
	}

	@Override
	public List<ModePaiement> getMPByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getMPByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreMP() {
		// TODO Auto-generated method stub
		return dao.getNombreMP();
	}

	@Override
	public List<ModePaiement> allMPClient() {
		// TODO Auto-generated method stub
		return dao.allMPClient();
	}

	@Override
	public long getNombreMPMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreMPMC(motCle);  
	}

	@Override
	public List<Article> getArticles(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getArticles(position, nbre);
	}

	@Override
	public List<Article> getArticleByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getArticleByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreArticle() {
		// TODO Auto-generated method stub
		return dao.getNombreArticle();
	}

	@Override
	public List<Article> allArticleClient() {
		// TODO Auto-generated method stub
		return dao.allArticleClient();
	}

	@Override
	public long getNombreArticleMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreArticleMC(motCle);
	}

	@Override
	public List<Categorie> getCategories(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getCategories(position, nbre);
	}

	@Override
	public List<Categorie> getCategorieByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getCategorieByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreCategorie() {
		// TODO Auto-generated method stub
		return dao.getNombreCategorie();
	}

	@Override
	public List<Categorie> allCategorie() {
		// TODO Auto-generated method stub
		return dao.allCategorie();
	}

	@Override
	public long getNombreCategorieMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreCategorieMC(motCle);
	}

	@Override
	public Representant getLastRepresentantClient(Long idCl) {
		// TODO Auto-generated method stub
		return dao.getLastRepresentantClient(idCl);
	}

	@Override
	public List<Avoir> getAvoirFournisseur(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAvoirFournisseur(position, nbre);
	}

	@Override
	public List<Avoir> getAvoirByNomF(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAvoirByNomF(motCle, position, nbre);
	}

	@Override
	public long getNombreAvoirF() {
		// TODO Auto-generated method stub
		return dao.getNombreAvoirF();
	}

	@Override
	public long getNombreAvoirMCF(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreAvoirMCF(motCle);
	}

	@Override
	public List<ArticleListe> statArticleClient(Long idArt) {
		// TODO Auto-generated method stub
		return dao.statArticleClient(idArt);
	}

	@Override
	public List<Long> longs(Long idArt) {
		// TODO Auto-generated method stub
		return dao.longs(idArt);
	}

	@Override
	public List<StatArticle> statMois() {
		// TODO Auto-generated method stub
		return dao.statMois();
	}

	@Override
	public StatArticle statRecherche(Long idArt) {
		// TODO Auto-generated method stub
		return dao.statRecherche(idArt);
	}


	@Override
	public List<CommandeClient> getAllBonCommandesTraitees(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllBonCommandesTraitees(position, nbre);
	}

	@Override
	public List<CommandeClient> getCommandeByNomTraitees(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getCommandeByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreCommandesTraitees() {
		// TODO Auto-generated method stub
		return dao.getNombreCommandesTraitees();
	}

	@Override
	public List<CommandeClient> allCommandesClientTraitees() {
		// TODO Auto-generated method stub
		return dao.allCommandesClientTraitees();
	}

	@Override
	public long getNombreCommandesMCTraitees(String motCle) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CommandeClient> getAllBonCommandesNTraitees(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllBonCommandesNTraitees(position, nbre);
	}

	@Override
	public List<CommandeClient> getCommandeByNomNTraitees(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getCommandeByNomNTraitees(motCle, position, nbre);
	}

	@Override
	public long getNombreCommandesNTraitees() {
		// TODO Auto-generated method stub
		return dao.getNombreCommandesNTraitees();
	}

	@Override
	public List<CommandeClient> allCommandesClientNTraitees() {
		// TODO Auto-generated method stub
		return dao.allCommandesClientNTraitees();
	}

	@Override
	public long getNombreCommandesMCNTraitees(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreCommandesMCNTraitees(motCle);
	}

	@Override
	public List<BonLivraison> getBLClientByNomTraitees(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getBLClientByNomTraitees(motCle, position, nbre);
	}

	@Override
	public long getNombreBLClientTraitees() {
		// TODO Auto-generated method stub
		return dao.getNombreBLClientTraitees();
	}

	@Override
	public List<BonLivraison> allBLClientTraitees() {
		// TODO Auto-generated method stub
		return dao.allBLClientTraitees();
	}

	@Override
	public long getNombreBLMCTraitees(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreBLMCTraitees(motCle);
	}

	@Override
	public List<BonLivraison> getBLClientByNomNTraitees(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getBLClientByNomNTraitees(motCle, position, nbre);
	}

	@Override
	public long getNombreBLClientNTraitees() {
		// TODO Auto-generated method stub
		return dao.getNombreBLClientNTraitees();
	}

	@Override
	public List<BonLivraison> allBLClientNTraitees() {
		// TODO Auto-generated method stub
		return dao.allBLClientNTraitees();
	}

	@Override
	public long getNombreBLMCNTraitees(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreBLMCNTraitees(motCle);
	}

	@Override
	public List<Facture> getAllFacturesTraitees(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllFacturesTraitees(position, nbre);
	}

	@Override
	public List<Facture> getFacClientByNomTraitees(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getFacClientByNomTraitees(motCle, position, nbre);
	}

	@Override
	public long getNombreFacClientTraitees() {
		// TODO Auto-generated method stub
		return dao.getNombreFacClientTraitees();
	}

	@Override
	public List<Facture> allFacClientTraitees() {
		// TODO Auto-generated method stub
		return dao.allFacClientTraitees();
	}

	@Override
	public long getNombreFacMCTraitees(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreFacMCTraitees(motCle);
	}

	@Override
	public List<Facture> getAllFacturesNTraitees(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllFacturesNTraitees(position, nbre);
	}

	@Override
	public List<Facture> getFacClientByNomNTraitees(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getFacClientByNomNTraitees(motCle, position, nbre);
	}

	@Override
	public long getNombreFacClientNTraitees() {
		// TODO Auto-generated method stub
		return dao.getNombreFacClientNTraitees();
	}

	@Override
	public List<Facture> allFacClientNTraitees() {
		// TODO Auto-generated method stub
		return dao.allFacClientNTraitees();
	}

	@Override
	public long getNombreFacMCNTraitees(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreFacMCNTraitees(motCle);
	}

	@Override
	public List<BonLivraison> getAllBLTClient(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllBLTClient(position, nbre);
	}

	@Override
	public List<BonLivraison> getAllBLNTClient(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllBLNTClient(position, nbre);
	}

	@Override
	public List<CommandeFournisseur> getAllCommandeF(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllCommandeF(position, nbre);
	}

	@Override
	public List<CommandeFournisseur> getAllCommandeFT(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllCommandeFT(position, nbre);
	}

	@Override
	public List<CommandeFournisseur> getCommandeFByNomT(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getCommandeFByNomT(motCle, position, nbre);
	}

	@Override
	public long getNombreCommandesFT() {
		// TODO Auto-generated method stub
		return dao.getNombreCommandesFT();
	}

	@Override
	public List<CommandeFournisseur> allCommandesFT() {
		// TODO Auto-generated method stub
		return dao.allCommandesFT();
	}

	@Override
	public long getNombreCommandesFMCT(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreCommandesFMCT(motCle);
	}

	@Override
	public List<CommandeFournisseur> getAllCommandeFNT(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllCommandeFNT(position, nbre);
	}

	@Override
	public List<CommandeFournisseur> getCommandeFByNomNT(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getCommandeFByNomNT(motCle, position, nbre);
	}

	@Override
	public long getNombreCommandesFNT() {
		// TODO Auto-generated method stub
		return dao.getNombreCommandesFNT();
	}

	@Override
	public List<CommandeFournisseur> allCommandesFNT() {
		// TODO Auto-generated method stub
		return dao.allCommandesFNT();
	}

	@Override
	public long getNombreCommandesFMCNT(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreCommandesFMCNT(motCle);
	}

	@Override
	public List<BonLivraison> getAllBLF(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllBLF(position, nbre);
	}

	@Override
	public List<BonLivraison> getBLFByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getBLFByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreBLF() {
		// TODO Auto-generated method stub
		return dao.getNombreBLF();
	}

	@Override
	public List<BonLivraison> allBLF() {
		// TODO Auto-generated method stub
		return dao.allBLF();
	}

	@Override
	public long getNombreBLFMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreBLFMC(motCle);
	}

	@Override
	public List<BonLivraison> getAllBLFT(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllBLFT(position, nbre);
	}

	@Override
	public List<BonLivraison> getBLFByNomT(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getBLFByNomNT(motCle, position, nbre);
	}

	@Override
	public long getNombreBLFT() {
		// TODO Auto-generated method stub
		return dao.getNombreBLFT();
	}

	@Override
	public List<BonLivraison> allBLFT() {
		// TODO Auto-generated method stub
		return dao.allBLFT();
	}

	@Override
	public long getNombreBLFMCT(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreBLFMCT(motCle);
	}

	@Override
	public List<BonLivraison> getAllBLFNT(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllBLFNT(position, nbre);
	}

	@Override
	public List<BonLivraison> getBLFByNomNT(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getBLFByNomNT(motCle, position, nbre);
	}

	@Override
	public long getNombreBLFNT() {
		// TODO Auto-generated method stub
		return dao.getNombreBLFNT();
	}

	@Override
	public List<BonLivraison> allBLFNT() {
		// TODO Auto-generated method stub
		return dao.allBLFNT();
	}

	@Override
	public long getNombreBLFMCNT(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreBLFMCNT(motCle);
	}

	@Override
	public List<Facture> getAllFacturesF(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllFacturesF(position, nbre);
	}

	@Override
	public List<Facture> getFacturesFByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getFacturesFByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreFacturesF() {
		// TODO Auto-generated method stub
		return dao.getNombreFacturesF();
	}

	@Override
	public List<Facture> allFacturesF() {
		// TODO Auto-generated method stub
		return dao.allFacturesF();
	}

	@Override
	public long getNombreFacturesFMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreFacturesFMC(motCle);
	}

	@Override
	public List<Facture> getAllFacturesFT(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllFacturesFT(position, nbre);
	}

	@Override
	public List<Facture> getFacturesFByNomT(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getFacturesFByNomT(motCle, position, nbre);
	}

	@Override
	public long getNombreFacturesFT() {
		// TODO Auto-generated method stub
		return dao.getNombreFacturesFT();
	}

	@Override
	public List<Facture> allFacturesFT() {
		// TODO Auto-generated method stub
		return dao.allFacturesFT();
	}

	@Override
	public long getNombreFacturesFMCT(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreFacturesFMCT(motCle);
	}

	@Override
	public List<Facture> getAllFacturesFNT(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllFacturesFNT(position, nbre);
	}

	@Override
	public List<Facture> getFacturesFByNomNT(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getFacturesFByNomNT(motCle, position, nbre);
	}

	@Override
	public long getNombreFacturesFNT() {
		// TODO Auto-generated method stub
		return dao.getNombreFacturesFNT();
	}

	@Override
	public List<Facture> allFacturesFNT() {
		// TODO Auto-generated method stub
		return dao.allFacturesFNT();
	}

	@Override
	public long getNombreFacturesFMCNT(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreFacturesFMCNT(motCle);
	}

	@Override
	public List<Avoir> getAllAvoirsF(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllAvoirsF(position, nbre);
	}

	@Override
	public List<Avoir> getAvoirsFByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAvoirsFByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreAvoirsF() {
		// TODO Auto-generated method stub
		return dao.getNombreAvoirsF();
	}

	@Override
	public List<Avoir> allAvoirsF() {
		// TODO Auto-generated method stub
		return dao.allAvoirsF();
	}

	@Override
	public long getNombreAvoirsFMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getNombreAvoirsFMC(motCle);
	}

	@Override
	public List<Fournisseur> getAllFournisseurs(int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getAllFournisseurs(position, nbre);
	}

	@Override
	public List<Fournisseur> getFournByNom(String motCle, int position, int nbre) {
		// TODO Auto-generated method stub
		return dao.getFournByNom(motCle, position, nbre);
	}

	@Override
	public long getNombreFourn() {
		// TODO Auto-generated method stub
		return dao.getNombreFourn();
	}

	@Override
	public List<Fournisseur> allFourn() {
		// TODO Auto-generated method stub
		return dao.allFourn();
	}

	@Override
	public long getFournMC(String motCle) {
		// TODO Auto-generated method stub
		return dao.getFournMC(motCle);
	}

	@Override
	public Representant getLastRepresentantF(Long idF) {
		// TODO Auto-generated method stub
		return dao.getLastRepresentantF(idF);
	}

	@Override
	public void updateCommande(Long id, String date) {
          dao.updateCommande(id, date);		
	}

	@Override
	public void updateCommandeF(Long id, String date) {
		// TODO Auto-generated method stub
		dao.updateCommandeF(id, date);
		
	}

	@Override
	public void updateBl(Long id, String date) {
		// TODO Auto-generated method stub
		dao.updateBl(id, date);
		
	}
	
	@Override
	public void updateAv(Long id, String date) {
		// TODO Auto-generated method stub
		dao.updateAv(id, date);
		
	}

	@Override
	public void updateFacture(Long id, String date) {
		// TODO Auto-generated method stub
		dao.updateFacture(id, date);
		
	}

	@Override
	public void addFactureAccompteF(Facture f, Long idBl) {
		// TODO Auto-generated method stub
		dao.addFactureAccompteF(f, idBl);
		
	}
	
	
	@Override
	public void deleteFactureF(Long id) {
		dao.deleteFactureF(id);
		
	}

	@Override
	public void deleteBLF(Long id) {
		dao.deleteBLF(id);
	}

	@Override
	public List<ModePaiement> getModePaiesByF(Long idF) {
		// TODO Auto-generated method stub
		return dao.getModePaiesByF(idF);
	}

	@Override
	public void addModePautreF(AutrePaiement m, Fournisseur f) {
		// TODO Auto-generated method stub
		dao.addModePautreF(m, f);
		
	}
	
	@Override
	public void deleteAvoirF(Long id) {
	dao.deleteAvoirF(id);
	}

	@Override
	public void updateCl(Client c) {
		// TODO Auto-generated method stub
		dao.updateCl(c);
		
	}

	@Override
	public void updateFournisseur(Fournisseur f) {
		// TODO Auto-generated method stub
		dao.updateFournisseur(f);
		
	}

	@Override
	public void updateArticle(Article a) {
		// TODO Auto-generated method stub
		dao.updateArticle(a);
		
	}

	@Override
	public Connection connection() {
		// TODO Auto-generated method stub
		return dao.connection();
	}

	@Override
	public String pdfCommande(Long idComCl) {
		// TODO Auto-generated method stub
		return dao.pdfCommande(idComCl);
		
	}

	@Override
	public String pdfLivraison(Long idBl) {
		// TODO Auto-generated method stub
		return dao.pdfLivraison(idBl);
		
	}

	@Override
	public String pdfFacture(Long idFac) {
		// TODO Auto-generated method stub
		return dao.pdfFacture(idFac);
	}

	@Override
	public CommandeClient getCommandeById(Long id) {
		// TODO Auto-generated method stub
		return dao.getCommandeById(id);
	}

	@Override
	public BonLivraison getLivraisonById(Long id) {
		// TODO Auto-generated method stub
		return dao.getLivraisonById(id);
	}

	@Override
	public Facture getFactureById(Long id) {
		// TODO Auto-generated method stub
		return dao.getFactureById(id);
	}

	@Override
	public ArticleListe getLastArticleListe(Long idArt, Long idCl) {
		// TODO Auto-generated method stub
		return dao.getLastArticleListe(idArt, idCl);
	}

	@Override
	public List<ArticleListe> lesArticles(Long idCl) {
		// TODO Auto-generated method stub
		return dao.lesArticles(idCl);
	}

	@Override
	public void addArticleListe(ArticleListe a) {
		dao.addArticleListe(a);
		
	}

	@Override
	public List<ArticleListe> articlesComCl() {
		// TODO Auto-generated method stub
		return dao.articlesComCl();
	}

	@Override
	public Article getArticleListeByNom(String nom) {
		// TODO Auto-generated method stub
		return dao.getArticleListeByNom(nom);
	}

	@Override
	public void addFactureDirecteE(Facture f, String nomMode) {
		dao.addFactureDirecteE(f, nomMode);
		
	}

	@Override
	public void addFactureDirecteC(Facture f, String nomMode, String numCheque, String nomBanque) {
		// TODO Auto-generated method stub
		dao.addFactureDirecteC(f, nomMode, numCheque, nomBanque);
		
	}

	@Override
	public void addFactureDirecteB(Facture f, String nomMode, String numCompte, String nomBanque) {
		// TODO Auto-generated method stub
		dao.addFactureDirecteB(f, nomMode, numCompte, nomBanque);
		
	}

	@Override
	public List<Facture> getFacturesTClient(Long idCl) {
		// TODO Auto-generated method stub
		return dao.getFacturesTClient(idCl); 
	}

	@Override
	public List<Facture> getFacturesNTClient(Long idCl) {
		// TODO Auto-generated method stub
		return dao.getFacturesNTClient(idCl);
	}

	@Override
	public List<Avoir> getAvoirsClient(Long idCl) {
		// TODO Auto-generated method stub
		return dao.getAvoirsClient(idCl);
	}

	@Override
	public void releveCompte(Long idCl , String date) {
		// TODO Auto-generated method stub
		dao.releveCompte(idCl , date);
		
	}

	@Override
	public void listeInt(Long[] data) {
		// TODO Auto-generated method stub
		dao.listeInt(data);
		
	}

	@Override
	public String getSerialNumber(String drive) {
		// TODO Auto-generated method stub
		return dao.getSerialNumber(drive);
	}

	@Override
	public String getSerialNumber2() {
		// TODO Auto-generated method stub
		return dao.getSerialNumber2();
	}

	@Override
	public NumeroDisque addNumDisque(NumeroDisque n) {
		// TODO Auto-generated method stub
		return dao.addNumDisque(n);
	}

	@Override
	public String verifSerie(String num) {
		// TODO Auto-generated method stub
		System.out.println("Le num dans verif METIER est : "+num) ;
		return dao.verifSerie(num);
	}



}
