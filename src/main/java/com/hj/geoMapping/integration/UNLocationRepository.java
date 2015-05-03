package com.hj.geoMapping.integration;

import com.hj.geoMapping.domain.City;
import com.hj.geoMapping.domain.UNLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by heiko on 04.04.15.
 */
public interface UNLocationRepository extends CrudRepository<UNLocation,Long> {

    UNLocation findByCodeAllIgnoringCase(String code);

    @Query("SELECT u FROM UNLocation u WHERE upper(substring(u.code,1,2)) = upper(?1)")
    List<UNLocation> findByCountryCodeAllIgnoringCase(String countryCode);



    @Query("SELECT u FROM UNLocation u WHERE upper(substring(u.code,1,2)) = upper(?1) AND u NOT IN (SELECT m.location FROM Mapping m)")
    Page<UNLocation> findUnmappedByCountry(String countryCode, Pageable pageable);

    @Query("SELECT distinct substring(u.code,1,2) from UNLocation u")
    Set<String> getDistinctCountryCode();





}
