package com.example.demo.controller;

import com.example.demo.model.Payment;
import com.example.demo.service.PaymentService;
import com.example.demo.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/payment-home")
    public String showHomePage(Model model) {
        List<Payment> paymentsList = paymentService.getAllPayments();
        model.addAttribute("paymentList", paymentsList);
        return "payment-home";
    }

    @GetMapping("/add-payment")
    public String showAddPaymentPage(Model model) {
        model.addAttribute("payment", new Payment());
        return "add-payment";
    }

    @PostMapping("/save-payment")
    public String savePayment(@ModelAttribute("payment") Payment payment,
                              RedirectAttributes redirectAttributes) {
        paymentService.savePayment(payment);
        redirectAttributes.addFlashAttribute("message", "Payment Added Successfully");
        return "redirect:/payment-home";
    }

    @GetMapping("/edit-payment/{id}")
    public String showEditPaymentPage(Model model, @PathVariable Long id) throws ResourceNotFoundException {
        model.addAttribute("payment", paymentService.getPaymentById(id));
        return "edit-payment";
    }

    @PostMapping("/update-payment")
    public String updatePayment(@ModelAttribute("payment") Payment payment) throws ResourceNotFoundException {
        Payment existing = paymentService.getPaymentById(payment.getId());
        existing.setMethod(payment.getMethod());
        existing.setAmount(payment.getAmount());
        existing.setStatus(payment.getStatus());
        paymentService.savePayment(existing);
        return "redirect:/payment-home";
    }

    @GetMapping("/delete-payment/{id}")
    public String deletePayment(@PathVariable Long id) throws ResourceNotFoundException {
        paymentService.getPaymentById(id); // validate exists
        paymentService.deletePayment(id);
        return "redirect:/payment-home";
    }
}
