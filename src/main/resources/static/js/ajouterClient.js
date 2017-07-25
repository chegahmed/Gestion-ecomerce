var app=angular.module("ajouterClient",[]) ;
app.controller("ajouterClientController",function($scope,$http, $window){
	
	    
	$scope.nomCl = null ;
	$scope.familleCl = null ;
	$scope.formeCl = null ;
	$scope.tel1Cl = null ;
	$scope.tel2Cl = null ;
	$scope.faxCl = null ;
	$scope.gsm1Cl = null ;
	$scope.gsm2Cl = null ;
	$scope.email1Cl = null ;
	$scope.email2Cl = null ;
	$scope.adresseCl = null ;
	$scope.sitewebCl = null ;
	$scope.seuilCl = 0 ;
	$scope.totalduCl = 0 ;
	$scope.risqueCl = null ;
	$scope.etatCl = null ;
	$scope.patenteCl = null ;
	$scope.registreCl = null ;
	$scope.tvaCl = null ;
	$scope.iceCl = null ;
	$scope.nomRep = null ;
	$scope.prenomRep = null ;
	$scope.villeRep = null ;
	$scope.telRep = null ;
	$scope.gsm1Rep = null ;
	$scope.gsm2Rep = null ;
	$scope.emailRep = null ;
	
	
	$scope.visible=true ;
	$scope.onSave = function(){
		
	

        if($scope.form.$valid){
        	$window.open("http://localhost:8084/listeClient.html" , "_self");
        }
		
    }
	
	
	    $scope.clients=[] ;
		$http.get("/allclients")
		.success(function(data){
		 $scope.clients = data ;
}) ;
		
		$scope.representants=[] ;
		$http.get("/allrepresentantsClient")
		.success(function(data){
		 $scope.representants = data ;	
}) ;
		

		$scope.ajouterClient=function(){
			
			$http.get("/addClientRep?nomSoc="+$scope.nomCl+"&famSoc="+$scope.familleCl+"&formeJur="+$scope.formeCl+"" +
					"&tel1="+$scope.tel1Cl+"&tel2="+$scope.tel2Cl+"&fax="+$scope.faxCl+"&gsm1="+$scope.gsm1Cl+"" +
							"&gsm2="+$scope.gsm2Cl+"&email1="+$scope.email1Cl+"&email2="+$scope.email2Cl+"&adresse="+$scope.adresseCl+"" +
									"&siteweb="+$scope.sitewebCl+"&seuil="+$scope.seuilCl+"&totaldu="+$scope.totalduCl+"&risque="+$scope.risqueCl+"" +
											"&etat="+$scope.etatCl+"&numPatente="+$scope.patenteCl+"&numRegistre="+$scope.registreCl+"&numTva="+$scope.tvaCl+"" +
													"&numIce="+$scope.iceCl+"&nomRep="+$scope.nomRep+"&prenomRep="+$scope.prenomRep+"&villeRep="+$scope.villeRep+"" +
															"&telRep="+$scope.telRep+"&gsm1Rep="+$scope.gsm1rep+"&gsm2Rep="+$scope.gsm2rep+"&emailRep="+$scope.emailRep)
			.success(function(data){
				
				$http.get("/allclients")
				.success(function(data){
				 $scope.clients = data ;
		     }) ;
				
				
				$scope.representants=[] ;
				$http.get("/allrepresentantsClient")
				.success(function(data){
				 $scope.representants = data ;	
		}) ;
				
				
				
			});
			
			alert("Le client est bien ajouté") ;
			
		} ;
		
		
}) ;
