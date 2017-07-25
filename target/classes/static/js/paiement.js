var app=angular.module("Paiement",[]) ;
app.controller("PaiementController",function($scope,$http){
	
	
	$scope.modePaie = null ;
	$scope.numCompte = null ;
	$scope.nomBanque = null ;
	$scope.numCheque = null ;
	$scope.codeCl = 1 ;
	
	
	    $scope.clients=[] ;
		$http.get("/allclients")
		.success(function(data){
		 $scope.clients = data ;
}) ;
		
		
		$scope.modepaiements=[] ;
		$http.get("/allModePaiements")
		.success(function(data){
		 $scope.modepaiements = data ;	
}) ;
		
		
		
		$scope.ajouterPaiement=function(){
			if($scope.modePaie  == "Espece") {
			$http.get("/addModePautreCl?nom="+$scope.modePaie+"&idCl="+$scope.codeCl)
			.success(function(data){
				
				$http.get("/allclients")
				.success(function(data){
				 $scope.clients = data ;
		     }) ;
				
				
				$scope.modepaiements=[] ;
				$http.get("/allModePaiements")
				.success(function(data){
				 $scope.modepaiements = data ;	
		}) ;

				
				
			});  
			
		   }
			
			if($scope.modePaie  == "Virement Bancaire") {
				$http.get("/addModePbanqueCl?nom="+$scope.modePaie+"&numCompte="+$scope.numCompte+"&nomBanque="+$scope.nomBanque+"&idCl="+$scope.codeCl)
				.success(function(data){
					
					$http.get("/allclients")
					.success(function(data){
					 $scope.clients = data ;
			     }) ;
					
					
					$scope.modepaiements=[] ;
					$http.get("/allModePaiements")
					.success(function(data){
					 $scope.modepaiements = data ;	
			}) ;

					
					
				});  
				
			   }
			
			if($scope.modePaie  == "Cheque") {
				$http.get("/addModePchequeCl?nom="+$scope.modePaie+"&numCheque="+$scope.numCheque+"&idCl="+$scope.codeCl)
				.success(function(data){
					
					$http.get("/allclients")
					.success(function(data){
					 $scope.clients = data ;
			     }) ;
					
					
					$scope.modepaiements=[] ;
					$http.get("/allModePaiements")
					.success(function(data){
					 $scope.modepaiements = data ;	
			}) ;

					
					
				});  
				
			   }

			
		} ;
		

		
		
}) ;
