package com.example.dynatrace.controller;

import com.example.dynatrace.CustomMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import io.micrometer.core.instrument.Timer;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    private final CustomMetrics customMetrics;
    private final ConcurrentHashMap<String, String> dataStore;
    private final Random random;

    public ApiController(CustomMetrics customMetrics) {
        this.customMetrics = customMetrics;
        this.dataStore = new ConcurrentHashMap<>();
        this.random = new Random();
    }

    @GetMapping("/hello")
    public String hello() {
        Timer.Sample sample = customMetrics.startApiCallTimer();
        customMetrics.incrementApiCall();

        try {
            // Simulate processing time
            Thread.sleep(random.nextInt(100));
            customMetrics.recordBusinessEvent("hello_call");
            log.info("Hello API called");
            return "Hello from Spring Boot with Dynatrace OneAgent!";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Error occurred";
        } finally {
            customMetrics.stopApiCallTimer(sample, "/api/hello");
        }
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        Timer.Sample sample = customMetrics.startApiCallTimer();
        customMetrics.incrementApiCall();

        try {
            Thread.sleep(random.nextInt(50));

            if (dataStore.containsKey(id)) {
                customMetrics.recordBusinessEvent("user_retrieved");
                log.info("User retrieved with ID: {}", id);
                return new User(id, dataStore.get(id));
            } else {
                customMetrics.recordBusinessEvent("user_not_found");
                throw new RuntimeException("User not found");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error occurred");
        } finally {
            customMetrics.stopApiCallTimer(sample, "/api/users/{id}");
        }
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        Timer.Sample sample = customMetrics.startApiCallTimer();
        customMetrics.incrementApiCall();

        try {
            Thread.sleep(random.nextInt(80));
            String userId = String.valueOf(System.currentTimeMillis());
            dataStore.put(userId, user.name());
            customMetrics.recordBusinessEvent("user_created");
            customMetrics.setActiveUsers(dataStore.size());
            log.info("User created with ID: {}", userId);
            return new User(userId, user.name());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error occurred");
        } finally {
            customMetrics.stopApiCallTimer(sample, "/api/users");
        }
    }

    @GetMapping("/metrics/active-users")
    public String updateActiveUsers(@RequestParam int count) {
        customMetrics.setActiveUsers(count);
        return "Active users updated to: " + count;
    }

    @GetMapping("/metrics/queue-size")
    public String updateQueueSize(@RequestParam int size) {
        customMetrics.setQueueSize(size);
        return "Queue size updated to: " + size;
    }

    public record User(String id, String name) {}
}