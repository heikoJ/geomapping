package com.hj.geoMapping.common;

import lombok.Data;

/**
 * Created by heiko on 12.04.15.
 */
@Data
public class GeoLocation {

    private float latitude;

    private float longitude;

    public GeoLocation(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void addToLatitude(float x) {
        this.latitude+=x;
    }

    public void addToLongitude(float x) {
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

    public float getDistanceTo(GeoLocation location) {
        float deltaLongitude = location.longitude - longitude;
        float deltaLatitude = location.latitude - latitude;

        double a = Math.pow((Math.sin(deltaLatitude/2.0d)),2) + Math.cos(latitude) * Math.cos(location.latitude) * Math.pow((Math.sin(deltaLongitude/2.0d)),2);
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = 6373 * c;

        return new Double(distance).floatValue();
    }

}

