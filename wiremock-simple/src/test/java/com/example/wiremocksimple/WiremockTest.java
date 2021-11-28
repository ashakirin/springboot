package com.example.wiremocksimple;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WireMockTest
@AutoConfigureWireMock(port = 0)
public class WiremockTest {
    RestTemplate restTemplate = new RestTemplate();

    @Value("${wiremock.server.port}")
    String port;

    @Test
    public void shouldCallWireMock() {
        stubFor(get(urlEqualTo("/request")).willReturn(ResponseDefinitionBuilder.responseDefinition()
                .withBody("testBody")
                .withHeader("Content-Type", "text/plain")
        .withStatus(201)));

        final ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port + "/request", String.class);
        assertEquals("testBody", entity.getBody());
        assertEquals(201, entity.getStatusCode().value());
    }

}
