package com.business.erp.auth.customer.service;

import com.business.erp.auth.customer.model.Customer;
import com.business.erp.auth.customer.model.CustomerOutstandingResponse;
import com.business.erp.auth.customer.repository.CustomerPaymentRepository;
import com.business.erp.auth.customer.repository.CustomerRepository;
import com.business.erp.sale.repository.SaleRepository;

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
