package com.example.elasticsearchobservable.elastic;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Mono;

public interface ArticleRepository extends ReactiveElasticsearchRepository<Article, String> {
    Mono<Article> findByName(String name);
}
