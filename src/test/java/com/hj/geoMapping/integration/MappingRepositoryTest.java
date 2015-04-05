package com.hj.geoMapping.integration;

import com.hj.geoMapping.Application;
import com.hj.geoMapping.domain.Mapping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class MappingRepositoryTest {


    @Autowired
    MappingRepository repository;

    @Test
    public void testFindMappingsForCountry() throws Exception {
        Page<Mapping> page = repository.findMappingsForCountryCode("de", new PageRequest(0,100));
        page.forEach(System.out::println);
    }

}