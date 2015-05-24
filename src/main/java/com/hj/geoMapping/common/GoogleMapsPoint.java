package com.hj.geoMapping.common;

import lombok.Data;

/**
 * Created by heiko on 24.05.15.
 */
@Data
public class GoogleMapsPoint {


    private int x;
    private int y;

    public GoogleMapsPoint(int x, int y) {
        this.x=x;
        this.y=y;
    }

}
