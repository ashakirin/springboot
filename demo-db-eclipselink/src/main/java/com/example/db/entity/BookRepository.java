package com.example.db.entity;


import com.example.db.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class BookRepository {
    private EntityManager em;

    @Autowired
    public BookRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<BookEntity> findByName(final String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<BookEntity> cr = cb.createQuery(BookEntity.class);
        Root<BookEntity> root = cr.from(BookEntity.class);
        cr.select(root).where(cb.equal(root.get("name"), name));

        Query query = em.createQuery(cr);
        query.setMaxResults(1);
        List<BookEntity> result = query.getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<BookEntity> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<BookEntity> cr = cb.createQuery(BookEntity.class);
        Root<BookEntity> root = cr.from(BookEntity.class);

        Query query = em.createQuery(cr);
        query.setMaxResults(1);
        List<BookEntity> result = query.getResultList();

        return result;
    }

    public Optional<BookEntity> findById(Integer id) {
        return Optional.ofNullable(em.find(BookEntity.class, id));
    }

    public void save(BookEntity book) {
        em.persist(book);
    }
}
