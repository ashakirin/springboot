package com.example.wiremocksimple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.logging.Level;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
//@WireMockTest
public class WiremockTest {
    Logger log = LoggerFactory.getLogger(WiremockTest.class);
//    @Autowired
//    TestRestTemplate testRestTemplate;

    @Value("${wiremock.server.port}")
    String port;

    @Test
    public void shouldCallWireMock() {
//        stubFor(get(urlEqualTo("/request")).willReturn(ResponseDefinitionBuilder.responseDefinition()
//                .withBody("testBody")
//                .withHeader("Content-Type", "text/plain")
//        .withStatus(201)));

        ObjectMapper newMapper = new ObjectMapper();
        newMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(newMapper));
                }).build();

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .exchangeStrategies(strategies)
                .build();

        TestDTO testDTO = webClient
                .get()
                .uri("/request")
                .retrieve()
                .bodyToMono(TestDTO.class)
                .block();

        assertThat(testDTO.getMyAttribute()).isEqualTo("test");
    }

    @Test
    public void proceedErrors() {
//        stubFor(get(urlEqualTo("/request")).willReturn(ResponseDefinitionBuilder.responseDefinition()
//                .withBody("testBody")
//                .withHeader("Content-Type", "text/plain")
//        .withStatus(201)));

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        TestDTO testDTO = webClient
                .get()
                .uri("/error")
                .retrieve()
                .onStatus(HttpStatus::isError, r -> {
                    logErrorResponse(r);
                    return handlePayPalErrorResponse(r); //Mono.error(new RuntimeException("test"));
                }) //this::handlePayPalErrorResponse)
                .bodyToMono(TestDTO.class)
                .block();
    }

    private Mono<? extends Throwable> handlePayPalErrorResponse(ClientResponse clientResponse) {
        //TODO: Throws error to be fixed.
//        logErrorResponse(clientResponse);
        return switch (clientResponse.statusCode()) {
            case UNAUTHORIZED -> clientResponse.bodyToMono(String.class).map(
                    r -> new RuntimeException("unauthorized"));
            case BAD_REQUEST -> clientResponse.bodyToMono(String.class).map(
                    r -> new RuntimeException("bad request"));
            default -> clientResponse.bodyToMono(String.class).map(
                    r -> new RuntimeException("default"));
        };
    }

    private void logErrorResponse(ClientResponse response) {
        log.error("Response status: {}", response.statusCode());
        log.error("Response headers: {}", response.headers().asHttpHeaders());
        response.bodyToMono(String.class)
                .publishOn(Schedulers.boundedElastic())
                .subscribe(body -> log.error("Response body: {}", body));
    }
}
