var app=angular.module("Avoir",['ui.bootstrap','localytics.directives']) ;
app.controller("AvoirController",function($scope,$http, $filter, $q, $timeout , $window){

	$scope.codeArt=[""] ;
	$scope.desArt=[""] ;
	$scope.qteArt=[""] ;
	$scope.prixArt=[""] ;
	$scope.prixtotal=[""] ;
	$scope.prixtotalCommande=[""] ;
	$scope.dateComCl= null ;
	$scope.codeAvCl=0 ;

	    
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
	
	$scope.visible=true ;
	$scope.onSave = function(){
		
		if($scope.codeAvCl !=0) {

        if($scope.form.$valid){
        	$window.open("http://localhost:8084/listeAvoirsClient.html" , "_self");
        }
		}
    }
	
	$scope.codeSelection = function(code) {
	       
		$scope.nomAvCl = code ;
		
		$http.get("/getLastRepresentantClient?idCl="+code)
		.success(function(data){ 
			$scope.rep = data ;
			$scope.codeRep = $scope.rep.id ;
			$scope.nomRep = $scope.rep.nomRep ;
			$scope.telRep = $scope.rep.telRep;
			
			
		});
		
		}
	
	$scope.nomSelection = function(nom) {
		$scope.codeAvCl = nom ;
		
		$http.get("/getLastRepresentantClient?idCl="+nom)
		.success(function(data){ 
			$scope.rep = data ;
			$scope.codeRep = $scope.rep.id ;
			$scope.nomRep = $scope.rep.nomRep ;
			$scope.telRep = $scope.rep.telRep;
			
			
		});
		
		}
	
	

	$scope.autocompleteDesignation = function(des,i) {
		$http.get("/getArticleByDesignation?designation="+des)
		.success(function(data){
			$scope.article = data ;
			$scope.codeArt[i]=$scope.article.idArt ;
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
		
		
		$scope.factures=[] ;
		$http.get("/allFactures")
		.success(function(data){
		 $scope.factures = data ;
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
		
		
		
		$scope.avoirsClient=[] ;
		$http.get("/allAvoirsClient")
		.success(function(data){
		 $scope.avoirsClient = data ;
}) ;
		
		
		
		
		
		var addArticles = function(i){
			  $http.get("/addArticleToAvoir?quantiteAl="+$scope.qteArt[i]+"&designationAl="+$scope.desArt[i]+"&prixUnitAl="+$scope.prixArt[i]+"&idArt="+$scope.codeArt[i])
			    .success(function(data) {

			     }) ;
			  }
		

		
		
		
		$scope.ajouterAvoir=function(){
			
			if($scope.codeAvCl==0) {
	               alert("Veuillez choisir un Client") ;
				}
			else {
			var arrayLength = ($scope.codeArt.length - 1 );
			var d = $scope.dateAvCl ;
			d = $filter('date')(d, "dd/MM/yyyy");
			     $http.get("/addAvoirClient?dateAv="+d+"&idCl="+$scope.codeAvCl+"&prixTotalAvoir="+$scope.prixtotalCommande[arrayLength])
			     .success(function(data){   

			    	 for (var i = 1; i < (arrayLength); i++) {
				    	 
			    		       
			    		       addArticles(i) ;
				    	          }

			           });
			     
			         alert("Avoir Ajouté") ;
			      //   $scope.chargerArticles() ;
			         
			}

		} ;
		
		
		
	/*	$scope.ajouterArticlesToCommande=function(){

		     var arrayLength = $scope.codeArt.length;
		    	 
		    	 for (var i = 1; i < (arrayLength); i++) {
		    	 
		    	 $http.get("/addArticleToCommande?quantiteAl="+$scope.qteArt[i]+"&designationAl="+$scope.desArt[i]+"&prixUnitAl="+$scope.prixArt[i]+"&idArt="+$scope.codeArt[i])
					.success(function(data){
						
			           }) ;
		    	 }
		    $scope.chargerArticles() ;
         	alert("Articles Ajoutés") ;

	}*/
		

		
		$scope.getArticlesAvoir=function(idArtAv){
	
			$http.get("/getArticleForAvoir?idAv="+idArtAv)
			.success(function(data){ 
				alert(idArtAv) ;
				$scope.articlesAvoir = data ;
				alert(data);
				$scope.chargerArticles() ;
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
