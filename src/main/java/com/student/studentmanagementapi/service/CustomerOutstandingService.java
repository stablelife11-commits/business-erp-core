package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.dto.CustomerOutstandingResponse;
import com.student.studentmanagementapi.entity.Customer;
import com.student.studentmanagementapi.repository.CustomerPaymentRepository;
import com.student.studentmanagementapi.repository.CustomerRepository;
import com.student.studentmanagementapi.repository.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CustomerOutstandingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerPaymentRepository customerPaymentRepository;

    @Autowired
    private SaleRepository saleRepository;


    // ==========================================
    // CUSTOMER OUTSTANDING
    // ==========================================

    public CustomerOutstandingResponse getCustomerOutstanding(
            Long customerId) {

        // ------------------------------------------
        // Find Customer
        // ------------------------------------------

        Customer customer =
                customerRepository.findById(customerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer not found: " + customerId
                                )
                        );


        // ------------------------------------------
        // Total Sales
        // ------------------------------------------

        BigDecimal totalSales =
                saleRepository.getTotalSalesByCustomerMobile(
                        customer.getMobile()
                );


        // ------------------------------------------
        // Total Paid
        // ------------------------------------------

        BigDecimal totalPaid =
                customerPaymentRepository.getTotalPaidByCustomer(
                        customerId
                );


        // ------------------------------------------
        // Outstanding
        // ------------------------------------------

        BigDecimal outstanding =
                totalSales.subtract(totalPaid);


        // ------------------------------------------
        // Prepare Response
        // ------------------------------------------

        CustomerOutstandingResponse response =
                new CustomerOutstandingResponse();

        response.setCustomerId(customer.getId());

        response.setCustomerName(customer.getName());

        response.setMobile(customer.getMobile());

        response.setTotalSales(totalSales);

        response.setTotalPaid(totalPaid);

        response.setOutstanding(outstanding);


        return response;
    }
}