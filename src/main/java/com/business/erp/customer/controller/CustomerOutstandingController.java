package com.business.erp.customer.controller;

import com.business.erp.customer.dto.CustomerOutstandingResponse;
import com.business.erp.customer.service.CustomerOutstandingService;

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
