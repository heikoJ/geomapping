package com.hj.geoMapping.integration;

import com.hj.geoMapping.domain.Country;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by heiko on 04.04.15.
 */
public interface CountryRepository extends PagingAndSortingRepository<Country,Long> {

    List<Country> findByCodeLikeOrNameLikeAllIgnoringCase(String code,String name);

}
