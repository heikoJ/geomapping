package com.hj.geoMapping.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by heiko on 18.04.15.
 */
@Data
public class MarkerCluster  {

    private static AtomicLong idCounter = new AtomicLong();

    private static long createID()
    {
        return idCounter.getAndIncrement();
    }

    @JsonIgnore
    private GeoBounds bounds;

    @JsonIgnore
    private GeoLocation center;


    @JsonIgnore
    private int markerCount;

    private Marker firstMarker;

    @JsonIgnore
    private float size;

    public MarkerCluster(Marker marker, float size) {
        this.size = size;
        this.center = marker.getLocation();
        this.bounds = makeBoundsForLocation(center);
        this.markerCount = 1;
        this.firstMarker = marker;
    }




    public boolean isInCluster(Marker marker) {
        return marker.getLocation().isInBounds(bounds);
    }



    public void add(Marker marker) {
        GeoLocation location = marker.getLocation();
        markerCount++;
        this.center.addToLatitude(location.getLatitude()/markerCount - this.center.getLatitude()/markerCount);
        this.center.addToLongitude(location.getLongitude()/markerCount - this.center.getLongitude()/markerCount);
        this.bounds = makeBoundsForLocation(center);
    }


    private GeoBounds makeBoundsForLocation(GeoLocation location) {
        return new GeoBounds(new GeoLocation(location.getLatitude() - size, location.getLongitude() - size), new GeoLocation(location.getLatitude() + size,location.getLongitude() + size));
    }

    public Marker toMarker() {
        if(markerCount==1) return firstMarker;
        return new Marker(center,String.valueOf(markerCount),createID());
    }


}
