package com.hj.geoMapping.web;

import com.hj.geoMapping.domain.UNLocation;
import com.hj.geoMapping.integration.UNLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public Page<UNLocation> findCitiesByCountry(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return repository.findByCountryCodeAllIgnoringCase(countryCode, new PageRequest(page,size));
    }




}
