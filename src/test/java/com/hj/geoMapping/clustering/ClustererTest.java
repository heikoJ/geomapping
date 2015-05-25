package com.hj.geoMapping.clustering;

import com.hj.geoMapping.Application;
import com.hj.geoMapping.common.Marker;
import com.hj.geoMapping.common.ClusterMarker;
import com.hj.geoMapping.domain.UNLocation;
import com.hj.geoMapping.integration.UNLocationRepository;
import static com.hj.geoMapping.util.ExecutionTimer.*;

import com.hj.geoMapping.util.ExecutionTimer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ClustererTest {


    @Autowired
    private UNLocationRepository unLocationRepository;



    private static final float[] avgTestValues = {0.451f,1.456f,3.4567f,43566f,4.5567f,3.246f,7.684f,70.7634f,4.4563f,5.7655f,5.67458f,6.7465745f,7.76347f,4.763457f,3.378658743f,2.37865478f};

    @Test
    public void testClustering() throws Exception {
        Iterable<UNLocation> locations = unLocationRepository.findAll();

        Set<Marker> geoLocations = StreamSupport.stream(locations.spliterator(), true).
                unordered().
                map(location -> new Marker(location.getGeoLocation(),
                        location.getName(),
                        location.getId())).
                collect(Collectors.toSet());


        ExecutionTimer timer = timer(System.out, "clustering").start();
        Collection<? extends Marker> clusters = Clusterer.clustererWithSize(10f).doCluster(geoLocations);
        timer.stopAndPrintResults();

        System.out.println("Clusters: " + clusters.size());


        //clusters.forEach(System.out::println);

    }


    @Test
    public void testAvergaCalculation() {
        float sum=0.0f;
        for(float f : avgTestValues) {
            sum+=f;
        }

        float average= sum / avgTestValues.length;

        System.out.println("Averga1: " + average);


        average = avgTestValues[0];

        for(int i=1;i<avgTestValues.length;i++) {
            average += avgTestValues[i]/(i+1) - average/(i+1);
        }

        System.out.println("Averge1: " + average);




    }


}