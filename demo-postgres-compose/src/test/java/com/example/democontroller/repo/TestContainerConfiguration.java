package com.example.democontroller.repo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
//@Testcontainers
public class TestContainerConfiguration {
//    @Container
//    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");
//    @Container
//    private static GenericContainer<?> nginxContainer = new GenericContainer<>("nginx:latest").withExposedPorts(80);

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgres:latest");
    }

    @Bean
    @DynamicPropertySource
    public GenericContainer<?> nginxContainer(DynamicPropertyRegistry registry) {
        GenericContainer<?> nginxContainer = new GenericContainer<>("nginx:latest").withExposedPorts(80);
        registry.add("external-service.port", () -> nginxContainer.getFirstMappedPort());
        return nginxContainer;
    }
}
