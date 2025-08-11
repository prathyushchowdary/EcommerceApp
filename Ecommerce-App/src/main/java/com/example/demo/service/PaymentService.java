package com.example.demo.service;

import com.example.demo.model.Payment;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public void savePayment(Payment payment) {
        repository.save(payment);
    }

    public Payment getPaymentById(Long id) throws ResourceNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with '" + id + "'"));
    }

    public void deletePayment(Long id) {
        repository.deleteById(id);
    }
}
