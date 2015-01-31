angular.module('openkp.absencje', [ 'ngRoute' ]).config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/absencje', {
		templateUrl : 'presentation/czas_pracy/absencje/absencje.html',
		controller : 'AbsencjeController'
	});
} ]).controller(
		'AbsencjeController',
		function($scope, $http, AbsencjaResource, PracownikResource, $compile, uiCalendarConfig, uiGridConstants, $interval, $log) {
			$scope.wybranaAbsencja = {};
			var date = new Date();
			var d = date.getDate();
			var m = date.getMonth();
			var y = date.getFullYear();
			$scope.gridOptions = {
				enableRowSelection : true,
				enableRowHeaderSelection : false,
				multiSelect : false,
				noUnselect : true,
				modifierKeysToMultiSelect : false,
				onRegisterApi : function(gridApi) {
					$scope.gridApi = gridApi;

					gridApi.selection.on.rowSelectionChanged($scope, function(row) {
						$scope.wybranyPracownik = row.entity;
						while ($scope.events.length > 0) {
							$scope.events.pop();
						}
						AbsencjaResource.query({
							pracownikId : row.entity.id
						}).$promise.then(function(result) {
							result.forEach(function(row) {
								var kolor = 'blue';
								if (row.title == 'choroba') {
									kolor = 'orange';
								} else if (row.title == 'szpital') {
									kolor = 'red';
								} 
									
								$scope.events.push({
									id : row.id,
									start : row.start,
									end : row.end,
									dataOd : row.dataOd,
									dataDo : row.dataDo,
									title : row.title,
									dataOdOrg : row.dataOd,
									dataDoOrg : row.dataDo,
									titleOrg : row.title,
									version : row.version,
									allDay : row.allDay,
									color : kolor
								});
							});
						});

					});
				},
				columnDefs : [ {
					field : 'id',
				}, {
					field : 'imie',
					displayName : 'Imię'
				}, {
					field : 'nazwisko'
				}, {
					field : 'email',
				}, {
					field : 'telefon',
				}, {
					field : 'dataZatrudnienia',
				}, {
					field : 'dataZwolnienia',
					cellTemplate : '<div class="ui-grid-cell-contents">{{COL_FIELD == "9999-12-31" ? "" : COL_FIELD}}</div>'
				} ]
			};

			$scope.typAbsencjiList = [ "bezpłatna", "urlop", "choroba", "szpital" ];

			$scope.dateOptions = {
				startingDay : 1
			};

			$scope.events = [];

			$scope.alertOnEventClick = function(date, jsEvent, view) {
				date.dataOd = date.dataOdOrg;
				date.dataDo = date.dataDoOrg;
				date.title = date.titleOrg;
				$scope.wybranaAbsencja = date;
			};
			$scope.save = function() {
				var dtod = moment($scope.wybranaAbsencja.dataOd);
				var dtdo = moment($scope.wybranaAbsencja.dataDo);

				$log.info($scope.wybranaAbsencja.id);
				$log.info(dtod.year());
				$log.info(dtod.month());
				$log.info(dtod.date());
				$log.info(dtdo.year());
				$log.info(dtdo.month());
				$log.info(dtdo.date());
				$log.info($scope.wybranaAbsencja.title);
				$log.info($scope.wybranaAbsencja.version);

				var doZapisu = {
					id : $scope.wybranaAbsencja.id,
					rokOd : dtod.year(),
					miesiacOd : dtod.month(),
					dzienOd : dtod.date(),
					rokDo : dtdo.year(),
					miesiacDo : dtdo.month(),
					dzienDo : dtdo.date(),
					title : $scope.wybranaAbsencja.title,
					version : $scope.wybranaAbsencja.version
				}

				AbsencjaResource.save({
					pracownikId : $scope.wybranyPracownik.id
				}, doZapisu, function(absencja) {
					for (i = 0; i < $scope.events.length; i++) {
						if ($scope.events[i].id == absencja.id) {
							$scope.events.splice(i, 1);
							break;
						}
					}
					$scope.events.push(absencja);
					$scope.wybranaAbsencja = absencja;
				});
			};
			/* remove event */
			$scope.remove = function(index) {
				AbsencjaResource.remove({
					pracownikId : $scope.wybranyPracownik.id,
					absencjaId : $scope.wybranaAbsencja.id
				});
				for (i = 0; i < $scope.events.length; i++) {
					if ($scope.events[i].id == $scope.wybranaAbsencja.id) {
						$scope.events.splice(i, 1);
						break;
					}
				}
				$scope.wybranaAbsencja = {};

			};
			/* Change View */
			$scope.changeView = function(view, calendar) {
				uiCalendarConfig.calendars[calendar].fullCalendar('changeView', view);
			};
			/* config object */
			$scope.uiConfig = {
				calendar : {
					height : 500,
					editable : true,
					firstDay : 1,
					lang : 'pl',
					header : {
						left : 'prev,next today',
						center : 'title',
					},
					buttonText : {
						today : 'dziś',
						month : 'miesiąc',
						week : 'tydzień',
						day : 'dzień'
					},
					monthNames : [ 'Styczeń', 'Luty', 'Marzec', 'Kwiecień', 'Maj', 'Czerwiec', 'Lipiec', 'Sierpień', 'Wrzesień', 'Październik',
							'Listopad', 'Grudzień' ],
					eventClick : $scope.alertOnEventClick
				}
			};

			$scope.openOd = function($event) {
				$event.preventDefault();
				$event.stopPropagation();
				$scope.openedOd = true;
			};

			$scope.openDo = function($event) {
				$event.preventDefault();
				$event.stopPropagation();
				$scope.openedDo = true;
			};

			$scope.nowaAbsencja = function() {
				$scope.wybranaAbsencja = {};
			}

			$scope.eventSources = [ $scope.events ];

			PracownikResource.queryAll().$promise.then(function(data) {
				$scope.gridOptions.data = data;
				$interval(function() {
					$scope.gridApi.selection.selectRow($scope.gridOptions.data[0]);
				}, 0, 1);
			});

			$("#dataOd").inputmask("dd.mm.yyyy", {
				"placeholder" : "dd.mm.rrrr"
			});
			$("#dataDo").inputmask("dd.mm.yyyy", {
				"placeholder" : "dd.mm.rrrr"
			});
		});