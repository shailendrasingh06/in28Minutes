package com.in28minutes.microservices.limitservice.controller;

import com.in28minutes.microservices.limitservice.beans.Limits;
import com.in28minutes.microservices.limitservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    Configuration propertyConfiguration;

    @GetMapping("limits/")
    public Limits retreiveLimits() {
        return new Limits(propertyConfiguration.getMinimum(), propertyConfiguration.getMaximum());
    }
}
