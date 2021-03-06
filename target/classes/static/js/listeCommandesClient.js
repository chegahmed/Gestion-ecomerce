var app=angular.module("BonCommandesClient",[]) ;
app.controller("BonCommandesClientController",function($scope,$http){
	
	$scope.commandesClient={ coms : [] , selected: {} } ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreCommandeParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getCommandesClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
	.success(function(data){
		$scope.commandesClient.coms = data ;		
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
			$scope.commandesClient.coms = data ;
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
				$scope.commandesClient.coms = data ;
				
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
			$scope.commandesClient.coms = data ;
			
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

	
	
	
	   $scope.addFactureDirecte=function(idComFac){
		   alert(idComFac) ;
		   
		   $http.get("/addFactureDirecte?idCom="+idComFac)
			.success(function(data){ 
				alert(idComFac) ;
			});   
		   
		   $http.get("/allCommandesClient")
			.success(function(data){
			 $scope.commandesClient.coms = data ;
		}) ;
				
} 
	   
	   $scope.addBonLivraisonClient=function(idArtCom){
			
			$http.get("/addBonLivraisonClient?idComCl="+idArtCom)
			.success(function(data){ 
                   
				$http.get("/getCommandesClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
				.success(function(data){
					$scope.commandesClient.coms = data ;		
				});
			
			});

		} 
	   
	   $scope.deleteCommandeClient=function(idComCl){
			
			$http.get("/deleteCommandeClient?idComCl="+idComCl)
			.success(function(data){ 
				
				$http.get("/getCommandesClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
				.success(function(data){
					$scope.commandesClient.coms = data ;		
				});
				
			});
			
		} 
	   
	   
	   
	   
	   
	   $scope.getTemplate = function (comCl) {
			 
		      if (comCl.idComCl === $scope.commandesClient.selected.idComCl) return 'edit';
		        else return 'display';

		   };
		   
		   $scope.editCommande= function (comCl) {
		        $scope.commandesClient.selected= angular.copy(comCl);
		    };
		    
		    $scope.saveCommande = function (idx , id , date) {

		        $scope.commandesClient.coms[idx] = angular.copy($scope.commandesClient.selected);
		        
		        $http.get("/updateCommandeCl?id="+id+"&date="+date)
				.success(function(data){ 
					

				});
		        
		        $scope.reset();
		    };
		    
		    $scope.reset = function () {
		        $scope.commandesClient.selected = {};
		    };
		    
		    
	  $scope.deleteCommandeClient=function(idComCl){
				
				$http.get("/deleteCommandeClient?idComCl="+idComCl)
				.success(function(data){ 
					
					$http.get("/getCommandesClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
					.success(function(data){
						$scope.commandesClient.coms = data ;		
					});
					
				});
				
			} 
	   
	   
	   
		
}) ;
