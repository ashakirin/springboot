package com.example.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Order service entry point.
 *
 * <p>{@link Modulithic} marks this as a Spring Modulith application so the application
 * module structure (one module per top-level package below this class) is detected and verified.
 * Scheduling is enabled because we run a periodic job that resubmits failed event publications.
 */
@SpringBootApplication
@Modulithic
@EnableScheduling
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}
