package com.boaglio.rinhadebackend2024.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActuatorAPI {

    @GetMapping("/actuator/health")
    public String health() {
        return "{\"status\": \"UP\"}";
    }
}
