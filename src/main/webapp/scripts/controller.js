angular.module('geomapping', ['ui.bootstrap','uiGmapgoogle-maps']).controller('CountryController', function($scope, $http) {

    $scope.locationCoords = [];

    $scope.zoom = 3;

    $scope.map = { center: { latitude: 40.2534258, longitude: 31.1740902 }, zoom: 3 };



    $scope.getLocationsForCountry = function(countryCode) {
        $http.get("/locations/" + countryCode).
            then(function(response) {
                $scope.locations = response.data.content;

                $scope.locations.forEach(function(location) {
                    location.options = {draggable: true, icon : 'https://chart.googleapis.com/chart?chst=d_bubble_text_small_withshadow&chld=edge_bc|' + location.name +'|C6EF8C|000000'};
                });


            });
    };

    $scope.getCitiesForCountry = function(countryCode) {
        $http.get("/cities/" + countryCode).
            then(function(response) {
                $scope.cities = response.data.content;

                $scope.cities.forEach(function(city) {
                    city.options = {draggable: true, icon : 'https://chart.googleapis.com/chart?chst=d_bubble_text_small_withshadow&chld=edge_bc|' + city.name +'|00FF00|000000'};
                });
            });
    };

    $scope.getMappingsForCountry = function(countryCode) {
        $http.get("/mappings/" + countryCode).
            then(function(response) {
                $scope.mappings = response.data.content;


                $scope.zoom = 8;
                $scope.map.refresh({latitude:$scope.selectedCountry.latitude, longitude:$scope.selectedCountry.longitude});
            });
    };

    $scope.getCountry = function (val) {

        return $http.get('/countries', {
            params: {
                filter: val
            }
        }).then(function (response) {
           return response.data;
        });
    };

    $scope.onSelect = function(item,model,label) {
        var countryCode = $scope.selectedCountry.code;
        $scope.getMappingsForCountry(countryCode);
        $scope.getLocationsForCountry(countryCode);
        $scope.getCitiesForCountry(countryCode);


    };

});