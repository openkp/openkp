appModule.directive('walidacje', function() {
	return {
		restrict: 'E',
		template: '<div ng-show="bledyWalidacji != null" class="alert alert-danger">' 
			+ '      <ul ng-repeat="komunikat in bledyWalidacji.parameterViolations">'
			+ '        <li>{{komunikat.message}}</li>'
			+ '      </ul>'
			+ '    </div>',
		replace : true
	};
});