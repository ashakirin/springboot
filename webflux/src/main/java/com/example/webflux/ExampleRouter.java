package com.example.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ExampleRouter {
    @Bean
    public RouterFunction<ServerResponse> routeExample (ExampleHandler exampleHandler) {
        RouterFunction<ServerResponse> route = RouterFunctions
                .route(RequestPredicates.GET("/hello"), exampleHandler::hello);

        return route;
    }
}
