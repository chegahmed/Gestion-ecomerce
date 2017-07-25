var app=angular.module("listeBLF",[]) ;
app.controller("listeBLFController",function($scope,$http){

$scope.articlesLivraison = null ;
$scope.bonLivraisonF=[] ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreBLParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getBLF?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
	.success(function(data){
		$scope.bonLivraisonF = data ;		
	});
	

	
	$http.get("/nbreBLF")
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
		
		$http.get("/getBLF?position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			
			$scope.bonLivraisonF = data ;
		});
		
	
		$http.get("/nbreBLF")
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
			
			
			$http.get("/RechercheBLByIdandNomF?motCle="+mc+"&position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonF = data ;
				
			  }) ;
			 
			$http.get("/nbreBLFMC?motCle="+motCle)
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
		$http.get("/RechercheBLByIdandNomF?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			$scope.bonLivraisonF = data ;
			
		  }) ;
		
		$http.get("/nbreBLFMC?motCle="+motCle)
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
			
			$http.get("/addFactureAccompteF?idBl="+idFac)
			.success(function(data){ 
				$http.get("/getBLF?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
				.success(function(data){
					$scope.bonLivraisonF = data ;		
				});
				
			});

			
		} 
	 
	 
	 $scope.deleteBLF=function(idBL){
			$http.get("/deleteBL?idBL="+idBL)
			.success(function(data){ 
				$http.get("/getBLF?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
				.success(function(data){
					$scope.bonLivraisonF = data ;		
				});
				
			});
			
		} 
	 
	 
	 
	 $scope.getArticlesLivraison=function(idArtBl){
			$http.get("/getArticlesForLivraison?idBl="+idArtBl)
			.success(function(data){ 
				$scope.articlesLivraison = data ;

			});

			
		} 
	 
		
		
}) ;
