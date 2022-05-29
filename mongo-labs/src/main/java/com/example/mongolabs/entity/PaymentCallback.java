package com.example.mongolabs.entity;

import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class PaymentCallback implements BeforeConvertCallback<Payment>, BeforeSaveCallback<Payment> {

    @Override
    public Payment onBeforeConvert(Payment entity, String collection) {
        Date date = new Date();
        entity.setUpdated(date);
        if (entity.getId() == null) {
            entity.setCreated(date);
        }
        return entity;
    }

    @Override
    public Payment onBeforeSave(Payment entity, Document document, String collection) {
        System.out.println("before save!!!");
        return entity;
    }
}
