var app=angular.module("BonLivraison",[]) ;
app.controller("BonLivraisonController",function($scope,$http , $q){

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
		
			
}
	
	
	
	

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
		
		
		
		var addArticles = function(i){
			  $http.get("/addArticleToCommande?quantiteAl="+$scope.qteArt[i]+"&designationAl="+$scope.desArt[i]+"&prixUnitAl="+$scope.prixArt[i]+"&idArt="+$scope.codeArt[i])
			    .success(function(data) {

			     }) ;
			  alert("La numero "+i+" est faite") ; 
			  }

		
		
		
		$scope.ajouterCommande=function(){
			var arrayLength = $scope.codeArt.length;
			     $http.get("/addCommandeClient?dateComCl="+$scope.dateComCl+"&idCl="+$scope.codeComCl)
			     .success(function(data){   

			    	 for (var i = 1; i < (arrayLength); i++) {
				    	 
			    		       alert("La valeur de i est = "+i) ;
			    		       
			    		       addArticles(i) ;
				    	          }

			           });
			     
			         alert("Commande Ajoutée") ;
			         $scope.chargerArticles() ;

		} ;
		
		
		
	/*	$scope.ajouterArticlesToCommande=function(){

		     var arrayLength = $scope.codeArt.length;
		    	 
		    	 for (var i = 1; i < (arrayLength); i++) {
		    	 
		    	 $http.get("/addArticleToCommande?quantiteAl="+$scope.qteArt[i]+"&designationAl="+$scope.desArt[i]+"&prixUnitAl="+$scope.prixArt[i]+"&idArt="+$scope.codeArt[i])
					.success(function(data){
						
			           }) ;
		    	 }
		    $scope.chargerArticles() ;
         	alert("Articles Ajoutés") ;

	}*/
		
		
		
		
		
	

		
		
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
		
		
   $scope.addFactureDirecte=function(idComFac){
	   alert(idComFac) ;
	   
	   $http.get("/addFactureDirecte?idCom="+idComFac)
		.success(function(data){ 
			alert(idComFac) ;
			$scope.chargerArticles() ;
		});
	   
			
		} 
		

	

		
		
}) ;
