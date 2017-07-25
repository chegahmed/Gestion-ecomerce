var app=angular.module("listeBLFNT",[]) ;
app.controller("listeBLFNTController",function($scope,$http){

$scope.articlesLivraison = null ;
$scope.bonLivraisonF={ coms : [] , selected: {} } ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreBLParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getBLFNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
	.success(function(data){
		$scope.bonLivraisonF.coms = data ;		
	});
	

	
	$http.get("/nbreBLFNT")
	.success(function(data){
		$scope.nbrePages = (data/ $scope.nbreBLParPage ) ;
		pages = [];
		for(var i=0;i< $scope.nbrePages;i++) {
		  pages.push(i);
		}
		$scope.pages = pages;
	});
	
	
	$scope.charger=function(pos,mc){
		$scope.pageCourante = pos ;
		
		if(mc==null) {
		
		$http.get("/getBLFNT?position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			
			$scope.bonLivraisonF.coms = data ;
		});
		
	
		$http.get("/nbreBLFNT")
		.success(function(data){
			$scope.nbrePages = (data/ $scope.nbreBLParPage ) ;
			pages = [];
			for(var i=0;i< $scope.nbrePages;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});
		
		}
		
		else {
			
			
			$http.get("/RechercheBLByIdandNomFNT?motCle="+mc+"&position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonF.coms = data ;
				
			  }) ;
			 
			$http.get("/nbreBLFMCNT?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreBLParPage ) ;
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
		$http.get("/RechercheBLByIdandNomFNT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			$scope.bonLivraisonF.coms = data ;
			
		  }) ;
		
		$http.get("/nbreBLFMCNT?motCle="+motCle)
		.success(function(data){
			$scope.nbrePagesR = (data/ $scope.nbreBLParPage ) ;
			 pages = [];
			for(var i=0;i< $scope.nbrePagesR;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});

		}

	
	
	 $scope.addFactureAccompteF=function(idFac){
			
			$http.get("/addFactureAccompteFournisseur?idBl="+idFac)
			.success(function(data){ 
				$http.get("/getBLFNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
				.success(function(data){
					$scope.bonLivraisonF.coms = data ;		
				});
				
			});

			
		} 
	 
	 

	 
	 
	 $scope.getArticlesLivraison=function(idArtBl){
			$http.get("/getArticlesForLivraisonClient?idBl="+idArtBl)
			.success(function(data){ 
				$scope.articlesLivraison = data ;

			});

			
		} 
	 
	 
	 
	 
	 
	 $scope.getTemplate = function (Bl) {
		 
	      if (Bl.idBl === $scope.bonLivraisonF.selected.idBl) return 'edit';
	        else return 'display';

	   };
	   
	   $scope.editBl= function (Bl) {
	        $scope.bonLivraisonF.selected= angular.copy(Bl);
	    };
	    
	    $scope.saveBl = function (idx , id , date) {

	        $scope.bonLivraisonF.coms[idx] = angular.copy($scope.bonLivraisonF.selected);
	        
	        $http.get("/updateBl?id="+id+"&date="+date)
			.success(function(data){ 
				

			});
	        
	        $scope.reset();
	    };
	    
	    $scope.reset = function () {
	        $scope.bonLivraisonF.selected = {};
	    };
	    
	    
	    
	    
	    $scope.deleteBL=function(idBL){
			$http.get("/deleteBLF?idBL="+idBL)
			.success(function(data){ 
				
				$http.get("/getBLFNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
				.success(function(data){
					$scope.bonLivraisonF.coms = data ;		
				});
				
				
			});
			
			
			
			
			
		} 
	
		
}) ;
