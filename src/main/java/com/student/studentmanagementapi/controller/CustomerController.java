package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.entity.Customer;
import com.student.studentmanagementapi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Add Customer
    @PostMapping
    public Customer addCustomer(@Valid @RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    // Get All Customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Get Customer By ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // Update Customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody Customer customer) {

        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    // Delete Customer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

    // Get Customer By Mobile
    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<Customer> getCustomerByMobile(@PathVariable String mobile) {

        return ResponseEntity.ok(customerService.getCustomerByMobile(mobile));
    }

    // Search Customer By Name
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomerByName(
            @RequestParam String name) {

        return ResponseEntity.ok(customerService.searchCustomerByName(name));
    }
}