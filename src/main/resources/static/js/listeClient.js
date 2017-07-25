var app=angular.module("listeClient",[]) ;
app.controller("listeClientController",function($scope,$http){

		
		
		$scope.clients={ coms : [] , selected: {} } ;
		
		$scope.motCle = null ;
		$scope.pageCourante=0 ;
		$scope.nbreclientParPage=5 ;
		$scope.nbrePages= 0 ;
		$scope.nbrePagesR= 0 ;
		var pages = [];
		
		$scope.reps=[] ;
		
	$scope.listeRep = function(idCl) {

		
		$http.get("/getRepresentantsClient?idCl="+idCl)
		.success(function(data){
		 $scope.reps = data ;
	}) ;
	}
		
		
		$http.get("/getClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreclientParPage)
		.success(function(data){
			$scope.clients.coms = data ;		
		});
		

		
		$http.get("/nbreClient")
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
			
			$http.get("/getClient?position="+(pos*$scope.nbreclientParPage)+"&nbre="+$scope.nbreclientParPage)
			.success(function(data){
				$scope.clients.coms = data ;
			});
			
		
			$http.get("/nbreClient")
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
				
				
				$http.get("/RechercheClientByIdandNomClient?motCle="+mc+"&position="+(pos*$scope.nbreclientParPage)+"&nbre="+$scope.nbreclientParPage)
				.success(function(data){
					$scope.clients.coms = data ;
					
				  }) ;
				
				$http.get("/nbreClientMC?motCle="+motCle)
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
			$http.get("/RechercheClientByIdandNomClient?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreclientParPage)
			.success(function(data){
				$scope.clients.coms = data ;
				
			  }) ;
			
			$http.get("/nbreClientMC?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreClientParPage ) ;
				 pages = [];
				for(var i=0;i< $scope.nbrePagesR;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});

			}
		
		
		
		
		 
		 $scope.deleteClient=function(idCl){
				
				$http.get("/deleteClient?idCl="+idCl)
				.success(function(data){ 

					$http.get("/getClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreclientParPage)
					.success(function(data){
						$scope.clients.coms = data ;		
					});
					
				});
				
			} 
		 
		 
		 $scope.getTemplate = function (Bl) {
			 
		      if (Bl.idCl === $scope.clients.selected.idCl) return 'edit';
		        else return 'display';

		   };
		   
		   $scope.editCl= function (Bl) {
		        $scope.clients.selected= angular.copy(Bl);
		    };
		    
		    $scope.saveCl = function (idx , id , nom , tel , fax , gsm , email , totaldu , seuil) {

		        $scope.clients.coms[idx] = angular.copy($scope.clients.selected);
		        
		        $http.get("/updateCl?idCl="+id+"&nomSoc="+nom+"&tel1="+tel+"&fax="+fax+"&gsm1="+gsm+"&email1="+email+"&totaldu="+totaldu+"&seuil="+seuil)
				.success(function(data){ 
					

				});
		        
		        $scope.reset();
		    };
		    
		    $scope.reset = function () {
		        $scope.clients.selected = {};
		    };
		
		

}) ;
