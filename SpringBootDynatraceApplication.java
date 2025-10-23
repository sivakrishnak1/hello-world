package com.example.dynatrace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class SpringBootDynatraceApplication {

    private final MeterRegistry meterRegistry;

    public SpringBootDynatraceApplication(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDynatraceApplication.class, args);
    }

    // Custom metrics examples that will be ingested by Dynatrace OneAgent
    @Bean
    public CustomMetrics customMetrics() {
        return new CustomMetrics(meterRegistry);
    }
}