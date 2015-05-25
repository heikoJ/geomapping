package com.hj.geoMapping.common;

import lombok.Data;

/**
 * Created by heiko on 24.05.15.
 */

public enum ClusterIcon {

    EXTRA_SMALL("/images/clustering/m1.png","3 32"),
    SMALL("/images/clustering/m2.png","5 35"),
    MEDIUM("/images/clustering/m3.png","7 40"),
    LARGE("/images/clustering/m4.png","10 45"),
    EXTRA_LARGE("/images/clustering/m5.png","10 50");

    private String icon;

    private String labelAnchor;

    ClusterIcon(String icon,String labelAnchor) {
        this.icon = icon;
        this.labelAnchor = labelAnchor;
    }

    public String getIcon() {
        return icon;
    }

    public String getLabelAnchor() {
        return labelAnchor;
    }

    public static ClusterIcon iconForMarkerCount(int markerCount) {
        if(markerCount<10) {
            return EXTRA_SMALL;
        } else if(markerCount<100) {
            return SMALL;
        } else if(markerCount<1000) {
            return MEDIUM;
        } else if(markerCount<10000) {
            return LARGE;
        } else {
            return EXTRA_LARGE;
        }
    }

}
