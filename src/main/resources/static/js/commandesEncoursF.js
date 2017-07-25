var app=angular.module("listeCommandesFNT",[]) ;
app.controller("listeCommandesFNTController",function($scope,$http){
	
	$scope.commandesF={ coms : [] , selected: {} } ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreCommandeParPage=5 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getCommandesFNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
	.success(function(data){
		$scope.commandesF.coms = data ;		
	});
	
	$http.get("/allCommandesFNT")
	.success(function(data){

}) ;
	
	$http.get("/nbreCommandesFNT")
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
		
		$http.get("/getCommandesFNT?position="+(pos*$scope.nbreCommandeParPage)+"&nbre="+$scope.nbreCommandeParPage)
		.success(function(data){
			$scope.commandesF.coms = data ;
		});
		
	
		$http.get("/nbreCommandesFNT")
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
			
			
			$http.get("/RechercheCommandeFByIdandNomFNT?motCle="+mc+"&position="+(pos*$scope.nbreCommandeParPage)+"&nbre="+$scope.nbreCommandeParPage)
			.success(function(data){
				$scope.commandesF.coms = data ;
				
			  }) ;
			
			$http.get("/nbreCommandesFMCNT?motCle="+motCle)
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
		$http.get("/RechercheCommandeFByIdandNomFNT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreCommandeParPage)
		.success(function(data){
			$scope.commandesF.coms = data ;
			
		  }) ;
		
		$http.get("/nbreCommandesFMCNT?motCle="+motCle)
		.success(function(data){
			$scope.nbrePagesR = (data/ $scope.nbreCommandeParPage ) ;
			 pages = [];
			for(var i=0;i< $scope.nbrePagesR;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});

		}
	
	
	 $scope.getTemplate = function (comCl) {
		 
	      if (comCl.idFour === $scope.commandesF.selected.idFour) return 'edit';
	        else return 'display';

	   };
	   
	   $scope.editCommande= function (comCl) {
	        $scope.commandesF.selected= angular.copy(comCl);
	    };
	    
	    $scope.saveCommande = function (idx , id , date) {

	    	$scope.commandesF.coms[idx] = angular.copy($scope.commandesF.selected);
	        
	        $http.get("/updateCommandeF?id="+id+"&date="+date)
			.success(function(data){ 

			});
	        
	        $scope.reset();
	    };
	    
	    $scope.reset = function () {
	        $scope.commandesF.selected = {};
	    };
	    
	    
 $scope.deleteCommandeF=function(idComCl){
			
			$http.get("/deleteCommandeF?idFour="+idComCl)
			.success(function(data){ 
				$http.get("/getCommandesFNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
				.success(function(data){
					$scope.commandesF.coms = data ;		
				});
			});
			
			
			
			
			
		} 
 
 
 
 $scope.addBonLivraisonFour=function(idArtCom){

		
		$http.get("/addBonLivFour?idComFour="+idArtCom)
		.success(function(data){ 
			
			
			$http.get("/getCommandesFNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreCommandeParPage)
			.success(function(data){
				$scope.commandesF.coms = data ;		
			});

			
			
		});
		

		
	} 


		
}) ;
