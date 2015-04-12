package com.hj.geoMapping.common;

import lombok.Getter;

/**
 * Created by heiko on 12.04.15.
 */
@Getter
public class GeoLocation {

    private float latitude;

    private float longitude;

    public GeoLocation(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

}
