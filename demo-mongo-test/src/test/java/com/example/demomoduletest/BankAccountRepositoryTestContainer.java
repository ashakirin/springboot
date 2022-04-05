package com.example.demomoduletest;

import com.example.demomoduletest.repository1.BankAccount;
import com.example.demomoduletest.repository1.BankAccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@DataMongoTest
//@Import(MongoTestDataConfig.class)
public class BankAccountRepositoryTestContainer {
    private static GenericContainer mongoContainer;

    @Autowired
    private BankAccountRepository bankAccountRepository;
    private String id1;
    private String id2;

    @BeforeAll
    public static void setupAll() {
        mongoContainer = startDockerContainer(
                "mongo:5.0.6",
                List.of(27017, 27017),
                "mongodb",
                null);
    }

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        mongoContainer.start();
        registry.add("spring.data.mongodb.host", mongoContainer::getContainerIpAddress);
        registry.add("spring.data.mongodb.port", mongoContainer::getFirstMappedPort);
    }

    @BeforeEach
    public void setup() {
        BankAccount bankAccount1 = new BankAccount();
        id1 = UUID.randomUUID().toString();
        bankAccount1.setId(id1);
        bankAccount1.setAccountNumber("testAccount");
        bankAccount1.setAmount(10);
        bankAccountRepository.save(bankAccount1);

        BankAccount bankAccount2 = new BankAccount();
        id2 = UUID.randomUUID().toString();
        bankAccount2.setId(id2);
        bankAccount2.setAccountNumber("testAccount");
        bankAccount2.setAmount(30);
        bankAccountRepository.save(bankAccount2);

    }

    @Test
    public void shouldGetBankAccount() throws InterruptedException {
        BankAccount bankAccountRead = bankAccountRepository.findById(id1).get();
        assertThat(bankAccountRead.getAccountNumber()).isEqualTo("testAccount");
    }

    @Test
    public void shouldFindPreloadAccount() throws InterruptedException {
        BankAccount bankAccountRead = bankAccountRepository.findById("preloadId1").get();
        assertThat(bankAccountRead.getAccountNumber()).isEqualTo("bankAccountTestPreload1");
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
