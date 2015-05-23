package com.hj.geoMapping.integration;

import com.hj.geoMapping.domain.Setting;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by heiko on 23.05.15.
 */
public interface SettingRepository extends CrudRepository<Setting,Long> {


    public Setting findByName(String name);

}
