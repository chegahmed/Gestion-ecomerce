var app=angular.module("statistiqueArticleClient",["chart.js"]) ;
app.controller("statistiqueArticleClientController",function($scope,$http){
	    
	    $scope.labels = ['Janvier', 'Fevrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet' , 'Aout', 'Septembre', 'Octobre', 'Novembre', 'Decembre'];
	    $scope.listeArts=[] ;
		$http.get("/allArticles")
		.success(function(data){
		 $scope.listeArts = data ;
}) ;
		
		$scope.listeArticles=[] ;
		$scope.stats = null;
		$scope.statistiqueArticle=function(idArt){
			$http.get("/statArticleClient?idArt="+idArt)
			.success(function(data){

				$scope.listeArticles = data ;
				
			});
			
			$http.get("/statRechercheMois?idArt="+idArt)
			.success(function(data){

				$scope.stats = data ;
				$scope.donnees = [[data.statJan, data.statFev, data.statMar,data.statAvr, data.statMai, data.statJuin, data.statJuil , data.statAout, data.statSept, data.statOct, data.statNov,data.statDec]]; 
				
				//$scope.donnees = [[65, 59, 700, 81, 56, 55, 40 , 65, 59, 80, 81, 56]];
			});
			
		} ;

	   
}) ;


