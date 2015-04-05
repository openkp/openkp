'use strict';

angular.module('openkp.pracownikEdycja', [ 'ngRoute' ]).config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/pracownik/edycja/:PracownikId', {
		templateUrl : 'presentation/kadry/pracownik/pracownik.html',
		controller : 'PracownikEdycjaController'
	});
} ]).controller('PracownikEdycjaController', function($scope, $routeParams, $location, PracownikResource , AbsencjaResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.pracownik = new PracownikResource(self.original);            
        };
        var errorCallback = function() {
            $location.path("/pracownicy");
        };
        PracownikResource.get({PracownikId:$routeParams.PracownikId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.pracownik);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
        };
        var errorCallback = function(resp) {
        	if (resp.status == 400) {
				$scope.bledyWalidacji = resp.data;
			}
        };
        $scope.pracownik.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/pracownicy");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/pracownicy");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.pracownik.$remove(successCallback, errorCallback);
    };
            
    $scope.get();
    
    $scope.open = function($event) {
		$event.preventDefault();
		$event.stopPropagation();

		$scope.opened = true;
	};
	
	$scope.openZwolnienie = function($event) {
		$event.preventDefault();
		$event.stopPropagation();

		$scope.openedZwolnienie = true;
	};
});