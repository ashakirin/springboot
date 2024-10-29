package com.example.elasticsearchobservable;

import com.example.elasticsearchobservable.elastic.Article;
import com.example.elasticsearchobservable.elastic.ArticleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.observability.DefaultSignalListener;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class ReactiveController {
    private static final Logger logger = Loggers.getLogger(ReactiveController.class);

    private final ArticleRepository repository;

    public ReactiveController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/echo")
    public Mono<Object> getSimpleString() {
        logger.info("Enter controller");
        Mono<Object> name = repository.findByName("Article 1")
                .map(Article::getName)
                .tap(() -> new DefaultSignalListener<>() {
                    @Override
                    public void doOnComplete() {
                        logger.info("inside repo");
                    }
                })
                .handle((result, sink) -> {
                    logger.info("inside handle");
                    sink.next(result);
                })
                .log(logger);
//                .contextCapture();
        logger.info("After elastic search call");
        return name;
    }

//    private static long correlationId() {
//        return Math.abs(ThreadLocalRandom.current().nextLong());
//    }
}
