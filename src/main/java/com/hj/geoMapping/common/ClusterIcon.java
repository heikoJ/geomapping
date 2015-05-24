package com.hj.geoMapping.common;

import lombok.Data;

/**
 * Created by heiko on 24.05.15.
 */

public enum ClusterIcon {

    M1 ("/images/clustering/m1.png","3 32"),
    M2 ("/images/clustering/m2.png","5 35"),
    M3 ("/images/clustering/m3.png","7 40"),
    M4 ("/images/clustering/m4.png","10 45"),
    M5 ("/images/clustering/m5.png","10 50");

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
}
