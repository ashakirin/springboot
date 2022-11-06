package com.example.batchdemo.job;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyReader implements ItemReader<String> {
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (counter.incrementAndGet() > 100) {
            return null;
        }
        return "my first item: " + System.currentTimeMillis();
    }
}
