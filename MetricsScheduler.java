package com.example.dynatrace.scheduler;

import com.example.dynatrace.CustomMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
@Slf4j
public class MetricsScheduler {

    private final CustomMetrics customMetrics;
    private final Random random;

    public MetricsScheduler(CustomMetrics customMetrics) {
        this.customMetrics = customMetrics;
        this.random = new Random();
    }

    // Update simulated metrics every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void updateSimulatedMetrics() {
        int activeUsers = random.nextInt(1000);
        int queueSize = random.nextInt(500);

        customMetrics.setActiveUsers(activeUsers);
        customMetrics.setQueueSize(queueSize);

        log.debug("Updated simulated metrics - Active Users: {}, Queue Size: {}", activeUsers, queueSize);
    }
}