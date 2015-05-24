package com.hj.geoMapping.clustering;

import com.hj.geoMapping.common.Marker;
import com.hj.geoMapping.common.ClusterMarker;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by heiko on 18.04.15.
 */

public class Clusterer {


    private float clusterSize;


    Set<ClusterMarker> clusters;


    public static Clusterer clustererWithSize(float clusterSize) {
        return new Clusterer(clusterSize);
    }

    private Clusterer(float clusterSize) {
        this.clusterSize = clusterSize;
        this.clusters = new HashSet<>();
    }



    public Collection<? extends Marker> doCluster(Collection<Marker>markers) {

        if(markers.size()<150) {
            return markers;
        }

        for (Marker marker : markers) {
            addMarker(marker);
        }

        return clusters;

    }


    private void addMarker( Marker marker) {
        ClusterMarker nearestEnclosingCluster = getNearestEnclosingCluster(marker);

        if(nearestEnclosingCluster!=null) {
            nearestEnclosingCluster.add(marker);
        } else {
            clusters.add(new ClusterMarker(marker, clusterSize));
        }
    }

    private ClusterMarker getNearestEnclosingCluster( Marker marker) {
        Set<ClusterMarker> enclosingClusters = getEnlcosingClusters( marker);
        return getNearestCluster(enclosingClusters,marker);
    }

    private Set<ClusterMarker> getEnlcosingClusters( Marker marker) {
        return clusters.parallelStream().
                filter(cluster -> cluster.includes(marker)).
                collect(Collectors.toSet());
    }


    private ClusterMarker getNearestCluster( Set<ClusterMarker> clusters,Marker marker) {
        ClusterMarker nearestCluster= null;
        float minDistance = Float.MAX_VALUE;
        for(ClusterMarker cluster : clusters) {
            float distance = marker.distanceTo(cluster);
            if (distance < minDistance) {
                minDistance = distance;
                nearestCluster = cluster;
            }
        }

        return nearestCluster;
    }


}
