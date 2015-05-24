package com.hj.geoMapping.web;

import com.hj.geoMapping.clustering.Clusterer;

import com.hj.geoMapping.common.Marker;
import com.hj.geoMapping.common.ClusterMarker;
import com.hj.geoMapping.common.GeoBounds;
import com.hj.geoMapping.domain.UNLocation;
import com.hj.geoMapping.integration.UNLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static com.hj.geoMapping.clustering.Clusterer.*;

/**
 * Created by heiko on 05.04.15.
 */
@RestController
@RequestMapping("/locations")
@Transactional
public class LocationController {

    @Autowired
    UNLocationRepository repository;



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<? extends Marker> findCitiesByCountry(
            @RequestParam("bounds") String boundsAsString) {

        Iterable<UNLocation> locations = repository.findAll();


        GeoBounds bounds = new GeoBounds(boundsAsString);

        float clusterSize = (bounds.getNorthEastLocation().getLongitude() - bounds.getSouthWestLocation().getLongitude()) / 20f;

        return getLocationsForBounds(locations, bounds, clusterSize);
    }


    private Collection<? extends Marker> getLocationsForBounds(Iterable<UNLocation> locations, GeoBounds bounds, float clusterSize) {

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

        Collection<? extends Marker> result = clustererWithSize(clusterSize).doCluster(markers);

        end = System.currentTimeMillis();

        System.out.println("Clustering: " + (end-start));


        return result;



    }


    private Set<Marker> getTooMuchMarkersMarker(GeoBounds bounds) {
        Marker marker = new Marker(bounds.getCenter(),">1000",-System.currentTimeMillis());
        Set<Marker> markers = new HashSet<>();
        markers.add(marker);
        return markers;
    }



}
