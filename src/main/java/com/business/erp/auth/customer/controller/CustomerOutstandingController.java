package com.business.erp.auth.customer.controller;

import com.business.erp.auth.customer.model.CustomerOutstandingResponse;
import com.business.erp.auth.customer.service.CustomerOutstandingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer-outstanding")
public class CustomerOutstandingController {

    @Autowired
    private CustomerOutstandingService customerOutstandingService;


    // ==========================================
    // GET CUSTOMER OUTSTANDING
    // ==========================================

    @GetMapping("/customer/{customerId}")
    public CustomerOutstandingResponse getCustomerOutstanding(
            @PathVariable Long customerId) {

        return customerOutstandingService
                .getCustomerOutstanding(customerId);
    }
}
