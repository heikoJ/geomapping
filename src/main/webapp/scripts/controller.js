angular.module('geomapping', ['ui.bootstrap','uiGmapgoogle-maps', 'ngDragDrop']).controller('CountryController', function($scope, $http, $log) {

    $scope.locationCoords = [];

    $scope.zoom = 3;

    $scope.map = { center: { latitude: 40.2534258, longitude: 31.1740902 }, zoom: 3 };


    $scope.citites = [];
    $scope.droppedCities = [];

    $scope.getLocationsForCountry = function(countryCode) {
        $http.get("/locations/" + countryCode).
            then(function(response) {
                $scope.locations = response.data.content;

                $scope.locations.forEach(function (location) {
                    location.options = {
                        draggable: true,
                        icon: 'https://chart.googleapis.com/chart?chst=d_bubble_text_small_withshadow&chld=edge_bc|' + location.name + '|C6EF8C|000000'
                    };


                });
            });
    };

    $scope.onCityDropped = function(event,ui,location) {
        $log.log("dropped");
        $log.log(event);
        $log.log(ui);
        $log.log("city");
        $log.log($scope.droppedCities);
        $log.log("location");
        $log.log(location);

        $scope.droppedCities[0].mapped = true;
        var mapping = {
            "id": 0,
            "city" : $scope.droppedCities[0],
            "location" : location
        };

        $scope.droppedCities = [];

        $scope.mappings.push(mapping);

        $http.post("/mappings", mapping);

        $scope.map.refresh();
    };

    $scope.deleteMapping = function(index) {
        var mapping = $scope.mappings[index];


        $http.delete("/mappings/" + mapping.id);

        var city = mapping.city;
        city.mapped = false;



        $scope.mappings.pop(index);
    }



    $scope.cityMarkerEvents = {
        dragend : function (marker, eventName, model) {
            $log.log('marker dragend');
            $log.log(model);


            $http.put("/cities/" + model.id, model);

        }
    };


    $scope.getCitiesForCountry = function(countryCode) {
        $http.get("/cities/" + countryCode).
            then(function(response) {

                var cities = response.data.content;
                cities.forEach(function(city) {
                    city.options = {draggable: true, icon : 'https://chart.googleapis.com/chart?chst=d_bubble_text_small_withshadow&chld=edge_bc|' + city.name +'|00FF00|000000'};

                });

                $scope.cities = cities;

                $scope.zoom = 8;
                $scope.map.refresh({latitude:$scope.selectedCountry.latitude, longitude:$scope.selectedCountry.longitude});

            });
    };

    $scope.getMappingsForCountry = function(countryCode) {
        $http.get("/mappings/" + countryCode).
            then(function(response) {
                $scope.mappings = response.data.content;

                $scope.mappingLines = [];

                $scope.mappings.forEach(function(mapping) {

                    $scope.mappingLines.push( {
                        id: mapping.id,
                        path: [
                            {
                                latitude: mapping.city.latitude,
                                longitude: mapping.city.longitude
                            },
                            {
                                latitude: mapping.location.latitude,
                                longitude: mapping.location.longitude
                            }
                        ]
                    });
                }) ;

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