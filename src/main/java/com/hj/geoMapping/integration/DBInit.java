package com.hj.geoMapping.integration;

import com.hj.geoMapping.domain.City;
import com.hj.geoMapping.domain.UNLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by heiko on 08.04.15.
 */
@Component
public class DBInit {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UNLocationRepository unLocationRepository;

    @Autowired
    MappingRepository mappingRepository;


    private static final float MIN_LATITUDE = 47.7f;
    private static final float MAX_LATITUDE = 53.9f;
    private static final float MIN_LONGITUDE = 7.0f;
    private static final float MAX_LONGITUDE = 12.7f;


    public void createRandomCitiesForDE() {

        for(int i=0;i<1000;i++) {
            UNLocation location = new UNLocation("DE" + i,"Loc" + i);
            location.setLatitude(randomInRange(MIN_LATITUDE, MAX_LATITUDE));
            location.setLongitude(randomInRange(MIN_LONGITUDE, MAX_LONGITUDE));

            unLocationRepository.save(location);

            City city = new City("DE","City" + i);
            city.setLatitude(randomInRange(MIN_LATITUDE,MAX_LATITUDE));
            city.setLongitude(randomInRange(MIN_LONGITUDE,MAX_LONGITUDE));

            cityRepository.save(city);
        }
    }


    private float randomInRange(float min, float max) {
        return Double.valueOf(Math.random() * (max-min) + min).floatValue();
    }

}
