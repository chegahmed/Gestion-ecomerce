var app=angular.module("Facture",[]) ;
app.controller("FactureController",function($scope,$http){

	$scope.codeArt=[] ;
	$scope.desArt=[] ;
	$scope.qteArt=[] ;
	$scope.prixArt=[] ;
	$scope.dateComCl= null ;
	$scope.codeComCl=2 ;

	
	    
	$scope.designation = null ;
	$scope.etat = null ;
	$scope.stockmin = 0 ;
	$scope.stockmax = 0 ;
	$scope.stockreel = 0 ;
	$scope.prixachat = 0 ;
	$scope.prixvente = 0 ;
	$scope.idCat = 1 ;
	
	$scope.idComCl = 0 ;
	$scope.idArtCom = 3 ;
	$scope.articlesCommande=[] ;

	 $scope.clients=[] ;
		$http.get("/allclients")
		.success(function(data){
		 $scope.clients = data ;
}) ;
		  
		  
		  $scope.arts=[] ;
			$http.get("/allArticles")
			.success(function(data){
			 $scope.arts = data ;
	}) ;
		  

	    $scope.categories=[] ;
		$http.get("/allCategories")
		.success(function(data){
		 $scope.categories = data ;
}) ;
		
		$scope.articles=[] ;
		$http.get("/allArticles")
		.success(function(data){
		 $scope.articles = data ;
}) ;
		
		
		$scope.autoraticles=[] ;
		$http.get("/allArticles")
		.success(function(data){
		 $scope.autoarticles = data ;
		 
}) ;
		
		
		$scope.factures=[] ;
		$http.get("/allFactures")
		.success(function(data){
		 $scope.factures = data ;
}) ;
		
		$scope.commandesClient=[] ;
		$http.get("/allCommandesClient")
		.success(function(data){
		 $scope.commandesClient = data ;
}) ;
		
		$scope.bonLivraisonClient=[] ;
		$http.get("/allBonLivraisonClient")
		.success(function(data){
		 $scope.bonLivraisonClient = data ;
}) ;
		
		$scope.factureClient=[] ;
		$http.get("/getFactureClient")
		.success(function(data){
		 $scope.factureClient = data ;
}) ;
		

		$scope.ajouterArticle=function(){
			$http.get("/addArticle?designation="+$scope.designation+"&etat="+$scope.etat+
					"&stockmin="+$scope.stockmin+"&stockmax="+$scope.stockmax+"&stockreel="
					+$scope.stockreel+"&prixachat="+$scope.prixachat+"&prixvente="+$scope.prixvente+
					"&idCat="+$scope.idCat)
			.success(function(data){
				
				 $scope.clients=[] ;
					$http.get("/allclients")
					.success(function(data){
					 $scope.clients = data ;
			}) ;
				
				    $scope.categories=[] ;
					$http.get("/allCategories")
					.success(function(data){
					 $scope.categories = data ;
			}) ;
					
					$scope.articles=[] ;
					$http.get("/allArticles")
					.success(function(data){
					 $scope.articles = data ;
			}) ;
					
					$scope.factures=[] ;
					$http.get("/allFactures")
					.success(function(data){
					 $scope.factures = data ;
			}) ;
					
					$scope.commandesClient=[] ;
					$http.get("/allCommandesClient")
					.success(function(data){
					 $scope.commandesClient = data ;
			}) ;
				
				
				
			});
		} ;
		
		
		var Objet = function(idArt,designation){
			 this.idArt = idArt;
			 this.designation = designation;//ItemObject
			};

		
		
		
		$scope.ajouterCommande=function(){

			     var arrayLength = $scope.codeArt.length;

			     $http.get("/addCommandeClient?dateComCl="+$scope.dateComCl+"&idCl="+$scope.codeComCl)
			     .success(function(data){
			    	 
			    	 for (var i = 1; i < (arrayLength); i++) {
			    	 
			    	 $http.get("/addArticleToCommande?quantiteAl="+$scope.qteArt[i]+"&designationAl="+$scope.desArt[i]+"&prixUnitAl="+$scope.prixArt[i]+"&idArt="+$scope.codeArt[i])
						.success(function(data){
							
				           }) ;
			    	 }

						 $scope.clients=[] ;
							$http.get("/allclients")
							.success(function(data){
							 $scope.clients = data ;
					}) ;
						
						    $scope.categories=[] ;
							$http.get("/allCategories")
							.success(function(data){
							 $scope.categories = data ;
					}) ;
							
							$scope.articles=[] ;
							$http.get("/allArticles")
							.success(function(data){
							 $scope.articles = data ;
					}) ;
							
							$scope.factures=[] ;
							$http.get("/allFactures")
							.success(function(data){
							 $scope.factures = data ;
					}) ;
							
							$scope.commandesClient=[] ;
							$http.get("/allCommandesClient")
							.success(function(data){
							 $scope.commandesClient = data ;
					}) ;
							
							alert("CA MARCHE ENFIN") ;
						
						
						
					});

		} ;
		
		
		
		
		
	$scope.chargerArticles=function(){
		
		  $scope.clients=[] ;
			$http.get("/allclients")
			.success(function(data){
			 $scope.clients = data ;
	}) ;
		
		    $scope.categories=[] ;
			$http.get("/allCategories")
			.success(function(data){
			 $scope.categories = data ;
	}) ;
			
			$scope.articles=[] ;
			$http.get("/allArticles")
			.success(function(data){
			 $scope.articles = data ;
	}) ;
			
			$scope.factures=[] ;
			$http.get("/allFactures")
			.success(function(data){
			 $scope.factures = data ;
	}) ;
			
			$scope.commandesClient=[] ;
			$http.get("/allCommandesClient")
			.success(function(data){
			 $scope.commandesClient = data ;
	}) ;
			
			$scope.bonLivraisonClient=[] ;
			$http.get("/allBonLivraisonClient")
			.success(function(data){
			 $scope.bonLivraisonClient = data ;
	}) ;
			
			$scope.factureClient=[] ;
			$http.get("/getFactureClient")
			.success(function(data){
			 $scope.factureClient = data ;
	}) ;
		
			
}

		
		
		$scope.getArticlesCommande=function(idArtCom){
	
			$http.get("/getArticlesForCommandeClient?idComCl="+idArtCom)
			.success(function(data){ 
				alert(idArtCom) ;
				$scope.articlesCommande = data ;
				alert(data);
				$scope.chargerArticles() ;
			});

			
		} 
		
		
		

		$scope.getArticlesLivraison=function(idArtBl){
	
			$http.get("/getArticlesForLivraisonClient?idBl="+idArtBl)
			.success(function(data){ 
				alert(idArtBl) ;
				$scope.articlesLivraison = data ;
				alert(data);
				$scope.chargerArticles() ;
			});

			
		} 
		
		
		
		$scope.addBonLivraisonClient=function(idArtCom){
			
			$http.get("/addBonLivraisonClient?idComCl="+idArtCom)
			.success(function(data){ 
				alert(idArtCom) ;
				$scope.chargerArticles() ;
			});

			
		} 
		
		
		
        $scope.getArticlesFacture=function(idFac){
			
			$http.get("/getArticlesFacture?idFac="+idFac)
			.success(function(data){ 
				$scope.articlesFacture = [] ;
				$scope.articlesFacture = data ;
				$scope.chargerArticles() ;
			});

			
		} 
        
        
 $scope.addFactureAccompteClient=function(idFac){
			
			$http.get("/addFactureAccompteClient?idBl="+idFac)
			.success(function(data){ 
				
				$scope.chargerArticles() ;
			});

			
		} 
 
   
 $scope.paiementFacture=function(idFac , idCl , nomClient , montantFac ){
	    alert(nomClient) ;
	 $http.get("/getModePaiementClient?idCl="+idCl)
		.success(function(data){ 
			$scope.modes = [] ;
			$scope.modes = data ;
			$scope.nomClient = nomClient ;
			$scope.montantFac = montantFac ;
		});
			$scope.chargerArticles() ;
		
	} 
        
        

	

		
		
}) ;
