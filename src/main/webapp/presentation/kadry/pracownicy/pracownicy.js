'use strict';

angular.module('openkp.pracownicy', [ 'ngRoute' ]).config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/pracownicy', {
		templateUrl : 'presentation/kadry/pracownicy/pracownicy.html',
		controller : 'PracownicyController'
	});
} ]).controller('PracownicyController', function($scope, $http, $location, $log, PracownikResource, AbsencjaResource, uiGridConstants) {

	$scope.$location = $location;
	$scope.$log = $log;
	$scope.gridOptions = {
		enableRowSelection : true,
		enableRowHeaderSelection : false,
		multiSelect : false,
		noUnselect : true,
		modifierKeysToMultiSelect : false,
		onRegisterApi : function(gridApi) {
			$scope.gridApi = gridApi;
		},
		columnDefs : [ {
			field : 'id',
		}, {
			field : 'imie',
			displayName : 'Imię'
		}, {
			field : 'nazwisko'
		}, {
			field : 'email',
		}, {
			field : 'telefon',
		}, {
			field : 'dataZatrudnienia',
		}, {
			field : 'dataZwolnienia',
			cellTemplate : '<div class="ui-grid-cell-contents">{{COL_FIELD == "9999-12-31" ? "" : COL_FIELD}}</div>'
		} ]
	};
	$scope.performSearch = function() {
		$scope.gridOptions.data = PracownikResource.queryAll();
	};

	$scope.performEdit = function() {
		$location.path("/pracownik/edycja/" + $scope.gridApi.selection.getSelectedRows()[0].id);
	};

	$scope.performSearch();
});