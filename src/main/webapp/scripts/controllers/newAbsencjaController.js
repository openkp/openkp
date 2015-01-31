
angular.module('openkp').controller('NewAbsencjaController', function ($scope, $location, locationParser, AbsencjaResource , PracownikResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.absencja = $scope.absencja || {};
    
    $scope.pracownikList = PracownikResource.queryAll(function(items){
        $scope.pracownikSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("pracownikSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.absencja.pracownik = {};
            $scope.absencja.pracownik.id = selection.value;
        }
    });
    
    $scope.typAbsencjiList = [
        "BEZPLATNA",
        "URLOP",
        "CHOROBA",
        "SZPITAL"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Absencjas/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        AbsencjaResource.save($scope.absencja, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Absencjas");
    };
});