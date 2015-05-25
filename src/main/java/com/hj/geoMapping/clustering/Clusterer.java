package com.hj.geoMapping.clustering;

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



    public Collection<? extends Marker> doCluster(Collection<Marker>markers) {

        if(markers.size()<150) {
            return markers;
        }

        markers.forEach(this::addMarker);

        return clusters;

    }


    void addMarker( Marker marker) {
        Optional<ClusterMarker> nearestEnclosingCluster = getNearestEnclosingCluster(marker);

        if(nearestEnclosingCluster.isPresent()) {
            nearestEnclosingCluster.get().add(marker);
        } else {
            clusters.add(new ClusterMarker(marker, clusterSize));
        }
    }

    Optional<ClusterMarker> getNearestEnclosingCluster( Marker marker) {
        Map<Double,ClusterMarker> enclosingClusters = getEnlcosingClustersWithDistance(marker);

        return enclosingClusters.
                keySet().
                stream().
                min((distance1, distance2) -> distance1.compareTo(distance2)).
                map(minDistance -> enclosingClusters.get(minDistance));

    }

    Map<Double,ClusterMarker> getEnlcosingClustersWithDistance( Marker marker) {
        return clusters.stream().
                filter(cluster -> cluster.includes(marker)).
                collect (Collectors.toMap(
                                clusterMarker -> marker.distanceTo(clusterMarker),
                                clusterMarker -> clusterMarker,
                                (clusterMarker1,clusterMarker2) -> clusterMarker1));
    }


}
