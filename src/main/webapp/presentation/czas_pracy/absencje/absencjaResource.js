angular.module('openkp').factory('AbsencjaResource', [ '$resource', function($resource) {
	return $resource('rest/absencja/:pracownikId/absencja/:absencjaId', {
		pracownikId : '@pracownikId',
		absencjaId : '@absencjaId'
	});
} ]);