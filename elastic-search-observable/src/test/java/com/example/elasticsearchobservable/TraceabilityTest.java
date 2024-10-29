package com.example.elasticsearchobservable;

import com.example.elasticsearchobservable.elastic.Article;
import com.example.elasticsearchobservable.elastic.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.tracing.MicrometerTracingAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import reactor.core.observability.DefaultSignalListener;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@ExtendWith(OutputCaptureExtension.class)
public class TraceabilityTest {
    private static final Logger logger = LoggerFactory.getLogger(ReactiveController.class);
    @Autowired
    private ArticleRepository repository;

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
