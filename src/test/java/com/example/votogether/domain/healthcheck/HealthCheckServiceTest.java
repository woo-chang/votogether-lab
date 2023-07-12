package com.example.votogether.domain.healthcheck;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HealthCheckServiceTest {

    private final HealthCheckService healthCheckService = new HealthCheckService();

    @Test
    void check() {
        final String result = healthCheckService.check();
        assertThat(result).isEqualTo("health-check");
    }

    @Test
    void health() {
        final String result = healthCheckService.check();
        assertThat(result).isEqualTo("health-check");
    }

}
