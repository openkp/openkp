angular.module('openkp').factory('WyplataResource', [ '$resource', function($resource) {
	return $resource('rest/wyplata/:pracownikId/:rok/:miesiac', {
		pracownikId : '@pracownikId',
		miesiac : '@miesiac',
		rok : '@rok'
	});
} ]);