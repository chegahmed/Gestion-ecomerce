var app=angular.module("ajouterUtilisateur",[]) ;
app.controller("ajouterUtilisateurController",function($scope,$http){

	$scope.nomUser=null ;
	$scope.prenomUser=null ;
	$scope.statutUser="ADMIN" ;
	$scope.username=null ;
	$scope.password= null ;

	
	    $scope.allUsers=[] ;
		$http.get("/allUsers")
		.success(function(data){
		 $scope.allUsers = data ;
}) ;
		

		

		$scope.ajouterUtilisateur=function(){

			
			$http.get("/addUser?username="+$scope.username+"&password="+$scope.password+
					"&nom="+$scope.nomUser+"&prenom="+$scope.prenomUser+"&role="+$scope.statutUser)
			.success(function(data){
				
				$http.get("/allUsers")
				.success(function(data2){
				 $scope.allUsers = data2 ;
				 alert("Utilisateur ajouté") ;
	                     	}) ;

				
			}) ;
			
	
			

    } ;
    
    
    
    $scope.deleteUser=function(id){

		
		$http.get("/deleteUser?idUser="+id)
		.success(function(data){
			
			$http.get("/allUsers")
			.success(function(data2){
			 $scope.allUsers = data2 ;
                     	}) ;
         
			alert("Utilisateur supprimé") ;
			
		}) ;
		

} ;
    
    
    
		

	
}) ;
