package com.example.elasticsearchobservable;

import com.example.elasticsearchobservable.elastic.Article;
import com.example.elasticsearchobservable.elastic.ArticleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Arrays;

@SpringBootApplication
public class ElasticSearchObservableApplication {
    @Autowired
    ElasticsearchOperations operations;
    @Autowired
    ArticleRepository repository;

//    public static final ThreadLocal<Long> CORRELATION_ID = new ThreadLocal<>();

    @PreDestroy
    public void deleteIndex() {
        operations.indexOps(Article.class).delete();
    }

    @PostConstruct
    public void insertDataSample() {

        operations.indexOps(Article.class).refresh();

        var documents = Arrays.asList(
                Article.builder().name("Article 1").author("Author 1").content("Test").build(), //
                Article.builder().name("Article 2").author("Author 21").content("Test").build());

        operations.save(documents);
        operations.indexOps(Article.class).refresh();

//        Hooks.enableAutomaticContextPropagation();
//        ContextRegistry.getInstance()
//                .registerThreadLocalAccessor("CORRELATION_ID",
//                        CORRELATION_ID::get,
//                        CORRELATION_ID::set,
//                        CORRELATION_ID::remove);
    }

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchObservableApplication.class, args);
    }
}
