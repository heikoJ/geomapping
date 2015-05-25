package com.hj.geoMapping.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by heiko on 12.04.15.
 */
@Data
public class GeoLocation {

    @JsonProperty("lat")
    private double latitude;

    @JsonProperty("lng")
    private double longitude;

    public GeoLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void addToLatitude(double x) {
        this.latitude+=x;
    }

    public void addToLongitude(double x) {
        this.longitude+=x;
    }


    public boolean equalsOrNorthEastOf (GeoLocation location) {
        return latitude >= location.latitude && longitude >= location.longitude;
    }

    public boolean equalsOrSouthWestOf(GeoLocation location) {
        return latitude <= location.latitude && longitude <= location.longitude;
    }


    public boolean isInBounds(GeoBounds bounds) {
        return equalsOrNorthEastOf(bounds.getSouthWestLocation()) &&
                equalsOrSouthWestOf(bounds.getNorthEastLocation());
    }

    public double distanceTo(GeoLocation location) {
        double deltaLongitude = location.longitude - longitude;
        double deltaLatitude = location.latitude - latitude;

        double latSin = Math.sin(deltaLatitude/2.0d);
        double lonSin = Math.sin(deltaLongitude/2.0d);

        double a = latSin * latSin + Math.cos(latitude) * Math.cos(location.latitude) * lonSin * lonSin;
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6373 * c;

    }

}

