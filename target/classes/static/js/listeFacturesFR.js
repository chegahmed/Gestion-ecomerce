var app=angular.module("listeFactureFR",[]) ;
app.controller("listeFactureFRController",function($scope,$http){
	
		
	    $scope.factures=[] ;
	    $scope.montantF=0 ;
		$scope.motCle = null ;
		$scope.pageCourante=0 ;
		$scope.nbreFacParPage=5 ;
		$scope.nbrePages= 0 ;
		$scope.nbrePagesR= 0 ;
		var pages = [];
		
		$http.get("/getFacFT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
		.success(function(data){
			$scope.factures = data ;		
		});
		
		$scope.factureClient=[] ;
		$http.get("/getFacFT")
		.success(function(data){
		 $scope.factureClient = data ;
}) ;
		
	
		
		$http.get("/nbreFacFT")
		.success(function(data){
			$scope.nbrePages = (data/ $scope.nbreFacParPage ) ;
			pages = [];
			for(var i=0;i< $scope.nbrePages;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});
		
		
		$scope.charger=function(pos,mc){
			$scope.pageCourante=pos ;
			if(mc==null) {
			
			$http.get("/getFacCFT?position="+(pos*$scope.nbreFacParPage)+"&nbre="+$scope.nbreFacParPage)
			.success(function(data){
				$scope.factures = data ;
			});
			
		
			$http.get("/nbreFacFT")
			.success(function(data){
				$scope.nbrePages = (data/ $scope.nbreFacParPage ) ;
				pages = [];
				for(var i=0;i< $scope.nbrePages;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});
			
			}
			
			else {
				
				
				$http.get("/RechercheFacByIdandNomFT?motCle="+mc+"&position="+(pos*$scope.nbreFacParPage)+"&nbre="+$scope.nbreFacParPage)
				.success(function(data){
					$scope.factures = data ;
					
				  }) ;
				
				$http.get("/nbreFacFMCT?motCle="+motCle)
				.success(function(data){
					$scope.nbrePagesR = (data/ $scope.nbreFacParPage ) ;
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
			$http.get("/RechercheFacByIdandNomFT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreFacParPage)
			.success(function(data){
				$scope.factures = data ;
				
			  }) ;
			
			$http.get("/nbreFacFMCT?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreFacParPage ) ;
				 pages = [];
				for(var i=0;i< $scope.nbrePagesR;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});

			} 

		
		
		 $scope.paiementFacture=function(idFac , idCl , nomClient , montantFac ){
			 
			    
			 $http.get("/getModePaiementF?idCl="+idCl)
				.success(function(data){ 
					$scope.modes = [] ;
					$scope.modes = data ;
					$scope.codeFac = idFac ;
					$scope.montantFac = montantFac ;
				});
					
				
			} 
		 
		 
		 
		 $scope.reglementFacture=function(idFac , modeP , montantF ){
			 alert(modeP) ;
			 $http.get("/reglementFacture?idF="+idFac+"&idM="+modeP+"&montantF="+montantF)
				.success(function(data){ 
				
					$http.get("/getFacFT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
					.success(function(data){
						$scope.factures = data ;		
					});
					
					
				});
					
				
			} 
		 
		 $scope.deleteFactureF=function(idF){
				
				$http.get("/deleteFacture?idF="+idF)
				.success(function(data){ 

				
					
				});
				
				$http.get("/getFacFT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
				.success(function(data){
					$scope.factures = data ;		
				});
				
			} 
		 
		 
		 $scope.getArticlesFacture=function(idFac){
				$http.get("/getArticlesFacture?idFac="+idFac)
				.success(function(data){ 
					$scope.articlesFacture = data ;
				});

				
			} 
		  

		
		
}) ;
