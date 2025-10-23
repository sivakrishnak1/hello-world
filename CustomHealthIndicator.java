package com.example.dynatrace.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    private final Random random = new Random();

    @Override
    public Health health() {
        // Simulate health check with occasional failures
        boolean isHealthy = random.nextInt(10) > 1; // 80% healthy

        if (isHealthy) {
            return Health.up()
                    .withDetail("service", "springboot-dynatrace-app")
                    .withDetail("status", "operational")
                    .build();
        } else {
            return Health.down()
                    .withDetail("service", "springboot-dynatrace-app")
                    .withDetail("status", "degraded")
                    .withDetail("reason", "simulated failure")
                    .build();
        }
    }
}