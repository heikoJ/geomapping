package com.hj.geoMapping.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by heiko on 04.04.15.
 */
@Entity
@Table(name="COUNTRY")
@Data
public class Country {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(name="CODE")
    private String code;

    @Column(name="NAME")
    private String name;

    @Column(name="LATITUDE")
    private Float latitude;

    @Column(name="LONGITUDE")
    private Float longitude;


    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Country() {
    }


    public String getDisplayName() {
        return code + " - " + name;
    }

}
