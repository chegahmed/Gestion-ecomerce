package com.entreprise;

import java.sql.Connection;




import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.Repository.IClientR;
import com.entreprise.entities.Article;
import com.entreprise.entities.ArticleListe;
import com.entreprise.entities.AutrePaiement;
import com.entreprise.entities.Avoir;
import com.entreprise.entities.BeanWithList;
import com.entreprise.entities.CommandeClient;
import com.entreprise.entities.BonLivraison;
import com.entreprise.entities.Categorie;
import com.entreprise.entities.Client;
import com.entreprise.entities.CommandeFournisseur;
import com.entreprise.entities.CompteBancaire;
import com.entreprise.entities.Facture;
import com.entreprise.entities.Fournisseur;
import com.entreprise.entities.PaiementCheque;
import com.entreprise.entities.Representant;
import com.entreprise.entities.User;
import com.entreprise.entities.CashWordConverter;
import com.entreprise.metier.IComMetier;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@ImportResource("classpath:applicationContext.xml")
@SpringBootApplication
public class ComProjectApplication { 

	public static void main(String[] args) {
		SpringApplication.run(ComProjectApplication.class, args);
		ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"}) ;

		IComMetier metier = (IComMetier) context.getBean("metier") ;
		         
		        
		    /*   Role r1 = metier.addRole(new Role("Administrateur", "rien")) ;
		      Role r2 =  metier.addRole(new Role("Assistant", "rien")) ;
		        
		      User u1 =  metier.addUser(new User("admin", "123", "Adamou", "Bachir"),1L) ;
		      metier.addRoleToUser(1L, 1L) ;*/
		      
		      /*

				metier.addCat(new Categorie("Ampoule"));
				metier.addCat(new Categorie("Cable"));
				metier.addCat(new Categorie("fusible"));
				
				Article m1 = new Article("grande Aampoule", "Actif", 20 , 300 , 150 , 300 , 335 ) ;
				Article m2 = new Article("grande Petite", "Actif", 20 , 300 , 150 , 300 , 335 ) ;
				Article m3 = new Article("grande Brillante", "inactif", 20 , 300 , 150 , 300 , 335 ) ;
				Article m4 = new Article("grande ronde", "Actif", 20 , 300 , 150 , 150 , 200 ) ;
				
				ArticleListe m5 = new ArticleListe("grande Aampoule", 80,"article") ;
				ArticleListe m6 = new ArticleListe("grande Petite", 180,"article") ;
				ArticleListe m7 = new ArticleListe("grande Brillante", 200,"article") ;
				ArticleListe m8 = new ArticleListe("grande ronde", 220,"article") ;
				
				
				
				metier.addArticleListe(m5);
				metier.addArticleListe(m6);
				metier.addArticleListe(m7);
				metier.addArticleListe(m8);
				
				
				metier.addArticle(m1, 1L);
				metier.addArticle(m2, 1L);
				metier.addArticle(m3, 2L);
				metier.addArticle(m4, 3L);
				
				//metier.addClient(new Client(nomSoc, famSoc, formeJur, tel1, tel2, fax, gsm1, gsm2, email1, email2, adresse, siteweb, seuil, totaldu, risque, etat, numPatente, numRegistre, numTva, numIce))
			    Client c1 =	metier.addClient(new Client("ingelec", "entreprise", "SARL", "0680452536","", "0585746536", "0525478962","", "contact@ingelec.com","", "4 rue des hopitaux casablanca", "www.ingelec.ma", 15000, 4000, "faible", "actif", "07PC25847", "0254856", "265565", "05248925"));
				metier.addRepresentantToClient(new Representant("Sani", "Casablanca"), c1.getIdCl());
				Client c2 = metier.addClient(new Client("RADEEJ", "Societe", "SA",  "0680452536","", "0585746536", "0525478962","", "contact@ingelec.com","", "4 rue des hopitaux casablanca", "www.ingelec.ma", 15000, 4000, "faible", "actif", "07PC25847", "0254856", "265565", "05248925"));
				metier.addRepresentantToClient(new Representant("Kadri", "Rabat"), c2.getIdCl());
				Client c3 = metier.addClient(new Client("Lydec", "entreprise", "SARL", "0680452536","", "0585746536", "0525478962","", "contact@ingelec.com","", "4 rue des hopitaux casablanca", "www.ingelec.ma", 15000, 4000, "faible", "actif", "07PC25847", "0254856", "265565", "05248925"));
				metier.addRepresentantToClient(new Representant("aminou", "meknes"), c3.getIdCl());
				
		
				metier.addModePautreCl(new AutrePaiement("Cheque"), 1l);
				metier.addModePautreCl(new AutrePaiement("Espece"), 1l);
				
				
				metier.addModePchequeCl(new PaiementCheque("Cheque", "02548MKG5884"), 2l);
				metier.addModePautreCl(new AutrePaiement("Espece"), 2l);
				metier.addModePbanqueCl(new CompteBancaire("Compte Bancaire", "068574155525", "BMCE"), 2l);
				
				metier.addModePautreCl(new AutrePaiement("Cheque"), 3l);   //mounia el koraichi 
				metier.addModePautreCl(new AutrePaiement("Espece"), 3l); 
				
				Fournisseur f1 = metier.addFour(new Fournisseur("four1","entreprise", "SA", "0680452536","", "0585746536", "0525478962","", "contact@ingelec.com","", "www.monfour1.com"));
				metier.addRepresentantToFourn(new Representant("Kadri", "Rabat"), f1.getIdF());
				Fournisseur f2 = metier.addFour(new Fournisseur("four2","societe", "SARL","0680452536","", "0585746536", "0525478962","", "contact@ingelec.com","", "www.monfour1.com"));
				metier.addRepresentantToFourn(new Representant("Kadri", "Rabat"), f2.getIdF());
				Fournisseur f3 = metier.addFour(new Fournisseur("four3","entreprise", "SA", "0680452536","", "0585746536", "0525478962","", "contact@ingelec.com","","www.monfour1.com"));
				metier.addRepresentantToFourn(new Representant("Kadri", "Rabat"), f3.getIdF());
				
			
				 
				List<ArticleListe> articles = new ArrayList<ArticleListe>();
				ArticleListe a = new ArticleListe("ampoule",50, 8, 200,m1) ;
				ArticleListe a1 = new ArticleListe("Lampe",50, 8, 200,m1) ;
				  
				articles.add(a) ;
				articles.add(a1) ;
				metier.addFacture(new Facture("FA00001", "05/02/2016","8H30", 5500, "infos"), articles , 1l);
							
		
				List<ArticleListe> articles2 = new ArrayList<ArticleListe>();
				ArticleListe a2 = new ArticleListe("ampoule Rouge",50, 8, 200,m2) ;
				ArticleListe a3 = new ArticleListe("Lampe Verte",50, 8, 200,m3) ;
				articles2.add(a2) ;
				articles2.add(a3) ;
				
		
				metier.addComFour(new CommandeFournisseur("CF0001", "02/05/2016", "16:18", 15000), articles2, 1L);
				
				List<ArticleListe> articles3 = new ArrayList<ArticleListe>();
				ArticleListe a4 = new ArticleListe("ampoule",50.3, 8, 200,m1) ;
				ArticleListe a5 = new ArticleListe("ampoule",50, 8, 200,m4) ;
				articles3.add(a4) ;
				articles3.add(a5) ; 
				metier.addBCommande(new CommandeClient("BC00005", 5000, "27/05/2016"), articles3, 1L); 
				//metier.addBCommande(new CommandeClient("BC00005", "27/05/2016"),articles3,1L) ;
				
				List<ArticleListe> articles4 = new ArrayList<ArticleListe>();
				ArticleListe a6 = new ArticleListe("ampoule",50, 8, 200,m1) ;
				ArticleListe a7 = new ArticleListe("ampoule",50, 8, 200,m2) ;
				  
				articles4.add(a6) ;
				articles4.add(a7) ;
				
				metier.addAvoirClient(new Avoir("AV0001", "06/05/2015", "16:18", 1500), articles4, 1L);
				
				
				List<ArticleListe>  articles5 = new ArrayList<ArticleListe>();
				ArticleListe a8 = new ArticleListe("ampoule",50, 8, 200,m1) ;
				ArticleListe a9 = new ArticleListe("ampoule",50, 8, 200,m3) ;
				  
				articles5.add(a8) ;
				articles5.add(a9) ;
				metier.addComFour(new CommandeFournisseur("CF001","15/12/2015", "18H16", 15000), articles5, 1L);
				
				List<ArticleListe> articles6 = new ArrayList<ArticleListe>();
				ArticleListe a10 = new ArticleListe("ampoule",50, 8, 200,m4) ;
				ArticleListe a11 = new ArticleListe("ampoule",50, 8, 200,m1) ;
				  
				articles6.add(a10) ; 
				articles6.add(a11) ;
				metier.addBLivrFourn(new BonLivraison("BL0001", "30/12/2014", "16H30",1600, 500, 400), articles6, 1L); 
				
		
				
				
				
				
				
			
				
				Collection<BeanWithList> coll = new ArrayList<BeanWithList>();

			    BeanWithList bean = new BeanWithList(Arrays.asList("London", "Paris"), 1);

			    coll.add(bean);

			    bean = new BeanWithList(Arrays.asList("London", "Madrid", "Moscow"), 2);
			    coll.add(bean);

			    bean = new BeanWithList(Arrays.asList("Rome"), 3);
			    coll.add(bean);
			 
				
				Collection<CommandeClient> coll2 = new ArrayList<CommandeClient>();
				
				Collection<ArticleListe> coll = new ArrayList<ArticleListe>();
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				coll.add(a) ;
				coll.add(a1) ;
				coll.add(a2) ;
				coll.add(a3) ;
				CommandeClient coms = metier.getCommandeById(1L) ;
				coms.setTotalString(CashWordConverter.doubleConvert(coms.getTotalComCl()).concat(" dirhams"));
                coll2.add(coms) ;
                
                Collection<Client> coll3 = new ArrayList<Client>();
                Client cl = coms.getClient() ;
                coll3.add(cl) ;
				
				for(ArticleListe p : coll) {
					System.out.println("la reference = "+p.getDesignationAl()) ;
				}
			
				
				try {
					JasperCompileManager.compileReportToFile("pdf/vraie.jrxml","pdf/vraie.jasper");
					JasperCompileManager.compileReportToFile("pdf/vraie2.jrxml","pdf/vraie2.jasper");
					System.out.println("Compilation faite") ;

				} catch (JRException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("Compilation nooonn faite") ;
				}
				
		    JRBeanCollectionDataSource beanColDataSource2 = new JRBeanCollectionDataSource(coll2);
		    JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(coll);  
		    JRBeanCollectionDataSource beanColDataSource3 = new JRBeanCollectionDataSource(coll3);
		    
		    Collection<JRBeanCollectionDataSource> finale = new ArrayList<JRBeanCollectionDataSource>();
		    finale.add(beanColDataSource) ;
		    finale.add(beanColDataSource2) ;
		    
			    try {
			        Map<String, Object> params = new HashMap<String, Object>();
			        params.put("articles",coll) ;
			        params.put("client",coll3) ;
			       
			        JasperReport jasperReport = JasperCompileManager.compileReport("pdf/vrai.jrxml");
			        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource2);

			        JasperExportManager.exportReportToPdfFile(jasperPrint, "w1.pdf");
			        System.out.println("rapport 1 generer");
			    } catch (Exception e) {
			        e.printStackTrace();
			        System.out.println(e.getMessage());
			        System.out.println("1 non generer");
			    }
			    
			    
		    
			    
			    try {
			        Map<String, Object> params2 = new HashMap<String, Object>();
			        //params.put("idComCl",coms.getIdComCl()) ;
			        
			        
			        JasperReport jasperReport = JasperCompileManager.compileReport("pdf/vraie.jrxml");
			        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params2, new JRBeanCollectionDataSource(coll));

			        JasperExportManager.exportReportToPdfFile(jasperPrint, "w2.pdf");
			        System.out.println(" rapport2 generer");
			    } catch (Exception e) {
			        e.printStackTrace();
			        System.out.println(e.getMessage());
			        System.out.println("2 non generer");
			    }
			    
			    try {
			        Map<String, Object> params3 = new HashMap<String, Object>();
			        //params.put("idComCl",coms.getIdComCl()) ;
			        
			        
			        JasperReport jasperReport = JasperCompileManager.compileReport("pdf/vraie2.jrxml");
			        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params3, new JRBeanCollectionDataSource(coll3));

			        JasperExportManager.exportReportToPdfFile(jasperPrint, "w3.pdf");
			        System.out.println(" rapport3 generer");
			    } catch (Exception e) {
			        e.printStackTrace();
			        System.out.println(e.getMessage());
			        System.out.println("3 non generer");
			    }
			    
			    System.out.println("prix === "+coms.getTotalString()) ;

				
				
				
				
				
				List<ArticleListe> liste = metier.getArticlesForCommande(1L) ;
				
				List<Map<String , ?>>  maps = new ArrayList<Map<String , ?>>() ;
		        
		        for(ArticleListe ar : liste) {
		        	Map<String , Object> m = new HashMap<String, Object>() ;
		        	m.put("idArtListe", ar.getIdArtListe()) ;
		        	m.put("designationAl", ar.getDesignationAl()) ;
		        	m.put("quantiteAl", ar.getQuantiteAl()) ;
		        	m.put("prixtotalAl", ar.getPrixtotalAl()) ;
		        	m.put("prixUnitAl", ar.getPrixUnitAl()) ;
		        	maps.add(m) ;
		        	
		        }
		        
		        
		        
		       JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(maps);
		        
		      Map parameters = new HashMap();
		    
			        String sourceName = "pdf/moun.jrxml" ;
			        try {
						JasperReport report = JasperCompileManager.compileReport(sourceName) ;
						JasperPrint fileReport = JasperFillManager.fillReport(report,null , beanColDataSource);
						//JasperPrint fileReport = JasperFillManager.fillReport(report, parametersMap, new JREmptyDataSource()) ;
						JasperExportManager.exportReportToPdfFile(fileReport, "o.pdf");
						System.out.println(" le rapport est GENERE") ;
						
			        } catch (JRException e) {
			        	System.out.println(" le rapport n'est pas GENERE") ;
						e.printStackTrace();
					}
			        
			        
				
				
			*/
				
		
		
		
		
		
		
		/*Long idCl = 17L ;
		Client monCl = metier.getClientById(idCl) ;
		
		Collection<Facture> collFac = monCl.getFactures() ;
	
		
		Collection<Client> collCl = new ArrayList<Client>();
        collCl.add(monCl) ;
		
		
		try {
			JasperCompileManager.compileReportToFile("pdf/releveCompte.jrxml","pdf/releveCompte.jasper");
			JasperCompileManager.compileReportToFile("pdf/sreleveCompte.jrxml","pdf/sreleveCompte.jasper");
			System.out.println("Compilation faite") ;

		} catch (JRException e1) {
			e1.printStackTrace();
			System.out.println("Compilation nooonn faite") ;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	     Date concatener = new Date();
	     String ts = "src/main/resources/static/pdf/"+dateFormat.format(concatener);
	     String releveCompteString = ts.concat("releveCompte.pdf") ;
	     String chemin = (dateFormat.format(concatener)).concat("releveCompte.pdf");
		
		 JRBeanCollectionDataSource beanColDataSource2 = new JRBeanCollectionDataSource(collCl);
		 
		 try {
		        Map<String, Object> params = new HashMap<String, Object>();
		        params.put("factures",collFac) ;
		       
		        JasperReport jasperReport = JasperCompileManager.compileReport("pdf/releveCompte.jrxml");
		        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource2);

		        JasperExportManager.exportReportToPdfFile(jasperPrint, releveCompteString);
		        System.out.println("relevé compte generer");
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println(e.getMessage());
		        System.out.println("relevé compte non generer");
		    }
		 
		 
		 System.out.println("le chemin est "+chemin) ;*/
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
