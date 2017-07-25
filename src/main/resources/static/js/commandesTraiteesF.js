var app=angular.module("listeCommandesFT",[]) ;
app.controller("listeCommandesFTController",function($scope,$http){
	
	$scope.commandesF=[] ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreCommandeParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getCommandesFT?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
	.success(function(data){
		$scope.commandesF = data ;		
	});
	
	$http.get("/allCommandesFT")
	.success(function(data){

}) ;
	
	$http.get("/nbreCommandesFT")
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
		
		$http.get("/getCommandesFT?position="+(pos*$scope.nbreCommandeParPage)+"&nbre="+$scope.nbreCommandeParPage)
		.success(function(data){
			$scope.commandesF = data ;
		});
		
	
		$http.get("/nbreCommandesFT")
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
			
			
			$http.get("/RechercheCommandeFByIdandNomFT?motCle="+mc+"&position="+(pos*$scope.nbreCommandeParPage)+"&nbre="+$scope.nbreCommandeParPage)
			.success(function(data){
				$scope.commandesF = data ;
				
			  }) ;
			
			$http.get("/nbreCommandesFMCT?motCle="+motCle)
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
		$http.get("/RechercheCommandeFByIdandNomFT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreCommandeParPage)
		.success(function(data){
			$scope.commandesF = data ;
			
		  }) ;
		
		$http.get("/nbreCommandesFMCT?motCle="+motCle)
		.success(function(data){
			$scope.nbrePagesR = (data/ $scope.nbreCommandeParPage ) ;
			 pages = [];
			for(var i=0;i< $scope.nbrePagesR;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});

		}
	
	
	
	
	
	 $scope.deleteCommandeF=function(idComCl){
			
			$http.get("/deleteCommandeF?idFour="+idComCl)
			.success(function(data){ 
				
				
				
			});
			
			
			$http.get("/getCommandesFT?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
			.success(function(data){
				$scope.commandesF = data ;		
			});
			
			
		}
	
	
	
	


		
}) ;
