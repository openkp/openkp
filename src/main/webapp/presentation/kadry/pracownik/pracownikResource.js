angular.module('openkp').factory('PracownikResource', ['$resource', function($resource) {
	var resource = $resource('rest/pracownik/:PracownikId', {
		PracownikId : '@id'
	}, {
		'queryAll' : {
			method : 'GET',
			isArray : true
		},
		'query' : {
			method : 'GET',
			isArray : false
		},
		'update' : {
			method : 'PUT'
		}
	});
	return resource;
}]);