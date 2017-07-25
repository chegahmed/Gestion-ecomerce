var app=angular.module("listeBLClient",[]) ;
app.controller("listeBLClientController",function($scope,$http){

	
$scope.bonLivraisonClient={ coms : [] , selected: {} } ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreBLParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getBLClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
	.success(function(data){
		$scope.bonLivraisonClient.coms = data ;		
	});
	

	
	$http.get("/nbreBLClient")
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
		
		$http.get("/getBLClient?position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			
			$scope.bonLivraisonClient.coms = data ;
		});
		
	
		$http.get("/nbreBLClient")
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
			
			
			$http.get("/RechercheBLByIdandNomClient?motCle="+mc+"&position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonClient.coms = data ;
				
			  }) ;
			
			$http.get("/nbreBLClientMC?motCle="+motCle)
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
		$http.get("/RechercheBLByIdandNomClient?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			$scope.bonLivraisonClient.coms = data ;
			
		  }) ;
		
		$http.get("/nbreBLClientMC?motCle="+motCle)
		.success(function(data){
			$scope.nbrePagesR = (data/ $scope.nbreBLParPage ) ;
			 pages = [];
			for(var i=0;i< $scope.nbrePagesR;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});

		}

	
	
	 $scope.addFactureAccompteClient=function(idFac){
			
			$http.get("/addFactureAccompteClient?idBl="+idFac)
			.success(function(data){ 
				$http.get("/getBLClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
				.success(function(data){
					$scope.bonLivraisonClient.coms = data ;		
				});
				
			});

			
		} 
	 
	 
	 $scope.deleteBLClient=function(idBL){
			$http.get("/deleteBL?idBL="+idBL)
			.success(function(data){ 
				$http.get("/getBLClient?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
				.success(function(data){
					$scope.bonLivraisonClient.coms = data ;		
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
		 
	      if (Bl.idBl === $scope.bonLivraisonClient.selected.idBl) return 'edit';
	        else return 'display';

	   };
	   
	   $scope.editBl= function (Bl) {
	        $scope.bonLivraisonClient.selected= angular.copy(Bl);
	    };
	    
	    $scope.saveCommande = function (idx , id , date) {

	        $scope.bonLivraisonClient.coms[idx] = angular.copy($scope.bonLivraisonClient.selected);
	        
	        $http.get("/updateBl?id="+id+"&date="+date)
			.success(function(data){ 
				

			});
	        
	        $scope.reset();
	    };
	    
	    $scope.reset = function () {
	        $scope.bonLivraisonClient.selected = {};
	    };
	    
	    

	 
	 
	 
	 
	    $scope.imprimerBl=function(idBl){
			   
			   
			   $http.get("/getBlById?idBl="+idBl)
				.success(function(data){
					 $window.open("http://localhost:8084/pdf/"+data.fichierBl, "_blank");
			   }) ;   

			} 
	 
	 
	 
	 
	 
		
		
}) ;
