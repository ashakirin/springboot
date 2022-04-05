package com.example.demomoduletest.repository1;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class GenericCascadingListener extends AbstractMongoEventListener<Object> {
    private final MongoTemplate mongoTemplate;

    public GenericCascadingListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), field -> {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DBRef.class)) {
                mongoTemplate.save(field.get(source));
            }
        });
    }
}
