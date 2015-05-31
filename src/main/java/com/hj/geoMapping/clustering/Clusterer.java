package com.hj.geoMapping.clustering;

import com.hj.geoMapping.common.HasLocation;
import com.hj.geoMapping.common.Marker;
import com.hj.geoMapping.common.ClusterMarker;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by heiko on 18.04.15.
 */

public class Clusterer {


    private double clusterSize;


    Set<ClusterMarker> clusters;


    public static Clusterer clustererWithSize(double clusterSize) {
        return new Clusterer(clusterSize);
    }

    private Clusterer(double clusterSize) {
        this.clusterSize = clusterSize;
        this.clusters = new HashSet<>();
    }



    public Collection<? extends Marker> doCluster(Collection<HasLocation>locations) {

        if(locations.size()<150) {
            return locations.stream().
                    map(location -> new Marker(location)).
                    collect(Collectors.toSet());
        }

        locations.forEach(this::addMarker);

        return clusters;

    }


    void addMarker( HasLocation location) {
        Optional<ClusterMarker> nearestEnclosingCluster = getNearestEnclosingCluster(location);

        if(nearestEnclosingCluster.isPresent()) {
            nearestEnclosingCluster.get().addLocation();
        } else {
            clusters.add(new ClusterMarker(location, clusterSize));
        }
    }

    Optional<ClusterMarker> getNearestEnclosingCluster( HasLocation location) {
        Map<Double,ClusterMarker> enclosingClusters = getEnlcosingClustersWithDistance(location);

        return enclosingClusters.
                keySet().
                stream().
                min((distance1, distance2) -> distance1.compareTo(distance2)).
                map(minDistance -> enclosingClusters.get(minDistance));

    }

    Map<Double,ClusterMarker> getEnlcosingClustersWithDistance( HasLocation location) {
        return clusters.stream().
                filter(cluster -> (cluster.includes(location.getGeoLocation()))).
                        collect(Collectors.toMap(
                                clusterMarker -> location.getGeoLocation().distanceTo(clusterMarker.getLocation()),
                                clusterMarker -> clusterMarker,
                                (clusterMarker1, clusterMarker2) -> clusterMarker1));
    }


}
