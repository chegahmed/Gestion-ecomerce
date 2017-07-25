var app=angular.module("ajouterFactureClient",[]) ;
app.controller("ajouterFactureClientController",function($scope,$http){

	$scope.bonLivraisonClient=[] ;
	$http.get("/allBonLivraisonClient")
	.success(function(data){
	 $scope.bonLivraisonClient = data ;
}) ;
	
	
	
	
	
	
	
	
	
	
$scope.commandesClient=[] ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreBLParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getBLClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
	.success(function(data){
		$scope.bonLivraisonClient = data ;		
	});
	
	$http.get("/allBLClient")
	.success(function(data){

		
}) ;
	
	$http.get("/nbreBLClient")
	.success(function(data){
		$scope.nbrePages = (data/ $scope.nbreBLParPage ) ;
		pages = [];
		for(var i=0;i< $scope.nbrePages;i++) {
		  pages.push(i);
		}
		$scope.pages = pages;
	});
	
	
	$scope.charger=function(pos,mc){
		$scope.pageCourante = pos ;
		
		if(mc==null) {
		
		$http.get("/getBLClient?position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			
			$scope.bonLivraisonClient = data ;
		});
		
	
		$http.get("/nbreBLClient")
		.success(function(data){
			$scope.nbrePages = (data/ $scope.nbreBLParPage ) ;
			pages = [];
			for(var i=0;i< $scope.nbrePages;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});
		
		}
		
		else {
			
			
			$http.get("/RechercheBLByIdandNomClient?motCle="+mc+"&position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonClient = data ;
				
			  }) ;
			
			$http.get("/nbreBLClientMC?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreBLParPage ) ;
				pages = [];
				for(var i=0;i< $scope.nbrePagesR;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});
			
			
			
		}
		
	} ;
	
	
	$scope.recherche = function(motCle) {
		$scope.nbreMC = 0 ;
		$http.get("/RechercheBLByIdandNomClient?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			$scope.bonLivraisonClient = data ;
			
		  }) ;
		
		$http.get("/nbreBLClientMC?motCle="+motCle)
		.success(function(data){
			$scope.nbrePagesR = (data/ $scope.nbreBLParPage ) ;
			 pages = [];
			for(var i=0;i< $scope.nbrePagesR;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});

		}
	
	

	
	
	
	   $scope.addFactureDirecte=function(idComFac){
		   alert(idComFac) ;
		   
		   $http.get("/addFactureDirecte?idCom="+idComFac)
			.success(function(data){ 
				alert(idComFac) ;
			});   
		   
		   $http.get("/allCommandesClient")
			.success(function(data){
			 $scope.commandesClient = data ;
		}) ;
				
} 
	   
	   $scope.addBonLivraisonClient=function(idArtCom){
			
			$http.get("/addBonLivraisonClient?idComCl="+idArtCom)
			.success(function(data){ 
				alert(idArtCom) ;
			});
			
			$http.get("/allCommandesClient")
			.success(function(data){
			 $scope.commandesClient = data ;
		}) ;

			
		} 
		
	
	
	
	
	

		
}) ;
