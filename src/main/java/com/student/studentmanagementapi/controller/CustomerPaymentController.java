package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.entity.CustomerPayment;
import com.student.studentmanagementapi.service.CustomerPaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-payments")
public class CustomerPaymentController {

    @Autowired
    private CustomerPaymentService customerPaymentService;


    // ==========================================
    // ADD PAYMENT
    // ==========================================

    @PostMapping("/customer/{customerId}")
    public CustomerPayment addPayment(
            @PathVariable Long customerId,
            @RequestParam(required = false) Long saleId,
            @RequestBody CustomerPayment payment) {

        return customerPaymentService.addPayment(
                customerId,
                saleId,
                payment
        );
    }


    // ==========================================
    // GET ALL PAYMENTS
    // ==========================================

    @GetMapping
    public List<CustomerPayment> getAllPayments() {

        return customerPaymentService.getAllPayments();
    }


    // ==========================================
    // GET PAYMENT BY ID
    // ==========================================

    @GetMapping("/{id}")
    public CustomerPayment getPaymentById(
            @PathVariable Long id) {

        return customerPaymentService.getPaymentById(id);
    }


    // ==========================================
    // DELETE PAYMENT
    // ==========================================

    @DeleteMapping("/{id}")
    public String deletePayment(
            @PathVariable Long id) {

        customerPaymentService.deletePayment(id);

        return "Payment deleted successfully.";
    }
}