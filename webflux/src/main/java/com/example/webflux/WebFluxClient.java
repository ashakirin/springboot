package com.example.webflux;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class WebFluxClient {
    private WebClient webClient;

    public WebFluxClient(@Value("${paypal.base.url}") String paypalBaseUrl,
                         @Value("${proxy.host}") String proxyHost,
                         @Value("${proxy.port}") String proxyPort) {
        HttpClient httpClient = HttpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));


        httpClient = httpClient
                .proxy(proxy -> proxy
                        .type(ProxyProvider.Proxy.HTTP)
                        .host(proxyHost)
                        .port(Integer.parseInt(proxyPort)));

        ReactorClientHttpConnector reactorClientHttpConnector = new ReactorClientHttpConnector(httpClient);

        webClient = WebClient
                .builder()
                .clientConnector(reactorClientHttpConnector)
                .baseUrl(paypalBaseUrl)
                .build();

    }

    public String retrieveString() {
        return webClient
                .get()
                .uri(URI.create("/test"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    return Mono.error(new RuntimeException("server error"));
                })
                .bodyToMono(String.class)
                .onErrorMap(t -> new RuntimeException("Paypal client error: " + t.getMessage(), t))
                .block();
    }
}
