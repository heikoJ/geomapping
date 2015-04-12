angular.module('geomapping', ['ui.bootstrap','uiGmapgoogle-maps', 'ngDragDrop']).controller('CountryController', function($scope, $http, $log, filterFilter) {

    $scope.locationCoords = [];

    $scope.filteredLocations = [];

    // create empty search model (object) to trigger $watch on update
    $scope.locationSearch = {};

    $scope.resetLocationFilter = function () {
        // needs to be a function or it won't trigger a $watch
        $scope.locationSearch = {};
    };


    $scope.locItemsPerPage = 10;

    $scope.resetLocationFilter = function () {
        $log.log('reset');
        // needs to be a function or it won't trigger a $watch
        $scope.locationSearch = {};
        $scope.locCurPage = 1;
        $scope.locTotalItems = $scope.filteredLocations.length;
        $scope.locPages = Math.ceil($scope.locTotalItems / $scope.locItemsPerPage);
    };

    // $watch search to update pagination
    $scope.$watch('locationSearch', function (newVal, oldVal) {
        var filter = (newVal && newVal.name.length>2) ? newVal.name : '';
        $scope.filteredLocations = filterFilter($scope.locations, filter);
        $scope.locTotalItems = $scope.filteredLocations.length;
        $scope.locPages = Math.ceil($scope.locTotalItems / $scope.locItemsPerPage);
        $scope.locCurPage = 1;
    }, true);


    $scope.zoom = 3;

    $scope.map = { center: { latitude: 40.2534258, longitude: 31.1740902 }, zoom: 3 };


    $scope.citites = [];
    $scope.droppedCities = [];



    $scope.getLocationsForCountry = function(countryCode,bounds) {
        $log.log("Zoom: " +$scope.zoom);
        if($scope.zoom>=10) {
            $http.get("/locations/" + countryCode + '?bounds=' + bounds).
                then(function (response) {
                    $scope.locations = response.data;


                    $scope.locations.forEach(function (location) {
                        location.options = {
                            draggable: true,
                            icon: 'https://chart.googleapis.com/chart?chst=d_bubble_text_small_withshadow&chld=edge_bc|' + location.name + '|C6EF8C|000000'
                        };


                    });


                    $scope.filteredLocations = $scope.locations;
                    $scope.resetLocationFilter();
                });
        }


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

        $http.post("/mappings", mapping).
            then(function(response) {
                mapping.id = response;
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
                $scope.map.refresh();
            });


    };

    $scope.deleteMapping = function(index) {
        var mapping = $scope.mappings[index];


        $http.delete("/mappings/" + mapping.id);

        var city = mapping.city;
        city.mapped = false;


        $scope.mappingLines.splice(index,1);

        $scope.mappings.splice(index,1);
    }



    $scope.cityMarkerEvents = {
        dragend : function (marker, eventName, model) {
            $log.log('marker dragend');
            $log.log(model);

            var mappingIndex = -1;
            for(var i=0;i<$scope.mappings.length;i++) {
                if ($scope.mappings[i].city.id == model.id) {
                    mappingIndex = i;
                    break;
                }
            }



            if(mappingIndex>-1) {
                $log.log("line:");
                $log.log($scope.mappingLines[mappingIndex]);
                $scope.mappingLines[mappingIndex].path[0].latitude = model.latitude;
                $scope.mappingLines[mappingIndex].path[0].longitude = model.longitude;

            }

            $scope.map.refresh();

            $http.put("/cities/" + model.id, model);

        }
    };

    $scope.mapEvents = {


        idle: function() {
            var bounds = $scope.map.getGMap().getBounds();
            var boundsStr = bounds.getSouthWest().toUrlValue() + "," + bounds.getNorthEast().toUrlValue();

            $log.log("Bounds" + boundsStr);

            $scope.getLocationsForCountry($scope.selectedCountry.code,boundsStr);
        }

    }


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
        $scope.locCurPage=1;
        $scope.getMappingsForCountry(countryCode);
      //  $scope.getLocationsForCountry(countryCode);
        $scope.getCitiesForCountry(countryCode);


    };

    $scope.gotoLocation = function(location) {
        $scope.zoom = 11;
        $scope.map.refresh({latitude:location.latitude, longitude: location.longitude});
    }

});