angular.module('openkp').factory('AbsencjaResource', [ '$resource', function($resource) {
	return $resource('rest/pracownik/:pracownikId/absencja/:absencjaId', {
		pracownikId : '@pracownikId',
		absencjaId : '@absencjaId'
	});
} ]);