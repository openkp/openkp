'use strict'
angular.module('openkp.pracownikNowy', [ 'ngRoute' ]).config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/pracownik/nowy', {
		templateUrl : 'presentation/kadry/pracownik/pracownik.html',
		controller : 'PracownikNowyController'
	})
} ]).controller('PracownikNowyController', function($scope, $location, locationParser, PracownikResource, AbsencjaResource) {
	$scope.disabled = false;
	$scope.$location = $location;
	$scope.pracownik = $scope.pracownik || {};

	
	$scope.$watch("absencjeSelection", function(selection) {
		if (typeof selection != 'undefined') {
			$scope.pracownik.absencje = [];
			$.each(selection, function(idx, selectedItem) {
				var collectionItem = {};
				collectionItem.id = selectedItem.value;
				$scope.pracownik.absencje.push(collectionItem);
			});
		}
	});

	$scope.save = function() {
		var successCallback = function(data, responseHeaders) {
			var id = locationParser(responseHeaders);
			$location.path('/pracownik/edycja/' + id);
			$scope.displayError = false;
		};
		var errorCallback = function() {
			$scope.displayError = true;
		};
		PracownikResource.save($scope.pracownik, successCallback, errorCallback);
	};

	$scope.cancel = function() {
		$location.path("/pracownicy");
	};

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