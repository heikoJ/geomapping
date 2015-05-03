package com.hj.geoMapping.common;

import lombok.Data;

/**
 * Created by heiko on 18.04.15.
 */
@Data
public class GoogleMapsMarkerOptions {


    private boolean draggable = false;

    private String icon;


    public GoogleMapsMarkerOptions(boolean draggable, String icon) {
        this.draggable = draggable;
        this.icon = icon;
    }



}
