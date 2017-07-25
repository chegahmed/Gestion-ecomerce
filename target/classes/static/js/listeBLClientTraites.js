var app=angular.module("listeBLClientTraites",[]) ;
app.controller("listeBLClientTraitesController",function($scope,$http , $window){

	$scope.bonLivraisonClient=[] ;
	$http.get("/allBlClientT")
	.success(function(data){
	 $scope.bonLivraisonClient = data ;
}) ;
	
	
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreBLParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getBLClientT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
	.success(function(data){
		$scope.bonLivraisonClient = data ;		
	});
	
	$http.get("/allBLClientT")
	.success(function(data){

		
}) ;
	
	$http.get("/nbreBLClientT")
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
		
		$http.get("/getBLClientT?position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			
			$scope.bonLivraisonClient = data ;
		});
		
	
		$http.get("/nbreBLClientT")
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
			
			
			$http.get("/RechercheBLByIdandNomClientT?motCle="+mc+"&position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonClient = data ;
				
			  }) ;
			
			$http.get("/nbreBLClientMCT?motCle="+motCle)
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
		$http.get("/RechercheBLByIdandNomClientT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			$scope.bonLivraisonClient = data ;
			
		  }) ;
		
		$http.get("/nbreBLClientMCT?motCle="+motCle)
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
		  
		   
		   $http.get("/addFactureDirecte?idCom="+idComFac)
			.success(function(data){ 
			
			});   
		   
		   $http.get("/allCommandesClient")
			.success(function(data){
			 $scope.commandesClient = data ;
		}) ;
				
} 
	   
	   $scope.addBonLivraisonClient=function(idArtCom){
			
			$http.get("/addBonLivraisonClient?idComCl="+idArtCom)
			.success(function(data){ 
				
			});
			
			$http.get("/allCommandesClient")
			.success(function(data){
			 $scope.commandesClient = data ;
		}) ;

			
		} 
	   
	   $scope.deleteBLClient=function(idBL){
			$http.get("/deleteBL?idBL="+idBL)
			.success(function(data){ 
				
				
			});
			
			
			$http.get("/getBLClientT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonClient = data ;		
			});
			
			
		} 
		
	
	
	
	   $scope.imprimerBl=function(idBl){
     
		   $http.get("/getLivraisonById?idBl="+idBl)
			.success(function(data){
				 
				 $window.open("http://localhost:8084/pdf/"+data.fichierBl, "_blank");
		   }) ;   

		} 
	

		
}) ;
