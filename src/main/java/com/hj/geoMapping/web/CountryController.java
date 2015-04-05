package com.hj.geoMapping.web;

import com.hj.geoMapping.domain.Country;
import com.hj.geoMapping.integration.CityRepository;
import com.hj.geoMapping.integration.CountryRepository;
import com.hj.geoMapping.integration.UNLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by heiko on 03.04.15.
 */
@RestController
@Transactional
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    CountryRepository repository;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<Country> countries(@RequestParam String filter) {
        if(filter== null || filter.trim().equals("") ) {
            return repository.findAll();
        }

        filter = "%" + filter + "%";
        return repository.findByCodeLikeOrNameLikeAllIgnoringCase(filter,filter);
    }

}
