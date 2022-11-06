package com.example.batchdemo.job;

import com.example.batchdemo.entity.BookEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyProcessor implements ItemProcessor<BookEntity, String> {
    @Override
    public String process(BookEntity s) throws Exception {
        return s.getName() + " " + s.getId();
    }
}
