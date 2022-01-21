package com.example.demodbeclipselinktest.entity;


import com.example.demodbeclipselinktest.entity.AuthorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public class AuthorRepository {
    private EntityManager em;

    @Autowired
    public AuthorRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<AuthorEntity> findById(Integer id) {
        return Optional.ofNullable(em.find(AuthorEntity.class, id));
    }

    public void save(AuthorEntity author) {
        em.persist(author);
    }
}
