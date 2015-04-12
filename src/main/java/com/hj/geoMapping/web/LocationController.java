package com.hj.geoMapping.web;

import com.hj.geoMapping.common.GeoBounds;
import com.hj.geoMapping.domain.UNLocation;
import com.hj.geoMapping.integration.UNLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by heiko on 05.04.15.
 */
@RestController
@RequestMapping("/locations")
@Transactional
public class LocationController {

    @Autowired
    UNLocationRepository repository;

    @RequestMapping(value = "/{countryCode}", method = RequestMethod.GET)
    public List<UNLocation> findCitiesByCountry(
            @PathVariable String countryCode,
            @RequestParam("bounds") String boundsAsString) {

        List<UNLocation> locations = repository.findByCountryCodeAllIgnoringCase(countryCode);

        if(boundsAsString!=null && !boundsAsString.trim().equals("")) {
            GeoBounds bounds = new GeoBounds(boundsAsString);
            return getLocationsForBounds(locations,bounds);
        }

        return locations;
    }


    private List<UNLocation> getLocationsForBounds(List<UNLocation> locations, GeoBounds bounds) {
        return locations.parallelStream().
                filter(location ->
                        location.getGeoLocation().
                                isInBounds(bounds)).
                collect(Collectors.toList());

    }




}
