var app=angular.module("listeFactureClientNR",[]) ;
app.controller("listeFactureClientNRController",function($scope,$http){
	
		
	    $scope.factures={ coms : [] , selected: {} } ;
	    $scope.montantF=0 ;
	    $scope.totalPaye=0 ;
	    $scope.totaldu=0 ;
	    $scope.totaldu2=0 ;
		$scope.motCle = null ;
		$scope.pageCourante=0 ;
		$scope.nbreFacParPage=5 ;
		$scope.nbrePages= 0 ;
		$scope.nbrePagesR= 0 ;
		$scope.codeFacture = 0 ;
		var pages = [];
		
		$http.get("/getFacClientNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
		.success(function(data){
			$scope.factures.coms = data ;		
		});
		
		$scope.factureClient=[] ;
		$http.get("/getFactureClientNT")
		.success(function(data){
		// $scope.factureClient.coms = data ;
}) ;
		
	
		
		$http.get("/nbreFacClientNT")
		.success(function(data){
			$scope.nbrePages = (data/ $scope.nbreFacParPage ) ;
			pages = [];
			for(var i=0;i< $scope.nbrePages;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});
		
		
		$scope.charger=function(pos,mc){
			$scope.pageCourante=pos ;
			if(mc==null) {
			
			$http.get("/getFacClientNT?position="+(pos*$scope.nbreFacParPage)+"&nbre="+$scope.nbreFacParPage)
			.success(function(data){
				$scope.factures.coms = data ;
			});
			
		
			$http.get("/nbreFacClientNT")
			.success(function(data){
				$scope.nbrePages = (data/ $scope.nbreFacParPage ) ;
				pages = [];
				for(var i=0;i< $scope.nbrePages;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});
			
			}
			
			else {
				
				
				$http.get("/RechercheFacByIdandNomClientNT?motCle="+mc+"&position="+(pos*$scope.nbreFacParPage)+"&nbre="+$scope.nbreFacParPage)
				.success(function(data){
					$scope.factures.coms = data ;
					
				  }) ;
				
				$http.get("/nbreFacClientMCNT?motCle="+motCle)
				.success(function(data){
					$scope.nbrePagesR = (data/ $scope.nbreFacParPage ) ;
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
			$http.get("/RechercheFacByIdandNomClientNT?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbreFacParPage)
			.success(function(data){
				$scope.factures.coms = data ;
				
			  }) ;
			
			$http.get("/nbreFacClientMCNT?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbreFacParPage ) ;
				 pages = [];
				for(var i=0;i< $scope.nbrePagesR;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});

			} 

		
		
		 $scope.paiementFacture=function(idFac , idCl , nomClient , montantFac ){
			 
			    
			 $http.get("/getModePaiementClient?idCl="+idCl)
				.success(function(data){ 
					$scope.modes = [] ;
					$scope.modes = data ;
					$scope.codeFac = idFac ;
					$scope.montantFac = montantFac ;
					
					$http.get("/getClientById?idCl="+idCl)
					.success(function(data2){ 
						$scope.totaldu = 0;
						$scope.totaldu2 = data2.totaldu ;
						$scope.totalPaye = montantFac + $scope.totaldu2 ; 
					
					});
					

				});
			 			 
			 
	
			} 
		 
		 
		 $scope.updatePrix=function(total){
			 
			
			 
			 $scope.totaldu = $scope.totaldu2 - total + $scope.montantFac ;
			 
		 }
		 
		 
		 $scope.visible=true ;
			$scope.onSave = function(){
				

		        if($scope.form.$valid){


		        }
				
		    }
			
			
			$scope.visible2=true ;
			$scope.onSave2 = function(){
				

		        if($scope.form2.$valid){


		        }
				
		    }
			
		 
		 $scope.reglementFacture=function(idFac , modeP , montantF , total ){
			 if(modeP==undefined  ) {
				 alert("Veuillez choisir un mode de paiement" ) ; 
			 }
			 
			 else {
			  $http.get("/reglementFacture?idF="+idFac+"&idM="+modeP+"&montantF="+montantF+"&totaldu="+total)
				.success(function(data){
					
					
					
					$http.get("/getFacClientNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
					.success(function(data){
						$scope.factures.coms = data ;
						
						$http.get("/pdfFacture?idFac="+idFac)
						.success(function(data){
							 

						}) ;
						
						
					});
					
					alert("Votre reglement est Fait");
			
					
					
				});
			  
			 }
			 
			
				
			} 
		 
		 $scope.deleteFactureClient=function(idF){
				
				$http.get("/deleteFacture?idF="+idF)
				.success(function(data){ 
					
					$http.get("/getFacClientNT?position="+$scope.pageCourante+"&nbre="+$scope.nbreFacParPage)
					.success(function(data){
						$scope.factures.coms = data ;		
					});

				});
				
			
				
			} 
		 
		 
		 $scope.getArticlesFacture=function(idFac){
				$http.get("/getArticlesFacture?idFac="+idFac)
				.success(function(data){ 
					$scope.articlesFacture = data ;
				});

				
			} 
		 
		 
		 
		 $scope.getTemplate = function (Fac) {
			 
		      if (Fac.idFac === $scope.factures.selected.idFac) return 'edit';
		        else return 'display';

		   };
		   
		   $scope.vCode = 0 ;
		   $scope.editFacture= function (code , login , pass) {
			   $scope.verif1 = 0 ;
			   $scope.vCode = code ;
			
			 
			   $scope.fac = null ;
			   $http.get("/getFactureById?idFac="+$scope.vCode)
				.success(function(data){
					$scope.fac = data ;
					//$scope.factures.selected= angular.copy($scope.fac);
					
					 $http.get("/auth?login="+login+"&pass="+pass)
						.success(function(data2){ 
		                   					
							$scope.verif1 = data2 ;
							if($scope.verif1 == 1) {
							//	alert("id selectionne "+ $scope.factures.selected) ;
							       $scope.factures.selected= angular.copy($scope.fac);

							    }
								   else {
									   
									   alert("Authentification incorrecte") ;
									   
									   
								   }
						});
					
				   
				}) ;
			   
			  
			   
			
		    };
		    
		   
		    $scope.editFacture1= function (id) {
				   
		    	$scope.codeFacture = id ;
				   
			    };
		    
		    
		    $scope.saveFacture = function (idx , id , date) {

		        $scope.factures.coms[idx] = angular.copy($scope.factures.selected);
		        
		        $http.get("/updateF?id="+id+"&date="+date)
				.success(function(data){ 
					

				});
		        
		        $scope.reset();
		    };
		    
		    $scope.reset = function () {
		        $scope.factures.selected = {};
		    };
		    
		    $scope.imprimerFac=function(idFac){
				   
				   
				   $http.get("/getFactureById?idFac="+idFac)
					.success(function(data){
						 $window.open("http://localhost:8084/pdf/"+data.fichierFac, "_blank");
				   }) ;   

				} 
		    
		    
		    $scope.verif=function(){
				
				alert("dans deco");

				$http.get("/auth")
				.success(function(data){


				});

				
			} ;
		  

		
		
}) ;
