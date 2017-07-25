var app=angular.module("listeBLFT",[]) ;
app.controller("listeBLFTController",function($scope,$http){

$scope.articlesLivraison = null ;
$scope.bonLivraisonF=[] ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreBLParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getBLFT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
	.success(function(data){
		$scope.bonLivraisonF = data ;		
	});
	

	
	$http.get("/nbreBLFT")
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
		
		$http.get("/getBLFT?position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			
			$scope.bonLivraisonF = data ;
		});
		
	
		$http.get("/nbreBLFT")
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
			
			
			$http.get("/RechercheBLByIdandNomFT?motCle="+mc+"&position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonF = data ;
				
			  }) ;
			 
			$http.get("/nbreBLFMCT?motCle="+motCle)
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
		$http.get("/RechercheBLByIdandNomFT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			$scope.bonLivraisonF = data ;
			
		  }) ;
		
		$http.get("/nbreBLFMCT?motCle="+motCle)
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
			
			$http.get("/addFactureAccompteFT?idBl="+idFac)
			.success(function(data){ 
				$http.get("/getBLFT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
				.success(function(data){
					$scope.bonLivraisonF = data ;		
				});
				
			});

			
		} 
	 
	 
	 $scope.deleteBL=function(idBl){
		   alert(idBl) ;
			$http.get("/deleteBLF?idBL="+idBl)
			.success(function(data){ 
				
				
			});
			
			$http.get("/getBLFT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonF = data ;		
			});
			
		} 
	 
	 
	 
	 $scope.getArticlesLivraison=function(idArtBl){
			$http.get("/getArticlesForLivraisonClient?idBl="+idArtBl)
			.success(function(data){ 
				$scope.articlesLivraison = data ;

			});

			
		} 
	 
		
		
}) ;
