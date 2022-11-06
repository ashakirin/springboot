package com.talend.microservices.patterns.gateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"httpbin=http://localhost:${wiremock.server.port}",
                "foo-url=http://localhost:${wiremock.server.port}",
        "spring.cloud.gateway.httpclient.proxy.host=",
        "spring.cloud.gateway.httpclient.proxy.port="})
@AutoConfigureWireMock(port = 0)
public class RoutesTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void contextLoads() throws Exception {
        //Stubs
        stubFor(get(urlEqualTo("/get"))
                .willReturn(aResponse()
                        .withBody("{\"headers\":{\"Hello\":\"World\"}}")
                        .withHeader("Content-Type", "application/json")));
        stubFor(get(urlEqualTo("/delay/3"))
                .willReturn(aResponse()
                        .withBody("no fallback")
                        .withFixedDelay(3000)));

        webTestClient
                .get().uri("/get")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.headers.Hello").isEqualTo("World");
    }

    @Test
    public void feeBarTest() throws Exception {
        //Stubs
        stubFor(get(urlEqualTo("/foo/hello"))
                .willReturn(aResponse()
                        .withBody("{\"foo\":{\"Hello\":\"World\"}}")
                        .withHeader("Content-Type", "application/json")));

        webTestClient
                .get().uri("/foo/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("""
                {"foo":{"Hello":"World"}}
""");
    }
}
