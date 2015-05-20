var geomappingApp = angular.module('geomappingApp', ['ngRoute','ui.bootstrap']);

// configure our routes
geomappingApp.config(function($routeProvider) {
    $routeProvider

        // route for the home page
        .when('/locations', {
            templateUrl : 'pages/locations.html',
            controller  : 'locationController'
        })

        // route for the about page
        .when('/stats', {
            templateUrl : 'pages/stats.html',
            controller  : 'statsController'
        });

});



geomappingApp.factory('appSettings', function() {
    return {zoomOnMarkerDropped:true, zoomLevelOnMarkerDropped: 5};
});




// create the controller and inject Angular's $scope
geomappingApp.controller('mainController', function($scope,$modal,appSettings) {
    // create a message to display in our view
    $scope.message = 'Everyone come and see how good I look!';



    $scope.openSettings = function() {
        $scope.settings = angular.copy(appSettings);
        var modalInstance = $modal.open({
            animation: true,
            templateUrl: 'modals/settings.html',
            controller: 'settingsController',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                settings: function () {
                    return $scope.settings;
                }
            }
        });

        modalInstance.result.then(function (settings) {
            appSettings = angular.copy(settings);
        }, function () {
           // $log.info('Modal dismissed at: ' + new Date());
        });
    }




});

geomappingApp.controller('settingsController', function($scope,$modalInstance,settings) {

    $scope.settings = settings;

    $scope.ok = function () {
        $modalInstance.close($scope.settings);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.message = 'Settings';
});


// create the controller and inject Angular's $scope
geomappingApp.controller('statsController', function($scope) {
    // create a message to display in our view
    $scope.message = 'Statistics';
});

// create the controller and inject Angular's $scope
geomappingApp.controller('locationController', function($scope) {
    // create a message to display in our view
    $scope.message = 'Locations';
});

geomappingApp.directive('toggleCheckbox', function($timeout) {

    /**
     * Directive
     */
    return {
        restrict: 'A',
        transclude: true,
        replace: false,
        require: 'ngModel',
        link: function ($scope, $element, $attr, ngModel) {

            // update model from Element
            var updateModelFromElement = function() {
                // If modified
                var checked = $element.prop('checked');
                if (checked != ngModel.$viewValue) {
                    // Update ngModel
                    ngModel.$setViewValue(checked);
                    $scope.$apply();
                }
            };

            // Update input from Model
            var updateElementFromModel = function(newValue) {
                $element.trigger('change');
            };

            // Observe: Element changes affect Model
            $element.on('change', function() {
                updateModelFromElement();
            });

            $scope.$watch(function() {
                return ngModel.$viewValue;
            }, function(newValue) {
                updateElementFromModel(newValue);
            }, true);

            // Initialise BootstrapToggle
            $element.bootstrapToggle();
        }
    };
});
