package com.example.demorabbitmq;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import java.util.List;

public class DockerContainerTest {

    @Test
    public void startContainers() {
        startDockerContainer(
                "rabbitmq:3-management",
                List.of(5672, 15672),
                "amqphost",
                null);
    }

    private void startDockerContainer(String image,
                                    List<Integer> ports,
                                    String networkAlias,
                                    Network attachNetwork) {
        Network network = attachNetwork == null ? Network.newNetwork() : attachNetwork;

        GenericContainer genericContainer = new GenericContainer(image)
                .withExposedPorts(ports.toArray(new Integer[0]))
                .withNetwork(network)
                .withNetworkMode("host")
                .withNetworkAliases(networkAlias);
        genericContainer.start();
    }
}
