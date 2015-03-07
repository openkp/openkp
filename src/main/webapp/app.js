'use strict';

angular.module(
		'openkp',
		[ 'ngRoute', 'ngResource', 'ui.bootstrap', 'ui.grid', 'ui.grid.resizeColumns', 'ui.grid.pinning', 'ui.grid.selection', 'ui.calendar',
				'openkp.wyplata', 'openkp.pracownicy', 'openkp.pracownikEdycja', 'openkp.pracownikNowy', 'openkp.absencje' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/', {
				templateUrl : 'presentation/landing.html',
				controller : 'LandingPageController'
			}).otherwise({
				redirectTo : '/'
			});
		} ]).controller('LandingPageController', function LandingPageController() {

}).controller('NavController', function NavController($scope, $location) {
	$scope.matchesRoute = function(route) {
		var path = $location.path();
		return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
	};
}).controller('ApplicationController', function NavController($scope, $location) {
	$scope.uzytkownik = {
		nazwa : 'Andrzej Szywała',
		zatrudniony : 'Zatrudniony od lutego 2015',
		status : ' aktywny'
	}
});
