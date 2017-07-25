var app=angular.module("ajouterArticle",[]) ;
app.controller("ajouterArticleController",function($scope,$http){

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

	
	    $scope.categories=[] ;
		$http.get("/allCategories")
		.success(function(data){
		 $scope.categories = data ;
}) ;
		

		

		$scope.ajouterArticle=function(){


			$http.get("/addArticle?designation="+$scope.designation+"&etat="+$scope.etat+
					"&stockmin="+$scope.stockmin+"&stockmax="+$scope.stockmax+"&stockreel="
					+$scope.stockreel+"&prixachat="+$scope.prixachat+"&prixvente="+$scope.prixvente+
					"&idCat="+$scope.idCat)
			.success(function(data){

				
				    $scope.categories=[] ;
					$http.get("/allCategories")
					.success(function(data){
					 $scope.categories = data ;
			}) ;
					
					
				
			});
			
			alert("Article bien ajouté") ; 
			
			
			
		} ;
		
		
		
		

	
}) ;
