package com.example.democontroller.repo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainerConfiguration {
    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgres:latest");
    }

    @Bean
    @DynamicPropertySource
    public GenericContainer<?> nginxContainer(DynamicPropertyRegistry registry) {
        var container = new GenericContainer<>("nginx:latest").withExposedPorts(80);
        registry.add("external-service.port", () -> container.getFirstMappedPort());
        return container;
    }
}
