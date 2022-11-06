package com.example.batchdemo.job;

import com.example.batchdemo.entity.BookEntity;
import com.example.batchdemo.entity.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ItemReader<String> myReader;
    private final ItemProcessor<BookEntity, String> myProcessor;
    private final ItemWriter<Object> myWriter;

    private final BookRepository bookRepository;

    @Bean
    public Job buildJob(Step myStep) {
        return jobBuilderFactory
                .get("MyFirstJob")
                .start(myStep)
                .build();
    }

    @Bean
    public Step buildStep() {
        RepositoryItemReader<BookEntity> reader = new RepositoryItemReader<>();
        reader.setRepository(bookRepository);
        reader.setMethodName("findAll");
        Map<String, Sort.Direction> sorts = Map.of("id", Sort.Direction.ASC);
        reader.setSort(sorts);
        return stepBuilderFactory
                .get("my first step")
                .<BookEntity, String> chunk(1)
//                .reader(myReader)
                .reader(reader)
                .processor(myProcessor)
                .writer(myWriter)
                .build();
    }
}
