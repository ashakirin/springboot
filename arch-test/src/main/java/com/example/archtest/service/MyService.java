package com.example.archtest.service;

import com.example.archtest.repository.MyEntity;
import com.example.archtest.repository.MyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MyService {
    private final MyRepository myRepository;

    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public MyDomainObject readAndProcess(String id) {
        Optional<MyEntity> myEntity = myRepository.findById(id);
        return myEntity
                .map(e -> new MyDomainObject(e.getId(), e.getContent()))
                .orElse(null);
    }

    public String save(MyDomainObject myDomainObject) {
        MyEntity myEntity = new MyEntity();
        myEntity.setContent(myDomainObject.getContent());
        String id = UUID.randomUUID().toString();
        myEntity.setId(id);
        myRepository.save(myEntity);
        return id;
    }
}
