
var app=angular.module("ajouterFactureDirecte",['ui.bootstrap','localytics.directives']) ;

app.controller("ajouterFactureDirecteController", function($scope,$http,$filter, $q, $timeout){

	$scope.modePaie = null ;
	$scope.numCompte = null ;
	$scope.nomBanque = null ;
	$scope.numCheque = null ;
	
	$scope.codeArt=[""] ;
	$scope.desArt=[""] ;
	$scope.qteArt=[""] ;
	$scope.prixArt=[""] ;
	$scope.prixtotal=[""] ;
	$scope.prixtotalCommande=[""] ;
	$scope.dateComCl= null ;
	$scope.codeComCl=2 ;
	$scope.size=5;

	
	    
	$scope.designation = null ;
	$scope.etat = null ;
	$scope.stockmin = 0 ;
	$scope.stockmax = 0 ;
	$scope.stockreel = 0 ;
	$scope.prixachat = 0 ;
	$scope.prixvente = 0 ;
	$scope.idCat = 1 ;
	
	$scope.idComCl = 0 ;
	$scope.idArtCom = 3 ;
	$scope.articlesCommande=[] ;
	
  $scope.articles=[] ;
	$http.get("/articlesComCl")
	.success(function(data){
	 $scope.articles = data ;
}) ;
	
	/*$scope.articles=[] ;
	$http.get("/lesArticles")
	.success(function(data){
	 $scope.articles = data ;
}) ;
	*/

	$scope.codeSelection = function(code) {
	       
		$scope.nomComCl = code ;
		
		$http.get("/getLastRepresentantClient?idCl="+code)
		.success(function(data){ 
			$scope.rep = data ;
			$scope.codeRep = $scope.rep.id ;
			$scope.nomRep = $scope.rep.nomRep ;
			$scope.telRep = $scope.rep.telRep;
			
			
		});
		

		
		}
	
	$scope.nomSelection = function(nom) {
		$scope.codeComCl = nom ;
		
		$http.get("/getLastRepresentantClient?idCl="+nom)
		.success(function(data){ 
			$scope.rep = data ;
			$scope.codeRep = $scope.rep.id ;
			$scope.nomRep = $scope.rep.nomRep ;
			$scope.telRep = $scope.rep.telRep;
			
			
		});
		
		}
	
	

	$scope.autocompleteDesignation = function(des,i) {
		$http.get("/getArticleListeByDesignation?designation="+des)
		.success(function(data){
			$scope.article = data ;
			$scope.codeArt[i]=$scope.article.idArt ;
			$scope.prixArt[i]=$scope.article.prixvente;
			$scope.qteArt[i]=1 ;
			$scope.calculPrixTotal(i) ;
			
		  }) ;

		}
	
	
	$scope.autocompleteCode = function(code,i) {
		$http.get("/getArticleByCode?code="+code)
		.success(function(data){
			$scope.article = data ;
			$scope.desArt[i]=$scope.article.desArt ;
			$scope.prixArt[i]=$scope.article.prixvente ;
			$scope.qteArt[i]=1 ;
			$scope.calculPrixTotal(i) ;
			
		  }) ;

		}
	
	
	$scope.calculPrixTotal = function(i) {
		   
		$scope.prixtotal[i]=$scope.prixArt[i] * $scope.qteArt[i] ; 
		$scope.prix = 0 ;
		for(j=0 ; j<=i ; j++) {
			$scope.prix = $scope.prix + $scope.prixtotal[j] ;
		}
		
		$scope.prixtotalCommande[i] = $scope.prix ;
		
		}

	
	
	$scope.addrow = function() {
		
		$scope.codeArt.push("");
		$scope.desArt.push("");
		$scope.qteArt.push("");
		$scope.prixArt.push("");
		$scope.prixtotal.push("");
		$scope.prixtotalCommande.push("");

		}
	
$scope.duppliquerrow = function(index) {
	    var arrayLength = ( $scope.codeArt.length) ;
		$scope.codeArt.push($scope.codeArt[index]);
		$scope.desArt.push($scope.desArt[index]);
		$scope.qteArt.push($scope.qteArt[index]);
		$scope.prixArt.push($scope.prixArt[index]);
		$scope.prixtotal.push($scope.prixtotal[index]);
		$scope.calculPrixTotal(arrayLength) ;
		}
	
	 $scope.removeRow = function(index) {
	        $scope.codeArt.push(index, 1);
			$scope.desArt.push(index, 1);
			$scope.qteArt.push(index, 1);
			$scope.prixArt.push(index, 1);
			$scope.prixtotal.push(index, 1);
			$scope.prixtotalCommande.push(index, 1);
	    }
	
	

	 $scope.clients=[] ;
		$http.get("/allClient")
		.success(function(data){
		 $scope.clients = data ;
}) ;
		  
		  
		  $scope.arts=[] ;
			$http.get("/allArticles")
			.success(function(data){
			 $scope.arts = data ;
	}) ;
		  

	    $scope.categories=[] ;
		$http.get("/allCategories")
		.success(function(data){
		 $scope.categories = data ;
}) ;
		
		$scope.articles=[] ;
		$http.get("/allArticles")
		.success(function(data){
		 $scope.articles = data ;
}) ;
		
		
		$scope.autoraticles=[] ;
		$http.get("/allArticles")
		.success(function(data){
		 $scope.autoarticles = data ;
		 
}) ;
		
		

		
		$scope.commandesClient=[] ;
		$http.get("/allCommandesClient")
		.success(function(data){
		 $scope.commandesClient = data ;
}) ;
		
		$scope.bonLivraisonClient=[] ;
		$http.get("/allBonLivraisonClient")
		.success(function(data){
		 $scope.bonLivraisonClient = data ;
}) ;
		

		
		
		
		var addArticles = function(i){
			
			  $http.get("/addArticleToCommande?quantiteAl="+$scope.qteArt[i]+"&designationAl="+$scope.desArt[i]+"&prixUnitAl="+$scope.prixArt[i]+"&prixtotalAl="+$scope.prixtotal[i]+"&idArt="+$scope.codeArt[i])
			    .success(function(data) {

			     }) ;
			 
			  }

		
		
		
		$scope.ajouterFactureDirecte=function(){
			
			
			var arrayLength = ( $scope.codeArt.length - 1) ;
			var d = $scope.dateComCl ;
			d = $filter('date')(d, "dd/MM/yyyy");

			    $http.get("/addCommandeClientD?dateComCl="+d+"&dateVraiComCl="+d+"&prixTotalCommande="+$scope.prixtotalCommande[arrayLength]+"&idCl="+$scope.codeComCl)
			     .success(function(data){   

			    	 for (var i = 0; i <= (arrayLength); i++) {

			    		       addArticles(i) ;
				    	      }

			           });

			    
			         //alert("Commande Ajoutée") ;
			       //  window.location.href = 'http://localhost:8084/ajouterCommandeClient.html';
			
			
			
			    //Ajout de la facture
			    
			    
			    
			    
			    
			    if($scope.modePaie  == "Espece") {
					
						
					
						$http.get("/addFactureDirecteE?nomMode="+$scope.modePaie)
						.success(function(data){ 
							alert("facture bien ajoutée") ;
						}); 
						
					
					
				   }
					
					if($scope.modePaie  == "Virement Bancaire") {
					
							
							$http.get("/addFactureDirecteB?nomMode="+$scope.modePaie+"&numCompte="+$scope.numCompte+"&nomBanque="+$scope.nomBanque)
							.success(function(data){ 
								alert("facture bien ajoutée") ;
							}); 

						
					   }
					
					if($scope.modePaie  == "Cheque") {
					
							$http.get("/addFactureDirecteC?nomMode="+$scope.modePaie+"&numCheque="+$scope.numCheque+"&nomBanque="+$scope.nomBanque)
							.success(function(data){ 
								alert("facture bien ajoutée") ;
							}); 

													
					   }


		} ;
		

		
		$scope.getArticlesCommande=function(idArtCom){
	
			$http.get("/getArticlesForCommandeClient?idComCl="+idArtCom)
			.success(function(data){ 
				$scope.articlesCommande = data ;
			});

			
		} 
		
		
		
		
		
		  var simulateAjax;

	      simulateAjax = function(result) {
	        var deferred, fn;

	        deferred = $q.defer();
	        fn = function() {
	          return deferred.resolve(result);
	        };
	        $timeout(fn, 3000);
	        return deferred.promise;
	      };
	      simulateAjax(['grooo', 'wowowowow', 'lakakalakakl', 'yadayada', 'insight', 'delve', 'synergy']).then(function(result) {
	        return $scope.optionsFromQuery = result;
	      });
	      $scope.optionsFromQueryAsHash = (function() {
	        var result;

	        result = {
	          win: "Brilliant Escape",
	          fail: "Untimely Demise"
	        };
	        return simulateAjax(result);
	      })();
	      $scope.$watch('emptyCollection', function(empty) {
	        return $scope.emptyOptions = simulateAjax(empty ? [] : ['look', 'i', 'have', 'data']);
	      });
	      $scope.directiveOptions = {
	        no_results_text: "SO SORRY"
	      };
	      $scope.ngIfInherit = true;
	      $scope.myPets = ['cat'];
	      $scope.pets = {
	        cat: 'Cat',
	        dog: 'Dog',
	        hamster: 'Hamster'
	      };
	      $timeout(function() {
	        return $scope.$apply(function() {
	          return $scope.myPets.push('hamster');
	        });
	      }, 1000);
	     // return $scope.disabled = true;
	 
		
		


		
}) ;
