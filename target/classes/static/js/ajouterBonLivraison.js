var app=angular.module("ajouterBonlivraisonClient",[]) ;
app.controller("ajouterBonlivraisonClientController",function($scope,$http){
	
	$scope.commandesClient=[] ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreCommandeParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getCommandesClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
	.success(function(data){
		$scope.commandesClient = data ;		
	});
	
	$http.get("/allCommandesClient")
	.success(function(data){

		
}) ;
	
	$http.get("/nbreCommandesClient")
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
		
		$http.get("/getCommandesClient?position="+(pos*$scope.nbreCommandeParPage)+"&nbre="+$scope.nbreCommandeParPage)
		.success(function(data){
			$scope.commandesClient = data ;
		});
		
	
		$http.get("/nbreCommandesClient")
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
			
			
			$http.get("/RechercheCommandeByIdandNomClient?motCle="+mc+"&position="+(pos*$scope.nbreCommandeParPage)+"&nbre="+$scope.nbreCommandeParPage)
			.success(function(data){
				$scope.commandesClient = data ;
				
			  }) ;
			
			$http.get("/nbreCommandesClientMC?motCle="+motCle)
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
		$http.get("/RechercheCommandeByIdandNomClient?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreCommandeParPage)
		.success(function(data){
			$scope.commandesClient = data ;
			
		  }) ;
		
		$http.get("/nbreCommandesClientMC?motCle="+motCle)
		.success(function(data){
			$scope.nbrePagesR = (data/ $scope.nbreCommandeParPage ) ;
			 pages = [];
			for(var i=0;i< $scope.nbrePagesR;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});

		}

	
	
	
	 $scope.addBonLivraisonClient=function(idArtCom){
			
			$http.get("/addBonLivraisonClient?idComCl="+idArtCom)
			.success(function(data){ 
                
				$http.get("/getCommandesClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
				.success(function(data){
					$scope.commandesClient = data ;		
				});
			
			});

		} 
	   
	   $scope.deleteCommandeClient=function(idComCl){
			
			$http.get("/deleteCommandeClient?idComCl="+idComCl)
			.success(function(data){ 
				alert(idComCl) ;
				
				$http.get("/getCommandesClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
				.success(function(data){
					$scope.commandesClient = data ;		
				});
				
			});

		} 
		
}) ;
