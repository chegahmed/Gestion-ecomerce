package com.entreprise.dao;

import java.sql.Connection;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
import com.entreprise.entities.CashWordConverter;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ComDaoImpl implements IComDao {
	
	@PersistenceContext
	private EntityManager em ;


	@Override
	public Client addClient(Client c) {

		c.setTotalCumul(0);
		em.persist(c);
		return c ;
	}
	

	@Override
	public void deleteClient(Long id) {
		
		Query req = em.createQuery("select r from Representant r where r.client.idCl =:x ");
		req.setParameter("x",id) ;
		List<Representant> artListe = req.getResultList();
		
		if(artListe!=null) {
			for(Representant a : artListe) {

		         Client com=em.find(Client.class, a.getClient().getIdCl()) ;
				 Collection<Representant> articles2 = com.getRepresentants() ;
				 articles2.remove(a) ;
				 com.setRepresentants(articles2);
		         em.persist(com);
				 em.remove(a);
				
			}
			
			
		}
		
		em.remove(em.find(Client.class, id));
		
	}

	@Override
	public void updateClient(Long id) {
		Client c = em.find(Client.class, id) ;
		em.merge(c) ;
		
	}



	@Override
	public Avoir addAvoirClient(Avoir a,List<ArticleListe> l , Long idCl) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		Client c = em.find(Client.class, idCl) ;
		a.setClient(c);
		a.setArticles(l);
		a.setTypeAv("AvoirClient");
		//a.setDateVraiAv(prefixe);
		em.persist(a);
		double total = 0 ;
		
		if(l!=null) {
			for(ArticleListe ar : l) {
				em.find(Article.class, ar.getArticle().getIdArt()).setStockreel(
						em.find(Article.class, ar.getArticle().getIdArt()).getStockreel()+ar.getQuantiteAl()) ;
     			ar.setClient(c);
				ar.setTypeAl("Avoir");
				ar.setDateEnregAl(date);
				ar.setDateVraiAl(prefixe);
				em.persist(a);
				total = total+ar.getPrixUnitAl()*ar.getQuantiteAl() ;
			}
		}
		
		Collection<Avoir> liste = c.getAvoirs() ;
		liste.add(a) ;
		c.setAvoirs(liste);
		em.persist(c);
		
		return a ;
		
		
	}
	
	@Override
	public Avoir addAvoirFourn(Avoir a,List<ArticleListe> l , Long idFourn) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		Fournisseur f = em.find(Fournisseur.class, idFourn) ;
		a.setFournisseur(f);
		a.setArticles(l);
		a.setTypeAv("AvoirFournisseur");
		a.setDateVraiAv(prefixe);
		em.persist(a);
		double total = 0 ;
		if(l!=null) {
			for(ArticleListe ar : l) {
				em.find(Article.class, ar.getArticle().getIdArt()).setStockreel(
						em.find(Article.class, ar.getArticle().getIdArt()).getStockreel()-ar.getQuantiteAl()) ;
     			ar.setFournisseur(f);;
				ar.setTypeAl("AvoirFournisseur"); 
				ar.setDateEnregAl(date);
				ar.setDateVraiAl(prefixe);
				em.persist(a);
				total = total+ar.getPrixUnitAl()*ar.getQuantiteAl() ;
			}
		}
		
		Collection<Avoir> liste = f.getAvoirs() ;
		liste.add(a) ;
		f.setAvoirs(liste);
		em.persist(f);
		
		return a ;
		
		
	}

	@Override
	public void deleteAvoir(Long id) {
		Query req = em.createQuery("select a from ArticleListe a where a.avoir.idAv =:x ");
		req.setParameter("x",id);
		List<ArticleListe> artListe = req.getResultList();
		if(artListe!=null) {
			for(ArticleListe a : artListe) {
				
				 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
				 Collection<ArticleListe> articles1 = arte.getArticles() ;
				 articles1.remove(a) ;
				 arte.setArticles(articles1);
		         em.persist(arte);
		         
		         Avoir av = em.find(Avoir.class, a.getAvoir().getIdAv()) ;
				 Collection<ArticleListe> articles2 = av.getArticles() ;
				 articles2.remove(a) ;
				 av.setArticles(articles2);
		         em.persist(av);
				
				 em.remove(a);
				
			}
		}
		
		Avoir av1=em.find(Avoir.class, id) ;
		Client cl=em.find(Client.class, av1.getClient().getIdCl()) ;
		Collection<Avoir> listeAv = cl.getAvoirs() ;
		listeAv.remove(av1) ;
		cl.setAvoirs(listeAv);
        em.persist(cl);
		em.remove(em.find(Avoir.class, id));
		
	}

	@Override
	public void updateAvoir(Long id) {
		Avoir a = em.find(Avoir.class, id) ;
		em.merge(a) ;
		
	}

	@Override
	public CommandeClient addBCommande(CommandeClient b ,List<ArticleListe> l , Long idCl) {
		   Date prefixe = new Date() ;
	       SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	       String date= formatDateJour.format(prefixe);
			Client c = em.find(Client.class, idCl) ;
			b.setClient(c);
			b.setArticles(l);
			b.setEtatComCl("Non reglée");
			b.setDateVraiComCl(prefixe);
			double total = 0 ;
			
			if(l!=null) {
				for(ArticleListe a : l) {
	     			a.setClient(c);
					a.setTypeAl("BonCommande"); 
					a.setDateEnregAl(date);
					a.setDateVraiAl(prefixe);
					em.persist(a);
					total = total+a.getPrixUnitAl()*a.getQuantiteAl() ;
				}
			}
			b.setTotalComCl(total);
			em.persist(b);
			Collection<CommandeClient> liste = c.getCommandeClients();
			liste.add(b) ;
			c.setCommandeClients(liste);
			em.persist(c);	
			
			return b ;
		
	}

	@Override
	public void deleteBCommande(Long id) {
		Query req = em.createQuery("select a from ArticleListe a where a.commandeClient.idComCl =:x ");
		req.setParameter("x",id) ;
		List<ArticleListe> artListe = req.getResultList();

		if(artListe!=null) {
			for(ArticleListe a : artListe) {
				 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
				 Collection<ArticleListe> articles1 = arte.getArticles() ;
				 articles1.remove(a) ;
				 arte.setArticles(articles1);
		         em.persist(arte);
		         
		         CommandeClient com=em.find(CommandeClient.class, a.getCommandeClient().getIdComCl()) ;
				 Collection<ArticleListe> articles2 = com.getArticles() ;
				 articles2.remove(a) ;
				 com.setArticles(articles2);
		         em.persist(com);
				
				 em.remove(a);	
			}
		}
		
		CommandeClient b=em.find(CommandeClient.class, id) ;
		Client cl=em.find(Client.class, b.getClient().getIdCl()) ;
		Collection<CommandeClient> listeCom = cl.getCommandeClients() ;
		listeCom.remove(b) ;
		cl.setCommandeClients(listeCom);
        em.persist(cl);
		em.remove(em.find(CommandeClient.class, id));
		
	}

	@Override
	public void updateBCommande(Long id) {
		CommandeClient b = em.find(CommandeClient.class, id) ;
		em.merge(b) ;
	}

	@Override
	public BonLivraison addBLivrFourn(BonLivraison b, List<ArticleListe> l ,Long idFour) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		Fournisseur f = em.find(Fournisseur.class, idFour) ;
		b.setFournisseur(f);
		b.setArticlesBl(l);
		b.setDateVraiBl(prefixe);
		double total = 0 ;
		
		if(l!=null) {
			for(ArticleListe a : l) {
				em.find(Article.class, a.getArticle().getIdArt()).setStockreel(
						em.find(Article.class, a.getArticle().getIdArt()).getStockreel()+a.getQuantiteAl()) ;
     			a.setFournisseur(f);
				a.setTypeAl("BonLivraisonFournisseur"); 
				a.setDateEnregAl(date);
				a.setDateVraiAl(prefixe);
				em.persist(a);
				total = total+a.getPrixUnitAl()*a.getQuantiteAl() ;
			}
		}
		em.persist(b);
		Collection<BonLivraison> liste = f.getBonLivs();
		liste.add(b) ;
		f.setBonLivs(liste);
		em.persist(f);
		
		return b ;
		
	}

	@Override
	public void deleteBLivr(Long id) {
	
		
		BonLivraison bon1=em.find(BonLivraison.class, id) ;
		Collection<ArticleListe> artListeBon = bon1.getArticlesBl();
		Collection<ArticleListe> artListeBon2 = new ArrayList<ArticleListe>();
		artListeBon2.addAll(artListeBon) ;


		CommandeClient com2=em.find(CommandeClient.class, bon1.getCommandeClient().getIdComCl()) ;
		Collection<ArticleListe> artListeCopie = com2.getArticles();
		Collection<ArticleListe> artListe = new ArrayList<ArticleListe>();
		artListe.addAll(artListeCopie) ;
		
		
		if(artListe!=null) {
			for(ArticleListe a : artListe) {
				
				 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
				 Collection<ArticleListe> articles1 = arte.getArticles() ;
				 articles1.remove(a) ;
				 arte.setArticles(articles1);
		         em.persist(arte);
		         
		         CommandeClient com=em.find(CommandeClient.class, a.getCommandeClient().getIdComCl()) ;
				 Collection<ArticleListe> articles2 = com.getArticles() ;
				 articles2.remove(a) ;
				 com.setArticles(articles2);
		         em.persist(com);
				
				 em.remove(a);	
			}
		}
		
		
		if(artListeBon!=null) {
			for(ArticleListe a : artListeBon) {
				
				 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
				 Collection<ArticleListe> articles1 = arte.getArticles() ;
				 articles1.remove(a) ;
				 arte.setArticles(articles1);
		         em.persist(arte);
		         
		         artListeBon2.remove(a) ;
		         bon1.setArticlesBl(artListeBon2);
		         em.persist(bon1);
				 em.remove(a);
				
			}
			
			
		}

		Client cl=em.find(Client.class, bon1.getClient().getIdCl()) ;
		Collection<CommandeClient> listeCom = cl.getCommandeClients() ;
		Collection<BonLivraison> listeBl = cl.getBonLivs() ;
		listeCom.remove(com2) ;
		listeBl.remove(bon1) ;
		cl.setCommandeClients(listeCom);
		cl.setBonLivs(listeBl);
        em.persist(cl);
		em.remove(em.find(CommandeClient.class, bon1.getCommandeClient().getIdComCl()));
		em.remove(bon1);
		
	}

	@Override
	public void updateBLivr(Long id) {
		BonLivraison b = em.find(BonLivraison.class, id) ;
		em.merge(b) ;
	}

	@Override
	public Categorie addCat(Categorie c) {
		em.persist(c);
		return c ;
		
	}

	@Override
	public void deleteCat(Long id) {
		em.remove(em.find(Categorie.class, id));
	}

	@Override
	public void updateCat(Long id) {
		Categorie c = em.find(Categorie.class, id) ;
		em.merge(c) ;
		
	}

	@Override
	public CommandeFournisseur addComFour(CommandeFournisseur c,List<ArticleListe> l , Long idFour) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		Fournisseur f = em.find(Fournisseur.class, idFour) ;
		c.setFournisseur(f);
		c.setArticles(l);
		c.setDateVraiFour(prefixe);
		double total = 0 ;
		
		if(l!=null) {
			for(ArticleListe a : l) {
     			a.setFournisseur(f);;
				a.setTypeAl("CommandeFournisseur"); 
				a.setDateEnregAl(date);
				a.setDateVraiAl(prefixe);
				em.persist(a);
				total = total+a.getPrixUnitAl()*a.getQuantiteAl() ;
			}
		}
		em.persist(c);
		
		Collection<CommandeFournisseur> liste = f.getCommandeFours();
		liste.add(c) ;
		f.setCommandeFours(liste);
		em.persist(f);
		return c ;
		
	}

	@Override
	public void deleteComFour(Long id) {
		Query req = em.createQuery("select a from ArticleListe a where a.commandeFournisseur.idFour =:x ");
		req.setParameter("x",id) ;
		List<ArticleListe> artListe = req.getResultList();
		
		if(artListe!=null) {
			for(ArticleListe a : artListe) {
				
				 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
				 Collection<ArticleListe> articles1 = arte.getArticles() ;
				 articles1.remove(a) ;
				 arte.setArticles(articles1);
		         em.persist(arte);
		         
		         CommandeFournisseur com=em.find(CommandeFournisseur.class, a.getCommandeFournisseur().getIdFour()) ;
				 Collection<ArticleListe> articles2 = com.getArticles() ;
				 articles2.remove(a) ;
				 com.setArticles(articles2);
		         em.persist(com);
				
				 em.remove(a);
				
			}
			
			
		}
		
		CommandeFournisseur b=em.find(CommandeFournisseur.class, id) ;
		Fournisseur f=em.find(Fournisseur.class, b.getFournisseur().getIdF()) ;
		Collection<CommandeFournisseur> listeCom = f.getCommandeFours() ;
		listeCom.remove(b) ;
		f.setCommandeFours(listeCom);
        em.persist(f);
		em.remove(em.find(CommandeFournisseur.class, id));
		
	}

	@Override
	public void updateComFour(Long id) {
		CommandeFournisseur c = em.find(CommandeFournisseur.class, id) ;
		em.merge(c) ;
	}

	@Override
	public ModePaiement addModeP(ModePaiement m) {
		em.persist(m);
		return m ;
	}
	
	@Override
	public void addModePautreCl(AutrePaiement m , Long idCl) {
		Client c = em.find(Client.class, idCl) ;
		Collection<ModePaiement> liste = c.getModePaiements();
		liste.add(m) ;
		c.setModePaiements(liste);
		m.setClient(c);
		em.persist(c);
		em.persist(m);
	}
	
	@Override
	public void addModePchequeCl(PaiementCheque m , Long idCl) {
		Client c = em.find(Client.class, idCl) ;
		Collection<ModePaiement> liste = c.getModePaiements();
		liste.add(m) ;
		c.setModePaiements(liste);
		m.setClient(c);
		em.persist(c);
		em.persist(m);
	}

	
	@Override
	public void addModePbanqueCl(CompteBancaire m , Long idCl) {
		Client c = em.find(Client.class, idCl) ;
		Collection<ModePaiement> liste = c.getModePaiements();
		liste.add(m) ;
		c.setModePaiements(liste);
		m.setClient(c);
		em.persist(c);
		em.persist(m);
	}


	@Override
	public void deleteModeP(Long idMod ,Long idCl) {
		Client c = em.find(Client.class, idCl) ;
		Collection<ModePaiement> liste = c.getModePaiements();
		ModePaiement m=em.find(ModePaiement.class, idMod) ;
		liste.remove(m) ;
		c.setModePaiements(liste);
		em.persist(c);
		em.remove(em.find(ModePaiement.class, idMod));
		
	}

	@Override
	public void updateModeP(Long id) {
		ModePaiement m = em.find(ModePaiement.class, id) ;
		em.merge(m) ;
		
	}

	@Override
	public Facture addFacture(Facture f, List<ArticleListe> l , Long idCl) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		Client c = em.find(Client.class, idCl) ;
		f.setClient(c);
		f.setArticles(l);
		f.setDateVraiFac(prefixe);
		double total = 0 ;
		if(l!=null) {
			for(ArticleListe a : l) {
				
				em.find(Article.class, a.getArticle().getIdArt()).setStockreel(
						em.find(Article.class, a.getArticle().getIdArt()).getStockreel() - a.getQuantiteAl()) ;
		
     			a.setClient(c);
				a.setTypeAl("Facture"); 
				a.setDateEnregAl(date);
				a.setDateVraiAl(prefixe);
				em.persist(a);
				total = total+a.getPrixUnitAl()*a.getQuantiteAl() ;
				
			}
		}
		f.setTotalFac(total);
		f.setCategorieFacture("Comptant");
		em.persist(f);
		Collection<Facture> liste = c.getFactures();
		liste.add(f) ;
		c.setFactures(liste);
		em.persist(c);
		return f ;
		
	}

	@Override
	public void deleteFacture(Long idF) {

		Long id = 0L ;
		Query req1 = em.createQuery("select a from ArticleListe a where a.facture.idFac =:x ");
		req1.setParameter("x",idF) ;
		List<ArticleListe> artListeFac= req1.getResultList();
		
		Facture fac=em.find(Facture.class, idF) ;
		
		Collection<BonLivraison> listeBonsCopie = fac.getBonLivraisons() ;
		Collection<BonLivraison> listeBons = new ArrayList<BonLivraison>();
		listeBons.addAll(listeBonsCopie) ;
		
		if(listeBons != null) {
			
			for(BonLivraison bonX : listeBons ) {
				
				id = bonX.getIdBl() ;
				
				BonLivraison bon1=em.find(BonLivraison.class, id) ;
				Collection<ArticleListe> artListeBon = bon1.getArticlesBl();
				Collection<ArticleListe> artListeBon2 = new ArrayList<ArticleListe>();
				artListeBon2.addAll(artListeBon) ;
				
				CommandeClient com2=em.find(CommandeClient.class, bonX.getCommandeClient().getIdComCl()) ;
				
				Collection<ArticleListe> artListeCopie = com2.getArticles();
				Collection<ArticleListe> artListe = new ArrayList<ArticleListe>();
				artListe.addAll(artListeCopie) ;
								
				if(artListe!=null) {

					for(ArticleListe a : artListe) {
						
						 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
						 Collection<ArticleListe> articles1 = arte.getArticles() ;
						 articles1.remove(a) ;
						 arte.setArticles(articles1);
				         em.persist(arte);
				         
				         CommandeClient com=em.find(CommandeClient.class, a.getCommandeClient().getIdComCl()) ;
						 Collection<ArticleListe> articles2 = com.getArticles() ;
						 articles2.remove(a) ;
						 com.setArticles(articles2);
				         em.persist(com);
						
						 em.remove(a);
						
					}
					
					
				}
				
				
				if(artListeBon!=null) {
					for(ArticleListe a : artListeBon) {
						
						 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
						 Collection<ArticleListe> articles1 = arte.getArticles() ;
						 articles1.remove(a) ;
						 arte.setArticles(articles1);
				         em.persist(arte);
				         
				         artListeBon2.remove(a) ;
				         bon1.setArticlesBl(artListeBon2);
				         em.persist(bon1);
						 em.remove(a);
						
					}
					
					
				}

				Client cl=em.find(Client.class, bonX.getClient().getIdCl()) ;
				Collection<CommandeClient> listeCom = cl.getCommandeClients() ;
				Collection<BonLivraison> listeBl = cl.getBonLivs() ;
				listeCom.remove(com2) ;
				listeBl.remove(bonX) ;
				cl.setCommandeClients(listeCom);
				cl.setBonLivs(listeBl);
		        em.persist(cl);
				em.remove(em.find(CommandeClient.class, bonX.getCommandeClient().getIdComCl()));
				em.remove(bon1);
				
			}
			
			
		}
		
		
	   if(artListeFac!=null) {
			for(ArticleListe a : artListeFac) {
				
				 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
				 Collection<ArticleListe> articles1 = arte.getArticles() ;
				 articles1.remove(a) ;
				 arte.setArticles(articles1);
		         em.persist(arte);
		         
		         Facture fac1=em.find(Facture.class, a.getFacture().getIdFac()) ;
				 Collection<ArticleListe> articles2 = fac1.getArticles() ;
				 articles2.remove(a) ;
				 fac1.setArticles(articles2);
		         em.persist(fac1);
				
				 em.remove(a);
				
			}
			
			
		}
		
		Client cl=em.find(Client.class, fac.getClient().getIdCl()) ;
		Collection<Facture> listeFacVrai = cl.getFactures() ;
		listeFacVrai.remove(fac) ;
		cl.setFactures(listeFacVrai);
        em.persist(cl);
		em.remove(em.find(Facture.class, idF));
				
		
	}

	@Override
	public void updateFacture(Long id) {
		Facture f = em.find(Facture.class, id) ;
		em.merge(f) ;
		
	}

	@Override
	public Fournisseur addFour(Fournisseur f) {
		em.persist(f);
		addModePautreF(new AutrePaiement("Espece"), f);
		addModePautreF(new AutrePaiement("Cheque"), f);
		addModePautreF(new AutrePaiement("Carte Bancaire"), f);
		em.persist(f);
		return f ;
		
	}

	@Override
	public void deleteFour(Long id) {
		Query req = em.createQuery("select r from Representant r where r.fournisseur.idF =:x ");
		req.setParameter("x",id) ;
		List<Representant> artListe = req.getResultList();
		
		if(artListe!=null) {
			for(Representant a : artListe) {

		         Fournisseur com=em.find(Fournisseur.class, a.getFournisseur().getIdF()) ;
				 Collection<Representant> articles2 = com.getRepresentants() ;
				 articles2.remove(a) ;
				 com.setRepresentants(articles2);
		         em.persist(com);
				 em.remove(a);
				
			}
			
			
		}
		
		em.remove(em.find(Fournisseur.class, id));
	}

	@Override
	public void updateFour(Long id) {
		Fournisseur f = em.find(Fournisseur.class, id) ;
		em.merge(f) ;
	}

	@Override
	public User addUser(User u , String role) {
		u.setRoleUser(role);
		em.persist(u);
		return u ;
		  
	}

	@Override
	public void deleteUser(Long idUser) {
		//User u = em.find(User.class, idUser) ;
		em.remove(em.find(User.class, idUser));
		
	}

	@Override
	public void updateUser(Long id) {
		User u = em.find(User.class, id) ;
		em.merge(u) ;
	}

	@Override
	public Article addArticle(Article a ,Long idCat ) {
		Categorie cat = em.find(Categorie.class, idCat) ;
		a.setCategorie(cat);
		em.persist(a);
        Collection<Article> liste = cat.getArticles() ;
        liste.add(a) ;
        cat.setArticles(liste);
        em.persist(cat);
        
        StatArticle stat = new StatArticle() ;
        stat.setArticle(a);
        em.persist(stat);

        return a ;
		
	}

	@Override
	public void deleteArticle(Long id) {
		em.remove(em.find(Article.class, id));
	}

	@Override
	public void updateArticle(Long id) {
		Article a = em.find(Article.class, id) ;
		em.merge(a) ;
	}

	@Override
	public Client getClientById(Long id) {
		Client c=em.find(Client.class, id) ;
		return c;
	}

	@Override
	public Client getClientByNom(String nom) {
		Query req = em.createQuery("select c from Client c where c.nom like :x ") ;
		req.setParameter("x",nom) ;
		return (Client) req.getResultList().get(0);
	}

	@Override
	public List<Article> getAllArticles() {
		Query req = em.createQuery("select a from Article a order by a.id desc");
		return req.getResultList();
	}

	@Override
	public List<Article> getArticlesByCategories(Long idCat) {
		Query req = em.createQuery("select a from Article a where a.categorie.id =:x order by a.id desc") ;
		req.setParameter("x",idCat) ;
		return req.getResultList() ;
	}

	@Override
	public Article getArticleById(Long id) {
		Article a=em.find(Article.class, id) ;
		return a;
	}

	@Override
	public Article getArticleByNom(String nom) {
		Query req = em.createQuery("select a from Article a where a.designation like :x ") ;
		req.setParameter("x",nom) ;
		return (Article) req.getResultList().get(0);
	}

	@Override
	public List<Avoir> getAllAvoirs() {
		Query req = em.createQuery("select a from Avoir a order by a.idAv desc");
		return req.getResultList();
	}

	@Override
	public List<Avoir> getAvoirsByClient(Long idCl) {
		Query req = em.createQuery("select a from Avoir a where a.client.id =:x order by a.id desc") ;
		req.setParameter("x",idCl) ;
		return req.getResultList() ;
	}

	@Override
	public List<CommandeClient> getAllBonCommandes(int position , int nbre) {
		Query req = em.createQuery("select c from CommandeClient c ");
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}

	@Override
	public List<CommandeClient> getCommandesByClient(Long idCl) {
		Query req = em.createQuery("select a from Avoir a where a.client.id =:x order by a.id desc") ;
		req.setParameter("x",idCl) ;
		return req.getResultList() ;
	}

	@Override
	public List<CommandeClient> getCommandesByDate(String date) {
		Query req = em.createQuery("select bc from BonCommande bc where bc.date like :x ") ;
		req.setParameter("x",date) ;
		return req.getResultList();
	}

	@Override
	public List<BonLivraison> getAllBonLivraisons() {
		Query req = em.createQuery("select bl from BonLivraison bl order by bl.id desc");
		return req.getResultList();
	}

	@Override
	public List<BonLivraison> getLivraisonByFourn(Long idFourn) {
		Query req = em.createQuery("select bl from BonLivraison bl where bl.fournisseur.id =:x order by a.id desc") ;
		req.setParameter("x",idFourn) ;
		return req.getResultList() ;
	}

	@Override
	public List<BonLivraison> getLivraisonByDate(String date) {
		Query req = em.createQuery("select bl from BonLlivraison bl where bl.date like :x ") ;
		req.setParameter("x",date) ;
		return req.getResultList();
	}

	@Override
	public List<Categorie> getAllCategories() {
		Query req = em.createQuery("select c from Categorie c order by c.id desc");
		return req.getResultList();
	}

	@Override
	public List<CommandeFournisseur> getAllComFournisseurs(int position , int nbre) {
		Query req = em.createQuery("select f from CommandeFournisseur f order by f.idFour desc");
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}

	@Override
	public List<CommandeFournisseur> getComFourByFourn(Long idFourn) {
		Query req = em.createQuery("select cf from CommandeFournisseur cf where cf.fournisseur.id =:x order by a.id desc") ;
		req.setParameter("x",idFourn) ;
		return req.getResultList() ;
	}

	@Override
	public List<ModePaiement> getAllModePaies(int position , int nbre) {
		Query req = em.createQuery("select mp from ModePaiement mp order by mp.id desc");
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}

	@Override
	public List<ModePaiement> getModePaiesByClient(Long idCl) {
		Query req = em.createQuery("select mp from ModePaiement mp where mp.client.idCl =:x order by mp.idMode desc") ;
		req.setParameter("x",idCl) ;
		return req.getResultList() ;
	}

	@Override
	public List<Facture> getAllFactures(int position , int nbre) {
		Query req = em.createQuery("select f from Facture f order by f.id desc");
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}

	@Override
	public List<Facture> getFacturesByClient(Long idCl) {
		Query req = em.createQuery("select f from Facture f where f.client.id =:x order by a.id desc") ;
		req.setParameter("x",idCl) ;
		return req.getResultList() ;
	}

	@Override
	public List<Fournisseur> getAllFournisseurs() {
		Query req = em.createQuery("select f from Fournisseur f order by f.idF desc");
		return req.getResultList();
	}

	@Override
	public Fournisseur getFournById(Long id) {
		Fournisseur f=em.find(Fournisseur.class, id) ;
		return f;
	}

	@Override
	public Fournisseur getFournByNom(String nom) {
		Query req = em.createQuery("select f from Fournisseur f where f.nom like :x ") ;
		req.setParameter("x",nom) ;
		return (Fournisseur) req.getResultList().get(0);
	}

	@Override
	public List<User> getAllUsers() {
		Query req = em.createQuery("select u from User u");
		return req.getResultList();
	}

	@Override
	public User getUserById(Long id) {
		User u =em.find(User.class, id) ;
		return u;
	}

	@Override
	public User getUserByNom(String nom) {
		Query req = em.createQuery("select u from User u where u.nom like :x ") ;
		req.setParameter("x",nom) ;
		return (User) req.getResultList().get(0);
	}

	@Override
	public Facture addFactureLivs(Facture f, List<BonLivraison> b , Long idCl) {
		List<ArticleListe> listeArticles = new ArrayList<ArticleListe>();
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		if(b!=null) {
			for(BonLivraison bl : b) {
				listeArticles.addAll(bl.getArticlesBl()) ;
				bl.setEtatBl("reglé");
				em.persist(bl);
			}
		}
		
		Client c = em.find(Client.class, idCl) ;
		double total = 0 ;
		
		if(b!=null) {
			for(ArticleListe a : listeArticles) {
				em.find(Article.class, a.getArticle().getIdArt()).setStockreel(
						em.find(Article.class, a.getArticle().getIdArt()).getStockreel()-a.getQuantiteAl()) ;
     			a.setClient(c);
				a.setTypeAl("Facture"); 
				a.setDateEnregAl(date);
				a.setDateVraiAl(prefixe);
				em.persist(a);
				total = total+a.getPrixUnitAl()*a.getQuantiteAl() ;
			}
		}
		
		f.setClient(c);
		f.setArticles(listeArticles);
		f.setTotalFac(total);
		f.setDateVraiFac(prefixe);
		f.setCategorieFacture("Accompte");
		em.persist(f);
		Collection<Facture> liste = c.getFactures();
		liste.add(f) ;
		c.setFactures(liste);
		em.persist(c);
		return f ;

	}

	@Override
	public BonLivraison addBLivrClient(BonLivraison b, List<ArticleListe> l, Long idCl) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		Client c = em.find(Client.class, idCl) ;
		b.setClient(c);;
		b.setArticlesBl(l);
		b.setEtatBl("non facturé");
		b.setDateVraiBl(prefixe);
		double total = 0 ;
		
		if(l!=null) {
			for(ArticleListe a : l) {
				em.find(Article.class, a.getArticle().getIdArt()).setStockreel(
						em.find(Article.class, a.getArticle().getIdArt()).getStockreel()+a.getQuantiteAl()) ;
     			a.setClient(c);
				a.setTypeAl("BonLivraisonClient"); 
				a.setDateEnregAl(date);
				a.setDateVraiAl(prefixe);
				em.persist(a);
				total = total+a.getPrixUnitAl()*a.getQuantiteAl() ;
			}
		}
		b.setTotalhtBl(total);
		em.persist(b);
		Collection<BonLivraison> liste = c.getBonLivs();
		liste.add(b) ;
		c.setBonLivs(liste);
		em.persist(c);
		
		return b ;
	}

	@Override
	public Facture reglementFacture(Long idFacture , Long idMode , double montantFac , double totaldu) { 
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		ModePaiement m = em.find(ModePaiement.class, idMode) ;
		
		Facture f = em.find(Facture.class, idFacture) ;
		f.setModePaiement(m);
		f.setDateRegle(date);
		f.setPaiement(m.getNomMode());
		Client c = f.getClient() ;
		c.setTotaldu(totaldu);
		if(f.getTotalFac() <= montantFac) {
			f.setEtatFac("reglée");
			
			c.setTotalCumul(c.getTotalCumul() + (montantFac - f.getTotalFac() ));
			
		}
		else {
			f.setTotalFac(f.getTotalFac() - montantFac);
		}
		
		em.persist(c);
		return f;
	}

	@Override
	public Facture addFactureCommandes(Facture f, List<CommandeClient> lc, Long idCl) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		List<ArticleListe> listeArticles = null;
		if(lc!=null) {
			for(CommandeClient cl : lc) {
				listeArticles.addAll(cl.getArticles()) ;
				cl.setEtatComCl("reglée");
			}
		}
		
		Client c = em.find(Client.class, idCl) ;
		double total = 0 ;
		
		if(lc!=null) {
			for(ArticleListe a : listeArticles) {
				em.find(Article.class, a.getArticle().getIdArt()).setStockreel(
						em.find(Article.class, a.getArticle().getIdArt()).getStockreel()-a.getQuantiteAl()) ;
     			a.setClient(c);
				a.setTypeAl("Facture"); 
				a.setDateEnregAl(date);
				a.setDateVraiAl(prefixe);
				em.persist(a);
				total = total+a.getPrixUnitAl()*a.getQuantiteAl() ;
			}
		}
		
		f.setClient(c);
		f.setArticles(listeArticles);
		f.setTotalFac(total);
		f.setDateVraiFac(prefixe);
		em.persist(f);
		Collection<Facture> liste = c.getFactures();
		liste.add(f) ;
		c.setFactures(liste);
		em.persist(c);
		return f ;
	}

	@Override
	public BonLivraison addBLivrCommandes(BonLivraison b, List<CommandeClient> lc, Long idCl) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		List<ArticleListe> listeArticles = null;
		if(lc!=null) {
			for(CommandeClient cl : lc) {
				listeArticles.addAll(cl.getArticles()) ;
				cl.setEtatComCl("traitée");
			}
		}
		
		Client c = em.find(Client.class, idCl) ;
		double total = 0 ;
		
		if(lc!=null) {
			for(ArticleListe a : listeArticles) {
				em.find(Article.class, a.getArticle().getIdArt()).setStockreel(
						em.find(Article.class, a.getArticle().getIdArt()).getStockreel()-a.getQuantiteAl()) ;
     			a.setClient(c);
				a.setTypeAl("BonLivraison"); 
				a.setDateEnregAl(date);
				a.setDateVraiAl(prefixe);
				em.persist(a);
				total = total+a.getPrixUnitAl()*a.getQuantiteAl() ;
			}
		}
		
		
		b.setClient(c);
		b.setArticlesBl(listeArticles);
		b.setTotalhtBl(total);
		b.setDateVraiBl(prefixe);
		em.persist(b);
		
		Collection<BonLivraison> liste = c.getBonLivs();
		liste.add(b) ;
		c.setBonLivs(liste);;
		em.persist(c);
		return b ;
	}

	@Override
	public List<BonLivraison> getBonLivraisonsParDate() {
		Query req = em.createQuery("select b from BonLivraison b order by b.id desc");
		return req.getResultList();
	}

	@Override
	public List<CommandeClient> getCommandeClientParDate() {
		Query req = em.createQuery("select c from CommandeClient c order by c.id desc");
		return req.getResultList();
	}

	@Override
	public List<CommandeFournisseur> getCommandeFourParDate() {
		Query req = em.createQuery("select c from CommandeFournisseur c order by c.id desc");
		return req.getResultList();
	}

	@Override
	public List<Facture> getFacturesParDate() {
		Query req = em.createQuery("select f from Facture f order by f.id desc");
		return req.getResultList();
	}

	@Override
	public List<Avoir> getAvoirParNom() {
		Query req = em.createQuery("select a from Avoir a order by a.client.nom asc");
		return req.getResultList();
	}

	@Override
	public List<BonLivraison> getBonLivraisonsParNom() {
		Query req = em.createQuery("select b from BonLivraison b order by b.client.nom asc");
		return req.getResultList();
	}

	@Override
	public List<CommandeClient> getCommandeClientParNom() {
		Query req = em.createQuery("select c from CommandeClient c order by c.client.nom asc");
		return req.getResultList();
	}

	@Override
	public List<CommandeFournisseur> getCommandeFourParNom() {
		Query req = em.createQuery("select c from CommandeFournisseur c order by c.fournisseur.nom asc");
		return req.getResultList();
	}

	@Override
	public List<Facture> getFacturesParNom() {
		Query req = em.createQuery("select f from Facture f order by f.client.nom asc");
		return req.getResultList();
	}

	@Override
	public List<Avoir> getAvoirParNum(String num) {
		Query req = em.createQuery("select a from Avoir a where a.id =:x ");
		req.setParameter("x",num) ;
		return req.getResultList();
	}

	@Override
	public List<BonLivraison> getBonLivraisonsParNum(String num) {
		Query req = em.createQuery("select b from BonLivraison b where b.id =:x ");
		req.setParameter("x",num) ;
		return req.getResultList();
	}

	@Override
	public List<CommandeClient> getCommandeClientParNum(String num) {
		Query req = em.createQuery("select c from CommandeClient c where c.id =:x ");
		req.setParameter("x",num) ;
		return req.getResultList();
	}

	@Override
	public List<CommandeFournisseur> getCommandeFourParNum(String num) {
		Query req = em.createQuery("select c from CommandeFournisseur c where c.id =:x ");
		req.setParameter("x",num) ;
		return req.getResultList();
	}

	@Override
	public List<Facture> getFacturesParNum(String num) {
		Query req = em.createQuery("select f from Facture f where f.id =:x ");
		req.setParameter("x",num) ;
		return req.getResultList();
	}

	@Override
	public List<Avoir> getAvoirParEntreDate(Date d1, Date d2) {
		Query req = em.createQuery("select a from Avoir a where ( a.dateVrai between :x and :y ) order by a.id desc");
		req.setParameter("x","%"+d1+"%") ;
		req.setParameter("y","%"+d2+"%") ;
		return req.getResultList();
	}

	@Override
	public List<BonLivraison> getBonLivraisonsEntreDate(Date d1, Date d2) {
		Query req = em.createQuery("select b from BonLivraison b where ( b.dateVrai between :x and :y ) order by b.id desc");
		req.setParameter("x","%"+d1+"%") ;
		req.setParameter("y","%"+d2+"%") ;
		return req.getResultList();
	}

	@Override
	public List<CommandeClient> getCommandeClientEntreDate(Date d1, Date d2) {
		Query req = em.createQuery("select c from CommandeClient c where ( c.dateVrai between :x and :y ) order by c.id desc");
		req.setParameter("x","%"+d1+"%") ;
		req.setParameter("y","%"+d2+"%") ;
		return req.getResultList();
	}

	@Override
	public List<CommandeFournisseur> getCommandeFourEntreDate(Date d1, Date d2) {
		Query req = em.createQuery("select c from CommandeFournisseur c where ( c.dateVrai between :x and :y ) order by c.id desc");
		req.setParameter("x","%"+d1+"%") ;
		req.setParameter("y","%"+d2+"%") ;
		return req.getResultList();
	}

	@Override
	public List<Facture> getFacturesEntreDate(Date d1, Date d2) {
		Query req = em.createQuery("select f from Facture f where ( f.dateVrai between :x and :y ) order by f.id desc");
		req.setParameter("x","%"+d1+"%") ;
		req.setParameter("y","%"+d2+"%") ;
		return req.getResultList();
	}

	@Override
	public List<Representant> getAllRepresentantsClient() {
		Query req = em.createQuery("select r from Representant r where r.fournisseur is null order by r.id desc");
		return req.getResultList();
	}
	
	@Override
	public List<Representant> getAllRepresentantsFourn() {
		Query req = em.createQuery("select r from Representant r where r.client is null order by r.id desc");
		return req.getResultList();
	}

	@Override
	public void addRepresentantToClient(Representant r, Long idCl) {
		Client c = em.find(Client.class, idCl) ;
		Collection<Representant> liste = c.getRepresentants();
		liste.add(r) ;
		c.setRepresentants(liste);
		r.setClient(c);
		em.persist(c);
		em.persist(r);
	}

	@Override
	public void addRepresentantToFourn(Representant r, Long idFourn) {
		Fournisseur f = em.find(Fournisseur.class, idFourn) ;
		Collection<Representant> liste = f.getRepresentants();
		liste.add(r) ;
		f.setRepresentants(liste);
		r.setFournisseur(f);;
		em.persist(f);
		em.persist(r);
	}

	@Override
	public boolean deleteRepresentant(Long idRep) {
		em.remove(em.find(Representant.class, idRep));
		return true ;
	}

	@Override
	public List<Representant> getRepresentantsByClient(Long idCl) {
		Query req = em.createQuery("select r from Representant r where r.client.id =:x ") ;
		req.setParameter("x",idCl) ;
		return req.getResultList();
	}

	@Override
	public List<Representant> getRepresentantsByFourn(Long idFourn) {
		Query req = em.createQuery("select r from Representant r where r.fournisseur.id =:x ") ;
		req.setParameter("x",idFourn) ;
		return req.getResultList();
	}

	@Override
	public Client addClientRepresentant(Client c, Representant r) {
		em.persist(c);
		if(r!=null) {
		r.setClient(c);
		em.persist(r);
		List<Representant> liste = new ArrayList<Representant>();
		liste.add(r) ;
		c.setRepresentants(liste);
		em.refresh(c);
		em.persist(r);
		
		}
		
		
		return c;
	}


	@Override
	public CommandeClient getLastCommandeClient() {
		Query req = em.createQuery("select c from CommandeClient c order by c.idComCl desc ");
		return (CommandeClient) req.getResultList().get(0);
	}


	@Override
	@Transactional
	public CommandeClient addCommandeClientSimple(CommandeClient c,double prixTotalCommande , Long idCl) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
	    
	   // c.setDateComCl(date);
		
		Client cl = em.find(Client.class, idCl) ;
		c.setClient(cl);
		c.setEtatComCl("Non reglée");
		c.setTotalComCl(prixTotalCommande);
		c.setTotaltvaComCl((prixTotalCommande)/5);
		c.setTotalttcComCl((prixTotalCommande) + (prixTotalCommande)/5 );
		c.setTotalString(CashWordConverter.doubleConvert(c.getTotalComCl()).concat(" dirhams"));
		Collection<CommandeClient> liste =  cl.getCommandeClients();
		liste.add(c);
		cl.setCommandeClients(liste);
		em.persist(c);
		em.persist(cl);
		
		return c ;
	}
	
	
	
	@Override
	@Transactional
	public CommandeClient addCommandeClientSimpleD(CommandeClient c,double prixTotalCommande , Long idCl) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
	    
	   // c.setDateComCl(date);
		
		Client cl = em.find(Client.class, idCl) ;
		c.setClient(cl);
		c.setEtatComCl("reglée");
		c.setTotalComCl(prixTotalCommande);
		c.setTotaltvaComCl((prixTotalCommande)/5);
		c.setTotalttcComCl((prixTotalCommande) + (prixTotalCommande)/5 );
		c.setTotalString(CashWordConverter.doubleConvert(c.getTotalComCl()).concat(" dirhams"));
		Collection<CommandeClient> liste =  cl.getCommandeClients();
		liste.add(c);
		cl.setCommandeClients(liste);
		em.persist(c);
		em.persist(cl);
		
		return c ;
	}



	@Override
	@Transactional
	public ArticleListe addArticleToBonCommande(ArticleListe a , Long idArt) {
		CommandeClient c = getLastCommandeClient();
		Article ar = em.find(Article.class, idArt) ;
		Collection<ArticleListe> listear = ar.getArticles() ;
		listear.add(a) ;
		a.setArticle(ar);
		em.persist(ar);
		Client cl  = c.getClient() ;
		a.setClient(cl);
	 	a.setCommandeClient(c);
	 	a.setDateEnregAl(c.getDateComCl());
		a.setTypeAl("Commande Client");
		Collection<ArticleListe> liste = c.getArticles();
		liste.add(a);
		c.setArticles(liste);
		em.persist(c);
		em.persist(a);
		return a ;
	}


	@Override
	public List<ArticleListe> getArticlesForCommande(Long idCommande) {
		Query req = em.createQuery("select a from ArticleListe a where a.commandeClient.idComCl =:x ");
		req.setParameter("x",idCommande) ;
		return req.getResultList();
	}
	
	@Override
	public List<ArticleListe> getArticlesForLivraisonClient(Long idBl) {
		Query req = em.createQuery("select a from ArticleListe a where a.bonLivraison.idBl =:x ");
		req.setParameter("x",idBl) ;
		return req.getResultList();
	}


	@Override
	public List<BonLivraison> getBonLivraisonClient(int position , int nbre) {
		String y ="BonLivraisonClient" ;
		Query req = em.createQuery("select bl from BonLivraison bl where bl.typeBl like :x order by bl.idBl desc");
		req.setParameter("x",y) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<BonLivraison> getBonLivraisonFourn() {
		String y ="BonLivraisonFournisseur" ;
		Query req = em.createQuery("select bl from BonLivraison bl where bl.typeBl like :x order by bl.id desc");
		req.setParameter("x",y) ;
		return req.getResultList();
	}


	@Override
	public void addBonLivraisonClient(BonLivraison b ,Long idComCl) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
	   
	    
		CommandeClient c = em.find(CommandeClient.class, idComCl) ;
		Client cl = c.getClient() ;
		Collection<ArticleListe> liste = c.getArticles() ;
		Collection<ArticleListe> listeBl = new ArrayList<ArticleListe>();
		listeBl.addAll(liste) ;
		List<ArticleListe> test = null ;
		
		/*Calendar cal1 = Calendar.getInstance();
	    cal1.setTime(c.getDateVraiComCl());
	    int mois = cal1.get(Calendar.MONTH) + 1 ;*/
	    String z = c.getDateComCl() ;
	    String h = z.substring(3,5) ;
	    int mois = Integer.parseInt(h) ;
	    System.out.println("Le mois est : "+mois) ; 

     	if(listeBl!=null) {
     		
     		String s = "statLivre" ;
     		int i = 0 ;
     		int j = 0 ;
     		Long[] x = new Long[20];
     		Long[] y = new Long[20];
     		Query[] req= new Query[20];
     		Query[] req1= new Query[20];
			for(ArticleListe a : listeBl) {
				
				x[j] = a.getArticle().getIdArt() ;
				y[j] = a.getClient().getIdCl() ;
				
				if(getLastArticleListe(x[j], y[j]) != null) {
					ArticleListe al = getLastArticleListe(x[j], y[j]) ;
					i = al.getNbreLivre() ;
					al.setStatLivre("");
					em.persist(al);
					em.flush();
					System.out.println("article existant") ;
					System.out.println("la quantité est "+i) ;
				}
				
				else {
					i = 0 ;
				}
				
               /* req[j] = em.createQuery("select ar2  from ArticleListe ar2 where (ar2.article.idArt =:x and ar2.client.idCl =:y and ar2.statLivre like :s) order by ar2.idArtListe DESC  ");
                req[j].setParameter("x",x[j]) ;
				req[j].setParameter("y",y[j]) ;
				req[j].setParameter("s","%"+s+"%") ;
				
				if((req[j].getResultList().size() != 0) && ( req[j].getResultList().get(0) !=null ) && (!(req[j].getResultList().isEmpty()))  ) {
				i = ((ArticleListe) req[j].getResultList().get(0)).getNbreLivre() ;
					((ArticleListe) req[j].getResultList().get(0)).setStatLivre("");
					ArticleListe arteV = ((ArticleListe) req[j].getResultList().get(0)) ;
					em.persist(arteV);
					em.flush();
					System.out.println("article existant") ;
					System.out.println("la quantité est "+i) ;

				}
				else {
					i = 0 ;
				}*/
				
				
				
				
			    req1[j] = em.createQuery("select s  from StatArticle s where s.article.idArt =:x");
		        req1[j].setParameter("x",x[j]) ;
		         
		        if(mois==1) {
				((StatArticle)req1[j].getResultList().get(0)).setStatJan(((StatArticle)req1[j].getResultList().get(0)).getStatJan() + a.getQuantiteAl());
		        }
		        if(mois==2) {
					((StatArticle)req1[j].getResultList().get(0)).setStatFev(((StatArticle)req1[j].getResultList().get(0)).getStatFev() + a.getQuantiteAl());
			        }
		        if(mois==3) {
					((StatArticle)req1[j].getResultList().get(0)).setStatMar(((StatArticle)req1[j].getResultList().get(0)).getStatMar() + a.getQuantiteAl());
			        }
		        if(mois==4) {
					((StatArticle)req1[j].getResultList().get(0)).setStatAvr(((StatArticle)req1[j].getResultList().get(0)).getStatAvr() + a.getQuantiteAl());
			        }
		        if(mois==5) {
					((StatArticle)req1[j].getResultList().get(0)).setStatMai(((StatArticle)req1[j].getResultList().get(0)).getStatMai() + a.getQuantiteAl());
			        }
		        if(mois==6) {
					((StatArticle)req1[j].getResultList().get(0)).setStatJuin(((StatArticle)req1[j].getResultList().get(0)).getStatJuin() + a.getQuantiteAl());
			        }
		        if(mois==7) {
					((StatArticle)req1[j].getResultList().get(0)).setStatJuil(((StatArticle)req1[j].getResultList().get(0)).getStatJuil() + a.getQuantiteAl());
			        }
		        if(mois==8) {
					((StatArticle)req1[j].getResultList().get(0)).setStatAout(((StatArticle)req1[j].getResultList().get(0)).getStatAout() + a.getQuantiteAl());
			        }
		        if(mois==9) {
					((StatArticle)req1[j].getResultList().get(0)).setStatSept(((StatArticle)req1[j].getResultList().get(0)).getStatSept() + a.getQuantiteAl());
			        }
		        if(mois==10) {
					((StatArticle)req1[j].getResultList().get(0)).setStatOct(((StatArticle)req1[j].getResultList().get(0)).getStatOct() + a.getQuantiteAl());
			        }
		        if(mois==11) {
					((StatArticle)req1[j].getResultList().get(0)).setStatNov(((StatArticle)req1[j].getResultList().get(0)).getStatNov() + a.getQuantiteAl());
			        }
		        if(mois==12) {
					((StatArticle)req1[j].getResultList().get(0)).setStatDec(((StatArticle)req1[j].getResultList().get(0)).getStatDec() + a.getQuantiteAl());
			        }
		        em.persist((StatArticle)req1[j].getResultList().get(0));
			    
			    j = j+1 ;
				a.setStatLivre("statLivre");
				a.setNbreLivre(i + a.getQuantiteAl());
     			a.setClient(c.getClient());
				a.setTypeAl("BonLivraisonClient"); 
				a.setDateEnregAl(date);  
				a.setBonLivraison(b);
				a.setDateVraiAl(prefixe);
				a.setNbreLivre(i + a.getQuantiteAl());
				em.persist(a);
			}
		}
     	
     	if(listeBl!=null) {
			for(ArticleListe a : listeBl) {
     			a.getArticle().setStockreel(a.getArticle().getStockreel()-a.getQuantiteAl());
				em.persist(a.getArticle());
			}
		}
        
     	b.setArticlesBl(listeBl);
     	Collection<BonLivraison> bons = cl.getBonLivs() ;
     	bons.add(b) ;
     	cl.setBonLivs(bons);
		b.setTypeBl("BonLivraisonClient");
		b.setEtatBl("non reglé");
		b.setClient(c.getClient());
		b.setCommandeClient(c);
		b.setTotalhtBl(c.getTotalComCl());
		b.setTotaltvaBl(c.getTotalComCl()/5);
		b.setTotalttcBl(c.getTotalComCl() + c.getTotalComCl()/5);
		b.setTotalString(CashWordConverter.doubleConvert(c.getTotalComCl() + c.getTotalComCl()/5).concat(" dirhams"));
		b.setClient(cl);
		b.setDateBl(date);
		b.setDateVraiBl(prefixe);
		c.setEtatComCl("reglée");
		em.persist(c);
		em.persist(cl);
		em.persist(b);
		
		
		
	}


	@Override
	public void addBonLivraisonFournisseur(BonLivraison b ,Long idCom) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		CommandeFournisseur c = em.find(CommandeFournisseur.class, idCom) ;
		Fournisseur f = c.getFournisseur() ;
		Collection<ArticleListe> liste = c.getArticles() ;
		Collection<ArticleListe> listeBl = new ArrayList<ArticleListe>();
		listeBl.addAll(liste) ;
		
     	if(listeBl!=null) {
			for(ArticleListe a : listeBl) {
     			a.setFournisseur(c.getFournisseur());
				a.setTypeAl("BonLivraisonFournisseur"); 
				a.setDateEnregAl(date);
				a.setBonLivraison(b);
				a.setDateVraiAl(prefixe);
				em.persist(a);
			}
		}
     	
     	if(listeBl!=null) {
			for(ArticleListe a : listeBl) {
     			a.getArticle().setStockreel(a.getArticle().getStockreel() + a.getQuantiteAl());
				em.persist(a.getArticle());
			}
		}
        
     	b.setArticlesBl(listeBl);
     	Collection<BonLivraison> bons = f.getBonLivs() ;
     	bons.add(b) ;
     	f.setBonLivs(bons);
		b.setTypeBl("BonLivraisonFournisseur");
		b.setEtatBl("non reglé");
		b.setFournisseur(c.getFournisseur());
		b.setCommandeFourn(c);
		b.setTotalttcBl(c.getTotalFour());
		b.setFournisseur(f);
		b.setDateBl(date);
		b.setDateVraiBl(prefixe);
		c.setEtatComFour("reglée");
		em.persist(c);
		em.persist(f);
		em.persist(b);
		
	}
	
	
	
	@Override
	public void addFactureAccompteClientM(Facture f, Long[] data) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
	    
	    
	    
      Collection<BonLivraison> collBl = new ArrayList<BonLivraison>();
		
		for(int i=0 ; i<= (data.length - 1) ; i++) {
			BonLivraison bv = em.find(BonLivraison.class, data[i]) ;
			collBl.add(bv) ;
		}
		
		double totalFacture = 0 ;
		
		Collection<ArticleListe> liste = new ArrayList<ArticleListe>();
		for(BonLivraison b : collBl) {
			
			totalFacture = totalFacture + b.getTotalhtBl() ;
			liste.addAll(b.getArticlesBl()) ;
			
		}
	    
	   
		BonLivraison b = em.find(BonLivraison.class, data[0]) ;
		Client cl = b.getClient() ;
		//Collection<ArticleListe> listeFac= new ArrayList<ArticleListe>();

		Collection<ArticleListe> listeFac= new ArrayList<ArticleListe>();
		listeFac.addAll(liste) ;
		
     	if(listeFac!=null) {
			for(ArticleListe a : listeFac) {
     			a.setClient(b.getClient());
				a.setDateEnregAl(date);
				a.setFacture(f);
				a.setDateVraiAl(prefixe);
				em.persist(a);
			}
		}
        
     	f.setArticles(listeFac);
     	Collection<Facture> facs = cl.getFactures() ;
     	facs.add(f) ;
     	cl.setFactures(facs);
     	f.setTypeFac("FactureClient");
     	f.setEtatFac("non reglée");
     	f.setClient(cl);
     	f.setCategorieFacture("FactureAccompte");
     	//Collection<BonLivraison> bons = new ArrayList<BonLivraison>();
     	//bons.add(b) ;
     	f.setBonLivraisons(collBl);

     	for(int i=0 ; i<= (data.length - 1) ; i++) {
			em.find(BonLivraison.class, data[i]).setEtatBl("reglé"); ;
			em.find(BonLivraison.class, data[i]).setFacture(f);
			em.persist(em.find(BonLivraison.class, data[i]));
		}
     	//b.setEtatBl("reglé");
     	f.setTotalFac(totalFacture);
     	f.setTotaltvaFac(b.getTotalhtBl()/5);
     	f.setTotalttcFac(b.getTotalhtBl() + b.getTotalhtBl()/5);
     	f.setTotalString(CashWordConverter.doubleConvert(b.getTotalhtBl() + b.getTotalhtBl()/5).concat(" dirhams"));
     	f.setDateFac(date);
     	f.setDateVraiFac(prefixe);
     	em.persist(cl);
		
		em.persist(f);
	}


	@Override
	public void addFactureAccompteClient(Facture f , Long idBl) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
	   
		BonLivraison b = em.find(BonLivraison.class, idBl) ;
		Client cl = b.getClient() ;
		Collection<ArticleListe> liste = b.getArticlesBl() ;
		Collection<ArticleListe> listeFac= new ArrayList<ArticleListe>();
		listeFac.addAll(liste) ;
		
     	if(listeFac!=null) {
			for(ArticleListe a : listeFac) {
     			a.setClient(b.getClient());
				a.setDateEnregAl(date);
				a.setFacture(f);
				a.setDateVraiAl(prefixe);
				em.persist(a);
			}
		}
        
     	f.setArticles(listeFac);
     	Collection<Facture> facs = cl.getFactures() ;
     	facs.add(f) ;
     	cl.setFactures(facs);
     	f.setTypeFac("FactureClient");
     	f.setEtatFac("non reglée");
     	f.setClient(cl);
     	f.setCategorieFacture("FactureAccompte");
     	Collection<BonLivraison> bons = new ArrayList<BonLivraison>();
     	bons.add(b) ;
     	f.setBonLivraisons(bons);
     	b.setFacture(f);
     	b.setEtatBl("reglé");
     	f.setTotalFac(b.getTotalhtBl());
     	f.setTotaltvaFac(b.getTotalhtBl()/5);
     	f.setTotalttcFac(b.getTotalhtBl() + b.getTotalhtBl()/5);
     	f.setTotalString(CashWordConverter.doubleConvert(b.getTotalhtBl() + b.getTotalhtBl()/5).concat(" dirhams"));
     	f.setDateFac(date);
     	f.setDateVraiFac(prefixe);
     	em.persist(cl);
		em.persist(b);
		em.persist(f);
	}


	@Override
	public void addFactureDirecteE(Facture f , String nomMode) {

		CommandeClient c = getLastCommandeClient() ;
		Long idCom = c.getIdComCl() ;
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		//CommandeClient c = em.find(CommandeClient.class, idCom) ;
		Client cl = c.getClient() ;
		
		Collection<ModePaiement> liste5 = cl.getModePaiements();
		ModePaiement m = new AutrePaiement("Espèce") ;
		liste5.add(m) ;
		cl.setModePaiements(liste5);
		m.setClient(cl);
		
		Collection<ArticleListe> liste = c.getArticles() ;
		Collection<ArticleListe> listeFac = new ArrayList<ArticleListe>();
		listeFac.addAll(liste) ;
		
     	if(listeFac!=null) {
			for(ArticleListe a : listeFac) {
     			a.setClient(c.getClient());
				a.setTypeAl("FactureClient"); 
				a.setDateEnregAl(date);
				a.setFacture(f);;
				a.setDateVraiAl(prefixe);
				em.persist(a);
			}
		}
        
     	f.setArticles(listeFac);
     	f.setTypeFac("FactureClient");
     	f.setCategorieFacture("FactureDirecte");
     	f.setClient(cl);
     	f.setModePaiement(m);
        Collection<CommandeClient> coms = new ArrayList<CommandeClient>();
        coms.add(c) ; 
        f.setCommandes(coms);
        f.setTotalFac(c.getTotalComCl());
        f.setTotaltvaFac(c.getTotalComCl()/5);
     	f.setTotalttcFac(c.getTotalComCl() + c.getTotalComCl()/5);
        CashWordConverter.doubleConvert(c.getTotalComCl() + c.getTotalComCl()/5).concat(" dirhams") ;
        Collection<Facture> fact = cl.getFactures() ;
     	fact.add(f) ;
     	cl.setFactures(fact);
		f.setDateFac(date);
     	f.setTotalString(CashWordConverter.doubleConvert(c.getTotalComCl() + c.getTotalComCl()/5).concat(" dirhams"));
     	
		f.setDateVraiFac(prefixe);
		f.setEtatFac("reglée");
		c.setEtatComCl("reglée");
		em.persist(cl);
		em.persist(c);
		em.persist(f);
		em.persist(m);
		
		pdfFacture(f.getIdFac()) ;
	}
	
	
	
	@Override
	public void addFactureDirecteC(Facture f , String nomMode , String numCheque , String nomBanque) {

		CommandeClient c = getLastCommandeClient() ;
		Long idCom = c.getIdComCl() ;
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		//CommandeClient c = em.find(CommandeClient.class, idCom) ;
		Client cl = c.getClient() ;
		
		Collection<ModePaiement> liste5 = cl.getModePaiements();
		ModePaiement m = new PaiementCheque("Chèque", numCheque, nomBanque) ;
		liste5.add(m) ;
		cl.setModePaiements(liste5);
		m.setClient(cl);
		
		Collection<ArticleListe> liste = c.getArticles() ;
		Collection<ArticleListe> listeFac = new ArrayList<ArticleListe>();
		listeFac.addAll(liste) ;
		
     	if(listeFac!=null) {
			for(ArticleListe a : listeFac) {
     			a.setClient(c.getClient());
				a.setTypeAl("FactureClient"); 
				a.setDateEnregAl(date);
				a.setFacture(f);;
				a.setDateVraiAl(prefixe);
				em.persist(a);
			}
		}
        
     	f.setArticles(listeFac);
     	f.setTypeFac("FactureClient");
     	f.setCategorieFacture("FactureDirecte");
     	f.setClient(cl);
     	f.setModePaiement(m);
        Collection<CommandeClient> coms = new ArrayList<CommandeClient>();
        coms.add(c) ; 
        f.setCommandes(coms);
        f.setTotalFac(c.getTotalComCl());
        f.setTotaltvaFac(c.getTotalComCl()/5);
     	f.setTotalttcFac(c.getTotalComCl() + c.getTotalComCl()/5);
        CashWordConverter.doubleConvert(c.getTotalComCl() + c.getTotalComCl()/5).concat(" dirhams") ;
        Collection<Facture> fact = cl.getFactures() ;
     	fact.add(f) ;
     	cl.setFactures(fact);
		f.setDateFac(date);
     	f.setTotalString(CashWordConverter.doubleConvert(c.getTotalComCl() + c.getTotalComCl()/5).concat(" dirhams"));
     	
		f.setDateVraiFac(prefixe);
		f.setEtatFac("reglée");
		c.setEtatComCl("reglée");
		em.persist(cl);
		em.persist(c);
		em.persist(f);
		em.persist(m);
		
		pdfFacture(f.getIdFac()) ;
	}
	
	
	@Override
	public void addFactureDirecteB(Facture f , String nomMode , String numCompte , String nomBanque) {

		CommandeClient c = getLastCommandeClient() ;
		Long idCom = c.getIdComCl() ;
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		//CommandeClient c = em.find(CommandeClient.class, idCom) ;
		Client cl = c.getClient() ;
		
		Collection<ModePaiement> liste5 = cl.getModePaiements();
		ModePaiement m = new CompteBancaire("Compte Bancaire", numCompte, nomBanque) ;
		liste5.add(m) ;
		cl.setModePaiements(liste5);
		m.setClient(cl);
		
		Collection<ArticleListe> liste = c.getArticles() ;
		Collection<ArticleListe> listeFac = new ArrayList<ArticleListe>();
		listeFac.addAll(liste) ;
		
     	if(listeFac!=null) {
			for(ArticleListe a : listeFac) {
     			a.setClient(c.getClient());
				a.setTypeAl("FactureClient"); 
				a.setDateEnregAl(date);
				a.setFacture(f);;
				a.setDateVraiAl(prefixe);
				em.persist(a);
			}
		}
        
     	f.setArticles(listeFac);
     	f.setTypeFac("FactureClient");
     	f.setCategorieFacture("FactureDirecte");
     	f.setClient(cl);
     	f.setModePaiement(m);
        Collection<CommandeClient> coms = new ArrayList<CommandeClient>();
        coms.add(c) ; 
        f.setCommandes(coms);
        f.setTotalFac(c.getTotalComCl());
        f.setTotaltvaFac(c.getTotalComCl()/5);
     	f.setTotalttcFac(c.getTotalComCl() + c.getTotalComCl()/5);
        CashWordConverter.doubleConvert(c.getTotalComCl() + c.getTotalComCl()/5).concat(" dirhams") ;
        Collection<Facture> fact = cl.getFactures() ;
     	fact.add(f) ;
     	cl.setFactures(fact);
		f.setDateFac(date);
     	f.setTotalString(CashWordConverter.doubleConvert(c.getTotalComCl() + c.getTotalComCl()/5).concat(" dirhams"));
     	
		f.setDateVraiFac(prefixe);
		f.setEtatFac("reglée");
		c.setEtatComCl("reglée");
		em.persist(cl);
		em.persist(c);
		em.persist(f);
		em.persist(m);
		
		pdfFacture(f.getIdFac()) ;
	}
	


	@Override
	public List<ArticleListe> getArticlesForFacture(Long idFacture) {
		Query req = em.createQuery("select a from ArticleListe a where a.facture.idFac =:x ");
		req.setParameter("x",idFacture) ;
		return req.getResultList();
	}
	
	@Override
	public List<Facture> getFactureClient() {
		String y ="FactureClient" ;
		Query req = em.createQuery("select f from Facture f where f.typeFac like :x order by f.idFac desc");
		req.setParameter("x",y) ;
		return req.getResultList();
	}


	@Override
	public List<Facture> getFactureDirecte() {
		String y ="FactureDirecte" ;
		Query req = em.createQuery("select f from Facture f where f.categorieFacture like :x order by f.idFac desc");
		req.setParameter("x",y) ;
		return req.getResultList();
	}


	@Override
	public List<Facture> getFactureAccompte() {
		String y ="FactureAccompte" ;
		Query req = em.createQuery("select f from Facture f where f.categorieFacture like :x order by f.idFac desc");
		req.setParameter("x",y) ;
		return req.getResultList();
	}


	@Override
	public List<ModePaiement> getModePaiementByClient(Long idCl) {
		Query req = em.createQuery("select m from ModePaiement m where m.client.idCl =:x order by m.idMode desc");
		req.setParameter("x",idCl) ;
		return req.getResultList();
	}


	@Override
	public double updatePrixCommande() {
		CommandeClient c = getLastCommandeClient() ;
		Collection<ArticleListe> liste = c.getArticles() ;
		double totale = 0 ;
		if(liste!=null) {
			for(ArticleListe a : liste) {
     			totale = totale + a.getPrixUnitAl()*a.getQuantiteAl() ;
			}
		}
		
		c.setTotalComCl(totale);
		em.persist(c);
		return totale ;
	}


	@Override
	public Avoir getLastAvoirClient() {
		Query req = em.createQuery("select a from Avoir a order by a.idAv desc ");
		return (Avoir) req.getResultList().get(0);
	}


	@Override
	public Avoir addAvoirClient(Avoir a, Long idCl , double prixTotalAvoir) {
		Client cl = em.find(Client.class, idCl) ;
		a.setClient(cl);
		a.setTotalAv(prixTotalAvoir);
		a.setTypeAv("AvoirClient");
		Collection<Avoir> liste =  cl.getAvoirs();
		liste.add(a);
		cl.setAvoirs(liste);
		em.persist(a);
		em.persist(cl);
		
		return a ;
	}


	@Override
	public ArticleListe addArticleToAvoir(ArticleListe a, Long idArt) {
		Avoir av = getLastAvoirClient();
		Article ar = em.find(Article.class, idArt) ;
		ar.setStockreel(ar.getStockreel()+1);
		Collection<ArticleListe> listear = ar.getArticles() ;
		listear.add(a) ;
		a.setArticle(ar);
		em.persist(ar);
		Client cl  = av.getClient() ;
		a.setClient(cl);
		a.setAvoir(av);
		a.setTypeAl("Avoir Client");
		Collection<ArticleListe> liste = av.getArticles();
		liste.add(a);
		av.setArticles(liste);
		em.persist(av);
		em.persist(a);
		return a ;
	}


	@Override
	public List<ArticleListe> getArticlesForAvoir(Long idAvoir) {
		Query req = em.createQuery("select a from ArticleListe a where a.avoir.idAv =:x ");
		req.setParameter("x",idAvoir) ;
		return req.getResultList();
	}


	@Override
	public CommandeFournisseur addCommandeFournisseur(CommandeFournisseur c, Long idFourn ,  double prixTotalCommande) {
		Fournisseur f = em.find(Fournisseur.class, idFourn) ;
		c.setFournisseur(f);
		c.setEtatComFour("Non reglée");
		c.setTotalFour(prixTotalCommande);
		Collection<CommandeFournisseur> liste =  f.getCommandeFours();
		liste.add(c);
		f.setCommandeFours(liste);
		em.persist(c);
		em.persist(f);
		
		return c ;
	}


	@Override
	public ArticleListe addArticleToBonCommandeFourn(ArticleListe a, Long idArt) {
		CommandeFournisseur c = getLastCommandeFour();
		Article ar = em.find(Article.class, idArt) ;
		Collection<ArticleListe> listear = ar.getArticles() ;
		listear.add(a) ;
		a.setArticle(ar);
		em.persist(ar);
		//double totale = c.getTotalFour() + a.getPrixUnitAl()*a.getQuantiteAl() ;
		//c.setTotalFour(totale);
		Fournisseur f  = c.getFournisseur() ;
		a.setFournisseur(f);
		a.setCommandeFournisseur(c);
		a.setTypeAl("Commande Fournisseur");
		Collection<ArticleListe> liste = c.getArticles();
		liste.add(a);
		c.setArticles(liste);
		em.persist(c);
		em.persist(a);
		return a ;
	}


	@Override
	public Fournisseur addFournisseur(Fournisseur f) {
		em.persist(f);
		return f ;
	}


	@Override
	public CommandeFournisseur getLastCommandeFour() {
		Query req = em.createQuery("select c from CommandeFournisseur c order by c.idFour desc ");
		return (CommandeFournisseur) req.getResultList().get(0);
	}


	@Override
	public Avoir getLastAvoirFournisseur() {
		Query req = em.createQuery("select a from Avoir a order by a.idAv desc ");
		return (Avoir) req.getResultList().get(0);
	}


	@Override
	public Avoir addAvoirFournisseur(Avoir a, Long idFourn , double prixTotalAvoir) {
		Fournisseur f = em.find(Fournisseur.class, idFourn) ;
		a.setFournisseur(f);
		a.setTypeAv("AvoirFournisseur");
		a.setTotalAv(prixTotalAvoir);
		Collection<Avoir> liste =  f.getAvoirs();
		liste.add(a);
		f.setAvoirs(liste);
		em.persist(a);
		em.persist(f);
		
		return a ;
	}


	@Override
	public ArticleListe addArticleToAvoirFourn(ArticleListe a, Long idArt) {
		Avoir av = getLastAvoirFournisseur();
		Article ar = em.find(Article.class, idArt) ;
		ar.setStockreel(ar.getStockreel()-1);
		Collection<ArticleListe> listear = ar.getArticles() ;
		listear.add(a) ;
		a.setArticle(ar);
		em.persist(ar);
		double totale = av.getTotalAv() + a.getPrixUnitAl()*a.getQuantiteAl() ;
		av.setTotalAv(totale);
		Fournisseur f  = av.getFournisseur() ;
		a.setFournisseur(f);;
		a.setAvoir(av);
		a.setTypeAl("Avoir Fournisseur");
		Collection<ArticleListe> liste = av.getArticles();
		liste.add(a);
		av.setArticles(liste);
		em.persist(av);
		em.persist(a);
		return a ;
	}


	@Override
	public List<ArticleListe> getArticlesForAvoirFourn(Long idAvoir) {
		Query req = em.createQuery("select a from ArticleListe a where a.avoir.idAv =:x ");
		req.setParameter("x",idAvoir) ;
		return req.getResultList();
	}


	@Override
	public Fournisseur addFournisseurRepresentant(Fournisseur f, Representant r) {
		em.persist(f);
		if(r!=null) {
		r.setFournisseur(f);
		em.persist(r);
		List<Representant> liste = new ArrayList<Representant>();
		liste.add(r) ;
		f.setRepresentants(liste);
		em.refresh(f);
		em.persist(r);
		
		}
		
		
		return f;
	}


	@Override
	public List<ArticleListe> getArticlesForCommandeFour(Long idComF) {
		Query req = em.createQuery("select a from ArticleListe a where a.commandeFournisseur.idFour =:x ");
		req.setParameter("x",idComF) ;
		return req.getResultList();
	}


	@Override
	public List<ArticleListe> getArticlesForBonLivs(Long idBl) {
		Query req = em.createQuery("select a from ArticleListe a where a.bonLivraison.idBl =:x ");
		req.setParameter("x",idBl) ;
		return req.getResultList();
	}
 

	@Override
	public List<CommandeClient> getCommandeByNom(String motCle , int position , int nbre) {
		Query req = em.createQuery("select c from CommandeClient c where (c.client.nomSoc like :x) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCommandes() {
		Query req = em.createQuery("select count(c) from CommandeClient c ");
		return (Long) req.getResultList().get(0);
	}
	
	@Override
	public List<CommandeClient> allCommandesClient() {
		Query req = em.createQuery("select c from CommandeClient c order by c.idComCl desc");
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesMC(String motCle) {
		Query req = em.createQuery("select count(c) from CommandeClient c where (c.client.nomSoc like :x) ");
		req.setParameter("x","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<BonLivraison> getBLClientByNom(String motCle, int position, int nbre) {
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.client.nomSoc like :x) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLClient() {
		Query req = em.createQuery("select count(bl) from BonLivraison bl ");
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<BonLivraison> allBLClient() {
		Query req = em.createQuery("select bl from BonLivraison bl order by bl.idBl desc");
		return req.getResultList();
	}


	@Override
	public long getNombreBLMC(String motCle) {
		Query req = em.createQuery("select count(bl) from BonLivraison bl where (bl.client.nomSoc like :x) ");
		req.setParameter("x","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Facture> getFacClientByNom(String motCle, int position, int nbre) {
		Query req = em.createQuery("select f from Facture f where (f.client.nomSoc like :x) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacClient() {
		Query req = em.createQuery("select count(f) from Facture f ");
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Facture> allFacClient() {
		Query req = em.createQuery("select f from Facture f order by f.idFac desc");
		return req.getResultList();
	}


	@Override
	public long getNombreFacMC(String motCle) {
		Query req = em.createQuery("select count(f) from Facture f where (f.client.nomSoc like :x) ");
		req.setParameter("x","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	


	@Override
	public List<Avoir> getAllAvoirClient(int position, int nbre) {
		String y = "AvoirClient" ;
		Query req = em.createQuery("select a from Avoir a where a.typeAv like :x order by a.idAv desc");
		req.setParameter("x","%"+y+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<Avoir> getAvoirByNom(String motCle, int position, int nbre) {
		String y = "AvoirClient" ;
		Query req = em.createQuery("select a from Avoir a where ( a.typeAv like :x and a.client.nomSoc like :z ) order by a.idAv desc");
		req.setParameter("x","%"+y+"%") ;
		req.setParameter("z","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreAvoir() {
		String y = "AvoirClient" ;
		Query req = em.createQuery("select count(a) from Avoir a where  a.typeAv like :x ");
		req.setParameter("x","%"+y+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Avoir> allAvoirClient() {
		String y = "AvoirClient" ;
		Query req = em.createQuery("select a from Avoir a where ( a.typeAv like :x ) order by a.idAv desc");
		req.setParameter("x","%"+y+"%") ;
		return req.getResultList();
	}


	@Override
	public long getNombreAvoirMC(String motCle) {
		String y = "AvoirClient" ;
		Query req = em.createQuery("select count(a) from Avoir a where   ( a.typeAv like :x and a.client.nomSoc like :z ) ");
		req.setParameter("x","%"+y+"%") ;
		req.setParameter("z","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Client> getAllClients(int position, int nbre) {
		Query req = em.createQuery("select c from Client c order by c.id desc");
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<Client> getClientByNom(String motCle, int position, int nbre) {
		Query req = em.createQuery("select c from Client c where (c.nomSoc like :x) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreClient() {
		Query req = em.createQuery("select count(c) from Client c ");
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Client> allClient() {
		Query req = em.createQuery("select c from Client c order by c.idCl desc");
		return req.getResultList();
	}


	@Override
	public long getClientMC(String motCle) {
		Query req = em.createQuery("select count(c) from Client c where (c.nomSoc like :x) ");
		req.setParameter("x","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<ModePaiement> getMPByNom(String motCle, int position, int nbre) {
		Query req = em.createQuery("select mp from ModePaiement mp where (mp.client.nomSoc like :x) order by mp.idMode desc") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreMP() {
		Query req = em.createQuery("select count(mp) from ModePaiement mp ");
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<ModePaiement> allMPClient() {
		Query req = em.createQuery("select mp from ModePaiement mp order by mp.idMode desc ");
		return req.getResultList();
	}


	@Override
	public long getNombreMPMC(String motCle) {
		Query req = em.createQuery("select count(mp) from ModePaiement mp where (mp.nomMode like :x) ");
		req.setParameter("x","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Article> getArticles(int position, int nbre) {
		Query req = em.createQuery("select a from Article a order by a.idArt desc");
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<Article> getArticleByNom(String motCle, int position, int nbre) {
		Query req = em.createQuery("select a from Article a where a.designation like :x order by a.idArt desc ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreArticle() {
		Query req = em.createQuery("select count(a) from Article a ");
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Article> allArticleClient() {
		Query req = em.createQuery("select a from Article a order by a.idArt desc");
		return req.getResultList();
	}


	@Override
	public long getNombreArticleMC(String motCle) {
		Query req = em.createQuery("select count(a) from Article a where (a.designation like :x) ");
		req.setParameter("x","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Categorie> getCategories(int position, int nbre) {
		Query req = em.createQuery("select c from Categorie c order by c.idCat desc");
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<Categorie> getCategorieByNom(String motCle, int position, int nbre) {
		Query req = em.createQuery("select c from Categorie c where c.nomCat like :x order by c.idCat desc ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCategorie() {
		Query req = em.createQuery("select count(c) from Categorie c ");
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Categorie> allCategorie() {
		Query req = em.createQuery("select c from Categorie c order by c.idACat desc");
		return req.getResultList();
	}


	@Override
	public long getNombreCategorieMC(String motCle) {
		Query req = em.createQuery("select count(c) from Categorie c where (c.nomCat like :x) ");
		req.setParameter("x","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public Representant getLastRepresentantClient(Long idCl) {
		Query req = em.createQuery("select r from Representant r where r.client.idCl =:x order by r.id desc") ;
		req.setParameter("x",idCl) ;
		return (Representant) req.getResultList().get(0); 
	}


	@Override
	public List<Avoir> getAvoirFournisseur(int position, int nbre) {
		String y = "AvoirFournisseur" ;
		Query req = em.createQuery("select a from Avoir a where a.typeAv like :x order by a.idAv desc");
		req.setParameter("x","%"+y+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<Avoir> getAvoirByNomF(String motCle, int position, int nbre) {
		String y = "AvoirFournisseur" ;
		Query req = em.createQuery("select a from Avoir a where ( a.typeAv like :x and a.fournisseur.nomSocF like :z ) order by a.idAv desc");
		req.setParameter("x","%"+y+"%") ;
		req.setParameter("z","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreAvoirF() {
		String y = "AvoirFournisseur" ;
		Query req = em.createQuery("select count(a) from Avoir a where  a.typeAv like :x ");
		req.setParameter("x","%"+y+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public long getNombreAvoirMCF(String motCle) {
		String y = "AvoirFournisseur" ;
		Query req = em.createQuery("select count(a) from Avoir a where   ( a.typeAv like :x and a.fournisseur.nomSocF like :z ) ");
		req.setParameter("x","%"+y+"%") ;
		req.setParameter("z","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<ArticleListe> statArticleClient(Long idArt) {
		String y = "BonLivraisonClient" ;
		String z = "statLivre" ;
		Query req = em.createQuery("select a  from ArticleListe a where a.article.idArt =:x and  a.statLivre like :z and a.typeAl like :y and "
				+ " ( a.client.idCl in ( select DISTINCT(ar.client.idCl) from ArticleListe ar "
				+ "where (ar.article.idArt =:x and  ar.statLivre like :z and ar.typeAl like :y) )  ) order by a.nbreLivre DESC  ");
		//Query req = em.createQuery("select c  from Client c where (a.article.idArt =:x and a.typeAl like :y) group by a.client.idCl  ");
		req.setParameter("x",idArt) ;
		req.setParameter("y","%"+y+"%") ; 
		req.setParameter("z","%"+z+"%") ;
		req.setFirstResult(0) ;
		req.setMaxResults(5) ;
		return req.getResultList() ;
	}


	@Override
	public List<Long> longs(Long idArt) {
		String y = "Commande Client" ;
		String z = "statLivre" ;
		Query req = em.createQuery("select DISTINCT(ar.client.idCl) from ArticleListe ar "
				+ "where (ar.article.idArt =:x and ar.typeAl like :y) order by ar.nbreLivre DESC ))  ");
		req.setParameter("x",idArt) ;
		req.setParameter("y","%"+y+"%") ; 
		req.setFirstResult(0) ;
		req.setMaxResults(5) ;
		return req.getResultList() ;
	}


	@Override
	public List<StatArticle> statMois() {
		Query req = em.createQuery("select s from StatArticle s") ;
		return req.getResultList();
	}


	@Override
	public StatArticle statRecherche(Long idArt) {
		Query req = em.createQuery("select s from StatArticle s where s.article.idArt =:x ") ;
		req.setParameter("x",idArt) ;
		return (StatArticle) req.getResultList().get(0);
	}


	@Override
	public List<CommandeClient> getAllBonCommandesTraitees(int position, int nbre) {
		String x ="reglée" ;
		Query req = em.createQuery("select c from CommandeClient c where c.etatComCl like :x order by c.idComCl desc");
		req.setParameter("x",x) ; 
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<CommandeClient> getCommandeByNomTraitees(String motCle , int position , int nbre) {
		String y ="reglée" ;
		Query req = em.createQuery("select c from CommandeClient c where (c.client.nomSoc like :x) and (c.etatComCl like :y)  order by c.idComCl desc") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y",y) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesTraitees() {
		String y ="reglée" ;
		Query req = em.createQuery("select count(c) from CommandeClient c where c.etatComCl like :y ");
		req.setParameter("y",y) ;
		return (Long) req.getResultList().get(0);
	}
	
	@Override
	public List<CommandeClient> allCommandesClientTraitees() {
		String y ="reglée" ;
		Query req = em.createQuery("select c from CommandeClient c where c.etatComCl like :y order by c.idComCl desc");
		req.setParameter("y",y) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesMCTraitees(String motCle) {
		String y ="reglée" ;
		Query req = em.createQuery("select count(c) from CommandeClient c where (c.client.nomSoc like :x) and (c.etatComCl like :y) ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y",y) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<CommandeClient> getAllBonCommandesNTraitees(int position, int nbre) {
		String x ="non reglée" ;
		Query req = em.createQuery("select c from CommandeClient c where c.etatComCl like :x order by c.idComCl desc");
		req.setParameter("x",x) ; 
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<CommandeClient> getCommandeByNomNTraitees(String motCle , int position , int nbre) {
		String y ="non reglée" ;
		Query req = em.createQuery("select c from CommandeClient c where (c.client.nomSoc like :x) and (c.etatComCl like :y) order by c.idComCl desc ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y",y) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesNTraitees() {
		String y ="non reglée" ;
		Query req = em.createQuery("select count(c) from CommandeClient c where c.etatComCl like :y ");
		req.setParameter("y",y) ;
		return (Long) req.getResultList().get(0);
	}
	
	@Override
	public List<CommandeClient> allCommandesClientNTraitees() {
		String y ="non reglée" ;
		Query req = em.createQuery("select c from CommandeClient c where c.etatComCl like :y order by c.idComCl desc");
		req.setParameter("y",y) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesMCNTraitees(String motCle) {
		String y ="non reglée" ;
		Query req = em.createQuery("select count(c) from CommandeClient c where (c.client.nomSoc like :x) and (c.etatComCl like :y) ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y",y) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<BonLivraison> getBLClientByNomTraitees(String motCle, int position, int nbre) {
		String y ="BonLivraisonClient" ;
		String z ="reglé" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.client.nomSoc like :x) and (bl.typeBl like :y) and (bl.etatBl like :z) order by bl.idBl desc ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLClientTraitees() {
		String y ="BonLivraisonClient" ;
		String z ="reglé" ;
		Query req = em.createQuery("select count(bl) from BonLivraison bl where (bl.typeBl like :y) and (bl.etatBl like :z)  ");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<BonLivraison> allBLClientTraitees() {
		String y ="BonLivraisonClient" ;
		String z ="reglé" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.typeBl like :y) and (bl.etatBl like :z) order by bl.idBl desc");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLMCTraitees(String motCle) {
		String y ="BonLivraisonClient" ;
		String z ="reglé" ;
		Query req = em.createQuery("select count(bl) from BonLivraison bl where (bl.client.nomSoc like :x) and (bl.typeBl like :y) and (bl.etatBl like :z) ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}
	
	@Override
	public List<BonLivraison> getBLClientByNomNTraitees(String motCle, int position, int nbre) {
		String y ="BonLivraisonClient" ;
		String z ="non reglé" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.client.nomSoc like :x) and (bl.typeBl like :y) and (bl.etatBl like :z) order by bl.idBl desc  ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLClientNTraitees() {
		String y ="BonLivraisonClient" ;
		String z ="non reglé" ;
		Query req = em.createQuery("select count(bl) from BonLivraison bl where bl.typeBl  like :y and (bl.etatBl like :z) ");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<BonLivraison> allBLClientNTraitees() {
		String y ="BonLivraisonClient" ;
		String z ="non reglé" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.typeBl  like :y) and (bl.etatBl like :z) order by bl.idBl desc");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLMCNTraitees(String motCle) {
		String y ="BonLivraisonClient" ;
		String z ="non reglé" ;
		Query req = em.createQuery("select count(bl) from BonLivraison bl where (bl.client.nomSoc like :x) and (bl.typeBl like :y) and (bl.etatBl like :z) ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Facture> getAllFacturesTraitees(int position, int nbre) {
		String z ="reglée" ;
		Query req = em.createQuery("select f from Facture f where f.etatFac like :z order by f.idFac desc");
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	
	}


	@Override
	public List<Facture> getFacClientByNomTraitees(String motCle, int position, int nbre) {
		String z ="reglée" ;
		Query req = em.createQuery("select f from Facture f where (f.etatFac like :z ) and (f.client.nomSoc like :x) order by f.idFac desc ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacClientTraitees() {
		String z ="reglée" ;
		Query req = em.createQuery("select count(f) from Facture f where f.etatFac like :z");
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Facture> allFacClientTraitees() {
		String z ="reglée" ;
		Query req = em.createQuery("select f from Facture f where f.etatFac like :z order by f.idFac desc");
		req.setParameter("z",z) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacMCTraitees(String motCle) {
		String z ="reglée" ;
		Query req = em.createQuery("select count(f) from Facture f where (f.client.nomSoc like :x) and f.etatFac like :z ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}
	
	@Override
	public List<Facture> getAllFacturesNTraitees(int position, int nbre) {
		String z ="non reglée" ;
		Query req = em.createQuery("select f from Facture f where f.etatFac like :z order by f.idFac desc");
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	
	}


	@Override
	public List<Facture> getFacClientByNomNTraitees(String motCle, int position, int nbre) {
		String z ="non reglée" ;
		Query req = em.createQuery("select f from Facture f where f.etatFac like :z and (f.client.nomSoc like :x) order by f.idFac desc ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacClientNTraitees() {
		String z ="non reglée" ;
		Query req = em.createQuery("select count(f) from Facture f where f.etatFac like :z");
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Facture> allFacClientNTraitees() {
		String z ="non reglée" ;
		Query req = em.createQuery("select f from Facture f where f.etatFac like :z order by f.idFac desc");
		req.setParameter("z",z) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacMCNTraitees(String motCle) {
		String z ="non reglée" ;
		Query req = em.createQuery("select count(f) from Facture f where (f.client.nomSoc like :x) and f.etatFac like :z ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<BonLivraison> getAllBLTClient(int position, int nbre) {
		String y ="BonLivraisonClient" ;
		String z ="reglé" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.typeBl like :y) and (bl.etatBl like :z) order by bl.idBl desc ") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<BonLivraison> getAllBLNTClient(int position, int nbre) {
		String y ="BonLivraisonClient" ;
		String z ="non reglé" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.typeBl like :y) and (bl.etatBl like :z) order by bl.idBl desc ") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}
	
	
	
	//FOURNISSEUR
	
	
	@Override
	public List<CommandeFournisseur> getCommandeFByNom(String motCle, int position, int nbre) {
		Query req = em.createQuery("select c from CommandeFournisseur c where (c.fournisseur.nomSocF like :x) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesF() {
		Query req = em.createQuery("select count(c) from CommandeFournisseur c ");
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<CommandeFournisseur> allCommandesF() {
		Query req = em.createQuery("select c from CommandeFournisseur c order by c.idFour desc");
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesFMC(String motCle) {
		Query req = em.createQuery("select count(c) from CommandeFournisseur c where (c.fournisseur.nomSocF like :x) ");
		req.setParameter("x","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<CommandeFournisseur> getAllCommandeF(int position, int nbre) {
		Query req = em.createQuery("select f from Fournisseur f ");
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<CommandeFournisseur> getAllCommandeFT(int position, int nbre) {
		String x ="reglée" ;
		Query req = em.createQuery("select c from CommandeFournisseur c where c.etatComFour like :x ");
		req.setParameter("x",x) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<CommandeFournisseur> getCommandeFByNomT(String motCle, int position, int nbre) {
		String y ="reglée" ;
		Query req = em.createQuery("select c from CommandeFournisseur c where (c.fournisseur.nomSocF like :x) and (c.etatComMFour like :y) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y",y) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesFT() {
		String y ="reglée" ;
		Query req = em.createQuery("select count(c) from CommandeFournisseur c where c.etatComFour like :y ");
		req.setParameter("y",y) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<CommandeFournisseur> allCommandesFT() {
		String y ="reglée" ;
		Query req = em.createQuery("select c from CommandeFournisseur c where c.etatComFour like :y  order by c.idFour desc");
		req.setParameter("y",y) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesFMCT(String motCle) {
		String y ="reglée" ;
		Query req = em.createQuery("select count(c) from CommandeFournisseur c where (c.fournisseur.nomSocF like :x) and (c.etatComFour like :y)");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y",y) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<CommandeFournisseur> getAllCommandeFNT(int position, int nbre) {
		String x ="non reglée" ;
		Query req = em.createQuery("select c from CommandeFournisseur c where c.etatComFour like :x ");
		req.setParameter("x",x) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<CommandeFournisseur> getCommandeFByNomNT(String motCle, int position, int nbre) {
		String y ="non reglée" ;
		Query req = em.createQuery("select c from CommandeFournisseur c where (c.fournisseur.nomSocF like :x) and (c.etatComMFour like :y) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y",y) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesFNT() {
		String y ="non reglée" ;
		Query req = em.createQuery("select count(c) from CommandeFournisseur c where c.etatComFour like :y ");
		req.setParameter("y",y) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<CommandeFournisseur> allCommandesFNT() {
		String y ="non reglée" ;
		Query req = em.createQuery("select c from CommandeFournisseur c where c.etatComFour like :y  order by c.idFour desc");
		req.setParameter("y",y) ;
		return req.getResultList();
	}


	@Override
	public long getNombreCommandesFMCNT(String motCle) {
		String y ="non reglée" ;
		Query req = em.createQuery("select count(c) from CommandeFournisseur c where (c.fournisseur.nomSocF like :x) and (c.etatComFour like :y)");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y",y) ;
		return (Long) req.getResultList().get(0);
	}
	
	
	//FOURNISSEUR


	@Override
	public List<BonLivraison> getAllBLF(int position, int nbre) {
		String y ="BonLivraisonFournisseur" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.typeBl like :y)  ") ;
		req.setParameter("y","%"+y+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<BonLivraison> getBLFByNom(String motCle, int position, int nbre) {
		String y ="BonLivraisonFournisseur" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.fournisseur.nomSocF like :x) and (bl.typeBl like :y) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLF() {
		String y ="BonLivraisonFournisseur" ;
		Query req = em.createQuery("select count(bl) from BonLivraison bl where bl.typeBl like :y ");
		req.setParameter("y","%"+y+"%") ;
		return (Long) req.getResultList().get(0);
	}

	@Override
	public List<BonLivraison> allBLF() {
		String y ="BonLivraisonFournisseur" ;
		Query req = em.createQuery("select bl from BonLivraison bl where bl.typeBl like :y order by bl.idBl desc");
		req.setParameter("y","%"+y+"%") ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLFMC(String motCle) {
		String y ="BonLivraisonFournisseur" ;
		Query req = em.createQuery("select count(bl) from BonLivraison bl (bl.client.nomSoc like :x) and (bl.typeBl like :y) ");
		req.setParameter("x","%"+motCle+"%") ;   
		req.setParameter("y","%"+y+"%") ;
		return (Long) req.getResultList().get(0);
	}

	
	//bl FOURNISSEUR TRAITES

	
	@Override
	public List<BonLivraison> getAllBLFT(int position, int nbre) {
		String z ="reglé" ;
		String y ="BonLivraisonFournisseur" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.typeBl like :y) and (bl.etatBl like :z)  ") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<BonLivraison> getBLFByNomT(String motCle, int position, int nbre) {
		String z ="reglé" ;
		String y ="BonLivraisonFournisseur" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.client.nomSoc like :x) and (bl.typeBl like :y) and (bl.etatBl like :z) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLFT() {
		String y ="BonLivraisonFournisseur" ;
		String z ="reglé" ;
		Query req = em.createQuery("select count(bl) from BonLivraison bl where (bl.typeBl like :y) and (bl.etatBl like :z) ");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}

	@Override
	public List<BonLivraison> allBLFT() {
		String y ="BonLivraisonFournisseur" ;
		String z ="reglé" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.typeBl  like :y) and  (bl.etatBl like :z) order by bl.idBl desc");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLFMCT(String motCle) {
		String y ="BonLivraisonFournisseur" ;
		String z ="reglé" ;
		Query req = em.createQuery("select count(bl) from BonLivraison bl (bl.client.nomSoc like :x) and (bl.typeBl like :y) and  (bl.etatBl like :z) ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}
	
	
	
	//BL NON TRAITES
	
	
	@Override
	public List<BonLivraison> getAllBLFNT(int position, int nbre) {
		String z ="non reglé" ;
		String y ="BonLivraisonFournisseur" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.typeBl like :y) and (bl.etatBl like :z)  ") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<BonLivraison> getBLFByNomNT(String motCle, int position, int nbre) {
		String z ="non reglé" ;
		String y ="BonLivraisonFournisseur" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.client.nomSoc like :x) and (bl.typeBl like :y) and (bl.etatBl like :z) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLFNT() {
		String y ="BonLivraisonFournisseur" ;
		String z ="non reglé" ;
		Query req = em.createQuery("select count(bl) from BonLivraison bl where (bl.typeBl like :y) and (bl.etatBl like :z) ");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}

	@Override
	public List<BonLivraison> allBLFNT() {
		String y ="BonLivraisonFournisseur" ;
		String z ="non reglé" ;
		Query req = em.createQuery("select bl from BonLivraison bl where (bl.typeBl  like :y) and  (bl.etatBl like :z) order by bl.idBl desc");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return req.getResultList();
	}


	@Override
	public long getNombreBLFMCNT(String motCle) {
		String y ="BonLivraisonFournisseur" ;
		String z ="non reglé" ;
		Query req = em.createQuery("select count(bl) from BonLivraison bl (bl.client.nomSoc like :x) and (bl.typeBl like :y) and  (bl.etatBl like :z) ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}
	
	
	// Factures


	@Override
	public List<Facture> getAllFacturesF(int position, int nbre) {
		
		String y ="FactureFournisseur" ;
		Query req = em.createQuery("select f from Facture f where f.typeFac like :y order by f.idFac desc");
		req.setParameter("y","%"+y+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
		
	}


	@Override
	public List<Facture> getFacturesFByNom(String motCle, int position, int nbre) {
		String y ="FactureFournisseur" ;
		Query req = em.createQuery("select f from Facture f where (f.fournisseur.nomSoc like :x) and (f.typeFac like :y) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacturesF() {
		String y ="FactureFournisseur" ;
		Query req = em.createQuery("select count(f) from Facture f where (f.typeFac like :y) ");
		req.setParameter("y","%"+y+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Facture> allFacturesF() {
		String y ="FactureFournisseur" ;
		Query req = em.createQuery("select f from Facture f where (f.typeFac like :y) order by f.idFac desc");
		req.setParameter("y","%"+y+"%") ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacturesFMC(String motCle) {
		String y ="FactureFournisseur" ;
		Query req = em.createQuery("select count(f) from Facture f where (f.client.nomSoc like :x) and (f.typeFac like :y) ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		return (Long) req.getResultList().get(0);
	}


	//Factures traitées fournisseur
	
	@Override
	public List<Facture> getAllFacturesFT(int position, int nbre) {
		
		String y ="FactureFournisseur" ;
		String z ="reglée" ;
		Query req = em.createQuery("select f from Facture f where (f.typeFac like :y) and (f.etatFac like :z) order by f.idFac desc");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
		
	}


	@Override
	public List<Facture> getFacturesFByNomT(String motCle, int position, int nbre) {
		String y ="FactureFournisseur" ;
		String z ="reglée" ;
		Query req = em.createQuery("select f from Facture f where (f.fournisseur.nomSoc like :x) and (f.typeFac like :y) and (f.etatFac like :z) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacturesFT() {
		String y ="FactureFournisseur" ;
		String z ="reglée" ;
		Query req = em.createQuery("select count(f) from Facture f where (f.typeFac like :y) and (f.etatFac like :z) ");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Facture> allFacturesFT() {
		String y ="FactureFournisseur" ;
		String z ="reglée" ;
		Query req = em.createQuery("select f from Facture f where (f.typeFac like :y) and (f.etatFac like :z) order by f.idFac desc");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacturesFMCT(String motCle) {
		String y ="FactureFournisseur" ;
		String z ="reglée" ;
		Query req = em.createQuery("select count(f) from Facture f where (f.client.nomSoc like :x) and (f.typeFac like :y) and (f.etatFac like :z) ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}
	
	// non traitees fournisseur
	
	

	@Override
	public List<Facture> getAllFacturesFNT(int position, int nbre) {
		
		String y ="FactureFournisseur" ;
		String z ="non reglée" ;
		Query req = em.createQuery("select f from Facture f where (f.typeFac like :y) and (f.etatFac like :z) order by f.idFac desc");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
		
	}


	@Override
	public List<Facture> getFacturesFByNomNT(String motCle, int position, int nbre) {
		String y ="FactureFournisseur" ;
		String z ="non reglée" ;
		Query req = em.createQuery("select f from Facture f where (f.fournisseur.nomSoc like :x) and (f.typeFac like :y) and (f.etatFac like :z) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacturesFNT() {
		String y ="FactureFournisseur" ;
		String z ="non reglée" ;
		Query req = em.createQuery("select count(f) from Facture f where (f.typeFac like :y) and (f.etatFac like :z) ");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Facture> allFacturesFNT() {
		String y ="FactureFournisseur" ;
		String z ="non reglée" ;
		Query req = em.createQuery("select f from Facture f where (f.typeFac like :y) and (f.etatFac like :z) order by f.idFac desc");
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFacturesFMCNT(String motCle) {
		String y ="FactureFournisseur" ;
		String z ="non reglée" ;
		Query req = em.createQuery("select count(f) from Facture f where (f.client.nomSoc like :x) and (f.typeFac like :y) and (f.etatFac like :z) ");
		req.setParameter("x","%"+motCle+"%") ;
		req.setParameter("y","%"+y+"%") ;
		req.setParameter("z",z) ;
		return (Long) req.getResultList().get(0);
	}
	
	
	// Avoirs Fournisseurs


	@Override
	public List<Avoir> getAllAvoirsF(int position, int nbre) {
		String y ="AvoirFournisseur" ;
		Query req = em.createQuery("select a from Avoir a where (a.typeAv like :y) order by a.idAv desc");
		req.setParameter("y","%"+y+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<Avoir> getAvoirsFByNom(String motCle, int position, int nbre) {
		String y = "AvoirFournisseur" ;
		Query req = em.createQuery("select a from Avoir a where ( a.typeAv like :x and a.client.nomSoc like :z ) order by a.idAv desc");
		req.setParameter("x","%"+y+"%") ;
		req.setParameter("z","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreAvoirsF() {
		String y = "AvoirFournisseur" ;
		Query req = em.createQuery("select count(a) from Avoir a where  a.typeAv like :x ");
		req.setParameter("x","%"+y+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Avoir> allAvoirsF() {
		String y = "AvoirFournisseur" ;
		Query req = em.createQuery("select a from Avoir a  where  a.typeAv like :x  order by a.idAv desc");
		req.setParameter("x","%"+y+"%") ;
		return req.getResultList();
	}


	@Override
	public long getNombreAvoirsFMC(String motCle) {
		String y = "AvoirFournisseur" ;
		Query req = em.createQuery("select count(a) from Avoir a where   ( a.typeAv like :x and a.client.nomSoc like :z ) ");
		req.setParameter("x","%"+y+"%") ;
		req.setParameter("z","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Fournisseur> getAllFournisseurs(int position, int nbre) {
		Query req = em.createQuery("select f from Fournisseur f order by f.idF desc");
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public List<Fournisseur> getFournByNom(String motCle, int position, int nbre) {
		Query req = em.createQuery("select f from Fournisseur f where (f.nomSocF like :x) ") ;
		req.setParameter("x","%"+motCle+"%") ;
		req.setFirstResult(position) ;
		req.setMaxResults(nbre) ;
		return req.getResultList();
	}


	@Override
	public long getNombreFourn() {
		Query req = em.createQuery("select count(f) from Fournisseur f ");
		return (Long) req.getResultList().get(0);
	}


	@Override
	public List<Fournisseur> allFourn() {
		Query req = em.createQuery("select f from Fournisseur f order by f.idF desc");
		return req.getResultList();
	}


	@Override
	public long getFournMC(String motCle) {
		Query req = em.createQuery("select count(f) from Fournisseur f where (f.nomSocF like :x) ");
		req.setParameter("x","%"+motCle+"%") ;
		return (Long) req.getResultList().get(0);
	}


	@Override
	public Representant getLastRepresentantF(Long idF) {
		Query req = em.createQuery("select r from Representant r where r.fournisseur.idF =:x order by r.id desc") ;
		req.setParameter("x",idF) ;
		return (Representant) req.getResultList().get(0); 
	} 


	@Override
	public void updateCommande(Long id , String date) {
		CommandeClient c = em.find(CommandeClient.class, id) ;
		Date dateV = null ;
		try {
			dateV = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.setDateComCl(date);
		c.setDateVraiComCl(dateV);
		
		em.merge(c) ;
	}


	@Override
	public void updateCommandeF(Long id, String date) {
		CommandeFournisseur c = em.find(CommandeFournisseur.class, id) ;
		Date dateV = null ;
		try {
			dateV = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.setDateFour(date);
		c.setDateVraiFour(dateV);
		
		em.merge(c) ;
	}


	@Override
	public void updateBl(Long id, String date) {
		BonLivraison b = em.find(BonLivraison.class, id) ;
		Date dateV = null ;
		try {
			dateV = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		b.setDateBl(date);
		b.setDateVraiBl(dateV);
		
		em.merge(b) ;
	}


	@Override
	public void updateFacture(Long id, String date) {
		Facture f = em.find(Facture.class, id) ;
		Date dateV = null ;
		try {
			dateV = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f.setDateFac(date);
		f.setDateVraiFac(dateV);
		
		em.merge(f) ;
	}


	@Override
	public void addFactureAccompteF(Facture f, Long idBl) {
		Date prefixe = new Date() ;
	    SimpleDateFormat formatDateJour = new SimpleDateFormat("dd/MM/yyyy"); 
	    String date= formatDateJour.format(prefixe);
		BonLivraison b = em.find(BonLivraison.class, idBl) ;
		Fournisseur fo = b.getFournisseur();
		Collection<ArticleListe> liste = b.getArticlesBl() ;
		Collection<ArticleListe> listeFac= new ArrayList<ArticleListe>();
		listeFac.addAll(liste) ;
		
     	if(listeFac!=null) {
			for(ArticleListe a : listeFac) {
     			a.setClient(b.getClient());
				a.setDateEnregAl(date);
				a.setFacture(f);
				a.setDateVraiAl(prefixe);
				em.persist(a);
			}
		}
        
     	f.setArticles(listeFac);
     	Collection<Facture> facs = fo.getFactures();
     	facs.add(f) ;
     	fo.setFactures(facs);
     	f.setTypeFac("FactureFournisseur");
     	f.setEtatFac("non reglée");
     	f.setFournisseur(fo);
     	f.setCategorieFacture("FactureAccompte");
     	Collection<BonLivraison> bons = new ArrayList<BonLivraison>();
     	bons.add(b) ;
     	f.setBonLivraisons(bons);
     	b.setFacture(f);
     	b.setEtatBl("reglé");
     	f.setTotalFac(b.getTotalttcBl());
     	f.setDateFac(date);
     	f.setDateVraiFac(prefixe);
     	em.persist(fo);
		em.persist(b);
		em.persist(f);
	}


	@Override
	public void deleteFactureF(Long idF) {
		Long id = 0L ;
		Query req1 = em.createQuery("select a from ArticleListe a where a.facture.idFac =:x ");
		req1.setParameter("x",idF) ;
		List<ArticleListe> artListeFac= req1.getResultList();
		
		Facture fac=em.find(Facture.class, idF) ;
		
		Collection<BonLivraison> listeBonsCopie = fac.getBonLivraisons() ;
		Collection<BonLivraison> listeBons = new ArrayList<BonLivraison>();
		listeBons.addAll(listeBonsCopie) ;
		
		if(listeBons != null) {
			
			for(BonLivraison bonX : listeBons ) {
				
				id = bonX.getIdBl() ;
				
				BonLivraison bon1=em.find(BonLivraison.class, id) ;
				Collection<ArticleListe> artListeBon = bon1.getArticlesBl();
				Collection<ArticleListe> artListeBon2 = new ArrayList<ArticleListe>();
				artListeBon2.addAll(artListeBon) ;
				
				
				CommandeFournisseur com2=em.find(CommandeFournisseur.class, bonX.getCommandeFourn().getIdFour()) ;
				
				Collection<ArticleListe> artListeCopie = com2.getArticles();
				Collection<ArticleListe> artListe = new ArrayList<ArticleListe>();
				artListe.addAll(artListeCopie) ;
								
				if(artListe!=null) {

					for(ArticleListe a : artListe) {
						
						 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
						 Collection<ArticleListe> articles1 = arte.getArticles() ;
						 articles1.remove(a) ;
						 arte.setArticles(articles1);
				         em.persist(arte);
				         
				         CommandeFournisseur com=em.find(CommandeFournisseur.class, a.getCommandeFournisseur().getIdFour()) ;
						 Collection<ArticleListe> articles2 = com.getArticles() ;
						 articles2.remove(a) ;
						 com.setArticles(articles2);
				         em.persist(com);
						
						 em.remove(a);
						
					}
					
					
				}
				
				
				if(artListeBon!=null) {
					for(ArticleListe a : artListeBon) {
						
						 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
						 Collection<ArticleListe> articles1 = arte.getArticles() ;
						 articles1.remove(a) ;
						 arte.setArticles(articles1);
				         em.persist(arte);
				         
				         artListeBon2.remove(a) ;
				         bon1.setArticlesBl(artListeBon2);
				         em.persist(bon1);
						 em.remove(a);
						
					}
					
					
				}

				Fournisseur cl=em.find(Fournisseur.class, bonX.getFournisseur().getIdF()) ;
				Collection<CommandeFournisseur> listeCom = cl.getCommandeFours() ;
				Collection<BonLivraison> listeBl = cl.getBonLivs() ;
				listeCom.remove(com2) ;
				listeBl.remove(bonX) ;
				cl.setCommandeFours(listeCom);
				cl.setBonLivs(listeBl);
		        em.persist(cl);
				em.remove(em.find(CommandeFournisseur.class, bonX.getCommandeFourn().getIdFour()));
				em.remove(bon1);
				
			}
			
			
		}
		
		
	   if(artListeFac!=null) {
			for(ArticleListe a : artListeFac) {
				
				 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
				 Collection<ArticleListe> articles1 = arte.getArticles() ;
				 articles1.remove(a) ;
				 arte.setArticles(articles1);
		         em.persist(arte);
		         
		         Facture fac1=em.find(Facture.class, a.getFacture().getIdFac()) ;
				 Collection<ArticleListe> articles2 = fac1.getArticles() ;
				 articles2.remove(a) ;
				 fac1.setArticles(articles2);
		         em.persist(fac1);
				
				 em.remove(a);
				
			}
			
			
		}
		
		Fournisseur cl=em.find(Fournisseur.class, fac.getFournisseur().getIdF()) ;
		Collection<Facture> listeFacVrai = cl.getFactures() ;
		listeFacVrai.remove(fac) ;
		cl.setFactures(listeFacVrai);
        em.persist(cl);
		em.remove(em.find(Facture.class, idF));
	}
	
	
	
	@Override
	public void deleteBLF(Long id) {
		
		BonLivraison bon1=em.find(BonLivraison.class, id) ;
		Collection<ArticleListe> artListeBon = bon1.getArticlesBl();
		Collection<ArticleListe> artListeBon2 = new ArrayList<ArticleListe>();
		artListeBon2.addAll(artListeBon) ;
		
		CommandeFournisseur com2=em.find(CommandeFournisseur.class, bon1.getCommandeFourn().getIdFour()) ;
		Collection<ArticleListe> artListeCopie = com2.getArticles();
		Collection<ArticleListe> artListe = new ArrayList<ArticleListe>();
		artListe.addAll(artListeCopie) ;
		
		
		if(artListe!=null) {
			for(ArticleListe a : artListe) {
				
				 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
				 Collection<ArticleListe> articles1 = arte.getArticles() ;
				 articles1.remove(a) ;
				 arte.setArticles(articles1);
		         em.persist(arte);
		         
		         CommandeFournisseur com=em.find(CommandeFournisseur.class, a.getCommandeFournisseur().getIdFour()) ;
				 Collection<ArticleListe> articles2 = com.getArticles() ;
				 articles2.remove(a) ;
				 com.setArticles(articles2);
		         em.persist(com);
				
				 em.remove(a);
				
			}
			
			
		}
		
		
		if(artListeBon!=null) {
			for(ArticleListe a : artListeBon) {
				
				 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
				 Collection<ArticleListe> articles1 = arte.getArticles() ;
				 articles1.remove(a) ;
				 arte.setArticles(articles1);
		         em.persist(arte);
		         
		         artListeBon2.remove(a) ;
		         bon1.setArticlesBl(artListeBon2);
		         em.persist(bon1);
				 em.remove(a);
				
			}
			
			
		}

		Fournisseur cl=em.find(Fournisseur.class, bon1.getFournisseur().getIdF()) ;
		Collection<CommandeFournisseur> listeCom = cl.getCommandeFours() ;
		Collection<BonLivraison> listeBl = cl.getBonLivs() ;
		listeCom.remove(com2) ;
		listeBl.remove(bon1) ;
		cl.setCommandeFours(listeCom);
		cl.setBonLivs(listeBl);
        em.persist(cl);
		em.remove(em.find(CommandeFournisseur.class, bon1.getCommandeFourn().getIdFour()));
		em.remove(bon1);
		
	}
	
	@Override
	public List<ModePaiement> getModePaiesByF(Long idF) {
		Query req = em.createQuery("select m from ModePaiement m where m.fournisseur.idF =:x order by m.idMode desc");
		req.setParameter("x",idF) ;
		return req.getResultList();
	}
	
	@Override
	public void addModePautreF(AutrePaiement m , Fournisseur f) {
		Collection<ModePaiement> liste1 = f.getModePaiements() ;
		Collection<ModePaiement> listem = new ArrayList<ModePaiement>();
		if(liste1 !=null) {
		listem.addAll(liste1) ;
		}
		listem.add(m) ;
		f.setModePaiements(listem);
		m.setFournisseur(f);
		em.persist(f);
		em.persist(m);
	}
	
	
	@Override
	public void deleteAvoirF(Long id) {
		Query req = em.createQuery("select a from ArticleListe a where a.avoir.idAv =:x ");
		req.setParameter("x",id) ;
		List<ArticleListe> artListe = req.getResultList();
		
		if(artListe!=null) {
			for(ArticleListe a : artListe) {
				
				 Article arte=em.find(Article.class, a.getArticle().getIdArt()) ;
				 Collection<ArticleListe> articles1 = arte.getArticles() ;
				 articles1.remove(a) ;
				 arte.setArticles(articles1);
		         em.persist(arte);
		         
		         Avoir av = em.find(Avoir.class, a.getAvoir().getIdAv()) ;
				 Collection<ArticleListe> articles2 = av.getArticles() ;
				 articles2.remove(a) ;
				 av.setArticles(articles2);
		         em.persist(av);
				
				 em.remove(a);
				
			}
			
			
		}
		
		Avoir av1=em.find(Avoir.class, id) ;
		Fournisseur cl=em.find(Fournisseur.class, av1.getFournisseur().getIdF()) ;
		Collection<Avoir> listeAv = cl.getAvoirs() ;
		listeAv.remove(av1) ;
		cl.setAvoirs(listeAv);
        em.persist(cl);
		em.remove(em.find(Avoir.class, id));
		
	}
	
	
	@Override
	public void updateAv(Long id, String date) {
		Avoir b = em.find(Avoir.class, id) ;
		Date dateV = null ;
		try {
			dateV = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		b.setDateAv(date);
		b.setDateVraiAv(dateV);
		
		em.merge(b) ;
	}


	@Override
	public void updateCl(Client c) {
		// TODO Auto-generated method stub
		em.merge(c) ;
		
	}


	@Override
	public void updateFournisseur(Fournisseur f) {
		// TODO Auto-generated method stub
		em.merge(f) ;
		
	}


	@Override
	public void updateArticle(Article a) {
		// TODO Auto-generated method stub
		em.merge(a) ;
		
	}


	@Override
	public Connection connection() {
		
		Connection connection = null;

		return connection;
	}


	@Override
	public String pdfCommande(Long idComCl) {
		CommandeClient c = em.find(CommandeClient.class, idComCl) ;
		Collection<CommandeClient> coll2 = new ArrayList<CommandeClient>();
		coll2.add(c) ;
		
		Collection<Client> coll3 = new ArrayList<Client>();
        Client cl = c.getClient() ;
        coll3.add(cl) ;
		
		Collection<ArticleListe> coll = new ArrayList<ArticleListe>();
		coll.addAll(c.getArticles());
		
		try {
			JasperCompileManager.compileReportToFile("pdf/vraie.jrxml","pdf/vraie.jasper");
			JasperCompileManager.compileReportToFile("pdf/vraie2.jrxml","pdf/vraie2.jasper");
			System.out.println("Compilation faite") ;

		} catch (JRException e1) {
			e1.printStackTrace();
			System.out.println("Compilation nooonn faite") ;
		}
		
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	     Date concatener = new Date();
	     String ts = "src/main/resources/static/pdf/"+dateFormat.format(concatener);
	     String commande = ts.concat("CommandeClient.pdf") ;
	     String chemin = dateFormat.format(concatener).concat("CommandeClient.pdf");
	    
		
		 JRBeanCollectionDataSource beanColDataSource2 = new JRBeanCollectionDataSource(coll2);
		 
		 try {
		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("articles",coll) ;
		        params.put("client",coll3) ;
		       
		        JasperReport jasperReport = JasperCompileManager.compileReport("pdf/vrai.jrxml");
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource2);

		        JasperExportManager.exportReportToPdfFile(jasperPrint, commande);
		        System.out.println("rapport commande generer");
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println(e.getMessage());
		        System.out.println("commande non generer");
		    }
		 
		
		 c.setFichierComCl(chemin);
	     em.merge(c);
		 return commande ;
		
		
		
	}


	@Override
	public String pdfLivraison(Long idBl) {
		BonLivraison bl = em.find(BonLivraison.class, idBl) ;
		Collection<BonLivraison> coll2 = new ArrayList<BonLivraison>();
		coll2.add(bl) ;
		
		Collection<Client> coll3 = new ArrayList<Client>();
        Client cl = bl.getClient() ;
        coll3.add(cl) ;
		
		Collection<ArticleListe> coll = new ArrayList<ArticleListe>();
		coll.addAll(bl.getArticlesBl());
		
		try {
			JasperCompileManager.compileReportToFile("pdf/vraiebl.jrxml","pdf/vraiebl.jasper");
			JasperCompileManager.compileReportToFile("pdf/vraie2bl.jrxml","pdf/vraie2bl.jasper");
			System.out.println("Compilation faite") ;

		} catch (JRException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Compilation nooonn faite") ;
		}
		
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	     Date concatener = new Date();
	     String ts = "src/main/resources/static/pdf/"+dateFormat.format(concatener);
	     String livraison = ts.concat("LivraisonClient.pdf") ;
	     String chemin = (dateFormat.format(concatener)).concat("LivraisonClient.pdf");
		
		 JRBeanCollectionDataSource beanColDataSource2 = new JRBeanCollectionDataSource(coll2);
		 
		 try {
		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("articles",coll) ;
		        params.put("client",coll3) ;
		       
		        JasperReport jasperReport = JasperCompileManager.compileReport("pdf/vraibl.jrxml");
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource2);

		        JasperExportManager.exportReportToPdfFile(jasperPrint, livraison);
		        System.out.println("rapport livraison generer");
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println(e.getMessage());
		        System.out.println("livraison non generer");
		    }
		 
		 bl.setFichierBl(chemin);
	     em.merge(bl);

		 return livraison ;
		
	}


	@Override
	public String pdfFacture(Long idFac) {
		Facture fac = em.find(Facture.class, idFac) ;
		Collection<Facture> coll2 = new ArrayList<Facture>();
		coll2.add(fac) ;
		
		Collection<Client> coll3 = new ArrayList<Client>();
        Client cl = fac.getClient() ;
        coll3.add(cl) ;
		
		Collection<ArticleListe> coll = new ArrayList<ArticleListe>();
		coll.addAll(fac.getArticles());
		
		try {
			JasperCompileManager.compileReportToFile("pdf/vraief.jrxml","pdf/vraief.jasper");
			JasperCompileManager.compileReportToFile("pdf/vraie2f.jrxml","pdf/vraie2f.jasper");
			System.out.println("Compilation faite") ;

		} catch (JRException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Compilation nooonn faite") ;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	     Date concatener = new Date();
	     String ts = "src/main/resources/static/pdf/"+dateFormat.format(concatener);
	     String facture = ts.concat("FactureClient.pdf") ;
	     String chemin = (dateFormat.format(concatener)).concat("FactureClient.pdf");
		
		 JRBeanCollectionDataSource beanColDataSource2 = new JRBeanCollectionDataSource(coll2);
		 
		 try {
		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("articles",coll) ;
		        params.put("client",coll3) ;
		       
		        JasperReport jasperReport = JasperCompileManager.compileReport("pdf/vraif.jrxml");
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource2);

		        JasperExportManager.exportReportToPdfFile(jasperPrint, facture);
		        System.out.println("rapport facture generer");
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println(e.getMessage());
		        System.out.println("Facture non generer");
		    }
		 
		 fac.setFichierFac(chemin);
	     em.merge(fac);
		 return facture ;
	}


	@Override
	public CommandeClient getCommandeById(Long id) {
		// TODO Auto-generated method stub
		return em.find(CommandeClient.class, id);
	}


	@Override
	public BonLivraison getLivraisonById(Long id) {
		// TODO Auto-generated method stub
		return em.find(BonLivraison.class, id);
	}


	@Override
	public Facture getFactureById(Long id) {
		// TODO Auto-generated method stub
		return em.find(Facture.class, id);
	}


	@Override
	public ArticleListe getLastArticleListe(Long idArt, Long idCl) {
        String s = "statLivre" ;
      //  req[j] = em.createQuery("select ar2  from ArticleListe ar2 where (ar2.article.idArt =:x and ar2.client.idCl =:y and ar2.dateEnregAl like :z) order by ar2.idArtListe DESC  ");
        Query req = em.createQuery("select ar2  from ArticleListe ar2 where (ar2.article.idArt =:x and ar2.client.idCl =:y and ar2.statLivre like :s) order by ar2.idArtListe DESC  ");
        req.setParameter("x",idArt) ;
		req.setParameter("y",idCl) ;
		req.setParameter("s",s) ;
		if(req.getResultList().isEmpty() || req.getResultList().get(0) == null) {
			return null ;
		}
		else {
		return (ArticleListe) req.getResultList().get(0) ;
		}
	}


	@Override
	public List<ArticleListe> lesArticles(Long idCl) {
		 String y = "Article" ;
		 String z = "commandeClient" ;
		// Long m = 6L ;
	//	Query req = em.createQuery("select ar  from ArticleListe ar where ar.article.idArt in (select distinct(ar3.article.idArt)  from ArticleListe ar3 where ar3.client.idCl =:x order by ar3.idArtListe DESC )    )  ");		
		//Query req = em.createQuery("select ar  from ArticleListe ar where ar.article.idArt in (select distinct(ar3.article.idArt)  from ArticleListe ar3 where ar3.client.idCl =:x order by ar3.idArtListe DESC ) OR ar.article.idArt in (select distinct(ar4)  from ArticleListe ar4 where (ar4.typeArt like :y)  AND ar4.article.idArt not in (select distinct(ar5.article.idArt)  from ArticleListe ar5 where ar5.client.idCl =:x order by ar5.idArtListe DESC )   )  ");		
         
	/*	Query req = em.createQuery("select ar  from ArticleListe ar  where ( ar.client.idCl =:x and ar.typeAl like :y )"
				+ " OR ar.idArtListe in ( select idArtListe from articleListe ar2 where ar2.typeArt like :y and ar2.article.idArt not in (  select ar3  from ArticleListe  ar3 where ( ar3.client.idCl =:x and ar3.typeAl like :y )  ))");		
        */
		 
		// Query req = em.createQuery("select ar  from ArticleListe ar  where ( ar.client.idCl =:x and ar.typeAl like :z ) OR (ar.idArtListe in (select ar2.idArtListe from ArticleListe ar2 where (ar2.typeArt like :y) and (ar2.idArtListe not in ( select ar3.idArtListe  from ArticleListe ar3  where ( ar3.client.idCl =:x and ar3.typeAl like :z )  )) ))") ;
				
			
		// Query req = em.createQuery("select ar  from ArticleListe ar  where  (ar.idArtListe in (select ar2.idArtListe from ArticleListe ar2 where (ar2.typeArt like :y) and (ar2.article.idArt not in ( select ar3.article.idArt  from ArticleListe ar3  where ( ar3.client.idCl =:x and ar3.typeAl like :z )  )) ))") ;
			
	 Query req = em.createQuery("select ar  from ArticleListe ar  where (ar.idArtListe in  ( select ar4.idArtListe from ArticleListe ar4 where( ar4.client.idCl =:x and ar4.typeAl like :z ))) OR (ar.idArtListe in (select ar2.idArtListe from ArticleListe ar2 where (ar2.typeArt like :y) and (ar2.article.idArt not in ( select ar3.article.idArt  from ArticleListe ar3  where ( ar3.client.idCl =:x and ar3.typeAl like :z )  )) )) group by ar.designationAl  ") ;

		 
		// Query req = em.createQuery("select  ar from ArticleListe ar , ArticleListe ar5 where (ar.idArtListe in  ( select DISTINCT ar4.idArtListe from ArticleListe ar4 where( ar4.client.idCl =:x and ar4.typeAl like :z ))) group by ar.designationAl  ") ;

		
		req.setParameter("x",idCl) ;
	    req.setParameter("y",y) ;
	   // req.setParameter("m",m) ;
	    req.setParameter("z",z) ;
		return req.getResultList(); 
	}


	@Override
	public void addArticleListe(ArticleListe a) {
		em.persist(a);
		
	}


	@Override
	public List<ArticleListe> articlesComCl() {
		// TODO Auto-generated method stub
		String x = "Article" ;
		Query req = em.createQuery("select ar  from ArticleListe ar where ar.typeArt like :x") ;
		req.setParameter("x",x) ;
		return req.getResultList();
	}
	
	@Override
	public Article getArticleListeByNom(String nom) {
		Query req = em.createQuery("select a from Article a where a.designation like :x ") ;
		req.setParameter("x",nom) ;
		return (Article) req.getResultList().get(0);
	}


	@Override
	public List<Facture> getFacturesTClient(Long idCl) {
		String z ="reglée" ;
		Query req = em.createQuery("select f from Facture f where f.etatFac like :z and f.client.idCl =:x order by f.idFac desc");
		req.setParameter("z","%"+z+"%") ;
		req.setParameter("x",idCl) ;
		return req.getResultList();
	}


	@Override
	public List<Facture> getFacturesNTClient(Long idCl) {
		String z ="non reglée" ;
		Query req = em.createQuery("select f from Facture f where f.etatFac like :z and f.client.idCl =:x order by f.idFac desc");
		req.setParameter("z","%"+z+"%") ;
		req.setParameter("x",idCl) ;
		return req.getResultList();
	}


	@Override
	public List<Avoir> getAvoirsClient(Long idCl) {
		Query req = em.createQuery("select a from Avoir a where a.client.idCl =:x order by a.idAv desc");
		req.setParameter("x",idCl) ;
		return req.getResultList();
	}


	@Override
	public void releveCompte(Long idCl , String date) {
		// TODO Auto-generated method stub
		
	//	Long idCl = 17L ;
		Client monCl = getClientById(idCl) ;
		
        monCl.setDateReleve(date);
        
		
		Collection<Facture> Facs = monCl.getFactures() ;
		Collection<Avoir> Avs = monCl.getAvoirs() ;
		
		double totalRel = 0 ;
		
		  for(Facture fac : Facs) {
	        	totalRel = totalRel + fac.getTotalttcFac() ;
	        }
		  
		  for(Avoir av : Avs) {
	        	
			  totalRel = totalRel + av.getTotalAv() ;
	        }
		  
		  monCl.setTotalReleve(totalRel);
		
		
		
		//Collection<Facture> coll2 = new ArrayList<Facture>();
		//coll2.add(fac) ;
		
		
		Collection<Map<String , ?>>  collFac = new ArrayList<Map<String , ?>>() ;
        
        for(Facture fac : Facs) {
        	Map<String , Object> m = new HashMap<String, Object>() ;
        	m.put("idFac", fac.getIdFac()) ;
        	m.put("dateFac", fac.getDateFac()) ;
        	m.put("totalttcFac", fac.getTotalttcFac()) ;
        	if(fac.getPaiement()==null) {
        		fac.setPaiement("");
        	}
        	m.put("paiement", fac.getPaiement()) ;
        	collFac.add(m) ;
        	
        }
        
Collection<Map<String , ?>>  collAv = new ArrayList<Map<String , ?>>() ;
        
        for(Avoir av : Avs) {
        	Map<String , Object> m = new HashMap<String, Object>() ;
        	m.put("idFac", av.getIdAv()) ;
        	m.put("dateFac", av.getDateAv()) ;
        	m.put("totalttcFac", av.getTotalAv()) ;
        	m.put("paiement", "") ;
        	collFac.add(m) ;
        	
        }
		
		
		Collection<Client> collCl = new ArrayList<Client>();
        collCl.add(monCl) ;
		
		
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	     Date concatener = new Date();
	     String ts = "src/main/resources/static/pdf/"+dateFormat.format(concatener);
	     String releveCompteString = ts.concat("releveCompte.pdf") ;
	     String chemin = (dateFormat.format(concatener)).concat("releveCompte.pdf");
		
		 JRBeanCollectionDataSource beanColDataSource2 = new JRBeanCollectionDataSource(collCl);
		 
		 try {
		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("factures",collFac) ;
		      //  params.put("client",coll3) ;
		       
		        JasperReport jasperReport = JasperCompileManager.compileReport("pdf/releveCompte.jrxml");
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource2);

		        JasperExportManager.exportReportToPdfFile(jasperPrint, releveCompteString);
		        System.out.println("relevé compte generer");
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println(e.getMessage());
		        System.out.println("relevé compte non generer");
		    }
		 
		 
		
		 monCl.setReleveCompte(chemin);
		 em.persist(monCl);
		// fac.setFichierFac(chemin);
		
	}


	@Override
	public void listeInt(Long[] data) {
		
		 Collection<BonLivraison> collBl = new ArrayList<BonLivraison>();
		
		for(int i=0 ; i<= (data.length - 1) ; i++) {
			System.out.println("valeur = "+data[i]) ;
			BonLivraison b = em.find(BonLivraison.class, data[i]) ;
			collBl.add(b) ;
		}
		
		for(BonLivraison b : collBl) {
			System.out.println("La verif "+b.getIdBl()) ;
		}
		
		
		 
		 
		
	}


	@Override
	public String getSerialNumber(String drive) {
		String result = "";
	    try {
	      File file = File.createTempFile("realhowto",".vbs");
	      file.deleteOnExit();
	      FileWriter fw = new java.io.FileWriter(file);

	      String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
	                  +"Set colDrives = objFSO.Drives\n"
	                  +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
	                  +"Wscript.Echo objDrive.SerialNumber";  // see note
	      fw.write(vbs);
	      fw.close();
	      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
	      BufferedReader input =
	        new BufferedReader
	          (new InputStreamReader(p.getInputStream()));
	      String line;
	      while ((line = input.readLine()) != null) {
	         result += line;
	      }
	      input.close();
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	    return result.trim();
	}


	@Override
	public String getSerialNumber2() {
	
		String NEWLINE = System.getProperty("line.separator");
	    StringBuffer buffer = new StringBuffer();
	    try{

	      Process pb = new ProcessBuilder("cmd","/c", "vol").start();  
	      InputStream in = pb.getInputStream();  
	      BufferedReader br = new BufferedReader(new InputStreamReader(in));  
	      String line;  
	      while ((line = br.readLine()) != null) {  
	        buffer.append(line + NEWLINE);  
	      }
	    }
	    catch(Exception e){e.printStackTrace();
	    }
	    
	    String resultat = buffer.toString().substring(buffer.toString().length() - 11) ;
	    return resultat;
	
	}


	@Override
	public NumeroDisque addNumDisque(NumeroDisque n) {
		// TODO Auto-generated method stub
		em.persist(n);
		return n;
	}


	@Override
	public String verifSerie(String num) {
		// TODO Auto-generated method stub

		Query req = em.createQuery("select n from NumeroDisque n where n.numDisque like :x ") ;
		req.setParameter("x","%"+num+"%") ;
		//Query req = em.createQuery("select n from NumeroDisque n ") ;
		
		if(req.getResultList().isEmpty() || req.getResultList().size()==0 ) {
			
			System.out.println("dans 0 , le resultat est : "+req.getResultList()) ;
			return "0" ;
		}
		
		else {
			System.out.println("dans 1 , le resultat est : "+req.getResultList()) ;
			return "1" ;
		}

	}
	


}
