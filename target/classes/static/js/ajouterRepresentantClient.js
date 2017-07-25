var app=angular.module("ajouterRepresentantClient",[]) ;
app.controller("ajouterRepresentantClientController",function($scope,$http , $window){

	
	$scope.nomRep = null ;
	$scope.prenomRep = null ;
	$scope.villeRep = null ;
	$scope.telRep = null ;
	$scope.gsm1Rep = null ;
	$scope.gsm2Rep = null ;
	$scope.emailRep = null ;
	
	
	$scope.visible=true ;
	$scope.onSave = function(){
		
		
		if($scope.idCl !=undefined) {

        if($scope.form.$valid){
        	$window.open("http://localhost:8084/listeClient.html" , "_self");
        }
		}
    }
	
	
	$scope.clients=[] ;
	$http.get("/allClient")
	.success(function(data){
	 $scope.clients = data ;
}) ;

		

		$scope.ajouterRepClient=function(){
			
			if($scope.idCl == undefined){
				alert("Choisissez un client") ;
			}
			
			else {
			$http.get("/addRepToClient?nomRep="+$scope.nomRep+"&prenomRep="+$scope.prenomRep+"&villeRep="+$scope.villeRep+"" +
															"&telRep="+$scope.telRep+"&gsm1Rep="+$scope.gsm1Rep+"&gsm2Rep="+$scope.gsm2Rep+"&emailRep="+$scope.emailRep+"&idCl="+$scope.idCl)
			.success(function(data){

				$scope.clients=[] ;
				$http.get("/allClient")
				.success(function(data){
				 $scope.clients = data ;
			}) ;
				
			});
			alert("Le representant est bien ajouté") ;
			
			}
		} ;
		
		
}) ;
