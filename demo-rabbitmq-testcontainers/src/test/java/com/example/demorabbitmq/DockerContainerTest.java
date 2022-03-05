package com.example.demorabbitmq;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest(classes = DemoRabbitmqApplication.class , webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DockerContainerTest {
    private static GenericContainer rabbitContainer;

    @Autowired
    MessagingService messagingService;

    @Value("${local.server.port}")
    private int port;

    @BeforeAll
    public static void setupAll() {
        rabbitContainer = startDockerContainer(
                "rabbitmq:3-management",
                List.of(5672, 15672),
                "rabbitmqhost",
                null);
    }

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        rabbitContainer.start();
        registry.add("spring.rabbitmq.host", rabbitContainer::getContainerIpAddress);
        registry.add("spring.rabbitmq.port", rabbitContainer::getFirstMappedPort);
    }

    @Test
    public void checkSendAndReceiveMessage() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/message", "test", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        for (int i = 0; i < 10; i++) {
            if (messagingService.getMessageList().size() > 0) {
                return;
            }
            Thread.sleep(200);
        }
        fail("Haven't received message in 2000 ms");
    }

    private static GenericContainer startDockerContainer(String image,
                                    List<Integer> ports,
                                    String networkAlias,
                                    Network attachNetwork) {
        Network network = attachNetwork == null ? Network.newNetwork() : attachNetwork;

        return new GenericContainer(image)
                .withExposedPorts(ports.toArray(new Integer[0]))
                .withNetwork(network)
                .withNetworkMode("host")
                .withNetworkAliases(networkAlias);
    }
}
