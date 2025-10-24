package com.example.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {

    @Bean
    public OpenTelemetry openTelemetry() {
        // This will be auto-configured based on application.properties
        // and environment variables
        return AutoConfiguredOpenTelemetrySdk.initialize()
                .getOpenTelemetrySdk();
    }
}