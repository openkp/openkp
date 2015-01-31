

angular.module('openkp').controller('EditAbsencjaController', function($scope, $routeParams, $location, AbsencjaResource , PracownikResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.absencja = new AbsencjaResource(self.original);
            PracownikResource.queryAll(function(items) {
                $scope.pracownikSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.absencja.pracownik && item.id == $scope.absencja.pracownik.id) {
                        $scope.pracownikSelection = labelObject;
                        $scope.absencja.pracownik = wrappedObject;
                        self.original.pracownik = $scope.absencja.pracownik;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Absencjas");
        };
        AbsencjaResource.get({AbsencjaId:$routeParams.AbsencjaId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.absencja);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.absencja.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Absencjas");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Absencjas");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.absencja.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("pracownikSelection", function(selection) {
        if (typeof selection != 'undefined') {
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
    
    $scope.get();
    
    $scope.today = function() {
        $scope.dt = new Date();
      };
      $scope.today();

      $scope.clear = function () {
        $scope.dt = null;
      };

      // Disable weekend selection
      $scope.disabled = function(date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
      };

      $scope.toggleMin = function() {
        $scope.minDate = $scope.minDate ? null : new Date();
      };
      $scope.toggleMin();

      $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
      };

      $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
      };

      $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
      $scope.format = $scope.formats[0];
      $scope.oneAtATime = true;

});