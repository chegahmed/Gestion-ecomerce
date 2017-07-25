var app=angular.module("ajouterMPClient",[]) ;
app.controller("ajouterMPClientController",function($scope,$http, $window){

	$scope.modePaie = null ;
	$scope.numCompte = null ;
	$scope.nomBanque = null ;
	$scope.numCheque = null ;
	$scope.codeCl = 1 ;
	
	
	
	
	$scope.visible=true ;
	$scope.onSave = function(){
		
		
		

        if($scope.form.$valid){

        }
	
    }
	
	
	
	
$scope.modepaiements=[] ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreMPParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getMPClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreMPParPage)
	.success(function(data){
		$scope.modepaiements = data ;		
	});
	

	
	$http.get("/nbreMPClient")
	.success(function(data){
		$scope.nbrePages = (data/ $scope.nbreMPParPage ) ;
		pages = [];
		for(var i=0;i< $scope.nbrePages;i++) {
		  pages.push(i);
		}
		$scope.pages = pages;
	});
	
	
	$scope.charger=function(pos,mc){
		
		if(mc==null) {
		
		$http.get("/getMPClient?position="+(pos*$scope.nbreMPParPage)+"&nbre="+$scope.nbreMPParPage)
		.success(function(data){
			$scope.modepaiements = data ;
		});
		
	
		$http.get("/nbreMPClient")
		.success(function(data){
			$scope.nbrePages = (data/ $scope.nbreMPParPage ) ;
			pages = [];
			for(var i=0;i< $scope.nbrePages;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});
		
		}
		
		else {
			
			
			$http.get("/RechercheMPByIdandNomClient?motCle="+mc+"&position="+(pos*$scope.nbreMPParPage)+"&nbre="+$scope.nbreMPParPage)
			.success(function(data){
				$scope.modepaiements = data ;
				
			  }) ;
			
			$http.get("/nbreMPClientMC?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreMPParPage ) ;
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
		$http.get("/RechercheMPByIdandNomClient?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreMPParPage)
		.success(function(data){
			$scope.modepaiements = data ;
			
		  }) ;
		
		$http.get("/nbreMPClientMC?motCle="+motCle)
		.success(function(data){
			$scope.nbrePagesR = (data/ $scope.nbreMPParPage ) ;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	    $scope.clients=[] ;
		$http.get("/allClient")
		.success(function(data){
		 $scope.clients = data ;
}) ;
		
		

		
		
		$scope.ajouterPaiement=function(){
			if($scope.modePaie  == null) { 
				alert("Choisissez un mode de paiement") ;
			}
			else {
			if($scope.modePaie  == "Espece") {
			$http.get("/addModePautreCl?nomMode="+$scope.modePaie+"&idCl="+$scope.codeCl)
			.success(function(data){
				
				$http.get("/allClient")
				.success(function(data){
				 $scope.clients = data ;
		     }) ;
				
				
				$http.get("/getMPClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreMPParPage)
				.success(function(data){
					$scope.modepaiements = data ;		
				});
				
				
			});  
			
		   }
			
			if($scope.modePaie  == "Virement Bancaire") {
				$http.get("/addModePbanqueCl?nomMode="+$scope.modePaie+"&numCompte="+$scope.numCompte+"&nomBanque="+$scope.nomBanque+"&idCl="+$scope.codeCl)
				.success(function(data){
					
					$http.get("/allClient")
					.success(function(data){
					 $scope.clients = data ;
			     }) ;
					
					
					$http.get("/getMPClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreMPParPage)
					.success(function(data){
						$scope.modepaiements = data ;		
					});

					
					
				});  
				
			   }
			
			if($scope.modePaie  == "Cheque") {
				$http.get("/addModePchequeCl?nomMode="+$scope.modePaie+"&numCheque="+$scope.numCheque+"&idCl="+$scope.codeCl)
				.success(function(data){
					
					$http.get("/allClient")
					.success(function(data){
					 $scope.clients = data ;
			     }) ;
					
					
					$http.get("/getMPClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreMPParPage)
					.success(function(data){
						$scope.modepaiements = data ;		
					});

					
					
				});  
				
			   }
			}

			
		} ;
		
		
		$scope.deleteMP=function(idMP , idCl){
			
			$http.get("/deleteMP?idMP="+idMP+"&idCl="+idCl)
			.success(function(data){ 

				$http.get("/getMPClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreMPParPage)
				.success(function(data){
					$scope.modepaiements = data ;		
				})
				
			});
			
		} 
	
		
}) ;
