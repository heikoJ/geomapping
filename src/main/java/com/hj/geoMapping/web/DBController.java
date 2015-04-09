package com.hj.geoMapping.web;

import com.hj.geoMapping.integration.DBInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by heiko on 08.04.15.
 */
@RestController
@RequestMapping("/db")
@Transactional
public class DBController {

    @Autowired
    DBInit dbInit;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public void initDb() {
        dbInit.createRandomCitiesForDE();
    }

}
