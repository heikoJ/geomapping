package com.hj.geoMapping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hj.geoMapping.common.GeoLocation;
import com.hj.geoMapping.common.HasLocation;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.*;


/**
 * Created by heiko on 03.04.15.
 */
@Entity
@Table(name="UN_LOCATION")
@Data
public class UNLocation implements HasLocation{

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;


    @Column(nullable = false, unique = true, name="CODE")
    private String code;


    @Column(nullable = false, name="NAME")
    private String name;

    @Column(name="LATITUDE")
    private Float latitude;

    @Column(name="LONGITUDE")
    private Float longitude;

    public UNLocation(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Transient
    private GeoLocation geoLocation;

    @Transient
    public String getCountryCode() {
        return code.substring(0,2);
    }

    @Transient
    @JsonIgnore
    public GeoLocation getGeoLocation() {
        if(geoLocation==null) {
            geoLocation = new GeoLocation(latitude,longitude);
        }
        return geoLocation;
    }

    public UNLocation() {

    }


    public void setLatitude(Float latitude) {
        this.latitude = latitude;
        if(this.geoLocation!=null)
            this.geoLocation.setLatitude(latitude);
    }


    public void setLongitude(Float longitude) {
        this.longitude = longitude;
        if(geoLocation!=null)
            this.getGeoLocation().setLongitude(longitude);
    }

    public void updateValuesFrom(UNLocation that) {
        this.name = that.name;
        this.setLatitude(that.latitude);
        this.setLongitude(that.longitude);
    }

}
