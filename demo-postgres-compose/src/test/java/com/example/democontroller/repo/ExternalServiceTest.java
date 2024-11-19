package com.example.democontroller.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestContainerConfiguration.class)
public class ExternalServiceTest {

    @Value("${external-service.port:-1}")
    int port;

    @Test
    void checkPort() {
        assertThat(port).isGreaterThan(0);
    }
}
