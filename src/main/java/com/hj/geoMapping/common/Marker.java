package com.hj.geoMapping.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by heiko on 19.04.15.
 */

@Data
public class Marker {

    @JsonIgnore
    protected GeoLocation location;

    protected String name;

    private Long id;

    public Marker(GeoLocation location, String name, Long id) {
        this.id = id;
        this.location = location;
        this.name = name;
    }


    @JsonProperty
    public double getLatitude() {
        return location.getLatitude();
    }

    @JsonProperty
    public double getLongitude() {
        return location.getLongitude();
    }


    @JsonProperty("options")
    public GoogleMapsMarkerOptions getOptions() {
        return new GoogleMapsMarkerOptions(true,name);
    }


    public double distanceTo(Marker marker) {
        return location.distanceTo(marker.location);
    }


}
