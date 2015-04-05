package com.hj.geoMapping.domain;

import lombok.Data;

import javax.persistence.*;
import java.beans.Transient;

/**
 * Created by heiko on 03.04.15.
 */
@Entity
@Table(name="UN_LOCATION")
@Data
public class UNLocation {

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
    public String getCountryCode() {
        return code.substring(0,2);
    }

    public UNLocation() {
    }
}
