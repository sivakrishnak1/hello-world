package com.otle.ctotledemo;

import io.opentelemetry.api.GlobalOpenTelemetry;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CtotledemoApplication {

	public static void main(String[] args) {
		// Set OpenTelemetry system properties before Spring Boot starts
//		System.setProperty("otel.service.name", "skk-service");
//		System.setProperty("otel.exporter.otlp.endpoint", "https://xmz83804.apps.dynatrace.com/api/v2/otlp/");
//		System.setProperty("otel.exporter.otlp.headers", "Authorization=Api-Token dt0c01.RN3RXG5KBFTZ5XPOECY27EWU.FL2EFZRWX6XMGULOZJZT3ES272W2DRSHJZUHX55HKV6L65YO5URWVT2COMZCIKMB");
//		System.setProperty("otel.resource.attributes", "service.name=skk-service,deployment.environment=production");
//		System.setProperty("otel.traces.sampler", "always_on");
		SpringApplication.run(CtotledemoApplication.class, args);
	}

	@PostConstruct
	public void checkOpenTelemetry() {
		System.out.println("=== OpenTelemetry Diagnostic ===");
		//System.out.println("OTEL Service Name: " + System.getenv("OTEL_SERVICE_NAME"));
		//System.out.println("OTEL Endpoint: " + System.getenv("OTEL_EXPORTER_OTLP_ENDPOINT"));

		try {
			var otel = GlobalOpenTelemetry.get();
			if (otel != null) {
				System.out.println("✓ OpenTelemetry is initialized");
			} else {
				System.out.println("✗ OpenTelemetry is NOT initialized");
			}
		} catch (Exception e) {
			System.out.println("✗ OpenTelemetry error: " + e.getMessage());
		}
		System.out.println("=================================");
	}
}
