package com.hj.geoMapping.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by heiko on 03.04.15.
 */
@Entity
@Table(uniqueConstraints =
    @UniqueConstraint(columnNames = {"COUNTRY_CODE","NAME"}))
@Data
public class City {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(nullable=false , name="COUNTRY_CODE")
    private String countryCode;

    @Column(nullable = false, name="NAME")
    private String name;

    @Column(name="LATITUDE")
    private Float latitude;


    @Column(name="LONGITUDE")
    private Float longitude;



    public City(String countryCode, String name) {
        this.countryCode = countryCode;
        this.name = name;
    }

    public City() {
    }

    public void updateValues(City newValues) {
        countryCode = newValues.countryCode;
        name = newValues.name;
        latitude = newValues.latitude;
        longitude = newValues.longitude;
    }

}
