package com.example.citestcontainers;

import org.testcontainers.containers.GenericContainer;

// Concurse build
public class UsageTestContainerTest {

    GenericContainer container = new GenericContainer(image)
            .withExposedPorts(ports.toArray(new Integer[0]))
            .withNetwork(network)
            .withNetworkMode("host")
            .withNetworkAliases(networkAlias);

}
