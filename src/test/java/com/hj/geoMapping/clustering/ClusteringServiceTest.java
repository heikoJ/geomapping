package com.hj.geoMapping.clustering;

import com.hj.geoMapping.Application;
import com.hj.geoMapping.common.MarkerCluster;
import com.hj.geoMapping.common.GeoLocation;
import com.hj.geoMapping.domain.UNLocation;
import com.hj.geoMapping.integration.UNLocationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ClusteringServiceTest {

    @Autowired
    private ClusteringService clusteringService;

    @Autowired
    private UNLocationRepository unLocationRepository;


    @Test
    public void testClustering() throws Exception {
        Iterable<UNLocation> locations = unLocationRepository.findAll();

        Set<GeoLocation> geoLocations = StreamSupport.stream(locations.spliterator(),true).unordered().map(location -> location.getGeoLocation()).collect(Collectors.toSet());

        Set<MarkerCluster> clusters = clusteringService.doCluster(geoLocations,10f);

        System.out.println("Clusters: " + clusters.size());

        clusters.forEach(System.out::println);

    }
}