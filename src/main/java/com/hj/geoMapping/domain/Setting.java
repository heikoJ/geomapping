package com.hj.geoMapping.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by heiko on 23.05.15.
 */
@Entity
@Data
public class Setting {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(name="NAME",unique = true)
    private String name;

    @Column(name="VALUE")
    private String value;

    public Setting() {}

    public Setting(String name,String value) {
        this.setName(name);
        this.setValue(value);
    }

}
