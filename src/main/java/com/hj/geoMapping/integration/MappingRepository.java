package com.hj.geoMapping.integration;

import com.hj.geoMapping.domain.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by heiko on 04.04.15.
 */
public interface MappingRepository extends CrudRepository<Mapping,Long> {

    @Query("SELECT m FROM Mapping m where m.city in (SELECT c FROM City c WHERE upper(c.countryCode) = upper(?1))")
    public Page<Mapping> findMappingsForCountryCode(String countryCode,Pageable pageable);

}
