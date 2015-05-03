package com.hj.geoMapping.web;

import com.hj.geoMapping.clustering.ClusteringService;
import com.hj.geoMapping.common.Marker;
import com.hj.geoMapping.common.MarkerCluster;
import com.hj.geoMapping.common.GeoBounds;
import com.hj.geoMapping.common.GeoLocation;
import com.hj.geoMapping.domain.UNLocation;
import com.hj.geoMapping.integration.UNLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by heiko on 05.04.15.
 */
@RestController
@RequestMapping("/locations")
@Transactional
public class LocationController {

    @Autowired
    UNLocationRepository repository;

    @Autowired
    ClusteringService clusteringService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Set<Marker> findCitiesByCountry(
            @RequestParam("bounds") String boundsAsString) {

        Iterable<UNLocation> locations = repository.findAll();


        GeoBounds bounds = new GeoBounds(boundsAsString);

        float clusterSize = (bounds.getNorthEastLocation().getLongitude() - bounds.getSouthWestLocation().getLongitude()) / 20f;

        return getLocationsForBounds(locations, bounds, clusterSize);
    }


    private Set<Marker> getLocationsForBounds(Iterable<UNLocation> locations, GeoBounds bounds, float clusterSize) {

        long start = System.currentTimeMillis();

        Set<Marker> markers =
                StreamSupport.stream(locations.spliterator(), true).
                        unordered().
                        filter(location -> location.getGeoLocation().isInBounds(bounds)).
                        map(location -> new Marker(location.getGeoLocation(), location.getName(), location.getId())).
                        collect(Collectors.toSet());

        long end = System.currentTimeMillis();

        System.out.println("GET markers: " + (end-start));


        start = System.currentTimeMillis();

        Set<Marker> result =  clusteringService.doCluster(markers,clusterSize);

        end = System.currentTimeMillis();

        System.out.println("Clustering: " + (end-start));


        return result;

       /* if(markers.size()>1000) {
            return getTooMuchMarkersMarker(bounds);
        }

        return markers;*/

    }


    private Set<Marker> getTooMuchMarkersMarker(GeoBounds bounds) {
        Marker marker = new Marker(bounds.getCenter(),">1000",-System.currentTimeMillis());
        Set<Marker> markers = new HashSet<>();
        markers.add(marker);
        return markers;
    }



}
