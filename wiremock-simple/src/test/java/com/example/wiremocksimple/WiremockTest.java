package com.example.wiremocksimple;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
//@WireMockTest
public class WiremockTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Value("${wiremock.server.port}")
    String port;

    @Test
    public void shouldCallWireMock() {
//        stubFor(get(urlEqualTo("/request")).willReturn(ResponseDefinitionBuilder.responseDefinition()
//                .withBody("testBody")
//                .withHeader("Content-Type", "text/plain")
//        .withStatus(201)));

        final ResponseEntity<String> entity = testRestTemplate.getForEntity("http://localhost:" + port + "/request", String.class);
//        final ResponseEntity<String> entity = testRestTemplate.getForEntity("/request", String.class);
        assertEquals("testBody", entity.getBody());
        assertEquals(201, entity.getStatusCode().value());
    }

}
