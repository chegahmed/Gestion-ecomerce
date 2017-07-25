var app=angular.module("ajouterCategorie",[]) ;
app.controller("ajouterCategorieController",function($scope,$http){

	$scope.nomCat = null ;


		$scope.categories=[] ;
			
			$scope.motCle = null ;
			$scope.pageCourante=0 ;
			$scope.nbrecategoriesParPage=5 ;
			$scope.nbrePages= 0 ;
			$scope.nbrePagesR= 0 ;
			var pages = [];
			
			$http.get("/getCategorie?position="+$scope.pageCourante+"&nbre="+$scope.nbrecategoriesParPage)
			.success(function(data){
				$scope.categories = data ;		
			});
			

			
			$http.get("/nbreCategorie")
			.success(function(data){
				$scope.nbrePages = (data/ $scope.nbrecategoriesParPage ) ;
				pages = [];
				for(var i=0;i< $scope.nbrePages;i++) {
				  pages.push(i);
				}
				$scope.pages = pages;
			});
			
			
			$scope.charger=function(pos,mc){
				
				if(mc==null) {
				
				$http.get("/getCategorie?position="+(pos*$scope.nbrecategoriesParPage)+"&nbre="+$scope.nbrecategoriesParPage)
				.success(function(data){
					$scope.categories = data ;
				});
				
			
				$http.get("/nbreCategorie")
				.success(function(data){
					$scope.nbrePages = (data/ $scope.nbrecategoriesParPage ) ;
					pages = [];
					for(var i=0;i< $scope.nbrePages;i++) {
					  pages.push(i);
					}
					$scope.pages = pages;
				});
				
				}
				
				else {
					
					
					$http.get("/RechercheCategorieByIdandNom?motCle="+mc+"&position="+(pos*$scope.nbrecategoriesParPage)+"&nbre="+$scope.nbrecategoriesParPage)
					.success(function(data){
						$scope.categories = data ;
						
					  }) ;
					
					$http.get("/nbreCategorieMC?motCle="+motCle)
					.success(function(data){
						$scope.nbrePagesR = (data/ $scope.nbrecategoriesParPage ) ;
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
				$http.get("/RechercheCategorieByIdandNom?motCle="+motCle+"&position=0&nbre=1000")
				.success(function(data){
					$scope.categories = data ;
					
				  }) ;
				
				$http.get("/nbreCategorieMC?motCle="+motCle)
				.success(function(data){
					$scope.nbrePagesR = (data/ $scope.nbrecategoriesParPage ) ;
					 pages = [];
					for(var i=0;i< $scope.nbrePagesR;i++) {
					  pages.push(i);
					}
					$scope.pages = pages;
				});

				}
			

		
		
		
		
		
		
		
		
		
		$scope.ajouterCategorie=function(){
			$http.get("/addCategorie?nomCat="+$scope.nomCat)
			.success(function(data){
               
				$http.get("/getCategorie?position="+$scope.pageCourante+"&nbre="+$scope.nbrecategoriesParPage)
				.success(function(data){
					$scope.categories = data ;		
				});

				

			});
		} ;
		
	
}) ;
