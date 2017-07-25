var app=angular.module("commandesClientTraitees",[]) ;
app.controller("commandesClientTraiteesController",function($scope,$http,$window){
	
	$scope.commandesClient=[] ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreCommandeParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getCommandesClientT?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
	.success(function(data){
		$scope.commandesClient = data ;		
	});
	
	$http.get("/allCommandesClientT")
	.success(function(data){

		
}) ;
	
	$http.get("/nbreCommandesClientT")
	.success(function(data){
		$scope.nbrePages = (data/ $scope.nbreCommandeParPage ) ;
		pages = [];
		for(var i=0;i< $scope.nbrePages;i++) {
		  pages.push(i);
		}
		$scope.pages = pages;
	});
	
	
	$scope.charger=function(pos,mc){
		
		if(mc==null) {
		
		$http.get("/getCommandesClientT?position="+(pos*$scope.nbreCommandeParPage)+"&nbre="+$scope.nbreCommandeParPage)
		.success(function(data){
			$scope.commandesClient = data ;
		});
		
	
		$http.get("/nbreCommandesClientT")
		.success(function(data){
			$scope.nbrePages = (data/ $scope.nbreCommandeParPage ) ;
			pages = [];
			for(var i=0;i< $scope.nbrePages;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});
		
		}
		
		else {
			
			
			$http.get("/RechercheCommandeByIdandNomClientT?motCle="+mc+"&position="+(pos*$scope.nbreCommandeParPage)+"&nbre="+$scope.nbreCommandeParPage)
			.success(function(data){
				$scope.commandesClient = data ;
				
			  }) ;
			
			$http.get("/nbreCommandesClientMCT?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreCommandeParPage ) ;
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
		$http.get("/RechercheCommandeByIdandNomClientT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreCommandeParPage)
		.success(function(data){
			$scope.commandesClient = data ;
			
		  }) ;
		
		$http.get("/nbreCommandesClientMCT?motCle="+motCle)
		.success(function(data){
			$scope.nbrePagesR = (data/ $scope.nbreCommandeParPage ) ;
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

			});
			
			$http.get("/allCommandesClient")
			.success(function(data){
			 $scope.commandesClient = data ;
		}) ;

			
		} 
	   
	   
	   
	   $scope.deleteCommandeClient=function(idComCl){
			
			$http.get("/deleteCommandeClient?idComCl="+idComCl)
			.success(function(data){ 
				
				$http.get("/getCommandesClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
				.success(function(data){
					$scope.commandesClient = data ;		
				});
				
			});
			
		} 
	   
	/*   $scope.imprimerCommande=function(idComCl){
		
			$http.get("/pdfCommande?idComCl="+idComCl)
			.success(function(data){
				alert(idComCl)
				
				//$location.path("http://localhost:8084/pdf/"+data);
			});
						
		} */
	   
	   
	   $scope.imprimerCommande=function(idComCl){
		   
		   
		   $http.get("/getCommandeById?idComCl="+idComCl)
			.success(function(data){
				 $window.open("http://localhost:8084/pdf/"+data.fichierComCl, "_blank");
				//window.location.href = "http://localhost:8084/pdf/"+data.fichierComCl ;
		   }) ;   
		   
		   
			
		} 
	   

		   
		   

	   
		
}) ;
