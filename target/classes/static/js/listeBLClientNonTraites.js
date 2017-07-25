var app=angular.module("listeBLClientNonTraites",[]) ;
app.controller("listeBLClientNonTraitesController",function($scope,$http){

	$scope.bonLivraisonClient={ coms : [] , selected: {} } ;

	
$scope.commandesClient=[] ;
	
	$scope.motCle = null ;
	$scope.pageCourante=0 ;
	$scope.nbreBLParPage=5000 ;
	$scope.nbrePages= 0 ;
	$scope.nbrePagesR= 0 ;
	var pages = [];
	
	$http.get("/getBLClientNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
	.success(function(data){
		$scope.bonLivraisonClient.coms = data ;		
	});
	
	$http.get("/allBLClientNT")
	.success(function(data){

		
}) ;
	

	
	
	$http.get("/nbreBLClientNT")
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
		
		$http.get("/getBLClientNT?position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			
			$scope.bonLivraisonClient.coms = data ;
		});
		
	
		$http.get("/nbreBLClientNT")
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
			
			
			$http.get("/RechercheBLByIdandNomClientNT?motCle="+mc+"&position="+(pos*$scope.nbreBLParPage)+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonClient.coms = data ;
				
			  }) ;
			
			$http.get("/nbreBLClientMCNT?motCle="+motCle)
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
		$http.get("/RechercheBLByIdandNomClientNT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreBLParPage)
		.success(function(data){
			$scope.bonLivraisonClient.coms = data ;
			
		  }) ;
		
		$http.get("/nbreBLClientMCNT?motCle="+motCle)
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
			
			
			
			$http.get("/getBLClientNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonClient.coms = data ;	
				
				
				
			});
			
			
			$http.get("/pdfLivraison?idBl="+idFac)
			.success(function(data){
				 

			}) ;
			
			
		});
		   
				
} 
	
	
$scope.addFactureAccompteClientM=function(){
	
	 var arrayLength = ( temp2.length -1 ) ;
	   
	   for (var i = 0; i <= (arrayLength); i++) {

	       
	      }
		
		$http.get("/addFactureAccompteClientM?data="+temp2)
		.success(function(data){
			
			
			
			$http.get("/getBLClientNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
			.success(function(data){
				$scope.bonLivraisonClient.coms = data ;	
				
				
				
			});
			
			
			$http.get("/pdfLivraison?idBl="+idFac)
			.success(function(data){
				 

			}) ;
			
			
		});
		   
				
} 
	
	
	
	$scope.active = null ;
	var temp2 = [];
	$scope.valider = function(id) {
		
		temp2.push(id) ;
		

		}
	
   $scope.lesCodes = function() {
	   
	   
	   $http.get("/listeInt?data="+temp2)
		.success(function(data){
			
			
			
		});
	   
	   
	   
	  /* var arrayLength = ( temp2.length -1 ) ;
	   
	   for (var i = 0; i <= (arrayLength); i++) {

	       alert("indice "+i+" valeur "+temp2[i]) ;
	       
	      }
	   
	   
	   alert("longeur = "+arrayLength) ;

		alert(temp2) ;*/

		}
	
	   
	   $scope.addBonLivraisonClient=function(idArtCom){
			
			$http.get("/addBonLivraisonClient?idComCl="+idArtCom)
			.success(function(data){ 
				alert(idArtCom) ;
				
				$http.get("/getBLClientNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
				.success(function(data){
					$scope.bonLivraisonClient.coms = data ;		
				});
				
			});
			
			

			
		} 
	   
	   
	   
	   
		 $scope.getTemplate = function (Bl) {
			 
		      if (Bl.idBl === $scope.bonLivraisonClient.selected.idBl) return 'edit';
		        else return 'display';

		   };
		   
		   $scope.editBl= function (Bl) {
		        $scope.bonLivraisonClient.selected= angular.copy(Bl);
		    };
		    
		    $scope.saveBl = function (idx , id , date) {

		        $scope.bonLivraisonClient.coms[idx] = angular.copy($scope.bonLivraisonClient.selected);
		        
		        $http.get("/updateBl?id="+id+"&date="+date)
				.success(function(data){ 
					

				});
		        
		        $scope.reset();
		    };
		    
		    $scope.reset = function () {
		        $scope.bonLivraisonClient.selected = {};
		    };
		    
		    
		    
		    
		    $scope.deleteBLClient=function(idBL){
				$http.get("/deleteBL?idBL="+idBL)
				.success(function(data){ 
					
					$http.get("/getBLClientNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreBLParPage)
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
		    
		
		    

		
}) ;
