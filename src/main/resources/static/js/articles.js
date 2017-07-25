var app=angular.module("Articles",[]) ;
app.controller("ArticlesController",function($scope,$http){

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
			     alert($scope.dateComCl) ;

			     $http.get("/addCommandeClient?dateComCl="+$scope.dateComCl+"&idCl="+$scope.codeComCl)
			     .success(function(data){
			    	 
			    	 for (var i = 1; i < (arrayLength); i++) {
			    		 alert(i) ;
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
		

	

		
		
}) ;
