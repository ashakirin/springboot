package com.example.mongolabs.controller;

import com.example.mongolabs.entity.Payment;
import com.example.mongolabs.entity.PaymentItem;
import com.example.mongolabs.entity.PaymentRepository;
import com.example.mongolabs.service.MongoLabsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/labs")
public class MongoLabsController {
    private MongoLabsService mongoLabsService;

    @Autowired
    public MongoLabsController(MongoLabsService mongoLabsService) {
        this.mongoLabsService = mongoLabsService;
    }

    @GetMapping("/payments/{id}")
    public @ResponseBody Payment getPayment(@PathVariable("id") String id) {
        return mongoLabsService.getPayment(id);
    }

    @GetMapping("/payments")
    public @ResponseBody List<Payment> findPayment(@RequestParam("name") String name) {
        return mongoLabsService.findPayments(name);
    }

    @GetMapping("/payments/date")
    public @ResponseBody List<Payment> findPaymentByDate() {
        return mongoLabsService.findPaymentsByDate(LocalDate.now());
    }

    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment() throws URISyntaxException {
        String paymentId = mongoLabsService.createPayment("test", BigDecimal.TEN);
        return ResponseEntity.created(new URI(paymentId)).build();
    }

    @PutMapping("/payments/selective")
    public void updatePaymenSelective() {
        mongoLabsService.selectiveUpdate("625aa8e24023d84f4359c73b");
    }

    @GetMapping("/payments/between")
    public @ResponseBody List<Payment> findPaymentBetween() {
        return mongoLabsService.findPaymentsBetween();
    }
}
