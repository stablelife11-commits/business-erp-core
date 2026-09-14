package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.dto.CustomerOutstandingResponse;
import com.student.studentmanagementapi.service.CustomerOutstandingService;

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