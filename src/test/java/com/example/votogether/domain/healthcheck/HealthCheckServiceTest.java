package com.example.votogether.domain.healthcheck;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HealthCheckServiceTest {

    private final HealthCheckService healthCheckService = new HealthCheckService();

    @Test
    void check() {
        final String result = healthCheckService.check();
        Assertions.assertThat(result).isEqualTo("health");
    }

}
