package com.example.kafka.testkafka.messaging;

import com.example.kafka.testkafka.entity.TransactionalSaver;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.transaction.ChainedKafkaTransactionManager;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;

import static org.springframework.transaction.support.AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION;

public class TransactionKafka {
    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public ChainedKafkaTransactionManager<?, ?> chainedTxM(JpaTransactionManager jpa,
                                                           KafkaTransactionManager<?, ?> kafka) {
        kafka.setTransactionSynchronization(SYNCHRONIZATION_ON_ACTUAL_TRANSACTION);
        return new ChainedKafkaTransactionManager<>(kafka, jpa);
    }

    @Autowired
    private TransactionalSaver saver;

    @KafkaListener(id = "so58804826", topics = "so58804826")
    public void listen(String in) {
        System.out.println("Storing: " + in);
        this.saver.save(in);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("so58804826")
                .partitions(1)
                .replicas(1)
                .build();
    }

//    @Bean
//    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
//        return args -> {
//          template.executeInTransaction(t -> t.send("so58804826", "foo"));
//        };
//    }
}
