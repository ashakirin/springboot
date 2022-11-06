package com.example.batchdemo.job;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyWriter implements ItemWriter<Object> {
    @Override
    public void write(List<?> list) throws Exception {
        list.forEach(System.out::println);
    }
}
