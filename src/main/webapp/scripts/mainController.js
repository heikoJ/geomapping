var geomappingApp = angular.module('geomappingApp', ['ngRoute','ui.bootstrap','uiGmapgoogle-maps']);

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
geomappingApp.controller('locationController', function($scope,$http, $log) {


    $scope.markers = [];

    $scope.bounds={}

    var locationRequestCount=0;


    $scope.zoom = 3;

    $scope.map = { center: { latitude: 40.2534258, longitude: 31.1740902 }, zoom: 3, options: {disableDefaultUI: false, draggable: true} };




    $scope.getLocations = function(bounds) {
        locationRequestCount++;
        //$log.log("Zoom: " +$scope.zoom);

        $http.get("/locations/?bounds=" + bounds ).
            then(function (response) {
                locationRequestCount--;
                if(locationRequestCount==0) {
                    $scope.markers = response.data;
                    //$log.log($scope.markers);
                }
            });
    };


    $scope.mapEvents = {


        idle: function() {
            var bounds = $scope.map.getGMap().getBounds();
            var boundsStr = bounds.getSouthWest().toUrlValue() + "," + bounds.getNorthEast().toUrlValue();

            $log.log("Bounds" + boundsStr);

            $scope.getLocations(boundsStr);
        }

    };

    $scope.locationEvents = {

        click: function(marker,eventName, model) {

            $log.log("MArker clikc");
            $log.log(model);


            $scope.map.getGMap().fitBounds(model.bounds);

        }
    };


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
