package com.example.openapi.demoopenapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class SampleScheduling {
    private static final Logger logger = LoggerFactory.getLogger(SampleScheduling.class);
    private int counter;

    @Scheduled(fixedDelayString = "${statusjob.delay}")
    public void scheduledIncrement() {
        logger.info("Entered into scheduled method");
        counter++;
    }

    public int getCounter() {
        return counter;
    }
}
