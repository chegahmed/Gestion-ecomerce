var app=angular.module("ajouterRepresentantF",[]) ;
app.controller("ajouterRepresentantFController",function($scope,$http){

	
	$scope.nomRep = null ;
	$scope.prenomRep = null ;
	$scope.villeRep = null ;
	$scope.telRep = null ;
	$scope.gsm1Rep = null ;
	$scope.gsm2Rep = null ;
	$scope.emailRep = null ;
	
	
	 $scope.fournisseurs=[] ;
		$http.get("/allFournisseur")
		.success(function(data){
		 $scope.fournisseurs = data ;
}) ;

		

		$scope.ajouterRepFournisseur=function(){
			$http.get("/addRepToFourn?nomRep="+$scope.nomRep+"&prenomRep="+$scope.prenomRep+"&villeRep="+$scope.villeRep+"" +
															"&telRep="+$scope.telRep+"&gsm1Rep="+$scope.gsm1Rep+"&gsm2Rep="+$scope.gsm2Rep+"&emailRep="+$scope.emailRep+"&idF="+$scope.idF)
			.success(function(data){

				$scope.fournisseurs=[] ;
				$http.get("/allFournisseur")
				.success(function(data){
				 $scope.fournisseurs = data ;
		    }) ;
				
			});
			
			alert("Le representant est bien ajouté") ;
			
		} ;
		
		
}) ;
