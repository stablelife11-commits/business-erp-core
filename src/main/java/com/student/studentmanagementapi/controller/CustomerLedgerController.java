package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.dto.CustomerLedgerResponse;
import com.student.studentmanagementapi.service.CustomerLedgerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer-ledger")
public class CustomerLedgerController {

    @Autowired
    private CustomerLedgerService customerLedgerService;


    // ==========================================
    // GET CUSTOMER LEDGER
    // ==========================================

    @GetMapping("/customer/{customerId}")
    public CustomerLedgerResponse getCustomerLedger(
            @PathVariable Long customerId) {

        return customerLedgerService.getCustomerLedger(customerId);
    }
}