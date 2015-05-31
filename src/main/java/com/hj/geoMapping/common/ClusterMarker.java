package com.hj.geoMapping.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
    private double size;

    public ClusterMarker(HasLocation location, double size) {
        super(location.getGeoLocation(),location.getName(),location.getId());
        this.size = size;
        makeBounds();
        this.markerCount = 1;
    }




    public boolean includes(GeoLocation location) {
        return location.isInBounds(bounds);
    }



    public void addLocation() {
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
        return new GoogleMapsMarkerOptions(true,name,ClusterIcon.iconForMarkerCount(this.markerCount));
    }




}
