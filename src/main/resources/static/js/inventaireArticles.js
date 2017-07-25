var app=angular.module("inventaireArticles",[]) ;
app.controller("inventaireArticlesController",function($scope,$http){


	$scope.articles=[] ;
	$scope.nbrecategoriesParPage=1000 ;
	$scope.nbreMC = 0 ;
	$http.get("/allArticle")
	.success(function(data){
	 $scope.articles = data ;
}) ;
	
	$scope.recherche = function(motCle) {
		
		$http.get("/RechercheArticleByIdandNom?motCle="+motCle+"&position=0&nbre=1000")
		.success(function(data){
			$scope.articles = data ;
			
		  }) ;
	}
		
		

}) ;
