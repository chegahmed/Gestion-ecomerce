var app=angular.module("listeFactureClientR",[]) ;
app.controller("listeFactureClientRController",function($scope,$http , $window){
	
		
	    $scope.factures={ coms : [] , selected: {} } ;
		
		$scope.motCle = null ;
		$scope.pageCourante=0 ;
		$scope.nbreFacParPage=5 ;
		$scope.nbrePages= 0 ;
		$scope.nbrePagesR= 0 ;
		var pages = [];
		
		$http.get("/getFacClientT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
		.success(function(data){
			$scope.factures.coms = data ;		
		});
		
		
		
	
		
		$http.get("/nbreFacClientT")
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
			
			$http.get("/getFacClientT?position="+(pos*$scope.nbreFacParPage)+"&nbre="+$scope.nbreFacParPage)
			.success(function(data){
				$scope.factures.coms = data ;
			});
			
		
			$http.get("/nbreFacClientT")
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
				
				
				$http.get("/RechercheFacByIdandNomClientT?motCle="+mc+"&position="+(pos*$scope.nbreFacParPage)+"&nbre="+$scope.nbreFacParPage)
				.success(function(data){
					$scope.factures.coms = data ;
					
				  }) ;
				
				$http.get("/nbreFacClientMCT?motCle="+motCle)
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
			$http.get("/RechercheFacByIdandNomClientT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreFacParPage)
			.success(function(data){
				$scope.factures.coms = data ;
				
			  }) ;
			
			$http.get("/nbreFacClientMCT?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreFacParPage ) ;
				 pages = [];
				for(var i=0;i< $scope.nbrePagesR;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});

			} 

		
		 $scope.deleteFactureClient=function(idF){
				
				$http.get("/deleteFacture?idF="+idF)
				.success(function(data){ 
					
					
					$http.get("/getFacClientT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
					.success(function(data){
						$scope.factures.coms = data ;		
					});
					
				

				});
				
				
				
				
			
				
			} 
		 
		 
		 
		 $scope.getTemplate = function (Fac) {
			 
		      if (Fac.idFac === $scope.factures.selected.idFac) return 'edit';
		        else return 'display';

		   };
		   
		   $scope.editFac= function (Fac) {
		        $scope.factures.selected= angular.copy(Fac);
		    };
		    
		    $scope.saveFac = function (idx , id , date) {

		        $scope.factures.coms[idx] = angular.copy($scope.factures.selected);
		        
		        $http.get("/updateFac?id="+id+"&date="+date)
				.success(function(data){ 
					

				});
		        
		        $scope.reset();
		    };
		    
		    $scope.reset = function () {
		        $scope.factures.selected = {};
		    };
		    
		    $scope.imprimerFac=function(idFac){
				   
				   
				   $http.get("/getFactureById?idFac="+idFac)
					.success(function(data){
						 $window.open("http://localhost:8084/pdf/"+data.fichierFac, "_blank");
				   }) ;   

				} 
		
		  

		
		
}) ;
