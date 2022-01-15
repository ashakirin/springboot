package com.example.mongolabs.service;

import com.example.mongolabs.entity.Payment;
import com.example.mongolabs.entity.PaymentItem;
import com.example.mongolabs.entity.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class MongoLabsService {
    private PaymentRepository paymentRepository;

    public MongoLabsService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment getPayment(String id) {
        return paymentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Payment ID is unknown: " + id));
    }

    public List<Payment> findPayments(String name) {
//        return paymentRepository.findByName(name);
        return paymentRepository.findCustomQuery(name);
    }

    public String createPayment(String name, BigDecimal amount) {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, name, BigDecimal.TEN);
        PaymentItem paymentItem = new PaymentItem(UUID.randomUUID().toString(), BigDecimal.ONE);
        payment.getPaymentItems().add(paymentItem);
        paymentRepository.save(payment);

        return paymentId;
    }
}
