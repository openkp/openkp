

angular.module('openkp').controller('EditPracownikController', function($scope, $routeParams, $location, PracownikResource , AbsencjaResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.pracownik = new PracownikResource(self.original);
            AbsencjaResource.queryAll(function(items) {
                $scope.absencjeSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.pracownik.absencje){
                        $.each($scope.pracownik.absencje, function(idx, element) {
                            if(item.id == element.id) {
                                $scope.absencjeSelection.push(labelObject);
                                $scope.pracownik.absencje.push(wrappedObject);
                            }
                        });
                        self.original.absencje = $scope.pracownik.absencje;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Pracowniks");
        };
        PracownikResource.get({PracownikId:$routeParams.PracownikId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.pracownik);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.pracownik.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Pracowniks");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Pracowniks");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.pracownik.$remove(successCallback, errorCallback);
    };
    
    $scope.absencjeSelection = $scope.absencjeSelection || [];
    $scope.$watch("absencjeSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.pracownik) {
            $scope.pracownik.absencje = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.pracownik.absencje.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});