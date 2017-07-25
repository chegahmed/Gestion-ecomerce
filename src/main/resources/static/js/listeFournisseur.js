var app=angular.module("listeF",[]) ;
app.controller("listeFController",function($scope,$http){
          
	$scope.reps=[] ;
	
	$scope.listeRep = function(idF) {

		
		$http.get("/getRepresentantsF?idF="+idF)
		.success(function(data){
		 $scope.reps = data ;
	}) ;
	}
		
		$scope.fournisseurs={ coms : [] , selected: {} } ;
		
		$scope.motCle = null ;
		$scope.pageCourante=0 ;
		$scope.nbreclientParPage=5 ;
		$scope.nbrePages= 0 ;
		$scope.nbrePagesR= 0 ;
		var pages = [];
		
		$http.get("/getF?position="+$scope.pageCourante+"&nbre="+$scope.nbreclientParPage)
		.success(function(data){
			$scope.fournisseurs.coms = data ;		
		});
		

		
		$http.get("/nbreF")
		.success(function(data){
			$scope.nbrePages = (data/ $scope.nbreclientParPage ) ;
			pages = [];
			for(var i=0;i< $scope.nbrePages;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});
		
		
		$scope.charger=function(pos,mc){
			
			if(mc==null) {
			
			$http.get("/getF?position="+(pos*$scope.nbreclientParPage)+"&nbre="+$scope.nbreclientParPage)
			.success(function(data){
				$scope.fournisseurs.coms = data ;
			});
			
		
			$http.get("/nbreF")
			.success(function(data){
				$scope.nbrePages = (data/ $scope.nbreclientParPage ) ;
				pages = [];
				for(var i=0;i< $scope.nbrePages;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});
			
			}
			
			else {
				
				
				$http.get("/RechercheClientByIdandNomF?motCle="+mc+"&position="+(pos*$scope.nbreclientParPage)+"&nbre="+$scope.nbreclientParPage)
				.success(function(data){
					$scope.fournisseurs.coms = data ;
					
				  }) ;
				
				$http.get("/nbreFMC?motCle="+motCle)
				.success(function(data){
					$scope.nbrePagesR = (data/ $scope.nbreclientParPage ) ;
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
			$http.get("/RechercheClientByIdandNomF?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreclientParPage)
			.success(function(data){
				$scope.fournisseurs.coms = data ;
				
			  }) ;
			
			$http.get("/nbreFMC?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreClientParPage ) ;
				 pages = [];
				for(var i=0;i< $scope.nbrePagesR;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});

			}
		
		
		
		
		 
		 $scope.deleteF=function(idF){
				
				$http.get("/deleteF?idF="+idF)
				.success(function(data){ 

					$http.get("/getF?position="+$scope.pageCourante+"&nbre="+$scope.nbreclientParPage)
					.success(function(data){
						$scope.fournisseurs.coms = data ;		
					});
					
				});
				
			} 
		 
		 
		 
		 $scope.getTemplate = function (Bl) {
			 
		      if (Bl.idF === $scope.fournisseurs.selected.idF) return 'edit';
		        else return 'display';

		   };
		   
		   $scope.editF= function (Bl) {
		        $scope.fournisseurs.selected= angular.copy(Bl);
		    };
		    
		    $scope.saveF = function (idx , id , nom , tel , fax , gsm , email) {

		        $scope.fournisseurs.coms[idx] = angular.copy($scope.fournisseurs.selected);
		        
		        $http.get("/updateFournisseur?idF="+id+"&nomSocF="+nom+"&tel1F="+tel+"&faxF="+fax+"&gsm1F="+gsm+"&email1F="+email)
				.success(function(data){ 
					

				});
		        
		        $scope.reset();
		    };
		    
		    $scope.reset = function () {
		        $scope.fournisseurs.selected = {};
		    };
		
		

}) ;
