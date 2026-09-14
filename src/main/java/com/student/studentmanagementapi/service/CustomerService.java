package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.Customer;
import com.student.studentmanagementapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Add Customer
    public Customer addCustomer(Customer customer) {

        if (customerRepository.existsByMobile(customer.getMobile())) {
            throw new RuntimeException("Mobile number already exists");
        }

        return customerRepository.save(customer);
    }

    // Get All Customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get Customer By I'd
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // Update Customer
    public Customer updateCustomer(Long id, Customer updatedCustomer) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Check duplicate mobile (ignore current customer's own mobile)
        if (!customer.getMobile().equals(updatedCustomer.getMobile())
                && customerRepository.existsByMobile(updatedCustomer.getMobile())) {
            throw new RuntimeException("Mobile number already exists");
        }

        customer.setName(updatedCustomer.getName());
        customer.setMobile(updatedCustomer.getMobile());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setAddress(updatedCustomer.getAddress());

        return customerRepository.save(customer);
    }

    // Delete Customer
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customerRepository.delete(customer);
    }


    // Get Customer By Mobile
    public Customer getCustomerByMobile(String mobile) {

        return customerRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // Search Customer By Name
    public List<Customer> searchCustomerByName(String name) {

        return customerRepository.findByNameContainingIgnoreCase(name);
    }

}