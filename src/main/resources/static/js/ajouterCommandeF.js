var app=angular.module("ajouterCommandeF",['ui.bootstrap','localytics.directives']) ;
app.controller("ajouterCommandeFController",function($scope,$filter,$http, $q, $timeout, $window){
	
	$scope.codeArt=[""] ;
	$scope.desArt=[""] ;
	$scope.qteArt=[""] ;
	$scope.prixArt=[""] ;
	$scope.prixtotal=[""] ;
	$scope.prixtotalCommande=[""] ;
	$scope.dateComCl= null ;
	$scope.codeComCl=0 ;
	$scope.codeComF=0 ;

	
	    
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
		if($scope.codeComF !=0) {

        if($scope.form.$valid){
        	$window.open("http://localhost:8084/commandesEncoursF.html" , "_self");
        }
		}
    }
	
	
	$scope.codeSelection = function(code) {
	       
		$scope.nomComF = code ;
		
		$http.get("/getLastRepresentantF?idF="+code)
		.success(function(data){ 
			$scope.rep = data ;
			$scope.codeRep = $scope.rep.id ;
			$scope.nomRep = $scope.rep.nomRep ;
			$scope.telRep = $scope.rep.telRep;
			
			
		});
		
		}
	
	$scope.nomSelection = function(nom) {
		$scope.codeComF = nom ;
		
		$http.get("/getLastRepresentantF?idF="+nom)
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
	
	
	
	
	
	
	$scope.commandesF=[] ;
	$http.get("/allComFourn")
	.success(function(data){
	 $scope.commandesF = data ;
}) ;
	
	$scope.fournisseurs=[] ;
	$http.get("/allFournisseur")
	.success(function(data){
	 $scope.fournisseurs = data ;
}) ;
	
	
	$scope.bonLivraisonFourn=[] ;
	$http.get("/allBonLivFourn")
	.success(function(data){ 
	 $scope.bonLivraisonFourn = data ;	
}) ;
	
	
	$scope.articles=[] ;
	$http.get("/allArticles")
	.success(function(data){
	 $scope.articles = data ;
}) ;
	
	
	
	
	
	
	
	var addArticles = function(i){
		
		

		$http.get("/addArticleToCommandeFourn?quantiteAl="+$scope.qteArt[i]+"&designationAl="+$scope.desArt[i]+"&prixUnitAl="+$scope.prixArt[i]+"&idArt="+$scope.codeArt[i])
		    .success(function(data) {

		     }) ;
		  }


	
	
	$scope.ajouterCommande=function(){
		
		if($scope.codeComF==0) {
            alert("Veuillez choisir un Client") ;
			}
	
		else {
		var arrayLength = ($scope.codeArt.length - 1 );
		var d = $scope.dateComF;
		
		d = $filter('date')(d, "dd/MM/yyyy");
		     $http.get("/addCommandeFourn?dateFour="+d+"&idFourn="+$scope.codeComF+"&prixTotalCommande="+$scope.prixtotalCommande[arrayLength])
		     .success(function(data){   
		    	

		    	 for (var i = 0; i <= (arrayLength); i++) {
		    		       addArticles(i) ;
			    	          }

		           });
		     
		         alert("Commande Ajoutée") ;
		}

	} ;
	

	
	
	$scope.getArticlesCommande=function(idArtCom){
		$http.get("/getArticlesForCommandeFour?idComF="+idArtCom)
		.success(function(data){ 
			$scope.articlesCom = data ;
			$scope.chargerArticles() ;
		});

		
	} 
	
	
	

	$scope.getArticlesLivraison=function(idArtBl){

		$http.get("/getArticlesForBonLivsFour?idBl="+idArtBl)
		.success(function(data){ 
			$scope.articlesLivraison = data ;
			$scope.chargerArticles() ;
		});

		
	} 
	
	
	
	$scope.addBonLivraisonFour=function(idArtCom){
		
		$http.get("/addBonLivFour?idComFour="+idArtCom)
		.success(function(data){ 

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