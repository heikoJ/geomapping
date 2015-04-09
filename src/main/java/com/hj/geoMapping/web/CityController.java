package com.hj.geoMapping.web;

import com.hj.geoMapping.domain.City;
import com.hj.geoMapping.integration.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by heiko on 03.04.15.
 */
@RestController
@RequestMapping("/cities")
@Transactional
public class CityController {

    @Autowired
    CityRepository repository;

    @RequestMapping(value = "/{countryCode}", method = RequestMethod.GET)
    public Page<City> findCitiesByCountry(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10000") int size) {

        Page<City> allCities = repository.findByCountryCodeAllIgnoringCase(countryCode, new PageRequest(page,size));
        List<City> unmappedCitites = repository.findUnmappedForByCountry(countryCode,new PageRequest(page,size)).getContent();

        allCities.forEach(city -> city.setMapped(!unmappedCitites.contains(city)));

        return allCities;
    }

    @RequestMapping(value="/{countryCode}/unmapped", method = RequestMethod.GET)
    public Page<City> findUnmappedCitiesByCountry(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10000") int size) {

        return repository.findUnmappedForByCountry(countryCode, new PageRequest(page, size));

    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCity(@RequestBody @Valid City city) {
        repository.save(city);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCity(
            @RequestBody @Valid City city,
            @PathVariable Long id) {
        City persistedCity = repository.findOne(id);
        persistedCity.updateValues(city);
        repository.save(persistedCity);
    }


}
