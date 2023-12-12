package com.example.observability;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Configuration(proxyBeanMethods = false)
class MyConfiguration {
    private static Logger logger = LoggerFactory.getLogger(MyConfiguration.class);
    // IMPORTANT! To instrument RestTemplate you must inject the RestTemplateBuilder
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    CommandLineRunner myCommandLineRunner(ObservationRegistry registry, RestTemplate restTemplate) {
        Random highCardinalityValues = new Random(); // Simulates potentially large number of values
        List<String> lowCardinalityValues = Arrays.asList("userType1", "userType2", "userType3"); // Simulates low number of values
        return args -> {
            String highCardinalityUserId = String.valueOf(highCardinalityValues.nextLong(100_000));
            // Example of using the Observability API manually
            // <my.observation> is a "technical" name that does not depend on the context. It will be used to name e.g. Metrics
            Observation.createNotStarted("my.observation", registry)
                    // Low cardinality means that the number of potential values won't be big. Low cardinality entries will end up in e.g. Metrics
                    .lowCardinalityKeyValue("userType", "userType1")
                    // High cardinality means that the number of potential values can be large. High cardinality entries will end up in e.g. Spans
                    .highCardinalityKeyValue("userId", highCardinalityUserId)
                    // <command-line-runner> is a "contextual" name that gives more details within the provided context. It will be used to name e.g. Spans
                    .contextualName("command-line-runner")
                    // The following lambda will be executed with an observation scope (e.g. all the MDC entries will be populated with tracing information). Also the observation will be started, stopped and if an error occurred it will be recorded on the observation
                    .observe(() -> {
                        logger.info("Will send a request to the server"); // Since we're in an observation scope - this log line will contain tracing MDC entries ...
                        String response = restTemplate.getForObject("http://localhost:7654/user/{userId}", String.class, highCardinalityUserId); // Boot's RestTemplate instrumentation creates a child span here
                        logger.info("Got response [{}]", response); // ... so will this line
                    });

        };
    }
}