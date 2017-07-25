var app=angular.module("listeArticles",[]) ;
app.controller("listeArticlesController",function($scope,$http){


	
		
		
		
		$scope.articles={ coms : [] , selected: {} } ;
		
		$scope.motCle = null ;
		$scope.pageCourante=0 ;
		$scope.nbrearticlesParPage=5 ;
		$scope.nbrePages= 0 ;
		$scope.nbrePagesR= 0 ;
		var pages = [];
		
		$http.get("/getArticle?position="+$scope.pageCourante+"&nbre="+$scope.nbrearticlesParPage)
		.success(function(data){
			$scope.articles.coms = data ;		
		});
		

		
		$http.get("/nbreArticle")
		.success(function(data){
			$scope.nbrePages = (data/ $scope.nbrearticlesParPage ) ;
			pages = [];
			for(var i=0;i< $scope.nbrePages;i++) {
			  pages.push(i);
			}
			$scope.pages = pages;
		});
		
		
		$scope.charger=function(pos,mc){
			
			if(mc==null) {
			
			$http.get("/getArticle?position="+(pos*$scope.nbrearticlesParPage)+"&nbre="+$scope.nbrearticlesParPage)
			.success(function(data){
				$scope.articles.coms = data ;
			});
			
		
			$http.get("/nbreArticle")
			.success(function(data){
				$scope.nbrePages = (data/ $scope.nbrearticlesParPage ) ;
				pages = [];
				for(var i=0;i< $scope.nbrePages;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});
			
			}
			
			else {
				
				
				$http.get("/RechercheArticleByIdandNom?motCle="+mc+"&position="+(pos*$scope.nbrearticlesParPage)+"&nbre="+$scope.nbrearticlesParPage)
				.success(function(data){
					$scope.articles.coms = data ;
					
				  }) ;
				
				$http.get("/nbreArticleMC?motCle="+motCle)
				.success(function(data){
					$scope.nbrePagesR = (data/ $scope.nbrearticlesParPage ) ;
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
			$http.get("/RechercheArticleByIdandNom?motCle="+motCle+"&position="+$scope.nbreMC+"&nbre="+$scope.nbrearticlesParPage)
			.success(function(data){
				$scope.articles.coms = data ;
				
			  }) ;
			
			$http.get("/nbreArticleMC?motCle="+motCle)
			.success(function(data){
				$scope.nbrePagesR = (data/ $scope.nbrearticlesParPage ) ;
				 pages = [];
				for(var i=0;i< $scope.nbrePagesR;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});

			}
		
		
		
		$scope.getTemplate = function (Bl) {
			 
		      if (Bl.idArt=== $scope.articles.selected.idArt) return 'edit';
		        else return 'display';

		   };
		   
		   $scope.editA= function (Bl) {
		        $scope.articles.selected= angular.copy(Bl);
		    };
		    
		    $scope.saveA = function (idx , id , designation , etat , stockreel , stockmin , stockmax , prixvente , prixachat) {

		        $scope.articles.coms[idx] = angular.copy($scope.articles.selected);
		        
		        $http.get("/updateArticle?idArt="+id+"&designation="+designation+"&etat="+etat+"&stockreel="+stockreel+"&stockmin="+stockmin+"&prixvente="+prixvente+"&prixachat="+prixachat)
				.success(function(data){ 
					

				});
		        
		        $scope.reset();
		    };
		    
		    $scope.reset = function () {
		        $scope.articles.selected = {};
		    };
		    
		    
      $scope.deleteArticle=function(idF){
    	  
				
				$http.get("/deleteArticle?id="+idF)
				.success(function(data){ 
					
					$http.get("/getArticle?position="+$scope.pageCourante+"&nbre="+$scope.nbrearticlesParPage)
					.success(function(data){
						$scope.articles.coms = data ;		
					});

					
					
				});
				
				
				
			} 
		
		
		

}) ;
