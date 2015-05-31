package com.hj.geoMapping.common;

/**
 * Created by heiko on 28.05.15.
 */
public interface HasLocation {

    Long getId();
    GeoLocation getGeoLocation();
    String getName();

}
