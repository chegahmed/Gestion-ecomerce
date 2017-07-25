var app=angular.module("listeFactureFNR",[]) ;
app.controller("listeFactureFNRController",function($scope,$http){
	
		
	    $scope.factures= { coms : [] , selected: {} } ;
	    $scope.montantF=0 ;
		$scope.motCle = null ;
		$scope.pageCourante=0 ;
		$scope.nbreFacParPage=5 ;
		$scope.nbrePages= 0 ;
		$scope.nbrePagesR= 0 ;
		var pages = [];
		
		$http.get("/getFacFNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
		.success(function(data){
			$scope.factures.coms = data ;		
		});
		

	
		
		$http.get("/nbreFacFNT")
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
			
			$http.get("/getFacCFNT?position="+(pos*$scope.nbreFacParPage)+"&nbre="+$scope.nbreFacParPage)
			.success(function(data){
				$scope.factures.coms = data ;
			});
			
		
			$http.get("/nbreFacFNT")
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
				
				
				$http.get("/RechercheFacByIdandNomFNT?motCle="+mc+"&position="+(pos*$scope.nbreFacParPage)+"&nbre="+$scope.nbreFacParPage)
				.success(function(data){
					$scope.factures.coms = data ;
					
				  }) ;
				
				$http.get("/nbreFacFMCNT?motCle="+motCle)
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
			$http.get("/RechercheFacByIdandNomFNT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreFacParPage)
			.success(function(data){
				$scope.factures.coms = data ;
				
			  }) ;
			
			$http.get("/nbreFacFMCNT?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreFacParPage ) ;
				 pages = [];
				for(var i=0;i< $scope.nbrePagesR;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});

			} 

		
		
		 $scope.paiementFacture=function(idFac , idFour , nomFour , montantFac ){
			 
			 $http.get("/getModePaiementF?idF="+idFour)
				.success(function(data){ 
					$scope.modes = [] ;
					$scope.modes = data ;
					$scope.codeFac = idFac ;
					$scope.montantFac = montantFac ;
				});
					
				
			} 
		 
		 
		 $scope.visible=true ;
			$scope.onSave = function(){
				
              
		        if($scope.form.$valid){

                  
		        }
				
		    }
		 
		 $scope.reglementFacture=function(idFac , modeP , montantF ){
			 alert("mode est "+modeP) ;
			 if(modeP==undefined  ) {
				 alert("Veuillez choisir un mode de paiement" ) ; 
			 }
			 else {
			 $http.get("/reglementFacture?idF="+idFac+"&idM="+modeP+"&montantF="+montantF)
				.success(function(data){ 
				
					$http.get("/getFacFNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
					.success(function(data){
						$scope.factures.coms = data ;		
					});
					
					
				});
					
				alert("Votre Facture est bien reglée") ;
			 }
			} 
		 
		 $scope.deleteFactureF=function(idF){
				
				$http.get("/deleteFactureF?idF="+idF)
				.success(function(data){ 

					$http.get("/getFacFNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
					.success(function(data){
						$scope.factures.coms = data ;	 	
					});
					
				});
				
			} 
		 
		 
		 $scope.getArticlesFacture=function(idFac){
				$http.get("/getArticlesFacture?idFac="+idFac)
				.success(function(data){ 
					$scope.articlesFacture = data ;
				});

				
			} 
		 
		 
		 
		 $scope.getTemplate = function (Fac) {
			 
		      if (Fac.idFac === $scope.factures.selected.idFac) return 'edit';
		        else return 'display';

		   };
		   
		   $scope.editFacture= function (Fac) {
		        $scope.factures.selected= angular.copy(Fac);
		    };
		    
		    $scope.saveFacture = function (idx , id , date) {

		        $scope.factures.coms[idx] = angular.copy($scope.factures.selected);
		        
		        $http.get("/updateF?id="+id+"&date="+date)
				.success(function(data){ 
					

				});
		        
		        $scope.reset();
		    };
		    
		    $scope.reset = function () {
		        $scope.factures.selected = {};
		    };
		    
		    
		    $scope.reglementFacture=function(idFac , modeP , montantF ){

				 $http.get("/reglementFacture?idF="+idFac+"&idM="+modeP+"&montantF="+montantF)
					.success(function(data){ 
				
						
						
					});
				 
				 $http.get("/getFacFNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
					.success(function(data){
						$scope.factures.coms = data ;	 	
					});
						
					
				} 
		  

		
		
}) ;
