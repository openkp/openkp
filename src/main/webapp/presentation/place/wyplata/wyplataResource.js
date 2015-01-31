angular.module('openkp').factory('WyplataResource', [ '$resource', function($resource) {
	return $resource('rest/pracownik/:pracownikId/wyplata/:miesiac/:rok', {
		pracownikId : '@pracownikId',
		miesiac : '@miesiac',
		rok : '@rok'
	});
} ]);