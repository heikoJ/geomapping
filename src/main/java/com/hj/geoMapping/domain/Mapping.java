package com.hj.geoMapping.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by heiko on 03.04.15.
 */
@Entity
@Data
public class Mapping {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;


    @ManyToOne(cascade = {})
    @JoinColumn(name = "CITY_ID")

    private City city;

    @OneToOne(orphanRemoval = false,cascade = {})
    @JoinColumn(name = "LOCATION_ID")
    private UNLocation location;


    public Mapping(City city, UNLocation location) {
        this.city = city;
        this.location = location;
    }

    public Mapping() {
    }
}
