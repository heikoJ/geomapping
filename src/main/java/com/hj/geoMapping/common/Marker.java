package com.hj.geoMapping.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by heiko on 19.04.15.
 */

@Data
public class Marker {

    private GeoLocation location;

    private String name;

    private Long id;

    public Marker(GeoLocation location, String name, Long id) {
        this.id = id;
        this.location = location;
        this.name = name;
    }


    @JsonProperty("options")
    public GoogleMapsMarkerOptions getOptions() {
        return new GoogleMapsMarkerOptions(false,"https://chart.googleapis.com/chart?chst=d_bubble_text_small_withshadow&chld=edge_bc|" + name + "|C6EF8C|000000");
    }



}
