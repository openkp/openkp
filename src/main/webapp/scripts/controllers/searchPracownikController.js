angular.module('openkp').controller('SearchPracownikController', function($scope, $http, PracownikResource, AbsencjaResource, uiGridConstants) {
	$scope.gridOptions = {
			enableSelection : true,
		columnDefs : [
		// default
		{
			field : 'imie'
		},
		// pre-populated search field
		{
			field : 'nazwisko',
			filter : {}
		},
		// no filter input
		{
			field : 'email',
			filter : {}
		},
		// specifies one of the built-in conditions
		// and a placeholder for the input
		{
			field : 'phoneNumber',
			filter : {
				condition : uiGridConstants.filter.ENDS_WITH,
			}
		},
		// custom condition function
		{
			field : 'dataZatrudnienia',
			filter : {
				condition : function(searchTerm, cellValue) {
					var strippedValue = (cellValue + '').replace(/[^\d]/g, '');
					return strippedValue.indexOf(searchTerm) >= 0;
				}
			}
		},
		// multiple filters
		{
			field : 'dataZwolnienia',
		} ]
	};
	$scope.search = {};
	$scope.currentPage = 0;
	$scope.pageSize = 10;
	$scope.searchResults = [];
	$scope.filteredResults = [];
	$scope.pageRange = [];
	$scope.numberOfPages = function() {
		var result = Math.ceil($scope.filteredResults.length / $scope.pageSize);
		var max = (result == 0) ? 1 : result;
		$scope.pageRange = [];
		for (var ctr = 0; ctr < max; ctr++) {
			$scope.pageRange.push(ctr);
		}
		return max;
	};

	$scope.performSearch = function() {
		$scope.gridOptions.data = PracownikResource.queryAll(function() {

		});
	};

	$scope.previous = function() {
		if ($scope.currentPage > 0) {
			$scope.currentPage--;
		}
	};

	$scope.next = function() {
		if ($scope.currentPage < ($scope.numberOfPages() - 1)) {
			$scope.currentPage++;
		}
	};

	$scope.setPage = function(n) {
		$scope.currentPage = n;
	};

	$scope.performSearch();

	$scope.wiersz;

	

});