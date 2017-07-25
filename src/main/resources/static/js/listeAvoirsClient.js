var app=angular.module("listeAvoirsClient",[]) ;
app.controller("listeAvoirsClientController",function($scope,$http){

		
		
		
		
		$scope.avoirsClient={ coms : [] , selected: {} } ;
		
		$scope.motCle = null ;
		$scope.pageCourante=0 ;
		$scope.nbreavoirParPage=5 ;
		$scope.nbrePages= 0 ;
		$scope.nbrePagesR= 0 ;
		var pages = [];
		
		$http.get("/getAvoirClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreavoirParPage)
		.success(function(data){
			$scope.avoirsClient.coms = data ;		
		});
		

		
		$http.get("/nbreAvoirClient")
		.success(function(data){
			$scope.nbrePages = (data/ $scope.nbreavoirParPage ) ;
			pages = [];
			for(var i=0;i< $scope.nbrePages;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});
		
		
		$scope.charger=function(pos,mc){
			
			if(mc==null) {
			
			$http.get("/getAvoirClient?position="+(pos*$scope.nbreavoirParPage)+"&nbre="+$scope.nbreavoirParPage)
			.success(function(data){
				$scope.avoirsClient.coms = data ;
			});
			
		
			$http.get("/nbreAvoirClient")
			.success(function(data){
				$scope.nbrePages = (data/ $scope.nbreavoirParPage ) ;
				pages = [];
				for(var i=0;i< $scope.nbrePages;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});
			
			}
			
			else {
				
				
				$http.get("/RechercheAvoirByIdandNomClient?motCle="+mc+"&position="+(pos*$scope.nbreavoirParPage)+"&nbre="+$scope.nbreavoirParPage)
				.success(function(data){
					$scope.avoirsClient.coms = data ;
					
				  }) ;
				
				$http.get("/nbreAvoirClientMC?motCle="+motCle)
				.success(function(data){
					$scope.nbrePagesR = (data/ $scope.nbreavoirParPage ) ;
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
			$http.get("/RechercheAvoirByIdandNomClient?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreavoirParPage)
			.success(function(data){
				$scope.avoirsClient.coms = data ;
				
			  }) ;
			
			$http.get("/nbreAvoirClientMC?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreavoirParPage ) ;
				 pages = [];
				for(var i=0;i< $scope.nbrePagesR;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});

			}
		
		
		   $scope.deleteAvoir=function(idAvCl){
				
				$http.get("/deleteAvoir?idAv="+idAvCl)
				.success(function(data){ 

					$http.get("/getAvoirClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreavoirParPage)
					.success(function(data){
						$scope.avoirsClient.coms = data ;		
					});
					
				});
				
			} 
		   
		   
		   $scope.getTemplate = function (Bl) {
				 
			      if (Bl.idAv === $scope.avoirsClient.selected.idAv) return 'edit';
			        else return 'display';

			   };
			   
			   $scope.editAv= function (Bl) {
			        $scope.avoirsClient.selected= angular.copy(Bl);
			    };
			    
			    $scope.saveAv = function (idx , id , date) {

			        $scope.avoirsClient.coms[idx] = angular.copy($scope.avoirsClient.selected);
			        
			        $http.get("/updateAv?id="+id+"&date="+date)
					.success(function(data){ 
						

					});
			        
			        $scope.reset();
			    };
			    
			    $scope.reset = function () {
			        $scope.avoirsClient.selected = {};
			    };



}) ;
