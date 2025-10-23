package com.example.dynatrace;

import io.micrometer.core.instrument.*;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class CustomMetrics {

    private final Counter apiCallCounter;
    private final Counter businessEventCounter;
    private final Timer apiCallTimer;
    private final AtomicInteger activeUsers;
    private final AtomicInteger queueSize;

    public CustomMetrics(MeterRegistry meterRegistry) {
        // Counter for API calls
        this.apiCallCounter = Counter.builder("springboot.api.calls")
                .description("Total number of API calls")
                .tag("application", "springboot-dynatrace-app")
                .register(meterRegistry);

        // Counter for business events
        this.businessEventCounter = Counter.builder("springboot.business.events")
                .description("Total number of business events")
                .tag("application", "springboot-dynatrace-app")
                .register(meterRegistry);

        // Timer for API call duration
        this.apiCallTimer = Timer.builder("springboot.api.duration")
                .description("API call duration")
                .tag("application", "springboot-dynatrace-app")
                .register(meterRegistry);

        // Gauge for active users
        this.activeUsers = new AtomicInteger(0);
        Gauge.builder("springboot.active.users", activeUsers, AtomicInteger::get)
                .description("Number of active users")
                .tag("application", "springboot-dynatrace-app")
                .register(meterRegistry);

        // Gauge for queue size
        this.queueSize = new AtomicInteger(0);
        Gauge.builder("springboot.queue.size", queueSize, AtomicInteger::get)
                .description("Current queue size")
                .tag("application", "springboot-dynatrace-app")
                .register(meterRegistry);

        log.info("Custom metrics initialized for Dynatrace ingestion");
    }

    public void incrementApiCall() {
        apiCallCounter.increment();
    }

    public void recordBusinessEvent(String eventType) {
        businessEventCounter.increment();
    }

    public Timer.Sample startApiCallTimer() {
       // return Timer.start(meterRegistry);
        return Timer.start(Clock.SYSTEM);
    }

    public void stopApiCallTimer(Timer.Sample sample, String endpoint) {
        //sample.stop(apiCallTimer.tag("endpoint", endpoint));
    }

    public void setActiveUsers(int count) {
        activeUsers.set(count);
    }

    public void setQueueSize(int size) {
        queueSize.set(size);
    }
}