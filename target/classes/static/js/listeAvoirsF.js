var app = angular.module("listeAvoirsF", []);
app.controller("listeAvoirsFController", function($scope, $http) {

	$scope.avoirsFournisseur={ coms : [] , selected: {} } ;
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreavoirParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	

	
	
	$http.get("/getAvoirFournisseur?position="+$scope.pageCourante+"&nbre="+$scope.nbreavoirParPage)
	.success(function(data){
		$scope.avoirsFournisseur.coms = data ;		
	});
	
	
	
	$http.get("/nbreAvoirFournisseur")
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
		
		$http.get("/getAvoirFournisseur?position="+(pos*$scope.nbreavoirParPage)+"&nbre="+$scope.nbreavoirParPage)
		.success(function(data){
			$scope.avoirsFournisseur.coms = data ;
		});
		
	
		$http.get("/nbreAvoirFournisseur")
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
			
			
			$http.get("/RechercheAvoirByIdandNomF?motCle="+mc+"&position="+(pos*$scope.nbreavoirParPage)+"&nbre="+$scope.nbreavoirParPage)
			.success(function(data){
				$scope.avoirsFournisseur.coms = data ;
				
			  }) ;
			
			$http.get("/nbreAvoirClientMF?motCle="+motCle)
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
		$http.get("/RechercheAvoirByIdandNomF?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreavoirParPage)
		.success(function(data){
			$scope.avoirsFournisseur.coms = data ;
			
		  }) ;
		
		$http.get("/nbreAvoirClientMF?motCle="+motCle)
		.success(function(data){
			$scope.nbrePagesR = (data/ $scope.nbreavoirParPage ) ;
			 pages = [];
			for(var i=0;i< $scope.nbrePagesR;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});

		}
	
	
	 $scope.deleteAvoirF=function(idAvCl){
			
			$http.get("/deleteAvoirF?idAv="+idAvCl)
			.success(function(data){ 

				$http.get("/getAvoirFournisseur?position="+$scope.pageCourante+"&nbre="+$scope.nbreavoirParPage)
				.success(function(data){
					$scope.avoirsFournisseur.coms = data ;		
				});
				
			});
			
		} 
	 
	 
	 
	 
	 
	 $scope.getTemplate = function (Bl) {
		 
	      if (Bl.idAv === $scope.avoirsFournisseur.selected.idAv) return 'edit';
	        else return 'display';

	   };
	   
	   $scope.editAv= function (Bl) {
	        $scope.avoirsFournisseur.selected= angular.copy(Bl);
	    };
	    
	    $scope.saveAv = function (idx , id , date) {

	        $scope.avoirsFournisseur.coms[idx] = angular.copy($scope.avoirsFournisseur.selected);
	        
	        $http.get("/updateAv?id="+id+"&date="+date)
			.success(function(data){ 
				

			});
	        
	        $scope.reset();
	    };
	    
	    $scope.reset = function () {
	        $scope.avoirsFournisseur.selected = {};
	    };
	 
	 
	 


	
});
