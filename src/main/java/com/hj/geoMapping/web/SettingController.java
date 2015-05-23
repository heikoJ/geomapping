package com.hj.geoMapping.web;

import com.hj.geoMapping.domain.Setting;
import com.hj.geoMapping.integration.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heiko on 23.05.15.
 */
@RestController("/settings")
public class SettingController {

    @Autowired
    private SettingRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public Map<String,String> getSettings() {
        Map<String,String> settings = new HashMap<>();
        repository.findAll().forEach(setting -> {
            settings.put(setting.getName(), setting.getValue());
        });

        return settings;
    }


    @RequestMapping(method = RequestMethod.PUT)
    @Transactional
    public void updateSettings(@RequestBody Map<String,String> settings) {
        settings.forEach((name, value) -> {
            upsertSetting(name, value);
        });
    }

    private void upsertSetting(String name,String value) {
        Setting setting = repository.findByName(name);
        if(setting==null) {
            insertSetting(name,value);
        } else {
            setting.setValue(value);
        }
    }

    private void insertSetting(String name,String value) {
        Setting setting = new Setting(name,value);
        repository.save(setting);
    }


}
