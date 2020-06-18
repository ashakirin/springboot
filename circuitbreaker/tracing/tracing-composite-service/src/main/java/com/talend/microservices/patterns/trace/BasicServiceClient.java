package com.talend.microservices.patterns.trace;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BasicServiceClient {
    private static final String HYSTRIX_DEFAULT_CONFIG = "default";
    public static final String LONG_RUNNING_CALL = "default";
    Logger LOGGER = LoggerFactory.getLogger(BasicServiceClient.class);
    private static final String HYSTRIX_HELLO = "hello";
    private static final String DEFAULT_HYSTRIX_CONFIG = "default";

    private final RestTemplateBuilder restTemplateBuilder;
    private final String basicHelloUrl;

    public BasicServiceClient(final RestTemplateBuilder restTemplateBuilder, @Value("${basic.hello.url}") final String basicHelloUrl) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.basicHelloUrl = basicHelloUrl;
    }

    @HystrixCommand(fallbackMethod = "defaultFallback",
            groupKey = HYSTRIX_DEFAULT_CONFIG,
            commandKey = HYSTRIX_DEFAULT_CONFIG,
            threadPoolKey = LONG_RUNNING_CALL
    )
    public String invokeBasic() {
        try {
            LOGGER.info("Invoking basic service ...");
            RestTemplate restTemplate = restTemplateBuilder.rootUri(basicHelloUrl).build();
            final String response = restTemplate.getForObject("/basic/hello", String.class);
            LOGGER.info("Returned from basic service ...");
            return response;
        } catch (RuntimeException e) {
            LOGGER.error("Error due basic service call: {}", e.getMessage(), e);
            throw e;
        }
    }

    @HystrixCommand(fallbackMethod = "defaultFallback",
            groupKey = HYSTRIX_DEFAULT_CONFIG,
            commandKey = HYSTRIX_DEFAULT_CONFIG,
            threadPoolKey = LONG_RUNNING_CALL
//            commandProperties = {
//                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "15000"),
//            }
    )
    public String invokeSendTopic() {
        try {
            LOGGER.info("Invoking basic service with send operation ...");
            RestTemplate restTemplate = restTemplateBuilder.rootUri(basicHelloUrl).build();
            final String response = restTemplate.getForObject("/basic/send", String.class);
            LOGGER.info("Returned from basic service send operation ...");
            return response;
        } catch (RuntimeException e) {
            LOGGER.error("Error due basic service call: {}", e.getMessage(), e);
            throw e;
        }
    }

    private String defaultFallback(final Throwable e) {
        return "Hello from composite fallback: " + e.toString();
    }
}
