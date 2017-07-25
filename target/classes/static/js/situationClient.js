var app=angular.module("situationClient",["chart.js"]) ;
app.controller("situationClientController",function($scope,$http){
	    
	    $scope.labels = ['Janvier', 'Fevrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet' , 'Aout', 'Septembre', 'Octobre', 'Novembre', 'Decembre'];
	    $scope.clients=[] ;
		$http.get("/allClient")
		.success(function(data){
		 $scope.clients = data ;
}) ;
		
		$scope.facturesR=[] ;
		$scope.facturesNR=[] ;
		$scope.avoirs=[] ;
		$scope.client=null;
		$scope.stats = null;
		$scope.situation=function(idCl){
			
			$http.get("/getClientById?idCl="+idCl)
			.success(function(data){

				$scope.client = data ;
				
			});
			
			$http.get("/getFacturesTByClient?idCl="+idCl)
			.success(function(data){

				$scope.facturesR = data ;
				
			});
			
			$http.get("/getFacturesNTByClient?idCl="+idCl)
			.success(function(data){

				$scope.facturesNR = data ;
				
			});
			
			$http.get("/getAvoirsClient?idCl="+idCl)
			.success(function(data){

				$scope.avoirs= data ;
				
			});
			
			
		
			
		} ;

	   
}) ;


