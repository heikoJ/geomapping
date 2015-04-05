package com.hj.geoMapping.integration;

import com.hj.geoMapping.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created by heiko on 03.04.15.
 */

public interface CityRepository extends CrudRepository<City,Long>{

    City findByNameAndCountryCodeAllIgnoringCase(String name,String countryCode);

    Page<City> findByCountryCodeAllIgnoringCase(String countryCode, Pageable pageable);



    @Query("SELECT c FROM City c WHERE upper(c.countryCode) = upper(?1) AND c NOT IN (SELECT m.city FROM Mapping m)")
    Page<City> findUnmappedForByCountry(String countryCode, Pageable pageable);

    @Query("SELECT distinct c.countryCode from City c")
    Set<String> getDistinctCountryCode();


}
