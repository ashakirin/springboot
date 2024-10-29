package com.example.elasticsearchobservable;

import com.example.elasticsearchobservable.elastic.Article;
import com.example.elasticsearchobservable.elastic.ArticleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import reactor.core.observability.DefaultSignalListener;

@SpringBootTest
//@ExtendWith(OutputCaptureExtension.class)
public class TraceabilityTestContainersTest {
    private static final Logger logger = LoggerFactory.getLogger(ReactiveController.class);
    @Autowired
    private ArticleRepository repository;

    private static ElasticsearchContainer container;

    @BeforeAll
    public static void setup() {
        ElasticsearchContainer container = new ElasticsearchContainer("elasticsearch:8.10.4");
        container.start();
    }

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitContainer::getContainerIpAddress);
        registry.add("spring.rabbitmq.port", rabbitContainer::getFirstMappedPort);
    }


    @Test
    public void checkTracingInLog() {
        MDC.put("traceId", "my trace id");
        MDC.put("spanId", "my span id");
        logger.info("before");
        Object name = repository.findByName("Article 1")
                .map(Article::getName)
                .tap(() -> new DefaultSignalListener<>() {
                    @Override
                    public void doOnComplete() {
                        logger.info("inside doOnComplete");
//                        assertThat(output.getOut()).contains("my trace id");
                    }
                })
                .handle((result, sink) -> {
                    logger.info("inside handle");
  //                  assertThat(output.getOut()).contains("my trace id");
                    sink.next(result);
                })
                .block();
    }
}
