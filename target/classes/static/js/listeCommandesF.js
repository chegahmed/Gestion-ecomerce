var app=angular.module("listeCommandesF",[]) ;
app.controller("listeCommandesFController",function($scope,$http){
	
	$scope.commandesF=[] ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreCommandeParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getCommandesF?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
	.success(function(data){
		$scope.commandesF = data ;		
	});
	
	$http.get("/allCommandesF")
	.success(function(data){

}) ;
	
	$http.get("/nbreCommandesF")
	.success(function(data){
		$scope.nbrePages = (data/ $scope.nbreCommandeParPage ) ;
		pages = [];
		for(var i=0;i< $scope.nbrePages;i++) {
		  pages.push(i);
		}
		$scope.pages = pages;
	});
	
	
	$scope.charger=function(pos,mc){
		$scope.pageCourante= pos ;
		if(mc==null) {
		
		$http.get("/getCommandesF?position="+(pos*$scope.nbreCommandeParPage)+"&nbre="+$scope.nbreCommandeParPage)
		.success(function(data){
			$scope.commandesF = data ;
		});
		
	
		$http.get("/nbreCommandesF")
		.success(function(data){
			$scope.nbrePages = (data/ $scope.nbreCommandeParPage ) ;
			pages = [];
			for(var i=0;i< $scope.nbrePages;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});
		
		}
		
		else {
			
			
			$http.get("/RechercheCommandeFByIdandNomF?motCle="+mc+"&position="+(pos*$scope.nbreCommandeParPage)+"&nbre="+$scope.nbreCommandeParPage)
			.success(function(data){
				$scope.commandesF = data ;
				
			  }) ;
			
			$http.get("/nbreCommandesFMC?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreCommandeParPage ) ;
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
		$http.get("/RechercheCommandeFByIdandNomF?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreCommandeParPage)
		.success(function(data){
			$scope.commandesF = data ;
			
		  }) ;
		
		$http.get("/nbreCommandesFMC?motCle="+motCle)
		.success(function(data){
			$scope.nbrePagesR = (data/ $scope.nbreCommandeParPage ) ;
			 pages = [];
			for(var i=0;i< $scope.nbrePagesR;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});

		}


		
}) ;
