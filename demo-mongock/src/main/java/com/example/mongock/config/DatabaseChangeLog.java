package com.example.mongock.config;

import com.example.mongock.repository.Payment;
import com.example.mongock.repository.PaymentRepository;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

import java.math.BigDecimal;
import java.util.List;

@ChangeLog
public class DatabaseChangeLog {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseChangeLog.class);


    @ChangeSet(order = "001", id = "2022-04-13 CART-20773#1", author = "andrei.shakirin")
    public void createMongoEntity(PaymentRepository repository) {
        logger.info("STARTING MongoBee-Script import payments");
        Payment payment1 = new Payment("payment-load-1", BigDecimal.TEN);
        Payment payment2 = new Payment("payment-load-2", BigDecimal.TEN);
        Payment payment3 = new Payment("payment-load-3", BigDecimal.TEN);
        repository.saveAll(List.of(payment1, payment2, payment3));
        logger.info("END MongoBee-Script import payments");
    }

    @ChangeSet(order = "002", id = "2022-04-13 CART-20773#2", author = "andrei.shakirin")
    public void createIndex(MongockTemplate mongockTemplate) {
        logger.info("STARTING MongoBee-Script indexing");
        IndexOperations idx = mongockTemplate.indexOps(Payment.class);
        Index index = new Index().named("nameIndex").on("name", Sort.Direction.ASC).background();
        idx.ensureIndex(index);
        logger.info("END MongoBee-Script indexing");
    }

    //    @ChangeSet(order = "001", id = "2022-04-13 CART-20773#2", author = "andrei.shakirin")
//    public void testMongoEntity(MongockTemplate template) {
//        log.info("STARTING MongoBee-Script deleteOldEmptyCarts who are older than 30 days");
//        DeleteOldEmptyCarts.deleteOldEmptyCartsWithParallelStream(template);
//        log.info("END MongoBee-Script deleteOldEmptyCarts");
//    }
}
