package com.business.erp.auth.customer.controller;

import com.business.erp.auth.customer.model.CustomerLedgerResponse;
import com.business.erp.auth.customer.service.CustomerLedgerService;

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
