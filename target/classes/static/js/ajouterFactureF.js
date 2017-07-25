var app=angular.module("ajouterFactureF",[]) ;
app.controller("ajouterFactureFController",function($scope,$http){
	
	
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
	
	
	
	$scope.commandesF=[] ;
	$http.get("/allComFourn")
	.success(function(data){
	 $scope.commandesF = data ;
}) ;
	
	$scope.fournisseurs=[] ;
	$http.get("/allFournisseur")
	.success(function(data){
	 $scope.fournisseurs = data ;
}) ;
	
	
	$scope.bonLivraisonFourn=[] ;
	$http.get("/allBonLivFourn")
	.success(function(data){ 
	 $scope.bonLivraisonFourn = data ;	
}) ;
	
	
	
	$scope.chargerArticles=function(){
		
		$scope.commandesF=[] ;
		$http.get("/allComFourn")
		.success(function(data){
		 $scope.commandesF = data ;
	}) ;
		
		$scope.fournisseurs=[] ;
		$http.get("/allFournisseur")
		.success(function(data){
		 $scope.fournisseurs = data ;
	}) ;
		
		
		$scope.bonLivs=[] ;
		$http.get("/allrepresentantsFourn")
		.success(function(data){ 
		 $scope.bonLivs = data ;	
	}) ;
		
			
}
	
	
	
	
	
	var addArticles = function(i){
		  $http.get("/addArticleToCommandeFourn?quantiteAl="+$scope.qteArt[i]+"&designationAl="+$scope.desArt[i]+"&prixUnitAl="+$scope.prixArt[i]+"&idArt="+$scope.codeArt[i])
		    .success(function(data) {

		     }) ;
		  alert("La numero "+i+" est faite") ; 
		  }

	
	
	
	$scope.ajouterCommande=function(){
		var arrayLength = $scope.codeArt.length;
		alert("code = "+$scope.codeComF) ;
		     $http.get("/addCommandeFourn?dateFour="+$scope.dateComF+"&idFourn="+$scope.codeComF)
		     .success(function(data){   

		    	 for (var i = 1; i < (arrayLength); i++) {
			    	 
		    		       alert("La valeur de i est = "+i) ;
		    		       
		    		       addArticles(i) ;
			    	          }

		           });
		     
		         alert("Commande Ajoutée") ;
		         $scope.chargerArticles() ;

	} ;
	

	
	
	$scope.getArticlesCommande=function(idArtCom){
		alert(idArtCom) ;
		$http.get("/getArticlesForCommandeFour?idComF="+idArtCom)
		.success(function(data){ 
			alert(" donnees = "+data) ;
			$scope.articlesCom = data ;
			$scope.chargerArticles() ;
		});

		
	} 
	
	
	

	$scope.getArticlesLivraison=function(idArtBl){

		$http.get("/getArticlesForBonLivsFour?idBl="+idArtBl)
		.success(function(data){ 
			alert(idArtBl) ;
			$scope.articlesLivraison = data ;
			alert(data);
			$scope.chargerArticles() ;
		});

		
	} 
	
	
	
	$scope.addBonLivraisonFour=function(idArtCom){
		
		$http.get("/addBonLivFour?idComFour="+idArtCom)
		.success(function(data){ 
			alert(idArtCom) ;
			$scope.chargerArticles() ;
		});

		
	} 
	
	
	
	
	
	
}) ;