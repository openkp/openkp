
angular.module('openkp').controller('NewPracownikController', function ($scope, $location, locationParser, PracownikResource , AbsencjaResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.pracownik = $scope.pracownik || {};
    
    $scope.absencjeList = AbsencjaResource.queryAll(function(items){
        $scope.absencjeSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("absencjeSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.pracownik.absencje = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.pracownik.absencje.push(collectionItem);
            });
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Pracowniks/edit/' + id);
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
});