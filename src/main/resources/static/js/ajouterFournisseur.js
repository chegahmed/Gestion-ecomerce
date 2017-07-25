var app=angular.module("ajouterFournisseur",[]) ;
app.controller("ajouterFournisseurController",function($scope,$http){
	
	    
	$scope.nomF = null ;
	$scope.familleF = null ;
	$scope.formeFF = null ;
	$scope.tel1F = null ;
	$scope.tel2F = null ;
	$scope.faxF = null ;
	$scope.gsm1F = null ;
	$scope.gsm2F = null ;
	$scope.email1F = null ;
	$scope.email2F = null ;
	$scope.adresseF = null ;
	$scope.sitewebF = null ;
	$scope.seuilF = 0 ;
	$scope.totalduF = 0 ;
	$scope.risqueF = null ;
	$scope.etatF = null ;
	$scope.patenteF = null ;
	$scope.registreF = null ;
	$scope.tvaF = null ;
	$scope.iceF = null ;
	$scope.nomRep = null ;
	$scope.prenomRep = null ;
	$scope.villeRep = null ;
	$scope.telRep = null ;
	$scope.gsm1Rep = null ;
	$scope.gsm2Rep = null ;
	$scope.emailRep = null ;
	

		$scope.ajouterRepresentant=function(){
			$http.get("/addFournRep?nomSocF="+$scope.nomF+"&famSocF="+$scope.familleF+"&formeJurF="+$scope.formeF+"" +
					"&tel1F="+$scope.tel1F+"&tel2F="+$scope.tel2F+"&faxF="+$scope.faxF+"&gsm1F="+$scope.gsm1F+"" +
							"&gsm2F="+$scope.gsm2F+"&email1F="+$scope.email1F+"&email2F="+$scope.email2F+"&adresseF="+$scope.adresseF+"" +
									"&sitewebF="+$scope.sitewebF+"&seuilF="+$scope.seuilF+"" +
											"&numPatenteF="+$scope.patenteF+"&numRegistreF="+$scope.registreF+"" +
													"&numIceF="+$scope.iceF+"&nomRep="+$scope.nomRep+"&prenomRep="+$scope.prenomRep+"&villeRep="+$scope.villeRep+"" +
															"&telRep="+$scope.telRep+"&gsm1Rep="+$scope.gsm1Rep+"&gsm2Rep="+$scope.gsm2Rep+"&emailRep="+$scope.emailRep)
			.success(function(data){
				
				 $scope.fournisseurs=[] ;
					$http.get("/allFournisseur")
					.success(function(data){
					 $scope.fournisseurs = data ;
			}) ;
					
					$scope.representants=[] ;
					$http.get("/allrepresentantsFourn")
					.success(function(data){
					 $scope.representants = data ;	
			}) ;
					
				
				
				
			});
			
			alert("Le Fournisseur est bien ajouté") ;
			
		} ;
		
		
}) ;
