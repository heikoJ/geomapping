package com.hj.geoMapping.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by heiko on 12.04.15.
 */
@Data
public class GeoBounds {

    private GeoLocation southWestLocation;

    private GeoLocation northEastLocation;

    public GeoBounds(String bounds) {
        String [] coords = bounds.split(",");
        southWestLocation = createSouthwestLocation(coords);
        northEastLocation = createNorthEastLocation(coords);
    }

    public GeoBounds(GeoLocation southWestLocation, GeoLocation northEastLocation) {
        this.southWestLocation = southWestLocation;
        this.northEastLocation = northEastLocation;
    }

    private GeoLocation createSouthwestLocation(String []coords) {
        return new GeoLocation(Float.valueOf(coords[0]), Float.valueOf(coords[1]));
    }

    private GeoLocation createNorthEastLocation(String []coords) {
        return new GeoLocation(Float.valueOf(coords[2]), Float.valueOf(coords[3]));
    }

    private GeoLocation getSouthEastLocation() {
        return new GeoLocation(southWestLocation.getLatitude(), northEastLocation.getLongitude());
    }

    private GeoLocation getNortWestLocation() {
        return new GeoLocation(northEastLocation.getLatitude(),southWestLocation.getLongitude());
    }

    public boolean intersetcsWith(GeoBounds bounds) {
        return (southWestLocation.isInBounds(bounds)) || northEastLocation.isInBounds(bounds) || getSouthEastLocation().isInBounds(bounds) || getNortWestLocation().isInBounds(bounds);
    }

    public GeoLocation getCenter() {
        return new GeoLocation((northEastLocation.getLatitude() + southWestLocation.getLatitude())/2.0f, (northEastLocation.getLongitude() + southWestLocation.getLongitude()) / 2.0f);
    }



}
