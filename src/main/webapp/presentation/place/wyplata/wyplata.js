'use strict';

angular.module('openkp.wyplata', ['ngRoute']).config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/wyplata', {
        templateUrl: 'presentation/place/wyplata/wyplata.html',
        controller: 'WyplataController'
    });
}]).controller(
    'WyplataController',
    function ($scope, $compile, $log, PracownikResource, WyplataResource) {

        $scope.miesiace = ['styczeń', 'luty', 'marzec', 'kwiecień', 'maj', 'czerwiec', 'lipiec', 'sierpień', 'wrzesień', 'październik',
            'listopad', 'grudzień'];

        $scope.gridOptions = {
            enableRowSelection: true,
            enableRowHeaderSelection: false,
            multiSelect: false,
            noUnselect: true,
            modifierKeysToMultiSelect: false,
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
            },
            columnDefs: [{
                field: 'id',
            }, {
                field: 'imie',
                displayName: 'Imię'
            }, {
                field: 'nazwisko'
            }]
        };

        $scope.oblicz = function () {
        	var successCallback = function(data, responseHeaders) { 
        		$scope.bledyWalidacji = null;
    		};
    		var errorCallback = function(resp) {
    			if (resp.status == 400) {
    				$scope.bledyWalidacji = resp.data;
    			}
    		};
            $scope.wyplata = WyplataResource.get({
                pracownikId: $scope.gridApi.selection.getSelectedRows()[0].id,
                miesiac: $scope.miesiace.indexOf($scope.miesiac),
                rok: $scope.rok
            }, successCallback, errorCallback);

        }

//        $("#rok").inputmask("9999", {
//            "placeholder": "rrrr"
//        });

        $scope.gridOptions.data = PracownikResource.queryAll();
    });
