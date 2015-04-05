package com.hj.geoMapping.integration;

import com.hj.geoMapping.Application;
import com.hj.geoMapping.domain.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CityRepositoryTest {

    @Autowired
    private CityRepository repository;

    @Test
    public void testFindByNameAndCountryCodeAllIgnoringCase() throws Exception {
        separator();
        System.out.println("FindByNameAndCountry");
        City city = repository.findByNameAndCountryCodeAllIgnoringCase("hamburg","de");

        System.out.println(city);


    }

    @Test
    public void testFindByCountryCodeAllIgnoringCase() throws Exception {
        separator();
        System.out.println("FindByCountryCode");
        Page<City> page = repository.findByCountryCodeAllIgnoringCase("de",new PageRequest(0,5));

        page.forEach(System.out::println);

        page = repository.findByCountryCodeAllIgnoringCase("de",new PageRequest(1,5));

        page.forEach(System.out::println);

    }

    @Test
    public void testFindUnmappedCities() throws Exception {
        separator();
        System.out.println("FindUnmappedCities");
        Page<City> page = repository.findUnmappedForByCountry("DE", new PageRequest(0,5));

        page.forEach(System.out::println);

    }

    private void separator() {
        System.out.println("-------------------------------------");
    }
}