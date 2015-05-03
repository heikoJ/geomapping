package com.hj.geoMapping.integration;

import com.hj.geoMapping.Application;
import com.hj.geoMapping.domain.UNLocation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UNLocationRepositoryTest {

    @Autowired
    UNLocationRepository repository;

    @Test
    public void testFindByCodeAllIgnoringCase() throws Exception {
        separator();
        UNLocation loc = repository.findByCodeAllIgnoringCase("DEHAM");

        Assert.assertEquals("Hamburg", loc.getName());
    }

    @Test
    public void testFindByCountryCodeAllIgnoringCase() throws Exception {
        separator();
        List<UNLocation> page = repository.findByCountryCodeAllIgnoringCase("de");
        page.forEach(System.out::println);
    }

    @Test
    public void testFindUnmappedByCountry() throws Exception {
        separator();
        Page<UNLocation> page = repository.findUnmappedByCountry("de", new PageRequest(0, 100));
        page.forEach(System.out::println);
    }

    @Test
    public void testGetDistinctCountryCode() throws Exception {
        separator();
        Set<String> countries = repository.getDistinctCountryCode();

        countries.forEach(System.out::println);

    }



    private void separator() {
        System.out.println("-------------------------------------");
    }
}