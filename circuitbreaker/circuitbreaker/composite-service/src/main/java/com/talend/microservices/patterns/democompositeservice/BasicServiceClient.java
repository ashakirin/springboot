package com.talend.microservices.patterns.democompositeservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BasicServiceClient {
    private static final String HYSTRIX_DEFAULT_CONFIG = "default";
    public static final String LONG_RUNNING_CALL = "default";
    Logger LOGGER = LoggerFactory.getLogger(BasicServiceClient.class);
    private static final String HYSTRIX_HELLO = "hello";
    private static final String DEFAULT_HYSTRIX_CONFIG = "default";
    private final String basicHelloUrl;

    public BasicServiceClient(@Value("${basic.hello.url}") final String basicHelloUrl) {
        this.basicHelloUrl = basicHelloUrl;
    }

    @HystrixCommand(fallbackMethod = "defaultFallback",
            groupKey = HYSTRIX_DEFAULT_CONFIG,
            commandKey = HYSTRIX_DEFAULT_CONFIG,
            threadPoolKey = LONG_RUNNING_CALL
//            commandProperties = {
//                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//            }
    )
    public String invokeBasic() {
        try {
            LOGGER.info("Invoking basic service ...");
            WebClient webClient = WebClient.builder().baseUrl(basicHelloUrl).build();
            Mono<String> response = webClient.get().uri("basic/hello").retrieve().bodyToMono(String.class);
            return response.block();
        } catch (RuntimeException e) {
            LOGGER.error("Error due basic service call: {}", e.getMessage(), e);
            throw e;
        }
    }

    private String defaultFallback(final Throwable e) {
        return "Hello from composite fallback: " + e.toString();
    }
}
