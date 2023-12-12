package com.example.elasticsearchobservable;

import com.example.elasticsearchobservable.elastic.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveController {
    private static final Logger logger = LoggerFactory.getLogger(ReactiveController.class);

    private final ArticleRepository repository;

    public ReactiveController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/echo")
    public Mono<String> getSimpleString() {
        logger.info("Enter controller");
        Mono<String> name = repository.findByName("Article 1")
                .map(a -> a.getName());
        logger.info("After elastic search call");
        return name;
    }
}
