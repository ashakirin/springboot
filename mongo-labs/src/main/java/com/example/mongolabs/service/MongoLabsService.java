package com.example.mongolabs.service;

import com.example.mongolabs.entity.Payment;
import com.example.mongolabs.entity.PaymentItem;
import com.example.mongolabs.entity.PaymentRepository;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.internal.bulk.UpdateRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.util.ClassTypeInformation.COLLECTION;

@Service
public class MongoLabsService {
    private PaymentRepository paymentRepository;

    @Autowired
    private MongoOperations mongoOperations;

    public MongoLabsService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void selectiveUpdate(String id)  {
        Optional<Payment> payment = paymentRepository.findById(id);
        Query query = new Query(new Criteria("_id").is(id));
        Update update = new Update().set("name", "testName").set("amount", BigDecimal.ZERO);
        UpdateResult result = mongoOperations.updateFirst(query, update, "payment");
        System.out.println(result.getMatchedCount() + " - " + result.getModifiedCount());
    }

    public Payment getPayment(String id) {
        return paymentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Payment ID is unknown: " + id));
    }

    public List<Payment> findPayments(String name) {
//        return paymentRepository.findByName(name);
        return paymentRepository.findCustomQuery(name);
    }

    public List<Payment> findPaymentsByDate(LocalDate localDate) {
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return paymentRepository.findByUpdatedLessThanEqual(date);
    }

    public List<Payment> findPaymentsBetween() {
        Date date = new Date();
        return paymentRepository.findByCreatedBetween(DateUtils.addMilliseconds(date, -60000), date);
    }

    public String createPayment(String name, BigDecimal amount) {
        Payment payment = new Payment(name, BigDecimal.TEN);
        PaymentItem paymentItem = new PaymentItem(UUID.randomUUID().toString(), BigDecimal.ONE);
        payment.getPaymentItems().add(paymentItem);
        paymentRepository.save(payment);

        return payment.getId();
    }
}
