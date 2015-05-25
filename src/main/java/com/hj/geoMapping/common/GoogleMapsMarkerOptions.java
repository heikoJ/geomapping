package com.hj.geoMapping.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by heiko on 18.04.15.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoogleMapsMarkerOptions {

    private static Map<String,String> LABLE_STYLE;

    static {
        LABLE_STYLE = new HashMap<>();
        LABLE_STYLE.put("font-size","12px");
        LABLE_STYLE.put("font-weight","bold");
    }

    private boolean draggable = false;

    private boolean visible=true;

    private String labelContent;

    private Map<String,String> labelStyle = LABLE_STYLE;

    private String icon=null;


    private String labelAnchor;


    public GoogleMapsMarkerOptions(boolean draggable, String label) {
        this.draggable = draggable;
        this.labelContent = label;
    }

    public GoogleMapsMarkerOptions(boolean draggable, String label, ClusterIcon clusterIcon) {
        this.draggable = draggable;
        this.labelContent = label;
        this.icon = clusterIcon.getIcon();
        this.labelAnchor = clusterIcon.getLabelAnchor();
    }



}
