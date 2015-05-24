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
public class ClusterMarker extends Marker {

    private static AtomicLong idCounter = new AtomicLong();

    private static long createID()
    {
        return idCounter.getAndDecrement();
    }


    @JsonProperty
    private GeoBounds bounds;


    @JsonIgnore
    private int markerCount;

    @JsonIgnore
    private float size;

    public ClusterMarker(Marker marker, float size) {
        super(marker.getLocation(),marker.getName(),marker.getId());
        this.size = size;
        makeBounds();
        this.markerCount = 1;
    }




    public boolean includes(Marker marker) {
        return marker.getLocation().isInBounds(bounds);
    }



    public void add(Marker marker) {
        setId(createID());
        markerCount++;
        name = String.valueOf(markerCount);
       // this.location.addToLatitude(markerLocation.getLatitude()/markerCount - this.location.getLatitude()/markerCount);
       // this.location.addToLongitude(markerLocation.getLongitude()/markerCount - this.location.getLongitude()/markerCount);
       // makeBounds();
    }


    private void makeBounds() {
        bounds = new GeoBounds(new GeoLocation(location.getLatitude() - size, location.getLongitude() - size), new GeoLocation(location.getLatitude() + size,location.getLongitude() + size));
    }

    @JsonProperty("options")
    public GoogleMapsMarkerOptions getOptions() {
        if(this.markerCount==1) {
            return super.getOptions();
        }
        return new GoogleMapsMarkerOptions(true,name,getClusterIcon());
    }

    private ClusterIcon getClusterIcon() {
        if(this.markerCount<10) {
            return ClusterIcon.M1;
        } else if(this.markerCount<100) {
            return ClusterIcon.M2;
        } else if(this.markerCount<1000) {
            return ClusterIcon.M3;
        } else if(this.markerCount<10000) {
            return ClusterIcon.M4;
        } else {
            return ClusterIcon.M5;
        }
    }



}
