package com.hj.geoMapping.common;

import lombok.Getter;

/**
 * Created by heiko on 12.04.15.
 */
@Getter
public class GeoBounds {

    private GeoLocation southWestLocation;

    private GeoLocation northEastLocation;

    public GeoBounds(String bounds) {
        String [] coords = bounds.split(",");
        southWestLocation = createSouthwestLocation(coords);
        northEastLocation = createNorthEastLocation(coords);
    }

    private GeoLocation createSouthwestLocation(String []coords) {
        return new GeoLocation(Float.valueOf(coords[0]), Float.valueOf(coords[1]));
    }

    private GeoLocation createNorthEastLocation(String []coords) {
        return new GeoLocation(Float.valueOf(coords[2]), Float.valueOf(coords[3]));
    }



}
