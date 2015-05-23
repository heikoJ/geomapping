package com.hj.geoMapping.clustering;

import com.hj.geoMapping.common.Marker;
import com.hj.geoMapping.common.MarkerCluster;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by heiko on 18.04.15.
 */
@Service
public class ClusteringService {


    public Set<Marker> doCluster(Collection<Marker>markers, float clusterSize) {

        Set<MarkerCluster> clusters = new HashSet<>();

        for (Marker marker : markers) {
            addMarkerToClusters(clusters, marker, clusterSize);
        }

        return clusters.parallelStream().map(cluster->cluster.toMarker()).collect(Collectors.toSet());

    }


    private void addMarkerToClusters(Set<MarkerCluster> clusters, Marker marker, float clusterSize) {
        MarkerCluster nearestEnclosingCluster = getNearestEnclosingCluster(clusters,marker);

        if(nearestEnclosingCluster!=null) {
            nearestEnclosingCluster.add(marker);
        } else {
            clusters.add(new MarkerCluster(marker, clusterSize));
        }
    }

    private MarkerCluster getNearestEnclosingCluster(Set<MarkerCluster> clusters, Marker marker) {
        Set<MarkerCluster> enclosingClusters = getEnlcosingClusters(clusters, marker);
        return getNearestCluster(enclosingClusters,marker);
    }

    private Set<MarkerCluster> getEnlcosingClusters(Set<MarkerCluster> clusters, Marker marker) {
        return clusters.parallelStream().
                filter(cluster -> cluster.isInCluster(marker)).
                collect(Collectors.toSet());
    }


    private MarkerCluster getNearestCluster(Set<MarkerCluster> clusters, Marker marker) {
        MarkerCluster nearestCluster= null;
        float minDistance = Float.MAX_VALUE;
        for(MarkerCluster cluster : clusters) {
            float distance = marker.getLocation().getDistanceTo(cluster.getCenter());
            if (distance < minDistance) {
                minDistance = distance;
                nearestCluster = cluster;
            }
        }

        return nearestCluster;
    }


}
