package com.example.kafkastreams.consumer;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author diego.costa on 19.02.21
 **/
@Service
public class KafkaThingsStarter {

    @PostConstruct
    private void run() throws IOException {
        OrderStatelessConsumer
                .create("testTopic")
                .start();
    }
}
