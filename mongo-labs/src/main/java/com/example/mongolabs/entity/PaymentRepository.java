package com.example.mongolabs.entity;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PaymentRepository extends CrudRepository<Payment, String> {

    List<Payment> findByName(String name);

    @Query("{'name' : '?0'}")
    List<Payment> findCustomQuery(String name);

    List<Payment> findByUpdatedLessThanEqual(Date date);

    List<Payment> findByCreatedBetween(Date dateFrom, Date dateTo);
}
