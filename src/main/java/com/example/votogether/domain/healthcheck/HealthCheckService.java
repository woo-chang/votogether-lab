package com.example.votogether.domain.healthcheck;

import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    public String check() {
        return "health-check";
    }

}
