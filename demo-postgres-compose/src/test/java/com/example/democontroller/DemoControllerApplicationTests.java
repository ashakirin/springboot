package com.example.democontroller;

import com.example.democontroller.repo.TestContainerConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.properties.TestcontainersPropertySourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(OutputCaptureExtension.class)
@Import(TestContainerConfiguration.class)
class DemoControllerApplicationTests {
    @LocalServerPort
    private int port;

    @Test
    void controllerCall(CapturedOutput capturedOutput) {
        RestClient restClient = RestClient.create();

        String body = restClient.get()
                .uri("http://localhost:" + port + "/demo")
                .retrieve()
                .body(String.class);
        assertThat(body).isEqualTo("my demo");
        assertThat(capturedOutput.getOut()).contains("tomcat");
    }
}
