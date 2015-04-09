package com.hj.geoMapping.web;

import com.hj.geoMapping.domain.City;
import com.hj.geoMapping.domain.Mapping;
import com.hj.geoMapping.domain.UNLocation;
import com.hj.geoMapping.integration.CityRepository;
import com.hj.geoMapping.integration.MappingRepository;
import com.hj.geoMapping.integration.UNLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by heiko on 04.04.15.
 */
@RestController
@Transactional
@RequestMapping("/mappings")
public class MappingController {

    @Autowired
    MappingRepository repository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UNLocationRepository locationRepository;

    @RequestMapping(value="/{countryCode}", method = RequestMethod.GET)
    public Page<Mapping> getMappingsForCountryCode(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10000") int size) {

        return repository.findMappingsForCountryCode(countryCode, new PageRequest(page,size));

    }

    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createMapping(@RequestBody @Valid Mapping mapping) {
        UNLocation location = locationRepository.findOne(mapping.getLocation().getId());
        City city = cityRepository.findOne(mapping.getCity().getId());

        mapping.setCity(city);
        mapping.setLocation(location);

        repository.save(mapping);
    }

    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteMapping(@PathVariable Long id) {
        repository.delete(id);
    }

}
